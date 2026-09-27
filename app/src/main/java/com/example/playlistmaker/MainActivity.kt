package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.ui.theme.PlayListMakerTheme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayListMakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingPreview(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(modifier: Modifier = Modifier) {
    PlayListMakerTheme {
        Column(modifier.background(Color.Blue)) {
            val context = LocalContext.current
            Text(stringResource(R.string.app_name), modifier = Modifier
                .fillMaxHeight(0.08f)
                .padding(20.dp), color = Color.White)
            Column (modifier = Modifier.clip(RoundedCornerShape(16.dp,16.dp,0.dp,0.dp))
                .fillMaxWidth().fillMaxHeight(1f).background(Color.White)){
                Row (modifier = Modifier.padding(20.dp).fillMaxWidth().pointerInput(Unit) {
                    detectTapGestures {
                        val intent = Intent(context, SearchActivity::class.java)
                        context.startActivity(intent)
                    }
                }, verticalAlignment = Alignment.CenterVertically){
                    Row (modifier = Modifier.fillMaxWidth(0.95f)){
                        Image(painterResource(id=R.drawable.search), contentDescription = null)
                        Text(stringResource(R.string.tittle_search), Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    }
                    Image(painterResource(id=R.drawable.vector), contentDescription = null)
                }
                Row (modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Row (modifier = Modifier.fillMaxWidth(0.95f)){
                        Image(painterResource(id=R.drawable.playlist), contentDescription = null)
                        Text(stringResource(R.string.tittle_playlists), Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    }
                    Image(painterResource(id=R.drawable.vector), contentDescription = null)
                }
                Row (modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Row (modifier = Modifier.fillMaxWidth(0.95f)){
                        Image(painterResource(id=R.drawable.favorites), contentDescription = null)
                        Text(stringResource(R.string.tittle_favourites), Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    }
                    Image(painterResource(id=R.drawable.vector), contentDescription = null)
                }
                Row (modifier = Modifier.padding(20.dp).fillMaxWidth().pointerInput(Unit){
                    detectTapGestures {
                        val intent= Intent(context, SettingsActivity::class.java)
                        context.startActivity(intent)
                    }
                }, verticalAlignment = Alignment.CenterVertically,){
                    Row (modifier = Modifier.fillMaxWidth(0.95f)){
                        Image(painterResource(id=R.drawable.settings), contentDescription = null)
                        Text(stringResource(R.string.tittle_settings), Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    }
                    Image(painterResource(id=R.drawable.vector), contentDescription = null)
                }
            }

        }
    }
}