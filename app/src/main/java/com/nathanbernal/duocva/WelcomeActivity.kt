package com.nathanbernal.duocva

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Welcome()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Welcome() {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.girafa))
    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.6f
    )
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Button(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 100.dp,
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
                text = "Iniciar"
            )
        }

        Spacer(
            modifier = Modifier
                .padding(40.dp)
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            fontFamily = FontFamily.SansSerif,
            text = "Bienvenido!",
            color = Color.Black,
            fontSize = 30.sp
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            fontFamily = FontFamily.SansSerif,
            text = "Estamos listos para ayudarte como tu asistence personal.",
            color = colorResource(R.color.ligthRed),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        LottieAnimation(
            composition = composition,
            progress = { progress }
        )

    }
}