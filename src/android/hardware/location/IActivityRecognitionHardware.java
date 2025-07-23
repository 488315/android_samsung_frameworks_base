package android.hardware.location;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.location.IActivityRecognitionHardwareSink;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IActivityRecognitionHardware extends IInterface {

    public static class Default implements IActivityRecognitionHardware {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean disableActivityEvent(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean enableActivityEvent(String str, int i, long j) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean flush() throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public String[] getSupportedActivities() throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean isActivitySupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IActivityRecognitionHardware
        public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
            return false;
        }
    }

    boolean disableActivityEvent(String str, int i) throws RemoteException;

    boolean enableActivityEvent(String str, int i, long j) throws RemoteException;

    boolean flush() throws RemoteException;

    String[] getSupportedActivities() throws RemoteException;

    boolean isActivitySupported(String str) throws RemoteException;

    boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException;

    boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityRecognitionHardware {
        public static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardware";
        static final int TRANSACTION_disableActivityEvent = 6;
        static final int TRANSACTION_enableActivityEvent = 5;
        static final int TRANSACTION_flush = 7;
        static final int TRANSACTION_getSupportedActivities = 1;
        static final int TRANSACTION_isActivitySupported = 2;
        static final int TRANSACTION_registerSink = 3;
        static final int TRANSACTION_unregisterSink = 4;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IActivityRecognitionHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityRecognitionHardware)) {
                return (IActivityRecognitionHardware) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSupportedActivities";
                case 2:
                    return "isActivitySupported";
                case 3:
                    return "registerSink";
                case 4:
                    return "unregisterSink";
                case 5:
                    return "enableActivityEvent";
                case 6:
                    return "disableActivityEvent";
                case 7:
                    return "flush";
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
                    String[] supportedActivities = getSupportedActivities();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(supportedActivities);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isActivitySupported = isActivitySupported(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivitySupported);
                    return true;
                case 3:
                    IActivityRecognitionHardwareSink asInterface = IActivityRecognitionHardwareSink.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSink = registerSink(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSink);
                    return true;
                case 4:
                    IActivityRecognitionHardwareSink asInterface2 = IActivityRecognitionHardwareSink.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterSink = unregisterSink(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterSink);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean enableActivityEvent = enableActivityEvent(readString2, readInt, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableActivityEvent);
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableActivityEvent = disableActivityEvent(readString3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableActivityEvent);
                    return true;
                case 7:
                    boolean flush = flush();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(flush);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IActivityRecognitionHardware {
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

            @Override // android.hardware.location.IActivityRecognitionHardware
            public String[] getSupportedActivities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean isActivitySupported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean registerSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityRecognitionHardwareSink);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean unregisterSink(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityRecognitionHardwareSink);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean enableActivityEvent(String str, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean disableActivityEvent(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IActivityRecognitionHardware
            public boolean flush() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getSupportedActivities_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void isActivitySupported_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void registerSink_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void unregisterSink_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void enableActivityEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void disableActivityEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }

        protected void flush_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOCATION_HARDWARE, getCallingPid(), getCallingUid());
        }
    }
}
