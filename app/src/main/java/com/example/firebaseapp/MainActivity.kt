package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.firebaseapp.databinding.ActivityMainBinding
import com.example.firebaseapp.models.*
import com.example.firebaseapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var  spinnerMes: Spinner?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIrEstadoFinanciero = findViewById<Button>(R.id.buttonEstado)
        btnIrEstadoFinanciero.setOnClickListener {
            val intent = Intent(this, EstadoFinancieroActivity::class.java)
           startActivity(intent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Aquí vincula el botón de cerrar sesión en el diseño con su respectivo elemento de la vista.
        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish() // Cierra esta actividad para que el usuario no pueda volver atrás.
        }
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val buttonCreate = findViewById<Button>(R.id.buttonCreate)
        val buttonRead = findViewById<Button>(R.id.buttonRead)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        val buttonClear = findViewById<Button>(R.id.clearButton)

        val editTextUserId = findViewById<EditText>(R.id.editTextUserId)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        val editTextManga = findViewById<EditText>(R.id.editTextManga)
        val editTextServicio = findViewById<EditText>(R.id.editTextServicio)
        val editTextSerieONT = findViewById<EditText>(R.id.editTextSerieONT)
        val editTextIP = findViewById<EditText>(R.id.editTextIP)
        val editTextAnchoBanda = findViewById<EditText>(R.id.editTextAnchoBanda)
        val editTextInventario = findViewById<EditText>(R.id.editTextInventario)
        val editTextMes = findViewById<EditText>(R.id.editTextMes)
        val editTextValor = findViewById<EditText>(R.id.editTextValor)
        val editTextCuenta = findViewById<EditText>(R.id.editTextCuenta)

        buttonCreate.setOnClickListener {
            val userId = editTextUserId.text.toString()
            val name = editTextName.text.toString()
            val lastName = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()
            val address = editTextAddress.text.toString()
            val manga = editTextManga.text.toString()
            val servicio = editTextServicio.text.toString()
            val serieONT = editTextSerieONT.text.toString()
            val ip = editTextIP.text.toString()
            val anchoBanda = editTextAnchoBanda.text.toString()
            val inventario = editTextInventario.text.toString()
            val mes = editTextMes.text.toString()
            val valor = editTextValor.text.toString()
            val cuenta = editTextCuenta.text.toString()
            // Lógica para crear usuario y guardar en Firebase
            //createUser(userId, name, lastName, phone, email, address, manga, servicio, serieONT, ip, anchoBanda, inventario, mes, valor, cuenta)

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId,
                name = name,
                lastname = lastName,
                email = email,
                phone = phone,
                address = address
            )

            val financialData = FinancialData(
                inventario = inventario,
                mes = mes,
                valor = valor,
                cuenta = cuenta
            )

            val technicalData = TechnicalData(
                manga = manga,
                servicio = servicio,
                serieONT = serieONT,
                ip = ip,
                anchoBanda = anchoBanda
            )

            val user = User(
                generalData = generalData,
                financialData = financialData,
                technicalData = technicalData
            )

            createUserFirebase(user)

        }

        buttonRead.setOnClickListener {
            val userId = editTextUserId.text.toString()
            readUser(userId)
        }

        buttonUpdate.setOnClickListener {
            val userId = editTextUserId.text.toString()
            val newName = editTextName.text.toString()
            val newLastName = editTextLastName.text.toString()
            val newEmail = editTextEmail.text.toString()
            val newPhone = editTextPhone.text.toString()
            val newAddress = editTextAddress.text.toString()
            val newManga = editTextManga.text.toString()
            val newServicio = editTextServicio.text.toString()
            val newSerieONT = editTextSerieONT.text.toString()
            val newIp = editTextIP.text.toString()
            val newAnchoBanda = editTextAnchoBanda.text.toString()
            val newInventario = editTextInventario.text.toString()
            val newMes = editTextMes.text.toString()
            val newValor = editTextValor.text.toString()
            val newCuenta = editTextCuenta.text.toString()

            updateUser(userId, newName, newLastName, newEmail, newPhone, newAddress,newManga, newServicio, newSerieONT, newIp,
                newAnchoBanda, newInventario, newMes, newValor, newCuenta)
        }

        buttonDelete.setOnClickListener {
            val userId = editTextUserId.text.toString()
            deleteUser(userId)
        }
        buttonClear.setOnClickListener {
            clearEditTextFields()
        }
    }
    private fun clearEditTextFields() {
        findViewById<EditText>(R.id.editTextUserId).text.clear()
        findViewById<EditText>(R.id.editTextName).text.clear()
        findViewById<EditText>(R.id.editTextLastName).text.clear()
        findViewById<EditText>(R.id.editTextEmail).text.clear()
        findViewById<EditText>(R.id.editTextPhone).text.clear()
        findViewById<EditText>(R.id.editTextAddress).text.clear()
        findViewById<EditText>(R.id.editTextManga).text.clear()
        findViewById<EditText>(R.id.editTextServicio).text.clear()
        findViewById<EditText>(R.id.editTextSerieONT).text.clear()
        findViewById<EditText>(R.id.editTextIP).text.clear()
        findViewById<EditText>(R.id.editTextAnchoBanda).text.clear()
        findViewById<EditText>(R.id.editTextInventario).text.clear()
        findViewById<EditText>(R.id.editTextMes).text.clear()
        findViewById<EditText>(R.id.editTextValor).text.clear()
        findViewById<EditText>(R.id.editTextCuenta).text.clear()
    }

    private fun createUserFirebase(user: com.example.firebaseapp.models.User){
        val userId = user.generalData?.id

        // Escribir el registro en la base de datos
        userId?.let {
            database.reference.child("users").child(it).setValue(user)
                .addOnSuccessListener {
                    // Escritura exitosa
                    Log.d("Firebase", "Write successful")
                }
                .addOnFailureListener {
                    // Error en la escritura
                    Log.e("Firebase", "Error writing to database: ${it.message}")
                }
        }
    }


    private fun readUser(userId: String) {
        database.reference.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: User? = snapshot.getValue(com.example.firebaseapp.models.User::class.java)
                user?.let {
                Log.e("iser",user.toString())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Read operation cancelled: ${error.message}")
            }
        })
    }

    private fun updateUser(userId: String, newName: String, newLastName: String,newEmail: String, newPhone: String,newAddress: String,newManga: String, newServicio: String, newSerieONT: String, newIP: String, newAnchoBanda: String, newInventario: String, newMes: String, newVal: String,
                           newCuenta: String ){
        val updates = hashMapOf<String, Any>(
            "name" to newName,         // Actualiza el nombre
            "lastName" to newLastName, // Actualiza el apellido
            "email" to newEmail,        // Actualiza el correo electrónico
            "phone" to newPhone,       // Actualiza el teléfono
            "address" to newAddress,   // Actualiza la dirección
            "manga" to newManga,       // Actualiza el manga
            "servicio" to newServicio, // Actualiza el servicio
            "serieONT" to newSerieONT, // Actualiza la serie ONT
            "ip" to newIP,             // Actualiza la dirección IP
            "anchoBanda" to newAnchoBanda, // Actualiza el ancho de banda
            "inventario" to newInventario, // Actualiza el inventario
            "mes" to newMes,           // Actualiza el mes
            "valor" to newVal,         // Actualiza el valor
            "cuenta" to newCuenta,     // Actualiza la cuenta

        )
        database.reference.child("users").child(userId).updateChildren(updates)
            .addOnSuccessListener {
                // Actualización exitosa
                Log.d("Firebase", "Update successful")
            }
            .addOnFailureListener {
                // Error en la actualización
                Log.e("Firebase", "Error updating user: ${it.message}")
            }
    }
    private fun deleteUser(userId: String) {
        database.reference.child("users").child(userId).removeValue()
    }
}

data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val manga: String = "",
    val servicio: String = "",
    val serieONT: String = "",
    val ip: String = "",
    val anchoBanda: String = "",
    val inventario: String = "",
    val mes: String = "",
    val valor: String = "",
    val cuenta: String = ""
)
