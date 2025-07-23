package android.hardware;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IConsumerIrService extends IInterface {

    public static class Default implements IConsumerIrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.IConsumerIrService
        public int[] getCarrierFrequencies() throws RemoteException {
            return null;
        }

        @Override // android.hardware.IConsumerIrService
        public boolean hasIrEmitter() throws RemoteException {
            return false;
        }

        @Override // android.hardware.IConsumerIrService
        public void transmit(String str, int i, int[] iArr) throws RemoteException {
        }
    }

    int[] getCarrierFrequencies() throws RemoteException;

    boolean hasIrEmitter() throws RemoteException;

    void transmit(String str, int i, int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IConsumerIrService {
        public static final String DESCRIPTOR = "android.hardware.IConsumerIrService";
        static final int TRANSACTION_getCarrierFrequencies = 3;
        static final int TRANSACTION_hasIrEmitter = 1;
        static final int TRANSACTION_transmit = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
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

        public static IConsumerIrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IConsumerIrService)) {
                return (IConsumerIrService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "hasIrEmitter";
            }
            if (i == 2) {
                return "transmit";
            }
            if (i != 3) {
                return null;
            }
            return "getCarrierFrequencies";
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
                boolean hasIrEmitter = hasIrEmitter();
                parcel2.writeNoException();
                parcel2.writeBoolean(hasIrEmitter);
            } else if (i == 2) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                transmit(readString, readInt, createIntArray);
                parcel2.writeNoException();
            } else if (i == 3) {
                int[] carrierFrequencies = getCarrierFrequencies();
                parcel2.writeNoException();
                parcel2.writeIntArray(carrierFrequencies);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IConsumerIrService {
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

            @Override // android.hardware.IConsumerIrService
            public boolean hasIrEmitter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public void transmit(String str, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public int[] getCarrierFrequencies() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void transmit_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }

        protected void getCarrierFrequencies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TRANSMIT_IR, getCallingPid(), getCallingUid());
        }
    }
}
