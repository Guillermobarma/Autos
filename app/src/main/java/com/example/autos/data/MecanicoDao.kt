package com.example.autos.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.example.autos.model.Mecanico


class MecanicoDao {

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
    fun saveMecanico(mecanico: Mecanico){
        //un documento de tipo: json (un DocumentReference es un enlace a un documento json en la nube)
        val documento: DocumentReference

        if(mecanico.id.isEmpty()){ //es un Mecanico nuevo--sin id
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            mecanico.id = documento.id //es como un DocumentReference.id
        } else{ // el Mecanico ya tiene id.... existe en la nube
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(mecanico.id)
        }

        //ahora se inserta o actualiza dependiendo como salió de arriba
        documento.set(mecanico)
            .addOnSuccessListener {
                Log.d("saveMecanico", "Mecanico agregado o modificado")
            }
            .addOnCanceledListener {
                Log.e("saveMecanico", "Mecanico  NO agregado o modificado")
            }
    }

    fun deleteMecanico(mecanico: Mecanico){
        //SE valida que el id Mecanico no está vacio... si es así
        if (mecanico.id.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(mecanico.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteMecanico", "Mecanico eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteMecanico", "Mecanico  NO eliminado")
                }
        }
    }


    fun getMecanicos() : MutableLiveData<List<Mecanico>> {
        //lista para registrar la info de los Mecanico tomados de la coleccion
        val listaMecanicos =MutableLiveData<List<Mecanico>>()

        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener{instatanea, error ->
                if (error!=null){ //hubo error en la recuperacion de Mecanicos del usuario
                    //este permite devolverse sin problema
                    return@addSnapshotListener
                }
                if (instatanea!=null){ //se logró encontrar la info y hay no vacia
                    //recorrer la instantanea, crea un objeto lugar de lo recuperado de la list
                    val lista = ArrayList<Mecanico>()
                    //recorreo la instantanea y convierto el json a Lugar
                    instatanea.documents.forEach{
                        val mecanico = it.toObject(Mecanico::class.java)
                        if (mecanico!=null){ //si se pudo convertir en lugar lo que venía dentro entonces..
                            lista.add(mecanico)
                        }
                    }
                    listaMecanicos.value = lista
                }
            }
        return listaMecanicos
    }
}