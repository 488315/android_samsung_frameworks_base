package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsRegistrationAttributes;
import android.telephony.ims.SipDetails;

public interface IImsRegistrationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistrationCallback";

    void onDeregistered(ImsReasonInfo imsReasonInfo, int i, int i2) throws RemoteException;

    void onDeregisteredWithDetails(
            ImsReasonInfo imsReasonInfo, int i, int i2, SipDetails sipDetails)
            throws RemoteException;

    void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException;

    void onRegistering(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException;

    void onSubscriberAssociatedUriChanged(Uri[] uriArr) throws RemoteException;

    void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    public static class Default implements IImsRegistrationCallback {
        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistered(ImsRegistrationAttributes attr) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistering(ImsRegistrationAttributes attr) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregistered(ImsReasonInfo info, int suggestedAction, int imsRadioTech)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregisteredWithDetails(
                ImsReasonInfo info, int suggestedAction, int imsRadioTech, SipDetails detail)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onTechnologyChangeFailed(int imsRadioTech, ImsReasonInfo info)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onSubscriberAssociatedUriChanged(Uri[] uris) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IImsRegistrationCallback {
        static final int TRANSACTION_onDeregistered = 3;
        static final int TRANSACTION_onDeregisteredWithDetails = 4;
        static final int TRANSACTION_onRegistered = 1;
        static final int TRANSACTION_onRegistering = 2;
        static final int TRANSACTION_onSubscriberAssociatedUriChanged = 6;
        static final int TRANSACTION_onTechnologyChangeFailed = 5;

        public Stub() {
            attachInterface(this, IImsRegistrationCallback.DESCRIPTOR);
        }

        public static IImsRegistrationCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsRegistrationCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsRegistrationCallback)) {
                return (IImsRegistrationCallback) iin;
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
                    return "onRegistered";
                case 2:
                    return "onRegistering";
                case 3:
                    return "onDeregistered";
                case 4:
                    return "onDeregisteredWithDetails";
                case 5:
                    return "onTechnologyChangeFailed";
                case 6:
                    return "onSubscriberAssociatedUriChanged";
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
                data.enforceInterface(IImsRegistrationCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImsRegistrationCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ImsRegistrationAttributes _arg0 =
                            (ImsRegistrationAttributes)
                                    data.readTypedObject(ImsRegistrationAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    onRegistered(_arg0);
                    return true;
                case 2:
                    ImsRegistrationAttributes _arg02 =
                            (ImsRegistrationAttributes)
                                    data.readTypedObject(ImsRegistrationAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    onRegistering(_arg02);
                    return true;
                case 3:
                    ImsReasonInfo _arg03 =
                            (ImsReasonInfo) data.readTypedObject(ImsReasonInfo.CREATOR);
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onDeregistered(_arg03, _arg1, _arg2);
                    return true;
                case 4:
                    ImsReasonInfo _arg04 =
                            (ImsReasonInfo) data.readTypedObject(ImsReasonInfo.CREATOR);
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    SipDetails _arg3 = (SipDetails) data.readTypedObject(SipDetails.CREATOR);
                    data.enforceNoDataAvail();
                    onDeregisteredWithDetails(_arg04, _arg12, _arg22, _arg3);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    ImsReasonInfo _arg13 =
                            (ImsReasonInfo) data.readTypedObject(ImsReasonInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onTechnologyChangeFailed(_arg05, _arg13);
                    return true;
                case 6:
                    Uri[] _arg06 = (Uri[]) data.createTypedArray(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    onSubscriberAssociatedUriChanged(_arg06);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsRegistrationCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRegistrationCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistered(ImsRegistrationAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeTypedObject(attr, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistering(ImsRegistrationAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeTypedObject(attr, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregistered(ImsReasonInfo info, int suggestedAction, int imsRadioTech)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(suggestedAction);
                    _data.writeInt(imsRadioTech);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregisteredWithDetails(
                    ImsReasonInfo info, int suggestedAction, int imsRadioTech, SipDetails detail)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(suggestedAction);
                    _data.writeInt(imsRadioTech);
                    _data.writeTypedObject(detail, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onTechnologyChangeFailed(int imsRadioTech, ImsReasonInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeInt(imsRadioTech);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onSubscriberAssociatedUriChanged(Uri[] uris) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    _data.writeTypedArray(uris, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
