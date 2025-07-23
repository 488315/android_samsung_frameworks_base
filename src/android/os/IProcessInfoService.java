package android.os;

/* loaded from: classes3.dex */
public interface IProcessInfoService extends IInterface {

    public static class Default implements IProcessInfoService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IProcessInfoService
        public void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws RemoteException {
        }

        @Override // android.os.IProcessInfoService
        public void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws RemoteException {
        }
    }

    void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws RemoteException;

    void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws RemoteException;

    public static abstract class Stub extends Binder implements IProcessInfoService {
        public static final String DESCRIPTOR = "android.os.IProcessInfoService";
        static final int TRANSACTION_getProcessStatesAndOomScoresFromPids = 2;
        static final int TRANSACTION_getProcessStatesFromPids = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IProcessInfoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProcessInfoService)) {
                return (IProcessInfoService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getProcessStatesFromPids";
            }
            if (i != 2) {
                return null;
            }
            return "getProcessStatesAndOomScoresFromPids";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int[] iArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] createIntArray = parcel.createIntArray();
                int readInt = parcel.readInt();
                if (readInt > 1000000) {
                    throw new BadParcelableException("Array too large: " + readInt);
                }
                iArr = readInt >= 0 ? new int[readInt] : null;
                parcel.enforceNoDataAvail();
                getProcessStatesFromPids(createIntArray, iArr);
                parcel2.writeNoException();
                parcel2.writeIntArray(iArr);
            } else if (i == 2) {
                int[] createIntArray2 = parcel.createIntArray();
                int readInt2 = parcel.readInt();
                if (readInt2 > 1000000) {
                    throw new BadParcelableException("Array too large: " + readInt2);
                }
                int[] iArr2 = readInt2 < 0 ? null : new int[readInt2];
                int readInt3 = parcel.readInt();
                if (readInt3 > 1000000) {
                    throw new BadParcelableException("Array too large: " + readInt3);
                }
                iArr = readInt3 >= 0 ? new int[readInt3] : null;
                parcel.enforceNoDataAvail();
                getProcessStatesAndOomScoresFromPids(createIntArray2, iArr2, iArr);
                parcel2.writeNoException();
                parcel2.writeIntArray(iArr2);
                parcel2.writeIntArray(iArr);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProcessInfoService {
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

            @Override // android.os.IProcessInfoService
            public void getProcessStatesFromPids(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(iArr2.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IProcessInfoService
            public void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(iArr2.length);
                    obtain.writeInt(iArr3.length);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr2);
                    obtain2.readIntArray(iArr3);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
