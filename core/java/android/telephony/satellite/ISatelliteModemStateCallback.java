package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteModemStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteModemStateCallback";

    void onEmergencyModeChanged(boolean z) throws RemoteException;

    void onRegistrationFailure(int i) throws RemoteException;

    void onSatelliteModemStateChanged(int i) throws RemoteException;

    void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException;

    public static class Default implements ISatelliteModemStateCallback {
        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(int state) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onEmergencyModeChanged(boolean isEmergency) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onRegistrationFailure(int causeCode) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onTerrestrialNetworkAvailableChanged(boolean isAvailable) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISatelliteModemStateCallback {
        static final int TRANSACTION_onEmergencyModeChanged = 2;
        static final int TRANSACTION_onRegistrationFailure = 3;
        static final int TRANSACTION_onSatelliteModemStateChanged = 1;
        static final int TRANSACTION_onTerrestrialNetworkAvailableChanged = 4;

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSatelliteModemStateChanged";
                case 2:
                    return "onEmergencyModeChanged";
                case 3:
                    return "onRegistrationFailure";
                case 4:
                    return "onTerrestrialNetworkAvailableChanged";
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
                data.enforceInterface(ISatelliteModemStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteModemStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onSatelliteModemStateChanged(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onEmergencyModeChanged(_arg02);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onRegistrationFailure(_arg03);
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTerrestrialNetworkAvailableChanged(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteModemStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
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

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onEmergencyModeChanged(boolean isEmergency) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    _data.writeBoolean(isEmergency);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onRegistrationFailure(int causeCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    _data.writeInt(causeCode);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onTerrestrialNetworkAvailableChanged(boolean isAvailable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    _data.writeBoolean(isAvailable);
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
