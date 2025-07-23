package com.android.internal.content;

import android.content.ContentResolver;
import android.os.Environment;
import android.os.incremental.IncrementalManager;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class F2fsUtils {
    private static final String COMPRESSION_FEATURE = "compression";
    private static final boolean DEBUG_F2FS = false;
    private static final String HEIMDALLFS_FEATURE = "sec_heimdallfs";
    private static final String RELOCATION_FEATURE = "sec_dnode_relocation";
    private static final String TAG = "F2fsUtils";
    private static boolean sKernelCompressionAvailable;
    private static final boolean sUserDataCompressionAvailable;
    private static final File sKernelFeatures = new File("/sys/fs/f2fs/features");
    private static final File sUserDataFeatures = new File("/dev/sys/fs/by-name/userdata/features");
    private static final File sDataDirectory = Environment.getDataDirectory();

    private static native long nativeReleaseCompressedBlocks(String str);

    static {
        sKernelCompressionAvailable = isFeatureEnabledInKernel(COMPRESSION_FEATURE);
        if (isFeatureEnabledInKernel(RELOCATION_FEATURE) || isFeatureEnabledInKernel(HEIMDALLFS_FEATURE)) {
            sKernelCompressionAvailable = false;
        }
        sUserDataCompressionAvailable = isCompressionEnabledOnUserData();
    }

    public static void releaseCompressedBlocks(ContentResolver contentResolver, File file) {
        File[] filesToRelease;
        if (!sKernelCompressionAvailable || !sUserDataCompressionAvailable || Settings.Secure.getInt(contentResolver, Settings.Secure.RELEASE_COMPRESS_BLOCKS_ON_INSTALL, 1) == 0 || !isCompressionAllowed(file) || (filesToRelease = getFilesToRelease(file)) == null || filesToRelease.length == 0) {
            return;
        }
        for (int length = filesToRelease.length - 1; length >= 0; length--) {
            nativeReleaseCompressedBlocks(filesToRelease[length].getAbsolutePath());
        }
    }

    private static boolean isCompressionAllowed(File file) {
        try {
            String canonicalPath = file.getCanonicalPath();
            return !IncrementalManager.isIncrementalPath(canonicalPath) && isChild(sDataDirectory, canonicalPath);
        } catch (IOException unused) {
            return false;
        }
    }

    private static boolean isChild(File file, String str) {
        try {
            File canonicalFile = file.getCanonicalFile();
            for (File canonicalFile2 = new File(str).getCanonicalFile(); canonicalFile2 != null; canonicalFile2 = canonicalFile2.getParentFile()) {
                if (canonicalFile.equals(canonicalFile2)) {
                    return true;
                }
            }
        } catch (IOException unused) {
        }
        return false;
    }

    private static boolean isFeatureEnabledInKernel(String str) {
        File[] listFiles = sKernelFeatures.listFiles();
        if (listFiles != null && listFiles.length != 0) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                if (str.equals(listFiles[length].getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCompressionEnabledOnUserData() {
        File file = sUserDataFeatures;
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                List<String> readAllLines = Files.readAllLines(file.toPath());
                if (readAllLines != null && readAllLines.size() <= 1 && !TextUtils.isEmpty(readAllLines.get(0))) {
                    String[] split = readAllLines.get(0).split(",");
                    for (int length = split.length - 1; length >= 0; length--) {
                        if (COMPRESSION_FEATURE.equals(split[length].trim())) {
                            return true;
                        }
                    }
                }
            } catch (IOException unused) {
            }
        }
        return false;
    }

    private static List<File> getFilesRecursive(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                arrayList.addAll(getFilesRecursive(file2));
            } else if (file2.isFile()) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    private static File[] getFilesToRelease(File file) {
        List<File> filesRecursive = getFilesRecursive(file);
        if (filesRecursive == null) {
            if (file.isFile()) {
                return new File[]{file};
            }
            return null;
        }
        if (filesRecursive.size() == 0) {
            return null;
        }
        return (File[]) filesRecursive.toArray(new File[filesRecursive.size()]);
    }
}
