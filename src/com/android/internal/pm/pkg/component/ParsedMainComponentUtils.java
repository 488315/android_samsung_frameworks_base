package com.android.internal.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.security.Flags;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedMainComponentUtils {
    private static final String TAG = "PackageParsing";

    public static int resolveIntentMatchingFlags(int i, int i2) {
        return i2 == 0 ? i : i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static <Component extends ParsedMainComponentImpl> ParseResult<Component> parseMainComponent(Component component, String str, String[] strArr, ParsingPackage parsingPackage, TypedArray typedArray, int i, boolean z, String str2, ParseInput parseInput, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14) {
        ParsingPackage parsingPackage2;
        ParseInput parseInput2;
        String nonConfigurationString;
        String nonResourceString;
        ParseResult<Component> component2 = ParsedComponentUtils.parseComponent(component, str, parsingPackage, typedArray, z, parseInput, i2, i3, i6, i7, i8, i9, i11);
        if (component2.isError()) {
            return component2;
        }
        if (i4 != -1) {
            component.setDirectBootAware(typedArray.getBoolean(i4, false));
            if (component.isDirectBootAware()) {
                parsingPackage2 = parsingPackage;
                parsingPackage2.setPartiallyDirectBootAware(true);
            } else {
                parsingPackage2 = parsingPackage;
            }
        }
        if (i5 != -1) {
            component.setEnabled(typedArray.getBoolean(i5, true));
        }
        if (i10 != -1) {
            if (parsingPackage2.getTargetSdkVersion() >= 8) {
                nonResourceString = typedArray.getNonConfigurationString(i10, 1024);
            } else {
                nonResourceString = typedArray.getNonResourceString(i10);
            }
            ParseResult<String> parseResultBuildProcessName = ComponentParseUtils.buildProcessName(parsingPackage2.getPackageName(), parsingPackage2.getProcessName(), nonResourceString, i, strArr, parseInput);
            parseInput2 = parseInput;
            if (parseResultBuildProcessName.isError()) {
                return parseInput2.error(parseResultBuildProcessName);
            }
            component.setProcessName(parseResultBuildProcessName.getResult());
        } else {
            parseInput2 = parseInput;
        }
        if (i12 != -1) {
            component.setSplitName(typedArray.getNonConfigurationString(i12, 0));
        }
        if (str2 != null && component.getSplitName() == null) {
            component.setSplitName(str2);
        }
        if (i13 != -1 && (nonConfigurationString = typedArray.getNonConfigurationString(i13, 0)) != null) {
            component.setAttributionTags(nonConfigurationString.split("\\|"));
        }
        if (Flags.enableIntentMatchingFlags()) {
            component.setIntentMatchingFlags(resolveIntentMatchingFlags(parsingPackage2.getIntentMatchingFlags(), typedArray.getInt(i14, 0)));
        }
        return parseInput2.success(component);
    }

    static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(ParsedMainComponent parsedMainComponent, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, ParseInput parseInput) throws XmlPullParserException, IOException {
        int i;
        ParseResult<ParsedIntentInfoImpl> intentInfo = ParsedIntentInfoUtils.parseIntentInfo(parsedMainComponent.getName(), parsingPackage, resources, xmlResourceParser, z2, z3, parseInput);
        if (intentInfo.isError()) {
            return parseInput.error(intentInfo);
        }
        ParsedIntentInfoImpl result = intentInfo.getResult();
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
        return parseInput.success(intentInfo.getResult());
    }
}
