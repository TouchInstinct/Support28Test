package ru.touchin.checknewsupport

import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.support.v7.app.AppCompatActivity
import android.view.View

class BottomAppBarActivity : AppCompatActivity() {

    private lateinit var bottomAppBar: BottomAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_app_bar)
        bottomAppBar = findViewById(R.id.bottom_appbar)

        //setSupportActionBar(bottom_appbar) calling this breaks it!
        bottomAppBar.replaceMenu(R.menu.bottom_app_menu)
        findViewById<View>(R.id.toggle_alignment).setOnClickListener {
            bottomAppBar.toggleAlignment()
        }
    }

    private fun BottomAppBar.toggleAlignment() {
        val current = fabAlignmentMode
        fabAlignmentMode = current.xor(1)
    }

}
