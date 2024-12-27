package android.sec.enterprise.email;

import android.os.Parcel;
import android.os.Parcelable;

public class EnterpriseExchangeAccount implements Parcelable {
    public static final String ACTION_CBA_INSTALL_STATUS_INTERNAL =
            "com.samsung.android.knox.intent.action.CBA_INSTALL_STATUS_INTERNAL";
    public static final String ACTION_CBA_INSTALL_STATUS_OLD =
            "android.intent.action.sec.CBA_INSTALL_STATUS";
    public static final String ACTION_CBA_INSTALL_STATUS_SEC_EDM =
            "com.samsung.edm.intent.action.EXCHANGE_CBA_INSTALL_STATUS";
    public static final String ACTION_CREATE_EMAIL_ACCOUNT =
            "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_DELETE_EMAIL_ACCOUNT =
            "com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_DELETE_NOT_VALIDATED_EMAIL_ACCOUNT =
            "com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL";
    public static final String ACTION_EAS_INTENT =
            "com.samsung.android.knox.intent.action.EAS_INTENT_INTERNAL";
    public static final String ACTION_EMAIL_ENABLE_MESSSAGE_COMPOSE =
            "com.samsung.android.knox.intent.action.EMAIL_ENABLE_MSG_COMPOSE_INTERNAL";
    public static final String ACTION_EMAIL_INSTALL_CERTIFICATE =
            "com.samsung.android.knox.intent.action.EMAIL_INSTALL_CERT_INTERNAL";
    public static final String ACTION_EMAIL_RENAME_CERTIFICATE =
            "com.samsung.android.knox.intent.action.EMAIL_RENAME_CERTIFICATE_INTERNAL";
    public static final String ACTION_ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL =
            "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL";
    public static final String ACTION_ENFORCE_SMIME_ALIAS_INTERNAL =
            "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_INTERNAL";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE =
            "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION =
            "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL";
    public static final String ACTION_FORCE_SMIME_CERTIFICATE_FOR_SIGNING =
            "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL";
    public static final String ACTION_GET_EMAIL_DEVICE_ID_REQUEST =
            "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_REQUEST_INTERNAL";
    public static final String ACTION_GET_EMAIL_DEVICE_ID_RESULT =
            "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_RESULT_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE =
            "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION =
            "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL";
    public static final String ACTION_RELEASE_SMIME_CERTIFICATE_FOR_SIGNING =
            "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL";
    public static final String ACTION_REQUEST_EMAIL_ACCOUNT_PASSWORD =
            "com.samsung.android.knox.intent.action.REQUEST_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final String ACTION_RESULT_EMAIL_ACCOUNT_PASSWORD =
            "com.samsung.android.knox.intent.action.RESULT_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final String ACTION_SET_EMAIL_ACCOUNT_PASSWORD =
            "com.samsung.android.knox.intent.action.SET_EMAILACCOUNT_PASSWORD_INTERNAL";
    public static final String ACTION_SMIME_INSTALL_STATUS =
            "com.samsung.edm.intent.action.EXCHANGE_SMIME_INSTALL_STATUS";
    public static final Parcelable.Creator<EnterpriseExchangeAccount> CREATOR =
            new Parcelable.Creator<
                    EnterpriseExchangeAccount>() { // from class:
                                                   // android.sec.enterprise.email.EnterpriseExchangeAccount.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EnterpriseExchangeAccount createFromParcel(Parcel in) {
                    return new EnterpriseExchangeAccount(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EnterpriseExchangeAccount[] newArray(int size) {
                    return new EnterpriseExchangeAccount[size];
                }
            };
    public static final int ENFORCE_SMIME_ALIAS_TYPE_ENCRYPT = 0;
    public static final int ENFORCE_SMIME_ALIAS_TYPE_SIGN = 1;
    public static final String EXTRA_ACCOUNT_EAS_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_EAS_INTERNAL";
    public static final String EXTRA_ACCOUNT_ID =
            "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
    public static final String EXTRA_ACCOUNT_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL";
    public static final String EXTRA_ACCOUNT_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL";
    public static final String EXTRA_ACCOUNT_SETUP_RESULT_STATUS_INTERNAL =
            "com.samsung.android.knox.intent.extra.ACCOUNT_SETUP_RESULT_STATUS_INTERNAL";
    public static final String EXTRA_CERTIFICATE_ALIAS_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_ALIAS_INTERNAL";
    public static final String EXTRA_CERTIFICATE_DATA_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL";
    public static final String EXTRA_CERTIFICATE_PASSWORD_ID =
            "com.samsung.android.knox.intent.extra.CERT_PASSWORD_ID_INTERNAL";
    public static final String EXTRA_CERTIFICATE_PASSWORD_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL";
    public static final String EXTRA_CERTIFICATE_PASSWORD_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_INTERNAL";
    public static final String EXTRA_CERTIFICATE_PATH =
            "com.samsung.android.knox.intent.extra.CERT_PATH_INTERNAL";
    public static final String EXTRA_CERTIFICATE_RESULT_ID =
            "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL";
    public static final String EXTRA_CERTIFICATE_RESULT_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.CERTIFICATE_RESULT_ID_INTERNAL";
    public static final String EXTRA_DEVICE_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.DEVICE_ID_INTERNAL";
    public static final String EXTRA_DOMAIN_INTERNAL =
            "com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_NAME =
            "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_NAME_INTERNAL";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_STORAGE =
            "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_STORAGE_INTERNAL";
    public static final String EXTRA_ENFORCE_SMIME_ALIAS_TYPE =
            "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE";
    public static final String EXTRA_IS_DEFAULT_INTERNAL =
            "com.samsung.android.knox.intent.extra.IS_DEFAULT_INTERNAL";
    public static final String EXTRA_NOTIFY_INTERNAL =
            "com.samsung.android.knox.intent.extra.NOTIFY_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_OUTGOING_USER_PASSWORD_INTERNAL =
            "com.samsung.android.knox.intent.extra.OUTGOING_USER_PASSWD_INTERNAL";
    public static final String EXTRA_PEAK_DAYS_INTERNAL =
            "com.samsung.android.knox.intent.extra.PEAK_DAYS_INTERNAL";
    public static final String EXTRA_PEAK_END_TIME_INTERNAL =
            "com.samsung.android.knox.intent.extra.PEAK_END_TIME_INTERNAL";
    public static final String EXTRA_PEAK_INTERNAL =
            "com.samsung.android.knox.intent.extra.PEAK_INTERNAL";
    public static final String EXTRA_PEAK_OFF_INTERNAL =
            "com.samsung.android.knox.intent.extra.OFF_PEAK_INTERNAL";
    public static final String EXTRA_PEAK_START_TIME_INTERNAL =
            "com.samsung.android.knox.intent.extra.PEAK_START_TIME_INTERNAL";
    public static final String EXTRA_PERIOD_CALENDAR_INTERNAL =
            "com.samsung.android.knox.intent.extra.PERIOD_CALENDAR_INTERNAL";
    public static final String EXTRA_PERIOD_EMAIL_INTERNAL =
            "com.samsung.android.knox.intent.extra.PERIOD_EMAIL_INTERNAL";
    public static final String EXTRA_RECEIVE_HOST_INTERNAL =
            "com.samsung.android.knox.intent.extra.RECEIVE_HOST_INTERNAL";
    public static final String EXTRA_RETRIVAL_SIZE2_INTERNAL =
            "com.samsung.android.knox.intent.extra.RETRIVAL_SIZE_INTERNAL";
    public static final String EXTRA_RETRIVAL_SIZE_INTERNAL =
            "com.samsung.android.knox.intent.extra.";
    public static final String EXTRA_ROAMING_SCHEDULE_INTERNAL =
            "com.samsung.android.knox.intent.extra.ROAMING_SCHEDULE_INTERNAL";
    public static final String EXTRA_SERVER_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL";
    public static final String EXTRA_SERVER_PATH_PREFIX_INTERNAL =
            "com.samsung.android.knox.intent.extra.SERVER_PATH_PREFIX_INTERNAL";
    public static final String EXTRA_SERVICE_INTERNAL =
            "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL";
    public static final String EXTRA_SIGNATURE_INTERNAL =
            "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL";
    public static final String EXTRA_SMIME_INSTALL_TYPE =
            "com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE";
    public static final String EXTRA_SMIME_RESULT =
            "com.samsung.android.knox.intent.extra.SMIME_RESULT";
    public static final String EXTRA_STATUS = "com.samsung.android.knox.intent.extra.STATUS";
    public static final String EXTRA_SYNC_CALENDAR_INTERNAL =
            "com.samsung.android.knox.intent.extra.SYNC_CALENDAR_INTERNAL";
    public static final String EXTRA_SYNC_CONTACTS_INTERNAL =
            "com.samsung.android.knox.intent.extra.SYNC_CONTACTS_INTERNAL";
    public static final String EXTRA_TRUST_ALL_INTERNAL =
            "com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL";
    public static final String EXTRA_USER_HANDLE_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_HANDLE_ID_INTERNAL";
    public static final String EXTRA_USER_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
    public static final String EXTRA_USER_NAME_INTERNAL =
            "com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_ID_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL";
    public static final String EXTRA_USER_PASSWORD_INTERNAL =
            "com.samsung.android.knox.intent.extra.USER_PASSWD_INTERNAL";
    public static final String EXTRA_USE_SSL_INTERNAL =
            "com.samsung.android.knox.intent.extra.USE_SSL_INTERNAL";
    public static final String EXTRA_USE_TLS_INTERNAL =
            "com.samsung.android.knox.intent.extra.USE_TLS_INTERNAL";
    public static final String EXTRA_VIBRATE_INTERNAL =
            "com.samsung.android.knox.intent.extra.EXTRA_VIBRATE_INTERNAL";
    public static final String EXTRA_VIBRATE_WHEN_SILENT_INTERNAL =
            "com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL";
    public static final int SET_SMIME_CERTIFICATE_ALL = 1;
    public static final int SET_SMIME_CERTIFICATE_BY_ENCRYPTION = 2;
    public static final int SET_SMIME_CERTIFICATE_BY_SIGNING = 3;
    public static final int SET_SMIME_CERTIFICATE_INVALID_ACCOUNT_ID = 3;
    public static final int SET_SMIME_CERTIFICATE_INVALID_PASSWORD = 2;
    public static final int SET_SMIME_CERTIFICATE_NOT_FOUND = 1;
    public static final int SET_SMIME_CERTIFICATE_OK = -1;
    public static final int SET_SMIME_CERTIFICATE_UNKNOWN_ERROR = 0;
    public static final int SET_SMIME_CERTIFICATE_USER_CANCELED = 4;
    public boolean mAcceptAllCertificates;
    public boolean mAllowHTMLEmail;
    public String mDisplayName;
    public String mEasUser;
    public String mEmailAddress;
    public int mEmailBodyTruncationSize;
    public boolean mEmailNotificationVibrateAlways;
    public boolean mEmailNotificationVibrateWhenSilent;
    public int mEmailRoamingBodyTruncationSize;
    public long mId;
    public boolean mIsDefault;
    public int mMaxCalendarAgeFilter;
    public int mMaxDevicePasswordFailedAttempts;
    public int mMaxEmailAgeFilter;
    public int mMaxEmailBodyTruncationSize;
    public int mMaxEmailHtmlBodyTruncationSize;
    public int mMinDevicePasswordLength;
    public int mMinPasswordComplexCharacters;
    public int mOffPeakSyncSchedule;
    public String mPassword;
    public int mPeakDays;
    public int mPeakEndMinute;
    public int mPeakStartMinute;
    public int mPeakSyncSchedule;
    public String mProtocol;
    public String mProtocolVersion;
    public int mRoamingSyncSchedule;
    public String mSenderName;
    public String mServerAddress;
    public int mServerPort;
    public String mSignature;
    public boolean mSyncCalendar;
    public int mSyncCalendarAge;
    public boolean mSyncContacts;
    public int mSyncInterval;
    public int mSyncLookback;
    public boolean mSyncNotes;
    public boolean mSyncTasks;
    public boolean mUseSSL;
    public boolean mUseTLS;

    public EnterpriseExchangeAccount() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mEasUser);
        parcel.writeString(this.mServerAddress);
        parcel.writeInt(this.mServerPort);
        parcel.writeString(this.mProtocol);
        parcel.writeString(this.mPassword);
        parcel.writeInt(!this.mUseSSL ? 1 : 0);
        parcel.writeInt(!this.mUseTLS ? 1 : 0);
        parcel.writeInt(!this.mAcceptAllCertificates ? 1 : 0);
        parcel.writeLong(this.mId);
        parcel.writeString(this.mDisplayName);
        parcel.writeString(this.mEmailAddress);
        parcel.writeString(this.mSenderName);
        parcel.writeString(this.mProtocolVersion);
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
        parcel.writeInt(this.mSyncCalendarAge);
        parcel.writeInt(this.mEmailBodyTruncationSize);
        parcel.writeInt(this.mEmailRoamingBodyTruncationSize);
        parcel.writeInt(!this.mSyncCalendar ? 1 : 0);
        parcel.writeInt(!this.mSyncContacts ? 1 : 0);
        parcel.writeInt(!this.mSyncTasks ? 1 : 0);
        parcel.writeInt(!this.mSyncNotes ? 1 : 0);
        parcel.writeInt(!this.mAllowHTMLEmail ? 1 : 0);
        parcel.writeInt(this.mMinDevicePasswordLength);
        parcel.writeInt(this.mMinPasswordComplexCharacters);
        parcel.writeInt(this.mMaxEmailHtmlBodyTruncationSize);
        parcel.writeInt(this.mMaxEmailBodyTruncationSize);
        parcel.writeInt(this.mMaxCalendarAgeFilter);
        parcel.writeInt(this.mMaxEmailAgeFilter);
        parcel.writeInt(this.mMaxDevicePasswordFailedAttempts);
    }

    private EnterpriseExchangeAccount(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.mEasUser = in.readString();
        this.mServerAddress = in.readString();
        this.mServerPort = in.readInt();
        this.mProtocol = in.readString();
        this.mPassword = in.readString();
        this.mUseSSL = in.readInt() == 0;
        this.mUseTLS = in.readInt() == 0;
        this.mAcceptAllCertificates = in.readInt() == 0;
        this.mId = in.readLong();
        this.mDisplayName = in.readString();
        this.mEmailAddress = in.readString();
        this.mSenderName = in.readString();
        this.mProtocolVersion = in.readString();
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
        this.mSyncCalendarAge = in.readInt();
        this.mEmailBodyTruncationSize = in.readInt();
        this.mEmailRoamingBodyTruncationSize = in.readInt();
        this.mSyncCalendar = in.readInt() == 0;
        this.mSyncContacts = in.readInt() == 0;
        this.mSyncTasks = in.readInt() == 0;
        this.mSyncNotes = in.readInt() == 0;
        this.mAllowHTMLEmail = in.readInt() == 0;
        this.mMinDevicePasswordLength = in.readInt();
        this.mMinPasswordComplexCharacters = in.readInt();
        this.mMaxEmailHtmlBodyTruncationSize = in.readInt();
        this.mMaxEmailBodyTruncationSize = in.readInt();
        this.mMaxCalendarAgeFilter = in.readInt();
        this.mMaxEmailAgeFilter = in.readInt();
        this.mMaxDevicePasswordFailedAttempts = in.readInt();
    }

    public String toString() {
        return "mId = "
                + this.mId
                + ", mDisplayName="
                + this.mDisplayName
                + ",mEmailAddress ="
                + this.mEmailAddress
                + ", mSenderName="
                + this.mSenderName
                + ", mProtocol="
                + this.mProtocol
                + ", mProtocolVersion = "
                + this.mProtocolVersion
                + ", mUseSSL="
                + this.mUseSSL
                + ", mUseTLS="
                + this.mUseTLS
                + ", mAcceptAllCertificates = "
                + this.mAcceptAllCertificates
                + ", mSyncLookback="
                + this.mSyncLookback
                + ", mSyncInterval="
                + this.mSyncInterval
                + ", mEmailNotificationVibrateAlways="
                + this.mEmailNotificationVibrateAlways
                + ", mIsDefault ="
                + this.mIsDefault
                + ", mPeakDays ="
                + this.mPeakDays
                + ", mPeakStartMinute="
                + this.mPeakStartMinute
                + ", mPeakEndMinute="
                + this.mPeakEndMinute
                + ", mPeakSyncSchedule="
                + this.mPeakSyncSchedule
                + ", mOffPeakSyncSchedule="
                + this.mOffPeakSyncSchedule
                + ", mRoamingSyncSchedule="
                + this.mRoamingSyncSchedule
                + ", mSyncCalendarAge="
                + this.mSyncCalendarAge
                + ", mEmailBodyTruncationSize="
                + this.mEmailBodyTruncationSize
                + ", mEmailRoamingBodyTruncationSize="
                + this.mEmailRoamingBodyTruncationSize
                + ", mSyncCalendar="
                + this.mSyncCalendar
                + ", mSyncContacts="
                + this.mSyncContacts
                + ", mAllowHTMLEmail="
                + this.mAllowHTMLEmail;
    }
}
