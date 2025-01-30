package android.hardware;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IDeviceInjectorSession extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IDeviceInjectorSession";

    void setDeviceInjectorPending(boolean z) throws RemoteException;

    void stopDeviceInjector() throws RemoteException;

    public static class Default implements IDeviceInjectorSession {
        @Override // android.hardware.IDeviceInjectorSession
        public void stopDeviceInjector() throws RemoteException {
        }

        @Override // android.hardware.IDeviceInjectorSession
        public void setDeviceInjectorPending(boolean pending) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDeviceInjectorSession {
        static final int TRANSACTION_setDeviceInjectorPending = 2;
        static final int TRANSACTION_stopDeviceInjector = 1;

        public Stub() {
            attachInterface(this, IDeviceInjectorSession.DESCRIPTOR);
        }

        public static IDeviceInjectorSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDeviceInjectorSession.DESCRIPTOR);
            if (iin != null && (iin instanceof IDeviceInjectorSession)) {
                return (IDeviceInjectorSession) iin;
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
                    return "stopDeviceInjector";
                case 2:
                    return "setDeviceInjectorPending";
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
                data.enforceInterface(IDeviceInjectorSession.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDeviceInjectorSession.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            stopDeviceInjector();
                            reply.writeNoException();
                            return true;
                        case 2:
                            boolean _arg0 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setDeviceInjectorPending(_arg0);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDeviceInjectorSession {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInjectorSession.DESCRIPTOR;
            }

            @Override // android.hardware.IDeviceInjectorSession
            public void stopDeviceInjector() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDeviceInjectorSession.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IDeviceInjectorSession
            public void setDeviceInjectorPending(boolean pending) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDeviceInjectorSession.DESCRIPTOR);
                    _data.writeBoolean(pending);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
