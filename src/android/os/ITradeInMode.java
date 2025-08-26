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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITradeInMode.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITradeInMode)) {
                return (ITradeInMode) iInterfaceQueryLocalInterface;
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
                    boolean zStart = start();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStart);
                    return true;
                case 2:
                    boolean zIsEvaluationModeAllowed = isEvaluationModeAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEvaluationModeAllowed);
                    return true;
                case 3:
                    boolean zEnterEvaluationMode = enterEvaluationMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnterEvaluationMode);
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
                    boolean zIsTesting = isTesting();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTesting);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean isEvaluationModeAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean enterEvaluationMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void scheduleWipeForTesting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void startTesting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public void stopTesting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ITradeInMode
            public boolean isTesting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITradeInMode.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
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
