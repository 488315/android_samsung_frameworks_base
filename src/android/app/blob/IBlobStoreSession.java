package android.app.blob;

import android.app.blob.IBlobCommitCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBlobStoreSession extends IInterface {
    public static final String DESCRIPTOR = "android.app.blob.IBlobStoreSession";

    public static class Default implements IBlobStoreSession {
        @Override // android.app.blob.IBlobStoreSession
        public void abandon() throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowPackageAccess(String str, byte[] bArr) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowPublicAccess() throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowSameSignatureAccess() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.blob.IBlobStoreSession
        public void close() throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void commit(IBlobCommitCallback iBlobCommitCallback) throws RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public long getSize() throws RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isPackageAccessAllowed(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isPublicAccessAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isSameSignatureAccessAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public ParcelFileDescriptor openRead() throws RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreSession
        public ParcelFileDescriptor openWrite(long j, long j2) throws RemoteException {
            return null;
        }
    }

    void abandon() throws RemoteException;

    void allowPackageAccess(String str, byte[] bArr) throws RemoteException;

    void allowPublicAccess() throws RemoteException;

    void allowSameSignatureAccess() throws RemoteException;

    void close() throws RemoteException;

    void commit(IBlobCommitCallback iBlobCommitCallback) throws RemoteException;

    long getSize() throws RemoteException;

    boolean isPackageAccessAllowed(String str, byte[] bArr) throws RemoteException;

    boolean isPublicAccessAllowed() throws RemoteException;

    boolean isSameSignatureAccessAllowed() throws RemoteException;

    ParcelFileDescriptor openRead() throws RemoteException;

    ParcelFileDescriptor openWrite(long j, long j2) throws RemoteException;

    public static abstract class Stub extends Binder implements IBlobStoreSession {
        static final int TRANSACTION_abandon = 11;
        static final int TRANSACTION_allowPackageAccess = 3;
        static final int TRANSACTION_allowPublicAccess = 5;
        static final int TRANSACTION_allowSameSignatureAccess = 4;
        static final int TRANSACTION_close = 10;
        static final int TRANSACTION_commit = 12;
        static final int TRANSACTION_getSize = 9;
        static final int TRANSACTION_isPackageAccessAllowed = 6;
        static final int TRANSACTION_isPublicAccessAllowed = 8;
        static final int TRANSACTION_isSameSignatureAccessAllowed = 7;
        static final int TRANSACTION_openRead = 2;
        static final int TRANSACTION_openWrite = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IBlobStoreSession.DESCRIPTOR);
        }

        public static IBlobStoreSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBlobStoreSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBlobStoreSession)) {
                return (IBlobStoreSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openWrite";
                case 2:
                    return "openRead";
                case 3:
                    return "allowPackageAccess";
                case 4:
                    return "allowSameSignatureAccess";
                case 5:
                    return "allowPublicAccess";
                case 6:
                    return "isPackageAccessAllowed";
                case 7:
                    return "isSameSignatureAccessAllowed";
                case 8:
                    return "isPublicAccessAllowed";
                case 9:
                    return "getSize";
                case 10:
                    return "close";
                case 11:
                    return "abandon";
                case 12:
                    return "commit";
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
                parcel.enforceInterface(IBlobStoreSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBlobStoreSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openWrite = openWrite(readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openWrite, 1);
                    return true;
                case 2:
                    ParcelFileDescriptor openRead = openRead();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openRead, 1);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    allowPackageAccess(readString, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    allowSameSignatureAccess();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    allowPublicAccess();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString2 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAccessAllowed = isPackageAccessAllowed(readString2, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAccessAllowed);
                    return true;
                case 7:
                    boolean isSameSignatureAccessAllowed = isSameSignatureAccessAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSameSignatureAccessAllowed);
                    return true;
                case 8:
                    boolean isPublicAccessAllowed = isPublicAccessAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPublicAccessAllowed);
                    return true;
                case 9:
                    long size = getSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(size);
                    return true;
                case 10:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    abandon();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBlobCommitCallback asInterface = IBlobCommitCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    commit(asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBlobStoreSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBlobStoreSession.DESCRIPTOR;
            }

            @Override // android.app.blob.IBlobStoreSession
            public ParcelFileDescriptor openWrite(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public ParcelFileDescriptor openRead() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowPackageAccess(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowSameSignatureAccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowPublicAccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isPackageAccessAllowed(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isSameSignatureAccessAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isPublicAccessAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public long getSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void abandon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void commit(IBlobCommitCallback iBlobCommitCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlobStoreSession.DESCRIPTOR);
                    obtain.writeStrongInterface(iBlobCommitCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
