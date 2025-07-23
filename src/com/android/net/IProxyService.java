package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IProxyService extends IInterface {
    public static final String DESCRIPTOR = "com.android.net.IProxyService";

    public static class Default implements IProxyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.net.IProxyService
        public int getProxyPortForProfile(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.net.IProxyService
        public String getProxythreadStatus(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public void handleScreenunlock() throws RemoteException {
        }

        @Override // com.android.net.IProxyService
        public boolean isProxyThreadAlive(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public boolean isProxyThreadRunning(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public void resetInterface(String str) throws RemoteException {
        }

        @Override // com.android.net.IProxyService
        public String resolvePacFile(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.net.IProxyService
        public boolean setMiscValueForPacProfile(int i, String str, String str2, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public void setPacFile(String str) throws RemoteException {
        }

        @Override // com.android.net.IProxyService
        public boolean setPacFileForKnoxProfile(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public boolean startPacSystemForKnoxProfile(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public int startProxyServerForKnoxProfile(String str, int i, String str2, String str3, boolean z, String str4, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.net.IProxyService
        public boolean stopPacSystemForKnoxProfile(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.net.IProxyService
        public void stopProxyServerForKnoxProfile(String str) throws RemoteException {
        }
    }

    int getProxyPortForProfile(String str) throws RemoteException;

    String getProxythreadStatus(String str) throws RemoteException;

    void handleScreenunlock() throws RemoteException;

    boolean isProxyThreadAlive(String str) throws RemoteException;

    boolean isProxyThreadRunning(String str) throws RemoteException;

    void resetInterface(String str) throws RemoteException;

    String resolvePacFile(String str, String str2) throws RemoteException;

    boolean setMiscValueForPacProfile(int i, String str, String str2, int i2) throws RemoteException;

    void setPacFile(String str) throws RemoteException;

    boolean setPacFileForKnoxProfile(String str, String str2) throws RemoteException;

    boolean startPacSystemForKnoxProfile(String str) throws RemoteException;

    int startProxyServerForKnoxProfile(String str, int i, String str2, String str3, boolean z, String str4, int i2) throws RemoteException;

    boolean stopPacSystemForKnoxProfile(String str) throws RemoteException;

    void stopProxyServerForKnoxProfile(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyService {
        static final int TRANSACTION_getProxyPortForProfile = 9;
        static final int TRANSACTION_getProxythreadStatus = 13;
        static final int TRANSACTION_handleScreenunlock = 10;
        static final int TRANSACTION_isProxyThreadAlive = 14;
        static final int TRANSACTION_isProxyThreadRunning = 12;
        static final int TRANSACTION_resetInterface = 11;
        static final int TRANSACTION_resolvePacFile = 1;
        static final int TRANSACTION_setMiscValueForPacProfile = 8;
        static final int TRANSACTION_setPacFile = 2;
        static final int TRANSACTION_setPacFileForKnoxProfile = 7;
        static final int TRANSACTION_startPacSystemForKnoxProfile = 3;
        static final int TRANSACTION_startProxyServerForKnoxProfile = 5;
        static final int TRANSACTION_stopPacSystemForKnoxProfile = 4;
        static final int TRANSACTION_stopProxyServerForKnoxProfile = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, "com.android.net.IProxyService");
        }

        public static IProxyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.net.IProxyService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProxyService)) {
                return (IProxyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "resolvePacFile";
                case 2:
                    return "setPacFile";
                case 3:
                    return "startPacSystemForKnoxProfile";
                case 4:
                    return "stopPacSystemForKnoxProfile";
                case 5:
                    return "startProxyServerForKnoxProfile";
                case 6:
                    return "stopProxyServerForKnoxProfile";
                case 7:
                    return "setPacFileForKnoxProfile";
                case 8:
                    return "setMiscValueForPacProfile";
                case 9:
                    return "getProxyPortForProfile";
                case 10:
                    return "handleScreenunlock";
                case 11:
                    return "resetInterface";
                case 12:
                    return "isProxyThreadRunning";
                case 13:
                    return "getProxythreadStatus";
                case 14:
                    return "isProxyThreadAlive";
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
                parcel.enforceInterface("com.android.net.IProxyService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.net.IProxyService");
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String resolvePacFile = resolvePacFile(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(resolvePacFile);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPacFile(readString3);
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startPacSystemForKnoxProfile = startPacSystemForKnoxProfile(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startPacSystemForKnoxProfile);
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean stopPacSystemForKnoxProfile = stopPacSystemForKnoxProfile(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopPacSystemForKnoxProfile);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    String readString9 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startProxyServerForKnoxProfile = startProxyServerForKnoxProfile(readString6, readInt, readString7, readString8, readBoolean, readString9, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startProxyServerForKnoxProfile);
                    return true;
                case 6:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopProxyServerForKnoxProfile(readString10);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean pacFileForKnoxProfile = setPacFileForKnoxProfile(readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pacFileForKnoxProfile);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean miscValueForPacProfile = setMiscValueForPacProfile(readInt3, readString13, readString14, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(miscValueForPacProfile);
                    return true;
                case 9:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int proxyPortForProfile = getProxyPortForProfile(readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(proxyPortForProfile);
                    return true;
                case 10:
                    handleScreenunlock();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetInterface(readString16);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProxyThreadRunning = isProxyThreadRunning(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxyThreadRunning);
                    return true;
                case 13:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String proxythreadStatus = getProxythreadStatus(readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(proxythreadStatus);
                    return true;
                case 14:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProxyThreadAlive = isProxyThreadAlive(readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProxyThreadAlive);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IProxyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "com.android.net.IProxyService";
            }

            @Override // com.android.net.IProxyService
            public String resolvePacFile(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void setPacFile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean startPacSystemForKnoxProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean stopPacSystemForKnoxProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int startProxyServerForKnoxProfile(String str, int i, String str2, String str3, boolean z, String str4, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void stopProxyServerForKnoxProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setPacFileForKnoxProfile(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setMiscValueForPacProfile(int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int getProxyPortForProfile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void handleScreenunlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void resetInterface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadRunning(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public String getProxythreadStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadAlive(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.net.IProxyService");
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
