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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPermissionController)) {
                return (IPermissionController) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zCheckPermission = checkPermission(string, i3, i4);
                parcel2.writeNoException();
                parcel2.writeBoolean(zCheckPermission);
            } else if (i == 2) {
                String string2 = parcel.readString();
                int i5 = parcel.readInt();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                int iNoteOp = noteOp(string2, i5, string3);
                parcel2.writeNoException();
                parcel2.writeInt(iNoteOp);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String[] packagesForUid = getPackagesForUid(i6);
                parcel2.writeNoException();
                parcel2.writeStringArray(packagesForUid);
            } else if (i == 4) {
                String string4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zIsRuntimePermission = isRuntimePermission(string4);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsRuntimePermission);
            } else if (i == 5) {
                String string5 = parcel.readString();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int packageUid = getPackageUid(string5, i7);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int noteOp(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public String[] getPackagesForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public boolean isRuntimePermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPermissionController
            public int getPackageUid(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
