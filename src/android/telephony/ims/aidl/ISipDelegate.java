package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SipMessage;

/* loaded from: classes4.dex */
public interface ISipDelegate extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegate";

    public static class Default implements ISipDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void cleanupSession(String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceiveError(String str, int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceived(String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void sendMessage(SipMessage sipMessage, long j) throws RemoteException {
        }
    }

    void cleanupSession(String str) throws RemoteException;

    void notifyMessageReceiveError(String str, int i) throws RemoteException;

    void notifyMessageReceived(String str) throws RemoteException;

    void sendMessage(SipMessage sipMessage, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipDelegate {
        static final int TRANSACTION_cleanupSession = 4;
        static final int TRANSACTION_notifyMessageReceiveError = 3;
        static final int TRANSACTION_notifyMessageReceived = 2;
        static final int TRANSACTION_sendMessage = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISipDelegate.DESCRIPTOR);
        }

        public static ISipDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISipDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISipDelegate)) {
                return (ISipDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "sendMessage";
            }
            if (i == 2) {
                return "notifyMessageReceived";
            }
            if (i == 3) {
                return "notifyMessageReceiveError";
            }
            if (i != 4) {
                return null;
            }
            return "cleanupSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipDelegate.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SipMessage sipMessage = (SipMessage) parcel.readTypedObject(SipMessage.CREATOR);
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                sendMessage(sipMessage, j);
            } else if (i == 2) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                notifyMessageReceived(string);
            } else if (i == 3) {
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyMessageReceiveError(string2, i3);
            } else if (i == 4) {
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                cleanupSession(string3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISipDelegate {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegate.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void sendMessage(SipMessage sipMessage, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sipMessage, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceived(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceiveError(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void cleanupSession(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
