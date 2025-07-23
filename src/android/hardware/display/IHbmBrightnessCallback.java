package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHbmBrightnessCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IHbmBrightnessCallback";

    public static class Default implements IHbmBrightnessCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IHbmBrightnessCallback
        public void onChanged(int i, boolean z) throws RemoteException {
        }
    }

    void onChanged(int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IHbmBrightnessCallback {
        static final int TRANSACTION_onChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IHbmBrightnessCallback.DESCRIPTOR);
        }

        public static IHbmBrightnessCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IHbmBrightnessCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHbmBrightnessCallback)) {
                return (IHbmBrightnessCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IHbmBrightnessCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHbmBrightnessCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onChanged(readInt, readBoolean);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHbmBrightnessCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHbmBrightnessCallback.DESCRIPTOR;
            }

            @Override // android.hardware.display.IHbmBrightnessCallback
            public void onChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IHbmBrightnessCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
