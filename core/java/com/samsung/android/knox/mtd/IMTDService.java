package com.samsung.android.knox.mtd;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface IMTDService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IMTDService";

    void analyzeContent(String str, String str2, int i, boolean z, int i2) throws RemoteException;

    void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException;

    void analyzeFrameBuffers(List<FrameBuffersInfo> list) throws RemoteException;

    void analyzeURL(String str, String str2, int i, boolean z, Intent intent)
            throws RemoteException;

    void analyzeURLs(List<String> list, IMtdCallback iMtdCallback, String str)
            throws RemoteException;

    String getSystemProperty(String str) throws RemoteException;

    void setSystemProperty(String str, String str2) throws RemoteException;

    public static class Default implements IMTDService {
        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeContent(
                String content, String pkgName, int userId, boolean isManagedProfile, int uid)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeURL(
                String url,
                String pkgName,
                int userId,
                boolean isManagedProfile,
                Intent launchIntent)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeURLs(List<String> urls, IMtdCallback cb, String location)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeContents(List<String> contents, IMtdCallback cb)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeFrameBuffers(List<FrameBuffersInfo> frameBuffers)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void setSystemProperty(String key, String value) throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IMTDService
        public String getSystemProperty(String key) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMTDService {
        static final int TRANSACTION_analyzeContent = 1;
        static final int TRANSACTION_analyzeContents = 4;
        static final int TRANSACTION_analyzeFrameBuffers = 5;
        static final int TRANSACTION_analyzeURL = 2;
        static final int TRANSACTION_analyzeURLs = 3;
        static final int TRANSACTION_getSystemProperty = 7;
        static final int TRANSACTION_setSystemProperty = 6;

        public Stub() {
            attachInterface(this, IMTDService.DESCRIPTOR);
        }

        public static IMTDService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMTDService.DESCRIPTOR);
            if (iin != null && (iin instanceof IMTDService)) {
                return (IMTDService) iin;
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
                    return "analyzeContent";
                case 2:
                    return "analyzeURL";
                case 3:
                    return "analyzeURLs";
                case 4:
                    return "analyzeContents";
                case 5:
                    return "analyzeFrameBuffers";
                case 6:
                    return "setSystemProperty";
                case 7:
                    return "getSystemProperty";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IMTDService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMTDService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    boolean _arg3 = data.readBoolean();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    analyzeContent(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    boolean _arg32 = data.readBoolean();
                    Intent _arg42 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    analyzeURL(_arg02, _arg12, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 3:
                    List<String> _arg03 = data.createStringArrayList();
                    IMtdCallback _arg13 = IMtdCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    analyzeURLs(_arg03, _arg13, _arg23);
                    return true;
                case 4:
                    List<String> _arg04 = data.createStringArrayList();
                    IMtdCallback _arg14 = IMtdCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    analyzeContents(_arg04, _arg14);
                    return true;
                case 5:
                    List<FrameBuffersInfo> _arg05 =
                            data.createTypedArrayList(FrameBuffersInfo.CREATOR);
                    data.enforceNoDataAvail();
                    analyzeFrameBuffers(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    setSystemProperty(_arg06, _arg15);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    data.enforceNoDataAvail();
                    String _result = getSystemProperty(_arg07);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMTDService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMTDService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeContent(
                    String content, String pkgName, int userId, boolean isManagedProfile, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeString(content);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    _data.writeBoolean(isManagedProfile);
                    _data.writeInt(uid);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURL(
                    String url,
                    String pkgName,
                    int userId,
                    boolean isManagedProfile,
                    Intent launchIntent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    _data.writeBoolean(isManagedProfile);
                    _data.writeTypedObject(launchIntent, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURLs(List<String> urls, IMtdCallback cb, String location)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeStringList(urls);
                    _data.writeStrongInterface(cb);
                    _data.writeString(location);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeContents(List<String> contents, IMtdCallback cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeStringList(contents);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeFrameBuffers(List<FrameBuffersInfo> frameBuffers)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeTypedList(frameBuffers, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void setSystemProperty(String key, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public String getSystemProperty(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
