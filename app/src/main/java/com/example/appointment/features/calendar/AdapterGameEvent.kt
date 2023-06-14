package com.example.appointment.features.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.models.local.GameEventModel

class AdapterGameEvent (private val GameEventlist: List<GameEventModel>, private val onItemClicked: (position: Int) -> Unit)
    : RecyclerView.Adapter<AdapterGameEvent.MyViewHolder>()
{

    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_event_list, parent, false)

        return MyViewHolder(itemView, onItemClicked)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return GameEventlist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEmp = GameEventlist[position]
        holder.gameEventName.text = currentEmp.gameEventName
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val gameEventName: TextView = itemView.findViewById(R.id.gameEventName)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }

    }
}