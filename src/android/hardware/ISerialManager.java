package android.hardware;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISerialManager extends IInterface {

    public static class Default implements ISerialManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ISerialManager
        public String[] getSerialPorts() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ISerialManager
        public ParcelFileDescriptor openSerialPort(String str) throws RemoteException {
            return null;
        }
    }

    String[] getSerialPorts() throws RemoteException;

    ParcelFileDescriptor openSerialPort(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISerialManager {
        public static final String DESCRIPTOR = "android.hardware.ISerialManager";
        static final int TRANSACTION_getSerialPorts = 1;
        static final int TRANSACTION_openSerialPort = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
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

        public static ISerialManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISerialManager)) {
                return (ISerialManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSerialPorts";
            }
            if (i != 2) {
                return null;
            }
            return "openSerialPort";
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
            if (i == 1) {
                String[] serialPorts = getSerialPorts();
                parcel2.writeNoException();
                parcel2.writeStringArray(serialPorts);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor openSerialPort = openSerialPort(readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(openSerialPort, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISerialManager {
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

            @Override // android.hardware.ISerialManager
            public String[] getSerialPorts() throws RemoteException {
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

            @Override // android.hardware.ISerialManager
            public ParcelFileDescriptor openSerialPort(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getSerialPorts_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SERIAL_PORT, getCallingPid(), getCallingUid());
        }

        protected void openSerialPort_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SERIAL_PORT, getCallingPid(), getCallingUid());
        }
    }
}
