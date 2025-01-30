package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ExchangeAccount implements Parcelable {
    public static final Parcelable.Creator<ExchangeAccount> CREATOR = new Parcelable.Creator<ExchangeAccount>() { // from class: com.samsung.android.knox.accounts.ExchangeAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ExchangeAccount createFromParcel(Parcel parcel) {
            return new ExchangeAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ExchangeAccount[] newArray(int i) {
            return new ExchangeAccount[i];
        }

        @Override // android.os.Parcelable.Creator
        public final ExchangeAccount createFromParcel(Parcel parcel) {
            return new ExchangeAccount(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final ExchangeAccount[] newArray(int i) {
            return new ExchangeAccount[i];
        }
    };
    public static final int SET_SMIME_CERTIFICATE_ALL = 1;
    public static final int SET_SMIME_CERTIFICATE_BY_ECRYPTION = 2;
    public static final int SET_SMIME_CERTIFICATE_BY_ENCRYPTION = 2;
    public static final int SET_SMIME_CERTIFICATE_BY_SIGNING = 3;
    public boolean acceptAllCertificates;
    public String certificateAlias;
    public byte[] certificateData;
    public String certificatePassword;
    public String certificateStorageName;
    public String displayName;
    public String easDomain;
    public String easUser;
    public String emailAddress;
    public boolean emailNotificationVibrateAlways;
    public boolean emailNotificationVibrateWhenSilent;
    public boolean isDefault;
    public boolean isNotify;
    public int offPeak;
    public int peakDays;
    public int peakEndTime;
    public int peakStartTime;
    public int periodCalendar;
    public String protocolVersion;
    public int retrivalSize;
    public int roamingSchedule;
    public String senderName;
    public String serverAddress;
    public String serverPassword;
    public String serverPathPrefix;
    public String signature;
    public int smimeCertificareMode;
    public int smimeCertificateMode;
    public String smimeCertificatePassword;
    public String smimeCertificatePath;
    public int syncCalendar;
    public int syncContacts;
    public int syncInterval;
    public int syncLookback;
    public boolean useSSL;
    public boolean useTLS;

    public ExchangeAccount() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.displayName = parcel.readString();
        this.emailAddress = parcel.readString();
        this.easUser = parcel.readString();
        this.easDomain = parcel.readString();
        this.senderName = parcel.readString();
        this.protocolVersion = parcel.readString();
        this.signature = parcel.readString();
        this.serverAddress = parcel.readString();
        this.serverPassword = parcel.readString();
        this.serverPathPrefix = parcel.readString();
        this.certificatePassword = parcel.readString();
        this.certificateData = parcel.createByteArray();
        this.certificateAlias = parcel.readString();
        this.certificateStorageName = parcel.readString();
        this.syncLookback = parcel.readInt();
        this.syncInterval = parcel.readInt();
        this.peakStartTime = parcel.readInt();
        this.peakEndTime = parcel.readInt();
        this.peakDays = parcel.readInt();
        this.offPeak = parcel.readInt();
        this.roamingSchedule = parcel.readInt();
        this.retrivalSize = parcel.readInt();
        this.periodCalendar = parcel.readInt();
        this.syncContacts = parcel.readInt();
        this.syncCalendar = parcel.readInt();
        this.emailNotificationVibrateAlways = parcel.readInt() != 0;
        this.emailNotificationVibrateWhenSilent = parcel.readInt() != 0;
        this.useSSL = parcel.readInt() != 0;
        this.useTLS = parcel.readInt() != 0;
        this.acceptAllCertificates = parcel.readInt() != 0;
        this.isDefault = parcel.readInt() != 0;
        this.isNotify = parcel.readInt() != 0;
        this.smimeCertificareMode = parcel.readInt();
        this.smimeCertificateMode = parcel.readInt();
        this.smimeCertificatePath = parcel.readString();
        this.smimeCertificatePassword = parcel.readString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.displayName);
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.easUser);
        parcel.writeString(this.easDomain);
        parcel.writeString(this.senderName);
        parcel.writeString(this.protocolVersion);
        parcel.writeString(this.signature);
        parcel.writeString(this.serverAddress);
        parcel.writeString(this.serverPassword);
        parcel.writeString(this.serverPathPrefix);
        parcel.writeString(this.certificatePassword);
        parcel.writeByteArray(this.certificateData);
        parcel.writeString(this.certificateAlias);
        parcel.writeString(this.certificateStorageName);
        parcel.writeInt(this.syncLookback);
        parcel.writeInt(this.syncInterval);
        parcel.writeInt(this.peakStartTime);
        parcel.writeInt(this.peakEndTime);
        parcel.writeInt(this.peakDays);
        parcel.writeInt(this.offPeak);
        parcel.writeInt(this.roamingSchedule);
        parcel.writeInt(this.retrivalSize);
        parcel.writeInt(this.periodCalendar);
        parcel.writeInt(this.syncContacts);
        parcel.writeInt(this.syncCalendar);
        parcel.writeInt(this.emailNotificationVibrateAlways ? 1 : 0);
        parcel.writeInt(this.emailNotificationVibrateWhenSilent ? 1 : 0);
        parcel.writeInt(this.useSSL ? 1 : 0);
        parcel.writeInt(this.useTLS ? 1 : 0);
        parcel.writeInt(this.acceptAllCertificates ? 1 : 0);
        parcel.writeInt(this.isDefault ? 1 : 0);
        parcel.writeInt(this.isNotify ? 1 : 0);
        parcel.writeInt(this.smimeCertificareMode);
        parcel.writeInt(this.smimeCertificateMode);
        parcel.writeString(this.smimeCertificatePath);
        parcel.writeString(this.smimeCertificatePassword);
    }

    public ExchangeAccount(Parcel parcel) {
        readFromParcel(parcel);
    }

    public ExchangeAccount(String str, String str2, String str3, String str4, String str5) {
        this.displayName = null;
        this.emailAddress = str;
        this.easUser = str2;
        this.easDomain = str3;
        this.senderName = null;
        this.protocolVersion = "2.5";
        this.signature = null;
        this.serverAddress = str4;
        this.serverPassword = str5;
        this.serverPathPrefix = null;
        this.certificatePassword = null;
        this.certificateData = null;
        this.certificateAlias = null;
        this.certificateStorageName = null;
        this.syncLookback = 1;
        this.syncInterval = -1;
        this.peakStartTime = VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE;
        this.peakEndTime = 1020;
        this.peakDays = 62;
        this.offPeak = -2;
        this.roamingSchedule = 0;
        this.retrivalSize = 3;
        this.periodCalendar = 4;
        this.syncContacts = 1;
        this.syncCalendar = 1;
        this.emailNotificationVibrateAlways = false;
        this.emailNotificationVibrateWhenSilent = false;
        this.useSSL = true;
        this.useTLS = false;
        this.acceptAllCertificates = true;
        this.isDefault = false;
        this.isNotify = true;
        this.smimeCertificatePath = null;
        this.smimeCertificatePassword = null;
        this.smimeCertificareMode = 1;
    }
}
