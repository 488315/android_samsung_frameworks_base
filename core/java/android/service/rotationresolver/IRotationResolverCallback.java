package android.service.rotationresolver;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.ICancellationSignal;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IRotationResolverCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.rotationresolver.IRotationResolverCallback";

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(int i) throws RemoteException;

    void onSuccess(int i) throws RemoteException;

    public static class Default implements IRotationResolverCallback {
        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
        }

        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onSuccess(int recommendedRotation) throws RemoteException {
        }

        @Override // android.service.rotationresolver.IRotationResolverCallback
        public void onFailure(int error) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRotationResolverCallback {
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IRotationResolverCallback.DESCRIPTOR);
        }

        public static IRotationResolverCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRotationResolverCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRotationResolverCallback)) {
                return (IRotationResolverCallback) iin;
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
                    return "onCancellable";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
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
                data.enforceInterface(IRotationResolverCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IRotationResolverCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ICancellationSignal _arg0 = ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            onCancellable(_arg0);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            onSuccess(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            onFailure(_arg03);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IRotationResolverCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRotationResolverCallback.DESCRIPTOR;
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    _data.writeStrongInterface(cancellation);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onSuccess(int recommendedRotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    _data.writeInt(recommendedRotation);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.rotationresolver.IRotationResolverCallback
            public void onFailure(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRotationResolverCallback.DESCRIPTOR);
                    _data.writeInt(error);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
