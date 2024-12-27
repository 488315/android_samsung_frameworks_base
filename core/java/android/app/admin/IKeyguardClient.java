package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IKeyguardClient extends IInterface {
    public static final String DESCRIPTOR = "android.app.admin.IKeyguardClient";

    void onCreateKeyguardSurface(IBinder iBinder, IKeyguardCallback iKeyguardCallback)
            throws RemoteException;

    public static class Default implements IKeyguardClient {
        @Override // android.app.admin.IKeyguardClient
        public void onCreateKeyguardSurface(
                IBinder hostInputToken, IKeyguardCallback keyguardCallback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeyguardClient {
        static final int TRANSACTION_onCreateKeyguardSurface = 1;

        public Stub() {
            attachInterface(this, IKeyguardClient.DESCRIPTOR);
        }

        public static IKeyguardClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeyguardClient.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardClient)) {
                return (IKeyguardClient) iin;
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
                    return "onCreateKeyguardSurface";
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
                data.enforceInterface(IKeyguardClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeyguardClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    IKeyguardCallback _arg1 =
                            IKeyguardCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCreateKeyguardSurface(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyguardClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardClient.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardClient
            public void onCreateKeyguardSurface(
                    IBinder hostInputToken, IKeyguardCallback keyguardCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKeyguardClient.DESCRIPTOR);
                    _data.writeStrongBinder(hostInputToken);
                    _data.writeStrongInterface(keyguardCallback);
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
