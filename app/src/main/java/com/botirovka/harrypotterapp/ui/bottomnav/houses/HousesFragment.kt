package com.botirovka.harrypotterapp.ui.bottomnav.houses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.botirovka.harrypotterapp.R
import com.botirovka.harrypotterapp.databinding.FragmentHousesBinding


class HousesFragment : Fragment() {
    private lateinit var binding: FragmentHousesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHousesBinding.inflate(inflater, container, false)

        binding.buttonGryffindor

        binding.buttonSlytherin

        binding.buttonHufflepuff

        binding.buttonRavenclaw

        return binding.root
    }


}