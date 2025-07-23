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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIdmap2.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIdmap2)) {
                return (IIdmap2) queryLocalInterface;
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
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String idmapPath = getIdmapPath(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(idmapPath);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeIdmap = removeIdmap(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIdmap);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    OverlayConstraint[] overlayConstraintArr = (OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean verifyIdmap = verifyIdmap(readString3, readString4, readString5, readInt3, readBoolean, readInt4, overlayConstraintArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyIdmap);
                    return true;
                case 4:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    OverlayConstraint[] overlayConstraintArr2 = (OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR);
                    parcel.enforceNoDataAvail();
                    String createIdmap = createIdmap(readString6, readString7, readString8, readInt5, readBoolean2, readInt6, overlayConstraintArr2);
                    parcel2.writeNoException();
                    parcel2.writeString(createIdmap);
                    return true;
                case 5:
                    FabricatedOverlayInternal fabricatedOverlayInternal = (FabricatedOverlayInternal) parcel.readTypedObject(FabricatedOverlayInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    FabricatedOverlayInfo createFabricatedOverlay = createFabricatedOverlay(fabricatedOverlayInternal);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createFabricatedOverlay, 1);
                    return true;
                case 6:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteFabricatedOverlay = deleteFabricatedOverlay(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteFabricatedOverlay);
                    return true;
                case 7:
                    int acquireFabricatedOverlayIterator = acquireFabricatedOverlayIterator();
                    parcel2.writeNoException();
                    parcel2.writeInt(acquireFabricatedOverlayIterator);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFabricatedOverlayIterator(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<FabricatedOverlayInfo> nextFabricatedOverlayInfos = nextFabricatedOverlayInfos(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nextFabricatedOverlayInfos, 1);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String dumpIdmap = dumpIdmap(readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(dumpIdmap);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean removeIdmap(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean verifyIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(overlayConstraintArr, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public String createIdmap(String str, String str2, String str3, int i, boolean z, int i2, OverlayConstraint[] overlayConstraintArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(overlayConstraintArr, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeTypedObject(fabricatedOverlayInternal, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FabricatedOverlayInfo) obtain2.readTypedObject(FabricatedOverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public boolean deleteFabricatedOverlay(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public int acquireFabricatedOverlayIterator() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public void releaseFabricatedOverlayIterator(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(FabricatedOverlayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIdmap2
            public String dumpIdmap(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIdmap2.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
