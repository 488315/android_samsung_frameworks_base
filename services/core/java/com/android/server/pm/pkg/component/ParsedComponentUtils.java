package com.android.server.pm.pkg.component;

import android.content.pm.PackageManager;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.TypedValue;
import com.android.server.pm.pkg.parsing.ParsingPackage;
import com.android.server.pm.pkg.parsing.ParsingPackageUtils;
import com.android.server.pm.pkg.parsing.ParsingUtils;

/* loaded from: classes3.dex */
public abstract class ParsedComponentUtils {
    public static ParseResult parseComponent(ParsedComponentImpl parsedComponentImpl, String str, ParsingPackage parsingPackage, TypedArray typedArray, boolean z, ParseInput parseInput, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        String nonConfigurationString = typedArray.getNonConfigurationString(i6, 0);
        if (TextUtils.isEmpty(nonConfigurationString)) {
            return parseInput.error(str + " does not specify android:name");
        }
        String packageName = parsingPackage.getPackageName();
        String buildClassName = ParsingUtils.buildClassName(packageName, nonConfigurationString);
        if (PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
            return parseInput.error(str + " invalid android:name");
        }
        parsedComponentImpl.setName(buildClassName).setPackageName(packageName);
        int resourceId = z ? typedArray.getResourceId(i7, 0) : 0;
        if (resourceId != 0) {
            parsedComponentImpl.setIcon(resourceId).setNonLocalizedLabel(null);
        } else {
            int resourceId2 = typedArray.getResourceId(i3, 0);
            if (resourceId2 != 0) {
                parsedComponentImpl.setIcon(resourceId2);
                parsedComponentImpl.setNonLocalizedLabel(null);
            }
        }
        int resourceId3 = typedArray.getResourceId(i5, 0);
        if (resourceId3 != 0) {
            parsedComponentImpl.setLogo(resourceId3);
        }
        int resourceId4 = typedArray.getResourceId(i, 0);
        if (resourceId4 != 0) {
            parsedComponentImpl.setBanner(resourceId4);
        }
        if (i2 != -1) {
            parsedComponentImpl.setDescriptionRes(typedArray.getResourceId(i2, 0));
        }
        TypedValue peekValue = typedArray.peekValue(i4);
        if (peekValue != null) {
            parsedComponentImpl.setLabelRes(peekValue.resourceId);
            if (peekValue.resourceId == 0) {
                parsedComponentImpl.setNonLocalizedLabel(peekValue.coerceToString());
            }
        }
        return parseInput.success(parsedComponentImpl);
    }

    public static ParseResult addMetaData(ParsedComponentImpl parsedComponentImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        ParseResult parseMetaData = ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<meta-data>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        PackageManager.Property property = (PackageManager.Property) parseMetaData.getResult();
        if (property != null) {
            parsedComponentImpl.setMetaData(property.toBundle(parsedComponentImpl.getMetaData()));
        }
        return parseInput.success(parsedComponentImpl.getMetaData());
    }

    public static ParseResult addProperty(ParsedComponentImpl parsedComponentImpl, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        ParseResult parseMetaData = ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<property>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        PackageManager.Property property = (PackageManager.Property) parseMetaData.getResult();
        if (property != null) {
            parsedComponentImpl.addProperty(property);
        }
        return parseInput.success(property);
    }
}
