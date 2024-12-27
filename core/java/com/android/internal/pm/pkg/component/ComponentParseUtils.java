package com.android.internal.pm.pkg.component;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.TypedArray;
import android.text.TextUtils;

public class ComponentParseUtils {
    public static boolean isImplicitlyExposedIntent(ParsedIntentInfo intentInfo) {
        IntentFilter intentFilter = intentInfo.getIntentFilter();
        return intentFilter.hasCategory(Intent.CATEGORY_BROWSABLE)
                || intentFilter.hasAction(Intent.ACTION_SEND)
                || intentFilter.hasAction(Intent.ACTION_SENDTO)
                || intentFilter.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004a, code lost:

       return r9.success(r8);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <Component extends com.android.internal.pm.pkg.component.ParsedComponentImpl>
            android.content.pm.parsing.result.ParseResult<Component> parseAllMetaData(
                    com.android.internal.pm.pkg.parsing.ParsingPackage r4,
                    android.content.res.Resources r5,
                    android.content.res.XmlResourceParser r6,
                    java.lang.String r7,
                    Component r8,
                    android.content.pm.parsing.result.ParseInput r9)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            int r0 = r6.getDepth()
        L4:
            int r1 = r6.next()
            r2 = r1
            r3 = 1
            if (r1 == r3) goto L46
            r1 = 3
            if (r2 != r1) goto L15
            int r1 = r6.getDepth()
            if (r1 <= r0) goto L46
        L15:
            r1 = 2
            if (r2 == r1) goto L19
            goto L4
        L19:
            com.android.internal.pm.pkg.component.AconfigFlags r1 = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.getAconfigFlags()
            boolean r1 = r1.skipCurrentElement(r6)
            if (r1 == 0) goto L24
            goto L4
        L24:
            java.lang.String r1 = "meta-data"
            java.lang.String r3 = r6.getName()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L36
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(r8, r4, r5, r6, r9)
            goto L3a
        L36:
            android.content.pm.parsing.result.ParseResult r1 = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(r7, r4, r6, r9)
        L3a:
            boolean r3 = r1.isError()
            if (r3 == 0) goto L45
            android.content.pm.parsing.result.ParseResult r3 = r9.error(r1)
            return r3
        L45:
            goto L4
        L46:
            android.content.pm.parsing.result.ParseResult r1 = r9.success(r8)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser,"
                    + " java.lang.String,"
                    + " com.android.internal.pm.pkg.component.ParsedComponentImpl,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    public static ParseResult<String> buildProcessName(
            String pkg,
            String defProc,
            CharSequence procSeq,
            int flags,
            String[] separateProcesses,
            ParseInput input) {
        if ((flags & 2) != 0 && !"system".contentEquals(procSeq)) {
            return input.success(defProc != null ? defProc : pkg);
        }
        if (separateProcesses != null) {
            for (int i = separateProcesses.length - 1; i >= 0; i--) {
                String sp = separateProcesses[i];
                if (sp.equals(pkg) || sp.equals(defProc) || sp.contentEquals(procSeq)) {
                    return input.success(pkg);
                }
            }
        }
        if (procSeq == null || procSeq.length() <= 0) {
            ParseResult<String> nameResult = input.success(defProc);
            return nameResult;
        }
        ParseResult<String> nameResult2 = buildCompoundName(pkg, procSeq, "process", input);
        return input.success(TextUtils.safeIntern(nameResult2.getResult()));
    }

    public static ParseResult<String> buildTaskAffinityName(
            String pkg, String defProc, CharSequence procSeq, ParseInput input) {
        if (procSeq == null) {
            return input.success(defProc);
        }
        if (procSeq.length() <= 0) {
            return input.success(null);
        }
        return buildCompoundName(pkg, procSeq, "taskAffinity", input);
    }

    public static ParseResult<String> buildCompoundName(
            String pkg, CharSequence procSeq, String type, ParseInput input) {
        String proc = procSeq.toString();
        char c = proc.charAt(0);
        if (pkg != null && c == ':') {
            if (proc.length() < 2) {
                return input.error(
                        "Bad "
                                + type
                                + " name "
                                + proc
                                + " in package "
                                + pkg
                                + ": must be at least two characters");
            }
            String subName = proc.substring(1);
            ParseResult<?> nameResult =
                    FrameworkParsingPackageUtils.validateName(input, subName, false, false);
            if (nameResult.isError()) {
                return input.error(
                        "Invalid "
                                + type
                                + " name "
                                + proc
                                + " in package "
                                + pkg
                                + ": "
                                + nameResult.getErrorMessage());
            }
            return input.success(pkg + proc);
        }
        if (!"system".equals(proc)) {
            ParseResult<?> nameResult2 =
                    FrameworkParsingPackageUtils.validateName(input, proc, true, false);
            if (nameResult2.isError()) {
                return input.error(
                        "Invalid "
                                + type
                                + " name "
                                + proc
                                + " in package "
                                + pkg
                                + ": "
                                + nameResult2.getErrorMessage());
            }
        }
        return input.success(proc);
    }

    public static int flag(int flag, int attribute, TypedArray typedArray) {
        if (typedArray.getBoolean(attribute, false)) {
            return flag;
        }
        return 0;
    }

    public static int flag(int flag, int attribute, boolean defaultValue, TypedArray typedArray) {
        if (typedArray.getBoolean(attribute, defaultValue)) {
            return flag;
        }
        return 0;
    }

    public static CharSequence getNonLocalizedLabel(ParsedComponent component) {
        return component.getNonLocalizedLabel();
    }

    public static int getIcon(ParsedComponent component) {
        return component.getIcon();
    }
}
