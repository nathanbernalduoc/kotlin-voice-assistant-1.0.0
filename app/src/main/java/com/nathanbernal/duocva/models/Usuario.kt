package com.nathanbernal.duocva.models

data class Usuario (val email: String, val nombre: String, val contrasena: String) {
    companion object {
        private val listaUsuario = mutableListOf<Usuario>()

        fun agregarUsuario(usuario: Usuario) {
            listaUsuario.add(usuario)
        }

        fun obtenerUsuario(email: String): Usuario? {
            return listaUsuario.find { it.email.equals(email.trim()) }
        }
        fun obtenerUsuarios(): List<Usuario> {
            return listaUsuario
        }
    }
}