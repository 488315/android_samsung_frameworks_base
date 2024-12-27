package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteCapabilitiesCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.telephony.satellite.ISatelliteCapabilitiesCallback";

    void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities)
            throws RemoteException;

    public static class Default implements ISatelliteCapabilitiesCallback {
        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(SatelliteCapabilities capabilities)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISatelliteCapabilitiesCallback {
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 1;

        public Stub() {
            attachInterface(this, ISatelliteCapabilitiesCallback.DESCRIPTOR);
        }

        public static ISatelliteCapabilitiesCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteCapabilitiesCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteCapabilitiesCallback)) {
                return (ISatelliteCapabilitiesCallback) iin;
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
                    return "onSatelliteCapabilitiesChanged";
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
                data.enforceInterface(ISatelliteCapabilitiesCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteCapabilitiesCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SatelliteCapabilities _arg0 =
                            (SatelliteCapabilities)
                                    data.readTypedObject(SatelliteCapabilities.CREATOR);
                    data.enforceNoDataAvail();
                    onSatelliteCapabilitiesChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteCapabilitiesCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteCapabilitiesCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
            public void onSatelliteCapabilitiesChanged(SatelliteCapabilities capabilities)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteCapabilitiesCallback.DESCRIPTOR);
                    _data.writeTypedObject(capabilities, 0);
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
