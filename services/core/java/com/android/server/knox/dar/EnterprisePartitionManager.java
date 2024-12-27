package com.android.server.knox.dar;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerTracedLock;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnterprisePartitionManager {
    public static final boolean isKnoxBuildEnabled;
    public static Object mInstallLock;
    public static Installer mPackageTasker;
    public static EnterprisePartitionManager sInstance;
    public final Context mContext;
    public final Injector mInjector;
    public final Hashtable mSessionIdDstPath;
    public List notAppliedPaths = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    static {
        Bundle knoxInfo = SemPersonaManager.getKnoxInfo();
        String string = knoxInfo != null ? knoxInfo.getString("version") : null;
        isKnoxBuildEnabled = (string == null || string.isEmpty() || "v00".equals(string)) ? false : true;
        sInstance = null;
    }

    public EnterprisePartitionManager(Injector injector) {
        this.mSessionIdDstPath = null;
        this.mInjector = injector;
        this.mContext = injector.mContext;
        if (isKnoxBuildEnabled) {
            this.mSessionIdDstPath = new Hashtable();
        }
    }

    public static boolean getDualDARLockstate() {
        boolean dualDARLockstate;
        Log.i("EnterprisePartitionManager", "getDualDARLockstate");
        synchronized (mInstallLock) {
            try {
                Installer installer = mPackageTasker;
                installer.getClass();
                try {
                    dualDARLockstate = installer.mInstalld.getDualDARLockstate();
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException unused) {
                return false;
            }
        }
        return dualDARLockstate;
    }

    public static synchronized EnterprisePartitionManager getInstance(Context context) {
        EnterprisePartitionManager enterprisePartitionManager;
        synchronized (EnterprisePartitionManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new EnterprisePartitionManager(new Injector(context));
                }
                enterprisePartitionManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterprisePartitionManager;
    }

    public static boolean setDualDARPolicyDir(int i, int i2, String str) {
        boolean dualDARPolicyDir;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setDualDARPolicyDir(user:", " flags:", " path:");
        m.append(str);
        m.append(")");
        Log.i("EnterprisePartitionManager", m.toString());
        synchronized (mInstallLock) {
            try {
                Installer installer = mPackageTasker;
                installer.getClass();
                try {
                    dualDARPolicyDir = installer.mInstalld.setDualDARPolicyDir(i, i2, str);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException unused) {
                return false;
            }
        }
        return dualDARPolicyDir;
    }

    public static boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
        boolean dualDARPolicyDirRecursively;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setDualDARPolicyDirRecursively(user:", " flags:", " path:");
        m.append(str);
        m.append(")");
        Log.i("EnterprisePartitionManager", m.toString());
        synchronized (mInstallLock) {
            try {
                Installer installer = mPackageTasker;
                installer.getClass();
                try {
                    dualDARPolicyDirRecursively = installer.mInstalld.setDualDARPolicyDirRecursively(i, i2, str);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException unused) {
                return false;
            }
        }
        return dualDARPolicyDirRecursively;
    }

    public static void setInstaller(Installer installer, PackageManagerTracedLock packageManagerTracedLock) {
        if (!isKnoxBuildEnabled) {
            mPackageTasker = null;
        } else {
            mPackageTasker = installer;
            mInstallLock = packageManagerTracedLock;
        }
    }

    public static void setTestInstaller(Installer installer, Object obj) {
        mPackageTasker = installer;
        mInstallLock = obj;
    }

    public final void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(Binder.getCallingPid(), Binder.getCallingUid(), this.mContext, "EnterprisePartitionManager", str) == 0) {
            return;
        }
        throw new SecurityException("Security Exception Occurred while pid[" + Binder.getCallingPid() + "] with uid[" + Binder.getCallingUid() + "] trying to access methodName [" + str + "] in [EnterprisePartitionManager] service");
    }

    public final int copy(int i, int i2, int i3, String str, String str2) {
        int i4;
        boolean copyKnoxAppData;
        checkCallerPermissionFor("copy");
        if (mPackageTasker == null) {
            return -19;
        }
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty() || !isUserUnlocked(i) || !isUserUnlocked(i2)) {
            return -2;
        }
        synchronized (mInstallLock) {
            i4 = -1;
            try {
                Installer installer = mPackageTasker;
                if (installer.checkBeforeRemote()) {
                    try {
                        copyKnoxAppData = installer.mInstalld.copyKnoxAppData(str, i, str2, i2, i3);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                } else {
                    copyKnoxAppData = false;
                }
                if (copyKnoxAppData) {
                    i4 = 0;
                }
            } catch (Installer.InstallerException unused) {
            }
        }
        return i4;
    }

    public final boolean hasDualDARPolicyRecursively(String str, List list) {
        boolean hasDualDARPolicyRecursively;
        AudioDeviceInventory$$ExternalSyntheticOutline0.m("hasDualDARPolicyRecursively(path:", str, ")", "EnterprisePartitionManager");
        try {
            List list2 = this.notAppliedPaths;
            if (list2 == null) {
                this.notAppliedPaths = new ArrayList();
            } else {
                ((ArrayList) list2).clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (mInstallLock) {
            try {
                try {
                    try {
                        Installer installer = mPackageTasker;
                        List list3 = this.notAppliedPaths;
                        installer.getClass();
                        try {
                            hasDualDARPolicyRecursively = installer.mInstalld.hasDualDARPolicyRecursively(str, list3);
                        } catch (Exception e2) {
                            Installer.InstallerException.from(e2);
                            throw null;
                        }
                    } finally {
                        if (!((ArrayList) this.notAppliedPaths).isEmpty()) {
                            Iterator it = ((ArrayList) this.notAppliedPaths).iterator();
                            while (it.hasNext()) {
                                String str2 = (String) it.next();
                                Log.d("EnterprisePartitionManager", "Policy not applied paths : " + str2);
                                ((ArrayList) list).add(str2);
                            }
                        }
                    }
                } catch (Installer.InstallerException unused) {
                    if (!((ArrayList) this.notAppliedPaths).isEmpty()) {
                        Iterator it2 = ((ArrayList) this.notAppliedPaths).iterator();
                        while (it2.hasNext()) {
                            String str3 = (String) it2.next();
                            Log.d("EnterprisePartitionManager", "Policy not applied paths : " + str3);
                            ((ArrayList) list).add(str3);
                        }
                    }
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return hasDualDARPolicyRecursively;
    }

    public final boolean isUserUnlocked(int i) {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            z = UserManager.get(this.mContext).isUserUnlocked(i);
            if (!z) {
                Log.e("EnterprisePartitionManager", "User " + i + " is not unlocked");
            }
        } finally {
            try {
                return z;
            } finally {
            }
        }
        return z;
    }
}
