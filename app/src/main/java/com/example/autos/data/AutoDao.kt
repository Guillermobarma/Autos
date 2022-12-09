package com.example.autos.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.example.autos.model.Auto


class AutoDao {

    //definiendo la jerarquia donde se gestiona los lugares
    private val coleccion1 = "autosApp"
    //con el ? significa que puede no venir
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "misAutos"

    //conexion a la BD en la nube
    private var firestore: FirebaseFirestore =
        FirebaseFirestore.getInstance()

    init { //crea conexion estandar para poder conectarse
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    //CRUD Create Read Update Delete
    fun saveAuto(auto: Auto){
        //un documento de tipo: json (un DocumentReference es un enlace a un documento json en la nube)
        val documento: DocumentReference

        if(auto.id.isEmpty()){ //es un Auto nuevo--sin id
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            auto.id = documento.id //es como un DocumentReference.id
        } else{ // el Auto ya tiene id.... existe en la nube
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(auto.id)
        }

        //ahora se inserta o actualiza dependiendo como salió de arriba
        documento.set(auto)
            .addOnSuccessListener {
                Log.d("saveAuto", "Auto agregado o modificado")
            }
            .addOnCanceledListener {
                Log.e("saveAuto", "Auto  NO agregado o modificado")
            }
    }

    fun deleteAuto(auto: Auto){
        //SE valida que el id Auto no está vacio... si es así
        if (auto.id.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(auto.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteAuto", "Auto eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteAuto", "Lugar  NO eliminado")
                }
        }
    }


    fun getAutos() : MutableLiveData<List<Auto>> {
        //lista para registrar la info de los Auto tomados de la coleccion
        val listaAutos =MutableLiveData<List<Auto>>()

        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener{instatanea, error ->
                if (error!=null){ //hubo error en la recuperacion de Autos del usuario
                    //este permite devolverse sin problema
                    return@addSnapshotListener
                }
                if (instatanea!=null){ //se logró encontrar la info y hay no vacia
                    //recorrer la instantanea, crea un objeto lugar de lo recuperado de la list
                    val lista = ArrayList<Auto>()
                    //recorreo la instantanea y convierto el json a Lugar
                    instatanea.documents.forEach{
                        val auto = it.toObject(Auto::class.java)
                        if (auto!=null){ //si se pudo convertir en lugar lo que venía dentro entonces..
                            lista.add(auto)
                        }
                    }
                    listaAutos.value = lista
                }
            }
        return listaAutos
    }
}