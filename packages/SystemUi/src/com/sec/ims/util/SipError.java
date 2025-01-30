package com.sec.ims.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SipError implements Parcelable {
    public static final Parcelable.Creator<SipError> CREATOR = new Parcelable.Creator<SipError>() { // from class: com.sec.ims.util.SipError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipError createFromParcel(Parcel parcel) {
            return new SipError(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipError[] newArray(int i) {
            return new SipError[i];
        }
    };
    private static final String LOG_TAG = "SipError";
    protected int mCode;
    protected String mReason;
    protected String mReasonHeader;

    public /* synthetic */ SipError(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SipError)) {
            return false;
        }
        SipError sipError = (SipError) obj;
        return (TextUtils.isEmpty(this.mReason) || TextUtils.isEmpty(sipError.mReason)) ? this.mCode == sipError.mCode : this.mCode == sipError.mCode && this.mReason.equalsIgnoreCase(sipError.mReason);
    }

    public boolean equalsWithStrict(Object obj) {
        if (!(obj instanceof SipError)) {
            return false;
        }
        SipError sipError = (SipError) obj;
        return (TextUtils.isEmpty(this.mReason) && TextUtils.isEmpty(sipError.mReason)) ? this.mCode == sipError.mCode : !TextUtils.isEmpty(this.mReason) && !TextUtils.isEmpty(sipError.mReason) && this.mCode == sipError.mCode && this.mReason.equalsIgnoreCase(sipError.mReason);
    }

    public int getCode() {
        return this.mCode;
    }

    public SipError getFromRejectReason(int i) {
        Log.e(LOG_TAG, "getFromRejectReason: Should be called!!!");
        return null;
    }

    public String getReason() {
        return this.mReason;
    }

    public String getReasonHeader() {
        return this.mReasonHeader;
    }

    public int hashCode() {
        Integer valueOf = Integer.valueOf(this.mCode);
        String str = this.mReasonHeader;
        return Objects.hash(valueOf, str, str);
    }

    public void setCode(int i) {
        this.mCode = i;
    }

    public void setReason(String str) {
        this.mReason = str;
    }

    public String toString() {
        String str = "";
        StringBuilder sb = new StringBuilder("");
        sb.append(this.mCode);
        sb.append(" - ");
        sb.append(this.mReason);
        if (this.mReasonHeader != null) {
            str = " - " + this.mReasonHeader;
        }
        sb.append(str);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCode);
        if (this.mReason == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.mReason);
        }
        if (this.mReasonHeader == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.mReasonHeader);
        }
    }

    public SipError() {
    }

    public SipError(int i) {
        this.mCode = i;
    }

    public SipError(int i, String str) {
        this.mCode = i;
        this.mReason = str;
    }

    public SipError(int i, String str, String str2) {
        this.mCode = i;
        this.mReason = str;
        this.mReasonHeader = str2;
    }

    private SipError(Parcel parcel) {
        this.mCode = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mReason = parcel.readString();
        }
        if (parcel.readInt() == 1) {
            this.mReasonHeader = parcel.readString();
        }
    }
}
