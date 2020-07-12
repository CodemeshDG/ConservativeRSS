package com.dommyg.conservativerss.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Rss {

    @Element(name = "channel", required = false)
    private Channel channel;

    public Rss() {

    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
