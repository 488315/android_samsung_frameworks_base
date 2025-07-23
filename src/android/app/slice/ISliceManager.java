package android.app.slice;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISliceManager extends IInterface {

    public static class Default implements ISliceManager {
        @Override // android.app.slice.ISliceManager
        public void applyRestore(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public int checkSlicePermission(Uri uri, String str, int i, int i2, String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // android.app.slice.ISliceManager
        public byte[] getBackupPayload(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public Uri[] getPinnedSlices(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public SliceSpec[] getPinnedSpecs(Uri uri, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.slice.ISliceManager
        public void grantPermissionFromUser(Uri uri, String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void grantSlicePermission(String str, String str2, Uri uri) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public boolean hasSliceAccess(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.slice.ISliceManager
        public void pinSlice(String str, Uri uri, SliceSpec[] sliceSpecArr, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void revokeSlicePermission(String str, String str2, Uri uri) throws RemoteException {
        }

        @Override // android.app.slice.ISliceManager
        public void unpinSlice(String str, Uri uri, IBinder iBinder) throws RemoteException {
        }
    }

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    int checkSlicePermission(Uri uri, String str, int i, int i2, String[] strArr) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    Uri[] getPinnedSlices(String str) throws RemoteException;

    SliceSpec[] getPinnedSpecs(Uri uri, String str) throws RemoteException;

    void grantPermissionFromUser(Uri uri, String str, String str2, boolean z) throws RemoteException;

    void grantSlicePermission(String str, String str2, Uri uri) throws RemoteException;

    boolean hasSliceAccess(String str) throws RemoteException;

    void pinSlice(String str, Uri uri, SliceSpec[] sliceSpecArr, IBinder iBinder) throws RemoteException;

    void revokeSlicePermission(String str, String str2, Uri uri) throws RemoteException;

    void unpinSlice(String str, Uri uri, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements ISliceManager {
        public static final String DESCRIPTOR = "android.app.slice.ISliceManager";
        static final int TRANSACTION_applyRestore = 7;
        static final int TRANSACTION_checkSlicePermission = 10;
        static final int TRANSACTION_getBackupPayload = 6;
        static final int TRANSACTION_getPinnedSlices = 5;
        static final int TRANSACTION_getPinnedSpecs = 4;
        static final int TRANSACTION_grantPermissionFromUser = 11;
        static final int TRANSACTION_grantSlicePermission = 8;
        static final int TRANSACTION_hasSliceAccess = 3;
        static final int TRANSACTION_pinSlice = 1;
        static final int TRANSACTION_revokeSlicePermission = 9;
        static final int TRANSACTION_unpinSlice = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISliceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISliceManager)) {
                return (ISliceManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pinSlice";
                case 2:
                    return "unpinSlice";
                case 3:
                    return "hasSliceAccess";
                case 4:
                    return "getPinnedSpecs";
                case 5:
                    return "getPinnedSlices";
                case 6:
                    return "getBackupPayload";
                case 7:
                    return "applyRestore";
                case 8:
                    return "grantSlicePermission";
                case 9:
                    return "revokeSlicePermission";
                case 10:
                    return "checkSlicePermission";
                case 11:
                    return "grantPermissionFromUser";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    SliceSpec[] sliceSpecArr = (SliceSpec[]) parcel.createTypedArray(SliceSpec.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    pinSlice(readString, uri, sliceSpecArr, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unpinSlice(readString2, uri2, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasSliceAccess = hasSliceAccess(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSliceAccess);
                    return true;
                case 4:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SliceSpec[] pinnedSpecs = getPinnedSpecs(uri3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pinnedSpecs, 1);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri[] pinnedSlices = getPinnedSlices(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(pinnedSlices, 1);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 7:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantSlicePermission(readString6, readString7, uri4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeSlicePermission(readString8, readString9, uri5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    Uri uri6 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString10 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int checkSlicePermission = checkSlicePermission(uri6, readString10, readInt3, readInt4, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSlicePermission);
                    return true;
                case 11:
                    Uri uri7 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    grantPermissionFromUser(uri7, readString11, readString12, readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISliceManager {
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

            @Override // android.app.slice.ISliceManager
            public void pinSlice(String str, Uri uri, SliceSpec[] sliceSpecArr, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedArray(sliceSpecArr, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void unpinSlice(String str, Uri uri, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public boolean hasSliceAccess(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public SliceSpec[] getPinnedSpecs(Uri uri, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SliceSpec[]) obtain2.createTypedArray(SliceSpec.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public Uri[] getPinnedSlices(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri[]) obtain2.createTypedArray(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void applyRestore(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantSlicePermission(String str, String str2, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void revokeSlicePermission(String str, String str2, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public int checkSlicePermission(Uri uri, String str, int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.slice.ISliceManager
            public void grantPermissionFromUser(Uri uri, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
