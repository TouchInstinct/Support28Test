package ru.touchin.checknewsupport

import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

class ChipActivity : AppCompatActivity() {

    private lateinit var chipGroupSingleLine: ChipGroup
    private lateinit var chipGroupMultiLine: ChipGroup
    private lateinit var chipGroupSingleLineBlock: View
    private lateinit var newChipTextEdit: EditText
    private lateinit var newChipAddButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chip)

        chipGroupMultiLine = findViewById(R.id.chip_group_multiple_line)
        newChipTextEdit = findViewById(R.id.add_chip_text_field)
        newChipAddButton = findViewById(R.id.add_chip_button)
        chipGroupSingleLine = findViewById(R.id.chip_group_single_line)
        chipGroupSingleLineBlock = findViewById(R.id.chip_group_single_line_block)

        newChipAddButton.setOnClickListener {
            chipGroupMultiLine.addView(createChip())
            chipGroupSingleLine.addView(createChip())
            newChipTextEdit.setText("")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.change_group_visibility -> {
                changeGroupVisibility()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_app_menu, menu)
        return true
    }

    private fun changeGroupVisibility() {
        val oldVisibility = chipGroupSingleLineBlock.visibility
        chipGroupSingleLineBlock.visibility = chipGroupMultiLine.visibility
        chipGroupMultiLine.visibility = oldVisibility
    }

    private fun createChip() = Chip(this).apply {
        chipText = newChipTextEdit.text
        isCheckable = true
        isClickable = true
        chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.colorAccent)
    }
}