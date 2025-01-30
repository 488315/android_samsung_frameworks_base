package android.p009os.incremental;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IStorageHealthListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.incremental.IStorageHealthListener";
    public static final int HEALTH_STATUS_BLOCKED = 2;
    public static final int HEALTH_STATUS_OK = 0;
    public static final int HEALTH_STATUS_READS_PENDING = 1;
    public static final int HEALTH_STATUS_UNHEALTHY = 3;

    void onHealthStatus(int i, int i2) throws RemoteException;

    public static class Default implements IStorageHealthListener {
        @Override // android.p009os.incremental.IStorageHealthListener
        public void onHealthStatus(int storageId, int status) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStorageHealthListener {
        static final int TRANSACTION_onHealthStatus = 1;

        public Stub() {
            attachInterface(this, IStorageHealthListener.DESCRIPTOR);
        }

        public static IStorageHealthListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStorageHealthListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IStorageHealthListener)) {
                return (IStorageHealthListener) iin;
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
                    return "onHealthStatus";
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
                data.enforceInterface(IStorageHealthListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IStorageHealthListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onHealthStatus(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IStorageHealthListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStorageHealthListener.DESCRIPTOR;
            }

            @Override // android.p009os.incremental.IStorageHealthListener
            public void onHealthStatus(int storageId, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStorageHealthListener.DESCRIPTOR);
                    _data.writeInt(storageId);
                    _data.writeInt(status);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
