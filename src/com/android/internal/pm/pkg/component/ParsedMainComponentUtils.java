package com.android.internal.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Slog;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedMainComponentUtils {
    private static final String TAG = "PackageParsing";

    public static int resolveIntentMatchingFlags(int i, int i2) {
        return i2 == 0 ? i : i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <Component extends com.android.internal.pm.pkg.component.ParsedMainComponentImpl> android.content.pm.parsing.result.ParseResult<Component> parseMainComponent(Component r16, java.lang.String r17, java.lang.String[] r18, com.android.internal.pm.pkg.parsing.ParsingPackage r19, android.content.res.TypedArray r20, int r21, boolean r22, java.lang.String r23, android.content.pm.parsing.result.ParseInput r24, int r25, int r26, int r27, int r28, int r29, int r30, int r31, int r32, int r33, int r34, int r35, int r36, int r37) {
        /*
            r14 = r27
            r15 = r28
            r0 = r16
            r1 = r17
            r2 = r19
            r3 = r20
            r4 = r22
            r5 = r24
            r6 = r25
            r7 = r26
            r8 = r29
            r9 = r30
            r10 = r31
            r11 = r32
            r13 = r33
            r12 = r34
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            boolean r2 = r1.isError()
            if (r2 == 0) goto L2b
            return r1
        L2b:
            r1 = 1
            r2 = 0
            r4 = -1
            if (r14 == r4) goto L43
            boolean r5 = r3.getBoolean(r14, r2)
            r0.setDirectBootAware(r5)
            boolean r5 = r0.isDirectBootAware()
            if (r5 == 0) goto L43
            r5 = r19
            r5.setPartiallyDirectBootAware(r1)
            goto L45
        L43:
            r5 = r19
        L45:
            if (r15 == r4) goto L4e
            boolean r1 = r3.getBoolean(r15, r1)
            r0.setEnabled(r1)
        L4e:
            if (r13 == r4) goto L92
            int r1 = r5.getTargetSdkVersion()
            r6 = 8
            if (r1 < r6) goto L5f
            r1 = 1024(0x400, float:1.435E-42)
            java.lang.String r1 = r3.getNonConfigurationString(r13, r1)
            goto L63
        L5f:
            java.lang.String r1 = r3.getNonResourceString(r13)
        L63:
            java.lang.String r6 = r5.getPackageName()
            java.lang.String r7 = r5.getProcessName()
            r29 = r18
            r28 = r21
            r30 = r24
            r27 = r1
            r25 = r6
            r26 = r7
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(r25, r26, r27, r28, r29, r30)
            r6 = r30
            boolean r7 = r1.isError()
            if (r7 == 0) goto L88
            android.content.pm.parsing.result.ParseResult r0 = r6.error(r1)
            return r0
        L88:
            java.lang.Object r1 = r1.getResult()
            java.lang.String r1 = (java.lang.String) r1
            r0.setProcessName(r1)
            goto L94
        L92:
            r6 = r24
        L94:
            r1 = r35
            if (r1 == r4) goto L9f
            java.lang.String r1 = r3.getNonConfigurationString(r1, r2)
            r0.setSplitName(r1)
        L9f:
            if (r23 == 0) goto Lac
            java.lang.String r1 = r0.getSplitName()
            if (r1 != 0) goto Lac
            r13 = r23
            r0.setSplitName(r13)
        Lac:
            r1 = r36
            if (r1 == r4) goto Lbf
            java.lang.String r1 = r3.getNonConfigurationString(r1, r2)
            if (r1 == 0) goto Lbf
            java.lang.String r4 = "\\|"
            java.lang.String[] r1 = r1.split(r4)
            r0.setAttributionTags(r1)
        Lbf:
            boolean r1 = com.android.internal.hidden_from_bootclasspath.android.security.Flags.enableIntentMatchingFlags()
            if (r1 == 0) goto Ld6
            int r1 = r5.getIntentMatchingFlags()
            r4 = r37
            int r2 = r3.getInt(r4, r2)
            int r1 = resolveIntentMatchingFlags(r1, r2)
            r0.setIntentMatchingFlags(r1)
        Ld6:
            android.content.pm.parsing.result.ParseResult r0 = r6.success(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseMainComponent(com.android.internal.pm.pkg.component.ParsedMainComponentImpl, java.lang.String, java.lang.String[], com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.TypedArray, int, boolean, java.lang.String, android.content.pm.parsing.result.ParseInput, int, int, int, int, int, int, int, int, int, int, int, int, int):android.content.pm.parsing.result.ParseResult");
    }

    static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(ParsedMainComponent parsedMainComponent, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, ParseInput parseInput) throws IOException, XmlPullParserException {
        int i;
        ParseResult<ParsedIntentInfoImpl> parseIntentInfo = ParsedIntentInfoUtils.parseIntentInfo(parsedMainComponent.getName(), parsingPackage, resources, xmlResourceParser, z2, z3, parseInput);
        if (parseIntentInfo.isError()) {
            return parseInput.error(parseIntentInfo);
        }
        ParsedIntentInfoImpl result = parseIntentInfo.getResult();
        IntentFilter intentFilter = result.getIntentFilter();
        if (intentFilter.countActions() == 0 && z5) {
            Slog.w("PackageParsing", "No actions in " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            return parseInput.success(null);
        }
        if (z) {
            i = 1;
        } else {
            i = (z4 && ComponentParseUtils.isImplicitlyExposedIntent(result)) ? 2 : 0;
        }
        intentFilter.setVisibilityToInstantApp(i);
        return parseInput.success(parseIntentInfo.getResult());
    }
}
