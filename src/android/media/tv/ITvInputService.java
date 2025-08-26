package android.media.tv;

import android.content.AttributionSource;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.tv.ITvInputServiceCallback;
import android.media.tv.ITvInputSessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInputService extends IInterface {

    public static class Default implements ITvInputService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str, String str2, AttributionSource attributionSource) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public List<String> getAvailableExtensionInterfaceNames() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public IBinder getExtensionInterface(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public String getExtensionInterfacePermission(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void notifyHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputService
        public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
        }
    }

    void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str, String str2) throws RemoteException;

    void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str, String str2, AttributionSource attributionSource) throws RemoteException;

    List<String> getAvailableExtensionInterfaceNames() throws RemoteException;

    IBinder getExtensionInterface(String str) throws RemoteException;

    String getExtensionInterfacePermission(String str) throws RemoteException;

    void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException;

    void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void notifyHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException;

    void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputService {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputService";
        static final int TRANSACTION_createRecordingSession = 4;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_getAvailableExtensionInterfaceNames = 5;
        static final int TRANSACTION_getExtensionInterface = 6;
        static final int TRANSACTION_getExtensionInterfacePermission = 7;
        static final int TRANSACTION_notifyHardwareAdded = 8;
        static final int TRANSACTION_notifyHardwareRemoved = 9;
        static final int TRANSACTION_notifyHdmiDeviceAdded = 10;
        static final int TRANSACTION_notifyHdmiDeviceRemoved = 11;
        static final int TRANSACTION_notifyHdmiDeviceUpdated = 12;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInputService)) {
                return (ITvInputService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "createRecordingSession";
                case 5:
                    return "getAvailableExtensionInterfaceNames";
                case 6:
                    return "getExtensionInterface";
                case 7:
                    return "getExtensionInterfacePermission";
                case 8:
                    return "notifyHardwareAdded";
                case 9:
                    return "notifyHardwareRemoved";
                case 10:
                    return "notifyHdmiDeviceAdded";
                case 11:
                    return "notifyHdmiDeviceRemoved";
                case 12:
                    return "notifyHdmiDeviceUpdated";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITvInputServiceCallback iTvInputServiceCallbackAsInterface = ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iTvInputServiceCallbackAsInterface);
                    return true;
                case 2:
                    ITvInputServiceCallback iTvInputServiceCallbackAsInterface2 = ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iTvInputServiceCallbackAsInterface2);
                    return true;
                case 3:
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    ITvInputSessionCallback iTvInputSessionCallbackAsInterface = ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, iTvInputSessionCallbackAsInterface, string, string2, attributionSource);
                    return true;
                case 4:
                    ITvInputSessionCallback iTvInputSessionCallbackAsInterface2 = ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createRecordingSession(iTvInputSessionCallbackAsInterface2, string3, string4);
                    return true;
                case 5:
                    List<String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(string5);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String extensionInterfacePermission = getExtensionInterfacePermission(string6);
                    parcel2.writeNoException();
                    parcel2.writeString(extensionInterfacePermission);
                    return true;
                case 8:
                    TvInputHardwareInfo tvInputHardwareInfo = (TvInputHardwareInfo) parcel.readTypedObject(TvInputHardwareInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHardwareAdded(tvInputHardwareInfo);
                    return true;
                case 9:
                    TvInputHardwareInfo tvInputHardwareInfo2 = (TvInputHardwareInfo) parcel.readTypedObject(TvInputHardwareInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHardwareRemoved(tvInputHardwareInfo2);
                    return true;
                case 10:
                    HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) parcel.readTypedObject(HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceAdded(hdmiDeviceInfo);
                    return true;
                case 11:
                    HdmiDeviceInfo hdmiDeviceInfo2 = (HdmiDeviceInfo) parcel.readTypedObject(HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceRemoved(hdmiDeviceInfo2);
                    return true;
                case 12:
                    HdmiDeviceInfo hdmiDeviceInfo3 = (HdmiDeviceInfo) parcel.readTypedObject(HdmiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyHdmiDeviceUpdated(hdmiDeviceInfo3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputService
            public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str, String str2, AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeStrongInterface(iTvInputSessionCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInputSessionCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public List<String> getAvailableExtensionInterfaceNames() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public IBinder getExtensionInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public String getExtensionInterfacePermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
