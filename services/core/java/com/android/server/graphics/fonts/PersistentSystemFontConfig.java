package com.android.server.graphics.fonts;

import android.graphics.fonts.FontUpdateRequest;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class PersistentSystemFontConfig {

  public class Config {
    public long lastModifiedMillis;
    public final Set updatedFontDirs = new ArraySet();
    public final List fontFamilies = new ArrayList();
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x0059, code lost:

     if (r3.equals("family") == false) goto L17;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static void loadFromXml(InputStream inputStream, Config config) {
    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
    while (true) {
      int next = resolvePullParser.next();
      boolean z = true;
      if (next == 1) {
        return;
      }
      if (next == 2) {
        int depth = resolvePullParser.getDepth();
        String name = resolvePullParser.getName();
        if (depth == 1) {
          if (!"fontConfig".equals(name)) {
            Slog.e("PersistentSystemFontConfig", "Invalid root tag: " + name);
            return;
          }
        } else if (depth == 2) {
          name.hashCode();
          switch (name.hashCode()) {
            case -1540845619:
              if (name.equals("lastModifiedDate")) {
                z = false;
                break;
              }
              z = -1;
              break;
            case -1281860764:
              break;
            case -23402365:
              if (name.equals("updatedFontDir")) {
                z = 2;
                break;
              }
              z = -1;
              break;
            default:
              z = -1;
              break;
          }
          switch (z) {
            case false:
              config.lastModifiedMillis = parseLongAttribute(resolvePullParser, "value", 0L);
              break;
            case true:
              config.fontFamilies.add(FontUpdateRequest.Family.readFromXml(resolvePullParser));
              break;
            case true:
              config.updatedFontDirs.add(getAttribute(resolvePullParser, "value"));
              break;
            default:
              Slog.w("PersistentSystemFontConfig", "Skipping unknown tag: " + name);
              break;
          }
        }
      }
    }
  }

  public static void writeToXml(OutputStream outputStream, Config config) {
    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
    resolveSerializer.startDocument((String) null, Boolean.TRUE);
    resolveSerializer.startTag((String) null, "fontConfig");
    resolveSerializer.startTag((String) null, "lastModifiedDate");
    resolveSerializer.attribute((String) null, "value", Long.toString(config.lastModifiedMillis));
    resolveSerializer.endTag((String) null, "lastModifiedDate");
    for (String str : config.updatedFontDirs) {
      resolveSerializer.startTag((String) null, "updatedFontDir");
      resolveSerializer.attribute((String) null, "value", str);
      resolveSerializer.endTag((String) null, "updatedFontDir");
    }
    List list = config.fontFamilies;
    for (int i = 0; i < list.size(); i++) {
      FontUpdateRequest.Family family = (FontUpdateRequest.Family) list.get(i);
      resolveSerializer.startTag((String) null, "family");
      FontUpdateRequest.Family.writeFamilyToXml(resolveSerializer, family);
      resolveSerializer.endTag((String) null, "family");
    }
    resolveSerializer.endTag((String) null, "fontConfig");
    resolveSerializer.endDocument();
  }

  public static long parseLongAttribute(TypedXmlPullParser typedXmlPullParser, String str, long j) {
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, str);
    if (TextUtils.isEmpty(attributeValue)) {
      return j;
    }
    try {
      return Long.parseLong(attributeValue);
    } catch (NumberFormatException unused) {
      return j;
    }
  }

  public static String getAttribute(TypedXmlPullParser typedXmlPullParser, String str) {
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, str);
    return attributeValue == null ? "" : attributeValue;
  }
}
