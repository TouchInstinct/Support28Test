package ru.touchin.checknewsupport.selection.words

import androidx.recyclerview.selection.ItemKeyProvider
import ru.touchin.checknewsupport.selection.models.Word

// В конструкторе ItemKeyProvider мы выбираем метод предоставления доступа к данным:
//  SCOPE_MAPPED - ко всем данным. Позволяет реализовать Shift+click выбор данных
//  SCOPE_CACHED - к данным, которые были недавно или сейчас на экране. Экономит память
class WordKeyProvider(private val items: List<Word>) : ItemKeyProvider<Word>(ItemKeyProvider.SCOPE_CACHED) {

    override fun getKey(position: Int) = items.getOrNull(position)

    override fun getPosition(key: Word) = items.indexOf(key)

}