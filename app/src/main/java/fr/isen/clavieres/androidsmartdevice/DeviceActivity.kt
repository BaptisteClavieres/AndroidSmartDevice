package fr.isen.clavieres.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.clavieres.androidsmartdevice.databinding.ActivityDeviceBinding
import java.util.*


class DeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeviceBinding
    private var bluetoothGatt: BluetoothGatt? = null
    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")
    private val characteristicLedUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")
    private val characteristicButtonUUID = UUID.fromString("00001234-8e22-4541-9d4c-21edae82ed19")
    private var textIncre=0
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothDevice: BluetoothDevice? = intent.getParcelableExtra("device")

        bluetoothGatt = bluetoothDevice?.connectGatt(this, false, bluetoothGattCallback)
// bluetoothGatt?.connect()

        clickOnLed()
    }
    @SuppressLint("MissingPermission")
    override fun onStop(){
        super.onStop()
        bluetoothGatt?.close()
    }

    @SuppressLint("MissingPermission")
    private fun clickOnLed(){
        binding.led1.setOnClickListener{
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)

            if(binding.led1.imageTintList == getColorStateList(R.color.teal_200)){
                binding.led1.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led1.imageTintList = getColorStateList(R.color.teal_200)
                characteristic?.value = byteArrayOf(0x01)
                bluetoothGatt?.writeCharacteristic(characteristic)
                textIncre ++
                binding.textIncre.text="Nombre de click : $textIncre"
            }
        }
        binding.led2.setOnClickListener{
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)

            if(binding.led2.imageTintList == getColorStateList(R.color.teal_200)){
                binding.led2.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led2.imageTintList = getColorStateList(R.color.teal_200)
                characteristic?.value = byteArrayOf(0x02)
                bluetoothGatt?.writeCharacteristic(characteristic)
                textIncre ++
                binding.textIncre.text="Nombre de click : $textIncre"
            }
        }
        binding.led3.setOnClickListener{
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)

            if(binding.led3.imageTintList == getColorStateList(R.color.teal_200)){
                binding.led3.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led3.imageTintList = getColorStateList(R.color.teal_200)
                characteristic?.value = byteArrayOf(0x03)
                bluetoothGatt?.writeCharacteristic(characteristic)
                textIncre ++
                binding.textIncre.text="Nombre de click : $textIncre"
            }
        }
    }

    private val bluetoothGattCallback = object: BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if(newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread{
                    displayContentConnected()
                }
                bluetoothGatt?.discoverServices()
            }
        }

    }

    private fun displayContentConnected(){
        binding.textTitle.text = "TPBLE"
        binding.textDescription.text = "Affichage des LEDs"
        binding.textNotif.isVisible = true
        binding.progressBar2.isVisible = false
        binding.led1.isVisible = true
        binding.led2.isVisible = true
        binding.led3.isVisible = true
        binding.textIncre.isVisible = true
    }
}