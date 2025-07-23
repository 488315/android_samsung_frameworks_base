package android.security.apc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.apc.IConfirmationCallback;

/* loaded from: classes3.dex */
public interface IProtectedConfirmation extends IInterface {
    public static final String DESCRIPTOR = "android.security.apc.IProtectedConfirmation";
    public static final int FLAG_UI_OPTION_INVERTED = 1;
    public static final int FLAG_UI_OPTION_MAGNIFIED = 2;

    public static class Default implements IProtectedConfirmation {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.apc.IProtectedConfirmation
        public void cancelPrompt(IConfirmationCallback iConfirmationCallback) throws RemoteException {
        }

        @Override // android.security.apc.IProtectedConfirmation
        public boolean isSupported() throws RemoteException {
            return false;
        }

        @Override // android.security.apc.IProtectedConfirmation
        public void presentPrompt(IConfirmationCallback iConfirmationCallback, String str, byte[] bArr, String str2, int i) throws RemoteException {
        }
    }

    void cancelPrompt(IConfirmationCallback iConfirmationCallback) throws RemoteException;

    boolean isSupported() throws RemoteException;

    void presentPrompt(IConfirmationCallback iConfirmationCallback, String str, byte[] bArr, String str2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IProtectedConfirmation {
        static final int TRANSACTION_cancelPrompt = 2;
        static final int TRANSACTION_isSupported = 3;
        static final int TRANSACTION_presentPrompt = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IProtectedConfirmation.DESCRIPTOR);
        }

        public static IProtectedConfirmation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProtectedConfirmation.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProtectedConfirmation)) {
                return (IProtectedConfirmation) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProtectedConfirmation.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProtectedConfirmation.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IConfirmationCallback asInterface = IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                byte[] createByteArray = parcel.createByteArray();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                presentPrompt(asInterface, readString, createByteArray, readString2, readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                IConfirmationCallback asInterface2 = IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                cancelPrompt(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean isSupported = isSupported();
                parcel2.writeNoException();
                parcel2.writeBoolean(isSupported);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProtectedConfirmation {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProtectedConfirmation.DESCRIPTOR;
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void presentPrompt(IConfirmationCallback iConfirmationCallback, String str, byte[] bArr, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    obtain.writeStrongInterface(iConfirmationCallback);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void cancelPrompt(IConfirmationCallback iConfirmationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    obtain.writeStrongInterface(iConfirmationCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public boolean isSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
