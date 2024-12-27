package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteSupportedStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteSupportedStateCallback";

    void onSatelliteSupportedStateChanged(boolean z) throws RemoteException;

    public static class Default implements ISatelliteSupportedStateCallback {
        @Override // android.telephony.satellite.ISatelliteSupportedStateCallback
        public void onSatelliteSupportedStateChanged(boolean supported) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteSupportedStateCallback {
        static final int TRANSACTION_onSatelliteSupportedStateChanged = 1;

        public Stub() {
            attachInterface(this, ISatelliteSupportedStateCallback.DESCRIPTOR);
        }

        public static ISatelliteSupportedStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteSupportedStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteSupportedStateCallback)) {
                return (ISatelliteSupportedStateCallback) iin;
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
                    return "onSatelliteSupportedStateChanged";
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
                data.enforceInterface(ISatelliteSupportedStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteSupportedStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onSatelliteSupportedStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteSupportedStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteSupportedStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteSupportedStateCallback
            public void onSatelliteSupportedStateChanged(boolean supported) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteSupportedStateCallback.DESCRIPTOR);
                    _data.writeBoolean(supported);
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
