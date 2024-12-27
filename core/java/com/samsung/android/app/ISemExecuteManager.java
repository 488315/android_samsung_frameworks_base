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

import java.util.List;

/* loaded from: classes5.dex */
public interface ISemExecuteManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.app.ISemExecuteManager";

    ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle)
            throws RemoteException;

    SemExecutableInfo getExecutableInfo(String str) throws RemoteException;

    List<SemExecutableInfo> getExecutableInfos() throws RemoteException;

    ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, String str4, int i)
            throws RemoteException;

    ParceledListSlice getShortcuts(
            String str,
            String str2,
            long j,
            String str3,
            List list,
            ComponentName componentName,
            int i,
            UserHandle userHandle)
            throws RemoteException;

    boolean hasShortcutHostPermission(String str) throws RemoteException;

    void registerChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle)
            throws RemoteException;

    boolean startShortcut(
            String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i)
            throws RemoteException;

    void unRegisterChangedCallback(String str, PendingIntent pendingIntent, UserHandle userHandle)
            throws RemoteException;

    public static class Default implements ISemExecuteManager {
        @Override // com.samsung.android.app.ISemExecuteManager
        public List<SemExecutableInfo> getExecutableInfos() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public SemExecutableInfo getExecutableInfo(String id) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ParcelFileDescriptor getShortcutIconFd(
                String callingPackage,
                String targetLauncherPackage,
                String packageName,
                String id,
                int userId)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ParceledListSlice getShortcuts(
                String callingPackage,
                String targetLauncherPackage,
                long changedSince,
                String packageName,
                List shortcutIds,
                ComponentName componentName,
                int flags,
                UserHandle user)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public boolean startShortcut(
                String callingPackage,
                String targetLauncherPackage,
                String packageName,
                String id,
                Rect sourceBounds,
                Bundle startActivityOptions,
                int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public ApplicationInfo getApplicationInfo(
                String callingPackage, String packageName, int flags, UserHandle user)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.app.ISemExecuteManager
        public void registerChangedCallback(
                String packageName, PendingIntent pIntent, UserHandle user)
                throws RemoteException {}

        @Override // com.samsung.android.app.ISemExecuteManager
        public void unRegisterChangedCallback(
                String packageName, PendingIntent pIntent, UserHandle user)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemExecuteManager {
        static final int TRANSACTION_getApplicationInfo = 7;
        static final int TRANSACTION_getExecutableInfo = 2;
        static final int TRANSACTION_getExecutableInfos = 1;
        static final int TRANSACTION_getShortcutIconFd = 3;
        static final int TRANSACTION_getShortcuts = 5;
        static final int TRANSACTION_hasShortcutHostPermission = 4;
        static final int TRANSACTION_registerChangedCallback = 8;
        static final int TRANSACTION_startShortcut = 6;
        static final int TRANSACTION_unRegisterChangedCallback = 9;

        public Stub() {
            attachInterface(this, ISemExecuteManager.DESCRIPTOR);
        }

        public static ISemExecuteManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemExecuteManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemExecuteManager)) {
                return (ISemExecuteManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemExecuteManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemExecuteManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<SemExecutableInfo> _result = getExecutableInfos();
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    SemExecutableInfo _result2 = getExecutableInfo(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    String _arg02 = data.readString();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result3 =
                            getShortcutIconFd(_arg02, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result4 = hasShortcutHostPermission(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    String _arg12 = data.readString();
                    long _arg22 = data.readLong();
                    String _arg32 = data.readString();
                    ClassLoader cl = getClass().getClassLoader();
                    List _arg42 = data.readArrayList(cl);
                    ComponentName _arg5 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg6 = data.readInt();
                    UserHandle _arg7 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    ParceledListSlice _result5 =
                            getShortcuts(
                                    _arg04, _arg12, _arg22, _arg32, _arg42, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    String _arg13 = data.readString();
                    String _arg23 = data.readString();
                    String _arg33 = data.readString();
                    Rect _arg43 = (Rect) data.readTypedObject(Rect.CREATOR);
                    Bundle _arg52 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg62 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 =
                            startShortcut(_arg05, _arg13, _arg23, _arg33, _arg43, _arg52, _arg62);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    String _arg14 = data.readString();
                    int _arg24 = data.readInt();
                    UserHandle _arg34 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    ApplicationInfo _result7 = getApplicationInfo(_arg06, _arg14, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    PendingIntent _arg15 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    UserHandle _arg25 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    registerChangedCallback(_arg07, _arg15, _arg25);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    PendingIntent _arg16 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    UserHandle _arg26 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    unRegisterChangedCallback(_arg08, _arg16, _arg26);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemExecuteManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<SemExecutableInfo> _result =
                            _reply.createTypedArrayList(SemExecutableInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public SemExecutableInfo getExecutableInfo(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    SemExecutableInfo _result =
                            (SemExecutableInfo) _reply.readTypedObject(SemExecutableInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParcelFileDescriptor getShortcutIconFd(
                    String callingPackage,
                    String targetLauncherPackage,
                    String packageName,
                    String id,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(targetLauncherPackage);
                    _data.writeString(packageName);
                    _data.writeString(id);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean hasShortcutHostPermission(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ParceledListSlice getShortcuts(
                    String callingPackage,
                    String targetLauncherPackage,
                    long changedSince,
                    String packageName,
                    List shortcutIds,
                    ComponentName componentName,
                    int flags,
                    UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(targetLauncherPackage);
                    _data.writeLong(changedSince);
                    _data.writeString(packageName);
                    _data.writeList(shortcutIds);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(flags);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public boolean startShortcut(
                    String callingPackage,
                    String targetLauncherPackage,
                    String packageName,
                    String id,
                    Rect sourceBounds,
                    Bundle startActivityOptions,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(targetLauncherPackage);
                    _data.writeString(packageName);
                    _data.writeString(id);
                    _data.writeTypedObject(sourceBounds, 0);
                    _data.writeTypedObject(startActivityOptions, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public ApplicationInfo getApplicationInfo(
                    String callingPackage, String packageName, int flags, UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    ApplicationInfo _result =
                            (ApplicationInfo) _reply.readTypedObject(ApplicationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void registerChangedCallback(
                    String packageName, PendingIntent pIntent, UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(pIntent, 0);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.app.ISemExecuteManager
            public void unRegisterChangedCallback(
                    String packageName, PendingIntent pIntent, UserHandle user)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemExecuteManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(pIntent, 0);
                    _data.writeTypedObject(user, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
