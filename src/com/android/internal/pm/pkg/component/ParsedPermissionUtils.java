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
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedPermissionUtils {
    private static final String TAG = "PackageParsing";

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0186, code lost:
    
        r4.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> parsePermission(com.android.internal.pm.pkg.parsing.ParsingPackage r17, android.content.res.Resources r18, android.content.res.XmlResourceParser r19, boolean r20, android.content.pm.parsing.result.ParseInput r21, int r22) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 479
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.pm.pkg.component.ParsedPermissionUtils.parsePermission(com.android.internal.pm.pkg.parsing.ParsingPackage, android.content.res.Resources, android.content.res.XmlResourceParser, boolean, android.content.pm.parsing.result.ParseInput, int):android.content.pm.parsing.result.ParseResult");
    }

    public static ParseResult<ParsedPermission> parsePermissionTree(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws IOException, XmlPullParserException {
        ParsedPermissionImpl parsedPermissionImpl = new ParsedPermissionImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionTree);
        try {
            ParseResult<?> parseComponent = ParsedComponentUtils.parseComponent(parsedPermissionImpl, str, parsingPackage, obtainAttributes, z, parseInput, 4, -1, 1, 0, 3, 2, 5);
            if (parseComponent.isError()) {
                return parseInput.error(parseComponent);
            }
            obtainAttributes.recycle();
            int indexOf = parsedPermissionImpl.getName().indexOf(46);
            if (indexOf > 0) {
                indexOf = parsedPermissionImpl.getName().indexOf(46, indexOf + 1);
            }
            if (indexOf < 0) {
                return parseInput.error("<permission-tree> name has less than three segments: " + parsedPermissionImpl.getName());
            }
            parsedPermissionImpl.setProtectionLevel(0).setTree(true);
            ParseResult<?> parseAllMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
            if (parseAllMetaData.isError()) {
                return parseInput.error(parseAllMetaData);
            }
            return parseInput.success((ParsedPermission) parseAllMetaData.getResult());
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static ParseResult<ParsedPermissionGroup> parsePermissionGroup(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws IOException, XmlPullParserException {
        ParsedPermissionGroupImpl parsedPermissionGroupImpl = new ParsedPermissionGroupImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionGroup);
        try {
            ParseResult<?> parseComponent = ParsedComponentUtils.parseComponent(parsedPermissionGroupImpl, str, parsingPackage, obtainAttributes, z, parseInput, 7, 4, 1, 0, 5, 2, 8);
            if (parseComponent.isError()) {
                return parseInput.error(parseComponent);
            }
            parsedPermissionGroupImpl.setRequestDetailRes(obtainAttributes.getResourceId(12, 0)).setBackgroundRequestRes(obtainAttributes.getResourceId(9, 0)).setBackgroundRequestDetailRes(obtainAttributes.getResourceId(10, 0)).setRequestRes(obtainAttributes.getResourceId(11, 0)).setPriority(obtainAttributes.getInt(3, 0)).setFlags(obtainAttributes.getInt(6, 0));
            obtainAttributes.recycle();
            ParseResult<?> parseAllMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionGroupImpl, parseInput);
            if (parseAllMetaData.isError()) {
                return parseInput.error(parseAllMetaData);
            }
            return parseInput.success((ParsedPermissionGroup) parseAllMetaData.getResult());
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static boolean isRuntime(ParsedPermission parsedPermission) {
        return getProtection(parsedPermission) == 1;
    }

    public static boolean isAppOp(ParsedPermission parsedPermission) {
        return (parsedPermission.getProtectionLevel() & 64) != 0;
    }

    public static int getProtection(ParsedPermission parsedPermission) {
        return parsedPermission.getProtectionLevel() & 15;
    }

    public static int getProtectionFlags(ParsedPermission parsedPermission) {
        return parsedPermission.getProtectionLevel() & (-16);
    }

    public static int calculateFootprint(ParsedPermission parsedPermission) {
        int length = parsedPermission.getName().length();
        CharSequence nonLocalizedLabel = parsedPermission.getNonLocalizedLabel();
        return nonLocalizedLabel != null ? length + nonLocalizedLabel.length() : length;
    }

    private static boolean isMalformedDuplicate(ParsedPermission parsedPermission, ParsedPermission parsedPermission2) {
        return (parsedPermission == null || parsedPermission2 == null || parsedPermission.isTree() || parsedPermission2.isTree() || (parsedPermission.getProtectionLevel() == parsedPermission2.getProtectionLevel() && Objects.equals(parsedPermission.getGroup(), parsedPermission2.getGroup()))) ? false : true;
    }

    public static boolean declareDuplicatePermission(ParsingPackage parsingPackage) {
        List<ParsedPermission> permissions = parsingPackage.getPermissions();
        int size = permissions.size();
        if (size > 0) {
            ArrayMap arrayMap = new ArrayMap(size);
            for (int i = 0; i < size; i++) {
                ParsedPermission parsedPermission = permissions.get(i);
                String name = parsedPermission.getName();
                if (isMalformedDuplicate(parsedPermission, (ParsedPermission) arrayMap.get(name))) {
                    EventLog.writeEvent(1397638484, "213323615");
                    return true;
                }
                arrayMap.put(name, parsedPermission);
            }
        }
        return false;
    }
}
