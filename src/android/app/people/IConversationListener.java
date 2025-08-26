package android.app.people;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IConversationListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.people.IConversationListener";

    public static class Default implements IConversationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.people.IConversationListener
        public void onConversationUpdate(ConversationChannel conversationChannel) throws RemoteException {
        }
    }

    void onConversationUpdate(ConversationChannel conversationChannel) throws RemoteException;

    public static abstract class Stub extends Binder implements IConversationListener {
        static final int TRANSACTION_onConversationUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IConversationListener.DESCRIPTOR);
        }

        public static IConversationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IConversationListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConversationListener)) {
                return (IConversationListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onConversationUpdate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IConversationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IConversationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ConversationChannel conversationChannel = (ConversationChannel) parcel.readTypedObject(ConversationChannel.CREATOR);
                parcel.enforceNoDataAvail();
                onConversationUpdate(conversationChannel);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IConversationListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConversationListener.DESCRIPTOR;
            }

            @Override // android.app.people.IConversationListener
            public void onConversationUpdate(ConversationChannel conversationChannel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IConversationListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(conversationChannel, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
