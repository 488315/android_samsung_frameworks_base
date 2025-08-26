package android.companion.virtual;

import android.Manifest;
import android.app.ActivityThread;
import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceListener;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.content.AttributionSource;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes.dex */
public interface IVirtualDeviceManager extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceManager";

    public static class Default implements IVirtualDeviceManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public IVirtualDevice createVirtualDevice(IBinder iBinder, AttributionSource attributionSource, int i, VirtualDeviceParams virtualDeviceParams, IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public List<String> getAllPersistentDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioPlaybackSessionId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioRecordingSessionId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDeviceIdForDisplayId(int i) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDevicePolicy(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public CharSequence getDisplayNameForPersistentDeviceId(String str) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public VirtualDevice getVirtualDevice(int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public List<VirtualDevice> getVirtualDevices() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isValidVirtualDeviceId(int i) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void playSoundEffect(int i, int i2) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void registerVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void unregisterVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
        }
    }

    IVirtualDevice createVirtualDevice(IBinder iBinder, AttributionSource attributionSource, int i, VirtualDeviceParams virtualDeviceParams, IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException;

    List<String> getAllPersistentDeviceIds() throws RemoteException;

    int getAudioPlaybackSessionId(int i) throws RemoteException;

    int getAudioRecordingSessionId(int i) throws RemoteException;

    int getDeviceIdForDisplayId(int i) throws RemoteException;

    int getDevicePolicy(int i, int i2) throws RemoteException;

    CharSequence getDisplayNameForPersistentDeviceId(String str) throws RemoteException;

    VirtualDevice getVirtualDevice(int i) throws RemoteException;

    List<VirtualDevice> getVirtualDevices() throws RemoteException;

    boolean isValidVirtualDeviceId(int i) throws RemoteException;

    boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws RemoteException;

    void playSoundEffect(int i, int i2) throws RemoteException;

    void registerVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException;

    void unregisterVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceManager {
        static final int TRANSACTION_createVirtualDevice = 1;
        static final int TRANSACTION_getAllPersistentDeviceIds = 14;
        static final int TRANSACTION_getAudioPlaybackSessionId = 10;
        static final int TRANSACTION_getAudioRecordingSessionId = 11;
        static final int TRANSACTION_getDeviceIdForDisplayId = 6;
        static final int TRANSACTION_getDevicePolicy = 9;
        static final int TRANSACTION_getDisplayNameForPersistentDeviceId = 7;
        static final int TRANSACTION_getVirtualDevice = 3;
        static final int TRANSACTION_getVirtualDevices = 2;
        static final int TRANSACTION_isValidVirtualDeviceId = 8;
        static final int TRANSACTION_isVirtualDeviceOwnedMirrorDisplay = 13;
        static final int TRANSACTION_playSoundEffect = 12;
        static final int TRANSACTION_registerVirtualDeviceListener = 4;
        static final int TRANSACTION_unregisterVirtualDeviceListener = 5;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IVirtualDeviceManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IVirtualDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualDeviceManager)) {
                return (IVirtualDeviceManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createVirtualDevice";
                case 2:
                    return "getVirtualDevices";
                case 3:
                    return "getVirtualDevice";
                case 4:
                    return "registerVirtualDeviceListener";
                case 5:
                    return "unregisterVirtualDeviceListener";
                case 6:
                    return "getDeviceIdForDisplayId";
                case 7:
                    return "getDisplayNameForPersistentDeviceId";
                case 8:
                    return "isValidVirtualDeviceId";
                case 9:
                    return "getDevicePolicy";
                case 10:
                    return "getAudioPlaybackSessionId";
                case 11:
                    return "getAudioRecordingSessionId";
                case 12:
                    return "playSoundEffect";
                case 13:
                    return "isVirtualDeviceOwnedMirrorDisplay";
                case 14:
                    return "getAllPersistentDeviceIds";
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
                parcel.enforceInterface(IVirtualDeviceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    int i3 = parcel.readInt();
                    VirtualDeviceParams virtualDeviceParams = (VirtualDeviceParams) parcel.readTypedObject(VirtualDeviceParams.CREATOR);
                    IVirtualDeviceActivityListener iVirtualDeviceActivityListenerAsInterface = IVirtualDeviceActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListenerAsInterface = IVirtualDeviceSoundEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IVirtualDevice iVirtualDeviceCreateVirtualDevice = createVirtualDevice(strongBinder, attributionSource, i3, virtualDeviceParams, iVirtualDeviceActivityListenerAsInterface, iVirtualDeviceSoundEffectListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iVirtualDeviceCreateVirtualDevice);
                    return true;
                case 2:
                    List<VirtualDevice> virtualDevices = getVirtualDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualDevices, 1);
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VirtualDevice virtualDevice = getVirtualDevice(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(virtualDevice, 1);
                    return true;
                case 4:
                    IVirtualDeviceListener iVirtualDeviceListenerAsInterface = IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVirtualDeviceListener(iVirtualDeviceListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IVirtualDeviceListener iVirtualDeviceListenerAsInterface2 = IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterVirtualDeviceListener(iVirtualDeviceListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceIdForDisplayId = getDeviceIdForDisplayId(i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceIdForDisplayId);
                    return true;
                case 7:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence displayNameForPersistentDeviceId = getDisplayNameForPersistentDeviceId(string);
                    parcel2.writeNoException();
                    if (displayNameForPersistentDeviceId != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(displayNameForPersistentDeviceId, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsValidVirtualDeviceId = isValidVirtualDeviceId(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsValidVirtualDeviceId);
                    return true;
                case 9:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 10:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioPlaybackSessionId = getAudioPlaybackSessionId(i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioPlaybackSessionId);
                    return true;
                case 11:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioRecordingSessionId = getAudioRecordingSessionId(i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioRecordingSessionId);
                    return true;
                case 12:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playSoundEffect(i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVirtualDeviceOwnedMirrorDisplay = isVirtualDeviceOwnedMirrorDisplay(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVirtualDeviceOwnedMirrorDisplay);
                    return true;
                case 14:
                    List<String> allPersistentDeviceIds = getAllPersistentDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allPersistentDeviceIds);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVirtualDeviceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceManager.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public IVirtualDevice createVirtualDevice(IBinder iBinder, AttributionSource attributionSource, int i, VirtualDeviceParams virtualDeviceParams, IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(virtualDeviceParams, 0);
                    parcelObtain.writeStrongInterface(iVirtualDeviceActivityListener);
                    parcelObtain.writeStrongInterface(iVirtualDeviceSoundEffectListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IVirtualDevice.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<VirtualDevice> getVirtualDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(VirtualDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public VirtualDevice getVirtualDevice(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VirtualDevice) parcelObtain2.readTypedObject(VirtualDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void registerVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void unregisterVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDeviceIdForDisplayId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public CharSequence getDisplayNameForPersistentDeviceId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isValidVirtualDeviceId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDevicePolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioPlaybackSessionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioRecordingSessionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void playSoundEffect(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<String> getAllPersistentDeviceIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void createVirtualDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }
    }
}
