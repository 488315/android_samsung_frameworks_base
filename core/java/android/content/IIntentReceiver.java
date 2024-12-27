package android.content;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntentReceiver extends IInterface {
    void performReceive(
            Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2)
            throws RemoteException;

    public static class Default implements IIntentReceiver {
        @Override // android.content.IIntentReceiver
        public void performReceive(
                Intent intent,
                int resultCode,
                String data,
                Bundle extras,
                boolean ordered,
                boolean sticky,
                int sendingUser)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIntentReceiver {
        public static final String DESCRIPTOR = "android.content.IIntentReceiver";
        static final int TRANSACTION_performReceive = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIntentReceiver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IIntentReceiver)) {
                return (IIntentReceiver) iin;
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
                    return "performReceive";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    boolean _arg4 = data.readBoolean();
                    boolean _arg5 = data.readBoolean();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    performReceive(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIntentReceiver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.IIntentReceiver
            public void performReceive(
                    Intent intent,
                    int resultCode,
                    String data,
                    Bundle extras,
                    boolean ordered,
                    boolean sticky,
                    int sendingUser)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeInt(resultCode);
                    _data.writeString(data);
                    _data.writeTypedObject(extras, 0);
                    _data.writeBoolean(ordered);
                    _data.writeBoolean(sticky);
                    _data.writeInt(sendingUser);
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
