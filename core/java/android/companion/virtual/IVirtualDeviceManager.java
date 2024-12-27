package android.companion.virtual;

import android.Manifest;
import android.app.ActivityThread;
import android.content.AttributionSource;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplayConfig;
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

    IVirtualDevice createVirtualDevice(
            IBinder iBinder,
            AttributionSource attributionSource,
            int i,
            VirtualDeviceParams virtualDeviceParams,
            IVirtualDeviceActivityListener iVirtualDeviceActivityListener,
            IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener)
            throws RemoteException;

    int createVirtualDisplay(
            VirtualDisplayConfig virtualDisplayConfig,
            IVirtualDisplayCallback iVirtualDisplayCallback,
            IVirtualDevice iVirtualDevice,
            String str)
            throws RemoteException;

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

    void registerVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener)
            throws RemoteException;

    void unregisterVirtualDeviceListener(IVirtualDeviceListener iVirtualDeviceListener)
            throws RemoteException;

    public static class Default implements IVirtualDeviceManager {
        @Override // android.companion.virtual.IVirtualDeviceManager
        public IVirtualDevice createVirtualDevice(
                IBinder token,
                AttributionSource attributionSource,
                int associationId,
                VirtualDeviceParams params,
                IVirtualDeviceActivityListener activityListener,
                IVirtualDeviceSoundEffectListener soundEffectListener)
                throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public List<VirtualDevice> getVirtualDevices() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public VirtualDevice getVirtualDevice(int deviceId) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void registerVirtualDeviceListener(IVirtualDeviceListener listener)
                throws RemoteException {}

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void unregisterVirtualDeviceListener(IVirtualDeviceListener listener)
                throws RemoteException {}

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDeviceIdForDisplayId(int displayId) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public CharSequence getDisplayNameForPersistentDeviceId(String persistentDeviceId)
                throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isValidVirtualDeviceId(int deviceId) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getDevicePolicy(int deviceId, int policyType) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int createVirtualDisplay(
                VirtualDisplayConfig virtualDisplayConfig,
                IVirtualDisplayCallback callback,
                IVirtualDevice virtualDevice,
                String packageName)
                throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioPlaybackSessionId(int deviceId) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public int getAudioRecordingSessionId(int deviceId) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public void playSoundEffect(int deviceId, int effectType) throws RemoteException {}

        @Override // android.companion.virtual.IVirtualDeviceManager
        public boolean isVirtualDeviceOwnedMirrorDisplay(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDeviceManager
        public List<String> getAllPersistentDeviceIds() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVirtualDeviceManager {
        static final int TRANSACTION_createVirtualDevice = 1;
        static final int TRANSACTION_createVirtualDisplay = 10;
        static final int TRANSACTION_getAllPersistentDeviceIds = 15;
        static final int TRANSACTION_getAudioPlaybackSessionId = 11;
        static final int TRANSACTION_getAudioRecordingSessionId = 12;
        static final int TRANSACTION_getDeviceIdForDisplayId = 6;
        static final int TRANSACTION_getDevicePolicy = 9;
        static final int TRANSACTION_getDisplayNameForPersistentDeviceId = 7;
        static final int TRANSACTION_getVirtualDevice = 3;
        static final int TRANSACTION_getVirtualDevices = 2;
        static final int TRANSACTION_isValidVirtualDeviceId = 8;
        static final int TRANSACTION_isVirtualDeviceOwnedMirrorDisplay = 14;
        static final int TRANSACTION_playSoundEffect = 13;
        static final int TRANSACTION_registerVirtualDeviceListener = 4;
        static final int TRANSACTION_unregisterVirtualDeviceListener = 5;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IVirtualDeviceManager.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IVirtualDeviceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDeviceManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDeviceManager)) {
                return (IVirtualDeviceManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "createVirtualDisplay";
                case 11:
                    return "getAudioPlaybackSessionId";
                case 12:
                    return "getAudioRecordingSessionId";
                case 13:
                    return "playSoundEffect";
                case 14:
                    return "isVirtualDeviceOwnedMirrorDisplay";
                case 15:
                    return "getAllPersistentDeviceIds";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IVirtualDeviceManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    AttributionSource _arg1 =
                            (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
                    int _arg2 = data.readInt();
                    VirtualDeviceParams _arg3 =
                            (VirtualDeviceParams) data.readTypedObject(VirtualDeviceParams.CREATOR);
                    IVirtualDeviceActivityListener _arg4 =
                            IVirtualDeviceActivityListener.Stub.asInterface(
                                    data.readStrongBinder());
                    IVirtualDeviceSoundEffectListener _arg5 =
                            IVirtualDeviceSoundEffectListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    IVirtualDevice _result =
                            createVirtualDevice(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    List<VirtualDevice> _result2 = getVirtualDevices();
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    VirtualDevice _result3 = getVirtualDevice(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    IVirtualDeviceListener _arg03 =
                            IVirtualDeviceListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerVirtualDeviceListener(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    IVirtualDeviceListener _arg04 =
                            IVirtualDeviceListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterVirtualDeviceListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = getDeviceIdForDisplayId(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    CharSequence _result5 = getDisplayNameForPersistentDeviceId(_arg06);
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        TextUtils.writeToParcel(_result5, reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = isValidVirtualDeviceId(_arg07);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 9:
                    int _arg08 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result7 = getDevicePolicy(_arg08, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 10:
                    VirtualDisplayConfig _arg09 =
                            (VirtualDisplayConfig)
                                    data.readTypedObject(VirtualDisplayConfig.CREATOR);
                    IVirtualDisplayCallback _arg13 =
                            IVirtualDisplayCallback.Stub.asInterface(data.readStrongBinder());
                    IVirtualDevice _arg22 =
                            IVirtualDevice.Stub.asInterface(data.readStrongBinder());
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 = createVirtualDisplay(_arg09, _arg13, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result9 = getAudioPlaybackSessionId(_arg010);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result10 = getAudioRecordingSessionId(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 13:
                    int _arg012 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    playSoundEffect(_arg012, _arg14);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = isVirtualDeviceOwnedMirrorDisplay(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 15:
                    List<String> _result12 = getAllPersistentDeviceIds();
                    reply.writeNoException();
                    reply.writeStringList(_result12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDeviceManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceManager.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public IVirtualDevice createVirtualDevice(
                    IBinder token,
                    AttributionSource attributionSource,
                    int associationId,
                    VirtualDeviceParams params,
                    IVirtualDeviceActivityListener activityListener,
                    IVirtualDeviceSoundEffectListener soundEffectListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(attributionSource, 0);
                    _data.writeInt(associationId);
                    _data.writeTypedObject(params, 0);
                    _data.writeStrongInterface(activityListener);
                    _data.writeStrongInterface(soundEffectListener);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IVirtualDevice _result =
                            IVirtualDevice.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<VirtualDevice> getVirtualDevices() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<VirtualDevice> _result =
                            _reply.createTypedArrayList(VirtualDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public VirtualDevice getVirtualDevice(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    VirtualDevice _result =
                            (VirtualDevice) _reply.readTypedObject(VirtualDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void registerVirtualDeviceListener(IVirtualDeviceListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void unregisterVirtualDeviceListener(IVirtualDeviceListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDeviceIdForDisplayId(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public CharSequence getDisplayNameForPersistentDeviceId(String persistentDeviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeString(persistentDeviceId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    CharSequence _result =
                            (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isValidVirtualDeviceId(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getDevicePolicy(int deviceId, int policyType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeInt(policyType);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int createVirtualDisplay(
                    VirtualDisplayConfig virtualDisplayConfig,
                    IVirtualDisplayCallback callback,
                    IVirtualDevice virtualDevice,
                    String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeTypedObject(virtualDisplayConfig, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeStrongInterface(virtualDevice);
                    _data.writeString(packageName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioPlaybackSessionId(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public int getAudioRecordingSessionId(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public void playSoundEffect(int deviceId, int effectType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    _data.writeInt(effectType);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public boolean isVirtualDeviceOwnedMirrorDisplay(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceManager
            public List<String> getAllPersistentDeviceIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void createVirtualDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
