package android.view.textclassifier;

import android.util.NtpTrustedTime;

/* loaded from: classes4.dex */
public final class SelectionSessionLogger {
    private static final String CLASSIFIER_ID = "androidtc";

    static boolean isPlatformLocalTextClassifierSmartSelection(String str) {
        return "androidtc".equals(SignatureParser.getClassifierId(str));
    }

    public static final class SignatureParser {
        static String getClassifierId(String str) {
            int iIndexOf;
            if (str == null || (iIndexOf = str.indexOf(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)) < 0) {
                return "";
            }
            return str.substring(0, iIndexOf);
        }
    }
}
