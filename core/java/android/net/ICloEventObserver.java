package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICloEventObserver extends IInterface {
    public static final String DESCRIPTOR = "android.net.ICloEventObserver";

    void onUpdatedGroRlEnvironment(String str) throws RemoteException;

    public static class Default implements ICloEventObserver {
        @Override // android.net.ICloEventObserver
        public void onUpdatedGroRlEnvironment(String env) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICloEventObserver {
        static final int TRANSACTION_onUpdatedGroRlEnvironment = 1;

        public Stub() {
            attachInterface(this, ICloEventObserver.DESCRIPTOR);
        }

        public static ICloEventObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICloEventObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof ICloEventObserver)) {
                return (ICloEventObserver) iin;
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
                    return "onUpdatedGroRlEnvironment";
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
                data.enforceInterface(ICloEventObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICloEventObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onUpdatedGroRlEnvironment(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICloEventObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICloEventObserver.DESCRIPTOR;
            }

            @Override // android.net.ICloEventObserver
            public void onUpdatedGroRlEnvironment(String env) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICloEventObserver.DESCRIPTOR);
                    _data.writeString(env);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
