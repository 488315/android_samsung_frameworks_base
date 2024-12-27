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

    void cleanupSession(String str) throws RemoteException;

    void notifyMessageReceiveError(String str, int i) throws RemoteException;

    void notifyMessageReceived(String str) throws RemoteException;

    void sendMessage(SipMessage sipMessage, long j) throws RemoteException;

    public static class Default implements ISipDelegate {
        @Override // android.telephony.ims.aidl.ISipDelegate
        public void sendMessage(SipMessage sipMessage, long configVersion) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceived(String viaTransactionId) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceiveError(String viaTransactionId, int reason) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void cleanupSession(String callId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISipDelegate {
        static final int TRANSACTION_cleanupSession = 4;
        static final int TRANSACTION_notifyMessageReceiveError = 3;
        static final int TRANSACTION_notifyMessageReceived = 2;
        static final int TRANSACTION_sendMessage = 1;

        public Stub() {
            attachInterface(this, ISipDelegate.DESCRIPTOR);
        }

        public static ISipDelegate asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISipDelegate.DESCRIPTOR);
            if (iin != null && (iin instanceof ISipDelegate)) {
                return (ISipDelegate) iin;
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
                    return "sendMessage";
                case 2:
                    return "notifyMessageReceived";
                case 3:
                    return "notifyMessageReceiveError";
                case 4:
                    return "cleanupSession";
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
                data.enforceInterface(ISipDelegate.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISipDelegate.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SipMessage _arg0 = (SipMessage) data.readTypedObject(SipMessage.CREATOR);
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    sendMessage(_arg0, _arg1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    notifyMessageReceived(_arg02);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyMessageReceiveError(_arg03, _arg12);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    cleanupSession(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISipDelegate {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegate.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void sendMessage(SipMessage sipMessage, long configVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    _data.writeTypedObject(sipMessage, 0);
                    _data.writeLong(configVersion);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceived(String viaTransactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    _data.writeString(viaTransactionId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void notifyMessageReceiveError(String viaTransactionId, int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    _data.writeString(viaTransactionId);
                    _data.writeInt(reason);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegate
            public void cleanupSession(String callId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISipDelegate.DESCRIPTOR);
                    _data.writeString(callId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
