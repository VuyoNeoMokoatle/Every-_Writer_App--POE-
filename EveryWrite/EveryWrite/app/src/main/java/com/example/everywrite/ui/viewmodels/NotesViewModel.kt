package com.example.everywrite.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everywrite.data.Note
import com.example.everywrite.data.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    // Existing properties
    val notes = repository.getAllNotes()
    val archivedNotes = repository.getArchivedNotes()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    // Existing methods
    fun searchNotes(query: String) = repository.searchNotes(query)
    fun setSearchQuery(query: String) { _searchQuery.value = query }
    fun setIsSearching(isSearching: Boolean) { _isSearching.value = isSearching }
    fun setCurrentNote(note: Note?) { _currentNote.value = note }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun updatePinStatus(id: String, isPinned: Boolean) {
        viewModelScope.launch {
            repository.updatePinStatus(id, isPinned)
        }
    }

    fun updateArchiveStatus(id: String, isArchived: Boolean) {
        viewModelScope.launch {
            repository.updateArchiveStatus(id, isArchived)
        }
    }

    fun deleteAllArchived() {
        viewModelScope.launch {
            repository.deleteAllArchived()
        }
    }

    fun createNewNote() {
        val newNote = Note(
            title = "",
            content = ""
        )
        setCurrentNote(newNote)
    }

    // Create note with weather
    fun createNoteWithWeather(title: String, content: String, location: String) {
        viewModelScope.launch {
            repository.createNoteWithWeather(title, content, location)
        }
    }

    // NEW: Clear cache function
    fun clearCache() {
        viewModelScope.launch {
            repository.deleteAllArchived() // This clears archived notes (your cache)
        }
    }

    // NEW: Simple cache info (you can enhance this later)
    suspend fun getCacheSize(): String {
        val archivedNotes = repository.getArchivedNotes().first().size
        val estimatedSize = archivedNotes * 2 // Rough estimate in KB
        return if (estimatedSize < 1024) {
            "${estimatedSize} KB"
        } else {
            "${estimatedSize / 1024} MB"
        }
    }

    // ADD THIS MISSING FUNCTION:
    suspend fun getWeatherPreview(location: String): String {
        return repository.getQuickWeather(location)
    }
}