package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactUceCapability;

public interface IOptionsRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IOptionsRequestCallback";

    void respondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z)
            throws RemoteException;

    void respondToCapabilityRequestWithError(int i, String str) throws RemoteException;

    public static class Default implements IOptionsRequestCallback {
        @Override // android.telephony.ims.aidl.IOptionsRequestCallback
        public void respondToCapabilityRequest(
                RcsContactUceCapability ownCapabilities, boolean isBlocked)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IOptionsRequestCallback
        public void respondToCapabilityRequestWithError(int code, String reason)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOptionsRequestCallback {
        static final int TRANSACTION_respondToCapabilityRequest = 1;
        static final int TRANSACTION_respondToCapabilityRequestWithError = 2;

        public Stub() {
            attachInterface(this, IOptionsRequestCallback.DESCRIPTOR);
        }

        public static IOptionsRequestCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOptionsRequestCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IOptionsRequestCallback)) {
                return (IOptionsRequestCallback) iin;
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
                    return "respondToCapabilityRequest";
                case 2:
                    return "respondToCapabilityRequestWithError";
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
                data.enforceInterface(IOptionsRequestCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOptionsRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    RcsContactUceCapability _arg0 =
                            (RcsContactUceCapability)
                                    data.readTypedObject(RcsContactUceCapability.CREATOR);
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    respondToCapabilityRequest(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    respondToCapabilityRequestWithError(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOptionsRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOptionsRequestCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IOptionsRequestCallback
            public void respondToCapabilityRequest(
                    RcsContactUceCapability ownCapabilities, boolean isBlocked)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOptionsRequestCallback.DESCRIPTOR);
                    _data.writeTypedObject(ownCapabilities, 0);
                    _data.writeBoolean(isBlocked);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IOptionsRequestCallback
            public void respondToCapabilityRequestWithError(int code, String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOptionsRequestCallback.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeString(reason);
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
