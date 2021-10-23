package com.melhem.neotask.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melhem.neotask.Models.Portfolio
import com.melhem.neotask.OptionsActivity
import com.melhem.neotask.databinding.AdapterPortfolioBinding

class MainAdapter: RecyclerView.Adapter<MainViewHolder> ()  {

    var portfolios = mutableListOf<Portfolio>()

    fun setPortFolioList(portfolios: List<Portfolio>) {
        this.portfolios = portfolios.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterPortfolioBinding.inflate(inflater, parent, false)



        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val portfolio = portfolios[position]
        holder.binding.name.text = portfolio.name
        holder.binding.balance.text=portfolio.balance.toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, OptionsActivity::class.java)
            intent.putExtra("name",portfolio.name)
            intent.putExtra("id",portfolio.id)

            intent.putExtra("balance",portfolio.balance)
            intent.putExtra("investment_type",portfolio.investment_type)
            intent.putExtra("modified_risk_score",portfolio.modified_risk_score)
            intent.putExtra("created",portfolio.created_at)

            holder.itemView.context.startActivity(intent) }
    }


    override fun getItemCount(): Int {
        return portfolios.size
    }
}

class MainViewHolder(val binding: AdapterPortfolioBinding) : RecyclerView.ViewHolder(binding.root) {

}