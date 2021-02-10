import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File

private lateinit var logText: MutableState<TextFieldValue>

fun main() = Window(title = "FitCSVTool-GUI") {
    logText = remember { mutableStateOf(TextFieldValue("")) }
    val filePath = remember { mutableStateOf(TextFieldValue(System.getProperty("user.home"))) }

    MaterialTheme {
        Column() {
            firstRow(filePath)
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = logText.value,
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(autoCorrect = false),
                    onImeActionPerformed = { _, _ ->
                    },
                    onValueChange = {
                        logText.value = it
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    val dropTarget = object : DropTarget() {
        @Synchronized
        override fun drop(evt: DropTargetDropEvent) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_REFERENCE)
                val droppedFiles = evt
                    .transferable.getTransferData(
                        DataFlavor.javaFileListFlavor
                    ) as List<*>
                droppedFiles.first()?.let {
                    val file = (it as File)
                    filePath.value = TextFieldValue(file.absolutePath)
                    if (!file.isDirectory && file.extension == "fit") {
                        decode(file.absolutePath)
                    } else {
                        logText.value = TextFieldValue("Fitファイルを指定してください")
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
    val window = AppManager.windows.first().window
    window.contentPane.dropTarget = dropTarget
    window.iconImage = Toolkit.getDefaultToolkit().getImage("app_icon.png")
}

private fun decode(filePath: String) {
    try {
        val result = FitToCSV(filePath).execute()
        logText.value = TextFieldValue(result)
    } catch (e: Exception) {
        logText.value = TextFieldValue("ERROR: ${e.message}")
    }
}

@Composable
fun firstRow(filePath: MutableState<TextFieldValue>) {
    val decodeButtonEnabled = !File(filePath.value.text).isDirectory
    val decodeButtonClickFunction = {
        if (decodeButtonEnabled) {
            decode(filePath.value.text)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = filePath.value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Search),
            onImeActionPerformed = { _, _ ->
                decodeButtonClickFunction()
            },
            placeholder = {
                Text("Fit File")
            },
            modifier = Modifier.alignBy(LastBaseline)
                .weight(1.0f),
            onValueChange = {
                filePath.value = it
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = decodeButtonClickFunction,
            modifier = Modifier.alignByBaseline().width(100.dp),
            enabled = decodeButtonEnabled
        ) {
            Text("Decode")
        }
    }
}

