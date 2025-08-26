package com.android.internal.pm.pkg.component;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ComponentParseUtils {
    public static boolean isImplicitlyExposedIntent(ParsedIntentInfo parsedIntentInfo) {
        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        return intentFilter.hasCategory(Intent.CATEGORY_BROWSABLE) || intentFilter.hasAction(Intent.ACTION_SEND) || intentFilter.hasAction(Intent.ACTION_SENDTO) || intentFilter.hasAction(Intent.ACTION_SEND_MULTIPLE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0047, code lost:
    
        return r8.success(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static <Component extends ParsedComponentImpl> ParseResult<Component> parseAllMetaData(ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, String str, Component component, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParseResult<Bundle> parseResultUnknownTag;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                if ("meta-data".equals(xmlResourceParser.getName())) {
                    parseResultUnknownTag = ParsedComponentUtils.addMetaData(component, parsingPackage, resources, xmlResourceParser, parseInput);
                } else {
                    parseResultUnknownTag = ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                }
                if (parseResultUnknownTag.isError()) {
                    return parseInput.error(parseResultUnknownTag);
                }
            }
        }
    }

    public static ParseResult<String> buildProcessName(String str, String str2, CharSequence charSequence, int i, String[] strArr, ParseInput parseInput) {
        if ((i & 2) != 0 && !"system".contentEquals(charSequence)) {
            if (str2 != null) {
                str = str2;
            }
            return parseInput.success(str);
        }
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                String str3 = strArr[length];
                if (str3.equals(str) || str3.equals(str2) || str3.contentEquals(charSequence)) {
                    return parseInput.success(str);
                }
            }
        }
        if (charSequence == null || charSequence.length() <= 0) {
            return parseInput.success(str2);
        }
        return parseInput.success(TextUtils.safeIntern(buildCompoundName(str, charSequence, "process", parseInput).getResult()));
    }

    public static ParseResult<String> buildTaskAffinityName(String str, String str2, CharSequence charSequence, ParseInput parseInput) {
        if (charSequence == null) {
            return parseInput.success(str2);
        }
        if (charSequence.length() <= 0) {
            return parseInput.success(null);
        }
        return buildCompoundName(str, charSequence, "taskAffinity", parseInput);
    }

    public static ParseResult<String> buildCompoundName(String str, CharSequence charSequence, String str2, ParseInput parseInput) {
        String string = charSequence.toString();
        char cCharAt = string.charAt(0);
        if (str != null && cCharAt == ':') {
            if (string.length() < 2) {
                return parseInput.error("Bad " + str2 + " name " + string + " in package " + str + ": must be at least two characters");
            }
            ParseResult parseResultValidateName = FrameworkParsingPackageUtils.validateName(parseInput, string.substring(1), false, false);
            if (parseResultValidateName.isError()) {
                return parseInput.error("Invalid " + str2 + " name " + string + " in package " + str + ": " + parseResultValidateName.getErrorMessage());
            }
            return parseInput.success(str + string);
        }
        if (!"system".equals(string)) {
            ParseResult parseResultValidateName2 = FrameworkParsingPackageUtils.validateName(parseInput, string, true, false);
            if (parseResultValidateName2.isError()) {
                return parseInput.error("Invalid " + str2 + " name " + string + " in package " + str + ": " + parseResultValidateName2.getErrorMessage());
            }
        }
        return parseInput.success(string);
    }

    public static int flag(int i, int i2, TypedArray typedArray) {
        if (typedArray.getBoolean(i2, false)) {
            return i;
        }
        return 0;
    }

    public static int flag(int i, int i2, boolean z, TypedArray typedArray) {
        if (typedArray.getBoolean(i2, z)) {
            return i;
        }
        return 0;
    }

    public static CharSequence getNonLocalizedLabel(ParsedComponent parsedComponent) {
        return parsedComponent.getNonLocalizedLabel();
    }

    public static int getIcon(ParsedComponent parsedComponent) {
        return parsedComponent.getIcon();
    }
}
