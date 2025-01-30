package com.samsung.android.fontutil;

import android.p009os.UserHandle;
import android.util.Log;
import android.util.Slog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/* loaded from: classes5.dex */
public class FontWriter {
    public static final String NEW_FONT_DIRECTORY = "/data/app_fonts/";
    public static final String SANS_LOC_NAME = "sans.loc";
    private static final String TAG = "FontWriter";
    private static final int TTF_FILE_COPY_BUF_SIZE = 8192;

    public void writeLoc(String directory) {
        String canonicalPath;
        String sFile = NEW_FONT_DIRECTORY + UserHandle.myUserId();
        File dest = new File(sFile, SANS_LOC_NAME);
        try {
            canonicalPath = new File(sFile).getCanonicalPath();
        } catch (Exception e) {
            Log.m96e(TAG, "Cannot create the loc file : " + e.getMessage());
        }
        if (!dest.getCanonicalPath().startsWith(canonicalPath)) {
            throw new Exception("Directory traversal attack!");
        }
        setFileProperties(dest, false);
        try {
            FileOutputStream fos = new FileOutputStream(dest);
            try {
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                try {
                    osw.write(directory + "\n");
                    osw.close();
                    fos.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            Log.m96e(TAG, "Cannot create the loc file : " + e2.getMessage());
        }
    }

    public File createFontDirectory(String fontName) {
        File fontFile = null;
        try {
            File newFontDir = new File(NEW_FONT_DIRECTORY + UserHandle.myUserId());
            setFileProperties(newFontDir, true);
            fontFile = new File(newFontDir, fontName);
            setFileProperties(fontFile, true);
            return fontFile;
        } catch (IOException e) {
            Log.m96e(TAG, "IOException while CreatFontDirectory");
            return fontFile;
        }
    }

    private void setFileProperties(File file, boolean needMakeDir) throws IOException {
        try {
            if (needMakeDir) {
                file.mkdir();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.m96e(TAG, "IOException while createNewFile");
        } catch (SecurityException e2) {
            Log.m96e(TAG, "SecurityException while setFileProperties");
        }
        file.setReadable(true, false);
        if (!file.setWritable(true, false)) {
            Slog.m113d(TAG, "Couldn't give Writable permission : " + file.getAbsolutePath());
        }
        file.setExecutable(true, false);
    }

    public void deleteFontDirectory(String keepfolder) {
        File newFontDir = new File(NEW_FONT_DIRECTORY + UserHandle.myUserId());
        String[] tmp = newFontDir.list();
        if (tmp != null) {
            for (String temp : tmp) {
                if (temp.compareTo(keepfolder) != 0) {
                    deleteFolder(newFontDir, temp);
                }
            }
        }
    }

    private void deleteFolder(File FontDir, String folderName) {
        File newDir = new File(FontDir, folderName);
        String[] tmp = newDir.list();
        if (tmp != null) {
            for (String temp : tmp) {
                File file = new File(newDir, temp);
                if (!file.delete()) {
                    Slog.m113d(TAG, "Couldn't delete Folder Dir : " + newDir + ", " + temp);
                }
            }
            try {
                newDir.delete();
            } catch (SecurityException e) {
                Log.m96e(TAG, "Error while delete directory : " + e.getMessage());
            }
        }
    }

    public boolean copyFontFile(File directory, InputStream is, String destination) {
        File dest = new File(directory, destination);
        try {
            if (!dest.getCanonicalPath().startsWith(directory.getCanonicalPath())) {
                throw new Exception("Directory traversal attack!");
            }
            setFileProperties(dest, false);
            try {
                FileOutputStream fos = new FileOutputStream(dest);
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    try {
                        byte[] buf = new byte[8192];
                        while (true) {
                            int readCount = is.read(buf);
                            if (readCount <= 0) {
                                break;
                            }
                            bos.write(buf, 0, readCount);
                        }
                        bos.close();
                        fos.close();
                        if (dest.length() != 0) {
                            return false;
                        }
                        if (!dest.delete()) {
                            Slog.m113d(TAG, "Couldn't delete file " + directory + " , " + destination);
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.m96e(TAG, "Error while copy FontFile : " + e.getMessage());
                if (!dest.delete()) {
                    Slog.m113d(TAG, "Couldn't delete file " + directory + " , " + destination);
                }
                return true;
            }
        } catch (Exception e2) {
            Log.m96e(TAG, "Error while make destination File : " + e2.getMessage());
            if (!dest.delete()) {
                Slog.m113d(TAG, "Couldn't delete file " + directory + " , " + destination);
            }
            return true;
        }
    }
}
