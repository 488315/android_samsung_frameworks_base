package com.samsung.android.iccc;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IIntegrityControlCheckCenter extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.iccc.IIntegrityControlCheckCenter";

    byte[] getDeviceInfo(byte[] bArr) throws RemoteException;

    byte[] getDeviceStatus(int i, byte[] bArr) throws RemoteException;

    int getSecureData(int i) throws RemoteException;

    int getTrustedBootData() throws RemoteException;

    byte[] setAttestationData(byte[] bArr) throws RemoteException;

    int setSecureData(int i, int i2) throws RemoteException;

    public static class Default implements IIntegrityControlCheckCenter {
        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int getSecureData(int type) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int setSecureData(int type, int value) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public int getTrustedBootData() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] setAttestationData(byte[] blob) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] getDeviceStatus(int comp_type, byte[] nonce) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
        public byte[] getDeviceInfo(byte[] nonce) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IIntegrityControlCheckCenter {
        static final int TRANSACTION_getDeviceInfo = 6;
        static final int TRANSACTION_getDeviceStatus = 5;
        static final int TRANSACTION_getSecureData = 1;
        static final int TRANSACTION_getTrustedBootData = 3;
        static final int TRANSACTION_setAttestationData = 4;
        static final int TRANSACTION_setSecureData = 2;

        public Stub() {
            attachInterface(this, IIntegrityControlCheckCenter.DESCRIPTOR);
        }

        public static IIntegrityControlCheckCenter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIntegrityControlCheckCenter.DESCRIPTOR);
            if (iin != null && (iin instanceof IIntegrityControlCheckCenter)) {
                return (IIntegrityControlCheckCenter) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IIntegrityControlCheckCenter.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IIntegrityControlCheckCenter.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result = getSecureData(_arg0);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result2 = setSecureData(_arg02, _arg1);
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        case 3:
                            int _result3 = getTrustedBootData();
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 4:
                            byte[] _arg03 = data.createByteArray();
                            data.enforceNoDataAvail();
                            byte[] _result4 = setAttestationData(_arg03);
                            reply.writeNoException();
                            reply.writeByteArray(_result4);
                            return true;
                        case 5:
                            int _arg04 = data.readInt();
                            byte[] _arg12 = data.createByteArray();
                            data.enforceNoDataAvail();
                            byte[] _result5 = getDeviceStatus(_arg04, _arg12);
                            reply.writeNoException();
                            reply.writeByteArray(_result5);
                            return true;
                        case 6:
                            byte[] _arg05 = data.createByteArray();
                            data.enforceNoDataAvail();
                            byte[] _result6 = getDeviceInfo(_arg05);
                            reply.writeNoException();
                            reply.writeByteArray(_result6);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IIntegrityControlCheckCenter {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntegrityControlCheckCenter.DESCRIPTOR;
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int getSecureData(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int setSecureData(int type, int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(value);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public int getTrustedBootData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] setAttestationData(byte[] blob) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    _data.writeByteArray(blob);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] getDeviceStatus(int comp_type, byte[] nonce) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    _data.writeInt(comp_type);
                    _data.writeByteArray(nonce);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.iccc.IIntegrityControlCheckCenter
            public byte[] getDeviceInfo(byte[] nonce) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIntegrityControlCheckCenter.DESCRIPTOR);
                    _data.writeByteArray(nonce);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
