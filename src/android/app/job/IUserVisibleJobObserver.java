package android.app.job;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUserVisibleJobObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.job.IUserVisibleJobObserver";

    public static class Default implements IUserVisibleJobObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.job.IUserVisibleJobObserver
        public void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) throws RemoteException {
        }
    }

    void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IUserVisibleJobObserver {
        static final int TRANSACTION_onUserVisibleJobStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUserVisibleJobObserver.DESCRIPTOR);
        }

        public static IUserVisibleJobObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUserVisibleJobObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUserVisibleJobObserver)) {
                return (IUserVisibleJobObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUserVisibleJobStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUserVisibleJobObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUserVisibleJobObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                UserVisibleJobSummary userVisibleJobSummary = (UserVisibleJobSummary) parcel.readTypedObject(UserVisibleJobSummary.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onUserVisibleJobStateChanged(userVisibleJobSummary, readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUserVisibleJobObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUserVisibleJobObserver.DESCRIPTOR;
            }

            @Override // android.app.job.IUserVisibleJobObserver
            public void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUserVisibleJobObserver.DESCRIPTOR);
                    obtain.writeTypedObject(userVisibleJobSummary, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
