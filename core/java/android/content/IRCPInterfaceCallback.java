package android.content;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IRCPInterfaceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.IRCPInterfaceCallback";

    void onComplete(List<String> list, int i, int i2) throws RemoteException;

    void onDone(String str, int i) throws RemoteException;

    void onFail(String str, int i, int i2) throws RemoteException;

    void onProgress(String str, int i, int i2) throws RemoteException;

    public static class Default implements IRCPInterfaceCallback {
        @Override // android.content.IRCPInterfaceCallback
        public void onComplete(List<String> srcPathsOrig, int destinationUserId, int successCnt) throws RemoteException {
        }

        @Override // android.content.IRCPInterfaceCallback
        public void onDone(String srcPathsOrig, int destinationUserId) throws RemoteException {
        }

        @Override // android.content.IRCPInterfaceCallback
        public void onFail(String srcPathsOrig, int destinationUserId, int errorCode) throws RemoteException {
        }

        @Override // android.content.IRCPInterfaceCallback
        public void onProgress(String srcPathsOrig, int destinationUserId, int progress) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRCPInterfaceCallback {
        static final int TRANSACTION_onComplete = 1;
        static final int TRANSACTION_onDone = 2;
        static final int TRANSACTION_onFail = 3;
        static final int TRANSACTION_onProgress = 4;

        public Stub() {
            attachInterface(this, IRCPInterfaceCallback.DESCRIPTOR);
        }

        public static IRCPInterfaceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRCPInterfaceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRCPInterfaceCallback)) {
                return (IRCPInterfaceCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onComplete";
                case 2:
                    return "onDone";
                case 3:
                    return "onFail";
                case 4:
                    return "onProgress";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRCPInterfaceCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IRCPInterfaceCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<String> _arg0 = data.createStringArrayList();
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            onComplete(_arg0, _arg1, _arg2);
                            reply.writeNoException();
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onDone(_arg02, _arg12);
                            reply.writeNoException();
                            return true;
                        case 3:
                            String _arg03 = data.readString();
                            int _arg13 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            onFail(_arg03, _arg13, _arg22);
                            reply.writeNoException();
                            return true;
                        case 4:
                            String _arg04 = data.readString();
                            int _arg14 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            onProgress(_arg04, _arg14, _arg23);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IRCPInterfaceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRCPInterfaceCallback.DESCRIPTOR;
            }

            @Override // android.content.IRCPInterfaceCallback
            public void onComplete(List<String> srcPathsOrig, int destinationUserId, int successCnt) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterfaceCallback.DESCRIPTOR);
                    _data.writeStringList(srcPathsOrig);
                    _data.writeInt(destinationUserId);
                    _data.writeInt(successCnt);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterfaceCallback
            public void onDone(String srcPathsOrig, int destinationUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterfaceCallback.DESCRIPTOR);
                    _data.writeString(srcPathsOrig);
                    _data.writeInt(destinationUserId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterfaceCallback
            public void onFail(String srcPathsOrig, int destinationUserId, int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterfaceCallback.DESCRIPTOR);
                    _data.writeString(srcPathsOrig);
                    _data.writeInt(destinationUserId);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterfaceCallback
            public void onProgress(String srcPathsOrig, int destinationUserId, int progress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterfaceCallback.DESCRIPTOR);
                    _data.writeString(srcPathsOrig);
                    _data.writeInt(destinationUserId);
                    _data.writeInt(progress);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
