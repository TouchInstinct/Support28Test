package ru.touchin.checknewsupport

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import ru.touchin.checknewsupport.selection.SelectionActivity
import ru.touchin.checknewsupport.slices.SliceActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.open_recycler_view_selection).setOnClickListener { openNewActivity(SelectionActivity::class.java) }
        findViewById<View>(R.id.open_material_elements).setOnClickListener { openNewActivity(MaterialElementsActivity::class.java) }
        findViewById<View>(R.id.open_chip).setOnClickListener { openNewActivity(ChipActivity::class.java) }
        findViewById<View>(R.id.open_bottom_app_navigation).setOnClickListener { openNewActivity(BottomAppBarActivity::class.java) }
        findViewById<View>(R.id.open_slice).setOnClickListener { openNewActivity(SliceActivity::class.java) }

    }

    private fun openNewActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

}