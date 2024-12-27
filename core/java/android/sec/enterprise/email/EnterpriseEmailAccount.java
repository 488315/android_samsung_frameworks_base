package android.sec.enterprise.email;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class EnterpriseEmailAccount implements Parcelable {
    public static final Parcelable.Creator<EnterpriseEmailAccount> CREATOR =
            new Parcelable.Creator<
                    EnterpriseEmailAccount>() { // from class:
                                                // android.sec.enterprise.email.EnterpriseEmailAccount.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EnterpriseEmailAccount createFromParcel(Parcel in) {
                    return new EnterpriseEmailAccount(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EnterpriseEmailAccount[] newArray(int size) {
                    return new EnterpriseEmailAccount[size];
                }
            };
    public static final String EXTRA_ACCOUNT_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL";
    public static final String EXTRA_ACCOUNT_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL";
    public static final String EXTRA_NOTIFY_INTERNAL =
            "com.samsung.android.knox.intent.extra.EXTRA_NOTIFY_INTERNAL";
    public static final String EXTRA_OUTGOING_SENDER_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_SENDER_NAME_INTERNAL";
    public static final String EXTRA_OUTGOING_SERVICE_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_SERVICE_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_INTERNAL";
    public static final String EXTRA_RECEIVE_HOST_INTERNAL =
            "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL";
    public static final String EXTRA_RECEIVE_PORT_INTERNAL =
            "com.samsung.android.knox.intent.extra.RECEIVE_PORT_INTERNAL";
    public static final String EXTRA_RECEIVE_SECURITY_INTERNAL =
            "com.samsung.android.knox.intent.extra.RECEIVE_SECURITY_INTERNAL";
    public static final String EXTRA_SENDER_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.SENDER_NAME_INTERNAL";
    public static final String EXTRA_SEND_HOST_INTERNAL =
            "com.samsung.android.knox.intent.extra.SEND_HOST_INTERNAL";
    public static final String EXTRA_SEND_PORT_INTERNAL =
            "com.samsung.android.knox.intent.extra.SEND_PORT_INTERNAL";
    public static final String EXTRA_SEND_SECURITY_INTERNAL =
            "com.samsung.android.knox.intent.extra.SEND_SECURITY_INTERNAL";
    public static final String EXTRA_SERVICE_INTERNAL =
            "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL";
    public static final String EXTRA_SIGNATURE_INTERNAL =
            "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL";
    public static final String EXTRA_USER_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_PASSWD_INTERNAL";
    public static final String EXTRA_VIBRATE_INTERNAL =
            "com.samsung.android.knox.intent.extra.VIBRATE_INTERNAL";
    public static final String EXTRA_VIBRATE_WHEN_SILENT_INTERNAL =
            "com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL";
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

    private EnterpriseEmailAccount(Parcel in) {
        readFromParcel(in);
    }

    public EnterpriseEmailAccount() {}

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

    public void readFromParcel(Parcel in) {
        this.mOutgoingUserName = in.readString();
        this.mOutgoingServerAddress = in.readString();
        this.mOutgoingServerPort = in.readInt();
        this.mOutgoingProtocol = in.readString();
        this.mOutgoingPassword = in.readString();
        this.mOutgoingUseSSL = in.readInt() == 0;
        this.mOutgoingUseTLS = in.readInt() == 0;
        this.mOutgoingAcceptAllCertificates = in.readInt() == 0;
        this.mInComingUserName = in.readString();
        this.mInComingServerAddress = in.readString();
        this.mInComingServerPort = in.readInt();
        this.mInComingProtocol = in.readString();
        this.mInComingPassword = in.readString();
        this.mInComingUseSSL = in.readInt() == 0;
        this.mInComingUseTLS = in.readInt() == 0;
        this.mInComingAcceptAllCertificates = in.readInt() == 0;
        this.mId = in.readLong();
        this.mDisplayName = in.readString();
        this.mEmailAddress = in.readString();
        this.mSenderName = in.readString();
        this.mSignature = in.readString();
        this.mSyncLookback = in.readInt();
        this.mSyncInterval = in.readInt();
        this.mEmailNotificationVibrateAlways = in.readInt() == 0;
        this.mEmailNotificationVibrateWhenSilent = in.readInt() == 0;
        this.mIsDefault = in.readInt() == 0;
        this.mPeakDays = in.readInt();
        this.mPeakStartMinute = in.readInt();
        this.mPeakEndMinute = in.readInt();
        this.mPeakSyncSchedule = in.readInt();
        this.mOffPeakSyncSchedule = in.readInt();
        this.mRoamingSyncSchedule = in.readInt();
    }

    public String toString() {
        return "mId = "
                + this.mId
                + ", mDisplayName="
                + this.mDisplayName
                + ", mEmailAddress="
                + this.mEmailAddress
                + ", mSenderName"
                + this.mSenderName
                + ", mSyncLookback ="
                + this.mSyncLookback
                + ", mSyncInterval="
                + this.mSyncInterval
                + ", mEmailNotificationVibrateAlways ="
                + this.mEmailNotificationVibrateAlways
                + ", mIsDefault="
                + this.mIsDefault
                + ", mPeakDays="
                + this.mPeakDays
                + ", mPeakStartMinute="
                + this.mPeakStartMinute
                + ", mPeakEndMinute="
                + this.mPeakEndMinute
                + ", mPeakSyncSchedule= "
                + this.mPeakSyncSchedule
                + ", mOffPeakSyncSchedule="
                + this.mOffPeakSyncSchedule
                + ", mRoamingSyncSchedule="
                + this.mRoamingSyncSchedule;
    }
}
