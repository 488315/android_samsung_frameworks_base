package android.accessibilityservice;

import android.accessibilityservice.IBrailleDisplayConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBrailleDisplayController extends IInterface {
    public static final String DESCRIPTOR = "android.accessibilityservice.IBrailleDisplayController";

    public static class Default implements IBrailleDisplayController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(byte[] bArr) throws RemoteException {
        }
    }

    void onConnected(IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws RemoteException;

    void onConnectionFailed(int i) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onInput(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IBrailleDisplayController {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onConnectionFailed = 2;
        static final int TRANSACTION_onDisconnected = 4;
        static final int TRANSACTION_onInput = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IBrailleDisplayController.DESCRIPTOR);
        }

        public static IBrailleDisplayController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBrailleDisplayController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBrailleDisplayController)) {
                return (IBrailleDisplayController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnected";
            }
            if (i == 2) {
                return "onConnectionFailed";
            }
            if (i == 3) {
                return "onInput";
            }
            if (i != 4) {
                return null;
            }
            return "onDisconnected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBrailleDisplayController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBrailleDisplayController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBrailleDisplayConnection iBrailleDisplayConnectionAsInterface = IBrailleDisplayConnection.Stub.asInterface(parcel.readStrongBinder());
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onConnected(iBrailleDisplayConnectionAsInterface, bArrCreateByteArray);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onConnectionFailed(i3);
            } else if (i == 3) {
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onInput(bArrCreateByteArray2);
            } else if (i == 4) {
                onDisconnected();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBrailleDisplayController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBrailleDisplayController.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnected(IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBrailleDisplayConnection);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnectionFailed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onInput(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
