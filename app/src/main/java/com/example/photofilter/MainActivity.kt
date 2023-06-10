package com.example.photofilter

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextClock
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.compose.rememberImagePainter
import com.example.photofilter.MainActivity
import com.example.photofilter.R
import com.example.photofilter.ui.theme.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PhotoFilterTheme {
				Surface(
					modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
				) {
					// on below line we are specifying theme as scaffold.
					Scaffold(
						// in scaffold we are specifying top bar.
						topBar = {
							// inside top bar we are specifying background color.
							TopAppBar(
								// along with that we are specifying
								// title for our top bar.
								title = {
									// in the top bar we are specifying tile as a text
									Text(
										// on below line we are specifying text
										// to display in top app bar.
										text = "GFG",
										// on below line we are specifying modifier
										// to fill max width.
										modifier = Modifier.fillMaxWidth(),
										// on below line we are specifying
										// text alignment.
										textAlign = TextAlign.Center,
										// on below line we are specifying
										// color for our text.
										color = Color.White
									)
								})
						},
						content = displayImage()

					)
				}
			}
		}
	}
}

// on below line we are creating a
// function to read image from file path.
@Composable
fun displayImage(): @Composable (PaddingValues) -> Unit {

	// on below line we are creating a column on below sides.
	Column(
		// on below line we are adding padding
		// padding for our column and filling the max size.
		Modifier
			.fillMaxSize()
			.fillMaxHeight()
			.fillMaxWidth(),
		// on below line we are adding
		// horizontal alignment for our column
		horizontalAlignment = Alignment.CenterHorizontally,
		// on below line we are adding
		// vertical arrangement for our column
		verticalArrangement = Arrangement.Center
	) {

		Text(
			// on below line we are specifying text to display.
			text = "Image from File Path in Android",

			// on below line we are specifying
			// modifier to fill max width.
			modifier = Modifier
				.fillMaxWidth()
				.padding(20.dp),

			// on below line we are
			// specifying text alignment.
			textAlign = TextAlign.Center,

			// on below line we are
			// specifying color for our text.
			color = Color.Black,

			// on below line we are
			// specifying font weight
			fontWeight = FontWeight.Bold,

			// on below line we
			// are updating font size.
			fontSize = 25.sp,
		)


		// on below line we are creating an image file and
		// specifying path for the image file on below line.
		val imgFile = File("/storage/emulated/0/Download/GeekforGeeksphoto.png")

		// on below line we are checking if the image file exist or not.
		var imgBitmap: Bitmap? = null
		if (imgFile.exists()) {
			// on below line we are creating an image bitmap variable
			// and adding a bitmap to it from image file.
			imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
		}

		Image(
			// on the below line we are specifying the drawable image for our image.
			// painter = painterResource(id = courseList[it].languageImg),
			painter = rememberImagePainter(data = imgBitmap),

			// on the below line we are specifying
			// content description for our image
			contentDescription = "Image",

			// on the below line we are setting the height
			// and width for our image.
			modifier = Modifier
				.fillMaxWidth()
				.height(200.dp)
				.padding(10.dp)
		)
	}
}