package android.app.job;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IUserVisibleJobObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.job.IUserVisibleJobObserver";

    void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) throws RemoteException;

    public static class Default implements IUserVisibleJobObserver {
        @Override // android.app.job.IUserVisibleJobObserver
        public void onUserVisibleJobStateChanged(UserVisibleJobSummary summary, boolean isRunning) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUserVisibleJobObserver {
        static final int TRANSACTION_onUserVisibleJobStateChanged = 1;

        public Stub() {
            attachInterface(this, IUserVisibleJobObserver.DESCRIPTOR);
        }

        public static IUserVisibleJobObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUserVisibleJobObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IUserVisibleJobObserver)) {
                return (IUserVisibleJobObserver) iin;
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
                    return "onUserVisibleJobStateChanged";
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
                data.enforceInterface(IUserVisibleJobObserver.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IUserVisibleJobObserver.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            UserVisibleJobSummary _arg0 = (UserVisibleJobSummary) data.readTypedObject(UserVisibleJobSummary.CREATOR);
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onUserVisibleJobStateChanged(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUserVisibleJobObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUserVisibleJobObserver.DESCRIPTOR;
            }

            @Override // android.app.job.IUserVisibleJobObserver
            public void onUserVisibleJobStateChanged(UserVisibleJobSummary summary, boolean isRunning) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUserVisibleJobObserver.DESCRIPTOR);
                    _data.writeTypedObject(summary, 0);
                    _data.writeBoolean(isRunning);
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
