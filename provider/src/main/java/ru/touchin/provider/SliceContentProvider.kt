package ru.touchin.provider

import android.net.Uri
import android.widget.Toast
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import java.text.SimpleDateFormat
import java.util.Calendar

class SliceContentProvider : SliceProvider() {

    private var counter = 0
    override fun onBindSlice(sliceUri: Uri): Slice {
        return when (sliceUri.path) {
            "/time" -> createTimeSlice(sliceUri)
            else -> throw IllegalArgumentException("Bad url")
        }
    }

    override fun onCreateSliceProvider(): Boolean {
        Toast.makeText(context, "Slice content provider is launched", Toast.LENGTH_LONG).show()
        return true
    }

    private fun createTimeSlice(sliceUri: Uri): Slice = ListBuilder(context, sliceUri)
            .apply {
                counter++
                setHeader(
                        ListBuilder.HeaderBuilder(this)
                                .setTitle("What's the time now?")
                )
                addRow(
                        ListBuilder.RowBuilder(this)
                                .setTitle("It is ${SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)}")
                )
                addRow(
                        ListBuilder.RowBuilder(this)
                                .setTitle("Slice has called $counter times")
                )
            }
            .build()

}
