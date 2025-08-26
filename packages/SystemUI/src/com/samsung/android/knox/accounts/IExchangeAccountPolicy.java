package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IExchangeAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IExchangeAccountPolicy";

    long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) throws RemoteException;

    long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) throws RemoteException;

    long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) throws RemoteException;

    boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) throws RemoteException;

    boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException;

    boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException;

    String getAccountCertificatePassword(ContextInfo contextInfo, long j) throws RemoteException;

    Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException;

    String getAccountEmailPassword(ContextInfo contextInfo, long j) throws RemoteException;

    long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    Account[] getAllEASAccounts(ContextInfo contextInfo) throws RemoteException;

    String getDeviceId(ContextInfo contextInfo) throws RemoteException;

    boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException;

    boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException;

    boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException;

    int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) throws RemoteException;

    int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) throws RemoteException;

    int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) throws RemoteException;

    int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException;

    int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException;

    boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException;

    boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException;

    String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) throws RemoteException;

    boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) throws RemoteException;

    void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) throws RemoteException;

    void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException;

    boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) throws RemoteException;

    long setAccountCertificatePassword(ContextInfo contextInfo, String str) throws RemoteException;

    long setAccountEmailPassword(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException;

    void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) throws RemoteException;

    boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) throws RemoteException;

    boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) throws RemoteException;

    int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException;

    boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) throws RemoteException;

    int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException;

    int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException;

    boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setPassword(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException;

    boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException;

    boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException;

    boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException;

    boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException;

    boolean setSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException;

    boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException;

    public abstract class Stub extends Binder implements IExchangeAccountPolicy {
        public static final int TRANSACTION_addNewAccount = 2;
        public static final int TRANSACTION_addNewAccount_ex = 3;
        public static final int TRANSACTION_addNewAccount_new = 52;
        public static final int TRANSACTION_allowEmailSettingsChange = 36;
        public static final int TRANSACTION_allowInComingAttachments = 30;
        public static final int TRANSACTION_createAccount = 1;
        public static final int TRANSACTION_deleteAccount = 15;
        public static final int TRANSACTION_getAccountCertificatePassword = 54;
        public static final int TRANSACTION_getAccountDetails = 14;
        public static final int TRANSACTION_getAccountEmailPassword = 53;
        public static final int TRANSACTION_getAccountId = 13;
        public static final int TRANSACTION_getAllEASAccounts = 20;
        public static final int TRANSACTION_getDeviceId = 21;
        public static final int TRANSACTION_getForceSMIMECertificate = 28;
        public static final int TRANSACTION_getForceSMIMECertificateForEncryption = 50;
        public static final int TRANSACTION_getForceSMIMECertificateForSigning = 47;
        public static final int TRANSACTION_getIncomingAttachmentsSize = 33;
        public static final int TRANSACTION_getMaxCalendarAgeFilter = 39;
        public static final int TRANSACTION_getMaxEmailAgeFilter = 41;
        public static final int TRANSACTION_getMaxEmailBodyTruncationSize = 43;
        public static final int TRANSACTION_getMaxEmailHTMLBodyTruncationSize = 45;
        public static final int TRANSACTION_getRequireEncryptedSMIMEMessages = 26;
        public static final int TRANSACTION_getRequireSignedSMIMEMessages = 24;
        public static final int TRANSACTION_getSMIMECertificateAlias = 58;
        public static final int TRANSACTION_isEmailNotificationsEnabled = 35;
        public static final int TRANSACTION_isEmailSettingsChangeAllowed = 37;
        public static final int TRANSACTION_isIncomingAttachmentsAllowed = 31;
        public static final int TRANSACTION_removePendingAccount = 22;
        public static final int TRANSACTION_sendAccountsChangedBroadcast = 16;
        public static final int TRANSACTION_setAcceptAllCertificates = 5;
        public static final int TRANSACTION_setAccountBaseParameters = 59;
        public static final int TRANSACTION_setAccountCertificatePassword = 56;
        public static final int TRANSACTION_setAccountEmailPassword = 55;
        public static final int TRANSACTION_setAccountName = 12;
        public static final int TRANSACTION_setAlwaysVibrateOnEmailNotification = 6;
        public static final int TRANSACTION_setAsDefaultAccount = 11;
        public static final int TRANSACTION_setClientAuthCert = 9;
        public static final int TRANSACTION_setDataSyncs = 19;
        public static final int TRANSACTION_setEmailNotificationsState = 34;
        public static final int TRANSACTION_setForceSMIMECertificate = 27;
        public static final int TRANSACTION_setForceSMIMECertificateAlias = 57;
        public static final int TRANSACTION_setForceSMIMECertificateForEncryption = 49;
        public static final int TRANSACTION_setForceSMIMECertificateForSigning = 46;
        public static final int TRANSACTION_setIncomingAttachmentsSize = 32;
        public static final int TRANSACTION_setMaxCalendarAgeFilter = 38;
        public static final int TRANSACTION_setMaxEmailAgeFilter = 40;
        public static final int TRANSACTION_setMaxEmailBodyTruncationSize = 42;
        public static final int TRANSACTION_setMaxEmailHTMLBodyTruncationSize = 44;
        public static final int TRANSACTION_setPassword = 7;
        public static final int TRANSACTION_setPastDaysToSync = 10;
        public static final int TRANSACTION_setProtocolVersion = 60;
        public static final int TRANSACTION_setReleaseSMIMECertificate = 29;
        public static final int TRANSACTION_setReleaseSMIMECertificateForEncryption = 51;
        public static final int TRANSACTION_setReleaseSMIMECertificateForSigning = 48;
        public static final int TRANSACTION_setRequireEncryptedSMIMEMessages = 25;
        public static final int TRANSACTION_setRequireSignedSMIMEMessages = 23;
        public static final int TRANSACTION_setSSL = 4;
        public static final int TRANSACTION_setSenderName = 61;
        public static final int TRANSACTION_setSignature = 8;
        public static final int TRANSACTION_setSilentVibrateOnEmailNotification = 62;
        public static final int TRANSACTION_setSyncInterval = 63;
        public static final int TRANSACTION_setSyncPeakTimings = 17;
        public static final int TRANSACTION_setSyncSchedules = 18;

        class Proxy implements IExchangeAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeString(str9);
                    parcelObtain.writeString(str10);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeString(str9);
                    parcelObtain.writeString(str10);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeInt(i8);
                    parcelObtain.writeInt(i9);
                    parcelObtain.writeBoolean(z7);
                    parcelObtain.writeInt(i10);
                    parcelObtain.writeInt(i11);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str11);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(exchangeAccount, 0);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public String getAccountCertificatePassword(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account) parcelObtain2.readTypedObject(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public String getAccountEmailPassword(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public Account[] getAllEASAccounts(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account[]) parcelObtain2.createTypedArray(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public String getDeviceId(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IExchangeAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long setAccountCertificatePassword(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public long setAccountEmailPassword(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IExchangeAccountPolicy.DESCRIPTOR);
        }

        public static IExchangeAccountPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExchangeAccountPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IExchangeAccountPolicy)) ? new Proxy(iBinder) : (IExchangeAccountPolicy) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createAccount";
                case 2:
                    return "addNewAccount";
                case 3:
                    return "addNewAccount_ex";
                case 4:
                    return "setSSL";
                case 5:
                    return "setAcceptAllCertificates";
                case 6:
                    return "setAlwaysVibrateOnEmailNotification";
                case 7:
                    return "setPassword";
                case 8:
                    return "setSignature";
                case 9:
                    return "setClientAuthCert";
                case 10:
                    return "setPastDaysToSync";
                case 11:
                    return "setAsDefaultAccount";
                case 12:
                    return "setAccountName";
                case 13:
                    return "getAccountId";
                case 14:
                    return "getAccountDetails";
                case 15:
                    return "deleteAccount";
                case 16:
                    return "sendAccountsChangedBroadcast";
                case 17:
                    return "setSyncPeakTimings";
                case 18:
                    return "setSyncSchedules";
                case 19:
                    return "setDataSyncs";
                case 20:
                    return "getAllEASAccounts";
                case 21:
                    return "getDeviceId";
                case 22:
                    return "removePendingAccount";
                case 23:
                    return "setRequireSignedSMIMEMessages";
                case 24:
                    return "getRequireSignedSMIMEMessages";
                case 25:
                    return "setRequireEncryptedSMIMEMessages";
                case 26:
                    return "getRequireEncryptedSMIMEMessages";
                case 27:
                    return "setForceSMIMECertificate";
                case 28:
                    return "getForceSMIMECertificate";
                case 29:
                    return "setReleaseSMIMECertificate";
                case 30:
                    return "allowInComingAttachments";
                case 31:
                    return "isIncomingAttachmentsAllowed";
                case 32:
                    return "setIncomingAttachmentsSize";
                case 33:
                    return "getIncomingAttachmentsSize";
                case 34:
                    return "setEmailNotificationsState";
                case 35:
                    return "isEmailNotificationsEnabled";
                case 36:
                    return "allowEmailSettingsChange";
                case 37:
                    return "isEmailSettingsChangeAllowed";
                case 38:
                    return "setMaxCalendarAgeFilter";
                case 39:
                    return "getMaxCalendarAgeFilter";
                case 40:
                    return "setMaxEmailAgeFilter";
                case 41:
                    return "getMaxEmailAgeFilter";
                case 42:
                    return "setMaxEmailBodyTruncationSize";
                case 43:
                    return "getMaxEmailBodyTruncationSize";
                case 44:
                    return "setMaxEmailHTMLBodyTruncationSize";
                case 45:
                    return "getMaxEmailHTMLBodyTruncationSize";
                case 46:
                    return "setForceSMIMECertificateForSigning";
                case 47:
                    return "getForceSMIMECertificateForSigning";
                case 48:
                    return "setReleaseSMIMECertificateForSigning";
                case 49:
                    return "setForceSMIMECertificateForEncryption";
                case 50:
                    return "getForceSMIMECertificateForEncryption";
                case 51:
                    return "setReleaseSMIMECertificateForEncryption";
                case 52:
                    return "addNewAccount_new";
                case 53:
                    return "getAccountEmailPassword";
                case 54:
                    return "getAccountCertificatePassword";
                case 55:
                    return "setAccountEmailPassword";
                case 56:
                    return "setAccountCertificatePassword";
                case 57:
                    return "setForceSMIMECertificateAlias";
                case 58:
                    return "getSMIMECertificateAlias";
                case 59:
                    return "setAccountBaseParameters";
                case 60:
                    return "setProtocolVersion";
                case 61:
                    return "setSenderName";
                case 62:
                    return "setSilentVibrateOnEmailNotification";
                case 63:
                    return "setSyncInterval";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 62;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExchangeAccountPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExchangeAccountPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jCreateAccount = createAccount(contextInfo, string, string2, string3, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCreateAccount);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    String string13 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount = addNewAccount(contextInfo2, string6, string7, string8, string9, i3, i4, z, string10, string11, string12, z2, z3, string13, z4, z5, z6, string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    boolean z8 = parcel.readBoolean();
                    boolean z9 = parcel.readBoolean();
                    String string23 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount_ex = addNewAccount_ex(contextInfo3, string16, string17, string18, string19, i5, i6, z7, string20, string21, string22, z8, z9, string23, z10, z11, z12, string24, string25, i7, i8, i9, i10, i11, i12, i13, z13, i14, i15, bArrCreateByteArray, string26);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount_ex);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean ssl = setSSL(contextInfo4, z14, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ssl);
                    break;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean acceptAllCertificates = setAcceptAllCertificates(contextInfo5, z15, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acceptAllCertificates);
                    break;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z16 = parcel.readBoolean();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean alwaysVibrateOnEmailNotification = setAlwaysVibrateOnEmailNotification(contextInfo6, z16, j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysVibrateOnEmailNotification);
                    break;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string27 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean password = setPassword(contextInfo7, string27, j4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(password);
                    break;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string28 = parcel.readString();
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean signature = setSignature(contextInfo8, string28, j5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(signature);
                    break;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string29 = parcel.readString();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setClientAuthCert(contextInfo9, bArrCreateByteArray2, string29, j6);
                    parcel2.writeNoException();
                    break;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i16 = parcel.readInt();
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean pastDaysToSync = setPastDaysToSync(contextInfo10, i16, j7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pastDaysToSync);
                    break;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean asDefaultAccount = setAsDefaultAccount(contextInfo11, j8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asDefaultAccount);
                    break;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string30 = parcel.readString();
                    long j9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean accountName = setAccountName(contextInfo12, string30, j9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountName);
                    break;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountId = getAccountId(contextInfo13, string31, string32, string33);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountId);
                    break;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Account accountDetails = getAccountDetails(contextInfo14, j10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(accountDetails, 1);
                    break;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j11 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteAccount = deleteAccount(contextInfo15, j11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteAccount);
                    break;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAccountsChangedBroadcast(contextInfo16);
                    parcel2.writeNoException();
                    break;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    long j12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncPeakTimings = setSyncPeakTimings(contextInfo17, i17, i18, i19, j12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncPeakTimings);
                    break;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    long j13 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncSchedules = setSyncSchedules(contextInfo18, i20, i21, i22, j13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncSchedules);
                    break;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z17 = parcel.readBoolean();
                    boolean z18 = parcel.readBoolean();
                    boolean z19 = parcel.readBoolean();
                    boolean z20 = parcel.readBoolean();
                    long j14 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean dataSyncs = setDataSyncs(contextInfo19, z17, z18, z19, z20, j14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataSyncs);
                    break;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Account[] allEASAccounts = getAllEASAccounts(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allEASAccounts, 1);
                    break;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceId = getDeviceId(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    break;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePendingAccount(contextInfo22, string34, string35, string36, string37);
                    parcel2.writeNoException();
                    break;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j15 = parcel.readLong();
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requireSignedSMIMEMessages = setRequireSignedSMIMEMessages(contextInfo23, j15, z21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireSignedSMIMEMessages);
                    break;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j16 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean requireSignedSMIMEMessages2 = getRequireSignedSMIMEMessages(contextInfo24, j16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireSignedSMIMEMessages2);
                    break;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j17 = parcel.readLong();
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requireEncryptedSMIMEMessages = setRequireEncryptedSMIMEMessages(contextInfo25, j17, z22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireEncryptedSMIMEMessages);
                    break;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j18 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean requireEncryptedSMIMEMessages2 = getRequireEncryptedSMIMEMessages(contextInfo26, j18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireEncryptedSMIMEMessages2);
                    break;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j19 = parcel.readLong();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificate = setForceSMIMECertificate(contextInfo27, j19, string38, string39);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificate);
                    break;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j20 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificate2 = getForceSMIMECertificate(contextInfo28, j20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificate2);
                    break;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j21 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificate = setReleaseSMIMECertificate(contextInfo29, j21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificate);
                    break;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z23 = parcel.readBoolean();
                    long j22 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zAllowInComingAttachments = allowInComingAttachments(contextInfo30, z23, j22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowInComingAttachments);
                    break;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j23 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingAttachmentsAllowed = isIncomingAttachmentsAllowed(contextInfo31, j23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingAttachmentsAllowed);
                    break;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i23 = parcel.readInt();
                    long j24 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean incomingAttachmentsSize = setIncomingAttachmentsSize(contextInfo32, i23, j24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingAttachmentsSize);
                    break;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int incomingAttachmentsSize2 = getIncomingAttachmentsSize(contextInfo33, j25);
                    parcel2.writeNoException();
                    parcel2.writeInt(incomingAttachmentsSize2);
                    break;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j26 = parcel.readLong();
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emailNotificationsState = setEmailNotificationsState(contextInfo34, j26, z24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emailNotificationsState);
                    break;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j27 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmailNotificationsEnabled = isEmailNotificationsEnabled(contextInfo35, j27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmailNotificationsEnabled);
                    break;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j28 = parcel.readLong();
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowEmailSettingsChange = allowEmailSettingsChange(contextInfo36, j28, z25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowEmailSettingsChange);
                    break;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j29 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmailSettingsChangeAllowed = isEmailSettingsChangeAllowed(contextInfo37, j29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmailSettingsChangeAllowed);
                    break;
                case 38:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i24 = parcel.readInt();
                    long j30 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxCalendarAgeFilter = setMaxCalendarAgeFilter(contextInfo38, i24, j30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxCalendarAgeFilter);
                    break;
                case 39:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j31 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxCalendarAgeFilter2 = getMaxCalendarAgeFilter(contextInfo39, j31);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxCalendarAgeFilter2);
                    break;
                case 40:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i25 = parcel.readInt();
                    long j32 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailAgeFilter = setMaxEmailAgeFilter(contextInfo40, i25, j32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailAgeFilter);
                    break;
                case 41:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j33 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailAgeFilter2 = getMaxEmailAgeFilter(contextInfo41, j33);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailAgeFilter2);
                    break;
                case 42:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i26 = parcel.readInt();
                    long j34 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailBodyTruncationSize = setMaxEmailBodyTruncationSize(contextInfo42, i26, j34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailBodyTruncationSize);
                    break;
                case 43:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j35 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailBodyTruncationSize2 = getMaxEmailBodyTruncationSize(contextInfo43, j35);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailBodyTruncationSize2);
                    break;
                case 44:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i27 = parcel.readInt();
                    long j36 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailHTMLBodyTruncationSize = setMaxEmailHTMLBodyTruncationSize(contextInfo44, i27, j36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailHTMLBodyTruncationSize);
                    break;
                case 45:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j37 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailHTMLBodyTruncationSize2 = getMaxEmailHTMLBodyTruncationSize(contextInfo45, j37);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailHTMLBodyTruncationSize2);
                    break;
                case 46:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j38 = parcel.readLong();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificateForSigning = setForceSMIMECertificateForSigning(contextInfo46, j38, string40, string41);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificateForSigning);
                    break;
                case 47:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j39 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateForSigning2 = getForceSMIMECertificateForSigning(contextInfo47, j39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateForSigning2);
                    break;
                case 48:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j40 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificateForSigning = setReleaseSMIMECertificateForSigning(contextInfo48, j40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificateForSigning);
                    break;
                case 49:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j41 = parcel.readLong();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificateForEncryption = setForceSMIMECertificateForEncryption(contextInfo49, j41, string42, string43);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificateForEncryption);
                    break;
                case 50:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j42 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateForEncryption2 = getForceSMIMECertificateForEncryption(contextInfo50, j42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateForEncryption2);
                    break;
                case 51:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j43 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificateForEncryption = setReleaseSMIMECertificateForEncryption(contextInfo51, j43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificateForEncryption);
                    break;
                case 52:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ExchangeAccount exchangeAccount = (ExchangeAccount) parcel.readTypedObject(ExchangeAccount.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount_new = addNewAccount_new(contextInfo52, exchangeAccount);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount_new);
                    break;
                case 53:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j44 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String accountEmailPassword = getAccountEmailPassword(contextInfo53, j44);
                    parcel2.writeNoException();
                    parcel2.writeString(accountEmailPassword);
                    break;
                case 54:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j45 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String accountCertificatePassword = getAccountCertificatePassword(contextInfo54, j45);
                    parcel2.writeNoException();
                    parcel2.writeString(accountCertificatePassword);
                    break;
                case 55:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountEmailPassword2 = setAccountEmailPassword(contextInfo55, string44);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountEmailPassword2);
                    break;
                case 56:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountCertificatePassword2 = setAccountCertificatePassword(contextInfo56, string45);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountCertificatePassword2);
                    break;
                case 57:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j46 = parcel.readLong();
                    String string46 = parcel.readString();
                    String string47 = parcel.readString();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateAlias = setForceSMIMECertificateAlias(contextInfo57, j46, string46, string47, i28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateAlias);
                    break;
                case 58:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j47 = parcel.readLong();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String sMIMECertificateAlias = getSMIMECertificateAlias(contextInfo58, j47, i29);
                    parcel2.writeNoException();
                    parcel2.writeString(sMIMECertificateAlias);
                    break;
                case 59:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    long j48 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long accountBaseParameters = setAccountBaseParameters(contextInfo59, string48, string49, string50, string51, j48);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountBaseParameters);
                    break;
                case 60:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string52 = parcel.readString();
                    long j49 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean protocolVersion = setProtocolVersion(contextInfo60, string52, j49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(protocolVersion);
                    break;
                case 61:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string53 = parcel.readString();
                    long j50 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean senderName = setSenderName(contextInfo61, string53, j50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(senderName);
                    break;
                case 62:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z26 = parcel.readBoolean();
                    long j51 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean silentVibrateOnEmailNotification = setSilentVibrateOnEmailNotification(contextInfo62, z26, j51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(silentVibrateOnEmailNotification);
                    break;
                case 63:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i30 = parcel.readInt();
                    long j52 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncInterval = setSyncInterval(contextInfo63, i30, j52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncInterval);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IExchangeAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public String getAccountCertificatePassword(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public String getAccountEmailPassword(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public Account[] getAllEASAccounts(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public String getDeviceId(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long setAccountCertificatePassword(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public long setAccountEmailPassword(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) throws RemoteException {
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) throws RemoteException {
        }
    }
}
