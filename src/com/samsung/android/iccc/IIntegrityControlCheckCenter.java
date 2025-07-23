package com.samsung.android.iccc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIntegrityControlCheckCenter extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.iccc.IIntegrityControlCheckCenter";

    public static class Default implements IIntegrityControlCheckCenter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] getBldpData() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] getDeviceInfo(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] getDeviceStatus(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int getSecureData(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int getTrustedBootData() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] setAttestationData(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int setSecureData(int i, int i2) throws RemoteException {
            return 0;
        }
    }

    byte[] getBldpData() throws RemoteException;

    byte[] getDeviceInfo(byte[] bArr) throws RemoteException;

    byte[] getDeviceStatus(int i, byte[] bArr) throws RemoteException;

    int getSecureData(int i) throws RemoteException;

    int getTrustedBootData() throws RemoteException;

    byte[] setAttestationData(byte[] bArr) throws RemoteException;

    int setSecureData(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIntegrityControlCheckCenter {
        static final int TRANSACTION_getBldpData = 7;
        static final int TRANSACTION_getDeviceInfo = 6;
        static final int TRANSACTION_getDeviceStatus = 5;
        static final int TRANSACTION_getSecureData = 1;
        static final int TRANSACTION_getTrustedBootData = 3;
        static final int TRANSACTION_setAttestationData = 4;
        static final int TRANSACTION_setSecureData = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IIntegrityControlCheckCenter.DESCRIPTOR);
        }

        public static IIntegrityControlCheckCenter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIntegrityControlCheckCenter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIntegrityControlCheckCenter)) {
                return (IIntegrityControlCheckCenter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSecureData";
                case 2:
                    return "setSecureData";
                case 3:
                    return "getTrustedBootData";
                case 4:
                    return "setAttestationData";
                case 5:
                    return "getDeviceStatus";
                case 6:
                    return "getDeviceInfo";
                case 7:
                    return "getBldpData";
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
                parcel.enforceInterface(IIntegrityControlCheckCenter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntegrityControlCheckCenter.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int secureData = getSecureData(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(secureData);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int secureData2 = setSecureData(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(secureData2);
                    return true;
                case 3:
                    int trustedBootData = getTrustedBootData();
                    parcel2.writeNoException();
                    parcel2.writeInt(trustedBootData);
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] attestationData = setAttestationData(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(attestationData);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] deviceStatus = getDeviceStatus(readInt4, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(deviceStatus);
                    return true;
                case 6:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] deviceInfo = getDeviceInfo(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(deviceInfo);
                    return true;
                case 7:
                    byte[] bldpData = getBldpData();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bldpData);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIntegrityControlCheckCenter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntegrityControlCheckCenter.DESCRIPTOR;
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int getSecureData(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int setSecureData(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int getTrustedBootData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] setAttestationData(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] getDeviceStatus(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] getDeviceInfo(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] getBldpData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
