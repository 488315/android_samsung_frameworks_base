package com.android.systemui.pluginlock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperUtils;

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
            Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawColor(-16777216);
            Log.w(TAG, "fitToScreen: bitmap is null, return blank bitmap");
            return createBitmap;
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
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f * f5), (int) (f2 * f5), true);
        if (createScaledBitmap != bitmap) {
            Log.d(TAG, "fitToScreen: Recycle. bitmap = " + bitmap);
            bitmap.recycle();
        }
        Log.d(TAG, "fitToScreen: Resized width = " + createScaledBitmap.getWidth() + ", height = " + createScaledBitmap.getHeight());
        return createScaledBitmap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:4:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:7:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap getBitmapFromPath(android.content.Context r8, java.lang.String r9, boolean r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.utils.BitmapUtils.getBitmapFromPath(android.content.Context, java.lang.String, boolean, boolean):android.graphics.Bitmap");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap getBitmapFromUri(android.content.Context r7, android.net.Uri r8, boolean r9, boolean r10) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getBitmapFromPath() uri:"
            r0.<init>(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "BitmapUtils"
            android.util.Log.d(r1, r0)
            r0 = 0
            if (r8 == 0) goto L6f
            android.content.ContentResolver r1 = r7.getContentResolver()     // Catch: java.lang.Throwable -> L58
            java.lang.String r2 = "r"
            android.os.ParcelFileDescriptor r8 = r1.openFileDescriptor(r8, r2)     // Catch: java.lang.Throwable -> L58
            if (r8 == 0) goto L4d
            java.io.FileDescriptor r1 = r8.getFileDescriptor()     // Catch: java.lang.Throwable -> L4b
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L4b
            r2.<init>()     // Catch: java.lang.Throwable -> L4b
            r3 = 1
            r2.inJustDecodeBounds = r3     // Catch: java.lang.Throwable -> L4b
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Throwable -> L4b
            r2.inPreferredConfig = r3     // Catch: java.lang.Throwable -> L4b
            android.graphics.BitmapFactory.decodeFileDescriptor(r1, r0, r2)     // Catch: java.lang.Throwable -> L4b
            int r4 = r2.outWidth     // Catch: java.lang.Throwable -> L4b
            int r2 = r2.outHeight     // Catch: java.lang.Throwable -> L4b
            android.graphics.Rect r5 = new android.graphics.Rect     // Catch: java.lang.Throwable -> L4b
            r6 = 0
            r5.<init>(r6, r6, r4, r2)     // Catch: java.lang.Throwable -> L4b
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L4b
            r2.<init>()     // Catch: java.lang.Throwable -> L4b
            r2.inPreferredConfig = r3     // Catch: java.lang.Throwable -> L4b
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFileDescriptor(r1, r5, r2)     // Catch: java.lang.Throwable -> L4b
            goto L4d
        L4b:
            r1 = move-exception
            goto L5a
        L4d:
            if (r8 == 0) goto L6f
            r8.close()     // Catch: java.io.IOException -> L53
            goto L6f
        L53:
            r8 = move-exception
            r8.printStackTrace()
            goto L6f
        L58:
            r1 = move-exception
            r8 = r0
        L5a:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L63
            if (r8 == 0) goto L6f
            r8.close()     // Catch: java.io.IOException -> L53
            goto L6f
        L63:
            r7 = move-exception
            if (r8 == 0) goto L6e
            r8.close()     // Catch: java.io.IOException -> L6a
            goto L6e
        L6a:
            r8 = move-exception
            r8.printStackTrace()
        L6e:
            throw r7
        L6f:
            if (r9 == 0) goto L75
            android.graphics.Bitmap r0 = fitToScreen(r7, r0, r10)
        L75:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.utils.BitmapUtils.getBitmapFromUri(android.content.Context, android.net.Uri, boolean, boolean):android.graphics.Bitmap");
    }
}
