package com.samsung.android.server.pm.install;

import android.util.ArrayMap;
import android.util.Log;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.File;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class PreloadDuplicateApps {
    public final Object mLock = new Object();
    public final Map mDuplicateDataPackages = new ArrayMap();
    public final Map mSystemPackages = new ArrayMap();

    public boolean isDuplicatePackage(AndroidPackage androidPackage, PackageSetting packageSetting, boolean z, int i) {
        if (androidPackage == null || packageSetting == null || (i & 16) != 0 || !packageSetting.isSystem() || z) {
            return false;
        }
        return packageSetting.getAndroidPackage().isStub() ? androidPackage.getLongVersionCode() >= packageSetting.getVersionCode() : androidPackage.getLongVersionCode() > packageSetting.getVersionCode();
    }

    public void addDuplicatePackage(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return;
        }
        Log.d("PreloadDuplicateApps", "Add duplicate package " + androidPackage.getPackageName() + " (" + androidPackage.getLongVersionCode() + ")");
        synchronized (this.mLock) {
            this.mDuplicateDataPackages.put(androidPackage.getPackageName(), new File(androidPackage.getPath()));
        }
    }

    public void addSystemPackage(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return;
        }
        Log.d("PreloadDuplicateApps", "Add system package " + androidPackage.getPackageName() + " (" + androidPackage.getLongVersionCode() + ")");
        synchronized (this.mLock) {
            this.mSystemPackages.put(androidPackage.getPackageName(), new File(androidPackage.getPath()));
        }
    }

    public boolean hasDuplicatePackage(String str) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mDuplicateDataPackages.containsKey(str);
        }
        return containsKey;
    }

    public void addSystemPackagesTo(final ArrayMap arrayMap) {
        synchronized (this.mLock) {
            this.mSystemPackages.forEach(new BiConsumer() { // from class: com.samsung.android.server.pm.install.PreloadDuplicateApps$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PreloadDuplicateApps.lambda$addSystemPackagesTo$0(arrayMap, (String) obj, (File) obj2);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$addSystemPackagesTo$0(ArrayMap arrayMap, String str, File file) {
        Log.d("PreloadDuplicateApps", "Add system package " + str + " (" + file + ") to expectingBetter");
        arrayMap.put(str, file);
    }

    public void clearPackages() {
        synchronized (this.mLock) {
            this.mDuplicateDataPackages.clear();
            this.mSystemPackages.clear();
        }
    }

    public void installDuplicatePackages(BiConsumer biConsumer) {
        synchronized (this.mLock) {
            this.mDuplicateDataPackages.forEach(biConsumer);
        }
    }
}
