package com.example.firebaseapp

import android.accounts.Account
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapp.models.FinancialData
import com.example.firebaseapp.models.User

class EstadoFinancieroActivity : AppCompatActivity() {
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estado_financiero)

        // Initialize spinners and adapters
        val userSpinner = findViewById<Spinner>(R.id.userDropdown)
        val accountSpinner = findViewById<Spinner>(R.id.accountDropdown)

        // Set up spinner adapters with user and account data

        // Set up spinner selection listeners
        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Logic to handle user selection
                val selectedUser = userSpinner.selectedItem as User
                val selectedAccount = accountSpinner.selectedItem as Account

                // Get financial data for the selected user and account
                val financialData = getFinancialData(selectedUser, selectedAccount)

                // Populate the table with financial data
                populateTable(financialData)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }
    }

    private fun getFinancialData(user: User, account: Account): FinancialData {
        // Logic to retrieve financial data based on selected user and account
        // Replace this with your actual data retrieval logic
        return FinancialData()
    }

    private fun populateTable(financialData: FinancialData) {
        // Clear existing table rows

        // Create new table rows and populate with financial data
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val newRow = TableRow(this)
        // Add TextViews or other views to the row and set their text/data
        // Repeat for each financial data attribute (inventory, month, value, total)
        // ...

        // Add the new row to the table
        tableLayout.addView(newRow)
    }
    */

}