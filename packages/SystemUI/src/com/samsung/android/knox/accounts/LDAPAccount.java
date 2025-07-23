package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LDAPAccount implements Parcelable {
    public static final Parcelable.Creator<LDAPAccount> CREATOR = new Parcelable.Creator<LDAPAccount>() { // from class: com.samsung.android.knox.accounts.LDAPAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LDAPAccount createFromParcel(Parcel parcel) {
            return new LDAPAccount(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LDAPAccount[] newArray(int i) {
            return new LDAPAccount[i];
        }
    };
    public String baseDN;
    public String host;
    public long id;
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
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.id = parcel.readLong();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
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
