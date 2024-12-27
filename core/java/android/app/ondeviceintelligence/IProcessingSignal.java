package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IProcessingSignal extends IInterface {
    public static final String DESCRIPTOR = "android.app.ondeviceintelligence.IProcessingSignal";

    void sendSignal(PersistableBundle persistableBundle) throws RemoteException;

    public static class Default implements IProcessingSignal {
        @Override // android.app.ondeviceintelligence.IProcessingSignal
        public void sendSignal(PersistableBundle actionParams) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IProcessingSignal {
        static final int TRANSACTION_sendSignal = 3;

        public Stub() {
            attachInterface(this, IProcessingSignal.DESCRIPTOR);
        }

        public static IProcessingSignal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProcessingSignal.DESCRIPTOR);
            if (iin != null && (iin instanceof IProcessingSignal)) {
                return (IProcessingSignal) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 3:
                    return "sendSignal";
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
                data.enforceInterface(IProcessingSignal.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProcessingSignal.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 3:
                    PersistableBundle _arg0 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    sendSignal(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProcessingSignal {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProcessingSignal.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IProcessingSignal
            public void sendSignal(PersistableBundle actionParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IProcessingSignal.DESCRIPTOR);
                    _data.writeTypedObject(actionParams, 0);
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
