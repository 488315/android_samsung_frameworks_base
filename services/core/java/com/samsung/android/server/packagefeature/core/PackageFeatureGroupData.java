package com.samsung.android.server.packagefeature.core;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class PackageFeatureGroupData implements Serializable {
    private static final long serialVersionUID = 202206080250L;
    private final Map mFeatures = new ConcurrentHashMap();
    private int mVersion;

    public PackageFeatureGroupData(int i) {
        this.mVersion = i;
    }

    public final void dump(PrintWriter printWriter, String str, Set set) {
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
        for (Map.Entry entry : this.mFeatures.entrySet()) {
            String str2 = (String) entry.getKey();
            PackageFeatureRawData packageFeatureRawData = (PackageFeatureRawData) entry.getValue();
            printWriter.print(str + "FeatureName=" + str2 + ", Size=" + packageFeatureRawData.size());
            if (set.contains(str2)) {
                packageFeatureRawData.dump(printWriter, m$1);
            } else {
                printWriter.println();
                printWriter.println(m$1 + "Callback for feature is not registered.");
            }
        }
    }

    public final PackageFeatureData getCopiedPackageFeature(String str) {
        PackageFeatureData packageFeatureData = new PackageFeatureData();
        PackageFeatureRawData packageFeatureRawData = (PackageFeatureRawData) this.mFeatures.get(str);
        if (packageFeatureRawData == null) {
            packageFeatureRawData = new PackageFeatureRawData();
            this.mFeatures.put(str, packageFeatureRawData);
        }
        packageFeatureData.putAll(packageFeatureRawData);
        return packageFeatureData;
    }

    public final int getVersion() {
        return this.mVersion;
    }

    public final void putPackageFeature(String str, String str2, String str3, String str4) {
        PackageFeatureRawData packageFeatureRawData = (PackageFeatureRawData) this.mFeatures.get(str);
        if (packageFeatureRawData == null) {
            packageFeatureRawData = new PackageFeatureRawData();
            this.mFeatures.put(str, packageFeatureRawData);
        }
        packageFeatureRawData.put(str2, str3, str4);
    }
}
