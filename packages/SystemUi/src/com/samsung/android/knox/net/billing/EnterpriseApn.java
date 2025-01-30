package com.samsung.android.knox.net.billing;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnterpriseApn implements Parcelable {
    public static Parcelable.Creator<EnterpriseApn> CREATOR = new Parcelable.Creator<EnterpriseApn>() { // from class: com.samsung.android.knox.net.billing.EnterpriseApn.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseApn[] newArray(int i) {
            return new EnterpriseApn[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseApn createFromParcel(Parcel parcel) {
            return new EnterpriseApn(parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final EnterpriseApn[] newArray(int i) {
            return new EnterpriseApn[i];
        }
    };
    public final String apn;
    public final String mcc;
    public final String mnc;

    public EnterpriseApn(String str, String str2, String str3) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.apn = str;
        this.mcc = str2;
        this.mnc = str3;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            return ((EnterpriseApn) obj).toString().equalsIgnoreCase(toString());
        }
        return false;
    }

    public final int hashCode() {
        String str = this.apn;
        if (str == null || this.mcc == null || this.mnc == null) {
            return 0;
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i += c;
        }
        for (char c2 : this.mcc.toCharArray()) {
            i += c2;
        }
        for (char c3 : this.mnc.toCharArray()) {
            i += c3;
        }
        return i;
    }

    public final String toString() {
        return this.apn + ":" + this.mcc + ":" + this.mnc;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.apn);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
    }
}
