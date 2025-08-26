package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IEmailAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IEmailAccountPolicy";

    long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) throws RemoteException;

    long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) throws RemoteException;

    long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) throws RemoteException;

    boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException;

    Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException;

    long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    Account[] getAllEmailAccounts(ContextInfo contextInfo) throws RemoteException;

    String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) throws RemoteException;

    String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) throws RemoteException;

    void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException;

    boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException;

    long setEmailAddress(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    long setInComingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException;

    long setInComingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException;

    long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException;

    boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) throws RemoteException;

    long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException;

    boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException;

    boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException;

    public abstract class Stub extends Binder implements IEmailAccountPolicy {
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

        class Proxy implements IEmailAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeString(str9);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeString(str9);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    parcelObtain.writeBoolean(z5);
                    parcelObtain.writeBoolean(z6);
                    parcelObtain.writeString(str10);
                    parcelObtain.writeBoolean(z7);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(emailAccount, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account) parcelObtain2.readTypedObject(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public Account[] getAllEmailAccounts(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Account[]) parcelObtain2.createTypedArray(Account.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEmailAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setEmailAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setInComingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setInComingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
            public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEmailAccountPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEmailAccountPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEmailAccountPolicy)) ? new Proxy(iBinder) : (IEmailAccountPolicy) iInterfaceQueryLocalInterface;
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

        public int getMaxTransactionId() {
            return 36;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount = addNewAccount(contextInfo, string, string2, string3, i3, string4, string5, string6, string7, i4, string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i5 = parcel.readInt();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    int i6 = parcel.readInt();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    String string19 = parcel.readString();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount_ex = addNewAccount_ex(contextInfo2, string10, string11, string12, i5, string13, string14, string15, string16, i6, string17, string18, z, z2, z3, z4, z5, z6, string19, z7);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount_ex);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean accountName = setAccountName(contextInfo3, string20, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(accountName);
                    break;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean senderName = setSenderName(contextInfo4, string21, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(senderName);
                    break;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string22 = parcel.readString();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean signature = setSignature(contextInfo5, string22, j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(signature);
                    break;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean alwaysVibrateOnEmailNotification = setAlwaysVibrateOnEmailNotification(contextInfo6, z8, j4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysVibrateOnEmailNotification);
                    break;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingProtocol = setInComingProtocol(contextInfo7, string23, j5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingProtocol);
                    break;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string24 = parcel.readString();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long inComingServerAddress = setInComingServerAddress(contextInfo8, string24, j6);
                    parcel2.writeNoException();
                    parcel2.writeLong(inComingServerAddress);
                    break;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPort = setInComingServerPort(contextInfo9, i7, j7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPort);
                    break;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerSSL = setInComingServerSSL(contextInfo10, z9, j8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerSSL);
                    break;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    long j9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerAcceptAllCertificates = setInComingServerAcceptAllCertificates(contextInfo11, z10, j9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerAcceptAllCertificates);
                    break;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string25 = parcel.readString();
                    long j10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPassword = setInComingServerPassword(contextInfo12, string25, j10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPassword);
                    break;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string26 = parcel.readString();
                    long j11 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long outGoingServerAddress = setOutGoingServerAddress(contextInfo13, string26, j11);
                    parcel2.writeNoException();
                    parcel2.writeLong(outGoingServerAddress);
                    break;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i8 = parcel.readInt();
                    long j12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPort = setOutGoingServerPort(contextInfo14, i8, j12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPort);
                    break;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    long j13 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerSSL = setOutGoingServerSSL(contextInfo15, z11, j13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerSSL);
                    break;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    long j14 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerAcceptAllCertificates = setOutGoingServerAcceptAllCertificates(contextInfo16, z12, j14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerAcceptAllCertificates);
                    break;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string27 = parcel.readString();
                    long j15 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPassword = setOutGoingServerPassword(contextInfo17, string27, j15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPassword);
                    break;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j16 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean asDefaultAccount = setAsDefaultAccount(contextInfo18, j16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(asDefaultAccount);
                    break;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long accountId = getAccountId(contextInfo19, string28, string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeLong(accountId);
                    break;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j17 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Account accountDetails = getAccountDetails(contextInfo20, j17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(accountDetails, 1);
                    break;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j18 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteAccount = deleteAccount(contextInfo21, j18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteAccount);
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
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePendingAccount(contextInfo24, string31, string32, string33);
                    parcel2.writeNoException();
                    break;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    EmailAccount emailAccount = (EmailAccount) parcel.readTypedObject(EmailAccount.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAddNewAccount_new = addNewAccount_new(contextInfo25, emailAccount);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddNewAccount_new);
                    break;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j19 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String securityOutGoingServerPassword = getSecurityOutGoingServerPassword(contextInfo26, j19);
                    parcel2.writeNoException();
                    parcel2.writeString(securityOutGoingServerPassword);
                    break;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j20 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String securityInComingServerPassword = getSecurityInComingServerPassword(contextInfo27, j20);
                    parcel2.writeNoException();
                    parcel2.writeString(securityInComingServerPassword);
                    break;
                case 28:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long securityOutGoingServerPassword2 = setSecurityOutGoingServerPassword(contextInfo28, string34);
                    parcel2.writeNoException();
                    parcel2.writeLong(securityOutGoingServerPassword2);
                    break;
                case 29:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long securityInComingServerPassword2 = setSecurityInComingServerPassword(contextInfo29, string35);
                    parcel2.writeNoException();
                    parcel2.writeLong(securityInComingServerPassword2);
                    break;
                case 30:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string36 = parcel.readString();
                    long j21 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long emailAddress = setEmailAddress(contextInfo30, string36, j21);
                    parcel2.writeNoException();
                    parcel2.writeLong(emailAddress);
                    break;
                case 31:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string37 = parcel.readString();
                    long j22 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long inComingServerLogin = setInComingServerLogin(contextInfo31, string37, j22);
                    parcel2.writeNoException();
                    parcel2.writeLong(inComingServerLogin);
                    break;
                case 32:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string38 = parcel.readString();
                    long j23 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean inComingServerPathPrefix = setInComingServerPathPrefix(contextInfo32, string38, j23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inComingServerPathPrefix);
                    break;
                case 33:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string39 = parcel.readString();
                    long j24 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingProtocol = setOutGoingProtocol(contextInfo33, string39, j24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingProtocol);
                    break;
                case 34:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string40 = parcel.readString();
                    long j25 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long outGoingServerLogin = setOutGoingServerLogin(contextInfo34, string40, j25);
                    parcel2.writeNoException();
                    parcel2.writeLong(outGoingServerLogin);
                    break;
                case 35:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string41 = parcel.readString();
                    long j26 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean outGoingServerPathPrefix = setOutGoingServerPathPrefix(contextInfo35, string41, j26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outGoingServerPathPrefix);
                    break;
                case 36:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    long j27 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean silentVibrateOnEmailNotification = setSilentVibrateOnEmailNotification(contextInfo36, z13, j27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(silentVibrateOnEmailNotification);
                    break;
                case 37:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i9 = parcel.readInt();
                    long j28 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean syncInterval = setSyncInterval(contextInfo37, i9, j28);
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

    public class Default implements IEmailAccountPolicy {
        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long addNewAccount(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long addNewAccount_ex(ContextInfo contextInfo, String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, int i2, String str8, String str9, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str10, boolean z7) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long addNewAccount_new(ContextInfo contextInfo, EmailAccount emailAccount) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean deleteAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public Account getAccountDetails(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public Account[] getAllEmailAccounts(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public String getSecurityInComingServerPassword(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public String getSecurityOutGoingServerPassword(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setAccountName(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setAlwaysVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setAsDefaultAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setEmailAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setInComingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setInComingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setInComingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingProtocol(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingServerAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setOutGoingServerAddress(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setOutGoingServerLogin(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingServerPassword(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingServerPathPrefix(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingServerPort(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setOutGoingServerSSL(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setSecurityInComingServerPassword(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public long setSecurityOutGoingServerPassword(ContextInfo contextInfo, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setSenderName(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setSignature(ContextInfo contextInfo, String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setSilentVibrateOnEmailNotification(ContextInfo contextInfo, boolean z, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public boolean setSyncInterval(ContextInfo contextInfo, int i, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public void sendAccountsChangedBroadcast(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.accounts.IEmailAccountPolicy
        public void removePendingAccount(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
        }
    }
}
