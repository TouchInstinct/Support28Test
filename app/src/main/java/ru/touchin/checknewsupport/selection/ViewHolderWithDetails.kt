package ru.touchin.checknewsupport.selection

import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails

interface ViewHolderWithDetails<TItem> {

    fun getItemDetail(): ItemDetails<TItem>

}