package com.melhem.neotask.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.github.islamkhsh.CardSliderAdapter
import com.melhem.neotask.Models.Options
import com.melhem.neotask.R
import com.melhem.neotask.databinding.AdapterPortfolioBinding
import com.melhem.neotask.databinding.ItemCardContentBinding
import com.melhem.neotask.utils.OnItemClick

class OptionsAdapter(var context :Context ,var listener: OnItemClick) : CardSliderAdapter<OptionsAdapter.OptionsViewHolder>() {
     var options = ArrayList<Options>()
    private var selectedPosition: Int = -1

    override fun getItemCount() = options.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardContentBinding.inflate(inflater, parent, false)

      //  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_content, parent, false)
        return OptionsViewHolder(binding)
    }

    override fun bindVH(holder: OptionsViewHolder, position: Int) {
        val option = options[position]
        holder.itemView.run {
            holder.binding.titleTxt.text="Option "+(position+1)
            holder.binding.optionNameTxt.text=option.name
            holder.binding.riskScoreTxt.text="$"+(option.risk_score*1000)
            holder.binding.shortDescTxt.text=option.short_description



        }

        var inti = Intent("ss")
        inti.putExtra("sel",true)




//if(sharedPeref?.getBoolean("clicked",false) == true){
//           sharedPeref?.edit()?.putBoolean("clicked",false)?.commit()
//default(holder)
//
//}else{
//    sharedPeref?.edit()?.putBoolean("clicked",true)?.commit()
//    selected(holder)
//}
//
//
//        }
        if(selectedPosition==-1) {
            default(holder)
            }
        else {if
                (selectedPosition==holder.adapterPosition) {
            selected(holder)
        }else{
            default(holder)
        }
                }
            holder.itemView.setOnClickListener {
                selected(holder)
                listener.onClick(position)
               if(selectedPosition!=holder.adapterPosition){
                  notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                }
             //  LocalBroadcastManager.getInstance(holder.itemView.context).sendBroadcast(inti)

            }
    }

    fun default(holder:OptionsViewHolder){
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.binding.titleTxt.setTextColor(Color.BLACK)
        holder.binding.optionNameTxt.setTextColor(Color.BLACK)
        holder.binding.riskScoreTxt.setTextColor(Color.BLACK)
        holder.binding.shortDescTxt.setTextColor(Color.BLACK)
        holder.binding.selectedTxt.setTextColor(Color.BLACK)
        holder.binding.selectedTxt.visibility=View.GONE


    }
    fun selected (holder:OptionsViewHolder){
        holder.itemView.setBackgroundColor(Color.BLUE)
        holder.binding.titleTxt.setTextColor(Color.WHITE)
        holder.binding.optionNameTxt.setTextColor(Color.WHITE)
        holder.binding.riskScoreTxt.setTextColor(Color.WHITE)
        holder.binding.shortDescTxt.setTextColor(Color.WHITE)
        holder.binding.selectedTxt.setTextColor(Color.WHITE)
        holder.binding.selectedTxt.visibility=View.VISIBLE
    }

    class OptionsViewHolder(val binding: ItemCardContentBinding) : RecyclerView.ViewHolder(binding.root)


}