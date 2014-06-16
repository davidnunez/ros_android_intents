package edu.mit.media.prg.ros_android_intents.intent_handler;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by davidnunez on 6/13/14.
 */
public class RosIntentConnection {

    private String action;
    private String topic;
    private Boolean talker;
    private Boolean listener;
    private String type;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getTalker() {
        return talker;
    }

    public void setTalker(Boolean talker) {
        this.talker = talker;
    }

    public Boolean getListener() {
        return listener;
    }

    public void setListener(Boolean listener) {
        this.listener = listener;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static RosIntentConnection fromJSONString(String s) {
        JSONObject obj= (JSONObject) JSONValue.parse(s);
        return new RosIntentConnection((String)obj.get("action"), (String) obj.get("topic"), (Boolean) obj.get("talker"), (Boolean) obj.get("listener"), (String) obj.get("type"));

    }

    public RosIntentConnection(String action, String topic, Boolean talker, Boolean listener, String type) {
        this.action = action;
        this.topic = topic;
        this.talker = talker;
        this.listener = listener;
        this.type = type;
    }



}
