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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUdfpsRefreshRateRequestCallback)) {
                return (IUdfpsRefreshRateRequestCallback) queryLocalInterface;
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
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRequestEnabled(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRequestDisabled(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAuthenticationPossible(readInt3, readBoolean);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onRequestDisabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback
            public void onAuthenticationPossible(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUdfpsRefreshRateRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
