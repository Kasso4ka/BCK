package com.example.bckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.view.View;
import android.webkit.WebView
import java.io.IOException
import java.io.InputStream
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.content.SharedPreferences
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var db: DataBasePreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result: TextView = findViewById(R.id.text1)
        val btnSend: Button = findViewById(R.id.btn_request)
        val login: EditText = findViewById(R.id.input1)
        val pass: EditText = findViewById(R.id.input2)

        db = DataBasePreferences(this)
        db.addUid("no")

        btnSend.setOnClickListener() {
                val PostData = "act=auth&login=${login.text}&pass=${pass.text}&uid=${db.getUid().toString()}"
                GlobalScope.launch (Dispatchers.IO) {
                    val url = URL("https://script.google.com/macros/s/AKfycbxjeGVbvv4VtDHPae-5u-qdVwMNtyzqmeMGNkgGY98B9WwSENyYD_pOUY7S2ywIp5mKJg/exec")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    conn.setRequestProperty("Accept","application/json")
                    conn.doOutput = true
                    conn.doInput = true
                    val outStream = OutputStreamWriter(conn.outputStream)
                    Log.d("Input", PostData)
                    outStream.write(PostData)
                    outStream.flush()
                    val status = conn.responseCode
                    if (status == HttpURLConnection.HTTP_OK) {
                        val response = conn.inputStream.bufferedReader()
                            .use { it.readText() }
                        withContext((Dispatchers.Main)) {
                            Log.d("JSON:", response)
                            result.text = response.toString()
                            db.addUid(response.toString())
                            Log.d("Test:", db.getUid().toString())
                        }
                }
                    else {
                        Log.e("HTTPURLCONNECTION_ERROR", status.toString(1))
                    }
            }
        }
    }
}