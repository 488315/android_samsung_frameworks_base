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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecoverySystem)) {
                return (IRecoverySystem) queryLocalInterface;
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allocateSpaceForUpdate = allocateSpaceForUpdate(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allocateSpaceForUpdate);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    IRecoverySystemProgressListener asInterface = IRecoverySystemProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean uncrypt = uncrypt(readString2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uncrypt);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean z = setupBcb(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 4:
                    boolean clearBcb = clearBcb();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearBcb);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rebootRecoveryWithCommand(readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestLskf = requestLskf(readString5, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestLskf);
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearLskf = clearLskf(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearLskf);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isLskfCaptured = isLskfCaptured(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLskfCaptured);
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int rebootWithLskfAssumeSlotSwitch = rebootWithLskfAssumeSlotSwitch(readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(rebootWithLskfAssumeSlotSwitch);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int rebootWithLskf = rebootWithLskf(readString10, readString11, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(rebootWithLskf);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean uncrypt(String str, IRecoverySystemProgressListener iRecoverySystemProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iRecoverySystemProgressListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean setupBcb(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearBcb() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public void rebootRecoveryWithCommand(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean requestLskf(String str, IntentSender intentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean clearLskf(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public boolean isLskfCaptured(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskfAssumeSlotSwitch(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IRecoverySystem
            public int rebootWithLskf(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
