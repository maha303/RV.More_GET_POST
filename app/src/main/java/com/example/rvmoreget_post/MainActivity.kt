package com.example.rvmoreget_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var edName:EditText
    private lateinit var btnSave:Button
    private lateinit var btnGet:Button
    private lateinit var rvMain:RecyclerView

    private lateinit var items:ArrayList<UserItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items= arrayListOf()

        edName=findViewById(R.id.edName)
        btnSave=findViewById(R.id.btnSave)

        rvMain=findViewById(R.id.rvMain)

        rvMain.adapter=RVAdapter(items)
        rvMain.layoutManager=LinearLayoutManager(this)

        btnSave.setOnClickListener {

            var f=UserItem("KSA",edName.text.toString(),0)
            addUsers(f,onResult = {
                edName.setText("")
                Toast.makeText(this@MainActivity,"Saved &&", Toast.LENGTH_LONG).show()
            })
        }
        btnGet=findViewById(R.id.btnGet)
        btnGet.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.getUser()?.enqueue(object :Callback<List<UserItem>>{
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
              for (i in response.body()!!){
                  val lo=i.location
              val name = i.name
                  val pk=i.pk
             items.add(UserItem(lo,name,pk))
                      }
                    rvMain.adapter!!.notifyDataSetChanged()
              }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                 call.cancel()                }
            })


        }
    }
    private fun addUsers(f: UserItem,onResult:()->Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.addUser(f)?.enqueue(object: Callback<List<UserItem>>{
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                onResult()
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
            onResult()
            }
        })


    }
}