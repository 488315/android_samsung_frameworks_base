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
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedProviderUtils {
    private static final String TAG = "PackageParsing";

    public static ParseResult<ParsedProvider> parseProvider(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, boolean z, String str, ParseInput parseInput) throws IOException, XmlPullParserException {
        ParsingPackage parsingPackage2;
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        String packageName = parsingPackage.getPackageName();
        ParsedProviderImpl parsedProviderImpl = new ParsedProviderImpl();
        String name = xmlResourceParser.getName();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProvider);
        try {
            ParseResult<?> parseMainComponent = ParsedMainComponentUtils.parseMainComponent(parsedProviderImpl, name, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 17, 14, 18, 6, 1, 0, 15, 2, 8, 19, 21, 23, 25);
            if (parseMainComponent.isError()) {
                return parseInput.error(parseMainComponent);
            }
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(10, 0);
            parsedProviderImpl.setSyncable(obtainAttributes.getBoolean(11, false)).setExported(obtainAttributes.getBoolean(7, targetSdkVersion < 17));
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(3, 0);
            String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString3 == null) {
                nonConfigurationString3 = nonConfigurationString2;
            }
            if (nonConfigurationString3 == null) {
                parsedProviderImpl.setReadPermission(parsingPackage.getPermission());
            } else {
                parsedProviderImpl.setReadPermission(nonConfigurationString3);
            }
            String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString4 != null) {
                nonConfigurationString2 = nonConfigurationString4;
            }
            if (nonConfigurationString2 == null) {
                parsedProviderImpl.setWritePermission(parsingPackage.getPermission());
            } else {
                parsedProviderImpl.setWritePermission(nonConfigurationString2);
            }
            parsedProviderImpl.setGrantUriPermissions(obtainAttributes.getBoolean(13, false)).setForceUriPermissions(obtainAttributes.getBoolean(22, false)).setMultiProcess(obtainAttributes.getBoolean(9, false)).setInitOrder(obtainAttributes.getInt(12, 0)).setFlags(parsedProviderImpl.getFlags() | ComponentParseUtils.flag(1073741824, 16, obtainAttributes));
            if (Flags.enableSystemUserOnlyForServicesAndProviders()) {
                parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | ComponentParseUtils.flag(536870912, 24, obtainAttributes));
            }
            boolean z2 = obtainAttributes.getBoolean(20, false);
            if (z2) {
                parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | 1048576);
                parsingPackage2 = parsingPackage;
                parsingPackage2.setVisibleToInstantApps(true);
            } else {
                parsingPackage2 = parsingPackage;
            }
            obtainAttributes.recycle();
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
            obtainAttributes.recycle();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x00c8, code lost:
    
        return r17.success(r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0060, code lost:
    
        if (r1.equals("meta-data") == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> parseProviderTags(com.android.internal.pm.pkg.parsing.ParsingPackage r11, java.lang.String r12, android.content.res.Resources r13, android.content.res.XmlResourceParser r14, boolean r15, com.android.internal.pm.pkg.component.ParsedProviderImpl r16, android.content.pm.parsing.result.ParseInput r17) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r0 = r16
            r9 = r17
            int r10 = r14.getDepth()
        L8:
            int r1 = r14.next()
            r2 = 1
            if (r1 == r2) goto Lc4
            r3 = 3
            if (r1 != r3) goto L18
            int r4 = r14.getDepth()
            if (r4 <= r10) goto Lc4
        L18:
            r4 = 2
            if (r1 == r4) goto L1c
            goto L8
        L1c:
            com.android.internal.pm.pkg.component.AconfigFlags r1 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getAconfigFlags()
            boolean r1 = r1.skipCurrentElement(r11, r14)
            if (r1 == 0) goto L27
            goto L8
        L27:
            java.lang.String r1 = r14.getName()
            r1.hashCode()
            int r5 = r1.hashCode()
            r6 = -1
            switch(r5) {
                case -1814617695: goto L63;
                case -1115949454: goto L5a;
                case -1029793847: goto L4f;
                case -993141291: goto L43;
                case 636171383: goto L38;
                default: goto L36;
            }
        L36:
            r2 = r6
            goto L6d
        L38:
            java.lang.String r2 = "path-permission"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L41
            goto L36
        L41:
            r2 = 4
            goto L6d
        L43:
            java.lang.String r2 = "property"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L4d
            goto L36
        L4d:
            r2 = r3
            goto L6d
        L4f:
            java.lang.String r2 = "intent-filter"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L58
            goto L36
        L58:
            r2 = r4
            goto L6d
        L5a:
            java.lang.String r3 = "meta-data"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L6d
            goto L36
        L63:
            java.lang.String r2 = "grant-uri-permission"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L6c
            goto L36
        L6c:
            r2 = 0
        L6d:
            switch(r2) {
                case 0: goto Lb5;
                case 1: goto Lb0;
                case 2: goto L80;
                case 3: goto L7a;
                case 4: goto L75;
                default: goto L70;
            }
        L70:
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(r12, r11, r14, r9)
            goto L7e
        L75:
            android.content.pm.parsing.result.ParseResult r1 = parsePathPermission(r0, r11, r13, r14, r9)
            goto L7e
        L7a:
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.component.ParsedComponentUtils.addProperty(r0, r11, r13, r14, r9)
        L7e:
            r4 = r1
            goto Lb9
        L80:
            r7 = 0
            r8 = 0
            r5 = 1
            r6 = 0
            r1 = r11
            r2 = r13
            r3 = r14
            r4 = r15
            android.content.pm.parsing.result.ParseResult r5 = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseIntentFilter(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
            boolean r4 = r5.isSuccess()
            if (r4 == 0) goto Lae
            java.lang.Object r4 = r5.getResult()
            com.android.internal.pm.pkg.component.ParsedIntentInfoImpl r4 = (com.android.internal.pm.pkg.component.ParsedIntentInfoImpl) r4
            android.content.IntentFilter r6 = r4.getIntentFilter()
            int r6 = r6.getOrder()
            int r7 = r0.getOrder()
            int r6 = java.lang.Math.max(r6, r7)
            r0.setOrder(r6)
            r0.addIntent(r4)
        Lae:
            r4 = r5
            goto Lb9
        Lb0:
            android.content.pm.parsing.result.ParseResult r4 = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(r0, r11, r13, r14, r9)
            goto Lb9
        Lb5:
            android.content.pm.parsing.result.ParseResult r4 = parseGrantUriPermission(r0, r11, r13, r14, r9)
        Lb9:
            boolean r5 = r4.isError()
            if (r5 == 0) goto L8
            android.content.pm.parsing.result.ParseResult r11 = r9.error(r4)
            return r11
        Lc4:
            android.content.pm.parsing.result.ParseResult r11 = r9.success(r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedProviderUtils.parseProviderTags(com.android.internal.pm.pkg.parsing.ParsingPackage, java.lang.String, android.content.res.Resources, android.content.res.XmlResourceParser, boolean, com.android.internal.pm.pkg.component.ParsedProviderImpl, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedProvider> parseGrantUriPermission(ParsedProviderImpl parsedProviderImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        PatternMatcher patternMatcher;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestGrantUriPermission);
        try {
            String name = xmlResourceParser.getName();
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                patternMatcher = new PatternMatcher(nonConfigurationString, 3);
            } else {
                String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(2, 0);
                if (nonConfigurationString2 != null) {
                    patternMatcher = new PatternMatcher(nonConfigurationString2, 2);
                } else {
                    String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(1, 0);
                    if (nonConfigurationString3 != null) {
                        patternMatcher = new PatternMatcher(nonConfigurationString3, 1);
                    } else {
                        String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(3, 0);
                        if (nonConfigurationString4 != null) {
                            patternMatcher = new PatternMatcher(nonConfigurationString4, 4);
                        } else {
                            String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(0, 0);
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
            obtainAttributes.recycle();
        }
    }

    private static ParseResult<ParsedProvider> parsePathPermission(ParsedProviderImpl parsedProviderImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        boolean z;
        PathPermission pathPermission;
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPathPermission);
        try {
            String name = xmlResourceParser.getName();
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString2 == null) {
                nonConfigurationString2 = nonConfigurationString;
            }
            String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(2, 0);
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
            String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(7, 0);
            if (nonConfigurationString4 != null) {
                pathPermission = new PathPermission(nonConfigurationString4, 3, nonConfigurationString2, nonConfigurationString);
            } else {
                String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(5, 0);
                if (nonConfigurationString5 != null) {
                    pathPermission = new PathPermission(nonConfigurationString5, 2, nonConfigurationString2, nonConfigurationString);
                } else {
                    String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(4, 0);
                    if (nonConfigurationString6 != null) {
                        pathPermission = new PathPermission(nonConfigurationString6, 1, nonConfigurationString2, nonConfigurationString);
                    } else {
                        String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(6, 0);
                        if (nonConfigurationString7 != null) {
                            pathPermission = new PathPermission(nonConfigurationString7, 4, nonConfigurationString2, nonConfigurationString);
                        } else {
                            String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(3, 0);
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
            obtainAttributes.recycle();
        }
    }
}
