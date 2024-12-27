package android.service.remotelockscreenvalidation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteLockscreenValidationService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService";

    void validateLockscreenGuess(
            byte[] bArr, IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback)
            throws RemoteException;

    public static class Default implements IRemoteLockscreenValidationService {
        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
        public void validateLockscreenGuess(
                byte[] guess, IRemoteLockscreenValidationCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteLockscreenValidationService {
        static final int TRANSACTION_validateLockscreenGuess = 1;

        public Stub() {
            attachInterface(this, IRemoteLockscreenValidationService.DESCRIPTOR);
        }

        public static IRemoteLockscreenValidationService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteLockscreenValidationService.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteLockscreenValidationService)) {
                return (IRemoteLockscreenValidationService) iin;
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
                    return "validateLockscreenGuess";
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
                data.enforceInterface(IRemoteLockscreenValidationService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteLockscreenValidationService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    byte[] _arg0 = data.createByteArray();
                    IRemoteLockscreenValidationCallback _arg1 =
                            IRemoteLockscreenValidationCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    validateLockscreenGuess(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteLockscreenValidationService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLockscreenValidationService.DESCRIPTOR;
            }

            @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
            public void validateLockscreenGuess(
                    byte[] guess, IRemoteLockscreenValidationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteLockscreenValidationService.DESCRIPTOR);
                    _data.writeByteArray(guess);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
