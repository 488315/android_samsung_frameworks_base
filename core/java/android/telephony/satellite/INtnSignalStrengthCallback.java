package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface INtnSignalStrengthCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.telephony.satellite.INtnSignalStrengthCallback";

    void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    public static class Default implements INtnSignalStrengthCallback {
        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements INtnSignalStrengthCallback {
        static final int TRANSACTION_onNtnSignalStrengthChanged = 1;

        public Stub() {
            attachInterface(this, INtnSignalStrengthCallback.DESCRIPTOR);
        }

        public static INtnSignalStrengthCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(INtnSignalStrengthCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof INtnSignalStrengthCallback)) {
                return (INtnSignalStrengthCallback) iin;
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
                    return "onNtnSignalStrengthChanged";
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
                data.enforceInterface(INtnSignalStrengthCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(INtnSignalStrengthCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    NtnSignalStrength _arg0 =
                            (NtnSignalStrength) data.readTypedObject(NtnSignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    onNtnSignalStrengthChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INtnSignalStrengthCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INtnSignalStrengthCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.INtnSignalStrengthCallback
            public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(INtnSignalStrengthCallback.DESCRIPTOR);
                    _data.writeTypedObject(ntnSignalStrength, 0);
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
