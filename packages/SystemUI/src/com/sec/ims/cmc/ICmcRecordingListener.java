package com.sec.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICmcRecordingListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.cmc.ICmcRecordingListener";

    void onError(int i, int i2) throws RemoteException;

    void onInfo(int i, int i2) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ICmcRecordingListener {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onInfo = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ICmcRecordingListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICmcRecordingListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.cmc.ICmcRecordingListener
            public void onError(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICmcRecordingListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcRecordingListener
            public void onInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICmcRecordingListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICmcRecordingListener.DESCRIPTOR);
        }

        public static ICmcRecordingListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICmcRecordingListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICmcRecordingListener)) ? new Proxy(iBinder) : (ICmcRecordingListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICmcRecordingListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICmcRecordingListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onInfo(readInt, readInt2);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt3, readInt4);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ICmcRecordingListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.cmc.ICmcRecordingListener
        public void onError(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.cmc.ICmcRecordingListener
        public void onInfo(int i, int i2) throws RemoteException {
        }
    }
}
