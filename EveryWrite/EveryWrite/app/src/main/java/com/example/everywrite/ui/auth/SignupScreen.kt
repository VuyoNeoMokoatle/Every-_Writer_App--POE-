package com.example.everywrite.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.everywrite.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val isLoading by authViewModel.isLoading.collectAsState()
    val error by authViewModel.signupError.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.clearErrors()
    }

    // Green theme color palette
    val primaryGreen = Color(0xFF2E7D32)
    val primaryLightGreen = Color(0xFF60AD5E)
    val primaryDarkGreen = Color(0xFF005005)
    val lightGreenBackground = Color(0xFFE8F5E8) // Solid light green background

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "🌱 Create Account",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primaryGreen,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(lightGreenBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Welcome Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "📗", // Green book emoji
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Join EveryWrite! ✨",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = primaryDarkGreen
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Start your writing journey with us 🍃",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = primaryDarkGreen
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Error Message
                error?.let {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🍂", modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = it,
                                color = Color(0xFFC62828),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("📨 Email Address") },
                    placeholder = { Text("your.email@example.com") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = primaryLightGreen,
                        focusedLabelColor = primaryGreen,
                        unfocusedLabelColor = primaryLightGreen,
                        focusedLeadingIconColor = primaryGreen,
                        unfocusedLeadingIconColor = primaryLightGreen,
                        focusedTextColor = primaryDarkGreen,
                        unfocusedTextColor = primaryDarkGreen,
                        cursorColor = primaryGreen
                    ),
                    shape = MaterialTheme.shapes.medium
                )

                // Username Field
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("🌿 Username") },
                    placeholder = { Text("Choose a cool username") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Username")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = primaryLightGreen,
                        focusedLabelColor = primaryGreen,
                        unfocusedLabelColor = primaryLightGreen,
                        focusedLeadingIconColor = primaryGreen,
                        unfocusedLeadingIconColor = primaryLightGreen,
                        focusedTextColor = primaryDarkGreen,
                        unfocusedTextColor = primaryDarkGreen,
                        cursorColor = primaryGreen
                    ),
                    shape = MaterialTheme.shapes.medium
                )

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("🌱 Password") },
                    placeholder = { Text("Create a strong password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPassword) "Hide password" else "Show password",
                                tint = primaryGreen
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = primaryLightGreen,
                        focusedLabelColor = primaryGreen,
                        unfocusedLabelColor = primaryLightGreen,
                        focusedLeadingIconColor = primaryGreen,
                        unfocusedLeadingIconColor = primaryLightGreen,
                        focusedTextColor = primaryDarkGreen,
                        unfocusedTextColor = primaryDarkGreen,
                        cursorColor = primaryGreen
                    ),
                    shape = MaterialTheme.shapes.medium
                )

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("🍀 Confirm Password") },
                    placeholder = { Text("Re-enter your password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirm Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Icon(
                                if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showConfirmPassword) "Hide password" else "Show password",
                                tint = primaryGreen
                            )
                        }
                    },
                    visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = primaryLightGreen,
                        focusedLabelColor = primaryGreen,
                        unfocusedLabelColor = primaryLightGreen,
                        focusedLeadingIconColor = primaryGreen,
                        unfocusedLeadingIconColor = primaryLightGreen,
                        focusedTextColor = primaryDarkGreen,
                        unfocusedTextColor = primaryDarkGreen,
                        cursorColor = primaryGreen
                    ),
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Create Account Button
                Button(
                    onClick = {
                        authViewModel.signup(email, username, password, confirmPassword, onSignupSuccess)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !isLoading && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryGreen,
                        contentColor = Color.White,
                        disabledContainerColor = primaryLightGreen,
                        disabledContentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.large,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    if (isLoading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Text("Creating Account...")
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🌳", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                "Create Account",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login Redirect
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            color = primaryDarkGreen
                        )
                        TextButton(
                            onClick = onNavigateToLogin,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = primaryGreen
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("🎍")
                                Text("Login Here")
                            }
                        }
                    }
                }

                // Fun footer
                Text(
                    text = "🍃 Your thoughts deserve a beautiful home 🍃",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = primaryDarkGreen
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}