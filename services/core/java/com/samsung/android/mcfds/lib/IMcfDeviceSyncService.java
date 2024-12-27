package com.samsung.android.mcfds.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;

public interface IMcfDeviceSyncService extends IInterface {

    public abstract class Stub extends Binder implements IMcfDeviceSyncService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final class Proxy implements IMcfDeviceSyncService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final int internalCommand(Message message) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "com.samsung.android.mcfds.lib.IMcfDeviceSyncService");
                    obtain.writeInt(1);
                    message.writeToParcel(obtain, 0);
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
}
