package com.example.playlistmaker

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.ui.theme.PlayListMakerTheme
import com.example.playlistmaker.ui.theme.SettingsBlack
import com.example.playlistmaker.ui.theme.SettingsBlue
import com.example.playlistmaker.ui.theme.SettingsGray
import com.example.playlistmaker.ui.theme.SettingsLightGray
import com.example.playlistmaker.ui.theme.SettingsWhite

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayListMakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SettingsScreen (
                        modifier = Modifier.padding(innerPadding),
                        { finish() }
                    )
                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun SettingsScreen(modifier: Modifier= Modifier, onBackClick: () -> Unit={}) {
    val context = LocalContext.current

    val shareMessage = stringResource(R.string.share_app_message)
    val email = stringResource(R.string.support_email)
    val emailSubject = stringResource(R.string.support_email_subject)
    val emailBody = stringResource(R.string.support_email_body)
    val agreementUrl = stringResource(R.string.user_agreement_url)

    var isDarkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SettingsWhite)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(R.drawable.arrow),
                    contentDescription = "Назад",
                    tint = SettingsBlack
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                modifier = Modifier.padding(start = 12.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = SettingsBlack
            )
        }
        Column(modifier = Modifier.padding(top = 24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(61.dp)
                    .padding(start = 16.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Темная тема",
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = SettingsBlack
                )
                ThemeSwitch(isDarkTheme, onCheckedChange = {isDarkTheme=!isDarkTheme})
            }
            SettingsItem(
                text = stringResource(R.string.settings_share_app),
                icon =painterResource(R.drawable.share),
                onClick = { shareApp(context, shareMessage) }
            )

            SettingsItem(
                text = stringResource(R.string.settings_write_to_developers),
                icon = painterResource(R.drawable.support),
                onClick = { writeToDevelopers(context, email, emailSubject, emailBody) }
            )

            SettingsItem(
                text = stringResource(R.string.settings_user_agreement),
                icon = painterResource(R.drawable.vector),
                onClick = { openUrl(context, agreementUrl) }
            )
        }
    }
}

@Composable
private fun SettingsItem(
    text: String,
    onClick: () -> Unit,
    icon: Painter,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = SettingsBlack
        )


        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

    }
}


private fun shareApp(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    startSafely(context, Intent.createChooser(intent, null))
}

private fun writeToDevelopers(context: Context, email: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    startSafely(context, intent)
}

private fun openUrl(context: Context, url: String) {
    startSafely(context, Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}


private fun startSafely(context: Context, intent: Intent) {
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Не найдено подходящее приложение", Toast.LENGTH_SHORT).show()
    }
}
@Composable
private fun ThemeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 16.dp else 0.dp,
        label = "thumbOffset"
    )
    val trackColor = if (checked) SettingsBlue else SettingsLightGray
    val thumbColor = if (checked) SettingsBlue else SettingsGray

    Box(
        modifier = Modifier
            .size(width = 36.dp, height = 20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .background(trackColor, RoundedCornerShape(7.dp))
        )
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(20.dp)
                .shadow(elevation = 1.dp, shape = CircleShape)
                .background(thumbColor, CircleShape)
        )
    }
}
@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(onBackClick = {})
}
