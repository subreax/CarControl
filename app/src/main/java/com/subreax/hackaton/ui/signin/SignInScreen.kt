package com.subreax.hackaton.ui.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.ui.PasswordTextField

@Composable
fun SignInScreen(
    navBack: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    SignInScreen(
        username = signInViewModel.username,
        password = signInViewModel.password,
        error = signInViewModel.error,
        onUsernameUpdated = signInViewModel::updateUsername,
        onPasswordUpdated = signInViewModel::updatePassword,
        navBack = navBack,
        signInClicked = signInViewModel::signIn
    )
}

private val horizontalPadding = Modifier.padding(horizontal = 16.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    username: String,
    password: String,
    error: String,
    onUsernameUpdated: (String) -> Unit,
    onPasswordUpdated: (String) -> Unit,
    navBack: () -> Unit,
    signInClicked: () -> Unit
) {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            IconButton(
                onClick = navBack,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 32.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Вернуться назад")
            }

            Text(
                text = "Вход",
                style = MaterialTheme.typography.headlineLarge,
                modifier = horizontalPadding
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameUpdated,
                label = {
                    Text(text = "Имя пользователя")
                },
                singleLine = true,
                modifier = horizontalPadding
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            PasswordTextField(
                password = password,
                onPasswordUpdated = onPasswordUpdated,
                modifier = horizontalPadding.fillMaxWidth().padding(bottom = 8.dp)
            )

            Button(onClick = signInClicked, modifier = Modifier.align(CenterHorizontally)) {
                Text(text = "Войти")
            }

            Text(
                text = error,
                modifier = horizontalPadding.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
