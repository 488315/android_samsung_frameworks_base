package com.android.server.compat.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class Config {
  public List compatChange;

  public List getCompatChange() {
    if (this.compatChange == null) {
      this.compatChange = new ArrayList();
    }
    return this.compatChange;
  }

  public static Config read(XmlPullParser xmlPullParser) {
    int next;
    Config config = new Config();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("compat-change")) {
          config.getCompatChange().add(Change.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return config;
    }
    throw new DatatypeConfigurationException("Config is not closed");
  }
}
