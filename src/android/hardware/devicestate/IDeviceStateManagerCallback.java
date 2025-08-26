package android.hardware.devicestate;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceStateManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.devicestate.IDeviceStateManagerCallback";

    public static class Default implements IDeviceStateManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onDeviceStateInfoChanged(DeviceStateInfo deviceStateInfo) throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestActive(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestCanceled(IBinder iBinder) throws RemoteException {
        }
    }

    void onDeviceStateInfoChanged(DeviceStateInfo deviceStateInfo) throws RemoteException;

    void onRequestActive(IBinder iBinder) throws RemoteException;

    void onRequestCanceled(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceStateManagerCallback {
        static final int TRANSACTION_onDeviceStateInfoChanged = 1;
        static final int TRANSACTION_onRequestActive = 2;
        static final int TRANSACTION_onRequestCanceled = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IDeviceStateManagerCallback.DESCRIPTOR);
        }

        public static IDeviceStateManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceStateManagerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceStateManagerCallback)) {
                return (IDeviceStateManagerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDeviceStateInfoChanged";
            }
            if (i == 2) {
                return "onRequestActive";
            }
            if (i != 3) {
                return null;
            }
            return "onRequestCanceled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceStateManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceStateManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DeviceStateInfo deviceStateInfo = (DeviceStateInfo) parcel.readTypedObject(DeviceStateInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceStateInfoChanged(deviceStateInfo);
            } else if (i == 2) {
                IBinder strongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onRequestActive(strongBinder);
            } else if (i == 3) {
                IBinder strongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onRequestCanceled(strongBinder2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDeviceStateManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceStateManagerCallback.DESCRIPTOR;
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onDeviceStateInfoChanged(DeviceStateInfo deviceStateInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManagerCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(deviceStateInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onRequestActive(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManagerCallback.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.devicestate.IDeviceStateManagerCallback
            public void onRequestCanceled(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceStateManagerCallback.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
