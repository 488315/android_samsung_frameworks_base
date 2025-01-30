package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEmailAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IEmailAccountPolicy";

    long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9);

    long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7);

    long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount);

    boolean deleteAccount(ContextInfo contextInfo, long j);

    Account getAccountDetails(ContextInfo contextInfo, long j);

    long getAccountId(ContextInfo contextInfo, String str, String str2, String str3);

    Account[] getAllEmailAccounts(ContextInfo contextInfo);

    String getSecurityInComingServerPassword(ContextInfo contextInfo, long j);

    String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j);

    void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3);

    void sendAccountsChangedBroadcast(ContextInfo contextInfo);

    boolean setAccountName(ContextInfo contextInfo, String str, long j);

    boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j);

    boolean setAsDefaultAccount(ContextInfo contextInfo, long j);

    long setEmailAddress(ContextInfo contextInfo, String str, long j);

    boolean setInComingProtocol(ContextInfo contextInfo, String str, long j);

    boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j);

    long setInComingServerAddress(ContextInfo contextInfo, String str, long j);

    long setInComingServerLogin(ContextInfo contextInfo, String str, long j);

    boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j);

    boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j);

    boolean setInComingServerPort(ContextInfo contextInfo, int i, long j);

    boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j);

    boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j);

    boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j);

    long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j);

    long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j);

    boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j);

    boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j);

    boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j);

    boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j);

    long setSecurityInComingServerPassword(ContextInfo contextInfo, String str);

    long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str);

    boolean setSenderName(ContextInfo contextInfo, String str, long j);

    boolean setSignature(ContextInfo contextInfo, String str, long j);

    boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j);

    boolean setSyncInterval(ContextInfo contextInfo, int i, long j);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEmailAccountPolicy {
        public static final int TRANSACTION_addNewAccount = 1;
        public static final int TRANSACTION_addNewAccount_ex = 2;
        public static final int TRANSACTION_addNewAccount_new = 25;
        public static final int TRANSACTION_deleteAccount = 21;
        public static final int TRANSACTION_getAccountDetails = 20;
        public static final int TRANSACTION_getAccountId = 19;
        public static final int TRANSACTION_getAllEmailAccounts = 23;
        public static final int TRANSACTION_getSecurityInComingServerPassword = 27;
        public static final int TRANSACTION_getSecurityOutGoingServerPassword = 26;
        public static final int TRANSACTION_removePendingAccount = 24;
        public static final int TRANSACTION_sendAccountsChangedBroadcast = 22;
        public static final int TRANSACTION_setAccountName = 3;
        public static final int TRANSACTION_setAlwaysVibrateOnEmailNotification = 6;
        public static final int TRANSACTION_setAsDefaultAccount = 18;
        public static final int TRANSACTION_setEmailAddress = 30;
        public static final int TRANSACTION_setInComingProtocol = 7;
        public static final int TRANSACTION_setInComingServerAcceptAllCertificates = 11;
        public static final int TRANSACTION_setInComingServerAddress = 8;
        public static final int TRANSACTION_setInComingServerLogin = 31;
        public static final int TRANSACTION_setInComingServerPassword = 12;
        public static final int TRANSACTION_setInComingServerPathPrefix = 32;
        public static final int TRANSACTION_setInComingServerPort = 9;
        public static final int TRANSACTION_setInComingServerSSL = 10;
        public static final int TRANSACTION_setOutGoingProtocol = 33;
        public static final int TRANSACTION_setOutGoingServerAcceptAllCertificates = 16;
        public static final int TRANSACTION_setOutGoingServerAddress = 13;
        public static final int TRANSACTION_setOutGoingServerLogin = 34;
        public static final int TRANSACTION_setOutGoingServerPassword = 17;
        public static final int TRANSACTION_setOutGoingServerPathPrefix = 35;
        public static final int TRANSACTION_setOutGoingServerPort = 14;
        public static final int TRANSACTION_setOutGoingServerSSL = 15;
        public static final int TRANSACTION_setSecurityInComingServerPassword = 29;
        public static final int TRANSACTION_setSecurityOutGoingServerPassword = 28;
        public static final int TRANSACTION_setSenderName = 4;
        public static final int TRANSACTION_setSignature = 5;
        public static final int TRANSACTION_setSilentVibrateOnEmailNotification = 36;
        public static final int TRANSACTION_setSyncInterval = 37;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEmailAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeInt(i2);
                    obtain.writeString(str8);
                    obtain.writeString(str9);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeInt(i2);
                    obtain.writeString(str8);
                    obtain.writeString(str9);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeString(str10);
                    obtain.writeBoolean(z7);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(emailAccount, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean deleteAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final Account getAccountDetails(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account) obtain2.readTypedObject(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final Account[] getAllEmailAccounts(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Account[]) obtain2.createTypedArray(Account.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IEmailAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setEmailAddress(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setInComingServerAddress(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setInComingServerLogin(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEmailAccountPolicy.DESCRIPTOR);
        }

        public static IEmailAccountPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEmailAccountPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEmailAccountPolicy)) ? new Proxy(iBinder) : (IEmailAccountPolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addNewAccount";
                case 2:
                    return "addNewAccount_ex";
                case 3:
                    return "setAccountName";
                case 4:
                    return "setSenderName";
                case 5:
                    return "setSignature";
                case 6:
                    return "setAlwaysVibrateOnEmailNotification";
                case 7:
                    return "setInComingProtocol";
                case 8:
                    return "setInComingServerAddress";
                case 9:
                    return "setInComingServerPort";
                case 10:
                    return "setInComingServerSSL";
                case 11:
                    return "setInComingServerAcceptAllCertificates";
                case 12:
                    return "setInComingServerPassword";
                case 13:
                    return "setOutGoingServerAddress";
                case 14:
                    return "setOutGoingServerPort";
                case 15:
                    return "setOutGoingServerSSL";
                case 16:
                    return "setOutGoingServerAcceptAllCertificates";
                case 17:
                    return "setOutGoingServerPassword";
                case 18:
                    return "setAsDefaultAccount";
                case 19:
                    return "getAccountId";
                case 20:
                    return "getAccountDetails";
                case 21:
                    return "deleteAccount";
                case 22:
                    return "sendAccountsChangedBroadcast";
                case 23:
                    return "getAllEmailAccounts";
                case 24:
                    return "removePendingAccount";
                case 25:
                    return "addNewAccount_new";
                case 26:
                    return "getSecurityOutGoingServerPassword";
                case 27:
                    return "getSecurityInComingServerPassword";
                case 28:
                    return "setSecurityOutGoingServerPassword";
                case 29:
                    return "setSecurityInComingServerPassword";
                case 30:
                    return "setEmailAddress";
                case 31:
                    return "setInComingServerLogin";
                case 32:
                    return "setInComingServerPathPrefix";
                case 33:
                    return "setOutGoingProtocol";
                case 34:
                    return "setOutGoingServerLogin";
                case 35:
                    return "setOutGoingServerPathPrefix";
                case 36:
                    return "setSilentVibrateOnEmailNotification";
                case 37:
                    return "setSyncInterval";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 36;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEmailAccountPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEmailAccountPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long addNewAccount = addNewAccount(contextInfo, readString, readString2, readString3, readInt, readString4, readString5, readString6, readString7, readInt2, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    String readString19 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long addNewAccount_ex = addNewAccount_ex(contextInfo2, readString10, readString11, readString12, readInt3, readString13, readString14, readString15, readString16, readInt4, readString17, readString18, readBoolean, readBoolean2, readBoolean3, readBoolean4, readBoolean5, readBoolean6, readString19, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount_ex);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean accountName = setAccountName(contextInfo3, readString20, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountName);
                    break;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString21 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean senderName = setSenderName(contextInfo4, readString21, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(senderName);
                    break;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString22 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean signature = setSignature(contextInfo5, readString22, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(signature);
                    break;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean alwaysVibrateOnEmailNotification = setAlwaysVibrateOnEmailNotification(contextInfo6, readBoolean8, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysVibrateOnEmailNotification);
                    break;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingProtocol = setInComingProtocol(contextInfo7, readString23, readLong5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingProtocol);
                    break;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString24 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long inComingServerAddress = setInComingServerAddress(contextInfo8, readString24, readLong6);
                    parcel2.writeNoException();
                    parcel2.writeLong(inComingServerAddress);
                    break;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPort = setInComingServerPort(contextInfo9, readInt5, readLong7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPort);
                    break;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerSSL = setInComingServerSSL(contextInfo10, readBoolean9, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerSSL);
                    break;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerAcceptAllCertificates = setInComingServerAcceptAllCertificates(contextInfo11, readBoolean10, readLong9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerAcceptAllCertificates);
                    break;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPassword = setInComingServerPassword(contextInfo12, readString25, readLong10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPassword);
                    break;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString26 = parcel.readString();
                    long readLong11 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long outGoingServerAddress = setOutGoingServerAddress(contextInfo13, readString26, readLong11);
                    parcel2.writeNoException();
                    parcel2.writeLong(outGoingServerAddress);
                    break;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPort = setOutGoingServerPort(contextInfo14, readInt6, readLong12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPort);
                    break;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    long readLong13 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerSSL = setOutGoingServerSSL(contextInfo15, readBoolean11, readLong13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerSSL);
                    break;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    long readLong14 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerAcceptAllCertificates = setOutGoingServerAcceptAllCertificates(contextInfo16, readBoolean12, readLong14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerAcceptAllCertificates);
                    break;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString27 = parcel.readString();
                    long readLong15 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPassword = setOutGoingServerPassword(contextInfo17, readString27, readLong15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPassword);
                    break;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong16 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean asDefaultAccount = setAsDefaultAccount(contextInfo18, readLong16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asDefaultAccount);
                    break;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountId = getAccountId(contextInfo19, readString28, readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountId);
                    break;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong17 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Account accountDetails = getAccountDetails(contextInfo20, readLong17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(accountDetails, 1);
                    break;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong18 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean deleteAccount = deleteAccount(contextInfo21, readLong18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteAccount);
                    break;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAccountsChangedBroadcast(contextInfo22);
                    parcel2.writeNoException();
                    break;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Account[] allEmailAccounts = getAllEmailAccounts(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allEmailAccounts, 1);
                    break;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePendingAccount(contextInfo24, readString31, readString32, readString33);
                    parcel2.writeNoException();
                    break;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    EmailAccount emailAccount = (EmailAccount) parcel.readTypedObject(EmailAccount.CREATOR);
                    parcel.enforceNoDataAvail();
                    long addNewAccount_new = addNewAccount_new(contextInfo25, emailAccount);
                    parcel2.writeNoException();
                    parcel2.writeLong(addNewAccount_new);
                    break;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong19 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String securityOutGoingServerPassword = getSecurityOutGoingServerPassword(contextInfo26, readLong19);
                    parcel2.writeNoException();
                    parcel2.writeString(securityOutGoingServerPassword);
                    break;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong20 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String securityInComingServerPassword = getSecurityInComingServerPassword(contextInfo27, readLong20);
                    parcel2.writeNoException();
                    parcel2.writeString(securityInComingServerPassword);
                    break;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long securityOutGoingServerPassword2 = setSecurityOutGoingServerPassword(contextInfo28, readString34);
                    parcel2.writeNoException();
                    parcel2.writeLong(securityOutGoingServerPassword2);
                    break;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long securityInComingServerPassword2 = setSecurityInComingServerPassword(contextInfo29, readString35);
                    parcel2.writeNoException();
                    parcel2.writeLong(securityInComingServerPassword2);
                    break;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString36 = parcel.readString();
                    long readLong21 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long emailAddress = setEmailAddress(contextInfo30, readString36, readLong21);
                    parcel2.writeNoException();
                    parcel2.writeLong(emailAddress);
                    break;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString37 = parcel.readString();
                    long readLong22 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long inComingServerLogin = setInComingServerLogin(contextInfo31, readString37, readLong22);
                    parcel2.writeNoException();
                    parcel2.writeLong(inComingServerLogin);
                    break;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString38 = parcel.readString();
                    long readLong23 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPathPrefix = setInComingServerPathPrefix(contextInfo32, readString38, readLong23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPathPrefix);
                    break;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString39 = parcel.readString();
                    long readLong24 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingProtocol = setOutGoingProtocol(contextInfo33, readString39, readLong24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingProtocol);
                    break;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString40 = parcel.readString();
                    long readLong25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long outGoingServerLogin = setOutGoingServerLogin(contextInfo34, readString40, readLong25);
                    parcel2.writeNoException();
                    parcel2.writeLong(outGoingServerLogin);
                    break;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString41 = parcel.readString();
                    long readLong26 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPathPrefix = setOutGoingServerPathPrefix(contextInfo35, readString41, readLong26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPathPrefix);
                    break;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    long readLong27 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean silentVibrateOnEmailNotification = setSilentVibrateOnEmailNotification(contextInfo36, readBoolean13, readLong27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(silentVibrateOnEmailNotification);
                    break;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    long readLong28 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncInterval = setSyncInterval(contextInfo37, readInt7, readLong28);
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
    public static class Default implements IEmailAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) {
            return 0L;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean deleteAccount(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final Account getAccountDetails(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final Account[] getAllEmailAccounts(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setEmailAddress(ContextInfo contextInfo, String str, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setInComingServerAddress(ContextInfo contextInfo, String str, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setInComingServerLogin(ContextInfo contextInfo, String str, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public final void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) {
        }
    }
}
