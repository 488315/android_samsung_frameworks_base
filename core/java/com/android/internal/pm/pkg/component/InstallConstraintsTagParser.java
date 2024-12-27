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
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class InstallConstraintsTagParser {
    private static final String TAG_FINGERPRINT_PREFIX = "fingerprint-prefix";

    public static ParseResult<ParsingPackage> parseInstallConstraints(ParseInput input, ParsingPackage pkg, Resources res, XmlResourceParser parser, Set<String> allowlist) throws XmlPullParserException, IOException {
        if (!allowlist.contains(pkg.getPackageName())) {
            return input.skip("install-constraints cannot be used by this package");
        }
        ParseResult<Set<String>> prefixes = parseFingerprintPrefixes(input, res, parser);
        if (prefixes.isSuccess()) {
            if (validateFingerprintPrefixes(prefixes.getResult())) {
                return input.success(pkg);
            }
            return input.skip("Install of this package is restricted on this device; device fingerprint does not start with one of the allowed prefixes");
        }
        return input.skip(prefixes.getErrorMessage());
    }

    private static ParseResult<Set<String>> parseFingerprintPrefixes(ParseInput input, Resources res, XmlResourceParser parser) throws XmlPullParserException, IOException {
        ArraySet arraySet = new ArraySet();
        while (true) {
            int type = parser.next();
            if (type == 3) {
                if (arraySet.size() == 0) {
                    return input.error("install-constraints must contain at least one constraint");
                }
                return input.success(arraySet);
            }
            if (type == 2 && !ParsingPackageUtils.getAconfigFlags().skipCurrentElement(parser)) {
                if (parser.getName().equals(TAG_FINGERPRINT_PREFIX)) {
                    ParseResult<String> parsedPrefix = readFingerprintPrefixValue(input, res, parser);
                    if (parsedPrefix.isSuccess()) {
                        arraySet.add(parsedPrefix.getResult());
                        int type2 = parser.next();
                        if (type2 != 3) {
                            return input.error("Expected end tag; instead got " + type2);
                        }
                    } else {
                        return input.error(parsedPrefix.getErrorMessage());
                    }
                } else {
                    return input.error("Unexpected tag: " + parser.getName());
                }
            }
        }
    }

    private static ParseResult<String> readFingerprintPrefixValue(ParseInput input, Resources res, XmlResourceParser parser) {
        TypedArray sa = res.obtainAttributes(parser, R.styleable.AndroidManifestInstallConstraintsFingerprintPrefix);
        try {
            String value = sa.getString(0);
            if (value == null) {
                return input.error("Failed to specify prefix value");
            }
            return input.success(value);
        } finally {
            sa.recycle();
        }
    }

    private static boolean validateFingerprintPrefixes(Set<String> prefixes) {
        String fingerprint = Build.FINGERPRINT;
        for (String prefix : prefixes) {
            if (fingerprint.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
