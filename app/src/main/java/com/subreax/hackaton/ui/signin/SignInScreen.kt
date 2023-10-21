package com.subreax.hackaton.ui.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.ui.PasswordTextField

@Composable
fun SignInScreen(
    navBack: () -> Unit,
    navHome: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    SignInScreen(
        email = signInViewModel.email,
        password = signInViewModel.password,
        error = signInViewModel.error,
        signInEnabled = signInViewModel.signInButtonEnabled,
        onEmailUpdated = signInViewModel::updateEmail,
        onPasswordUpdated = signInViewModel::updatePassword,
        navBack = navBack,
        signInClicked = signInViewModel::signIn
    )

    LaunchedEffect(signInViewModel.eventNavHomeScreen) {
        if (signInViewModel.eventNavHomeScreen) {
            navHome()
            signInViewModel.navHomeScreenHandled()
        }
    }
}

private val horizontalPadding = Modifier.padding(horizontal = 16.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    email: String,
    password: String,
    error: String,
    signInEnabled: Boolean,
    onEmailUpdated: (String) -> Unit,
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
                value = email,
                onValueChange = onEmailUpdated,
                label = {
                    Text(text = "Почта")
                },
                singleLine = true,
                modifier = horizontalPadding
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            PasswordTextField(
                password = password,
                onPasswordUpdated = onPasswordUpdated,
                modifier = horizontalPadding
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )




            Button(
                onClick = signInClicked,
                modifier = Modifier.align(CenterHorizontally),
                enabled = signInEnabled
            ) {
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
