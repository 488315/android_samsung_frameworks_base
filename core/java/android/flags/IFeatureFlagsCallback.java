package android.flags;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IFeatureFlagsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.flags.IFeatureFlagsCallback";

    void onFlagChange(SyncableFlag syncableFlag) throws RemoteException;

    public static class Default implements IFeatureFlagsCallback {
        @Override // android.flags.IFeatureFlagsCallback
        public void onFlagChange(SyncableFlag flag) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IFeatureFlagsCallback {
        static final int TRANSACTION_onFlagChange = 1;

        public Stub() {
            attachInterface(this, IFeatureFlagsCallback.DESCRIPTOR);
        }

        public static IFeatureFlagsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFeatureFlagsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFeatureFlagsCallback)) {
                return (IFeatureFlagsCallback) iin;
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
                    return "onFlagChange";
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
                data.enforceInterface(IFeatureFlagsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFeatureFlagsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SyncableFlag _arg0 = (SyncableFlag) data.readTypedObject(SyncableFlag.CREATOR);
                    data.enforceNoDataAvail();
                    onFlagChange(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFeatureFlagsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFeatureFlagsCallback.DESCRIPTOR;
            }

            @Override // android.flags.IFeatureFlagsCallback
            public void onFlagChange(SyncableFlag flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFeatureFlagsCallback.DESCRIPTOR);
                    _data.writeTypedObject(flag, 0);
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
