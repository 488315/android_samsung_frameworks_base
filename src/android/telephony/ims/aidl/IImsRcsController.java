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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsRcsController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsRcsController)) {
                return (IImsRcsController) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    IImsRegistrationCallback asInterface = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationCallback(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    IImsRegistrationCallback asInterface2 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationCallback(readInt2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    IIntegerConsumer asInterface3 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationState(readInt3, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    IIntegerConsumer asInterface4 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsRcsRegistrationTransportType(readInt4, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    IImsCapabilityCallback asInterface5 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsAvailabilityCallback(readInt5, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    IImsCapabilityCallback asInterface6 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRcsAvailabilityCallback(readInt6, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCapable = isCapable(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCapable);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAvailable = isAvailable(readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailable);
                    return true;
                case 9:
                    int readInt13 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    IRcsUceControllerCallback asInterface7 = IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestCapabilities(readInt13, readString, readString2, createTypedArrayList, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt14 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    IRcsUceControllerCallback asInterface8 = IRcsUceControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAvailability(readInt14, readString3, readString4, uri, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int ucePublishState = getUcePublishState(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(ucePublishState);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUceSettingEnabled = isUceSettingEnabled(readInt16, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUceSettingEnabled);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUceSettingEnabled(readInt17, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    IRcsUcePublishStateCallback asInterface9 = IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUcePublishStateCallback(readInt18, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    IRcsUcePublishStateCallback asInterface10 = IRcsUcePublishStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUcePublishStateCallback(readInt19, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSipDelegateSupported = isSipDelegateSupported(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSipDelegateSupported);
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    DelegateRequest delegateRequest = (DelegateRequest) parcel.readTypedObject(DelegateRequest.CREATOR);
                    String readString7 = parcel.readString();
                    ISipDelegateConnectionStateCallback asInterface11 = ISipDelegateConnectionStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    ISipDelegateMessageCallback asInterface12 = ISipDelegateMessageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSipDelegate(readInt21, delegateRequest, readString7, asInterface11, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    ISipDelegate asInterface13 = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySipDelegate(readInt22, asInterface13, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt24 = parcel.readInt();
                    ISipDelegate asInterface14 = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                    int readInt25 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerNetworkRegistration(readInt24, asInterface14, readInt25, readString8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt26 = parcel.readInt();
                    ISipDialogStateCallback asInterface15 = ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSipDialogStateCallback(readInt26, asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt27 = parcel.readInt();
                    ISipDialogStateCallback asInterface16 = ISipDialogStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSipDialogStateCallback(readInt27, asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt28 = parcel.readInt();
                    IImsServiceFeatureCallback asInterface17 = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsFeatureCallback(readInt28, asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IImsServiceFeatureCallback asInterface18 = IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsFeatureCallback(asInterface18);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationState(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void getImsRcsRegistrationTransportType(int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterRcsAvailabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isCapable(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isAvailable(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestCapabilities(int i, String str, String str2, List<Uri> list, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void requestAvailability(int i, String str, String str2, Uri uri, IRcsUceControllerCallback iRcsUceControllerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeStrongInterface(iRcsUceControllerCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public int getUcePublishState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isUceSettingEnabled(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void setUceSettingEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterUcePublishStateCallback(int i, IRcsUcePublishStateCallback iRcsUcePublishStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsUcePublishStateCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public boolean isSipDelegateSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void createSipDelegate(int i, DelegateRequest delegateRequest, String str, ISipDelegateConnectionStateCallback iSipDelegateConnectionStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(delegateRequest, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSipDelegateConnectionStateCallback);
                    obtain.writeStrongInterface(iSipDelegateMessageCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void destroySipDelegate(int i, ISipDelegate iSipDelegate, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void triggerNetworkRegistration(int i, ISipDelegate iSipDelegate, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterSipDialogStateCallback(int i, ISipDialogStateCallback iSipDialogStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSipDialogStateCallback);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void registerRcsFeatureCallback(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRcsController
            public void unregisterImsFeatureCallback(IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRcsController.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
