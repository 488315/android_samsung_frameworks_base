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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISession)) {
                return (ISession) iInterfaceQueryLocalInterface;
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
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setReaderEphemeralPublicKey(bArrCreateByteArray);
                parcel2.writeNoException();
            } else if (i == 4) {
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                setSessionTranscript(bArrCreateByteArray2);
                parcel2.writeNoException();
            } else if (i == 5) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                ICredential credentialForPresentation = getCredentialForPresentation(string);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public long getAuthChallenge() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public void setSessionTranscript(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ISession
            public ICredential getCredentialForPresentation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICredential.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
