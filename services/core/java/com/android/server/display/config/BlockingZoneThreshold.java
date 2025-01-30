package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class BlockingZoneThreshold {
    public List displayBrightnessPoint;

    public final List getDisplayBrightnessPoint() {
        if (this.displayBrightnessPoint == null) {
            this.displayBrightnessPoint = new ArrayList();
        }
        return this.displayBrightnessPoint;
    }

    public static BlockingZoneThreshold read(XmlPullParser xmlPullParser) {
        int next;
        BlockingZoneThreshold blockingZoneThreshold = new BlockingZoneThreshold();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("displayBrightnessPoint")) {
                    blockingZoneThreshold.getDisplayBrightnessPoint().add(DisplayBrightnessPoint.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return blockingZoneThreshold;
        }
        throw new DatatypeConfigurationException("BlockingZoneThreshold is not closed");
    }
}
