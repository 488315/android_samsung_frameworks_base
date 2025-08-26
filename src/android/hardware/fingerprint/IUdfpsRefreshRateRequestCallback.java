package android.hardware.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUdfpsRefreshRateRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback";

    public static class Default implements IUdfpsRefreshRateRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onAuthenticationPossible(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onRequestDisabled(int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
        public void onRequestEnabled(int i) throws RemoteException {
        }
    }

    void onAuthenticationPossible(int i, boolean z) throws RemoteException;

    void onRequestDisabled(int i) throws RemoteException;

    void onRequestEnabled(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IUdfpsRefreshRateRequestCallback {
        static final int TRANSACTION_onAuthenticationPossible = 3;
        static final int TRANSACTION_onRequestDisabled = 2;
        static final int TRANSACTION_onRequestEnabled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
        }

        public static IUdfpsRefreshRateRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUdfpsRefreshRateRequestCallback)) {
                return (IUdfpsRefreshRateRequestCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRequestEnabled";
            }
            if (i == 2) {
                return "onRequestDisabled";
            }
            if (i != 3) {
                return null;
            }
            return "onAuthenticationPossible";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRequestEnabled(i3);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRequestDisabled(i4);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAuthenticationPossible(i5, z);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUdfpsRefreshRateRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUdfpsRefreshRateRequestCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onRequestEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onRequestDisabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onAuthenticationPossible(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
