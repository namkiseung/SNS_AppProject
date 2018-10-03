package com.example.namki.sns_sampleapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.namki.sns_sampleapp.R.id.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import android.view.View
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    var callbackManager:CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener{//일반 로그인버튼
            createAndLoginEmail()
        }
        google_sign_in_button.setOnClickListener(){//구글 로그인 버튼
            googleLogin()
        }
        facebook_login_button.setOnClickListener(){//facebook 로그인 버튼
            facebookLogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso) //구글 로그인하는 클래스 생성됨
        printHashKey(this)
        callbackManager = CallbackManager.Factory.create() //not exist and Initialize Facebook Login btn
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(),0))
                Log.i("namki", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("namki", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("namki", "printHashKey()", e)
        }

    }

    fun createAndLoginEmail() {
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE
                    if (task.isSuccessful) {
                        //아이디 생성이 성공했을 경우
                        Toast.makeText(this,
                                getString(R.string.signup_complete), Toast.LENGTH_SHORT).show()
                        //다음페이지 호출
                        moveMainPage(auth?.currentUser)
                    } else if (task.exception?.message.isNullOrEmpty()) {
                        //회원가입 에러가 발생했을 경우
                        Toast.makeText(this,
                                task.exception!!.message, Toast.LENGTH_SHORT).show()
                    } else {
                        //아이디 생성도 안되고 에러도 발생되지 않았을 경우 로그인
                        signinEmail()
                    }
                }
    }

    //로그인 메소드
    fun signinEmail() {
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
                ?.addOnCompleteListener { task ->
                    //progress_bar.visibility = View.GONE
                    if (task.isSuccessful) {//로그인 성공 및 다음페이지 호출
                        moveMainPage(auth?.currentUser)  //세션가지고 있어서 넘긴다
                    } else { //로그인 실패
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
    }
    fun emailLogin() {

        if (email_edittext.text.toString().isNullOrEmpty() || password_edittext.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.signout_fail_null), Toast.LENGTH_SHORT).show()

        } else {

            progress_bar.visibility = View.VISIBLE
            createAndLoginEmail()

        }
    }
    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            Toast.makeText(this, getString(R.string.signin_complete), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()//액티비티넘기고 끝
        }
    }
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)
    }
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount){ //구글과 파이어베이스 다른플랫폼이라 구글정보를 파베로 전송
        var credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    progress_bar.visibility = View.GONE
                    if (task.isSuccessful) {


                        //다음페이지 호출
                        moveMainPage(auth?.currentUser)
                    }
                }
    }
    fun facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager
                .getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult>{ //object에 implements 정의하자 왜? <LoginResult>결과값처리
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("", "facebook:onSuccess:" + result)
                        handleFacebookAccessToken(result?.accessToken)
                    }
                    override fun onCancel() {
                    }
                    override fun onError(error: FacebookException?) {
                        Log.d("", "facebook:onError:" + error)
                    }
                })
    }
    fun handleFacebookAccessToken(token:AccessToken?){//파베 서버에 넘기자
        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)?.addOnCompleteListener{
            task ->
            println("task"+task.isSuccessful)
        }?.addOnFailureListener{
            exception ->
            println("exception"+exception.message)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //구글로그인 버튼 클릭~이후 넘어오는 값 처리(GOOGLE_LOGIN_CODE)
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)// Facebook SDK로 값 넘겨주기

        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data) //구글에서 data넘어오는것
            if(result.isSuccess){
                var account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            }
        }
    }

    override fun onResume() {  //자동로그인
        super.onResume()
        moveMainPage(auth?.currentUser)
    }
    override fun onStart() {
        super.onStart()
        //자동 로그인 설정
        moveMainPage(auth?.currentUser)
    }
}
