package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemDMACdata implements Parcelable {
    public static final Parcelable.Creator<SemDMACdata> CREATOR = new Parcelable.Creator<SemDMACdata>() { // from class: com.android.internal.telephony.SemDMACdata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDMACdata createFromParcel(Parcel in) {
            return new SemDMACdata(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDMACdata[] newArray(int size) {
            return new SemDMACdata[size];
        }
    };
    private String carrierActivatedId;
    private String isUnLockedPhone;
    private String mccmnc;
    private String preInstalledMsgAppError;
    private String tssActivated;

    protected SemDMACdata() {
        this.preInstalledMsgAppError = "None";
        this.tssActivated = "";
        this.carrierActivatedId = "";
        this.isUnLockedPhone = "false";
        this.mccmnc = "";
    }

    protected SemDMACdata(Parcel in) {
        this.preInstalledMsgAppError = in.readStringNoHelper();
        this.tssActivated = in.readStringNoHelper();
        this.carrierActivatedId = in.readStringNoHelper();
        this.isUnLockedPhone = in.readStringNoHelper();
        this.mccmnc = in.readStringNoHelper();
    }

    public void setPreInstalledMsgAppError(String preInstalledMsgAppError) {
        this.preInstalledMsgAppError = preInstalledMsgAppError;
    }

    public void setTssActivated(String tssActivated) {
        this.tssActivated = tssActivated;
    }

    public void setCarrierActivatedId(String carrierActivatedId) {
        this.carrierActivatedId = carrierActivatedId;
    }

    public void setIsUnLockedPhone(String isUnLockedPhone) {
        this.isUnLockedPhone = isUnLockedPhone;
    }

    public void setMccmnc(String mccmnc) {
        this.mccmnc = mccmnc;
    }

    public String getPreInstalledMsgAppError() {
        return this.preInstalledMsgAppError;
    }

    public String getTssActivated() {
        return this.tssActivated;
    }

    public String getCarrierActivatedId() {
        return this.carrierActivatedId;
    }

    public String getIsUnLockedPhone() {
        return this.isUnLockedPhone;
    }

    public String getMccmnc() {
        return this.mccmnc;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringNoHelper(this.preInstalledMsgAppError);
        parcel.writeStringNoHelper(this.tssActivated);
        parcel.writeStringNoHelper(this.carrierActivatedId);
        parcel.writeStringNoHelper(this.isUnLockedPhone);
        parcel.writeStringNoHelper(this.mccmnc);
    }
}
