package com.android.internal.pm.pkg.component;

import android.content.pm.PathPermission;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.multiuser.Flags;
import android.os.PatternMatcher;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedProviderUtils {
    private static final String TAG = "PackageParsing";

    public static ParseResult<ParsedProvider> parseProvider(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsingPackage parsingPackage2;
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        String packageName = parsingPackage.getPackageName();
        ParsedProviderImpl parsedProviderImpl = new ParsedProviderImpl();
        String name = xmlResourceParser.getName();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProvider);
        try {
            ParseResult<?> mainComponent = ParsedMainComponentUtils.parseMainComponent(parsedProviderImpl, name, strArr, parsingPackage, typedArrayObtainAttributes, i, z, str, parseInput, 17, 14, 18, 6, 1, 0, 15, 2, 8, 19, 21, 23, 25);
            if (mainComponent.isError()) {
                return parseInput.error(mainComponent);
            }
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(10, 0);
            parsedProviderImpl.setSyncable(typedArrayObtainAttributes.getBoolean(11, false)).setExported(typedArrayObtainAttributes.getBoolean(7, targetSdkVersion < 17));
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
            String nonConfigurationString3 = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString3 == null) {
                nonConfigurationString3 = nonConfigurationString2;
            }
            if (nonConfigurationString3 == null) {
                parsedProviderImpl.setReadPermission(parsingPackage.getPermission());
            } else {
                parsedProviderImpl.setReadPermission(nonConfigurationString3);
            }
            String nonConfigurationString4 = typedArrayObtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString4 != null) {
                nonConfigurationString2 = nonConfigurationString4;
            }
            if (nonConfigurationString2 == null) {
                parsedProviderImpl.setWritePermission(parsingPackage.getPermission());
            } else {
                parsedProviderImpl.setWritePermission(nonConfigurationString2);
            }
            parsedProviderImpl.setGrantUriPermissions(typedArrayObtainAttributes.getBoolean(13, false)).setForceUriPermissions(typedArrayObtainAttributes.getBoolean(22, false)).setMultiProcess(typedArrayObtainAttributes.getBoolean(9, false)).setInitOrder(typedArrayObtainAttributes.getInt(12, 0)).setFlags(parsedProviderImpl.getFlags() | ComponentParseUtils.flag(1073741824, 16, typedArrayObtainAttributes));
            if (Flags.enableSystemUserOnlyForServicesAndProviders()) {
                parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | ComponentParseUtils.flag(536870912, 24, typedArrayObtainAttributes));
            }
            boolean z2 = typedArrayObtainAttributes.getBoolean(20, false);
            if (z2) {
                parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | 1048576);
                parsingPackage2 = parsingPackage;
                parsingPackage2.setVisibleToInstantApps(true);
            } else {
                parsingPackage2 = parsingPackage;
            }
            typedArrayObtainAttributes.recycle();
            if (parsingPackage2.isSaveStateDisallowed() && Objects.equals(parsedProviderImpl.getProcessName(), packageName)) {
                return parseInput.error("Heavy-weight applications can not have providers in main process");
            }
            if (nonConfigurationString == null) {
                return parseInput.error("<provider> does not include authorities attribute");
            }
            if (nonConfigurationString.length() <= 0) {
                return parseInput.error("<provider> has empty authorities attribute");
            }
            parsedProviderImpl.setAuthority(nonConfigurationString);
            return parseProviderTags(parsingPackage2, name, resources, xmlResourceParser, z2, parsedProviderImpl, parseInput);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c8, code lost:
    
        return r17.success(r16);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsedProvider> parseProviderTags(ParsingPackage parsingPackage, String str, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParsedProviderImpl parsedProviderImpl, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<?> grantUriPermission;
        ParseResult<?> parseResultAddProperty;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            char c = 1;
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                    String name = xmlResourceParser.getName();
                    name.hashCode();
                    switch (name.hashCode()) {
                        case -1814617695:
                            if (!name.equals("grant-uri-permission")) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -1115949454:
                            if (!name.equals("meta-data")) {
                            }
                            break;
                        case -1029793847:
                            if (name.equals("intent-filter")) {
                                c = 2;
                                break;
                            }
                            break;
                        case -993141291:
                            if (name.equals("property")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 636171383:
                            if (name.equals("path-permission")) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            grantUriPermission = parseGrantUriPermission(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        case 1:
                            grantUriPermission = ParsedComponentUtils.addMetaData(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        case 2:
                            ParseResult<ParsedIntentInfoImpl> intentFilter = ParsedMainComponentUtils.parseIntentFilter(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, z, true, false, false, false, parseInput);
                            if (intentFilter.isSuccess()) {
                                ParsedIntentInfoImpl result = intentFilter.getResult();
                                parsedProviderImpl.setOrder(Math.max(result.getIntentFilter().getOrder(), parsedProviderImpl.getOrder()));
                                parsedProviderImpl.addIntent(result);
                            }
                            grantUriPermission = intentFilter;
                            break;
                        case 3:
                            parseResultAddProperty = ParsedComponentUtils.addProperty(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            grantUriPermission = parseResultAddProperty;
                            break;
                        case 4:
                            parseResultAddProperty = parsePathPermission(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            grantUriPermission = parseResultAddProperty;
                            break;
                        default:
                            parseResultAddProperty = ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                            grantUriPermission = parseResultAddProperty;
                            break;
                    }
                    if (grantUriPermission.isError()) {
                        return parseInput.error(grantUriPermission);
                    }
                }
            }
        }
    }

    private static ParseResult<ParsedProvider> parseGrantUriPermission(ParsedProviderImpl parsedProviderImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        PatternMatcher patternMatcher;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestGrantUriPermission);
        try {
            String name = xmlResourceParser.getName();
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                patternMatcher = new PatternMatcher(nonConfigurationString, 3);
            } else {
                String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(2, 0);
                if (nonConfigurationString2 != null) {
                    patternMatcher = new PatternMatcher(nonConfigurationString2, 2);
                } else {
                    String nonConfigurationString3 = typedArrayObtainAttributes.getNonConfigurationString(1, 0);
                    if (nonConfigurationString3 != null) {
                        patternMatcher = new PatternMatcher(nonConfigurationString3, 1);
                    } else {
                        String nonConfigurationString4 = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
                        if (nonConfigurationString4 != null) {
                            patternMatcher = new PatternMatcher(nonConfigurationString4, 4);
                        } else {
                            String nonConfigurationString5 = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
                            patternMatcher = nonConfigurationString5 != null ? new PatternMatcher(nonConfigurationString5, 0) : null;
                        }
                    }
                }
            }
            if (patternMatcher != null) {
                parsedProviderImpl.addUriPermissionPattern(patternMatcher);
                parsedProviderImpl.setGrantUriPermissions(true);
            } else {
                Slog.w("PackageParsing", "Unknown element under <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            }
            return parseInput.success(parsedProviderImpl);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsedProvider> parsePathPermission(ParsedProviderImpl parsedProviderImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        boolean z;
        PathPermission pathPermission;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPathPermission);
        try {
            String name = xmlResourceParser.getName();
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
            String nonConfigurationString2 = typedArrayObtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString2 == null) {
                nonConfigurationString2 = nonConfigurationString;
            }
            String nonConfigurationString3 = typedArrayObtainAttributes.getNonConfigurationString(2, 0);
            if (nonConfigurationString3 != null) {
                nonConfigurationString = nonConfigurationString3;
            }
            if (nonConfigurationString2 != null) {
                nonConfigurationString2 = nonConfigurationString2.intern();
                z = true;
            } else {
                z = false;
            }
            if (nonConfigurationString != null) {
                nonConfigurationString = nonConfigurationString.intern();
                z = true;
            }
            if (!z) {
                Slog.w("PackageParsing", "No readPermission or writePermission for <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                return parseInput.success(parsedProviderImpl);
            }
            String nonConfigurationString4 = typedArrayObtainAttributes.getNonConfigurationString(7, 0);
            if (nonConfigurationString4 != null) {
                pathPermission = new PathPermission(nonConfigurationString4, 3, nonConfigurationString2, nonConfigurationString);
            } else {
                String nonConfigurationString5 = typedArrayObtainAttributes.getNonConfigurationString(5, 0);
                if (nonConfigurationString5 != null) {
                    pathPermission = new PathPermission(nonConfigurationString5, 2, nonConfigurationString2, nonConfigurationString);
                } else {
                    String nonConfigurationString6 = typedArrayObtainAttributes.getNonConfigurationString(4, 0);
                    if (nonConfigurationString6 != null) {
                        pathPermission = new PathPermission(nonConfigurationString6, 1, nonConfigurationString2, nonConfigurationString);
                    } else {
                        String nonConfigurationString7 = typedArrayObtainAttributes.getNonConfigurationString(6, 0);
                        if (nonConfigurationString7 != null) {
                            pathPermission = new PathPermission(nonConfigurationString7, 4, nonConfigurationString2, nonConfigurationString);
                        } else {
                            String nonConfigurationString8 = typedArrayObtainAttributes.getNonConfigurationString(3, 0);
                            pathPermission = nonConfigurationString8 != null ? new PathPermission(nonConfigurationString8, 0, nonConfigurationString2, nonConfigurationString) : null;
                        }
                    }
                }
            }
            if (pathPermission != null) {
                parsedProviderImpl.addPathPermission(pathPermission);
            } else {
                Slog.w("PackageParsing", "No path, pathPrefix, or pathPattern for <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            }
            return parseInput.success(parsedProviderImpl);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }
}
