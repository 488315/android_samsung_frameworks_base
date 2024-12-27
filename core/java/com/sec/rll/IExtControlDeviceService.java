package com.sec.rll;

import android.graphics.rendererpolicy.ScpmApiContract;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IExtControlDeviceService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.rll.IExtControlDeviceService";

    int getStatus(int i) throws RemoteException;

    void setStatus(int i, int i2) throws RemoteException;

    public static class Default implements IExtControlDeviceService {
        @Override // com.sec.rll.IExtControlDeviceService
        public void setStatus(int deviceType, int status) throws RemoteException {}

        @Override // com.sec.rll.IExtControlDeviceService
        public int getStatus(int deviceType) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IExtControlDeviceService {
        static final int TRANSACTION_getStatus = 2;
        static final int TRANSACTION_setStatus = 1;

        public Stub() {
            attachInterface(this, IExtControlDeviceService.DESCRIPTOR);
        }

        public static IExtControlDeviceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExtControlDeviceService.DESCRIPTOR);
            if (iin != null && (iin instanceof IExtControlDeviceService)) {
                return (IExtControlDeviceService) iin;
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
                    return "setStatus";
                case 2:
                    return ScpmApiContract.Method.GET_STATUS;
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IExtControlDeviceService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IExtControlDeviceService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    setStatus(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getStatus(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IExtControlDeviceService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExtControlDeviceService.DESCRIPTOR;
            }

            @Override // com.sec.rll.IExtControlDeviceService
            public void setStatus(int deviceType, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExtControlDeviceService.DESCRIPTOR);
                    _data.writeInt(deviceType);
                    _data.writeInt(status);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.rll.IExtControlDeviceService
            public int getStatus(int deviceType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExtControlDeviceService.DESCRIPTOR);
                    _data.writeInt(deviceType);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
