package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.SipDetails;
import android.telephony.ims.aidl.IOptionsRequestCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICapabilityExchangeEventListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ICapabilityExchangeEventListener";

    public static class Default implements ICapabilityExchangeEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onPublishUpdated(SipDetails sipDetails) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onRemoteCapabilityRequest(Uri uri, List<String> list, IOptionsRequestCallback iOptionsRequestCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onRequestPublishCapabilities(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
        public void onUnpublish() throws RemoteException {
        }
    }

    void onPublishUpdated(SipDetails sipDetails) throws RemoteException;

    void onRemoteCapabilityRequest(Uri uri, List<String> list, IOptionsRequestCallback iOptionsRequestCallback) throws RemoteException;

    void onRequestPublishCapabilities(int i) throws RemoteException;

    void onUnpublish() throws RemoteException;

    public static abstract class Stub extends Binder implements ICapabilityExchangeEventListener {
        static final int TRANSACTION_onPublishUpdated = 3;
        static final int TRANSACTION_onRemoteCapabilityRequest = 4;
        static final int TRANSACTION_onRequestPublishCapabilities = 1;
        static final int TRANSACTION_onUnpublish = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ICapabilityExchangeEventListener.DESCRIPTOR);
        }

        public static ICapabilityExchangeEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICapabilityExchangeEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICapabilityExchangeEventListener)) {
                return (ICapabilityExchangeEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRequestPublishCapabilities";
            }
            if (i == 2) {
                return "onUnpublish";
            }
            if (i == 3) {
                return "onPublishUpdated";
            }
            if (i != 4) {
                return null;
            }
            return "onRemoteCapabilityRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICapabilityExchangeEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICapabilityExchangeEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRequestPublishCapabilities(i3);
            } else if (i == 2) {
                onUnpublish();
            } else if (i == 3) {
                SipDetails sipDetails = (SipDetails) parcel.readTypedObject(SipDetails.CREATOR);
                parcel.enforceNoDataAvail();
                onPublishUpdated(sipDetails);
            } else if (i == 4) {
                Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                IOptionsRequestCallback iOptionsRequestCallbackAsInterface = IOptionsRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onRemoteCapabilityRequest(uri, arrayListCreateStringArrayList, iOptionsRequestCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICapabilityExchangeEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICapabilityExchangeEventListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onRequestPublishCapabilities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityExchangeEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onUnpublish() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityExchangeEventListener.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onPublishUpdated(SipDetails sipDetails) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityExchangeEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ICapabilityExchangeEventListener
            public void onRemoteCapabilityRequest(Uri uri, List<String> list, IOptionsRequestCallback iOptionsRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityExchangeEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iOptionsRequestCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
