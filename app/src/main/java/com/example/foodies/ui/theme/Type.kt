package com.example.foodies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.foodies.R


val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal)
)

val Inter = FontFamily(
    Font(R.font.inter_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    /* TopAppBar Font */
    displayMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.5.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),

    /* Headline 6 */
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = Color.Black
    ),

    /* Headline 4 */
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),

    /* Body 2 */
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),

    /* Body 1 */
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),

    /* Button */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),

    /* Discount */
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        textDecoration = TextDecoration.LineThrough,
        color = Color.Gray
    )
)
