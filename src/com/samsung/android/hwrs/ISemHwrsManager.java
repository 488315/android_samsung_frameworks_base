package com.samsung.android.hwrs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemHwrsManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hwrs.ISemHwrsManager";

    public static class Default implements ISemHwrsManager {
        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean addShare(String str, String str2, String str3, String str4, String str5) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean addUser(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean deleteUser(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public String getKsmbdServerStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean ksmbdServerCleanup() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean reloadKmbdServerConfiguration() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean restartKsmbdServer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean startKsmbdServer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hwrs.ISemHwrsManager
        public boolean stopKsmbdServer() throws RemoteException {
            return false;
        }
    }

    boolean addShare(String str, String str2, String str3, String str4, String str5) throws RemoteException;

    boolean addUser(String str, String str2) throws RemoteException;

    boolean deleteUser(String str) throws RemoteException;

    String getKsmbdServerStatus() throws RemoteException;

    boolean ksmbdServerCleanup() throws RemoteException;

    boolean reloadKmbdServerConfiguration() throws RemoteException;

    boolean restartKsmbdServer() throws RemoteException;

    boolean startKsmbdServer() throws RemoteException;

    boolean stopKsmbdServer() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemHwrsManager {
        static final int TRANSACTION_addShare = 1;
        static final int TRANSACTION_addUser = 2;
        static final int TRANSACTION_deleteUser = 3;
        static final int TRANSACTION_getKsmbdServerStatus = 9;
        static final int TRANSACTION_ksmbdServerCleanup = 8;
        static final int TRANSACTION_reloadKmbdServerConfiguration = 7;
        static final int TRANSACTION_restartKsmbdServer = 6;
        static final int TRANSACTION_startKsmbdServer = 4;
        static final int TRANSACTION_stopKsmbdServer = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISemHwrsManager.DESCRIPTOR);
        }

        public static ISemHwrsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemHwrsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemHwrsManager)) {
                return (ISemHwrsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addShare";
                case 2:
                    return "addUser";
                case 3:
                    return "deleteUser";
                case 4:
                    return "startKsmbdServer";
                case 5:
                    return "stopKsmbdServer";
                case 6:
                    return "restartKsmbdServer";
                case 7:
                    return "reloadKmbdServerConfiguration";
                case 8:
                    return "ksmbdServerCleanup";
                case 9:
                    return "getKsmbdServerStatus";
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
                parcel.enforceInterface(ISemHwrsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemHwrsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addShare = addShare(readString, readString2, readString3, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addShare);
                    return true;
                case 2:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addUser = addUser(readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addUser);
                    return true;
                case 3:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteUser = deleteUser(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteUser);
                    return true;
                case 4:
                    boolean startKsmbdServer = startKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startKsmbdServer);
                    return true;
                case 5:
                    boolean stopKsmbdServer = stopKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopKsmbdServer);
                    return true;
                case 6:
                    boolean restartKsmbdServer = restartKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(restartKsmbdServer);
                    return true;
                case 7:
                    boolean reloadKmbdServerConfiguration = reloadKmbdServerConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reloadKmbdServerConfiguration);
                    return true;
                case 8:
                    boolean ksmbdServerCleanup = ksmbdServerCleanup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ksmbdServerCleanup);
                    return true;
                case 9:
                    String ksmbdServerStatus = getKsmbdServerStatus();
                    parcel2.writeNoException();
                    parcel2.writeString(ksmbdServerStatus);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemHwrsManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemHwrsManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean addShare(String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean addUser(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean deleteUser(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean startKsmbdServer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean stopKsmbdServer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean restartKsmbdServer() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean reloadKmbdServerConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean ksmbdServerCleanup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public String getKsmbdServerStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
