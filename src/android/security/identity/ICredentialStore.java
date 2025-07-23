package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.identity.ICredential;
import android.security.identity.ISession;
import android.security.identity.IWritableCredential;

/* loaded from: classes3.dex */
public interface ICredentialStore extends IInterface {
    public static final String DESCRIPTOR = "android.security.identity.ICredentialStore";
    public static final int ERROR_ALREADY_PERSONALIZED = 2;
    public static final int ERROR_AUTHENTICATION_KEY_NOT_FOUND = 9;
    public static final int ERROR_CIPHER_SUITE_NOT_SUPPORTED = 4;
    public static final int ERROR_DOCUMENT_TYPE_NOT_SUPPORTED = 8;
    public static final int ERROR_EPHEMERAL_PUBLIC_KEY_NOT_FOUND = 5;
    public static final int ERROR_GENERIC = 1;
    public static final int ERROR_INVALID_ITEMS_REQUEST_MESSAGE = 10;
    public static final int ERROR_INVALID_READER_SIGNATURE = 7;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 12;
    public static final int ERROR_NO_AUTHENTICATION_KEY_AVAILABLE = 6;
    public static final int ERROR_NO_SUCH_CREDENTIAL = 3;
    public static final int ERROR_SESSION_TRANSCRIPT_MISMATCH = 11;

    public static class Default implements ICredentialStore {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public IWritableCredential createCredential(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public ISession createPresentationSession(int i) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public ICredential getCredentialByName(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredentialStore
        public SecurityHardwareInfoParcel getSecurityHardwareInfo() throws RemoteException {
            return null;
        }
    }

    IWritableCredential createCredential(String str, String str2) throws RemoteException;

    ISession createPresentationSession(int i) throws RemoteException;

    ICredential getCredentialByName(String str, int i) throws RemoteException;

    SecurityHardwareInfoParcel getSecurityHardwareInfo() throws RemoteException;

    public static abstract class Stub extends Binder implements ICredentialStore {
        static final int TRANSACTION_createCredential = 2;
        static final int TRANSACTION_createPresentationSession = 4;
        static final int TRANSACTION_getCredentialByName = 3;
        static final int TRANSACTION_getSecurityHardwareInfo = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ICredentialStore.DESCRIPTOR);
        }

        public static ICredentialStore asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialStore.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICredentialStore)) {
                return (ICredentialStore) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSecurityHardwareInfo";
            }
            if (i == 2) {
                return "createCredential";
            }
            if (i == 3) {
                return "getCredentialByName";
            }
            if (i != 4) {
                return null;
            }
            return "createPresentationSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialStore.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialStore.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SecurityHardwareInfoParcel securityHardwareInfo = getSecurityHardwareInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(securityHardwareInfo, 1);
            } else if (i == 2) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                IWritableCredential createCredential = createCredential(readString, readString2);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(createCredential);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ICredential credentialByName = getCredentialByName(readString3, readInt);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(credentialByName);
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ISession createPresentationSession = createPresentationSession(readInt2);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(createPresentationSession);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICredentialStore {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialStore.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredentialStore
            public SecurityHardwareInfoParcel getSecurityHardwareInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStore.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SecurityHardwareInfoParcel) obtain2.readTypedObject(SecurityHardwareInfoParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public IWritableCredential createCredential(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWritableCredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public ICredential getCredentialByName(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ICredentialStore
            public ISession createPresentationSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStore.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
