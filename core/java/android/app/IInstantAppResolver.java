package android.app;

import android.content.pm.InstantAppRequestInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInstantAppResolver extends IInterface {
    void getInstantAppIntentFilterList(
            InstantAppRequestInfo instantAppRequestInfo, IRemoteCallback iRemoteCallback)
            throws RemoteException;

    void getInstantAppResolveInfoList(
            InstantAppRequestInfo instantAppRequestInfo, int i, IRemoteCallback iRemoteCallback)
            throws RemoteException;

    public static class Default implements IInstantAppResolver {
        @Override // android.app.IInstantAppResolver
        public void getInstantAppResolveInfoList(
                InstantAppRequestInfo request, int sequence, IRemoteCallback callback)
                throws RemoteException {}

        @Override // android.app.IInstantAppResolver
        public void getInstantAppIntentFilterList(
                InstantAppRequestInfo request, IRemoteCallback callback) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInstantAppResolver {
        public static final String DESCRIPTOR = "android.app.IInstantAppResolver";
        static final int TRANSACTION_getInstantAppIntentFilterList = 2;
        static final int TRANSACTION_getInstantAppResolveInfoList = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstantAppResolver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInstantAppResolver)) {
                return (IInstantAppResolver) iin;
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
                    return "getInstantAppResolveInfoList";
                case 2:
                    return "getInstantAppIntentFilterList";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    InstantAppRequestInfo _arg0 =
                            (InstantAppRequestInfo)
                                    data.readTypedObject(InstantAppRequestInfo.CREATOR);
                    int _arg1 = data.readInt();
                    IRemoteCallback _arg2 =
                            IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getInstantAppResolveInfoList(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    InstantAppRequestInfo _arg02 =
                            (InstantAppRequestInfo)
                                    data.readTypedObject(InstantAppRequestInfo.CREATOR);
                    IRemoteCallback _arg12 =
                            IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getInstantAppIntentFilterList(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInstantAppResolver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppResolveInfoList(
                    InstantAppRequestInfo request, int sequence, IRemoteCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeInt(sequence);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppIntentFilterList(
                    InstantAppRequestInfo request, IRemoteCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
