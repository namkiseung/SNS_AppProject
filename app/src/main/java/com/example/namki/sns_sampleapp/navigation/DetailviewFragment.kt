package com.example.namki.sns_sampleapp.navigation

import android.os.Bundle
import android.support.constraint.R.id.parent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.namki.sns_sampleapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.squareup.okhttp.OkHttpClient
import kotlinx.android.synthetic.main.frament_detail.view.*

class DetailviewFragment : Fragment(){
    var user: FirebaseUser? = null
    var firestore: FirebaseFirestore? = null
    var imagesSnapshot: ListenerRegistration? = null
    var okHttpClient: OkHttpClient? = null
    //var fcmPush: FcmPush? = null
    var mainView: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        user = FirebaseAuth.getInstance().currentUser
        firestore = FirebaseFirestore.getInstance()
        okHttpClient = OkHttpClient()
        //fcmPush = FcmPush()

        //리사이클러 뷰와 어뎁터랑 연결
//        mainView = inflater.inflate(R.layout.frament_detail, container, false)
//        return mainView
        var view = LayoutInflater.from(inflater.context).inflate(R.layout.frament_detail,container,false)
        view.detailviewfragment_recyclerview.adapter = DetailRecyclerviewAdapter()
        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)
        return view
    }
    inner class DetailRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun getItemCount(): Int {
            return 3
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            //(holder as CustomViewHolder).itemView
        }

    }


}
