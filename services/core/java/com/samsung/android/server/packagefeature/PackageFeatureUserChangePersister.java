package com.samsung.android.server.packagefeature;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import com.android.server.DisplayThread;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class PackageFeatureUserChangePersister {
    public static final String CONVENTIONAL_MODE_DIRECTORY;
    public static final String DATA_SYSTEM_DIRECTORY;
    public static final String EMBED_ACTIVITY_DIRECTORY;
    public static final String MULTI_DISPLAY_DIRECTORY;
    public static final String PACKAGE_SETTINGS_DIRECTORY;
    public static final String SPLIT_ACTIVITY_DIRECTORY;

    /* renamed from: mH */
    public final HandlerC3183H f1777mH;
    public int mLoadRequestFlags;
    public final Object mLock;
    public final List mPackageFeatureUserChanges;
    public final Map mSaveRequestFlagsWithUserId;
    public boolean mThreadStarted;

    public abstract class LazyHolder {
        public static final PackageFeatureUserChangePersister sPackageFeatureUserChangePersister = new PackageFeatureUserChangePersister();
    }

    static {
        String str = Environment.getDataSystemDirectory().getPath() + "/";
        DATA_SYSTEM_DIRECTORY = str;
        PACKAGE_SETTINGS_DIRECTORY = str + "packagesettings";
        MULTI_DISPLAY_DIRECTORY = str + "multidisplay";
        CONVENTIONAL_MODE_DIRECTORY = str + "conventionalmode";
        SPLIT_ACTIVITY_DIRECTORY = str + "splitactivity";
        EMBED_ACTIVITY_DIRECTORY = str + "embedactivity";
    }

    public static PackageFeatureUserChangePersister getInstance() {
        return LazyHolder.sPackageFeatureUserChangePersister;
    }

    public PackageFeatureUserChangePersister() {
        this.mLock = new Object();
        this.f1777mH = new HandlerC3183H(DisplayThread.get().getLooper());
        this.mPackageFeatureUserChanges = new ArrayList();
        this.mSaveRequestFlagsWithUserId = new ConcurrentHashMap();
    }

    public void addUserChange(PackageFeatureUserChange packageFeatureUserChange) {
        synchronized (this.mLock) {
            this.mPackageFeatureUserChanges.add(packageFeatureUserChange);
            requestToLoad(packageFeatureUserChange.mIdentityFlag);
        }
    }

    public void requestToLoad(int i) {
        synchronized (this.mLock) {
            this.mLoadRequestFlags = i | this.mLoadRequestFlags;
            scheduleRequest(1);
        }
    }

    public void requestToSave(int i, int i2) {
        synchronized (this.mLock) {
            Integer num = (Integer) this.mSaveRequestFlagsWithUserId.get(Integer.valueOf(i));
            if (num != null) {
                i2 |= num.intValue();
            }
            this.mSaveRequestFlagsWithUserId.put(Integer.valueOf(i), Integer.valueOf(i2));
            scheduleRequest(2);
        }
    }

    public void requestToReset(int i, int i2) {
        synchronized (this.mLock) {
            this.f1777mH.removeMessages(1);
            this.mLoadRequestFlags = 0;
            this.mSaveRequestFlagsWithUserId.clear();
            resetFiles(i, i2);
        }
    }

    public final void scheduleRequest(int i) {
        if (this.f1777mH.hasMessages(1)) {
            return;
        }
        if (i == 1) {
            this.f1777mH.sendEmptyMessageDelayed(1, 500L);
        } else if (i == 2) {
            this.f1777mH.sendEmptyMessageDelayed(1, 2000L);
        }
    }

    /* renamed from: com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister$H */
    public class HandlerC3183H extends Handler {
        public HandlerC3183H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            synchronized (PackageFeatureUserChangePersister.this.mLock) {
                if (PackageFeatureUserChangePersister.this.mThreadStarted) {
                    return;
                }
                if (PackageFeatureUserChangePersister.this.mLoadRequestFlags == 0 && PackageFeatureUserChangePersister.this.mSaveRequestFlagsWithUserId.isEmpty()) {
                    return;
                }
                PackageFeatureUserChangePersister.this.new LazyThread().start();
                PackageFeatureUserChangePersister.this.mThreadStarted = true;
            }
        }
    }

    public class LazyThread extends Thread {
        public LazyThread() {
            super("PackageFeatureUserChangePersister");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (PackageFeatureUserChangePersister.this.mLock) {
                PackageFeatureUserChangePersister.this.saveFiles();
                PackageFeatureUserChangePersister.this.loadFiles();
                PackageFeatureUserChangePersister.this.mThreadStarted = false;
            }
        }
    }

    public final void loadFiles() {
        int i = this.mLoadRequestFlags;
        if (i == 0) {
            return;
        }
        for (PackageFeatureUserChange packageFeatureUserChange : this.mPackageFeatureUserChanges) {
            if ((packageFeatureUserChange.mIdentityFlag & i) != 0) {
                loadFile(packageFeatureUserChange);
            }
        }
        this.mLoadRequestFlags = 0;
    }

    public final void loadFile(PackageFeatureUserChange packageFeatureUserChange) {
        File[] listFiles;
        if (packageFeatureUserChange == null || (listFiles = getDirectory(packageFeatureUserChange.mFilePath).listFiles()) == null || listFiles.length == 0) {
            return;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                int parseInt = Integer.parseInt(file.getName());
                File file2 = new File(file.getPath() + "/" + packageFeatureUserChange.mFileName);
                if (!file2.exists()) {
                    if (CoreRune.SAFE_DEBUG) {
                        Slog.d("PackageFeature", "Failed to loadFile, userId=" + parseInt + ", flag=0x" + Integer.toHexString(packageFeatureUserChange.mIdentityFlag) + ", file is not exists");
                    }
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file2);
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                            try {
                                Object readObject = objectInputStream.readObject();
                                if (readObject instanceof ConcurrentHashMap) {
                                    packageFeatureUserChange.getChangeValuesAsUser(parseInt).putAll((ConcurrentHashMap) readObject);
                                }
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
                        } catch (Throwable th3) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th4) {
                                th3.addSuppressed(th4);
                            }
                            throw th3;
                        }
                    } catch (Exception e) {
                        Slog.w("PackageFeature", "Failed to loadFile, userId=" + parseInt + ", flag=0x" + Integer.toHexString(packageFeatureUserChange.mIdentityFlag), e);
                    }
                }
            }
        }
    }

    public final void saveFiles() {
        if (this.mSaveRequestFlagsWithUserId.isEmpty()) {
            return;
        }
        for (Map.Entry entry : this.mSaveRequestFlagsWithUserId.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            for (PackageFeatureUserChange packageFeatureUserChange : this.mPackageFeatureUserChanges) {
                if ((packageFeatureUserChange.mIdentityFlag & intValue2) != 0) {
                    saveFile(intValue, packageFeatureUserChange);
                }
            }
        }
        this.mSaveRequestFlagsWithUserId.clear();
    }

    public final void saveFile(int i, PackageFeatureUserChange packageFeatureUserChange) {
        if (packageFeatureUserChange == null) {
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getDirectory(packageFeatureUserChange.mFilePath + "/" + i).getPath() + "/" + packageFeatureUserChange.mFileName);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream.writeObject(packageFeatureUserChange.getChangeValuesAsUser(i));
                    objectOutputStream.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.w("PackageFeature", "Failed to saveFile, userId=" + i + ", flag=0x" + Integer.toHexString(packageFeatureUserChange.mIdentityFlag), e);
        }
    }

    public final void resetFiles(int i, int i2) {
        for (PackageFeatureUserChange packageFeatureUserChange : this.mPackageFeatureUserChanges) {
            if ((packageFeatureUserChange.mIdentityFlag & i2) != 0) {
                resetFile(i, packageFeatureUserChange);
            }
        }
    }

    public final void resetFile(int i, PackageFeatureUserChange packageFeatureUserChange) {
        if (packageFeatureUserChange == null) {
            return;
        }
        try {
            if (new File(packageFeatureUserChange.mFilePath + "/" + i + "/" + packageFeatureUserChange.mFileName).delete()) {
                Slog.d("PackageFeature", "Succeeded to resetFile, userId=" + i + ", flag=0x" + Integer.toHexString(packageFeatureUserChange.mIdentityFlag));
            }
        } catch (Exception e) {
            Slog.w("PackageFeature", "Failed to resetFile, userId=" + i + ", flag=0x" + Integer.toHexString(packageFeatureUserChange.mIdentityFlag), e);
        }
    }

    public static File getDirectory(String str) {
        File file = new File(str);
        if (!file.exists()) {
            if (file.mkdirs()) {
                Slog.d("PackageFeature", "Create directory: " + file);
            } else {
                Slog.w("PackageFeature", "Fail to create directory: " + str);
            }
        }
        return file;
    }
}
