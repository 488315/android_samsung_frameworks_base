package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ITrustedPresentationListener extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITrustedPresentationListener";

    public static class Default implements ITrustedPresentationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws RemoteException {
        }
    }

    void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws RemoteException;

    public static abstract class Stub extends Binder implements ITrustedPresentationListener {
        static final int TRANSACTION_onTrustedPresentationChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITrustedPresentationListener.DESCRIPTOR);
        }

        public static ITrustedPresentationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITrustedPresentationListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITrustedPresentationListener)) {
                return (ITrustedPresentationListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTrustedPresentationChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITrustedPresentationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITrustedPresentationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] createIntArray = parcel.createIntArray();
                int[] createIntArray2 = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onTrustedPresentationChanged(createIntArray, createIntArray2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITrustedPresentationListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITrustedPresentationListener.DESCRIPTOR;
            }

            @Override // android.window.ITrustedPresentationListener
            public void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITrustedPresentationListener.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
