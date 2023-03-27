package fr.isen.clavieres.androidsmartdevice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.clavieres.androidsmartdevice.databinding.ScanCellBinding


class ScanAdapter (var devices: ArrayList<String>) : RecyclerView.Adapter<ScanAdapter.ScanViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScanCellBinding.inflate(inflater, parent, false)
        return ScanViewHolder(binding)
    }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        holder.deviceName.text = devices[position]
    }
    class ScanViewHolder(binding: ScanCellBinding): RecyclerView.ViewHolder(binding.root){
        val deviceName : TextView = binding.ScanTitle
    }
}