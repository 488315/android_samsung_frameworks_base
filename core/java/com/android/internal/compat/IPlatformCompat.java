package com.android.internal.compat;

import android.Manifest;
import android.app.ActivityThread;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

public interface IPlatformCompat extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.compat.IPlatformCompat";

    boolean clearOverride(long j, String str) throws RemoteException;

    boolean clearOverrideForTest(long j, String str) throws RemoteException;

    void clearOverrides(String str) throws RemoteException;

    void clearOverridesForTest(String str) throws RemoteException;

    boolean containsOverride(long j, String str) throws RemoteException;

    int disableTargetSdkChanges(String str, int i) throws RemoteException;

    int enableTargetSdkChanges(String str, int i) throws RemoteException;

    CompatibilityChangeConfig getAppConfig(ApplicationInfo applicationInfo) throws RemoteException;

    IOverrideValidator getOverrideValidator() throws RemoteException;

    boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) throws RemoteException;

    boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException;

    boolean isChangeEnabledByUid(long j, int i) throws RemoteException;

    CompatibilityChangeInfo[] listAllChanges() throws RemoteException;

    CompatibilityChangeInfo[] listUIChanges() throws RemoteException;

    void putAllOverridesOnReleaseBuilds(
            CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig)
            throws RemoteException;

    void putOverridesOnReleaseBuilds(
            CompatibilityOverrideConfig compatibilityOverrideConfig, String str)
            throws RemoteException;

    void removeAllOverridesOnReleaseBuilds(
            CompatibilityOverridesToRemoveByPackageConfig
                    compatibilityOverridesToRemoveByPackageConfig)
            throws RemoteException;

    void removeOverridesOnReleaseBuilds(
            CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str)
            throws RemoteException;

    void reportChange(long j, ApplicationInfo applicationInfo) throws RemoteException;

    void reportChangeByPackageName(long j, String str, int i) throws RemoteException;

    void reportChangeByUid(long j, int i) throws RemoteException;

    void setOverrides(CompatibilityChangeConfig compatibilityChangeConfig, String str)
            throws RemoteException;

    void setOverridesForTest(CompatibilityChangeConfig compatibilityChangeConfig, String str)
            throws RemoteException;

    public static class Default implements IPlatformCompat {
        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChange(long changeId, ApplicationInfo appInfo) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByPackageName(long changeId, String packageName, int userId)
                throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByUid(long changeId, int uid) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabled(long changeId, ApplicationInfo appInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByPackageName(long changeId, String packageName, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByUid(long changeId, int uid) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean containsOverride(long changeId, String packageName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverrides(CompatibilityChangeConfig overrides, String packageName)
                throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void putAllOverridesOnReleaseBuilds(
                CompatibilityOverridesByPackageConfig overridesByPackage) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void putOverridesOnReleaseBuilds(
                CompatibilityOverrideConfig overrides, String packageName) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverridesForTest(CompatibilityChangeConfig overrides, String packageName)
                throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverride(long changeId, String packageName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverrideForTest(long changeId, String packageName)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeAllOverridesOnReleaseBuilds(
                CompatibilityOverridesToRemoveByPackageConfig overridesToRemoveByPackage)
                throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeOverridesOnReleaseBuilds(
                CompatibilityOverridesToRemoveConfig overridesToRemove, String packageName)
                throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public int enableTargetSdkChanges(String packageName, int targetSdkVersion)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public int disableTargetSdkChanges(String packageName, int targetSdkVersion)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverrides(String packageName) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverridesForTest(String packageName) throws RemoteException {}

        @Override // com.android.internal.compat.IPlatformCompat
        public CompatibilityChangeConfig getAppConfig(ApplicationInfo appInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public CompatibilityChangeInfo[] listAllChanges() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public CompatibilityChangeInfo[] listUIChanges() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public IOverrideValidator getOverrideValidator() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPlatformCompat {
        static final int TRANSACTION_clearOverride = 12;
        static final int TRANSACTION_clearOverrideForTest = 13;
        static final int TRANSACTION_clearOverrides = 18;
        static final int TRANSACTION_clearOverridesForTest = 19;
        static final int TRANSACTION_containsOverride = 7;
        static final int TRANSACTION_disableTargetSdkChanges = 17;
        static final int TRANSACTION_enableTargetSdkChanges = 16;
        static final int TRANSACTION_getAppConfig = 20;
        static final int TRANSACTION_getOverrideValidator = 23;
        static final int TRANSACTION_isChangeEnabled = 4;
        static final int TRANSACTION_isChangeEnabledByPackageName = 5;
        static final int TRANSACTION_isChangeEnabledByUid = 6;
        static final int TRANSACTION_listAllChanges = 21;
        static final int TRANSACTION_listUIChanges = 22;
        static final int TRANSACTION_putAllOverridesOnReleaseBuilds = 9;
        static final int TRANSACTION_putOverridesOnReleaseBuilds = 10;
        static final int TRANSACTION_removeAllOverridesOnReleaseBuilds = 14;
        static final int TRANSACTION_removeOverridesOnReleaseBuilds = 15;
        static final int TRANSACTION_reportChange = 1;
        static final int TRANSACTION_reportChangeByPackageName = 2;
        static final int TRANSACTION_reportChangeByUid = 3;
        static final int TRANSACTION_setOverrides = 8;
        static final int TRANSACTION_setOverridesForTest = 11;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_isChangeEnabled = {
            Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG
        };
        static final String[] PERMISSIONS_isChangeEnabledByPackageName = {
            Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG
        };
        static final String[] PERMISSIONS_isChangeEnabledByUid = {
            Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG
        };
        static final String[] PERMISSIONS_getAppConfig = {
            Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG
        };

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IPlatformCompat.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPlatformCompat asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPlatformCompat.DESCRIPTOR);
            if (iin != null && (iin instanceof IPlatformCompat)) {
                return (IPlatformCompat) iin;
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
                    return "reportChange";
                case 2:
                    return "reportChangeByPackageName";
                case 3:
                    return "reportChangeByUid";
                case 4:
                    return "isChangeEnabled";
                case 5:
                    return "isChangeEnabledByPackageName";
                case 6:
                    return "isChangeEnabledByUid";
                case 7:
                    return "containsOverride";
                case 8:
                    return "setOverrides";
                case 9:
                    return "putAllOverridesOnReleaseBuilds";
                case 10:
                    return "putOverridesOnReleaseBuilds";
                case 11:
                    return "setOverridesForTest";
                case 12:
                    return "clearOverride";
                case 13:
                    return "clearOverrideForTest";
                case 14:
                    return "removeAllOverridesOnReleaseBuilds";
                case 15:
                    return "removeOverridesOnReleaseBuilds";
                case 16:
                    return "enableTargetSdkChanges";
                case 17:
                    return "disableTargetSdkChanges";
                case 18:
                    return "clearOverrides";
                case 19:
                    return "clearOverridesForTest";
                case 20:
                    return "getAppConfig";
                case 21:
                    return "listAllChanges";
                case 22:
                    return "listUIChanges";
                case 23:
                    return "getOverrideValidator";
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
                data.enforceInterface(IPlatformCompat.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPlatformCompat.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    ApplicationInfo _arg1 =
                            (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    reportChange(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    long _arg02 = data.readLong();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    reportChangeByPackageName(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    long _arg03 = data.readLong();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    reportChangeByUid(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    long _arg04 = data.readLong();
                    ApplicationInfo _arg14 =
                            (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result = isChangeEnabled(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 5:
                    long _arg05 = data.readLong();
                    String _arg15 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isChangeEnabledByPackageName(_arg05, _arg15, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 6:
                    long _arg06 = data.readLong();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isChangeEnabledByUid(_arg06, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 7:
                    long _arg07 = data.readLong();
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result4 = containsOverride(_arg07, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 8:
                    CompatibilityChangeConfig _arg08 =
                            (CompatibilityChangeConfig)
                                    data.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    setOverrides(_arg08, _arg18);
                    reply.writeNoException();
                    return true;
                case 9:
                    CompatibilityOverridesByPackageConfig _arg09 =
                            (CompatibilityOverridesByPackageConfig)
                                    data.readTypedObject(
                                            CompatibilityOverridesByPackageConfig.CREATOR);
                    data.enforceNoDataAvail();
                    putAllOverridesOnReleaseBuilds(_arg09);
                    reply.writeNoException();
                    return true;
                case 10:
                    CompatibilityOverrideConfig _arg010 =
                            (CompatibilityOverrideConfig)
                                    data.readTypedObject(CompatibilityOverrideConfig.CREATOR);
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    putOverridesOnReleaseBuilds(_arg010, _arg19);
                    reply.writeNoException();
                    return true;
                case 11:
                    CompatibilityChangeConfig _arg011 =
                            (CompatibilityChangeConfig)
                                    data.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    setOverridesForTest(_arg011, _arg110);
                    reply.writeNoException();
                    return true;
                case 12:
                    long _arg012 = data.readLong();
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = clearOverride(_arg012, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 13:
                    long _arg013 = data.readLong();
                    String _arg112 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = clearOverrideForTest(_arg013, _arg112);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 14:
                    CompatibilityOverridesToRemoveByPackageConfig _arg014 =
                            (CompatibilityOverridesToRemoveByPackageConfig)
                                    data.readTypedObject(
                                            CompatibilityOverridesToRemoveByPackageConfig.CREATOR);
                    data.enforceNoDataAvail();
                    removeAllOverridesOnReleaseBuilds(_arg014);
                    reply.writeNoException();
                    return true;
                case 15:
                    CompatibilityOverridesToRemoveConfig _arg015 =
                            (CompatibilityOverridesToRemoveConfig)
                                    data.readTypedObject(
                                            CompatibilityOverridesToRemoveConfig.CREATOR);
                    String _arg113 = data.readString();
                    data.enforceNoDataAvail();
                    removeOverridesOnReleaseBuilds(_arg015, _arg113);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg016 = data.readString();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result7 = enableTargetSdkChanges(_arg016, _arg114);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 17:
                    String _arg017 = data.readString();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result8 = disableTargetSdkChanges(_arg017, _arg115);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 18:
                    String _arg018 = data.readString();
                    data.enforceNoDataAvail();
                    clearOverrides(_arg018);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg019 = data.readString();
                    data.enforceNoDataAvail();
                    clearOverridesForTest(_arg019);
                    reply.writeNoException();
                    return true;
                case 20:
                    ApplicationInfo _arg020 =
                            (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    CompatibilityChangeConfig _result9 = getAppConfig(_arg020);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 21:
                    CompatibilityChangeInfo[] _result10 = listAllChanges();
                    reply.writeNoException();
                    reply.writeTypedArray(_result10, 1);
                    return true;
                case 22:
                    CompatibilityChangeInfo[] _result11 = listUIChanges();
                    reply.writeNoException();
                    reply.writeTypedArray(_result11, 1);
                    return true;
                case 23:
                    IOverrideValidator _result12 = getOverrideValidator();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPlatformCompat {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPlatformCompat.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChange(long changeId, ApplicationInfo appInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeTypedObject(appInfo, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByPackageName(long changeId, String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByUid(long changeId, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeInt(uid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabled(long changeId, ApplicationInfo appInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeTypedObject(appInfo, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByPackageName(
                    long changeId, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByUid(long changeId, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeInt(uid);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean containsOverride(long changeId, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeString(packageName);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverrides(CompatibilityChangeConfig overrides, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overrides, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putAllOverridesOnReleaseBuilds(
                    CompatibilityOverridesByPackageConfig overridesByPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overridesByPackage, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putOverridesOnReleaseBuilds(
                    CompatibilityOverrideConfig overrides, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overrides, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverridesForTest(CompatibilityChangeConfig overrides, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overrides, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverride(long changeId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeString(packageName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverrideForTest(long changeId, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeLong(changeId);
                    _data.writeString(packageName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeAllOverridesOnReleaseBuilds(
                    CompatibilityOverridesToRemoveByPackageConfig overridesToRemoveByPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overridesToRemoveByPackage, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeOverridesOnReleaseBuilds(
                    CompatibilityOverridesToRemoveConfig overridesToRemove, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(overridesToRemove, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int enableTargetSdkChanges(String packageName, int targetSdkVersion)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int disableTargetSdkChanges(String packageName, int targetSdkVersion)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverrides(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverridesForTest(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeConfig getAppConfig(ApplicationInfo appInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    _data.writeTypedObject(appInfo, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    CompatibilityChangeConfig _result =
                            (CompatibilityChangeConfig)
                                    _reply.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listAllChanges() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    CompatibilityChangeInfo[] _result =
                            (CompatibilityChangeInfo[])
                                    _reply.createTypedArray(CompatibilityChangeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listUIChanges() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    CompatibilityChangeInfo[] _result =
                            (CompatibilityChangeInfo[])
                                    _reply.createTypedArray(CompatibilityChangeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public IOverrideValidator getOverrideValidator() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    IOverrideValidator _result =
                            IOverrideValidator.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void reportChange_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByPackageName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByUid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(
                    PERMISSIONS_isChangeEnabled, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByPackageName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(
                    PERMISSIONS_isChangeEnabledByPackageName, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByUid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(
                    PERMISSIONS_isChangeEnabledByUid, getCallingPid(), getCallingUid());
        }

        protected void containsOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void setOverrides_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void putAllOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void putOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void setOverridesForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void clearOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void clearOverrideForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void removeAllOverridesOnReleaseBuilds_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void removeOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void enableTargetSdkChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void disableTargetSdkChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void clearOverrides_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void clearOverridesForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void getAppConfig_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(
                    PERMISSIONS_getAppConfig, getCallingPid(), getCallingUid());
        }

        protected void listAllChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.READ_COMPAT_CHANGE_CONFIG,
                    getCallingPid(),
                    getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
