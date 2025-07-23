package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ILDAPAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.ILDAPAccountPolicy";

    void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount) throws RemoteException;

    boolean deleteLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException;

    List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo) throws RemoteException;

    LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ILDAPAccountPolicy {
        public static final int TRANSACTION_createLDAPAccount = 1;
        public static final int TRANSACTION_deleteLDAPAccount = 2;
        public static final int TRANSACTION_getAllLDAPAccounts = 4;
        public static final int TRANSACTION_getLDAPAccount = 3;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ILDAPAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILDAPAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(lDAPAccount, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public boolean deleteLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILDAPAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILDAPAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(LDAPAccount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ILDAPAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILDAPAccountPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LDAPAccount) obtain2.readTypedObject(LDAPAccount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ILDAPAccountPolicy.DESCRIPTOR);
        }

        public static ILDAPAccountPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILDAPAccountPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ILDAPAccountPolicy)) ? new Proxy(iBinder) : (ILDAPAccountPolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createLDAPAccount";
            }
            if (i == 2) {
                return "deleteLDAPAccount";
            }
            if (i == 3) {
                return "getLDAPAccount";
            }
            if (i != 4) {
                return null;
            }
            return "getAllLDAPAccounts";
        }

        public int getMaxTransactionId() {
            return 3;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILDAPAccountPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILDAPAccountPolicy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                LDAPAccount lDAPAccount = (LDAPAccount) parcel.readTypedObject(LDAPAccount.CREATOR);
                parcel.enforceNoDataAvail();
                createLDAPAccount(contextInfo, lDAPAccount);
                parcel2.writeNoException();
            } else if (i == 2) {
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                boolean deleteLDAPAccount = deleteLDAPAccount(contextInfo2, readLong);
                parcel2.writeNoException();
                parcel2.writeBoolean(deleteLDAPAccount);
            } else if (i == 3) {
                ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                LDAPAccount lDAPAccount2 = getLDAPAccount(contextInfo3, readLong2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(lDAPAccount2, 1);
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                parcel.enforceNoDataAvail();
                List<LDAPAccount> allLDAPAccounts = getAllLDAPAccounts(contextInfo4);
                parcel2.writeNoException();
                parcel2.writeTypedList(allLDAPAccounts, 1);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ILDAPAccountPolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public boolean deleteLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount) throws RemoteException {
        }
    }
}
