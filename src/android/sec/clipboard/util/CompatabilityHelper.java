package android.sec.clipboard.util;

import android.os.FileUtils;
import android.sec.clipboard.data.ClipboardConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public class CompatabilityHelper {
    private static final int MAX_SECOND_USER_ID = 200;
    private static final int MIN_SECOND_USER_ID = 10;
    public static final String OLD_CLIPBOARD_ROOT_PATH = "/data/clipboard";
    private static final String TAG = "CompatabilityHelper";

    public static String getRootPathForMultiUser(int i) {
        return "/data/semclipboard/" + String.valueOf(i - 1000);
    }

    public static String replacePathForCompatability(String str) {
        if (str == null) {
            return null;
        }
        if (!str.contains(OLD_CLIPBOARD_ROOT_PATH)) {
            return str;
        }
        if (str.startsWith("/data/clipboard/")) {
            return str.replace(OLD_CLIPBOARD_ROOT_PATH, ClipboardConstants.CLIPBOARD_ROOT_PATH);
        }
        return "/data/semclipboard/" + str.substring(15);
    }

    public static void migrationClipboard() {
        copyClipboardDir(OLD_CLIPBOARD_ROOT_PATH, ClipboardConstants.CLIPBOARD_ROOT_PATH);
        for (int i = 10; i < 200; i++) {
            copyClipboardDir(OLD_CLIPBOARD_ROOT_PATH + i, "/data/semclipboard/" + i);
        }
    }

    public static void recursiveDelete(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    if (!listFiles[i].delete()) {
                        Log.d(TAG, "Failed to delete.");
                    }
                } else {
                    recursiveDelete(listFiles[i]);
                }
            }
        }
        if (OLD_CLIPBOARD_ROOT_PATH.equals(file.getPath()) || ClipboardConstants.CLIPBOARD_ROOT_PATH.equals(file.getPath()) || file.delete()) {
            return;
        }
        Log.d(TAG, "Failed to delete root .");
    }

    private static void copyClipboardDir(String str, String str2) {
        File[] listFiles;
        File file = new File(str2);
        File file2 = new File(str);
        if (!file2.exists() || (listFiles = file2.listFiles()) == null || listFiles.length <= 0) {
            return;
        }
        copyDir(file2, file);
        recursiveDelete(file2);
        Log.d(TAG, "migration progressed from " + str + " to " + str2);
    }

    private static void copyDir(File file, File file2) {
        if (file.isDirectory()) {
            if (!file2.exists() && file2.mkdir()) {
                FileUtils.setPermissions(file2, 509, -1, -1);
            }
            String[] list = file.list();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    copyDir(new File(file, list[i]), new File(file2, list[i]));
                }
                return;
            }
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read > 0) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            FileUtils.setPermissions(file2, 509, -1, -1);
                            fileOutputStream.close();
                            fileInputStream.close();
                            return;
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.d(TAG, "copyDir failed. " + e.getMessage());
        }
    }
}
