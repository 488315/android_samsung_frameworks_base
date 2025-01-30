package android.content.p002pm;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageInstallerCallback extends IInterface {
    void onSessionActiveChanged(int i, boolean z) throws RemoteException;

    void onSessionBadgingChanged(int i) throws RemoteException;

    void onSessionCreated(int i) throws RemoteException;

    void onSessionFinished(int i, boolean z) throws RemoteException;

    void onSessionProgressChanged(int i, float f) throws RemoteException;

    public static class Default implements IPackageInstallerCallback {
        @Override // android.content.p002pm.IPackageInstallerCallback
        public void onSessionCreated(int sessionId) throws RemoteException {
        }

        @Override // android.content.p002pm.IPackageInstallerCallback
        public void onSessionBadgingChanged(int sessionId) throws RemoteException {
        }

        @Override // android.content.p002pm.IPackageInstallerCallback
        public void onSessionActiveChanged(int sessionId, boolean active) throws RemoteException {
        }

        @Override // android.content.p002pm.IPackageInstallerCallback
        public void onSessionProgressChanged(int sessionId, float progress) throws RemoteException {
        }

        @Override // android.content.p002pm.IPackageInstallerCallback
        public void onSessionFinished(int sessionId, boolean success) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPackageInstallerCallback {
        public static final String DESCRIPTOR = "android.content.pm.IPackageInstallerCallback";
        static final int TRANSACTION_onSessionActiveChanged = 3;
        static final int TRANSACTION_onSessionBadgingChanged = 2;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionFinished = 5;
        static final int TRANSACTION_onSessionProgressChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPackageInstallerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageInstallerCallback)) {
                return (IPackageInstallerCallback) iin;
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
                    return "onSessionBadgingChanged";
                case 3:
                    return "onSessionActiveChanged";
                case 4:
                    return "onSessionProgressChanged";
                case 5:
                    return "onSessionFinished";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onSessionCreated(_arg0);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            onSessionBadgingChanged(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onSessionActiveChanged(_arg03, _arg1);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            float _arg12 = data.readFloat();
                            data.enforceNoDataAvail();
                            onSessionProgressChanged(_arg04, _arg12);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            boolean _arg13 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onSessionFinished(_arg05, _arg13);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IPackageInstallerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.p002pm.IPackageInstallerCallback
            public void onSessionCreated(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPackageInstallerCallback
            public void onSessionBadgingChanged(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPackageInstallerCallback
            public void onSessionActiveChanged(int sessionId, boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeBoolean(active);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPackageInstallerCallback
            public void onSessionProgressChanged(int sessionId, float progress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeFloat(progress);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IPackageInstallerCallback
            public void onSessionFinished(int sessionId, boolean success) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeBoolean(success);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
