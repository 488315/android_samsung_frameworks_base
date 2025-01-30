package com.samsung.android.mcfds.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

/* loaded from: classes2.dex */
public interface IDeviceSyncCallback extends IInterface {

    public class Default implements IDeviceSyncCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void onDeviceFound(PersistableBundle persistableBundle);

    void onDeviceLost(PersistableBundle persistableBundle);

    void onDeviceUpdated(PersistableBundle persistableBundle);

    public abstract class Stub extends Binder implements IDeviceSyncCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.mcfds.lib.IDeviceSyncCallback");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.mcfds.lib.IDeviceSyncCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.mcfds.lib.IDeviceSyncCallback");
                return true;
            }
            if (i == 1) {
                onDeviceFound((PersistableBundle) _Parcel.readTypedObject(parcel, PersistableBundle.CREATOR));
            } else if (i == 2) {
                onDeviceUpdated((PersistableBundle) _Parcel.readTypedObject(parcel, PersistableBundle.CREATOR));
            } else if (i == 3) {
                onDeviceLost((PersistableBundle) _Parcel.readTypedObject(parcel, PersistableBundle.CREATOR));
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        public class Proxy implements IDeviceSyncCallback {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }

    public abstract class _Parcel {
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }
}
