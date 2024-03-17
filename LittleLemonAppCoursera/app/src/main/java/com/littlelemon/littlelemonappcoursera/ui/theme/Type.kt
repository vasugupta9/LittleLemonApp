package com.littlelemon.littlelemonappcoursera.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.littlelemon.littlelemonappcoursera.LittleLemonColors

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

   headlineLarge = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = LittleLemonColors.charcoal
    ),

    headlineMedium = TextStyle(
        color = LittleLemonColors.charcoal,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),

    bodyMedium = TextStyle(
        color = LittleLemonColors.darkGreen
    ),

    bodySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        color = LittleLemonColors.darkGreen
    ),

    labelSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )

)