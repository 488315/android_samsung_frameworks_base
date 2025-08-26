package com.android.internal.pm.pkg.component;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.media.TtmlUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedActivityUtils {
    public static final boolean LOG_UNSAFE_BROADCASTS = false;
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    public static final Set<String> SAFE_BROADCASTS;
    private static final String TAG = "PackageParsing";

    public static int getActivityConfigChanges(int i, int i2) {
        return i | ((~i2) & 3);
    }

    static {
        ArraySet arraySet = new ArraySet();
        SAFE_BROADCASTS = arraySet;
        arraySet.add(Intent.ACTION_BOOT_COMPLETED);
    }

    public static ParseResult<ParsedActivity> parseActivityOrReceiver(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws Throwable {
        int i2;
        String packageName = parsingPackage.getPackageName();
        ParsedActivityImpl parsedActivityImpl = new ParsedActivityImpl();
        boolean zEquals = "receiver".equals(xmlResourceParser.getName());
        String str2 = "<" + xmlResourceParser.getName() + ">";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivity);
        try {
            ParseResult<?> mainComponent = ParsedMainComponentUtils.parseMainComponent(parsedActivityImpl, str2, strArr, parsingPackage, typedArrayObtainAttributes, i, z, str, parseInput, 30, 17, 42, 5, 2, 1, 23, 3, 7, 44, 48, 57, 66);
            if (mainComponent.isError()) {
                ParseResult<ParsedActivity> parseResultError = parseInput.error(mainComponent);
                typedArrayObtainAttributes.recycle();
                return parseResultError;
            }
            if (zEquals && parsingPackage.isSaveStateDisallowed() && Objects.equals(parsedActivityImpl.getProcessName(), packageName)) {
                ParseResult<ParsedActivity> parseResultError2 = parseInput.error("Heavy-weight applications can not have receivers in main process");
                typedArrayObtainAttributes.recycle();
                return parseResultError2;
            }
            parsedActivityImpl.setTheme(typedArrayObtainAttributes.getResourceId(0, 0)).setUiOptions(typedArrayObtainAttributes.getInt(26, parsingPackage.getUiOptions()));
            parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(64, 19, parsingPackage.isTaskReparentingAllowed(), typedArrayObtainAttributes) | ComponentParseUtils.flag(8, 18, typedArrayObtainAttributes) | ComponentParseUtils.flag(4, 11, typedArrayObtainAttributes) | ComponentParseUtils.flag(32, 13, typedArrayObtainAttributes) | ComponentParseUtils.flag(256, 22, typedArrayObtainAttributes) | ComponentParseUtils.flag(2, 10, typedArrayObtainAttributes) | ComponentParseUtils.flag(2048, 24, typedArrayObtainAttributes) | ComponentParseUtils.flag(1, 9, typedArrayObtainAttributes) | ComponentParseUtils.flag(128, 21, typedArrayObtainAttributes) | ComponentParseUtils.flag(1024, 39, typedArrayObtainAttributes) | ComponentParseUtils.flag(1024, 29, typedArrayObtainAttributes) | ComponentParseUtils.flag(16, 12, typedArrayObtainAttributes) | ComponentParseUtils.flag(536870912, 64, typedArrayObtainAttributes));
            if (zEquals) {
                parsedActivityImpl.setLaunchMode(0).setConfigChanges(0).setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(1073741824, 28, typedArrayObtainAttributes));
            } else {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(512, 25, parsingPackage.isHardwareAccelerated(), typedArrayObtainAttributes) | ComponentParseUtils.flag(Integer.MIN_VALUE, 31, typedArrayObtainAttributes) | ComponentParseUtils.flag(262144, 67, typedArrayObtainAttributes) | ComponentParseUtils.flag(8192, 35, typedArrayObtainAttributes) | ComponentParseUtils.flag(4096, 36, typedArrayObtainAttributes) | ComponentParseUtils.flag(16384, 37, typedArrayObtainAttributes) | ComponentParseUtils.flag(8388608, 51, typedArrayObtainAttributes) | ComponentParseUtils.flag(4194304, 41, typedArrayObtainAttributes) | ComponentParseUtils.flag(16777216, 52, typedArrayObtainAttributes) | ComponentParseUtils.flag(33554432, 56, typedArrayObtainAttributes) | ComponentParseUtils.flag(268435456, 60, typedArrayObtainAttributes));
                parsedActivityImpl.setPrivateFlags(parsedActivityImpl.getPrivateFlags() | ComponentParseUtils.flag(1, 54, typedArrayObtainAttributes) | ComponentParseUtils.flag(2, 58, true, typedArrayObtainAttributes));
                parsedActivityImpl.setColorMode(typedArrayObtainAttributes.getInt(49, 0)).setDocumentLaunchMode(typedArrayObtainAttributes.getInt(33, 0)).setLaunchMode(typedArrayObtainAttributes.getInt(14, 0)).setLockTaskLaunchMode(typedArrayObtainAttributes.getInt(38, 0)).setMaxRecents(typedArrayObtainAttributes.getInt(34, ActivityTaskManager.getDefaultAppRecentsLimitStatic())).setPersistableMode(typedArrayObtainAttributes.getInteger(32, 0)).setRequestedVrComponent(typedArrayObtainAttributes.getString(43)).setRotationAnimation(typedArrayObtainAttributes.getInt(46, -1)).setSoftInputMode(typedArrayObtainAttributes.getInt(20, 0)).setConfigChanges(getActivityConfigChanges(typedArrayObtainAttributes.getInt(16, 0), typedArrayObtainAttributes.getInt(47, 0)));
                int i3 = typedArrayObtainAttributes.getInt(15, -1);
                int activityResizeMode = getActivityResizeMode(parsingPackage, typedArrayObtainAttributes, i3);
                parsedActivityImpl.setScreenOrientation(i3).setResizeMode(activityResizeMode);
                if (typedArrayObtainAttributes.hasValue(50) && typedArrayObtainAttributes.getType(50) == 4) {
                    parsedActivityImpl.setMaxAspectRatio(activityResizeMode, typedArrayObtainAttributes.getFloat(50, 0.0f));
                }
                if (typedArrayObtainAttributes.hasValue(53)) {
                    i2 = 4;
                    if (typedArrayObtainAttributes.getType(53) == 4) {
                        parsedActivityImpl.setMinAspectRatio(activityResizeMode, typedArrayObtainAttributes.getFloat(53, 0.0f));
                    }
                } else {
                    i2 = 4;
                }
                if (typedArrayObtainAttributes.hasValue(62)) {
                    boolean z2 = typedArrayObtainAttributes.getBoolean(62, false);
                    int privateFlags = parsedActivityImpl.getPrivateFlags();
                    if (!z2) {
                        i2 = 8;
                    }
                    parsedActivityImpl.setPrivateFlags(privateFlags | i2);
                }
            }
            ParseResult<String> parseResultBuildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, parsingPackage.getTaskAffinity(), typedArrayObtainAttributes.getNonConfigurationString(8, 1024), parseInput);
            if (parseResultBuildTaskAffinityName.isError()) {
                ParseResult<ParsedActivity> parseResultError3 = parseInput.error(parseResultBuildTaskAffinityName);
                typedArrayObtainAttributes.recycle();
                return parseResultError3;
            }
            parsedActivityImpl.setTaskAffinity(parseResultBuildTaskAffinityName.getResult());
            boolean z3 = typedArrayObtainAttributes.getBoolean(45, false);
            if (z3) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
                parsingPackage.setVisibleToInstantApps(true);
            }
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(63, 0);
            if (nonConfigurationString != null && FrameworkParsingPackageUtils.validateName(nonConfigurationString, false, false) != null) {
                ParseResult<ParsedActivity> parseResultError4 = parseInput.error("requiredDisplayCategory attribute can only consist of alphanumeric characters, '_', and '.'");
                typedArrayObtainAttributes.recycle();
                return parseResultError4;
            }
            parsedActivityImpl.setRequiredDisplayCategory(nonConfigurationString);
            parsedActivityImpl.setRequireContentUriPermissionFromCaller(typedArrayObtainAttributes.getInt(65, 0));
            try {
                ParseResult<ParsedActivity> activityOrAlias = parseActivityOrAlias(parsedActivityImpl, parsingPackage, str2, xmlResourceParser, resources, typedArrayObtainAttributes, zEquals, false, z3, parseInput, 27, 4, 6);
                typedArrayObtainAttributes.recycle();
                return activityOrAlias;
            } catch (Throwable th) {
                th = th;
                typedArrayObtainAttributes = typedArrayObtainAttributes;
                typedArrayObtainAttributes.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static ParseResult<ParsedActivity> parseActivityAlias(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, String str, ParseInput parseInput) throws Throwable {
        ParsedActivity parsedActivity;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(7, 1024);
            if (nonConfigurationString == null) {
                ParseResult<ParsedActivity> parseResultError = parseInput.error("<activity-alias> does not specify android:targetActivity");
                typedArrayObtainAttributes.recycle();
                return parseResultError;
            }
            String packageName = parsingPackage.getPackageName();
            String strBuildClassName = ParsingUtils.buildClassName(packageName, nonConfigurationString);
            if (strBuildClassName == null) {
                ParseResult<ParsedActivity> parseResultError2 = parseInput.error("Empty class name in package " + packageName);
                typedArrayObtainAttributes.recycle();
                return parseResultError2;
            }
            List<ParsedActivity> activities = parsingPackage.getActivities();
            int size = ArrayUtils.size(activities);
            int i = 0;
            while (true) {
                if (i >= size) {
                    parsedActivity = null;
                    break;
                }
                parsedActivity = activities.get(i);
                if (strBuildClassName.equals(parsedActivity.getName())) {
                    break;
                }
                i++;
            }
            if (parsedActivity == null) {
                ParseResult<ParsedActivity> parseResultError3 = parseInput.error("<activity-alias> target activity " + strBuildClassName + " not found in manifest with activities = " + parsingPackage.getActivities() + ", parsedActivities = " + activities);
                typedArrayObtainAttributes.recycle();
                return parseResultError3;
            }
            ParsedActivityImpl parsedActivityImplMakeAlias = ParsedActivityImpl.makeAlias(strBuildClassName, parsedActivity);
            String str2 = "<" + xmlResourceParser.getName() + ">";
            try {
                ParseResult<?> mainComponent = ParsedMainComponentUtils.parseMainComponent(parsedActivityImplMakeAlias, str2, null, parsingPackage, typedArrayObtainAttributes, 0, z, str, parseInput, 10, 6, -1, 4, 1, 0, 8, 2, -1, 11, -1, 12, 15);
                if (mainComponent.isError()) {
                    ParseResult<ParsedActivity> parseResultError4 = parseInput.error(mainComponent);
                    typedArrayObtainAttributes.recycle();
                    return parseResultError4;
                }
                ParseResult<ParsedActivity> activityOrAlias = parseActivityOrAlias(parsedActivityImplMakeAlias, parsingPackage, str2, xmlResourceParser, resources, typedArrayObtainAttributes, false, true, (parsedActivityImplMakeAlias.getFlags() & 1048576) != 0, parseInput, 9, 3, 5);
                typedArrayObtainAttributes.recycle();
                return activityOrAlias;
            } catch (Throwable th) {
                th = th;
                typedArrayObtainAttributes = typedArrayObtainAttributes;
                typedArrayObtainAttributes.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static ParseResult<ParsedActivity> parseActivityOrAlias(ParsedActivityImpl parsedActivityImpl, ParsingPackage parsingPackage, String str, XmlResourceParser xmlResourceParser, Resources resources, TypedArray typedArray, boolean z, boolean z2, boolean z3, ParseInput parseInput, int i, int i2, int i3) throws XmlPullParserException, Resources.NotFoundException, IOException {
        String string;
        ParseResult<?> parseResultUnknownTag;
        ParsedIntentInfoImpl parsedIntentInfoImpl;
        ParsedIntentInfoImpl parsedIntentInfoImpl2;
        String nonConfigurationString = typedArray.getNonConfigurationString(i, 1024);
        if (nonConfigurationString != null) {
            String strBuildClassName = ParsingUtils.buildClassName(parsingPackage.getPackageName(), nonConfigurationString);
            if (strBuildClassName == null) {
                Log.e("PackageParsing", "Activity " + parsedActivityImpl.getName() + " specified invalid parentActivityName " + nonConfigurationString);
            } else {
                parsedActivityImpl.setParentActivityName(strBuildClassName);
            }
        }
        String nonConfigurationString2 = typedArray.getNonConfigurationString(i2, 0);
        if (z2) {
            parsedActivityImpl.setPermission(nonConfigurationString2);
        } else {
            if (nonConfigurationString2 == null) {
                nonConfigurationString2 = parsingPackage.getPermission();
            }
            parsedActivityImpl.setPermission(nonConfigurationString2);
        }
        ParseResult<Set<String>> knownActivityEmbeddingCerts = ParsingUtils.parseKnownActivityEmbeddingCerts(typedArray, resources, z2 ? 14 : 61, parseInput);
        if (knownActivityEmbeddingCerts.isError()) {
            return parseInput.error(knownActivityEmbeddingCerts);
        }
        Set<String> result = knownActivityEmbeddingCerts.getResult();
        if (result != null) {
            parsedActivityImpl.setKnownActivityEmbeddingCerts(result);
        }
        boolean zHasValue = typedArray.hasValue(i3);
        if (zHasValue) {
            parsedActivityImpl.setExported(typedArray.getBoolean(i3, false));
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                if (xmlResourceParser.getName().equals("intent-filter")) {
                    parseResultUnknownTag = parseIntentFilter(parsingPackage, parsedActivityImpl, !z, z3, resources, xmlResourceParser, parseInput);
                    if (parseResultUnknownTag.isSuccess() && (parsedIntentInfoImpl2 = (ParsedIntentInfoImpl) parseResultUnknownTag.getResult()) != null) {
                        parsedActivityImpl.setOrder(Math.max(parsedIntentInfoImpl2.getIntentFilter().getOrder(), parsedActivityImpl.getOrder()));
                        parsedActivityImpl.addIntent(parsedIntentInfoImpl2);
                    }
                } else if (xmlResourceParser.getName().equals("meta-data")) {
                    parseResultUnknownTag = ParsedComponentUtils.addMetaData(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                } else if (xmlResourceParser.getName().equals("property")) {
                    parseResultUnknownTag = ParsedComponentUtils.addProperty(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                } else if (!z && !z2 && xmlResourceParser.getName().equals("preferred")) {
                    parseResultUnknownTag = parseIntentFilter(parsingPackage, parsedActivityImpl, true, z3, resources, xmlResourceParser, parseInput);
                    if (parseResultUnknownTag.isSuccess() && (parsedIntentInfoImpl = (ParsedIntentInfoImpl) parseResultUnknownTag.getResult()) != null) {
                        parsingPackage.addPreferredActivityFilter(parsedActivityImpl.getClassName(), parsedIntentInfoImpl);
                    }
                } else if (!z && !z2 && xmlResourceParser.getName().equals(TtmlUtils.TAG_LAYOUT)) {
                    parseResultUnknownTag = parseActivityWindowLayout(resources, xmlResourceParser, parseInput);
                    if (parseResultUnknownTag.isSuccess()) {
                        parsedActivityImpl.setWindowLayout((ActivityInfo.WindowLayout) parseResultUnknownTag.getResult());
                    }
                } else {
                    parseResultUnknownTag = ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                }
                if (parseResultUnknownTag.isError()) {
                    return parseInput.error(parseResultUnknownTag);
                }
            }
        }
        if (!z2 && parsedActivityImpl.getLaunchMode() != 4 && parsedActivityImpl.getMetaData().containsKey(ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE) && (string = parsedActivityImpl.getMetaData().getString(ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE)) != null && string.equals("singleInstancePerTask")) {
            parsedActivityImpl.setLaunchMode(4);
        }
        if (!z2) {
            boolean z4 = typedArray.getBoolean(59, true);
            if (!parsedActivityImpl.getMetaData().getBoolean(ParsingPackageUtils.METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES, true)) {
                z4 = false;
            }
            if (z4) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 65536);
            }
        }
        ParseResult<ActivityInfo.WindowLayout> parseResultResolveActivityWindowLayout = resolveActivityWindowLayout(parsedActivityImpl, parseInput);
        if (parseResultResolveActivityWindowLayout.isError()) {
            return parseInput.error(parseResultResolveActivityWindowLayout);
        }
        parsedActivityImpl.setWindowLayout(parseResultResolveActivityWindowLayout.getResult());
        if (!zHasValue) {
            boolean z5 = parsedActivityImpl.getIntents().size() > 0;
            if (z5) {
                ParseResult<?> parseResultDeferError = parseInput.deferError(parsedActivityImpl.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
                if (parseResultDeferError.isError()) {
                    return parseInput.error(parseResultDeferError);
                }
            }
            parsedActivityImpl.setExported(z5);
        }
        return parseInput.success(parsedActivityImpl);
    }

    private static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(ParsingPackage parsingPackage, ParsedActivityImpl parsedActivityImpl, boolean z, boolean z2, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<ParsedIntentInfoImpl> intentFilter = ParsedMainComponentUtils.parseIntentFilter(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, z2, true, true, z, true, parseInput);
        if (intentFilter.isError()) {
            return parseInput.error(intentFilter);
        }
        ParsedIntentInfoImpl result = intentFilter.getResult();
        if (result != null) {
            IntentFilter intentFilter2 = result.getIntentFilter();
            if (intentFilter2.isVisibleToInstantApp()) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
            }
            if (intentFilter2.isImplicitlyVisibleToInstantApp()) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 2097152);
            }
        }
        return parseInput.success(result);
    }

    private static int getActivityResizeMode(ParsingPackage parsingPackage, TypedArray typedArray, int i) {
        Boolean resizeableActivity = parsingPackage.getResizeableActivity();
        if (typedArray.hasValue(40) || resizeableActivity != null) {
            return typedArray.getBoolean(40, resizeableActivity != null && resizeableActivity.booleanValue()) ? 2 : 0;
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x002d A[Catch: all -> 0x0065, TryCatch #0 {all -> 0x0065, blocks: (B:3:0x0007, B:5:0x0013, B:10:0x0026, B:12:0x002d, B:16:0x0040, B:14:0x0034, B:7:0x001c), top: B:22:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ActivityInfo.WindowLayout> parseActivityWindowLayout(Resources resources, AttributeSet attributeSet, ParseInput parseInput) {
        int i;
        float fraction;
        int dimensionPixelSize;
        int type;
        int dimensionPixelSize2;
        int i2;
        float f;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.AndroidManifestLayout);
        try {
            int type2 = typedArrayObtainAttributes.getType(3);
            float fraction2 = -1.0f;
            if (type2 == 6) {
                i = 5;
                fraction = typedArrayObtainAttributes.getFraction(3, 1, 1, -1.0f);
            } else {
                if (type2 == 5) {
                    dimensionPixelSize = typedArrayObtainAttributes.getDimensionPixelSize(3, -1);
                    i = 5;
                    fraction = -1.0f;
                    type = typedArrayObtainAttributes.getType(4);
                    if (type != 6) {
                        fraction2 = typedArrayObtainAttributes.getFraction(4, 1, 1, -1.0f);
                    } else {
                        if (type == i) {
                            dimensionPixelSize2 = typedArrayObtainAttributes.getDimensionPixelSize(4, -1);
                            i2 = 1;
                            f = -1.0f;
                        }
                        return parseInput.success(new ActivityInfo.WindowLayout(dimensionPixelSize, fraction, dimensionPixelSize2, f, typedArrayObtainAttributes.getInt(0, 17), typedArrayObtainAttributes.getDimensionPixelSize(i2, -1), typedArrayObtainAttributes.getDimensionPixelSize(2, -1), typedArrayObtainAttributes.getNonConfigurationString(i, 0)));
                    }
                    i2 = 1;
                    f = fraction2;
                    dimensionPixelSize2 = -1;
                    return parseInput.success(new ActivityInfo.WindowLayout(dimensionPixelSize, fraction, dimensionPixelSize2, f, typedArrayObtainAttributes.getInt(0, 17), typedArrayObtainAttributes.getDimensionPixelSize(i2, -1), typedArrayObtainAttributes.getDimensionPixelSize(2, -1), typedArrayObtainAttributes.getNonConfigurationString(i, 0)));
                }
                i = 5;
                fraction = -1.0f;
            }
            dimensionPixelSize = -1;
            type = typedArrayObtainAttributes.getType(4);
            if (type != 6) {
            }
            i2 = 1;
            f = fraction2;
            dimensionPixelSize2 = -1;
            return parseInput.success(new ActivityInfo.WindowLayout(dimensionPixelSize, fraction, dimensionPixelSize2, f, typedArrayObtainAttributes.getInt(0, 17), typedArrayObtainAttributes.getDimensionPixelSize(i2, -1), typedArrayObtainAttributes.getDimensionPixelSize(2, -1), typedArrayObtainAttributes.getNonConfigurationString(i, 0)));
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ActivityInfo.WindowLayout> resolveActivityWindowLayout(ParsedActivity parsedActivity, ParseInput parseInput) {
        if (!parsedActivity.getMetaData().containsKey("android.activity_window_layout_affinity")) {
            return parseInput.success(parsedActivity.getWindowLayout());
        }
        if (parsedActivity.getWindowLayout() != null && parsedActivity.getWindowLayout().windowLayoutAffinity != null) {
            return parseInput.success(parsedActivity.getWindowLayout());
        }
        String string = parsedActivity.getMetaData().getString("android.activity_window_layout_affinity");
        ActivityInfo.WindowLayout windowLayout = parsedActivity.getWindowLayout();
        if (windowLayout == null) {
            windowLayout = new ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1, string);
        } else {
            windowLayout.windowLayoutAffinity = string;
        }
        return parseInput.success(windowLayout);
    }
}
