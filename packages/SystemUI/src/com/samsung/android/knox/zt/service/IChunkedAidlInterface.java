package com.samsung.android.knox.zt.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IChunkedAidlInterface extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IChunkedAidlInterface";

    void sendChunk(String str, int i, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IChunkedAidlInterface {
        public static final int TRANSACTION_sendChunk = 1;

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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IChunkedAidlInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IChunkedAidlInterface.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IChunkedAidlInterface)) ? new Proxy(iBinder) : (IChunkedAidlInterface) iInterfaceQueryLocalInterface;
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
