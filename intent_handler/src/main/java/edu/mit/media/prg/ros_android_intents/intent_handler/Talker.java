package edu.mit.media.prg.ros_android_intents.intent_handler;

/**
 * Created by prg on 4/19/14.
 */
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

public class Talker extends AbstractNodeMain {

    private String graphName;
    private Publisher<std_msgs.String> publisher;
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

    public void publish(String message) {
        std_msgs.String str = publisher.newMessage();
        str.setData(message);
        publisher.publish(str);
    }


    @Override
    public void onStart(final ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher("intent_to_ros", std_msgs.String._TYPE);
    }
}
