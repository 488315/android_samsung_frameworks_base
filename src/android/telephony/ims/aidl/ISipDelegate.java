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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISipDelegate.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISipDelegate)) {
                return (ISipDelegate) queryLocalInterface;
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
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                sendMessage(sipMessage, readLong);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                notifyMessageReceived(readString);
            } else if (i == 3) {
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                notifyMessageReceiveError(readString2, readInt);
            } else if (i == 4) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                cleanupSession(readString3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    obtain.writeTypedObject(sipMessage, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceived(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceiveError(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void cleanupSession(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
