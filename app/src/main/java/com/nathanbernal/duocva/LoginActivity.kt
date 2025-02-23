package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.nathanbernal.duocva.models.Usuario
import com.nathanbernal.duocva.ui.theme.DuocVATheme
import org.json.JSONObject
import org.xml.sax.Parser
import java.util.regex.Matcher

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

    Log.d("[LoginActivity]", "Cargando Firebase...")
    var database = FirebaseDatabase.getInstance().getReference("usuario")
    Log.d("[LoginActivity]", "Firebase cargado!")

    fun checkMail(email: String): Boolean {
        var pat:java.util.regex.Pattern = java.util.regex.Pattern.compile("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")
        var compare:Matcher = pat.matcher(email)
        return compare.find()
    }

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
        modifier = Modifier
            .fillMaxWidth()
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

                Log.d("[Login]", "Entrando...")

                val usuarioRef = database.child("/")

                usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        Log.d("[Login]", "Recuperando usuario")

                        if (snapshot.exists()) {
                            for (usuarioItem in snapshot.children) {
                                val email = usuarioItem.child("email").getValue().toString()
                                val pass = usuarioItem.child("contrasena").getValue().toString()
                                Log.d(
                                    "LOGIN ->",
                                    "Email " + email
                                )
                                Log.d(
                                    "LOGIN ->",
                                    "Contrasena " + contrasena
                                )

                                if (usuario.value.trim().equals(email.trim()) && contrasena.value.equals(pass)) {
                                    Log.d("[Login]", "Recuperando usuario")
                                    val intent = Intent(context, WelcomeActivity()::class.java)
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Credenciales incorrectas",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "No se ha recuperado el usuario especificado", Toast.LENGTH_SHORT).show()
                        Log.e("[Login]", "Error al obtener datos del usuario ${error.message}")
                    }
                })

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

