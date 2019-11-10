package com.r.events

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented nested_list_of_event_items, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under nested_list_of_event_items.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.r.events", appContext.packageName)
    }
}
