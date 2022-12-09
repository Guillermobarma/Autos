package com.example.autos.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.example.autos.model.Servicio


class ServicioDao {

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
    fun saveServicio(servicio: Servicio){
        //un documento de tipo: json (un DocumentReference es un enlace a un documento json en la nube)
        val documento: DocumentReference

        if(servicio.id.isEmpty()){ //es un Servicio nuevo--sin id
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document()
            servicio.id = documento.id //es como un DocumentReference.id
        } else{ // el Servicio ya tiene id.... existe en la nube
            documento = firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(servicio.id)
        }

        //ahora se inserta o actualiza dependiendo como salió de arriba
        documento.set(servicio)
            .addOnSuccessListener {
                Log.d("saveServicio", "Servicio agregado o modificado")
            }
            .addOnCanceledListener {
                Log.e("saveServicio", "Servicio  NO agregado o modificado")
            }
    }

    fun deleteServicio(servicio: Servicio){
        //SE valida que el id Servicio no está vacio... si es así
        if (servicio.id.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario)
                .collection(coleccion2)
                .document(servicio.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteServicio", "Servicio eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteServicio", "Lugar  NO eliminado")
                }
        }
    }


    fun getServicios() : MutableLiveData<List<Servicio>> {
        //lista para registrar la info de los Servicio tomados de la coleccion
        val listaServicios =MutableLiveData<List<Servicio>>()

        firestore
            .collection(coleccion1)
            .document(usuario)
            .collection(coleccion2)
            .addSnapshotListener{instatanea, error ->
                if (error!=null){ //hubo error en la recuperacion de Servicios del usuario
                    //este permite devolverse sin problema
                    return@addSnapshotListener
                }
                if (instatanea!=null){ //se logró encontrar la info y hay no vacia
                    //recorrer la instantanea, crea un objeto lugar de lo recuperado de la list
                    val lista = ArrayList<Servicio>()
                    //recorreo la instantanea y convierto el json a Lugar
                    instatanea.documents.forEach{
                        val servicio = it.toObject(Servicio::class.java)
                        if (servicio!=null){ //si se pudo convertir en lugar lo que venía dentro entonces..
                            lista.add(servicio)
                        }
                    }
                    listaServicios.value = lista
                }
            }
        return listaServicios
    }
}