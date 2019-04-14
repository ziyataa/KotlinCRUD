package com.ziyata.kotlincrud.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ziyata.kotlincrud.R
import com.ziyata.kotlincrud.UserRecyclerViewAdapter
import com.ziyata.kotlincrud.database.UserRepository
import com.ziyata.kotlincrud.local.UserDataSource
import com.ziyata.kotlincrud.local.UserDatabase
import com.ziyata.kotlincrud.model.User
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var userRepository : UserRepository? = null
    private var compisiteDisposable : CompositeDisposable? = null

    lateinit var adapter: ArrayAdapter<*>

    var userList: MutableList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item_list)
        
        compisiteDisposable = CompositeDisposable()
        val userDatabase = UserDatabase.getInstance(this)
        userRepository = UserRepository.gerInstance(UserDataSource.getInstance(userDatabase.userDAO()))

        val adduser = User()
        adduser.nama = "LALALALA"
        insertUser(adduser)

        loadData()
    }
    
    private fun insertUser(user: User){
        val disposable = io.reactivex.Observable.create(ObservableOnSubscribe<Any>{
            e -> userRepository!!.insertUser(user)
            e.onComplete()
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer {
                    //Success

                },
                        Consumer {
                            throwable -> Toast.makeText(this, ""+throwable.message, Toast.LENGTH_SHORT).show()
                        }, Action {
                    Toast.makeText(this,"Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                })
        compisiteDisposable!!.addAll(disposable)
    }

    private fun onGetAllUserSuccess(users: List<User>){
        userList.clear()
        userList.addAll(users)
        adapter.notifyDataSetChanged()
    }

    private fun loadData(){
        val disposable = userRepository!!.allUsers
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({users-> showData(users)}){
                    throwable -> Toast.makeText(this,""+throwable.message, Toast.LENGTH_SHORT).show()
                }
        compisiteDisposable!!.add(disposable )
    }

    private fun showData(items: List<User>?){
        list.adapter = UserRecyclerViewAdapter(items, object : UserRecyclerViewAdapter.OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(item: User?) {
                val kirim = Intent(this@MainActivity, Form::class.java)
                kirim.putExtra("kirim_id", item?.id.toString())
                kirim.putExtra("nama", item?.nama)
                startActivity(kirim)
            }
        })
    }


    override fun onDestroy() {
        compisiteDisposable?.clear()
        super.onDestroy()
    }
}
