package com.android.internal.pm.pkg.component;

import android.content.pm.PackageManager;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.pm.pkg.parsing.ParsingUtils;

/* loaded from: classes5.dex */
class ParsedComponentUtils {
    ParsedComponentUtils() {
    }

    static <Component extends ParsedComponentImpl> ParseResult<Component> parseComponent(Component component, String str, ParsingPackage parsingPackage, TypedArray typedArray, boolean z, ParseInput parseInput, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        String nonConfigurationString = typedArray.getNonConfigurationString(i6, 0);
        if (TextUtils.isEmpty(nonConfigurationString)) {
            return parseInput.error(str + " does not specify android:name");
        }
        String packageName = parsingPackage.getPackageName();
        String buildClassName = ParsingUtils.buildClassName(packageName, nonConfigurationString);
        if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
            return parseInput.error(str + " invalid android:name");
        }
        component.setName(buildClassName).setPackageName(packageName);
        int resourceId = z ? typedArray.getResourceId(i7, 0) : 0;
        if (resourceId != 0) {
            component.setIcon(resourceId).setNonLocalizedLabel(null);
        } else {
            int resourceId2 = typedArray.getResourceId(i3, 0);
            if (resourceId2 != 0) {
                component.setIcon(resourceId2);
                component.setNonLocalizedLabel(null);
            }
        }
        int resourceId3 = typedArray.getResourceId(i5, 0);
        if (resourceId3 != 0) {
            component.setLogo(resourceId3);
        }
        int resourceId4 = typedArray.getResourceId(i, 0);
        if (resourceId4 != 0) {
            component.setBanner(resourceId4);
        }
        if (i2 != -1) {
            component.setDescriptionRes(typedArray.getResourceId(i2, 0));
        }
        TypedValue peekValue = typedArray.peekValue(i4);
        if (peekValue != null) {
            component.setLabelRes(peekValue.resourceId);
            if (peekValue.resourceId == 0) {
                component.setNonLocalizedLabel(peekValue.coerceToString());
            }
        }
        return parseInput.success(component);
    }

    static ParseResult<Bundle> addMetaData(ParsedComponentImpl parsedComponentImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        ParseResult<PackageManager.Property> parseMetaData = ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<meta-data>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        PackageManager.Property result = parseMetaData.getResult();
        if (result != null) {
            parsedComponentImpl.setMetaData(result.toBundle(parsedComponentImpl.getMetaData()));
        }
        return parseInput.success(parsedComponentImpl.getMetaData());
    }

    static ParseResult<PackageManager.Property> addProperty(ParsedComponentImpl parsedComponentImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        ParseResult<PackageManager.Property> parseMetaData = ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<property>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        PackageManager.Property result = parseMetaData.getResult();
        if (result != null) {
            parsedComponentImpl.addProperty(result);
        }
        return parseInput.success(result);
    }
}
