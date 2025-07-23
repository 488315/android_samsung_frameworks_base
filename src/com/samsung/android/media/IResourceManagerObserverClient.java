package com.samsung.android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IResourceManagerObserverClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.IResourceManagerObserverClient";

    public static class Default implements IResourceManagerObserverClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserverClient
        public void notify(int i, int i2, int i3, MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel) throws RemoteException {
        }
    }

    void notify(int i, int i2, int i3, MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IResourceManagerObserverClient {
        static final int TRANSACTION_notify = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IResourceManagerObserverClient.DESCRIPTOR);
        }

        public static IResourceManagerObserverClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResourceManagerObserverClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourceManagerObserverClient)) {
                return (IResourceManagerObserverClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "notify";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResourceManagerObserverClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourceManagerObserverClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel = (MediaResourceNotifyInfoParcel) parcel.readTypedObject(MediaResourceNotifyInfoParcel.CREATOR);
                parcel.enforceNoDataAvail();
                notify(readInt, readInt2, readInt3, mediaResourceNotifyInfoParcel);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IResourceManagerObserverClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerObserverClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.IResourceManagerObserverClient
            public void notify(int i, int i2, int i3, MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IResourceManagerObserverClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(mediaResourceNotifyInfoParcel, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
