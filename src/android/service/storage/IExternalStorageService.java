package android.service.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.storage.StorageVolume;

/* loaded from: classes3.dex */
public interface IExternalStorageService extends IInterface {
    public static final String DESCRIPTOR = "android.service.storage.IExternalStorageService";

    public static class Default implements IExternalStorageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.storage.IExternalStorageService
        public void endSession(String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void freeCache(String str, String str2, long j, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyAnrDelayStarted(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyVolumeStateChanged(String str, StorageVolume storageVolume, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void startSession(String str, int i, ParcelFileDescriptor parcelFileDescriptor, String str2, String str3, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    void endSession(String str, RemoteCallback remoteCallback) throws RemoteException;

    void freeCache(String str, String str2, long j, RemoteCallback remoteCallback) throws RemoteException;

    void notifyAnrDelayStarted(String str, int i, int i2, int i3) throws RemoteException;

    void notifyVolumeStateChanged(String str, StorageVolume storageVolume, RemoteCallback remoteCallback) throws RemoteException;

    void startSession(String str, int i, ParcelFileDescriptor parcelFileDescriptor, String str2, String str3, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IExternalStorageService {
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_freeCache = 4;
        static final int TRANSACTION_notifyAnrDelayStarted = 5;
        static final int TRANSACTION_notifyVolumeStateChanged = 3;
        static final int TRANSACTION_startSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IExternalStorageService.DESCRIPTOR);
        }

        public static IExternalStorageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExternalStorageService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExternalStorageService)) {
                return (IExternalStorageService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startSession";
            }
            if (i == 2) {
                return "endSession";
            }
            if (i == 3) {
                return "notifyVolumeStateChanged";
            }
            if (i == 4) {
                return "freeCache";
            }
            if (i != 5) {
                return null;
            }
            return "notifyAnrDelayStarted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExternalStorageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExternalStorageService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                startSession(string, i3, parcelFileDescriptor, string2, string3, remoteCallback);
            } else if (i == 2) {
                String string4 = parcel.readString();
                RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                endSession(string4, remoteCallback2);
            } else if (i == 3) {
                String string5 = parcel.readString();
                StorageVolume storageVolume = (StorageVolume) parcel.readTypedObject(StorageVolume.CREATOR);
                RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                notifyVolumeStateChanged(string5, storageVolume, remoteCallback3);
            } else if (i == 4) {
                String string6 = parcel.readString();
                String string7 = parcel.readString();
                long j = parcel.readLong();
                RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                freeCache(string6, string7, j, remoteCallback4);
            } else if (i == 5) {
                String string8 = parcel.readString();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyAnrDelayStarted(string8, i4, i5, i6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IExternalStorageService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExternalStorageService.DESCRIPTOR;
            }

            @Override // android.service.storage.IExternalStorageService
            public void startSession(String str, int i, ParcelFileDescriptor parcelFileDescriptor, String str2, String str3, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExternalStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void endSession(String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExternalStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void notifyVolumeStateChanged(String str, StorageVolume storageVolume, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExternalStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(storageVolume, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void freeCache(String str, String str2, long j, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExternalStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void notifyAnrDelayStarted(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExternalStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
