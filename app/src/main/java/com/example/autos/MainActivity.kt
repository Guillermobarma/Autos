package com.example.autos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.autos.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //Objeto para tgener acceso a los controles creados en la vista...
    private lateinit var binding: ActivityMainBinding

    //Objeto para realizar la comunicación con FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //para hacer la autenticacion con google
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializo el objeto de autenticación, realmente Firebase
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btLogin.setOnClickListener { haceLogin() }
        binding.btRegister.setOnClickListener { haceRegistro() }

        //se establecen los paramentros de autenticacion de google
        //val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
          //  .requestIdToken(getString(R.string.default_web_client_id_r))
            //.requestEmail()
            //.build()

        //set fija la solicitud e cliente de google
        //googleSignInClient = GoogleSignIn.getClient(this,gso)
        //binding.btGoogle.setOnClickListener{ googleSignIn() }
    }

    private fun googleSignIn() {
        //este es el que hace el llamado a la autenticacion
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,5000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //esto es cuando se regresa de un activity que da un resultado, el de arriba con el 5000
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==5000){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val cuenta = task.getResult(ApiException::class.java)!!
                //hasta acá... todo fue en google... el !! significa que puede ser nulo por si se anula el proceso
                //ahora se hace la autenticacion e firebase.... registro
                firebaseAuthWithGoogle(cuenta.idToken!!)
            } catch ( e:ApiException){

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        //objeto para recuperar credenciales de la cuenta que se vino de google
        val credenciales = GoogleAuthProvider.getCredential(idToken,null)

        //se usan las credenciales para autenticar en firebase
        auth.signInWithCredential(credenciales)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user = auth.currentUser
                    refresca(user)
                } else{
                    refresca(null)
                }
            }
    }

    private fun haceRegistro() {
        val correo = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        auth.createUserWithEmailAndPassword(correo,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { //Si se hizo el registro
                    val user = auth.currentUser
                    refresca(user)
                } else {  //Si no se hizo el registro...
                    Toast.makeText(baseContext,getString(R.string.msg_fallo),Toast.LENGTH_LONG).show()
                    refresca(null)
                }
            }
    }

    private fun refresca(user: FirebaseUser?) {
        if (user!=null) {
            //ME paso a la pantalal principal...
            val intent= Intent(this,Principal::class.java)
            startActivity(intent)
        }
    }

    private fun haceLogin() {
        val correo = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(correo,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { //Si se hizo el registro
                    val user = auth.currentUser
                    refresca(user)
                } else {  //Si no se hizo el registro...
                    Toast.makeText(baseContext,getString(R.string.msg_fallo),Toast.LENGTH_LONG).show()
                    refresca(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        refresca(usuario)
    }
}