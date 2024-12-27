package com.samsung.android.lock;

import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Slog;

import com.android.internal.security.VerityUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* loaded from: classes6.dex */
public final class SPBnRManager {
    private static final String PASSWORD_DATA_NAME = "pwd";
    private static final String PASSWORD_METRICS_NAME = "metrics";
    private static final String SECDISCARDABLE_NAME = "secdis";
    private static final String SPBLOB_BACKUP_DIRECTORY = "/data/sec_backup_de/";
    private static final String SP_BLOB_NAME = "spblob";
    private static final String SP_HANDLE_NAME = "handle";
    private static final String SYNTHETIC_PASSWORD_DIRECTORY = "spblob/";
    private static final String TAG = "SPBnRManager";
    private static final String WEAVER_SLOT_NAME = "weaver";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static String[] BNR_LIST = null;
    private static BnRMode mBnRMode = BnRMode.FsVerity;
    private static List<BnRData> sBnRManagedFiles = new LinkedList();
    private static Queue<BnRData> sRemoveFiles = new LinkedList();

    public static void init(boolean isWeaver) {
        BNR_LIST =
                new String[] {
                    SP_BLOB_NAME,
                    PASSWORD_DATA_NAME,
                    PASSWORD_METRICS_NAME,
                    isWeaver ? WEAVER_SLOT_NAME : SECDISCARDABLE_NAME
                };
    }

    public static void setProtectorIdForBackup(int userId, long curPid, long bakPid) {
        ensureSPBnRDirectoryForUser(userId);
        clearManagedFiles();
        addManagedFile(userId, 0L, SP_HANDLE_NAME);
        if (curPid != 0) {
            addManagedFilesByProtectorId(userId, curPid);
            if (bakPid != 0) {
                addManagedFilesByProtectorId(userId, bakPid);
            }
        }
    }

    static class BnRData {
        String mFileName;
        String mName;
        long mProtectorId;
        int mUserId;

        BnRData() {}

        public int getUserId() {
            return this.mUserId;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public static BnRData create(int userId, long protectorId, String name) {
            BnRData result = new BnRData();
            result.mProtectorId = protectorId;
            result.mUserId = userId;
            result.mName = name;
            result.mFileName =
                    TextUtils.formatSimple(
                            "%016x.%s", Long.valueOf(result.mProtectorId), result.mName);
            return result;
        }
    }

    private enum BnRMode {
        None(0),
        Copy(1),
        FsVerity(2);

        private int mode;

        BnRMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return this.mode;
        }
    }

    public static void resetMode() {
        if (LsUtil.isDevBuild()) {
            int mode = SystemProperties.getInt("persist.lock.BnR", 5);
            switch (mode) {
                case 0:
                    mBnRMode = BnRMode.None;
                    break;
                case 1:
                    mBnRMode = BnRMode.Copy;
                    break;
                case 2:
                    mBnRMode = BnRMode.FsVerity;
                    break;
            }
            return;
        }
        if (DEBUG) {
            Slog.d(TAG, "Current mode is " + mBnRMode);
        }
    }

    private static void addManagedFile(int userId, long protectorId, String name) {
        if (mBnRMode == BnRMode.None) {
            Slog.d(TAG, "addManagedFile skipped. mode is " + mBnRMode);
            return;
        }
        BnRData data = BnRData.create(userId, protectorId, name);
        sBnRManagedFiles.add(data);
        if (DEBUG) {
            Slog.d(TAG, TextUtils.formatSimple("Added [%s] for BnR", data.getFileName()));
        }
    }

