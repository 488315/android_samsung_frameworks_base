package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes4.dex */
public interface ISatelliteProvisionStateCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.telephony.satellite.ISatelliteProvisionStateCallback";

    void onSatelliteProvisionStateChanged(boolean z) throws RemoteException;

    void onSatelliteSubscriptionProvisionStateChanged(List<SatelliteSubscriberProvisionStatus> list)
            throws RemoteException;

    public static class Default implements ISatelliteProvisionStateCallback {
        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteProvisionStateChanged(boolean provisioned) throws RemoteException {}

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteSubscriptionProvisionStateChanged(
                List<SatelliteSubscriberProvisionStatus> satelliteSubscriberProvisionStatus)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISatelliteProvisionStateCallback {
        static final int TRANSACTION_onSatelliteProvisionStateChanged = 1;
        static final int TRANSACTION_onSatelliteSubscriptionProvisionStateChanged = 2;

        public Stub() {
            attachInterface(this, ISatelliteProvisionStateCallback.DESCRIPTOR);
        }

        public static ISatelliteProvisionStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISatelliteProvisionStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISatelliteProvisionStateCallback)) {
                return (ISatelliteProvisionStateCallback) iin;
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
                    return "onSatelliteProvisionStateChanged";
                case 2:
                    return "onSatelliteSubscriptionProvisionStateChanged";
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
                data.enforceInterface(ISatelliteProvisionStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISatelliteProvisionStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onSatelliteProvisionStateChanged(_arg0);
                    return true;
                case 2:
                    List<SatelliteSubscriberProvisionStatus> _arg02 =
                            data.createTypedArrayList(SatelliteSubscriberProvisionStatus.CREATOR);
                    data.enforceNoDataAvail();
                    onSatelliteSubscriptionProvisionStateChanged(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISatelliteProvisionStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteProvisionStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
            public void onSatelliteProvisionStateChanged(boolean provisioned)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteProvisionStateCallback.DESCRIPTOR);
                    _data.writeBoolean(provisioned);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
            public void onSatelliteSubscriptionProvisionStateChanged(
                    List<SatelliteSubscriberProvisionStatus> satelliteSubscriberProvisionStatus)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISatelliteProvisionStateCallback.DESCRIPTOR);
                    _data.writeTypedList(satelliteSubscriberProvisionStatus, 0);
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
