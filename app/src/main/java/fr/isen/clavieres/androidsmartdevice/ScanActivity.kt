package fr.isen.clavieres.androidsmartdevice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import org.w3c.dom.Text

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    private val bluetoothAdapter: BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE){
        val bluetoothManager :
                getSystemService(Context.BLUETOOTH_Service) as BluetoothManager
        bluetoothManager.adapter
    }

    private var aScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_scan)

        var mainText = findViewById<TextView>(R.id.ScanText)
        mainText.setText("Lancer le Scan BLE")

        binding.ScanText.setOnClickListener {
            togglePlayPauseAction()
        }

        binding.playbutton.setOnClickListener {
            togglePlayPauseAction()
        }

        binding.scanList.layoutManager = LinearLayoutManager ( context: this)
        binding.scanList.adapter = ScanAdapter (arrayListOf("Device 1", "Device 2"))

    }

    private fun scandeviceWithPermissions(){
        if (allPermissionsGranted()){
            scanBLEDevices()
        }
        else{
            //request permission pour TOUTES les permissions
        }
    }

    private fun scanBLEDevices(){
       initToggleActions()
    }

    private fun allPermissionsGranted(){
        var allPermissions = getAllPermissions()
        return allPermissions.all {
            //a remplacer par la verification de chaque permission
            true
        }
    }

    private fun getAllPermissions(): Array<String> {
        return arrayOf(Manifest.permission.BLUETOOTH_ADMIN)
    }



    private fun togglePlayPauseAction(){
        aScanning = !aScanning
        if (aScanning){
            binding.ScanText.text = "Scan en cours..."
            binding.playbutton.text.text = getString(R.drawable.pause)
            binding.progressBar.isVisible = true
        }
        else {
            binding.ScanText.text = "Lancer le scan BLE"
            binding.playbutton.text.text = getString(R.drawable.play)
            binding.progressBar.isVisible = false
        }
    }
}