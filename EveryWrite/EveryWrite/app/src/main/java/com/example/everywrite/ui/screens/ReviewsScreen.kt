package com.example.everywrite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.RoundingMode
import java.text.DecimalFormat

data class Review(
    val id: String,
    val username: String,
    val rating: Int,
    val title: String,
    val content: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(onBack: () -> Unit) {
    var showReviewDialog by remember { mutableStateOf(false) }
    var userRating by remember { mutableStateOf(0) }
    var reviewTitle by remember { mutableStateOf("") }
    var reviewContent by remember { mutableStateOf("") }

    // Error state
    var screenError by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }

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

    // Reviews list (dynamic)
    val reviews = remember {
        mutableStateListOf(
            Review("1", "Alex Johnson", 5, "Amazing App! 🌿", "This notes app has completely changed how I organize my thoughts.", "2 days ago"),
            Review("2", "Sarah Miller", 4, "Very Useful 🍃", "Love the simplicity and the nature theme makes it peaceful to use.", "1 week ago"),
            Review("3", "Mike Chen", 5, "Perfect for Students 📚", "As a student, this helps me keep all my notes organized.", "2 weeks ago")
        )
    }

    // Average rating calculation (safe)
    val averageRating = remember(reviews) {
        try {
            if (reviews.isNotEmpty()) reviews.map { it.rating }.average() else 0.0
        } catch (e: Exception) {
            screenError = "Failed to calculate average rating: ${e.message}"
            showErrorDialog = true
            0.0
        }
    }

    // Show any error in an AlertDialog
    if (showErrorDialog && screenError != null) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = {
                Text(
                    "🍂 Error",
                    style = MaterialTheme.typography.titleMedium.copy(color = primaryDarkGreen)
                )
            },
            text = {
                Text(
                    screenError ?: "Unknown error",
                    color = primaryDarkGreen
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = primaryGreen)
                ) {
                    Text("🌿 OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "📗 Reviews",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showReviewDialog = true },
                containerColor = primaryGreen,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Write Review")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Write Review")
            }
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
                    .padding(16.dp)
            ) {
                // Rating Summary Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "📊 App Reviews",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = primaryDarkGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "⭐ ${String.format("%.1f", averageRating)}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = primaryGreen
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    "Average Rating",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = primaryDarkGreen)
                                )
                                Text(
                                    "${reviews.size} reviews",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = primaryLightGreen
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        RatingDistribution(reviews)
                    }
                }

                // Reviews List
                if (reviews.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "📝 No reviews yet\nBe the first to share your experience! 🍃",
                            textAlign = TextAlign.Center,
                            color = primaryDarkGreen
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(reviews) { review ->
                            ReviewItem(review)
                        }
                    }
                }

                // Review Dialog
                if (showReviewDialog) {
                    AlertDialog(
                        onDismissRequest = { showReviewDialog = false },
                        title = {
                            Text(
                                "📝 Write a Review",
                                style = MaterialTheme.typography.headlineSmall.copy(color = primaryDarkGreen)
                            )
                        },
                        text = {
                            Column {
                                Text("🎯 Your Rating:", style = MaterialTheme.typography.bodyMedium.copy(color = primaryDarkGreen))
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    for (i in 1..5) {
                                        IconButton(onClick = { userRating = i }, modifier = Modifier.size(32.dp)) {
                                            Icon(
                                                imageVector = if (i <= userRating) Icons.Default.Star else Icons.Default.StarBorder,
                                                contentDescription = "Rate $i stars",
                                                tint = primaryGreen
                                            )
                                        }
                                    }
                                }
                                if (userRating > 0) {
                                    Text(
                                        "Selected: $userRating/5",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = primaryGreen
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = reviewTitle,
                                    onValueChange = { reviewTitle = it },
                                    label = { Text("📌 Review Title") },
                                    placeholder = { Text("Amazing notes app!") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = primaryGreen,
                                        unfocusedBorderColor = primaryLightGreen,
                                        focusedLabelColor = primaryGreen,
                                        unfocusedLabelColor = primaryLightGreen
                                    )
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = reviewContent,
                                    onValueChange = { reviewContent = it },
                                    label = { Text("📄 Your Review") },
                                    placeholder = { Text("Share your experience with EveryWrite...") },
                                    modifier = Modifier.fillMaxWidth().height(120.dp),
                                    singleLine = false,
                                    maxLines = 4,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = primaryGreen,
                                        unfocusedBorderColor = primaryLightGreen,
                                        focusedLabelColor = primaryGreen,
                                        unfocusedLabelColor = primaryLightGreen
                                    )
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (userRating > 0 && reviewTitle.isNotEmpty()) {
                                        try {
                                            reviews.add(
                                                Review((reviews.size + 1).toString(), "You", userRating, reviewTitle, reviewContent, "Just now")
                                            )
                                        } catch (e: Exception) {
                                            screenError = "Failed to add review: ${e.message}"
                                            showErrorDialog = true
                                        }
                                    }
                                    showReviewDialog = false
                                    userRating = 0
                                    reviewTitle = ""
                                    reviewContent = ""
                                },
                                enabled = userRating > 0 && reviewTitle.isNotEmpty(),
                                colors = ButtonDefaults.textButtonColors(contentColor = primaryGreen)
                            ) { Text("🌿 Submit") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showReviewDialog = false },
                                colors = ButtonDefaults.textButtonColors(contentColor = primaryDarkGreen)
                            ) {
                                Text("🍃 Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🌿 ${review.username}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${review.rating}.0",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Row {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= review.rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Rating",
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                review.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF005005)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                review.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF005005)
                ),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                review.date,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF60AD5E)
            )
        }
    }
}

@Composable
fun RatingDistribution(reviews: List<Review>) {
    val totalReviews = reviews.size
    val distribution = remember(reviews) {
        (5 downTo 1).map { stars ->
            val count = reviews.count { it.rating == stars }
            val percentage = if (totalReviews > 0) (count.toFloat() / totalReviews * 100) else 0f
            stars to percentage
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            "Rating Breakdown:",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF005005)
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        distribution.forEach { (stars, percentage) ->
            val progressValue = (percentage / 100f).coerceIn(0f, 1f)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "$stars ★",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF005005)),
                    modifier = Modifier.width(40.dp)
                )
                LinearProgressIndicator(
                    progress = progressValue,
                    modifier = Modifier.weight(1f).height(8.dp).padding(horizontal = 8.dp),
                    color = Color(0xFF2E7D32),
                    trackColor = Color(0xFFC8E6C9)
                )
                Text(
                    String.format("%.1f%%", percentage),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF005005)),
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}