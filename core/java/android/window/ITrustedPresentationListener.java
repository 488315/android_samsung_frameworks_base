package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITrustedPresentationListener extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITrustedPresentationListener";

    void onTrustedPresentationChanged(int[] iArr, int[] iArr2) throws RemoteException;

    public static class Default implements ITrustedPresentationListener {
        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(
                int[] enteredTrustedStateIds, int[] exitedTrustedStateIds) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITrustedPresentationListener {
        static final int TRANSACTION_onTrustedPresentationChanged = 1;

        public Stub() {
            attachInterface(this, ITrustedPresentationListener.DESCRIPTOR);
        }

        public static ITrustedPresentationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITrustedPresentationListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ITrustedPresentationListener)) {
                return (ITrustedPresentationListener) iin;
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
                    return "onTrustedPresentationChanged";
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
                data.enforceInterface(ITrustedPresentationListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITrustedPresentationListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    onTrustedPresentationChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITrustedPresentationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITrustedPresentationListener.DESCRIPTOR;
            }

            @Override // android.window.ITrustedPresentationListener
            public void onTrustedPresentationChanged(
                    int[] enteredTrustedStateIds, int[] exitedTrustedStateIds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITrustedPresentationListener.DESCRIPTOR);
                    _data.writeIntArray(enteredTrustedStateIds);
                    _data.writeIntArray(exitedTrustedStateIds);
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
