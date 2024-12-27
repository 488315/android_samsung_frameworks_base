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
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
class DexLoadReporter implements BaseDexClassLoader.Reporter {
    private static final boolean DEBUG = false;
    private static final DexLoadReporter INSTANCE = new DexLoadReporter();
    private static final String TAG = "DexLoadReporter";
    private final Set<String> mDataDirs = new HashSet();

    private DexLoadReporter() {}

    static DexLoadReporter getInstance() {
        return INSTANCE;
    }

    void registerAppDataDir(String packageName, String dataDir) {
        if (dataDir != null) {
            synchronized (this.mDataDirs) {
                this.mDataDirs.add(dataDir);
            }
        }
    }

    public void report(Map<String, String> classLoaderContextMap) {
        if (classLoaderContextMap.isEmpty()) {
            Slog.wtf(TAG, "Bad call to DexLoadReporter: empty classLoaderContextMap");
        } else {
            notifyPackageManager(classLoaderContextMap);
            registerSecondaryDexForProfiling(classLoaderContextMap.keySet());
        }
    }

    private void notifyPackageManager(Map<String, String> classLoaderContextMap) {
        String packageName = ActivityThread.currentPackageName();
        try {
            ActivityThread.getPackageManager()
                    .notifyDexLoad(
                            packageName,
                            classLoaderContextMap,
                            VMRuntime.getRuntime().vmInstructionSet());
        } catch (RemoteException re) {
            Slog.e(TAG, "Failed to notify PM about dex load for package " + packageName, re);
        }
    }

    private void registerSecondaryDexForProfiling(Set<String> dexPaths) {
        String[] dataDirs;
        if (!SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false)) {
            return;
        }
        synchronized (this.mDataDirs) {
            dataDirs = (String[]) this.mDataDirs.toArray(new String[0]);
        }
        for (String dexPath : dexPaths) {
            registerSecondaryDexForProfiling(dexPath, dataDirs);
        }
    }

    private void registerSecondaryDexForProfiling(String dexPath, String[] dataDirs) {
        if (!isSecondaryDexFile(dexPath, dataDirs)) {
            return;
        }
        File dexPathFile = new File(dexPath);
        File secondaryProfileDir = new File(dexPathFile.getParent(), "oat");
        File secondaryCurProfile =
                new File(secondaryProfileDir, dexPathFile.getName() + ".cur.prof");
        File secondaryRefProfile = new File(secondaryProfileDir, dexPathFile.getName() + ".prof");
        if (!secondaryProfileDir.exists() && !secondaryProfileDir.mkdir()) {
            Slog.e(TAG, "Could not create the profile directory: " + secondaryCurProfile);
            return;
        }
        try {
            secondaryCurProfile.createNewFile();
            VMRuntime.registerAppInfo(
                    ActivityThread.currentPackageName(),
                    secondaryCurProfile.getPath(),
                    secondaryRefProfile.getPath(),
                    new String[] {dexPath},
                    4);
        } catch (IOException ex) {
            Slog.e(
                    TAG,
                    "Failed to create profile for secondary dex "
                            + dexPath
                            + ":"
                            + ex.getMessage());
        }
    }

    private boolean isSecondaryDexFile(String dexPath, String[] dataDirs) {
        for (String dataDir : dataDirs) {
            if (FileUtils.contains(dataDir, dexPath)) {
                return true;
            }
        }
        return false;
    }
}
