package android.security;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IFileIntegrityService extends IInterface {
    public static final String DESCRIPTOR = "android.security.IFileIntegrityService";

    IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    boolean isApkVeritySupported() throws RemoteException;

    boolean isAppSourceCertificateTrusted(byte[] bArr, String str) throws RemoteException;

    int setupFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException;

    public static class Default implements IFileIntegrityService {
        @Override // android.security.IFileIntegrityService
        public boolean isApkVeritySupported() throws RemoteException {
            return false;
        }

        @Override // android.security.IFileIntegrityService
        public boolean isAppSourceCertificateTrusted(byte[] certificateBytes, String packageName) throws RemoteException {
            return false;
        }

        @Override // android.security.IFileIntegrityService
        public IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor authFd) throws RemoteException {
            return null;
        }

        @Override // android.security.IFileIntegrityService
        public int setupFsverity(IInstalld.IFsveritySetupAuthToken authToken, String filePath, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFileIntegrityService {
        static final int TRANSACTION_createAuthToken = 3;
        static final int TRANSACTION_isApkVeritySupported = 1;
        static final int TRANSACTION_isAppSourceCertificateTrusted = 2;
        static final int TRANSACTION_setupFsverity = 4;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IFileIntegrityService.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFileIntegrityService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFileIntegrityService.DESCRIPTOR);
            if (iin != null && (iin instanceof IFileIntegrityService)) {
                return (IFileIntegrityService) iin;
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
                    return "isApkVeritySupported";
                case 2:
                    return "isAppSourceCertificateTrusted";
                case 3:
                    return "createAuthToken";
                case 4:
                    return "setupFsverity";
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
                data.enforceInterface(IFileIntegrityService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFileIntegrityService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = isApkVeritySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    byte[] _arg0 = data.createByteArray();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = isAppSourceCertificateTrusted(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    ParcelFileDescriptor _arg02 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    IInstalld.IFsveritySetupAuthToken _result3 = createAuthToken(_arg02);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    IInstalld.IFsveritySetupAuthToken _arg03 = IInstalld.IFsveritySetupAuthToken.Stub.asInterface(data.readStrongBinder());
                    String _arg12 = data.readString();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = setupFsverity(_arg03, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFileIntegrityService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFileIntegrityService.DESCRIPTOR;
            }

            @Override // android.security.IFileIntegrityService
            public boolean isApkVeritySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public boolean isAppSourceCertificateTrusted(byte[] certificateBytes, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    _data.writeByteArray(certificateBytes);
                    _data.writeString(packageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public IInstalld.IFsveritySetupAuthToken createAuthToken(ParcelFileDescriptor authFd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    _data.writeTypedObject(authFd, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    IInstalld.IFsveritySetupAuthToken _result = IInstalld.IFsveritySetupAuthToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.IFileIntegrityService
            public int setupFsverity(IInstalld.IFsveritySetupAuthToken authToken, String filePath, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFileIntegrityService.DESCRIPTOR);
                    _data.writeStrongInterface(authToken);
                    _data.writeString(filePath);
                    _data.writeString(packageName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setupFsverity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SETUP_FSVERITY, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
