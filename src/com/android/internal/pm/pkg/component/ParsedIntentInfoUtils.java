package com.android.internal.pm.pkg.component;

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
    /* JADX WARN: Code restructure failed: missing block: B:79:0x015f, code lost:
    
        r0.setHasDefault(r6.hasCategory(android.content.Intent.CATEGORY_DEFAULT));
        r1 = r10.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0170, code lost:
    
        if (r1.hasNext() == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0172, code lost:
    
        r2 = (com.samsung.android.core.pm.allowlist.RestrictedReceiverFilter.RestrictedAction) r1.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x017e, code lost:
    
        if (com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser.isPackageXXXIntent(r2.mAction) == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0184, code lost:
    
        if (com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser.hasPackageSSP(r6) == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0187, code lost:
    
        r6.removeAction(r2.mAction);
        com.android.internal.pm.pkg.component.ParsedIntentInfoUtils.sRRFilter.addViolationLog(r2.mPackageName, r2.mCodePath, r2.mAction);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x019c, code lost:
    
        return r22.success(r0);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ParseResult<ParsedIntentInfoImpl> parseIntentInfo(String str, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, boolean z2, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<?> parseResultUnknownTag;
        ParseResult<ParsedIntentInfo> relRefGroup;
        Resources resources2 = resources;
        ParsedIntentInfoImpl parsedIntentInfoImpl = new ParsedIntentInfoImpl();
        IntentFilter intentFilter = parsedIntentInfoImpl.getIntentFilter();
        TypedArray typedArrayObtainAttributes = resources2.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestIntentFilter);
        int i = 2;
        int i2 = 0;
        try {
            intentFilter.setPriority(typedArrayObtainAttributes.getInt(2, 0));
            int i3 = 3;
            intentFilter.setOrder(typedArrayObtainAttributes.getInt(3, 0));
            TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(0);
            if (typedValuePeekValue != null) {
                parsedIntentInfoImpl.setLabelRes(typedValuePeekValue.resourceId);
                if (typedValuePeekValue.resourceId == 0) {
                    parsedIntentInfoImpl.setNonLocalizedLabel(typedValuePeekValue.coerceToString());
                }
            }
            if (ParsingPackageUtils.sUseRoundIcon) {
                parsedIntentInfoImpl.setIcon(typedArrayObtainAttributes.getResourceId(7, 0));
            }
            int i4 = 1;
            if (parsedIntentInfoImpl.getIcon() == 0) {
                parsedIntentInfoImpl.setIcon(typedArrayObtainAttributes.getResourceId(1, 0));
            }
            if (z2) {
                intentFilter.setAutoVerify(typedArrayObtainAttributes.getBoolean(6, false));
            }
            typedArrayObtainAttributes.recycle();
            ArrayList arrayList = new ArrayList();
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
                                    parseResultUnknownTag = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue.isEmpty()) {
                                    intentFilter.addAction(attributeValue);
                                    parseResultUnknownTag = parseInput.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
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
                                    parseResultUnknownTag = parseInput.success(null);
                                    break;
                                }
                            case 1:
                                if (Flags.relativeReferenceIntentFilters()) {
                                    relRefGroup = parseRelRefGroup(parsedIntentInfoImpl, parsingPackage, resources2, xmlResourceParser, z, parseInput);
                                    parseResultUnknownTag = relRefGroup;
                                    break;
                                }
                                parseResultUnknownTag = ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                            case 2:
                                relRefGroup = parseData(parsedIntentInfoImpl, resources2, xmlResourceParser, z, parseInput);
                                parseResultUnknownTag = relRefGroup;
                                break;
                            case 3:
                                String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue2 == null) {
                                    parseResultUnknownTag = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue2.isEmpty()) {
                                    intentFilter.addCategory(attributeValue2);
                                    parseResultUnknownTag = parseInput.deferError("No value supplied for <android:name>", ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                    break;
                                } else {
                                    intentFilter.addCategory(attributeValue2);
                                    parseResultUnknownTag = parseInput.success(null);
                                    break;
                                }
                            default:
                                parseResultUnknownTag = ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                        }
                        if (parseResultUnknownTag.isError()) {
                            return parseInput.error(parseResultUnknownTag);
                        }
                        resources2 = resources;
                        i = 2;
                        i2 = 0;
                        i3 = 3;
                        i4 = 1;
                    }
                }
            }
        } catch (Throwable th) {
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0068, code lost:
    
        if (r3.getUriRelativeFilters().size() <= 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006a, code lost:
    
        r5.addUriRelativeFilterGroup(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0072, code lost:
    
        return r10.success(null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsedIntentInfo> parseRelRefGroup(ParsedIntentInfo parsedIntentInfo, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<ParsedIntentInfo> relRefGroupData;
        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestUriRelativeFilterGroup);
        try {
            UriRelativeFilterGroup uriRelativeFilterGroup = new UriRelativeFilterGroup(!typedArrayObtainAttributes.getBoolean(0, true) ? 1 : 0);
            typedArrayObtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                    break;
                }
                if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                    String name = xmlResourceParser.getName();
                    name.hashCode();
                    if (name.equals("data")) {
                        relRefGroupData = parseRelRefGroupData(uriRelativeFilterGroup, resources, xmlResourceParser, z, parseInput);
                    } else {
                        relRefGroupData = ParsingUtils.unknownTag("<uri-relative-filter-group>", parsingPackage, xmlResourceParser, parseInput);
                    }
                    if (relRefGroupData.isError()) {
                        return parseInput.error(relRefGroupData);
                    }
                }
            }
        } catch (Throwable th) {
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    private static ParseResult<ParsedIntentInfo> parseRelRefGroupData(UriRelativeFilterGroup uriRelativeFilterGroup, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestData);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 0, nonConfigurationString));
            }
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString2 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 1, nonConfigurationString2));
            }
            String nonConfigurationString3 = typedArrayObtainAttributes.getNonConfigurationString(6, 0);
            if (nonConfigurationString3 != null) {
                if (!z) {
                    return parseInput.error("pathPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 2, nonConfigurationString3));
            }
            String nonConfigurationString4 = typedArrayObtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString4 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 3, nonConfigurationString4));
            }
            String nonConfigurationString5 = typedArrayObtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString5 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(0, 4, nonConfigurationString5));
            }
            String nonConfigurationString6 = typedArrayObtainAttributes.getNonConfigurationString(7, 0);
            if (nonConfigurationString6 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 0, nonConfigurationString6));
            }
            String nonConfigurationString7 = typedArrayObtainAttributes.getNonConfigurationString(21, 0);
            if (nonConfigurationString7 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 1, nonConfigurationString7));
            }
            String nonConfigurationString8 = typedArrayObtainAttributes.getNonConfigurationString(22, 0);
            if (nonConfigurationString8 != null) {
                if (!z) {
                    return parseInput.error("fragmentPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 2, nonConfigurationString8));
            }
            String nonConfigurationString9 = typedArrayObtainAttributes.getNonConfigurationString(23, 0);
            if (nonConfigurationString9 != null) {
                if (!z) {
                    return parseInput.error("fragmentAdvancedPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 3, nonConfigurationString9));
            }
            String nonConfigurationString10 = typedArrayObtainAttributes.getNonConfigurationString(24, 0);
            if (nonConfigurationString10 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(2, 4, nonConfigurationString10));
            }
            String nonConfigurationString11 = typedArrayObtainAttributes.getNonConfigurationString(16, 0);
            if (nonConfigurationString11 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 0, nonConfigurationString11));
            }
            String nonConfigurationString12 = typedArrayObtainAttributes.getNonConfigurationString(17, 0);
            if (nonConfigurationString12 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 1, nonConfigurationString12));
            }
            String nonConfigurationString13 = typedArrayObtainAttributes.getNonConfigurationString(18, 0);
            if (nonConfigurationString13 != null) {
                if (!z) {
                    return parseInput.error("queryPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 2, nonConfigurationString13));
            }
            String nonConfigurationString14 = typedArrayObtainAttributes.getNonConfigurationString(19, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("queryAdvancedPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 3, nonConfigurationString14));
            }
            String nonConfigurationString15 = typedArrayObtainAttributes.getNonConfigurationString(20, 0);
            if (nonConfigurationString15 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new UriRelativeFilter(1, 4, nonConfigurationString15));
            }
            return parseInput.success(null);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsedIntentInfo> parseData(ParsedIntentInfo parsedIntentInfo, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) {
        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestData);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null) {
                intentFilter.addDataType(nonConfigurationString);
            }
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(11, 0);
            if (nonConfigurationString2 != null) {
                intentFilter.addMimeGroup(nonConfigurationString2);
            }
            String nonConfigurationString3 = typedArrayObtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString3 != null) {
                intentFilter.addDataScheme(nonConfigurationString3);
            }
            String nonConfigurationString4 = typedArrayObtainAttributes.getNonConfigurationString(8, 0);
            if (nonConfigurationString4 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString4, 0);
            }
            String nonConfigurationString5 = typedArrayObtainAttributes.getNonConfigurationString(9, 0);
            if (nonConfigurationString5 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString5, 1);
            }
            String nonConfigurationString6 = typedArrayObtainAttributes.getNonConfigurationString(10, 0);
            if (nonConfigurationString6 != null) {
                if (!z) {
                    return parseInput.error("sspPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString6, 2);
            }
            String nonConfigurationString7 = typedArrayObtainAttributes.getNonConfigurationString(15, 0);
            if (nonConfigurationString7 != null) {
                if (!z) {
                    return parseInput.error("sspAdvancedPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString7, 3);
            }
            String nonConfigurationString8 = typedArrayObtainAttributes.getNonConfigurationString(13, 0);
            if (nonConfigurationString8 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString8, 4);
            }
            String nonConfigurationString9 = typedArrayObtainAttributes.getNonConfigurationString(2, 0);
            String nonConfigurationString10 = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString9 != null) {
                intentFilter.addDataAuthority(nonConfigurationString9, nonConfigurationString10);
            }
            String nonConfigurationString11 = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString11 != null) {
                intentFilter.addDataPath(nonConfigurationString11, 0);
            }
            String nonConfigurationString12 = typedArrayObtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString12 != null) {
                intentFilter.addDataPath(nonConfigurationString12, 1);
            }
            String nonConfigurationString13 = typedArrayObtainAttributes.getNonConfigurationString(6, 0);
            if (nonConfigurationString13 != null) {
                if (!z) {
                    return parseInput.error("pathPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(nonConfigurationString13, 2);
            }
            String nonConfigurationString14 = typedArrayObtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(nonConfigurationString14, 3);
            }
            String nonConfigurationString15 = typedArrayObtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString15 != null) {
                intentFilter.addDataPath(nonConfigurationString15, 4);
            }
            return parseInput.success(null);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            return parseInput.error(e.toString());
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }
}
