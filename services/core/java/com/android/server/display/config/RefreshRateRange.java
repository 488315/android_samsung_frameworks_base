package com.android.server.display.config;

import org.xmlpull.v1.XmlPullParser;

import java.math.BigInteger;

import javax.xml.datatype.DatatypeConfigurationException;

public final class RefreshRateRange {
    public BigInteger maximum;
    public BigInteger minimum;

    public static RefreshRateRange read(XmlPullParser xmlPullParser) {
        int next;
        RefreshRateRange refreshRateRange = new RefreshRateRange();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("minimum")) {
                    refreshRateRange.minimum = new BigInteger(XmlParser.readText(xmlPullParser));
                } else if (name.equals("maximum")) {
                    refreshRateRange.maximum = new BigInteger(XmlParser.readText(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return refreshRateRange;
        }
        throw new DatatypeConfigurationException("RefreshRateRange is not closed");
    }
}
