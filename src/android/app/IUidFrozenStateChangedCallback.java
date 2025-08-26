package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUidFrozenStateChangedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUidFrozenStateChangedCallback";

    public static class Default implements IUidFrozenStateChangedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws RemoteException {
        }
    }

    void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUidFrozenStateChangedCallback {
        static final int TRANSACTION_onUidFrozenStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUidFrozenStateChangedCallback.DESCRIPTOR);
        }

        public static IUidFrozenStateChangedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUidFrozenStateChangedCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUidFrozenStateChangedCallback)) {
                return (IUidFrozenStateChangedCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUidFrozenStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUidFrozenStateChangedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUidFrozenStateChangedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                int[] iArrCreateIntArray2 = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onUidFrozenStateChanged(iArrCreateIntArray, iArrCreateIntArray2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUidFrozenStateChangedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUidFrozenStateChangedCallback.DESCRIPTOR;
            }

            @Override // android.app.IUidFrozenStateChangedCallback
            public void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUidFrozenStateChangedCallback.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
