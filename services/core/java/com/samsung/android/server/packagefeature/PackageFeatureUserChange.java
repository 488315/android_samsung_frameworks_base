package com.samsung.android.server.packagefeature;

import android.os.Debug;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class PackageFeatureUserChange {
    public final Map mChangeValuesAsUser;
    public final DumpInterface mDumpInterface;
    public final String mFileName;
    public final String mFilePath;
    public final int mIdentityFlag;
    public final PackageFeatureUserChangePersister mPersister;

    public interface DumpInterface {
        String valueToString(int i, String str, Object obj);
    }

    public PackageFeatureUserChange(int i, String str, String str2) {
        this(i, str, str2, null, true);
    }

    public PackageFeatureUserChange(int i, String str, String str2, DumpInterface dumpInterface) {
        this(i, str, str2, dumpInterface, true);
    }

    public PackageFeatureUserChange(int i, String str, String str2, DumpInterface dumpInterface, boolean z) {
        this.mIdentityFlag = i;
        this.mFilePath = str;
        this.mFileName = str2;
        this.mDumpInterface = dumpInterface;
        this.mChangeValuesAsUser = new ConcurrentHashMap();
        if (z) {
            PackageFeatureUserChangePersister packageFeatureUserChangePersister = PackageFeatureUserChangePersister.getInstance();
            this.mPersister = packageFeatureUserChangePersister;
            packageFeatureUserChangePersister.addUserChange(this);
            return;
        }
        this.mPersister = null;
    }

    public int getIdentityFlag() {
        return this.mIdentityFlag;
    }

    public Set getUserIds() {
        return this.mChangeValuesAsUser.keySet();
    }

    public ConcurrentHashMap getChangeValuesAsUser(int i) {
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.mChangeValuesAsUser.get(Integer.valueOf(i));
        if (concurrentHashMap != null) {
            return concurrentHashMap;
        }
        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        this.mChangeValuesAsUser.put(Integer.valueOf(i), concurrentHashMap2);
        return concurrentHashMap2;
    }

    public Object getValue(int i, String str) {
        if (str == null) {
            Slog.w("PackageFeature", "getValue, packageName is null. caller=" + Debug.getCallers(6));
            return null;
        }
        return getChangeValuesAsUser(i).get(str);
    }

    public Object putValue(int i, String str, Object obj) {
        return putValue(i, str, obj, false);
    }

    public Object putValue(int i, String str, Object obj, boolean z) {
        if (str == null) {
            Slog.w("PackageFeature", "putValue, packageName is null. caller=" + Debug.getCallers(6));
            return null;
        }
        if (obj == null) {
            Slog.w("PackageFeature", "putValue, value is null. caller=" + Debug.getCallers(6));
            return null;
        }
        ConcurrentHashMap changeValuesAsUser = getChangeValuesAsUser(i);
        Object put = changeValuesAsUser.put(str, obj);
        if (z) {
            changeValuesAsUser.remove(str);
        }
        requestToSave(i);
        return put;
    }

    public void requestToSave(int i) {
        PackageFeatureUserChangePersister packageFeatureUserChangePersister = this.mPersister;
        if (packageFeatureUserChangePersister != null) {
            packageFeatureUserChangePersister.requestToSave(i, this.mIdentityFlag);
        }
    }

    public void reset(int i) {
        getChangeValuesAsUser(i).clear();
        PackageFeatureUserChangePersister packageFeatureUserChangePersister = this.mPersister;
        if (packageFeatureUserChangePersister != null) {
            packageFeatureUserChangePersister.requestToReset(i, this.mIdentityFlag);
        }
    }

    public void dump(PrintWriter printWriter, String str, String str2) {
        String str3 = str2 + "  ";
        for (Map.Entry entry : this.mChangeValuesAsUser.entrySet()) {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) entry.getValue();
            if (!concurrentHashMap.isEmpty()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                printWriter.println(str2 + str + " (u" + intValue + ")");
                for (Map.Entry entry2 : concurrentHashMap.entrySet()) {
                    String str4 = (String) entry2.getKey();
                    Object value = entry2.getValue();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str3);
                    sb.append("[");
                    sb.append(str4);
                    sb.append("] ");
                    DumpInterface dumpInterface = this.mDumpInterface;
                    if (dumpInterface != null && value != null) {
                        value = dumpInterface.valueToString(intValue, str4, value);
                    }
                    sb.append(value);
                    printWriter.println(sb.toString());
                }
            }
        }
    }
}
