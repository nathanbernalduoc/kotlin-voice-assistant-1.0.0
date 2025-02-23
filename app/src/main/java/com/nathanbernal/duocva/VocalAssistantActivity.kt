package com.nathanbernal.duocva

import android.content.Intent
import android.media.MediaPlayer.*
import android.os.Bundle
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
import androidx.compose.runtime.Composable
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nathanbernal.duocva.ui.theme.DuocVATheme

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DuocVATheme {
                AssistantForm()
            }
        }
    }
}

@Composable
fun AssistantForm() {

    Log.d("[LoginActivity]", "Cargando Firebase...")
    var database = FirebaseDatabase.getInstance().getReference("dicionario")
    Log.d("[LoginActivity]", "Firebase cargado!")
    val stat = mutableMapOf<Int,String>()
    val stat2 = mapOf(
        1 to "Hola",
        2 to "Adiós",
        3 to "Estoy de acuerdo",
        4 to "Tengo un problema",
        5 to "Mi contacto es",
        6 to "Necesito ayuda")

    val diccionarioRef = database.child("/")
    val context = LocalContext.current

    diccionarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.d("[Login]", "Recuperando usuario")
            if (snapshot.exists()) {
                for (dicItem in snapshot.children) {

                    val etiqueta = dicItem.child("etiqueta").getValue().toString()
                    val frase = dicItem.child("frase").getValue().toString()
                    val audio = dicItem.child("audio").getValue().toString()
                    val recursoId: String = dicItem.child("recursoId").getValue().toString()
                    val recInt = recursoId.toInt()
                    stat.put(recInt, etiqueta)

                }
                Log.d("HOME", "STAT")
                Log.d("HOME", stat.toString())


            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "No se ha recuperado el usuario especificado", Toast.LENGTH_SHORT).show()
            Log.e("[Login]", "Error al obtener datos del usuario ${error.message}")
        }
    })

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
                .width(30.dp)
                .fillMaxWidth()
                .padding(top = 0.dp),
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.app_title),
            color = Color.DarkGray,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

        Text(
            text = stringResource(R.string.vocal_title),
            color = Color.DarkGray,
            fontSize = 60.sp,
            fontStyle = FontStyle.Italic
        )


        Spacer(modifier = Modifier.height(5.dp))

        stat2.forEach { statement ->

            Button(

                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        top = 0.dp,
                        end = 10.dp,
                        bottom = 0.dp
                    )
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.ligthRed),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),

                onClick = {
                    Log.d("CHECK", statement.key.toString())
                    when (statement.key) {
                        1 -> {
                            val mp = create(context, R.raw.audio_1)
                            mp.start()
                        }
                        2 -> {
                            val mp = create(context, R.raw.audio_2)
                            mp.start()
                        }
                        3 -> {
                            val mp = create(context, R.raw.audio_3)
                            mp.start()
                        }
                        4 -> {
                            val mp = create(context, R.raw.audio_4)
                            mp.start()
                        }
                        5 -> {
                            val mp = create(context, R.raw.audio_5)
                            mp.start()
                        }
                        6 -> {
                            val mp = create(context, R.raw.audio_6)
                            mp.start()
                        }
                    }
                }
            ) {
                Text(
                    text = statement.value.toString(),
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

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
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                "Volver",
                fontSize = 20.sp
            )

        }

    }
}


