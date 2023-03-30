package fr.isen.clavieres.androidsmartdevice

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.clavieres.androidsmartdevice.databinding.DeviceActivityBinding

class DeviceActivity: AppCompatActivity() {

    private lateinit var binding: DeviceActivity
    private var bluetoothGatt: BluetoothGattCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DeviceActivityBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        val bluetoothDevice: BluetoothDevice? = intent.getParcelableExtra("device")
        //val bluetoothGatt = bluetoothDevice?.connectGatt(this, false, bluetoothGattCallBack)
        //bluetoothGatt?.connect()
    }

    override fun onStop() {
        super.onStop()
        //bluetoothGatt?.close()
    }

    private val bluetoothGattCallBack = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread {
                    displayContentConnected()
                }
            }
        }
    }

    private fun displayContentConnected() {
        //binding.textTitle.text = getString(R.string.textDescription)
        //binding.progressBar2.isVisible = false
        //binding.led1.isVisible = true
        //binding.led2.isVisible = true
        //binding.led3.isVisible = true
    }

}
