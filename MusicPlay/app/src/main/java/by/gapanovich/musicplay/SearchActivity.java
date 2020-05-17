package by.gapanovich.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText enterArtist;
    private Button btnSearch;
    private Button btnSignOut;
    private RequestQueue mQueue;

    private static final String DEEZER_URL = "https://api.deezer.com/search/?q=";
    private static final String INDEX_URL = "&index=0";
    private static final String LIMIT_URL = "&limit=5";
    private static final String OUTPUT_URL = "&output=json";
    public static final ArrayList<MusicInfo> MUSICS_ARRAY = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        enterArtist = (EditText) findViewById(R.id.enter_artist);
        btnSearch = (Button) findViewById(R.id.button_search);
        btnSignOut = (Button) findViewById(R.id.button_signout);
        mQueue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonParse(enterArtist.getText().toString());
                Intent intentToMusicList = new Intent(SearchActivity.this, MusicList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sending_list", MUSICS_ARRAY);
                intentToMusicList.putExtras(bundle);
                startActivity(intentToMusicList);
                clearMusicArray(MUSICS_ARRAY);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signOut();
                Intent backToSMainActivity = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(backToSMainActivity);
            }
        });
    }

    private void jsonParse(String artistName){
        String url = DEEZER_URL + artistName + INDEX_URL + LIMIT_URL + OUTPUT_URL;
        ArrayList<MusicInfo> musicList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject data = jsonArray.getJSONObject(i);
                                String title_short = data.getString("title_short");
                                String artist = data.getJSONObject("artist").getString("name");
                                String preview = data.getString("preview");
                                String imageUrl =  data.getJSONObject("album").getString("cover_small");

                                MusicInfo music = new MusicInfo(imageUrl, artist, title_short, preview);
                                MUSICS_ARRAY.add(music);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void clearMusicArray(ArrayList<MusicInfo> musicArray){
        musicArray.clear();
    }

    private void signOut() {
        mAuth.signOut();
    }
}
