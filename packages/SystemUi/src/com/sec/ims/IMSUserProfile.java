package com.sec.ims;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import java.io.Serializable;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class IMSUserProfile implements Parcelable, Serializable, Cloneable {
    private static final int DEFAULT_PORT = 5060;
    private static final String TCP = "TCP";
    private static final String UDP = "UDP";
    private static final long serialVersionUID = 1;
    public int available;
    private String mAuthUserName;
    private boolean mAutoRegistration;
    private transient int mCallingUid;
    public transient Bundle mCapabilities;
    private String mDomain;
    private String mPassword;
    private int mPort;
    private String mProfileName;
    private String mProtocol;
    private String mProxyAddress;
    private boolean mSendKeepAlive;
    private static final String LOG_TAG = "IMSUserProfile";
    public static final Parcelable.Creator<IMSUserProfile> CREATOR = new Parcelable.Creator<IMSUserProfile>() { // from class: com.sec.ims.IMSUserProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSUserProfile createFromParcel(Parcel parcel) {
            return new IMSUserProfile(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSUserProfile[] newArray(int i) {
            return new IMSUserProfile[i];
        }
    };

    public /* synthetic */ IMSUserProfile(int i) {
        this();
    }

    private Object readResolve() {
        if (this.mPort == 0) {
            this.mPort = DEFAULT_PORT;
        }
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAuthUserName() {
        return this.mAuthUserName;
    }

    public boolean getAutoRegistration() {
        return this.mAutoRegistration;
    }

    public int getAvailability() {
        return this.available;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public Bundle getCapabilities() {
        return this.mCapabilities;
    }

    public String getDisplayName() {
        return this.mProfileName;
    }

    public String getMDN() {
        return this.mProfileName;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public String getProtocol() {
        return this.mProtocol;
    }

    public String getProxyAddress() {
        return this.mProxyAddress;
    }

    public boolean getSendKeepAlive() {
        return this.mSendKeepAlive;
    }

    public String getSipDomain() {
        return this.mDomain;
    }

    public String getUriString() {
        return "sip:" + getUserName() + "@" + this.mDomain;
    }

    public String getUserName() {
        return this.mProfileName;
    }

    public void setCallingUid(int i) {
        this.mCallingUid = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mProxyAddress);
        parcel.writeString(this.mPassword);
        parcel.writeString(this.mDomain);
        parcel.writeString(this.mProtocol);
        parcel.writeString(this.mProfileName);
        parcel.writeInt(this.mSendKeepAlive ? 1 : 0);
        parcel.writeInt(this.mAutoRegistration ? 1 : 0);
        parcel.writeInt(this.mCallingUid);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mAuthUserName);
        parcel.writeBundle(this.mCapabilities);
    }

    public /* synthetic */ IMSUserProfile(Parcel parcel, int i) {
        this(parcel);
    }

    public IMSUserProfile(IMSUserProfile iMSUserProfile) {
        this.mProtocol = UDP;
        this.mPort = DEFAULT_PORT;
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
        this.mProxyAddress = iMSUserProfile.getProxyAddress();
        this.mPassword = iMSUserProfile.getPassword();
        this.mDomain = iMSUserProfile.getSipDomain();
        this.mProtocol = iMSUserProfile.getProtocol();
        this.mProfileName = iMSUserProfile.getProfileName();
        this.mAuthUserName = iMSUserProfile.getAuthUserName();
        this.mPort = iMSUserProfile.getPort();
        this.mSendKeepAlive = iMSUserProfile.getSendKeepAlive();
        this.mAutoRegistration = iMSUserProfile.getAutoRegistration();
        this.mCallingUid = iMSUserProfile.getCallingUid();
        this.mCapabilities = iMSUserProfile.getCapabilities();
        this.available = iMSUserProfile.getAvailability();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        private String mDisplayName;
        private IMSUserProfile mProfile;
        private String mProxyAddress;

        public Builder(IMSUserProfile iMSUserProfile) {
            this.mProfile = new IMSUserProfile(0);
            iMSUserProfile.getClass();
            try {
                this.mProfile = (IMSUserProfile) iMSUserProfile.clone();
                this.mDisplayName = iMSUserProfile.getDisplayName();
                this.mProxyAddress = iMSUserProfile.getProxyAddress();
                this.mProfile.mPort = iMSUserProfile.getPort();
                Log.d(IMSUserProfile.LOG_TAG, "DisplayName:" + this.mDisplayName.hashCode() + " ProxyAddress:" + this.mProxyAddress.hashCode());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("should not occur", e);
            }
        }

        private String fix(String str) {
            return str.trim().toLowerCase(Locale.US).startsWith("sip:") ? str : "sip:".concat(str);
        }

        public IMSUserProfile build() {
            return this.mProfile;
        }

        public Builder setAuthUserName(String str) {
            this.mProfile.mAuthUserName = str;
            return this;
        }

        public Builder setAutoRegistration(boolean z) {
            this.mProfile.mAutoRegistration = z;
            return this;
        }

        public Builder setDisplayName(String str) {
            this.mDisplayName = str;
            return this;
        }

        public Builder setOutboundProxy(String str) {
            this.mProxyAddress = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.mProfile.mPassword = str;
            return this;
        }

        public Builder setPort(int i) {
            if (i > 65535 || i < 1000) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("incorrect port arugment: ", i));
            }
            this.mProfile.mPort = i;
            return this;
        }

        public Builder setProfileName(String str) {
            this.mProfile.mProfileName = str;
            return this;
        }

        public Builder setProtocol(String str) {
            if (str == null) {
                throw new NullPointerException("protocol cannot be null");
            }
            String upperCase = str.toUpperCase(Locale.US);
            if (!upperCase.equals(IMSUserProfile.UDP) && !upperCase.equals(IMSUserProfile.TCP)) {
                throw new IllegalArgumentException("unsupported protocol: ".concat(upperCase));
            }
            this.mProfile.mProtocol = upperCase;
            return this;
        }

        public Builder setSendKeepAlive(boolean z) {
            this.mProfile.mSendKeepAlive = z;
            return this;
        }

        public Builder(String str) {
            IMSUserProfile iMSUserProfile = new IMSUserProfile(0);
            this.mProfile = iMSUserProfile;
            if (str != null) {
                iMSUserProfile.mProfileName = str;
                return;
            }
            throw new NullPointerException("uriString cannot be null");
        }

        public Builder(String str, String str2) {
            IMSUserProfile iMSUserProfile = new IMSUserProfile(0);
            this.mProfile = iMSUserProfile;
            if (str != null && str2 != null) {
                iMSUserProfile.mDomain = str2;
                this.mProfile.mProfileName = str;
                return;
            }
            throw new NullPointerException("username and serverDomain cannot be null");
        }
    }

    private IMSUserProfile() {
        this.mProtocol = UDP;
        this.mPort = DEFAULT_PORT;
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
    }

    private IMSUserProfile(Parcel parcel) {
        this.mProtocol = UDP;
        this.mPort = DEFAULT_PORT;
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
        this.mProxyAddress = parcel.readString();
        this.mPassword = parcel.readString();
        this.mDomain = parcel.readString();
        this.mProtocol = parcel.readString();
        this.mProfileName = parcel.readString();
        this.mSendKeepAlive = parcel.readInt() != 0;
        this.mAutoRegistration = parcel.readInt() != 0;
        this.mCallingUid = parcel.readInt();
        this.mPort = parcel.readInt();
        this.mAuthUserName = parcel.readString();
        this.mCapabilities = parcel.readBundle();
    }
}
