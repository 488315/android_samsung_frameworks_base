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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWindowId)) {
                return (IWindowId) queryLocalInterface;
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
                IWindowFocusObserver asInterface = IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerFocusObserver(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IWindowFocusObserver asInterface2 = IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterFocusObserver(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean isFocused = isFocused();
                parcel2.writeNoException();
                parcel2.writeBoolean(isFocused);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public boolean isFocused() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
