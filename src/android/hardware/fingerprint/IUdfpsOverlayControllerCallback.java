package android.hardware.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUdfpsOverlayControllerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsOverlayControllerCallback";

    public static class Default implements IUdfpsOverlayControllerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IUdfpsOverlayControllerCallback
        public void onUserCanceled() throws RemoteException {
        }
    }

    void onUserCanceled() throws RemoteException;

    public static abstract class Stub extends Binder implements IUdfpsOverlayControllerCallback {
        static final int TRANSACTION_onUserCanceled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUdfpsOverlayControllerCallback.DESCRIPTOR);
        }

        public static IUdfpsOverlayControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUdfpsOverlayControllerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUdfpsOverlayControllerCallback)) {
                return (IUdfpsOverlayControllerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUserCanceled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUdfpsOverlayControllerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUdfpsOverlayControllerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onUserCanceled();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUdfpsOverlayControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUdfpsOverlayControllerCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayControllerCallback
            public void onUserCanceled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUdfpsOverlayControllerCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
