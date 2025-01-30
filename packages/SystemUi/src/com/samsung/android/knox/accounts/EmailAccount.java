package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EmailAccount implements Parcelable {
    public static final Parcelable.Creator<EmailAccount> CREATOR = new Parcelable.Creator<EmailAccount>() { // from class: com.samsung.android.knox.accounts.EmailAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EmailAccount createFromParcel(Parcel parcel) {
            return new EmailAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EmailAccount[] newArray(int i) {
            return new EmailAccount[i];
        }

        @Override // android.os.Parcelable.Creator
        public final EmailAccount createFromParcel(Parcel parcel) {
            return new EmailAccount(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final EmailAccount[] newArray(int i) {
            return new EmailAccount[i];
        }
    };
    public String emailAddress;
    public String incomingProtocol;
    public boolean incomingServerAcceptAllCertificates;
    public String incomingServerAddress;
    public String incomingServerLogin;
    public String incomingServerPassword;
    public int incomingServerPort;
    public boolean incomingServerUseSSL;
    public boolean incomingServerUseTLS;
    public boolean isNotify;
    public String outgoingProtocol;
    public boolean outgoingServerAcceptAllCertificates;
    public String outgoingServerAddress;
    public String outgoingServerLogin;
    public String outgoingServerPassword;
    public int outgoingServerPort;
    public boolean outgoingServerUseSSL;
    public boolean outgoingServerUseTLS;
    public String signature;

    public EmailAccount() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.emailAddress = parcel.readString();
        this.incomingProtocol = parcel.readString();
        this.incomingServerAddress = parcel.readString();
        this.incomingServerLogin = parcel.readString();
        this.incomingServerPassword = parcel.readString();
        this.outgoingProtocol = parcel.readString();
        this.outgoingServerAddress = parcel.readString();
        this.outgoingServerLogin = parcel.readString();
        this.outgoingServerPassword = parcel.readString();
        this.signature = parcel.readString();
        this.incomingServerPort = parcel.readInt();
        this.outgoingServerPort = parcel.readInt();
        this.outgoingServerUseSSL = parcel.readInt() != 0;
        this.outgoingServerUseTLS = parcel.readInt() != 0;
        this.outgoingServerAcceptAllCertificates = parcel.readInt() != 0;
        this.incomingServerUseSSL = parcel.readInt() != 0;
        this.incomingServerUseTLS = parcel.readInt() != 0;
        this.incomingServerAcceptAllCertificates = parcel.readInt() != 0;
        this.isNotify = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.incomingProtocol);
        parcel.writeString(this.incomingServerAddress);
        parcel.writeString(this.incomingServerLogin);
        parcel.writeString(this.incomingServerPassword);
        parcel.writeString(this.outgoingProtocol);
        parcel.writeString(this.outgoingServerAddress);
        parcel.writeString(this.outgoingServerLogin);
        parcel.writeString(this.outgoingServerPassword);
        parcel.writeString(this.signature);
        parcel.writeInt(this.incomingServerPort);
        parcel.writeInt(this.outgoingServerPort);
        parcel.writeInt(this.outgoingServerUseSSL ? 1 : 0);
        parcel.writeInt(this.outgoingServerUseTLS ? 1 : 0);
        parcel.writeInt(this.outgoingServerAcceptAllCertificates ? 1 : 0);
        parcel.writeInt(this.incomingServerUseSSL ? 1 : 0);
        parcel.writeInt(this.incomingServerUseTLS ? 1 : 0);
        parcel.writeInt(this.incomingServerAcceptAllCertificates ? 1 : 0);
        parcel.writeInt(this.isNotify ? 1 : 0);
    }

    public EmailAccount(Parcel parcel) {
        readFromParcel(parcel);
    }

    public EmailAccount(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) {
        this.emailAddress = str;
        this.incomingProtocol = str2;
        this.incomingServerAddress = str3;
        this.incomingServerLogin = str4;
        this.incomingServerPassword = str5;
        this.outgoingProtocol = str6;
        this.outgoingServerAddress = str7;
        this.outgoingServerLogin = str8;
        this.outgoingServerPassword = str9;
        this.signature = "Send from SamsungMobile";
        this.incomingServerPort = i;
        this.outgoingServerPort = i2;
        this.outgoingServerUseSSL = true;
        this.outgoingServerUseTLS = false;
        this.outgoingServerAcceptAllCertificates = false;
        this.incomingServerUseSSL = true;
        this.incomingServerUseTLS = false;
        this.incomingServerAcceptAllCertificates = false;
        this.isNotify = true;
    }
}
