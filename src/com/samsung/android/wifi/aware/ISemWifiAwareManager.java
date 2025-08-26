package com.samsung.android.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiAwareManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.aware.ISemWifiAwareManager";

    public static class Default implements ISemWifiAwareManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int getMaxNdpCountForAwareP2p() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int getMaxNdpCountForAwareSoftAp() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public long getStdPlusFeature() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isAwareP2pConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isAwareSoftApConcurrencySupported() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public int isPreEnabled() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public boolean isVendorNanServiceAvailable() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public void setClusterMergingEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
        public void setNanCommand(int i, byte[] bArr) throws RemoteException {
        }
    }

    int getMaxNdpCountForAwareP2p() throws RemoteException;

    int getMaxNdpCountForAwareSoftAp() throws RemoteException;

    long getStdPlusFeature() throws RemoteException;

    boolean isAwareP2pConcurrencySupported() throws RemoteException;

    boolean isAwareSoftApConcurrencySupported() throws RemoteException;

    int isPreEnabled() throws RemoteException;

    boolean isVendorNanServiceAvailable() throws RemoteException;

    void setClusterMergingEnabled(boolean z) throws RemoteException;

    void setNanCommand(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiAwareManager {
        static final int TRANSACTION_getMaxNdpCountForAwareP2p = 8;
        static final int TRANSACTION_getMaxNdpCountForAwareSoftAp = 9;
        static final int TRANSACTION_getStdPlusFeature = 5;
        static final int TRANSACTION_isAwareP2pConcurrencySupported = 7;
        static final int TRANSACTION_isAwareSoftApConcurrencySupported = 6;
        static final int TRANSACTION_isPreEnabled = 2;
        static final int TRANSACTION_isVendorNanServiceAvailable = 4;
        static final int TRANSACTION_setClusterMergingEnabled = 1;
        static final int TRANSACTION_setNanCommand = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISemWifiAwareManager.DESCRIPTOR);
        }

        public static ISemWifiAwareManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemWifiAwareManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemWifiAwareManager)) {
                return (ISemWifiAwareManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setClusterMergingEnabled";
                case 2:
                    return "isPreEnabled";
                case 3:
                    return "setNanCommand";
                case 4:
                    return "isVendorNanServiceAvailable";
                case 5:
                    return "getStdPlusFeature";
                case 6:
                    return "isAwareSoftApConcurrencySupported";
                case 7:
                    return "isAwareP2pConcurrencySupported";
                case 8:
                    return "getMaxNdpCountForAwareP2p";
                case 9:
                    return "getMaxNdpCountForAwareSoftAp";
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
                parcel.enforceInterface(ISemWifiAwareManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiAwareManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setClusterMergingEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int iIsPreEnabled = isPreEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsPreEnabled);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setNanCommand(i3, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean zIsVendorNanServiceAvailable = isVendorNanServiceAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVendorNanServiceAvailable);
                    return true;
                case 5:
                    long stdPlusFeature = getStdPlusFeature();
                    parcel2.writeNoException();
                    parcel2.writeLong(stdPlusFeature);
                    return true;
                case 6:
                    boolean zIsAwareSoftApConcurrencySupported = isAwareSoftApConcurrencySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAwareSoftApConcurrencySupported);
                    return true;
                case 7:
                    boolean zIsAwareP2pConcurrencySupported = isAwareP2pConcurrencySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAwareP2pConcurrencySupported);
                    return true;
                case 8:
                    int maxNdpCountForAwareP2p = getMaxNdpCountForAwareP2p();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNdpCountForAwareP2p);
                    return true;
                case 9:
                    int maxNdpCountForAwareSoftAp = getMaxNdpCountForAwareSoftAp();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNdpCountForAwareSoftAp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemWifiAwareManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiAwareManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public void setClusterMergingEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int isPreEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public void setNanCommand(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isVendorNanServiceAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public long getStdPlusFeature() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isAwareSoftApConcurrencySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public boolean isAwareP2pConcurrencySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int getMaxNdpCountForAwareP2p() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.aware.ISemWifiAwareManager
            public int getMaxNdpCountForAwareSoftAp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiAwareManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
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
