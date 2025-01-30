package com.android.server.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.android.server.pm.pkg.parsing.ParsingPackageUtils;
import com.android.server.pm.pkg.parsing.ParsingUtils;
import com.samsung.android.server.p025pm.allowlist.BroadcastReceiverListParser;
import com.samsung.android.server.p025pm.allowlist.RestrictedReceiverFilter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class ParsedIntentInfoUtils {
  public static final RestrictedReceiverFilter sRRFilter = RestrictedReceiverFilter.getInstance();

  /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
  public static ParseResult parseIntentInfo(
      String str,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      boolean z,
      boolean z2,
      ParseInput parseInput) {
    ParseResult success;
    ParsedIntentInfoImpl parsedIntentInfoImpl = new ParsedIntentInfoImpl();
    IntentFilter intentFilter = parsedIntentInfoImpl.getIntentFilter();
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestIntentFilter);
    int i = 2;
    int i2 = 0;
    try {
      intentFilter.setPriority(obtainAttributes.getInt(2, 0));
      intentFilter.setOrder(obtainAttributes.getInt(3, 0));
      TypedValue peekValue = obtainAttributes.peekValue(0);
      if (peekValue != null) {
        parsedIntentInfoImpl.setLabelRes(peekValue.resourceId);
        if (peekValue.resourceId == 0) {
          parsedIntentInfoImpl.setNonLocalizedLabel(peekValue.coerceToString());
        }
      }
      if (ParsingPackageUtils.sUseRoundIcon) {
        parsedIntentInfoImpl.setIcon(obtainAttributes.getResourceId(7, 0));
      }
      if (parsedIntentInfoImpl.getIcon() == 0) {
        parsedIntentInfoImpl.setIcon(obtainAttributes.getResourceId(1, 0));
      }
      if (z2) {
        intentFilter.setAutoVerify(obtainAttributes.getBoolean(6, false));
      }
      obtainAttributes.recycle();
      ArrayList<RestrictedReceiverFilter.RestrictedAction> arrayList = new ArrayList();
      int depth = xmlResourceParser.getDepth();
      while (true) {
        int next = xmlResourceParser.next();
        if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
          if (next == i) {
            String name = xmlResourceParser.getName();
            name.hashCode();
            int i3 = -1;
            switch (name.hashCode()) {
              case -1422950858:
                if (name.equals("action")) {
                  i3 = i2;
                  break;
                }
                break;
              case 3076010:
                if (name.equals("data")) {
                  i3 = 1;
                  break;
                }
                break;
              case 50511102:
                if (name.equals("category")) {
                  i3 = i;
                  break;
                }
                break;
            }
            switch (i3) {
              case 0:
                String attributeValue =
                    xmlResourceParser.getAttributeValue(
                        "http://schemas.android.com/apk/res/android", "name");
                if (attributeValue == null) {
                  success = parseInput.error("No value supplied for <android:name>");
                  break;
                } else if (attributeValue.isEmpty()) {
                  intentFilter.addAction(attributeValue);
                  success =
                      parseInput.deferError("No value supplied for <android:name>", 151163173L);
                  break;
                } else {
                  RestrictedReceiverFilter restrictedReceiverFilter = sRRFilter;
                  if (restrictedReceiverFilter != null) {
                    String packageName = parsingPackage.getPackageName();
                    String baseApkPath = parsingPackage.getBaseApkPath();
                    if (restrictedReceiverFilter.filterReceiver(
                        packageName, baseApkPath, attributeValue)) {
                      arrayList.add(
                          new RestrictedReceiverFilter.RestrictedAction(
                              attributeValue, packageName, baseApkPath));
                    }
                  }
                  intentFilter.addAction(attributeValue);
                  success = parseInput.success((Object) null);
                  break;
                }
              case 1:
                success =
                    parseData(parsedIntentInfoImpl, resources, xmlResourceParser, z, parseInput);
                break;
              case 2:
                String attributeValue2 =
                    xmlResourceParser.getAttributeValue(
                        "http://schemas.android.com/apk/res/android", "name");
                if (attributeValue2 == null) {
                  success = parseInput.error("No value supplied for <android:name>");
                  break;
                } else if (attributeValue2.isEmpty()) {
                  intentFilter.addCategory(attributeValue2);
                  success =
                      parseInput.deferError("No value supplied for <android:name>", 151163173L);
                  break;
                } else {
                  intentFilter.addCategory(attributeValue2);
                  success = parseInput.success((Object) null);
                  break;
                }
              default:
                success =
                    ParsingUtils.unknownTag(
                        "<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                break;
            }
            if (success.isError()) {
              return parseInput.error(success);
            }
            i = 2;
            i2 = 0;
          }
        }
      }
      parsedIntentInfoImpl.setHasDefault(
          intentFilter.hasCategory("android.intent.category.DEFAULT"));
      for (RestrictedReceiverFilter.RestrictedAction restrictedAction : arrayList) {
        if ((BroadcastReceiverListParser.isPackageXXXIntent(restrictedAction.mAction)
                && BroadcastReceiverListParser.hasPackageSSP(intentFilter))
            ? false
            : true) {
          intentFilter.removeAction(restrictedAction.mAction);
          sRRFilter.addViolationLog(
              restrictedAction.mPackageName, restrictedAction.mCodePath, restrictedAction.mAction);
        }
      }
      return parseInput.success(parsedIntentInfoImpl);
    } catch (Throwable th) {
      obtainAttributes.recycle();
      throw th;
    }
  }

  public static ParseResult parseData(
      ParsedIntentInfo parsedIntentInfo,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      boolean z,
      ParseInput parseInput) {
    IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestData);
    try {
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
      if (nonConfigurationString != null) {
        intentFilter.addDataType(nonConfigurationString);
      }
      String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(10, 0);
      if (nonConfigurationString2 != null) {
        intentFilter.addMimeGroup(nonConfigurationString2);
      }
      String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(1, 0);
      if (nonConfigurationString3 != null) {
        intentFilter.addDataScheme(nonConfigurationString3);
      }
      String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(7, 0);
      if (nonConfigurationString4 != null) {
        intentFilter.addDataSchemeSpecificPart(nonConfigurationString4, 0);
      }
      String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(8, 0);
      if (nonConfigurationString5 != null) {
        intentFilter.addDataSchemeSpecificPart(nonConfigurationString5, 1);
      }
      String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(9, 0);
      if (nonConfigurationString6 != null) {
        if (!z) {
          return parseInput.error("sspPattern not allowed here; ssp must be literal");
        }
        intentFilter.addDataSchemeSpecificPart(nonConfigurationString6, 2);
      }
      String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(14, 0);
      if (nonConfigurationString7 != null) {
        if (!z) {
          return parseInput.error("sspAdvancedPattern not allowed here; ssp must be literal");
        }
        intentFilter.addDataSchemeSpecificPart(nonConfigurationString7, 3);
      }
      String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(12, 0);
      if (nonConfigurationString8 != null) {
        intentFilter.addDataSchemeSpecificPart(nonConfigurationString8, 4);
      }
      String nonConfigurationString9 = obtainAttributes.getNonConfigurationString(2, 0);
      String nonConfigurationString10 = obtainAttributes.getNonConfigurationString(3, 0);
      if (nonConfigurationString9 != null) {
        intentFilter.addDataAuthority(nonConfigurationString9, nonConfigurationString10);
      }
      String nonConfigurationString11 = obtainAttributes.getNonConfigurationString(4, 0);
      if (nonConfigurationString11 != null) {
        intentFilter.addDataPath(nonConfigurationString11, 0);
      }
      String nonConfigurationString12 = obtainAttributes.getNonConfigurationString(5, 0);
      if (nonConfigurationString12 != null) {
        intentFilter.addDataPath(nonConfigurationString12, 1);
      }
      String nonConfigurationString13 = obtainAttributes.getNonConfigurationString(6, 0);
      if (nonConfigurationString13 != null) {
        if (!z) {
          return parseInput.error("pathPattern not allowed here; path must be literal");
        }
        intentFilter.addDataPath(nonConfigurationString13, 2);
      }
      String nonConfigurationString14 = obtainAttributes.getNonConfigurationString(13, 0);
      if (nonConfigurationString14 != null) {
        if (!z) {
          return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
        }
        intentFilter.addDataPath(nonConfigurationString14, 3);
      }
      String nonConfigurationString15 = obtainAttributes.getNonConfigurationString(11, 0);
      if (nonConfigurationString15 != null) {
        intentFilter.addDataPath(nonConfigurationString15, 4);
      }
      return parseInput.success((Object) null);
    } catch (IntentFilter.MalformedMimeTypeException e) {
      return parseInput.error(e.toString());
    } finally {
      obtainAttributes.recycle();
    }
  }
}
