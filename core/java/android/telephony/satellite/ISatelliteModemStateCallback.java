package android.telephony.satellite;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ISatelliteModemStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteModemStateCallback";

    void onSatelliteModemStateChanged(int i) throws RemoteException;

    public static class Default implements ISatelliteModemStateCallback {
        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(int state) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteModemStateCallback {
        static final int TRANSACTION_onSatelliteModemStateChanged = 1;

        public Stub() {
            attachInterface(this, ISatelliteModemStateCallback.DESCRIPTOR);
        }

        public static ISatelliteModemStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteModemStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteModemStateCallback)) {
                return (ISatelliteModemStateCallback) iin;
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
                    return "onSatelliteModemStateChanged";
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
                data.enforceInterface(ISatelliteModemStateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISatelliteModemStateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onSatelliteModemStateChanged(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISatelliteModemStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteModemStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onSatelliteModemStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    _data.writeInt(state);
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
