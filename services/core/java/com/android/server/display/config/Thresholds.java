package com.android.server.display.config;

import org.xmlpull.v1.XmlPullParser;

import javax.xml.datatype.DatatypeConfigurationException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
