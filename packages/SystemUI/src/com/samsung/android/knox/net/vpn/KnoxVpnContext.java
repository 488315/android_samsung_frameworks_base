package com.samsung.android.knox.net.vpn;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class KnoxVpnContext implements Parcelable {
    public static final Parcelable.Creator<KnoxVpnContext> CREATOR = new Parcelable.Creator<KnoxVpnContext>() { // from class: com.samsung.android.knox.net.vpn.KnoxVpnContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnContext createFromParcel(Parcel parcel) {
            return new KnoxVpnContext(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxVpnContext[] newArray(int i) {
            return new KnoxVpnContext[i];
        }
    };
    public int adminId;
    public int personaId;
    public String vendorName;

    public /* synthetic */ KnoxVpnContext(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAdminId() {
        return this.adminId;
    }

    public int getPersonaId() {
        return this.personaId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void readFromParcel(Parcel parcel) {
        this.adminId = parcel.readInt();
        this.personaId = parcel.readInt();
        this.vendorName = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.adminId);
        parcel.writeInt(this.personaId);
        parcel.writeString(this.vendorName);
    }

    public KnoxVpnContext(int i, int i2, String str) {
        this.adminId = i;
        this.personaId = i2;
        this.vendorName = str;
    }

    private KnoxVpnContext(Parcel parcel) {
        readFromParcel(parcel);
    }
}
