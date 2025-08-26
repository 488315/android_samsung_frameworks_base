package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsServiceControllerListener;
import android.telephony.ims.aidl.ISipTransport;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import com.android.ims.internal.IImsFeatureStatusCallback;

/* loaded from: classes4.dex */
public interface IImsServiceController extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceController";

    public static class Default implements IImsServiceController {
        @Override // android.telephony.ims.aidl.IImsServiceController
        public void addFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsMmTelFeature createMmTelFeature(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsRcsFeature createRcsFeature(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void disableIms(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void enableIms(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsConfig getConfig(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public long getImsServiceCapabilities() throws RemoteException {
            return 0L;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsRegistration getRegistration(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public ISipTransport getSipTransport(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void notifyImsServiceReadyForFeatureCreation() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeImsFeature(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void resetIms(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void setListener(IImsServiceControllerListener iImsServiceControllerListener) throws RemoteException {
        }
    }

    void addFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException;

    IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws RemoteException;

    IImsMmTelFeature createMmTelFeature(int i, int i2) throws RemoteException;

    IImsRcsFeature createRcsFeature(int i, int i2) throws RemoteException;

    void disableIms(int i, int i2) throws RemoteException;

    void enableIms(int i, int i2) throws RemoteException;

    IImsConfig getConfig(int i, int i2) throws RemoteException;

    long getImsServiceCapabilities() throws RemoteException;

    IImsRegistration getRegistration(int i, int i2) throws RemoteException;

    ISipTransport getSipTransport(int i) throws RemoteException;

    void notifyImsServiceReadyForFeatureCreation() throws RemoteException;

    ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException;

    void removeFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException;

    void removeImsFeature(int i, int i2, boolean z) throws RemoteException;

    void resetIms(int i, int i2) throws RemoteException;

    void setListener(IImsServiceControllerListener iImsServiceControllerListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsServiceController {
        static final int TRANSACTION_addFeatureStatusCallback = 7;
        static final int TRANSACTION_createEmergencyOnlyMmTelFeature = 3;
        static final int TRANSACTION_createMmTelFeature = 2;
        static final int TRANSACTION_createRcsFeature = 4;
        static final int TRANSACTION_disableIms = 15;
        static final int TRANSACTION_enableIms = 14;
        static final int TRANSACTION_getConfig = 11;
        static final int TRANSACTION_getImsServiceCapabilities = 6;
        static final int TRANSACTION_getRegistration = 12;
        static final int TRANSACTION_getSipTransport = 13;
        static final int TRANSACTION_notifyImsServiceReadyForFeatureCreation = 9;
        static final int TRANSACTION_querySupportedImsFeatures = 5;
        static final int TRANSACTION_removeFeatureStatusCallback = 8;
        static final int TRANSACTION_removeImsFeature = 10;
        static final int TRANSACTION_resetIms = 16;
        static final int TRANSACTION_setListener = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, IImsServiceController.DESCRIPTOR);
        }

        public static IImsServiceController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsServiceController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsServiceController)) {
                return (IImsServiceController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "createMmTelFeature";
                case 3:
                    return "createEmergencyOnlyMmTelFeature";
                case 4:
                    return "createRcsFeature";
                case 5:
                    return "querySupportedImsFeatures";
                case 6:
                    return "getImsServiceCapabilities";
                case 7:
                    return "addFeatureStatusCallback";
                case 8:
                    return "removeFeatureStatusCallback";
                case 9:
                    return "notifyImsServiceReadyForFeatureCreation";
                case 10:
                    return "removeImsFeature";
                case 11:
                    return "getConfig";
                case 12:
                    return "getRegistration";
                case 13:
                    return "getSipTransport";
                case 14:
                    return "enableIms";
                case 15:
                    return "disableIms";
                case 16:
                    return "resetIms";
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
                parcel.enforceInterface(IImsServiceController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsServiceController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsServiceControllerListener iImsServiceControllerListenerAsInterface = IImsServiceControllerListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(iImsServiceControllerListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsMmTelFeature iImsMmTelFeatureCreateMmTelFeature = createMmTelFeature(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsMmTelFeatureCreateMmTelFeature);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsMmTelFeature iImsMmTelFeatureCreateEmergencyOnlyMmTelFeature = createEmergencyOnlyMmTelFeature(i5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsMmTelFeatureCreateEmergencyOnlyMmTelFeature);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsRcsFeature iImsRcsFeatureCreateRcsFeature = createRcsFeature(i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsRcsFeatureCreateRcsFeature);
                    return true;
                case 5:
                    ImsFeatureConfiguration imsFeatureConfigurationQuerySupportedImsFeatures = querySupportedImsFeatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imsFeatureConfigurationQuerySupportedImsFeatures, 1);
                    return true;
                case 6:
                    long imsServiceCapabilities = getImsServiceCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeLong(imsServiceCapabilities);
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    IImsFeatureStatusCallback iImsFeatureStatusCallbackAsInterface = IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addFeatureStatusCallback(i8, i9, iImsFeatureStatusCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    IImsFeatureStatusCallback iImsFeatureStatusCallbackAsInterface2 = IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeFeatureStatusCallback(i10, i11, iImsFeatureStatusCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    notifyImsServiceReadyForFeatureCreation();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeImsFeature(i12, i13, z);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsConfig config = getConfig(i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(config);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsRegistration registration = getRegistration(i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(registration);
                    return true;
                case 13:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ISipTransport sipTransport = getSipTransport(i18);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sipTransport);
                    return true;
                case 14:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIms(i19, i20);
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIms(i21, i22);
                    return true;
                case 16:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetIms(i23, i24);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsServiceController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsServiceController.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void setListener(IImsServiceControllerListener iImsServiceControllerListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsServiceControllerListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public IImsMmTelFeature createMmTelFeature(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMmTelFeature.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMmTelFeature.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public IImsRcsFeature createRcsFeature(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsRcsFeature.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsFeatureConfiguration) parcelObtain2.readTypedObject(ImsFeatureConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public long getImsServiceCapabilities() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void addFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void removeFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void notifyImsServiceReadyForFeatureCreation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void removeImsFeature(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public IImsConfig getConfig(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsConfig.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public IImsRegistration getRegistration(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsRegistration.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public ISipTransport getSipTransport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISipTransport.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void enableIms(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void disableIms(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void resetIms(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
