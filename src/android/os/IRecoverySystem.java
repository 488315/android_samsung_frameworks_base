package android.os;

import android.Manifest;
import android.app.ActivityThread;
import android.content.IntentSender;
import android.os.IRecoverySystemProgressListener;

/* loaded from: classes3.dex */
public interface IRecoverySystem extends IInterface {

    public static class Default implements IRecoverySystem {
        @Override // android.os.IRecoverySystem
        public boolean allocateSpaceForUpdate(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IRecoverySystem
        public boolean clearBcb() throws RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean clearLskf(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean isLskfCaptured(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public void rebootRecoveryWithCommand(String str) throws RemoteException {
        }

        @Override // android.os.IRecoverySystem
        public int rebootWithLskf(String str, String str2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.IRecoverySystem
        public int rebootWithLskfAssumeSlotSwitch(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IRecoverySystem
        public boolean requestLskf(String str, IntentSender intentSender) throws RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean setupBcb(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IRecoverySystem
        public boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException {
            return false;
        }
    }

    boolean allocateSpaceForUpdate(String str) throws RemoteException;

    boolean clearBcb() throws RemoteException;

    boolean clearLskf(String str) throws RemoteException;

    boolean isLskfCaptured(String str) throws RemoteException;

    void rebootRecoveryWithCommand(String str) throws RemoteException;

    int rebootWithLskf(String str, String str2, boolean z) throws RemoteException;

    int rebootWithLskfAssumeSlotSwitch(String str, String str2) throws RemoteException;

    boolean requestLskf(String str, IntentSender intentSender) throws RemoteException;

    boolean setupBcb(String str) throws RemoteException;

    boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecoverySystem {
        public static final String DESCRIPTOR = "android.os.IRecoverySystem";
        static final int TRANSACTION_allocateSpaceForUpdate = 1;
        static final int TRANSACTION_clearBcb = 4;
        static final int TRANSACTION_clearLskf = 7;
        static final int TRANSACTION_isLskfCaptured = 8;
        static final int TRANSACTION_rebootRecoveryWithCommand = 5;
        static final int TRANSACTION_rebootWithLskf = 10;
        static final int TRANSACTION_rebootWithLskfAssumeSlotSwitch = 9;
        static final int TRANSACTION_requestLskf = 6;
        static final int TRANSACTION_setupBcb = 3;
        static final int TRANSACTION_uncrypt = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
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

        public static IRecoverySystem asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRecoverySystem)) {
                return (IRecoverySystem) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allocateSpaceForUpdate";
                case 2:
                    return "uncrypt";
                case 3:
                    return "setupBcb";
                case 4:
                    return "clearBcb";
                case 5:
                    return "rebootRecoveryWithCommand";
                case 6:
                    return "requestLskf";
                case 7:
                    return "clearLskf";
                case 8:
                    return "isLskfCaptured";
                case 9:
                    return "rebootWithLskfAssumeSlotSwitch";
                case 10:
                    return "rebootWithLskf";
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAllocateSpaceForUpdate = allocateSpaceForUpdate(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllocateSpaceForUpdate);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    IRecoverySystemProgressListener iRecoverySystemProgressListenerAsInterface = IRecoverySystemProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUncrypt = uncrypt(string2, iRecoverySystemProgressListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUncrypt);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean z = setupBcb(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 4:
                    boolean zClearBcb = clearBcb();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearBcb);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rebootRecoveryWithCommand(string4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestLskf = requestLskf(string5, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestLskf);
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearLskf = clearLskf(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearLskf);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsLskfCaptured = isLskfCaptured(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLskfCaptured);
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRebootWithLskfAssumeSlotSwitch = rebootWithLskfAssumeSlotSwitch(string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRebootWithLskfAssumeSlotSwitch);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iRebootWithLskf = rebootWithLskf(string10, string11, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRebootWithLskf);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRecoverySystem {
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

            @Override // android.os.IRecoverySystem
            public boolean allocateSpaceForUpdate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iRecoverySystemProgressListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean setupBcb(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearBcb() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public void rebootRecoveryWithCommand(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean requestLskf(String str, IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearLskf(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean isLskfCaptured(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskfAssumeSlotSwitch(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskf(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void allocateSpaceForUpdate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RECOVERY, getCallingPid(), getCallingUid());
        }

        protected void rebootWithLskfAssumeSlotSwitch_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RECOVERY, getCallingPid(), getCallingUid());
        }
    }
}
