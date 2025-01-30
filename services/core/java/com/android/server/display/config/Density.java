package com.android.server.display.config;

import java.math.BigInteger;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class Density {
    public BigInteger density;
    public BigInteger height;
    public BigInteger width;

    public final BigInteger getWidth() {
        return this.width;
    }

    public final void setWidth(BigInteger bigInteger) {
        this.width = bigInteger;
    }

    public final BigInteger getHeight() {
        return this.height;
    }

    public final void setHeight(BigInteger bigInteger) {
        this.height = bigInteger;
    }

    public final BigInteger getDensity() {
        return this.density;
    }

    public final void setDensity(BigInteger bigInteger) {
        this.density = bigInteger;
    }

    public static Density read(XmlPullParser xmlPullParser) {
        int next;
        Density density = new Density();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("width")) {
                    density.setWidth(new BigInteger(XmlParser.readText(xmlPullParser)));
                } else if (name.equals("height")) {
                    density.setHeight(new BigInteger(XmlParser.readText(xmlPullParser)));
                } else if (name.equals("density")) {
                    density.setDensity(new BigInteger(XmlParser.readText(xmlPullParser)));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return density;
        }
        throw new DatatypeConfigurationException("Density is not closed");
    }
}
