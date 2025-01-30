package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class RefreshRateZoneProfiles {
  public List refreshRateZoneProfile;

  public final List getRefreshRateZoneProfile() {
    if (this.refreshRateZoneProfile == null) {
      this.refreshRateZoneProfile = new ArrayList();
    }
    return this.refreshRateZoneProfile;
  }

  public static RefreshRateZoneProfiles read(XmlPullParser xmlPullParser) {
    int next;
    RefreshRateZoneProfiles refreshRateZoneProfiles = new RefreshRateZoneProfiles();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("refreshRateZoneProfile")) {
          refreshRateZoneProfiles
              .getRefreshRateZoneProfile()
              .add(RefreshRateZone.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return refreshRateZoneProfiles;
    }
    throw new DatatypeConfigurationException("RefreshRateZoneProfiles is not closed");
  }
}
