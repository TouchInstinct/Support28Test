package ru.touchin.checknewsupport.slices

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View
import androidx.slice.SliceManager
import androidx.slice.widget.SliceView
import ru.touchin.checknewsupport.R

class SliceActivity : AppCompatActivity() {

    private val baseSliceUri: Uri = Uri.parse("content://ru.touchin.provider/")
    private val timeSliceUri = baseSliceUri.buildUpon().appendPath("time").build()

    private lateinit var sliceView: SliceView
    private lateinit var getPermissionsButton: View
    private lateinit var sliceContainer: ConstraintLayout

    private lateinit var sliceManager: SliceManager
    private var pendingSliceUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slice_host)

        sliceView = findViewById(R.id.slice_view)
        getPermissionsButton = findViewById(R.id.button_grant_permission)
        sliceContainer = findViewById(R.id.slice_container)
        sliceManager = SliceManager.getInstance(this)

        findViewById<View>(R.id.get_slice).setOnClickListener {
            tryShowingSlice(timeSliceUri)
        }

        transitionUiTo(UiState.Empty, animate = false)
    }

    override fun onStart() {
        super.onStart()

        if (providerAppNotInstalled(packageManager, baseSliceUri.authority)) {
            showMissingProviderDialog(this, { finish() }, baseSliceUri)
            return
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        handleSlicePermissionActivityResult(requestCode, onSlicePermissionResult = {
            if (pendingSliceUri == null) {
                throw IllegalArgumentException("The slice URI to bind to cannot be null")
            }
            tryShowingSlice(pendingSliceUri!!)
            pendingSliceUri = null
        })
    }

    private fun tryShowingSlice(sliceUri: Uri) {
        if (sliceManager.missingPermission(sliceUri, appName = getString(R.string.app_name))) {
            transitionUiTo(UiState.NeedPermission)
            getPermissionsButton.setOnClickListener {
                pendingSliceUri = sliceUri
                sliceManager.requestPermission(sliceUri, this)
            }
        } else {
            getSliceAndBind(sliceUri)
        }
    }

    private fun getSliceAndBind(sliceUri: Uri) {
        transitionUiTo(UiState.SliceContent)
        sliceView.setSlice(sliceManager.bindSlice(sliceUri))

        // TODO due to a bug in 28.0.0-alpha1, we can't use the LiveData yet 😭
//        SliceLiveData.fromUri(this, sliceUri)
//            .observe(this, Observer({ sliceResult ->
//                sliceView.setSlice(sliceResult)
//                invalidateOptionsMenu()
//            }))
    }

    private fun transitionUiTo(uiState: UiState, animate: Boolean = true) {
        if (animate) {
            TransitionManager.beginDelayedTransition(sliceContainer)
        }

        ConstraintSet().apply {
            clone(sliceContainer)
            setVisibility(R.id.group_need_permission, uiState.permissionGroupVisibility)
            setVisibility(R.id.slice_view, uiState.sliceViewVisibility)
            applyTo(sliceContainer)
        }
    }

    private sealed class UiState {

        abstract val permissionGroupVisibility: Int

        abstract val sliceViewVisibility: Int

        object Empty : UiState() {
            override val permissionGroupVisibility = View.INVISIBLE
            override val sliceViewVisibility = View.INVISIBLE
        }

        object NeedPermission : UiState() {
            override val permissionGroupVisibility = View.VISIBLE
            override val sliceViewVisibility = View.INVISIBLE
        }

        object SliceContent : UiState() {
            override val permissionGroupVisibility = View.INVISIBLE
            override val sliceViewVisibility = View.VISIBLE
        }
    }
}
