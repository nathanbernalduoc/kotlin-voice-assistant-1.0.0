package com.nathanbernal.duocva

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.nathanbernal.duocva.models.Usuario
import java.math.BigInteger
import java.security.MessageDigest

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

        var usuariosAll = getUsuarios()
        var usuarios = usuariosAll //mutableMapOf<String, UsuarioService>()

        Log.d("USUARIOS", usuariosAll.toString())
        Log.d("USUARIOS", usuarioData.toString())

        for (usuarioItem in usuarioData) {
            usuarios.put(usuarioItem.key, usuarioItem.value)
        }

        Log.d("USUARIOS", usuarios.toString())

        databaseUsuario.setValue(usuarios).addOnSuccessListener {
            Log.d("[API SERVICE]","Usuario: Datos enviados")
        }.addOnFailureListener { error ->
            Log.d("[API SERVICE]", "Error al enviar los datos: ${error.message}")
        }
    }

    fun enviarDatosDiccionario(diccionarioData: Map<String, DiccionarioService>) {
        databaseDiccionario.setValue(diccionarioData).addOnSuccessListener {
            println("Diccionario: Datos enviados")
        }.addOnFailureListener { error ->
            println("Error al enviar los datos: ${error.message}")
        }
    }

    fun getUsuarios(): MutableMap<String, UsuarioService> {

        var usuarios = mutableMapOf<String, UsuarioService>()
        var database = FirebaseDatabase.getInstance().getReference("usuario")

        val usuarioRef = database.child("/")

        usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    usuarios.clear()
                    for(usuarioItem in snapshot.children) {
                        var u = UsuarioService(
                            email = usuarioItem.child("usuario").getValue().toString(),
                            nombre = usuarioItem.child("nombre").getValue().toString(),
                            contrasena = usuarioItem.child("contrasena").getValue().toString())
                        usuarios.put(usuarioItem.key.toString(), u)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("[Login]", "Error al obtener datos del usuario ${error.message}")
            }
        })

        return usuarios

    }

    fun getUsuariosAll(): MutableList<UsuarioService> {

        var usuarios = mutableListOf<UsuarioService>()
        var database = FirebaseDatabase.getInstance().getReference("usuario")
        val usuarioRef = database.child("/")

        usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    usuarios.clear()
                    for(usuarioItem in snapshot.children) {
                        var u = UsuarioService(
                            email = usuarioItem.child("usuario").getValue().toString(),
                            nombre = usuarioItem.child("nombre").getValue().toString(),
                            contrasena = usuarioItem.child("contrasena").getValue().toString())
                        usuarios.add(u)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("[Login]", "Error al obtener datos del usuario ${error.message}")
            }
        })

        return usuarios
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun crearDatosUsuario(usuarios: List<UsuarioService>, usuarioIn: Usuario): Map<String, UsuarioService> {

        return mapOf(
            this.md5(usuarioIn.email) to UsuarioService(
                email = usuarioIn.email,
                nombre = usuarioIn.nombre,
                contrasena = usuarioIn.contrasena
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