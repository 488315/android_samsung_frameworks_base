package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IRemoteProvisioning extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IRemoteProvisioning";

    void getRegistration(String str, IGetRegistrationCallback iGetRegistrationCallback)
            throws RemoteException;

    public static class Default implements IRemoteProvisioning {
        @Override // android.security.rkp.IRemoteProvisioning
        public void getRegistration(String irpcName, IGetRegistrationCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteProvisioning {
        static final int TRANSACTION_getRegistration = 1;

        public Stub() {
            attachInterface(this, IRemoteProvisioning.DESCRIPTOR);
        }

        public static IRemoteProvisioning asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteProvisioning.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteProvisioning)) {
                return (IRemoteProvisioning) iin;
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
                    return "getRegistration";
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
                data.enforceInterface(IRemoteProvisioning.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteProvisioning.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    IGetRegistrationCallback _arg1 =
                            IGetRegistrationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getRegistration(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteProvisioning {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteProvisioning.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRemoteProvisioning
            public void getRegistration(String irpcName, IGetRegistrationCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteProvisioning.DESCRIPTOR);
                    _data.writeString(irpcName);
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
