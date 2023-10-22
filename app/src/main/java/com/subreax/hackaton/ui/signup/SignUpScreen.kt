package com.subreax.hackaton.ui.signup

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subreax.hackaton.ui.PasswordTextField

@Composable
fun SignUpScreen(
    navBack: () -> Unit,
    navHome: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpScreen(
        email = signUpViewModel.email,
        username = signUpViewModel.username,
        password = signUpViewModel.password,
        passwordRepeat = signUpViewModel.passwordRepeat,
        signUpEnabled = signUpViewModel.buttonSignUpEnabled,
        error = signUpViewModel.error,
        onEmailUpdated = signUpViewModel::updateEmail,
        onUsernameUpdated = signUpViewModel::updateUsername,
        onPasswordUpdated = signUpViewModel::updatePassword,
        onPasswordRepeatUpdated = signUpViewModel::updatePasswordRepeat,
        navBack = navBack,
        signInClicked = signUpViewModel::signUp
    )

    LaunchedEffect(signUpViewModel.eventNavNext) {
        if (signUpViewModel.eventNavNext) {
            navHome()
            signUpViewModel.navNextHandled()
        }
    }
}

private val horizontalPadding = Modifier.padding(horizontal = 16.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    email: String,
    username: String,
    password: String,
    passwordRepeat: String,
    signUpEnabled: Boolean,
    error: String,
    onEmailUpdated: (String) -> Unit,
    onUsernameUpdated: (String) -> Unit,
    onPasswordUpdated: (String) -> Unit,
    onPasswordRepeatUpdated: (String) -> Unit,
    navBack: () -> Unit,
    signInClicked: () -> Unit
) {
    Surface {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = navBack,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 32.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "Вернуться назад")
            }

            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.headlineLarge,
                modifier = horizontalPadding
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = username,
                onValueChange = onUsernameUpdated,
                label = {
                    Text(text = "Имя пользователя")
                },
                singleLine = true,
                modifier = horizontalPadding
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailUpdated,
                label = {
                    Text(text = "Почта")
                },
                singleLine = true,
                modifier = horizontalPadding.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )

            PasswordTextField(
                password = password,
                onPasswordUpdated = onPasswordUpdated,
                modifier = horizontalPadding
                    .fillMaxWidth()
            )

            PasswordTextField(
                password = passwordRepeat,
                onPasswordUpdated = onPasswordRepeatUpdated,
                hint = "Повторите пароль",
                modifier = horizontalPadding
                    .fillMaxWidth()
            )

            Text(
                text = "Нажимая на кнопку Зарегистрироваться, вы принимаете условия лицензионного соглашения",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodySmall,
                modifier = horizontalPadding.padding(top = 8.dp)
            )

            Button(
                onClick = signInClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = signUpEnabled
            ) {
                Text(text = "Зарегистрироваться")
            }

            Text(
                text = error,
                modifier = horizontalPadding.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}