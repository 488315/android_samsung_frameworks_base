package android.os;

/* loaded from: classes3.dex */
public interface IIncidentAuthListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIncidentAuthListener";

    public static class Default implements IIncidentAuthListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIncidentAuthListener
        public void onReportApproved() throws RemoteException {
        }

        @Override // android.os.IIncidentAuthListener
        public void onReportDenied() throws RemoteException {
        }
    }

    void onReportApproved() throws RemoteException;

    void onReportDenied() throws RemoteException;

    public static abstract class Stub extends Binder implements IIncidentAuthListener {
        static final int TRANSACTION_onReportApproved = 1;
        static final int TRANSACTION_onReportDenied = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IIncidentAuthListener.DESCRIPTOR);
        }

        public static IIncidentAuthListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIncidentAuthListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncidentAuthListener)) {
                return (IIncidentAuthListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onReportApproved";
            }
            if (i != 2) {
                return null;
            }
            return "onReportDenied";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIncidentAuthListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIncidentAuthListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onReportApproved();
            } else if (i == 2) {
                onReportDenied();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIncidentAuthListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncidentAuthListener.DESCRIPTOR;
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportApproved() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentAuthListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportDenied() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentAuthListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
