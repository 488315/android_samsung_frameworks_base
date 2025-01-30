package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class Overrides {
    public List changeOverrides;

    public List getChangeOverrides() {
        if (this.changeOverrides == null) {
            this.changeOverrides = new ArrayList();
        }
        return this.changeOverrides;
    }

    public static Overrides read(XmlPullParser xmlPullParser) {
        int next;
        Overrides overrides = new Overrides();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("change-overrides")) {
                    overrides.getChangeOverrides().add(ChangeOverrides.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return overrides;
        }
        throw new DatatypeConfigurationException("Overrides is not closed");
    }

    public void write(XmlWriter xmlWriter, String str) {
        xmlWriter.print("<" + str);
        xmlWriter.print(">\n");
        xmlWriter.increaseIndent();
        Iterator it = getChangeOverrides().iterator();
        while (it.hasNext()) {
            ((ChangeOverrides) it.next()).write(xmlWriter, "change-overrides");
        }
        xmlWriter.decreaseIndent();
        xmlWriter.print("</" + str + ">\n");
    }
}
