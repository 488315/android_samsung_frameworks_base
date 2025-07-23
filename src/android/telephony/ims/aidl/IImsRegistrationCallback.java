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

/* loaded from: classes4.dex */
public interface IImsRegistrationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistrationCallback";

    public static class Default implements IImsRegistrationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregistered(ImsReasonInfo imsReasonInfo, int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onDeregisteredWithDetails(ImsReasonInfo imsReasonInfo, int i, int i2, SipDetails sipDetails) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onRegistering(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onSubscriberAssociatedUriChanged(Uri[] uriArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistrationCallback
        public void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }
    }

    void onDeregistered(ImsReasonInfo imsReasonInfo, int i, int i2) throws RemoteException;

    void onDeregisteredWithDetails(ImsReasonInfo imsReasonInfo, int i, int i2, SipDetails sipDetails) throws RemoteException;

    void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException;

    void onRegistering(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException;

    void onSubscriberAssociatedUriChanged(Uri[] uriArr) throws RemoteException;

    void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsRegistrationCallback {
        static final int TRANSACTION_onDeregistered = 3;
        static final int TRANSACTION_onDeregisteredWithDetails = 4;
        static final int TRANSACTION_onRegistered = 1;
        static final int TRANSACTION_onRegistering = 2;
        static final int TRANSACTION_onSubscriberAssociatedUriChanged = 6;
        static final int TRANSACTION_onTechnologyChangeFailed = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IImsRegistrationCallback.DESCRIPTOR);
        }

        public static IImsRegistrationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsRegistrationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsRegistrationCallback)) {
                return (IImsRegistrationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsRegistrationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsRegistrationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ImsRegistrationAttributes imsRegistrationAttributes = (ImsRegistrationAttributes) parcel.readTypedObject(ImsRegistrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistered(imsRegistrationAttributes);
                    return true;
                case 2:
                    ImsRegistrationAttributes imsRegistrationAttributes2 = (ImsRegistrationAttributes) parcel.readTypedObject(ImsRegistrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistering(imsRegistrationAttributes2);
                    return true;
                case 3:
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeregistered(imsReasonInfo, readInt, readInt2);
                    return true;
                case 4:
                    ImsReasonInfo imsReasonInfo2 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    SipDetails sipDetails = (SipDetails) parcel.readTypedObject(SipDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeregisteredWithDetails(imsReasonInfo2, readInt3, readInt4, sipDetails);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo3 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTechnologyChangeFailed(readInt5, imsReasonInfo3);
                    return true;
                case 6:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSubscriberAssociatedUriChanged(uriArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsRegistrationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRegistrationCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistrationAttributes, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistering(ImsRegistrationAttributes imsRegistrationAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistrationAttributes, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregistered(ImsReasonInfo imsReasonInfo, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregisteredWithDetails(ImsReasonInfo imsReasonInfo, int i, int i2, SipDetails sipDetails) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onSubscriberAssociatedUriChanged(Uri[] uriArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationCallback.DESCRIPTOR);
                    obtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
