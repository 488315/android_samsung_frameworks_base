package com.samsung.android.server.packagefeature;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageFeatureUserChangePersister {
    public static final String ASPECT_RATIO_DIRECTORY;
    public static final String EMBED_ACTIVITY_DIRECTORY;
    public static final String MULTI_DISPLAY_DIRECTORY;
    public static final String PACKAGE_SETTINGS_DIRECTORY;
    public final H mH;
    public int mLoadRequestFlags;
    public final Object mLock = new Object();
    public final List mPackageFeatureUserChanges;
    public final Map mSaveRequestFlagsWithUserId;
    public boolean mThreadStarted;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            synchronized (PackageFeatureUserChangePersister.this.mLock) {
                try {
                    PackageFeatureUserChangePersister packageFeatureUserChangePersister =
                            PackageFeatureUserChangePersister.this;
                    if (packageFeatureUserChangePersister.mThreadStarted) {
                        return;
                    }
                    if (packageFeatureUserChangePersister.mLoadRequestFlags == 0
                            && ((ConcurrentHashMap)
                                            packageFeatureUserChangePersister
                                                    .mSaveRequestFlagsWithUserId)
                                    .isEmpty()) {
                        return;
                    }
                    PackageFeatureUserChangePersister.this.new LazyThread().start();
                    PackageFeatureUserChangePersister.this.mThreadStarted = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final PackageFeatureUserChangePersister sPackageFeatureUserChangePersister =
                new PackageFeatureUserChangePersister();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LazyThread extends Thread {
        public LazyThread() {
            super("PackageFeatureUserChangePersister");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            synchronized (PackageFeatureUserChangePersister.this.mLock) {
                PackageFeatureUserChangePersister.m1230$$Nest$msaveFiles(
                        PackageFeatureUserChangePersister.this);
                PackageFeatureUserChangePersister.m1229$$Nest$mloadFiles(
                        PackageFeatureUserChangePersister.this);
                PackageFeatureUserChangePersister.this.mThreadStarted = false;
            }
        }
    }

    /* renamed from: -$$Nest$mloadFiles, reason: not valid java name */
    public static void m1229$$Nest$mloadFiles(
            PackageFeatureUserChangePersister packageFeatureUserChangePersister) {
        File[] listFiles;
        int i = packageFeatureUserChangePersister.mLoadRequestFlags;
        if (i == 0) {
            return;
        }
        Iterator it =
                ((ArrayList) packageFeatureUserChangePersister.mPackageFeatureUserChanges)
                        .iterator();
        loop0:
        while (true) {
            if (!it.hasNext()) {
                packageFeatureUserChangePersister.mLoadRequestFlags = 0;
                return;
            }
            final PackageFeatureUserChange packageFeatureUserChange =
                    (PackageFeatureUserChange) it.next();
            if ((packageFeatureUserChange.mIdentityFlag & i) != 0
                    && (listFiles = getDirectory(packageFeatureUserChange.mFilePath).listFiles())
                            != null
                    && listFiles.length != 0) {
                boolean z = false;
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        int parseInt = Integer.parseInt(file.getName());
                        File file2 =
                                new File(file.getPath() + "/" + packageFeatureUserChange.mFileName);
                        if (file2.exists()) {
                            try {
                                FileInputStream fileInputStream = new FileInputStream(file2);
                                try {
                                    ObjectInputStream objectInputStream =
                                            new ObjectInputStream(fileInputStream);
                                    try {
                                        Object readObject = objectInputStream.readObject();
                                        if (readObject instanceof ConcurrentHashMap) {
                                            packageFeatureUserChange
                                                    .getChangeValuesAsUser(parseInt)
                                                    .putAll((ConcurrentHashMap) readObject);
                                        }
                                        z = true;
                                        objectInputStream.close();
                                        fileInputStream.close();
                                    } catch (Throwable th) {
                                        try {
                                            objectInputStream.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                        throw th;
                                    }
                                } finally {
                                    try {
                                        break loop0;
                                    } catch (Throwable th3) {
                                    }
                                }
                            } catch (Exception e) {
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                parseInt,
                                                "Failed to loadFile, userId=",
                                                ", flag=0x");
                                m.append(
                                        Integer.toHexString(
                                                packageFeatureUserChange.mIdentityFlag));
                                Slog.w("PackageFeature", m.toString(), e);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                if (z) {
                    packageFeatureUserChangePersister.mH.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PackageFeatureUserChange.this.onLoadFileCompletedAndSystemReady(
                                            false, true);
                                }
                            });
                }
            }
        }
    }

    /* renamed from: -$$Nest$msaveFiles, reason: not valid java name */
    public static void m1230$$Nest$msaveFiles(
            PackageFeatureUserChangePersister packageFeatureUserChangePersister) {
        if (((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId)
                .isEmpty()) {
            return;
        }
        for (Map.Entry entry :
                ((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId)
                        .entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            Iterator it =
                    ((ArrayList) packageFeatureUserChangePersister.mPackageFeatureUserChanges)
                            .iterator();
            while (it.hasNext()) {
                PackageFeatureUserChange packageFeatureUserChange =
                        (PackageFeatureUserChange) it.next();
                if ((packageFeatureUserChange.mIdentityFlag & intValue2) != 0) {
                    try {
                        FileOutputStream fileOutputStream =
                                new FileOutputStream(
                                        getDirectory(
                                                                packageFeatureUserChange.mFilePath
                                                                        + "/"
                                                                        + intValue)
                                                        .getPath()
                                                + "/"
                                                + packageFeatureUserChange.mFileName);
                        try {
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(fileOutputStream);
                            try {
                                objectOutputStream.writeObject(
                                        packageFeatureUserChange.getChangeValuesAsUser(intValue));
                                objectOutputStream.close();
                                fileOutputStream.close();
                            } catch (Throwable th) {
                                try {
                                    objectOutputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } finally {
                        }
                    } catch (Exception e) {
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        intValue, "Failed to saveFile, userId=", ", flag=0x");
                        m.append(Integer.toHexString(packageFeatureUserChange.mIdentityFlag));
                        Slog.w("PackageFeature", m.toString(), e);
                    }
                }
            }
        }
        ((ConcurrentHashMap) packageFeatureUserChangePersister.mSaveRequestFlagsWithUserId).clear();
    }

    static {
        String str = Environment.getDataSystemDirectory().getPath() + "/";
        PACKAGE_SETTINGS_DIRECTORY =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "packagesettings");
        MULTI_DISPLAY_DIRECTORY =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "multidisplay");
        ASPECT_RATIO_DIRECTORY =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "conventionalmode");
        EMBED_ACTIVITY_DIRECTORY =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "embedactivity");
    }

    public PackageFeatureUserChangePersister() {
        H h = new H(DisplayThread.get().getLooper());
        this.mH = h;
        this.mPackageFeatureUserChanges = new ArrayList();
        this.mSaveRequestFlagsWithUserId = new ConcurrentHashMap();
        h.postDelayed(new PackageFeatureUserChangePersister$$ExternalSyntheticLambda0(), 60000L);
    }

    public static void deleteLegacyFile(String str, String str2) {
        File[] listFiles;
        try {
            File file = new File(str);
            if (file.exists() && (listFiles = file.listFiles()) != null && listFiles.length != 0) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        new File(file2.getPath() + "/" + str2).delete();
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static File getDirectory(String str) {
        File file = new File(str);
        if (!file.exists()) {
            if (file.mkdirs()) {
                Slog.d("PackageFeature", "Create directory: " + file);
            } else {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Fail to create directory: ", str, "PackageFeature");
            }
        }
        return file;
    }

    public final void resetFiles(int i, int i2) {
        Iterator it = ((ArrayList) this.mPackageFeatureUserChanges).iterator();
        while (it.hasNext()) {
            PackageFeatureUserChange packageFeatureUserChange =
                    (PackageFeatureUserChange) it.next();
            int i3 = packageFeatureUserChange.mIdentityFlag;
            if ((i2 & i3) != 0) {
                try {
                    if (new File(
                                    packageFeatureUserChange.mFilePath
                                            + "/"
                                            + i
                                            + "/"
                                            + packageFeatureUserChange.mFileName)
                            .delete()) {
                        Slog.d(
                                "PackageFeature",
                                "Succeeded to resetFile, userId="
                                        + i
                                        + ", flag=0x"
                                        + Integer.toHexString(i3));
                    }
                } catch (Exception e) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "Failed to resetFile, userId=", ", flag=0x");
                    m.append(Integer.toHexString(i3));
                    Slog.w("PackageFeature", m.toString(), e);
                }
            }
        }
    }
}
