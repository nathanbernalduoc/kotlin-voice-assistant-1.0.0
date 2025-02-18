package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
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
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .fillMaxHeight()
            .padding(top = 0.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

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
            text = "Registro de nuevo usuario",
            color = colorResource(R.color.purple),
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(10.dp))

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
            onValueChange = { email.value = it},
            placeholder = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            nombre.value,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onValueChange = { nombre.value = it},
            placeholder = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            contrasena.value,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onValueChange = { contrasena.value = it},
            placeholder = { Text("Contraseña") }

        )

        Spacer(modifier = Modifier.height(10.dp))

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

                val api = ApiServices()
                val usuariosList = api.getUsuariosAll()
                var usuarioService = api.crearDatosUsuario(usuariosList, Usuario(email = email.value, nombre = nombre.value, contrasena = contrasena.value))
                api.enviarDatosUsuario(usuarioService)

                Toast.makeText(context, "Usuario almacenado", Toast.LENGTH_SHORT).show()

                // volviendo a login
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Crear mi cuenta",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.oreja2_plus),
                modifier = Modifier
                    .width(30.dp),
                contentDescription = null
            )
        }

    }

}