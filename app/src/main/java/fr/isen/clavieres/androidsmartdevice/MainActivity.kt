package fr.isen.clavieres.androidsmartdevice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainTitle = findViewById<TextView>(R.id.MainTitle)

        var mainText = findViewById<TextView>(R.id.Description)

        var mainButton = findViewById<Button>(R.id.Mainbutton)
        mainButton.setOnClickListener(){
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

    }
}