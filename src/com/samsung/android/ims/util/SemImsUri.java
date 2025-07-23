package com.samsung.android.ims.util;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccount;
import android.util.Log;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.parser.URLParser;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemImsUri implements Parcelable {
    private static final String LOG_TAG = "SemImsUri";
    private String mMsisdn;
    private String mScheme;
    private SipUri mSipUri;
    private SemTelUri mTelUri;
    private String mUriToString;
    private UriType mUriType;
    private String mUrn;
    private String mUser;
    private static final boolean DBG = "eng".equals(Build.TYPE);
    private static final Pattern PATTERN_WHITE_SPACES = Pattern.compile("\\s+");
    public static final Parcelable.Creator<SemImsUri> CREATOR = new Parcelable.Creator<SemImsUri>() { // from class: com.samsung.android.ims.util.SemImsUri.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsUri createFromParcel(Parcel parcel) {
            return new SemImsUri(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsUri[] newArray(int i) {
            return new SemImsUri[i];
        }
    };

    public enum UriType {
        TEL_URI,
        SIP_URI,
        URN
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static SemImsUri parse(String str) {
        if (str == null) {
            return null;
        }
        String replaceAll = PATTERN_WHITE_SPACES.matcher(str).replaceAll("");
        int indexOf = replaceAll.indexOf(58);
        if (indexOf < 0) {
            StringBuilder sb = new StringBuilder("parse: illegal Uri - ");
            if (!DBG) {
                replaceAll = "xxxxx";
            }
            sb.append(replaceAll);
            Log.e(LOG_TAG, sb.toString());
            return null;
        }
        String substring = replaceAll.substring(0, indexOf);
        try {
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("parse: failured. uri=");
            if (!DBG) {
                replaceAll = "xxxxx";
            }
            sb2.append(replaceAll);
            sb2.append(" e=");
            sb2.append(e);
            Log.e(LOG_TAG, sb2.toString());
            e.printStackTrace();
        }
        if (!"sip".equalsIgnoreCase(substring) && !"sips".equalsIgnoreCase(substring)) {
            if (PhoneAccount.SCHEME_TEL.equalsIgnoreCase(substring)) {
                return new SemImsUri(SemTelUri.parseUri(replaceAll));
            }
            if ("urn".equalsIgnoreCase(substring)) {
                return new SemImsUri(replaceAll);
            }
            return null;
        }
        return new SemImsUri(new URLParser(replaceAll).sipURL(true));
    }

    public SemImsUri() {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        this.mUser = null;
        this.mMsisdn = null;
        this.mUriType = UriType.SIP_URI;
        this.mScheme = null;
        this.mUriToString = null;
    }

    public SemImsUri(String str) {
        this.mSipUri = null;
        this.mTelUri = null;
        this.mUrn = str;
        this.mUser = null;
        this.mUriType = UriType.URN;
        this.mScheme = null;
        this.mMsisdn = "";
        this.mUriToString = null;
    }

    private SemImsUri(SipUri sipUri) {
        this.mUrn = null;
        this.mTelUri = null;
        this.mSipUri = sipUri;
        if (sipUri != null) {
            this.mUser = sipUri.getUser();
            this.mScheme = this.mSipUri.getScheme();
            String str = this.mUser;
            if (str == null) {
                this.mMsisdn = "";
            } else {
                int indexOf = str.indexOf(59);
                if (indexOf > 0) {
                    this.mMsisdn = this.mUser.substring(0, indexOf);
                } else {
                    this.mMsisdn = this.mUser;
                }
            }
        } else {
            this.mUser = null;
            this.mScheme = null;
            this.mMsisdn = null;
        }
        this.mUriType = UriType.SIP_URI;
        this.mUriToString = null;
    }

    private SemImsUri(SemTelUri semTelUri) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = semTelUri;
        if (semTelUri != null) {
            this.mScheme = semTelUri.getScheme();
            this.mMsisdn = this.mTelUri.getPhoneNumber();
        } else {
            this.mScheme = null;
            this.mMsisdn = null;
        }
        this.mUser = null;
        this.mUriType = UriType.TEL_URI;
        this.mUriToString = null;
    }

    public String getUser() {
        return this.mUser;
    }

    public void setUser(String str) {
        this.mUser = str;
    }

    public String getMsisdn() {
        return this.mMsisdn;
    }

    public void setMsisdn(String str) {
        this.mMsisdn = str;
    }

    public UriType getUriType() {
        return this.mUriType;
    }

    public void setUriType(String str) {
        try {
            this.mUriType = UriType.valueOf(str);
        } catch (IllegalArgumentException unused) {
            this.mUriType = UriType.SIP_URI;
        }
    }

    public String getScheme() {
        return this.mScheme;
    }

    public void setScheme(String str) {
        this.mScheme = str;
    }

    public void setString(String str) {
        this.mUriToString = str;
    }

    public String toString() {
        String str = this.mUriToString;
        if (str != null) {
            return str;
        }
        String str2 = this.mUrn;
        if (str2 != null) {
            return str2;
        }
        SemTelUri semTelUri = this.mTelUri;
        if (semTelUri != null) {
            return semTelUri.toString();
        }
        return this.mSipUri.toString();
    }

    private SemImsUri(Parcel parcel) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mUser = parcel.readString();
        this.mMsisdn = parcel.readString();
        this.mUriType = UriType.valueOf(parcel.readString());
        this.mScheme = parcel.readString();
        this.mUriToString = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUser);
        parcel.writeString(this.mMsisdn);
        parcel.writeString(this.mUriType.name());
        parcel.writeString(this.mScheme);
        parcel.writeString(this.mUriToString);
    }
}
