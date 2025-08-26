package android.content.pm.parsing;

import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.internal.modules.utils.build.UnboundedSdkLevel;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Slog;
import android.util.apk.ApkSignatureVerifier;
import com.android.internal.util.ArrayUtils;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/* loaded from: classes.dex */
public class FrameworkParsingPackageUtils {
    private static final int MAX_FILE_NAME_SIZE = 223;
    public static final int PARSE_APK_IN_APEX = 512;
    public static final int PARSE_IGNORE_OVERLAY_REQUIRED_SYSTEM_PROPERTY = 128;
    private static final String TAG = "FrameworkParsingPackageUtils";

    public static String validateName(String str, boolean z, boolean z2) {
        int length = str.length();
        boolean z3 = false;
        boolean z4 = true;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                z4 = false;
            } else if (z4 || ((cCharAt < '0' || cCharAt > '9') && cCharAt != '_')) {
                if (cCharAt != '.') {
                    return "bad character '" + cCharAt + "'";
                }
                z3 = true;
                z4 = true;
            }
        }
        if (z2) {
            if (!FileUtils.isValidExtFilename(str)) {
                return "Invalid filename";
            }
            if (length > 223) {
                return "the length of the name is greater than 223";
            }
        }
        if (z3 || !z) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    public static ParseResult validateName(ParseInput parseInput, String str, boolean z, boolean z2) {
        String strValidateName = validateName(str, z, z2);
        if (strValidateName != null) {
            return parseInput.error(strValidateName);
        }
        return parseInput.success(null);
    }

    public static PublicKey parsePublicKey(String str) {
        if (str == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            return parsePublicKey(Base64.decode(str, 0));
        } catch (IllegalArgumentException unused) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static PublicKey parsePublicKey(byte[] bArr) {
        if (bArr == null) {
            Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bArr);
            try {
                return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
            } catch (NoSuchAlgorithmException unused) {
                Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
                } catch (NoSuchAlgorithmException unused2) {
                    Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                    } catch (NoSuchAlgorithmException unused3) {
                        Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (InvalidKeySpecException unused4) {
                        return null;
                    }
                } catch (InvalidKeySpecException unused5) {
                    return KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                }
            } catch (InvalidKeySpecException unused6) {
                return KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
            }
        } catch (IllegalArgumentException unused7) {
            Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static boolean checkRequiredSystemProperties(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                return true;
            }
            Slog.w(TAG, "Disabling overlay - incomplete property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue to be specified.");
            return false;
        }
        String[] strArrSplit = str.split(",");
        String[] strArrSplit2 = str2.split(",");
        if (strArrSplit.length != strArrSplit2.length) {
            Slog.w(TAG, "Disabling overlay - property :'" + str + "=" + str2 + "' - require both requiredSystemPropertyName AND requiredSystemPropertyValue lists to have the same size.");
            return false;
        }
        for (int i = 0; i < strArrSplit.length; i++) {
            if (!TextUtils.equals(SystemProperties.get(strArrSplit[i]), strArrSplit2[i])) {
                return false;
            }
        }
        return true;
    }

    public static ParseResult<SigningDetails> getSigningDetails(ParseInput parseInput, String str, boolean z, boolean z2, SigningDetails signingDetails, int i) {
        ParseResult<SigningDetails> parseResultVerify;
        int minimumSignatureSchemeVersionForTargetSdk = ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
        if (z2) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        if (z) {
            parseResultVerify = ApkSignatureVerifier.unsafeGetCertsWithoutVerification(parseInput, str, 1);
        } else {
            parseResultVerify = ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (parseResultVerify.isError()) {
            return parseInput.error(parseResultVerify);
        }
        if (signingDetails == SigningDetails.UNKNOWN) {
            return parseResultVerify;
        }
        if (!Signature.areExactMatch(signingDetails, parseResultVerify.getResult())) {
            return parseInput.error(-104, str + " has mismatched certificates");
        }
        return parseInput.success(signingDetails);
    }

    public static ParseResult<Integer> computeMinSdkVersion(int i, String str, int i2, String[] strArr, ParseInput parseInput) {
        if (str == null) {
            if (i <= i2) {
                return parseInput.success(Integer.valueOf(i));
            }
            return parseInput.error(-12, "Requires newer sdk version #" + i + " (current version is #" + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (matchTargetCode(strArr, str)) {
            return parseInput.success(10000);
        }
        if (strArr.length > 0) {
            return parseInput.error(-12, "Requires development platform " + str + " (current platform is any of " + Arrays.toString(strArr) + NavigationBarInflaterView.KEY_CODE_END);
        }
        return parseInput.error(-12, "Requires development platform " + str + " but this is a release platform.");
    }

    public static ParseResult<Integer> computeTargetSdkVersion(int i, String str, String[] strArr, ParseInput parseInput, boolean z) {
        if (str == null) {
            return parseInput.success(Integer.valueOf(i));
        }
        if (z) {
            try {
                if (UnboundedSdkLevel.isAtMost(str)) {
                    return parseInput.success(10000);
                }
            } catch (IllegalArgumentException e) {
                return parseInput.error(-12, e.getMessage());
            }
        }
        if (matchTargetCode(strArr, str)) {
            return parseInput.success(10000);
        }
        if (strArr.length > 0) {
            return parseInput.error(-12, "Requires development platform " + str + " (current platform is any of " + Arrays.toString(strArr) + NavigationBarInflaterView.KEY_CODE_END);
        }
        return parseInput.error(-12, "Requires development platform " + str + " but this is a release platform.");
    }

    public static ParseResult<Integer> computeMaxSdkVersion(int i, int i2, ParseInput parseInput) {
        if (i2 > i) {
            return parseInput.error(-14, "Requires max SDK version " + i + " but is " + i2);
        }
        return parseInput.success(Integer.valueOf(i));
    }

    private static boolean matchTargetCode(String[] strArr, String str) {
        int iIndexOf = str.indexOf(46);
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
        }
        return ArrayUtils.contains(strArr, str);
    }
}
