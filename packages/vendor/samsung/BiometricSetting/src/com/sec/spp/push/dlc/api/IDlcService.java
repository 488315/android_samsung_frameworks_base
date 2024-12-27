package com.sec.spp.push.dlc.api;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public interface IDlcService extends IInterface {

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public abstract class Stub extends Binder implements IDlcService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
        public final class Proxy implements IDlcService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final int requestSend(
                    String str, String str2, long j, String str3, String str4) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.spp.push.dlc.api.IDlcService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    obtain.writeString("0");
                    obtain.writeString("");
                    obtain.writeString("6.05.015");
                    obtain.writeString(str4);
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
