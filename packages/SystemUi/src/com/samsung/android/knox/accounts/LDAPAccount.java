package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LDAPAccount implements Parcelable {
    public static final Parcelable.Creator<LDAPAccount> CREATOR = new Parcelable.Creator<LDAPAccount>() { // from class: com.samsung.android.knox.accounts.LDAPAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LDAPAccount[] newArray(int i) {
            return new LDAPAccount[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LDAPAccount createFromParcel(Parcel parcel) {
            return new LDAPAccount(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final LDAPAccount[] newArray(int i) {
            return new LDAPAccount[i];
        }
    };
    public String baseDN;
    public String host;

    /* renamed from: id */
    public long f476id;
    public boolean isAnonymous;
    public boolean isSSL;
    public String password;
    public int port;
    public int trustAll;
    public String userName;

    public /* synthetic */ LDAPAccount(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.f476id = parcel.readLong();
        this.userName = parcel.readString();
        this.password = parcel.readString();
        this.port = parcel.readInt();
        this.host = parcel.readString();
        this.isSSL = parcel.readInt() == 0;
        this.isAnonymous = parcel.readInt() == 0;
        this.baseDN = parcel.readString();
        this.trustAll = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f476id);
        parcel.writeString(this.userName);
        parcel.writeString(this.password);
        parcel.writeInt(this.port);
        parcel.writeString(this.host);
        parcel.writeInt(!this.isSSL ? 1 : 0);
        parcel.writeInt(!this.isAnonymous ? 1 : 0);
        parcel.writeString(this.baseDN);
        parcel.writeInt(this.trustAll);
    }

    private LDAPAccount(Parcel parcel) {
        readFromParcel(parcel);
    }

    public LDAPAccount() {
    }
}
