package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IControlsSubscriber extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsSubscriber";

    void onComplete(IBinder iBinder) throws RemoteException;

    void onError(IBinder iBinder, String str) throws RemoteException;

    void onNext(IBinder iBinder, Control control) throws RemoteException;

    void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription)
            throws RemoteException;

    public static class Default implements IControlsSubscriber {
        @Override // android.service.controls.IControlsSubscriber
        public void onSubscribe(IBinder token, IControlsSubscription cs) throws RemoteException {}

        @Override // android.service.controls.IControlsSubscriber
        public void onNext(IBinder token, Control c) throws RemoteException {}

        @Override // android.service.controls.IControlsSubscriber
        public void onError(IBinder token, String s) throws RemoteException {}

        @Override // android.service.controls.IControlsSubscriber
        public void onComplete(IBinder token) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IControlsSubscriber {
        static final int TRANSACTION_onComplete = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onNext = 2;
        static final int TRANSACTION_onSubscribe = 1;

        public Stub() {
            attachInterface(this, IControlsSubscriber.DESCRIPTOR);
        }

        public static IControlsSubscriber asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IControlsSubscriber.DESCRIPTOR);
            if (iin != null && (iin instanceof IControlsSubscriber)) {
                return (IControlsSubscriber) iin;
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
                    return "onSubscribe";
                case 2:
                    return "onNext";
                case 3:
                    return "onError";
                case 4:
                    return "onComplete";
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
                data.enforceInterface(IControlsSubscriber.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IControlsSubscriber.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    IControlsSubscription _arg1 =
                            IControlsSubscription.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onSubscribe(_arg0, _arg1);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    Control _arg12 = (Control) data.readTypedObject(Control.CREATOR);
                    data.enforceNoDataAvail();
                    onNext(_arg02, _arg12);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg03, _arg13);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    onComplete(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IControlsSubscriber {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsSubscriber.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onSubscribe(IBinder token, IControlsSubscription cs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(cs);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onNext(IBinder token, Control c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(c, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onError(IBinder token, String s) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(s);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
