package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IOnMessageReceivedListener extends IInterface {
    public static final String DESCRIPTOR = "android.companion.IOnMessageReceivedListener";

    void onMessageReceived(int i, byte[] bArr) throws RemoteException;

    public static class Default implements IOnMessageReceivedListener {
        @Override // android.companion.IOnMessageReceivedListener
        public void onMessageReceived(int associationId, byte[] data) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnMessageReceivedListener {
        static final int TRANSACTION_onMessageReceived = 1;

        public Stub() {
            attachInterface(this, IOnMessageReceivedListener.DESCRIPTOR);
        }

        public static IOnMessageReceivedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnMessageReceivedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnMessageReceivedListener)) {
                return (IOnMessageReceivedListener) iin;
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
                    return "onMessageReceived";
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
                data.enforceInterface(IOnMessageReceivedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnMessageReceivedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onMessageReceived(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnMessageReceivedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnMessageReceivedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnMessageReceivedListener
            public void onMessageReceived(int associationId, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOnMessageReceivedListener.DESCRIPTOR);
                    _data.writeInt(associationId);
                    _data.writeByteArray(data);
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
