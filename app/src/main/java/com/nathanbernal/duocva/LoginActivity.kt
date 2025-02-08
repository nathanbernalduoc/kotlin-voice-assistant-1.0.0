package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
import android.text.Layout
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nathanbernal.duocva.models.Usuario
import com.nathanbernal.duocva.ui.theme.DuocVATheme

open class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuocVATheme {
                // Usuario default
                Usuario.agregarUsuario(Usuario("nathanbernal@gmail.com", "Nathan Bernal", "123"))
                Login()
            }
        }
    }
}

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
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(id = R.drawable.oreja2),
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

        Spacer(modifier = Modifier.height(5.dp))

        /*Text(
            text = "Email",
            modifier = Modifier.padding(16.dp)
        )*/
        TextField(
            usuario.value,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onValueChange = { usuario.value = it},
            placeholder = { Text("Email") }
        )
        /*Text(
            text = "Contraseña",
            modifier = Modifier.padding(16.dp)
        )*/
        Spacer(modifier = Modifier.height(5.dp))

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
        Spacer(modifier = Modifier.height(5.dp))
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

                val usuario = Usuario.obtenerUsuario(usuario.value)
                if (usuario != null && usuario.contrasena == contrasena.value) {
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Entrar",
                fontSize = 20.sp
            )
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null
            )
        }
        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onClick = {
                val intent = Intent(context, RecoverActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Recuperar contraseña",
                fontSize = 15.sp
            )
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null
            )
        }
        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 0.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth(),
            onClick = {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {

            Text(
                "Registrarse",
                fontSize = 15.sp
            )
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null
            )

        }
    }
}
