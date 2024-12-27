package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISensitiveContentProtectionManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISensitiveContentProtectionManager";

    void setSensitiveContentProtection(IBinder iBinder, String str, boolean z) throws RemoteException;

    public static class Default implements ISensitiveContentProtectionManager {
        @Override // android.view.ISensitiveContentProtectionManager
        public void setSensitiveContentProtection(IBinder windowToken, String packageName, boolean isShowingSensitiveContent) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISensitiveContentProtectionManager {
        static final int TRANSACTION_setSensitiveContentProtection = 1;

        public Stub() {
            attachInterface(this, ISensitiveContentProtectionManager.DESCRIPTOR);
        }

        public static ISensitiveContentProtectionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISensitiveContentProtectionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISensitiveContentProtectionManager)) {
                return (ISensitiveContentProtectionManager) iin;
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
                    return "setSensitiveContentProtection";
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
                data.enforceInterface(ISensitiveContentProtectionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISensitiveContentProtectionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    String _arg1 = data.readString();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSensitiveContentProtection(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISensitiveContentProtectionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensitiveContentProtectionManager.DESCRIPTOR;
            }

            @Override // android.view.ISensitiveContentProtectionManager
            public void setSensitiveContentProtection(IBinder windowToken, String packageName, boolean isShowingSensitiveContent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISensitiveContentProtectionManager.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeString(packageName);
                    _data.writeBoolean(isShowingSensitiveContent);
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
