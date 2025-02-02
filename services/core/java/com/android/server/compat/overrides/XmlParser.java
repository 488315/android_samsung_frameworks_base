package com.android.server.compat.overrides;

import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public abstract class XmlParser {
  public static Overrides read(InputStream inputStream) {
    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
    newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
    newPullParser.setInput(inputStream, null);
    newPullParser.nextTag();
    if (newPullParser.getName().equals("overrides")) {
      return Overrides.read(newPullParser);
    }
    return null;
  }

  public static void skip(XmlPullParser xmlPullParser) {
    if (xmlPullParser.getEventType() != 2) {
      throw new IllegalStateException();
    }
    int i = 1;
    while (i != 0) {
      int next = xmlPullParser.next();
      if (next == 2) {
        i++;
      } else if (next == 3) {
        i--;
      }
    }
  }
}
