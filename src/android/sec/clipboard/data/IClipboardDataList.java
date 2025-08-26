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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IClipboardDataList.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IClipboardDataList)) {
                return (IClipboardDataList) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                SemClipData item = getItem(i3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(item, 1);
            } else if (i == 3) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zRemoveData = removeData(i4);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRemoveData);
            } else if (i == 4) {
                int i5 = parcel.readInt();
                SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                parcel.enforceNoDataAvail();
                boolean zUpdateData = updateData(i5, semClipData);
                parcel2.writeNoException();
                parcel2.writeBoolean(zUpdateData);
            } else if (i == 5) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                SemClipData clipByID = getClipByID(string);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getItem(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemClipData) parcelObtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean removeData(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean updateData(int i, SemClipData semClipData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(semClipData, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getClipByID(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemClipData) parcelObtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
