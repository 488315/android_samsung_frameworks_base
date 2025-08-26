package android.os;

import java.util.List;

/* loaded from: classes3.dex */
public interface IIdmap2 extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIdmap2";

    public static class Default implements IIdmap2 {
        @Override // android.os.IIdmap2
        public int acquireFabricatedOverlayIterator() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIdmap2
        public FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) throws RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public String createIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public boolean deleteFabricatedOverlay(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IIdmap2
        public String dumpIdmap(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public String getIdmapPath(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IIdmap2
        public void releaseFabricatedOverlayIterator(int i) throws RemoteException {
        }

        @Override // android.os.IIdmap2
        public boolean removeIdmap(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IIdmap2
        public boolean verifyIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
            return false;
        }
    }

    int acquireFabricatedOverlayIterator() throws RemoteException;

    FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) throws RemoteException;

    String createIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException;

    boolean deleteFabricatedOverlay(String str) throws RemoteException;

    String dumpIdmap(String str) throws RemoteException;

    String getIdmapPath(String str, int i) throws RemoteException;

    List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws RemoteException;

    void releaseFabricatedOverlayIterator(int i) throws RemoteException;

    boolean removeIdmap(String str, int i) throws RemoteException;

    boolean verifyIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IIdmap2 {
        static final int TRANSACTION_acquireFabricatedOverlayIterator = 7;
        static final int TRANSACTION_createFabricatedOverlay = 5;
        static final int TRANSACTION_createIdmap = 4;
        static final int TRANSACTION_deleteFabricatedOverlay = 6;
        static final int TRANSACTION_dumpIdmap = 10;
        static final int TRANSACTION_getIdmapPath = 1;
        static final int TRANSACTION_nextFabricatedOverlayInfos = 9;
        static final int TRANSACTION_releaseFabricatedOverlayIterator = 8;
        static final int TRANSACTION_removeIdmap = 2;
        static final int TRANSACTION_verifyIdmap = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IIdmap2.DESCRIPTOR);
        }

        public static IIdmap2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIdmap2.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIdmap2)) {
                return (IIdmap2) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getIdmapPath";
                case 2:
                    return "removeIdmap";
                case 3:
                    return "verifyIdmap";
                case 4:
                    return "createIdmap";
                case 5:
                    return "createFabricatedOverlay";
                case 6:
                    return "deleteFabricatedOverlay";
                case 7:
                    return "acquireFabricatedOverlayIterator";
                case 8:
                    return "releaseFabricatedOverlayIterator";
                case 9:
                    return "nextFabricatedOverlayInfos";
                case 10:
                    return "dumpIdmap";
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
                parcel.enforceInterface(IIdmap2.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIdmap2.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String idmapPath = getIdmapPath(string, i3);
                    parcel2.writeNoException();
                    parcel2.writeString(idmapPath);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveIdmap = removeIdmap(string2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveIdmap);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i5 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    OverlayConstraint[] overlayConstraintArr = (OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zVerifyIdmap = verifyIdmap(string3, string4, string5, i5, z, i6, overlayConstraintArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zVerifyIdmap);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i7 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    int i8 = parcel.readInt();
                    OverlayConstraint[] overlayConstraintArr2 = (OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strCreateIdmap = createIdmap(string6, string7, string8, i7, z2, i8, overlayConstraintArr2);
                    parcel2.writeNoException();
                    parcel2.writeString(strCreateIdmap);
                    return true;
                case 5:
                    FabricatedOverlayInternal fabricatedOverlayInternal = (FabricatedOverlayInternal) parcel.readTypedObject(FabricatedOverlayInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    FabricatedOverlayInfo fabricatedOverlayInfoCreateFabricatedOverlay = createFabricatedOverlay(fabricatedOverlayInternal);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fabricatedOverlayInfoCreateFabricatedOverlay, 1);
                    return true;
                case 6:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteFabricatedOverlay = deleteFabricatedOverlay(string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteFabricatedOverlay);
                    return true;
                case 7:
                    int iAcquireFabricatedOverlayIterator = acquireFabricatedOverlayIterator();
                    parcel2.writeNoException();
                    parcel2.writeInt(iAcquireFabricatedOverlayIterator);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFabricatedOverlayIterator(i9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<FabricatedOverlayInfo> listNextFabricatedOverlayInfos = nextFabricatedOverlayInfos(i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listNextFabricatedOverlayInfos, 1);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strDumpIdmap = dumpIdmap(string10);
                    parcel2.writeNoException();
                    parcel2.writeString(strDumpIdmap);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIdmap2 {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIdmap2.DESCRIPTOR;
            }

            @Override // android.os.IIdmap2
            public String getIdmapPath(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean removeIdmap(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean verifyIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(overlayConstraintArr, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public String createIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(overlayConstraintArr, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fabricatedOverlayInternal, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FabricatedOverlayInfo) parcelObtain2.readTypedObject(FabricatedOverlayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean deleteFabricatedOverlay(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public int acquireFabricatedOverlayIterator() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public void releaseFabricatedOverlayIterator(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(FabricatedOverlayInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public String dumpIdmap(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
