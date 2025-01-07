package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nathanbernal.duocva.models.Usuario
import com.nathanbernal.duocva.ui.theme.DuocVATheme

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuocVATheme {
                RegisterForm()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterForm() {
    var email = remember { mutableStateOf("") }
    var nombre = remember { mutableStateOf("") }
    var contrasena = remember { mutableStateOf("") }

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
        ) {
            Text(
                "<",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Registro de nuevo usuario",
            color = Color.Yellow,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            email.value,
            onValueChange = { email.value = it}
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            nombre.value,
            onValueChange = { nombre.value = it}
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            contrasena.value,
            onValueChange = { contrasena.value = it}
        )

        Spacer(modifier = Modifier.height(60.dp))

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                val usuario = Usuario(email.value, nombre.value, contrasena.value)
                Usuario.agregarUsuario(usuario)
                Toast.makeText(context, "Usuario almacenado", Toast.LENGTH_SHORT).show()
                // volviendo a login
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
        ) {
            Text(
                "Registrar",
                fontSize = 20.sp
            )
        }

    }

}