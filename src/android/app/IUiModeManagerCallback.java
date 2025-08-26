package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUiModeManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUiModeManagerCallback";

    public static class Default implements IUiModeManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyContrastChanged(float f) throws RemoteException {
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyForceInvertStateChanged(int i) throws RemoteException {
        }
    }

    void notifyContrastChanged(float f) throws RemoteException;

    void notifyForceInvertStateChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IUiModeManagerCallback {
        static final int TRANSACTION_notifyContrastChanged = 1;
        static final int TRANSACTION_notifyForceInvertStateChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IUiModeManagerCallback.DESCRIPTOR);
        }

        public static IUiModeManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUiModeManagerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUiModeManagerCallback)) {
                return (IUiModeManagerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "notifyContrastChanged";
            }
            if (i != 2) {
                return null;
            }
            return "notifyForceInvertStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUiModeManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUiModeManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                notifyContrastChanged(f);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyForceInvertStateChanged(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUiModeManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUiModeManagerCallback.DESCRIPTOR;
            }

            @Override // android.app.IUiModeManagerCallback
            public void notifyContrastChanged(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUiModeManagerCallback.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManagerCallback
            public void notifyForceInvertStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IUiModeManagerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
