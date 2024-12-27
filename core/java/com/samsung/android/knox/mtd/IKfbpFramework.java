package com.samsung.android.knox.mtd;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IKfbpFramework extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IKfbpFramework";

    void analyzeContent(String str, String str2, int i) throws RemoteException;

    void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException;

    void analyzeUrl(String str, String str2, int i, Intent intent) throws RemoteException;

    void analyzeUrls(List<String> list, IMtdCallback iMtdCallback, String str)
            throws RemoteException;

    public static class Default implements IKfbpFramework {
        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeUrl(String url, String pkgName, int userId, Intent launchIntent)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeContent(String content, String pkgName, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeUrls(List<String> urls, IMtdCallback cb, String location)
                throws RemoteException {}

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeContents(List<String> contents, IMtdCallback cb)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKfbpFramework {
        static final int TRANSACTION_analyzeContent = 2;
        static final int TRANSACTION_analyzeContents = 4;
        static final int TRANSACTION_analyzeUrl = 1;
        static final int TRANSACTION_analyzeUrls = 3;

        public Stub() {
            attachInterface(this, IKfbpFramework.DESCRIPTOR);
        }

        public static IKfbpFramework asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKfbpFramework.DESCRIPTOR);
            if (iin != null && (iin instanceof IKfbpFramework)) {
                return (IKfbpFramework) iin;
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
                    return "analyzeUrl";
                case 2:
                    return "analyzeContent";
                case 3:
                    return "analyzeUrls";
                case 4:
                    return "analyzeContents";
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
                data.enforceInterface(IKfbpFramework.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKfbpFramework.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    Intent _arg3 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    analyzeUrl(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    analyzeContent(_arg02, _arg12, _arg22);
                    return true;
                case 3:
                    List<String> _arg03 = data.createStringArrayList();
                    IMtdCallback _arg13 = IMtdCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    analyzeUrls(_arg03, _arg13, _arg23);
                    return true;
                case 4:
                    List<String> _arg04 = data.createStringArrayList();
                    IMtdCallback _arg14 = IMtdCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    analyzeContents(_arg04, _arg14);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKfbpFramework {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKfbpFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeUrl(String url, String pkgName, int userId, Intent launchIntent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    _data.writeTypedObject(launchIntent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContent(String content, String pkgName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    _data.writeString(content);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeUrls(List<String> urls, IMtdCallback cb, String location)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    _data.writeStringList(urls);
                    _data.writeStrongInterface(cb);
                    _data.writeString(location);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContents(List<String> contents, IMtdCallback cb)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    _data.writeStringList(contents);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
