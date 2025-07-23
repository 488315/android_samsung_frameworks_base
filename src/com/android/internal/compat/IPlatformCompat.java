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
import com.android.internal.compat.IOverrideValidator;

/* loaded from: classes5.dex */
public interface IPlatformCompat extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.compat.IPlatformCompat";

    public static class Default implements IPlatformCompat {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverride(long j, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverrideForTest(long j, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverrides(String str) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverridesForTest(String str) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean containsOverride(long j, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public int disableTargetSdkChanges(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public int enableTargetSdkChanges(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public CompatibilityChangeConfig getAppConfig(ApplicationInfo applicationInfo) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public IOverrideValidator getOverrideValidator() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByUid(long j, int i) throws RemoteException {
            return false;
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
        public void putAllOverridesOnReleaseBuilds(CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void putOverridesOnReleaseBuilds(CompatibilityOverrideConfig compatibilityOverrideConfig, String str) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeAllOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChange(long j, ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByPackageName(long j, String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByUid(long j, int i) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverrides(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverridesForTest(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
        }
    }

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

    void putAllOverridesOnReleaseBuilds(CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws RemoteException;

    void putOverridesOnReleaseBuilds(CompatibilityOverrideConfig compatibilityOverrideConfig, String str) throws RemoteException;

    void removeAllOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws RemoteException;

    void removeOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) throws RemoteException;

    void reportChange(long j, ApplicationInfo applicationInfo) throws RemoteException;

    void reportChangeByPackageName(long j, String str, int i) throws RemoteException;

    void reportChangeByUid(long j, int i) throws RemoteException;

    void setOverrides(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException;

    void setOverridesForTest(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IPlatformCompat {
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
        static final String[] PERMISSIONS_isChangeEnabled = {Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final String[] PERMISSIONS_isChangeEnabledByPackageName = {Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final String[] PERMISSIONS_isChangeEnabledByUid = {Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final String[] PERMISSIONS_getAppConfig = {Manifest.permission.LOG_COMPAT_CHANGE, Manifest.permission.READ_COMPAT_CHANGE_CONFIG};

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IPlatformCompat.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPlatformCompat asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPlatformCompat.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPlatformCompat)) {
                return (IPlatformCompat) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPlatformCompat.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPlatformCompat.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportChange(readLong, applicationInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong2 = parcel.readLong();
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByPackageName(readLong2, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByUid(readLong3, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long readLong4 = parcel.readLong();
                    ApplicationInfo applicationInfo2 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabled = isChangeEnabled(readLong4, applicationInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabled);
                    return true;
                case 5:
                    long readLong5 = parcel.readLong();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabledByPackageName = isChangeEnabledByPackageName(readLong5, readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabledByPackageName);
                    return true;
                case 6:
                    long readLong6 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabledByUid = isChangeEnabledByUid(readLong6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabledByUid);
                    return true;
                case 7:
                    long readLong7 = parcel.readLong();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean containsOverride = containsOverride(readLong7, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(containsOverride);
                    return true;
                case 8:
                    CompatibilityChangeConfig compatibilityChangeConfig = (CompatibilityChangeConfig) parcel.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverrides(compatibilityChangeConfig, readString4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig = (CompatibilityOverridesByPackageConfig) parcel.readTypedObject(CompatibilityOverridesByPackageConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    putAllOverridesOnReleaseBuilds(compatibilityOverridesByPackageConfig);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    CompatibilityOverrideConfig compatibilityOverrideConfig = (CompatibilityOverrideConfig) parcel.readTypedObject(CompatibilityOverrideConfig.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    putOverridesOnReleaseBuilds(compatibilityOverrideConfig, readString5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    CompatibilityChangeConfig compatibilityChangeConfig2 = (CompatibilityChangeConfig) parcel.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverridesForTest(compatibilityChangeConfig2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    long readLong8 = parcel.readLong();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearOverride = clearOverride(readLong8, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearOverride);
                    return true;
                case 13:
                    long readLong9 = parcel.readLong();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearOverrideForTest = clearOverrideForTest(readLong9, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearOverrideForTest);
                    return true;
                case 14:
                    CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig = (CompatibilityOverridesToRemoveByPackageConfig) parcel.readTypedObject(CompatibilityOverridesToRemoveByPackageConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAllOverridesOnReleaseBuilds(compatibilityOverridesToRemoveByPackageConfig);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig = (CompatibilityOverridesToRemoveConfig) parcel.readTypedObject(CompatibilityOverridesToRemoveConfig.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridesOnReleaseBuilds(compatibilityOverridesToRemoveConfig, readString9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString10 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enableTargetSdkChanges = enableTargetSdkChanges(readString10, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableTargetSdkChanges);
                    return true;
                case 17:
                    String readString11 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int disableTargetSdkChanges = disableTargetSdkChanges(readString11, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(disableTargetSdkChanges);
                    return true;
                case 18:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverrides(readString12);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverridesForTest(readString13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ApplicationInfo applicationInfo3 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    CompatibilityChangeConfig appConfig = getAppConfig(applicationInfo3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appConfig, 1);
                    return true;
                case 21:
                    CompatibilityChangeInfo[] listAllChanges = listAllChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAllChanges, 1);
                    return true;
                case 22:
                    CompatibilityChangeInfo[] listUIChanges = listUIChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listUIChanges, 1);
                    return true;
                case 23:
                    IOverrideValidator overrideValidator = getOverrideValidator();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(overrideValidator);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPlatformCompat {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPlatformCompat.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChange(long j, ApplicationInfo applicationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByPackageName(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByUid(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByUid(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean containsOverride(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverrides(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityChangeConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putAllOverridesOnReleaseBuilds(CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesByPackageConfig, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putOverridesOnReleaseBuilds(CompatibilityOverrideConfig compatibilityOverrideConfig, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverrideConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverridesForTest(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityChangeConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverride(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverrideForTest(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeAllOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesToRemoveByPackageConfig, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesToRemoveConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int enableTargetSdkChanges(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int disableTargetSdkChanges(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverrides(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverridesForTest(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeConfig getAppConfig(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CompatibilityChangeConfig) obtain2.readTypedObject(CompatibilityChangeConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listAllChanges() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CompatibilityChangeInfo[]) obtain2.createTypedArray(CompatibilityChangeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listUIChanges() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CompatibilityChangeInfo[]) obtain2.createTypedArray(CompatibilityChangeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public IOverrideValidator getOverrideValidator() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return IOverrideValidator.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void reportChange_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByPackageName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByUid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabled, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByPackageName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabledByPackageName, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByUid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabledByUid, getCallingPid(), getCallingUid());
        }

        protected void containsOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void setOverrides_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void putAllOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void putOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void setOverridesForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverrideForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void removeAllOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void removeOverridesOnReleaseBuilds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void enableTargetSdkChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void disableTargetSdkChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverrides_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverridesForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void getAppConfig_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_getAppConfig, getCallingPid(), getCallingUid());
        }

        protected void listAllChanges_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }
    }
}
