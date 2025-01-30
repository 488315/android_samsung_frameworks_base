package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IExchangeAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IExchangeAccountPolicy";

    long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10);

    long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11);

    long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount);

    boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z);

    boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j);

    long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5);

    boolean deleteAccount(ContextInfo contextInfo, long j);

    String getAccountCertificatePassword(ContextInfo contextInfo, long j);

    Account getAccountDetails(ContextInfo contextInfo, long j);

    String getAccountEmailPassword(ContextInfo contextInfo, long j);

    long getAccountId(ContextInfo contextInfo, String str, String str2, String str3);

    Account[] getAllEASAccounts(ContextInfo contextInfo);

    String getDeviceId(ContextInfo contextInfo);

    boolean getForceSMIMECertificate(ContextInfo contextInfo, long j);

    boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j);

    boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j);

    int getIncomingAttachmentsSize(ContextInfo contextInfo, long j);

    int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j);

    int getMaxEmailAgeFilter(ContextInfo contextInfo, long j);

    int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j);

    int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j);

    boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j);

    boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j);

    String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i);

    boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j);

    boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j);

    boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j);

    void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4);

    void sendAccountsChangedBroadcast(ContextInfo contextInfo);

    boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j);

    long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j);

    long setAccountCertificatePassword(ContextInfo contextInfo, String str);

    long setAccountEmailPassword(ContextInfo contextInfo, String str);

    boolean setAccountName(ContextInfo contextInfo, String str, long j);

    boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j);

    boolean setAsDefaultAccount(ContextInfo contextInfo, long j);

    void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j);

    boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j);

    boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z);

    int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2);

    boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i);

    int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2);

    int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2);

    boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j);

    boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j);

    boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j);

    boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j);

    boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j);

    boolean setPassword(ContextInfo contextInfo, String str, long j);

    boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j);

    boolean setProtocolVersion(ContextInfo contextInfo, String str, long j);

    boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j);

    boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j);

    boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j);

    boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z);

    boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z);

    boolean setSSL(ContextInfo contextInfo, boolean z, long j);

    boolean setSenderName(ContextInfo contextInfo, String str, long j);

    boolean setSignature(ContextInfo contextInfo, String str, long j);

    boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j);

    boolean setSyncInterval(ContextInfo contextInfo, int i, long j);

    boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j);

    boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IExchangeAccountPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IExchangeAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str8);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeString(str9);
                    obtain.writeString(str10);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str8);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeString(str9);
                    obtain.writeString(str10);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    obtain.writeInt(i9);
                    obtain.writeBoolean(z7);
                    obtain.writeInt(i10);
                    obtain.writeInt(i11);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str11);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(exchangeAccount, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean deleteAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final String getAccountCertificatePassword(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final Account getAccountDetails(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account) obtain2.readTypedObject(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final String getAccountEmailPassword(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final Account[] getAllEASAccounts(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account[]) obtain2.createTypedArray(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final String getDeviceId(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IExchangeAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeLong(j);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long setAccountCertificatePassword(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final long setAccountEmailPassword(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setPassword(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSSL(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
            public final boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExchangeAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IExchangeAccountPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IExchangeAccountPolicy)) ? new Proxy(iBinder) : (IExchangeAccountPolicy) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 62;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long createAccount = createAccount(contextInfo, readString, readString2, readString3, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeLong(createAccount);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    String readString13 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long addNewAccount = addNewAccount(contextInfo2, readString6, readString7, readString8, readString9, readInt, readInt2, readBoolean, readString10, readString11, readString12, readBoolean2, readBoolean3, readString13, readBoolean4, readBoolean5, readBoolean6, readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    String readString23 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long addNewAccount_ex = addNewAccount_ex(contextInfo3, readString16, readString17, readString18, readString19, readInt3, readInt4, readBoolean7, readString20, readString21, readString22, readBoolean8, readBoolean9, readString23, readBoolean10, readBoolean11, readBoolean12, readString24, readString25, readInt5, readInt6, readInt7, readInt8, readInt9, readInt10, readInt11, readBoolean13, readInt12, readInt13, createByteArray, readString26);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount_ex);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean ssl = setSSL(contextInfo4, readBoolean14, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ssl);
                    break;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean acceptAllCertificates = setAcceptAllCertificates(contextInfo5, readBoolean15, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acceptAllCertificates);
                    break;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean alwaysVibrateOnEmailNotification = setAlwaysVibrateOnEmailNotification(contextInfo6, readBoolean16, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysVibrateOnEmailNotification);
                    break;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString27 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean password = setPassword(contextInfo7, readString27, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(password);
                    break;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean signature = setSignature(contextInfo8, readString28, readLong5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(signature);
                    break;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString29 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setClientAuthCert(contextInfo9, createByteArray2, readString29, readLong6);
                    parcel2.writeNoException();
                    break;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt14 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean pastDaysToSync = setPastDaysToSync(contextInfo10, readInt14, readLong7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pastDaysToSync);
                    break;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean asDefaultAccount = setAsDefaultAccount(contextInfo11, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asDefaultAccount);
                    break;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString30 = parcel.readString();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean accountName = setAccountName(contextInfo12, readString30, readLong9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountName);
                    break;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountId = getAccountId(contextInfo13, readString31, readString32, readString33);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountId);
                    break;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Account accountDetails = getAccountDetails(contextInfo14, readLong10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(accountDetails, 1);
                    break;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong11 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean deleteAccount = deleteAccount(contextInfo15, readLong11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteAccount);
                    break;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAccountsChangedBroadcast(contextInfo16);
                    parcel2.writeNoException();
                    break;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncPeakTimings = setSyncPeakTimings(contextInfo17, readInt15, readInt16, readInt17, readLong12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncPeakTimings);
                    break;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    long readLong13 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncSchedules = setSyncSchedules(contextInfo18, readInt18, readInt19, readInt20, readLong13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncSchedules);
                    break;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean17 = parcel.readBoolean();
                    boolean readBoolean18 = parcel.readBoolean();
                    boolean readBoolean19 = parcel.readBoolean();
                    boolean readBoolean20 = parcel.readBoolean();
                    long readLong14 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean dataSyncs = setDataSyncs(contextInfo19, readBoolean17, readBoolean18, readBoolean19, readBoolean20, readLong14);
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
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePendingAccount(contextInfo22, readString34, readString35, readString36, readString37);
                    parcel2.writeNoException();
                    break;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong15 = parcel.readLong();
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requireSignedSMIMEMessages = setRequireSignedSMIMEMessages(contextInfo23, readLong15, readBoolean21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireSignedSMIMEMessages);
                    break;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong16 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean requireSignedSMIMEMessages2 = getRequireSignedSMIMEMessages(contextInfo24, readLong16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireSignedSMIMEMessages2);
                    break;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong17 = parcel.readLong();
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requireEncryptedSMIMEMessages = setRequireEncryptedSMIMEMessages(contextInfo25, readLong17, readBoolean22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireEncryptedSMIMEMessages);
                    break;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong18 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean requireEncryptedSMIMEMessages2 = getRequireEncryptedSMIMEMessages(contextInfo26, readLong18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireEncryptedSMIMEMessages2);
                    break;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong19 = parcel.readLong();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificate = setForceSMIMECertificate(contextInfo27, readLong19, readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificate);
                    break;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong20 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificate2 = getForceSMIMECertificate(contextInfo28, readLong20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificate2);
                    break;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong21 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificate = setReleaseSMIMECertificate(contextInfo29, readLong21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificate);
                    break;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean23 = parcel.readBoolean();
                    long readLong22 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean allowInComingAttachments = allowInComingAttachments(contextInfo30, readBoolean23, readLong22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowInComingAttachments);
                    break;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong23 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingAttachmentsAllowed = isIncomingAttachmentsAllowed(contextInfo31, readLong23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingAttachmentsAllowed);
                    break;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt21 = parcel.readInt();
                    long readLong24 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean incomingAttachmentsSize = setIncomingAttachmentsSize(contextInfo32, readInt21, readLong24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingAttachmentsSize);
                    break;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int incomingAttachmentsSize2 = getIncomingAttachmentsSize(contextInfo33, readLong25);
                    parcel2.writeNoException();
                    parcel2.writeInt(incomingAttachmentsSize2);
                    break;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong26 = parcel.readLong();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emailNotificationsState = setEmailNotificationsState(contextInfo34, readLong26, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emailNotificationsState);
                    break;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong27 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isEmailNotificationsEnabled = isEmailNotificationsEnabled(contextInfo35, readLong27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailNotificationsEnabled);
                    break;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong28 = parcel.readLong();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailSettingsChange = allowEmailSettingsChange(contextInfo36, readLong28, readBoolean25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailSettingsChange);
                    break;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong29 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isEmailSettingsChangeAllowed = isEmailSettingsChangeAllowed(contextInfo37, readLong29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailSettingsChangeAllowed);
                    break;
                case 38:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt22 = parcel.readInt();
                    long readLong30 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxCalendarAgeFilter = setMaxCalendarAgeFilter(contextInfo38, readInt22, readLong30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxCalendarAgeFilter);
                    break;
                case 39:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong31 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxCalendarAgeFilter2 = getMaxCalendarAgeFilter(contextInfo39, readLong31);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxCalendarAgeFilter2);
                    break;
                case 40:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt23 = parcel.readInt();
                    long readLong32 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailAgeFilter = setMaxEmailAgeFilter(contextInfo40, readInt23, readLong32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailAgeFilter);
                    break;
                case 41:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong33 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailAgeFilter2 = getMaxEmailAgeFilter(contextInfo41, readLong33);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailAgeFilter2);
                    break;
                case 42:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt24 = parcel.readInt();
                    long readLong34 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailBodyTruncationSize = setMaxEmailBodyTruncationSize(contextInfo42, readInt24, readLong34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailBodyTruncationSize);
                    break;
                case 43:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong35 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailBodyTruncationSize2 = getMaxEmailBodyTruncationSize(contextInfo43, readLong35);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailBodyTruncationSize2);
                    break;
                case 44:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt25 = parcel.readInt();
                    long readLong36 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean maxEmailHTMLBodyTruncationSize = setMaxEmailHTMLBodyTruncationSize(contextInfo44, readInt25, readLong36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxEmailHTMLBodyTruncationSize);
                    break;
                case 45:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong37 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int maxEmailHTMLBodyTruncationSize2 = getMaxEmailHTMLBodyTruncationSize(contextInfo45, readLong37);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxEmailHTMLBodyTruncationSize2);
                    break;
                case 46:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong38 = parcel.readLong();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificateForSigning = setForceSMIMECertificateForSigning(contextInfo46, readLong38, readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificateForSigning);
                    break;
                case 47:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong39 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateForSigning2 = getForceSMIMECertificateForSigning(contextInfo47, readLong39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateForSigning2);
                    break;
                case 48:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong40 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificateForSigning = setReleaseSMIMECertificateForSigning(contextInfo48, readLong40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificateForSigning);
                    break;
                case 49:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong41 = parcel.readLong();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int forceSMIMECertificateForEncryption = setForceSMIMECertificateForEncryption(contextInfo49, readLong41, readString42, readString43);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceSMIMECertificateForEncryption);
                    break;
                case 50:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong42 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateForEncryption2 = getForceSMIMECertificateForEncryption(contextInfo50, readLong42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateForEncryption2);
                    break;
                case 51:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong43 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean releaseSMIMECertificateForEncryption = setReleaseSMIMECertificateForEncryption(contextInfo51, readLong43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseSMIMECertificateForEncryption);
                    break;
                case 52:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ExchangeAccount exchangeAccount = (ExchangeAccount) parcel.readTypedObject(ExchangeAccount.CREATOR);
                    parcel.enforceNoDataAvail();
                    long addNewAccount_new = addNewAccount_new(contextInfo52, exchangeAccount);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount_new);
                    break;
                case 53:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong44 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String accountEmailPassword = getAccountEmailPassword(contextInfo53, readLong44);
                    parcel2.writeNoException();
                    parcel2.writeString(accountEmailPassword);
                    break;
                case 54:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong45 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String accountCertificatePassword = getAccountCertificatePassword(contextInfo54, readLong45);
                    parcel2.writeNoException();
                    parcel2.writeString(accountCertificatePassword);
                    break;
                case 55:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountEmailPassword2 = setAccountEmailPassword(contextInfo55, readString44);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountEmailPassword2);
                    break;
                case 56:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountCertificatePassword2 = setAccountCertificatePassword(contextInfo56, readString45);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountCertificatePassword2);
                    break;
                case 57:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong46 = parcel.readLong();
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean forceSMIMECertificateAlias = setForceSMIMECertificateAlias(contextInfo57, readLong46, readString46, readString47, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSMIMECertificateAlias);
                    break;
                case 58:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong47 = parcel.readLong();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String sMIMECertificateAlias = getSMIMECertificateAlias(contextInfo58, readLong47, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeString(sMIMECertificateAlias);
                    break;
                case 59:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    long readLong48 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long accountBaseParameters = setAccountBaseParameters(contextInfo59, readString48, readString49, readString50, readString51, readLong48);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountBaseParameters);
                    break;
                case 60:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString52 = parcel.readString();
                    long readLong49 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean protocolVersion = setProtocolVersion(contextInfo60, readString52, readLong49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(protocolVersion);
                    break;
                case 61:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString53 = parcel.readString();
                    long readLong50 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean senderName = setSenderName(contextInfo61, readString53, readLong50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(senderName);
                    break;
                case 62:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean26 = parcel.readBoolean();
                    long readLong51 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean silentVibrateOnEmailNotification = setSilentVibrateOnEmailNotification(contextInfo62, readBoolean26, readLong51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(silentVibrateOnEmailNotification);
                    break;
                case 63:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt28 = parcel.readInt();
                    long readLong52 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncInterval = setSyncInterval(contextInfo63, readInt28, readLong52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncInterval);
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IExchangeAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i, int i2, boolean z, String str5, String str6, String str7, boolean z2, boolean z3, String str8, boolean z4, boolean z5, boolean z6, String str9, String str10, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z7, int i10, int i11, byte[] bArr, String str11) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long createAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4, String str5) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean deleteAccount(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final String getAccountCertificatePassword(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final Account getAccountDetails(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final String getAccountEmailPassword(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final Account[] getAllEASAccounts(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final String getDeviceId(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long setAccountBaseParameters(ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long setAccountCertificatePassword(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final long setAccountEmailPassword(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setDataSyncs(ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int setForceSMIMECertificate(ContextInfo contextInfo, long j, String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setForceSMIMECertificateAlias(ContextInfo contextInfo, long j, String str, String str2, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int setForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j, String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final int setForceSMIMECertificateForSigning(ContextInfo contextInfo, long j, String str, String str2) {
            return 0;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setPassword(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSSL(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSyncPeakTimings(ContextInfo contextInfo, int i, int i2, int i3, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) {
        }

        @Override // com.samsung.android.knox.accounts.IExchangeAccountPolicy
        public final void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3, String str4) {
        }
    }
}
