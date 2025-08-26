package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDevicesForAttributesCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.IDevicesForAttributesCallback";

    public static class Default implements IDevicesForAttributesCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IDevicesForAttributesCallback
        public void onDevicesForAttributesChanged(AudioAttributes audioAttributes, boolean z, List<AudioDeviceAttributes> list) throws RemoteException {
        }
    }

    void onDevicesForAttributesChanged(AudioAttributes audioAttributes, boolean z, List<AudioDeviceAttributes> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IDevicesForAttributesCallback {
        static final int TRANSACTION_onDevicesForAttributesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDevicesForAttributesCallback.DESCRIPTOR);
        }

        public static IDevicesForAttributesCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDevicesForAttributesCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDevicesForAttributesCallback)) {
                return (IDevicesForAttributesCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDevicesForAttributesChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDevicesForAttributesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDevicesForAttributesCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                boolean z = parcel.readBoolean();
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AudioDeviceAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                onDevicesForAttributesChanged(audioAttributes, z, arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDevicesForAttributesCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDevicesForAttributesCallback.DESCRIPTOR;
            }

            @Override // android.media.IDevicesForAttributesCallback
            public void onDevicesForAttributesChanged(AudioAttributes audioAttributes, boolean z, List<AudioDeviceAttributes> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDevicesForAttributesCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
