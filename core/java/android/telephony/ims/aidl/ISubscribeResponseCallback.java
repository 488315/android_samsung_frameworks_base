package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactTerminatedReason;
import android.telephony.ims.SipDetails;

import java.util.List;

public interface ISubscribeResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISubscribeResponseCallback";

    void onCommandError(int i) throws RemoteException;

    void onNetworkResponse(SipDetails sipDetails) throws RemoteException;

    void onNotifyCapabilitiesUpdate(List<String> list) throws RemoteException;

    void onResourceTerminated(List<RcsContactTerminatedReason> list) throws RemoteException;

    void onTerminated(String str, long j) throws RemoteException;

    public static class Default implements ISubscribeResponseCallback {
        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onCommandError(int code) throws RemoteException {}

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNetworkResponse(SipDetails detail) throws RemoteException {}

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNotifyCapabilitiesUpdate(List<String> pidfXmls) throws RemoteException {}

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onResourceTerminated(List<RcsContactTerminatedReason> uriTerminatedReason)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onTerminated(String reason, long retryAfterMilliseconds)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISubscribeResponseCallback {
        static final int TRANSACTION_onCommandError = 1;
        static final int TRANSACTION_onNetworkResponse = 2;
        static final int TRANSACTION_onNotifyCapabilitiesUpdate = 3;
        static final int TRANSACTION_onResourceTerminated = 4;
        static final int TRANSACTION_onTerminated = 5;

        public Stub() {
            attachInterface(this, ISubscribeResponseCallback.DESCRIPTOR);
        }

        public static ISubscribeResponseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISubscribeResponseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISubscribeResponseCallback)) {
                return (ISubscribeResponseCallback) iin;
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
                    return "onCommandError";
                case 2:
                    return "onNetworkResponse";
                case 3:
                    return "onNotifyCapabilitiesUpdate";
                case 4:
                    return "onResourceTerminated";
                case 5:
                    return "onTerminated";
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
                data.enforceInterface(ISubscribeResponseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISubscribeResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onCommandError(_arg0);
                    return true;
                case 2:
                    SipDetails _arg02 = (SipDetails) data.readTypedObject(SipDetails.CREATOR);
                    data.enforceNoDataAvail();
                    onNetworkResponse(_arg02);
                    return true;
                case 3:
                    List<String> _arg03 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    onNotifyCapabilitiesUpdate(_arg03);
                    return true;
                case 4:
                    List<RcsContactTerminatedReason> _arg04 =
                            data.createTypedArrayList(RcsContactTerminatedReason.CREATOR);
                    data.enforceNoDataAvail();
                    onResourceTerminated(_arg04);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    onTerminated(_arg05, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISubscribeResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISubscribeResponseCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onCommandError(int code) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    _data.writeInt(code);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNetworkResponse(SipDetails detail) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(detail, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNotifyCapabilitiesUpdate(List<String> pidfXmls) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    _data.writeStringList(pidfXmls);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onResourceTerminated(List<RcsContactTerminatedReason> uriTerminatedReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    _data.writeTypedList(uriTerminatedReason, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onTerminated(String reason, long retryAfterMilliseconds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    _data.writeString(reason);
                    _data.writeLong(retryAfterMilliseconds);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
