package com.android.server.display.config;

import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class Thresholds {
  public BrightnessThresholds brighteningThresholds;
  public BrightnessThresholds darkeningThresholds;

  public final BrightnessThresholds getBrighteningThresholds() {
    return this.brighteningThresholds;
  }

  public final void setBrighteningThresholds(BrightnessThresholds brightnessThresholds) {
    this.brighteningThresholds = brightnessThresholds;
  }

  public final BrightnessThresholds getDarkeningThresholds() {
    return this.darkeningThresholds;
  }

  public final void setDarkeningThresholds(BrightnessThresholds brightnessThresholds) {
    this.darkeningThresholds = brightnessThresholds;
  }

  public static Thresholds read(XmlPullParser xmlPullParser) {
    int next;
    Thresholds thresholds = new Thresholds();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        String name = xmlPullParser.getName();
        if (name.equals("brighteningThresholds")) {
          thresholds.setBrighteningThresholds(BrightnessThresholds.read(xmlPullParser));
        } else if (name.equals("darkeningThresholds")) {
          thresholds.setDarkeningThresholds(BrightnessThresholds.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return thresholds;
    }
    throw new DatatypeConfigurationException("Thresholds is not closed");
  }
}
