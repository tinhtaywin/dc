package com.bingo.admin.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bingo.admin.R
import com.bingo.admin.ui.theme.BingoAdminTheme
import com.bingo.admin.ui.theme.NeonCyan
import com.bingo.admin.ui.theme.NeonPink
import com.bingo.admin.ui.theme.NeonYellow
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    BingoAdminTheme {
        var showLoginButton by remember { mutableStateOf(false) }
        var showLoginText by remember { mutableStateOf(false) }
        var showLoginGlow by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            delay(500)
            showLoginButton = true
            delay(300)
            showLoginText = true
            delay(200)
            showLoginGlow = true
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .drawBehind {
                    // Animated background grid
                    for (i in 0..20) {
                        drawLine(
                            color = NeonCyan.copy(alpha = 0.1f),
                            start = androidx.compose.ui.geometry.Offset(i * 50f, 0f),
                            end = androidx.compose.ui.geometry.Offset(i * 50f, size.height),
                            strokeWidth = 1f
                        )
                        drawLine(
                            color = NeonCyan.copy(alpha = 0.1f),
                            start = androidx.compose.ui.geometry.Offset(0f, i * 50f),
                            end = androidx.compose.ui.geometry.Offset(size.width, i * 50f),
                            strokeWidth = 1f
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo/Title
                Text(
                    text = "BINGO ADMIN",
                    style = MaterialTheme.typography.displayLarge,
                    color = NeonCyan,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 36.sp
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                // Username Field
                NeonTextField(
                    value = viewModel.username.value,
                    onValueChange = { viewModel.username.value = it },
                    label = "Username",
                    imeAction = ImeAction.Next
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Password Field
                NeonTextField(
                    value = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    label = "Password",
                    imeAction = ImeAction.Done,
                    visualTransformation = PasswordVisualTransformation(),
                    isPassword = true
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Animated Login Button
                AnimatedVisibility(
                    visible = showLoginButton,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    LoginButton(
                        onClick = {
                            viewModel.login()
                        },
                        isLoading = viewModel.isLoading.value,
                        showText = showLoginText,
                        showGlow = showLoginGlow
                    )
                }
                
                // Error Message
                if (viewModel.error.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = viewModel.error.value,
                        color = NeonPink,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        
        // Loading Overlay
        if (viewModel.isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = NeonCyan,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Authenticating...",
                        color = NeonCyan,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
        
        // Success Navigation
        if (viewModel.isLoggedIn.value) {
            LaunchedEffect(viewModel.isLoggedIn.value) {
                onLoginSuccess()
            }
        }
    }
}

@Composable
private fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPassword: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = NeonCyan,
            unfocusedIndicatorColor = NeonCyan.copy(alpha = 0.5f),
            cursorColor = NeonCyan,
            focusedLabelColor = NeonCyan,
            unfocusedLabelColor = NeonCyan.copy(alpha = 0.7f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(alpha = 0.8f)
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (imeAction == ImeAction.Next) {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .graphicsLayer {
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .drawBehind {
                // Neon border effect
                drawRoundRect(
                    color = NeonCyan.copy(alpha = 0.3f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx()),
                    style = Stroke(
                        width = 2.dp.toPx()
                    )
                )
            }
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    showText: Boolean,
    showGlow: Boolean
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
            .shadow(8.dp, RoundedCornerShape(30.dp))
            .graphicsLayer {
                shape = RoundedCornerShape(30.dp)
                clip = true
            }
            .drawBehind {
                if (showGlow) {
                    // Pulsing glow effect
                    val alpha = if (isLoading) 0.8f else 0.3f
                    drawRoundRect(
                        color = NeonYellow.copy(alpha = alpha),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(30.dp.toPx()),
                        style = Stroke(
                            width = 8.dp.toPx()
                        )
                    )
                }
            }
    ) {
        Button(
            onClick = onClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(NeonCyan, NeonPink, NeonCyan)
                    )
                )
        ) {
            if (showText) {
                Text(
                    text = if (isLoading) "AUTHENTICATING..." else "LOGIN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}
