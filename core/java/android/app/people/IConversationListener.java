package android.app.people;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IConversationListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.people.IConversationListener";

    void onConversationUpdate(ConversationChannel conversationChannel) throws RemoteException;

    public static class Default implements IConversationListener {
        @Override // android.app.people.IConversationListener
        public void onConversationUpdate(ConversationChannel conversation) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IConversationListener {
        static final int TRANSACTION_onConversationUpdate = 1;

        public Stub() {
            attachInterface(this, IConversationListener.DESCRIPTOR);
        }

        public static IConversationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IConversationListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IConversationListener)) {
                return (IConversationListener) iin;
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
                    return "onConversationUpdate";
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
                data.enforceInterface(IConversationListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IConversationListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ConversationChannel _arg0 =
                            (ConversationChannel) data.readTypedObject(ConversationChannel.CREATOR);
                    data.enforceNoDataAvail();
                    onConversationUpdate(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IConversationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConversationListener.DESCRIPTOR;
            }

            @Override // android.app.people.IConversationListener
            public void onConversationUpdate(ConversationChannel conversation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IConversationListener.DESCRIPTOR);
                    _data.writeTypedObject(conversation, 0);
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
