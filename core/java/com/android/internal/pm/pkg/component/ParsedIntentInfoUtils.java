package com.android.internal.pm.pkg.component;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.UriRelativeFilter;
import android.content.UriRelativeFilterGroup;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser;
import com.samsung.android.core.pm.allowlist.RestrictedReceiverFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedIntentInfoUtils {
    public static final boolean DEBUG = false;
    private static final String TAG = "PackageParsing";
    private static final RestrictedReceiverFilter sRRFilter = RestrictedReceiverFilter.getInstance();

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public static ParseResult<ParsedIntentInfoImpl> parseIntentInfo(String className, ParsingPackage pkg, Resources res, XmlResourceParser parser, boolean allowGlobs, boolean allowAutoVerify, ParseInput input) throws XmlPullParserException, IOException {
        List<RestrictedReceiverFilter.RestrictedAction> restrictedActions;
        int i;
        int depth;
        List<RestrictedReceiverFilter.RestrictedAction> restrictedActions2;
        ParseResult result;
        ParsedIntentInfoImpl intentInfo = new ParsedIntentInfoImpl();
        IntentFilter intentFilter = intentInfo.getIntentFilter();
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestIntentFilter);
        int i2 = 2;
        int i3 = 0;
        try {
            intentFilter.setPriority(sa.getInt(2, 0));
            int i4 = 3;
            intentFilter.setOrder(sa.getInt(3, 0));
            TypedValue v = sa.peekValue(0);
            if (v != null) {
                intentInfo.setLabelRes(v.resourceId);
                if (v.resourceId == 0) {
                    intentInfo.setNonLocalizedLabel(v.coerceToString());
                }
            }
            if (ParsingPackageUtils.sUseRoundIcon) {
                intentInfo.setIcon(sa.getResourceId(7, 0));
            }
            int i5 = 1;
            if (intentInfo.getIcon() == 0) {
                intentInfo.setIcon(sa.getResourceId(1, 0));
            }
            if (allowAutoVerify) {
                intentFilter.setAutoVerify(sa.getBoolean(6, false));
            }
            sa.recycle();
            List<RestrictedReceiverFilter.RestrictedAction> restrictedActions3 = new ArrayList<>();
            int depth2 = parser.getDepth();
            while (true) {
                int type = parser.next();
                if (type == i5) {
                    restrictedActions = restrictedActions3;
                } else if (type == i4 && parser.getDepth() <= depth2) {
                    restrictedActions = restrictedActions3;
                } else if (type == i2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parser)) {
                    String nodeName = parser.getName();
                    switch (nodeName.hashCode()) {
                        case -1422950858:
                            if (nodeName.equals("action")) {
                                i = i3;
                                break;
                            }
                            break;
                        case -1194267734:
                            if (nodeName.equals("uri-relative-filter-group")) {
                                i = i4;
                                break;
                            }
                            break;
                        case 3076010:
                            if (nodeName.equals("data")) {
                                i = i2;
                                break;
                            }
                            break;
                        case 50511102:
                            if (nodeName.equals("category")) {
                                i = i5;
                                break;
                            }
                            break;
                    }
                    i = -1;
                    switch (i) {
                        case 0:
                            depth = depth2;
                            restrictedActions2 = restrictedActions3;
                            String value = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                            if (value == null) {
                                result = input.error("No value supplied for <android:name>");
                                break;
                            } else if (value.isEmpty()) {
                                intentFilter.addAction(value);
                                result = input.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                break;
                            } else {
                                if (sRRFilter != null) {
                                    String packageName = pkg.getPackageName();
                                    String codePath = pkg.getBaseApkPath();
                                    if (sRRFilter.filterReceiver(packageName, value)) {
                                        restrictedActions2.add(new RestrictedReceiverFilter.RestrictedAction(value, packageName, codePath));
                                    }
                                }
                                intentFilter.addAction(value);
                                ParseResult result2 = input.success(null);
                                result = result2;
                                break;
                            }
                        case 1:
                            depth = depth2;
                            restrictedActions2 = restrictedActions3;
                            String value2 = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                            if (value2 == null) {
                                result = input.error("No value supplied for <android:name>");
                                break;
                            } else if (value2.isEmpty()) {
                                intentFilter.addCategory(value2);
                                result = input.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                break;
                            } else {
                                intentFilter.addCategory(value2);
                                ParseResult result3 = input.success(null);
                                result = result3;
                                break;
                            }
                        case 2:
                            depth = depth2;
                            ParseResult result4 = parseData(intentInfo, res, parser, allowGlobs, input);
                            restrictedActions2 = restrictedActions3;
                            result = result4;
                            break;
                        case 3:
                            if (Flags.relativeReferenceIntentFilters()) {
                                depth = depth2;
                                ParseResult result5 = parseRelRefGroup(intentInfo, pkg, res, parser, allowGlobs, input);
                                restrictedActions2 = restrictedActions3;
                                result = result5;
                                break;
                            } else {
                                depth = depth2;
                                restrictedActions2 = restrictedActions3;
                                result = ParsingUtils.unknownTag("<intent-filter>", pkg, parser, input);
                                break;
                            }
                        default:
                            depth = depth2;
                            restrictedActions2 = restrictedActions3;
                            result = ParsingUtils.unknownTag("<intent-filter>", pkg, parser, input);
                            break;
                    }
                    if (result.isError()) {
                        return input.error((ParseResult<?>) result);
                    }
                    restrictedActions3 = restrictedActions2;
                    depth2 = depth;
                    i2 = 2;
                    i3 = 0;
                    i4 = 3;
                    i5 = 1;
                }
            }
            intentInfo.setHasDefault(intentFilter.hasCategory(Intent.CATEGORY_DEFAULT));
            for (RestrictedReceiverFilter.RestrictedAction ra : restrictedActions) {
                boolean removeAction = true;
                if (BroadcastReceiverListParser.isPackageXXXIntent(ra.mAction) && BroadcastReceiverListParser.hasPackageSSP(intentFilter)) {
                    removeAction = false;
                }
                if (removeAction) {
                    intentFilter.removeAction(ra.mAction);
                    sRRFilter.addViolationLog(ra.mPackageName, ra.mCodePath, ra.mAction);
                }
            }
            return input.success(intentInfo);
        } catch (Throwable th) {
            sa.recycle();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0022 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseRelRefGroup(com.android.internal.pm.pkg.component.ParsedIntentInfo r10, com.android.internal.pm.pkg.parsing.ParsingPackage r11, android.content.res.Resources r12, android.content.res.XmlResourceParser r13, boolean r14, android.content.pm.parsing.result.ParseInput r15) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            android.content.IntentFilter r0 = r10.getIntentFilter()
            int[] r1 = com.android.internal.R.styleable.AndroidManifestUriRelativeFilterGroup
            android.content.res.TypedArray r1 = r12.obtainAttributes(r13, r1)
            r2 = 0
            r3 = 1
            r4 = 0
            boolean r5 = r1.getBoolean(r4, r3)     // Catch: java.lang.Throwable -> L86
            if (r5 != 0) goto L14
            r2 = 1
        L14:
            android.content.UriRelativeFilterGroup r5 = new android.content.UriRelativeFilterGroup     // Catch: java.lang.Throwable -> L86
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L86
            r2 = r5
            r1.recycle()
            int r5 = r13.getDepth()
        L22:
            int r6 = r13.next()
            r7 = r6
            if (r6 == r3) goto L73
            r6 = 3
            if (r7 != r6) goto L32
            int r6 = r13.getDepth()
            if (r6 <= r5) goto L73
        L32:
            r6 = 2
            if (r7 == r6) goto L36
            goto L22
        L36:
            com.android.internal.pm.pkg.component.AconfigFlags r6 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getAconfigFlags()
            boolean r6 = r6.skipCurrentElement(r13)
            if (r6 == 0) goto L41
            goto L22
        L41:
            java.lang.String r6 = r13.getName()
            int r8 = r6.hashCode()
            switch(r8) {
                case 3076010: goto L4d;
                default: goto L4c;
            }
        L4c:
            goto L57
        L4d:
            java.lang.String r8 = "data"
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L4c
            r8 = r4
            goto L58
        L57:
            r8 = -1
        L58:
            switch(r8) {
                case 0: goto L62;
                default: goto L5b;
            }
        L5b:
            java.lang.String r8 = "<uri-relative-filter-group>"
            android.content.pm.parsing.result.ParseResult r8 = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(r8, r11, r13, r15)
            goto L67
        L62:
            android.content.pm.parsing.result.ParseResult r8 = parseRelRefGroupData(r2, r12, r13, r14, r15)
        L67:
            boolean r9 = r8.isError()
            if (r9 == 0) goto L72
            android.content.pm.parsing.result.ParseResult r3 = r15.error(r8)
            return r3
        L72:
            goto L22
        L73:
            java.util.Collection r3 = r2.getUriRelativeFilters()
            int r3 = r3.size()
            if (r3 <= 0) goto L80
            r0.addUriRelativeFilterGroup(r2)
        L80:
            r3 = 0
            android.content.pm.parsing.result.ParseResult r3 = r15.success(r3)
            return r3
        L86:
            r2 = move-exception
            r1.recycle()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedIntentInfoUtils.parseRelRefGroup(com.android.internal.pm.pkg.component.ParsedIntentInfo, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, boolean, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedIntentInfo> parseRelRefGroupData(UriRelativeFilterGroup group, Resources res, XmlResourceParser parser, boolean allowGlobs, ParseInput input) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestData);
        try {
            String str = sa.getNonConfigurationString(4, 0);
            if (str != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(0, 0, str));
            }
            String str2 = sa.getNonConfigurationString(5, 0);
            if (str2 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(0, 1, str2));
            }
            String str3 = sa.getNonConfigurationString(6, 0);
            if (str3 != null) {
                if (!allowGlobs) {
                    return input.error("pathPattern not allowed here; path must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(0, 2, str3));
            }
            String str4 = sa.getNonConfigurationString(14, 0);
            if (str4 != null) {
                if (!allowGlobs) {
                    return input.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(0, 3, str4));
            }
            String str5 = sa.getNonConfigurationString(12, 0);
            if (str5 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(0, 4, str5));
            }
            String str6 = sa.getNonConfigurationString(7, 0);
            if (str6 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(2, 0, str6));
            }
            String str7 = sa.getNonConfigurationString(21, 0);
            if (str7 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(2, 1, str7));
            }
            String str8 = sa.getNonConfigurationString(22, 0);
            if (str8 != null) {
                if (!allowGlobs) {
                    return input.error("fragmentPattern not allowed here; fragment must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(2, 2, str8));
            }
            String str9 = sa.getNonConfigurationString(23, 0);
            if (str9 != null) {
                if (!allowGlobs) {
                    return input.error("fragmentAdvancedPattern not allowed here; fragment must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(2, 3, str9));
            }
            String str10 = sa.getNonConfigurationString(24, 0);
            if (str10 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(2, 4, str10));
            }
            String str11 = sa.getNonConfigurationString(16, 0);
            if (str11 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(1, 0, str11));
            }
            String str12 = sa.getNonConfigurationString(17, 0);
            if (str12 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(1, 1, str12));
            }
            String str13 = sa.getNonConfigurationString(18, 0);
            if (str13 != null) {
                if (!allowGlobs) {
                    return input.error("queryPattern not allowed here; query must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(1, 2, str13));
            }
            String str14 = sa.getNonConfigurationString(19, 0);
            if (str14 != null) {
                if (!allowGlobs) {
                    return input.error("queryAdvancedPattern not allowed here; query must be literal");
                }
                group.addUriRelativeFilter(new UriRelativeFilter(1, 3, str14));
            }
            String str15 = sa.getNonConfigurationString(20, 0);
            if (str15 != null) {
                group.addUriRelativeFilter(new UriRelativeFilter(1, 4, str15));
            }
            return input.success(null);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsedIntentInfo> parseData(ParsedIntentInfo intentInfo, Resources resources, XmlResourceParser parser, boolean allowGlobs, ParseInput input) {
        IntentFilter intentFilter = intentInfo.getIntentFilter();
        TypedArray sa = resources.obtainAttributes(parser, R.styleable.AndroidManifestData);
        try {
            String str = sa.getNonConfigurationString(0, 0);
            if (str != null) {
                intentFilter.addDataType(str);
            }
            String str2 = sa.getNonConfigurationString(11, 0);
            if (str2 != null) {
                intentFilter.addMimeGroup(str2);
            }
            String str3 = sa.getNonConfigurationString(1, 0);
            if (str3 != null) {
                intentFilter.addDataScheme(str3);
            }
            String str4 = sa.getNonConfigurationString(8, 0);
            if (str4 != null) {
                intentFilter.addDataSchemeSpecificPart(str4, 0);
            }
            String str5 = sa.getNonConfigurationString(9, 0);
            if (str5 != null) {
                intentFilter.addDataSchemeSpecificPart(str5, 1);
            }
            String str6 = sa.getNonConfigurationString(10, 0);
            if (str6 != null) {
                if (!allowGlobs) {
                    return input.error("sspPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(str6, 2);
            }
            String str7 = sa.getNonConfigurationString(15, 0);
            if (str7 != null) {
                if (!allowGlobs) {
                    return input.error("sspAdvancedPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(str7, 3);
            }
            String str8 = sa.getNonConfigurationString(13, 0);
            if (str8 != null) {
                intentFilter.addDataSchemeSpecificPart(str8, 4);
            }
            String host = sa.getNonConfigurationString(2, 0);
            String port = sa.getNonConfigurationString(3, 0);
            if (host != null) {
                intentFilter.addDataAuthority(host, port);
            }
            String str9 = sa.getNonConfigurationString(4, 0);
            if (str9 != null) {
                intentFilter.addDataPath(str9, 0);
            }
            String str10 = sa.getNonConfigurationString(5, 0);
            if (str10 != null) {
                intentFilter.addDataPath(str10, 1);
            }
            String str11 = sa.getNonConfigurationString(6, 0);
            if (str11 != null) {
                if (!allowGlobs) {
                    return input.error("pathPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(str11, 2);
            }
            String str12 = sa.getNonConfigurationString(14, 0);
            if (str12 != null) {
                if (!allowGlobs) {
                    return input.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(str12, 3);
            }
            String str13 = sa.getNonConfigurationString(12, 0);
            if (str13 != null) {
                intentFilter.addDataPath(str13, 4);
            }
            return input.success(null);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            return input.error(e.toString());
        } finally {
            sa.recycle();
        }
    }
}
