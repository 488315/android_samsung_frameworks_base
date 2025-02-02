package com.sec.android.diagmonagent.sa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDMAInterface extends IInterface {

    public static abstract class Stub extends Binder implements IDMAInterface {

        private static class Proxy implements IDMAInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final String checkToken() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendCommon(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public final int sendLog(String str, String str2, long j, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IDMAInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.android.diagmonagent.sa.IDMAInterface");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDMAInterface)) ? new Proxy(iBinder) : (IDMAInterface) queryLocalInterface;
        }
    }

    String checkToken() throws RemoteException;

    int sendCommon(String str, String str2, String str3) throws RemoteException;

    int sendLog(String str, String str2, long j, String str3) throws RemoteException;
}
