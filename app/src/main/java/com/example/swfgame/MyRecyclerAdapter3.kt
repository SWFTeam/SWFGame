package com.example.tp_android_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swfgame.Event
import com.example.swfgame.R
import kotlinx.android.synthetic.main.my_event_item.view.*

class MyRecyclerAdapter3(private val dataset: Array<Event>, private val onClickListener: ((View, Event) -> Unit)?) : RecyclerView.Adapter<MyRecyclerAdapter3.MyViewHolder>() {

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        view = LayoutInflater.from(parent.context).inflate(R.layout.my_event_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.title_event.text = this.dataset[position].getDescriptions()?.get(0)?.get(0)?.getTitle().toString()
        holder.itemView.descriptionEvent_textView.text = this.dataset[position].getDescriptions()?.get(0)?.get(0)?.getDescription().toString()
        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener.invoke(it, dataset[position])
            }
        }
    }
}