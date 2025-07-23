package android.security.legacykeystore;

import android.app.slice.Slice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILegacyKeystore extends IInterface {
    public static final String DESCRIPTOR = "android.security.legacykeystore.ILegacyKeystore";
    public static final int ERROR_ENTRY_NOT_FOUND = 7;
    public static final int ERROR_PERMISSION_DENIED = 6;
    public static final int ERROR_SYSTEM_ERROR = 4;
    public static final int UID_SELF = -1;

    public static class Default implements ILegacyKeystore {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public byte[] get(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public String[] list(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void put(String str, int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.security.legacykeystore.ILegacyKeystore
        public void remove(String str, int i) throws RemoteException {
        }
    }

    byte[] get(String str, int i) throws RemoteException;

    String[] list(String str, int i) throws RemoteException;

    void put(String str, int i, byte[] bArr) throws RemoteException;

    void remove(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ILegacyKeystore {
        static final int TRANSACTION_get = 1;
        static final int TRANSACTION_list = 4;
        static final int TRANSACTION_put = 2;
        static final int TRANSACTION_remove = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ILegacyKeystore.DESCRIPTOR);
        }

        public static ILegacyKeystore asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILegacyKeystore.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILegacyKeystore)) {
                return (ILegacyKeystore) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "get";
            }
            if (i == 2) {
                return "put";
            }
            if (i == 3) {
                return "remove";
            }
            if (i != 4) {
                return null;
            }
            return Slice.HINT_LIST;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILegacyKeystore.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILegacyKeystore.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                byte[] bArr = get(readString, readInt);
                parcel2.writeNoException();
                parcel2.writeByteArray(bArr);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt2 = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                put(readString2, readInt2, createByteArray);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString3 = parcel.readString();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                remove(readString3, readInt3);
                parcel2.writeNoException();
            } else if (i == 4) {
                String readString4 = parcel.readString();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String[] list = list(readString4, readInt4);
                parcel2.writeNoException();
                parcel2.writeStringArray(list);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILegacyKeystore {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILegacyKeystore.DESCRIPTOR;
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public byte[] get(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void put(String str, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public void remove(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.legacykeystore.ILegacyKeystore
            public String[] list(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILegacyKeystore.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
