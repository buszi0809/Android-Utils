package com.kwdev.viewscoped

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class ViewScopedDelegate<T : Any>(
    private val producer: (() -> T)?,
    onPreDestroy: (T?) -> Unit,
    fragment: Fragment,
) : ReadWriteProperty<Fragment, T> {

    private var value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            onPreDestroy(value)
                            value = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        assureViewInCreatedState(thisRef, property)
        if (value == null) {
            value = producer?.invoke()
        }
        return value ?: throw valueNotPresentException(thisRef, property)
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        assureViewInCreatedState(thisRef, property)
        this.value = value
    }

    private fun assureViewInCreatedState(fragment: Fragment, property: KProperty<*>) {
        if (fragment.view == null) {
            throw IllegalStateException(
                """
            Property (${property.name}) was accessed when Fragment's ($fragment) view is null.
            Property was accessed when:
                - view was not created yet
                - view was already destroyed
                - this Fragment does not have a view
                """.trimIndent()
            )
        }
    }

    private fun valueNotPresentException(fragment: Fragment, property: KProperty<*>) =
        IllegalStateException(
            """
            Property (${property.name}) in Fragment ($fragment) was null.
            Initialize the property within view lifecycle or provide a producer to delegate.
            """.trimIndent()
        )
}

fun <T : Any> Fragment.viewScoped(
    producer: (() -> T)? = null,
    onPreDestroy: (T?) -> Unit = {},
): ReadWriteProperty<Fragment, T> = ViewScopedDelegate(producer, onPreDestroy, this)

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline onPreDestroy: (T?) -> Unit = {},
): ReadOnlyProperty<Fragment, T> = viewScoped(
    producer = {
        T::class.java
            .getMethod("bind", View::class.java)
            .invoke(null, requireView())
            as T
    },
    onPreDestroy = onPreDestroy,
)
