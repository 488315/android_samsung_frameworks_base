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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemExecuteManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemExecuteManager)) {
                return (ISemExecuteManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemExecutableInfo executableInfo = getExecutableInfo(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(executableInfo, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(string2, string3, string4, string5, i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIconFd, 1);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasShortcutHostPermission = hasShortcutHostPermission(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasShortcutHostPermission);
                    return true;
                case 5:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    long j = parcel.readLong();
                    String string9 = parcel.readString();
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i4 = parcel.readInt();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcuts = getShortcuts(string7, string8, j, string9, arrayList, componentName, i4, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 6:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStartShortcut = startShortcut(string10, string11, string12, string13, rect, bundle, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartShortcut);
                    return true;
                case 7:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    int i6 = parcel.readInt();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ApplicationInfo applicationInfo = getApplicationInfo(string14, string15, i6, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 8:
                    String string16 = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerChangedCallback(string16, pendingIntent, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string17 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    unRegisterChangedCallback(string17, pendingIntent2, userHandle4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemExecutableInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public SemExecutableInfo getExecutableInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemExecutableInfo) parcelObtain2.readTypedObject(SemExecutableInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean hasShortcutHostPermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParceledListSlice getShortcuts(String str, String str2, long j, String str3, List list, ComponentName componentName, int i, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeList(list);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ApplicationInfo) parcelObtain2.readTypedObject(ApplicationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void registerChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void unRegisterChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
