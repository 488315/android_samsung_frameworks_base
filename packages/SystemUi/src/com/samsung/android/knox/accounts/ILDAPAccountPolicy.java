package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ILDAPAccountPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.ILDAPAccountPolicy";

    void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount);

    boolean deleteLDAPAccount(ContextInfo contextInfo, long j);

    List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo);

    LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ILDAPAccountPolicy {
        public static final int TRANSACTION_createLDAPAccount = 1;
        public static final int TRANSACTION_deleteLDAPAccount = 2;
        public static final int TRANSACTION_getAllLDAPAccounts = 4;
        public static final int TRANSACTION_getLDAPAccount = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ILDAPAccountPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public final void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean deleteLDAPAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final String getInterfaceDescriptor() {
                return ILDAPAccountPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
            public final LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

        public final int getMaxTransactionId() {
            return 3;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ILDAPAccountPolicy {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public final boolean deleteLDAPAccount(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public final List<LDAPAccount> getAllLDAPAccounts(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public final LDAPAccount getLDAPAccount(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.ILDAPAccountPolicy
        public final void createLDAPAccount(ContextInfo contextInfo, LDAPAccount lDAPAccount) {
        }
    }
}
