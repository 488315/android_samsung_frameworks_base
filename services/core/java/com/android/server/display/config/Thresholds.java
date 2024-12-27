package com.android.server.display.config;

import org.xmlpull.v1.XmlPullParser;

import javax.xml.datatype.DatatypeConfigurationException;

public final class Thresholds {
    public BrightnessThresholds brighteningThresholds;
    public BrightnessThresholds darkeningThresholds;

    public static Thresholds read(XmlPullParser xmlPullParser) {
        int next;
        Thresholds thresholds = new Thresholds();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("brighteningThresholds")) {
                    thresholds.brighteningThresholds = BrightnessThresholds.read(xmlPullParser);
                } else if (name.equals("darkeningThresholds")) {
                    thresholds.darkeningThresholds = BrightnessThresholds.read(xmlPullParser);
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return thresholds;
        }
        throw new DatatypeConfigurationException("Thresholds is not closed");
    }
}
