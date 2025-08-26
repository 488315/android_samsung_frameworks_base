package com.android.internal.infra;

import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class WhitelistHelper {
    private static final String TAG = "WhitelistHelper";
    private ArrayMap<String, ArraySet<ComponentName>> mWhitelistedPackages;

    public void setWhitelist(ArraySet<String> arraySet, ArraySet<ComponentName> arraySet2) {
        this.mWhitelistedPackages = null;
        if (arraySet == null && arraySet2 == null) {
            return;
        }
        if ((arraySet != null && arraySet.isEmpty()) || (arraySet2 != null && arraySet2.isEmpty())) {
            throw new IllegalArgumentException("Packages or Components cannot be empty.");
        }
        this.mWhitelistedPackages = new ArrayMap<>();
        if (arraySet != null) {
            for (int i = 0; i < arraySet.size(); i++) {
                this.mWhitelistedPackages.put(arraySet.valueAt(i), null);
            }
        }
        if (arraySet2 != null) {
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                ComponentName componentNameValueAt = arraySet2.valueAt(i2);
                if (componentNameValueAt == null) {
                    Log.w(TAG, "setWhitelist(): component is null");
                } else {
                    String packageName = componentNameValueAt.getPackageName();
                    ArraySet<ComponentName> arraySet3 = this.mWhitelistedPackages.get(packageName);
                    if (arraySet3 == null) {
                        arraySet3 = new ArraySet<>();
                        this.mWhitelistedPackages.put(packageName, arraySet3);
                    }
                    arraySet3.add(componentNameValueAt);
                }
            }
        }
    }

    public void setWhitelist(List<String> list, List<ComponentName> list2) {
        setWhitelist(list == null ? null : new ArraySet<>(list), list2 != null ? new ArraySet<>(list2) : null);
    }

    public boolean isWhitelisted(String str) {
        Objects.requireNonNull(str);
        ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
        return arrayMap != null && arrayMap.containsKey(str) && this.mWhitelistedPackages.get(str) == null;
    }

    public boolean isWhitelisted(ComponentName componentName) {
        Objects.requireNonNull(componentName);
        String packageName = componentName.getPackageName();
        ArraySet<ComponentName> whitelistedComponents = getWhitelistedComponents(packageName);
        if (whitelistedComponents != null) {
            return whitelistedComponents.contains(componentName);
        }
        return isWhitelisted(packageName);
    }

    public ArraySet<ComponentName> getWhitelistedComponents(String str) {
        Objects.requireNonNull(str);
        ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str);
    }

    public ArraySet<String> getWhitelistedPackages() {
        if (this.mWhitelistedPackages == null) {
            return null;
        }
        return new ArraySet<>(this.mWhitelistedPackages.keySet());
    }

    public String toString() {
        return "WhitelistHelper[" + this.mWhitelistedPackages + ']';
    }

    public void dump(String str, String str2, PrintWriter printWriter) {
        ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
        if (arrayMap == null || arrayMap.size() == 0) {
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.println(": (no whitelisted packages)");
            return;
        }
        String str3 = str + "  ";
        int size = this.mWhitelistedPackages.size();
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(": ");
        printWriter.print(size);
        printWriter.println(" packages");
        for (int i = 0; i < this.mWhitelistedPackages.size(); i++) {
            String strKeyAt = this.mWhitelistedPackages.keyAt(i);
            ArraySet<ComponentName> arraySetValueAt = this.mWhitelistedPackages.valueAt(i);
            printWriter.print(str3);
            printWriter.print(i);
            printWriter.print(MediaMetrics.SEPARATOR);
            printWriter.print(strKeyAt);
            printWriter.print(": ");
            if (arraySetValueAt == null) {
                printWriter.println("(whole package)");
            } else {
                printWriter.print(NavigationBarInflaterView.SIZE_MOD_START);
                printWriter.print(arraySetValueAt.valueAt(0));
                for (int i2 = 1; i2 < arraySetValueAt.size(); i2++) {
                    printWriter.print(", ");
                    printWriter.print(arraySetValueAt.valueAt(i2));
                }
                printWriter.println(NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
    }
}
