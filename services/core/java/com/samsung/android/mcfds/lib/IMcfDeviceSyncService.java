package com.samsung.android.mcfds.lib;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public interface IMcfDeviceSyncService extends IInterface {

    public class Default implements IMcfDeviceSyncService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mcfds.lib.IMcfDeviceSyncService
        public int internalCommand(Message message) {
            return 0;
        }
    }

    int internalCommand(Message message);

    int internalCommandWithReturnValue(Message message, Bundle bundle);

    public abstract class Stub extends Binder implements IMcfDeviceSyncService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
        }

        public static IMcfDeviceSyncService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMcfDeviceSyncService)) {
                return (IMcfDeviceSyncService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
                return true;
            }
            if (i == 1) {
                int internalCommand = internalCommand((Message) _Parcel.readTypedObject(parcel, Message.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(internalCommand);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                Message message = (Message) _Parcel.readTypedObject(parcel, Message.CREATOR);
                Bundle bundle = new Bundle();
                int internalCommandWithReturnValue = internalCommandWithReturnValue(message, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(internalCommandWithReturnValue);
                _Parcel.writeTypedObject(parcel2, bundle, 1);
            }
            return true;
        }

        public class Proxy implements IMcfDeviceSyncService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.mcfds.lib.IMcfDeviceSyncService
            public int internalCommand(Message message) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
                    _Parcel.writeTypedObject(obtain, message, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
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

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable != null) {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
