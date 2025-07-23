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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsRcsFeature.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsRcsFeature)) {
                return (IImsRcsFeature) queryLocalInterface;
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
                    int queryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCapabilityStatus);
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    IImsCapabilityCallback asInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(asInterface);
                    return true;
                case 4:
                    IImsCapabilityCallback asInterface2 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(asInterface2);
                    return true;
                case 5:
                    CapabilityChangeRequest capabilityChangeRequest = (CapabilityChangeRequest) parcel.readTypedObject(CapabilityChangeRequest.CREATOR);
                    IImsCapabilityCallback asInterface3 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, asInterface3);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    IImsCapabilityCallback asInterface4 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(readInt, readInt2, asInterface4);
                    return true;
                case 7:
                    ICapabilityExchangeEventListener asInterface5 = ICapabilityExchangeEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCapabilityExchangeEventListener(asInterface5);
                    return true;
                case 8:
                    String readString = parcel.readString();
                    IPublishResponseCallback asInterface6 = IPublishResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    publishCapabilities(readString, asInterface6);
                    return true;
                case 9:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    ISubscribeResponseCallback asInterface7 = ISubscribeResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    subscribeForCapabilities(createTypedArrayList, asInterface7);
                    return true;
                case 10:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IOptionsResponseCallback asInterface8 = IOptionsResponseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendOptionsCapabilityRequest(uri, createStringArrayList, asInterface8);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public int getFeatureState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedObject(capabilityChangeRequest, 0);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void setCapabilityExchangeEventListener(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapabilityExchangeEventListener);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void publishCapabilities(String str, IPublishResponseCallback iPublishResponseCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPublishResponseCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void subscribeForCapabilities(List<Uri> list, ISubscribeResponseCallback iSubscribeResponseCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iSubscribeResponseCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsFeature
            public void sendOptionsCapabilityRequest(Uri uri, List<String> list, IOptionsResponseCallback iOptionsResponseCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRcsFeature.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iOptionsResponseCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
