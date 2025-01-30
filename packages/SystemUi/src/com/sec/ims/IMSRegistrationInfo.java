package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class IMSRegistrationInfo implements Parcelable, Cloneable {
    public static final Parcelable.Creator<IMSRegistrationInfo> CREATOR = new Parcelable.Creator<IMSRegistrationInfo>() { // from class: com.sec.ims.IMSRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSRegistrationInfo createFromParcel(Parcel parcel) {
            return new IMSRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSRegistrationInfo[] newArray(int i) {
            return new IMSRegistrationInfo[i];
        }
    };
    private int mECMPMode;
    private int mEPDGStatus;
    private int mErrorCode;
    private String mErrorMessage;
    private int mExpiryTime;
    private int mFeatureMask;
    private String mFeatureTags;
    private String mIMSCkIk;
    private int mLimitedMode;
    private String mLocalProfileUri;
    private int mNetworkType;
    private int mRegisterRetryOver;
    private String mURIfromPAU;
    private String mURIfromPAU2nd;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ECMP_MODE {
        public static final int CS_PREFERRED = 0;
        public static final int NOT_SUPPORT = -1;
        public static final int PS_PREFERRED = 1;
    }

    public IMSRegistrationInfo() {
        this.mURIfromPAU = null;
        this.mURIfromPAU2nd = null;
        this.mFeatureTags = null;
        initialize();
    }

    private final void initialize() {
        this.mLocalProfileUri = null;
        this.mExpiryTime = 0;
        this.mFeatureMask = 0;
        this.mNetworkType = -1;
        this.mECMPMode = -1;
        this.mIMSCkIk = null;
        this.mLimitedMode = 0;
        this.mErrorCode = -1;
        this.mErrorMessage = null;
        this.mRegisterRetryOver = 0;
        this.mEPDGStatus = 0;
        this.mFeatureTags = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getECMPMode() {
        return this.mECMPMode;
    }

    public int getEPDGStatus() {
        return this.mEPDGStatus;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getExpiryTime() {
        return this.mExpiryTime;
    }

    public int getFeatureMask() {
        return this.mFeatureMask;
    }

    public String getFeatureTags() {
        return this.mFeatureTags;
    }

    public String getIMSCkIk() {
        return this.mIMSCkIk;
    }

    public int getLimitedMode() {
        return this.mLimitedMode;
    }

    public String getLocalProfileUri() {
        return this.mLocalProfileUri;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public String getPAssociatedUri() {
        return this.mURIfromPAU;
    }

    public String getPAssociatedUri2nd() {
        return this.mURIfromPAU2nd;
    }

    public int getRegisterRetryOver() {
        return this.mRegisterRetryOver;
    }

    public void setECMPMode(int i) {
        this.mECMPMode = i;
    }

    public void setEPDGStatus(int i) {
        this.mEPDGStatus = i;
    }

    public void setErrorCode(int i) {
        this.mErrorCode = i;
    }

    public void setErrorMessage(String str) {
        this.mErrorMessage = str;
    }

    public void setExpiryTime(int i) {
        this.mExpiryTime = i;
    }

    public void setFeatureMask(int i) {
        this.mFeatureMask = i;
    }

    public void setFeatureTags(String str) {
        this.mFeatureTags = str;
    }

    public void setIMSCkIk(String str) {
        this.mIMSCkIk = str;
    }

    public void setLimitedMode(int i) {
        this.mLimitedMode = i;
    }

    public void setLocalProfileUri(String str) {
        this.mLocalProfileUri = str;
    }

    public void setNetworkType(int i) {
        this.mNetworkType = i;
    }

    public void setPAssociatedUri(String str) {
        this.mURIfromPAU = str;
    }

    public void setPAssociatedUri2nd(String str) {
        this.mURIfromPAU2nd = str;
    }

    public void setRegisterRetryOver(int i) {
        this.mRegisterRetryOver = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mLocalProfileUri);
        parcel.writeInt(this.mExpiryTime);
        parcel.writeInt(this.mFeatureMask);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mECMPMode);
        parcel.writeString(this.mIMSCkIk);
        parcel.writeInt(this.mLimitedMode);
        parcel.writeInt(this.mErrorCode);
        parcel.writeString(this.mErrorMessage);
        parcel.writeInt(this.mRegisterRetryOver);
        parcel.writeInt(this.mEPDGStatus);
        parcel.writeString(this.mURIfromPAU);
        parcel.writeString(this.mURIfromPAU2nd);
        parcel.writeString(this.mFeatureTags);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IMSRegistrationInfo m2825clone() {
        IMSRegistrationInfo iMSRegistrationInfo = (IMSRegistrationInfo) super.clone();
        String str = this.mLocalProfileUri;
        if (str != null) {
            iMSRegistrationInfo.mLocalProfileUri = new String(str);
        }
        String str2 = this.mIMSCkIk;
        if (str2 != null) {
            iMSRegistrationInfo.mIMSCkIk = new String(str2);
        }
        String str3 = this.mErrorMessage;
        if (str3 != null) {
            iMSRegistrationInfo.mErrorMessage = new String(str3);
        }
        return iMSRegistrationInfo;
    }

    public IMSRegistrationInfo(String str, int i, int i2, int i3, int i4, String str2, int i5, int i6, String str3, int i7, int i8) {
        this.mURIfromPAU = null;
        this.mURIfromPAU2nd = null;
        this.mFeatureTags = null;
        this.mLocalProfileUri = str;
        this.mExpiryTime = i;
        this.mFeatureMask = i2;
        this.mNetworkType = i3;
        this.mECMPMode = i4;
        this.mIMSCkIk = str2;
        this.mLimitedMode = i5;
        this.mErrorCode = i6;
        this.mErrorMessage = str3;
        this.mRegisterRetryOver = i7;
        this.mEPDGStatus = i8;
    }

    public IMSRegistrationInfo(Parcel parcel) {
        this.mURIfromPAU = null;
        this.mURIfromPAU2nd = null;
        this.mFeatureTags = null;
        this.mLocalProfileUri = parcel.readString();
        this.mExpiryTime = parcel.readInt();
        this.mFeatureMask = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mECMPMode = parcel.readInt();
        this.mIMSCkIk = parcel.readString();
        this.mLimitedMode = parcel.readInt();
        this.mErrorCode = parcel.readInt();
        this.mErrorMessage = parcel.readString();
        this.mRegisterRetryOver = parcel.readInt();
        this.mEPDGStatus = parcel.readInt();
        this.mURIfromPAU = parcel.readString();
        this.mURIfromPAU2nd = parcel.readString();
        this.mFeatureTags = parcel.readString();
    }
}
