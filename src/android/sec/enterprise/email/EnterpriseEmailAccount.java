package android.sec.enterprise.email;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class EnterpriseEmailAccount implements Parcelable {
    public static final Parcelable.Creator<EnterpriseEmailAccount> CREATOR = new Parcelable.Creator<EnterpriseEmailAccount>() { // from class: android.sec.enterprise.email.EnterpriseEmailAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseEmailAccount createFromParcel(Parcel parcel) {
            return new EnterpriseEmailAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseEmailAccount[] newArray(int i) {
            return new EnterpriseEmailAccount[i];
        }
    };
    public static final String EXTRA_ACCOUNT_ID_INTERNAL = "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL";
    public static final String EXTRA_ACCOUNT_NAME_INTERNAL = "com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL";
    public static final String EXTRA_NOTIFY_INTERNAL = "com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL";
    public static final String EXTRA_OUTGOING_SENDER_NAME_INTERNAL = "com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL";
    public static final String EXTRA_OUTGOING_SERVICE_INTERNAL = "com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_ID_INTERNAL = "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_INTERNAL";
    public static final String EXTRA_RECEIVE_HOST_INTERNAL = "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL";
    public static final String EXTRA_RECEIVE_PORT_INTERNAL = "com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL";
    public static final String EXTRA_RECEIVE_SECURITY_INTERNAL = "com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL";
    public static final String EXTRA_SENDER_NAME_INTERNAL = "com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL";
    public static final String EXTRA_SEND_HOST_INTERNAL = "com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL";
    public static final String EXTRA_SEND_PORT_INTERNAL = "com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL";
    public static final String EXTRA_SEND_SECURITY_INTERNAL = "com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL";
    public static final String EXTRA_SERVICE_INTERNAL = "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL";
    public static final String EXTRA_SIGNATURE_INTERNAL = "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL";
    public static final String EXTRA_USER_ID_INTERNAL = "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_ID_INTERNAL = "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_INTERNAL = "com.samsung.android.knox.intent.extra.USER_PASSWD_INTERNAL";
    public static final String EXTRA_VIBRATE_INTERNAL = "com.samsung.android.knox.intent.extra.VIBRATE_INTERNAL";
    public static final String EXTRA_VIBRATE_WHEN_SILENT_INTERNAL = "com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL";
    public String mDisplayName;
    public String mEmailAddress;
    public boolean mEmailNotificationVibrateAlways;
    public boolean mEmailNotificationVibrateWhenSilent;
    public long mId;
    public boolean mInComingAcceptAllCertificates;
    public String mInComingPassword;
    public String mInComingProtocol;
    public String mInComingServerAddress;
    public int mInComingServerPort;
    public boolean mInComingUseSSL;
    public boolean mInComingUseTLS;
    public String mInComingUserName;
    public boolean mIsDefault;
    public int mOffPeakSyncSchedule;
    public boolean mOutgoingAcceptAllCertificates;
    public String mOutgoingPassword;
    public String mOutgoingProtocol;
    public String mOutgoingServerAddress;
    public int mOutgoingServerPort;
    public boolean mOutgoingUseSSL;
    public boolean mOutgoingUseTLS;
    public String mOutgoingUserName;
    public int mPeakDays;
    public int mPeakEndMinute;
    public int mPeakStartMinute;
    public int mPeakSyncSchedule;
    public int mRoamingSyncSchedule;
    public String mSenderName;
    public String mSignature;
    public int mSyncInterval;
    public int mSyncLookback;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private EnterpriseEmailAccount(Parcel parcel) {
        readFromParcel(parcel);
    }

    public EnterpriseEmailAccount() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mOutgoingUserName);
        parcel.writeString(this.mOutgoingServerAddress);
        parcel.writeInt(this.mOutgoingServerPort);
        parcel.writeString(this.mOutgoingProtocol);
        parcel.writeString(this.mOutgoingPassword);
        parcel.writeInt(!this.mOutgoingUseSSL ? 1 : 0);
        parcel.writeInt(!this.mOutgoingUseTLS ? 1 : 0);
        parcel.writeInt(!this.mOutgoingAcceptAllCertificates ? 1 : 0);
        parcel.writeString(this.mInComingUserName);
        parcel.writeString(this.mInComingServerAddress);
        parcel.writeInt(this.mInComingServerPort);
        parcel.writeString(this.mInComingProtocol);
        parcel.writeString(this.mInComingPassword);
        parcel.writeInt(!this.mInComingUseSSL ? 1 : 0);
        parcel.writeInt(!this.mInComingUseTLS ? 1 : 0);
        parcel.writeInt(!this.mInComingAcceptAllCertificates ? 1 : 0);
        parcel.writeLong(this.mId);
        parcel.writeString(this.mDisplayName);
        parcel.writeString(this.mEmailAddress);
        parcel.writeString(this.mSenderName);
        parcel.writeString(this.mSignature);
        parcel.writeInt(this.mSyncLookback);
        parcel.writeInt(this.mSyncInterval);
        parcel.writeInt(!this.mEmailNotificationVibrateAlways ? 1 : 0);
        parcel.writeInt(!this.mEmailNotificationVibrateWhenSilent ? 1 : 0);
        parcel.writeInt(!this.mIsDefault ? 1 : 0);
        parcel.writeInt(this.mPeakDays);
        parcel.writeInt(this.mPeakStartMinute);
        parcel.writeInt(this.mPeakEndMinute);
        parcel.writeInt(this.mPeakSyncSchedule);
        parcel.writeInt(this.mOffPeakSyncSchedule);
        parcel.writeInt(this.mRoamingSyncSchedule);
    }

    public void readFromParcel(Parcel parcel) {
        this.mOutgoingUserName = parcel.readString();
        this.mOutgoingServerAddress = parcel.readString();
        this.mOutgoingServerPort = parcel.readInt();
        this.mOutgoingProtocol = parcel.readString();
        this.mOutgoingPassword = parcel.readString();
        this.mOutgoingUseSSL = parcel.readInt() == 0;
        this.mOutgoingUseTLS = parcel.readInt() == 0;
        this.mOutgoingAcceptAllCertificates = parcel.readInt() == 0;
        this.mInComingUserName = parcel.readString();
        this.mInComingServerAddress = parcel.readString();
        this.mInComingServerPort = parcel.readInt();
        this.mInComingProtocol = parcel.readString();
        this.mInComingPassword = parcel.readString();
        this.mInComingUseSSL = parcel.readInt() == 0;
        this.mInComingUseTLS = parcel.readInt() == 0;
        this.mInComingAcceptAllCertificates = parcel.readInt() == 0;
        this.mId = parcel.readLong();
        this.mDisplayName = parcel.readString();
        this.mEmailAddress = parcel.readString();
        this.mSenderName = parcel.readString();
        this.mSignature = parcel.readString();
        this.mSyncLookback = parcel.readInt();
        this.mSyncInterval = parcel.readInt();
        this.mEmailNotificationVibrateAlways = parcel.readInt() == 0;
        this.mEmailNotificationVibrateWhenSilent = parcel.readInt() == 0;
        this.mIsDefault = parcel.readInt() == 0;
        this.mPeakDays = parcel.readInt();
        this.mPeakStartMinute = parcel.readInt();
        this.mPeakEndMinute = parcel.readInt();
        this.mPeakSyncSchedule = parcel.readInt();
        this.mOffPeakSyncSchedule = parcel.readInt();
        this.mRoamingSyncSchedule = parcel.readInt();
    }

    public String toString() {
        return "mId = " + this.mId + ", mDisplayName=" + this.mDisplayName + ", mEmailAddress=" + this.mEmailAddress + ", mSenderName" + this.mSenderName + ", mSyncLookback =" + this.mSyncLookback + ", mSyncInterval=" + this.mSyncInterval + ", mEmailNotificationVibrateAlways =" + this.mEmailNotificationVibrateAlways + ", mIsDefault=" + this.mIsDefault + ", mPeakDays=" + this.mPeakDays + ", mPeakStartMinute=" + this.mPeakStartMinute + ", mPeakEndMinute=" + this.mPeakEndMinute + ", mPeakSyncSchedule= " + this.mPeakSyncSchedule + ", mOffPeakSyncSchedule=" + this.mOffPeakSyncSchedule + ", mRoamingSyncSchedule=" + this.mRoamingSyncSchedule;
    }
}
