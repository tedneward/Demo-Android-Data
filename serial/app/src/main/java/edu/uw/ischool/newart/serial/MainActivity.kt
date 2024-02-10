package edu.uw.ischool.newart.serial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonWriter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Paths

// This class implicitly inherits the java.io.Serializable interface
data class StoredData(val servername : String, val portnumber: Int) : Serializable

class MainActivity : AppCompatActivity() {
    lateinit var edtServername : EditText
    lateinit var edtPort : EditText
    lateinit var btnStore : Button
    lateinit var btnLoad : Button
    lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtServername = findViewById(R.id.edtServername)
        edtPort = findViewById(R.id.edtPortnumber)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener { load() }
        btnStore = findViewById(R.id.btnStore)
        btnStore.setOnClickListener { store() }
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener { delete() }
    }

    val filename = "AppData.ser"
    fun load() {
        try {
            ObjectInputStream(openFileInput(filename)).apply {
                // this == ObjectInputStream

                val storedData = readObject() as StoredData
                Log.i("MainActivity", "StoredData: ${storedData}")

                edtServername.setText(storedData.servername)
                edtPort.setText(storedData.portnumber.toString())

                close()
            }

        }
        catch (ioEx : IOException) {
            Log.e("MainActivity", "Error opening AppData", ioEx)
        }
    }
    fun store() {
        try {
            ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).apply {
                // this == ObjectOutputStream

                val storedData = StoredData(edtServername.text.toString(), edtPort.text.toString().toInt())
                writeObject(storedData)

                close()
            }
        }
        catch (fnfEx : FileNotFoundException) {
            Log.e("MainActivity", "File not found exception?", fnfEx)
        }
        catch (ioEx : IOException) {
            Log.e("MainActivity", "Error storing AppData", ioEx)
        }
        catch (t : Throwable) {
            Log.e("MainActivity", "Throwable: ", t)
        }
        finally {
            Log.e("MainActivity", "Exiting openFileOutput()")
        }
    }
    fun delete() {
        // Java's "Files" class has some useful methods
        Files.deleteIfExists(Paths.get(filename))
    }
}