package android.companion.virtual.sensor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SharedMemory;

/* loaded from: classes.dex */
public interface IVirtualSensorCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.sensor.IVirtualSensorCallback";

    public static class Default implements IVirtualSensorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onConfigurationChanged(VirtualSensor virtualSensor, boolean z, int i, int i2) throws RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelConfigured(int i, VirtualSensor virtualSensor, int i2, int i3) throws RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelCreated(int i, SharedMemory sharedMemory) throws RemoteException {
        }

        @Override // android.companion.virtual.sensor.IVirtualSensorCallback
        public void onDirectChannelDestroyed(int i) throws RemoteException {
        }
    }

    void onConfigurationChanged(VirtualSensor virtualSensor, boolean z, int i, int i2) throws RemoteException;

    void onDirectChannelConfigured(int i, VirtualSensor virtualSensor, int i2, int i3) throws RemoteException;

    void onDirectChannelCreated(int i, SharedMemory sharedMemory) throws RemoteException;

    void onDirectChannelDestroyed(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualSensorCallback {
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onDirectChannelConfigured = 4;
        static final int TRANSACTION_onDirectChannelCreated = 2;
        static final int TRANSACTION_onDirectChannelDestroyed = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IVirtualSensorCallback.DESCRIPTOR);
        }

        public static IVirtualSensorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualSensorCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualSensorCallback)) {
                return (IVirtualSensorCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConfigurationChanged";
            }
            if (i == 2) {
                return "onDirectChannelCreated";
            }
            if (i == 3) {
                return "onDirectChannelDestroyed";
            }
            if (i != 4) {
                return null;
            }
            return "onDirectChannelConfigured";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualSensorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualSensorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                VirtualSensor virtualSensor = (VirtualSensor) parcel.readTypedObject(VirtualSensor.CREATOR);
                boolean z = parcel.readBoolean();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onConfigurationChanged(virtualSensor, z, i3, i4);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
                parcel.enforceNoDataAvail();
                onDirectChannelCreated(i5, sharedMemory);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDirectChannelDestroyed(i6);
            } else if (i == 4) {
                int i7 = parcel.readInt();
                VirtualSensor virtualSensor2 = (VirtualSensor) parcel.readTypedObject(VirtualSensor.CREATOR);
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDirectChannelConfigured(i7, virtualSensor2, i8, i9);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualSensorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualSensorCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onConfigurationChanged(VirtualSensor virtualSensor, boolean z, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualSensorCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualSensor, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelCreated(int i, SharedMemory sharedMemory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualSensorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sharedMemory, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelDestroyed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualSensorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelConfigured(int i, VirtualSensor virtualSensor, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualSensorCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(virtualSensor, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
