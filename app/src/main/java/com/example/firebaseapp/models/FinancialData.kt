package com.example.firebaseapp.models

data class FinancialData(
    val inventario: String = null.toString(),
    val mes: String = null.toString(),
    val revenueData:RevenueData? = null,
    val outgoData:OutgoData? = null
)
/*
data class MonthData(
    val revenueData:RevenueData,
    val outgoData:OutgoData,
)
*/
