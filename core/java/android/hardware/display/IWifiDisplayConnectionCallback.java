package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes2.dex */
public interface IWifiDisplayConnectionCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.hardware.display.IWifiDisplayConnectionCallback";

    void onFailure(int i) throws RemoteException;

    void onSuccess(List<SemWifiDisplayParameter> list) throws RemoteException;

    public static class Default implements IWifiDisplayConnectionCallback {
        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onSuccess(List<SemWifiDisplayParameter> parameters) throws RemoteException {}

        @Override // android.hardware.display.IWifiDisplayConnectionCallback
        public void onFailure(int reason) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWifiDisplayConnectionCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IWifiDisplayConnectionCallback.DESCRIPTOR);
        }

        public static IWifiDisplayConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWifiDisplayConnectionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWifiDisplayConnectionCallback)) {
                return (IWifiDisplayConnectionCallback) iin;
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
                    return "onSuccess";
                case 2:
                    return "onFailure";
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
                data.enforceInterface(IWifiDisplayConnectionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWifiDisplayConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SemWifiDisplayParameter> _arg0 =
                            data.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onFailure(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWifiDisplayConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWifiDisplayConnectionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.display.IWifiDisplayConnectionCallback
            public void onSuccess(List<SemWifiDisplayParameter> parameters) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWifiDisplayConnectionCallback.DESCRIPTOR);
                    _data.writeTypedList(parameters, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.display.IWifiDisplayConnectionCallback
            public void onFailure(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWifiDisplayConnectionCallback.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
