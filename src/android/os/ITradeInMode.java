package android.os;

/* loaded from: classes3.dex */
public interface ITradeInMode extends IInterface {
    public static final String DESCRIPTOR = "android.os.ITradeInMode";

    public static class Default implements ITradeInMode {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ITradeInMode
        public boolean enterEvaluationMode() throws RemoteException {
            return false;
        }

        @Override // android.os.ITradeInMode
        public boolean isEvaluationModeAllowed() throws RemoteException {
            return false;
        }

        @Override // android.os.ITradeInMode
        public boolean isTesting() throws RemoteException {
            return false;
        }

        @Override // android.os.ITradeInMode
        public void scheduleWipeForTesting() throws RemoteException {
        }

        @Override // android.os.ITradeInMode
        public boolean start() throws RemoteException {
            return false;
        }

        @Override // android.os.ITradeInMode
        public void startTesting() throws RemoteException {
        }

        @Override // android.os.ITradeInMode
        public void stopTesting() throws RemoteException {
        }
    }

    boolean enterEvaluationMode() throws RemoteException;

    boolean isEvaluationModeAllowed() throws RemoteException;

    boolean isTesting() throws RemoteException;

    void scheduleWipeForTesting() throws RemoteException;

    boolean start() throws RemoteException;

    void startTesting() throws RemoteException;

    void stopTesting() throws RemoteException;

    public static abstract class Stub extends Binder implements ITradeInMode {
        static final int TRANSACTION_enterEvaluationMode = 3;
        static final int TRANSACTION_isEvaluationModeAllowed = 2;
        static final int TRANSACTION_isTesting = 7;
        static final int TRANSACTION_scheduleWipeForTesting = 4;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_startTesting = 5;
        static final int TRANSACTION_stopTesting = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ITradeInMode.DESCRIPTOR);
        }

        public static ITradeInMode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITradeInMode.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITradeInMode)) {
                return (ITradeInMode) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "start";
                case 2:
                    return "isEvaluationModeAllowed";
                case 3:
                    return "enterEvaluationMode";
                case 4:
                    return "scheduleWipeForTesting";
                case 5:
                    return "startTesting";
                case 6:
                    return "stopTesting";
                case 7:
                    return "isTesting";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITradeInMode.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITradeInMode.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean start = start();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(start);
                    return true;
                case 2:
                    boolean isEvaluationModeAllowed = isEvaluationModeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEvaluationModeAllowed);
                    return true;
                case 3:
                    boolean enterEvaluationMode = enterEvaluationMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enterEvaluationMode);
                    return true;
                case 4:
                    scheduleWipeForTesting();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    startTesting();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopTesting();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isTesting = isTesting();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTesting);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITradeInMode {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITradeInMode.DESCRIPTOR;
            }

            @Override // android.os.ITradeInMode
            public boolean start() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean isEvaluationModeAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean enterEvaluationMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void scheduleWipeForTesting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void startTesting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void stopTesting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean isTesting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
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
