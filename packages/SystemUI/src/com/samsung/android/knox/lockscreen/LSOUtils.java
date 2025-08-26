package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.SystemProperties;
import android.util.Log;
import android.view.WindowManager;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class LSOUtils {
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    public static int MAX_IMAGE_SIZE = 0;
    public static final String TAG = "LSO_LSOUtils";
    public static final String TEMP_DIR = ".tmp";
    public static final String TEMP_LSO_DIR = ".lso";

    public static void cleanDataLocalDirectory(Context context) {
        cleanDataLocalDirectory(context, TEMP_DIR);
    }

    public static int convertDipToPixel(Context context, int i) {
        return (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, i);
    }

    public static boolean convertImageFormat(String str, Bitmap.CompressFormat compressFormat, String str2, Point point) {
        return saveBitmapToFile(point != null ? getBitmap(str, point.x, point.y) : getBitmap(str), compressFormat, str2);
    }

    public static boolean convertImageFormatToSize(String str, Bitmap.CompressFormat compressFormat, String str2, Point point) {
        return saveBitmapToFile(point != null ? getBitmapBySize(str, point.x, point.y) : getBitmap(str), compressFormat, str2);
    }

    public static String copyFile(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        if (str == null || str2 == null) {
            Log.e(TAG, "copyFile() : invalid request. ");
            return null;
        }
        File file = new File(str);
        File file2 = new File(str2);
        try {
            file2.createNewFile();
            if (!file2.exists() || !file2.isFile()) {
                Log.e(TAG, "copyFile() : created file not exist. ");
                return null;
            }
            file2.setExecutable(true, false);
            file2.setReadable(true, false);
            file2.setWritable(true, true);
            try {
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        fileOutputStream = new FileOutputStream(file2);
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = null;
                    }
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i <= 0) {
                                fileOutputStream.flush();
                                fileInputStream.close();
                                fileOutputStream.close();
                                return str2;
                            }
                            fileOutputStream.write(bArr, 0, i);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                    fileInputStream = null;
                }
            } catch (Exception e) {
                Log.e(TAG, "copyFile() : fail to save image: ", e);
                file2.delete();
                return null;
            }
        } catch (IOException e2) {
            Log.e(TAG, "copyFile() : fail to create new file: ", e2);
            return null;
        }
    }

    public static String copyFileToDataLocalDirectory(Context context, String str) {
        return copyFileToDataLocalDirectory(context, TEMP_DIR, str, null);
    }

    public static boolean createRippleImage(String str, Bitmap.CompressFormat compressFormat, String str2) {
        Bitmap bitmapCreateRippleImage = createRippleImage(getBitmap(str));
        if (bitmapCreateRippleImage == null) {
            return false;
        }
        return saveBitmapToFile(bitmapCreateRippleImage, compressFormat, str2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|(1:4)(1:5)|6|57|(2:52|7)|(1:(1:20)(2:21|(1:23)(8:24|25|59|26|27|51|56|44)))(1:11)|12|25|59|26|27|51|56|44|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0090, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0092, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0094, code lost:
    
        android.util.Log.e(com.samsung.android.knox.lockscreen.LSOUtils.TAG, "decodeFile: error occurs. ", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0097, code lost:
    
        if (r12 != 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0099, code lost:
    
        r12.close();
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x009d, code lost:
    
        android.util.Log.e(com.samsung.android.knox.lockscreen.LSOUtils.TAG, "decodeFile: ioexception", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a2, code lost:
    
        if (r12 != 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a4, code lost:
    
        r12.close();
        r12 = r12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [int] */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v27, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v38 */
    /* JADX WARN: Type inference failed for: r12v39 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v40 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r12v7, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeFile(File file, int i, int i2) throws Throwable {
        Point bitmapSize;
        int i3;
        int i4;
        double dPow;
        int i5;
        int i6 = i > i2 ? i2 : i;
        Bitmap bitmapDecodeStream = null;
        bitmapDecodeStream = null;
        bitmapDecodeStream = null;
        bitmapDecodeStream = null;
        bitmapDecodeStream = null;
        FileInputStream fileInputStream = null;
        try {
        } catch (Throwable th) {
            th = th;
            fileInputStream = i;
        }
        try {
            try {
                bitmapSize = getBitmapSize(file);
                i3 = bitmapSize.y;
            } catch (IOException e) {
                Log.e(TAG, "decodeFile: error occurs. ", e);
            }
        } catch (IOException e2) {
            e = e2;
            i = 0;
        } catch (Exception e3) {
            e = e3;
            i = 0;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    Log.e(TAG, "decodeFile: error occurs. ", e4);
                }
            }
            throw th;
        }
        if (i3 > i2 && (i5 = bitmapSize.x) > i) {
            dPow = Math.pow(2.0d, (int) Math.round(Math.log(i6 / Math.max(i3, i5)) / Math.log(0.5d)));
        } else if (i3 > i2) {
            dPow = Math.pow(2.0d, (int) Math.round(Math.log(i2 / i3) / Math.log(0.5d)));
        } else {
            if (bitmapSize.x <= i) {
                i4 = 1;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = i4;
                i = new FileInputStream(file);
                bitmapDecodeStream = BitmapFactory.decodeStream(i, null, options);
                i.close();
                i = i;
                return bitmapDecodeStream;
            }
            dPow = Math.pow(2.0d, (int) Math.round(Math.log(((double) i) / r13) / Math.log(0.5d)));
        }
        i4 = (int) dPow;
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = i4;
        i = new FileInputStream(file);
        bitmapDecodeStream = BitmapFactory.decodeStream(i, null, options2);
        i.close();
        i = i;
        return bitmapDecodeStream;
    }

    public static void deleteFile(String str) {
        try {
            if (new File(str).delete()) {
                Log.d(TAG, "deleteFile() : File deleted. " + str);
            } else {
                Log.d(TAG, "deleteFile() : Delete operation has failed. " + str);
            }
        } catch (Exception e) {
            Log.e(TAG, "deleteFile() error occurs. fileName:" + str, e);
        }
    }

    public static void deleteRecursive(File file) {
        File[] fileArrListFiles;
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                deleteRecursive(file2);
            }
        }
        if (file.isDirectory()) {
            return;
        }
        file.delete();
    }

    public static Bitmap getBitmap(String str) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            Log.e(TAG, "Image not found: ".concat(str));
            return null;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getBitmap: ", e, TAG);
            return null;
        }
    }

    public static Bitmap getBitmapBySize(String str, int i, int i2) {
        Bitmap bitmapResizeBitmapByScaleAndCropCenter;
        Bitmap bitmapDecodeFile = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                Log.e(TAG, "Image not found: ".concat(str));
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getBitmapBySize: ", e, TAG);
        }
        return (bitmapDecodeFile == null || (bitmapResizeBitmapByScaleAndCropCenter = resizeBitmapByScaleAndCropCenter(bitmapDecodeFile, i, i2)) == null) ? bitmapDecodeFile : bitmapResizeBitmapByScaleAndCropCenter;
    }

    public static Point getBitmapSize(File file) throws Throwable {
        BitmapFactory.Options options;
        FileInputStream fileInputStream;
        Point point = new Point();
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    fileInputStream = new FileInputStream(file);
                } catch (IOException e) {
                    Log.e(TAG, "getBitmapSize: error occurs. ", e);
                    return point;
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            BitmapFactory.decodeStream(fileInputStream, null, options);
            point.x = options.outWidth;
            point.y = options.outHeight;
            fileInputStream.close();
            return point;
        } catch (IOException e4) {
            e = e4;
            fileInputStream2 = fileInputStream;
            Log.e(TAG, "getBitmapSize: ioexception. " + e);
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return point;
        } catch (Exception e5) {
            e = e5;
            fileInputStream2 = fileInputStream;
            Log.e(TAG, "getBitmapSize: error occurs. ", e);
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            return point;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e6) {
                    Log.e(TAG, "getBitmapSize: error occurs. ", e6);
                }
            }
            throw th;
        }
    }

    public static Drawable getDrawable(String str) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "getDrawable() - Image found: ".concat(str));
                return Drawable.createFromPath(file.getAbsolutePath());
            }
            Log.e(TAG, "getDrawable() - Image not found: ".concat(str));
            return null;
        } catch (Exception e) {
            Log.e(TAG, "getDrawable() error occurs. imagePath = ".concat(str), e);
            return null;
        }
    }

    public static Bitmap getMaxBitmap(String str, int i, int i2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                return decodeFile(file, i, i2);
            }
            Log.e(TAG, "Image not found: ".concat(str));
            return null;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getBitmap: ", e, TAG);
            return null;
        }
    }

    public static int getMaxImageSize(Context context) {
        int i = MAX_IMAGE_SIZE;
        if (i > 0) {
            return i;
        }
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getSize(point);
        int i2 = point.x;
        int i3 = point.y;
        if (i2 > i3) {
            MAX_IMAGE_SIZE = i2;
        } else {
            MAX_IMAGE_SIZE = i3;
        }
        return MAX_IMAGE_SIZE;
    }

    public static Drawable getResourceDrawable(Context context, int i) {
        Resources resources = context.getResources();
        if (resources == null || i == 0) {
            return null;
        }
        return resources.getDrawable(i);
    }

    public static String getResourceString(Context context, int i) {
        Resources resources = context.getResources();
        if (resources == null || i == 0) {
            return null;
        }
        return resources.getString(i);
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public static boolean mkDir(String str) {
        boolean z = false;
        try {
            File file = new File(str);
            if (file.exists()) {
                file.setExecutable(true, false);
                return true;
            }
            if (!file.mkdir()) {
                Log.e(TAG, "Failed to create directory: " + str);
                return false;
            }
            try {
                file.setReadable(true);
                file.setWritable(true);
                file.setExecutable(true, false);
                return true;
            } catch (Exception e) {
                e = e;
                z = true;
                Log.e(TAG, "mkDir() error occurs. dirPath=" + str, e);
                return z;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static Bitmap resizeBitmapByScaleAndCropCenter(Bitmap bitmap, int i, int i2) {
        if (i == bitmap.getWidth() && i2 == bitmap.getHeight()) {
            return bitmap;
        }
        float f = i;
        Bitmap bitmapCreateBitmap = null;
        try {
            float f2 = i2;
            float fMax = Math.max(f / bitmap.getWidth(), f2 / bitmap.getHeight());
            if (fMax > 1.0f) {
                fMax = Math.min(f / bitmap.getWidth(), f2 / bitmap.getHeight());
                if (fMax > 1.0f) {
                    fMax = 1.0f;
                }
            }
            Log.d(TAG, "resizeBitmapByScaleAndCropCenter scale:" + fMax);
            int iRound = Math.round(((float) bitmap.getWidth()) * fMax);
            int iRound2 = Math.round(((float) bitmap.getHeight()) * fMax);
            bitmapCreateBitmap = (iRound < i || iRound2 < i2) ? Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(iRound, iRound2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-16777216);
            if (iRound < i || iRound2 < i2) {
                canvas.translate((i - iRound) / 2.0f, (i2 - iRound2) / 2.0f);
            }
            canvas.scale(fMax, fMax);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(6));
            return bitmapCreateBitmap;
        } catch (Exception e) {
            Log.e(TAG, "resizeBitmapAndCropCenter: ", e);
            return bitmapCreateBitmap;
        }
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String str) throws IOException {
        File file = new File(str);
        try {
            file.createNewFile();
            if (!file.exists() || !file.isFile()) {
                Log.e(TAG, "saveBitmapToFile() : created file not exist: ");
                return false;
            }
            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, true);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    return saveBitmapToOutputStream(bitmap, compressFormat, fileOutputStream);
                } finally {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "saveBitmapToFile() : fail to save image: ", e);
                file.delete();
                return false;
            }
        } catch (IOException e2) {
            Log.e(TAG, "saveBitmapToFile() : fail to create new file: ", e2);
            return false;
        }
    }

    public static boolean saveBitmapToOutputStream(Bitmap bitmap, Bitmap.CompressFormat compressFormat, OutputStream outputStream) {
        boolean zCompress = false;
        if (bitmap == null) {
            return false;
        }
        try {
            zCompress = bitmap.compress(compressFormat, 100, outputStream);
            if (zCompress) {
                return zCompress;
            }
            Log.e(TAG, "saveBitmapToOutputStream() : Bitmap write error!");
            return zCompress;
        } catch (Exception e) {
            Log.e(TAG, "saveBitmapToOutputStream() : error occurs. ", e);
            return zCompress;
        }
    }

    public static Bitmap scaledBitmap(Bitmap bitmap, int i, int i2) {
        try {
            if (bitmap.getWidth() <= i && bitmap.getHeight() <= i2) {
                return bitmap;
            }
            return Bitmap.createScaledBitmap(bitmap, i, i2, false);
        } catch (Exception e) {
            Log.e(TAG, "scaledBitmap: error occurs. ", e);
            return null;
        }
    }

    public static void cleanDataLocalDirectory(Context context, String str) {
        if (str != null) {
            deleteRecursive(new File(context.getFilesDir() + "/" + str));
        }
    }

    public static String copyFileToDataLocalDirectory(Context context, String str, String str2) {
        return copyFileToDataLocalDirectory(context, TEMP_DIR, str, str2);
    }

    public static String copyFileToDataLocalDirectory(Context context, String str, String str2, String str3) throws Throwable {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        if (str != null) {
            absolutePath = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(absolutePath, "/", str);
            if (!mkDir(absolutePath)) {
                return null;
            }
        }
        if (str3 == null) {
            str3 = "";
        }
        String strCopyFile = copyFile(str2, absolutePath + "/" + str3 + new File(str2).getName());
        if (strCopyFile == null && str != null) {
            deleteRecursive(new File(absolutePath));
        }
        return strCopyFile;
    }

    public static Bitmap createRippleImage(Bitmap bitmap) {
        float f;
        Bitmap bitmapCreateBitmap = null;
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int iMax = Math.max(width, height);
        float f2 = 0.0f;
        if (width < height) {
            f2 = (height - width) / 2.0f;
            f = 0.0f;
        } else {
            f = (width - height) / 2.0f;
        }
        try {
            bitmapCreateBitmap = Bitmap.createBitmap(iMax, iMax, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-16777216);
            canvas.drawBitmap(bitmap, f2, f, new Paint(2));
            return bitmapCreateBitmap;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("createRippleImage: ", e, TAG);
            return bitmapCreateBitmap;
        }
    }

    public static Bitmap getBitmap(String str, int i) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                return decodeFile(file, i, i);
            }
            Log.e(TAG, "Image not found: ".concat(str));
            return null;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getBitmap: ", e, TAG);
            return null;
        }
    }

    public static Bitmap getBitmap(String str, int i, int i2) {
        Bitmap bitmap = getBitmap(str, i2 > i ? i2 : i);
        Bitmap bitmapCreateBitmap = null;
        if (bitmap == null) {
            return null;
        }
        Rect rect = new Rect();
        rect.top = 0;
        rect.left = 0;
        rect.right = bitmap.getWidth();
        rect.bottom = bitmap.getHeight();
        RectF rectF = new RectF();
        rectF.top = 0.0f;
        rectF.left = 0.0f;
        rectF.bottom = i2;
        rectF.right = i;
        int iWidth = rect.width() - ((int) rectF.width());
        int iHeight = rect.height() - ((int) rectF.height());
        if (iWidth <= 0 && iHeight <= 0) {
            return bitmap;
        }
        if (iWidth > 0) {
            int i3 = iWidth / 2;
            rect.left += i3;
            rect.right -= i3;
        } else {
            float f = iWidth / 2;
            rectF.left -= f;
            rectF.right += f;
        }
        if (iHeight > 0) {
            int i4 = iHeight / 2;
            rect.top += i4;
            rect.bottom -= i4;
        } else {
            float f2 = iHeight / 2;
            rectF.top -= f2;
            rectF.bottom += f2;
        }
        try {
            bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-16777216);
            canvas.drawBitmap(bitmap, rect, rectF, new Paint(2));
            return bitmapCreateBitmap;
        } catch (Exception e) {
            Log.e(TAG, "getBitmap: failed. ", e);
            return bitmapCreateBitmap;
        }
    }
}
