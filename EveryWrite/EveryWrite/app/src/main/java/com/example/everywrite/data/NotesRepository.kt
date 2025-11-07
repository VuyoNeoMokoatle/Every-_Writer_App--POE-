package com.example.everywrite.data

import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NoteDao) {

    // Existing functions
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    fun getArchivedNotes(): Flow<List<Note>> = noteDao.getArchivedNotes()
    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)

    suspend fun getNoteById(id: String): Note? = noteDao.getNoteById(id)
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun deleteAllArchived() = noteDao.deleteAllArchived()
    suspend fun updatePinStatus(id: String, isPinned: Boolean) = noteDao.updatePinStatus(id, isPinned)
    suspend fun updateArchiveStatus(id: String, isArchived: Boolean) = noteDao.updateArchiveStatus(id, isArchived)

    // NEW: Create note with SIMPLE weather
    suspend fun createNoteWithWeather(
        title: String,
        content: String,
        city: String = "London"
    ): Note {
        val (weatherInfo, weatherIcon) = getSimpleWeatherForCity(city)

        val note = Note(
            title = title,
            content = content,
            location = city,
            weather = weatherInfo,
            weatherIcon = weatherIcon,
            updatedAt = System.currentTimeMillis()
        )

        noteDao.insertNote(note)
        return note
    }

    // SIMPLE weather without API calls - returns both description and icon
    private fun getSimpleWeatherForCity(city: String): Pair<String, String> {
        val cityWeather = mapOf(
            "london" to Pair("🌧️ Rainy, 15°C", "🌧️"),
            "paris" to Pair("⛅ Cloudy, 18°C", "⛅"),
            "new york" to Pair("☀️ Sunny, 22°C", "☀️"),
            "tokyo" to Pair("☀️ Sunny, 25°C", "☀️"),
            "sydney" to Pair("☀️ Sunny, 28°C", "☀️"),
            "berlin" to Pair("⛅ Cloudy, 16°C", "⛅"),
            "rome" to Pair("☀️ Sunny, 24°C", "☀️"),
            "madrid" to Pair("☀️ Sunny, 26°C", "☀️"),
            "amsterdam" to Pair("🌧️ Rainy, 14°C", "🌧️"),
            "dublin" to Pair("🌧️ Rainy, 13°C", "🌧️"),
            "moscow" to Pair("❄️ Snowy, -5°C", "❄️"),
            "dubai" to Pair("☀️ Sunny, 35°C", "☀️"),
            "los angeles" to Pair("☀️ Sunny, 26°C", "☀️"),
            "toronto" to Pair("⛅ Cloudy, 12°C", "⛅"),
            "singapore" to Pair("🌧️ Rainy, 30°C", "🌧️")
        )

        return cityWeather[city.lowercase()] ?: Pair("🌈 Beautiful, 20°C", "🌈")
    }

    // Quick weather for common cities
    suspend fun getQuickWeather(city: String): String {
        return getSimpleWeatherForCity(city).first
    }
}