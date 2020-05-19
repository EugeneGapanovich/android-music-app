package by.gapanovich.musicplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText enterArtist;
    private Button btnSearch;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        enterArtist = (EditText) findViewById(R.id.enter_artist);
        btnSearch = (Button) findViewById(R.id.button_search);
        btnSignOut = (Button) findViewById(R.id.button_signout);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMusicList = new Intent(SearchActivity.this, MusicList.class);
                String value = enterArtist.getText().toString();
                intentToMusicList.putExtra("message", value);
                startActivity(intentToMusicList);
            }
        });


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSMainActivity = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(backToSMainActivity);
            }
        });
    }
}
