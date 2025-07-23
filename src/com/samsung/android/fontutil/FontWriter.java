package com.samsung.android.fontutil;

import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/* loaded from: classes6.dex */
public class FontWriter {
    public static final String NEW_FONT_DIRECTORY = "/data/app_fonts/";
    public static final String SANS_LOC_NAME = "sans.loc";
    private static final String TAG = "FontWriter";
    private static final int TTF_FILE_COPY_BUF_SIZE = 8192;

    public void writeLoc(String str) {
        String str2 = NEW_FONT_DIRECTORY + UserHandle.myUserId();
        File file = new File(str2, SANS_LOC_NAME);
        try {
        } catch (Exception e) {
            Log.e(TAG, "Cannot create the loc file : " + e.getMessage());
        }
        if (!file.getCanonicalPath().startsWith(new File(str2).getCanonicalPath())) {
            throw new Exception("Directory traversal attack!");
        }
        setFileProperties(file, false);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                try {
                    outputStreamWriter.write(str + ShaderAssembler.NEWLINE);
                    outputStreamWriter.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            Log.e(TAG, "Cannot create the loc file : " + e2.getMessage());
        }
    }

    public File createFontDirectory(String str) {
        File file;
        File file2 = null;
        try {
            File file3 = new File(NEW_FONT_DIRECTORY + UserHandle.myUserId());
            setFileProperties(file3, true);
            file = new File(file3, str.replaceAll("\\.\\./", "").replaceAll("/", ""));
        } catch (IOException unused) {
        }
        try {
            setFileProperties(file, true);
            return file;
        } catch (IOException unused2) {
            file2 = file;
            Log.e(TAG, "IOException while CreatFontDirectory");
            return file2;
        }
    }

    private void setFileProperties(File file, boolean z) throws IOException {
        try {
            if (z) {
                file.mkdir();
            } else {
                file.createNewFile();
            }
        } catch (IOException unused) {
            Log.e(TAG, "IOException while createNewFile");
        } catch (SecurityException unused2) {
            Log.e(TAG, "SecurityException while setFileProperties");
        }
        file.setReadable(true, false);
        if (!file.setWritable(true, false)) {
            Slog.d(TAG, "Couldn't give Writable permission : " + file.getAbsolutePath());
        }
        file.setExecutable(true, false);
    }

    public void deleteFontDirectory(String str) {
        File file = new File(NEW_FONT_DIRECTORY + UserHandle.myUserId());
        String[] list = file.list();
        if (list != null) {
            for (String str2 : list) {
                if (str2.compareTo(str) != 0) {
                    deleteFolder(file, str2);
                }
            }
        }
    }

    private void deleteFolder(File file, String str) {
        File file2 = new File(file, str);
        String[] list = file2.list();
        if (list != null) {
            for (String str2 : list) {
                if (!new File(file2, str2).delete()) {
                    Slog.d(TAG, "Couldn't delete Folder Dir : " + file2 + ", " + str2);
                }
            }
            try {
                file2.delete();
            } catch (SecurityException e) {
                Log.e(TAG, "Error while delete directory : " + e.getMessage());
            }
        }
    }

    public boolean copyFontFile(File file, InputStream inputStream, String str) {
        File file2 = new File(file, str);
        try {
            if (!file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
                throw new Exception("Directory traversal attack!");
            }
            setFileProperties(file2, false);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            bufferedOutputStream.write(bArr, 0, read);
                        }
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                        if (file2.length() != 0) {
                            return false;
                        }
                        if (!file2.delete()) {
                            Slog.d(TAG, "Couldn't delete file " + file + " , " + str);
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while copy FontFile : " + e.getMessage());
                if (!file2.delete()) {
                    Slog.d(TAG, "Couldn't delete file " + file + " , " + str);
                }
                return true;
            }
        } catch (Exception e2) {
            Log.e(TAG, "Error while make destination File : " + e2.getMessage());
            if (!file2.delete()) {
                Slog.d(TAG, "Couldn't delete file " + file + " , " + str);
            }
            return true;
        }
    }
}
