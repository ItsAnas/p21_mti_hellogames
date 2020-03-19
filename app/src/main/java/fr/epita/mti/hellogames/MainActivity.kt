package fr.epita.mti.hellogames

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data : List<GameInfo>?

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback : Callback<List<GameInfo>> = object : Callback<List<GameInfo>> {
            override fun onFailure(call: Call<List<GameInfo>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<GameInfo>>, response: Response<List<GameInfo>>) {
                if (response.code() == 200)
                {
                    data = response?.body()?.toMutableList();

                    val game1: GameInfo = data?.random()!!
                    val game2: GameInfo = data?.random()!!
                    val game3: GameInfo = data?.random()!!
                    val game4: GameInfo = data?.random()!!


                    Glide
                        .with(applicationContext)
                        .load(game1.picture)
                        .into(game_1)
                    Glide
                        .with(applicationContext)
                        .load(game2.picture)
                        .into(game_2)
                    Glide
                        .with(applicationContext)
                        .load(game3.picture)
                        .into(game_3)
                    Glide
                        .with(applicationContext)
                        .load(game4.picture)
                        .into(game_4)

                    game_1.setOnClickListener {
                        val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
                        val game_id = game1.id
                        explicitIntent.putExtra("game_id", game_id)
                        startActivity(explicitIntent)
                    }

                    game_2.setOnClickListener {
                        val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
                        val game_id = game2.id
                        explicitIntent.putExtra("game_id", game_id)
                        startActivity(explicitIntent)
                    }

                    game_3.setOnClickListener {
                        val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
                        val game_id = game3.id
                        explicitIntent.putExtra("game_id", game_id)
                        startActivity(explicitIntent)
                    }

                    game_4.setOnClickListener {
                        val explicitIntent = Intent(applicationContext, SecondActivity::class.java)
                        val game_id = game4.id
                        explicitIntent.putExtra("game_id", game_id)
                        startActivity(explicitIntent)
                    }
                }
            }
        }

        service.listGame().enqueue(callback);
    }
}
