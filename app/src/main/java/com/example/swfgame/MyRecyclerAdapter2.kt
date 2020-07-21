package com.example.tp_android_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swfgame.Challenge
import com.example.swfgame.R
import kotlinx.android.synthetic.main.my_challenge_item.view.*

class MyRecyclerAdapter2(private val dataset: ArrayList<Challenge>, private val onClickListener: ((View, Challenge) -> Unit)?) : RecyclerView.Adapter<MyRecyclerAdapter2.MyViewHolder>() {

    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        view = LayoutInflater.from(parent.context).inflate(R.layout.my_challenge_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var index: Int = 0

        for(i in 0..this.dataset[position].getDescription()?.size!!){
            if(this.dataset[position].getDescription()?.get(i)?.getCountryCode().toString() == "GB"){
                index = i
                break
            }
        }

        holder.itemView.title_challenge.text = this.dataset[position].getDescription()?.get(index)?.getTitle().toString()
        if(this.dataset[position].getDescription()?.get(index)?.getDescription().toString().length <= 40){
            holder.itemView.description_textView.text = this.dataset[position].getDescription()?.get(index)?.getDescription().toString()
        } else {
            var text: String = this.dataset[position].getDescription()?.get(index)?.getDescription().toString().subSequence(0, 40).toString() + "..."
            holder.itemView.description_textView.text = text
        }
        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener.invoke(it, dataset[position])
            }
        }
    }
}