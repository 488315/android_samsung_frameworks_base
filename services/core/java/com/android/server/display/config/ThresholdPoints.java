package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class ThresholdPoints {
    public List brightnessThresholdPoint;

    public final List getBrightnessThresholdPoint() {
        if (this.brightnessThresholdPoint == null) {
            this.brightnessThresholdPoint = new ArrayList();
        }
        return this.brightnessThresholdPoint;
    }

    public static ThresholdPoints read(XmlPullParser xmlPullParser) {
        int next;
        ThresholdPoints thresholdPoints = new ThresholdPoints();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("brightnessThresholdPoint")) {
                    thresholdPoints.getBrightnessThresholdPoint().add(ThresholdPoint.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return thresholdPoints;
        }
        throw new DatatypeConfigurationException("ThresholdPoints is not closed");
    }
}
