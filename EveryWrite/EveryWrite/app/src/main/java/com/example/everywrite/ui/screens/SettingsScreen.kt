package com.example.everywrite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onClearCache: () -> Unit
) {
    var showClearCacheDialog by remember { mutableStateOf(false) }

    // Green theme color palette
    val primaryGreen = Color(0xFF2E7D32)
    val primaryLightGreen = Color(0xFF60AD5E)
    val primaryDarkGreen = Color(0xFF005005)
    val secondaryGreen = Color(0xFF388E3C)
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFE8F5E8),
            Color(0xFFC8E6C9),
            Color(0xFFA5D6A7)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "⚙️ Settings",
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
                .background(backgroundGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Appearance Section
                SettingsSection(title = "🎨 Appearance") {
                    SettingsSwitchItem(
                        icon = Icons.Default.Palette,
                        title = "Dark Mode",
                        subtitle = "Switch between light and dark theme",
                        checked = isDarkMode,
                        onCheckedChange = onDarkModeChange
                    )
                }

                // Data & Storage Section
                SettingsSection(title = "💾 Data & Storage") {
                    SettingsButtonItem(
                        icon = Icons.Default.Storage,
                        title = "Clear Cache",
                        subtitle = "Remove temporary files and free up space",
                        buttonText = "Clear",
                        onClick = { showClearCacheDialog = true }
                    )
                }

                // Notifications Section
                SettingsSection(title = "🔔 Notifications") {
                    Text(
                        text = "Reminder Defaults",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = primaryDarkGreen
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    SettingsItem(title = "🌅 Morning", value = "8:00 am", onClick = {})
                    SettingsItem(title = "☀️ Afternoon", value = "1:00 pm", onClick = {})
                    SettingsItem(title = "🌙 Evening", value = "6:00 pm", onClick = {})
                }

                // Display Section
                SettingsSection(title = "📱 Display") {
                    SettingsItem(
                        title = "List Order",
                        value = "By time of creation",
                        onClick = { /* Change order */ }
                    )
                }

                // About Section
                SettingsSection(title = "ℹ️ About") {
                    SettingsItem(
                        title = "Version",
                        value = "1.0.0",
                        onClick = { }
                    )
                    SettingsItem(
                        title = "Theme",
                        value = "Green Nature",
                        onClick = { }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Clear Cache Confirmation Dialog
            if (showClearCacheDialog) {
                AlertDialog(
                    onDismissRequest = { showClearCacheDialog = false },
                    title = {
                        Text(
                            "🧹 Clear Cache",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = primaryDarkGreen
                            )
                        )
                    },
                    text = {
                        Text(
                            "This will remove temporary files and free up storage space. Your notes and data will remain safe.",
                            color = primaryDarkGreen
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showClearCacheDialog = false
                                onClearCache()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = primaryGreen
                            )
                        ) {
                            Text("🌿 Clear Now")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showClearCacheDialog = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = primaryDarkGreen
                            )
                        ) {
                            Text("🍃 Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF2E7D32)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF005005)
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF60AD5E)
                )
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF005005)
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF60AD5E)
                )
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF2E7D32),
                checkedTrackColor = Color(0xFFC8E6C9),
                uncheckedThumbColor = Color(0xFF9E9E9E),
                uncheckedTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Composable
fun SettingsButtonItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF005005)
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF60AD5E)
                )
            )
        }

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8F5E8),
                contentColor = Color(0xFF2E7D32)
            ),
            modifier = Modifier.height(36.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2E7D32), Color(0xFF60AD5E))
                )
            )
        ) {
            Text(buttonText)
        }
    }
}