package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class DisplayQuirks {
    public List quirk;

    public List getQuirk() {
        if (this.quirk == null) {
            this.quirk = new ArrayList();
        }
        return this.quirk;
    }

    public static DisplayQuirks read(XmlPullParser xmlPullParser) {
        int next;
        DisplayQuirks displayQuirks = new DisplayQuirks();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("quirk")) {
                    displayQuirks.getQuirk().add(XmlParser.readText(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return displayQuirks;
        }
        throw new DatatypeConfigurationException("DisplayQuirks is not closed");
    }
}
