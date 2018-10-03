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
import com.bumptech.glide.Glide
import com.example.namki.sns_sampleapp.R
import com.example.namki.sns_sampleapp.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.squareup.okhttp.OkHttpClient
import kotlinx.android.synthetic.main.frament_detail.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailviewFragment : Fragment() {
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
        var view = LayoutInflater.from(inflater.context).inflate(R.layout.frament_detail, container, false)
        view.detailviewfragment_recyclerview.adapter = DetailRecyclerviewAdapter()
        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)
        return view
    }

    inner class DetailRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        val contentDTOs: ArrayList<ContentDTO>?  //image디렉터리의 문서
        val contentUidList: ArrayList<String>?  // image디렉터리 안의 필드부분 데이터

        init { //파베에서 image폴더 접근 로직
            contentDTOs = ArrayList()
            contentUidList = ArrayList()
            var uid = FirebaseAuth.getInstance().currentUser?.uid  //현재 로그인 유저의 UID
            firestore?.collection("images")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                contentDTOs.clear()
                contentUidList.clear()
                for (snapshot in querySnapshot!!.documents) {  //DB호출시마다 반복되어 데이터 connect
                    var item = snapshot.toObject(ContentDTO::class.java)//반복시 마다 객체를 만들자(형태를 맞추기위해)
                    contentDTOs.add(item)
                    contentUidList.add(snapshot.id)
                }
                notifyDataSetChanged() //적용
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return CustomViewHolder(view)
        }

        private inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)  //Gird랑 접근제어 위해 private추가

        override fun getItemCount(): Int {
            return contentDTOs!!.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            //(holder as CustomViewHolder).itemView
            val viewHolder = (holder as CustomViewHolder).itemView
            viewHolder.detailviewitem_profile_textview.text = contentDTOs!![position].userId  //유저 아이디
            viewHolder.detailviewitem_explain_textview.text = contentDTOs!![position].userId  //설명 텍스트

            //이미지
            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUrl).into(viewHolder.detailviewitem_imageview_content)
            viewHolder.detailviewitem_favoritecounter_textview.text = "좋아요 " + contentDTOs!![position].favoriteCount + "개"  //좋아요 카운터 설정
            var uid = FirebaseAuth.getInstance().currentUser!!.uid
            viewHolder.detailviewitem_favorite_imageview.setOnClickListener { favoriteEvent(position) }
            if (contentDTOs!![position].favorites.containsKey(uid)) { //좋아요 클릭시
                viewHolder.detailviewitem_favorite_imageview.setImageResource(R.drawable.ic_favorite)
            } else {
                viewHolder.detailviewitem_favorite_imageview.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        private fun favoriteEvent(position: Int) {
            var tsDoc = firestore?.collection("images")?.document(contentUidList!![position])//도큐먼트는 문서 이름 선택 그래야 그 문서 안 좋아요 카운터 접근
            firestore?.runTransaction { transaction ->
                var uid = FirebaseAuth.getInstance().currentUser!!.uid
                var contentDTO = transaction.get(tsDoc!!).toObject(ContentDTO::class.java)

                if (contentDTO!!.favorites.containsKey(uid)) {  //favorites에 해시값있니?(좋아요 눌런단 얘기)
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount - 1
                    contentDTO?.favorites.remove(uid)
                } else {//좋아요 안눌렀을때
                    contentDTO.favorites!![uid] = true
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount + 1
                }
                transaction.set(tsDoc, contentDTO)  //사용 후 돌려놓자
            }
        }
    }
}
