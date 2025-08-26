package android.os;

/* loaded from: classes3.dex */
public interface IHintSession extends IInterface {
    public static final String DESCRIPTOR = "android.os.IHintSession";

    public static class Default implements IHintSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IHintSession
        public void associateToLayers(IBinder[] iBinderArr) throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void close() throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void reportActualWorkDuration(long[] jArr, long[] jArr2) throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void reportActualWorkDuration2(android.hardware.power.WorkDuration[] workDurationArr) throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void sendHint(int i) throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void setMode(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.IHintSession
        public void updateTargetWorkDuration(long j) throws RemoteException {
        }
    }

    void associateToLayers(IBinder[] iBinderArr) throws RemoteException;

    void close() throws RemoteException;

    void reportActualWorkDuration(long[] jArr, long[] jArr2) throws RemoteException;

    void reportActualWorkDuration2(android.hardware.power.WorkDuration[] workDurationArr) throws RemoteException;

    void sendHint(int i) throws RemoteException;

    void setMode(int i, boolean z) throws RemoteException;

    void updateTargetWorkDuration(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IHintSession {
        static final int TRANSACTION_associateToLayers = 7;
        static final int TRANSACTION_close = 3;
        static final int TRANSACTION_reportActualWorkDuration = 2;
        static final int TRANSACTION_reportActualWorkDuration2 = 6;
        static final int TRANSACTION_sendHint = 4;
        static final int TRANSACTION_setMode = 5;
        static final int TRANSACTION_updateTargetWorkDuration = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IHintSession.DESCRIPTOR);
        }

        public static IHintSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHintSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHintSession)) {
                return (IHintSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateTargetWorkDuration";
                case 2:
                    return "reportActualWorkDuration";
                case 3:
                    return "close";
                case 4:
                    return "sendHint";
                case 5:
                    return "setMode";
                case 6:
                    return "reportActualWorkDuration2";
                case 7:
                    return "associateToLayers";
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
                parcel.enforceInterface(IHintSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHintSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTargetWorkDuration(j);
                    return true;
                case 2:
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    long[] jArrCreateLongArray2 = parcel.createLongArray();
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration(jArrCreateLongArray, jArrCreateLongArray2);
                    return true;
                case 3:
                    close();
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendHint(i3);
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(i4, z);
                    return true;
                case 6:
                    android.hardware.power.WorkDuration[] workDurationArr = (android.hardware.power.WorkDuration[]) parcel.createTypedArray(android.hardware.power.WorkDuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportActualWorkDuration2(workDurationArr);
                    return true;
                case 7:
                    IBinder[] iBinderArrCreateBinderArray = parcel.createBinderArray();
                    parcel.enforceNoDataAvail();
                    associateToLayers(iBinderArrCreateBinderArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IHintSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHintSession.DESCRIPTOR;
            }

            @Override // android.os.IHintSession
            public void updateTargetWorkDuration(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void reportActualWorkDuration(long[] jArr, long[] jArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeLongArray(jArr);
                    parcelObtain.writeLongArray(jArr2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void sendHint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void setMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void reportActualWorkDuration2(android.hardware.power.WorkDuration[] workDurationArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeTypedArray(workDurationArr, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IHintSession
            public void associateToLayers(IBinder[] iBinderArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHintSession.DESCRIPTOR);
                    parcelObtain.writeBinderArray(iBinderArr);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
