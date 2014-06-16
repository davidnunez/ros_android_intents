package edu.mit.media.prg.ros_android_intents.intent_handler;

/**
 * Created by davidnunez on 6/13/14.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import java.util.HashMap;
import java.util.Vector;


public class IntentNode extends AbstractNodeMain {
    private Context context;

    private String graphName;
    private Publisher<std_msgs.String> publisher;
    private HashMap<java.lang.String, Publisher> talkers;
    private HashMap<java.lang.String, RosIntentConnection> listeners;
    private Vector<RosIntentConnection> connections;
    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            publish("intent_to_ros", intent.getStringExtra("data"));
        }
    };
    public IntentNode() {
        //talkers = new HashMap<String, RosIntentConnection>();
        listeners = new HashMap<String, RosIntentConnection>();
        connections = new Vector<RosIntentConnection>();

        talkers = new HashMap<String, Publisher>();
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addConnection(RosIntentConnection connection) {
        connections.add(connection);
    }
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of(graphName);
    }

    public void publish(String topic, String message) {
        Publisher<std_msgs.String> publisher = (Publisher<std_msgs.String>)talkers.get(topic);
        std_msgs.String str = publisher.newMessage();
        str.setData(message);
        publisher.publish(str);
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        for (RosIntentConnection connection : connections) {
            android.util.Log.i("IntentNode", connection.getTopic());
            if (connection.getTalker().booleanValue()) {
                final Publisher talker = connectedNode.newPublisher(connection.getTopic(), std_msgs.String._TYPE);
                talkers.put(connection.getTopic(), talker);


                context.registerReceiver( myReceiver, new IntentFilter(connection.getAction()));

            }
            if (connection.getListener().booleanValue()) {
                listeners.put(connection.getTopic(), connection);
                final Log log = connectedNode.getLog();
                final String action = connection.getAction();
                Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber(connection.getTopic(), std_msgs.String._TYPE);
                subscriber.addMessageListener(new MessageListener<std_msgs.String>() {
                    @Override
                    public void onNewMessage(std_msgs.String message) {
                        log.info("I heard: \"" + message.getData() + "\"");
                        Intent intent = new Intent();
                        intent.setAction(action);
                        intent.putExtra("data", message.getData());
                        context.sendBroadcast(intent);
                    }
                });
                listeners.put(connection.getTopic(),connection);

            }
        }
    }
}
