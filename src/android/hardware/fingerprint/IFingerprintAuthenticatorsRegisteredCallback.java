package android.hardware.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFingerprintAuthenticatorsRegisteredCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback";

    public static class Default implements IFingerprintAuthenticatorsRegisteredCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
        public void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) throws RemoteException {
        }
    }

    void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IFingerprintAuthenticatorsRegisteredCallback {
        static final int TRANSACTION_onAllAuthenticatorsRegistered = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
        }

        public static IFingerprintAuthenticatorsRegisteredCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFingerprintAuthenticatorsRegisteredCallback)) {
                return (IFingerprintAuthenticatorsRegisteredCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAllAuthenticatorsRegistered";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                parcel.enforceNoDataAvail();
                onAllAuthenticatorsRegistered(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IFingerprintAuthenticatorsRegisteredCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback
            public void onAllAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFingerprintAuthenticatorsRegisteredCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
