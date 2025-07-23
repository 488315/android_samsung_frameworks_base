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

    public static class Default implements ILightsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public void closeSession(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.lights.ILightsManager
        public LightState getLightState(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public List<Light> getLights() throws RemoteException {
            return null;
        }

        @Override // android.hardware.lights.ILightsManager
        public void openSession(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.hardware.lights.ILightsManager
        public void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr) throws RemoteException {
        }
    }

    void closeSession(IBinder iBinder) throws RemoteException;

    LightState getLightState(int i) throws RemoteException;

    List<Light> getLights() throws RemoteException;

    void openSession(IBinder iBinder, int i) throws RemoteException;

    void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ILightsManager {
        static final int TRANSACTION_closeSession = 4;
        static final int TRANSACTION_getLightState = 2;
        static final int TRANSACTION_getLights = 1;
        static final int TRANSACTION_openSession = 3;
        static final int TRANSACTION_setLightStates = 5;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, ILightsManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ILightsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILightsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILightsManager)) {
                return (ILightsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getLights";
            }
            if (i == 2) {
                return "getLightState";
            }
            if (i == 3) {
                return "openSession";
            }
            if (i == 4) {
                return "closeSession";
            }
            if (i != 5) {
                return null;
            }
            return "setLightStates";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILightsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILightsManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                List<Light> lights = getLights();
                parcel2.writeNoException();
                parcel2.writeTypedList(lights, 1);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                LightState lightState = getLightState(readInt);
                parcel2.writeNoException();
                parcel2.writeTypedObject(lightState, 1);
            } else if (i == 3) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                openSession(readStrongBinder, readInt2);
                parcel2.writeNoException();
            } else if (i == 4) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                closeSession(readStrongBinder2);
                parcel2.writeNoException();
            } else if (i == 5) {
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                int[] createIntArray = parcel.createIntArray();
                LightState[] lightStateArr = (LightState[]) parcel.createTypedArray(LightState.CREATOR);
                parcel.enforceNoDataAvail();
                setLightStates(readStrongBinder3, createIntArray, lightStateArr);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILightsManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Light.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public LightState getLightState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LightState) obtain2.readTypedObject(LightState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void openSession(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void closeSession(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.lights.ILightsManager
            public void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILightsManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedArray(lightStateArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getLights_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void getLightState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void openSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void closeSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }

        protected void setLightStates_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DEVICE_LIGHTS, getCallingPid(), getCallingUid());
        }
    }
}
