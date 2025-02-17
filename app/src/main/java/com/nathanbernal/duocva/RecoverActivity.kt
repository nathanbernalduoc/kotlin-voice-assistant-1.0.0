package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nathanbernal.duocva.models.Usuario
import com.nathanbernal.duocva.ui.theme.DuocVATheme

class RecoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuocVATheme {
                RecoverForm()
            }
        }
    }
}

@Composable
fun RecoverForm() {

    var email = remember { mutableStateOf("") }
    Log.d("[LoginActivity]", "Cargando Firebase...")
    var database = FirebaseDatabase.getInstance().getReference("usuario")
    Log.d("[LoginActivity]", "Firebase cargado!")
    val context = LocalContext.current


    Column (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            modifier = Modifier
                .background(color = Color.Transparent)
                .align(Alignment.Start)
                .padding(top = 0.dp),
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
        ) {

            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = null
            )

        }

        Image(
            painter = painterResource(id = R.drawable.oreja2_plus),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .fillMaxWidth()
                .padding(top = 0.dp),
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.app_title),
            color = Color.DarkGray,
            fontSize = 50.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Recuperar contraseña",
            color = colorResource(R.color.purple),
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            email.value,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 20.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onClick = {

                if (!EmailValida(email.value)) {
                    Toast.makeText(context, "El email ingresado es incorrecto (Email validación)", Toast.LENGTH_SHORT).show()
                } else {

                    val usuarioRef = database.child("/")
                    usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Log.d("[Login]", "Recuperando usuario")
                            var usuarioSelect:String = ""
                            if (snapshot.exists()) {
                                for (usuarioItem in snapshot.children) {
                                    Log.d("[RECOVER]", usuarioItem.toString())
                                    Log.d("[RECOVER]", "Pass "+usuarioItem.child("password").getValue().toString())
                                    val usuario = usuarioItem.child("usuario").getValue().toString()
                                    val password = usuarioItem.child("password").getValue().toString()
                                    if (usuario.equals(email.value.trim())) {
                                        usuarioSelect = usuario
                                        val contrasena =
                                            usuarioItem.child("password").getValue().toString()
                                        Toast.makeText(
                                            context,
                                            "Su contraseña es " + contrasena,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                if (usuarioSelect.isEmpty()) {
                                    Toast.makeText(context, "El usuario especificado no existe", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "El email de usuario no existe",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                context,
                                "No se ha podido recuperar la contraseña.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("[Login]", "Error al obtener datos del usuario ${error.message}")
                        }
                    })

                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Mostrar contraseña",
                fontSize = 20.sp
            )
        }

    }

}

fun EmailValida(email: String):Boolean {

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())

}