package android.service.controls;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IControlsActionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsActionCallback";

    void accept(IBinder iBinder, String str, int i) throws RemoteException;

    public static class Default implements IControlsActionCallback {
        @Override // android.service.controls.IControlsActionCallback
        public void accept(IBinder token, String controlId, int response) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IControlsActionCallback {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, IControlsActionCallback.DESCRIPTOR);
        }

        public static IControlsActionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IControlsActionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IControlsActionCallback)) {
                return (IControlsActionCallback) iin;
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
                    return "accept";
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
                data.enforceInterface(IControlsActionCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IControlsActionCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IBinder _arg0 = data.readStrongBinder();
                            String _arg1 = data.readString();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            accept(_arg0, _arg1, _arg2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IControlsActionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsActionCallback.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsActionCallback
            public void accept(IBinder token, String controlId, int response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsActionCallback.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(controlId);
                    _data.writeInt(response);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
