package fr.epita.mti.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val originIntent = intent
        val game_id = originIntent.getIntExtra("game_id", -1)

        val data = arrayListOf<GameInfo>()

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback: Callback<GameDetails> = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<GameDetails>,
                response: Response<GameDetails>
            ) {

                if (response.code() == 200) {

                    val game: GameDetails = response?.body()!!;

                    Glide
                        .with(applicationContext)
                        .load(game.picture)
                        .into(imageView)

                    name_value.text = game.name
                    type_value.text = game.type
                    year_value.text = game.year.toString()
                    nb_player_value.text = game.players.toString()
                    description_value.text = game.description_en

                    btn_more.setOnClickListener {
                        val implicitIntent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(game.url)
                        startActivity(implicitIntent)
                    }
                }
            }
        }

        service.details(game_id).enqueue(callback);
    }
}
