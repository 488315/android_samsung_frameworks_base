package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IWritableCredential extends IInterface {
    public static final String DESCRIPTOR = "android.security.identity.IWritableCredential";

    public static class Default implements IWritableCredential {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.identity.IWritableCredential
        public byte[] getCredentialKeyCertificateChain(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.IWritableCredential
        public byte[] personalize(AccessControlProfileParcel[] accessControlProfileParcelArr, EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws RemoteException {
            return null;
        }
    }

    byte[] getCredentialKeyCertificateChain(byte[] bArr) throws RemoteException;

    byte[] personalize(AccessControlProfileParcel[] accessControlProfileParcelArr, EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IWritableCredential {
        static final int TRANSACTION_getCredentialKeyCertificateChain = 1;
        static final int TRANSACTION_personalize = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IWritableCredential.DESCRIPTOR);
        }

        public static IWritableCredential asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWritableCredential.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWritableCredential)) {
                return (IWritableCredential) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getCredentialKeyCertificateChain";
            }
            if (i != 2) {
                return null;
            }
            return "personalize";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWritableCredential.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWritableCredential.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                byte[] credentialKeyCertificateChain = getCredentialKeyCertificateChain(createByteArray);
                parcel2.writeNoException();
                parcel2.writeByteArray(credentialKeyCertificateChain);
            } else if (i == 2) {
                AccessControlProfileParcel[] accessControlProfileParcelArr = (AccessControlProfileParcel[]) parcel.createTypedArray(AccessControlProfileParcel.CREATOR);
                EntryNamespaceParcel[] entryNamespaceParcelArr = (EntryNamespaceParcel[]) parcel.createTypedArray(EntryNamespaceParcel.CREATOR);
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                byte[] personalize = personalize(accessControlProfileParcelArr, entryNamespaceParcelArr, readLong);
                parcel2.writeNoException();
                parcel2.writeByteArray(personalize);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWritableCredential {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWritableCredential.DESCRIPTOR;
            }

            @Override // android.security.identity.IWritableCredential
            public byte[] getCredentialKeyCertificateChain(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWritableCredential.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.IWritableCredential
            public byte[] personalize(AccessControlProfileParcel[] accessControlProfileParcelArr, EntryNamespaceParcel[] entryNamespaceParcelArr, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IWritableCredential.DESCRIPTOR);
                    obtain.writeTypedArray(accessControlProfileParcelArr, 0);
                    obtain.writeTypedArray(entryNamespaceParcelArr, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
