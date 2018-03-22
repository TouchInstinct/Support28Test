package ru.touchin.checknewsupport.selection.words

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.SelectionTracker
import ru.touchin.checknewsupport.R
import ru.touchin.checknewsupport.selection.ViewHolderWithDetails
import ru.touchin.checknewsupport.selection.models.Word
import ru.touchin.checknewsupport.selection.words.WordAdapter.WordViewHolder

class WordAdapter(private val items: List<Word>) : RecyclerView.Adapter<WordViewHolder>() {

    lateinit var tracker: SelectionTracker<Word>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
            LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_word, parent, false), items
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) = Unit

    override fun onBindViewHolder(holder: WordViewHolder, position: Int, payloads: List<Any>) {
        holder.setActivatedState(tracker.isSelected(items[position]))

        if (SelectionTracker.SELECTION_CHANGED_MARKER !in payloads) {
            holder.changeText(items[position])
        }

    }

    class WordViewHolder(itemView: View, private val items: List<Word>) : RecyclerView.ViewHolder(itemView), ViewHolderWithDetails<Word> {

        private val text: TextView = itemView.findViewById(R.id.item_word_text)

        override fun getItemDetail() = WordDetails(adapterPosition, items.getOrNull(adapterPosition))

        fun setActivatedState(isActivated: Boolean) {
            itemView.isActivated = isActivated
        }

        fun changeText(word: Word) {
            text.text = word.text
        }

    }

}