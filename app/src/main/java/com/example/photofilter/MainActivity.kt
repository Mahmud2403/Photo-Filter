package com.example.photofilter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photofilter.helper.activityChooser
import com.example.photofilter.helper.checkAndAskPermission
import com.example.photofilter.helper.saveImage
import com.example.photofilter.ui.screens.components.Root
import com.example.photofilter.ui.screens.HomeScreen
import com.example.photofilter.ui.theme.PhotoFilterTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PhotoFilterTheme {
				var imageUri by remember { mutableStateOf<Uri?>(null) }
				val context = LocalContext.current
				var bitmapGallery by remember { mutableStateOf<Bitmap?>(null) }
				var bitmapCamera by remember { mutableStateOf<Bitmap?>(null) }
				val launcherGallery =
					rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
						imageUri = uri
					}
				val launcherCamera =
					rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
						bitmapCamera = it
					}

				Column(
					modifier = Modifier
						.fillMaxSize()
						.background(Color.Black),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					imageUri?.let {
						bitmapGallery = if (Build.VERSION.SDK_INT < 28) {
							MediaStore.Images
								.Media.getBitmap(context.contentResolver, it)
						} else {
							val source = ImageDecoder.createSource(context.contentResolver, it)
							ImageDecoder.decodeBitmap(source)
						}
						bitmapGallery?.let { btn ->
							Root(window = window) {
								HomeScreen(
									bitmap = bitmapGallery?.asImageBitmap()!!
								) {
									checkAndAskPermission {
										CoroutineScope(Dispatchers.IO).launch {
											val uri = saveImage(it)
											withContext(Dispatchers.Main) {
												startActivity(activityChooser(uri))
											}
										}
									}
								}
							}
						}
					}
					bitmapCamera?.let {
						Root(window = window) {
							HomeScreen(
								bitmap = bitmapCamera?.asImageBitmap()!!
							) {
								checkAndAskPermission {
									CoroutineScope(Dispatchers.IO).launch {
										val uri = saveImage(it)
										withContext(Dispatchers.Main) {
											startActivity(activityChooser(uri))
										}
									}
								}
							}
						}
					}
					if (bitmapGallery == null && bitmapCamera == null) {
						Column(
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							Text(
								text = "No image selected!",
								color = Color.White
							)
							Row(
								modifier = Modifier
									.fillMaxSize()
									.padding(16.dp),
								horizontalArrangement = Arrangement.Center,
								verticalAlignment = Alignment.Bottom
							) {
								Button(
									modifier = Modifier.padding(end = 16.dp),
									onClick = {
										launcherCamera.launch()
									}
								) {
									Text(text = "Take Photo")
								}
								Button(
									modifier = Modifier.padding(start = 16.dp),
									onClick = {
										launcherGallery.launch("image/*")
									},
								) {
									Text(text = "Pick image")
								}
							}
						}
					}
				}
			}
		}
	}
}







