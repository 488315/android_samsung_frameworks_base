package android.app;

import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUriGrantsManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUriGrantsManager";

    public static class Default implements IUriGrantsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public int checkGrantUriPermission_ignoreNonSystem(int i, String str, Uri uri, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IUriGrantsManager
        public void clearGrantedUriPermissions(String str, int i) throws RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public ParceledListSlice getGrantedUriPermissions(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) throws RemoteException {
            return null;
        }

        @Override // android.app.IUriGrantsManager
        public void grantUriPermissionFromOwner(IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public void releasePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.IUriGrantsManager
        public void takePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException {
        }
    }

    int checkGrantUriPermission_ignoreNonSystem(int i, String str, Uri uri, int i2, int i3) throws RemoteException;

    void clearGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getGrantedUriPermissions(String str, int i) throws RemoteException;

    ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) throws RemoteException;

    void grantUriPermissionFromOwner(IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4) throws RemoteException;

    void releasePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    void takePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUriGrantsManager {
        static final int TRANSACTION_checkGrantUriPermission_ignoreNonSystem = 7;
        static final int TRANSACTION_clearGrantedUriPermissions = 5;
        static final int TRANSACTION_getGrantedUriPermissions = 4;
        static final int TRANSACTION_getUriPermissions = 6;
        static final int TRANSACTION_grantUriPermissionFromOwner = 3;
        static final int TRANSACTION_releasePersistableUriPermission = 2;
        static final int TRANSACTION_takePersistableUriPermission = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IUriGrantsManager.DESCRIPTOR);
        }

        public static IUriGrantsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUriGrantsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUriGrantsManager)) {
                return (IUriGrantsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "takePersistableUriPermission";
                case 2:
                    return "releasePersistableUriPermission";
                case 3:
                    return "grantUriPermissionFromOwner";
                case 4:
                    return "getGrantedUriPermissions";
                case 5:
                    return "clearGrantedUriPermissions";
                case 6:
                    return "getUriPermissions";
                case 7:
                    return "checkGrantUriPermission_ignoreNonSystem";
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
                parcel.enforceInterface(IUriGrantsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUriGrantsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    takePersistableUriPermission(uri, readInt, readString, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePersistableUriPermission(uri2, readInt3, readString2, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    String readString3 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantUriPermissionFromOwner(readStrongBinder, readInt5, readString3, uri3, readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice grantedUriPermissions = getGrantedUriPermissions(readString4, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(grantedUriPermissions, 1);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearGrantedUriPermissions(readString5, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice uriPermissions = getUriPermissions(readString6, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(uriPermissions, 1);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    String readString7 = parcel.readString();
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkGrantUriPermission_ignoreNonSystem = checkGrantUriPermission_ignoreNonSystem(readInt11, readString7, uri4, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkGrantUriPermission_ignoreNonSystem);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUriGrantsManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUriGrantsManager.DESCRIPTOR;
            }

            @Override // android.app.IUriGrantsManager
            public void takePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void releasePersistableUriPermission(Uri uri, int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void grantUriPermissionFromOwner(IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public ParceledListSlice getGrantedUriPermissions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public void clearGrantedUriPermissions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUriGrantsManager
            public int checkGrantUriPermission_ignoreNonSystem(int i, String str, Uri uri, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUriGrantsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
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
