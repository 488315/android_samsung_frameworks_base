package android.content.om;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISamsungOverlayCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.om.ISamsungOverlayCallback";

    void onOverlayStateChanged(String str, String str2, int i) throws RemoteException;

    public static class Default implements ISamsungOverlayCallback {
        @Override // android.content.om.ISamsungOverlayCallback
        public void onOverlayStateChanged(String path, String packageName, int state) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISamsungOverlayCallback {
        static final int TRANSACTION_onOverlayStateChanged = 1;

        public Stub() {
            attachInterface(this, ISamsungOverlayCallback.DESCRIPTOR);
        }

        public static ISamsungOverlayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISamsungOverlayCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISamsungOverlayCallback)) {
                return (ISamsungOverlayCallback) iin;
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
                    return "onOverlayStateChanged";
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
                data.enforceInterface(ISamsungOverlayCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISamsungOverlayCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onOverlayStateChanged(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISamsungOverlayCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungOverlayCallback.DESCRIPTOR;
            }

            @Override // android.content.om.ISamsungOverlayCallback
            public void onOverlayStateChanged(String path, String packageName, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISamsungOverlayCallback.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(packageName);
                    _data.writeInt(state);
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
