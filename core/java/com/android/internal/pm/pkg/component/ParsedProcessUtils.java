package com.android.internal.pm.pkg.component;

import android.Manifest;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArraySet;

import com.android.internal.R;
import com.android.internal.pm.pkg.component.flags.Flags;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Set;

public class ParsedProcessUtils {
    private static ParseResult<Set<String>> parseDenyPermission(
            Set<String> perms, Resources res, XmlResourceParser parser, ParseInput input)
            throws IOException, XmlPullParserException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestDenyPermission);
        try {
            String perm = sa.getNonConfigurationString(0, 0);
            if (perm != null && perm.equals(Manifest.permission.INTERNET)) {
                perms = CollectionUtils.add(perms, perm);
            }
            sa.recycle();
            XmlUtils.skipCurrentTag(parser);
            return input.success(perms);
        } catch (Throwable th) {
            sa.recycle();
            throw th;
        }
    }

    private static ParseResult<Set<String>> parseAllowPermission(
            Set<String> perms, Resources res, XmlResourceParser parser, ParseInput input)
            throws IOException, XmlPullParserException {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestAllowPermission);
        try {
            String perm = sa.getNonConfigurationString(0, 0);
            if (perm != null && perm.equals(Manifest.permission.INTERNET)) {
                perms = CollectionUtils.remove(perms, perm);
            }
            sa.recycle();
            XmlUtils.skipCurrentTag(parser);
            return input.success(perms);
        } catch (Throwable th) {
            sa.recycle();
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:38:0x00bf. Please report as an issue. */
    private static ParseResult<ParsedProcess> parseProcess(
            Set<String> perms,
            String[] separateProcesses,
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            int flags,
            ParseInput input)
            throws IOException, XmlPullParserException {
        char c;
        ParseResult<?> result;
        ParseResult<?> result2;
        ParsedProcessImpl proc = new ParsedProcessImpl();
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestProcess);
        if (perms != null) {
            try {
                proc.setDeniedPermissions(new ArraySet(perms));
            } finally {
                sa.recycle();
            }
        }
        String processName = sa.getNonConfigurationString(1, 0);
        ParseResult<String> processNameResult =
                ComponentParseUtils.buildProcessName(
                        pkg.getPackageName(),
                        pkg.getPackageName(),
                        processName,
                        flags,
                        separateProcesses,
                        input);
        if (processNameResult.isError()) {
            return input.error(processNameResult);
        }
        String packageName = pkg.getPackageName();
        String className =
                ParsingUtils.buildClassName(packageName, sa.getNonConfigurationString(0, 0));
        proc.setName(processNameResult.getResult());
        proc.putAppClassNameForPackage(packageName, className);
        proc.setGwpAsanMode(sa.getInt(3, -1));
        proc.setMemtagMode(sa.getInt(4, -1));
        if (sa.hasValue(5)) {
            boolean v = sa.getBoolean(5, false);
            proc.setNativeHeapZeroInitialized(v ? 1 : 0);
        }
        if (Flags.enablePerProcessUseEmbeddedDexAttr()) {
            proc.setUseEmbeddedDex(sa.getBoolean(2, false));
        } else {
            proc.setUseEmbeddedDex(false);
        }
        sa.recycle();
        int innerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1 && (type != 3 || parser.getDepth() > innerDepth)) {
                if (type != 3 && type != 4) {
                    String tagName = parser.getName();
                    switch (tagName.hashCode()) {
                        case -1239165229:
                            if (tagName.equals("allow-permission")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1658008624:
                            if (tagName.equals("deny-permission")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            ParseResult<?> denyResult =
                                    parseDenyPermission(
                                            proc.getDeniedPermissions(), res, parser, input);
                            result = denyResult;
                            if (denyResult.isSuccess()) {
                                proc.setDeniedPermissions(denyResult.getResult());
                            }
                            result2 = result;
                            break;
                        case 1:
                            ParseResult<?> allowResult =
                                    parseAllowPermission(
                                            proc.getDeniedPermissions(), res, parser, input);
                            result = allowResult;
                            if (allowResult.isSuccess()) {
                                proc.setDeniedPermissions(allowResult.getResult());
                            }
                            result2 = result;
                            break;
                        default:
                            result2 = ParsingUtils.unknownTag("<process>", pkg, parser, input);
                            break;
                    }
                    if (result2.isError()) {
                        return input.error(result2);
                    }
                }
            }
        }
        return input.success(proc);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0102, code lost:

       return r20.success(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0057, code lost:

       if (r14.equals("allow-permission") != false) goto L28;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<
                    android.util.ArrayMap<
                            java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess>>
            parseProcesses(
                    java.lang.String[] r15,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r16,
                    android.content.res.Resources r17,
                    android.content.res.XmlResourceParser r18,
                    int r19,
                    android.content.pm.parsing.result.ParseInput r20)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedProcessUtils.parseProcesses(java.lang.String[],"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser, int,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }
}
