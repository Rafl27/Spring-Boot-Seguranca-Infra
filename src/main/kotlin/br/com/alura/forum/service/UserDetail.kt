package br.com.alura.forum.service

import br.com.alura.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val usuario: Usuario
    ) : UserDetails{

    //    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//
//    }

    //No Kotlin quando se tem apenas um retono em uma linha, como e o caso abaixo, apenas =null e o suficiente.
    override fun getAuthorities() = usuario.role

    override fun getPassword() = usuario.password

    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}