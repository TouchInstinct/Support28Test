package ru.touchin.checknewsupport.selection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import ru.touchin.checknewsupport.R
import ru.touchin.checknewsupport.selection.models.Word
import ru.touchin.checknewsupport.selection.words.WordAdapter
import ru.touchin.checknewsupport.selection.words.WordKeyProvider
import ru.touchin.checknewsupport.selection.words.WordLookup

class SelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        recyclerView = findViewById(R.id.recycler_view)

        val items = IntRange(0, 100).map { Word(it, it.toString()) }
        val adapter = WordAdapter(items)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val tracker = SelectionTracker
                .Builder<Word>(
                        // индетифицируем трекер в констексе
                        "someId",
                        recyclerView,
                        // для Long ItemKeyProvider реализован в виде StableIdKeyProvider
                        WordKeyProvider(items),
                        WordLookup(recyclerView),
                        // существуют аналогичные реализации для Long и String
                        StorageStrategy.createParcelableStorage(Word::class.java)
                )
                .build()

        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Any>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (tracker.hasSelection() && actionMode == null) {
                    actionMode = startSupportActionMode(ActionModeController(tracker))
                    setSelectedTitle(tracker.selection.size())
                } else if (!tracker.hasSelection()) {
                    actionMode?.finish()
                    actionMode = null
                } else {
                    setSelectedTitle(tracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Selected: $selected"
    }

}
