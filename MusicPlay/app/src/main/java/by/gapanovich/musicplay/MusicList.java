package by.gapanovich.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    private Button btnBack;

    private ImageView vAlbum1;
    private ImageView vAlbum2;
    private ImageView vAlbum3;
    private ImageView vAlbum4;
    private ImageView vAlbum5;

    private TextView vArtist1;
    private TextView vArtist2;
    private TextView vArtist3;
    private TextView vArtist4;
    private TextView vArtist5;

    private TextView vSong1;
    private TextView vSong2;
    private TextView vSong3;
    private TextView vSong4;
    private TextView vSong5;

    private Button bPlay1;
    private Button bPlay2;
    private Button bPlay3;
    private Button bPlay4;
    private Button bPlay5;


    private static final ArrayList<ImageView> IMAGES_ARRAY = new ArrayList<>();
    private static final ArrayList<TextView> ARTISTS_ARRAY = new ArrayList<>();
    private static final ArrayList<TextView> SONGS_ARRAY = new ArrayList<>();
    private static final ArrayList<Button> BUTTONS_ARRAY = new ArrayList<>();


    //private MediaPlayer mediaPlayer;
    //private String stream = "";
    private Boolean prepared = false;
    private boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        btnBack = (Button)findViewById(R.id.button_back_to_search);

        vAlbum1 = (ImageView) findViewById(R.id.album_view1);
        vAlbum2 = (ImageView) findViewById(R.id.album_view2);
        vAlbum3 = (ImageView) findViewById(R.id.album_view3);
        vAlbum4 = (ImageView) findViewById(R.id.album_view4);
        vAlbum5 = (ImageView) findViewById(R.id.album_view5);

        vArtist1 = (TextView)findViewById(R.id.artist_view1);
        vArtist2 = (TextView)findViewById(R.id.artist_view2);
        vArtist3 = (TextView)findViewById(R.id.artist_view3);
        vArtist4 = (TextView)findViewById(R.id.artist_view4);
        vArtist5 = (TextView)findViewById(R.id.artist_view5);

        vSong1 = (TextView) findViewById(R.id.song_name_view1);
        vSong2 = (TextView) findViewById(R.id.song_name_view2);
        vSong3 = (TextView) findViewById(R.id.song_name_view3);
        vSong4 = (TextView) findViewById(R.id.song_name_view4);
        vSong5 = (TextView) findViewById(R.id.song_name_view5);

        bPlay1 = (Button) findViewById(R.id.play_music1);
        bPlay2 = (Button) findViewById(R.id.play_music2);
        bPlay3 = (Button) findViewById(R.id.play_music3);
        bPlay4 = (Button) findViewById(R.id.play_music4);
        bPlay5 = (Button) findViewById(R.id.play_music5);


        Bundle bundleObject = getIntent().getExtras();
        ArrayList<MusicInfo> list = (ArrayList<MusicInfo>) bundleObject.getSerializable("sending_list");

        IMAGES_ARRAY.add(vAlbum1);
        IMAGES_ARRAY.add(vAlbum2);
        IMAGES_ARRAY.add(vAlbum3);
        IMAGES_ARRAY.add(vAlbum4);
        IMAGES_ARRAY.add(vAlbum5);

        ARTISTS_ARRAY.add(vArtist1);
        ARTISTS_ARRAY.add(vArtist2);
        ARTISTS_ARRAY.add(vArtist3);
        ARTISTS_ARRAY.add(vArtist4);
        ARTISTS_ARRAY.add(vArtist5);

        SONGS_ARRAY.add(vSong1);
        SONGS_ARRAY.add(vSong2);
        SONGS_ARRAY.add(vSong3);
        SONGS_ARRAY.add(vSong4);
        SONGS_ARRAY.add(vSong5);

        BUTTONS_ARRAY.add(bPlay1);
        BUTTONS_ARRAY.add(bPlay2);
        BUTTONS_ARRAY.add(bPlay3);
        BUTTONS_ARRAY.add(bPlay4);
        BUTTONS_ARRAY.add(bPlay5);

        insertValues(list);

        for (int i = 0; i < list.size(); i++) {
            //Insert picture
            Picasso.with(getApplicationContext())
                    .load(list.get(i).getAlbumImage())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(215,215)
                    .into(IMAGES_ARRAY.get(i));
        }

