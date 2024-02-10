package edu.uw.ischool.newart.fileio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.inputStream
import kotlin.io.outputStream

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

    fun load() {
        try {
            openFileInput("AppData.properties").apply {
                // this == FileInputStream

                // a java.util.Properties gives us "x=y" syntax out of the box
                val props = java.util.Properties()
                props.load(this)

                edtServername.setText(props["servername"] as String)
                val portnumber = props.getProperty("portnumber").toInt()
                edtPort.setText(portnumber.toString())

                // Otherwise we have to read and write the syntax ourselves
                /*
                val reader = this.reader()
                reader.forEachLine {
                    if (it.startsWith("servername")) {
                        val value = it.split("=")
                        edtServername.setText(value[1])
                    }
                    else if (it.startsWith("portnumber")) {
                        val value = it.split("=")
                        edtPort.setText(value[1])
                    }
                    else {
                        Log.w("MainActivity", "Unrecognized line: " + it)
                    }
                }
                 */

                close()
            }

        }
        catch (ioEx : IOException) {
            Log.e("MainActivity", "Error opening AppData", ioEx)
        }
    }
    fun store() {
        try {
            openFileOutput("AppData.properties", MODE_PRIVATE).apply {
                // this == FileOutputStream

                // a java.util.Properties gives us "x=y" syntax out of the box
                val props = java.util.Properties()
                props["servername"] = edtServername.text.toString()
                props["port"] = edtPort.text.toString()
                props.store(this, "No comments")

                // Otherwise we have to read and write the syntax ourselves
                /*
                val reader = this.reader()
                reader.forEachLine {
                    if (it.startsWith("servername")) {
                        val value = it.split("=")
                        edtServername.setText(value[1])
                    }
                    else if (it.startsWith("portnumber")) {
                        val value = it.split("=")
                        edtPort.setText(value[1])
                    }
                    else {
                        Log.w("MainActivity", "Unrecognized line: " + it)
                    }
                }
                 */

                close()
            }

        }
        catch (ioEx : IOException) {
            Log.e("MainActivity", "Error storing AppData", ioEx)
        }
    }
    fun delete() {
        // Java's "Files" class has some useful methods
        Files.deleteIfExists(Paths.get("./AppData.properties"))
    }
}