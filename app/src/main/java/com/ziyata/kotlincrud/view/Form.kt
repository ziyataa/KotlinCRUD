package com.ziyata.kotlincrud.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ziyata.kotlincrud.R
import com.ziyata.kotlincrud.database.UserRepository
import com.ziyata.kotlincrud.local.UserDataSource
import com.ziyata.kotlincrud.local.UserDatabase
import com.ziyata.kotlincrud.model.User
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.fragment_item.*

class Form : AppCompatActivity() {

    private var userRepository : UserRepository? = null
    private var compisiteDisposable : CompositeDisposable? = null
    private lateinit var ambil : String
    private lateinit var ambilnama : String

    private lateinit var  nama: String
    private var id : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        compisiteDisposable = CompositeDisposable()
        val userDatabase = UserDatabase.getInstance(this)
        userRepository = UserRepository.gerInstance(UserDataSource.getInstance(userDatabase.userDAO()))

        val tangkap = intent
        ambil = tangkap.getStringExtra("kirim_id")
        ambilnama = tangkap.getStringExtra("nama")

        showDataExist()

        btnEdit.setOnClickListener {
            updateUser(edtNama.text.toString(),ambil.toInt())
            finish()
        }

        btnDelete.setOnClickListener {
            delete(ambil.toInt())
            finish()
        }
    }

    private fun showDataExist() {
        txtNama.text = ambil
        edtNama.setText(ambilnama)
    }

    private fun delete(id : Int){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any>{
            e -> userRepository!!.delete(id)
            e.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( {
                    //Success

                },
                        {
                            throwable -> Toast.makeText(this, ""+throwable.message, Toast.LENGTH_SHORT).show()
                        },  {
                    Toast.makeText(this,"Data berhasil didelete", Toast.LENGTH_SHORT).show()
                })
        compisiteDisposable!!.addAll(disposable)
    }


    private fun updateUser(nama: String, id : Int){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any>{
            e -> userRepository!!.updateUser(nama, id)
            e.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( {
                    //Success

                },
                         {
                            throwable -> Toast.makeText(this, ""+throwable.message, Toast.LENGTH_SHORT).show()
                        },  {
                    Toast.makeText(this,"Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                })
        compisiteDisposable!!.addAll(disposable)
    }

    override fun onDestroy() {
        compisiteDisposable?.clear()
        super.onDestroy()
    }
}
