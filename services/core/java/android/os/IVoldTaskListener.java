package android.os;

public interface IVoldTaskListener extends IInterface {

    public class Default implements IVoldTaskListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVoldTaskListener
        public void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException {}

        @Override // android.os.IVoldTaskListener
        public void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IVoldTaskListener {
        public static final String DESCRIPTOR = "android.os.IVoldTaskListener";
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onStatus = 1;

        public final class Proxy implements IVoldTaskListener {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.os.IVoldTaskListener
            public final void onFinished(int i, PersistableBundle persistableBundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldTaskListener
            public final void onStatus(int i, PersistableBundle persistableBundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoldTaskListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoldTaskListener)) {
                return (IVoldTaskListener) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                PersistableBundle persistableBundle =
                        (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                onStatus(readInt, persistableBundle);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int readInt2 = parcel.readInt();
                PersistableBundle persistableBundle2 =
                        (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                onFinished(readInt2, persistableBundle2);
            }
            return true;
        }
    }

    void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException;

    void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException;
}
