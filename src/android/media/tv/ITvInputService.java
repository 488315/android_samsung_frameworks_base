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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputService)) {
                return (ITvInputService) queryLocalInterface;
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
                    ITvInputServiceCallback asInterface = ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    return true;
                case 2:
                    ITvInputServiceCallback asInterface2 = ITvInputServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    return true;
                case 3:
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    ITvInputSessionCallback asInterface3 = ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface3, readString, readString2, attributionSource);
                    return true;
                case 4:
                    ITvInputSessionCallback asInterface4 = ITvInputSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createRecordingSession(asInterface4, readString3, readString4);
                    return true;
                case 5:
                    List<String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(readString5);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String extensionInterfacePermission = getExtensionInterfacePermission(readString6);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputServiceCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String str, String str2, AttributionSource attributionSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iTvInputSessionCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputSessionCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public List<String> getAvailableExtensionInterfaceNames() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public IBinder getExtensionInterface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public String getExtensionInterfacePermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputHardwareInfo, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(hdmiDeviceInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
