package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SemDMACdata implements Parcelable {
    public static final Parcelable.Creator<SemDMACdata> CREATOR = new Parcelable.Creator<SemDMACdata>() { // from class: com.android.internal.telephony.SemDMACdata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDMACdata createFromParcel(Parcel parcel) {
            return new SemDMACdata(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDMACdata[] newArray(int i) {
            return new SemDMACdata[i];
        }
    };
    private String carrierActivatedId;
    private String isUnLockedPhone;
    private String mccmnc;
    private String preInstalledMsgAppError;
    private String tssActivated;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected SemDMACdata() {
        this.preInstalledMsgAppError = "None";
        this.tssActivated = "";
        this.carrierActivatedId = "";
        this.isUnLockedPhone = "false";
        this.mccmnc = "";
    }

    protected SemDMACdata(Parcel parcel) {
        this.preInstalledMsgAppError = parcel.readStringNoHelper();
        this.tssActivated = parcel.readStringNoHelper();
        this.carrierActivatedId = parcel.readStringNoHelper();
        this.isUnLockedPhone = parcel.readStringNoHelper();
        this.mccmnc = parcel.readStringNoHelper();
    }

    public void setPreInstalledMsgAppError(String str) {
        this.preInstalledMsgAppError = str;
    }

    public void setTssActivated(String str) {
        this.tssActivated = str;
    }

    public void setCarrierActivatedId(String str) {
        this.carrierActivatedId = str;
    }

    public void setIsUnLockedPhone(String str) {
        this.isUnLockedPhone = str;
    }

    public void setMccmnc(String str) {
        this.mccmnc = str;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringNoHelper(this.preInstalledMsgAppError);
        parcel.writeStringNoHelper(this.tssActivated);
        parcel.writeStringNoHelper(this.carrierActivatedId);
        parcel.writeStringNoHelper(this.isUnLockedPhone);
        parcel.writeStringNoHelper(this.mccmnc);
    }
}
