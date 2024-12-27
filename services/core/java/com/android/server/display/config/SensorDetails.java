package com.android.server.display.config;

import org.xmlpull.v1.XmlPullParser;

import javax.xml.datatype.DatatypeConfigurationException;

public final class SensorDetails {
    public String featureFlag;
    public String name;
    public RefreshRateRange refreshRate;
    public NonNegativeFloatToFloatMap supportedModes;
    public String type;

    public static SensorDetails read(XmlPullParser xmlPullParser) {
        int next;
        SensorDetails sensorDetails = new SensorDetails();
        String attributeValue = xmlPullParser.getAttributeValue(null, "featureFlag");
        if (attributeValue != null) {
            sensorDetails.featureFlag = attributeValue;
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("type")) {
                    sensorDetails.type = XmlParser.readText(xmlPullParser);
                } else if (name.equals("name")) {
                    sensorDetails.name = XmlParser.readText(xmlPullParser);
                } else if (name.equals("refreshRate")) {
                    sensorDetails.refreshRate = RefreshRateRange.read(xmlPullParser);
                } else if (name.equals("supportedModes")) {
                    sensorDetails.supportedModes = NonNegativeFloatToFloatMap.read(xmlPullParser);
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return sensorDetails;
        }
        throw new DatatypeConfigurationException("SensorDetails is not closed");
    }
}
