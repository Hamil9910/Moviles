package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaseapp.models.*
import com.example.firebaseapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FinancialActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.financial_activity)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val buttonCreate = findViewById<Button>(R.id.buttonCreate)
        val buttonRead = findViewById<Button>(R.id.buttonRead)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        val editTextUserId = findViewById<EditText>(R.id.editTextUserId)
        val editTextInventario = findViewById<EditText>(R.id.editTextInventario)
        val editTextMes = findViewById<EditText>(R.id.editTextMes)
        val editTextValorC1 = findViewById<EditText>(R.id.editTextValorC1)
        val editTextValorC2 = findViewById<EditText>(R.id.editTextValorC2)
        val editTextValorC3 = findViewById<EditText>(R.id.editTextValorC3)
        val editTextValorT1 = findViewById<EditText>(R.id.editTextValorT1)
        val editTextValorA1 = findViewById<EditText>(R.id.editTextValorA1)
        val editTextValorA2 = findViewById<EditText>(R.id.editTextValorA2)

        buttonCreate.setOnClickListener {
            val userId = editTextUserId.text.toString()
            val inventario = editTextInventario.text.toString()
            val mes = editTextMes.text.toString()
            val valorC1 = editTextValorC1.text.toString()
            val valorC2 = editTextValorC2.text.toString()
            val valorC3 = editTextValorC3.text.toString()
            val valorT1 = editTextValorT1.text.toString()
            val valorA1 = editTextValorA1.text.toString()
            val valorA2 = editTextValorA2.text.toString()
            // Lógica para crear usuario y guardar en Firebase

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId
            )
            val revenueData = RevenueData(
                valorC1 = valorC1,
                valorC2 = valorC2,
                valorC3 = valorC3,
            )
            val outgoData = OutgoData(
                valorT1 = valorT1,
                valorA1 = valorA1,
                valorA2 = valorA2,
            )
            val financialData = FinancialData(
                inventario = inventario,
                mes = mes,
                revenueData = revenueData,
                outgoData = outgoData
            )
            val user = User(
                generalData = generalData,
                financialData = financialData
            )
            // ... Código para crear el usuario y otras operaciones
            createUserFirebase(user)
            // Limpia los campos de texto después de crear el usuario
            clearEditTextFields()

        }
        buttonRead.setOnClickListener {
            val userId = editTextUserId.text.toString()
            readUser(userId)
        }

        buttonUpdate.setOnClickListener {
            val userId = editTextUserId.text.toString()
            val newInventario = editTextInventario.text.toString()
            val newMes = editTextMes.text.toString()
            val newValorC1 = editTextValorC1.text.toString()
            val newValorC2 = editTextValorC2.text.toString()
            val newValorC3 = editTextValorC3.text.toString()
            val newValorT1 = editTextValorT1.text.toString()
            val newValorA1 = editTextValorA1.text.toString()
            val newValorA2 = editTextValorA2.text.toString()

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId
            )
            val revenueData = RevenueData(
                valorC1 = newValorC1,
                valorC2 = newValorC2,
                valorC3 = newValorC3,
            )
            val outgoData = OutgoData(
                valorT1 = newValorT1,
                valorA1 = newValorA1,
                valorA2 = newValorA2,
            )
            val financialData = FinancialData(
                inventario = newInventario,
                mes = newMes,
                revenueData = revenueData,
                outgoData = outgoData
            )
            val user = User(
                generalData = generalData,
                financialData = financialData
            )
            // ... Código para actualizar el usuario y otras operaciones
            updateUser(user)
            // Limpia los campos de texto después de actualizar el usuario
            clearEditTextFields()
        }

        buttonDelete.setOnClickListener {
            val userId = editTextUserId.text.toString()
            deleteUser(userId)
        }
    }

    private fun clearEditTextFields() {
        findViewById<EditText>(R.id.editTextInventario).text.clear()
        findViewById<EditText>(R.id.editTextMes).text.clear()
        findViewById<EditText>(R.id.editTextValorC1).text.clear()
        findViewById<EditText>(R.id.editTextValorC2).text.clear()
        findViewById<EditText>(R.id.editTextValorC3).text.clear()
        findViewById<EditText>(R.id.editTextValorT1).text.clear()
        findViewById<EditText>(R.id.editTextValorA1).text.clear()
        findViewById<EditText>(R.id.editTextValorA2).text.clear()
    }

    private fun createUserFirebase(user: com.example.firebaseapp.models.User) {
        val userId = user.generalData?.id

        // Escribir el registro en la base de datos
        userId?.let {
            database.reference.child("users").child(it).setValue(user)
                .addOnSuccessListener {
                    // Escritura exitosa
                    Log.d("Firebase", "Write successful")
                    Toast.makeText(this@FinancialActivity,"Usuario Financiero Creado",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Error en la escritura
                    Log.e("Firebase", "Error writing to database: ${it.message}")
                    Toast.makeText(this@FinancialActivity,"No hay Usuario!!",Toast.LENGTH_LONG).show()
                }
        }
    }
    private fun readUser(userId: String) {
        val database = FirebaseDatabase.getInstance() // Asegúrate de obtener una instancia de la base de datos
        val reference = database.reference.child("users").child(userId)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                user?.let {
                    // Obtén una referencia a cada EditText
                    val editTextInventario = findViewById<EditText>(R.id.editTextInventario)
                    val editTextMes = findViewById<EditText>(R.id.editTextMes)
                    val editTextValorC1 = findViewById<EditText>(R.id.editTextValorC1)
                    val editTextValorC2 = findViewById<EditText>(R.id.editTextValorC2)
                    val editTextValorC3 = findViewById<EditText>(R.id.editTextValorC3)
                    val editTextValorT1 = findViewById<EditText>(R.id.editTextValorT1)
                    val editTextValorA1 = findViewById<EditText>(R.id.editTextValorA1)
                    val editTextValorA2 = findViewById<EditText>(R.id.editTextValorA2)

                    // Asigna los valores a los EditText correspondientes
                    editTextInventario.setText(user.financialData?.inventario)
                    editTextMes.setText(user.financialData?.mes)
                    editTextValorC1.setText(user.financialData?.revenueData?.valorC1)
                    editTextValorC2.setText(user.financialData?.revenueData?.valorC2)
                    editTextValorC3.setText(user.financialData?.revenueData?.valorC3)
                    editTextValorT1.setText(user.financialData?.outgoData?.valorT1)
                    editTextValorA1.setText(user.financialData?.outgoData?.valorA1)
                    editTextValorA2.setText(user.financialData?.outgoData?.valorA2)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Manejo de error en caso de cancelación
                Log.e("Firebase", "Read canceled: ${error.message}")
                Toast.makeText(this@FinancialActivity,"No hay Lectura!!",Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun updateUser(user: com.example.firebaseapp.models.User) {
        val userId = user.generalData?.id
        val newFinancialData = user.financialData // Obtén una referencia al objeto FinancialData
        val newRevenueData = user.financialData?.revenueData // Obtén una referencia al objeto RevenueData
        val newOutgoData = user.financialData?.outgoData // Obtén una referencia al objeto OutgoData

        userId?.let {
            val updates = hashMapOf<String, Any>(
                "financialData/inventario" to newFinancialData?.inventario.toString(), // Actualiza el inventario
                "financialData/mes" to newFinancialData?.mes.toString(),           // Actualiza el mes
                "financialData/revenueData/valorC1" to newRevenueData?.valorC1.toString(),     // Actualiza el valor de C1
                "financialData/revenueData/valorC2" to newRevenueData?.valorC2.toString(),     // Actualiza el valor de C2
                "financialData/revenueData/valorC3" to newRevenueData?.valorC3.toString(),     // Actualiza el valor de C3
                "financialData/outgoData/valorT1" to newOutgoData?.valorT1.toString(),         // Actualiza el valor2
                "financialData/outgoData/valorA1" to newOutgoData?.valorA1.toString(),         // Actualiza el valor2
                "financialData/outgoData/valorA2" to newOutgoData?.valorA2.toString()     // Actualiza el egreso
            )
            database.reference.child("users").child(it).updateChildren(updates)
                .addOnSuccessListener {
                    // Actualización exitosa
                    Log.d("Firebase", "Update successful")
                    Toast.makeText(this@FinancialActivity,"Usuario Actualizado",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Error en la actualización
                    Log.e("Firebase", "Error updating user: ${it.message}")
                    Toast.makeText(this@FinancialActivity,"No hay UsuarioAc!!",Toast.LENGTH_LONG).show()
                }
        }
    }


    private fun deleteUser(userId: String) {
        database.reference.child("users").child(userId).removeValue()
            .addOnSuccessListener {
                // Eliminación exitosa
                Log.d("Firebase", "Delete successful")
                Toast.makeText(this@FinancialActivity,"Usuario Eliminado",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                // Error en la eliminación
                Log.e("Firebase", "Error deleting user: ${e.message}")
            }
    }


}
