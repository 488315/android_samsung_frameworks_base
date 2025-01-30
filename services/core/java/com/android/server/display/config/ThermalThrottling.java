package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class ThermalThrottling {
  public List brightnessThrottlingMap;
  public List refreshRateThrottlingMap;

  public final List getBrightnessThrottlingMap() {
    if (this.brightnessThrottlingMap == null) {
      this.brightnessThrottlingMap = new ArrayList();
    }
    return this.brightnessThrottlingMap;
  }

  public final List getRefreshRateThrottlingMap() {
    if (this.refreshRateThrottlingMap == null) {
      this.refreshRateThrottlingMap = new ArrayList();
    }
    return this.refreshRateThrottlingMap;
  }

  public static ThermalThrottling read(XmlPullParser xmlPullParser) {
    int next;
    ThermalThrottling thermalThrottling = new ThermalThrottling();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        String name = xmlPullParser.getName();
        if (name.equals("brightnessThrottlingMap")) {
          thermalThrottling
              .getBrightnessThrottlingMap()
              .add(BrightnessThrottlingMap.read(xmlPullParser));
        } else if (name.equals("refreshRateThrottlingMap")) {
          thermalThrottling
              .getRefreshRateThrottlingMap()
              .add(RefreshRateThrottlingMap.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return thermalThrottling;
    }
    throw new DatatypeConfigurationException("ThermalThrottling is not closed");
  }
}
