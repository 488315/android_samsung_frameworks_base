package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUidFrozenStateChangedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUidFrozenStateChangedCallback";

    void onUidFrozenStateChanged(int[] iArr, int[] iArr2) throws RemoteException;

    public static class Default implements IUidFrozenStateChangedCallback {
        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(int[] uids, int[] frozenStates) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUidFrozenStateChangedCallback {
        static final int TRANSACTION_onUidFrozenStateChanged = 1;

        public Stub() {
            attachInterface(this, IUidFrozenStateChangedCallback.DESCRIPTOR);
        }

        public static IUidFrozenStateChangedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUidFrozenStateChangedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IUidFrozenStateChangedCallback)) {
                return (IUidFrozenStateChangedCallback) iin;
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
                    return "onUidFrozenStateChanged";
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
                data.enforceInterface(IUidFrozenStateChangedCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUidFrozenStateChangedCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    onUidFrozenStateChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUidFrozenStateChangedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUidFrozenStateChangedCallback.DESCRIPTOR;
            }

            @Override // android.app.IUidFrozenStateChangedCallback
            public void onUidFrozenStateChanged(int[] uids, int[] frozenStates) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUidFrozenStateChangedCallback.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    _data.writeIntArray(frozenStates);
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
