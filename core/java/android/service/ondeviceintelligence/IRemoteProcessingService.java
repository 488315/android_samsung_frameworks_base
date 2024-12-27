package android.service.ondeviceintelligence;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteProcessingService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.ondeviceintelligence.IRemoteProcessingService";

    void updateProcessingState(
            Bundle bundle, IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback)
            throws RemoteException;

    public static class Default implements IRemoteProcessingService {
        @Override // android.service.ondeviceintelligence.IRemoteProcessingService
        public void updateProcessingState(
                Bundle processingState, IProcessingUpdateStatusCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteProcessingService {
        static final int TRANSACTION_updateProcessingState = 1;

        public Stub() {
            attachInterface(this, IRemoteProcessingService.DESCRIPTOR);
        }

        public static IRemoteProcessingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteProcessingService.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteProcessingService)) {
                return (IRemoteProcessingService) iin;
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
                    return "updateProcessingState";
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
                data.enforceInterface(IRemoteProcessingService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteProcessingService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IProcessingUpdateStatusCallback _arg1 =
                            IProcessingUpdateStatusCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    updateProcessingState(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteProcessingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteProcessingService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IRemoteProcessingService
            public void updateProcessingState(
                    Bundle processingState, IProcessingUpdateStatusCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteProcessingService.DESCRIPTOR);
                    _data.writeTypedObject(processingState, 0);
                    _data.writeStrongInterface(callback);
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
