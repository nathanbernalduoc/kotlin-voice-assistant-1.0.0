package com.nathanbernal.duocva

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor.FileDescriptorDetachedException
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nathanbernal.duocva.ui.theme.DuocVATheme
import java.io.File
import java.nio.file.Paths
import java.util.Dictionary

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

    val stat = mapOf(
        "Hola" to 1,
        "Adiós" to 2,
        "Estoy de acuerdo" to 3,
        "Tengo un problema" to 4,
        "Mi contacto es" to 5,
        "Necesito ayuda" to 6)
    
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

        stat.forEach { statement ->
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
                    when (statement.value) {
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
                    statement.key.toString(),
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
                "Salir",
                fontSize = 20.sp
            )

        }

    }
}