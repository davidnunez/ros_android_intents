package edu.mit.media.prg.ros_android_intents.intent_handler;

/**
 * Created by prg on 4/19/14.
 */
import android.content.Context;
import android.content.Intent;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

/**
 * A simple {@link Subscriber} {@link NodeMain}.
 *
 * @author damonkohler@google.com (Damon Kohler)
 */
public class Listener extends AbstractNodeMain {
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    private String graphName;

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }


    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of(graphName);
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber("ros_to_intent", std_msgs.String._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.String>() {
            @Override
            public void onNewMessage(std_msgs.String message) {
                log.info("I heard: \"" + message.getData() + "\"");
                Intent intent = new Intent();
                intent.setAction("edu.mit.media.prg.ros_android_intents.ros_to_intent");
                intent.putExtra("data", message.getData());
                context.sendBroadcast(intent);
            }
        });
    }
}