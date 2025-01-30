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
import android.util.TypedValue;
import android.view.WindowManager;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOUtils {
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    public static int MAX_IMAGE_SIZE = 0;
    public static final String TAG = "LSO_LSOUtils";
    public static final String TEMP_DIR = ".tmp";
    public static final String TEMP_LSO_DIR = ".lso";

    public static void cleanDataLocalDirectory(Context context) {
        cleanDataLocalDirectory(context, TEMP_DIR);
    }

    public static int convertDipToPixel(Context context, int i) {
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    public static boolean convertImageFormat(String str, Bitmap.CompressFormat compressFormat, String str2, Point point) {
        return saveBitmapToFile(point != null ? getBitmap(str, point.x, point.y) : getBitmap(str), compressFormat, str2);
    }

    public static boolean convertImageFormatToSize(String str, Bitmap.CompressFormat compressFormat, String str2, Point point) {
        return saveBitmapToFile(point != null ? getBitmapBySize(str, point.x, point.y) : getBitmap(str), compressFormat, str2);
    }

    public static String copyFile(String str, String str2) {
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
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (read <= 0) {
                                    fileOutputStream.flush();
                                    fileInputStream.close();
                                    fileOutputStream.close();
                                    return str2;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "copyFile() : fail to save image: ", e);
                    file2.delete();
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                fileInputStream = null;
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
        Bitmap createRippleImage = createRippleImage(getBitmap(str));
        if (createRippleImage == null) {
            return false;
        }
        return saveBitmapToFile(createRippleImage, compressFormat, str2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|1|(1:3)(1:55)|4|5|(2:6|7)|(1:(1:34)(2:35|(1:37)(8:38|13|14|15|16|17|19|20)))(1:11)|12|13|14|15|16|17|19|20|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0086, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0097, code lost:
    
        android.util.Log.e(com.samsung.android.knox.lockscreen.LSOUtils.TAG, "decodeFile: ioexception", r11);
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009c, code lost:
    
        if (r12 != null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009e, code lost:
    
        r12.close();
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0084, code lost:
    
        r11 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x008c, code lost:
    
        android.util.Log.e(com.samsung.android.knox.lockscreen.LSOUtils.TAG, "decodeFile: error occurs. ", r11);
        r12 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008f, code lost:
    
        if (r12 != null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0091, code lost:
    
        r12.close();
        r12 = r12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r12v0, types: [int] */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v37 */
    /* JADX WARN: Type inference failed for: r12v38 */
    /* JADX WARN: Type inference failed for: r12v39 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v40 */
    /* JADX WARN: Type inference failed for: r12v41 */
    /* JADX WARN: Type inference failed for: r12v42 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x0080 -> B:17:0x00a1). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap decodeFile(File file, int i, int i2) {
        Throwable th;
        FileInputStream fileInputStream;
        Point bitmapSize;
        int i3;
        int i4;
        double pow;
        int i5;
        int i6 = i > i2 ? i2 : i;
        Bitmap bitmap = null;
        bitmap = null;
        bitmap = null;
        bitmap = null;
        bitmap = null;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    bitmapSize = getBitmapSize(file);
                    i3 = bitmapSize.y;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = i;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e) {
                            Log.e(TAG, "decodeFile: error occurs. ", e);
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                fileInputStream = null;
            } catch (Exception e3) {
                e = e3;
                fileInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream2 != null) {
                }
                throw th;
            }
        } catch (IOException e4) {
            Log.e(TAG, "decodeFile: error occurs. ", e4);
            i = i;
        }
        if (i3 > i2 && (i5 = bitmapSize.x) > i) {
            pow = Math.pow(2.0d, (int) Math.round(Math.log(i6 / Math.max(i3, i5)) / Math.log(0.5d)));
        } else if (i3 > i2) {
            pow = Math.pow(2.0d, (int) Math.round(Math.log(i2 / i3) / Math.log(0.5d)));
        } else {
            if (bitmapSize.x <= i) {
                i4 = 1;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = i4;
                fileInputStream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
                fileInputStream.close();
                i = fileInputStream;
                return bitmap;
            }
            pow = Math.pow(2.0d, (int) Math.round(Math.log(((double) i) / r13) / Math.log(0.5d)));
        }
        i4 = (int) pow;
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = i4;
        fileInputStream = new FileInputStream(file);
        bitmap = BitmapFactory.decodeStream(fileInputStream, null, options2);
        fileInputStream.close();
        i = fileInputStream;
        return bitmap;
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
        File[] listFiles;
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                deleteRecursive(file2);
            }
        }
        if (file.isDirectory()) {
            return;
        }
        file.delete();
    }

    public static Bitmap getBitmap(String str) {
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                Log.e(TAG, "Image not found: ".concat(str));
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getBitmap: ", e, TAG);
        }
        return bitmap;
    }

    public static Bitmap getBitmapBySize(String str, int i, int i2) {
        Bitmap resizeBitmapByScaleAndCropCenter;
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                Log.e(TAG, "Image not found: ".concat(str));
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getBitmapBySize: ", e, TAG);
        }
        return (bitmap == null || (resizeBitmapByScaleAndCropCenter = resizeBitmapByScaleAndCropCenter(bitmap, i, i2)) == null) ? bitmap : resizeBitmapByScaleAndCropCenter;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x003e -> B:9:0x0057). Please report as a decompilation issue!!! */
    public static Point getBitmapSize(File file) {
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
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException e) {
                e = e;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (IOException e3) {
            Log.e(TAG, "getBitmapSize: error occurs. ", e3);
        }
        try {
            BitmapFactory.decodeStream(fileInputStream, null, options);
            point.x = options.outWidth;
            point.y = options.outHeight;
            fileInputStream.close();
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
        return point;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.graphics.drawable.Drawable] */
    public static Drawable getDrawable(String str) {
        Drawable drawable = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "getDrawable() - Image found: ".concat(str));
                ?? createFromPath = Drawable.createFromPath(file.getAbsolutePath());
                drawable = createFromPath;
                str = createFromPath;
            } else {
                Log.e(TAG, "getDrawable() - Image not found: ".concat(str));
                str = str;
            }
        } catch (Exception e) {
            Log.e(TAG, "getDrawable() error occurs. imagePath = ".concat(str), e);
        }
        return drawable;
    }

    public static Bitmap getMaxBitmap(String str, int i, int i2) {
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                bitmap = decodeFile(file, i, i2);
            } else {
                Log.e(TAG, "Image not found: ".concat(str));
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getBitmap: ", e, TAG);
        }
        return bitmap;
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
            } else {
                if (!file.mkdir()) {
                    Log.e(TAG, "Failed to create directory: " + str);
                    return false;
                }
                try {
                    file.setReadable(true);
                    file.setWritable(true);
                    file.setExecutable(true, false);
                } catch (Exception e) {
                    e = e;
                    z = true;
                    Log.e(TAG, "mkDir() error occurs. dirPath=" + str, e);
                    return z;
                }
            }
            return true;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static Bitmap resizeBitmapByScaleAndCropCenter(Bitmap bitmap, int i, int i2) {
        if (i == bitmap.getWidth() && i2 == bitmap.getHeight()) {
            return bitmap;
        }
        float f = i;
        Bitmap bitmap2 = null;
        try {
            float f2 = i2;
            float max = Math.max(f / bitmap.getWidth(), f2 / bitmap.getHeight());
            if (max > 1.0f) {
                max = Math.min(f / bitmap.getWidth(), f2 / bitmap.getHeight());
                if (max > 1.0f) {
                    max = 1.0f;
                }
            }
            Log.d(TAG, "resizeBitmapByScaleAndCropCenter scale:" + max);
            int round = Math.round(((float) bitmap.getWidth()) * max);
            int round2 = Math.round(((float) bitmap.getHeight()) * max);
            bitmap2 = (round < i || round2 < i2) ? Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(round, round2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawColor(EmergencyPhoneWidget.BG_COLOR);
            if (round < i || round2 < i2) {
                canvas.translate((i - round) / 2.0f, (i2 - round2) / 2.0f);
            }
            canvas.scale(max, max);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(6));
            return bitmap2;
        } catch (Exception e) {
            Log.e(TAG, "resizeBitmapAndCropCenter: ", e);
            return bitmap2;
        }
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String str) {
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
        boolean z = false;
        if (bitmap == null) {
            return false;
        }
        try {
            z = bitmap.compress(compressFormat, 100, outputStream);
            if (!z) {
                Log.e(TAG, "saveBitmapToOutputStream() : Bitmap write error!");
            }
        } catch (Exception e) {
            Log.e(TAG, "saveBitmapToOutputStream() : error occurs. ", e);
        }
        return z;
    }

    public static Bitmap scaledBitmap(Bitmap bitmap, int i, int i2) {
        try {
            return (bitmap.getWidth() > i || bitmap.getHeight() > i2) ? Bitmap.createScaledBitmap(bitmap, i, i2, false) : bitmap;
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

    public static String copyFileToDataLocalDirectory(Context context, String str, String str2, String str3) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        if (str != null) {
            absolutePath = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(absolutePath, "/", str);
            if (!mkDir(absolutePath)) {
                return null;
            }
        }
        if (str3 == null) {
            str3 = "";
        }
        String copyFile = copyFile(str2, absolutePath + "/" + str3 + new File(str2).getName());
        if (copyFile == null && str != null) {
            deleteRecursive(new File(absolutePath));
        }
        return copyFile;
    }

    public static Bitmap createRippleImage(Bitmap bitmap) {
        float f;
        Bitmap bitmap2 = null;
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        float f2 = 0.0f;
        if (width < height) {
            f2 = (height - width) / 2.0f;
            f = 0.0f;
        } else {
            f = (width - height) / 2.0f;
        }
        try {
            bitmap2 = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawColor(EmergencyPhoneWidget.BG_COLOR);
            canvas.drawBitmap(bitmap, f2, f, new Paint(2));
            return bitmap2;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("createRippleImage: ", e, TAG);
            return bitmap2;
        }
    }

    public static Bitmap getBitmap(String str, int i) {
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str);
            if (file.exists()) {
                Log.d(TAG, "Image found: ".concat(str));
                bitmap = decodeFile(file, i, i);
            } else {
                Log.e(TAG, "Image not found: ".concat(str));
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getBitmap: ", e, TAG);
        }
        return bitmap;
    }

    public static Bitmap getBitmap(String str, int i, int i2) {
        Bitmap bitmap = getBitmap(str, i2 > i ? i2 : i);
        Bitmap bitmap2 = null;
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
        int width = rect.width() - ((int) rectF.width());
        int height = rect.height() - ((int) rectF.height());
        if (width <= 0 && height <= 0) {
            return bitmap;
        }
        if (width > 0) {
            int i3 = width / 2;
            rect.left += i3;
            rect.right -= i3;
        } else {
            float f = width / 2;
            rectF.left -= f;
            rectF.right += f;
        }
        if (height > 0) {
            int i4 = height / 2;
            rect.top += i4;
            rect.bottom -= i4;
        } else {
            float f2 = height / 2;
            rectF.top -= f2;
            rectF.bottom += f2;
        }
        try {
            bitmap2 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawColor(EmergencyPhoneWidget.BG_COLOR);
            canvas.drawBitmap(bitmap, rect, rectF, new Paint(2));
            return bitmap2;
        } catch (Exception e) {
            Log.e(TAG, "getBitmap: failed. ", e);
            return bitmap2;
        }
    }
}
