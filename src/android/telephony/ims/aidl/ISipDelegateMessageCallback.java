package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SipMessage;

/* loaded from: classes4.dex */
public interface ISipDelegateMessageCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegateMessageCallback";

    public static class Default implements ISipDelegateMessageCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageReceived(SipMessage sipMessage) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSendFailure(String str, int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSent(String str) throws RemoteException {
        }
    }

    void onMessageReceived(SipMessage sipMessage) throws RemoteException;

    void onMessageSendFailure(String str, int i) throws RemoteException;

    void onMessageSent(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipDelegateMessageCallback {
        static final int TRANSACTION_onMessageReceived = 1;
        static final int TRANSACTION_onMessageSendFailure = 3;
        static final int TRANSACTION_onMessageSent = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ISipDelegateMessageCallback.DESCRIPTOR);
        }

        public static ISipDelegateMessageCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISipDelegateMessageCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISipDelegateMessageCallback)) {
                return (ISipDelegateMessageCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMessageReceived";
            }
            if (i == 2) {
                return "onMessageSent";
            }
            if (i != 3) {
                return null;
            }
            return "onMessageSendFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipDelegateMessageCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDelegateMessageCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SipMessage sipMessage = (SipMessage) parcel.readTypedObject(SipMessage.CREATOR);
                parcel.enforceNoDataAvail();
                onMessageReceived(sipMessage);
            } else if (i == 2) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onMessageSent(string);
            } else if (i == 3) {
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMessageSendFailure(string2, i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISipDelegateMessageCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegateMessageCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
            public void onMessageReceived(SipMessage sipMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegateMessageCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sipMessage, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
            public void onMessageSent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegateMessageCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
            public void onMessageSendFailure(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegateMessageCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
