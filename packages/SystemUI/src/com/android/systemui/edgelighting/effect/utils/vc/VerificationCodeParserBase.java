package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Slog;
import androidx.core.util.PatternsCompat;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class VerificationCodeParserBase implements VerificationCodeParser {
    public static final String[] OTP_PATTERN = {"^([\\d]{4,10})$", "^(?=.*[\\d])(?=.*[a-zA-Z])([\\da-zA-Z]{6,10})$", "^([A-Z]{1,3}-[\\d]{4,6})$", "^([\\d]{3,5}-[\\d]{3,5})$"};
    public static final String PATTERN_EMAIL = PatternsCompat.EMAIL_ADDRESS.toString();

    public static int containKey(String str, String[] strArr) {
        for (String str2 : strArr) {
            Matcher matcher = Pattern.compile("(^|[^\\da-z]|[0-9]| )" + str2 + "([^\\da-z]|[0-9]| |$)").matcher(str);
            if (matcher.find()) {
                return matcher.start();
            }
        }
        return -1;
    }

    public static String getVerificationCode(Context context, String str, String[] strArr, String[] strArr2) throws Resources.NotFoundException {
        if (TextUtils.isEmpty(str)) {
            Slog.d("ORC/VerificationCodeParserBase", "getVerificationCode() - empty text");
            return null;
        }
        String[] stringArray = context.getResources().getStringArray(R.array.verification_code_strong_otp_default);
        String[] stringArray2 = context.getResources().getStringArray(R.array.verification_code_weak_otp_default);
        if (strArr != null && strArr.length != 0) {
            String[] strArr3 = (String[]) Arrays.copyOf(stringArray, stringArray.length + strArr.length);
            System.arraycopy(strArr, 0, strArr3, stringArray.length, strArr.length);
            stringArray = strArr3;
        }
        if (strArr2 != null && strArr2.length != 0) {
            String[] strArr4 = (String[]) Arrays.copyOf(stringArray2, stringArray2.length + strArr2.length);
            System.arraycopy(strArr2, 0, strArr4, stringArray2.length, strArr2.length);
            stringArray2 = strArr4;
        }
        return getVerificationCodeWithoutDefault(str, stringArray, stringArray2);
    }

    public static String getVerificationCodeWithoutDefault(String str, String[] strArr, String[] strArr2) {
        if (TextUtils.isEmpty(str)) {
            Slog.d("ORC/VerificationCodeParserBase", "getVerificationCode() - empty text");
            return null;
        }
        String lowerCase = str.toLowerCase();
        int iContainKey = containKey(lowerCase, strArr);
        if (iContainKey != -1) {
            Slog.d("ORC/VerificationCodeParserBase", "getKeyOTPPosition: contain strong key");
        } else {
            iContainKey = containKey(lowerCase, strArr2);
        }
        if (iContainKey == -1) {
            Slog.d("ORC/VerificationCodeParserBase", "getVerificationCode - not OTP message");
            return null;
        }
        String[] strArrSplit = str.split("[^\\da-zA-Z-]|" + PATTERN_EMAIL + "|(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])");
        int length = str.length();
        int length2 = 0;
        int i = -1;
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String[] strArr3 = OTP_PATTERN;
            for (int i3 = 0; i3 < 4; i3++) {
                if (strArrSplit[i2].matches(strArr3[i3])) {
                    int i4 = iContainKey - length2;
                    if (length >= Math.abs(i4)) {
                        length = Math.abs(i4);
                        i = i2;
                    }
                }
            }
            length2 += strArrSplit[i2].length() + 1;
        }
        if (i != -1) {
            Slog.d("ORC/VerificationCodeParserBase", "getOTPCode = true");
            return strArrSplit[i];
        }
        Slog.d("ORC/VerificationCodeParserBase", "getOTPCode - Don't have any OTP code");
        return null;
    }
}
