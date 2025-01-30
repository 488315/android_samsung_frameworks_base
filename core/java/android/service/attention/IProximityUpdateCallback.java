package android.service.attention;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IProximityUpdateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.attention.IProximityUpdateCallback";

    void onProximityUpdate(double d) throws RemoteException;

    public static class Default implements IProximityUpdateCallback {
        @Override // android.service.attention.IProximityUpdateCallback
        public void onProximityUpdate(double distance) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProximityUpdateCallback {
        static final int TRANSACTION_onProximityUpdate = 1;

        public Stub() {
            attachInterface(this, IProximityUpdateCallback.DESCRIPTOR);
        }

        public static IProximityUpdateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProximityUpdateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IProximityUpdateCallback)) {
                return (IProximityUpdateCallback) iin;
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
                    return "onProximityUpdate";
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
                data.enforceInterface(IProximityUpdateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IProximityUpdateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            double _arg0 = data.readDouble();
                            data.enforceNoDataAvail();
                            onProximityUpdate(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IProximityUpdateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProximityUpdateCallback.DESCRIPTOR;
            }

            @Override // android.service.attention.IProximityUpdateCallback
            public void onProximityUpdate(double distance) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IProximityUpdateCallback.DESCRIPTOR);
                    _data.writeDouble(distance);
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
