package com.android.server.appop;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserPackage;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.Xml;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.PermissionManagerService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpsCheckingServiceImpl implements AppOpsCheckingServiceInterface {
    static final int CURRENT_VERSION = 4;
    public final Context mContext;
    public boolean mFastWriteScheduled;
    public final AtomicFile mFile;
    public final Handler mHandler;
    public final Object mLock;
    public boolean mWriteScheduled;
    public int mVersionAtBoot = -2;
    final SparseArray mUidModes = new SparseArray();
    public final SparseArray mUserPackageModes = new SparseArray();
    public final LegacyAppOpStateParser mAppOpsStateParser = new LegacyAppOpStateParser();
    public final List mModeChangedListeners = new ArrayList();
    public final AnonymousClass1 mWriteRunner =
            new Runnable() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (AppOpsCheckingServiceImpl.this.mLock) {
                        AppOpsCheckingServiceImpl appOpsCheckingServiceImpl =
                                AppOpsCheckingServiceImpl.this;
                        appOpsCheckingServiceImpl.mWriteScheduled = false;
                        appOpsCheckingServiceImpl.mFastWriteScheduled = false;
                        new AsyncTask() { // from class:
                                          // com.android.server.appop.AppOpsCheckingServiceImpl.1.1
                            @Override // android.os.AsyncTask
                            public final Object doInBackground(Object[] objArr) {
                                AppOpsCheckingServiceImpl.this.writeState();
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.appop.AppOpsCheckingServiceImpl$1] */
    public AppOpsCheckingServiceImpl(File file, Object obj, Handler handler, Context context) {
        this.mFile = new AtomicFile(file);
        this.mLock = obj;
        this.mHandler = handler;
        this.mContext = context;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean addAppOpsModeChangedListener(
            AppOpsService.AnonymousClass2 anonymousClass2) {
        boolean add;
        synchronized (this.mLock) {
            add = ((ArrayList) this.mModeChangedListeners).add(anonymousClass2);
        }
        return add;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void clearAllModes() {
        synchronized (this.mLock) {
            this.mUidModes.clear();
            this.mUserPackageModes.clear();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        synchronized (this.mLock) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i);
                if (sparseIntArray == null) {
                    return sparseBooleanArray;
                }
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2) == 4) {
                        sparseBooleanArray.put(sparseIntArray.keyAt(i2), true);
                    }
                }
                return sparseBooleanArray;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i, String str) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i);
                if (arrayMap == null) {
                    return sparseBooleanArray;
                }
                SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
                if (sparseIntArray == null) {
                    return sparseBooleanArray;
                }
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2) == 4) {
                        sparseBooleanArray.put(sparseIntArray.keyAt(i2), true);
                    }
                }
                return sparseBooleanArray;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultPackageModes(int i, String str) {
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i);
                if (arrayMap == null) {
                    return new SparseIntArray();
                }
                SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
                if (sparseIntArray == null) {
                    return new SparseIntArray();
                }
                return sparseIntArray.clone();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultUidModes(int i) {
        synchronized (this.mLock) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
                if (sparseIntArray == null) {
                    return new SparseIntArray();
                }
                return sparseIntArray.clone();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getPackageMode(int i, int i2, String str) {
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i2, null);
                if (arrayMap == null) {
                    return AppOpsManager.opToDefaultMode(i);
                }
                SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.getOrDefault(str, null);
                if (sparseIntArray == null) {
                    return AppOpsManager.opToDefaultMode(i);
                }
                return sparseIntArray.get(i, AppOpsManager.opToDefaultMode(i));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List getPackagesWithNonDefaultModes() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUserPackageModes.size(); i++) {
                try {
                    ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.valueAt(i);
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        if (((SparseIntArray) arrayMap.valueAt(i2)).size() > 0) {
                            arrayList.add(
                                    UserPackage.of(
                                            this.mUserPackageModes.keyAt(i),
                                            (String) arrayMap.keyAt(i2)));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getUidMode(int i, int i2, String str) {
        synchronized (this.mLock) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
                if (sparseIntArray == null) {
                    return AppOpsManager.opToDefaultMode(i2);
                }
                return sparseIntArray.get(i2, AppOpsManager.opToDefaultMode(i2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List getUidsWithNonDefaultModes() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUidModes.size(); i++) {
                try {
                    if (((SparseIntArray) this.mUidModes.valueAt(i)).size() > 0) {
                        arrayList.add(Integer.valueOf(this.mUidModes.keyAt(i)));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void readState() {
        synchronized (this.mFile) {
            synchronized (this.mLock) {
                LegacyAppOpStateParser legacyAppOpStateParser = this.mAppOpsStateParser;
                AtomicFile atomicFile = this.mFile;
                SparseArray sparseArray = this.mUidModes;
                SparseArray sparseArray2 = this.mUserPackageModes;
                legacyAppOpStateParser.getClass();
                this.mVersionAtBoot =
                        LegacyAppOpStateParser.readState(atomicFile, sparseArray, sparseArray2);
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean removePackage(int i, String str) {
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i, null);
                if (arrayMap == null) {
                    return false;
                }
                if (((SparseIntArray) arrayMap.remove(str)) == null) {
                    return false;
                }
                scheduleFastWriteLocked();
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void removeUid(int i) {
        synchronized (this.mLock) {
            try {
                if (((SparseIntArray) this.mUidModes.get(i)) == null) {
                    return;
                }
                this.mUidModes.remove(i);
                scheduleFastWriteLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void resetUseFullScreenIntentLocked() {
        PermissionManagerService.PermissionManagerServiceInternalImpl
                permissionManagerServiceInternalImpl =
                        (PermissionManagerService.PermissionManagerServiceInternalImpl)
                                LocalServices.getService(
                                        PermissionManagerService
                                                .PermissionManagerServiceInternalImpl.class);
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        PermissionManager permissionManager =
                (PermissionManager) this.mContext.getSystemService(PermissionManager.class);
        String opToPermission = AppOpsManager.opToPermission(133);
        String[] appOpPermissionPackages =
                PermissionManagerService.this.mPermissionManagerServiceImpl
                        .getAppOpPermissionPackages(opToPermission);
        int[] userIds = userManagerInternal.getUserIds();
        for (String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if ((permissionManager.getPermissionFlags(str, opToPermission, UserHandle.of(i))
                                & 1)
                        == 0) {
                    setUidMode(packageUid, 133, AppOpsManager.opToDefaultMode(133));
                }
            }
        }
    }

    public final void scheduleFastWriteLocked() {
        if (this.mFastWriteScheduled) {
            return;
        }
        this.mWriteScheduled = true;
        this.mFastWriteScheduled = true;
        Handler handler = this.mHandler;
        AnonymousClass1 anonymousClass1 = this.mWriteRunner;
        handler.removeCallbacks(anonymousClass1);
        handler.postDelayed(anonymousClass1, 10000L);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void setPackageMode(int i, int i2, int i3, String str) {
        int opToDefaultMode = AppOpsManager.opToDefaultMode(i);
        synchronized (this.mLock) {
            try {
                SparseIntArray sparseIntArray = null;
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i3, null);
                if (arrayMap == null && i2 != opToDefaultMode) {
                    arrayMap = new ArrayMap();
                    this.mUserPackageModes.put(i3, arrayMap);
                }
                if (i2
                        == ((arrayMap == null
                                        || (sparseIntArray = (SparseIntArray) arrayMap.get(str))
                                                == null)
                                ? opToDefaultMode
                                : sparseIntArray.get(i, opToDefaultMode))) {
                    return;
                }
                if (i2 == opToDefaultMode) {
                    sparseIntArray.delete(i);
                    if (sparseIntArray.size() == 0) {
                        arrayMap.remove(str);
                        if (arrayMap.size() == 0) {
                            this.mUserPackageModes.remove(i3);
                        }
                    }
                } else {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                        this.mUserPackageModes.put(i3, arrayMap);
                    }
                    if (sparseIntArray == null) {
                        sparseIntArray = new SparseIntArray();
                        arrayMap.put(str, sparseIntArray);
                    }
                    sparseIntArray.put(i, i2);
                }
                scheduleFastWriteLocked();
                ArrayList arrayList = new ArrayList(this.mModeChangedListeners);
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    AppOpsService appOpsService = AppOpsService.this;
                    appOpsService.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new AppOpsService$$ExternalSyntheticLambda15(1),
                                    appOpsService,
                                    str,
                                    Integer.valueOf(i),
                                    Integer.valueOf(i2),
                                    Integer.valueOf(i3)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean setUidMode(int i, int i2, int i3) {
        int opToDefaultMode = AppOpsManager.opToDefaultMode(i2);
        synchronized (this.mLock) {
            try {
                SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
                if (i3
                        == (sparseIntArray != null
                                ? sparseIntArray.get(i2, opToDefaultMode)
                                : opToDefaultMode)) {
                    return false;
                }
                if (i3 == opToDefaultMode) {
                    sparseIntArray.delete(i2);
                    if (sparseIntArray.size() == 0) {
                        this.mUidModes.remove(i);
                    }
                } else {
                    if (sparseIntArray == null) {
                        sparseIntArray = new SparseIntArray();
                        this.mUidModes.put(i, sparseIntArray);
                    }
                    sparseIntArray.put(i2, i3);
                }
                scheduleFastWriteLocked();
                ArrayList arrayList = new ArrayList(this.mModeChangedListeners);
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    ((AppOpsService.AnonymousClass2) arrayList.get(i4))
                            .onUidModeChanged(i, i2, "default:0");
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void shutdown() {
        boolean z;
        synchronized (this) {
            try {
                z = false;
                if (this.mWriteScheduled) {
                    this.mWriteScheduled = false;
                    this.mFastWriteScheduled = false;
                    this.mHandler.removeCallbacks(this.mWriteRunner);
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            writeState();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void systemReady() {
        synchronized (this.mLock) {
            int i = this.mVersionAtBoot;
            if (i != -2 && i < 4) {
                Slog.d(
                        "LegacyAppOpsServiceInterfaceImpl",
                        "Upgrading app-ops xml from version " + i + " to 4");
                if (i == -1) {
                    upgradeRunAnyInBackgroundLocked();
                } else if (i != 1) {
                    if (i != 2 && i != 3) {
                        scheduleFastWriteLocked();
                    }
                    resetUseFullScreenIntentLocked();
                    scheduleFastWriteLocked();
                }
                upgradeScheduleExactAlarmLocked();
                resetUseFullScreenIntentLocked();
                scheduleFastWriteLocked();
            }
        }
    }

    public void upgradeRunAnyInBackgroundLocked() {
        int size = this.mUidModes.size();
        for (int i = 0; i < size; i++) {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.valueAt(i);
            int indexOfKey = sparseIntArray.indexOfKey(63);
            if (indexOfKey >= 0) {
                sparseIntArray.put(70, sparseIntArray.valueAt(indexOfKey));
            }
        }
        int size2 = this.mUserPackageModes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.valueAt(i2);
            int size3 = arrayMap.size();
            for (int i3 = 0; i3 < size3; i3++) {
                SparseIntArray sparseIntArray2 = (SparseIntArray) arrayMap.valueAt(i3);
                int indexOfKey2 = sparseIntArray2.indexOfKey(63);
                if (indexOfKey2 >= 0) {
                    sparseIntArray2.put(70, sparseIntArray2.valueAt(indexOfKey2));
                }
            }
        }
    }

    public void upgradeScheduleExactAlarmLocked() {
        PermissionManagerService.PermissionManagerServiceInternalImpl
                permissionManagerServiceInternalImpl =
                        (PermissionManagerService.PermissionManagerServiceInternalImpl)
                                LocalServices.getService(
                                        PermissionManagerService
                                                .PermissionManagerServiceInternalImpl.class);
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        String[] appOpPermissionPackages =
                PermissionManagerService.this.mPermissionManagerServiceImpl
                        .getAppOpPermissionPackages(AppOpsManager.opToPermission(107));
        int[] userIds = userManagerInternal.getUserIds();
        for (String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if (getUidMode(packageUid, 107, "default:0")
                        == AppOpsManager.opToDefaultMode(107)) {
                    setUidMode(packageUid, 107, 0);
                }
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void writeState() {
        int size;
        int size2;
        synchronized (this.mFile) {
            try {
                try {
                    FileOutputStream startWrite = this.mFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "app-ops");
                        resolveSerializer.attributeInt((String) null, "v", 4);
                        SparseArray sparseArray = new SparseArray();
                        SparseArray sparseArray2 = new SparseArray();
                        synchronized (this.mLock) {
                            try {
                                size = this.mUidModes.size();
                                for (int i = 0; i < size; i++) {
                                    sparseArray.put(
                                            this.mUidModes.keyAt(i),
                                            ((SparseIntArray) this.mUidModes.valueAt(i)).clone());
                                }
                                size2 = this.mUserPackageModes.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    int keyAt = this.mUserPackageModes.keyAt(i2);
                                    ArrayMap arrayMap =
                                            (ArrayMap) this.mUserPackageModes.valueAt(i2);
                                    ArrayMap arrayMap2 = new ArrayMap();
                                    sparseArray2.put(keyAt, arrayMap2);
                                    int size3 = arrayMap.size();
                                    for (int i3 = 0; i3 < size3; i3++) {
                                        arrayMap2.put(
                                                (String) arrayMap.keyAt(i3),
                                                ((SparseIntArray) arrayMap.valueAt(i3)).clone());
                                    }
                                }
                            } finally {
                            }
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            int keyAt2 = sparseArray.keyAt(i4);
                            SparseIntArray sparseIntArray =
                                    (SparseIntArray) sparseArray.valueAt(i4);
                            resolveSerializer.startTag((String) null, "uid");
                            resolveSerializer.attributeInt((String) null, "n", keyAt2);
                            int size4 = sparseIntArray.size();
                            for (int i5 = 0; i5 < size4; i5++) {
                                int keyAt3 = sparseIntArray.keyAt(i5);
                                int valueAt = sparseIntArray.valueAt(i5);
                                resolveSerializer.startTag((String) null, "op");
                                resolveSerializer.attributeInt((String) null, "n", keyAt3);
                                resolveSerializer.attributeInt((String) null, "m", valueAt);
                                resolveSerializer.endTag((String) null, "op");
                            }
                            resolveSerializer.endTag((String) null, "uid");
                        }
                        for (int i6 = 0; i6 < size2; i6++) {
                            int keyAt4 = sparseArray2.keyAt(i6);
                            ArrayMap arrayMap3 = (ArrayMap) sparseArray2.valueAt(i6);
                            resolveSerializer.startTag((String) null, "user");
                            resolveSerializer.attributeInt((String) null, "n", keyAt4);
                            int size5 = arrayMap3.size();
                            int i7 = 0;
                            while (i7 < size5) {
                                String str = (String) arrayMap3.keyAt(i7);
                                SparseIntArray sparseIntArray2 =
                                        (SparseIntArray) arrayMap3.valueAt(i7);
                                resolveSerializer.startTag((String) null, "pkg");
                                resolveSerializer.attribute((String) null, "n", str);
                                int size6 = sparseIntArray2.size();
                                int i8 = 0;
                                while (i8 < size6) {
                                    int keyAt5 = sparseIntArray2.keyAt(i8);
                                    int valueAt2 = sparseIntArray2.valueAt(i8);
                                    resolveSerializer.startTag((String) null, "op");
                                    resolveSerializer.attributeInt((String) null, "n", keyAt5);
                                    resolveSerializer.attributeInt((String) null, "m", valueAt2);
                                    resolveSerializer.endTag((String) null, "op");
                                    i8++;
                                    size5 = size5;
                                }
                                resolveSerializer.endTag((String) null, "pkg");
                                i7++;
                                size5 = size5;
                            }
                            resolveSerializer.endTag((String) null, "user");
                        }
                        resolveSerializer.endTag((String) null, "app-ops");
                        resolveSerializer.endDocument();
                        this.mFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w(
                                "LegacyAppOpsServiceInterfaceImpl",
                                "Failed to write state, restoring backup.",
                                e);
                        this.mFile.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Failed to write state: " + e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
