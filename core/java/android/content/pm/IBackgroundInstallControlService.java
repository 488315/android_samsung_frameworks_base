package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBackgroundInstallControlService extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IBackgroundInstallControlService";

    ParceledListSlice getBackgroundInstalledPackages(long j, int i) throws RemoteException;

    void registerBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    void unregisterBackgroundInstallCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    public static class Default implements IBackgroundInstallControlService {
        @Override // android.content.pm.IBackgroundInstallControlService
        public ParceledListSlice getBackgroundInstalledPackages(long flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void registerBackgroundInstallCallback(IRemoteCallback callback) throws RemoteException {
        }

        @Override // android.content.pm.IBackgroundInstallControlService
        public void unregisterBackgroundInstallCallback(IRemoteCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBackgroundInstallControlService {
        static final int TRANSACTION_getBackgroundInstalledPackages = 1;
        static final int TRANSACTION_registerBackgroundInstallCallback = 2;
        static final int TRANSACTION_unregisterBackgroundInstallCallback = 3;

        public Stub() {
            attachInterface(this, IBackgroundInstallControlService.DESCRIPTOR);
        }

        public static IBackgroundInstallControlService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBackgroundInstallControlService.DESCRIPTOR);
            if (iin != null && (iin instanceof IBackgroundInstallControlService)) {
                return (IBackgroundInstallControlService) iin;
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
                    return "getBackgroundInstalledPackages";
                case 2:
                    return "registerBackgroundInstallCallback";
                case 3:
                    return "unregisterBackgroundInstallCallback";
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
                data.enforceInterface(IBackgroundInstallControlService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBackgroundInstallControlService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result = getBackgroundInstalledPackages(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    IRemoteCallback _arg02 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerBackgroundInstallCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    IRemoteCallback _arg03 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterBackgroundInstallCallback(_arg03);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBackgroundInstallControlService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBackgroundInstallControlService.DESCRIPTOR;
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public ParceledListSlice getBackgroundInstalledPackages(long flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    _data.writeLong(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void registerBackgroundInstallCallback(IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IBackgroundInstallControlService
            public void unregisterBackgroundInstallCallback(IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IBackgroundInstallControlService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
