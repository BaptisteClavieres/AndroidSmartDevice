package fr.isen.clavieres.androidsmartdevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ScanAdapter(var devices: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ScanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScanActivityBinding.inflate(inflater, parent, false)
        return ScanViewHolder(binding)
    }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.deviceName.text = devices[position]
    }

    class ScanViewHolder(binding: ScanCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val deviceName = binding.DeviceName
    }
}