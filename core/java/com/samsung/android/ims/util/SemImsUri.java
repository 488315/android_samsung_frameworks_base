package com.samsung.android.ims.util;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccount;
import android.util.Log;

import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.parser.URLParser;

import java.util.regex.Pattern;

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
    public static final Parcelable.Creator<SemImsUri> CREATOR =
            new Parcelable.Creator<
                    SemImsUri>() { // from class: com.samsung.android.ims.util.SemImsUri.1
                @Override // android.os.Parcelable.Creator
                public SemImsUri createFromParcel(Parcel in) {
                    return new SemImsUri(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemImsUri[] newArray(int size) {
                    return new SemImsUri[size];
                }
            };

    public enum UriType {
        TEL_URI,
        SIP_URI,
        URN
    }

    public static SemImsUri parse(String str) {
        if (str == null) {
            return null;
        }
        String str2 = PATTERN_WHITE_SPACES.matcher(str).replaceAll("");
        int i = str2.indexOf(58);
        if (i < 0) {
            Log.e(LOG_TAG, "parse: illegal Uri - " + (DBG ? str2 : "xxxxx"));
            return null;
        }
        String scheme = str2.substring(0, i);
        try {
        } catch (Exception e) {
            Log.e(LOG_TAG, "parse: failured. uri=" + (DBG ? str2 : "xxxxx") + " e=" + e);
            e.printStackTrace();
        }
        if (!"sip".equalsIgnoreCase(scheme) && !"sips".equalsIgnoreCase(scheme)) {
            if (PhoneAccount.SCHEME_TEL.equalsIgnoreCase(scheme)) {
                return new SemImsUri(SemTelUri.parseUri(str2));
            }
            if (!"urn".equalsIgnoreCase(scheme)) {
                return null;
            }
            return new SemImsUri(str2);
        }
        URLParser parse = new URLParser(str2);
        return new SemImsUri(parse.sipURL(true));
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

    public SemImsUri(String urn) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        this.mUrn = urn;
        this.mUser = null;
        this.mUriType = UriType.URN;
        this.mScheme = null;
        this.mMsisdn = "";
        this.mUriToString = null;
    }

    private SemImsUri(SipUri uri) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        this.mSipUri = uri;
        if (this.mSipUri != null) {
            this.mUser = this.mSipUri.getUser();
            this.mScheme = this.mSipUri.getScheme();
            if (this.mUser == null) {
                this.mMsisdn = "";
            } else {
                int index = this.mUser.indexOf(59);
                if (index > 0) {
                    this.mMsisdn = this.mUser.substring(0, index);
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

    private SemImsUri(SemTelUri uri) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        this.mTelUri = uri;
        if (this.mTelUri != null) {
            this.mScheme = this.mTelUri.getScheme();
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

    public void setUser(String user) {
        this.mUser = user;
    }

    public String getMsisdn() {
        return this.mMsisdn;
    }

    public void setMsisdn(String msisdn) {
        this.mMsisdn = msisdn;
    }

    public UriType getUriType() {
        return this.mUriType;
    }

    public void setUriType(String uriType) {
        try {
            this.mUriType = UriType.valueOf(uriType);
        } catch (IllegalArgumentException e) {
            this.mUriType = UriType.SIP_URI;
        }
    }

    public String getScheme() {
        return this.mScheme;
    }

    public void setScheme(String scheme) {
        this.mScheme = scheme;
    }

    public void setString(String str) {
        this.mUriToString = str;
    }

    public String toString() {
        if (this.mUriToString != null) {
            return this.mUriToString;
        }
        if (this.mUrn != null) {
            return this.mUrn;
        }
        if (this.mTelUri != null) {
            return this.mTelUri.toString();
        }
        return this.mSipUri.toString();
    }

    private SemImsUri(Parcel in) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.mUser = in.readString();
        this.mMsisdn = in.readString();
        this.mUriType = UriType.valueOf(in.readString());
        this.mScheme = in.readString();
        this.mUriToString = in.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUser);
        dest.writeString(this.mMsisdn);
        dest.writeString(this.mUriType.name());
        dest.writeString(this.mScheme);
        dest.writeString(this.mUriToString);
    }
}
