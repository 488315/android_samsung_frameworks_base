package com.android.server.policy.devicestate.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes3.dex */
public class Flags {
  public List flag;

  public List getFlag() {
    if (this.flag == null) {
      this.flag = new ArrayList();
    }
    return this.flag;
  }

  public static Flags read(XmlPullParser xmlPullParser) {
    int next;
    Flags flags = new Flags();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("flag")) {
          flags.getFlag().add(XmlParser.readText(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return flags;
    }
    throw new DatatypeConfigurationException("Flags is not closed");
  }
}
