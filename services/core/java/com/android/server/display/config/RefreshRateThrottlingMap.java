package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class RefreshRateThrottlingMap {

    /* renamed from: id */
    public String f1679id;
    public List refreshRateThrottlingPoint;

    public final List getRefreshRateThrottlingPoint() {
        if (this.refreshRateThrottlingPoint == null) {
            this.refreshRateThrottlingPoint = new ArrayList();
        }
        return this.refreshRateThrottlingPoint;
    }

    public String getId() {
        return this.f1679id;
    }

    public void setId(String str) {
        this.f1679id = str;
    }

    public static RefreshRateThrottlingMap read(XmlPullParser xmlPullParser) {
        int next;
        RefreshRateThrottlingMap refreshRateThrottlingMap = new RefreshRateThrottlingMap();
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            refreshRateThrottlingMap.setId(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("refreshRateThrottlingPoint")) {
                    refreshRateThrottlingMap.getRefreshRateThrottlingPoint().add(RefreshRateThrottlingPoint.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return refreshRateThrottlingMap;
        }
        throw new DatatypeConfigurationException("RefreshRateThrottlingMap is not closed");
    }
}
