package com.samsung.android.ims.util;

import android.telecom.PhoneAccount;
import android.webkit.WebView;
import gov.nist.javax.sip.address.GenericURI;

/* loaded from: classes5.dex */
public class SemTelUri extends GenericURI {
    private static final String LOG_TAG = "TelUri";
    private boolean mIsInternational = false;
    private String mNumber;
    private String mPhoneContext;

    public static SemTelUri parseUri(String uri) {
        if (uri == null || !uri.startsWith(WebView.SCHEME_TEL)) {
            return null;
        }
        String uri2 = uri.replaceAll("\\s+", "");
        int c = uri2.indexOf(59);
        if (c < 0) {
            return new SemTelUri(uri2.substring(4), null);
        }
        String phoneContext = null;
        int p = uri2.indexOf("phone-context");
        if (p > 0) {
            phoneContext = uri2.substring(p + 14);
        }
        return new SemTelUri(uri2.substring(4, c), phoneContext);
    }

    public SemTelUri(String number, String context) {
        setPhoneNumber(number);
        this.mPhoneContext = context;
    }

    public void setPhoneNumber(String number) {
        if (number.charAt(0) == '+') {
            this.mIsInternational = true;
            number = number.substring(1);
        }
        this.mNumber = number;
    }

    public String getPhoneNumber() {
        return (this.mIsInternational ? "+" : "") + this.mNumber;
    }

    public String getScheme() {
        return PhoneAccount.SCHEME_TEL;
    }

    public String toString() {
        return WebView.SCHEME_TEL + (this.mIsInternational ? "+" : "") + this.mNumber + (this.mPhoneContext != null ? ";phone-context=" + this.mPhoneContext : "");
    }
}
