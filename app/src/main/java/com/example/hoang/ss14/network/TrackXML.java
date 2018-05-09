package com.example.hoang.ss14.network;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by hoang on 5/3/2018.
 */

@Root(name = "track",strict = false)
public class TrackXML {
    @Element(name = "location")
    public String location;
}
