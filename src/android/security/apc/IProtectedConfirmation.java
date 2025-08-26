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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProtectedConfirmation.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProtectedConfirmation)) {
                return (IProtectedConfirmation) iInterfaceQueryLocalInterface;
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
                IConfirmationCallback iConfirmationCallbackAsInterface = IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                presentPrompt(iConfirmationCallbackAsInterface, string, bArrCreateByteArray, string2, i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                IConfirmationCallback iConfirmationCallbackAsInterface2 = IConfirmationCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                cancelPrompt(iConfirmationCallbackAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean zIsSupported = isSupported();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsSupported);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConfirmationCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void cancelPrompt(IConfirmationCallback iConfirmationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConfirmationCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public boolean isSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
