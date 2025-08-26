package com.android.systemui.pluginlock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap fitToCoverScreen(Context context, Bitmap bitmap) {
        return fitToScreen(context, bitmap, true);
    }

    private static Bitmap fitToScreen(Context context, Bitmap bitmap, boolean z) {
        int i;
        Point realScreenSize = WallpaperUtils.getRealScreenSize(context, z);
        Log.d(TAG, "fitToScreen: screenSize = " + realScreenSize + ", isForceCoverScreen = " + z);
        int i2 = realScreenSize.x;
        if (i2 <= 0 || (i = realScreenSize.y) <= 0) {
            Log.w(TAG, "fitToScreen: Can not resize");
            return bitmap;
        }
        int i3 = context.getResources().getConfiguration().orientation;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i3, "fitToScreen: orientation = ", TAG);
        if (!z && i3 == 2) {
            i2 = realScreenSize.y;
            i = realScreenSize.x;
        }
        if (bitmap == null) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawColor(-16777216);
            Log.w(TAG, "fitToScreen: bitmap is null, return blank bitmap");
            return bitmapCreateBitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = width;
        float f2 = height;
        float f3 = i2;
        float f4 = i;
        float f5 = f3 / f4 > f / f2 ? f3 / f : f4 / f2;
        Log.d(TAG, "fitToScreen: scale = " + f5);
        if (f5 == 1.0f) {
            return bitmap;
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(width, height, "fitToScreen: original width = ", ", height = ", TAG);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f * f5), (int) (f2 * f5), true);
        if (bitmapCreateScaledBitmap != bitmap) {
            Log.d(TAG, "fitToScreen: Recycle. bitmap = " + bitmap);
            bitmap.recycle();
        }
        Log.d(TAG, "fitToScreen: Resized width = " + bitmapCreateScaledBitmap.getWidth() + ", height = " + bitmapCreateScaledBitmap.getHeight());
        return bitmapCreateScaledBitmap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap getBitmapFromPath(Context context, String str, boolean z, boolean z2) throws IOException {
        Bitmap bitmap;
        ?? r3;
        Bitmap bitmap2;
        BitmapFactory.Options options;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getBitmapFromPath() path:", str, TAG);
        Bitmap bitmap3 = null;
        Bitmap bitmapDecodeStreamConsiderQMG = null;
        bitmap3 = null;
        bitmap3 = null;
        FileInputStream fileInputStream3 = null;
        bitmap3 = null;
        if (str != null) {
            try {
                File file = new File(str);
                ?? sb = new StringBuilder("getBitmapFromPath() file.exists():");
                sb.append(file.exists());
                Log.d(TAG, sb.toString());
                if (file.exists() && file.canRead()) {
                    try {
                        options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        boolean z3 = WallpaperUtils.mIsExternalLiveWallpaper;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        fileInputStream = new FileInputStream(str);
                        try {
                            WallpaperUtils.decodeStreamConsiderQMG(fileInputStream, null, options);
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                e = e;
                                e.printStackTrace();
                                int i = options.outWidth;
                                int i2 = options.outHeight;
                                Log.d(TAG, "getBitmapFromPath() width:" + i + ", height:" + i2);
                                fileInputStream2 = new FileInputStream(file);
                                Rect rect = new Rect(0, 0, i, i2);
                                options.inJustDecodeBounds = false;
                                bitmapDecodeStreamConsiderQMG = WallpaperUtils.decodeStreamConsiderQMG(fileInputStream2, rect, options);
                                Log.d(TAG, "getBitmapFromPath() bitmap:" + bitmapDecodeStreamConsiderQMG);
                                bitmap2 = bitmapDecodeStreamConsiderQMG;
                                fileInputStream3 = fileInputStream2;
                                sb = i2;
                                bitmap3 = bitmap2;
                                if (z) {
                                }
                            }
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e = e3;
                                    e.printStackTrace();
                                    int i3 = options.outWidth;
                                    int i22 = options.outHeight;
                                    Log.d(TAG, "getBitmapFromPath() width:" + i3 + ", height:" + i22);
                                    fileInputStream2 = new FileInputStream(file);
                                    Rect rect2 = new Rect(0, 0, i3, i22);
                                    options.inJustDecodeBounds = false;
                                    bitmapDecodeStreamConsiderQMG = WallpaperUtils.decodeStreamConsiderQMG(fileInputStream2, rect2, options);
                                    Log.d(TAG, "getBitmapFromPath() bitmap:" + bitmapDecodeStreamConsiderQMG);
                                    bitmap2 = bitmapDecodeStreamConsiderQMG;
                                    fileInputStream3 = fileInputStream2;
                                    sb = i22;
                                    bitmap3 = bitmap2;
                                    if (z) {
                                    }
                                }
                            }
                            int i32 = options.outWidth;
                            int i222 = options.outHeight;
                            Log.d(TAG, "getBitmapFromPath() width:" + i32 + ", height:" + i222);
                            fileInputStream2 = new FileInputStream(file);
                            Rect rect22 = new Rect(0, 0, i32, i222);
                            options.inJustDecodeBounds = false;
                            bitmapDecodeStreamConsiderQMG = WallpaperUtils.decodeStreamConsiderQMG(fileInputStream2, rect22, options);
                            Log.d(TAG, "getBitmapFromPath() bitmap:" + bitmapDecodeStreamConsiderQMG);
                            bitmap2 = bitmapDecodeStreamConsiderQMG;
                            fileInputStream3 = fileInputStream2;
                            sb = i222;
                            bitmap3 = bitmap2;
                            if (z) {
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        fileInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        sb = 0;
                        if (sb != 0) {
                            try {
                                sb.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        throw th;
                    }
                    int i322 = options.outWidth;
                    int i2222 = options.outHeight;
                    Log.d(TAG, "getBitmapFromPath() width:" + i322 + ", height:" + i2222);
                    fileInputStream2 = new FileInputStream(file);
                    try {
                        Rect rect222 = new Rect(0, 0, i322, i2222);
                        options.inJustDecodeBounds = false;
                        bitmapDecodeStreamConsiderQMG = WallpaperUtils.decodeStreamConsiderQMG(fileInputStream2, rect222, options);
                        Log.d(TAG, "getBitmapFromPath() bitmap:" + bitmapDecodeStreamConsiderQMG);
                        bitmap2 = bitmapDecodeStreamConsiderQMG;
                        fileInputStream3 = fileInputStream2;
                        sb = i2222;
                    } catch (Throwable th3) {
                        th = th3;
                        bitmap = bitmapDecodeStreamConsiderQMG;
                        r3 = fileInputStream2;
                        try {
                            Log.w(TAG, "Can't load dynamic lock wallpaper!", th);
                            th.printStackTrace();
                            if (r3 != 0) {
                                try {
                                    r3.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            bitmap3 = bitmap;
                            if (z) {
                            }
                        } finally {
                            if (r3 != 0) {
                                try {
                                    r3.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    Log.w(TAG, "Can't load dynamic lock file");
                    bitmap2 = null;
                    sb = sb;
                }
                bitmap3 = bitmap2;
            } catch (Throwable th4) {
                th = th4;
                bitmap = bitmap3;
                r3 = bitmap3;
            }
        }
        return z ? fitToScreen(context, bitmap3, z2) : bitmap3;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap getBitmapFromUri(Context context, Uri uri, boolean z, boolean z2) throws IOException {
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor;
        Log.d(TAG, "getBitmapFromPath() uri:" + uri);
        Bitmap bitmapDecodeFileDescriptor = null;
        try {
            if (uri != null) {
                try {
                    parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            FileDescriptor fileDescriptor = parcelFileDescriptorOpenFileDescriptor.getFileDescriptor();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            Bitmap.Config config = Bitmap.Config.ARGB_8888;
                            options.inPreferredConfig = config;
                            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                            Rect rect = new Rect(0, 0, options.outWidth, options.outHeight);
                            BitmapFactory.Options options2 = new BitmapFactory.Options();
                            options2.inPreferredConfig = config;
                            bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, rect, options2);
                        } catch (Throwable th) {
                            th = th;
                            try {
                                th.printStackTrace();
                                if (parcelFileDescriptorOpenFileDescriptor != null) {
                                    parcelFileDescriptorOpenFileDescriptor.close();
                                }
                                if (!z) {
                                }
                            } catch (Throwable th2) {
                                if (parcelFileDescriptorOpenFileDescriptor != null) {
                                    try {
                                        parcelFileDescriptorOpenFileDescriptor.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                throw th2;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    parcelFileDescriptorOpenFileDescriptor = null;
                }
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return !z ? fitToScreen(context, bitmapDecodeFileDescriptor, z2) : bitmapDecodeFileDescriptor;
    }
}