    private static void addManagedFilesByProtectorId(int userId, long protectorId) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "addManagedFilesByProtectorId skipped. mode is " + mBnRMode);
            }
        } else if (BNR_LIST != null) {
            for (int i = 0; i < BNR_LIST.length; i++) {
                addManagedFile(userId, protectorId, BNR_LIST[i]);
            }
        }
    }

    private static void clearManagedFiles() {
        sBnRManagedFiles.clear();
    }

    public static boolean checkIntegrity() {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "checkIntegrity not support in " + mBnRMode);
            }
            return true;
        }
        if (sBnRManagedFiles.isEmpty()) {
            Slog.e(TAG, "checkIntegrity failed! list is empty!");
            return false;
        }
        if (DEBUG) {
            Slog.d(TAG, "checkIntegrity start!");
        }
        List<BnRData> backupList = new LinkedList<>();
        List<BnRData> restoreList = new LinkedList<>();
        for (int i = 0; i < sBnRManagedFiles.size(); i++) {
            BnRData data = sBnRManagedFiles.get(i);
            if (data == null || TextUtils.isEmpty(data.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                String filename = data.getFileName();
                File orgFile =
                        new File(getSyntheticPasswordDirectoryForUser(data.getUserId()), filename);
                File bakFile = new File(getBackupDirectoryForUser(data.getUserId()), filename);
                if (checkValidState(orgFile)) {
                    if (checkValidState(bakFile)) {
                        if (DEBUG) {
                            Slog.d(
                                    TAG,
                                    TextUtils.formatSimple("[%s] exists on both sides.", filename));
                        }
                    } else {
                        backupList.add(data);
                        Slog.e(
                                TAG,
                                TextUtils.formatSimple("[%s] does not exist in bak!", filename));
                    }
                } else if (checkValidState(bakFile)) {
                    restoreList.add(data);
                    Slog.e(TAG, TextUtils.formatSimple("[%s] does not exist in org!", filename));
                } else if (DEBUG) {
                    Slog.d(
                            TAG,
                            TextUtils.formatSimple("[%s] does not exist on both sides.", filename));
                }
            }
        }
        boolean success = backupList.isEmpty() ? true : !startBackuplist(backupList);
        if (!restoreList.isEmpty()) {
            boolean success2 = true ^ startRestorelist(restoreList);
            return success2;
        }
        return success;
    }

    private static boolean checkValidState(File file) {
        byte[] data;
        if (!file.exists()) {
            return false;
        }
        if (mBnRMode == BnRMode.None || mBnRMode == BnRMode.Copy) {
            return true;
        }
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            try {
                data = new byte[(int) raf.length()];
                raf.readFully(data, 0, data.length);
                raf.close();
                raf.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e(TAG, "checkValidState(), Cannot read file " + e);
            data = null;
        }
        return !ArrayUtils.isEmpty(data);
    }

    public static boolean startBackup() {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "startBackup skipped. mode is " + mBnRMode);
                return true;
            }
            return true;
        }
        if (sBnRManagedFiles.isEmpty()) {
            Slog.e(TAG, "startBackup failed! list is empty!");
            return false;
        }
        return startBackuplist(sBnRManagedFiles);
    }

    private static boolean startBackuplist(List<BnRData> files) {
        if (files.isEmpty()) {
            Slog.e(TAG, "list is empty! check backup list first!");
            return false;
        }
        int count = 0;
        int size = files.size();
        for (int i = 0; i < size; i++) {
            BnRData data = files.get(i);
            if (data == null || TextUtils.isEmpty(data.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                File srcFile =
                        new File(
                                getSyntheticPasswordDirectoryForUser(data.getUserId()),
                                data.getFileName());
                File destFile =
                        new File(getBackupDirectoryForUser(data.getUserId()), data.getFileName());
                if (!srcFile.exists()) {
                    Slog.w(TAG, TextUtils.formatSimple("[%s] is not exist!", srcFile));
                } else {
                    if (destFile.exists()) {
                        Slog.w(
                                TAG,
                                TextUtils.formatSimple(
                                        "[%s] is alread exist! try to overwrite!", srcFile));
                    }
                    if (!FileUtils.copyFile(srcFile, destFile)) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] copy failed!", srcFile));
                    } else {
                        if (DEBUG) {
                            Slog.d(TAG, TextUtils.formatSimple("[%s] copy success!", srcFile));
                        }
                        if (mBnRMode == BnRMode.FsVerity) {
                            setUpFsVerity(srcFile);
                            setUpFsVerity(destFile);
                        }
                        count++;
                    }
                }
            }
        }
        if (count <= 0) {
            return false;
        }
        LsLog.restore(
                TextUtils.formatSimple(
                        "SPblobBNR, %d/%d files Backuped!",
                        Integer.valueOf(count), Integer.valueOf(size)));
        return true;
    }

    public static boolean startRestore() {
        if (mBnRMode == BnRMode.None) {
            Slog.d(TAG, "startRestore skipped. mode is " + mBnRMode);
            return true;
        }
        if (sBnRManagedFiles.isEmpty()) {
            Slog.e(TAG, "startRestore failed! list is empty!");
            return false;
        }
        return startRestorelist(sBnRManagedFiles);
    }

    private static boolean startRestorelist(List<BnRData> files) {
        if (files.isEmpty()) {
            if (DEBUG) {
                Slog.e(TAG, "list is empty! check restore list first!");
            }
            return false;
        }
        int count = 0;
        int size = files.size();
        for (int i = 0; i < size; i++) {
            BnRData data = files.get(i);
            if (data == null || TextUtils.isEmpty(data.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                File srcFile =
                        new File(getBackupDirectoryForUser(data.getUserId()), data.getFileName());
                File destFile =
                        new File(
                                getSyntheticPasswordDirectoryForUser(data.getUserId()),
                                data.getFileName());
                if (!srcFile.exists()) {
                    Slog.w(TAG, TextUtils.formatSimple("[%s] is not exist!", srcFile));
                } else {
                    if (destFile.exists()) {
                        Slog.w(
                                TAG,
                                TextUtils.formatSimple(
                                        "[%s] is alread exist! try to overwrite!", srcFile));
                    }
                    if (!FileUtils.copyFile(srcFile, destFile)) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] copy failed!", srcFile));
                    } else {
                        Slog.d(TAG, TextUtils.formatSimple("[%s] copy success!", srcFile));
                        if (mBnRMode == BnRMode.FsVerity) {
                            setUpFsVerity(srcFile);
                            setUpFsVerity(destFile);
                        }
                        count++;
                    }
                }
            }
        }
        if (count <= 0) {
            return false;
        }
        LsLog.restore(
                TextUtils.formatSimple(
                        "SPblobBNR, %d/%d files Restored!",
                        Integer.valueOf(count), Integer.valueOf(size)));
        return true;
    }

    private static boolean hasFsverity(File file) {
        if (mBnRMode == BnRMode.FsVerity) {
            boolean hasVerity = VerityUtils.hasFsverity(file.getAbsolutePath());
            if (DEBUG) {
                Slog.d(TAG, "hasFsverity [" + file + "], " + hasVerity);
            }
            return hasVerity;
        }
        return false;
    }

    private static void setUpFsVerity(File file) {
        if (!hasFsverity(file) && mBnRMode == BnRMode.FsVerity) {
            try {
                ParcelFileDescriptor pfd = ParcelFileDescriptor.open(file, 268435456);
                try {
                    VerityUtils.setUpFsverity(pfd.getFd());
                    Slog.w(TAG, "Success to verity-protect " + file);
                    if (pfd != null) {
                        pfd.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.w(TAG, "Failed to verity-protect " + file, e);
            }
        }
    }

    private static void addDeleteList(int userId, long protectorId, String name) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "addDeleteFile skipped. mode is " + mBnRMode);
            }
        } else {
            BnRData data = BnRData.create(userId, protectorId, name);
            sRemoveFiles.add(data);
            if (DEBUG) {
                Slog.d(TAG, TextUtils.formatSimple("Added [%s] for delete", data.getFileName()));
            }
        }
    }

    public static boolean deleteBackup(int userId, long protectorId) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "deleteBackup skipped. mode is " + mBnRMode);
                return true;
            }
            return true;
        }
        sRemoveFiles.clear();
        if (BNR_LIST != null) {
            for (int i = 0; i < BNR_LIST.length; i++) {
                addDeleteList(userId, protectorId, BNR_LIST[i]);
            }
        }
        return deleteBackuplist(sRemoveFiles);
    }

    public static boolean deleteBackup(int userId, long protectorId, String name) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "deleteBackup skipped. mode is " + mBnRMode);
                return true;
            }
            return true;
        }
        sRemoveFiles.clear();
        addDeleteList(userId, protectorId, name);
        return deleteBackuplist(sRemoveFiles);
    }

    private static boolean deleteBackuplist(Queue<BnRData> files) {
        if (files.isEmpty()) {
            Slog.e(TAG, "list is empty! check delete list first!");
            return false;
        }
        int count = 0;
        while (!files.isEmpty()) {
            BnRData data = files.poll();
            if (data == null || TextUtils.isEmpty(data.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                File srcFile =
                        new File(getBackupDirectoryForUser(data.getUserId()), data.getFileName());
                if (deleteFile(srcFile)) {
                    count++;
                }
            }
        }
        files.clear();
        Slog.d(TAG, TextUtils.formatSimple("[%d] files deleted!", Integer.valueOf(count)));
        return count > 0;
    }

    public static File startWrite(File path) {
        if (!path.exists()) {
            if (DEBUG) {
                Slog.d(TAG, TextUtils.formatSimple("[%s] is not exist!", path));
            }
            return null;
        }
        boolean hasVerity = false;
        if (mBnRMode == BnRMode.FsVerity) {
            boolean hasFsverity = hasFsverity(path);
            hasVerity = hasFsverity;
            if (hasFsverity) {
                File tempBackup = new File(path.getPath() + ".bnr");
                if (tempBackup.exists()) {
                    if (DEBUG) {
                        Slog.d(
                                TAG,
                                TextUtils.formatSimple(
                                        "TemporaryBackup [%s] is deleted", tempBackup.getPath()));
                    }
                    tempBackup.delete();
                }
                if (!path.renameTo(tempBackup)) {
                    Slog.e(TAG, TextUtils.formatSimple("[%s] rename failed!", path));
                    return null;
                }
                return tempBackup;
            }
        }
        if (DEBUG) {
            Slog.d(TAG, "Current mode is " + mBnRMode);
        }
        if (DEBUG) {
            Slog.d(TAG, "hasVerity = " + hasVerity);
        }
        return null;
    }

    public static void finishWrite(File path) {
        if (path == null) {
            if (DEBUG) {
                Slog.d(TAG, "No excute [startWrite()]");
            }
        } else if (path.exists()) {
            deleteFile(path);
        }
    }

    public static boolean deleteFile(File path) {
        if (!path.exists()) {
            if (DEBUG) {
                Slog.d(TAG, TextUtils.formatSimple("[%s] is not exist!", path));
                return false;
            }
            return false;
        }
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rws");
            try {
                int fileSize = (int) raf.length();
                raf.write(new byte[fileSize]);
                raf.close();
            } finally {
            }
        } catch (FileNotFoundException e) {
            if (mBnRMode == BnRMode.FsVerity) {
                Slog.w(TAG, "Failed to zeroize " + path);
            } else {
                Slog.w(TAG, "Failed to zeroize " + path, e);
            }
        } catch (Exception e2) {
            Slog.w(TAG, "Failed to zeroize " + path, e2);
        }
        new AtomicFile(path).delete();
        if (DEBUG) {
            Slog.d(TAG, TextUtils.formatSimple("[%s] delete success!", path));
            return true;
        }
        return true;
    }

    private static File getSyntheticPasswordDirectoryForUser(int userId) {
        return new File(Environment.getDataSystemDeDirectory(userId), SYNTHETIC_PASSWORD_DIRECTORY);
    }

    private static File getBackupDirectoryForUser(int userId) {
        return Environment.buildPath(
                new File(SPBLOB_BACKUP_DIRECTORY),
                String.valueOf(userId),
                SYNTHETIC_PASSWORD_DIRECTORY);
    }

    private static void ensureSPBnRDirectoryForUser(int userId) {
        File baseDir = getBackupDirectoryForUser(userId);
        if (!baseDir.exists() && !baseDir.mkdirs()) {
            Slog.e(TAG, "!@ Failed mkdir : " + baseDir);
        }
    }

    public static void dump(IndentingPrintWriter pw, int userId) {
        File userPath = getBackupDirectoryForUser(userId);
        pw.println(TextUtils.formatSimple("Backup [%s]:", userPath));
        pw.increaseIndent();
        File[] files = userPath.listFiles();
        if (files != null) {
            Arrays.sort(files);
            for (File file : files) {
                pw.println(
                        TextUtils.formatSimple(
                                "%6d %s %s",
                                Long.valueOf(file.length()),
                                LsUtil.timestampToString(file.lastModified()),
                                file.getName()));
            }
        } else {
            pw.println("[Not found]");
        }
        pw.decreaseIndent();
    }

    public static String getPWFilelist(int userId) {
        File userPath = getSyntheticPasswordDirectoryForUser(userId);
        File[] files = userPath.listFiles();
        if (files == null) {
            return TextUtils.formatSimple("  User %d [Not found]\n", Integer.valueOf(userId));
        }
        Arrays.sort(files);
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.formatSimple("  User %d [%s]:\n", Integer.valueOf(userId), userPath));
        for (File file : files) {
            sb.append(
                    TextUtils.formatSimple(
                            "  %6d %s %s\n",
                            Long.valueOf(file.length()),
                            LsUtil.timestampToString(file.lastModified()),
                            file.getName()));
        }
        return sb.toString();
    }

    public static String getBackupPWFilelist(int userId) {
        File userPath = getBackupDirectoryForUser(userId);
        File[] files = userPath.listFiles();
        if (files == null) {
            return TextUtils.formatSimple(
                    "  User %d Backup [Not found]\n", Integer.valueOf(userId));
        }
        Arrays.sort(files);
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.formatSimple("  Backup [%s]:\n", userPath));
        for (File file : files) {
            sb.append(
                    TextUtils.formatSimple(
                            "  %6d %s %s\n",
                            Long.valueOf(file.length()),
                            LsUtil.timestampToString(file.lastModified()),
                            file.getName()));
        }
        return sb.toString();
    }
}
