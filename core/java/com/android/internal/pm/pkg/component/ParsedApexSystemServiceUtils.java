package com.android.internal.pm.pkg.component;

import android.R;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ParsedApexSystemServiceUtils {
    public static ParseResult<ParsedApexSystemService> parseApexSystemService(
            Resources res, XmlResourceParser parser, ParseInput input)
            throws XmlPullParserException, IOException {
        ParsedApexSystemServiceImpl systemService = new ParsedApexSystemServiceImpl();
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestApexSystemService);
        try {
            String className = sa.getString(0);
            if (TextUtils.isEmpty(className)) {
                return input.error("<apex-system-service> does not have name attribute");
            }
            String jarPath = sa.getString(2);
            String minSdkVersion = sa.getString(3);
            String maxSdkVersion = sa.getString(4);
            int initOrder = sa.getInt(1, 0);
            systemService
                    .setName(className)
                    .setMinSdkVersion(minSdkVersion)
                    .setMaxSdkVersion(maxSdkVersion)
                    .setInitOrder(initOrder);
            if (!TextUtils.isEmpty(jarPath)) {
                systemService.setJarPath(jarPath);
            }
            return input.success(systemService);
        } finally {
            sa.recycle();
        }
    }
}
