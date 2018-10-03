package com.example.namki.sns_sampleapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.namki.sns_sampleapp.navigation.AddPhotoActivity
import com.example.namki.sns_sampleapp.navigation.DetailviewFragment
import com.example.namki.sns_sampleapp.navigation.GridFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    var PICK_PROFILE_FROM_ALBUM = 10
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.action_home->{
                var detailviewFragment = DetailviewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, detailviewFragment).commit()
                return true
            }
            R.id.action_search->{
                var gridFragment = GridFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, gridFragment).commit()
                return true
            }
            R.id.action_add_photo->{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, AddPhotoActivity::class.java))
                }else{
                    Toast.makeText(this, "Namki App에 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.action_favorite_alarm->{
                var alertFragment = GridFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, alertFragment).commit()
                return true
            }
            R.id.action_account->{
                var userFragment = GridFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userFragment).commit()
                return true
            }
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.selectedItemId = R.id.action_home //앱실행시마다 홈 보이게

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }
    fun setToolbarDefault() {
        //toolbar_title_image.visibility = View.VISIBLE
        toolbar_btn_back.visibility = View.GONE
        toolbar_username.visibility = View.GONE
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
            var imageUri = data?.data
            var uid = FirebaseAuth.getInstance().currentUser!!.uid
            FirebaseStorage.getInstance().reference.child("userProfileImages").child(uid).putFile(imageUri!!).addOnCompleteListener{
                task ->
                var url = task.result.downloadUrl.toString()
                var map = HashMap<String,Any>()
                map["image"]=url
                FirebaseFirestore.getInstance().collection("profileImages").document(uid).set(map)
            }
        }
    }
}
