package atlas.com.trainerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import atlas.com.trainerapp.authentication.views.AuthActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent auth = new Intent(SplashScreen.this, AuthActivity.class);
        auth.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(auth);
        overridePendingTransition(0,0);
    }
}
