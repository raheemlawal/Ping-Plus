package com.example.pingplus

//import java.lang.String
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.card_view.view.*


var database: DatabaseReference = FirebaseDatabase.getInstance().reference

class FindFragment : Fragment() {

    private lateinit var model: ViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var database: DatabaseReference

    var gs = Any()
    var games = arrayListOf<GameItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var ref = FirebaseDatabase.getInstance().getReference("games")

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                gs = dataSnapshot.value!!
                var z = gs as HashMap<Any,Any>
                for ((k, v) in z) {
                    Log.d("Here",v.toString())
                    var x : HashMap<String,Any> = v as HashMap<String, Any>
                    var y = GameItem(
                        x["gamename"].toString(),
                        x["creatorname"].toString(),
                        x["addy"].toString(),
                        x["time"].toString(),
                        x["pcount"].toString().toInt(),
                        x["pmax"].toString()
                    )
                    model.gameList.add(y)
                    viewAdapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        }
        model = activity?.run {
            ViewModelProviders.of(this).get(ViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        games = model.gameList

        viewManager = LinearLayoutManager(activity)
        viewAdapter = MyAdapter(games,requireActivity())
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var view = inflater.inflate(R.layout.fragment_find, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.game_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return view
    }
    override fun onViewCreated(view:View,bundle: Bundle?) {
        super.onViewCreated(view,bundle)
    }
}
class MyAdapter(private val myDataset: ArrayList<GameItem>, var context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val textView: View) : RecyclerView.ViewHolder(textView)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false) as CardView
        return MyViewHolder(textView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.textView.addy.text = myDataset[position].addy
        holder.textView.name.text = myDataset[position].gamename
        holder.textView.time.text = myDataset[position].time
        holder.textView.pLabel.text = "Players : " + myDataset[position].pCount + "/" + myDataset[position].pMax
        holder.textView.button.setOnClickListener{
            if(myDataset[position].pCount < myDataset[position].pMax.toInt())
            {
                myDataset[position].pCount += 1
                database.child("games").child(myDataset[position].gamename).child("pcount").setValue(myDataset[position].pCount)
                holder.textView.pLabel.text = "Players : " + myDataset[position].pCount + "/" + myDataset[position].pMax
            }
        }
        holder.itemView.setOnClickListener {
            val locData = Bundle()
            locData.putString("R",myDataset[position].addy)
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtras(locData)
            context.startActivity(intent)
        }
    }
    override fun getItemCount() = myDataset.size
}