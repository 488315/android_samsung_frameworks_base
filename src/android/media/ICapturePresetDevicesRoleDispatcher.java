package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface ICapturePresetDevicesRoleDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.ICapturePresetDevicesRoleDispatcher";

    public static class Default implements ICapturePresetDevicesRoleDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ICapturePresetDevicesRoleDispatcher
        public void dispatchDevicesRoleChanged(int i, int i2, List<AudioDeviceAttributes> list) throws RemoteException {
        }
    }

    void dispatchDevicesRoleChanged(int i, int i2, List<AudioDeviceAttributes> list) throws RemoteException;

    public static abstract class Stub extends Binder implements ICapturePresetDevicesRoleDispatcher {
        static final int TRANSACTION_dispatchDevicesRoleChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
        }

        public static ICapturePresetDevicesRoleDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICapturePresetDevicesRoleDispatcher)) {
                return (ICapturePresetDevicesRoleDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchDevicesRoleChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                dispatchDevicesRoleChanged(readInt, readInt2, createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICapturePresetDevicesRoleDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICapturePresetDevicesRoleDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ICapturePresetDevicesRoleDispatcher
            public void dispatchDevicesRoleChanged(int i, int i2, List<AudioDeviceAttributes> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICapturePresetDevicesRoleDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
