package com.android.server.enterprise.general.font;

import android.content.Context;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/* loaded from: classes2.dex */
public class FontWriter {
    public static String TAG = "FontWriter";
    public FileOutputStream fOut = null;
    public OutputStreamWriter osw = null;
    public BufferedOutputStream bos = null;

    /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:
    
        if (r7 != null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006c, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008e, code lost:
    
        if (r7 == null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeLoc(Context context, String str, String str2) {
        FileOutputStream fileOutputStream;
        File file;
        try {
            try {
                try {
                    file = new File("/data/app_fonts/" + String.valueOf(UserHandle.myUserId()), str);
                    file.createNewFile();
                    str = file.getAbsolutePath();
                } catch (Exception e) {
                    e = e;
                    str = "";
                }
                try {
                    this.fOut = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.fOut);
                    this.osw = outputStreamWriter;
                    outputStreamWriter.write(str2 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    this.osw.flush();
                    this.fOut.flush();
                    try {
                        OutputStreamWriter outputStreamWriter2 = this.osw;
                        if (outputStreamWriter2 != null) {
                            outputStreamWriter2.close();
                        }
                    } catch (IOException unused) {
                        Log.e(TAG, "writeLoc : osw.close() error");
                    }
                    fileOutputStream = this.fOut;
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    try {
                        OutputStreamWriter outputStreamWriter3 = this.osw;
                        if (outputStreamWriter3 != null) {
                            outputStreamWriter3.close();
                        }
                    } catch (IOException unused2) {
                        Log.e(TAG, "writeLoc : osw.close() error");
                    }
                    fileOutputStream = this.fOut;
                }
            } finally {
            }
        } catch (IOException unused3) {
            Log.e(TAG, "writeLoc : fOut.close() error");
        }
        SystemProperties.set("persist.sys.flipfontpath", "");
        changeFilePermission(str);
    }

    public File createFontDirectory(Context context, String str) {
        Process exec;
        Log.i(TAG, "createFontDirectory : Start");
        try {
            File file = new File("/data/app_fonts/" + String.valueOf(UserHandle.myUserId()));
            if (!file.mkdir()) {
                Slog.d(TAG, "Couldn't create font dir");
            }
            file.setReadable(true, false);
            if (!file.setWritable(true, false)) {
                Slog.d(TAG, "Couldn't give Writable permission : " + file.getAbsolutePath());
            }
            file.setExecutable(true, false);
            File file2 = new File(file, str);
            String[] list = file.list();
            if (list == null) {
                return null;
            }
            for (String str2 : list) {
                deleteFolder(file, str2);
            }
            if (file2.mkdir()) {
                Log.i(TAG, "Font directory  : Created");
            } else {
                Log.i(TAG, "Font directory  : Not Created");
            }
            try {
                exec = Runtime.getRuntime().exec("chmod 711 " + file2.getAbsolutePath());
                exec.waitFor();
            } catch (IOException unused) {
                Log.i(TAG, "IOException : ");
            } catch (InterruptedException unused2) {
                Log.i(TAG, "InterruptedException : ");
            }
            if (exec.exitValue() == 0) {
                return file2;
            }
            throw new IOException("Cannot chmod");
        } catch (SecurityException unused3) {
            Log.e(TAG, "SecurityException while setFileProperties");
            return null;
        } catch (Exception unused4) {
            Log.e(TAG, "Exception in createFontDirectory");
            return null;
        }
    }

    public final boolean deleteFolder(File file, String str) {
        File file2 = new File(file, str);
        String[] list = file2.list();
        if (list == null) {
            return false;
        }
        for (String str2 : list) {
            new File(file2, str2).delete();
        }
        return file2.delete();
    }

    public void changeFilePermission(String str) {
        try {
            Process exec = Runtime.getRuntime().exec("chmod 744 " + str);
            exec.waitFor();
            if (exec.exitValue() == 0) {
            } else {
                throw new IOException("Cannot chmod");
            }
        } catch (IOException | InterruptedException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005e, code lost:
    
        if (r12 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009a, code lost:
    
        if (r12 == null) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void copyFontFile(File file, InputStream inputStream, String str) {
        BufferedOutputStream bufferedOutputStream;
        String str2 = "";
        try {
            try {
                try {
                    File file2 = new File(file, str);
                    file2.createNewFile();
                    str2 = file2.getAbsolutePath();
                    this.fOut = new FileOutputStream(file2);
                    this.bos = new BufferedOutputStream(this.fOut);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        } else {
                            this.bos.write(bArr, 0, read);
                        }
                    }
                    this.bos.flush();
                    this.fOut.flush();
                    this.bos.close();
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                        Log.e(TAG, "copyFontFile : myInputStream.close() error");
                    }
                    try {
                        FileOutputStream fileOutputStream = this.fOut;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException unused2) {
                        Log.e(TAG, "copyFontFile : fOut.close() error");
                    }
                    bufferedOutputStream = this.bos;
                } catch (Exception e) {
                    File file3 = new File(file, str);
                    if (file3.length() == 0) {
                        file3.delete();
                    }
                    e.printStackTrace();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused3) {
                            Log.e(TAG, "copyFontFile : myInputStream.close() error");
                        }
                    }
                    try {
                        FileOutputStream fileOutputStream2 = this.fOut;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                    } catch (IOException unused4) {
                        Log.e(TAG, "copyFontFile : fOut.close() error");
                    }
                    bufferedOutputStream = this.bos;
                }
            } catch (IOException unused5) {
                Log.e(TAG, "copyFontFile : bos.close() error");
            }
            changeFilePermission(str2);
            File file4 = new File(file, str);
            if (file4.length() == 0) {
                file4.delete();
            }
        } finally {
        }
    }
}
