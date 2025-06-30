package com.example.soft_inteligente_app.ui.theme.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.soft_inteligente_app.data.ImageClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ClasificadorScreen(navController: NavHostController) {

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var resultado by remember { mutableStateOf("") }

    val imageClassifier = remember { ImageClassifier(context) }
    val scope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        onDispose {
            imageClassifier.close()
        }
    }

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }


    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { img: Bitmap? ->
        img?.let { bitmap = it }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavPanel(navController)
        }
    ) { paddingValues ->
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF4393C5),
                Color.White
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize().background(brush = gradientBrush)
                .padding(paddingValues), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Clasificador de Mascotas con IA", color = Color.White, fontWeight = FontWeight.ExtraBold
                , style = MaterialTheme.typography.titleLarge, fontSize = 30.sp, textAlign = TextAlign.Center,
                modifier = Modifier.padding(15.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { launcherCamara.launch() },
                    modifier = Modifier.padding(bottom = 20.dp,top = 30.dp)
                    ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))) {
                    Text("Tomar Foto",color =Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { launcherGaleria.launch("image/*") },
                    modifier = Modifier.padding(bottom = 20.dp,top = 30.dp)
                    ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))) {
                    Text("Subir Imagen",color =Color.White)
                }
            }

            bitmap?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.size(200.dp).padding(20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                    scope.launch {
                        resultado = withContext(Dispatchers.Default) {
                            imageClassifier.classify(it)
                        }
                    }
            },modifier = Modifier.padding(bottom = 20.dp,top = 30.dp)
                    ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4393C5))) {
                    Text("Clasificar",modifier = Modifier.padding(15.dp))
            }

                Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium
                    , fontWeight = FontWeight.ExtraBold, color = Color.Gray
                    ,modifier = Modifier.padding(15.dp))
            }
        }
    }
}
