package android.service.rotationresolver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRotationResolverService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.rotationresolver.IRotationResolverService";

    void resolveRotation(
            IRotationResolverCallback iRotationResolverCallback,
            RotationResolutionRequest rotationResolutionRequest)
            throws RemoteException;

    public static class Default implements IRotationResolverService {
        @Override // android.service.rotationresolver.IRotationResolverService
        public void resolveRotation(
                IRotationResolverCallback callback, RotationResolutionRequest request)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRotationResolverService {
        static final int TRANSACTION_resolveRotation = 1;

        public Stub() {
            attachInterface(this, IRotationResolverService.DESCRIPTOR);
        }

        public static IRotationResolverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRotationResolverService.DESCRIPTOR);
            if (iin != null && (iin instanceof IRotationResolverService)) {
                return (IRotationResolverService) iin;
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
                    return "resolveRotation";
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
                data.enforceInterface(IRotationResolverService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRotationResolverService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IRotationResolverCallback _arg0 =
                            IRotationResolverCallback.Stub.asInterface(data.readStrongBinder());
                    RotationResolutionRequest _arg1 =
                            (RotationResolutionRequest)
                                    data.readTypedObject(RotationResolutionRequest.CREATOR);
                    data.enforceNoDataAvail();
                    resolveRotation(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRotationResolverService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRotationResolverService.DESCRIPTOR;
            }

            @Override // android.service.rotationresolver.IRotationResolverService
            public void resolveRotation(
                    IRotationResolverCallback callback, RotationResolutionRequest request)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRotationResolverService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
