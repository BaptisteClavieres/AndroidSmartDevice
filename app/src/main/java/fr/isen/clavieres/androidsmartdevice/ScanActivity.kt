package fr.isen.clavieres.androidsmartdevice

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val REQUEST_PERMISSIONS_CODE = 1234


    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(bluetoothAdapter?.isEnabled == true){
            scanDeviceWithPermission()
            Toast.makeText(this,"bluetooth activé", Toast.LENGTH_LONG).show()
        }else{
            handleBLENotAvailable()
            Toast.makeText(this,"bluetooth pas activé", Toast.LENGTH_LONG).show()
        }

        binding.ScanTitle.setOnClickListener {
            togglePlayPauseAction()
        }
        binding.playButton.setOnClickListener {
            togglePlayPauseAction()
        }

        binding.scanList.layoutManager = LinearLayoutManager(this)
        binding.scanList.setHasFixedSize(false)
        binding.scanList.adapter = ScanAdapter(arrayListOf()) {
            val intent = Intent(this, DeviceActivity::class.java)
            intent.putExtra("device", it)
            startActivity(intent)
        }


        binding.playButton.setOnClickListener {
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
            initToggleActions()
        }else {

            requestPermissionLauncher.launch(getAllPermission())
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStop(){
        super.onStop()
        if (bluetoothAdapter?.isEnabled == true && allPermissionGranted()) {
            scanBLEDevices()
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }



    // Stops scanning after 10 seconds.


    @SuppressLint("MissingPermission")
    private fun scanBLEDevices() {
        if (!mScanning) { // Stops mScanning after a pre-defined scan period.
            handler.postDelayed({
                mScanning = false
                bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
                togglePlayPauseAction()
            }, SCAN_PERIOD)
            mScanning = true
            bluetoothAdapter?.bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            mScanning = false
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }
        togglePlayPauseAction()
    }


    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("Scan", "result: $result")
            (binding.scanList.adapter as ScanAdapter)?.addDevice(result.device)
            binding.scanList.adapter?.notifyDataSetChanged()
        }
    }

    private fun initToggleActions() {
        binding.ScanTitle.setOnClickListener {
            scanBLEDevices()
        }

        binding.playButton.setOnClickListener {
            scanBLEDevices()
        }
    }

    private fun allPermissionGranted(): Boolean {
        val allPermissions = getAllPermission()
        return allPermissions.all { permission ->
            ContextCompat.checkSelfPermission(
            this, permission) == PackageManager.PERMISSION_GRANTED
        } || requestPermissions(allPermissions)
            //true
    }

    private fun requestPermissions(permissions: Array<String>): Boolean {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
        return false
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

        if(mScanning){
            binding.ScanTitle.text = getString(R.string.ble_scan_title_pause)
            binding.playButton.setImageResource(R.drawable.pause)
            binding.progressBar.isVisible = true
        } else {
            binding.ScanTitle.text = getString(R.string.ble_scan_title_play)
            binding.playButton.setImageResource(R.drawable.play)
            binding.progressBar.isVisible = false
        }

    }

    companion object {
        private val SCAN_PERIOD: Long = 10000
    }


}