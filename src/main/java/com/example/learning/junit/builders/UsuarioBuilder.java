package com.example.learning.junit.builders;

import com.example.learning.junit.entidades.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder() {

    }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder usuarioBuilder = new UsuarioBuilder();
        usuarioBuilder.usuario = new Usuario();
        usuarioBuilder.usuario.setNome("Carol");
        return usuarioBuilder;
    }

    public Usuario agora() {
        return usuario;
    }
}
