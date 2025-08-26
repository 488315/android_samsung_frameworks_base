package com.samsung.android.perfsdkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IPerfSDKService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.perfsdkservice.IPerfSDKService";

    public static class Default implements IPerfSDKService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int connectionRequest() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public String getAllowedPkgName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public String getChangedForegroundPackagename() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public String getForegroundPackagename() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int[] getHighBoostingLevel() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int[] getLowBoostingLevel() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int[] getThermalTable() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int initPerfSDK(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int removeSessionKey(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.perfsdkservice.IPerfSDKService
        public int setSessionKey(String str) throws RemoteException {
            return 0;
        }
    }

    int connectionRequest() throws RemoteException;

    String getAllowedPkgName() throws RemoteException;

    String getChangedForegroundPackagename() throws RemoteException;

    String getForegroundPackagename() throws RemoteException;

    int[] getHighBoostingLevel() throws RemoteException;

    int[] getLowBoostingLevel() throws RemoteException;

    int[] getThermalTable() throws RemoteException;

    int initPerfSDK(String str) throws RemoteException;

    int removeSessionKey(String str) throws RemoteException;

    int setSessionKey(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IPerfSDKService {
        static final int TRANSACTION_connectionRequest = 10;
        static final int TRANSACTION_getAllowedPkgName = 2;
        static final int TRANSACTION_getChangedForegroundPackagename = 7;
        static final int TRANSACTION_getForegroundPackagename = 6;
        static final int TRANSACTION_getHighBoostingLevel = 3;
        static final int TRANSACTION_getLowBoostingLevel = 4;
        static final int TRANSACTION_getThermalTable = 5;
        static final int TRANSACTION_initPerfSDK = 1;
        static final int TRANSACTION_removeSessionKey = 9;
        static final int TRANSACTION_setSessionKey = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IPerfSDKService.DESCRIPTOR);
        }

        public static IPerfSDKService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPerfSDKService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPerfSDKService)) {
                return (IPerfSDKService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initPerfSDK";
                case 2:
                    return "getAllowedPkgName";
                case 3:
                    return "getHighBoostingLevel";
                case 4:
                    return "getLowBoostingLevel";
                case 5:
                    return "getThermalTable";
                case 6:
                    return "getForegroundPackagename";
                case 7:
                    return "getChangedForegroundPackagename";
                case 8:
                    return "setSessionKey";
                case 9:
                    return "removeSessionKey";
                case 10:
                    return "connectionRequest";
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
                parcel.enforceInterface(IPerfSDKService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPerfSDKService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iInitPerfSDK = initPerfSDK(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitPerfSDK);
                    return true;
                case 2:
                    String allowedPkgName = getAllowedPkgName();
                    parcel2.writeNoException();
                    parcel2.writeString(allowedPkgName);
                    return true;
                case 3:
                    int[] highBoostingLevel = getHighBoostingLevel();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(highBoostingLevel);
                    return true;
                case 4:
                    int[] lowBoostingLevel = getLowBoostingLevel();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(lowBoostingLevel);
                    return true;
                case 5:
                    int[] thermalTable = getThermalTable();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(thermalTable);
                    return true;
                case 6:
                    String foregroundPackagename = getForegroundPackagename();
                    parcel2.writeNoException();
                    parcel2.writeString(foregroundPackagename);
                    return true;
                case 7:
                    String changedForegroundPackagename = getChangedForegroundPackagename();
                    parcel2.writeNoException();
                    parcel2.writeString(changedForegroundPackagename);
                    return true;
                case 8:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int sessionKey = setSessionKey(string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionKey);
                    return true;
                case 9:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveSessionKey = removeSessionKey(string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveSessionKey);
                    return true;
                case 10:
                    int iConnectionRequest = connectionRequest();
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnectionRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPerfSDKService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPerfSDKService.DESCRIPTOR;
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int initPerfSDK(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public String getAllowedPkgName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int[] getHighBoostingLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int[] getLowBoostingLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int[] getThermalTable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public String getForegroundPackagename() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public String getChangedForegroundPackagename() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int setSessionKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int removeSessionKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.perfsdkservice.IPerfSDKService
            public int connectionRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
