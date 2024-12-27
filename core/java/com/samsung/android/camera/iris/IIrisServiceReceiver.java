package com.samsung.android.camera.iris;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IIrisServiceReceiver extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisServiceReceiver";

    void onAcquired(long j, int i) throws RemoteException;

    void onAuthenticationFailed(long j) throws RemoteException;

    void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) throws RemoteException;

    void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException;

    void onError(long j, int i) throws RemoteException;

    void onIRImage(long j, byte[] bArr, int i, int i2) throws RemoteException;

    void onRemoved(long j, int i, int i2) throws RemoteException;

    public static class Default implements IIrisServiceReceiver {
        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAcquired(long deviceId, int acquiredInfo) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationSucceeded(long deviceId, Iris ir, byte[] fidoResultData) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationFailed(long deviceId) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onError(long deviceId, int error) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onRemoved(long deviceId, int irisId, int groupId) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onIRImage(long deviceId, byte[] irisImage, int width, int height) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IIrisServiceReceiver {
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 4;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onIRImage = 7;
        static final int TRANSACTION_onRemoved = 6;

        public Stub() {
            attachInterface(this, IIrisServiceReceiver.DESCRIPTOR);
        }

        public static IIrisServiceReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIrisServiceReceiver.DESCRIPTOR);
            if (iin != null && (iin instanceof IIrisServiceReceiver)) {
                return (IIrisServiceReceiver) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onAuthenticationFailed";
                case 5:
                    return "onError";
                case 6:
                    return "onRemoved";
                case 7:
                    return "onIRImage";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IIrisServiceReceiver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIrisServiceReceiver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onEnrollResult(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onAcquired(_arg02, _arg12);
                    return true;
                case 3:
                    long _arg03 = data.readLong();
                    Iris _arg13 = (Iris) data.readTypedObject(Iris.CREATOR);
                    byte[] _arg22 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onAuthenticationSucceeded(_arg03, _arg13, _arg22);
                    return true;
                case 4:
                    long _arg04 = data.readLong();
                    data.enforceNoDataAvail();
                    onAuthenticationFailed(_arg04);
                    return true;
                case 5:
                    long _arg05 = data.readLong();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg05, _arg14);
                    return true;
                case 6:
                    long _arg06 = data.readLong();
                    int _arg15 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onRemoved(_arg06, _arg15, _arg23);
                    return true;
                case 7:
                    long _arg07 = data.readLong();
                    byte[] _arg16 = data.createByteArray();
                    int _arg24 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    onIRImage(_arg07, _arg16, _arg24, _arg32);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIrisServiceReceiver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisServiceReceiver.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeInt(irisId);
                    _data.writeInt(groupId);
                    _data.writeInt(remaining);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAcquired(long deviceId, int acquiredInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeInt(acquiredInfo);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationSucceeded(long deviceId, Iris ir, byte[] fidoResultData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeTypedObject(ir, 0);
                    _data.writeByteArray(fidoResultData);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationFailed(long deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onError(long deviceId, int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeInt(error);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onRemoved(long deviceId, int irisId, int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeInt(irisId);
                    _data.writeInt(groupId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onIRImage(long deviceId, byte[] irisImage, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    _data.writeLong(deviceId);
                    _data.writeByteArray(irisImage);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
