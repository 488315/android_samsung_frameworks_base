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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConsumerIrService)) {
                return (IConsumerIrService) iInterfaceQueryLocalInterface;
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
                boolean zHasIrEmitter = hasIrEmitter();
                parcel2.writeNoException();
                parcel2.writeBoolean(zHasIrEmitter);
            } else if (i == 2) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                transmit(string, i3, iArrCreateIntArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public void transmit(String str, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IConsumerIrService
            public int[] getCarrierFrequencies() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
