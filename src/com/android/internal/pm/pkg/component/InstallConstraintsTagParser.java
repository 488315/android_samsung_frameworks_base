package com.android.internal.pm.pkg.component;

import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.ArraySet;
import com.android.internal.R;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class InstallConstraintsTagParser {
    private static final String TAG_FINGERPRINT_PREFIX = "fingerprint-prefix";

    public static ParseResult<ParsingPackage> parseInstallConstraints(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser, Set<String> set) throws XmlPullParserException, IOException {
        if (!set.contains(parsingPackage.getPackageName())) {
            return parseInput.skip("install-constraints cannot be used by this package");
        }
        ParseResult<Set<String>> parseFingerprintPrefixes = parseFingerprintPrefixes(parseInput, parsingPackage, resources, xmlResourceParser);
        if (parseFingerprintPrefixes.isSuccess()) {
            if (validateFingerprintPrefixes(parseFingerprintPrefixes.getResult())) {
                return parseInput.success(parsingPackage);
            }
            return parseInput.skip("Install of this package is restricted on this device; device fingerprint does not start with one of the allowed prefixes");
        }
        return parseInput.skip(parseFingerprintPrefixes.getErrorMessage());
    }

    private static ParseResult<Set<String>> parseFingerprintPrefixes(ParseInput parseInput, ParsingPackage parsingPackage, Resources resources, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        ArraySet arraySet = new ArraySet();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 3) {
                if (arraySet.size() == 0) {
                    return parseInput.error("install-constraints must contain at least one constraint");
                }
                return parseInput.success(arraySet);
            }
            if (next == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parsingPackage, xmlResourceParser)) {
                if (xmlResourceParser.getName().equals(TAG_FINGERPRINT_PREFIX)) {
                    ParseResult<String> readFingerprintPrefixValue = readFingerprintPrefixValue(parseInput, resources, xmlResourceParser);
                    if (readFingerprintPrefixValue.isSuccess()) {
                        arraySet.add(readFingerprintPrefixValue.getResult());
                        int next2 = xmlResourceParser.next();
                        if (next2 != 3) {
                            return parseInput.error("Expected end tag; instead got " + next2);
                        }
                    } else {
                        return parseInput.error(readFingerprintPrefixValue.getErrorMessage());
                    }
                } else {
                    return parseInput.error("Unexpected tag: " + xmlResourceParser.getName());
                }
            }
        }
    }

    private static ParseResult<String> readFingerprintPrefixValue(ParseInput parseInput, Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestInstallConstraintsFingerprintPrefix);
        try {
            String string = obtainAttributes.getString(0);
            if (string == null) {
                return parseInput.error("Failed to specify prefix value");
            }
            return parseInput.success(string);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static boolean validateFingerprintPrefixes(Set<String> set) {
        String str = Build.FINGERPRINT;
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }
}
