package com.android.server.display.config.layout;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class Layouts {
  public List layout;

  public List getLayout() {
    if (this.layout == null) {
      this.layout = new ArrayList();
    }
    return this.layout;
  }

  public static Layouts read(XmlPullParser xmlPullParser) {
    int next;
    Layouts layouts = new Layouts();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        if (xmlPullParser.getName().equals("layout")) {
          layouts.getLayout().add(Layout.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return layouts;
    }
    throw new DatatypeConfigurationException("Layouts is not closed");
  }
}
