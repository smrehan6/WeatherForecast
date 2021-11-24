package me.smr.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.smr.weatherforecast.models.CitySearchResult

class SearchResultAdapter(private val searchClickListener: SearchClickListener) :
    ListAdapter<CitySearchResult, SearchResultAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.textView.setOnClickListener {
            searchClickListener.onSearchItemClicked(item)
        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(item: CitySearchResult) {
            textView.text = "${item.name}, ${item.country}"
        }

        companion object {
            fun from(parent: ViewGroup): SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false)

                return SearchViewHolder(view)
            }
        }

    }
}

