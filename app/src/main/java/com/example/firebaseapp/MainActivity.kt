package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val buttonEstado = findViewById<Button>(R.id.buttonEstado)

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
            // Lógica para crear usuario y guardar en Firebase

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId,
                name = name,
                lastname = lastName,
                email = email,
                phone = phone,
                address = address
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
                technicalData = technicalData
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

        buttonEstado.setOnClickListener {
            // Aquí definimos la acción al hacer clic en el botón
            val intent = Intent(this, FinancialActivity::class.java)
            startActivity(intent)
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

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId,
                name = newName,
                lastname = newLastName,
                email = newEmail,
                phone = newPhone,
                address = newAddress
            )
            val technicalData = TechnicalData(
                manga = newManga,
                servicio = newServicio,
                serieONT = newSerieONT,
                ip = newIp,
                anchoBanda = newAnchoBanda
            )
            val user = User(
                generalData = generalData,
                technicalData = technicalData
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
    }

    private fun createUserFirebase(user: com.example.firebaseapp.models.User) {
        val userId = user.generalData?.id

        // Escribir el registro en la base de datos
        userId?.let {
            database.reference.child("users").child(it).setValue(user)
                .addOnSuccessListener {
                    // Escritura exitosa
                    Log.d("Firebase", "Write successful")
                    Toast.makeText(this@MainActivity,"Usuario Creado",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Error en la escritura
                    Log.e("Firebase", "Error writing to database: ${it.message}")
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
                        // Obtén una referencia a cada EditText
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

                        // Asigna los valores a los EditText correspondientes
                        editTextName.setText(user.generalData?.name)
                        editTextLastName.setText(user.generalData?.lastname)
                        editTextEmail.setText(user.generalData?.email)
                        editTextPhone.setText(user.generalData?.phone)
                        editTextAddress.setText(user.generalData?.address)
                        editTextManga.setText(user.technicalData?.manga)
                        editTextServicio.setText(user.technicalData?.servicio)
                        editTextSerieONT.setText(user.technicalData?.serieONT)
                        editTextIP.setText(user.technicalData?.ip)
                        editTextAnchoBanda.setText(user.technicalData?.anchoBanda)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Manejo de error en caso de cancelación
                    Log.e("Firebase", "Read canceled: ${error.message}")
                }
            })
    }



    private fun updateUser(user: com.example.firebaseapp.models.User) {
        val userId = user.generalData?.id
        val newGeneralData = user.generalData // Obtén una referencia al objeto GeneralData
        val newTechnicalData = user.technicalData // Obtén una referencia al objeto TechnicalData

        userId?.let {
            val updates = hashMapOf<String, Any>(
                "generalData/name" to newGeneralData?.name.toString(),         // Actualiza el nombre
                "generalData/lastname" to newGeneralData?.lastname.toString(), // Actualiza el apellido
                "generalData/email" to newGeneralData?.email.toString(),        // Actualiza el correo electrónico
                "generalData/phone" to newGeneralData?.phone.toString(),       // Actualiza el teléfono
                "generalData/address" to newGeneralData?.address.toString(),   // Actualiza la dirección
                "technicalData/manga" to newTechnicalData?.manga.toString(),       // Actualiza el manga
                "technicalData/servicio" to newTechnicalData?.servicio.toString(), // Actualiza el servicio
                "technicalData/serieONT" to newTechnicalData?.serieONT.toString(), // Actualiza la serie ONT
                "technicalData/ip" to newTechnicalData?.ip.toString(),             // Actualiza la dirección IP
                "technicalData/anchoBanda" to newTechnicalData?.anchoBanda.toString(), // Actualiza el ancho de banda
            )
            database.reference.child("users").child(it).updateChildren(updates)
                .addOnSuccessListener {
                    // Actualización exitosa
                    Log.d("Firebase", "Update successful")
                    Toast.makeText(this@MainActivity,"Usuario Actualizado",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Error en la actualización
                    Log.e("Firebase", "Error updating user: ${it.message}")
                }
        }
    }


    private fun deleteUser(userId: String) {
        database.reference.child("users").child(userId).removeValue()
            .addOnSuccessListener {
                // Eliminación exitosa
                Log.d("Firebase", "Delete successful")
                Toast.makeText(this@MainActivity,"Usuario Eliminado",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                // Error en la eliminación
                Log.e("Firebase", "Error deleting user: ${e.message}")
            }
    }


}
