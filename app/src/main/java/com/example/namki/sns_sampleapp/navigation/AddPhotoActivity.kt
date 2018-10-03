package com.example.namki.sns_sampleapp.navigation

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.namki.sns_sampleapp.R
import com.example.namki.sns_sampleapp.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.util.*

class AddPhotoActivity : AppCompatActivity() {
    val PICK_IMAGE_FROM_ALBUM = 0
    var photoUri: Uri? = null
    var storage: FirebaseStorage? = null
    var firestore: FirebaseFirestore? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        storage = FirebaseStorage.getInstance() // Firebase storage
        auth = FirebaseAuth.getInstance() // Firebase Auth
        firestore = FirebaseFirestore.getInstance()

        val photoPickerIntent = Intent(Intent.ACTION_PICK)  //앨범열어서 사진 찍는 코드
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        addphoto_image.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }
        addphoto_btn_upload.setOnClickListener {
            contentUpload()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //결과값을 받아오는곳
        if (requestCode == PICK_IMAGE_FROM_ALBUM) { //이미지 선택시
            if(resultCode == Activity.RESULT_OK){
                //이미지뷰에 이미지 세팅
                println(data?.data)
                photoUri = data?.data
                addphoto_image.setImageURI(data?.data)
            }
            else{ //사진 선택안했을 때
                finish()
            }
        }
    }

    fun contentUpload(){
        //progress_bar.visibility = View.VISIBLE
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)
        storageRef?.putFile(photoUri!!)?.addOnSuccessListener { taskSnapshot ->
            //파베 스토리지와 연결부분
            progress_bar.visibility = View.GONE

            Toast.makeText(this, getString(R.string.upload_success),
                    Toast.LENGTH_SHORT).show()

            val uri = taskSnapshot.downloadUrl //업로드된 이미지 주소
            //디비에 바인딩 할 위치 생성 및 컬렉션(테이블)에 데이터 집합 생성

            //시간 생성
            val contentDTO = ContentDTO()

            contentDTO.imageUrl = uri!!.toString() //이미지 주소
            contentDTO.uid = auth?.currentUser?.uid  //유저의 UID
            contentDTO.explain = addphoto_edit_explain.text.toString() //게시물의 설명
            contentDTO.userId = auth?.currentUser?.email //유저의 아이디
            contentDTO.timestamp = System.currentTimeMillis() //게시물 업로드 시간

            firestore?.collection("images")?.document()?.set(contentDTO)?.addOnCompleteListener{ //게시물을 데이터를 생성 및 엑티비티 종료
            task->
                println("파일업로드 성공일때 : "+task.isSuccessful)
                println("파일업로드 에러일때 : "+task.exception?.message.toString())
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
                ?.addOnFailureListener {
                    progress_bar.visibility = View.GONE

                    Toast.makeText(this, getString(R.string.upload_fail),
                            Toast.LENGTH_SHORT).show()
                }
    }


}