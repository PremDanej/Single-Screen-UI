package com.merp.jet.single.ui.screen.signin

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.merp.jet.single.ui.screen.R
import com.merp.jet.single.ui.screen.components.HorizontalSpacer
import com.merp.jet.single.ui.screen.components.VerticalSpacer
import com.merp.jet.single.ui.screen.ui.theme.SingleScreenUITheme
import kotlinx.coroutines.launch

@Composable
fun SignInScreenPreview() {
    SingleScreenUITheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                SignInScreen(
                    onLoginClick = { _, _ -> },
                    onForgotPasswordClick = {},
                    onGoogleSignInClick = {},
                    onSignUpClick = {},
                )
            }
        }
    }
}


@Composable
fun SignInScreen(
    onLoginClick: (email: String, password: String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val materialShape = MaterialTheme.shapes.medium

    // Function to validate inputs
    fun validateInputs(): Boolean {
        var isValid = true

        // Validate email
        if (email.isBlank()) {
            emailError = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Please enter a valid email"
            isValid = false
        } else {
            emailError = null
        }

        // Validate password
        if (password.isBlank()) {
            passwordError = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError = null
        }
        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacer(48)

        // App Logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )

        VerticalSpacer(24)

        // Welcome Text
        Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        VerticalSpacer(8)

        Text(
            text = "Sign in to continue to your account",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        VerticalSpacer(32)

        // Email Field
        OutlinedTextField(
            shape = materialShape,
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
            isError = emailError != null,
            supportingText = {
                emailError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpacer(16)

        // Password Field
        OutlinedTextField(
            shape = materialShape,
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible)
                            "Hide Password" else "Show Password"
                    )
                }
            },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = {
                passwordError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Forgot Password
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Forgot Password?")
            }
        }

        VerticalSpacer(24)

        // Login Button
        Button(
            shape = materialShape,
            onClick = {
                if (validateInputs()) {
                    isLoading = true
                    onLoginClick(email, password)
                    // In a real app, you would handle loading state based on login response
                    // For demo, we'll just reset after a delay
                    coroutineScope.launch {
                        kotlinx.coroutines.delay(1500)
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Sign In", fontSize = 16.sp)
            }
        }

        VerticalSpacer(24)

        // Divider with OR
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Text(
                text = "OR",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        VerticalSpacer(24)

        // Google Sign In Button
        OutlinedButton(
            shape = materialShape,
            onClick = onGoogleSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Google Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                HorizontalSpacer(8)
                Text("Sign in with Google", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Sign Up Link
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            TextButton(
                shape = materialShape,
                onClick = onSignUpClick
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}