package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactUceCapability;
import android.telephony.ims.SipDetails;
import java.util.List;

/* loaded from: classes4.dex */
public interface IRcsUceControllerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcsUceControllerCallback";

    void onCapabilitiesReceived(List<RcsContactUceCapability> list) throws RemoteException;

    void onComplete(SipDetails sipDetails) throws RemoteException;

    void onError(int i, long j, SipDetails sipDetails) throws RemoteException;

    public static class Default implements IRcsUceControllerCallback {
        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(List<RcsContactUceCapability> contactCapabilities) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(SipDetails details) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(int errorCode, long retryAfterMilliseconds, SipDetails details) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRcsUceControllerCallback {
        static final int TRANSACTION_onCapabilitiesReceived = 1;
        static final int TRANSACTION_onComplete = 2;
        static final int TRANSACTION_onError = 3;

        public Stub() {
            attachInterface(this, IRcsUceControllerCallback.DESCRIPTOR);
        }

        public static IRcsUceControllerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRcsUceControllerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRcsUceControllerCallback)) {
                return (IRcsUceControllerCallback) iin;
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
                    return "onCapabilitiesReceived";
                case 2:
                    return "onComplete";
                case 3:
                    return "onError";
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
                data.enforceInterface(IRcsUceControllerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRcsUceControllerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<RcsContactUceCapability> _arg0 = data.createTypedArrayList(RcsContactUceCapability.CREATOR);
                    data.enforceNoDataAvail();
                    onCapabilitiesReceived(_arg0);
                    return true;
                case 2:
                    SipDetails _arg02 = (SipDetails) data.readTypedObject(SipDetails.CREATOR);
                    data.enforceNoDataAvail();
                    onComplete(_arg02);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    long _arg1 = data.readLong();
                    SipDetails _arg2 = (SipDetails) data.readTypedObject(SipDetails.CREATOR);
                    data.enforceNoDataAvail();
                    onError(_arg03, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRcsUceControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRcsUceControllerCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onCapabilitiesReceived(List<RcsContactUceCapability> contactCapabilities) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    _data.writeTypedList(contactCapabilities, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onComplete(SipDetails details) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    _data.writeTypedObject(details, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onError(int errorCode, long retryAfterMilliseconds, SipDetails details) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeLong(retryAfterMilliseconds);
                    _data.writeTypedObject(details, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
