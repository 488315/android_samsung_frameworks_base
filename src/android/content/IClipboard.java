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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IClipboard)) {
                return (IClipboard) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimaryClip(clipData, string, string2, i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPrimaryClipAsPackage(clipData2, string3, string4, i5, i6, string5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPrimaryClip(string6, string7, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ClipData primaryClip = getPrimaryClip(string8, string9, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClip, 1);
                    return true;
                case 5:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ClipDescription primaryClipDescription = getPrimaryClipDescription(string10, string11, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryClipDescription, 1);
                    return true;
                case 6:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasPrimaryClip = hasPrimaryClip(string12, string13, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasPrimaryClip);
                    return true;
                case 7:
                    IOnPrimaryClipChangedListener iOnPrimaryClipChangedListenerAsInterface = IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPrimaryClipChangedListener(iOnPrimaryClipChangedListenerAsInterface, string14, string15, i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IOnPrimaryClipChangedListener iOnPrimaryClipChangedListenerAsInterface2 = IOnPrimaryClipChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removePrimaryClipChangedListener(iOnPrimaryClipChangedListenerAsInterface2, string16, string17, i17, i18);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasClipboardText = hasClipboardText(string18, string19, i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasClipboardText);
                    return true;
                case 10:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String primaryClipSource = getPrimaryClipSource(string20, string21, i21, i22);
                    parcel2.writeNoException();
                    parcel2.writeString(primaryClipSource);
                    return true;
                case 11:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAreClipboardAccessNotificationsEnabledForUser = areClipboardAccessNotificationsEnabledForUser(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreClipboardAccessNotificationsEnabledForUser);
                    return true;
                case 12:
                    boolean z = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setClipboardAccessNotificationsEnabledForUser(z, i24);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clipData, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setPrimaryClipAsPackage(ClipData clipData, String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clipData, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void clearPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public ClipData getPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ClipData) parcelObtain2.readTypedObject(ClipData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public ClipDescription getPrimaryClipDescription(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ClipDescription) parcelObtain2.readTypedObject(ClipDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasPrimaryClip(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void addPrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void removePrimaryClipChangedListener(IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnPrimaryClipChangedListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean hasClipboardText(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public String getPrimaryClipSource(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public boolean areClipboardAccessNotificationsEnabledForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IClipboard
            public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
