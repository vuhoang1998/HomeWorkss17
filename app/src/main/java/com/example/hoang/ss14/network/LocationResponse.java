package com.example.hoang.ss14.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hoang on 5/3/2018.
 */

@Root(name = "tracklist", strict = false)

public class LocationResponse {

    @Element(name = "track")
    public TrackXML track;

}
