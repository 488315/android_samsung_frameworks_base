package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IInputDeviceBatteryListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IInputDeviceBatteryListener";

    void onBatteryStateChanged(IInputDeviceBatteryState iInputDeviceBatteryState)
            throws RemoteException;

    public static class Default implements IInputDeviceBatteryListener {
        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(IInputDeviceBatteryState batteryState)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInputDeviceBatteryListener {
        static final int TRANSACTION_onBatteryStateChanged = 1;

        public Stub() {
            attachInterface(this, IInputDeviceBatteryListener.DESCRIPTOR);
        }

        public static IInputDeviceBatteryListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInputDeviceBatteryListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IInputDeviceBatteryListener)) {
                return (IInputDeviceBatteryListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onBatteryStateChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInputDeviceBatteryListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInputDeviceBatteryListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IInputDeviceBatteryState _arg0 =
                            (IInputDeviceBatteryState)
                                    data.readTypedObject(IInputDeviceBatteryState.CREATOR);
                    data.enforceNoDataAvail();
                    onBatteryStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInputDeviceBatteryListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputDeviceBatteryListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IInputDeviceBatteryListener
            public void onBatteryStateChanged(IInputDeviceBatteryState batteryState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInputDeviceBatteryListener.DESCRIPTOR);
                    _data.writeTypedObject(batteryState, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
