package com.android.internal.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArraySet;
import android.util.AttributeSet;

import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.ArrayUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ParsedActivityUtils {
    public static final boolean LOG_UNSAFE_BROADCASTS = false;
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    public static final Set<String> SAFE_BROADCASTS = new ArraySet();
    private static final String TAG = "PackageParsing";

    static {
        SAFE_BROADCASTS.add("android.intent.action.BOOT_COMPLETED");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x02d6 A[Catch: all -> 0x02de, TRY_ENTER, TRY_LEAVE, TryCatch #8 {all -> 0x02de, blocks: (B:59:0x024a, B:61:0x025e, B:63:0x0265, B:64:0x026c, B:66:0x0274, B:68:0x027b, B:69:0x0282, B:71:0x028a, B:74:0x0297, B:30:0x02d6, B:36:0x02f3, B:41:0x0309, B:43:0x030f), top: B:58:0x024a }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x02e0 A[Catch: all -> 0x035a, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x035a, blocks: (B:24:0x00c5, B:28:0x02bf, B:33:0x02e0, B:38:0x0302, B:46:0x031a, B:27:0x02a7), top: B:23:0x00c5 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0151 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.component.ParsedActivity>
            parseActivityOrReceiver(
                    java.lang.String[] r27,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r28,
                    android.content.res.Resources r29,
                    android.content.res.XmlResourceParser r30,
                    int r31,
                    boolean r32,
                    java.lang.String r33,
                    android.content.pm.parsing.result.ParseInput r34)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 883
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrReceiver(java.lang.String[],"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser, int,"
                    + " boolean, java.lang.String,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    public static ParseResult<ParsedActivity> parseActivityAlias(
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            boolean useRoundIcon,
            String defaultSplitName,
            ParseInput input)
            throws XmlPullParserException, IOException {
        TypedArray sa;
        ParsedActivity target;
        TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestActivityAlias);
        try {
            String targetActivity = sa2.getNonConfigurationString(7, 1024);
            try {
                if (targetActivity == null) {
                    ParseResult<ParsedActivity> error =
                            input.error("<activity-alias> does not specify android:targetActivity");
                    sa2.recycle();
                    return error;
                }
                String packageName = pkg.getPackageName();
                String targetActivity2 = ParsingUtils.buildClassName(packageName, targetActivity);
                if (targetActivity2 == null) {
                    ParseResult<ParsedActivity> error2 =
                            input.error("Empty class name in package " + packageName);
                    sa2.recycle();
                    return error2;
                }
                List<ParsedActivity> activities = pkg.getActivities();
                int activitiesSize = ArrayUtils.size(activities);
                int i = 0;
                while (true) {
                    if (i >= activitiesSize) {
                        target = null;
                        break;
                    }
                    ParsedActivity t = activities.get(i);
                    if (targetActivity2.equals(t.getName())) {
                        target = t;
                        break;
                    }
                    i++;
                }
                if (target == null) {
                    ParseResult<ParsedActivity> error3 =
                            input.error(
                                    "<activity-alias> target activity "
                                            + targetActivity2
                                            + " not found in manifest with activities = "
                                            + pkg.getActivities()
                                            + ", parsedActivities = "
                                            + activities);
                    sa2.recycle();
                    return error3;
                }
                ParsedActivityImpl activity = ParsedActivityImpl.makeAlias(targetActivity2, target);
                String tag = "<" + parser.getName() + ">";
                sa = sa2;
                try {
                    ParseResult<ParsedActivityImpl> result =
                            ParsedMainComponentUtils.parseMainComponent(
                                    activity,
                                    tag,
                                    null,
                                    pkg,
                                    sa2,
                                    0,
                                    useRoundIcon,
                                    defaultSplitName,
                                    input,
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
                    if (result.isError()) {
                        ParseResult<ParsedActivity> error4 = input.error(result);
                        sa.recycle();
                        return error4;
                    }
                    boolean visibleToEphemeral = (activity.getFlags() & 1048576) != 0;
                    ParseResult<ParsedActivity> parseActivityOrAlias =
                            parseActivityOrAlias(
                                    activity,
                                    pkg,
                                    tag,
                                    parser,
                                    res,
                                    sa,
                                    false,
                                    true,
                                    visibleToEphemeral,
                                    input,
                                    9,
                                    3,
                                    5);
                    sa.recycle();
                    return parseActivityOrAlias;
                } catch (Throwable th) {
                    th = th;
                    sa.recycle();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sa = sa2;
            }
        } catch (Throwable th3) {
            th = th3;
            sa = sa2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x01e2, code lost:

       if (r29 != false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x01e9, code lost:

       if (r22.getLaunchMode() == 4) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01f5, code lost:

       if (r22.getMetaData().containsKey(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE) == false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x01f7, code lost:

       r1 = r22.getMetaData().getString(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE);
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01ff, code lost:

       if (r1 == null) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0208, code lost:

       if (r1.equals("singleInstancePerTask") == false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x020a, code lost:

       r22.setLaunchMode(4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x020d, code lost:

       if (r29 != false) goto L99;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x020f, code lost:

       r1 = r27.getBoolean(59, true);
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x021f, code lost:

       if (r22.getMetaData().getBoolean(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES, true) != false) goto L97;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0221, code lost:

       r1 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0222, code lost:

       if (r1 == false) goto L99;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0224, code lost:

       r22.setFlags(r22.getFlags() | 65536);
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x022e, code lost:

       r1 = resolveActivityWindowLayout(r22, r31);
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0237, code lost:

       if (r1.isError() == false) goto L103;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x023d, code lost:

       return r31.error(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x023e, code lost:

       r22.setWindowLayout(r1.getResult());
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0247, code lost:

       if (r16 != false) goto L116;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0251, code lost:

       if (r22.getIntents().size() <= 0) goto L108;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0254, code lost:

       r6 = r17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0256, code lost:

       r2 = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0257, code lost:

       if (r2 == false) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0259, code lost:

       r3 = r31.deferError(r22.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0287, code lost:

       if (r3.isError() == false) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x028d, code lost:

       return r31.error((android.content.pm.parsing.result.ParseResult<?>) r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x028e, code lost:

       r22.setExported(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0295, code lost:

       return r31.success(r22);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.component.ParsedActivity>
            parseActivityOrAlias(
                    com.android.internal.pm.pkg.component.ParsedActivityImpl r22,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r23,
                    java.lang.String r24,
                    android.content.res.XmlResourceParser r25,
                    android.content.res.Resources r26,
                    android.content.res.TypedArray r27,
                    boolean r28,
                    boolean r29,
                    boolean r30,
                    android.content.pm.parsing.result.ParseInput r31,
                    int r32,
                    int r33,
                    int r34)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 662
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrAlias(com.android.internal.pm.pkg.component.ParsedActivityImpl,"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage, java.lang.String,"
                    + " android.content.res.XmlResourceParser, android.content.res.Resources,"
                    + " android.content.res.TypedArray, boolean, boolean, boolean,"
                    + " android.content.pm.parsing.result.ParseInput, int, int,"
                    + " int):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(
            ParsingPackage pkg,
            ParsedActivityImpl activity,
            boolean allowImplicitEphemeralVisibility,
            boolean visibleToEphemeral,
            Resources resources,
            XmlResourceParser parser,
            ParseInput input)
            throws IOException, XmlPullParserException {
        ParseResult<ParsedIntentInfoImpl> result =
                ParsedMainComponentUtils.parseIntentFilter(
                        activity,
                        pkg,
                        resources,
                        parser,
                        visibleToEphemeral,
                        true,
                        true,
                        allowImplicitEphemeralVisibility,
                        true,
                        input);
        if (result.isError()) {
            return input.error(result);
        }
        ParsedIntentInfoImpl intent = result.getResult();
        if (intent != null) {
            IntentFilter intentFilter = intent.getIntentFilter();
            if (intentFilter.isVisibleToInstantApp()) {
                activity.setFlags(activity.getFlags() | 1048576);
            }
            if (intentFilter.isImplicitlyVisibleToInstantApp()) {
                activity.setFlags(activity.getFlags() | 2097152);
            }
        }
        return input.success(intent);
    }

    private static int getActivityResizeMode(
            ParsingPackage pkg, TypedArray sa, int screenOrientation) {
        Boolean resizeableActivity = pkg.getResizeableActivity();
        if (sa.hasValue(40) || resizeableActivity != null) {
            if (!sa.getBoolean(
                    40, resizeableActivity != null && resizeableActivity.booleanValue())) {
                return 0;
            }
            return 2;
        }
        if (pkg.isResizeableActivityViaSdkVersion()) {
            return 1;
        }
        if (ActivityInfo.isFixedOrientationPortrait(screenOrientation)) {
            return 6;
        }
        if (ActivityInfo.isFixedOrientationLandscape(screenOrientation)) {
            return 5;
        }
        if (screenOrientation == 14) {
            return 7;
        }
        return 4;
    }

    private static ParseResult<ActivityInfo.WindowLayout> parseActivityWindowLayout(
            Resources res, AttributeSet attrs, ParseInput input) {
        ActivityInfo.WindowLayout windowLayout;
        TypedArray sw = res.obtainAttributes(attrs, R.styleable.AndroidManifestLayout);
        int width = -1;
        float widthFraction = -1.0f;
        int height = -1;
        float heightFraction = -1.0f;
        try {
            int widthType = sw.getType(3);
            if (widthType == 6) {
                widthFraction = sw.getFraction(3, 1, 1, -1.0f);
            } else if (widthType == 5) {
                width = sw.getDimensionPixelSize(3, -1);
            }
            int heightType = sw.getType(4);
            if (heightType == 6) {
                heightFraction = sw.getFraction(4, 1, 1, -1.0f);
            } else if (heightType == 5) {
                height = sw.getDimensionPixelSize(4, -1);
            }
            int gravity = sw.getInt(0, 17);
            int minWidth = sw.getDimensionPixelSize(1, -1);
            int minHeight = sw.getDimensionPixelSize(2, -1);
            String windowLayoutAffinity = sw.getNonConfigurationString(5, 0);
            windowLayout =
                    new ActivityInfo.WindowLayout(
                            width,
                            widthFraction,
                            height,
                            heightFraction,
                            gravity,
                            minWidth,
                            minHeight,
                            windowLayoutAffinity);
        } catch (Throwable th) {
            th = th;
        }
        try {
            ParseResult<ActivityInfo.WindowLayout> success = input.success(windowLayout);
            sw.recycle();
            return success;
        } catch (Throwable th2) {
            th = th2;
            sw.recycle();
            throw th;
        }
    }

    private static ParseResult<ActivityInfo.WindowLayout> resolveActivityWindowLayout(
            ParsedActivity activity, ParseInput input) {
        if (!activity.getMetaData().containsKey("android.activity_window_layout_affinity")) {
            return input.success(activity.getWindowLayout());
        }
        if (activity.getWindowLayout() != null
                && activity.getWindowLayout().windowLayoutAffinity != null) {
            return input.success(activity.getWindowLayout());
        }
        String windowLayoutAffinity =
                activity.getMetaData().getString("android.activity_window_layout_affinity");
        ActivityInfo.WindowLayout layout = activity.getWindowLayout();
        if (layout == null) {
            layout =
                    new ActivityInfo.WindowLayout(
                            -1, -1.0f, -1, -1.0f, 0, -1, -1, windowLayoutAffinity);
        } else {
            layout.windowLayoutAffinity = windowLayoutAffinity;
        }
        return input.success(layout);
    }

    public static int getActivityConfigChanges(int configChanges, int recreateOnConfigChanges) {
        return ((~recreateOnConfigChanges) & 3) | configChanges;
    }
}
