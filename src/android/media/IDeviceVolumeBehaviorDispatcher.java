package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IDeviceVolumeBehaviorDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IDeviceVolumeBehaviorDispatcher";

    public static class Default implements IDeviceVolumeBehaviorDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IDeviceVolumeBehaviorDispatcher
        public void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException {
        }
    }

    void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceVolumeBehaviorDispatcher {
        static final int TRANSACTION_dispatchDeviceVolumeBehaviorChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
        }

        public static IDeviceVolumeBehaviorDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceVolumeBehaviorDispatcher)) {
                return (IDeviceVolumeBehaviorDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchDeviceVolumeBehaviorChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispatchDeviceVolumeBehaviorChanged(audioDeviceAttributes, readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDeviceVolumeBehaviorDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceVolumeBehaviorDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IDeviceVolumeBehaviorDispatcher
            public void dispatchDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
