package edu.mit.media.prg.ros_android_intents.broadcast_demo;

/**
 * Created by davidnunez on 2014-05-04
 */


import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class MainActivity extends ActionBarActivity {

    private final MyReceiver myReceiver = new MyReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        registerReceiver(myReceiver, new IntentFilter("edu.mit.media.prg.ros_android_intents.ros_to_intent"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickBtnBroadcastString(View unused) {
        doBroadcast(((EditText)findViewById(R.id.editTextString)).getText().toString());
    }

    public void onClickBtnBroadcastInt(View unused) {
        doBroadcast(((EditText)findViewById(R.id.editTextInt)).getText().toString());
    }

    private void doBroadcast(String data) {
        Intent intent = new Intent();
        if (((RadioButton)findViewById(R.id.radioIntentToRos)).isChecked()) {
            intent.setAction("edu.mit.media.prg.ros_android_intents.intent_to_ros");
        } else {
            intent.setAction("edu.mit.media.prg.ros_android_intents.ros_to_intent");
        }
        intent.putExtra("data", data);
        sendBroadcast(intent);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
