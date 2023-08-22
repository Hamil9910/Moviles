package com.example.firebaseapp.models

data class User(
    val generalData: GeneralData? = null,
    val financialData: FinancialData? = null,
    val technicalData: TechnicalData ? = null
)
