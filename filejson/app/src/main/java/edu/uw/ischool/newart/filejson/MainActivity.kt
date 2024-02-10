package edu.uw.ischool.newart.filejson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonWriter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import org.json.JSONObject
import java.io.FileNotFoundException
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
            openFileInput("AppData.json").apply {
                // this == FileInputStream
                // this.reader == InputStreamReader(this)

                // Use JsonReader to read the JSON file
                val jsonreader = JsonReader(reader())
                jsonreader.beginObject()
                assert(jsonreader.nextName() == "servername")
                val servername = jsonreader.nextString()
                assert(jsonreader.nextName() == "portnumber")
                val portnumber = jsonreader.nextInt().toString()
                jsonreader.endObject()

                // Or, use JSONObject/JSONArray and normal File I/O
                /*
                var filecontents = ""
                reader().forEachLine { filecontents += it }
                val filejson = JSONObject(filecontents)

                val servername = filejson["servername"].toString()
                val portnumber = filejson["portnumber"].toString()
                 */

                edtServername.setText(servername)
                edtPort.setText(portnumber)

                // Otherwise we have to read and write the syntax ourselves

                close()
            }

        }
        catch (ioEx : IOException) {
            Log.e("MainActivity", "Error opening AppData", ioEx)
        }
    }
    fun store() {
        try {
            openFileOutput("AppData.json", MODE_PRIVATE).apply {
                // this == FileOutputStream
                // this.writer() == OutputStreamWriter(this)

                // Use JsonWriter to write the JSON file
                var jsonwriter = JsonWriter(writer())
                jsonwriter.beginObject()
                jsonwriter.name("servername")
                jsonwriter.value(edtServername.text.toString())
                jsonwriter.name("portnumber")
                jsonwriter.value(edtPort.text.toString())
                jsonwriter.endObject()
                jsonwriter.close()

                // Or use JSONObject/JSONArray and normal file I/O
                /*
                var jsonObj = JSONObject()
                jsonObj.put("servername", edtServername.text.toString())
                jsonObj.put("portnumber", edtPort.text.toString())

                writer().write(jsonObj.toString(4))
                 */

                // Otherwise we have to read and write the syntax ourselves

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
        Files.deleteIfExists(Paths.get("./AppData.properties"))
    }
}