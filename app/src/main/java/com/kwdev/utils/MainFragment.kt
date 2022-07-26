package com.kwdev.utils

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kwdev.utils.databinding.FragmentMainBinding
import com.kwdev.viewscoped.viewBinding
import com.kwdev.viewscoped.viewScoped

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private val withProducer: Int by viewScoped(producer = { 0 })
    private var withManualInitialization: Int by viewScoped(onPreDestroy = { /* TODO */ })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withManualInitialization = withProducer
        binding.root.isVisible = false
    }
}
