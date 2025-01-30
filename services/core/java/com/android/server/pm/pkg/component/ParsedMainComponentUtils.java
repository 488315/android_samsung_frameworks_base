package com.android.server.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Slog;
import com.android.server.pm.pkg.parsing.ParsingPackage;

/* loaded from: classes3.dex */
public abstract class ParsedMainComponentUtils {
    /* JADX WARN: Removed duplicated region for block: B:12:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ParseResult parseMainComponent(ParsedMainComponentImpl parsedMainComponentImpl, String str, String[] strArr, ParsingPackage parsingPackage, TypedArray typedArray, int i, boolean z, String str2, ParseInput parseInput, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        ParseInput parseInput2;
        String nonConfigurationString;
        String nonResourceString;
        ParseResult parseComponent = ParsedComponentUtils.parseComponent(parsedMainComponentImpl, str, parsingPackage, typedArray, z, parseInput, i2, i3, i6, i7, i8, i9, i11);
        if (parseComponent.isError()) {
            return parseComponent;
        }
        if (i4 != -1) {
            parsedMainComponentImpl.setDirectBootAware(typedArray.getBoolean(i4, false));
            if (parsedMainComponentImpl.isDirectBootAware()) {
                parsingPackage.setPartiallyDirectBootAware(true);
                if (i5 != -1) {
                    parsedMainComponentImpl.setEnabled(typedArray.getBoolean(i5, true));
                }
                if (i10 == -1) {
                    if (parsingPackage.getTargetSdkVersion() >= 8) {
                        nonResourceString = typedArray.getNonConfigurationString(i10, 1024);
                    } else {
                        nonResourceString = typedArray.getNonResourceString(i10);
                    }
                    ParseResult buildProcessName = ComponentParseUtils.buildProcessName(parsingPackage.getPackageName(), parsingPackage.getProcessName(), nonResourceString, i, strArr, parseInput);
                    if (buildProcessName.isError()) {
                        return parseInput.error(buildProcessName);
                    }
                    parseInput2 = parseInput;
                    parsedMainComponentImpl.setProcessName((String) buildProcessName.getResult());
                } else {
                    parseInput2 = parseInput;
                }
                if (i12 != -1) {
                    parsedMainComponentImpl.setSplitName(typedArray.getNonConfigurationString(i12, 0));
                }
                if (str2 != null && parsedMainComponentImpl.getSplitName() == null) {
                    parsedMainComponentImpl.setSplitName(str2);
                }
                if (i13 != -1 && (nonConfigurationString = typedArray.getNonConfigurationString(i13, 0)) != null) {
                    parsedMainComponentImpl.setAttributionTags(nonConfigurationString.split("\\|"));
                }
                return parseInput2.success(parsedMainComponentImpl);
            }
        }
        if (i5 != -1) {
        }
        if (i10 == -1) {
        }
        if (i12 != -1) {
        }
        if (str2 != null) {
            parsedMainComponentImpl.setSplitName(str2);
        }
        if (i13 != -1) {
            parsedMainComponentImpl.setAttributionTags(nonConfigurationString.split("\\|"));
        }
        return parseInput2.success(parsedMainComponentImpl);
    }

    public static ParseResult parseIntentFilter(ParsedMainComponent parsedMainComponent, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, ParseInput parseInput) {
        int i;
        ParseResult parseIntentInfo = ParsedIntentInfoUtils.parseIntentInfo(parsedMainComponent.getName(), parsingPackage, resources, xmlResourceParser, z2, z3, parseInput);
        if (parseIntentInfo.isError()) {
            return parseInput.error(parseIntentInfo);
        }
        ParsedIntentInfo parsedIntentInfo = (ParsedIntentInfo) parseIntentInfo.getResult();
        IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        if (intentFilter.countActions() == 0 && z5) {
            Slog.w("PackageParsing", "No actions in " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            return parseInput.success((Object) null);
        }
        if (z) {
            i = 1;
        } else {
            i = (z4 && ComponentParseUtils.isImplicitlyExposedIntent(parsedIntentInfo)) ? 2 : 0;
        }
        intentFilter.setVisibilityToInstantApp(i);
        return parseInput.success((ParsedIntentInfoImpl) parseIntentInfo.getResult());
    }
}
