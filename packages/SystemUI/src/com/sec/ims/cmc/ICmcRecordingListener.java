package com.sec.ims.cmc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICmcRecordingListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.cmc.ICmcRecordingListener";

    void onError(int i, int i2) throws RemoteException;

    void onInfo(int i, int i2) throws RemoteException;

    public abstract class Stub extends Binder implements ICmcRecordingListener {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onInfo = 1;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICmcRecordingListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.cmc.ICmcRecordingListener
            public void onInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICmcRecordingListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICmcRecordingListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICmcRecordingListener)) ? new Proxy(iBinder) : (ICmcRecordingListener) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onInfo(i3, i4);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(i5, i6);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

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
