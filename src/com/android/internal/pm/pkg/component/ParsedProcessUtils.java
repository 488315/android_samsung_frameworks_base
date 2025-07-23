package com.android.internal.pm.pkg.component;

import android.Manifest;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import com.android.internal.R;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedProcessUtils {
    private static ParseResult<Set<String>> parseDenyPermission(Set<String> set, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws IOException, XmlPullParserException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestDenyPermission);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(Manifest.permission.INTERNET)) {
                set = CollectionUtils.add(set, nonConfigurationString);
            }
            obtainAttributes.recycle();
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    private static ParseResult<Set<String>> parseAllowPermission(Set<String> set, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws IOException, XmlPullParserException {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAllowPermission);
        try {
            String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(Manifest.permission.INTERNET)) {
                set = CollectionUtils.remove(set, nonConfigurationString);
            }
            obtainAttributes.recycle();
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00f7, code lost:
    
        return r15.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProcess> parseProcess(java.util.Set<java.lang.String> r9, java.lang.String[] r10, com.android.internal.pm.pkg.parsing.ParsingPackage r11, android.content.res.Resources r12, android.content.res.XmlResourceParser r13, int r14, android.content.pm.parsing.result.ParseInput r15) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            com.android.internal.pm.pkg.component.ParsedProcessImpl r0 = new com.android.internal.pm.pkg.component.ParsedProcessImpl
            r0.<init>()
            int[] r1 = com.android.internal.R.styleable.AndroidManifestProcess
            android.content.res.TypedArray r1 = r12.obtainAttributes(r13, r1)
            if (r9 == 0) goto L15
            android.util.ArraySet r2 = new android.util.ArraySet     // Catch: java.lang.Throwable -> Lf8
            r2.<init>(r9)     // Catch: java.lang.Throwable -> Lf8
            r0.setDeniedPermissions(r2)     // Catch: java.lang.Throwable -> Lf8
        L15:
            r9 = 1
            r2 = 0
            java.lang.String r5 = r1.getNonConfigurationString(r9, r2)     // Catch: java.lang.Throwable -> Lf8
            java.lang.String r3 = r11.getPackageName()     // Catch: java.lang.Throwable -> Lf8
            java.lang.String r4 = r11.getPackageName()     // Catch: java.lang.Throwable -> Lf8
            r7 = r10
            r6 = r14
            r8 = r15
            android.content.pm.parsing.result.ParseResult r10 = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lf8
            boolean r14 = r10.isError()     // Catch: java.lang.Throwable -> Lf8
            if (r14 == 0) goto L38
            android.content.pm.parsing.result.ParseResult r9 = r8.error(r10)     // Catch: java.lang.Throwable -> Lf8
            r1.recycle()
            return r9
        L38:
            java.lang.String r14 = r11.getPackageName()     // Catch: java.lang.Throwable -> Lf8
            java.lang.String r15 = r1.getNonConfigurationString(r2, r2)     // Catch: java.lang.Throwable -> Lf8
            java.lang.String r15 = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(r14, r15)     // Catch: java.lang.Throwable -> Lf8
            java.lang.Object r10 = r10.getResult()     // Catch: java.lang.Throwable -> Lf8
            java.lang.String r10 = (java.lang.String) r10     // Catch: java.lang.Throwable -> Lf8
            r0.setName(r10)     // Catch: java.lang.Throwable -> Lf8
            r0.putAppClassNameForPackage(r14, r15)     // Catch: java.lang.Throwable -> Lf8
            r10 = -1
            r14 = 3
            int r15 = r1.getInt(r14, r10)     // Catch: java.lang.Throwable -> Lf8
            r0.setGwpAsanMode(r15)     // Catch: java.lang.Throwable -> Lf8
            r15 = 4
            int r10 = r1.getInt(r15, r10)     // Catch: java.lang.Throwable -> Lf8
            r0.setMemtagMode(r10)     // Catch: java.lang.Throwable -> Lf8
            r10 = 5
            boolean r3 = r1.hasValue(r10)     // Catch: java.lang.Throwable -> Lf8
            if (r3 == 0) goto L6f
            boolean r10 = r1.getBoolean(r10, r2)     // Catch: java.lang.Throwable -> Lf8
            r0.setNativeHeapZeroInitialized(r10)     // Catch: java.lang.Throwable -> Lf8
        L6f:
            boolean r10 = com.android.internal.pm.pkg.component.flags.Flags.enablePerProcessUseEmbeddedDexAttr()     // Catch: java.lang.Throwable -> Lf8
            if (r10 == 0) goto L7e
            r10 = 2
            boolean r10 = r1.getBoolean(r10, r2)     // Catch: java.lang.Throwable -> Lf8
            r0.setUseEmbeddedDex(r10)     // Catch: java.lang.Throwable -> Lf8
            goto L81
        L7e:
            r0.setUseEmbeddedDex(r2)     // Catch: java.lang.Throwable -> Lf8
        L81:
            r1.recycle()
            int r10 = r13.getDepth()
        L88:
            int r1 = r13.next()
            if (r1 == r9) goto Lf3
            if (r1 != r14) goto L96
            int r2 = r13.getDepth()
            if (r2 <= r10) goto Lf3
        L96:
            if (r1 == r14) goto L88
            if (r1 != r15) goto L9b
            goto L88
        L9b:
            java.lang.String r1 = r13.getName()
            r1.hashCode()
            java.lang.String r2 = "allow-permission"
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto Ld1
            java.lang.String r2 = "deny-permission"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto Lb9
            java.lang.String r1 = "<process>"
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(r1, r11, r13, r8)
            goto Le8
        Lb9:
            java.util.Set r1 = r0.getDeniedPermissions()
            android.content.pm.parsing.result.ParseResult r1 = parseDenyPermission(r1, r12, r13, r8)
            boolean r2 = r1.isSuccess()
            if (r2 == 0) goto Le8
            java.lang.Object r2 = r1.getResult()
            java.util.Set r2 = (java.util.Set) r2
            r0.setDeniedPermissions(r2)
            goto Le8
        Ld1:
            java.util.Set r1 = r0.getDeniedPermissions()
            android.content.pm.parsing.result.ParseResult r1 = parseAllowPermission(r1, r12, r13, r8)
            boolean r2 = r1.isSuccess()
            if (r2 == 0) goto Le8
            java.lang.Object r2 = r1.getResult()
            java.util.Set r2 = (java.util.Set) r2
            r0.setDeniedPermissions(r2)
        Le8:
            boolean r2 = r1.isError()
            if (r2 == 0) goto L88
            android.content.pm.parsing.result.ParseResult r9 = r8.error(r1)
            return r9
        Lf3:
            android.content.pm.parsing.result.ParseResult r9 = r8.success(r0)
            return r9
        Lf8:
            r0 = move-exception
            r9 = r0
            r1.recycle()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedProcessUtils.parseProcess(java.util.Set, java.lang.String[], com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, int, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0045, code lost:
    
        if (r2.equals("process") == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess>> parseProcesses(java.lang.String[] r10, com.android.internal.pm.pkg.parsing.ParsingPackage r11, android.content.res.Resources r12, android.content.res.XmlResourceParser r13, int r14, android.content.pm.parsing.result.ParseInput r15) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedProcessUtils.parseProcesses(java.lang.String[], com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, int, android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }
}
