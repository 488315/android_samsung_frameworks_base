package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;

import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreEncryptor;
import com.samsung.android.server.util.CoreLogger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureGroupDataUtilWithEncryption {
    public static final String DIR_PATH;
    public static final String LEGACY_DIR_PATH;
    public final String mCacheFilePathName;
    public BiConsumer mConsumerForFailed;
    public final Context mContext;
    public final CoreEncryptor mEncryptor;
    public final PackageFeatureGroup mGroup;
    public final CoreLogger mLogger;
    public int mRetryByFailed = 0;

    static {
        String path = Environment.getDataDirectory().getPath();
        LEGACY_DIR_PATH =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                        path, "/system/packageconfiguration/");
        DIR_PATH =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                        path, "/system/packagefeature_cache/");
    }

    public PackageFeatureGroupDataUtilWithEncryption(
            Context context, CoreLogger coreLogger, PackageFeatureGroup packageFeatureGroup) {
        this.mContext = context;
        this.mLogger = coreLogger;
        this.mGroup = packageFeatureGroup;
        this.mCacheFilePathName = DIR_PATH + "/" + packageFeatureGroup.mName;
        ConcurrentHashMap concurrentHashMap = CoreEncryptor.sCoreEncryptor;
        CoreEncryptor coreEncryptor = (CoreEncryptor) concurrentHashMap.get(context);
        if (coreEncryptor == null) {
            coreEncryptor = new CoreEncryptor(context);
            concurrentHashMap.put(context, coreEncryptor);
        }
        this.mEncryptor = coreEncryptor;
    }

    public static String deleteCacheFiles(String str) {
        StringBuilder sb = null;
        try {
            File file = new File(str);
            if (!file.exists()) {
                return "directory does not exist";
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                StringBuilder sb2 = new StringBuilder();
                try {
                    for (File file2 : listFiles) {
                        sb2.append(file2.getName());
                        if (file2.delete()) {
                            sb2.append("(succeeded) ");
                        } else {
                            sb2.append("(failed) ");
                        }
                    }
                    return sb2.toString();
                } catch (Throwable unused) {
                    sb = sb2;
                    StringBuilder sb3 = new StringBuilder("deleteCacheFiles: Error!!! ");
                    sb3.append((Object) sb);
                    return sb3.toString() != null ? sb.toString() : "";
                }
            }
            return "files does not exist";
        } catch (Throwable unused2) {
        }
    }

    public final Object loadFromFileInputStream(FileInputStream fileInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (!this.mEncryptor.decrypt(fileInputStream, byteArrayOutputStream)) {
                logFailed("decrypt", null);
                throw new Exception("decrypt");
            }
            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                try {
                    Object readObject = objectInputStream.readObject();
                    objectInputStream.close();
                    byteArrayInputStream.close();
                    byteArrayOutputStream.close();
                    return readObject;
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final PackageFeatureGroupData loadFromReader(
            int i, Reader reader, String str, boolean z) {
        try {
            PackageFeatureGroupDataUtil$GroupDataReader
                    packageFeatureGroupDataUtil$GroupDataReader =
                            new PackageFeatureGroupDataUtil$GroupDataReader();
            boolean z2 = true;
            packageFeatureGroupDataUtil$GroupDataReader.mLineCount = 1;
            packageFeatureGroupDataUtil$GroupDataReader.mBufferedReader =
                    new BufferedReader(reader);
            try {
                packageFeatureGroupDataUtil$GroupDataReader.open(z);
                int i2 = packageFeatureGroupDataUtil$GroupDataReader.mVersion;
                CoreLogger coreLogger = this.mLogger;
                PackageFeatureGroup packageFeatureGroup = this.mGroup;
                if ((z || i != i2) && i >= i2) {
                    coreLogger.log(
                            3,
                            "Skip "
                                    + str
                                    + ", GroupName="
                                    + packageFeatureGroup.mName
                                    + ", currentVersion="
                                    + i
                                    + ", readerVersion="
                                    + i2,
                            null);
                    packageFeatureGroupDataUtil$GroupDataReader.close();
                    return null;
                }
                try {
                    PackageFeatureGroupData packageFeatureGroupDataInner =
                            packageFeatureGroupDataUtil$GroupDataReader
                                    .getPackageFeatureGroupDataInner();
                    String str2 =
                            str
                                    + "("
                                    + i2
                                    + ","
                                    + packageFeatureGroupDataUtil$GroupDataReader.mLineCount
                                    + ")";
                    if (packageFeatureGroupDataInner == null) {
                        logFailed(str2, null);
                        if (this.mConsumerForFailed != null) {
                            int i3 = this.mRetryByFailed + 1;
                            this.mRetryByFailed = i3;
                            if (i3 > 3) {
                                z2 = false;
                            }
                            coreLogger.log(
                                    3,
                                    "Failed groupName="
                                            + packageFeatureGroup.mName
                                            + ", retry="
                                            + z2
                                            + "("
                                            + this.mRetryByFailed
                                            + "), fromRawResource="
                                            + z,
                                    null);
                            if (z2) {
                                this.mConsumerForFailed.accept(
                                        packageFeatureGroup.mName, 10800000L);
                            }
                        }
                    } else {
                        this.mRetryByFailed = 0;
                        logSucceeded(str2);
                    }
                    packageFeatureGroupDataUtil$GroupDataReader.close();
                    return packageFeatureGroupDataInner;
                } catch (Throwable th) {
                    packageFeatureGroupDataUtil$GroupDataReader.log(6, "get: " + th, true);
                    throw th;
                }
            } finally {
            }
        } catch (Throwable th2) {
            logFailed(str, th2);
            return null;
        }
    }

    public final void logFailed(String str, Throwable th) {
        int i = th != null ? 6 : 5;
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Failed to ", str, ", GroupName=");
        m.append(this.mGroup.mName);
        this.mLogger.log(i, m.toString(), th);
    }

    public final void logSucceeded(String str) {
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m("Succeeded to ", str, ", GroupName=");
        m.append(this.mGroup.mName);
        this.mLogger.log(3, m.toString(), null);
    }

    public final void saveToCacheFile(PackageFeatureGroupData packageFeatureGroupData) {
        File file = new File(DIR_PATH);
        if (!file.exists() && !file.mkdirs()) {
            this.mLogger.log(6, "Failed to create directory.", null);
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mCacheFilePathName);
            try {
                saveToFileOutputStream(packageFeatureGroupData, fileOutputStream);
                logSucceeded("saveToCacheFile");
                fileOutputStream.close();
            } finally {
            }
        } catch (Throwable th) {
            logFailed("saveToCacheFile", th);
        }
    }

    public final void saveToFileOutputStream(
            PackageFeatureGroupData packageFeatureGroupData, FileOutputStream fileOutputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(packageFeatureGroupData);
                objectOutputStream.flush();
                ByteArrayInputStream byteArrayInputStream =
                        new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                try {
                    if (!this.mEncryptor.encrypt(byteArrayInputStream, fileOutputStream)) {
                        logFailed("encrypt", null);
                        throw new Exception("encrypt");
                    }
                    byteArrayInputStream.close();
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
