package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.identity.ICredential;

/* loaded from: classes3.dex */
public interface ISession extends IInterface {
    public static final String DESCRIPTOR = "android.security.identity.ISession";

    public static class Default implements ISession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.identity.ISession
        public long getAuthChallenge() throws RemoteException {
            return 0L;
        }

        @Override // android.security.identity.ISession
        public ICredential getCredentialForPresentation(String str) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ISession
        public byte[] getEphemeralKeyPair() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ISession
        public void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException {
        }

        @Override // android.security.identity.ISession
        public void setSessionTranscript(byte[] bArr) throws RemoteException {
        }
    }

    long getAuthChallenge() throws RemoteException;

    ICredential getCredentialForPresentation(String str) throws RemoteException;

    byte[] getEphemeralKeyPair() throws RemoteException;

    void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException;

    void setSessionTranscript(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISession {
        static final int TRANSACTION_getAuthChallenge = 2;
        static final int TRANSACTION_getCredentialForPresentation = 5;
        static final int TRANSACTION_getEphemeralKeyPair = 1;
        static final int TRANSACTION_setReaderEphemeralPublicKey = 3;
        static final int TRANSACTION_setSessionTranscript = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISession.DESCRIPTOR);
        }

        public static ISession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISession)) {
                return (ISession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getEphemeralKeyPair";
            }
            if (i == 2) {
                return "getAuthChallenge";
            }
            if (i == 3) {
                return "setReaderEphemeralPublicKey";
            }
            if (i == 4) {
                return "setSessionTranscript";
            }
            if (i != 5) {
                return null;
            }
            return "getCredentialForPresentation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISession.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] ephemeralKeyPair = getEphemeralKeyPair();
                parcel2.writeNoException();
                parcel2.writeByteArray(ephemeralKeyPair);
            } else if (i == 2) {
                long authChallenge = getAuthChallenge();
                parcel2.writeNoException();
                parcel2.writeLong(authChallenge);
            } else if (i == 3) {
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setReaderEphemeralPublicKey(createByteArray);
                parcel2.writeNoException();
            } else if (i == 4) {
                byte[] createByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setSessionTranscript(createByteArray2);
                parcel2.writeNoException();
            } else if (i == 5) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                ICredential credentialForPresentation = getCredentialForPresentation(readString);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(credentialForPresentation);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISession.DESCRIPTOR;
            }

            @Override // android.security.identity.ISession
            public byte[] getEphemeralKeyPair() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public long getAuthChallenge() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setSessionTranscript(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public ICredential getCredentialForPresentation(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICredential.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
