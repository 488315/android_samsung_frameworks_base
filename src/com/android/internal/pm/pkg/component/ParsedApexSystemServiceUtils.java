package com.android.internal.pm.pkg.component;

import android.R;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ParsedApexSystemServiceUtils {
    public static ParseResult<ParsedApexSystemService> parseApexSystemService(Resources resources, XmlResourceParser xmlResourceParser, ParseInput parseInput) throws XmlPullParserException, IOException {
        ParsedApexSystemServiceImpl parsedApexSystemServiceImpl = new ParsedApexSystemServiceImpl();
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestApexSystemService);
        try {
            String string = typedArrayObtainAttributes.getString(0);
            if (TextUtils.isEmpty(string)) {
                return parseInput.error("<apex-system-service> does not have name attribute");
            }
            String string2 = typedArrayObtainAttributes.getString(2);
            String string3 = typedArrayObtainAttributes.getString(3);
            String string4 = typedArrayObtainAttributes.getString(4);
            parsedApexSystemServiceImpl.setName(string).setMinSdkVersion(string3).setMaxSdkVersion(string4).setInitOrder(typedArrayObtainAttributes.getInt(1, 0));
            if (!TextUtils.isEmpty(string2)) {
                parsedApexSystemServiceImpl.setJarPath(string2);
            }
            return parseInput.success(parsedApexSystemServiceImpl);
        } finally {
            typedArrayObtainAttributes.recycle();
        }
    }
}
