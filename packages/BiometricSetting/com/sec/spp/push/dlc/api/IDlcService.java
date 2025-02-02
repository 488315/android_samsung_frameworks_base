package com.sec.spp.push.dlc.api;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDlcService extends IInterface {

    public static abstract class Stub extends Binder implements IDlcService {

        private static class Proxy implements IDlcService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.spp.push.dlc.api.IDlcService
            public final int requestSend(String str, String str2, long j, String str3, String str4) throws RemoteException {
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

        public static IDlcService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.spp.push.dlc.api.IDlcService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDlcService)) ? new Proxy(iBinder) : (IDlcService) queryLocalInterface;
        }
    }

    int requestSend(String str, String str2, long j, String str3, String str4) throws RemoteException;
}
