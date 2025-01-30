package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class NitsMap {
  public String interpolation;
  public List point;

  public final List getPoint() {
    if (this.point == null) {
      this.point = new ArrayList();
    }
    return this.point;
  }

  public String getInterpolation() {
    return this.interpolation;
  }

  public void setInterpolation(String str) {
    this.interpolation = str;
  }

  public static NitsMap read(XmlPullParser xmlPullParser) {
    int next;
    NitsMap nitsMap = new NitsMap();
    String attributeValue = xmlPullParser.getAttributeValue(null, "interpolation");
    if (attributeValue != null) {
      nitsMap.setInterpolation(attributeValue);
    }
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("point")) {
          nitsMap.getPoint().add(Point.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return nitsMap;
    }
    throw new DatatypeConfigurationException("NitsMap is not closed");
  }
}
