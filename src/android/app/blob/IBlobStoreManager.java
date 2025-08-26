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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBlobStoreManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBlobStoreManager)) {
                return (IBlobStoreManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jCreateSession = createSession(blobHandle, string);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCreateSession);
                    return true;
                case 2:
                    long j = parcel.readLong();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBlobStoreSession iBlobStoreSessionOpenSession = openSession(j, string2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iBlobStoreSessionOpenSession);
                    return true;
                case 3:
                    BlobHandle blobHandle2 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenBlob = openBlob(blobHandle2, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenBlob, 1);
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    abandonSession(j2, string4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    BlobHandle blobHandle3 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    int i3 = parcel.readInt();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    long j3 = parcel.readLong();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireLease(blobHandle3, i3, charSequence, j3, string5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    BlobHandle blobHandle4 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseLease(blobHandle4, string6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseAllLeases(string7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long remainingLeaseQuotaBytes = getRemainingLeaseQuotaBytes(string8);
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
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<BlobInfo> listQueryBlobsForUser = queryBlobsForUser(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listQueryBlobsForUser, 1);
                    return true;
                case 11:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteBlob(j4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<BlobHandle> leasedBlobs = getLeasedBlobs(string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(leasedBlobs, 1);
                    return true;
                case 13:
                    BlobHandle blobHandle5 = (BlobHandle) parcel.readTypedObject(BlobHandle.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LeaseInfo leaseInfo = getLeaseInfo(blobHandle5, string10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(blobHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public IBlobStoreSession openSession(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IBlobStoreSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(blobHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void abandonSession(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(blobHandle, 0);
                    parcelObtain.writeInt(i);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseLease(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(blobHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseAllLeases(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public long getRemainingLeaseQuotaBytes(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void waitForIdle(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public List<BlobInfo> queryBlobsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BlobInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void deleteBlob(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public List<BlobHandle> getLeasedBlobs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(BlobHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBlobStoreManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(blobHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LeaseInfo) parcelObtain2.readTypedObject(LeaseInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
