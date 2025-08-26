package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyboardBacklightListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IKeyboardBacklightListener";

    public static class Default implements IKeyboardBacklightListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws RemoteException {
        }
    }

    void onBrightnessChanged(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyboardBacklightListener {
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
            attachInterface(this, IKeyboardBacklightListener.DESCRIPTOR);
        }

        public static IKeyboardBacklightListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyboardBacklightListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyboardBacklightListener)) {
                return (IKeyboardBacklightListener) iInterfaceQueryLocalInterface;
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
                parcel.enforceInterface(IKeyboardBacklightListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyboardBacklightListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                IKeyboardBacklightState iKeyboardBacklightState = (IKeyboardBacklightState) parcel.readTypedObject(IKeyboardBacklightState.CREATOR);
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onBrightnessChanged(i3, iKeyboardBacklightState, z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyboardBacklightListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyboardBacklightListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IKeyboardBacklightListener
            public void onBrightnessChanged(int i, IKeyboardBacklightState iKeyboardBacklightState, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyboardBacklightListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(iKeyboardBacklightState, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
