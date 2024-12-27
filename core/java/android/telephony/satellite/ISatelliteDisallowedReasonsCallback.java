package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteDisallowedReasonsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteDisallowedReasonsCallback";

    void onSatelliteDisallowedReasonsChanged(int[] iArr) throws RemoteException;

    public static class Default implements ISatelliteDisallowedReasonsCallback {
        @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
        public void onSatelliteDisallowedReasonsChanged(int[] disallowedReasons) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteDisallowedReasonsCallback {
        static final int TRANSACTION_onSatelliteDisallowedReasonsChanged = 1;

        public Stub() {
            attachInterface(this, ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
        }

        public static ISatelliteDisallowedReasonsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteDisallowedReasonsCallback)) {
                return (ISatelliteDisallowedReasonsCallback) iin;
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
                    return "onSatelliteDisallowedReasonsChanged";
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
                data.enforceInterface(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    data.enforceNoDataAvail();
                    onSatelliteDisallowedReasonsChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteDisallowedReasonsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteDisallowedReasonsCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
            public void onSatelliteDisallowedReasonsChanged(int[] disallowedReasons) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
                    _data.writeIntArray(disallowedReasons);
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
