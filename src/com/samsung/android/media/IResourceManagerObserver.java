package com.samsung.android.media;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IResourceManagerObserver extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.IResourceManagerObserver";

    public static class Default implements IResourceManagerObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public void disconnect() throws RemoteException {
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public int enableObserver(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public GetCodecCapacityReturn getCodecCapacity(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public GetResourceInfoReturn getResourceInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public int setResourcePriority(int i) throws RemoteException {
            return 0;
        }
    }

    void disconnect() throws RemoteException;

    int enableObserver(int i, int i2) throws RemoteException;

    GetCodecCapacityReturn getCodecCapacity(int i) throws RemoteException;

    GetResourceInfoReturn getResourceInfo(int i) throws RemoteException;

    int setResourcePriority(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IResourceManagerObserver {
        static final int TRANSACTION_disconnect = 5;
        static final int TRANSACTION_enableObserver = 1;
        static final int TRANSACTION_getCodecCapacity = 3;
        static final int TRANSACTION_getResourceInfo = 2;
        static final int TRANSACTION_setResourcePriority = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IResourceManagerObserver.DESCRIPTOR);
        }

        public static IResourceManagerObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IResourceManagerObserver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IResourceManagerObserver)) {
                return (IResourceManagerObserver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "enableObserver";
            }
            if (i == 2) {
                return "getResourceInfo";
            }
            if (i == 3) {
                return "getCodecCapacity";
            }
            if (i == 4) {
                return "setResourcePriority";
            }
            if (i != 5) {
                return null;
            }
            return MediaMetrics.Value.DISCONNECT;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResourceManagerObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourceManagerObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iEnableObserver = enableObserver(i3, i4);
                parcel2.writeNoException();
                parcel2.writeInt(iEnableObserver);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                GetResourceInfoReturn resourceInfo = getResourceInfo(i5);
                parcel2.writeNoException();
                parcel2.writeTypedObject(resourceInfo, 1);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                GetCodecCapacityReturn codecCapacity = getCodecCapacity(i6);
                parcel2.writeNoException();
                parcel2.writeTypedObject(codecCapacity, 1);
            } else if (i == 4) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int resourcePriority = setResourcePriority(i7);
                parcel2.writeNoException();
                parcel2.writeInt(resourcePriority);
            } else if (i == 5) {
                disconnect();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IResourceManagerObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerObserver.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public int enableObserver(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public GetResourceInfoReturn getResourceInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetResourceInfoReturn) parcelObtain2.readTypedObject(GetResourceInfoReturn.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public GetCodecCapacityReturn getCodecCapacity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetCodecCapacityReturn) parcelObtain2.readTypedObject(GetCodecCapacityReturn.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public int setResourcePriority(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public void disconnect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
