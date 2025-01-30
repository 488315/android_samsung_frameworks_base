package com.android.server.display.config;

import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class RefreshRateZone {

  /* renamed from: id */
  public String f1680id;
  public RefreshRateRange refreshRateRange;

  public final RefreshRateRange getRefreshRateRange() {
    return this.refreshRateRange;
  }

  public final void setRefreshRateRange(RefreshRateRange refreshRateRange) {
    this.refreshRateRange = refreshRateRange;
  }

  public String getId() {
    return this.f1680id;
  }

  public void setId(String str) {
    this.f1680id = str;
  }

  public static RefreshRateZone read(XmlPullParser xmlPullParser) {
    int next;
    RefreshRateZone refreshRateZone = new RefreshRateZone();
    String attributeValue = xmlPullParser.getAttributeValue(null, "id");
    if (attributeValue != null) {
      refreshRateZone.setId(attributeValue);
    }
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("refreshRateRange")) {
          refreshRateZone.setRefreshRateRange(RefreshRateRange.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return refreshRateZone;
    }
    throw new DatatypeConfigurationException("RefreshRateZone is not closed");
  }
}
