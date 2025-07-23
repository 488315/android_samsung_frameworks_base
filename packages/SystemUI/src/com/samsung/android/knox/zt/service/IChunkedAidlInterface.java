package com.samsung.android.knox.zt.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IChunkedAidlInterface extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IChunkedAidlInterface";

    void sendChunk(String str, int i, boolean z) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IChunkedAidlInterface {
        public static final int TRANSACTION_sendChunk = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IChunkedAidlInterface {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IChunkedAidlInterface.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.service.IChunkedAidlInterface
            public void sendChunk(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IChunkedAidlInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IChunkedAidlInterface.DESCRIPTOR);
        }

        public static IChunkedAidlInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IChunkedAidlInterface.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IChunkedAidlInterface)) ? new Proxy(iBinder) : (IChunkedAidlInterface) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IChunkedAidlInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IChunkedAidlInterface.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            sendChunk(parcel.readString(), parcel.readInt(), parcel.readInt() != 0);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IChunkedAidlInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IChunkedAidlInterface
        public void sendChunk(String str, int i, boolean z) throws RemoteException {
        }
    }
}
