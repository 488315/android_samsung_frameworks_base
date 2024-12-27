package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ITaskFragmentOrganizer extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskFragmentOrganizer";

    void onTransactionReady(TaskFragmentTransaction taskFragmentTransaction) throws RemoteException;

    public static class Default implements ITaskFragmentOrganizer {
        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(TaskFragmentTransaction transaction) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITaskFragmentOrganizer {
        static final int TRANSACTION_onTransactionReady = 1;

        public Stub() {
            attachInterface(this, ITaskFragmentOrganizer.DESCRIPTOR);
        }

        public static ITaskFragmentOrganizer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITaskFragmentOrganizer.DESCRIPTOR);
            if (iin != null && (iin instanceof ITaskFragmentOrganizer)) {
                return (ITaskFragmentOrganizer) iin;
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
                    return "onTransactionReady";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITaskFragmentOrganizer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITaskFragmentOrganizer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    TaskFragmentTransaction _arg0 = (TaskFragmentTransaction) data.readTypedObject(TaskFragmentTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    onTransactionReady(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITaskFragmentOrganizer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskFragmentOrganizer.DESCRIPTOR;
            }

            @Override // android.window.ITaskFragmentOrganizer
            public void onTransactionReady(TaskFragmentTransaction transaction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskFragmentOrganizer.DESCRIPTOR);
                    _data.writeTypedObject(transaction, 0);
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
