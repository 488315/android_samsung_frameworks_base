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
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedIntentInfoUtils {
    public static final boolean DEBUG = false;
    private static final String TAG = "PackageParsing";
    private static final RestrictedReceiverFilter sRRFilter = RestrictedReceiverFilter.getInstance();

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public static ParseResult<ParsedIntentInfoImpl> parseIntentInfo(String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, boolean z2, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<?> unknownTag;
        ParseResult<ParsedIntentInfo> parseRelRefGroup;
        Resources resources2 = resources;
        ParsedIntentInfoImpl parsedIntentInfoImpl = new ParsedIntentInfoImpl();
        IntentFilter intentFilter = parsedIntentInfoImpl.getIntentFilter();
        TypedArray obtainAttributes = resources2.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestIntentFilter);
        int i = 2;
        int i2 = 0;
        try {
            intentFilter.setPriority(obtainAttributes.getInt(2, 0));
            int i3 = 3;
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
            int i4 = 1;
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
                if (next != i4 && (next != i3 || xmlResourceParser.getDepth() > depth)) {
                    if (next == i && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                        String name = xmlResourceParser.getName();
                        name.hashCode();
                        int i5 = -1;
                        switch (name.hashCode()) {
                            case -1422950858:
                                if (name.equals("action")) {
                                    i5 = i2;
                                    break;
                                }
                                break;
                            case -1194267734:
                                if (name.equals("uri-relative-filter-group")) {
                                    i5 = i4;
                                    break;
                                }
                                break;
                            case 3076010:
                                if (name.equals("data")) {
                                    i5 = i;
                                    break;
                                }
                                break;
                            case 50511102:
                                if (name.equals("category")) {
                                    i5 = i3;
                                    break;
                                }
                                break;
                        }
                        switch (i5) {
                            case 0:
                                String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue == null) {
                                    unknownTag = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue.isEmpty()) {
                                    intentFilter.addAction(attributeValue);
                                    unknownTag = parseInput.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                    break;
                                } else {
                                    RestrictedReceiverFilter restrictedReceiverFilter = sRRFilter;
                                    if (restrictedReceiverFilter != null) {
                                        String packageName = parsingPackage.getPackageName();
                                        String baseApkPath = parsingPackage.getBaseApkPath();
                                        if (restrictedReceiverFilter.filterReceiver(packageName, attributeValue)) {
                                            arrayList.add(new RestrictedReceiverFilter.RestrictedAction(attributeValue, packageName, baseApkPath));
                                        }
                                    }
                                    intentFilter.addAction(attributeValue);
                                    unknownTag = parseInput.success(null);
                                    break;
                                }
                            case 1:
                                if (Flags.relativeReferenceIntentFilters()) {
                                    parseRelRefGroup = parseRelRefGroup(parsedIntentInfoImpl, parsingPackage, resources2, xmlResourceParser, z, parseInput);
                                    unknownTag = parseRelRefGroup;
                                    break;
                                }
                                unknownTag = ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                            case 2:
                                parseRelRefGroup = parseData(parsedIntentInfoImpl, resources2, xmlResourceParser, z, parseInput);
                                unknownTag = parseRelRefGroup;
                                break;
                            case 3:
                                String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue2 == null) {
                                    unknownTag = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue2.isEmpty()) {
                                    intentFilter.addCategory(attributeValue2);
                                    unknownTag = parseInput.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                    break;
                                } else {
                                    intentFilter.addCategory(attributeValue2);
                                    unknownTag = parseInput.success(null);
                                    break;
                                }
                            default:
                                unknownTag = ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                        }
                        if (unknownTag.isError()) {
                            return parseInput.error(unknownTag);
                        }
                        resources2 = resources;
                        i = 2;
                        i2 = 0;
                        i3 = 3;
                        i4 = 1;
                    }
                }
            }
            parsedIntentInfoImpl.setHasDefault(intentFilter.hasCategory(Intent.CATEGORY_DEFAULT));
            for (RestrictedReceiverFilter.RestrictedAction restrictedAction : arrayList) {
                if (!BroadcastReceiverListParser.isPackageXXXIntent(restrictedAction.mAction) || !BroadcastReceiverListParser.hasPackageSSP(intentFilter)) {
                    intentFilter.removeAction(restrictedAction.mAction);
                    sRRFilter.addViolationLog(restrictedAction.mPackageName, restrictedAction.mCodePath, restrictedAction.mAction);
                }
            }
            return parseInput.success(parsedIntentInfoImpl);
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0068, code lost:
    
        if (r3.getUriRelativeFilters().size() <= 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:
    
        r5.addUriRelativeFilterGroup(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0072, code lost:
    
        return r10.success(null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseRelRefGroup(com.android.internal.pm.pkg.component.ParsedIntentInfo r5, com.android.internal.pm.pkg.parsing.ParsingPackage r6, android.content.res.Resources r7, android.content.res.XmlResourceParser r8, boolean r9, android.content.pm.parsing.result.ParseInput r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            android.content.IntentFilter r5 = r5.getIntentFilter()
            int[] r0 = com.android.internal.R.styleable.AndroidManifestUriRelativeFilterGroup
            android.content.res.TypedArray r0 = r7.obtainAttributes(r8, r0)
            r1 = 0
            r2 = 1
            boolean r1 = r0.getBoolean(r1, r2)     // Catch: java.lang.Throwable -> L73
            r1 = r1 ^ r2
            android.content.UriRelativeFilterGroup r3 = new android.content.UriRelativeFilterGroup     // Catch: java.lang.Throwable -> L73
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L73
            r0.recycle()
            int r0 = r8.getDepth()
        L1d:
            int r1 = r8.next()
            if (r1 == r2) goto L60
            r4 = 3
            if (r1 != r4) goto L2c
            int r4 = r8.getDepth()
            if (r4 <= r0) goto L60
        L2c:
            r4 = 2
            if (r1 == r4) goto L30
            goto L1d
        L30:
            com.android.internal.pm.pkg.component.AconfigFlags r1 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getAconfigFlags()
            boolean r1 = r1.skipCurrentElement(r6, r8)
            if (r1 == 0) goto L3b
            goto L1d
        L3b:
            java.lang.String r1 = r8.getName()
            r1.hashCode()
            java.lang.String r4 = "data"
            boolean r1 = r1.equals(r4)
            if (r1 != 0) goto L51
            java.lang.String r1 = "<uri-relative-filter-group>"
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(r1, r6, r8, r10)
            goto L55
        L51:
            android.content.pm.parsing.result.ParseResult r1 = parseRelRefGroupData(r3, r7, r8, r9, r10)
        L55:
            boolean r4 = r1.isError()
            if (r4 == 0) goto L1d
            android.content.pm.parsing.result.ParseResult r5 = r10.error(r1)
            return r5
        L60:
            java.util.Collection r6 = r3.getUriRelativeFilters()
            int r6 = r6.size()
            if (r6 <= 0) goto L6d
            r5.addUriRelativeFilterGroup(r3)
        L6d:
            r5 = 0
            android.content.pm.parsing.result.ParseResult r5 = r10.success(r5)
            return r5
        L73:
            r5 = move-exception
            r0.recycle()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedIntentInfoUtils.parseRelRefGroup(com.android.internal.pm.pkg.component.ParsedIntentInfo, com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, boolean, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedIntentInfo> parseRelRefGroupData(UriRelativeFilterGroup uriRelativeFilterGroup, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestData);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 0, nonConfigurationString));
            }
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString2 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 1, nonConfigurationString2));
            }
            String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(6, 0);
            if (nonConfigurationString3 != null) {
                if (!z) {
                    return parseInput.error("pathPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 2, nonConfigurationString3));
            }
            String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString4 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 3, nonConfigurationString4));
            }
            String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString5 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 4, nonConfigurationString5));
            }
            String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(7, 0);
            if (nonConfigurationString6 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 0, nonConfigurationString6));
            }
            String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(21, 0);
            if (nonConfigurationString7 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 1, nonConfigurationString7));
            }
            String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(22, 0);
            if (nonConfigurationString8 != null) {
                if (!z) {
                    return parseInput.error("fragmentPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 2, nonConfigurationString8));
            }
            String nonConfigurationString9 = obtainAttributes.getNonConfigurationString(23, 0);
            if (nonConfigurationString9 != null) {
                if (!z) {
                    return parseInput.error("fragmentAdvancedPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 3, nonConfigurationString9));
            }
            String nonConfigurationString10 = obtainAttributes.getNonConfigurationString(24, 0);
            if (nonConfigurationString10 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 4, nonConfigurationString10));
            }
            String nonConfigurationString11 = obtainAttributes.getNonConfigurationString(16, 0);
            if (nonConfigurationString11 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 0, nonConfigurationString11));
            }
            String nonConfigurationString12 = obtainAttributes.getNonConfigurationString(17, 0);
            if (nonConfigurationString12 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 1, nonConfigurationString12));
            }
            String nonConfigurationString13 = obtainAttributes.getNonConfigurationString(18, 0);
            if (nonConfigurationString13 != null) {
                if (!z) {
                    return parseInput.error("queryPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 2, nonConfigurationString13));
            }
            String nonConfigurationString14 = obtainAttributes.getNonConfigurationString(19, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("queryAdvancedPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 3, nonConfigurationString14));
            }
            String nonConfigurationString15 = obtainAttributes.getNonConfigurationString(20, 0);
            if (nonConfigurationString15 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 4, nonConfigurationString15));
            }
            return parseInput.success(null);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsedIntentInfo> parseData(ParsedIntentInfo parsedIntentInfo, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) {
        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestData);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null) {
                intentFilter.addDataType(nonConfigurationString);
            }
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(11, 0);
            if (nonConfigurationString2 != null) {
                intentFilter.addMimeGroup(nonConfigurationString2);
            }
            String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString3 != null) {
                intentFilter.addDataScheme(nonConfigurationString3);
            }
            String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(8, 0);
            if (nonConfigurationString4 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString4, 0);
            }
            String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(9, 0);
            if (nonConfigurationString5 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString5, 1);
            }
            String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(10, 0);
            if (nonConfigurationString6 != null) {
                if (!z) {
                    return parseInput.error("sspPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString6, 2);
            }
            String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(15, 0);
            if (nonConfigurationString7 != null) {
                if (!z) {
                    return parseInput.error("sspAdvancedPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString7, 3);
            }
            String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(13, 0);
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
            String nonConfigurationString14 = obtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(nonConfigurationString14, 3);
            }
            String nonConfigurationString15 = obtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString15 != null) {
                intentFilter.addDataPath(nonConfigurationString15, 4);
            }
            return parseInput.success(null);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            return parseInput.error(e.toString());
        } finally {
            obtainAttributes.recycle();
        }
    }
}
