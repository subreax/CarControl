package com.subreax.hackaton.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.subreax.hackaton.R
import com.subreax.hackaton.ui.theme.HackatonTheme

@Composable
fun WelcomeScreen(signInClicked: () -> Unit, signUpClicked: () -> Unit) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painterResource(R.drawable.logo),
                "Logo",
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Добро пожаловать!", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Безопасность - превыше всего",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(92.dp))
                Button(modifier = Modifier.width(192.dp), onClick = signInClicked) {
                    Text("Войти")
                }
                OutlinedButton(modifier = Modifier.width(192.dp), onClick = signUpClicked) {
                    Text("Зарегистрироваться")
                }
            }
        }
    }
}

@Preview(widthDp = 400, heightDp = 800)
@Composable
fun WelcomeScreenPreview() {
    HackatonTheme {
        WelcomeScreen({}, {})
    }
}