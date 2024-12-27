package com.samsung.android.server.packagefeature;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.wm.MultiTaskingAppCompatAspectRatioOverrides;
import com.android.server.wm.MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0;
import com.android.server.wm.MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda1;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureUserChange {
    public final Map mChangeValuesAsUser;
    public final DumpInterface mDumpInterface;
    public final String mFileName;
    public final String mFilePath;
    public final int mIdentityFlag;
    public boolean mIsLoadFileCompleted;
    public boolean mIsSystemReady;
    public final PackageFeatureUserChangePersister mPersister;
    public final MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0 mPersisterCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DumpInterface {
        String valueToString(String str, int i, Object obj);
    }

    public PackageFeatureUserChange(int i, String str, String str2) {
        this(i, str, str2, null, true, null);
    }

    public PackageFeatureUserChange(int i, String str, String str2, DumpInterface dumpInterface) {
        this(i, str, str2, dumpInterface, true, null);
    }

    public PackageFeatureUserChange(int i, String str, String str2, DumpInterface dumpInterface, boolean z, MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0 multiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0) {
        this.mIdentityFlag = i;
        this.mFilePath = str;
        this.mFileName = str2;
        this.mDumpInterface = dumpInterface;
        this.mChangeValuesAsUser = new ConcurrentHashMap();
        if (!z) {
            this.mPersister = null;
            this.mPersisterCallback = null;
            return;
        }
        String str3 = PackageFeatureUserChangePersister.PACKAGE_SETTINGS_DIRECTORY;
        PackageFeatureUserChangePersister packageFeatureUserChangePersister = PackageFeatureUserChangePersister.LazyHolder.sPackageFeatureUserChangePersister;
        this.mPersister = packageFeatureUserChangePersister;
        synchronized (packageFeatureUserChangePersister.mLock) {
            ((ArrayList) packageFeatureUserChangePersister.mPackageFeatureUserChanges).add(this);
            synchronized (packageFeatureUserChangePersister.mLock) {
                packageFeatureUserChangePersister.mLoadRequestFlags = i | packageFeatureUserChangePersister.mLoadRequestFlags;
                PackageFeatureUserChangePersister.H h = packageFeatureUserChangePersister.mH;
                if (!h.hasMessages(1)) {
                    h.sendEmptyMessageDelayed(1, 500L);
                }
            }
        }
        this.mPersisterCallback = multiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0;
    }

    public final void dump(PrintWriter printWriter, String str, String str2) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "  ");
        for (Map.Entry entry : ((ConcurrentHashMap) this.mChangeValuesAsUser).entrySet()) {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) entry.getValue();
            if (!concurrentHashMap.isEmpty()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                printWriter.println(str2 + str + " (u" + intValue + ")");
                for (Map.Entry entry2 : concurrentHashMap.entrySet()) {
                    String str3 = (String) entry2.getKey();
                    Object value = entry2.getValue();
                    StringBuilder sb = new StringBuilder();
                    sb.append(m$1);
                    sb.append("[");
                    sb.append(str3);
                    sb.append("] ");
                    DumpInterface dumpInterface = this.mDumpInterface;
                    if (dumpInterface != null && value != null) {
                        value = dumpInterface.valueToString(str3, intValue, value);
                    }
                    sb.append(value);
                    printWriter.println(sb.toString());
                }
            }
        }
    }

    public final ConcurrentHashMap getChangeValuesAsUser(int i) {
        return (ConcurrentHashMap) ((ConcurrentHashMap) this.mChangeValuesAsUser).computeIfAbsent(Integer.valueOf(i), new PackageFeatureUserChange$$ExternalSyntheticLambda0());
    }

    public final Object getValue(int i, String str) {
        if (str != null) {
            return getChangeValuesAsUser(i).get(str);
        }
        Slog.w("PackageFeature", "getValue, packageName is null. caller=" + Debug.getCallers(6));
        return null;
    }

    public final void onLoadFileCompletedAndSystemReady(boolean z, boolean z2) {
        boolean z3;
        if (this.mPersisterCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                boolean z4 = z | this.mIsSystemReady;
                this.mIsSystemReady = z4;
                boolean z5 = z2 | this.mIsLoadFileCompleted;
                this.mIsLoadFileCompleted = z5;
                z3 = z4 && z5;
                if (z3) {
                    this.mIsLoadFileCompleted = false;
                    this.mIsSystemReady = false;
                }
            } finally {
            }
        }
        if (z3) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("onLoadFileCompletedAndSystemReady: flag="), this.mIdentityFlag, "PackageFeature");
            final MultiTaskingAppCompatAspectRatioOverrides.MinAspectRatioOverrides minAspectRatioOverrides = this.mPersisterCallback.f$0;
            minAspectRatioOverrides.getClass();
            Slog.d("MultiTaskingAppCompat", "Start to migrate to package manager.");
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            synchronized (minAspectRatioOverrides) {
                try {
                    for (Integer num : ((ConcurrentHashMap) minAspectRatioOverrides.mUserOverrides.mChangeValuesAsUser).keySet()) {
                        ((ConcurrentHashMap) concurrentHashMap.computeIfAbsent(num, new MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda1())).putAll(minAspectRatioOverrides.mUserOverrides.getChangeValuesAsUser(num.intValue()));
                    }
                } finally {
                }
            }
            concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.server.wm.MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MultiTaskingAppCompatAspectRatioOverrides.MinAspectRatioOverrides minAspectRatioOverrides2 = MultiTaskingAppCompatAspectRatioOverrides.MinAspectRatioOverrides.this;
                    minAspectRatioOverrides2.getClass();
                    int intValue = ((Integer) obj).intValue();
                    MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides = MultiTaskingAppCompatAspectRatioOverrides.this;
                    multiTaskingAppCompatAspectRatioOverrides.getClass();
                    ((ConcurrentHashMap) obj2).forEach(new MultiTaskingAppCompatAspectRatioOverrides$$ExternalSyntheticLambda0(multiTaskingAppCompatAspectRatioOverrides, intValue));
                }
            });
            synchronized (minAspectRatioOverrides) {
                try {
                    Iterator it = ((ConcurrentHashMap) minAspectRatioOverrides.mUserOverrides.mChangeValuesAsUser).keySet().iterator();
                    while (it.hasNext()) {
                        minAspectRatioOverrides.mUserOverrides.reset(((Integer) it.next()).intValue());
                    }
                } finally {
                }
            }
            Slog.d("MultiTaskingAppCompat", "Finish to migrate to package manager.");
        }
    }

    public final Object putValue(String str, int i, Object obj) {
        Object obj2 = null;
        if (str == null) {
            Slog.w("PackageFeature", "putValue, packageName is null. caller=" + Debug.getCallers(6));
        } else if (obj == null) {
            Slog.w("PackageFeature", "putValue, value is null. caller=" + Debug.getCallers(6));
        } else {
            obj2 = getChangeValuesAsUser(i).put(str, obj);
            PackageFeatureUserChangePersister packageFeatureUserChangePersister = this.mPersister;
            if (packageFeatureUserChangePersister != null) {
                int i2 = this.mIdentityFlag;
                synchronized (packageFeatureUserChangePersister.mLock) {
                    try {
                        Integer num = (Integer) ((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId).get(Integer.valueOf(i));
                        if (num != null) {
                            i2 |= num.intValue();
                        }
                        ((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId).put(Integer.valueOf(i), Integer.valueOf(i2));
                        PackageFeatureUserChangePersister.H h = packageFeatureUserChangePersister.mH;
                        if (!h.hasMessages(1)) {
                            h.sendEmptyMessageDelayed(1, 2000L);
                        }
                    } finally {
                    }
                }
            }
        }
        return obj2;
    }

    public final void reset(int i) {
        getChangeValuesAsUser(i).clear();
        PackageFeatureUserChangePersister packageFeatureUserChangePersister = this.mPersister;
        if (packageFeatureUserChangePersister != null) {
            int i2 = this.mIdentityFlag;
            synchronized (packageFeatureUserChangePersister.mLock) {
                packageFeatureUserChangePersister.mH.removeMessages(1);
                packageFeatureUserChangePersister.mLoadRequestFlags = 0;
                ((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId).clear();
                packageFeatureUserChangePersister.resetFiles(i, i2);
            }
        }
    }
}
