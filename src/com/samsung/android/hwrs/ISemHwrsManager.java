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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemHwrsManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemHwrsManager)) {
                return (ISemHwrsManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddShare = addShare(string, string2, string3, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddShare);
                    return true;
                case 2:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddUser = addUser(string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddUser);
                    return true;
                case 3:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteUser = deleteUser(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteUser);
                    return true;
                case 4:
                    boolean zStartKsmbdServer = startKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartKsmbdServer);
                    return true;
                case 5:
                    boolean zStopKsmbdServer = stopKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopKsmbdServer);
                    return true;
                case 6:
                    boolean zRestartKsmbdServer = restartKsmbdServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRestartKsmbdServer);
                    return true;
                case 7:
                    boolean zReloadKmbdServerConfiguration = reloadKmbdServerConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReloadKmbdServerConfiguration);
                    return true;
                case 8:
                    boolean zKsmbdServerCleanup = ksmbdServerCleanup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zKsmbdServerCleanup);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean addUser(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean deleteUser(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean startKsmbdServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean stopKsmbdServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean restartKsmbdServer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean reloadKmbdServerConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public boolean ksmbdServerCleanup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hwrs.ISemHwrsManager
            public String getKsmbdServerStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHwrsManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
