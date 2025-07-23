package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IInputFilterHost;

/* loaded from: classes4.dex */
public interface IInputFilter extends IInterface {

    public static class Default implements IInputFilter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IInputFilter
        public void filterInputEvent(InputEvent inputEvent, int i) throws RemoteException {
        }

        @Override // android.view.IInputFilter
        public void install(IInputFilterHost iInputFilterHost) throws RemoteException {
        }

        @Override // android.view.IInputFilter
        public void uninstall() throws RemoteException {
        }
    }

    void filterInputEvent(InputEvent inputEvent, int i) throws RemoteException;

    void install(IInputFilterHost iInputFilterHost) throws RemoteException;

    void uninstall() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputFilter {
        public static final String DESCRIPTOR = "android.view.IInputFilter";
        static final int TRANSACTION_filterInputEvent = 3;
        static final int TRANSACTION_install = 1;
        static final int TRANSACTION_uninstall = 2;

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

        public static IInputFilter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputFilter)) {
                return (IInputFilter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "install";
            }
            if (i == 2) {
                return "uninstall";
            }
            if (i != 3) {
                return null;
            }
            return "filterInputEvent";
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
                IInputFilterHost asInterface = IInputFilterHost.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                install(asInterface);
            } else if (i == 2) {
                uninstall();
            } else if (i == 3) {
                InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                filterInputEvent(inputEvent, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputFilter {
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

            @Override // android.view.IInputFilter
            public void install(IInputFilterHost iInputFilterHost) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputFilterHost);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IInputFilter
            public void uninstall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IInputFilter
            public void filterInputEvent(InputEvent inputEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
