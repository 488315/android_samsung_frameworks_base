package com.samsung.android.app;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemExecuteManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.app.ISemExecuteManager";

    public static class Default implements ISemExecuteManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public SemExecutableInfo getExecutableInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public List<SemExecutableInfo> getExecutableInfos() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, String str4, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ParceledListSlice getShortcuts(String str, String str2, long j, String str3, List list, ComponentName componentName, int i, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public boolean hasShortcutHostPermission(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public void registerChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public void unRegisterChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
        }
    }

    ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException;

    SemExecutableInfo getExecutableInfo(String str) throws RemoteException;

    List<SemExecutableInfo> getExecutableInfos() throws RemoteException;

    ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, String str4, int i) throws RemoteException;

    ParceledListSlice getShortcuts(String str, String str2, long j, String str3, List list, ComponentName componentName, int i, UserHandle userHandle) throws RemoteException;

    boolean hasShortcutHostPermission(String str) throws RemoteException;

    void registerChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException;

    boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException;

    void unRegisterChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemExecuteManager {
        static final int TRANSACTION_getApplicationInfo = 7;
        static final int TRANSACTION_getExecutableInfo = 2;
        static final int TRANSACTION_getExecutableInfos = 1;
        static final int TRANSACTION_getShortcutIconFd = 3;
        static final int TRANSACTION_getShortcuts = 5;
        static final int TRANSACTION_hasShortcutHostPermission = 4;
        static final int TRANSACTION_registerChangedCallback = 8;
        static final int TRANSACTION_startShortcut = 6;
        static final int TRANSACTION_unRegisterChangedCallback = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISemExecuteManager.DESCRIPTOR);
        }

        public static ISemExecuteManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemExecuteManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemExecuteManager)) {
                return (ISemExecuteManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getExecutableInfos";
                case 2:
                    return "getExecutableInfo";
                case 3:
                    return "getShortcutIconFd";
                case 4:
                    return "hasShortcutHostPermission";
                case 5:
                    return "getShortcuts";
                case 6:
                    return "startShortcut";
                case 7:
                    return "getApplicationInfo";
                case 8:
                    return "registerChangedCallback";
                case 9:
                    return "unRegisterChangedCallback";
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
                parcel.enforceInterface(ISemExecuteManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemExecuteManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<SemExecutableInfo> executableInfos = getExecutableInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(executableInfos, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemExecutableInfo executableInfo = getExecutableInfo(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(executableInfo, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(readString2, readString3, readString4, readString5, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIconFd, 1);
                    return true;
                case 4:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasShortcutHostPermission = hasShortcutHostPermission(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasShortcutHostPermission);
                    return true;
                case 5:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    long readLong = parcel.readLong();
                    String readString9 = parcel.readString();
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcuts = getShortcuts(readString7, readString8, readLong, readString9, readArrayList, componentName, readInt2, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 6:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startShortcut = startShortcut(readString10, readString11, readString12, readString13, rect, bundle, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startShortcut);
                    return true;
                case 7:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ApplicationInfo applicationInfo = getApplicationInfo(readString14, readString15, readInt4, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 8:
                    String readString16 = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerChangedCallback(readString16, pendingIntent, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString17 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    unRegisterChangedCallback(readString17, pendingIntent2, userHandle4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemExecuteManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemExecuteManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public List<SemExecutableInfo> getExecutableInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemExecutableInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public SemExecutableInfo getExecutableInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemExecutableInfo) obtain2.readTypedObject(SemExecutableInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean hasShortcutHostPermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParceledListSlice getShortcuts(String str, String str2, long j, String str3, List list, ComponentName componentName, int i, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    obtain.writeList(list);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ApplicationInfo) obtain2.readTypedObject(ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void registerChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void unRegisterChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
