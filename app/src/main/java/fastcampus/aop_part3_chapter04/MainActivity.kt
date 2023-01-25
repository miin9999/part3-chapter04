package fastcampus.aop_part3_chapter04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fastcampus.aop_part3_chapter04.api.BookAPI
import fastcampus.aop_part3_chapter04.model.SearchBooksDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookAPI::class.java)

        bookService.getBooksByName("EE29yHWSCmCGhREiVgco","DH6VFtsHdD",
        "ddd")
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
                    }
                }

                override fun onFailure(call: Call<SearchBooksDto>, t: Throwable) {

                    Log.e(TAG,t.toString()) // 실패시 t로 들어옴
                }

            })


    }


    companion object {
        private const val TAG = "MainActivity"
    }
}