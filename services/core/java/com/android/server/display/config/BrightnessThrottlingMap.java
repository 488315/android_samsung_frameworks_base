package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class BrightnessThrottlingMap {
    public List brightnessThrottlingPoint;

    /* renamed from: id */
    public String f1678id;

    public final List getBrightnessThrottlingPoint() {
        if (this.brightnessThrottlingPoint == null) {
            this.brightnessThrottlingPoint = new ArrayList();
        }
        return this.brightnessThrottlingPoint;
    }

    public String getId() {
        return this.f1678id;
    }

    public void setId(String str) {
        this.f1678id = str;
    }

    public static BrightnessThrottlingMap read(XmlPullParser xmlPullParser) {
        int next;
        BrightnessThrottlingMap brightnessThrottlingMap = new BrightnessThrottlingMap();
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            brightnessThrottlingMap.setId(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("brightnessThrottlingPoint")) {
                    brightnessThrottlingMap.getBrightnessThrottlingPoint().add(BrightnessThrottlingPoint.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return brightnessThrottlingMap;
        }
        throw new DatatypeConfigurationException("BrightnessThrottlingMap is not closed");
    }
}
