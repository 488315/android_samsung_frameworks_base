package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.aidl.ICapabilityExchangeEventListener;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IOptionsResponseCallback;
import android.telephony.ims.aidl.IPublishResponseCallback;
import android.telephony.ims.aidl.ISubscribeResponseCallback;
import android.telephony.ims.feature.CapabilityChangeRequest;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsRcsFeature extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRcsFeature";

    public static class Default implements IImsRcsFeature {
        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int getFeatureState() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void publishCapabilities(String str, IPublishResponseCallback iPublishResponseCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int queryCapabilityStatus() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void sendOptionsCapabilityRequest(Uri uri, List<String> list, IOptionsResponseCallback iOptionsResponseCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void setCapabilityExchangeEventListener(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void subscribeForCapabilities(List<Uri> list, ISubscribeResponseCallback iSubscribeResponseCallback) throws RemoteException {
        }
    }

    void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    int getFeatureState() throws RemoteException;

    void publishCapabilities(String str, IPublishResponseCallback iPublishResponseCallback) throws RemoteException;

    void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    int queryCapabilityStatus() throws RemoteException;

    void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void sendOptionsCapabilityRequest(Uri uri, List<String> list, IOptionsResponseCallback iOptionsResponseCallback) throws RemoteException;

    void setCapabilityExchangeEventListener(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws RemoteException;

    void subscribeForCapabilities(List<Uri> list, ISubscribeResponseCallback iSubscribeResponseCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsRcsFeature {
        static final int TRANSACTION_addCapabilityCallback = 3;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 5;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_publishCapabilities = 8;
        static final int TRANSACTION_queryCapabilityConfiguration = 6;
        static final int TRANSACTION_queryCapabilityStatus = 1;
        static final int TRANSACTION_removeCapabilityCallback = 4;
        static final int TRANSACTION_sendOptionsCapabilityRequest = 10;
        static final int TRANSACTION_setCapabilityExchangeEventListener = 7;
        static final int TRANSACTION_subscribeForCapabilities = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IImsRcsFeature.DESCRIPTOR);
        }

        public static IImsRcsFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsRcsFeature.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsRcsFeature)) {
                return (IImsRcsFeature) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryCapabilityStatus";
                case 2:
                    return "getFeatureState";
                case 3:
                    return "addCapabilityCallback";
                case 4:
                    return "removeCapabilityCallback";
                case 5:
                    return "changeCapabilitiesConfiguration";
                case 6:
                    return "queryCapabilityConfiguration";
                case 7:
                    return "setCapabilityExchangeEventListener";
                case 8:
                    return "publishCapabilities";
                case 9:
                    return "subscribeForCapabilities";
                case 10:
                    return "sendOptionsCapabilityRequest";
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
                parcel.enforceInterface(IImsRcsFeature.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsRcsFeature.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int iQueryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCapabilityStatus);
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(iImsCapabilityCallbackAsInterface);
                    return true;
                case 4:
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface2 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(iImsCapabilityCallbackAsInterface2);
                    return true;
                case 5:
                    CapabilityChangeRequest capabilityChangeRequest = (CapabilityChangeRequest) parcel.readTypedObject(CapabilityChangeRequest.CREATOR);
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface3 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, iImsCapabilityCallbackAsInterface3);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface4 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(i3, i4, iImsCapabilityCallbackAsInterface4);
                    return true;
                case 7:
                    ICapabilityExchangeEventListener iCapabilityExchangeEventListenerAsInterface = ICapabilityExchangeEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCapabilityExchangeEventListener(iCapabilityExchangeEventListenerAsInterface);
                    return true;
                case 8:
                    String string = parcel.readString();
                    IPublishResponseCallback iPublishResponseCallbackAsInterface = IPublishResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    publishCapabilities(string, iPublishResponseCallbackAsInterface);
                    return true;
                case 9:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    ISubscribeResponseCallback iSubscribeResponseCallbackAsInterface = ISubscribeResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeForCapabilities(arrayListCreateTypedArrayList, iSubscribeResponseCallbackAsInterface);
                    return true;
                case 10:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    IOptionsResponseCallback iOptionsResponseCallbackAsInterface = IOptionsResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendOptionsCapabilityRequest(uri, arrayListCreateStringArrayList, iOptionsResponseCallbackAsInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsRcsFeature {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRcsFeature.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public int queryCapabilityStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public int getFeatureState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeTypedObject(capabilityChangeRequest, 0);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void setCapabilityExchangeEventListener(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCapabilityExchangeEventListener);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void publishCapabilities(String str, IPublishResponseCallback iPublishResponseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPublishResponseCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void subscribeForCapabilities(List<Uri> list, ISubscribeResponseCallback iSubscribeResponseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iSubscribeResponseCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void sendOptionsCapabilityRequest(Uri uri, List<String> list, IOptionsResponseCallback iOptionsResponseCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iOptionsResponseCallback);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
