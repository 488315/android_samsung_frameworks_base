package android.app;

import android.os.FileUtils;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Slog;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
class DexLoadReporter implements BaseDexClassLoader.Reporter {
    private static final boolean DEBUG = false;
    private static final DexLoadReporter INSTANCE = new DexLoadReporter();
    private static final String TAG = "DexLoadReporter";
    private final Set<String> mDataDirs = new HashSet();

    private DexLoadReporter() {
    }

    static DexLoadReporter getInstance() {
        return INSTANCE;
    }

    void registerAppDataDir(String str, String str2) {
        if (str2 != null) {
            synchronized (this.mDataDirs) {
                this.mDataDirs.add(str2);
            }
        }
    }

    public void report(Map<String, String> map) {
        if (map.isEmpty()) {
            Slog.wtf(TAG, "Bad call to DexLoadReporter: empty classLoaderContextMap");
        } else {
            notifyPackageManager(map);
            registerSecondaryDexForProfiling(map.keySet());
        }
    }

    private void notifyPackageManager(Map<String, String> map) {
        String currentPackageName = ActivityThread.currentPackageName();
        try {
            ActivityThread.getPackageManager().notifyDexLoad(currentPackageName, map, VMRuntime.getRuntime().vmInstructionSet());
        } catch (RemoteException e) {
            Slog.e(TAG, "Failed to notify PM about dex load for package " + currentPackageName, e);
        }
    }

    private void registerSecondaryDexForProfiling(Set<String> set) {
        String[] strArr;
        if (SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false)) {
            synchronized (this.mDataDirs) {
                strArr = (String[]) this.mDataDirs.toArray(new String[0]);
            }
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                registerSecondaryDexForProfiling(it.next(), strArr);
            }
        }
    }

    private void registerSecondaryDexForProfiling(String str, String[] strArr) {
        if (isSecondaryDexFile(str, strArr)) {
            File file = new File(str);
            File file2 = new File(file.getParent(), "oat");
            File file3 = new File(file2, file.getName() + ".cur.prof");
            File file4 = new File(file2, file.getName() + ".prof");
            if (!file2.exists() && !file2.mkdir()) {
                Slog.e(TAG, "Could not create the profile directory: " + file3);
                return;
            }
            try {
                file3.createNewFile();
                VMRuntime.registerAppInfo(ActivityThread.currentPackageName(), file3.getPath(), file4.getPath(), new String[]{str}, 4);
            } catch (IOException e) {
                Slog.e(TAG, "Failed to create profile for secondary dex " + str + ":" + e.getMessage());
            }
        }
    }

    private boolean isSecondaryDexFile(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (FileUtils.contains(str2, str)) {
                return true;
            }
        }
        return false;
    }
}
