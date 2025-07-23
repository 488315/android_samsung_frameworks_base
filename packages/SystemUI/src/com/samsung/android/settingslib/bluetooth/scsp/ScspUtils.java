package com.samsung.android.settingslib.bluetooth.scsp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ScspUtils {
    public static final String FILE_EXTENSION_SVG;
    public static final String FILE_NAME;
    public static final String FILE_NAME_AURA_CAST;
    public static final String FILE_NAME_TABLE;
    public static final String FILE_PATH_DEVICE_ID;
    public static final String FILE_PATH_ICON_INDEX;
    public static final String FILE_PATH_ROOT;
    public static final String FILE_PATH_SOLID;

    static {
        StringBuilder sb = new StringBuilder("bluetooth");
        String str = File.separator;
        FILE_PATH_ROOT = MutablePreferences$$ExternalSyntheticOutline0.m(sb, str, "scsp", str);
        FILE_PATH_DEVICE_ID = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("deviceid", str);
        FILE_PATH_ICON_INDEX = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("iconindex", str);
        FILE_PATH_SOLID = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("QSIcon", str);
        FILE_NAME = "icon";
        FILE_NAME_AURA_CAST = "icon_auracast";
        FILE_EXTENSION_SVG = ".svg";
        FILE_NAME_TABLE = "table.txt";
    }

    public static BitmapDrawable bitmapFromSVG(Context context, FileInputStream fileInputStream, int i, int i2) {
        try {
            SVG fromInputStream = SVG.getFromInputStream(fileInputStream);
            int i3 = (int) ((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * i);
            int i4 = (int) ((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * i2);
            fromInputStream.setDocumentWidth(i3);
            fromInputStream.setDocumentHeight(i4);
            Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
            fromInputStream.renderToCanvas(new Canvas(createBitmap));
            return new BitmapDrawable(context.getResources(), createBitmap);
        } catch (SVGParseException e) {
            Log.e("ScspUtils", "bitmapFromSVG: SVGParseException.", e);
            return null;
        } catch (NullPointerException e2) {
            Log.e("ScspUtils", "bitmapFromSVG: NullPointerException.", e2);
            return null;
        }
    }

    public static String byteToString(byte b) {
        StringBuilder sb = new StringBuilder(2);
        sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        return sb.toString();
    }

    public static void deleteDirectory(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                Log.d("ScspUtils", "deleteDirectory: listFiles is null");
                return;
            }
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                } else {
                    try {
                        file2.delete();
                    } catch (Exception e) {
                        Log.d("ScspUtils", "deleteDirectory: file delete fail");
                        e.printStackTrace();
                    }
                }
            }
            try {
                file.delete();
            } catch (Exception e2) {
                Log.d("ScspUtils", "deleteDirectory: directory delete fail");
                e2.printStackTrace();
            }
        }
    }

    public static String getFileDirPath(Context context) {
        return getFileRootPath(context) + Integer.toString(SystemProperties.getInt("ro.build.version.oneui", 0)) + File.separator;
    }

    public static String getFileRootPath(Context context) {
        return context.getApplicationContext().getFilesDir() + File.separator + FILE_PATH_ROOT;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.drawable.BitmapDrawable getIcon(android.content.Context r6, java.lang.String r7) {
        /*
            android.content.res.Resources r0 = r6.getResources()
            r1 = 2131165519(0x7f07014f, float:1.7945257E38)
            float r0 = r0.getDimension(r1)
            int r0 = java.lang.Math.round(r0)
            java.lang.String r1 = "getIcon: fail close"
            java.lang.String r2 = "getIcon: fail, resourcePath = "
            java.lang.String r3 = "getIcon: resourcePath = "
            java.lang.String r4 = "ScspUtils"
            android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r3, r7, r4)
            r3 = 0
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r5.<init>(r7)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            boolean r5 = r5.exists()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            if (r5 == 0) goto L5b
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            r5.<init>(r7)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L42
            android.graphics.drawable.BitmapDrawable r3 = bitmapFromSVG(r6, r5, r0, r0)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3e
            r5.close()     // Catch: java.lang.Exception -> L33
            return r3
        L33:
            r6 = move-exception
            android.util.Log.d(r4, r1)
            r6.printStackTrace()
            goto L5b
        L3b:
            r6 = move-exception
            r3 = r5
            goto L5c
        L3e:
            r6 = move-exception
            goto L44
        L40:
            r6 = move-exception
            goto L5c
        L42:
            r6 = move-exception
            r5 = r3
        L44:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L3b
            r0.append(r7)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r7 = r0.toString()     // Catch: java.lang.Throwable -> L3b
            android.util.Log.d(r4, r7)     // Catch: java.lang.Throwable -> L3b
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L3b
            if (r5 == 0) goto L5b
            r5.close()     // Catch: java.lang.Exception -> L33
        L5b:
            return r3
        L5c:
            if (r3 == 0) goto L69
            r3.close()     // Catch: java.lang.Exception -> L62
            goto L69
        L62:
            r7 = move-exception
            android.util.Log.d(r4, r1)
            r7.printStackTrace()
        L69:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settingslib.bluetooth.scsp.ScspUtils.getIcon(android.content.Context, java.lang.String):android.graphics.drawable.BitmapDrawable");
    }

    public static BitmapDrawable getListIcon(Context context, String str) {
        Log.d("ScspUtils", "getListIcon: path = " + str);
        return getIcon(context, str + FILE_NAME + FILE_EXTENSION_SVG);
    }

    public static boolean makeAllResourceData(Context context, Uri uri) {
        Log.d("ScspUtils", "makeAllResourceData: uri.getLastPathSegment() = " + uri.getLastPathSegment());
        if (!saveFileFromUri(context, uri, getFileDirPath(context))) {
            Log.d("ScspUtils", "makeAllResourceData: save fail. uri = " + uri.toString());
            return false;
        }
        String str = getFileDirPath(context) + uri.getLastPathSegment();
        String fileDirPath = getFileDirPath(context);
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry == null) {
                            zipInputStream.close();
                            fileInputStream.close();
                            return true;
                        }
                        String str2 = fileDirPath + File.separator + nextEntry.getName();
                        if (nextEntry.isDirectory()) {
                            new File(str2).mkdirs();
                        } else {
                            File file = new File(str2);
                            File parentFile = file.getParentFile();
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = zipInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                fileOutputStream.close();
                            } finally {
                            }
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.d("ScspUtils", "unzip: unzip fail, FileNotFoundException");
            e.printStackTrace();
            Log.d("ScspUtils", "makeAllResourceData: unzip fail. uri = " + uri.toString());
            return false;
        } catch (Exception e2) {
            Log.d("ScspUtils", "unzip: unzip fail, Exception");
            e2.printStackTrace();
            Log.d("ScspUtils", "makeAllResourceData: unzip fail. uri = " + uri.toString());
            return false;
        }
    }

    public static String makeByteArrayDir(byte[] bArr) {
        if (bArr.length < 2) {
            Log.e("ScspUtils", "makeByteArrayDir: byte length is short. length: " + bArr.length);
        }
        try {
            return byteToString(bArr[0]) + "_" + byteToString(bArr[1]);
        } catch (NumberFormatException e) {
            Log.e("ScspUtils", "makeByteArrayDir: NumberFormatException.", e);
            return null;
        }
    }

    public static void removeOldDir(Context context) {
        File file = new File(getFileRootPath(context));
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                Log.d("ScspUtils", "removeOldDir: listFiles is null");
                return;
            }
            String num = Integer.toString(SystemProperties.getInt("ro.build.version.oneui", 0));
            for (File file2 : listFiles) {
                if (file2.isDirectory() && !num.equals(file2.getName())) {
                    deleteDirectory(file2);
                }
            }
        }
    }

    public static boolean saveFileFromUri(Context context, Uri uri, String str) {
        Log.d("ScspUtils", "saveFileFromUri: uri.getLastPathSegment() = " + uri.getLastPathSegment());
        File file = new File(str, uri.getLastPathSegment());
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = openInputStream.read(bArr);
                        if (read == -1) {
                            fileOutputStream.close();
                            openInputStream.close();
                            Log.d("ScspUtils", "saveFileFromUri: true");
                            return true;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e("TAG", "saveFileFromUri: Error saving file", e);
            return false;
        }
    }
}
