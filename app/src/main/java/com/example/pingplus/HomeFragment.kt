package com.example.pingplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val createButton : Button = view.findViewById<Button>(R.id.create)
        createButton.setOnClickListener{
            view.findNavController().navigate(R.id.tocreate)
        }
        val findButton : Button = view.findViewById<Button>(R.id.find)
        findButton.setOnClickListener{
            view.findNavController().navigate(R.id.tofind)
        }
        return view
    }
    override fun onViewCreated(view:View,bundle: Bundle?) {
        super.onViewCreated(view,bundle)
    }
}