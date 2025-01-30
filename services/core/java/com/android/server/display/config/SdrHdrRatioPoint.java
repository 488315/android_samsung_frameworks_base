package com.android.server.display.config;

import java.math.BigDecimal;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class SdrHdrRatioPoint {
    public BigDecimal hdrRatio;
    public BigDecimal sdrNits;

    public final void setSdrNits(BigDecimal bigDecimal) {
        this.sdrNits = bigDecimal;
    }

    public final void setHdrRatio(BigDecimal bigDecimal) {
        this.hdrRatio = bigDecimal;
    }

    public static SdrHdrRatioPoint read(XmlPullParser xmlPullParser) {
        int next;
        SdrHdrRatioPoint sdrHdrRatioPoint = new SdrHdrRatioPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("sdrNits")) {
                    sdrHdrRatioPoint.setSdrNits(new BigDecimal(XmlParser.readText(xmlPullParser)));
                } else if (name.equals("hdrRatio")) {
                    sdrHdrRatioPoint.setHdrRatio(new BigDecimal(XmlParser.readText(xmlPullParser)));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return sdrHdrRatioPoint;
        }
        throw new DatatypeConfigurationException("SdrHdrRatioPoint is not closed");
    }
}
