package com.android.server.policy.devicestate.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes3.dex */
public class SensorCondition {
    public String name;
    public String type;
    public List value;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List getValue() {
        if (this.value == null) {
            this.value = new ArrayList();
        }
        return this.value;
    }

    public static SensorCondition read(XmlPullParser xmlPullParser) {
        int next;
        SensorCondition sensorCondition = new SensorCondition();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("type")) {
                    sensorCondition.setType(XmlParser.readText(xmlPullParser));
                } else if (name.equals("name")) {
                    sensorCondition.setName(XmlParser.readText(xmlPullParser));
                } else if (name.equals("value")) {
                    sensorCondition.getValue().add(NumericRange.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return sensorCondition;
        }
        throw new DatatypeConfigurationException("SensorCondition is not closed");
    }
}
