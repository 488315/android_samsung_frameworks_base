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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProcessInfoService)) {
                return (IProcessInfoService) iInterfaceQueryLocalInterface;
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
                int[] iArrCreateIntArray = parcel.createIntArray();
                int i3 = parcel.readInt();
                if (i3 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i3);
                }
                iArr = i3 >= 0 ? new int[i3] : null;
                parcel.enforceNoDataAvail();
                getProcessStatesFromPids(iArrCreateIntArray, iArr);
                parcel2.writeNoException();
                parcel2.writeIntArray(iArr);
            } else if (i == 2) {
                int[] iArrCreateIntArray2 = parcel.createIntArray();
                int i4 = parcel.readInt();
                if (i4 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i4);
                }
                int[] iArr2 = i4 < 0 ? null : new int[i4];
                int i5 = parcel.readInt();
                if (i5 > 1000000) {
                    throw new BadParcelableException("Array too large: " + i5);
                }
                iArr = i5 >= 0 ? new int[i5] : null;
                parcel.enforceNoDataAvail();
                getProcessStatesAndOomScoresFromPids(iArrCreateIntArray2, iArr2, iArr);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(iArr2.length);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readIntArray(iArr2);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IProcessInfoService
            public void getProcessStatesAndOomScoresFromPids(int[] iArr, int[] iArr2, int[] iArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(iArr2.length);
                    parcelObtain.writeInt(iArr3.length);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readIntArray(iArr2);
                    parcelObtain2.readIntArray(iArr3);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
