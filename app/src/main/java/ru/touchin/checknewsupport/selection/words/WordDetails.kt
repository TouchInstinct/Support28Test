package ru.touchin.checknewsupport.selection.words

import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import ru.touchin.checknewsupport.selection.models.Word

class WordDetails(private val adapterPosition: Int, private val selectedKey: Word?) : ItemDetails<Word>() {

    override fun getSelectionKey() = selectedKey

    override fun getPosition() = adapterPosition

}