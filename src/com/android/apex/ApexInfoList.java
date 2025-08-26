package com.android.apex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ApexInfoList {
    private List<ApexInfo> apexInfo;

    public List<ApexInfo> getApexInfo() {
        if (this.apexInfo == null) {
            this.apexInfo = new ArrayList();
        }
        return this.apexInfo;
    }

    static ApexInfoList read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        ApexInfoList apexInfoList = new ApexInfoList();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("apex-info")) {
                    apexInfoList.getApexInfo().add(ApexInfo.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return apexInfoList;
        }
        throw new DatatypeConfigurationException("ApexInfoList is not closed");
    }

    void write(XmlWriter xmlWriter, String str) throws IOException {
        xmlWriter.print("<" + str);
        xmlWriter.print(">\n");
        xmlWriter.increaseIndent();
        Iterator<ApexInfo> it = getApexInfo().iterator();
        while (it.hasNext()) {
            it.next().write(xmlWriter, "apex-info");
        }
        xmlWriter.decreaseIndent();
        xmlWriter.print("</" + str + ">\n");
    }
}
