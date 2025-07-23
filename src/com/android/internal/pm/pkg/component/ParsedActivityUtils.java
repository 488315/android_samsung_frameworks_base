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
import android.util.ArraySet;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
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

    public static ParseResult<ParsedActivity> parseActivityOrReceiver(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws XmlPullParserException, IOException {
        int i2;
        String packageName = parsingPackage.getPackageName();
        ParsedActivityImpl parsedActivityImpl = new ParsedActivityImpl();
        boolean equals = "receiver".equals(xmlResourceParser.getName());
        String str2 = "<" + xmlResourceParser.getName() + ">";
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivity);
        try {
            ParseResult<?> parseMainComponent = ParsedMainComponentUtils.parseMainComponent(parsedActivityImpl, str2, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 30, 17, 42, 5, 2, 1, 23, 3, 7, 44, 48, 57, 66);
            if (parseMainComponent.isError()) {
                ParseResult<ParsedActivity> error = parseInput.error(parseMainComponent);
                obtainAttributes.recycle();
                return error;
            }
            if (equals && parsingPackage.isSaveStateDisallowed() && Objects.equals(parsedActivityImpl.getProcessName(), packageName)) {
                ParseResult<ParsedActivity> error2 = parseInput.error("Heavy-weight applications can not have receivers in main process");
                obtainAttributes.recycle();
                return error2;
            }
            parsedActivityImpl.setTheme(obtainAttributes.getResourceId(0, 0)).setUiOptions(obtainAttributes.getInt(26, parsingPackage.getUiOptions()));
            parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(64, 19, parsingPackage.isTaskReparentingAllowed(), obtainAttributes) | ComponentParseUtils.flag(8, 18, obtainAttributes) | ComponentParseUtils.flag(4, 11, obtainAttributes) | ComponentParseUtils.flag(32, 13, obtainAttributes) | ComponentParseUtils.flag(256, 22, obtainAttributes) | ComponentParseUtils.flag(2, 10, obtainAttributes) | ComponentParseUtils.flag(2048, 24, obtainAttributes) | ComponentParseUtils.flag(1, 9, obtainAttributes) | ComponentParseUtils.flag(128, 21, obtainAttributes) | ComponentParseUtils.flag(1024, 39, obtainAttributes) | ComponentParseUtils.flag(1024, 29, obtainAttributes) | ComponentParseUtils.flag(16, 12, obtainAttributes) | ComponentParseUtils.flag(536870912, 64, obtainAttributes));
            if (equals) {
                parsedActivityImpl.setLaunchMode(0).setConfigChanges(0).setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(1073741824, 28, obtainAttributes));
            } else {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | ComponentParseUtils.flag(512, 25, parsingPackage.isHardwareAccelerated(), obtainAttributes) | ComponentParseUtils.flag(Integer.MIN_VALUE, 31, obtainAttributes) | ComponentParseUtils.flag(262144, 67, obtainAttributes) | ComponentParseUtils.flag(8192, 35, obtainAttributes) | ComponentParseUtils.flag(4096, 36, obtainAttributes) | ComponentParseUtils.flag(16384, 37, obtainAttributes) | ComponentParseUtils.flag(8388608, 51, obtainAttributes) | ComponentParseUtils.flag(4194304, 41, obtainAttributes) | ComponentParseUtils.flag(16777216, 52, obtainAttributes) | ComponentParseUtils.flag(33554432, 56, obtainAttributes) | ComponentParseUtils.flag(268435456, 60, obtainAttributes));
                parsedActivityImpl.setPrivateFlags(parsedActivityImpl.getPrivateFlags() | ComponentParseUtils.flag(1, 54, obtainAttributes) | ComponentParseUtils.flag(2, 58, true, obtainAttributes));
                parsedActivityImpl.setColorMode(obtainAttributes.getInt(49, 0)).setDocumentLaunchMode(obtainAttributes.getInt(33, 0)).setLaunchMode(obtainAttributes.getInt(14, 0)).setLockTaskLaunchMode(obtainAttributes.getInt(38, 0)).setMaxRecents(obtainAttributes.getInt(34, ActivityTaskManager.getDefaultAppRecentsLimitStatic())).setPersistableMode(obtainAttributes.getInteger(32, 0)).setRequestedVrComponent(obtainAttributes.getString(43)).setRotationAnimation(obtainAttributes.getInt(46, -1)).setSoftInputMode(obtainAttributes.getInt(20, 0)).setConfigChanges(getActivityConfigChanges(obtainAttributes.getInt(16, 0), obtainAttributes.getInt(47, 0)));
                int i3 = obtainAttributes.getInt(15, -1);
                int activityResizeMode = getActivityResizeMode(parsingPackage, obtainAttributes, i3);
                parsedActivityImpl.setScreenOrientation(i3).setResizeMode(activityResizeMode);
                if (obtainAttributes.hasValue(50) && obtainAttributes.getType(50) == 4) {
                    parsedActivityImpl.setMaxAspectRatio(activityResizeMode, obtainAttributes.getFloat(50, 0.0f));
                }
                if (obtainAttributes.hasValue(53)) {
                    i2 = 4;
                    if (obtainAttributes.getType(53) == 4) {
                        parsedActivityImpl.setMinAspectRatio(activityResizeMode, obtainAttributes.getFloat(53, 0.0f));
                    }
                } else {
                    i2 = 4;
                }
                if (obtainAttributes.hasValue(62)) {
                    boolean z2 = obtainAttributes.getBoolean(62, false);
                    int privateFlags = parsedActivityImpl.getPrivateFlags();
                    if (!z2) {
                        i2 = 8;
                    }
                    parsedActivityImpl.setPrivateFlags(privateFlags | i2);
                }
            }
            ParseResult<String> buildTaskAffinityName = ComponentParseUtils.buildTaskAffinityName(packageName, parsingPackage.getTaskAffinity(), obtainAttributes.getNonConfigurationString(8, 1024), parseInput);
            if (buildTaskAffinityName.isError()) {
                ParseResult<ParsedActivity> error3 = parseInput.error(buildTaskAffinityName);
                obtainAttributes.recycle();
                return error3;
            }
            parsedActivityImpl.setTaskAffinity(buildTaskAffinityName.getResult());
            boolean z3 = obtainAttributes.getBoolean(45, false);
            if (z3) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
                parsingPackage.setVisibleToInstantApps(true);
            }
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(63, 0);
            if (nonConfigurationString != null && FrameworkParsingPackageUtils.validateName(nonConfigurationString, false, false) != null) {
                ParseResult<ParsedActivity> error4 = parseInput.error("requiredDisplayCategory attribute can only consist of alphanumeric characters, '_', and '.'");
                obtainAttributes.recycle();
                return error4;
            }
            parsedActivityImpl.setRequiredDisplayCategory(nonConfigurationString);
            parsedActivityImpl.setRequireContentUriPermissionFromCaller(obtainAttributes.getInt(65, 0));
            try {
                ParseResult<ParsedActivity> parseActivityOrAlias = parseActivityOrAlias(parsedActivityImpl, parsingPackage, str2, xmlResourceParser, resources, obtainAttributes, equals, false, z3, parseInput, 27, 4, 6);
                obtainAttributes.recycle();
                return parseActivityOrAlias;
            } catch (Throwable th) {
                th = th;
                obtainAttributes = obtainAttributes;
                obtainAttributes.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static ParseResult<ParsedActivity> parseActivityAlias(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, String str, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsedActivity parsedActivity;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivityAlias);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(7, 1024);
            if (nonConfigurationString == null) {
                ParseResult<ParsedActivity> error = parseInput.error("<activity-alias> does not specify android:targetActivity");
                obtainAttributes.recycle();
                return error;
            }
            String packageName = parsingPackage.getPackageName();
            String buildClassName = ParsingUtils.buildClassName(packageName, nonConfigurationString);
            if (buildClassName == null) {
                ParseResult<ParsedActivity> error2 = parseInput.error("Empty class name in package " + packageName);
                obtainAttributes.recycle();
                return error2;
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
                if (buildClassName.equals(parsedActivity.getName())) {
                    break;
                }
                i++;
            }
            if (parsedActivity == null) {
                ParseResult<ParsedActivity> error3 = parseInput.error("<activity-alias> target activity " + buildClassName + " not found in manifest with activities = " + parsingPackage.getActivities() + ", parsedActivities = " + activities);
                obtainAttributes.recycle();
                return error3;
            }
            ParsedActivityImpl makeAlias = ParsedActivityImpl.makeAlias(buildClassName, parsedActivity);
            String str2 = "<" + xmlResourceParser.getName() + ">";
            try {
                ParseResult<?> parseMainComponent = ParsedMainComponentUtils.parseMainComponent(makeAlias, str2, null, parsingPackage, obtainAttributes, 0, z, str, parseInput, 10, 6, -1, 4, 1, 0, 8, 2, -1, 11, -1, 12, 15);
                if (parseMainComponent.isError()) {
                    ParseResult<ParsedActivity> error4 = parseInput.error(parseMainComponent);
                    obtainAttributes.recycle();
                    return error4;
                }
                ParseResult<ParsedActivity> parseActivityOrAlias = parseActivityOrAlias(makeAlias, parsingPackage, str2, xmlResourceParser, resources, obtainAttributes, false, true, (makeAlias.getFlags() & 1048576) != 0, parseInput, 9, 3, 5);
                obtainAttributes.recycle();
                return parseActivityOrAlias;
            } catch (Throwable th) {
                th = th;
                obtainAttributes = obtainAttributes;
                obtainAttributes.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x016b, code lost:
    
        if (r20 != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0172, code lost:
    
        if (r13.getLaunchMode() == 4) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x017e, code lost:
    
        if (r13.getMetaData().containsKey(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE) == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0180, code lost:
    
        r14 = r13.getMetaData().getString(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0188, code lost:
    
        if (r14 == null) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0191, code lost:
    
        if (r14.equals("singleInstancePerTask") == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0193, code lost:
    
        r13.setLaunchMode(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0196, code lost:
    
        if (r20 != false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0198, code lost:
    
        r14 = r18.getBoolean(59, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01a8, code lost:
    
        if (r13.getMetaData().getBoolean(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES, true) != false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01aa, code lost:
    
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01ab, code lost:
    
        if (r14 == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01ad, code lost:
    
        r13.setFlags(r13.getFlags() | 65536);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01b7, code lost:
    
        r14 = resolveActivityWindowLayout(r13, r22);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01bf, code lost:
    
        if (r14.isError() == false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01c5, code lost:
    
        return r22.error(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01c6, code lost:
    
        r13.setWindowLayout(r14.getResult());
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01cf, code lost:
    
        if (r9 != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01d9, code lost:
    
        if (r13.getIntents().size() <= 0) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01db, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01dc, code lost:
    
        if (r8 == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01de, code lost:
    
        r14 = r22.deferError(r13.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01fe, code lost:
    
        if (r14.isError() == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0204, code lost:
    
        return r22.error(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0205, code lost:
    
        r13.setExported(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x020c, code lost:
    
        return r22.success(r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityOrAlias(com.android.internal.pm.pkg.component.ParsedActivityImpl r13, com.android.internal.pm.pkg.parsing.ParsingPackage r14, java.lang.String r15, android.content.res.XmlResourceParser r16, android.content.res.Resources r17, android.content.res.TypedArray r18, boolean r19, boolean r20, boolean r21, android.content.pm.parsing.result.ParseInput r22, int r23, int r24, int r25) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrAlias(com.android.internal.pm.pkg.component.ParsedActivityImpl, com.android.internal.pm.pkg.parsing.ParsingPackage, java.lang.String, android.content.res.XmlResourceParser, android.content.res.Resources, android.content.res.TypedArray, boolean, boolean, boolean, android.content.pm.parsing.result.ParseInput, int, int, int):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(ParsingPackage parsingPackage, ParsedActivityImpl parsedActivityImpl, boolean z, boolean z2, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws IOException, XmlPullParserException {
        ParseResult<ParsedIntentInfoImpl> parseIntentFilter = ParsedMainComponentUtils.parseIntentFilter(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, z2, true, true, z, true, parseInput);
        if (parseIntentFilter.isError()) {
            return parseInput.error(parseIntentFilter);
        }
        ParsedIntentInfoImpl result = parseIntentFilter.getResult();
        if (result != null) {
            IntentFilter intentFilter = result.getIntentFilter();
            if (intentFilter.isVisibleToInstantApp()) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
            }
            if (intentFilter.isImplicitlyVisibleToInstantApp()) {
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002d A[Catch: all -> 0x0065, TryCatch #0 {all -> 0x0065, blocks: (B:3:0x0007, B:5:0x0013, B:7:0x0026, B:9:0x002d, B:11:0x0040, B:17:0x0034, B:19:0x001c), top: B:2:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<android.content.pm.ActivityInfo.WindowLayout> parseActivityWindowLayout(android.content.res.Resources r10, android.util.AttributeSet r11, android.content.pm.parsing.result.ParseInput r12) {
        /*
            int[] r0 = com.android.internal.R.styleable.AndroidManifestLayout
            android.content.res.TypedArray r10 = r10.obtainAttributes(r11, r0)
            r11 = 3
            int r0 = r10.getType(r11)     // Catch: java.lang.Throwable -> L65
            r1 = 6
            r2 = 5
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r4 = 1
            r5 = -1
            if (r0 != r1) goto L1a
            float r11 = r10.getFraction(r11, r4, r4, r3)     // Catch: java.lang.Throwable -> L65
            r0 = r2
            r2 = r11
            goto L25
        L1a:
            if (r0 != r2) goto L23
            int r11 = r10.getDimensionPixelSize(r11, r5)     // Catch: java.lang.Throwable -> L65
            r0 = r2
            r2 = r3
            goto L26
        L23:
            r0 = r2
            r2 = r3
        L25:
            r11 = r5
        L26:
            r6 = 4
            int r7 = r10.getType(r6)     // Catch: java.lang.Throwable -> L65
            if (r7 != r1) goto L32
            float r3 = r10.getFraction(r6, r4, r4, r3)     // Catch: java.lang.Throwable -> L65
            goto L3d
        L32:
            if (r7 != r0) goto L3d
            int r1 = r10.getDimensionPixelSize(r6, r5)     // Catch: java.lang.Throwable -> L65
            r9 = r3
            r3 = r1
            r1 = r4
            r4 = r9
            goto L40
        L3d:
            r1 = r4
            r4 = r3
            r3 = r5
        L40:
            r6 = 17
            r7 = 0
            int r6 = r10.getInt(r7, r6)     // Catch: java.lang.Throwable -> L65
            int r1 = r10.getDimensionPixelSize(r1, r5)     // Catch: java.lang.Throwable -> L65
            r8 = 2
            int r5 = r10.getDimensionPixelSize(r8, r5)     // Catch: java.lang.Throwable -> L65
            java.lang.String r8 = r10.getNonConfigurationString(r0, r7)     // Catch: java.lang.Throwable -> L65
            android.content.pm.ActivityInfo$WindowLayout r0 = new android.content.pm.ActivityInfo$WindowLayout     // Catch: java.lang.Throwable -> L65
            r7 = r5
            r5 = r6
            r6 = r1
            r1 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L65
            android.content.pm.parsing.result.ParseResult r11 = r12.success(r0)     // Catch: java.lang.Throwable -> L65
            r10.recycle()
            return r11
        L65:
            r0 = move-exception
            r11 = r0
            r10.recycle()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityWindowLayout(android.content.res.Resources, android.util.AttributeSet, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
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
