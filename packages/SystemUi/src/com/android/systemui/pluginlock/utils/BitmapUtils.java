package com.android.systemui.pluginlock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BitmapUtils {
    public static Bitmap fitToScreen(Context context, Bitmap bitmap, boolean z) {
        int i;
        Point realScreenSize = WallpaperUtils.getRealScreenSize(context, z);
        Log.d("BitmapUtils", "fitToScreen() screenSize: " + realScreenSize + ", cover: " + z);
        int i2 = realScreenSize.x;
        if (i2 <= 0 || (i = realScreenSize.y) <= 0) {
            Log.w("BitmapUtils", "fitToScreen, can not resize");
            return bitmap;
        }
        int i3 = context.getResources().getConfiguration().orientation;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("fitToScreen() orientation:", i3, "BitmapUtils");
        if (!z && i3 == 2) {
            i2 = realScreenSize.y;
            i = realScreenSize.x;
        }
        if (bitmap == null) {
            Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawColor(EmergencyPhoneWidget.BG_COLOR);
            Log.w("BitmapUtils", "fitToScreen() bitmap is null, return blank bitmap");
            return createBitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = width;
        float f2 = height;
        float f3 = i2;
        float f4 = i;
        float f5 = f3 / f4 > f / f2 ? f3 / f : f4 / f2;
        Log.d("BitmapUtils", "fitToScreen() scale:" + f5);
        if (f5 == 1.0f) {
            return bitmap;
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("fitToScreen() original width:", width, ", height:", height, "BitmapUtils");
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f * f5), (int) (f2 * f5), true);
        if (createScaledBitmap != bitmap) {
            bitmap.recycle();
        }
        Log.d("BitmapUtils", "fitToScreen() resized width:" + createScaledBitmap.getWidth() + ", height:" + createScaledBitmap.getHeight());
        return createScaledBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0058, code lost:
    
        if (r6 == null) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:4:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap getBitmapFromPath(Context context, String str, boolean z, boolean z2) {
        Bitmap bitmap;
        IOException e;
        Bitmap bitmap2;
        FileInputStream fileInputStream;
        AbstractC0000x2c234b15.m3m("getBitmapFromPath() path:", str, "BitmapUtils");
        Bitmap bitmap3 = null;
        Bitmap bitmap4 = null;
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        if (str != null) {
            try {
                File file = new File(str);
                Log.d("BitmapUtils", "getBitmapFromPath() file.exists():" + file.exists());
                if (file.exists() && file.canRead()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    boolean z3 = WallpaperUtils.mIsEmergencyMode;
                    try {
                        fileInputStream = new FileInputStream(str);
                        try {
                            try {
                                WallpaperUtils.decodeStreamConsiderQMG(fileInputStream, null, options);
                            } catch (FileNotFoundException e2) {
                                e = e2;
                                e.printStackTrace();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        fileInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = null;
                        if (fileInputStream != null) {
                        }
                        throw th;
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    int i = options.outWidth;
                    int i2 = options.outHeight;
                    Log.d("BitmapUtils", "getBitmapFromPath() width:" + i + ", height:" + i2);
                    FileInputStream fileInputStream4 = new FileInputStream(file);
                    try {
                        Rect rect = new Rect(0, 0, i, i2);
                        options.inJustDecodeBounds = false;
                        bitmap4 = WallpaperUtils.decodeStreamConsiderQMG(fileInputStream4, rect, options);
                        Log.d("BitmapUtils", "getBitmapFromPath() bitmap:" + bitmap4);
                        bitmap2 = bitmap4;
                        fileInputStream2 = fileInputStream4;
                    } catch (Throwable th3) {
                        th = th3;
                        bitmap = bitmap4;
                        fileInputStream3 = fileInputStream4;
                        try {
                            Log.w("BitmapUtils", "Can't load dynamic lock wallpaper!", th);
                            th.printStackTrace();
                            if (fileInputStream3 != null) {
                            }
                            bitmap3 = bitmap;
                            if (z) {
                            }
                        } catch (Throwable th4) {
                            if (fileInputStream3 != null) {
                                try {
                                    fileInputStream3.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            throw th4;
                        }
                    }
                } else {
                    Log.w("BitmapUtils", "Can't load dynamic lock file");
                    bitmap2 = null;
                }
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e7) {
                        e = e7;
                        e.printStackTrace();
                        bitmap3 = bitmap2;
                        if (z) {
                        }
                    }
                }
                bitmap3 = bitmap2;
            } catch (Throwable th5) {
                th = th5;
                bitmap = null;
                Log.w("BitmapUtils", "Can't load dynamic lock wallpaper!", th);
                th.printStackTrace();
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException e8) {
                        Bitmap bitmap5 = bitmap;
                        e = e8;
                        bitmap2 = bitmap5;
                        e.printStackTrace();
                        bitmap3 = bitmap2;
                        if (z) {
                        }
                    }
                }
                bitmap3 = bitmap;
                if (z) {
                }
            }
        }
        return z ? fitToScreen(context, bitmap3, z2) : bitmap3;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap getBitmapFromUri(Context context, Uri uri, boolean z, boolean z2) {
        ParcelFileDescriptor parcelFileDescriptor;
        Log.d("BitmapUtils", "getBitmapFromPath() uri:" + uri);
        Bitmap bitmap = null;
        try {
            if (uri != null) {
                try {
                    parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
                    if (parcelFileDescriptor != null) {
                        try {
                            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                            Rect rect = new Rect(0, 0, options.outWidth, options.outHeight);
                            BitmapFactory.Options options2 = new BitmapFactory.Options();
                            options2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, rect, options2);
                        } catch (Throwable th) {
                            th = th;
                            try {
                                th.printStackTrace();
                                if (parcelFileDescriptor != null) {
                                    parcelFileDescriptor.close();
                                }
                                if (!z) {
                                }
                            } catch (Throwable th2) {
                                if (parcelFileDescriptor != null) {
                                    try {
                                        parcelFileDescriptor.close();
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
                    parcelFileDescriptor = null;
                }
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return !z ? fitToScreen(context, bitmap, z2) : bitmap;
    }
}
