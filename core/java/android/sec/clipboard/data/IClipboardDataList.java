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

    SemClipData getClipByID(String str) throws RemoteException;

    SemClipData getItem(int i) throws RemoteException;

    boolean removeData(int i) throws RemoteException;

    int size() throws RemoteException;

    boolean updateData(int i, SemClipData semClipData) throws RemoteException;

    public static class Default implements IClipboardDataList {
        @Override // android.sec.clipboard.data.IClipboardDataList
        public int size() throws RemoteException {
            return 0;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public SemClipData getItem(int index) throws RemoteException {
            return null;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public boolean removeData(int index) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public boolean updateData(int index, SemClipData clipData) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.data.IClipboardDataList
        public SemClipData getClipByID(String id) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IClipboardDataList {
        static final int TRANSACTION_getClipByID = 5;
        static final int TRANSACTION_getItem = 2;
        static final int TRANSACTION_removeData = 3;
        static final int TRANSACTION_size = 1;
        static final int TRANSACTION_updateData = 4;

        public Stub() {
            attachInterface(this, IClipboardDataList.DESCRIPTOR);
        }

        public static IClipboardDataList asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IClipboardDataList.DESCRIPTOR);
            if (iin != null && (iin instanceof IClipboardDataList)) {
                return (IClipboardDataList) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return Contract.DatabaseSize.PATH;
                case 2:
                    return "getItem";
                case 3:
                    return "removeData";
                case 4:
                    return "updateData";
                case 5:
                    return "getClipByID";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IClipboardDataList.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IClipboardDataList.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _result = size();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    SemClipData _result2 = getItem(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = removeData(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    SemClipData _arg1 = (SemClipData) data.readTypedObject(SemClipData.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result4 = updateData(_arg03, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    SemClipData _result5 = getClipByID(_arg04);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IClipboardDataList {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getItem(int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    _data.writeInt(index);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    SemClipData _result = (SemClipData) _reply.readTypedObject(SemClipData.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean removeData(int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    _data.writeInt(index);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public boolean updateData(int index, SemClipData clipData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    _data.writeInt(index);
                    _data.writeTypedObject(clipData, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.data.IClipboardDataList
            public SemClipData getClipByID(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardDataList.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    SemClipData _result = (SemClipData) _reply.readTypedObject(SemClipData.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
