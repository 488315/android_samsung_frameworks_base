package com.android.server.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArraySet;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ParsedAttributionUtils {
  /* JADX WARN: Code restructure failed: missing block: B:31:0x00a8, code lost:

     if (r5 != null) goto L51;
  */
  /* JADX WARN: Code restructure failed: missing block: B:32:0x00aa, code lost:

     r5 = java.util.Collections.emptyList();
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x00bb, code lost:

     return r11.success(new com.android.server.pm.pkg.component.ParsedAttributionImpl(r3, r4, r5));
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x00af, code lost:

     r5.trimToSize();
     r5 = r5;
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r5v4, types: [java.util.List] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseAttribution(
      Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAttribution);
    if (obtainAttributes == null) {
      return parseInput.error("<attribution> could not be parsed");
    }
    try {
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(1, 0);
      if (nonConfigurationString == null) {
        return parseInput.error("<attribution> does not specify android:tag");
      }
      if (nonConfigurationString.length() > 50) {
        return parseInput.error("android:tag is too long. Max length is 50");
      }
      int resourceId = obtainAttributes.getResourceId(0, 0);
      if (resourceId == 0) {
        return parseInput.error("<attribution> does not specify android:label");
      }
      obtainAttributes.recycle();
      int depth = xmlResourceParser.getDepth();
      ArrayList arrayList = null;
      while (true) {
        int next = xmlResourceParser.next();
        if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
          break;
        }
        if (next != 3 && next != 4) {
          String name = xmlResourceParser.getName();
          if (!name.equals("inherit-from")) {
            return parseInput.error("Bad element under <attribution>: " + name);
          }
          TypedArray obtainAttributes2 =
              resources.obtainAttributes(
                  xmlResourceParser, R.styleable.AndroidManifestAttributionInheritFrom);
          if (obtainAttributes2 == null) {
            return parseInput.error("<inherit-from> could not be parsed");
          }
          try {
            String nonConfigurationString2 = obtainAttributes2.getNonConfigurationString(0, 0);
            arrayList = arrayList;
            if (arrayList == null) {
              arrayList = new ArrayList();
            }
            arrayList.add(nonConfigurationString2);
          } finally {
          }
        }
      }
    } finally {
    }
  }

  public static boolean isCombinationValid(List list) {
    if (list == null) {
      return true;
    }
    ArraySet arraySet = new ArraySet(list.size());
    ArraySet arraySet2 = new ArraySet();
    int size = list.size();
    if (size > 1000) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (!arraySet.add(((ParsedAttribution) list.get(i)).getTag())) {
        return false;
      }
    }
    for (int i2 = 0; i2 < size; i2++) {
      List inheritFrom = ((ParsedAttribution) list.get(i2)).getInheritFrom();
      int size2 = inheritFrom.size();
      for (int i3 = 0; i3 < size2; i3++) {
        String str = (String) inheritFrom.get(i3);
        if (arraySet.contains(str) || !arraySet2.add(str)) {
          return false;
        }
      }
    }
    return true;
  }
}
