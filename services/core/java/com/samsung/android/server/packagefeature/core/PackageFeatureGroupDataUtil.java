package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.os.Environment;
import com.android.server.clipboard.ClipboardService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreEncryptor;
import com.samsung.android.server.util.CoreLogger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public abstract class PackageFeatureGroupDataUtil {
    public static final String DIR_PATH;
    public static final String LEGACY_DIR_PATH;
    public static final long RETRY_TIMES;
    public static final String ROOT_PATH;
    public final String mCacheFilePathName;
    public BiConsumer mConsumerForFailed;
    public final Context mContext;
    public final PackageFeatureGroup mGroup;
    public final CoreLogger mLogger;
    public int mRetryByFailed = 0;

    public abstract Object loadFromFileInputStream(FileInputStream fileInputStream);

    public abstract void saveToFileOutputStream(PackageFeatureGroupData packageFeatureGroupData, FileOutputStream fileOutputStream);

    static {
        String path = Environment.getDataDirectory().getPath();
        ROOT_PATH = path;
        LEGACY_DIR_PATH = path + "/system/packageconfiguration/";
        DIR_PATH = path + "/system/packagefeature_cache/";
        RETRY_TIMES = (CoreRune.SAFE_DEBUG ? 60000L : ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) * 3;
    }

    public PackageFeatureGroupDataUtil(Context context, CoreLogger coreLogger, PackageFeatureGroup packageFeatureGroup) {
        this.mContext = context;
        this.mLogger = coreLogger;
        this.mGroup = packageFeatureGroup;
        this.mCacheFilePathName = DIR_PATH + "/" + packageFeatureGroup.mName;
    }

    public void setConsumerForFailed(BiConsumer biConsumer) {
        this.mConsumerForFailed = biConsumer;
    }

    public void saveToCacheFile(PackageFeatureGroupData packageFeatureGroupData) {
        File file = new File(DIR_PATH);
        if (!file.exists() && !file.mkdirs()) {
            this.mLogger.log(6, "Failed to create directory.");
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

    public PackageFeatureGroupData loadFromCacheFile() {
        File file = new File(DIR_PATH);
        File file2 = new File(this.mCacheFilePathName);
        if (file.exists() && file2.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file2);
                try {
                    Object loadFromFileInputStream = loadFromFileInputStream(fileInputStream);
                    if (loadFromFileInputStream instanceof PackageFeatureGroupData) {
                        logSucceeded("loadFromCacheFile");
                        PackageFeatureGroupData packageFeatureGroupData = (PackageFeatureGroupData) loadFromFileInputStream;
                        fileInputStream.close();
                        return packageFeatureGroupData;
                    }
                    logFailed("loadFromCacheFile", null);
                    fileInputStream.close();
                } finally {
                }
            } catch (Throwable th) {
                logFailed("loadFromCacheFile", th);
            }
        }
        return null;
    }

    public PackageFeatureGroupData loadFromRawResource(int i) {
        if (this.mGroup.mRawResId == 0) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.mContext.getResources().openRawResource(this.mGroup.mRawResId));
            try {
                PackageFeatureGroupData loadFromReader = loadFromReader(i, inputStreamReader, "loadFromRawResource", true);
                inputStreamReader.close();
                return loadFromReader;
            } finally {
            }
        } catch (Throwable th) {
            logFailed("loadFromRawResource", th);
            return null;
        }
    }

    public PackageFeatureGroupData loadFromScpm(int i, Reader reader) {
        return loadFromReader(i, reader, "loadFromScpm", false);
    }

    public PackageFeatureGroupData loadFromReader(int i, Reader reader, String str, boolean z) {
        try {
            GroupDataReader groupDataReader = new GroupDataReader(reader);
            try {
                groupDataReader.open(z);
                int version = groupDataReader.getVersion();
                if ((z || i != version) && i >= version) {
                    this.mLogger.log(3, "Skip " + str + ", GroupName=" + this.mGroup.mName + ", currentVersion=" + i + ", readerVersion=" + version);
                    groupDataReader.close();
                    return null;
                }
                PackageFeatureGroupData packageFeatureGroupData = groupDataReader.getPackageFeatureGroupData();
                String str2 = str + "(" + version + "," + groupDataReader.getLineCount() + ")";
                if (packageFeatureGroupData == null) {
                    logFailed(str2, null);
                    if (this.mConsumerForFailed != null) {
                        int i2 = this.mRetryByFailed + 1;
                        this.mRetryByFailed = i2;
                        boolean z2 = i2 <= 3;
                        this.mLogger.log(3, "Failed groupName=" + this.mGroup.mName + ", retry=" + z2 + "(" + this.mRetryByFailed + "), fromRawResource=" + z);
                        if (z2) {
                            this.mConsumerForFailed.accept(this.mGroup.mName, Long.valueOf(RETRY_TIMES));
                        }
                    }
                } else {
                    this.mRetryByFailed = 0;
                    logSucceeded(str2);
                }
                groupDataReader.close();
                return packageFeatureGroupData;
            } finally {
            }
        } catch (Throwable th) {
            logFailed(str, th);
            return null;
        }
    }

    public void logSucceeded(String str) {
        this.mLogger.log(3, "Succeeded to " + str + ", GroupName=" + this.mGroup.mName);
    }

    public void logFailed(String str, Throwable th) {
        this.mLogger.log(th != null ? 6 : 5, "Failed to " + str + ", GroupName=" + this.mGroup.mName, th);
    }

    public static String deleteLegacyFiles() {
        return deleteCacheFiles(LEGACY_DIR_PATH);
    }

    public static String deleteCacheFiles() {
        return deleteCacheFiles(DIR_PATH);
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
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("deleteCacheFiles: Error!!! ");
                    sb3.append((Object) sb);
                    return sb3.toString() != null ? sb.toString() : "";
                }
            }
            return "files does not exist";
        } catch (Throwable unused2) {
        }
    }

    public class GroupDataReader implements AutoCloseable {
        public static final boolean SAFE_LOGGABLE;
        public BufferedReader mBufferedReader;
        public CoreLogger mErrorLogger;
        public PackageFeatureGroupData mGroupData;
        public boolean mIsBase64PackageName;
        public int mLineCount = 1;
        public String mName;
        public int mVersion;

        static {
            SAFE_LOGGABLE = CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH;
        }

        public GroupDataReader(Reader reader) {
            this.mBufferedReader = new BufferedReader(reader);
        }

        public void open(boolean z) {
            String str = null;
            try {
                str = this.mBufferedReader.readLine();
                String[] split = str.split(",");
                this.mName = split[0];
                this.mVersion = Integer.valueOf(split[1]).intValue();
                this.mIsBase64PackageName = z;
                if (this.mName != null) {
                } else {
                    throw new IllegalStateException("It hasn't been opened yet.");
                }
            } catch (Throwable th) {
                log(6, "open: " + th + ", " + str, true);
                throw th;
            }
        }

        public PackageFeatureGroupData getPackageFeatureGroupData() {
            try {
                return getPackageFeatureGroupDataInner();
            } catch (Throwable th) {
                this.log(6, "get: " + th, true);
                throw th;
            }
        }

        public PackageFeatureGroupData getPackageFeatureGroupDataInner() {
            this.mGroupData = new PackageFeatureGroupData(this.mVersion);
            boolean z = this.mIsBase64PackageName || this.mVersion <= 2023080100;
            while (true) {
                String readLine = this.mBufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                this.mLineCount++;
                String[] split = readLine.split(",");
                String str = split[0];
                if (!z && "999999".equals(str)) {
                    z = true;
                    break;
                }
                int length = split.length;
                if (length <= 1) {
                    log(6, "get: " + readLine, SAFE_LOGGABLE);
                    return null;
                }
                String str2 = split[1];
                if (this.mIsBase64PackageName) {
                    str2 = CoreEncryptor.decodeBase64String(str2);
                }
                String str3 = length > 2 ? split[2] : PackageFeatureData.EMPTY_STRING;
                String str4 = length > 3 ? split[3] : PackageFeatureData.EMPTY_STRING;
                if (PackageFeatureData.EMPTY_STRING.equals(str3)) {
                    log(3, "get: " + readLine, SAFE_LOGGABLE);
                }
                this.mGroupData.putPackageFeature(str3, str, str2, str4);
            }
            if (z) {
                return this.mGroupData;
            }
            log(6, "Invalid!", true);
            return null;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            try {
                if (this.mErrorLogger != null) {
                    log(3, "line: " + this.mLineCount, true);
                    this.mErrorLogger.toDumpCriticalInfo();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            this.mGroupData = null;
            BufferedReader bufferedReader = this.mBufferedReader;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } finally {
                    this.mBufferedReader = null;
                }
            }
        }

        public int getVersion() {
            return this.mVersion;
        }

        public int getLineCount() {
            return this.mLineCount;
        }

        public final void log(int i, String str, boolean z) {
            if (this.mErrorLogger == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("PackageFeature_");
                sb.append(getEncryptionIfNeeded(this.mName + "_" + this.mVersion + "_" + this.mIsBase64PackageName, z));
                this.mErrorLogger = CoreLogger.getBuilder().setDumpTitle(sb.toString()).setBufferSize(20).setBufferOverflowAllowed(false).setUseTimeline(false).build();
            }
            this.mErrorLogger.log(i, getEncryptionIfNeeded(str, z));
        }

        public final String getEncryptionIfNeeded(String str, boolean z) {
            return z ? str : CoreEncryptor.encodeBase64String(str);
        }
    }
}
