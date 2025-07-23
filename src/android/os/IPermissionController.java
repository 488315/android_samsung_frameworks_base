package android.os;

/* loaded from: classes3.dex */
public interface IPermissionController extends IInterface {

    public static class Default implements IPermissionController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IPermissionController
        public boolean checkPermission(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IPermissionController
        public int getPackageUid(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IPermissionController
        public String[] getPackagesForUid(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IPermissionController
        public boolean isRuntimePermission(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IPermissionController
        public int noteOp(String str, int i, String str2) throws RemoteException {
            return 0;
        }
    }

    boolean checkPermission(String str, int i, int i2) throws RemoteException;

    int getPackageUid(String str, int i) throws RemoteException;

    String[] getPackagesForUid(int i) throws RemoteException;

    boolean isRuntimePermission(String str) throws RemoteException;

    int noteOp(String str, int i, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPermissionController {
        public static final String DESCRIPTOR = "android.os.IPermissionController";
        static final int TRANSACTION_checkPermission = 1;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 3;
        static final int TRANSACTION_isRuntimePermission = 4;
        static final int TRANSACTION_noteOp = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPermissionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPermissionController)) {
                return (IPermissionController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "checkPermission";
            }
            if (i == 2) {
                return "noteOp";
            }
            if (i == 3) {
                return "getPackagesForUid";
            }
            if (i == 4) {
                return "isRuntimePermission";
            }
            if (i != 5) {
                return null;
            }
            return "getPackageUid";
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
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean checkPermission = checkPermission(readString, readInt, readInt2);
                parcel2.writeNoException();
                parcel2.writeBoolean(checkPermission);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt3 = parcel.readInt();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                int noteOp = noteOp(readString2, readInt3, readString3);
                parcel2.writeNoException();
                parcel2.writeInt(noteOp);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String[] packagesForUid = getPackagesForUid(readInt4);
                parcel2.writeNoException();
                parcel2.writeStringArray(packagesForUid);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isRuntimePermission = isRuntimePermission(readString4);
                parcel2.writeNoException();
                parcel2.writeBoolean(isRuntimePermission);
            } else if (i == 5) {
                String readString5 = parcel.readString();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int packageUid = getPackageUid(readString5, readInt5);
                parcel2.writeNoException();
                parcel2.writeInt(packageUid);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPermissionController {
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

            @Override // android.os.IPermissionController
            public boolean checkPermission(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int noteOp(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public String[] getPackagesForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public boolean isRuntimePermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int getPackageUid(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
