package com.sec.android.iaft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIAFDService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.iaft.IIAFDService";

    boolean IAFDParse(
            String str, String str2, int i, int i2, int i3, String str3, String str4, String str5)
            throws RemoteException;

    void IAFDShow(int i, int i2, String str) throws RemoteException;

    public static class Default implements IIAFDService {
        @Override // com.sec.android.iaft.IIAFDService
        public boolean IAFDParse(
                String packageName,
                String nativeLibraryDir,
                int puserId,
                int appuid,
                int flags,
                String exceptionClassName,
                String exceptionMessage,
                String stackTrace)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.android.iaft.IIAFDService
        public void IAFDShow(int puserId, int appuid, String packageName) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIAFDService {
        static final int TRANSACTION_IAFDParse = 1;
        static final int TRANSACTION_IAFDShow = 2;

        public Stub() {
            attachInterface(this, IIAFDService.DESCRIPTOR);
        }

        public static IIAFDService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIAFDService.DESCRIPTOR);
            if (iin != null && (iin instanceof IIAFDService)) {
                return (IIAFDService) iin;
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
                    return "IAFDParse";
                case 2:
                    return "IAFDShow";
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
                data.enforceInterface(IIAFDService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIAFDService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    String _arg5 = data.readString();
                    String _arg6 = data.readString();
                    String _arg7 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result =
                            IAFDParse(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    IAFDShow(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIAFDService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIAFDService.DESCRIPTOR;
            }

            @Override // com.sec.android.iaft.IIAFDService
            public boolean IAFDParse(
                    String packageName,
                    String nativeLibraryDir,
                    int puserId,
                    int appuid,
                    int flags,
                    String exceptionClassName,
                    String exceptionMessage,
                    String stackTrace)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(nativeLibraryDir);
                    _data.writeInt(puserId);
                    _data.writeInt(appuid);
                    _data.writeInt(flags);
                    _data.writeString(exceptionClassName);
                    _data.writeString(exceptionMessage);
                    _data.writeString(stackTrace);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFDService
            public void IAFDShow(int puserId, int appuid, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    _data.writeInt(puserId);
                    _data.writeInt(appuid);
                    _data.writeString(packageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
