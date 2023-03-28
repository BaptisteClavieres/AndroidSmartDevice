package fr.isen.clavieres.androidsmartdevice

import android.bluetooth.BluetoothGattCallback
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.clavieres.androidsmartdevice.databinding.DeviceActivityBinding

class DeviceActivity: AppCompatActivity() {

    private lateinit var binding: DeviceActivity
    private var bluetoothGatt: BluetoothGattCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DeviceActivityBinding.inflate(layoutInflater)
        //setContentView()
    }
}
