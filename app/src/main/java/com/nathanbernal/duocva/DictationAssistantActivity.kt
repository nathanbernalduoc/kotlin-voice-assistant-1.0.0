package com.nathanbernal.duocva

import android.content.Intent
import android.Manifest
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DictActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DictadoForm()
        }
    }
}

@Composable
fun DictadoForm() {

    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    var speechText by remember { mutableStateOf("") }

    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        }
    }

    // Se crea objeto para ser invocado a la hora de solicitar permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            speechRecognizer.startListening(recognizerIntent)
        } else {
            Toast.makeText(context, "Permiso del micrófono denegados", Toast.LENGTH_SHORT).show()
        }
    }

    val recognizerListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            //TODO("Not yet implemented")
        }

        override fun onBeginningOfSpeech() {
            speechText = "Escuchando..."
        }

        override fun onRmsChanged(rmsdB: Float) {
            //TODO("Not yet implemented")
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            //TODO("Not yet implemented")
        }

        override fun onEndOfSpeech() {
            speechText = "Interpretando"
        }

        override fun onError(error: Int) {
            speechText = "Error al interpretar el audio, reintente!"
        }

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            speechText = matches?.firstOrNull() ?:"No fué posible reconocer el audio"
        }

        override fun onPartialResults(partialResults: Bundle?) {
            //TODO("Not yet implemented")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            //TODO("Not yet implemented")
        }
    }

    speechRecognizer.setRecognitionListener(recognizerListener)

    Column (
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
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

        Spacer(modifier = Modifier.height(70.dp))

        Surface (onClick = { permissionLauncher.launch(Manifest.permission.RECORD_AUDIO) }
        ) {

            Image(
                painter = painterResource(id = R.drawable.oreja2),
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                )

        }

        Text(
            text = speechText,
            color = Color.Blue,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(30.dp)
        )

        Text(
            text = stringResource(R.string.vocal_content),
            color = Color.Blue,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(30.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

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