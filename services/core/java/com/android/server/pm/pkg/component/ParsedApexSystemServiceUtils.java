package com.android.server.pm.pkg.component;

import android.R;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public abstract class ParsedApexSystemServiceUtils {
    public static ParseResult parseApexSystemService(Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) {
        ParsedApexSystemServiceImpl parsedApexSystemServiceImpl = new ParsedApexSystemServiceImpl();
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApexSystemService);
        try {
            String string = obtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                return parseInput.error("<apex-system-service> does not have name attribute");
            }
            String string2 = obtainAttributes.getString(2);
            String string3 = obtainAttributes.getString(3);
            String string4 = obtainAttributes.getString(4);
            parsedApexSystemServiceImpl.setName(string).setMinSdkVersion(string3).setMaxSdkVersion(string4).setInitOrder(obtainAttributes.getInt(1, 0));
            if (!TextUtils.isEmpty(string2)) {
                parsedApexSystemServiceImpl.setJarPath(string2);
            }
            return parseInput.success(parsedApexSystemServiceImpl);
        } finally {
            obtainAttributes.recycle();
        }
    }
}
