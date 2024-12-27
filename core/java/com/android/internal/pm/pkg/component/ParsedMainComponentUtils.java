package com.android.internal.pm.pkg.component;

import android.content.IntentFilter;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Slog;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
class ParsedMainComponentUtils {
    private static final String TAG = "PackageParsing";

    ParsedMainComponentUtils() {
    }

    static <Component extends ParsedMainComponentImpl> ParseResult<Component> parseMainComponent(Component component, String tag, String[] separateProcesses, ParsingPackage pkg, TypedArray array, int flags, boolean useRoundIcon, String defaultSplitName, ParseInput input, int bannerAttr, int descriptionAttr, int directBootAwareAttr, int enabledAttr, int iconAttr, int labelAttr, int logoAttr, int nameAttr, int processAttr, int roundIconAttr, int splitNameAttr, int attributionTagsAttr) {
        ParseInput parseInput;
        String attributionTags;
        CharSequence processName;
        ParseResult<Component> result = ParsedComponentUtils.parseComponent(component, tag, pkg, array, useRoundIcon, input, bannerAttr, descriptionAttr, iconAttr, labelAttr, logoAttr, nameAttr, roundIconAttr);
        if (result.isError()) {
            return result;
        }
        if (directBootAwareAttr != -1) {
            component.setDirectBootAware(array.getBoolean(directBootAwareAttr, false));
            if (component.isDirectBootAware()) {
                pkg.setPartiallyDirectBootAware(true);
            }
        }
        if (enabledAttr != -1) {
            component.setEnabled(array.getBoolean(enabledAttr, true));
        }
        if (processAttr == -1) {
            parseInput = input;
        } else {
            if (pkg.getTargetSdkVersion() >= 8) {
                processName = array.getNonConfigurationString(processAttr, 1024);
            } else {
                CharSequence processName2 = array.getNonResourceString(processAttr);
                processName = processName2;
            }
            ParseResult<String> processNameResult = ComponentParseUtils.buildProcessName(pkg.getPackageName(), pkg.getProcessName(), processName, flags, separateProcesses, input);
            if (processNameResult.isError()) {
                return input.error(processNameResult);
            }
            parseInput = input;
            component.setProcessName(processNameResult.getResult());
        }
        if (splitNameAttr != -1) {
            component.setSplitName(array.getNonConfigurationString(splitNameAttr, 0));
        }
        if (defaultSplitName != null && component.getSplitName() == null) {
            component.setSplitName(defaultSplitName);
        }
        if (attributionTagsAttr != -1 && (attributionTags = array.getNonConfigurationString(attributionTagsAttr, 0)) != null) {
            component.setAttributionTags(attributionTags.split("\\|"));
        }
        return parseInput.success(component);
    }

    static ParseResult<ParsedIntentInfoImpl> parseIntentFilter(ParsedMainComponent mainComponent, ParsingPackage pkg, Resources resources, XmlResourceParser parser, boolean visibleToEphemeral, boolean allowGlobs, boolean allowAutoVerify, boolean allowImplicitEphemeralVisibility, boolean failOnNoActions, ParseInput input) throws IOException, XmlPullParserException {
        int intentVisibility;
        ParseResult<ParsedIntentInfoImpl> intentResult = ParsedIntentInfoUtils.parseIntentInfo(mainComponent.getName(), pkg, resources, parser, allowGlobs, allowAutoVerify, input);
        if (intentResult.isError()) {
            return input.error(intentResult);
        }
        ParsedIntentInfo intent = intentResult.getResult();
        IntentFilter intentFilter = intent.getIntentFilter();
        int actionCount = intentFilter.countActions();
        if (actionCount == 0 && failOnNoActions) {
            Slog.w("PackageParsing", "No actions in " + parser.getName() + " at " + pkg.getBaseApkPath() + " " + parser.getPositionDescription());
            return input.success(null);
        }
        if (visibleToEphemeral) {
            intentVisibility = 1;
        } else if (allowImplicitEphemeralVisibility && ComponentParseUtils.isImplicitlyExposedIntent(intent)) {
            intentVisibility = 2;
        } else {
            intentVisibility = 0;
        }
        intentFilter.setVisibilityToInstantApp(intentVisibility);
        return input.success(intentResult.getResult());
    }
}
