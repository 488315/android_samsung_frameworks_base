package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBrightnessListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IBrightnessListener";

    public static class Default implements IBrightnessListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IBrightnessListener
        public void onBrightnessChanged(float f) throws RemoteException {
        }
    }

    void onBrightnessChanged(float f) throws RemoteException;

    public static abstract class Stub extends Binder implements IBrightnessListener {
        static final int TRANSACTION_onBrightnessChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBrightnessListener.DESCRIPTOR);
        }

        public static IBrightnessListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBrightnessListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBrightnessListener)) {
                return (IBrightnessListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBrightnessChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBrightnessListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBrightnessListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onBrightnessChanged(f);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBrightnessListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBrightnessListener.DESCRIPTOR;
            }

            @Override // android.hardware.display.IBrightnessListener
            public void onBrightnessChanged(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrightnessListener.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
