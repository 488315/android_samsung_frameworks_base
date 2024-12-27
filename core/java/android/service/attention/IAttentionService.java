package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAttentionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.attention.IAttentionService";

    void cancelAttentionCheck(IAttentionCallback iAttentionCallback) throws RemoteException;

    void checkAttention(IAttentionCallback iAttentionCallback) throws RemoteException;

    void onStartProximityUpdates(IProximityUpdateCallback iProximityUpdateCallback)
            throws RemoteException;

    void onStopProximityUpdates() throws RemoteException;

    public static class Default implements IAttentionService {
        @Override // android.service.attention.IAttentionService
        public void checkAttention(IAttentionCallback callback) throws RemoteException {}

        @Override // android.service.attention.IAttentionService
        public void cancelAttentionCheck(IAttentionCallback callback) throws RemoteException {}

        @Override // android.service.attention.IAttentionService
        public void onStartProximityUpdates(IProximityUpdateCallback callback)
                throws RemoteException {}

        @Override // android.service.attention.IAttentionService
        public void onStopProximityUpdates() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAttentionService {
        static final int TRANSACTION_cancelAttentionCheck = 2;
        static final int TRANSACTION_checkAttention = 1;
        static final int TRANSACTION_onStartProximityUpdates = 3;
        static final int TRANSACTION_onStopProximityUpdates = 4;

        public Stub() {
            attachInterface(this, IAttentionService.DESCRIPTOR);
        }

        public static IAttentionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAttentionService.DESCRIPTOR);
            if (iin != null && (iin instanceof IAttentionService)) {
                return (IAttentionService) iin;
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
                    return "checkAttention";
                case 2:
                    return "cancelAttentionCheck";
                case 3:
                    return "onStartProximityUpdates";
                case 4:
                    return "onStopProximityUpdates";
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
                data.enforceInterface(IAttentionService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAttentionService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IAttentionCallback _arg0 =
                            IAttentionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    checkAttention(_arg0);
                    return true;
                case 2:
                    IAttentionCallback _arg02 =
                            IAttentionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    cancelAttentionCheck(_arg02);
                    return true;
                case 3:
                    IProximityUpdateCallback _arg03 =
                            IProximityUpdateCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onStartProximityUpdates(_arg03);
                    return true;
                case 4:
                    onStopProximityUpdates();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAttentionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAttentionService.DESCRIPTOR;
            }

            @Override // android.service.attention.IAttentionService
            public void checkAttention(IAttentionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void cancelAttentionCheck(IAttentionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStartProximityUpdates(IProximityUpdateCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStopProximityUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAttentionService.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
