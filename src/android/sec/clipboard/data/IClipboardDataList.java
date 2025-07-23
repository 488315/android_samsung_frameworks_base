package android.sec.clipboard.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.knox.analytics.database.Contract;

/* loaded from: classes3.dex */
public interface IClipboardDataList extends IInterface {
    public static final String DESCRIPTOR = "android.sec.clipboard.data.IClipboardDataList";

    public static class Default implements IClipboardDataList {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public SemClipData getClipByID(String str) throws RemoteException {
            return null;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public SemClipData getItem(int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public boolean removeData(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public int size() throws RemoteException {
            return 0;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public boolean updateData(int i, SemClipData semClipData) throws RemoteException {
            return false;
        }
    }

    SemClipData getClipByID(String str) throws RemoteException;

    SemClipData getItem(int i) throws RemoteException;

    boolean removeData(int i) throws RemoteException;

    int size() throws RemoteException;

    boolean updateData(int i, SemClipData semClipData) throws RemoteException;

    public static abstract class Stub extends Binder implements IClipboardDataList {
        static final int TRANSACTION_getClipByID = 5;
        static final int TRANSACTION_getItem = 2;
        static final int TRANSACTION_removeData = 3;
        static final int TRANSACTION_size = 1;
        static final int TRANSACTION_updateData = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IClipboardDataList.DESCRIPTOR);
        }

        public static IClipboardDataList asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClipboardDataList.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClipboardDataList)) {
                return (IClipboardDataList) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return Contract.DatabaseSize.PATH;
            }
            if (i == 2) {
                return "getItem";
            }
            if (i == 3) {
                return "removeData";
            }
            if (i == 4) {
                return "updateData";
            }
            if (i != 5) {
                return null;
            }
            return "getClipByID";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IClipboardDataList.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClipboardDataList.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int size = size();
                parcel2.writeNoException();
                parcel2.writeInt(size);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                SemClipData item = getItem(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedObject(item, 1);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean removeData = removeData(readInt2);
                parcel2.writeNoException();
                parcel2.writeBoolean(removeData);
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                parcel.enforceNoDataAvail();
                boolean updateData = updateData(readInt3, semClipData);
                parcel2.writeNoException();
                parcel2.writeBoolean(updateData);
            } else if (i == 5) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                SemClipData clipByID = getClipByID(readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(clipByID, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IClipboardDataList {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClipboardDataList.DESCRIPTOR;
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public int size() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getItem(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemClipData) obtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean removeData(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean updateData(int i, SemClipData semClipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(semClipData, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getClipByID(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemClipData) obtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
