package android.app.blob;

import android.app.blob.IBlobStoreSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes.dex */
public interface IBlobStoreManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.blob.IBlobStoreManager";

    public static class Default implements IBlobStoreManager {
        @Override // android.app.blob.IBlobStoreManager
        public void abandonSession(long j, String str) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public long createSession(BlobHandle blobHandle, String str) throws RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreManager
        public void deleteBlob(long j) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public List<BlobHandle> getLeasedBlobs(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public long getRemainingLeaseQuotaBytes(String str) throws RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreManager
        public ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public IBlobStoreSession openSession(long j, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public List<BlobInfo> queryBlobsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public void releaseAllLeases(String str) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void releaseLease(BlobHandle blobHandle, String str) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void waitForIdle(RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    void abandonSession(long j, String str) throws RemoteException;

    void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) throws RemoteException;

    long createSession(BlobHandle blobHandle, String str) throws RemoteException;

    void deleteBlob(long j) throws RemoteException;

    LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) throws RemoteException;

    List<BlobHandle> getLeasedBlobs(String str) throws RemoteException;

    long getRemainingLeaseQuotaBytes(String str) throws RemoteException;

    ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) throws RemoteException;

    IBlobStoreSession openSession(long j, String str) throws RemoteException;

    List<BlobInfo> queryBlobsForUser(int i) throws RemoteException;

    void releaseAllLeases(String str) throws RemoteException;

    void releaseLease(BlobHandle blobHandle, String str) throws RemoteException;

    void waitForIdle(RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBlobStoreManager {
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_acquireLease = 5;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_deleteBlob = 11;
        static final int TRANSACTION_getLeaseInfo = 13;
        static final int TRANSACTION_getLeasedBlobs = 12;
        static final int TRANSACTION_getRemainingLeaseQuotaBytes = 8;
        static final int TRANSACTION_openBlob = 3;
        static final int TRANSACTION_openSession = 2;
        static final int TRANSACTION_queryBlobsForUser = 10;
        static final int TRANSACTION_releaseAllLeases = 7;
        static final int TRANSACTION_releaseLease = 6;
        static final int TRANSACTION_waitForIdle = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IBlobStoreManager.DESCRIPTOR);
        }

        public static IBlobStoreManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBlobStoreManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBlobStoreManager)) {
                return (IBlobStoreManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "openSession";
                case 3:
                    return "openBlob";
                case 4:
                    return "abandonSession";
                case 5:
                    return "acquireLease";
                case 6:
                    return "releaseLease";
                case 7:
                    return "releaseAllLeases";
                case 8:
                    return "getRemainingLeaseQuotaBytes";
                case 9:
                    return "waitForIdle";
                case 10:
                    return "queryBlobsForUser";
                case 11:
                    return "deleteBlob";
                case 12:
                    return "getLeasedBlobs";
                case 13:
                    return "getLeaseInfo";
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
                parcel.enforceInterface(IBlobStoreManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBlobStoreManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    BlobHandle blobHandle = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long createSession = createSession(blobHandle, readString);
                    parcel2.writeNoException();
                    parcel2.writeLong(createSession);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBlobStoreSession openSession = openSession(readLong, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 3:
                    BlobHandle blobHandle2 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openBlob = openBlob(blobHandle2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openBlob, 1);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    abandonSession(readLong2, readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    BlobHandle blobHandle3 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    int readInt = parcel.readInt();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    long readLong3 = parcel.readLong();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireLease(blobHandle3, readInt, charSequence, readLong3, readString5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    BlobHandle blobHandle4 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseLease(blobHandle4, readString6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseAllLeases(readString7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long remainingLeaseQuotaBytes = getRemainingLeaseQuotaBytes(readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(remainingLeaseQuotaBytes);
                    return true;
                case 9:
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    waitForIdle(remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<BlobInfo> queryBlobsForUser = queryBlobsForUser(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(queryBlobsForUser, 1);
                    return true;
                case 11:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteBlob(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<BlobHandle> leasedBlobs = getLeasedBlobs(readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(leasedBlobs, 1);
                    return true;
                case 13:
                    BlobHandle blobHandle5 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LeaseInfo leaseInfo = getLeaseInfo(blobHandle5, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(leaseInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBlobStoreManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBlobStoreManager.DESCRIPTOR;
            }

            @Override // android.app.blob.IBlobStoreManager
            public long createSession(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public IBlobStoreSession openSession(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return IBlobStoreSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void abandonSession(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseLease(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseAllLeases(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public long getRemainingLeaseQuotaBytes(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void waitForIdle(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public List<BlobInfo> queryBlobsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BlobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void deleteBlob(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public List<BlobHandle> getLeasedBlobs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BlobHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LeaseInfo) obtain2.readTypedObject(LeaseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
