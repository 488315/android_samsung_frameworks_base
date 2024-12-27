package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes3.dex */
public interface IContentProtectionAllowlistCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.contentcapture.IContentProtectionAllowlistCallback";

    void setAllowlist(List<String> list) throws RemoteException;

    public static class Default implements IContentProtectionAllowlistCallback {
        @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
        public void setAllowlist(List<String> packages) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements IContentProtectionAllowlistCallback {
        static final int TRANSACTION_setAllowlist = 1;

        public Stub() {
            attachInterface(this, IContentProtectionAllowlistCallback.DESCRIPTOR);
        }

        public static IContentProtectionAllowlistCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(IContentProtectionAllowlistCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IContentProtectionAllowlistCallback)) {
                return (IContentProtectionAllowlistCallback) iin;
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
                    return "setAllowlist";
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
                data.enforceInterface(IContentProtectionAllowlistCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IContentProtectionAllowlistCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<String> _arg0 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    setAllowlist(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContentProtectionAllowlistCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentProtectionAllowlistCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionAllowlistCallback
            public void setAllowlist(List<String> packages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContentProtectionAllowlistCallback.DESCRIPTOR);
                    _data.writeStringList(packages);
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
