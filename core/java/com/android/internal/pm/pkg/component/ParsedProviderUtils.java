package com.android.internal.pm.pkg.component;

import android.content.pm.PathPermission;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.PatternMatcher;
import android.util.Slog;

import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;

/* loaded from: classes5.dex */
public class ParsedProviderUtils {
    private static final String TAG = "PackageParsing";

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0162, code lost:

       return r34.error("<provider> does not include authorities attribute");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.component.ParsedProvider>
            parseProvider(
                    java.lang.String[] r27,
                    com.android.internal.pm.pkg.parsing.ParsingPackage r28,
                    android.content.res.Resources r29,
                    android.content.res.XmlResourceParser r30,
                    int r31,
                    boolean r32,
                    java.lang.String r33,
                    android.content.pm.parsing.result.ParseInput r34)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedProviderUtils.parseProvider(java.lang.String[],"
                    + " com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser, int,"
                    + " boolean, java.lang.String,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x010f, code lost:

       return r24.success(r23);
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0069, code lost:

       if (r8.equals("meta-data") != false) goto L36;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.component.ParsedProvider>
            parseProviderTags(
                    com.android.internal.pm.pkg.parsing.ParsingPackage r18,
                    java.lang.String r19,
                    android.content.res.Resources r20,
                    android.content.res.XmlResourceParser r21,
                    boolean r22,
                    com.android.internal.pm.pkg.component.ParsedProviderImpl r23,
                    android.content.pm.parsing.result.ParseInput r24)
                    throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedProviderUtils.parseProviderTags(com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " java.lang.String, android.content.res.Resources,"
                    + " android.content.res.XmlResourceParser, boolean,"
                    + " com.android.internal.pm.pkg.component.ParsedProviderImpl,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    private static ParseResult<ParsedProvider> parseGrantUriPermission(
            ParsedProviderImpl provider,
            ParsingPackage pkg,
            Resources resources,
            XmlResourceParser parser,
            ParseInput input) {
        TypedArray sa =
                resources.obtainAttributes(parser, R.styleable.AndroidManifestGrantUriPermission);
        try {
            String name = parser.getName();
            PatternMatcher pa = null;
            String str = sa.getNonConfigurationString(4, 0);
            if (str != null) {
                pa = new PatternMatcher(str, 3);
            } else {
                String str2 = sa.getNonConfigurationString(2, 0);
                if (str2 != null) {
                    pa = new PatternMatcher(str2, 2);
                } else {
                    String str3 = sa.getNonConfigurationString(1, 0);
                    if (str3 != null) {
                        pa = new PatternMatcher(str3, 1);
                    } else {
                        String str4 = sa.getNonConfigurationString(3, 0);
                        if (str4 != null) {
                            pa = new PatternMatcher(str4, 4);
                        } else {
                            String str5 = sa.getNonConfigurationString(0, 0);
                            if (str5 != null) {
                                pa = new PatternMatcher(str5, 0);
                            }
                        }
                    }
                }
            }
            if (pa != null) {
                provider.addUriPermissionPattern(pa);
                provider.setGrantUriPermissions(true);
            } else {
                Slog.w(
                        "PackageParsing",
                        "Unknown element under <path-permission>: "
                                + name
                                + " at "
                                + pkg.getBaseApkPath()
                                + " "
                                + parser.getPositionDescription());
            }
            return input.success(provider);
        } finally {
            sa.recycle();
        }
    }

    private static ParseResult<ParsedProvider> parsePathPermission(
            ParsedProviderImpl provider,
            ParsingPackage pkg,
            Resources resources,
            XmlResourceParser parser,
            ParseInput input) {
        PathPermission pa;
        TypedArray sa =
                resources.obtainAttributes(parser, R.styleable.AndroidManifestPathPermission);
        try {
            String name = parser.getName();
            String permission = sa.getNonConfigurationString(0, 0);
            String readPermission = sa.getNonConfigurationString(1, 0);
            if (readPermission == null) {
                readPermission = permission;
            }
            String writePermission = sa.getNonConfigurationString(2, 0);
            if (writePermission == null) {
                writePermission = permission;
            }
            boolean havePerm = false;
            if (readPermission != null) {
                readPermission = readPermission.intern();
                havePerm = true;
            }
            if (writePermission != null) {
                writePermission = writePermission.intern();
                havePerm = true;
            }
            if (havePerm) {
                String path = sa.getNonConfigurationString(7, 0);
                if (path == null) {
                    String path2 = sa.getNonConfigurationString(5, 0);
                    if (path2 == null) {
                        String path3 = sa.getNonConfigurationString(4, 0);
                        if (path3 == null) {
                            String path4 = sa.getNonConfigurationString(6, 0);
                            if (path4 == null) {
                                String path5 = sa.getNonConfigurationString(3, 0);
                                if (path5 == null) {
                                    pa = null;
                                } else {
                                    PathPermission pa2 =
                                            new PathPermission(
                                                    path5, 0, readPermission, writePermission);
                                    pa = pa2;
                                }
                            } else {
                                pa = new PathPermission(path4, 4, readPermission, writePermission);
                            }
                        } else {
                            pa = new PathPermission(path3, 1, readPermission, writePermission);
                        }
                    } else {
                        pa = new PathPermission(path2, 2, readPermission, writePermission);
                    }
                } else {
                    pa = new PathPermission(path, 3, readPermission, writePermission);
                }
                if (pa != null) {
                    provider.addPathPermission(pa);
                } else {
                    Slog.w(
                            "PackageParsing",
                            "No path, pathPrefix, or pathPattern for <path-permission>: "
                                    + name
                                    + " at "
                                    + pkg.getBaseApkPath()
                                    + " "
                                    + parser.getPositionDescription());
                }
                return input.success(provider);
            }
            Slog.w(
                    "PackageParsing",
                    "No readPermission or writePermission for <path-permission>: "
                            + name
                            + " at "
                            + pkg.getBaseApkPath()
                            + " "
                            + parser.getPositionDescription());
            return input.success(provider);
        } finally {
            sa.recycle();
        }
    }
}
