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



        val button: Button = findViewById(R.id.buttonEstado)
        button.setOnClickListener {
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
        val editTextValor2 = findViewById<EditText>(R.id.editTextValor2)
        val editTextEgreso = findViewById<EditText>(R.id.editTextEgreso)

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
            val valor2 = editTextValor2.text.toString()
            val egreso = editTextEgreso.text.toString()
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
            val revenueData = RevenueData(
                valor = valor,
                cuenta = cuenta,
            )
            val outgoData = OutgoData(
                valor2 = valor2,
                egreso = egreso,
            )
            val financialData = FinancialData(
                inventario = inventario,
                mes = mes,
                revenueData = revenueData,
                outgoData = outgoData
                //monthData = MonthData(revenueData,outgoData)
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
            val newValor2 = editTextValor2.text.toString()
            val newEgreso = editTextEgreso.text.toString()

            // Crear instancias de las clases de datos
            val generalData = GeneralData(
                id = userId,
                name = newName,
                lastname = newLastName,
                email = newEmail,
                phone = newPhone,
                address = newAddress
            )
            val revenueData = RevenueData(
                valor = newValor,
                cuenta = newCuenta,
            )
            val outgoData = OutgoData(
                valor2 = newValor2,
                egreso = newEgreso,
            )
            val financialData = FinancialData(
                inventario = newInventario,
                mes = newMes,
                revenueData = revenueData,
                outgoData = outgoData
                //monthData = MonthData(revenueData,outgoData)
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
                financialData = financialData,
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
        findViewById<EditText>(R.id.editTextInventario).text.clear()
        findViewById<EditText>(R.id.editTextMes).text.clear()
        findViewById<EditText>(R.id.editTextValor).text.clear()
        findViewById<EditText>(R.id.editTextCuenta).text.clear()
        findViewById<EditText>(R.id.editTextValor2).text.clear()
        findViewById<EditText>(R.id.editTextEgreso).text.clear()
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
                        val editTextInventario = findViewById<EditText>(R.id.editTextInventario)
                        val editTextMes = findViewById<EditText>(R.id.editTextMes)
                        val editTextValor = findViewById<EditText>(R.id.editTextValor)
                        val editTextCuenta = findViewById<EditText>(R.id.editTextCuenta)
                        val editTextValor2 = findViewById<EditText>(R.id.editTextValor2)
                        val editTextEgreso = findViewById<EditText>(R.id.editTextEgreso)

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
                        editTextInventario.setText(user.financialData?.inventario)
                        editTextMes.setText(user.financialData?.mes)
                        editTextValor.setText(user.financialData?.revenueData?.valor)
                        editTextCuenta.setText(user.financialData?.revenueData?.cuenta)
                        editTextValor2.setText(user.financialData?.outgoData?.valor2)
                        editTextEgreso.setText(user.financialData?.outgoData?.egreso)
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
        val newFinancialData = user.financialData // Obtén una referencia al objeto FinancialData
        val newRevenueData = user.financialData?.revenueData // Obtén una referencia al objeto RevenueData
        val newOutgoData = user.financialData?.outgoData // Obtén una referencia al objeto OutgoData

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
                "financialData/inventario" to newFinancialData?.inventario.toString(), // Actualiza el inventario
                "financialData/mes" to newFinancialData?.mes.toString(),           // Actualiza el mes
                "financialData/revenueData/valor" to newRevenueData?.valor.toString(),         // Actualiza el valor
                "financialData/revenueData/cuenta" to newRevenueData?.cuenta.toString(),     // Actualiza la cuenta
                "financialData/outgoData/valor2" to newOutgoData?.valor2.toString(),         // Actualiza el valor2
                "financialData/outgoData/egreso" to newOutgoData?.egreso.toString()     // Actualiza el egreso
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