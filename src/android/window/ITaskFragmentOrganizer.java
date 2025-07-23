package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ITaskFragmentOrganizer extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskFragmentOrganizer";

    public static class Default implements ITaskFragmentOrganizer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
        }
    }

    void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskFragmentOrganizer {
        static final int TRANSACTION_onTransactionReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITaskFragmentOrganizer.DESCRIPTOR);
        }

        public static ITaskFragmentOrganizer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITaskFragmentOrganizer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITaskFragmentOrganizer)) {
                return (ITaskFragmentOrganizer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTransactionReady";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITaskFragmentOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskFragmentOrganizer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                TaskFragmentTransaction taskFragmentTransaction = (TaskFragmentTransaction) parcel.readTypedObject(TaskFragmentTransaction.CREATOR);
                parcel.enforceNoDataAvail();
                onTransactionReady(taskFragmentTransaction);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITaskFragmentOrganizer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskFragmentOrganizer.DESCRIPTOR;
            }

            @Override // android.window.ITaskFragmentOrganizer
            public void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizer.DESCRIPTOR);
                    obtain.writeTypedObject(taskFragmentTransaction, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
