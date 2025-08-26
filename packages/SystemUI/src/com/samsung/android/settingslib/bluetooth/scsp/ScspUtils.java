package com.samsung.android.settingslib.bluetooth.scsp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
            fromInputStream.renderToCanvas(new Canvas(bitmapCreateBitmap));
            return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
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
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Log.d("ScspUtils", "deleteDirectory: listFiles is null");
                return;
            }
            for (File file2 : fileArrListFiles) {
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

    /* JADX WARN: Removed duplicated region for block: B:33:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BitmapDrawable getIcon(Context context, String str) {
        FileInputStream fileInputStream;
        int iRound = Math.round(context.getResources().getDimension(R.dimen.bt_nearby_icon_size));
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getIcon: resourcePath = ", str, "ScspUtils");
        FileInputStream fileInputStream2 = null;
        try {
            try {
                if (new File(str).exists()) {
                    fileInputStream = new FileInputStream(str);
                    try {
                        try {
                            BitmapDrawable bitmapDrawableBitmapFromSVG = bitmapFromSVG(context, fileInputStream, iRound, iRound);
                            fileInputStream.close();
                            return bitmapDrawableBitmapFromSVG;
                        } catch (Exception e) {
                            e = e;
                            Log.d("ScspUtils", "getIcon: fail, resourcePath = " + str);
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            return null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (Exception e2) {
                                Log.d("ScspUtils", "getIcon: fail close");
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (Exception e3) {
                Log.d("ScspUtils", "getIcon: fail close");
                e3.printStackTrace();
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
            }
            throw th;
        }
        return null;
    }

    public static BitmapDrawable getListIcon(Context context, String str) {
        Log.d("ScspUtils", "getListIcon: path = " + str);
        return getIcon(context, str + FILE_NAME + FILE_EXTENSION_SVG);
    }

    public static boolean makeAllResourceData(Context context, Uri uri) throws IOException {
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
                                    int i = zipInputStream.read(bArr);
                                    if (i == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, i);
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
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Log.d("ScspUtils", "removeOldDir: listFiles is null");
                return;
            }
            String string = Integer.toString(SystemProperties.getInt("ro.build.version.oneui", 0));
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory() && !string.equals(file2.getName())) {
                    deleteDirectory(file2);
                }
            }
        }
    }

    public static boolean saveFileFromUri(Context context, Uri uri, String str) throws IOException {
        Log.d("ScspUtils", "saveFileFromUri: uri.getLastPathSegment() = " + uri.getLastPathSegment());
        File file = new File(str, uri.getLastPathSegment());
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = inputStreamOpenInputStream.read(bArr);
                        if (i == -1) {
                            fileOutputStream.close();
                            inputStreamOpenInputStream.close();
                            Log.d("ScspUtils", "saveFileFromUri: true");
                            return true;
                        }
                        fileOutputStream.write(bArr, 0, i);
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
