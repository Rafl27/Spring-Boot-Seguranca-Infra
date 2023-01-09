package br.com.alura.forum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService
    ) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        //por meio do http? estou usando o null safe do kotlin, assim ele verifica se a variavel
        //http esta nula, caso for true ele nem cria um pointer.

        /*WHAT AN HTTP SESSION IS
        * Provides a way to identify a user across more than one page
        * request or visit to a Web site and to store information about that user.
        *
        * STATELESS: Spring Security will never create an
        * HttpSession and it will never use it to obtain the SecurityContext*/
        http?.
        authorizeRequests()?.
        antMatchers("/topicos")?.hasAuthority("LEITURA_ESCRITA")?.
        anyRequest()?.
        authenticated()?.
        and()?.
        sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.
        and()?.
        formLogin()?.disable()?.
        httpBasic()

        /* Por meio da variavel acima, estou fazendo as configuracoes basicas de http security
       * antes do primeiro and, ele autoriza requests de qualquer um que for autenticado
       * no segundo cria uma session stateless
       * e no terceiro desabilita aquele form automatico do spring e utiliza o http basic,
       * que e onde passa a info do usuario pelo postman por exemplo.
       * */
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(bCryptPasswordEncoder())
    }
}