package com.example.startkotlin

import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.startkotlin.ui.theme.StartKotlinTheme

// for style handling
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.font.Font
//import androidx.compose.ui.font.FontFamily
import androidx.compose.ui.unit.dp



class prevMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StartKotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mytest = context.getString(R.string.test)
    val appname = context.getString(R.string.app_name)

//    val fontFamily = FontFamily(Font(R.font.sans_serif_black)) // Ensure you have the font file

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp) // Padding around the column
            .background(Color(0xFF673AB7)),
        horizontalAlignment = Alignment.CenterHorizontally, // Center content horizontally
        verticalArrangement = Arrangement.Center // Center content vertically
    ) {

        Text(
            text = "This is the top text!\nMy App \n$appname",
            style = MaterialTheme.typography.bodyLarge,

            modifier = Modifier.padding(bottom = 0.dp)
                .wrapContentWidth(Alignment.Start)
//                .align(Alignment.CenterHorizontally) // Center horizontally
        // Add some space between this text and the next
        )


        Text(
            text = "Hello, $name. $mytest",
//            style = MaterialTheme.typography.displayLarge.copy(fontFamily = fontFamily), // Applying custom font
            modifier = modifier
                .width(250.dp)  // Width of 250dp
                .height(500.dp) // Height of 550dp
                .wrapContentHeight(align = Alignment.CenterVertically), // Vertically center inside the height
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StartKotlinTheme {
        Greeting("Android")
    }
}