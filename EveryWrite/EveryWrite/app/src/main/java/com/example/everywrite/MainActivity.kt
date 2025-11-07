package com.example.everywrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.everywrite.data.NotesDatabase
import com.example.everywrite.data.NotesRepository
import com.example.everywrite.data.UserRepository
import com.example.everywrite.ui.SafeComposable
import com.example.everywrite.ui.navigation.AppNavigation
import com.example.everywrite.ui.theme.EveryWriteTheme
import com.example.everywrite.ui.viewmodels.AuthViewModel
import com.example.everywrite.ui.viewmodels.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Global crash handler
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            e.printStackTrace()
        }

        // Initialize database and repositories
        val database = NotesDatabase.getInstance(this)
        val notesRepository = NotesRepository(database.noteDao())
        val userRepository = UserRepository(database.userDao())

        // Initialize ViewModels
        val notesViewModel = NotesViewModel(notesRepository)
        val authViewModel = AuthViewModel(userRepository)

        setContent {
            // Dark Mode State
            var isDarkMode by remember { mutableStateOf(false) }

            EveryWriteTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SafeComposable {
                        AppNavigation(
                            authViewModel = authViewModel,
                            notesViewModel = notesViewModel,
                            isDarkMode = isDarkMode,
                            onDarkModeChange = { newValue ->
                                isDarkMode = newValue
                            }
                        )
                    }
                }
            }
        }
    }
}