package com.example.kotlin_spring_login_passkey.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService() : UserDetailsService{
        val user = User.withUsername("username_123")
            .password(passwordEncoder().encode("12345"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.authorizeHttpRequests {
            req ->
            req.anyRequest().authenticated() }
            .formLogin { Customizer.withDefaults<HttpSecurity>() }
            .webAuthn { webAuthn -> webAuthn
                .rpName("Spring Security Relying Party")
                .rpId("localhost")
                .allowedOrigins("http://localhost:8080") // must use own domain name and https: when in production
            }
        return httpSecurity.build()
    }
}