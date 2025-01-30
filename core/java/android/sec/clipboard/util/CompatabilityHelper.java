package android.sec.clipboard.util;

import android.p009os.FileUtils;
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
    private static final String TAG = CompatabilityHelper.class.getSimpleName();

    public static String getRootPathForMultiUser(int cat) {
        return "/data/semclipboard/" + String.valueOf(cat - 1000);
    }

    public static String replacePathForCompatability(String original) {
        if (original == null) {
            return null;
        }
        if (original.contains(OLD_CLIPBOARD_ROOT_PATH)) {
            if (original.startsWith("/data/clipboard/")) {
                String result = original.replace(OLD_CLIPBOARD_ROOT_PATH, ClipboardConstants.CLIPBOARD_ROOT_PATH);
                return result;
            }
            String result2 = "/data/semclipboard/" + original.substring(OLD_CLIPBOARD_ROOT_PATH.length());
            return result2;
        }
        return original;
    }

    public static void migrationClipboard() {
        copyClipboardDir(OLD_CLIPBOARD_ROOT_PATH, ClipboardConstants.CLIPBOARD_ROOT_PATH);
        for (int i = 10; i < 200; i++) {
            copyClipboardDir(OLD_CLIPBOARD_ROOT_PATH + i, "/data/semclipboard/" + i);
        }
    }

    public static void recursiveDelete(File rootDir) {
        File[] childDirs = rootDir.listFiles();
        if (childDirs != null) {
            for (int i = 0; i < childDirs.length; i++) {
                if (childDirs[i].isFile()) {
                    if (!childDirs[i].delete()) {
                        Log.m59d(TAG, "Failed to delete.");
                    }
                } else {
                    recursiveDelete(childDirs[i]);
                }
            }
        }
        if (!OLD_CLIPBOARD_ROOT_PATH.equals(rootDir.getPath()) && !ClipboardConstants.CLIPBOARD_ROOT_PATH.equals(rootDir.getPath()) && !rootDir.delete()) {
            Log.m59d(TAG, "Failed to delete root .");
        }
    }

    private static void copyClipboardDir(String srcPath, String targetPath) {
        File[] srcDirlistFiles;
        boolean process = false;
        File targetDir = new File(targetPath);
        File srcDir = new File(srcPath);
        if (srcDir.exists() && (srcDirlistFiles = srcDir.listFiles()) != null && srcDirlistFiles.length > 0) {
            process = true;
            copyDir(srcDir, targetDir);
            recursiveDelete(srcDir);
        }
        if (process) {
            Log.m59d(TAG, "migration progressed from " + srcPath + " to " + targetPath);
        }
    }

    private static void copyDir(File src, File dest) {
        if (src.isDirectory()) {
            if (!dest.exists() && dest.mkdir()) {
                FileUtils.setPermissions(dest, 509, -1, -1);
            }
            String[] children = src.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    copyDir(new File(src, children[i]), new File(dest, children[i]));
                }
                return;
            }
            return;
        }
        try {
            FileInputStream in = new FileInputStream(src);
            try {
                FileOutputStream out = new FileOutputStream(dest);
                try {
                    byte[] buf = new byte[1024];
                    while (true) {
                        int len = in.read(buf);
                        if (len > 0) {
                            out.write(buf, 0, len);
                        } else {
                            FileUtils.setPermissions(dest, 509, -1, -1);
                            out.close();
                            in.close();
                            return;
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.m59d(TAG, "copyDir failed. " + e.getMessage());
        }
    }
}
