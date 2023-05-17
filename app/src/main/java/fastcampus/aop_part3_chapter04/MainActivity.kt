package fastcampus.aop_part3_chapter04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.aop_part3_chapter04.adapter.BookAdapter
import fastcampus.aop_part3_chapter04.api.BookAPI
import fastcampus.aop_part3_chapter04.databinding.ActivityMainBinding
import fastcampus.aop_part3_chapter04.model.SearchBooksDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : BookAdapter
    private lateinit var bookService: BookAPI


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)



        setContentView(binding.root)
        initBookRecyclerView()



        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookAPI::class.java)

        bookService.getBooksByName("EE29yHWSCmCGhREiVgco",getString(R.string.interParkAPIKey),
        "toeic")
            .enqueue(object : Callback<SearchBooksDto>{
                override fun onResponse(
                    call: Call<SearchBooksDto>,
                    response: Response<SearchBooksDto>
                ) {
                    if(response.isSuccessful.not()){
                        return
                    }

                    response.body()?.let{
                        Log.d(TAG,it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG,book.toString())
                        }
                        adapter.submitList(it.books) // submitList : 리스트가 이것으로 체인지됨
                        // BookAdapter 안에서 currentList가 booklist로 대체된다는 뜻

                    }
                }
                override fun onFailure(call: Call<SearchBooksDto>, t: Throwable) {

                    Log.e(TAG,t.toString()) // 실패시 t로 들어옴
                }
            })


        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }


    }

    private fun search(keyword:String){
        // 이 함수는 API를 찔러야 함, 즉 bookService
        bookService.getBooksByName("EE29yHWSCmCGhREiVgco",getString(R.string.interParkAPIKey), keyword)


    }


    fun initBookRecyclerView(){
        adapter = BookAdapter()
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}