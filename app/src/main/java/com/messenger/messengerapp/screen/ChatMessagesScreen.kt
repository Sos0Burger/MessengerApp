package com.messenger.messengerapp.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.messenger.messengerapp.R
import com.messenger.messengerapp.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatMessagesScreen() {
    var imageUri: MutableList<Uri> = remember {
        mutableListOf()
    }
    val context = LocalContext.current


    val message = remember {
        mutableStateOf("")
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.size > 2) {
            Toast.makeText(context, "Максимум 10 изображений", Toast.LENGTH_SHORT).show()
        } else {
            imageUri = uris.toMutableList()
        }
    }


    val scrollState = rememberScrollState(0)




    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.Black
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            IconButton(colors = IconButtonDefaults.iconButtonColors(contentColor = Orange),
                onClick = {
                    launcher.launch("image/*")
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.attachment_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(45f)
                        .size(48.dp)
                )
            }
            LaunchedEffect(scrollState.maxValue) {
                scrollState.scrollTo(scrollState.maxValue)
            }
            TextField(
                value = message.value,
                onValueChange = {
                    message.value = it
                },
                shape = MaterialTheme.shapes.extraLarge,
                label = { Text(text = "Сообщение", color = Color.DarkGray) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.Gray,
                    cursorColor = Orange
                ),
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .heightIn(min = 48.dp, max = 96.dp)
                    .widthIn(max = 256.dp)
            )
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Orange,
                    disabledContentColor = Color.DarkGray
                ),
                enabled = message.value.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(R.drawable.send_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessagesScreenPreview() {
    ChatMessagesScreen()
}