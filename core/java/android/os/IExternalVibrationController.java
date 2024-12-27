package android.os;

import android.media.MediaMetrics;

public interface IExternalVibrationController extends IInterface {
    public static final String DESCRIPTOR = "android.os.IExternalVibrationController";

    boolean mute() throws RemoteException;

    boolean unmute() throws RemoteException;

    public static class Default implements IExternalVibrationController {
        @Override // android.os.IExternalVibrationController
        public boolean mute() throws RemoteException {
            return false;
        }

        @Override // android.os.IExternalVibrationController
        public boolean unmute() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IExternalVibrationController {
        static final int TRANSACTION_mute = 1;
        static final int TRANSACTION_unmute = 2;

        public Stub() {
            attachInterface(this, IExternalVibrationController.DESCRIPTOR);
        }

        public static IExternalVibrationController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExternalVibrationController.DESCRIPTOR);
            if (iin != null && (iin instanceof IExternalVibrationController)) {
                return (IExternalVibrationController) iin;
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
                    return "mute";
                case 2:
                    return MediaMetrics.Value.UNMUTE;
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
                data.enforceInterface(IExternalVibrationController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IExternalVibrationController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = mute();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    boolean _result2 = unmute();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IExternalVibrationController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExternalVibrationController.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibrationController
            public boolean mute() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IExternalVibrationController
            public boolean unmute() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
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
