package android.os.storage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IStorageEventListener extends IInterface {

    public static class Default implements IStorageEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskScanned(DiskInfo diskInfo, int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onStorageStateChanged(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onUsbMassStorageConnectionChanged(boolean z) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeForgotten(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) throws RemoteException {
        }
    }

    void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException;

    void onDiskScanned(DiskInfo diskInfo, int i) throws RemoteException;

    void onStorageStateChanged(String str, String str2, String str3) throws RemoteException;

    void onUsbMassStorageConnectionChanged(boolean z) throws RemoteException;

    void onVolumeForgotten(String str) throws RemoteException;

    void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException;

    void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IStorageEventListener {
        public static final String DESCRIPTOR = "android.os.storage.IStorageEventListener";
        static final int TRANSACTION_onDiskDestroyed = 7;
        static final int TRANSACTION_onDiskScanned = 6;
        static final int TRANSACTION_onStorageStateChanged = 2;
        static final int TRANSACTION_onUsbMassStorageConnectionChanged = 1;
        static final int TRANSACTION_onVolumeForgotten = 5;
        static final int TRANSACTION_onVolumeRecordChanged = 4;
        static final int TRANSACTION_onVolumeStateChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStorageEventListener)) {
                return (IStorageEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUsbMassStorageConnectionChanged";
                case 2:
                    return "onStorageStateChanged";
                case 3:
                    return "onVolumeStateChanged";
                case 4:
                    return "onVolumeRecordChanged";
                case 5:
                    return "onVolumeForgotten";
                case 6:
                    return "onDiskScanned";
                case 7:
                    return "onDiskDestroyed";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUsbMassStorageConnectionChanged(z);
                    return true;
                case 2:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onStorageStateChanged(string, string2, string3);
                    return true;
                case 3:
                    VolumeInfo volumeInfo = (VolumeInfo) parcel.readTypedObject(VolumeInfo.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeStateChanged(volumeInfo, i3, i4);
                    return true;
                case 4:
                    VolumeRecord volumeRecord = (VolumeRecord) parcel.readTypedObject(VolumeRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVolumeRecordChanged(volumeRecord);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeForgotten(string4);
                    return true;
                case 6:
                    DiskInfo diskInfo = (DiskInfo) parcel.readTypedObject(DiskInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDiskScanned(diskInfo, i5);
                    return true;
                case 7:
                    DiskInfo diskInfo2 = (DiskInfo) parcel.readTypedObject(DiskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDiskDestroyed(diskInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IStorageEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.storage.IStorageEventListener
            public void onUsbMassStorageConnectionChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onStorageStateChanged(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(volumeInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeRecordChanged(VolumeRecord volumeRecord) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(volumeRecord, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeForgotten(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onDiskScanned(DiskInfo diskInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(diskInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onDiskDestroyed(DiskInfo diskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(diskInfo, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
