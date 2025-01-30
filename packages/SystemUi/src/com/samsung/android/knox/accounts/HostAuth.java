package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HostAuth implements Parcelable {
    public static final String ACCOUNT_KEY = "accountKey";
    public static final String ADDRESS = "address";
    public static final Parcelable.Creator<HostAuth> CREATOR = new Parcelable.Creator<HostAuth>() { // from class: com.samsung.android.knox.accounts.HostAuth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final HostAuth[] newArray(int i) {
            return new HostAuth[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final HostAuth createFromParcel(Parcel parcel) {
            return new HostAuth(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final HostAuth[] newArray(int i) {
            return new HostAuth[i];
        }
    };
    public static final String DOMAIN = "domain";
    public static final String FLAGS = "flags";
    public static final int FLAGS_ACCEPT_ALL_CERT = 8;
    public static final int FLAGS_USE_SSL = 1;
    public static final int FLAGS_USE_TLS = 2;

    /* renamed from: ID */
    public static final String f474ID = "_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public boolean acceptAllCertificates;
    public long accountKey;
    public String address;
    public String domain;
    public int flags;

    /* renamed from: id */
    public int f475id;
    public String login;
    public String password;
    public int port;
    public String protocol;
    public boolean useSSL;
    public boolean useTLS;

    public /* synthetic */ HostAuth(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.f475id = parcel.readInt();
        this.protocol = parcel.readString();
        this.address = parcel.readString();
        this.port = parcel.readInt();
        this.flags = parcel.readInt();
        this.useSSL = parcel.readInt() != 0;
        this.useTLS = parcel.readInt() != 0;
        this.acceptAllCertificates = parcel.readInt() != 0;
        this.login = parcel.readString();
        this.password = parcel.readString();
        this.domain = parcel.readString();
        this.accountKey = parcel.readLong();
    }

    public final String toString() {
        return "_id=" + this.f475id + " protocol=" + this.protocol + " address=" + this.address + " port=" + this.port + " usessl = " + this.useSSL + " usetls = " + this.useTLS + " acceptallcertificate = " + this.acceptAllCertificates + " login=" + this.login + " password= **** domain=" + this.domain + " accountKey=" + this.accountKey;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f475id);
        parcel.writeString(this.protocol);
        parcel.writeString(this.address);
        parcel.writeInt(this.port);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.useSSL ? 1 : 0);
        parcel.writeInt(this.useTLS ? 1 : 0);
        parcel.writeInt(this.acceptAllCertificates ? 1 : 0);
        parcel.writeString(this.login);
        parcel.writeString(this.password);
        parcel.writeString(this.domain);
        parcel.writeLong(this.accountKey);
    }

    public HostAuth() {
        this.useSSL = false;
        this.useTLS = false;
        this.acceptAllCertificates = false;
    }

    private HostAuth(Parcel parcel) {
        this.useSSL = false;
        this.useTLS = false;
        this.acceptAllCertificates = false;
        readFromParcel(parcel);
    }
}
