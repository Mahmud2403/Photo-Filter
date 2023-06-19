package com.example.photofilter.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.example.photofilter.drawbox.DrawBox
import com.example.photofilter.drawbox.rememberDrawController
import com.example.photofilter.local.convertToOldColor
import com.example.photofilter.ui.screens.components.ControlsBar
import com.example.photofilter.ui.screens.components.CustomSeekbar
import io.ak1.rangvikalp.RangVikalp
import io.ak1.rangvikalp.defaultSelectedColor


@Composable
fun HomeScreen(
	bitmap: ImageBitmap?,
	save: (Bitmap) -> Unit
) {
	val undoVisibility = remember { mutableStateOf(false) }
	val redoVisibility = remember { mutableStateOf(false) }
	val colorBarVisibility = remember { mutableStateOf(false) }
	val sizeBarVisibility = remember { mutableStateOf(false) }
	val currentColor = remember { mutableStateOf(defaultSelectedColor) }
	val bg = MaterialTheme.colorScheme.background
	val currentBgColor = remember { mutableStateOf(bg) }
	val currentSize = remember { mutableStateOf(10) }
	val colorIsBg = remember { mutableStateOf(false) }
	val drawController = rememberDrawController()

	Column {
		DrawBox(
			drawController = drawController,
			backgroundColor = currentBgColor.value,
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth(),
			bitmap = bitmap!!,
			bitmapCallback = { imageBitmap, error ->
				imageBitmap?.let {
					save(it.asAndroidBitmap())
				}
			}
		) { undoCount, redoCount ->
			sizeBarVisibility.value = false
			colorBarVisibility.value = false
			undoVisibility.value = undoCount != 0
			redoVisibility.value = redoCount != 0
		}

		ControlsBar(
			drawController = drawController,
			{
				drawController.saveBitmap()
			},
			{
				colorBarVisibility.value = when (colorBarVisibility.value) {
					false -> true
					colorIsBg.value -> true
					else -> false
				}
				colorIsBg.value = false
				sizeBarVisibility.value = false
			},
			{
				colorBarVisibility.value = when (colorBarVisibility.value) {
					false -> true
					!colorIsBg.value -> true
					else -> false
				}
				colorIsBg.value = true
				sizeBarVisibility.value = false
			},
			{
				sizeBarVisibility.value = !sizeBarVisibility.value
				colorBarVisibility.value = false
			},
			undoVisibility = undoVisibility,
			redoVisibility = redoVisibility,
			colorValue = currentColor,
			bgColorValue = currentBgColor,
			sizeValue = currentSize
		)
		RangVikalp(isVisible = colorBarVisibility.value, showShades = true) {
			if (colorIsBg.value) {
				currentBgColor.value = it
				drawController.changeBgColor(it)
			} else {
				currentColor.value = it
				drawController.changeColor(it)
			}
		}
		CustomSeekbar(
			isVisible = sizeBarVisibility.value,
			progress = currentSize.value,
			progressColor = MaterialTheme.colorScheme.primary.convertToOldColor(),
			thumbColor = currentColor.value.convertToOldColor(),
			modifier = Modifier
		) {
			currentSize.value = it
			drawController.changeStrokeWidth(it.toFloat())
		}
	}
}
/*
    var path: String = ""
    val json = GsonBuilder().create()
    if(path.isNotBlank()){
       val listOfMyClassObject = object : TypeToken<ArrayList<PathWrapper>>() {}.type
       drawController.importPath(json.fromJson(path,listOfMyClassObject))
       path = ""
    }else{
       path = json.toJson(drawController.exportPath())
       Log.e("to string","${path}")
    }
*/
