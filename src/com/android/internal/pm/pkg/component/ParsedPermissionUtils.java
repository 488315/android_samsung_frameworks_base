package com.android.internal.pm.pkg.component;

import android.content.pm.PermissionInfo;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedPermissionUtils {
    private static final String TAG = "PackageParsing";

    public static ParseResult<ParsedPermission> parsePermission(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput, int i) throws XmlPullParserException, IOException {
        TypedArray typedArray;
        ParseResult<?> component;
        String packageName = parsingPackage.getPackageName();
        ParsedPermissionImpl parsedPermissionImpl = new ParsedPermissionImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermission);
        try {
            component = ParsedComponentUtils.parseComponent(parsedPermissionImpl, str, parsingPackage, typedArrayObtainAttributes, z, parseInput, 9, 5, 1, 0, 7, 2, 10);
            typedArray = typedArrayObtainAttributes;
        } catch (Throwable th) {
            th = th;
            typedArray = typedArrayObtainAttributes;
        }
        try {
            if (component.isError()) {
                ParseResult<ParsedPermission> parseResultError = parseInput.error(component);
                if (typedArray != null) {
                    typedArray.close();
                }
                return parseResultError;
            }
            int i2 = typedArray.getInt(6, -1);
            if (i2 != -1 && i2 < Build.VERSION.SDK_INT) {
                ParseResult<ParsedPermission> parseResultSuccess = parseInput.success(null);
                if (typedArray != null) {
                    typedArray.close();
                }
                return parseResultSuccess;
            }
            if (typedArray.hasValue(12)) {
                boolean z2 = (i & 512) != 0;
                if ("android".equals(packageName) || (Flags.replaceBodySensorPermissionEnabled() && z2)) {
                    parsedPermissionImpl.setBackgroundPermission(typedArray.getNonResourceString(12));
                } else {
                    Slog.w("PackageParsing", packageName + " defines a background permission. Only the " + "'android'".concat(Flags.replaceBodySensorPermissionEnabled() ? " and APK_IN_APEX" : "") + " packages can do that.");
                }
            }
            parsedPermissionImpl.setGroup(typedArray.getNonResourceString(4)).setRequestRes(typedArray.getResourceId(13, 0)).setProtectionLevel(typedArray.getInt(3, 0)).setFlags(typedArray.getInt(8, 0));
            int resourceId = typedArray.getResourceId(11, 0);
            if (resourceId != 0) {
                if (resources.getResourceTypeName(resourceId).equals("array")) {
                    String[] stringArray = resources.getStringArray(resourceId);
                    if (stringArray != null) {
                        parsedPermissionImpl.setKnownCerts(stringArray);
                    }
                } else {
                    String string = resources.getString(resourceId);
                    if (string != null) {
                        parsedPermissionImpl.setKnownCert(string);
                    }
                }
                if (parsedPermissionImpl.getKnownCerts().isEmpty()) {
                    Slog.w("PackageParsing", packageName + " defines a knownSigner permission but the provided knownCerts resource is null");
                }
            } else {
                String string2 = typedArray.getString(11);
                if (string2 != null) {
                    parsedPermissionImpl.setKnownCert(string2);
                }
            }
            if (!isRuntime(parsedPermissionImpl) || !"android".equals(parsedPermissionImpl.getPackageName())) {
                parsedPermissionImpl.setFlags(parsedPermissionImpl.getFlags() & (-5));
                parsedPermissionImpl.setFlags(parsedPermissionImpl.getFlags() & (-9));
            } else if ((parsedPermissionImpl.getFlags() & 4) != 0 && (parsedPermissionImpl.getFlags() & 8) != 0) {
                throw new IllegalStateException("Permission cannot be both soft and hard restricted: " + parsedPermissionImpl.getName());
            }
            if (typedArray != null) {
                typedArray.close();
            }
            parsedPermissionImpl.setProtectionLevel(PermissionInfo.fixProtectionLevel(parsedPermissionImpl.getProtectionLevel()));
            if ((getProtectionFlags(parsedPermissionImpl) & (-12353)) != 0 && getProtection(parsedPermissionImpl) != 2 && getProtection(parsedPermissionImpl) != 4) {
                return parseInput.error("<permission> protectionLevel specifies a non-instant, non-appop, non-runtimeOnly flag but is not based on signature or internal type");
            }
            ParseResult<?> allMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
            if (allMetaData.isError()) {
                return parseInput.error(allMetaData);
            }
            return parseInput.success((ParsedPermission) allMetaData.getResult());
        } catch (Throwable th2) {
            th = th2;
            Throwable th3 = th;
            if (typedArray == null) {
                throw th3;
            }
            try {
                typedArray.close();
                throw th3;
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
                throw th3;
            }
        }
    }

    public static ParseResult<ParsedPermission> parsePermissionTree(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsedPermissionImpl parsedPermissionImpl = new ParsedPermissionImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionTree);
        try {
            ParseResult<?> component = ParsedComponentUtils.parseComponent(parsedPermissionImpl, str, parsingPackage, typedArrayObtainAttributes, z, parseInput, 4, -1, 1, 0, 3, 2, 5);
            if (component.isError()) {
                return parseInput.error(component);
            }
            typedArrayObtainAttributes.recycle();
            int iIndexOf = parsedPermissionImpl.getName().indexOf(46);
            if (iIndexOf > 0) {
                iIndexOf = parsedPermissionImpl.getName().indexOf(46, iIndexOf + 1);
            }
            if (iIndexOf < 0) {
                return parseInput.error("<permission-tree> name has less than three segments: " + parsedPermissionImpl.getName());
            }
            parsedPermissionImpl.setProtectionLevel(0).setTree(true);
            ParseResult<?> allMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
            if (allMetaData.isError()) {
                return parseInput.error(allMetaData);
            }
            return parseInput.success((ParsedPermission) allMetaData.getResult());
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }

    public static ParseResult<ParsedPermissionGroup> parsePermissionGroup(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsedPermissionGroupImpl parsedPermissionGroupImpl = new ParsedPermissionGroupImpl();
        String str = "<" + xmlResourceParser.getName() + ">";
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestPermissionGroup);
        try {
            ParseResult<?> component = ParsedComponentUtils.parseComponent(parsedPermissionGroupImpl, str, parsingPackage, typedArrayObtainAttributes, z, parseInput, 7, 4, 1, 0, 5, 2, 8);
            if (component.isError()) {
                return parseInput.error(component);
            }
            parsedPermissionGroupImpl.setRequestDetailRes(typedArrayObtainAttributes.getResourceId(12, 0)).setBackgroundRequestRes(typedArrayObtainAttributes.getResourceId(9, 0)).setBackgroundRequestDetailRes(typedArrayObtainAttributes.getResourceId(10, 0)).setRequestRes(typedArrayObtainAttributes.getResourceId(11, 0)).setPriority(typedArrayObtainAttributes.getInt(3, 0)).setFlags(typedArrayObtainAttributes.getInt(6, 0));
            typedArrayObtainAttributes.recycle();
            ParseResult<?> allMetaData = ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionGroupImpl, parseInput);
            if (allMetaData.isError()) {
                return parseInput.error(allMetaData);
            }
            return parseInput.success((ParsedPermissionGroup) allMetaData.getResult());
        } finally {
            typedArrayObtainAttributes.recycle();
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
