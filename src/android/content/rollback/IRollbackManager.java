package android.content.rollback;

import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRollbackManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.rollback.IRollbackManager";

    public static class Default implements IRollbackManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.rollback.IRollbackManager
        public void blockRollbackManager(long j) throws RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void commitRollback(int i, ParceledListSlice parceledListSlice, String str, IntentSender intentSender) throws RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void expireRollbackForPackage(String str) throws RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public ParceledListSlice getAvailableRollbacks() throws RemoteException {
            return null;
        }

        @Override // android.content.rollback.IRollbackManager
        public ParceledListSlice getRecentlyCommittedRollbacks() throws RemoteException {
            return null;
        }

        @Override // android.content.rollback.IRollbackManager
        public int notifyStagedSession(int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.rollback.IRollbackManager
        public void reloadPersistedData() throws RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void snapshotAndRestoreUserData(String str, int[] iArr, int i, long j, String str2, int i2) throws RemoteException {
        }
    }

    void blockRollbackManager(long j) throws RemoteException;

    void commitRollback(int i, ParceledListSlice parceledListSlice, String str, IntentSender intentSender) throws RemoteException;

    void expireRollbackForPackage(String str) throws RemoteException;

    ParceledListSlice getAvailableRollbacks() throws RemoteException;

    ParceledListSlice getRecentlyCommittedRollbacks() throws RemoteException;

    int notifyStagedSession(int i) throws RemoteException;

    void reloadPersistedData() throws RemoteException;

    void snapshotAndRestoreUserData(String str, int[] iArr, int i, long j, String str2, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IRollbackManager {
        static final int TRANSACTION_blockRollbackManager = 8;
        static final int TRANSACTION_commitRollback = 3;
        static final int TRANSACTION_expireRollbackForPackage = 6;
        static final int TRANSACTION_getAvailableRollbacks = 1;
        static final int TRANSACTION_getRecentlyCommittedRollbacks = 2;
        static final int TRANSACTION_notifyStagedSession = 7;
        static final int TRANSACTION_reloadPersistedData = 5;
        static final int TRANSACTION_snapshotAndRestoreUserData = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IRollbackManager.DESCRIPTOR);
        }

        public static IRollbackManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRollbackManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRollbackManager)) {
                return (IRollbackManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAvailableRollbacks";
                case 2:
                    return "getRecentlyCommittedRollbacks";
                case 3:
                    return "commitRollback";
                case 4:
                    return "snapshotAndRestoreUserData";
                case 5:
                    return "reloadPersistedData";
                case 6:
                    return "expireRollbackForPackage";
                case 7:
                    return "notifyStagedSession";
                case 8:
                    return "blockRollbackManager";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRollbackManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRollbackManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParceledListSlice availableRollbacks = getAvailableRollbacks();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableRollbacks, 1);
                    return true;
                case 2:
                    ParceledListSlice recentlyCommittedRollbacks = getRecentlyCommittedRollbacks();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentlyCommittedRollbacks, 1);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    String readString = parcel.readString();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitRollback(readInt, parceledListSlice, readString, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    snapshotAndRestoreUserData(readString2, createIntArray, readInt2, readLong, readString3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    reloadPersistedData();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    expireRollbackForPackage(readString4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notifyStagedSession = notifyStagedSession(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(notifyStagedSession);
                    return true;
                case 8:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    blockRollbackManager(readLong2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRollbackManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRollbackManager.DESCRIPTOR;
            }

            @Override // android.content.rollback.IRollbackManager
            public ParceledListSlice getAvailableRollbacks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public ParceledListSlice getRecentlyCommittedRollbacks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void commitRollback(int i, ParceledListSlice parceledListSlice, String str, IntentSender intentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void snapshotAndRestoreUserData(String str, int[] iArr, int i, long j, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void reloadPersistedData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void expireRollbackForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public int notifyStagedSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void blockRollbackManager(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRollbackManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
