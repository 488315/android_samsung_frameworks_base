package com.android.internal.os;

import android.os.Binder;
import android.os.DropBoxManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IDropBoxManagerService extends IInterface {
    void addData(String str, byte[] bArr, int i) throws RemoteException;

    void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    DropBoxManager.Entry getNextEntry(String str, long j, String str2) throws RemoteException;

    DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) throws RemoteException;

    boolean isTagEnabled(String str) throws RemoteException;

    public static class Default implements IDropBoxManagerService {
        @Override // com.android.internal.os.IDropBoxManagerService
        public void addData(String tag, byte[] data, int flags) throws RemoteException {
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public void addFile(String tag, ParcelFileDescriptor fd, int flags) throws RemoteException {
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public boolean isTagEnabled(String tag) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntry(String tag, long millis, String packageName) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public DropBoxManager.Entry getNextEntryWithAttribution(String tag, long millis, String packageName, String attributionTag) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDropBoxManagerService {
        public static final String DESCRIPTOR = "com.android.internal.os.IDropBoxManagerService";
        static final int TRANSACTION_addData = 1;
        static final int TRANSACTION_addFile = 2;
        static final int TRANSACTION_getNextEntry = 4;
        static final int TRANSACTION_getNextEntryWithAttribution = 5;
        static final int TRANSACTION_isTagEnabled = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDropBoxManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDropBoxManagerService)) {
                return (IDropBoxManagerService) iin;
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
                    return "addData";
                case 2:
                    return "addFile";
                case 3:
                    return "isTagEnabled";
                case 4:
                    return "getNextEntry";
                case 5:
                    return "getNextEntryWithAttribution";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    byte[] _arg1 = data.createByteArray();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    addData(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    ParcelFileDescriptor _arg12 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    addFile(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = isTagEnabled(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    long _arg13 = data.readLong();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    DropBoxManager.Entry _result2 = getNextEntry(_arg04, _arg13, _arg23);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    long _arg14 = data.readLong();
                    String _arg24 = data.readString();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    DropBoxManager.Entry _result3 = getNextEntryWithAttribution(_arg05, _arg14, _arg24, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDropBoxManagerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addData(String tag, byte[] data, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeByteArray(data);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addFile(String tag, ParcelFileDescriptor fd, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeTypedObject(fd, 0);
                    _data.writeInt(flags);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public boolean isTagEnabled(String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntry(String tag, long millis, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeLong(millis);
                    _data.writeString(packageName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    DropBoxManager.Entry _result = (DropBoxManager.Entry) _reply.readTypedObject(DropBoxManager.Entry.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public DropBoxManager.Entry getNextEntryWithAttribution(String tag, long millis, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(tag);
                    _data.writeLong(millis);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    DropBoxManager.Entry _result = (DropBoxManager.Entry) _reply.readTypedObject(DropBoxManager.Entry.CREATOR);
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
