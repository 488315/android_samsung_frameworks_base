package com.android.internal.pm.pkg.component;

import android.Manifest;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.R;
import com.android.internal.pm.pkg.component.flags.Flags;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedProcessUtils {
    private static ParseResult<Set<String>> parseDenyPermission(Set<String> set, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestDenyPermission);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(Manifest.permission.INTERNET)) {
                set = CollectionUtils.add(set, nonConfigurationString);
            }
            typedArrayObtainAttributes.recycle();
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (Throwable th) {
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    private static ParseResult<Set<String>> parseAllowPermission(Set<String> set, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestAllowPermission);
        try {
            String nonConfigurationString = typedArrayObtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(Manifest.permission.INTERNET)) {
                set = CollectionUtils.remove(set, nonConfigurationString);
            }
            typedArrayObtainAttributes.recycle();
            XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (Throwable th) {
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f7, code lost:
    
        return r15.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ParseResult<ParsedProcess> parseProcess(Set<String> set, String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<Set<String>> allowPermission;
        ParsedProcessImpl parsedProcessImpl = new ParsedProcessImpl();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestProcess);
        if (set != null) {
            try {
                parsedProcessImpl.setDeniedPermissions(new ArraySet(set));
            } finally {
                typedArrayObtainAttributes.recycle();
            }
        }
        ParseResult<String> parseResultBuildProcessName = ComponentParseUtils.buildProcessName(parsingPackage.getPackageName(), parsingPackage.getPackageName(), typedArrayObtainAttributes.getNonConfigurationString(1, 0), i, strArr, parseInput);
        if (parseResultBuildProcessName.isError()) {
            return parseInput.error(parseResultBuildProcessName);
        }
        String packageName = parsingPackage.getPackageName();
        String strBuildClassName = ParsingUtils.buildClassName(packageName, typedArrayObtainAttributes.getNonConfigurationString(0, 0));
        parsedProcessImpl.setName(parseResultBuildProcessName.getResult());
        parsedProcessImpl.putAppClassNameForPackage(packageName, strBuildClassName);
        parsedProcessImpl.setGwpAsanMode(typedArrayObtainAttributes.getInt(3, -1));
        parsedProcessImpl.setMemtagMode(typedArrayObtainAttributes.getInt(4, -1));
        if (typedArrayObtainAttributes.hasValue(5)) {
            parsedProcessImpl.setNativeHeapZeroInitialized(typedArrayObtainAttributes.getBoolean(5, false) ? 1 : 0);
        }
        if (Flags.enablePerProcessUseEmbeddedDexAttr()) {
            parsedProcessImpl.setUseEmbeddedDex(typedArrayObtainAttributes.getBoolean(2, false));
        } else {
            parsedProcessImpl.setUseEmbeddedDex(false);
        }
        typedArrayObtainAttributes.recycle();
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlResourceParser.getName();
                name.hashCode();
                if (name.equals("allow-permission")) {
                    allowPermission = parseAllowPermission(parsedProcessImpl.getDeniedPermissions(), resources, xmlResourceParser, parseInput);
                    if (allowPermission.isSuccess()) {
                        parsedProcessImpl.setDeniedPermissions(allowPermission.getResult());
                    }
                } else if (name.equals("deny-permission")) {
                    allowPermission = parseDenyPermission(parsedProcessImpl.getDeniedPermissions(), resources, xmlResourceParser, parseInput);
                    if (allowPermission.isSuccess()) {
                        parsedProcessImpl.setDeniedPermissions(allowPermission.getResult());
                    }
                } else {
                    allowPermission = ParsingUtils.unknownTag("<process>", parsingPackage, xmlResourceParser, parseInput);
                }
                if (allowPermission.isError()) {
                    return parseInput.error(allowPermission);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e5, code lost:
    
        return r15.success(r0);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ParseResult<ArrayMap<String, ParsedProcess>> parseProcesses(String[] strArr, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, int i, ParseInput parseInput) throws XmlPullParserException, IOException {
        String[] strArr2;
        ParsingPackage parsingPackage2;
        Resources resources2;
        XmlResourceParser xmlResourceParser2;
        int i2;
        ParseInput parseInput2;
        ParseResult<?> allowPermission;
        ArrayMap arrayMap = new ArrayMap();
        int depth = xmlResourceParser.getDepth();
        Set set = null;
        while (true) {
            int next = xmlResourceParser.next();
            char c = 1;
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next == 3 || next == 4) {
                    strArr2 = strArr;
                    parsingPackage2 = parsingPackage;
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    i2 = i;
                    parseInput2 = parseInput;
                } else {
                    String name = xmlResourceParser.getName();
                    name.hashCode();
                    switch (name.hashCode()) {
                        case -1239165229:
                            if (!name.equals("allow-permission")) {
                                c = 65535;
                                break;
                            } else {
                                c = 0;
                                break;
                            }
                        case -309518737:
                            if (!name.equals("process")) {
                            }
                            break;
                        case 1658008624:
                            if (name.equals("deny-permission")) {
                                c = 2;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            strArr2 = strArr;
                            parsingPackage2 = parsingPackage;
                            resources2 = resources;
                            xmlResourceParser2 = xmlResourceParser;
                            i2 = i;
                            parseInput2 = parseInput;
                            allowPermission = parseAllowPermission(set, resources2, xmlResourceParser2, parseInput2);
                            if (allowPermission.isSuccess()) {
                                set = (Set) allowPermission.getResult();
                                break;
                            }
                            break;
                        case 1:
                            strArr2 = strArr;
                            parsingPackage2 = parsingPackage;
                            resources2 = resources;
                            xmlResourceParser2 = xmlResourceParser;
                            i2 = i;
                            parseInput2 = parseInput;
                            allowPermission = parseProcess(set, strArr2, parsingPackage2, resources2, xmlResourceParser2, i2, parseInput2);
                            if (allowPermission.isSuccess()) {
                                ParsedProcess parsedProcess = (ParsedProcess) allowPermission.getResult();
                                if (arrayMap.put(parsedProcess.getName(), parsedProcess) != null) {
                                    allowPermission = parseInput2.error("<process> specified existing name '" + parsedProcess.getName() + "'");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            allowPermission = parseDenyPermission(set, resources, xmlResourceParser, parseInput);
                            if (allowPermission.isSuccess()) {
                                set = (Set) allowPermission.getResult();
                            }
                            strArr2 = strArr;
                            parsingPackage2 = parsingPackage;
                            resources2 = resources;
                            xmlResourceParser2 = xmlResourceParser;
                            i2 = i;
                            parseInput2 = parseInput;
                            break;
                        default:
                            allowPermission = ParsingUtils.unknownTag("<processes>", parsingPackage, xmlResourceParser, parseInput);
                            strArr2 = strArr;
                            parsingPackage2 = parsingPackage;
                            resources2 = resources;
                            xmlResourceParser2 = xmlResourceParser;
                            i2 = i;
                            parseInput2 = parseInput;
                            break;
                    }
                    if (allowPermission.isError()) {
                        return parseInput2.error(allowPermission);
                    }
                }
                strArr = strArr2;
                parsingPackage = parsingPackage2;
                resources = resources2;
                xmlResourceParser = xmlResourceParser2;
                i = i2;
                parseInput = parseInput2;
            }
        }
    }
}
