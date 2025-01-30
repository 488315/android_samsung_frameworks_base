package com.android.server.pm.pkg.component;

import android.app.ActivityTaskManager;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.IInstalld;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.server.display.DisplayPowerController2;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.android.server.pm.pkg.parsing.ParsingUtils;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class ParsedActivityUtils {
  public static final Set SAFE_BROADCASTS;

  public static int getActivityConfigChanges(int i, int i2) {
    return i | ((~i2) & 3);
  }

  static {
    ArraySet arraySet = new ArraySet();
    SAFE_BROADCASTS = arraySet;
    arraySet.add("android.intent.action.BOOT_COMPLETED");
  }

  public static ParseResult parseActivityOrReceiver(
      String[] strArr,
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      int i,
      boolean z,
      String str,
      ParseInput parseInput) {
    TypedArray typedArray;
    String str2;
    ParseInput parseInput2;
    ParsingPackage parsingPackage2;
    String packageName = parsingPackage.getPackageName();
    ParsedActivityImpl parsedActivityImpl = new ParsedActivityImpl();
    boolean equals = "receiver".equals(xmlResourceParser.getName());
    String str3 = "<" + xmlResourceParser.getName() + ">";
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivity);
    try {
      ParseResult parseMainComponent =
          ParsedMainComponentUtils.parseMainComponent(
              parsedActivityImpl,
              str3,
              strArr,
              parsingPackage,
              obtainAttributes,
              i,
              z,
              str,
              parseInput,
              30,
              17,
              42,
              5,
              2,
              1,
              23,
              3,
              7,
              44,
              48,
              57);
      if (parseMainComponent.isError()) {
        ParseResult error = parseInput.error(parseMainComponent);
        obtainAttributes.recycle();
        return error;
      }
      if (equals && parsingPackage.isSaveStateDisallowed()) {
        str2 = packageName;
        if (Objects.equals(parsedActivityImpl.getProcessName(), str2)) {
          ParseResult error2 =
              parseInput.error("Heavy-weight applications can not have receivers in main process");
          obtainAttributes.recycle();
          return error2;
        }
      } else {
        str2 = packageName;
      }
      typedArray = obtainAttributes;
      try {
        parsedActivityImpl
            .setTheme(typedArray.getResourceId(0, 0))
            .setUiOptions(typedArray.getInt(26, parsingPackage.getUiOptions()));
        int i2 = 4;
        parsedActivityImpl.setFlags(
            parsedActivityImpl.getFlags()
                | ComponentParseUtils.flag(
                    64, 19, parsingPackage.isTaskReparentingAllowed(), typedArray)
                | ComponentParseUtils.flag(8, 18, typedArray)
                | ComponentParseUtils.flag(4, 11, typedArray)
                | ComponentParseUtils.flag(32, 13, typedArray)
                | ComponentParseUtils.flag(256, 22, typedArray)
                | ComponentParseUtils.flag(2, 10, typedArray)
                | ComponentParseUtils.flag(
                    IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, 24, typedArray)
                | ComponentParseUtils.flag(1, 9, typedArray)
                | ComponentParseUtils.flag(128, 21, typedArray)
                | ComponentParseUtils.flag(1024, 39, typedArray)
                | ComponentParseUtils.flag(1024, 29, typedArray)
                | ComponentParseUtils.flag(16, 12, typedArray)
                | ComponentParseUtils.flag(536870912, 65, typedArray));
        if (equals) {
          parseInput2 = parseInput;
          parsingPackage2 = parsingPackage;
          parsedActivityImpl
              .setLaunchMode(0)
              .setConfigChanges(0)
              .setFlags(
                  parsedActivityImpl.getFlags()
                      | ComponentParseUtils.flag(1073741824, 28, typedArray));
        } else {
          parsedActivityImpl.setFlags(
              parsedActivityImpl.getFlags()
                  | ComponentParseUtils.flag(
                      512, 25, parsingPackage.isHardwareAccelerated(), typedArray)
                  | ComponentParseUtils.flag(Integer.MIN_VALUE, 31, typedArray)
                  | ComponentParseUtils.flag(262144, 64, typedArray)
                  | ComponentParseUtils.flag(IInstalld.FLAG_FORCE, 35, typedArray)
                  | ComponentParseUtils.flag(IInstalld.FLAG_USE_QUOTA, 36, typedArray)
                  | ComponentParseUtils.flag(16384, 37, typedArray)
                  | ComponentParseUtils.flag(8388608, 51, typedArray)
                  | ComponentParseUtils.flag(4194304, 41, typedArray)
                  | ComponentParseUtils.flag(16777216, 52, typedArray)
                  | ComponentParseUtils.flag(33554432, 56, typedArray)
                  | ComponentParseUtils.flag(268435456, 60, typedArray));
          parsedActivityImpl.setPrivateFlags(
              parsedActivityImpl.getPrivateFlags()
                  | ComponentParseUtils.flag(1, 54, typedArray)
                  | ComponentParseUtils.flag(2, 58, true, typedArray));
          parsedActivityImpl
              .setColorMode(typedArray.getInt(49, 0))
              .setDocumentLaunchMode(typedArray.getInt(33, 0))
              .setLaunchMode(typedArray.getInt(14, 0))
              .setLockTaskLaunchMode(typedArray.getInt(38, 0))
              .setMaxRecents(
                  typedArray.getInt(34, ActivityTaskManager.getDefaultAppRecentsLimitStatic()))
              .setPersistableMode(typedArray.getInteger(32, 0))
              .setRequestedVrComponent(typedArray.getString(43))
              .setRotationAnimation(typedArray.getInt(46, -1))
              .setSoftInputMode(typedArray.getInt(20, 0))
              .setConfigChanges(
                  getActivityConfigChanges(typedArray.getInt(16, 0), typedArray.getInt(47, 0)));
          int i3 = typedArray.getInt(15, -1);
          parseInput2 = parseInput;
          parsingPackage2 = parsingPackage;
          int activityResizeMode = getActivityResizeMode(parsingPackage2, typedArray, i3);
          parsedActivityImpl.setScreenOrientation(i3).setResizeMode(activityResizeMode);
          if (typedArray.hasValue(50) && typedArray.getType(50) == 4) {
            parsedActivityImpl.setMaxAspectRatio(
                activityResizeMode,
                typedArray.getFloat(50, DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
          }
          if (typedArray.hasValue(53) && typedArray.getType(53) == 4) {
            parsedActivityImpl.setMinAspectRatio(
                activityResizeMode,
                typedArray.getFloat(53, DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
          }
          if (typedArray.hasValue(62)) {
            boolean z2 = typedArray.getBoolean(62, false);
            int privateFlags = parsedActivityImpl.getPrivateFlags();
            if (!z2) {
              i2 = 8;
            }
            parsedActivityImpl.setPrivateFlags(privateFlags | i2);
          }
        }
        ParseResult buildTaskAffinityName =
            ComponentParseUtils.buildTaskAffinityName(
                str2,
                parsingPackage.getTaskAffinity(),
                typedArray.getNonConfigurationString(8, 1024),
                parseInput2);
        if (buildTaskAffinityName.isError()) {
          ParseResult error3 = parseInput2.error(buildTaskAffinityName);
          typedArray.recycle();
          return error3;
        }
        parsedActivityImpl.setTaskAffinity((String) buildTaskAffinityName.getResult());
        boolean z3 = typedArray.getBoolean(45, false);
        if (z3) {
          parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
          parsingPackage2.setVisibleToInstantApps(true);
        }
        String nonConfigurationString = typedArray.getNonConfigurationString(63, 0);
        if (nonConfigurationString != null
            && FrameworkParsingPackageUtils.validateName(nonConfigurationString, false, false)
                != null) {
          ParseResult error4 =
              parseInput2.error(
                  "requiredDisplayCategory attribute can only consist of alphanumeric characters,"
                      + " '_', and '.'");
          typedArray.recycle();
          return error4;
        }
        parsedActivityImpl.setRequiredDisplayCategory(nonConfigurationString);
        ParseResult parseActivityOrAlias =
            parseActivityOrAlias(
                parsedActivityImpl,
                parsingPackage,
                str3,
                xmlResourceParser,
                resources,
                typedArray,
                equals,
                false,
                z3,
                parseInput,
                27,
                4,
                6);
        typedArray.recycle();
        return parseActivityOrAlias;
      } catch (Throwable th) {
        th = th;
        typedArray.recycle();
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
      typedArray = obtainAttributes;
    }
  }

  public static ParseResult parseActivityAlias(
      ParsingPackage parsingPackage,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      boolean z,
      String str,
      ParseInput parseInput) {
    TypedArray typedArray;
    ParsedActivity parsedActivity;
    TypedArray obtainAttributes =
        resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
    try {
      String nonConfigurationString = obtainAttributes.getNonConfigurationString(7, 1024);
      if (nonConfigurationString == null) {
        ParseResult error =
            parseInput.error("<activity-alias> does not specify android:targetActivity");
        obtainAttributes.recycle();
        return error;
      }
      String packageName = parsingPackage.getPackageName();
      String buildClassName = ParsingUtils.buildClassName(packageName, nonConfigurationString);
      if (buildClassName == null) {
        ParseResult error2 = parseInput.error("Empty class name in package " + packageName);
        obtainAttributes.recycle();
        return error2;
      }
      List activities = parsingPackage.getActivities();
      int size = ArrayUtils.size(activities);
      int i = 0;
      while (true) {
        if (i >= size) {
          parsedActivity = null;
          break;
        }
        parsedActivity = (ParsedActivity) activities.get(i);
        if (buildClassName.equals(parsedActivity.getName())) {
          break;
        }
        i++;
      }
      if (parsedActivity == null) {
        ParseResult error3 =
            parseInput.error(
                "<activity-alias> target activity "
                    + buildClassName
                    + " not found in manifest with activities = "
                    + parsingPackage.getActivities()
                    + ", parsedActivities = "
                    + activities);
        obtainAttributes.recycle();
        return error3;
      }
      ParsedActivityImpl makeAlias = ParsedActivityImpl.makeAlias(buildClassName, parsedActivity);
      String str2 = "<" + xmlResourceParser.getName() + ">";
      typedArray = obtainAttributes;
      try {
        ParseResult parseMainComponent =
            ParsedMainComponentUtils.parseMainComponent(
                makeAlias,
                str2,
                null,
                parsingPackage,
                obtainAttributes,
                0,
                z,
                str,
                parseInput,
                10,
                6,
                -1,
                4,
                1,
                0,
                8,
                2,
                -1,
                11,
                -1,
                12);
        if (parseMainComponent.isError()) {
          ParseResult error4 = parseInput.error(parseMainComponent);
          typedArray.recycle();
          return error4;
        }
        ParseResult parseActivityOrAlias =
            parseActivityOrAlias(
                makeAlias,
                parsingPackage,
                str2,
                xmlResourceParser,
                resources,
                typedArray,
                false,
                true,
                (makeAlias.getFlags() & 1048576) != 0,
                parseInput,
                9,
                3,
                5);
        typedArray.recycle();
        return parseActivityOrAlias;
      } catch (Throwable th) {
        th = th;
        typedArray.recycle();
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
      typedArray = obtainAttributes;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:31:0x017d, code lost:

     if (r23 != false) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:33:0x0184, code lost:

     if (r16.getLaunchMode() == 4) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x0190, code lost:

     if (r16.getMetaData().containsKey("android.activity.launch_mode") == false) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:36:0x0192, code lost:

     r0 = r16.getMetaData().getString("android.activity.launch_mode");
  */
  /* JADX WARN: Code restructure failed: missing block: B:37:0x019a, code lost:

     if (r0 == null) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:39:0x01a3, code lost:

     if (r0.equals("singleInstancePerTask") == false) goto L83;
  */
  /* JADX WARN: Code restructure failed: missing block: B:40:0x01a5, code lost:

     r16.setLaunchMode(4);
  */
  /* JADX WARN: Code restructure failed: missing block: B:41:0x01a8, code lost:

     if (r23 != false) goto L89;
  */
  /* JADX WARN: Code restructure failed: missing block: B:42:0x01aa, code lost:

     r0 = r21.getBoolean(59, true);
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x01ba, code lost:

     if (r16.getMetaData().getBoolean("android.can_display_on_remote_devices", true) != false) goto L87;
  */
  /* JADX WARN: Code restructure failed: missing block: B:44:0x01bc, code lost:

     r0 = false;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x01bd, code lost:

     if (r0 == false) goto L89;
  */
  /* JADX WARN: Code restructure failed: missing block: B:46:0x01bf, code lost:

     r16.setFlags(r16.getFlags() | 65536);
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x01c9, code lost:

     r0 = resolveActivityWindowLayout(r16, r25);
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x01d1, code lost:

     if (r0.isError() == false) goto L93;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x01d7, code lost:

     return r25.error(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:51:0x01d8, code lost:

     r16.setWindowLayout((android.content.pm.ActivityInfo.WindowLayout) r0.getResult());
  */
  /* JADX WARN: Code restructure failed: missing block: B:52:0x01e1, code lost:

     if (r14 != false) goto L104;
  */
  /* JADX WARN: Code restructure failed: missing block: B:54:0x01eb, code lost:

     if (r16.getIntents().size() <= 0) goto L98;
  */
  /* JADX WARN: Code restructure failed: missing block: B:55:0x01ed, code lost:

     r13 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:56:0x01ee, code lost:

     if (r13 == false) goto L103;
  */
  /* JADX WARN: Code restructure failed: missing block: B:57:0x01f0, code lost:

     r0 = r25.deferError(r16.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", 150232615);
  */
  /* JADX WARN: Code restructure failed: missing block: B:58:0x021a, code lost:

     if (r0.isError() == false) goto L103;
  */
  /* JADX WARN: Code restructure failed: missing block: B:60:0x0220, code lost:

     return r25.error(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x0221, code lost:

     r16.setExported(r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:63:0x0228, code lost:

     return r25.success(r16);
  */
  /* JADX WARN: Removed duplicated region for block: B:77:0x0178 A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0091 A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseActivityOrAlias(
      ParsedActivityImpl parsedActivityImpl,
      ParsingPackage parsingPackage,
      String str,
      XmlResourceParser xmlResourceParser,
      Resources resources,
      TypedArray typedArray,
      boolean z,
      boolean z2,
      boolean z3,
      ParseInput parseInput,
      int i,
      int i2,
      int i3) {
    ParseResult unknownTag;
    ParseResult parseActivityWindowLayout;
    ParsedIntentInfoImpl parsedIntentInfoImpl;
    ParsedIntentInfoImpl parsedIntentInfoImpl2;
    String nonConfigurationString = typedArray.getNonConfigurationString(i, 1024);
    if (nonConfigurationString != null) {
      String buildClassName =
          ParsingUtils.buildClassName(parsingPackage.getPackageName(), nonConfigurationString);
      if (buildClassName == null) {
        Log.e(
            "PackageParsing",
            "Activity "
                + parsedActivityImpl.getName()
                + " specified invalid parentActivityName "
                + nonConfigurationString);
      } else {
        parsedActivityImpl.setParentActivityName(buildClassName);
      }
    }
    boolean z4 = false;
    String nonConfigurationString2 = typedArray.getNonConfigurationString(i2, 0);
    if (z2) {
      parsedActivityImpl.setPermission(nonConfigurationString2);
    } else {
      if (nonConfigurationString2 == null) {
        nonConfigurationString2 = parsingPackage.getPermission();
      }
      parsedActivityImpl.setPermission(nonConfigurationString2);
    }
    ParseResult parseKnownActivityEmbeddingCerts =
        ParsingUtils.parseKnownActivityEmbeddingCerts(
            typedArray, resources, z2 ? 14 : 61, parseInput);
    if (parseKnownActivityEmbeddingCerts.isError()) {
      return parseInput.error(parseKnownActivityEmbeddingCerts);
    }
    Set set = (Set) parseKnownActivityEmbeddingCerts.getResult();
    if (set != null) {
      parsedActivityImpl.setKnownActivityEmbeddingCerts(set);
    }
    boolean hasValue = typedArray.hasValue(i3);
    if (hasValue) {
      parsedActivityImpl.setExported(typedArray.getBoolean(i3, false));
    }
    int depth = xmlResourceParser.getDepth();
    while (true) {
      int next = xmlResourceParser.next();
      if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
        break;
      }
      if (next == 2) {
        if (xmlResourceParser.getName().equals("intent-filter")) {
          parseActivityWindowLayout =
              parseIntentFilter(
                  parsingPackage,
                  parsedActivityImpl,
                  !z,
                  z3,
                  resources,
                  xmlResourceParser,
                  parseInput);
          if (parseActivityWindowLayout.isSuccess()
              && (parsedIntentInfoImpl2 =
                      (ParsedIntentInfoImpl) parseActivityWindowLayout.getResult())
                  != null) {
            parsedActivityImpl.setOrder(
                Math.max(
                    parsedIntentInfoImpl2.getIntentFilter().getOrder(),
                    parsedActivityImpl.getOrder()));
            parsedActivityImpl.addIntent(parsedIntentInfoImpl2);
          }
        } else if (xmlResourceParser.getName().equals("meta-data")) {
          parseActivityWindowLayout =
              ParsedComponentUtils.addMetaData(
                  parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
        } else if (xmlResourceParser.getName().equals("property")) {
          parseActivityWindowLayout =
              ParsedComponentUtils.addProperty(
                  parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
        } else if (!z && !z2 && xmlResourceParser.getName().equals("preferred")) {
          parseActivityWindowLayout =
              parseIntentFilter(
                  parsingPackage,
                  parsedActivityImpl,
                  true,
                  z3,
                  resources,
                  xmlResourceParser,
                  parseInput);
          if (parseActivityWindowLayout.isSuccess()
              && (parsedIntentInfoImpl =
                      (ParsedIntentInfoImpl) parseActivityWindowLayout.getResult())
                  != null) {
            parsingPackage.addPreferredActivityFilter(
                parsedActivityImpl.getClassName(), parsedIntentInfoImpl);
          }
        } else if (!z && !z2 && xmlResourceParser.getName().equals("layout")) {
          parseActivityWindowLayout =
              parseActivityWindowLayout(resources, xmlResourceParser, parseInput);
          if (parseActivityWindowLayout.isSuccess()) {
            parsedActivityImpl.setWindowLayout(
                (ActivityInfo.WindowLayout) parseActivityWindowLayout.getResult());
          }
        } else {
          unknownTag = ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
          if (!unknownTag.isError()) {
            return parseInput.error(unknownTag);
          }
        }
        unknownTag = parseActivityWindowLayout;
        if (!unknownTag.isError()) {}
      }
    }
  }

  public static ParseResult parseIntentFilter(
      ParsingPackage parsingPackage,
      ParsedActivityImpl parsedActivityImpl,
      boolean z,
      boolean z2,
      Resources resources,
      XmlResourceParser xmlResourceParser,
      ParseInput parseInput) {
    ParseResult parseIntentFilter =
        ParsedMainComponentUtils.parseIntentFilter(
            parsedActivityImpl,
            parsingPackage,
            resources,
            xmlResourceParser,
            z2,
            true,
            true,
            z,
            true,
            parseInput);
    if (parseIntentFilter.isError()) {
      return parseInput.error(parseIntentFilter);
    }
    ParsedIntentInfoImpl parsedIntentInfoImpl =
        (ParsedIntentInfoImpl) parseIntentFilter.getResult();
    if (parsedIntentInfoImpl != null) {
      IntentFilter intentFilter = parsedIntentInfoImpl.getIntentFilter();
      if (intentFilter.isVisibleToInstantApp()) {
        parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
      }
      if (intentFilter.isImplicitlyVisibleToInstantApp()) {
        parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 2097152);
      }
    }
    return parseInput.success(parsedIntentInfoImpl);
  }

  public static int getActivityResizeMode(
      ParsingPackage parsingPackage, TypedArray typedArray, int i) {
    Boolean resizeableActivity = parsingPackage.getResizeableActivity();
    if (typedArray.hasValue(40) || resizeableActivity != null) {
      return typedArray.getBoolean(
              40, resizeableActivity != null && resizeableActivity.booleanValue())
          ? 2
          : 0;
    }
    if (parsingPackage.isResizeableActivityViaSdkVersion()) {
      return 1;
    }
    if (ActivityInfo.isFixedOrientationPortrait(i)) {
      return 6;
    }
    if (ActivityInfo.isFixedOrientationLandscape(i)) {
      return 5;
    }
    return i == 14 ? 7 : 4;
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x002a A[Catch: all -> 0x0063, TryCatch #0 {all -> 0x0063, blocks: (B:3:0x0007, B:5:0x0013, B:7:0x0023, B:9:0x002a, B:11:0x003a, B:17:0x0031, B:19:0x001b), top: B:2:0x0007 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ParseResult parseActivityWindowLayout(
      Resources resources, AttributeSet attributeSet, ParseInput parseInput) {
    float f;
    int dimensionPixelSize;
    int type;
    float f2;
    int dimensionPixelSize2;
    TypedArray obtainAttributes =
        resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestLayout);
    try {
      int type2 = obtainAttributes.getType(3);
      float f3 = -1.0f;
      if (type2 == 6) {
        f = obtainAttributes.getFraction(3, 1, 1, -1.0f);
      } else {
        if (type2 == 5) {
          dimensionPixelSize = obtainAttributes.getDimensionPixelSize(3, -1);
          f = -1.0f;
          type = obtainAttributes.getType(4);
          if (type != 6) {
            f3 = obtainAttributes.getFraction(4, 1, 1, -1.0f);
          } else if (type == 5) {
            f2 = -1.0f;
            dimensionPixelSize2 = obtainAttributes.getDimensionPixelSize(4, -1);
            return parseInput.success(
                new ActivityInfo.WindowLayout(
                    dimensionPixelSize,
                    f,
                    dimensionPixelSize2,
                    f2,
                    obtainAttributes.getInt(0, 17),
                    obtainAttributes.getDimensionPixelSize(1, -1),
                    obtainAttributes.getDimensionPixelSize(2, -1),
                    obtainAttributes.getNonConfigurationString(5, 0)));
          }
          f2 = f3;
          dimensionPixelSize2 = -1;
          return parseInput.success(
              new ActivityInfo.WindowLayout(
                  dimensionPixelSize,
                  f,
                  dimensionPixelSize2,
                  f2,
                  obtainAttributes.getInt(0, 17),
                  obtainAttributes.getDimensionPixelSize(1, -1),
                  obtainAttributes.getDimensionPixelSize(2, -1),
                  obtainAttributes.getNonConfigurationString(5, 0)));
        }
        f = -1.0f;
      }
      dimensionPixelSize = -1;
      type = obtainAttributes.getType(4);
      if (type != 6) {}
      f2 = f3;
      dimensionPixelSize2 = -1;
      return parseInput.success(
          new ActivityInfo.WindowLayout(
              dimensionPixelSize,
              f,
              dimensionPixelSize2,
              f2,
              obtainAttributes.getInt(0, 17),
              obtainAttributes.getDimensionPixelSize(1, -1),
              obtainAttributes.getDimensionPixelSize(2, -1),
              obtainAttributes.getNonConfigurationString(5, 0)));
    } finally {
      obtainAttributes.recycle();
    }
  }

  public static ParseResult resolveActivityWindowLayout(
      ParsedActivity parsedActivity, ParseInput parseInput) {
    if (!parsedActivity.getMetaData().containsKey("android.activity_window_layout_affinity")) {
      return parseInput.success(parsedActivity.getWindowLayout());
    }
    if (parsedActivity.getWindowLayout() != null
        && parsedActivity.getWindowLayout().windowLayoutAffinity != null) {
      return parseInput.success(parsedActivity.getWindowLayout());
    }
    String string =
        parsedActivity.getMetaData().getString("android.activity_window_layout_affinity");
    ActivityInfo.WindowLayout windowLayout = parsedActivity.getWindowLayout();
    if (windowLayout == null) {
      windowLayout = new ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1, string);
    } else {
      windowLayout.windowLayoutAffinity = string;
    }
    return parseInput.success(windowLayout);
  }
}
