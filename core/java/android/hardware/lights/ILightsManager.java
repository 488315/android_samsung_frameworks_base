package android.hardware.lights;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes2.dex */
public interface ILightsManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.lights.ILightsManager";

    void closeSession(IBinder iBinder) throws RemoteException;

    LightState getLightState(int i) throws RemoteException;

    List<Light> getLights() throws RemoteException;

    void openSession(IBinder iBinder, int i) throws RemoteException;

    void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr)
            throws RemoteException;

    public static class Default implements ILightsManager {
        @Override // android.hardware.lights.ILightsManager
        public List<Light> getLights() throws RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public LightState getLightState(int lightId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public void openSession(IBinder sessionToken, int priority) throws RemoteException {}

        @Override // android.hardware.lights.ILightsManager
        public void closeSession(IBinder sessionToken) throws RemoteException {}

        @Override // android.hardware.lights.ILightsManager
        public void setLightStates(IBinder sessionToken, int[] lightIds, LightState[] states)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ILightsManager {
        static final int TRANSACTION_closeSession = 4;
        static final int TRANSACTION_getLightState = 2;
        static final int TRANSACTION_getLights = 1;
        static final int TRANSACTION_openSession = 3;
        static final int TRANSACTION_setLightStates = 5;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, ILightsManager.DESCRIPTOR);
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

        public static ILightsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILightsManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ILightsManager)) {
                return (ILightsManager) iin;
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
                    return "getLights";
                case 2:
                    return "getLightState";
                case 3:
                    return "openSession";
                case 4:
                    return "closeSession";
                case 5:
                    return "setLightStates";
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
                data.enforceInterface(ILightsManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILightsManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<Light> _result = getLights();
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    LightState _result2 = getLightState(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    openSession(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg03 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    closeSession(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg04 = data.readStrongBinder();
                    int[] _arg12 = data.createIntArray();
                    LightState[] _arg2 = (LightState[]) data.createTypedArray(LightState.CREATOR);
                    data.enforceNoDataAvail();
                    setLightStates(_arg04, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILightsManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILightsManager.DESCRIPTOR;
            }

            @Override // android.hardware.lights.ILightsManager
            public List<Light> getLights() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<Light> _result = _reply.createTypedArrayList(Light.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public LightState getLightState(int lightId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    _data.writeInt(lightId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    LightState _result = (LightState) _reply.readTypedObject(LightState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void openSession(IBinder sessionToken, int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeInt(priority);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void closeSession(IBinder sessionToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void setLightStates(IBinder sessionToken, int[] lightIds, LightState[] states)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    _data.writeStrongBinder(sessionToken);
                    _data.writeIntArray(lightIds);
                    _data.writeTypedArray(states, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void getLights_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void getLightState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void openSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void closeSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void setLightStates_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
