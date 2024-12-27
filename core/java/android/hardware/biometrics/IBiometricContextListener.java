package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBiometricContextListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricContextListener";

    public @interface FoldState {
        public static final int FULLY_CLOSED = 3;
        public static final int FULLY_OPENED = 2;
        public static final int HALF_OPENED = 1;
        public static final int UNKNOWN = 0;
    }

    void onDisplayStateChanged(int i) throws RemoteException;

    void onFoldChanged(int i) throws RemoteException;

    void onHardwareIgnoreTouchesChanged(boolean z) throws RemoteException;

    public static class Default implements IBiometricContextListener {
        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onFoldChanged(int FoldState) throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onDisplayStateChanged(int displayState) throws RemoteException {}

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onHardwareIgnoreTouchesChanged(boolean shouldIgnore) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBiometricContextListener {
        static final int TRANSACTION_onDisplayStateChanged = 2;
        static final int TRANSACTION_onFoldChanged = 1;
        static final int TRANSACTION_onHardwareIgnoreTouchesChanged = 3;

        public Stub() {
            attachInterface(this, IBiometricContextListener.DESCRIPTOR);
        }

        public static IBiometricContextListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBiometricContextListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IBiometricContextListener)) {
                return (IBiometricContextListener) iin;
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
                    return "onFoldChanged";
                case 2:
                    return "onDisplayStateChanged";
                case 3:
                    return "onHardwareIgnoreTouchesChanged";
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
                data.enforceInterface(IBiometricContextListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBiometricContextListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onFoldChanged(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onDisplayStateChanged(_arg02);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onHardwareIgnoreTouchesChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBiometricContextListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricContextListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onFoldChanged(int FoldState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    _data.writeInt(FoldState);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onDisplayStateChanged(int displayState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    _data.writeInt(displayState);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onHardwareIgnoreTouchesChanged(boolean shouldIgnore)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    _data.writeBoolean(shouldIgnore);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
