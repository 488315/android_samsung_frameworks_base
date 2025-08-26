package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.DelegateRequest;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.aidl.IRcsUceControllerCallback;
import android.telephony.ims.aidl.IRcsUcePublishStateCallback;
import android.telephony.ims.aidl.ISipDelegate;
import android.telephony.ims.aidl.ISipDelegateConnectionStateCallback;
import android.telephony.ims.aidl.ISipDelegateMessageCallback;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ISipDialogStateCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsRcsController extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRcsController";

    public static class Default implements IImsRcsController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void createSipDelegate(int i, DelegateRequest delegateRequest, String str, ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void destroySipDelegate(int i, ISipDelegate iSipDelegate, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void getImsRcsRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void getImsRcsRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public int getUcePublishState(int i) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isAvailable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isCapable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isSipDelegateSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public boolean isUceSettingEnabled(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerRcsFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void registerUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void requestAvailability(int i, String str, String str2, Uri uri, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void requestCapabilities(int i, String str, String str2, List<Uri> list, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void setUceSettingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void triggerNetworkRegistration(int i, ISipDelegate iSipDelegate, int i2, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRcsController
        public void unregisterUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
        }
    }

    void createSipDelegate(int i, DelegateRequest delegateRequest, String str, ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException;

    void destroySipDelegate(int i, ISipDelegate iSipDelegate, int i2) throws RemoteException;

    void getImsRcsRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void getImsRcsRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    int getUcePublishState(int i) throws RemoteException;

    boolean isAvailable(int i, int i2, int i3) throws RemoteException;

    boolean isCapable(int i, int i2, int i3) throws RemoteException;

    boolean isSipDelegateSupported(int i) throws RemoteException;

    boolean isUceSettingEnabled(int i, String str, String str2) throws RemoteException;

    void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void registerRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void registerRcsFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    void registerSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException;

    void registerUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException;

    void requestAvailability(int i, String str, String str2, Uri uri, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException;

    void requestCapabilities(int i, String str, String str2, List<Uri> list, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException;

    void setUceSettingEnabled(int i, boolean z) throws RemoteException;

    void triggerNetworkRegistration(int i, ISipDelegate iSipDelegate, int i2, String str) throws RemoteException;

    void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void unregisterRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void unregisterSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException;

    void unregisterUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsRcsController {
        static final int TRANSACTION_createSipDelegate = 17;
        static final int TRANSACTION_destroySipDelegate = 18;
        static final int TRANSACTION_getImsRcsRegistrationState = 3;
        static final int TRANSACTION_getImsRcsRegistrationTransportType = 4;
        static final int TRANSACTION_getUcePublishState = 11;
        static final int TRANSACTION_isAvailable = 8;
        static final int TRANSACTION_isCapable = 7;
        static final int TRANSACTION_isSipDelegateSupported = 16;
        static final int TRANSACTION_isUceSettingEnabled = 12;
        static final int TRANSACTION_registerImsRegistrationCallback = 1;
        static final int TRANSACTION_registerRcsAvailabilityCallback = 5;
        static final int TRANSACTION_registerRcsFeatureCallback = 22;
        static final int TRANSACTION_registerSipDialogStateCallback = 20;
        static final int TRANSACTION_registerUcePublishStateCallback = 14;
        static final int TRANSACTION_requestAvailability = 10;
        static final int TRANSACTION_requestCapabilities = 9;
        static final int TRANSACTION_setUceSettingEnabled = 13;
        static final int TRANSACTION_triggerNetworkRegistration = 19;
        static final int TRANSACTION_unregisterImsFeatureCallback = 23;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 2;
        static final int TRANSACTION_unregisterRcsAvailabilityCallback = 6;
        static final int TRANSACTION_unregisterSipDialogStateCallback = 21;
        static final int TRANSACTION_unregisterUcePublishStateCallback = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub() {
            attachInterface(this, IImsRcsController.DESCRIPTOR);
        }

        public static IImsRcsController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsRcsController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsRcsController)) {
                return (IImsRcsController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerImsRegistrationCallback";
                case 2:
                    return "unregisterImsRegistrationCallback";
                case 3:
                    return "getImsRcsRegistrationState";
                case 4:
                    return "getImsRcsRegistrationTransportType";
                case 5:
                    return "registerRcsAvailabilityCallback";
                case 6:
                    return "unregisterRcsAvailabilityCallback";
                case 7:
                    return "isCapable";
                case 8:
                    return "isAvailable";
                case 9:
                    return "requestCapabilities";
                case 10:
                    return "requestAvailability";
                case 11:
                    return "getUcePublishState";
                case 12:
                    return "isUceSettingEnabled";
                case 13:
                    return "setUceSettingEnabled";
                case 14:
                    return "registerUcePublishStateCallback";
                case 15:
                    return "unregisterUcePublishStateCallback";
                case 16:
                    return "isSipDelegateSupported";
                case 17:
                    return "createSipDelegate";
                case 18:
                    return "destroySipDelegate";
                case 19:
                    return "triggerNetworkRegistration";
                case 20:
                    return "registerSipDialogStateCallback";
                case 21:
                    return "unregisterSipDialogStateCallback";
                case 22:
                    return "registerRcsFeatureCallback";
                case 23:
                    return "unregisterImsFeatureCallback";
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
                parcel.enforceInterface(IImsRcsController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsRcsController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationCallback(i3, iImsRegistrationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface2 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationCallback(i4, iImsRegistrationCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationState(i5, iIntegerConsumerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface2 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationTransportType(i6, iIntegerConsumerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsAvailabilityCallback(i7, iImsCapabilityCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface2 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRcsAvailabilityCallback(i8, iImsCapabilityCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCapable = isCapable(i9, i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCapable);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAvailable = isAvailable(i12, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAvailable);
                    return true;
                case 9:
                    int i15 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    IRcsUceControllerCallback iRcsUceControllerCallbackAsInterface = IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestCapabilities(i15, string, string2, arrayListCreateTypedArrayList, iRcsUceControllerCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i16 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    IRcsUceControllerCallback iRcsUceControllerCallbackAsInterface2 = IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAvailability(i16, string3, string4, uri, iRcsUceControllerCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int ucePublishState = getUcePublishState(i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(ucePublishState);
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUceSettingEnabled = isUceSettingEnabled(i18, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUceSettingEnabled);
                    return true;
                case 13:
                    int i19 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUceSettingEnabled(i19, z);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    IRcsUcePublishStateCallback iRcsUcePublishStateCallbackAsInterface = IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUcePublishStateCallback(i20, iRcsUcePublishStateCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    IRcsUcePublishStateCallback iRcsUcePublishStateCallbackAsInterface2 = IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUcePublishStateCallback(i21, iRcsUcePublishStateCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSipDelegateSupported = isSipDelegateSupported(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSipDelegateSupported);
                    return true;
                case 17:
                    int i23 = parcel.readInt();
                    DelegateRequest delegateRequest = (DelegateRequest) parcel.readTypedObject(DelegateRequest.CREATOR);
                    String string7 = parcel.readString();
                    ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallbackAsInterface = ISipDelegateConnectionStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    ISipDelegateMessageCallback iSipDelegateMessageCallbackAsInterface = ISipDelegateMessageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSipDelegate(i23, delegateRequest, string7, iSipDelegateConnectionStateCallbackAsInterface, iSipDelegateMessageCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i24 = parcel.readInt();
                    ISipDelegate iSipDelegateAsInterface = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySipDelegate(i24, iSipDelegateAsInterface, i25);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i26 = parcel.readInt();
                    ISipDelegate iSipDelegateAsInterface2 = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int i27 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerNetworkRegistration(i26, iSipDelegateAsInterface2, i27, string8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i28 = parcel.readInt();
                    ISipDialogStateCallback iSipDialogStateCallbackAsInterface = ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSipDialogStateCallback(i28, iSipDialogStateCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i29 = parcel.readInt();
                    ISipDialogStateCallback iSipDialogStateCallbackAsInterface2 = ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSipDialogStateCallback(i29, iSipDialogStateCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i30 = parcel.readInt();
                    IImsServiceFeatureCallback iImsServiceFeatureCallbackAsInterface = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsFeatureCallback(i30, iImsServiceFeatureCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IImsServiceFeatureCallback iImsServiceFeatureCallbackAsInterface2 = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsFeatureCallback(iImsServiceFeatureCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsRcsController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRcsController.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isCapable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isAvailable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestCapabilities(int i, String str, String str2, List<Uri> list, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestAvailability(int i, String str, String str2, Uri uri, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public int getUcePublishState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isUceSettingEnabled(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void setUceSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isSipDelegateSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void createSipDelegate(int i, DelegateRequest delegateRequest, String str, ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(delegateRequest, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSipDelegateConnectionStateCallback);
                    parcelObtain.writeStrongInterface(iSipDelegateMessageCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void destroySipDelegate(int i, ISipDelegate iSipDelegate, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSipDelegate);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void triggerNetworkRegistration(int i, ISipDelegate iSipDelegate, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSipDelegate);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
