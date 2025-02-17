package com.nathanbernal.duocva

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.nathanbernal.duocva.models.Usuario

data class UsuarioService (
    val email: String,
    val nombre: String,
    val contrasena: String
)

data class DiccionarioService (
    val frase: String,
    val etiqueta: String,
    val audio: String
)

class ApiServices {

    private val databaseUsuario: DatabaseReference = Firebase.database.reference.child("usuario")
    private val databaseDiccionario: DatabaseReference = Firebase.database.reference.child("diccionario")

    fun enviarDatosUsuario(usuarioData: Map<String, UsuarioService>) {
        databaseUsuario.setValue(usuarioData).addOnSuccessListener {
            println("Usuario: Datos enviados")
        }.addOnFailureListener { error ->
            println("Error al enviar los datos: ${error.message}")
        }
    }

    fun enviarDatosDiccionario(diccionarioData: Map<String, DiccionarioService>) {
        databaseDiccionario.setValue(diccionarioData).addOnSuccessListener {
            println("Diccionario: Datos enviados")
        }.addOnFailureListener { error ->
            println("Error al enviar los datos: ${error.message}")
        }
    }

    fun crearDatosUsuario(): Map<String, UsuarioService> {
        return mapOf(
            "100" to UsuarioService(
                "nanobernal@123.cl",
                "123123",
                "Nano"
            )
        )
    }

    fun createDatosDiccionario(): Map<String, DiccionarioService> {
        return mapOf(
            "100" to DiccionarioService(
                "homanundo",
                "Hola Mundo",
                "holamundo"
            )
        )
    }

}