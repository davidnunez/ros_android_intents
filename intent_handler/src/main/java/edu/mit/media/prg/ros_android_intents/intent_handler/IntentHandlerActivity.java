package edu.mit.media.prg.ros_android_intents.intent_handler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.message.MessageListener;
import org.ros.node.DefaultNodeFactory;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.topic.Subscriber;

public class IntentHandlerActivity extends RosActivity
{
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            talker.publish(intent.getStringExtra("data"));
        }
    };

    @Override
    public void onDestroy() {

        unregisterReceiver(myReceiver);

    }


    private Talker talker;


    public IntentHandlerActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        super("IntentHandler", "IntentHandler");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        registerReceiver(myReceiver, new IntentFilter("edu.mit.media.prg.ros_android_intents.intent_to_ros"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void sendMessageButtonClicked(View unused) {
        talker.publish("Send Message Button Clicked!");

    }
    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        talker = new Talker();
        talker.setGraphName("intent_handler/1");
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress().toString(), getMasterUri());
        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(talker, nodeConfiguration);
        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        //nodeMainExecutor.execute(rosTextView, nodeConfiguration);
    }

}
