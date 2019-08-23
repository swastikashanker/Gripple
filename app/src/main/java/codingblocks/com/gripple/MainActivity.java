package codingblocks.com.gripple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btnRecord)
    Button btnRecord;
    @BindView(R.id.btnSettings)
    Button btnSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    //load values from shared prefs settings


        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        verifySettings();

    }

    private void verifySettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (TextUtils.isEmpty(prefs.getString(SettingsActivity.PREF_IPCAM_URL, ""))) {
            btnRecord.setEnabled(false);
        }

       else Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
    }




    @OnClick(R.id.btnRecord)
    public void onClickRecord(){
        startActivity(new Intent(this, RecordingActivity.class));
        Toast.makeText(this,"Recording",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnSettings)
    public void onClickSettings(){

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else {
            Toast.makeText(this, "Settings not available", Toast.LENGTH_SHORT).show();
        }
    }




}
