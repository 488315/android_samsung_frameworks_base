package android.sec.clipboard.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class ClipboardDataBitmapUtil {
    private static final int CLIPBOARD_LANDSCAPE_COLUMN = 5;
    private static final int CLIPBOARD_PORTRAIT_COLUMN = 3;
    private static final int HTML_IMAG_MAX_HEIGHT = 110;
    private static final int LENGTH_CONTENT_URI = 10;
    private static final String PREFIX_CONTENT_URI = "content://";
    private static final String TAG = "ClipboardDataBitmapUtil";

    public static Bitmap getResizeBitmap(byte[] bArr, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        try {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i3 = 2;
            while (options.outWidth / i3 >= i && options.outHeight / i3 >= i2) {
                i3++;
            }
            options.inSampleSize = i3 - 1;
            try {
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            } catch (Exception e) {
                e.printStackTrace();
                return decodeByteArray;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006f, code lost:
    
        if (r8 != null) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap downloadSimpleBitmap(java.lang.String r8, int r9, int r10) {
        /*
            java.lang.String r0 = "ClipboardDataBitmapUtil"
            java.lang.String r1 = "Return null because received bitmap size is invalid. bitmapOption.outWidth :"
            java.lang.String r2 = "url : "
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            r4.<init>(r8)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            r8.<init>(r2)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            r8.append(r4)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            java.lang.String r8 = r8.toString()     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            android.util.Log.d(r0, r8)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            java.net.URLConnection r8 = r4.openConnection()     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            r2 = 2000(0x7d0, float:2.803E-42)
            r8.setConnectTimeout(r2)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            r2 = 3000(0xbb8, float:4.204E-42)
            r8.setReadTimeout(r2)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            java.io.InputStream r8 = r8.getInputStream()     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L94
            r2.<init>()     // Catch: java.lang.Throwable -> L94
            r5 = 1
            r2.inJustDecodeBounds = r5     // Catch: java.lang.Throwable -> L94
            r2.inPurgeable = r5     // Catch: java.lang.Throwable -> L94
            if (r8 == 0) goto L3f
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeStream(r8, r3, r2)     // Catch: java.lang.Throwable -> L94
            goto L40
        L3f:
            r5 = r3
        L40:
            int r6 = r2.outWidth     // Catch: java.lang.Throwable -> L94
            r7 = -1
            if (r6 <= r7) goto L75
            int r6 = r2.outHeight     // Catch: java.lang.Throwable -> L94
            if (r6 > r7) goto L4a
            goto L75
        L4a:
            int r9 = calculateInSampleSize(r2, r9, r10)     // Catch: java.lang.Throwable -> L94
            r2.inSampleSize = r9     // Catch: java.lang.Throwable -> L94
            r9 = 0
            r2.inJustDecodeBounds = r9     // Catch: java.lang.Throwable -> L94
            java.io.InputStream r9 = r4.openStream()     // Catch: java.lang.Throwable -> L94
            if (r9 == 0) goto L6a
            android.graphics.Bitmap r5 = android.graphics.BitmapFactory.decodeStream(r9, r3, r2)     // Catch: java.lang.Throwable -> L5e
            goto L6a
        L5e:
            r10 = move-exception
            if (r9 == 0) goto L69
            r9.close()     // Catch: java.lang.Throwable -> L65
            goto L69
        L65:
            r9 = move-exception
            r10.addSuppressed(r9)     // Catch: java.lang.Throwable -> L94
        L69:
            throw r10     // Catch: java.lang.Throwable -> L94
        L6a:
            if (r9 == 0) goto L6f
            r9.close()     // Catch: java.lang.Throwable -> L94
        L6f:
            if (r8 == 0) goto L74
        L71:
            r8.close()     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9 java.lang.Throwable -> La9
        L74:
            return r5
        L75:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L94
            r9.<init>(r1)     // Catch: java.lang.Throwable -> L94
            int r10 = r2.outWidth     // Catch: java.lang.Throwable -> L94
            r9.append(r10)     // Catch: java.lang.Throwable -> L94
            java.lang.String r10 = ", bitmapOption.outHeight :"
            r9.append(r10)     // Catch: java.lang.Throwable -> L94
            int r10 = r2.outHeight     // Catch: java.lang.Throwable -> L94
            r9.append(r10)     // Catch: java.lang.Throwable -> L94
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L94
            android.util.Log.d(r0, r9)     // Catch: java.lang.Throwable -> L94
            if (r8 == 0) goto L93
            goto L71
        L93:
            return r5
        L94:
            r9 = move-exception
            if (r8 == 0) goto L9f
            r8.close()     // Catch: java.lang.Throwable -> L9b
            goto L9f
        L9b:
            r8 = move-exception
            r9.addSuppressed(r8)     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9 java.lang.Throwable -> La9
        L9f:
            throw r9     // Catch: java.io.IOException -> La0 java.lang.Throwable -> La9 java.lang.Throwable -> La9
        La0:
            r8 = move-exception
            java.lang.String r8 = r8.getMessage()
            android.util.Log.e(r0, r8)
            return r3
        La9:
            r8 = move-exception
            java.lang.String r8 = r8.getMessage()
            android.util.Log.e(r0, r8)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.ClipboardDataBitmapUtil.downloadSimpleBitmap(java.lang.String, int, int):android.graphics.Bitmap");
    }

    public static Bitmap getFilePathBitmap(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        try {
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
            if (options.outWidth > -1 && options.outHeight > -1) {
                options.inSampleSize = calculateInSampleSize(options, i, i2);
                options.inJustDecodeBounds = false;
                Bitmap decodeFile2 = BitmapFactory.decodeFile(str, options);
                int exifOrientation = getExifOrientation(str);
                return exifOrientation != 0 ? rotateBitmap(decodeFile2, exifOrientation) : decodeFile2;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + options.outWidth + ", bitmapOption.outHeight :" + options.outHeight);
            return decodeFile;
        } catch (Exception e) {
            android.util.Log.e(TAG, "getFilePathBitmap error :" + e.getMessage());
            return null;
        }
    }

    public static Bitmap getBitmapFromContentUri(Context context, Uri uri) {
        ContentResolver contentResolver;
        if (context == null || (contentResolver = context.getContentResolver()) == null) {
            return null;
        }
        String uri2 = uri.toString();
        int length = uri2.length();
        int i = LENGTH_CONTENT_URI;
        if (length > i && uri2.substring(0, i).compareTo("content://") == 0) {
            try {
                InputStream openInputStream = contentResolver.openInputStream(uri);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    return decodeStream;
                } finally {
                }
            } catch (Exception e) {
                android.util.Log.e(TAG, "getUriPathBitmap error :" + e.getMessage());
            }
        }
        return null;
    }

    public static Bitmap getUriPathBitmap(Context context, Uri uri, int i, int i2) {
        ContentResolver contentResolver;
        if (context == null || (contentResolver = context.getContentResolver()) == null) {
            return null;
        }
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            if (openInputStream != null) {
                BitmapFactory.decodeStream(openInputStream, null, options);
                openInputStream.close();
            }
            if (options.outWidth > -1 && options.outHeight > -1) {
                options.inSampleSize = calculateInSampleSize(options, i, i2);
                options.inJustDecodeBounds = false;
                InputStream openInputStream2 = contentResolver.openInputStream(uri);
                if (openInputStream2 == null) {
                    return null;
                }
                Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream2, null, options);
                openInputStream2.close();
                return decodeStream;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + options.outWidth + ", bitmapOption.outHeight :" + options.outHeight);
            return null;
        } catch (Exception e) {
            android.util.Log.e(TAG, "getUriPathBitmap error :" + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int findImageDegree(android.content.ContentResolver r10, android.net.Uri r11) {
        /*
            java.lang.String r0 = "content"
            java.lang.String r1 = r11.getScheme()
            boolean r0 = r0.equals(r1)
            r1 = -1
            r2 = 0
            if (r0 == 0) goto L6b
            r7 = 0
            r8 = 0
            r5 = 0
            r6 = 0
            r3 = r10
            r4 = r11
            android.database.Cursor r10 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L55 java.lang.Throwable -> L58
            if (r10 == 0) goto L4e
            boolean r11 = r10.moveToNext()     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L48
            if (r11 == 0) goto L4e
            java.lang.String r11 = "_data"
            int r11 = r10.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L48
            if (r11 == r1) goto L2c
            java.lang.String r2 = r10.getString(r11)     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L48
        L2c:
            java.lang.String r11 = "orientation"
            int r11 = r10.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L48
            if (r11 == r1) goto L4e
            java.lang.String r11 = r10.getString(r11)     // Catch: java.lang.NumberFormatException -> L3e java.lang.Throwable -> L44 java.lang.Throwable -> L48 java.lang.Throwable -> L48
            int r11 = java.lang.Integer.parseInt(r11)     // Catch: java.lang.NumberFormatException -> L3e java.lang.Throwable -> L44 java.lang.Throwable -> L48 java.lang.Throwable -> L48
            goto L4f
        L3e:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L48 java.lang.Throwable -> L48
            goto L4e
        L44:
            r0 = move-exception
            r11 = r0
            r2 = r10
            goto L65
        L48:
            r0 = move-exception
            r11 = r0
            r9 = r2
            r2 = r10
            r10 = r9
            goto L5b
        L4e:
            r11 = r1
        L4f:
            if (r10 == 0) goto L7d
            r10.close()
            goto L7d
        L55:
            r0 = move-exception
            r11 = r0
            goto L65
        L58:
            r0 = move-exception
            r11 = r0
            r10 = r2
        L5b:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L55
            if (r2 == 0) goto L63
            r2.close()
        L63:
            r2 = r10
            goto L7c
        L65:
            if (r2 == 0) goto L6a
            r2.close()
        L6a:
            throw r11
        L6b:
            r4 = r11
            java.lang.String r10 = "file"
            java.lang.String r11 = r4.getScheme()
            boolean r10 = r10.equals(r11)
            if (r10 == 0) goto L7c
            java.lang.String r2 = r4.getPath()
        L7c:
            r11 = r1
        L7d:
            if (r11 != r1) goto L8d
            r11 = 0
            if (r2 != 0) goto L83
            goto L8d
        L83:
            int r11 = getExifOrientation(r2)     // Catch: java.lang.Exception -> L88
            goto L8d
        L88:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()
        L8d:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.ClipboardDataBitmapUtil.findImageDegree(android.content.ContentResolver, android.net.Uri):int");
    }

    private static int getExifOrientation(String str) {
        ExifInterface exifInterface;
        int attributeInt;
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface != null && (attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) != -1) {
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt == 8) {
                return 270;
            }
        }
        return 0;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Bitmap bitmap2;
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
        try {
            bitmap2 = bitmap;
        } catch (OutOfMemoryError e) {
            e = e;
            bitmap2 = bitmap;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap2.sameAs(createBitmap)) {
                return bitmap2;
            }
            bitmap2.recycle();
            return createBitmap;
        } catch (OutOfMemoryError e2) {
            e = e2;
            e.printStackTrace();
            return bitmap2;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(i3 / i2);
        int round2 = Math.round(i4 / i);
        return round < round2 ? round : round2;
    }

    public static int getThumbReqWidth(Context context) {
        int i = context.getResources().getDisplayMetrics().widthPixels;
        if (i < context.getResources().getDisplayMetrics().heightPixels) {
            return Math.round(i / 3.0f);
        }
        return Math.round(i / 5.0f);
    }

    public static int getThumbReqHeigth(Context context) {
        return Math.round(convertDpToPixel(context, 110.0f));
    }

    public static float convertDpToPixel(Context context, float f) {
        return f * (context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }
}
