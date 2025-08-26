package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IWindowFocusObserver;

/* loaded from: classes4.dex */
public interface IWindowId extends IInterface {

    public static class Default implements IWindowId {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IWindowId
        public boolean isFocused() throws RemoteException {
            return false;
        }

        @Override // android.view.IWindowId
        public void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException {
        }

        @Override // android.view.IWindowId
        public void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException {
        }
    }

    boolean isFocused() throws RemoteException;

    void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException;

    void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowId {
        public static final String DESCRIPTOR = "android.view.IWindowId";
        static final int TRANSACTION_isFocused = 3;
        static final int TRANSACTION_registerFocusObserver = 1;
        static final int TRANSACTION_unregisterFocusObserver = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindowId asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindowId)) {
                return (IWindowId) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerFocusObserver";
            }
            if (i == 2) {
                return "unregisterFocusObserver";
            }
            if (i != 3) {
                return null;
            }
            return "isFocused";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IWindowFocusObserver iWindowFocusObserverAsInterface = IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerFocusObserver(iWindowFocusObserverAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IWindowFocusObserver iWindowFocusObserverAsInterface2 = IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterFocusObserver(iWindowFocusObserverAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean zIsFocused = isFocused();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsFocused);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWindowId {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowId
            public void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public boolean isFocused() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
