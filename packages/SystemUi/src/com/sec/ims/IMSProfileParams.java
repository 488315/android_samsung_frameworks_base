package com.sec.ims;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class IMSProfileParams implements Parcelable, Cloneable {
    public static final Parcelable.Creator<IMSProfileParams> CREATOR = new Parcelable.Creator<IMSProfileParams>() { // from class: com.sec.ims.IMSProfileParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSProfileParams createFromParcel(Parcel parcel) {
            return new IMSProfileParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSProfileParams[] newArray(int i) {
            return new IMSProfileParams[i];
        }
    };
    private String mAuthUserName;
    private boolean mAutoRegistration;
    private int mAvailable;
    private transient int mCallingUid;
    private transient Bundle mCapabilities;
    private String mDomain;
    private String mPassword;
    private int mPort;
    private String mProfileName;
    private String mProtocol;
    private String mProxyAddress;
    private boolean mSendKeepAlive;
    private String mUriString;

    public IMSProfileParams(IMSUserProfile iMSUserProfile) {
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
        this.mAvailable = iMSUserProfile.getAvailability();
        this.mUriString = iMSUserProfile.getUriString();
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
        return this.mAvailable;
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
        return this.mUriString;
    }

    public String getUserName() {
        return this.mProfileName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mProxyAddress);
        parcel.writeString(this.mPassword);
        parcel.writeString(this.mDomain);
        parcel.writeString(this.mProtocol);
        parcel.writeString(this.mProfileName);
        parcel.writeString(this.mAuthUserName);
        parcel.writeInt(this.mPort);
        parcel.writeInt(this.mSendKeepAlive ? 1 : 0);
        parcel.writeInt(this.mAutoRegistration ? 1 : 0);
        parcel.writeInt(this.mAvailable);
        parcel.writeString(this.mUriString);
        parcel.writeBundle(this.mCapabilities);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IMSProfileParams m2824clone() {
        return (IMSProfileParams) super.clone();
    }

    public IMSProfileParams(Parcel parcel) {
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
        this.mProxyAddress = parcel.readString();
        this.mPassword = parcel.readString();
        this.mDomain = parcel.readString();
        this.mProtocol = parcel.readString();
        this.mProfileName = parcel.readString();
        this.mAuthUserName = parcel.readString();
        this.mPort = parcel.readInt();
        this.mSendKeepAlive = parcel.readInt() == 1;
        this.mAutoRegistration = parcel.readInt() == 1;
        this.mAvailable = parcel.readInt();
        this.mUriString = parcel.readString();
        this.mCapabilities = parcel.readBundle();
    }
}
