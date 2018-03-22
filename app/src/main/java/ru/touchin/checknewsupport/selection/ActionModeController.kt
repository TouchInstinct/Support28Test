package ru.touchin.checknewsupport.selection

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.selection.SelectionTracker
import ru.touchin.checknewsupport.R

class ActionModeController(
        private val tracker: SelectionTracker<*>
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        tracker.clearSelection()
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = true

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_clear -> {
            mode.finish()
            true
        }
        else -> false
    }


}