//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        new PlayerTask().execute(stream);
//       bPlay1.setEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSearchActivity = new Intent(".SearchActivity");
                clearTextView(ARTISTS_ARRAY);
                clearTextView(SONGS_ARRAY);
                clearImageView(IMAGES_ARRAY);
                startActivity(backToSearchActivity);
            }
        });

//        bPlay1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (started) {
//                    started = false;
//                    mediaPlayer.pause();
//                    bPlay1.setText("PLAY");
//                } else {
//                    started = true;
//                    mediaPlayer.start();
//                    bPlay1.setText("PAUSE");
//                }
//            }
//        });
    }

    private static void insertValues(ArrayList<MusicInfo> l){
        for (int i = 0; i < l.size(); i++) {
            //Insert picture
            Log.i("INSIDE CYCLY" + i,"TYTYTY");
//            Picasso.with(getApplicationContext())
//                    .load(l.get(i).getAlbumImage())
//                    .error(R.mipmap.ic_launcher)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .resize(215,215)
//                    .into(vAlblum1);
            //Insert artist name
            ARTISTS_ARRAY.get(i).setText(l.get(i).getArtistName());
            //Insert song name
            SONGS_ARRAY.get(i).setText(l.get(i).getSongName());
        }
    }

//    private static void jsonParse(String artistName){
//        String url = DEEZER_URL + artistName + INDEX_URL + LIMIT_URL + OUTPUT_URL;
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for (int i = 0; i < jsonArray.length(); i++){
//                                JSONObject data = jsonArray.getJSONObject(i);
//                                String title_short = data.getString("title_short");
//                                String artist = data.getJSONObject("artist").getString("name");
//                                String preview = data.getString("preview");
//                                String imageUrl =  data.getJSONObject("album").getString("cover_small");
//
//                                MusicInfo music = new MusicInfo(imageUrl, artist, title_short, preview);
//                                MUSICS_ARRAY.add(music);
//                                //Insert artist name
//                                ARTISTS_ARRAY.get(i).setText(MUSICS_ARRAY.get(i).getArtistName());
//                                //Insert song name
//                                SONGS_ARRAY.get(i).setText(MUSICS_ARRAY.get(i).getSongName());
//                                //Insert url song
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        mQueue.add(request);
//    }

    private void clearMusicArray(ArrayList<MusicInfo> musicArray){
        musicArray.clear();
    }
    private void clearTextView(ArrayList<TextView> tViewArray){
        tViewArray.clear();
    }
    private void clearImageView(ArrayList<ImageView> iViewArray){
        iViewArray.clear();
    }


//    public void insertValue(ArrayList<MusicInfo> musicList) {
//        for (int i = 0; i < musicList.size(); i++) {
//            //Insert picture
//            Picasso.with(getApplicationContext())
//                    .load(musicList.get(i).getAlbumImage())
//                    .error(R.mipmap.ic_launcher)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .resize(215,215)
//                    .into(IMAGES_ARRAY.get(i));
//            //Insert artist name
//            ARTISTS_ARRAY.get(i).setText(musicList.get(i).getArtistName());
//            //Insert song name
//            SONGS_ARRAY.get(i).setText(musicList.get(i).getSongName());
//        }
//    }

//    private class PlayerTask extends AsyncTask<String, Void, Boolean>{
//        @Override
//        protected Boolean doInBackground(String... strings){
//            try {
//                mediaPlayer.setDataSource(strings[0]);
//                mediaPlayer.prepare();
//                prepared = true;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return prepared;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean){
//            super.onPostExecute(aBoolean);
//            bPlay1.setEnabled(true);
//            bPlay1.setText("PLAY");
//        }
//    }

//    @Override
//    protected void onPause(){
//        super.onPause();
//        if(started){
//            mediaPlayer.pause();
//        }
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        if(started){
//            mediaPlayer.start();
//        }
//    }
//
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        if(prepared){
//            mediaPlayer.release();
//        }
//    }
}
