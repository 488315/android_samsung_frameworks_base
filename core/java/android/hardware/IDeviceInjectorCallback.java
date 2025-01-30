package android.hardware;

import android.hardware.IDeviceInjectorSession;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IDeviceInjectorCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IDeviceInjectorCallback";
    public static final int ERROR_INJECTION_INVALID_ERROR = -1;
    public static final int ERROR_INJECTION_SERVICE = 1;
    public static final int ERROR_INJECTION_SESSION = 0;
    public static final int ERROR_INJECTION_UNSUPPORTED = 2;

    void onError(int i) throws RemoteException;

    void onInjectionPendingStarted(String str, String str2) throws RemoteException;

    void onInjectionPendingStopped(String str, String str2) throws RemoteException;

    void onInjectionStarted(String str, String str2, String str3) throws RemoteException;

    void onInjectionStopped(String str, String str2, String str3) throws RemoteException;

    void onSessionCreated(IDeviceInjectorSession iDeviceInjectorSession) throws RemoteException;

    public static class Default implements IDeviceInjectorCallback {
        @Override // android.hardware.IDeviceInjectorCallback
        public void onSessionCreated(IDeviceInjectorSession deviceInjectorSession) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStarted(String packageName, String targetId, String sourceId) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionStopped(String packageName, String targetId, String sourceId) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStarted(String packageName, String targetId) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onInjectionPendingStopped(String packageName, String targetId) throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorCallback
        public void onError(int errorCode) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDeviceInjectorCallback {
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onInjectionPendingStarted = 4;
        static final int TRANSACTION_onInjectionPendingStopped = 5;
        static final int TRANSACTION_onInjectionStarted = 2;
        static final int TRANSACTION_onInjectionStopped = 3;
        static final int TRANSACTION_onSessionCreated = 1;

        public Stub() {
            attachInterface(this, IDeviceInjectorCallback.DESCRIPTOR);
        }

        public static IDeviceInjectorCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDeviceInjectorCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDeviceInjectorCallback)) {
                return (IDeviceInjectorCallback) iin;
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
                    return "onSessionCreated";
                case 2:
                    return "onInjectionStarted";
                case 3:
                    return "onInjectionStopped";
                case 4:
                    return "onInjectionPendingStarted";
                case 5:
                    return "onInjectionPendingStopped";
                case 6:
                    return "onError";
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
                data.enforceInterface(IDeviceInjectorCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDeviceInjectorCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IDeviceInjectorSession _arg0 = IDeviceInjectorSession.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            onSessionCreated(_arg0);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            String _arg1 = data.readString();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            onInjectionStarted(_arg02, _arg1, _arg2);
                            return true;
                        case 3:
                            String _arg03 = data.readString();
                            String _arg12 = data.readString();
                            String _arg22 = data.readString();
                            data.enforceNoDataAvail();
                            onInjectionStopped(_arg03, _arg12, _arg22);
                            return true;
                        case 4:
                            String _arg04 = data.readString();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            onInjectionPendingStarted(_arg04, _arg13);
                            return true;
                        case 5:
                            String _arg05 = data.readString();
                            String _arg14 = data.readString();
                            data.enforceNoDataAvail();
                            onInjectionPendingStopped(_arg05, _arg14);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            onError(_arg06);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDeviceInjectorCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInjectorCallback.DESCRIPTOR;
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onSessionCreated(IDeviceInjectorSession deviceInjectorSession) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeStrongInterface(deviceInjectorSession);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionStarted(String packageName, String targetId, String sourceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetId);
                    _data.writeString(sourceId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionStopped(String packageName, String targetId, String sourceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetId);
                    _data.writeString(sourceId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionPendingStarted(String packageName, String targetId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onInjectionPendingStopped(String packageName, String targetId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(targetId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorCallback
            public void onError(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInjectorCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
