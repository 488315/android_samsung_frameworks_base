package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDeviceIdleControllerAdapter extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telecom.IDeviceIdleControllerAdapter";

    void exemptAppTemporarilyForEvent(String str, long j, int i, String str2)
            throws RemoteException;

    public static class Default implements IDeviceIdleControllerAdapter {
        @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
        public void exemptAppTemporarilyForEvent(
                String packageName, long duration, int userHandle, String reason)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDeviceIdleControllerAdapter {
        static final int TRANSACTION_exemptAppTemporarilyForEvent = 1;

        public Stub() {
            attachInterface(this, IDeviceIdleControllerAdapter.DESCRIPTOR);
        }

        public static IDeviceIdleControllerAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDeviceIdleControllerAdapter.DESCRIPTOR);
            if (iin != null && (iin instanceof IDeviceIdleControllerAdapter)) {
                return (IDeviceIdleControllerAdapter) iin;
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
                    return "exemptAppTemporarilyForEvent";
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
                data.enforceInterface(IDeviceIdleControllerAdapter.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDeviceIdleControllerAdapter.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    long _arg1 = data.readLong();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    exemptAppTemporarilyForEvent(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDeviceIdleControllerAdapter {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceIdleControllerAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
            public void exemptAppTemporarilyForEvent(
                    String packageName, long duration, int userHandle, String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDeviceIdleControllerAdapter.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(duration);
                    _data.writeInt(userHandle);
                    _data.writeString(reason);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
