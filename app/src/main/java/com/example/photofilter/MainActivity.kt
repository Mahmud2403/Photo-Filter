package com.example.photofilter

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.core.content.ContextCompat
import com.example.photofilter.ui.screens.pick_photo.PickImageFromGallery
import com.example.photofilter.ui.theme.*
import java.util.*
import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.core.app.ActivityCompat
import coil.compose.rememberImagePainter
import com.example.photofilter.ui.screens.pick_photo.PickImageFromCamera
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PhotoFilterTheme {
				PickImageFromGallery()
			}
		}
	}
}

@Composable
fun PhotoFilter(
	onClickPhotoFromGallery: () -> Unit,
	onClickPhotoFromCamera: () -> Unit,
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Button(
			onClick = onClickPhotoFromGallery
		) {
			Text(text = "Выбрать фотку из галереи")
		}
		Button(
			onClick = onClickPhotoFromCamera
		) {
			Text(text = "Сделать фотку с помощью камеры")
		}
	}
}

