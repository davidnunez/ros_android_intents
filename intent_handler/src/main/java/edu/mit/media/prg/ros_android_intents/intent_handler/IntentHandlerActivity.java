package edu.mit.media.prg.ros_android_intents.intent_handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IntentHandlerActivity extends RosActivity {
    private static final String TAG = "IntentHandlerActivity" ;

    private IntentNode intentNode;
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        intentNode.publish("intent_to_ros", intent.getStringExtra("data"));
        }
    };

    public IntentHandlerActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("IntentHandler", "IntentHandler");
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(myReceiver);
    }


    public void sendMessageButtonClicked(View unused) {
        intentNode.publish("intent_to_ros","Send Message Button Clicked!");

    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        Log.i(TAG, "init()");
        JSONParser parser=new JSONParser();

        InputStream inputStream = getResources().openRawResource(R.raw.connections);
        String json = getStringFromInputStream(inputStream);

        intentNode = new IntentNode();
        intentNode.setContext(getApplicationContext());
        intentNode.setGraphName(getString(R.string.graphName));
        try {
            JSONObject obj = (JSONObject) parser.parse(json);

            JSONArray array = (JSONArray) obj.get("connections");

            for (int i = 0; i < array.size(); i++) {

                JSONObject j = (JSONObject) array.get(i);
                intentNode.addConnection(RosIntentConnection.fromJSONString(j.toJSONString()));
            }
        } catch (Exception e) {
            Log.e("IntentHandlerActivity", e.toString());

        }

        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress().toString(), getMasterUri());
        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(intentNode, nodeConfiguration);
        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        //nodeMainExecutor.execute(rosTextView, nodeConfiguration);
    }
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
