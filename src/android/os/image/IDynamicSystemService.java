package android.os.image;

import android.Manifest;
import android.app.ActivityThread;
import android.gsi.AvbPublicKey;
import android.gsi.GsiProgress;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDynamicSystemService extends IInterface {
    public static final String DESCRIPTOR = "android.os.image.IDynamicSystemService";

    public static class Default implements IDynamicSystemService {
        @Override // android.os.image.IDynamicSystemService
        public boolean abort() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean closePartition() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public int createPartition(String str, long j, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean finishInstallation() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public String getActiveDsuSlot() throws RemoteException {
            return null;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public GsiProgress getInstallationProgress() throws RemoteException {
            return null;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isInUse() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isInstalled() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean remove() throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean setAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean setEnable(boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean startInstallation(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean submitFromAshmem(long j) throws RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public long suggestScratchSize() throws RemoteException {
            return 0L;
        }
    }

    boolean abort() throws RemoteException;

    boolean closePartition() throws RemoteException;

    int createPartition(String str, long j, boolean z) throws RemoteException;

    boolean finishInstallation() throws RemoteException;

    String getActiveDsuSlot() throws RemoteException;

    boolean getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException;

    GsiProgress getInstallationProgress() throws RemoteException;

    boolean isEnabled() throws RemoteException;

    boolean isInUse() throws RemoteException;

    boolean isInstalled() throws RemoteException;

    boolean remove() throws RemoteException;

    boolean setAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    boolean setEnable(boolean z, boolean z2) throws RemoteException;

    boolean startInstallation(String str) throws RemoteException;

    boolean submitFromAshmem(long j) throws RemoteException;

    long suggestScratchSize() throws RemoteException;

    public static abstract class Stub extends Binder implements IDynamicSystemService {
        static final int TRANSACTION_abort = 6;
        static final int TRANSACTION_closePartition = 3;
        static final int TRANSACTION_createPartition = 2;
        static final int TRANSACTION_finishInstallation = 4;
        static final int TRANSACTION_getActiveDsuSlot = 16;
        static final int TRANSACTION_getAvbPublicKey = 14;
        static final int TRANSACTION_getInstallationProgress = 5;
        static final int TRANSACTION_isEnabled = 9;
        static final int TRANSACTION_isInUse = 7;
        static final int TRANSACTION_isInstalled = 8;
        static final int TRANSACTION_remove = 10;
        static final int TRANSACTION_setAshmem = 12;
        static final int TRANSACTION_setEnable = 11;
        static final int TRANSACTION_startInstallation = 1;
        static final int TRANSACTION_submitFromAshmem = 13;
        static final int TRANSACTION_suggestScratchSize = 15;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IDynamicSystemService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IDynamicSystemService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDynamicSystemService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDynamicSystemService)) {
                return (IDynamicSystemService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startInstallation";
                case 2:
                    return "createPartition";
                case 3:
                    return "closePartition";
                case 4:
                    return "finishInstallation";
                case 5:
                    return "getInstallationProgress";
                case 6:
                    return "abort";
                case 7:
                    return "isInUse";
                case 8:
                    return "isInstalled";
                case 9:
                    return "isEnabled";
                case 10:
                    return "remove";
                case 11:
                    return "setEnable";
                case 12:
                    return "setAshmem";
                case 13:
                    return "submitFromAshmem";
                case 14:
                    return "getAvbPublicKey";
                case 15:
                    return "suggestScratchSize";
                case 16:
                    return "getActiveDsuSlot";
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
                parcel.enforceInterface(IDynamicSystemService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDynamicSystemService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStartInstallation = startInstallation(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartInstallation);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    long j = parcel.readLong();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iCreatePartition = createPartition(string2, j, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreatePartition);
                    return true;
                case 3:
                    boolean zClosePartition = closePartition();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClosePartition);
                    return true;
                case 4:
                    boolean zFinishInstallation = finishInstallation();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFinishInstallation);
                    return true;
                case 5:
                    GsiProgress installationProgress = getInstallationProgress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installationProgress, 1);
                    return true;
                case 6:
                    boolean zAbort = abort();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAbort);
                    return true;
                case 7:
                    boolean zIsInUse = isInUse();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInUse);
                    return true;
                case 8:
                    boolean zIsInstalled = isInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInstalled);
                    return true;
                case 9:
                    boolean zIsEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabled);
                    return true;
                case 10:
                    boolean zRemove = remove();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemove);
                    return true;
                case 11:
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enable = setEnable(z2, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enable);
                    return true;
                case 12:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean ashmem = setAshmem(parcelFileDescriptor, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ashmem);
                    return true;
                case 13:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zSubmitFromAshmem = submitFromAshmem(j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSubmitFromAshmem);
                    return true;
                case 14:
                    AvbPublicKey avbPublicKey = new AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    boolean avbPublicKey2 = getAvbPublicKey(avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 15:
                    long jSuggestScratchSize = suggestScratchSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSuggestScratchSize);
                    return true;
                case 16:
                    String activeDsuSlot = getActiveDsuSlot();
                    parcel2.writeNoException();
                    parcel2.writeString(activeDsuSlot);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDynamicSystemService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDynamicSystemService.DESCRIPTOR;
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean startInstallation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public int createPartition(String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean closePartition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean finishInstallation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public GsiProgress getInstallationProgress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GsiProgress) parcelObtain2.readTypedObject(GsiProgress.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean abort() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isInUse() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isInstalled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean remove() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean setEnable(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean setAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean submitFromAshmem(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    if (parcelObtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(parcelObtain2);
                    }
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public long suggestScratchSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public String getActiveDsuSlot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void startInstallation_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void createPartition_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void closePartition_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void finishInstallation_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getInstallationProgress_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void abort_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void isEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void setEnable_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void setAshmem_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void submitFromAshmem_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getAvbPublicKey_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void suggestScratchSize_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getActiveDsuSlot_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }
    }
}
