package fr.isen.clavieres.androidsmartdevice

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.clavieres.androidsmartdevice.databinding.ActivityScanBinding
import org.w3c.dom.Text

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    private val bluetoothAdapter: BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE){
        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        permission ->
        if (permission.all {it.value}) {
            scanBLEDevices()
        }
    }

    private var mScanning = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(bluetoothAdapter?.isEnabled == true){
            scanDeviceWithPermission()
            Toast.makeText(this,"bluetooth activer", Toast.LENGTH_LONG).show()
        }else{
            handleBLENotAvailable()
            Toast.makeText(this,"bluetooth pas activer", Toast.LENGTH_LONG).show()
        }

        binding.ScanTitle.setOnClickListener {
            togglePlayPauseAction()
        }
        binding.playbutton.setOnClickListener {
            togglePlayPauseAction()
        }

        binding.scanList.layoutManager = LinearLayoutManager(this)
        binding.scanList.adapter = ScanAdapter(arrayListOf("Device 1", "Device 2"))


        binding.playbutton.setOnClickListener {
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)

            progressBar.setIndeterminate(true)

        }

        // binding.ScanTitle.layoutManage = LinearLayoutManager(this)
        // binding.ScanTitle.layoutAdapter = LinearLayoutManager(this)

    }

    private fun handleBLENotAvailable() {
        binding.ScanTitle.text = getString(R.string.ble_scan_title_pause)
    }

    private fun scanDeviceWithPermission() {
        if(allPermissionGranted()){
            scanBLEDevices()
        }else {
            //request toutes les permissions
            requestPermissionLauncher.launch(getAllPermission())
        }
    }

    private fun scanBLEDevices() {
        initToggleActions()
    }

    private fun initToggleActions() {
        binding.ScanTitle.setOnClickListener {
            togglePlayPauseAction()
        }

        binding.playbutton.setOnClickListener {
            togglePlayPauseAction()
        }
    }

    private fun allPermissionGranted(): Boolean {
        val allPermissions = getAllPermission()
        return allPermissions.all {
            // à remplacer par la vérification de chaque permission
            true
        }
    }

    private fun getAllPermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN)
        }
        else{
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        }

    }

    private fun togglePlayPauseAction(){
        mScanning = !mScanning
        if(mScanning){
            binding.ScanTitle.text = getString(R.string.ble_scan_title_pause)
            binding.playbutton.setImageResource(R.drawable.pause)
            binding.progressBar.isVisible = true
        } else {
            binding.ScanTitle.text = getString(R.string.ble_scan_title_play)
            binding.playbutton.setImageResource(R.drawable.play)
            binding.progressBar.isVisible = false
        }

    }


}