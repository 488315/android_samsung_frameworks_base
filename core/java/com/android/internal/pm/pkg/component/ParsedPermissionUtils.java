package com.android.internal.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import android.util.EventLog;

import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class ParsedPermissionUtils {
    private static final String TAG = "PackageParsing";

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0174, code lost:

       r1.close();
    */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<
                    com.android.internal.pm.pkg.component.ParsedPermission>
            parsePermission(
                    com.android.internal.pm.pkg.parsing.ParsingPackage r22,
                    android.content.res.Resources r23,
                    android.content.res.XmlResourceParser r24,
                    boolean r25,
                    android.content.pm.parsing.result.ParseInput r26)
                    throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.pm.pkg.component.ParsedPermissionUtils.parsePermission(com.android.internal.pm.pkg.parsing.ParsingPackage,"
                    + " android.content.res.Resources, android.content.res.XmlResourceParser,"
                    + " boolean,"
                    + " android.content.pm.parsing.result.ParseInput):android.content.pm.parsing.result.ParseResult");
    }

    public static ParseResult<ParsedPermission> parsePermissionTree(
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            boolean useRoundIcon,
            ParseInput input)
            throws IOException, XmlPullParserException {
        int index;
        ParsedPermissionImpl permission = new ParsedPermissionImpl();
        String tag = "<" + parser.getName() + ">";
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionTree);
        try {
            ParseResult<ParsedPermissionImpl> result =
                    ParsedComponentUtils.parseComponent(
                            permission, tag, pkg, sa, useRoundIcon, input, 4, -1, 1, 0, 3, 2, 5);
            if (result.isError()) {
                return input.error(result);
            }
            sa.recycle();
            int index2 = permission.getName().indexOf(46);
            if (index2 <= 0) {
                index = index2;
            } else {
                index = permission.getName().indexOf(46, index2 + 1);
            }
            if (index < 0) {
                return input.error(
                        "<permission-tree> name has less than three segments: "
                                + permission.getName());
            }
            permission.setProtectionLevel(0).setTree(true);
            ParseResult<ParsedPermissionImpl> result2 =
                    ComponentParseUtils.parseAllMetaData(pkg, res, parser, tag, permission, input);
            if (!result2.isError()) {
                return input.success(result2.getResult());
            }
            return input.error(result2);
        } finally {
            sa.recycle();
        }
    }

    public static ParseResult<ParsedPermissionGroup> parsePermissionGroup(
            ParsingPackage pkg,
            Resources res,
            XmlResourceParser parser,
            boolean useRoundIcon,
            ParseInput input)
            throws IOException, XmlPullParserException {
        TypedArray sa;
        ParsedPermissionGroupImpl permissionGroup = new ParsedPermissionGroupImpl();
        String tag = "<" + parser.getName() + ">";
        TypedArray sa2 = res.obtainAttributes(parser, R.styleable.AndroidManifestPermissionGroup);
        try {
            ParseResult<ParsedPermissionGroupImpl> result =
                    ParsedComponentUtils.parseComponent(
                            permissionGroup,
                            tag,
                            pkg,
                            sa2,
                            useRoundIcon,
                            input,
                            7,
                            4,
                            1,
                            0,
                            5,
                            2,
                            8);
            if (result.isError()) {
                try {
                    ParseResult<ParsedPermissionGroup> error = input.error(result);
                    sa2.recycle();
                    return error;
                } catch (Throwable th) {
                    th = th;
                    sa = sa2;
                }
            } else {
                sa = sa2;
                try {
                    permissionGroup
                            .setRequestDetailRes(sa.getResourceId(12, 0))
                            .setBackgroundRequestRes(sa.getResourceId(9, 0))
                            .setBackgroundRequestDetailRes(sa.getResourceId(10, 0))
                            .setRequestRes(sa.getResourceId(11, 0))
                            .setPriority(sa.getInt(3, 0))
                            .setFlags(sa.getInt(6, 0));
                    sa.recycle();
                    ParseResult<ParsedPermissionGroupImpl> result2 =
                            ComponentParseUtils.parseAllMetaData(
                                    pkg, res, parser, tag, permissionGroup, input);
                    return result2.isError()
                            ? input.error(result2)
                            : input.success(result2.getResult());
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            sa = sa2;
        }
        sa.recycle();
        throw th;
    }

    public static boolean isRuntime(ParsedPermission permission) {
        return getProtection(permission) == 1;
    }

    public static boolean isAppOp(ParsedPermission permission) {
        return (permission.getProtectionLevel() & 64) != 0;
    }

    public static int getProtection(ParsedPermission permission) {
        return permission.getProtectionLevel() & 15;
    }

    public static int getProtectionFlags(ParsedPermission permission) {
        return permission.getProtectionLevel() & (-16);
    }

    public static int calculateFootprint(ParsedPermission permission) {
        int size = permission.getName().length();
        CharSequence nonLocalizedLabel = permission.getNonLocalizedLabel();
        if (nonLocalizedLabel != null) {
            return size + nonLocalizedLabel.length();
        }
        return size;
    }

    private static boolean isMalformedDuplicate(ParsedPermission p1, ParsedPermission p2) {
        if (p1 == null || p2 == null || p1.isTree() || p2.isTree()) {
            return false;
        }
        return (p1.getProtectionLevel() == p2.getProtectionLevel()
                        && Objects.equals(p1.getGroup(), p2.getGroup()))
                ? false
                : true;
    }

    public static boolean declareDuplicatePermission(ParsingPackage pkg) {
        List<ParsedPermission> permissions = pkg.getPermissions();
        int size = permissions.size();
        if (size > 0) {
            ArrayMap<String, ParsedPermission> checkDuplicatePerm = new ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                ParsedPermission parsedPermission = permissions.get(i);
                String name = parsedPermission.getName();
                ParsedPermission perm = checkDuplicatePerm.get(name);
                if (isMalformedDuplicate(parsedPermission, perm)) {
                    EventLog.writeEvent(1397638484, "213323615");
                    return true;
                }
                checkDuplicatePerm.put(name, parsedPermission);
            }
            return false;
        }
        return false;
    }
}
