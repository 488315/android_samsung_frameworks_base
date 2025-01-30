package com.android.server.display.config;

import java.math.BigInteger;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class UsiVersion {
  public BigInteger majorVersion;
  public BigInteger minorVersion;

  public final BigInteger getMajorVersion() {
    return this.majorVersion;
  }

  public final void setMajorVersion(BigInteger bigInteger) {
    this.majorVersion = bigInteger;
  }

  public final BigInteger getMinorVersion() {
    return this.minorVersion;
  }

  public final void setMinorVersion(BigInteger bigInteger) {
    this.minorVersion = bigInteger;
  }

  public static UsiVersion read(XmlPullParser xmlPullParser) {
    int next;
    UsiVersion usiVersion = new UsiVersion();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        String name = xmlPullParser.getName();
        if (name.equals("majorVersion")) {
          usiVersion.setMajorVersion(new BigInteger(XmlParser.readText(xmlPullParser)));
        } else if (name.equals("minorVersion")) {
          usiVersion.setMinorVersion(new BigInteger(XmlParser.readText(xmlPullParser)));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return usiVersion;
    }
    throw new DatatypeConfigurationException("UsiVersion is not closed");
  }
}
