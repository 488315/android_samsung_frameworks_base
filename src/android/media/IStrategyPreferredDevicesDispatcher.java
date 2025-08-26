package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IStrategyPreferredDevicesDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IStrategyPreferredDevicesDispatcher";

    public static class Default implements IStrategyPreferredDevicesDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IStrategyPreferredDevicesDispatcher
        public void dispatchPrefDevicesChanged(int i, List<AudioDeviceAttributes> list) throws RemoteException {
        }
    }

    void dispatchPrefDevicesChanged(int i, List<AudioDeviceAttributes> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IStrategyPreferredDevicesDispatcher {
        static final int TRANSACTION_dispatchPrefDevicesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
        }

        public static IStrategyPreferredDevicesDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStrategyPreferredDevicesDispatcher)) {
                return (IStrategyPreferredDevicesDispatcher) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchPrefDevicesChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                dispatchPrefDevicesChanged(i3, arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStrategyPreferredDevicesDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStrategyPreferredDevicesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStrategyPreferredDevicesDispatcher
            public void dispatchPrefDevicesChanged(int i, List<AudioDeviceAttributes> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStrategyPreferredDevicesDispatcher.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
