package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainMenu()
        }
    }
}

@Composable
fun MainMenu() {

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(50.dp))

        Image(
            painter = painterResource(id = R.drawable.oreja2),
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .fillMaxWidth()
                .padding(top = 0.dp),
        )
        Text(
            text = stringResource(R.string.app_title),
            color = Color.DarkGray,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.padding(30.dp))

        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 100.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .height(100.dp)
                .fillMaxWidth(),
            onClick = {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Respuestas",
                fontSize = 40.sp
            )
        }

        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 100.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .height(100.dp)
                .fillMaxWidth(),
            onClick = {
                val intent = Intent(context, DictActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.ligthRed),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Dictado por voz",
                fontSize = 40.sp
            )
        }

        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 100.dp,
                    end = 10.dp,
                    bottom = 0.dp
                )
                .height(50.dp)
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
                text = "Salir",
                fontSize = 30.sp
            )
        }

    }

}