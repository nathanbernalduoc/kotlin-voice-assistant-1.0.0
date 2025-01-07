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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    val context = LocalContext.current


    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(60.dp))

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
            text = "Recuperación de contraseña",
            color = Color.Yellow,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            email.value,
            modifier =
                Modifier
                    .fillMaxWidth(),
            onValueChange = { email.value = it }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth(),
            onClick = {  },
        ) {
            Text(
                "Mostrar contraseña",
                fontSize = 20.sp
            )
        }

    }

}