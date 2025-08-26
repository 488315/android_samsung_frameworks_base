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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.net.IProxyService");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProxyService)) {
                return (IProxyService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strResolvePacFile = resolvePacFile(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeString(strResolvePacFile);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPacFile(string3);
                    return true;
                case 3:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStartPacSystemForKnoxProfile = startPacSystemForKnoxProfile(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartPacSystemForKnoxProfile);
                    return true;
                case 4:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStopPacSystemForKnoxProfile = stopPacSystemForKnoxProfile(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopPacSystemForKnoxProfile);
                    return true;
                case 5:
                    String string6 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    String string9 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartProxyServerForKnoxProfile = startProxyServerForKnoxProfile(string6, i3, string7, string8, z, string9, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartProxyServerForKnoxProfile);
                    return true;
                case 6:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopProxyServerForKnoxProfile(string10);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean pacFileForKnoxProfile = setPacFileForKnoxProfile(string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pacFileForKnoxProfile);
                    return true;
                case 8:
                    int i5 = parcel.readInt();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean miscValueForPacProfile = setMiscValueForPacProfile(i5, string13, string14, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(miscValueForPacProfile);
                    return true;
                case 9:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int proxyPortForProfile = getProxyPortForProfile(string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(proxyPortForProfile);
                    return true;
                case 10:
                    handleScreenunlock();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetInterface(string16);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProxyThreadRunning = isProxyThreadRunning(string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyThreadRunning);
                    return true;
                case 13:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String proxythreadStatus = getProxythreadStatus(string18);
                    parcel2.writeNoException();
                    parcel2.writeString(proxythreadStatus);
                    return true;
                case 14:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProxyThreadAlive = isProxyThreadAlive(string19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProxyThreadAlive);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void setPacFile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean startPacSystemForKnoxProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean stopPacSystemForKnoxProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int startProxyServerForKnoxProfile(String str, int i, String str2, String str3, boolean z, String str4, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void stopProxyServerForKnoxProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setPacFileForKnoxProfile(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean setMiscValueForPacProfile(int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public int getProxyPortForProfile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void handleScreenunlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public void resetInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadRunning(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public String getProxythreadStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.net.IProxyService
            public boolean isProxyThreadAlive(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.net.IProxyService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
