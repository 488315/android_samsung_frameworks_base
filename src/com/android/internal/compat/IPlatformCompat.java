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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPlatformCompat.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPlatformCompat)) {
                return (IPlatformCompat) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportChange(j, applicationInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long j2 = parcel.readLong();
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByPackageName(j2, string, i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long j3 = parcel.readLong();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByUid(j3, i4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long j4 = parcel.readLong();
                    ApplicationInfo applicationInfo2 = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsChangeEnabled = isChangeEnabled(j4, applicationInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsChangeEnabled);
                    return true;
                case 5:
                    long j5 = parcel.readLong();
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsChangeEnabledByPackageName = isChangeEnabledByPackageName(j5, string2, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsChangeEnabledByPackageName);
                    return true;
                case 6:
                    long j6 = parcel.readLong();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsChangeEnabledByUid = isChangeEnabledByUid(j6, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsChangeEnabledByUid);
                    return true;
                case 7:
                    long j7 = parcel.readLong();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zContainsOverride = containsOverride(j7, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zContainsOverride);
                    return true;
                case 8:
                    CompatibilityChangeConfig compatibilityChangeConfig = (CompatibilityChangeConfig) parcel.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverrides(compatibilityChangeConfig, string4);
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
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    putOverridesOnReleaseBuilds(compatibilityOverrideConfig, string5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    CompatibilityChangeConfig compatibilityChangeConfig2 = (CompatibilityChangeConfig) parcel.readTypedObject(CompatibilityChangeConfig.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverridesForTest(compatibilityChangeConfig2, string6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    long j8 = parcel.readLong();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearOverride = clearOverride(j8, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearOverride);
                    return true;
                case 13:
                    long j9 = parcel.readLong();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zClearOverrideForTest = clearOverrideForTest(j9, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearOverrideForTest);
                    return true;
                case 14:
                    CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig = (CompatibilityOverridesToRemoveByPackageConfig) parcel.readTypedObject(CompatibilityOverridesToRemoveByPackageConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAllOverridesOnReleaseBuilds(compatibilityOverridesToRemoveByPackageConfig);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig = (CompatibilityOverridesToRemoveConfig) parcel.readTypedObject(CompatibilityOverridesToRemoveConfig.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridesOnReleaseBuilds(compatibilityOverridesToRemoveConfig, string9);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string10 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iEnableTargetSdkChanges = enableTargetSdkChanges(string10, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnableTargetSdkChanges);
                    return true;
                case 17:
                    String string11 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iDisableTargetSdkChanges = disableTargetSdkChanges(string11, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDisableTargetSdkChanges);
                    return true;
                case 18:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverrides(string12);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverridesForTest(string13);
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
                    CompatibilityChangeInfo[] compatibilityChangeInfoArrListAllChanges = listAllChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(compatibilityChangeInfoArrListAllChanges, 1);
                    return true;
                case 22:
                    CompatibilityChangeInfo[] compatibilityChangeInfoArrListUIChanges = listUIChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(compatibilityChangeInfoArrListUIChanges, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByPackageName(long j, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByUid(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabled(long j, ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByUid(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean containsOverride(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverrides(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityChangeConfig, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putAllOverridesOnReleaseBuilds(CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityOverridesByPackageConfig, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putOverridesOnReleaseBuilds(CompatibilityOverrideConfig compatibilityOverrideConfig, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityOverrideConfig, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverridesForTest(CompatibilityChangeConfig compatibilityChangeConfig, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityChangeConfig, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverride(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverrideForTest(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeAllOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityOverridesToRemoveByPackageConfig, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeOverridesOnReleaseBuilds(CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(compatibilityOverridesToRemoveConfig, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int enableTargetSdkChanges(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int disableTargetSdkChanges(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverrides(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverridesForTest(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeConfig getAppConfig(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CompatibilityChangeConfig) parcelObtain2.readTypedObject(CompatibilityChangeConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listAllChanges() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CompatibilityChangeInfo[]) parcelObtain2.createTypedArray(CompatibilityChangeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public CompatibilityChangeInfo[] listUIChanges() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CompatibilityChangeInfo[]) parcelObtain2.createTypedArray(CompatibilityChangeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public IOverrideValidator getOverrideValidator() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IOverrideValidator.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
