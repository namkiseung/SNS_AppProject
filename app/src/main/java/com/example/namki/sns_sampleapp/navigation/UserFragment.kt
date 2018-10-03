package com.example.namki.sns_sampleapp.navigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.namki.sns_sampleapp.R
import com.example.namki.sns_sampleapp.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.frament_user.*
import kotlinx.android.synthetic.main.frament_user.view.*

class UserFragment : Fragment() {
    val PICK_PROFILE_FROM_ALBUM = 10
    // Firebase
    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null
    //private String destinationUid;
    var uid: String? = null
    var currentUserUid: String? = null
    var fragmentView: View? = null
    //var fcmPush: FcmPush? = null
    var followListenerRegistration: ListenerRegistration? = null
    var followingListenerRegistration: ListenerRegistration? = null
    var imageprofileListenerRegistration: ListenerRegistration? = null
    var recyclerListenerRegistration: ListenerRegistration? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid
        //fcmPush = FcmPush()
        fragmentView = inflater.inflate(R.layout.frament_user, container, false)
        fragmentView?.account_iv_profile?.setOnClickListener {
            var photoPcikerIntent = Intent(Intent.ACTION_PICK)
            photoPcikerIntent.type = "images/*"
            activity?.startActivityForResult(photoPcikerIntent, PICK_PROFILE_FROM_ALBUM)
        }
        fragmentView?.account_recyclerview?.adapter = UserFragmentRecyclerViewAdapter()
        fragmentView?.account_recyclerview?.layoutManager = GridLayoutManager(activity!!, 3)
        getProfileImages()
        return fragmentView
    }
    fun getProfileImages() {
        imageprofileListenerRegistration = firestore?.collection("profileImages")?.document(uid!!)   //모두에게 공개말고 uri 주소로 접근
                ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    //스냅샷은 이벤트 발생하는 순간의 이벤트 처리위해 설정
                    if (documentSnapshot?.data != null) {
                        val url = documentSnapshot?.data!!["image"]
                        Glide.with(activity)
                                .load(url)
                                .apply(RequestOptions().circleCrop()).into(fragmentView!!.account_iv_profile)  //사진 프레임 circleCrop()
                    }
                }
    }

    inner class UserFragmentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        val contentDTOs: ArrayList<ContentDTO>
        init {
            contentDTOs = ArrayList()
            // 나의 사진만 찾기
            recyclerListenerRegistration = firestore?.collection("images")?.whereEqualTo("uid", uid)?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                contentDTOs.clear()
                if (querySnapshot == null) return@addSnapshotListener
                for (snapshot in querySnapshot?.documents!!) {
                    contentDTOs.add(snapshot.toObject(ContentDTO::class.java)!!)
                }
                account_tv_post_count.text = contentDTOs.size.toString()
                notifyDataSetChanged()
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val width = resources.displayMetrics.widthPixels / 3
            val imageView = ImageView(parent.context)
            imageView.layoutParams = LinearLayoutCompat.LayoutParams(width, width)
            return CustomViewHolder(imageView)
        }
        inner class CustomViewHolder(var imageview: ImageView) : RecyclerView.ViewHolder(imageview) {

        }
        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var imageview = (holder as CustomViewHolder).imageview
            Glide.with(holder.itemView.context)
                    .load(contentDTOs[position].imageUrl)
                    .apply(RequestOptions().centerCrop())
                    .into(imageview)

        }

    }
    override fun onResume() {
        super.onResume()
        getProfileImages()
    }
}