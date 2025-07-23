package android.os;

/* loaded from: classes3.dex */
public interface IIncidentReportStatusListener extends IInterface {
    public static final int STATUS_FINISHED = 2;
    public static final int STATUS_STARTING = 1;

    public static class Default implements IIncidentReportStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIncidentReportStatusListener
        public void onReportFailed() throws RemoteException {
        }

        @Override // android.os.IIncidentReportStatusListener
        public void onReportFinished() throws RemoteException {
        }

        @Override // android.os.IIncidentReportStatusListener
        public void onReportSectionStatus(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IIncidentReportStatusListener
        public void onReportStarted() throws RemoteException {
        }
    }

    void onReportFailed() throws RemoteException;

    void onReportFinished() throws RemoteException;

    void onReportSectionStatus(int i, int i2) throws RemoteException;

    void onReportStarted() throws RemoteException;

    public static abstract class Stub extends Binder implements IIncidentReportStatusListener {
        public static final String DESCRIPTOR = "android.os.IIncidentReportStatusListener";
        static final int TRANSACTION_onReportFailed = 4;
        static final int TRANSACTION_onReportFinished = 3;
        static final int TRANSACTION_onReportSectionStatus = 2;
        static final int TRANSACTION_onReportStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentReportStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncidentReportStatusListener)) {
                return (IIncidentReportStatusListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onReportStarted";
            }
            if (i == 2) {
                return "onReportSectionStatus";
            }
            if (i == 3) {
                return "onReportFinished";
            }
            if (i != 4) {
                return null;
            }
            return "onReportFailed";
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
                onReportStarted();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onReportSectionStatus(readInt, readInt2);
            } else if (i == 3) {
                onReportFinished();
            } else if (i == 4) {
                onReportFailed();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIncidentReportStatusListener {
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

            @Override // android.os.IIncidentReportStatusListener
            public void onReportStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentReportStatusListener
            public void onReportSectionStatus(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentReportStatusListener
            public void onReportFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentReportStatusListener
            public void onReportFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
