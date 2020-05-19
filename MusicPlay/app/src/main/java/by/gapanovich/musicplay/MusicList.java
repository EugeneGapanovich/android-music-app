package by.gapanovich.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    private static final String DEEZER_URL = "https://api.deezer.com/search/?q=";
    private static final String INDEX_URL = "&index=0";
    private static final String LIMIT_URL = "&limit=2";
    private static final String OUTPUT_URL = "&output=json";

    private static final ArrayList<ImageView> IMAGES_ARRAY = new ArrayList<>();
    private static final ArrayList<TextView> ARTISTS_ARRAY = new ArrayList<>();
    private static final ArrayList<TextView> SONGS_ARRAY = new ArrayList<>();
    ArrayList<MusicInfo> MUSICS_ARRAY;
    ArrayList<String> urls;

    private Button btnBack;
    private static RequestQueue mQueue;


    private ImageView vAlbum1;
    private ImageView vAlbum2;
//    private ImageView vAlbum3;
//    private ImageView vAlbum4;
//    private ImageView vAlbum5;

    private TextView vArtist1;
    private TextView vArtist2;
//    private TextView vArtist3;
//    private TextView vArtist4;
//    private TextView vArtist5;

    private TextView vSong1;
    private TextView vSong2;
//    private TextView vSong3;
//    private TextView vSong4;
//    private TextView vSong5;

    private Button bPlay1;
    private Button bPlay2;
//    private Button bPlay3;
//    private Button bPlay4;
//    private Button bPlay5;

    private static MediaPlayer mediaPlayer1;
    private static MediaPlayer mediaPlayer2;
//    private static MediaPlayer mediaPlayer3;
//    private static MediaPlayer mediaPlayer4;
//    private static MediaPlayer mediaPlayer5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        mQueue = Volley.newRequestQueue(this);


        btnBack = (Button)findViewById(R.id.button_back_to_search);

        vAlbum1 = (ImageView) findViewById(R.id.album_view1);
        vAlbum2 = (ImageView) findViewById(R.id.album_view2);
//        vAlbum3 = (ImageView) findViewById(R.id.album_view3);
//        vAlbum4 = (ImageView) findViewById(R.id.album_view4);
//        vAlbum5 = (ImageView) findViewById(R.id.album_view5);

        vArtist1 = (TextView)findViewById(R.id.artist_view1);
        vArtist2 = (TextView)findViewById(R.id.artist_view2);
//        vArtist3 = (TextView)findViewById(R.id.artist_view3);
//        vArtist4 = (TextView)findViewById(R.id.artist_view4);
//        vArtist5 = (TextView)findViewById(R.id.artist_view5);

        vSong1 = (TextView) findViewById(R.id.song_name_view1);
        vSong2 = (TextView) findViewById(R.id.song_name_view2);
//        vSong3 = (TextView) findViewById(R.id.song_name_view3);
//        vSong4 = (TextView) findViewById(R.id.song_name_view4);
//        vSong5 = (TextView) findViewById(R.id.song_name_view5);


        bPlay1 = (Button) findViewById(R.id.play_music1);
        bPlay2 = (Button) findViewById(R.id.play_music2);
//        bPlay3 = (Button) findViewById(R.id.play_music3);
//        bPlay4 = (Button) findViewById(R.id.play_music4);
//        bPlay5 = (Button) findViewById(R.id.play_music5);

        IMAGES_ARRAY.add(vAlbum1);
        IMAGES_ARRAY.add(vAlbum2);
//        IMAGES_ARRAY.add(vAlbum3);
//        IMAGES_ARRAY.add(vAlbum4);
//        IMAGES_ARRAY.add(vAlbum5);

        ARTISTS_ARRAY.add(vArtist1);
        ARTISTS_ARRAY.add(vArtist2);
//        ARTISTS_ARRAY.add(vArtist3);
//        ARTISTS_ARRAY.add(vArtist4);
//        ARTISTS_ARRAY.add(vArtist5);

        SONGS_ARRAY.add(vSong1);
        SONGS_ARRAY.add(vSong2);
//        SONGS_ARRAY.add(vSong3);
//        SONGS_ARRAY.add(vSong4);
//        SONGS_ARRAY.add(vSong5);


        urls = new ArrayList<>();
        MUSICS_ARRAY = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        String value = bundle.getString("message");
        final String[] array = new String[2];
        jsonParse(value);
        urls.add(MUSICS_ARRAY.get(0).getSongUrl());
        urls.add(MUSICS_ARRAY.get(1).getSongUrl());
        array[0] = MUSICS_ARRAY.get(0).getSongUrl();
        array[1] = MUSICS_ARRAY.get(1).getSongUrl();



        mediaPlayer1 = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
//        mediaPlayer3 = new MediaPlayer();
//        mediaPlayer4 = new MediaPlayer();
//        mediaPlayer5 = new MediaPlayer();



        prepareMediaPlayer(urls.get(0), urls.get(1));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSearchActivity = new Intent(".SearchActivity");
//                if(mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                }
                clearTextView(ARTISTS_ARRAY);
                clearTextView(SONGS_ARRAY);
                clearImageView(IMAGES_ARRAY);
                clearMusicArray(MUSICS_ARRAY);
                startActivity(backToSearchActivity);
            }
        });

        bPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer1.isPlaying()) {
                    mediaPlayer1.pause();
                    bPlay1.setText("PLAY");
                } else {
                    mediaPlayer1.start();
                    bPlay1.setText("PAUSE");
                }
            }
        });
        bPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer2.isPlaying()) {
                    mediaPlayer2.pause();
                    bPlay2.setText("PLAY");
                } else {
                    mediaPlayer2.start();
                    bPlay2.setText("PAUSE");
                }
            }
        });
    }



    public static void prepareMediaPlayer(String s1, String s2){
        try {
            mediaPlayer1.setDataSource(s1);
            mediaPlayer1.prepare();

            mediaPlayer2.setDataSource(s2);
            mediaPlayer2.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void jsonParse(String artistName){
        String url = DEEZER_URL + artistName + INDEX_URL + LIMIT_URL + OUTPUT_URL;
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
                                //Insert image
                                Picasso.with(getApplicationContext())
                                        .load(MUSICS_ARRAY.get(i).getAlbumImage())
                                        .error(R.mipmap.ic_launcher)
                                        .placeholder(R.mipmap.ic_launcher)
                                        .resize(170,170)
                                        .into(IMAGES_ARRAY.get(i));
                                //Insert artist name
                                ARTISTS_ARRAY.get(i).setText(MUSICS_ARRAY.get(i).getArtistName());
                                //Insert song name
                                SONGS_ARRAY.get(i).setText(MUSICS_ARRAY.get(i).getSongName());
                                //Insert url song
                                //ssilki.add(preview);
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
    private void clearTextView(ArrayList<TextView> tViewArray){
        tViewArray.clear();
    }
    private void clearImageView(ArrayList<ImageView> iViewArray){
        iViewArray.clear();
    }



}
