package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nathanbernal.duocva.ui.theme.DuocVATheme

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuocVATheme {
                Login()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Login() {
    var usuario = remember { mutableStateOf("") }
    var contrasena = remember { mutableStateOf("") }
    var showPassword = remember { mutableStateOf(false) }

    val icon = if (showPassword.value) {
        painterResource(id = R.drawable.design_ic_visibility)
    } else {
        painterResource(id = R.drawable.design_ic_visibility_off)
    }

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Voice Assistant",
            color = Color.Yellow,
            fontSize = 50.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Email",
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            usuario.value,
            onValueChange = { usuario.value = it}
        )
        Text(
            text = "Contraseña",
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            contrasena.value,
            onValueChange = { contrasena.value = it},
            label = { Text("Password") },
            trailingIcon = {
                IconButton(onClick =  {
                    showPassword.value = !showPassword.value
                } ) {
                    Icon(
                        painter = painterResource(id = R.drawable.design_ic_visibility ),
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            visualTransformation = if(showPassword.value) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {

                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)

            },
        ) {
            Text(
                "Entrar",
                fontSize = 20.sp
            )
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {

                val intent = Intent(context, RecoverActivity::class.java)
                context.startActivity(intent)

            }
        ) {
            Text(
                "Recuperar contraseña",
                fontSize = 20.sp
            )
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {

                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)

            },
        ) {
            Text(
                "Registrarse",
                fontSize = 20.sp
            )
        }
    }
}
