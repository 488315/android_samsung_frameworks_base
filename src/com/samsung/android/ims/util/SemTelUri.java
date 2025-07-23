package com.samsung.android.ims.util;

import android.telecom.PhoneAccount;
import android.webkit.WebView;
import gov.nist.javax.sip.address.GenericURI;

/* loaded from: classes6.dex */
public class SemTelUri extends GenericURI {
    private static final String LOG_TAG = "TelUri";
    private boolean mIsInternational = false;
    private String mNumber;
    private String mPhoneContext;

    public static SemTelUri parseUri(String str) {
        if (str == null || !str.startsWith(WebView.SCHEME_TEL)) {
            return null;
        }
        String replaceAll = str.replaceAll("\\s+", "");
        int indexOf = replaceAll.indexOf(59);
        if (indexOf < 0) {
            return new SemTelUri(replaceAll.substring(4), null);
        }
        int indexOf2 = replaceAll.indexOf("phone-context");
        return new SemTelUri(replaceAll.substring(4, indexOf), indexOf2 > 0 ? replaceAll.substring(indexOf2 + 14) : null);
    }

    public SemTelUri(String str, String str2) {
        setPhoneNumber(str);
        this.mPhoneContext = str2;
    }

    public void setPhoneNumber(String str) {
        if (str.charAt(0) == '+') {
            this.mIsInternational = true;
            str = str.substring(1);
        }
        this.mNumber = str;
    }

    public String getPhoneNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mIsInternational ? "+" : "");
        sb.append(this.mNumber);
        return sb.toString();
    }

    public String getScheme() {
        return PhoneAccount.SCHEME_TEL;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(WebView.SCHEME_TEL);
        String str = "";
        sb.append(this.mIsInternational ? "+" : "");
        sb.append(this.mNumber);
        if (this.mPhoneContext != null) {
            str = ";phone-context=" + this.mPhoneContext;
        }
        sb.append(str);
        return sb.toString();
    }
}
