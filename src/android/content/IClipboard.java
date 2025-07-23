package android.content;

import android.Manifest;
import android.app.ActivityThread;
import android.content.IOnPrimaryClipChangedListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IClipboard extends IInterface {

    public static class Default implements IClipboard {
        @Override // android.content.IClipboard
        public void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IClipboard
        public boolean areClipboardAccessNotificationsEnabledForUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.IClipboard
        public void clearPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IClipboard
        public ClipData getPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public ClipDescription getPrimaryClipDescription(String str, String str2, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public String getPrimaryClipSource(String str, String str2, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.IClipboard
        public boolean hasClipboardText(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.IClipboard
        public boolean hasPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.IClipboard
        public void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IClipboard
        public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws RemoteException {
        }

        @Override // android.content.IClipboard
        public void setPrimaryClip(ClipData clipData, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IClipboard
        public void setPrimaryClipAsPackage(ClipData clipData, String str, String str2, int i, int i2, String str3) throws RemoteException {
        }
    }

    void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException;

    boolean areClipboardAccessNotificationsEnabledForUser(int i) throws RemoteException;

    void clearPrimaryClip(String str, String str2, int i, int i2) throws RemoteException;

    ClipData getPrimaryClip(String str, String str2, int i, int i2) throws RemoteException;

    ClipDescription getPrimaryClipDescription(String str, String str2, int i, int i2) throws RemoteException;

    String getPrimaryClipSource(String str, String str2, int i, int i2) throws RemoteException;

    boolean hasClipboardText(String str, String str2, int i, int i2) throws RemoteException;

    boolean hasPrimaryClip(String str, String str2, int i, int i2) throws RemoteException;

    void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException;

    void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws RemoteException;

    void setPrimaryClip(ClipData clipData, String str, String str2, int i, int i2) throws RemoteException;

    void setPrimaryClipAsPackage(ClipData clipData, String str, String str2, int i, int i2, String str3) throws RemoteException;

    public static abstract class Stub extends Binder implements IClipboard {
        public static final String DESCRIPTOR = "android.content.IClipboard";
        static final int TRANSACTION_addPrimaryClipChangedListener = 7;
        static final int TRANSACTION_areClipboardAccessNotificationsEnabledForUser = 11;
        static final int TRANSACTION_clearPrimaryClip = 3;
        static final int TRANSACTION_getPrimaryClip = 4;
        static final int TRANSACTION_getPrimaryClipDescription = 5;
        static final int TRANSACTION_getPrimaryClipSource = 10;
        static final int TRANSACTION_hasClipboardText = 9;
        static final int TRANSACTION_hasPrimaryClip = 6;
        static final int TRANSACTION_removePrimaryClipChangedListener = 8;
        static final int TRANSACTION_setClipboardAccessNotificationsEnabledForUser = 12;
        static final int TRANSACTION_setPrimaryClip = 1;
        static final int TRANSACTION_setPrimaryClipAsPackage = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
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

        public static IClipboard asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClipboard)) {
                return (IClipboard) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setPrimaryClip";
                case 2:
                    return "setPrimaryClipAsPackage";
                case 3:
                    return "clearPrimaryClip";
                case 4:
                    return "getPrimaryClip";
                case 5:
                    return "getPrimaryClipDescription";
                case 6:
                    return "hasPrimaryClip";
                case 7:
                    return "addPrimaryClipChangedListener";
                case 8:
                    return "removePrimaryClipChangedListener";
                case 9:
                    return "hasClipboardText";
                case 10:
                    return "getPrimaryClipSource";
                case 11:
                    return "areClipboardAccessNotificationsEnabledForUser";
                case 12:
                    return "setClipboardAccessNotificationsEnabledForUser";
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
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimaryClip(clipData, readString, readString2, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPrimaryClipAsPackage(clipData2, readString3, readString4, readInt3, readInt4, readString5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPrimaryClip(readString6, readString7, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ClipData primaryClip = getPrimaryClip(readString8, readString9, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClip, 1);
                    return true;
                case 5:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ClipDescription primaryClipDescription = getPrimaryClipDescription(readString10, readString11, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClipDescription, 1);
                    return true;
                case 6:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasPrimaryClip = hasPrimaryClip(readString12, readString13, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPrimaryClip);
                    return true;
                case 7:
                    IOnPrimaryClipChangedListener asInterface = IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrimaryClipChangedListener(asInterface, readString14, readString15, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IOnPrimaryClipChangedListener asInterface2 = IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrimaryClipChangedListener(asInterface2, readString16, readString17, readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasClipboardText = hasClipboardText(readString18, readString19, readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasClipboardText);
                    return true;
                case 10:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String primaryClipSource = getPrimaryClipSource(readString20, readString21, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(primaryClipSource);
                    return true;
                case 11:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean areClipboardAccessNotificationsEnabledForUser = areClipboardAccessNotificationsEnabledForUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areClipboardAccessNotificationsEnabledForUser);
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setClipboardAccessNotificationsEnabledForUser(readBoolean, readInt22);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IClipboard {
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

            @Override // android.content.IClipboard
            public void setPrimaryClip(ClipData clipData, String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setPrimaryClipAsPackage(ClipData clipData, String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void clearPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public ClipData getPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ClipData) obtain2.readTypedObject(ClipData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public ClipDescription getPrimaryClipDescription(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ClipDescription) obtain2.readTypedObject(ClipDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasClipboardText(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public String getPrimaryClipSource(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean areClipboardAccessNotificationsEnabledForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setPrimaryClipAsPackage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_CLIP_SOURCE, getCallingPid(), getCallingUid());
        }

        protected void getPrimaryClipSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_CLIP_SOURCE, getCallingPid(), getCallingUid());
        }
    }
}
