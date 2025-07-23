package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IInputDeviceBatteryListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IInputDeviceBatteryListener";

    public static class Default implements IInputDeviceBatteryListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(IInputDeviceBatteryState iInputDeviceBatteryState) throws RemoteException {
        }
    }

    void onBatteryStateChanged(IInputDeviceBatteryState iInputDeviceBatteryState) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputDeviceBatteryListener {
        static final int TRANSACTION_onBatteryStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInputDeviceBatteryListener.DESCRIPTOR);
        }

        public static IInputDeviceBatteryListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputDeviceBatteryListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputDeviceBatteryListener)) {
                return (IInputDeviceBatteryListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBatteryStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputDeviceBatteryListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputDeviceBatteryListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IInputDeviceBatteryState iInputDeviceBatteryState = (IInputDeviceBatteryState) parcel.readTypedObject(IInputDeviceBatteryState.CREATOR);
                parcel.enforceNoDataAvail();
                onBatteryStateChanged(iInputDeviceBatteryState);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInputDeviceBatteryListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputDeviceBatteryListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IInputDeviceBatteryListener
            public void onBatteryStateChanged(IInputDeviceBatteryState iInputDeviceBatteryState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputDeviceBatteryListener.DESCRIPTOR);
                    obtain.writeTypedObject(iInputDeviceBatteryState, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
