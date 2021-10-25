package com.example.pingplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateFragment : Fragment() {

    private lateinit var model: ViewModel

    lateinit var creatorNameText : EditText
    lateinit var gameNameText : EditText
    lateinit var addressText : EditText
    lateinit var timeMinuteText : EditText
    lateinit var timeHourText : EditText
    lateinit var timeofday : String
    lateinit var seekP : SeekBar
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_create, container, false)

        model = activity?.run {
            ViewModelProviders.of(this).get(ViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        database = FirebaseDatabase.getInstance().reference

        var spinnerMain: Spinner = view.findViewById<Spinner>(R.id.spinner)

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.aorp,
                android.R.layout.simple_spinner_item
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerMain?.adapter = adapter
            }
        }
        spinnerMain.setSelection(0,false)
        spinnerMain?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                timeofday = spinnerMain?.selectedItem.toString()
            }
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        val createButton : Button = view.findViewById<Button>(R.id.create)
        createButton.setOnClickListener{

            creatorNameText = (view.findViewById(R.id.personName))
            var cn = creatorNameText.text.toString()

            gameNameText = view.findViewById(R.id.gameName) as EditText
            var gn = gameNameText.text.toString()

            addressText = view.findViewById(R.id.addy) as EditText
            var a = addressText.text.toString()

            timeHourText = view.findViewById(R.id.hour) as EditText
            var th = timeHourText.text.toString()

            timeMinuteText = view.findViewById(R.id.minute) as EditText
            var tm = timeMinuteText.text.toString()

            seekP = view.findViewById(R.id.seekP)


            seekP.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

            val g : GameItem = model.createGame(gn,cn,
                a,th + ":" + tm + timeofday,
                seekP.progress.toString())

            model.addGame(g)
            database.child("games").child(g.gamename).setValue(g)
            view.findNavController().navigate(R.id.tohome)
        }
        return view
    }
    override fun onViewCreated(view:View,bundle: Bundle?) {
        super.onViewCreated(view,bundle)
    }
}