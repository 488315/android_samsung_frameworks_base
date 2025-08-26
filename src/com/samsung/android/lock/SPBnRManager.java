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
    private static String[] BNR_LIST = null;
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
    private static BnRMode mBnRMode = BnRMode.FsVerity;
    private static List<BnRData> sBnRManagedFiles = new LinkedList();
    private static Queue<BnRData> sRemoveFiles = new LinkedList();

    public static void init(boolean z) {
        BNR_LIST = new String[]{SP_BLOB_NAME, PASSWORD_DATA_NAME, PASSWORD_METRICS_NAME, z ? WEAVER_SLOT_NAME : SECDISCARDABLE_NAME};
    }

    public static void setProtectorIdForBackup(int i, long j, long j2) {
        ensureSPBnRDirectoryForUser(i);
        clearManagedFiles();
        addManagedFile(i, 0L, SP_HANDLE_NAME);
        if (j != 0) {
            addManagedFilesByProtectorId(i, j);
            if (j2 != 0) {
                addManagedFilesByProtectorId(i, j2);
            }
        }
    }

    static class BnRData {
        String mFileName;
        String mName;
        long mProtectorId;
        int mUserId;

        BnRData() {
        }

        public int getUserId() {
            return this.mUserId;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public static BnRData create(int i, long j, String str) {
            BnRData bnRData = new BnRData();
            bnRData.mProtectorId = j;
            bnRData.mUserId = i;
            bnRData.mName = str;
            bnRData.mFileName = TextUtils.formatSimple("%016x.%s", Long.valueOf(j), bnRData.mName);
            return bnRData;
        }
    }

    private enum BnRMode {
        None(0),
        Copy(1),
        FsVerity(2);

        private int mode;

        BnRMode(int i) {
            this.mode = i;
        }

        public int getMode() {
            return this.mode;
        }
    }

    public static void resetMode() {
        if (LsUtil.isDevBuild()) {
            int i = SystemProperties.getInt("persist.lock.BnR", 5);
            if (i == 0) {
                mBnRMode = BnRMode.None;
                return;
            } else if (i == 1) {
                mBnRMode = BnRMode.Copy;
                return;
            } else if (i == 2) {
                mBnRMode = BnRMode.FsVerity;
                return;
            }
        }
        if (DEBUG) {
            Slog.d(TAG, "Current mode is " + mBnRMode);
        }
    }

    private static void addManagedFile(int i, long j, String str) {
        if (mBnRMode == BnRMode.None) {
            Slog.d(TAG, "addManagedFile skipped. mode is " + mBnRMode);
        } else {
            BnRData bnRDataCreate = BnRData.create(i, j, str);
            sBnRManagedFiles.add(bnRDataCreate);
            if (DEBUG) {
                Slog.d(TAG, TextUtils.formatSimple("Added [%s] for BnR", bnRDataCreate.getFileName()));
            }
        }
    }

    private static void addManagedFilesByProtectorId(int i, long j) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "addManagedFilesByProtectorId skipped. mode is " + mBnRMode);
                return;
            }
            return;
        }
        if (BNR_LIST == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            String[] strArr = BNR_LIST;
            if (i2 >= strArr.length) {
                return;
            }
            addManagedFile(i, j, strArr[i2]);
            i2++;
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
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        for (int i = 0; i < sBnRManagedFiles.size(); i++) {
            BnRData bnRData = sBnRManagedFiles.get(i);
            if (bnRData == null || TextUtils.isEmpty(bnRData.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                String fileName = bnRData.getFileName();
                File file = new File(getSyntheticPasswordDirectoryForUser(bnRData.getUserId()), fileName);
                File file2 = new File(getBackupDirectoryForUser(bnRData.getUserId()), fileName);
                if (checkValidState(file)) {
                    if (checkValidState(file2)) {
                        if (DEBUG) {
                            Slog.d(TAG, TextUtils.formatSimple("[%s] exists on both sides.", fileName));
                        }
                    } else {
                        linkedList.add(bnRData);
                        Slog.e(TAG, TextUtils.formatSimple("[%s] does not exist in bak!", fileName));
                    }
                } else if (checkValidState(file2)) {
                    linkedList2.add(bnRData);
                    Slog.e(TAG, TextUtils.formatSimple("[%s] does not exist in org!", fileName));
                } else if (DEBUG) {
                    Slog.d(TAG, TextUtils.formatSimple("[%s] does not exist on both sides.", fileName));
                }
            }
        }
        return !linkedList2.isEmpty() ? !startRestorelist(linkedList2) : !linkedList.isEmpty() ? !startBackuplist(linkedList) : true;
    }

    private static boolean checkValidState(File file) throws IOException {
        byte[] bArr;
        if (!file.exists()) {
            return false;
        }
        if (mBnRMode == BnRMode.None || mBnRMode == BnRMode.Copy) {
            return true;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            try {
                int length = (int) randomAccessFile.length();
                bArr = new byte[length];
                randomAccessFile.readFully(bArr, 0, length);
                randomAccessFile.close();
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e(TAG, "checkValidState(), Cannot read file " + e);
            bArr = null;
        }
        return !ArrayUtils.isEmpty(bArr);
    }

    public static boolean startBackup() {
        if (mBnRMode == BnRMode.None) {
            if (!DEBUG) {
                return true;
            }
            Slog.d(TAG, "startBackup skipped. mode is " + mBnRMode);
            return true;
        }
        if (sBnRManagedFiles.isEmpty()) {
            Slog.e(TAG, "startBackup failed! list is empty!");
            return false;
        }
        return startBackuplist(sBnRManagedFiles);
    }

    private static boolean startBackuplist(List<BnRData> list) {
        if (list.isEmpty()) {
            Slog.e(TAG, "list is empty! check backup list first!");
            return false;
        }
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            BnRData bnRData = list.get(i2);
            if (bnRData == null || TextUtils.isEmpty(bnRData.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                File file = new File(getSyntheticPasswordDirectoryForUser(bnRData.getUserId()), bnRData.getFileName());
                File file2 = new File(getBackupDirectoryForUser(bnRData.getUserId()), bnRData.getFileName());
                if (!file.exists()) {
                    Slog.w(TAG, TextUtils.formatSimple("[%s] is not exist!", file));
                } else {
                    if (file2.exists()) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] is alread exist! try to overwrite!", file));
                    }
                    if (!FileUtils.copyFile(file, file2)) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] copy failed!", file));
                    } else {
                        if (DEBUG) {
                            Slog.d(TAG, TextUtils.formatSimple("[%s] copy success!", file));
                        }
                        if (mBnRMode == BnRMode.FsVerity) {
                            setUpFsVerity(file);
                            setUpFsVerity(file2);
                        }
                        i++;
                    }
                }
            }
        }
        if (i <= 0) {
            return false;
        }
        LsLog.restore(TextUtils.formatSimple("SPblobBNR, %d/%d files Backuped!", Integer.valueOf(i), Integer.valueOf(size)));
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

    private static boolean startRestorelist(List<BnRData> list) {
        if (list.isEmpty()) {
            if (DEBUG) {
                Slog.e(TAG, "list is empty! check restore list first!");
            }
            return false;
        }
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            BnRData bnRData = list.get(i2);
            if (bnRData == null || TextUtils.isEmpty(bnRData.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else {
                File file = new File(getBackupDirectoryForUser(bnRData.getUserId()), bnRData.getFileName());
                File file2 = new File(getSyntheticPasswordDirectoryForUser(bnRData.getUserId()), bnRData.getFileName());
                if (!file.exists()) {
                    Slog.w(TAG, TextUtils.formatSimple("[%s] is not exist!", file));
                } else {
                    if (file2.exists()) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] is alread exist! try to overwrite!", file));
                    }
                    if (!FileUtils.copyFile(file, file2)) {
                        Slog.w(TAG, TextUtils.formatSimple("[%s] copy failed!", file));
                    } else {
                        Slog.d(TAG, TextUtils.formatSimple("[%s] copy success!", file));
                        if (mBnRMode == BnRMode.FsVerity) {
                            setUpFsVerity(file);
                            setUpFsVerity(file2);
                        }
                        i++;
                    }
                }
            }
        }
        if (i <= 0) {
            return false;
        }
        LsLog.restore(TextUtils.formatSimple("SPblobBNR, %d/%d files Restored!", Integer.valueOf(i), Integer.valueOf(size)));
        return true;
    }

    private static boolean hasFsverity(File file) {
        if (mBnRMode != BnRMode.FsVerity) {
            return false;
        }
        boolean zHasFsverity = VerityUtils.hasFsverity(file.getAbsolutePath());
        if (DEBUG) {
            Slog.d(TAG, "hasFsverity [" + file + "], " + zHasFsverity);
        }
        return zHasFsverity;
    }

    private static void setUpFsVerity(File file) {
        if (!hasFsverity(file) && mBnRMode == BnRMode.FsVerity) {
            try {
                ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
                try {
                    VerityUtils.setUpFsverity(parcelFileDescriptorOpen.getFd());
                    Slog.w(TAG, "Success to verity-protect " + file);
                    if (parcelFileDescriptorOpen != null) {
                        parcelFileDescriptorOpen.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.w(TAG, "Failed to verity-protect " + file, e);
            }
        }
    }

    private static void addDeleteList(int i, long j, String str) {
        if (mBnRMode == BnRMode.None) {
            if (DEBUG) {
                Slog.d(TAG, "addDeleteFile skipped. mode is " + mBnRMode);
                return;
            }
            return;
        }
        BnRData bnRDataCreate = BnRData.create(i, j, str);
        sRemoveFiles.add(bnRDataCreate);
        if (DEBUG) {
            Slog.d(TAG, TextUtils.formatSimple("Added [%s] for delete", bnRDataCreate.getFileName()));
        }
    }

    public static boolean deleteBackup(int i, long j) {
        if (mBnRMode == BnRMode.None) {
            if (!DEBUG) {
                return true;
            }
            Slog.d(TAG, "deleteBackup skipped. mode is " + mBnRMode);
            return true;
        }
        sRemoveFiles.clear();
        if (BNR_LIST != null) {
            int i2 = 0;
            while (true) {
                String[] strArr = BNR_LIST;
                if (i2 >= strArr.length) {
                    break;
                }
                addDeleteList(i, j, strArr[i2]);
                i2++;
            }
        }
        return deleteBackuplist(sRemoveFiles);
    }

    public static boolean deleteBackup(int i, long j, String str) {
        if (mBnRMode == BnRMode.None) {
            if (!DEBUG) {
                return true;
            }
            Slog.d(TAG, "deleteBackup skipped. mode is " + mBnRMode);
            return true;
        }
        sRemoveFiles.clear();
        addDeleteList(i, j, str);
        return deleteBackuplist(sRemoveFiles);
    }

    private static boolean deleteBackuplist(Queue<BnRData> queue) {
        if (queue.isEmpty()) {
            Slog.e(TAG, "list is empty! check delete list first!");
            return false;
        }
        int i = 0;
        while (!queue.isEmpty()) {
            BnRData bnRDataPoll = queue.poll();
            if (bnRDataPoll == null || TextUtils.isEmpty(bnRDataPoll.getFileName())) {
                Slog.w(TAG, "data is null!");
            } else if (deleteFile(new File(getBackupDirectoryForUser(bnRDataPoll.getUserId()), bnRDataPoll.getFileName()))) {
                i++;
            }
        }
        queue.clear();
        Slog.d(TAG, TextUtils.formatSimple("[%d] files deleted!", Integer.valueOf(i)));
        return i > 0;
    }

    public static File startWrite(File file) {
        boolean zHasFsverity;
        if (!file.exists()) {
            if (DEBUG) {
                Slog.d(TAG, TextUtils.formatSimple("[%s] is not exist!", file));
            }
            return null;
        }
        if (mBnRMode == BnRMode.FsVerity) {
            zHasFsverity = hasFsverity(file);
            if (zHasFsverity) {
                File file2 = new File(file.getPath() + ".bnr");
                if (file2.exists()) {
                    if (DEBUG) {
                        Slog.d(TAG, TextUtils.formatSimple("TemporaryBackup [%s] is deleted", file2.getPath()));
                    }
                    file2.delete();
                }
                if (file.renameTo(file2)) {
                    return file2;
                }
                Slog.e(TAG, TextUtils.formatSimple("[%s] rename failed!", file));
                return null;
            }
        } else {
            zHasFsverity = false;
        }
        boolean z = DEBUG;
        if (z) {
            Slog.d(TAG, "Current mode is " + mBnRMode);
        }
        if (z) {
            Slog.d(TAG, "hasVerity = " + zHasFsverity);
        }
        return null;
    }

    public static void finishWrite(File file) throws IOException {
        if (file == null) {
            if (DEBUG) {
                Slog.d(TAG, "No excute [startWrite()]");
            }
        } else if (file.exists()) {
            deleteFile(file);
        }
    }

    public static boolean deleteFile(File file) throws IOException {
        if (!file.exists()) {
            if (!DEBUG) {
                return false;
            }
            Slog.d(TAG, TextUtils.formatSimple("[%s] is not exist!", file));
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
            try {
                randomAccessFile.write(new byte[(int) randomAccessFile.length()]);
                randomAccessFile.close();
            } catch (Throwable th) {
                try {
                    randomAccessFile.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            if (mBnRMode == BnRMode.FsVerity) {
                Slog.w(TAG, "Failed to zeroize " + file);
            } else {
                Slog.w(TAG, "Failed to zeroize " + file, e);
            }
        } catch (Exception e2) {
            Slog.w(TAG, "Failed to zeroize " + file, e2);
        }
        new AtomicFile(file).delete();
        if (!DEBUG) {
            return true;
        }
        Slog.d(TAG, TextUtils.formatSimple("[%s] delete success!", file));
        return true;
    }

    private static File getSyntheticPasswordDirectoryForUser(int i) {
        return new File(Environment.getDataSystemDeDirectory(i), SYNTHETIC_PASSWORD_DIRECTORY);
    }

    private static File getBackupDirectoryForUser(int i) {
        return Environment.buildPath(new File(SPBLOB_BACKUP_DIRECTORY), String.valueOf(i), SYNTHETIC_PASSWORD_DIRECTORY);
    }

    private static void ensureSPBnRDirectoryForUser(int i) {
        File backupDirectoryForUser = getBackupDirectoryForUser(i);
        if (backupDirectoryForUser.exists() || backupDirectoryForUser.mkdirs()) {
            return;
        }
        Slog.e(TAG, "!@ Failed mkdir : " + backupDirectoryForUser);
    }

    public static void dump(IndentingPrintWriter indentingPrintWriter, int i) {
        File backupDirectoryForUser = getBackupDirectoryForUser(i);
        indentingPrintWriter.println(TextUtils.formatSimple("Backup [%s]:", backupDirectoryForUser));
        indentingPrintWriter.increaseIndent();
        File[] fileArrListFiles = backupDirectoryForUser.listFiles();
        if (fileArrListFiles != null) {
            Arrays.sort(fileArrListFiles);
            for (File file : fileArrListFiles) {
                indentingPrintWriter.println(TextUtils.formatSimple("%6d %s %s", Long.valueOf(file.length()), LsUtil.timestampToString(file.lastModified()), file.getName()));
            }
        } else {
            indentingPrintWriter.println("[Not found]");
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static String getPWFilelist(int i) {
        File syntheticPasswordDirectoryForUser = getSyntheticPasswordDirectoryForUser(i);
        File[] fileArrListFiles = syntheticPasswordDirectoryForUser.listFiles();
        if (fileArrListFiles == null) {
            return TextUtils.formatSimple("  User %d [Not found]\n", Integer.valueOf(i));
        }
        Arrays.sort(fileArrListFiles);
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.formatSimple("  User %d [%s]:\n", Integer.valueOf(i), syntheticPasswordDirectoryForUser));
        for (File file : fileArrListFiles) {
            sb.append(TextUtils.formatSimple("  %6d %s %s\n", Long.valueOf(file.length()), LsUtil.timestampToString(file.lastModified()), file.getName()));
        }
        return sb.toString();
    }

    public static String getBackupPWFilelist(int i) {
        File backupDirectoryForUser = getBackupDirectoryForUser(i);
        File[] fileArrListFiles = backupDirectoryForUser.listFiles();
        if (fileArrListFiles == null) {
            return TextUtils.formatSimple("  User %d Backup [Not found]\n", Integer.valueOf(i));
        }
        Arrays.sort(fileArrListFiles);
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.formatSimple("  Backup [%s]:\n", backupDirectoryForUser));
        for (File file : fileArrListFiles) {
            sb.append(TextUtils.formatSimple("  %6d %s %s\n", Long.valueOf(file.length()), LsUtil.timestampToString(file.lastModified()), file.getName()));
        }
        return sb.toString();
    }
}
