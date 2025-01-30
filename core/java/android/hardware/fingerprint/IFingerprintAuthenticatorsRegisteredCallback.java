package android.hardware.fingerprint;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFingerprintAuthenticatorsRegisteredCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback";

    void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) throws RemoteException;

    public static class Default implements IFingerprintAuthenticatorsRegisteredCallback {
        @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
        public void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> sensors) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFingerprintAuthenticatorsRegisteredCallback {
        static final int TRANSACTION_onAllAuthenticatorsRegistered = 1;

        public Stub() {
            attachInterface(this, IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
        }

        public static IFingerprintAuthenticatorsRegisteredCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFingerprintAuthenticatorsRegisteredCallback)) {
                return (IFingerprintAuthenticatorsRegisteredCallback) iin;
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
                    return "onAllAuthenticatorsRegistered";
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
                data.enforceInterface(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<FingerprintSensorPropertiesInternal> _arg0 = data.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                            data.enforceNoDataAvail();
                            onAllAuthenticatorsRegistered(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IFingerprintAuthenticatorsRegisteredCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
            public void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> sensors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
                    _data.writeTypedList(sensors, 0);
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
