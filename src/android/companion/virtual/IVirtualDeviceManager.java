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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDeviceManager)) {
                return (IVirtualDeviceManager) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    int readInt = parcel.readInt();
                    VirtualDeviceParams virtualDeviceParams = (VirtualDeviceParams) parcel.readTypedObject(VirtualDeviceParams.CREATOR);
                    IVirtualDeviceActivityListener asInterface = IVirtualDeviceActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    IVirtualDeviceSoundEffectListener asInterface2 = IVirtualDeviceSoundEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IVirtualDevice createVirtualDevice = createVirtualDevice(readStrongBinder, attributionSource, readInt, virtualDeviceParams, asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createVirtualDevice);
                    return true;
                case 2:
                    List<VirtualDevice> virtualDevices = getVirtualDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualDevices, 1);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VirtualDevice virtualDevice = getVirtualDevice(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(virtualDevice, 1);
                    return true;
                case 4:
                    IVirtualDeviceListener asInterface3 = IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVirtualDeviceListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IVirtualDeviceListener asInterface4 = IVirtualDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterVirtualDeviceListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceIdForDisplayId = getDeviceIdForDisplayId(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceIdForDisplayId);
                    return true;
                case 7:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CharSequence displayNameForPersistentDeviceId = getDisplayNameForPersistentDeviceId(readString);
                    parcel2.writeNoException();
                    if (displayNameForPersistentDeviceId != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(displayNameForPersistentDeviceId, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isValidVirtualDeviceId = isValidVirtualDeviceId(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValidVirtualDeviceId);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 10:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioPlaybackSessionId = getAudioPlaybackSessionId(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioPlaybackSessionId);
                    return true;
                case 11:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int audioRecordingSessionId = getAudioRecordingSessionId(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(audioRecordingSessionId);
                    return true;
                case 12:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playSoundEffect(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVirtualDeviceOwnedMirrorDisplay = isVirtualDeviceOwnedMirrorDisplay(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVirtualDeviceOwnedMirrorDisplay);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(virtualDeviceParams, 0);
                    obtain.writeStrongInterface(iVirtualDeviceActivityListener);
                    obtain.writeStrongInterface(iVirtualDeviceSoundEffectListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVirtualDevice.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<VirtualDevice> getVirtualDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(VirtualDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public VirtualDevice getVirtualDevice(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VirtualDevice) obtain2.readTypedObject(VirtualDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void registerVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void unregisterVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDeviceIdForDisplayId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public CharSequence getDisplayNameForPersistentDeviceId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isValidVirtualDeviceId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDevicePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioPlaybackSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioRecordingSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void playSoundEffect(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isVirtualDeviceOwnedMirrorDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<String> getAllPersistentDeviceIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createVirtualDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }
    }
}
