package android.service.contentcapture;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IContentProtectionService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.contentcapture.IContentProtectionService";

    void onLoginDetected(ParceledListSlice parceledListSlice) throws RemoteException;

    void onUpdateAllowlistRequest(IBinder iBinder) throws RemoteException;

    public static class Default implements IContentProtectionService {
        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(ParceledListSlice events) throws RemoteException {}

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(IBinder callback) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IContentProtectionService {
        static final int TRANSACTION_onLoginDetected = 1;
        static final int TRANSACTION_onUpdateAllowlistRequest = 2;

        public Stub() {
            attachInterface(this, IContentProtectionService.DESCRIPTOR);
        }

        public static IContentProtectionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IContentProtectionService.DESCRIPTOR);
            if (iin != null && (iin instanceof IContentProtectionService)) {
                return (IContentProtectionService) iin;
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
                    return "onLoginDetected";
                case 2:
                    return "onUpdateAllowlistRequest";
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
                data.enforceInterface(IContentProtectionService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IContentProtectionService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParceledListSlice _arg0 =
                            (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    data.enforceNoDataAvail();
                    onLoginDetected(_arg0);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    onUpdateAllowlistRequest(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContentProtectionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentProtectionService.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onLoginDetected(ParceledListSlice events) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContentProtectionService.DESCRIPTOR);
                    _data.writeTypedObject(events, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onUpdateAllowlistRequest(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContentProtectionService.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
