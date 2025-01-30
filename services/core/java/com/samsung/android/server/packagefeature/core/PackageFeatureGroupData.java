package com.samsung.android.server.packagefeature.core;

import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class PackageFeatureGroupData implements Serializable {
    private static final long serialVersionUID = 202206080250L;
    private final Map mFeatures = new ConcurrentHashMap();
    private int mVersion;

    public PackageFeatureGroupData(int i) {
        this.mVersion = i;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void putPackageFeature(String str, String str2, String str3, String str4) {
        getPackageFeature(str).put(str2, str3, str4);
    }

    public PackageFeatureData getCopiedPackageFeature(String str) {
        PackageFeatureData packageFeatureData = new PackageFeatureData();
        packageFeatureData.putAll(getPackageFeature(str));
        return packageFeatureData;
    }

    public final PackageFeatureRawData getPackageFeature(String str) {
        PackageFeatureRawData packageFeatureRawData = (PackageFeatureRawData) this.mFeatures.get(str);
        if (packageFeatureRawData != null) {
            return packageFeatureRawData;
        }
        PackageFeatureRawData packageFeatureRawData2 = new PackageFeatureRawData();
        this.mFeatures.put(str, packageFeatureRawData2);
        return packageFeatureRawData2;
    }

    public void dump(PrintWriter printWriter, String str, Set set) {
        String str2 = str + "  ";
        for (Map.Entry entry : this.mFeatures.entrySet()) {
            String str3 = (String) entry.getKey();
            PackageFeatureRawData packageFeatureRawData = (PackageFeatureRawData) entry.getValue();
            printWriter.print(str + "FeatureName=" + str3 + ", Size=" + packageFeatureRawData.size());
            if (!set.contains(str3)) {
                printWriter.println();
                printWriter.println(str2 + "Callback for feature is not registered.");
            } else {
                packageFeatureRawData.dump(printWriter, str2);
            }
        }
    }
}
