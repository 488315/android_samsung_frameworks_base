package com.android.server.enterprise.general.font;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public final class FontWriter {
    public BufferedOutputStream bos;
    public FileOutputStream fOut;
    public OutputStreamWriter osw;

    public static void changeFilePermission(String str) {
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0064, code lost:

       if (r11 != null) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0066, code lost:

       r11.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0097, code lost:

       if (r11 == null) goto L41;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void copyFontFile(
            java.io.File r12, java.io.InputStream r13, java.lang.String r14) {
        /*
            r11 = this;
            java.lang.String r0 = "copyFontFile : bos.close() error"
            java.lang.String r1 = "copyFontFile : fOut.close() error"
            java.lang.String r2 = "copyFontFile : myInputStream.close() error"
            java.lang.String r3 = "FontWriter"
            java.lang.String r4 = ""
            r5 = 0
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.<init>(r12, r14)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.createNewFile()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.lang.String r4 = r7.getAbsolutePath()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r11.fOut = r8     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.io.FileOutputStream r8 = r11.fOut     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r11.bos = r7     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
        L2f:
            int r8 = r13.read(r7)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            if (r8 <= 0) goto L41
            java.io.BufferedOutputStream r9 = r11.bos     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r10 = 0
            r9.write(r7, r10, r8)     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            goto L2f
        L3c:
            r12 = move-exception
            goto Lae
        L3f:
            r7 = move-exception
            goto L6e
        L41:
            java.io.BufferedOutputStream r7 = r11.bos     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.flush()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.io.FileOutputStream r7 = r11.fOut     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.flush()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            java.io.BufferedOutputStream r7 = r11.bos     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r7.close()     // Catch: java.lang.Throwable -> L3c java.lang.Exception -> L3f
            r13.close()     // Catch: java.io.IOException -> L54
            goto L57
        L54:
            android.util.Log.e(r3, r2)
        L57:
            java.io.FileOutputStream r13 = r11.fOut     // Catch: java.io.IOException -> L5f
            if (r13 == 0) goto L62
            r13.close()     // Catch: java.io.IOException -> L5f
            goto L62
        L5f:
            android.util.Log.e(r3, r1)
        L62:
            java.io.BufferedOutputStream r11 = r11.bos     // Catch: java.io.IOException -> L6a
            if (r11 == 0) goto L9a
        L66:
            r11.close()     // Catch: java.io.IOException -> L6a
            goto L9a
        L6a:
            android.util.Log.e(r3, r0)
            goto L9a
        L6e:
            java.io.File r8 = new java.io.File     // Catch: java.lang.Throwable -> L3c
            r8.<init>(r12, r14)     // Catch: java.lang.Throwable -> L3c
            long r9 = r8.length()     // Catch: java.lang.Throwable -> L3c
            int r9 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r9 != 0) goto L7e
            r8.delete()     // Catch: java.lang.Throwable -> L3c
        L7e:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L3c
            if (r13 == 0) goto L8a
            r13.close()     // Catch: java.io.IOException -> L87
            goto L8a
        L87:
            android.util.Log.e(r3, r2)
        L8a:
            java.io.FileOutputStream r13 = r11.fOut     // Catch: java.io.IOException -> L92
            if (r13 == 0) goto L95
            r13.close()     // Catch: java.io.IOException -> L92
            goto L95
        L92:
            android.util.Log.e(r3, r1)
        L95:
            java.io.BufferedOutputStream r11 = r11.bos     // Catch: java.io.IOException -> L6a
            if (r11 == 0) goto L9a
            goto L66
        L9a:
            changeFilePermission(r4)
            java.io.File r11 = new java.io.File
            r11.<init>(r12, r14)
            long r12 = r11.length()
            int r12 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r12 != 0) goto Lad
            r11.delete()
        Lad:
            return
        Lae:
            if (r13 == 0) goto Lb7
            r13.close()     // Catch: java.io.IOException -> Lb4
            goto Lb7
        Lb4:
            android.util.Log.e(r3, r2)
        Lb7:
            java.io.FileOutputStream r13 = r11.fOut     // Catch: java.io.IOException -> Lbf
            if (r13 == 0) goto Lc2
            r13.close()     // Catch: java.io.IOException -> Lbf
            goto Lc2
        Lbf:
            android.util.Log.e(r3, r1)
        Lc2:
            java.io.BufferedOutputStream r11 = r11.bos     // Catch: java.io.IOException -> Lca
            if (r11 == 0) goto Lcd
            r11.close()     // Catch: java.io.IOException -> Lca
            goto Lcd
        Lca:
            android.util.Log.e(r3, r0)
        Lcd:
            throw r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.general.font.FontWriter.copyFontFile(java.io.File,"
                    + " java.io.InputStream, java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:

       if (r7 != null) goto L12;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006c, code lost:

       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008a, code lost:

       if (r7 == null) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeLoc(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "sans.loc"
            java.lang.String r1 = "writeLoc : fOut.close() error"
            java.lang.String r2 = "writeLoc : osw.close() error"
            java.lang.String r3 = "FontWriter"
            java.lang.String r4 = "/data/app_fonts/"
            java.lang.String r5 = ""
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            int r4 = android.os.UserHandle.myUserId()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            r6.append(r4)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            java.lang.String r4 = r6.toString()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            r6.<init>(r4, r0)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            r6.createNewFile()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            java.lang.String r0 = r6.getAbsolutePath()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L78
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r7.fOut = r4     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.io.OutputStreamWriter r4 = new java.io.OutputStreamWriter     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.io.FileOutputStream r6 = r7.fOut     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r7.osw = r4     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r6.<init>()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r6.append(r8)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.String r8 = "\n"
            r6.append(r8)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.lang.String r8 = r6.toString()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r4.write(r8)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.io.OutputStreamWriter r8 = r7.osw     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r8.flush()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.io.FileOutputStream r8 = r7.fOut     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            r8.flush()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L76
            java.io.OutputStreamWriter r8 = r7.osw     // Catch: java.io.IOException -> L65
            if (r8 == 0) goto L68
            r8.close()     // Catch: java.io.IOException -> L65
            goto L68
        L65:
            android.util.Log.e(r3, r2)
        L68:
            java.io.FileOutputStream r7 = r7.fOut     // Catch: java.io.IOException -> L70
            if (r7 == 0) goto L8d
        L6c:
            r7.close()     // Catch: java.io.IOException -> L70
            goto L8d
        L70:
            android.util.Log.e(r3, r1)
            goto L8d
        L74:
            r8 = move-exception
            goto L97
        L76:
            r8 = move-exception
            goto L7a
        L78:
            r8 = move-exception
            r0 = r5
        L7a:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L74
            java.io.OutputStreamWriter r8 = r7.osw     // Catch: java.io.IOException -> L85
            if (r8 == 0) goto L88
            r8.close()     // Catch: java.io.IOException -> L85
            goto L88
        L85:
            android.util.Log.e(r3, r2)
        L88:
            java.io.FileOutputStream r7 = r7.fOut     // Catch: java.io.IOException -> L70
            if (r7 == 0) goto L8d
            goto L6c
        L8d:
            java.lang.String r7 = "persist.sys.flipfontpath"
            android.os.SystemProperties.set(r7, r5)
            changeFilePermission(r0)
            return
        L97:
            java.io.OutputStreamWriter r0 = r7.osw     // Catch: java.io.IOException -> L9f
            if (r0 == 0) goto La2
            r0.close()     // Catch: java.io.IOException -> L9f
            goto La2
        L9f:
            android.util.Log.e(r3, r2)
        La2:
            java.io.FileOutputStream r7 = r7.fOut     // Catch: java.io.IOException -> Laa
            if (r7 == 0) goto Lad
            r7.close()     // Catch: java.io.IOException -> Laa
            goto Lad
        Laa:
            android.util.Log.e(r3, r1)
        Lad:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.general.font.FontWriter.writeLoc(java.lang.String):void");
    }
}
