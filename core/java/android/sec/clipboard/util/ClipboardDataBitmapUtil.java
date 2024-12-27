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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes3.dex */
public class ClipboardDataBitmapUtil {
    private static final int CLIPBOARD_LANDSCAPE_COLUMN = 5;
    private static final int CLIPBOARD_PORTRAIT_COLUMN = 3;
    private static final int HTML_IMAG_MAX_HEIGHT = 110;
    private static final int LENGTH_CONTENT_URI = "content://".length();
    private static final String PREFIX_CONTENT_URI = "content://";
    private static final String TAG = "ClipboardDataBitmapUtil";

    public static Bitmap getResizeBitmap(byte[] bytes, int reqWidth, int reqHeight) {
        int sampleSize = 2;
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inJustDecodeBounds = true;
        bitmapOption.inPurgeable = true;
        try {
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOption);
            while (bitmapOption.outWidth / sampleSize >= reqWidth && bitmapOption.outHeight / sampleSize >= reqHeight) {
                sampleSize++;
            }
            bitmapOption.inSampleSize = sampleSize - 1;
            try {
                bitmapOption.inJustDecodeBounds = false;
                Bitmap bm2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOption);
                return bm2;
            } catch (Exception e) {
                e.printStackTrace();
                return bm;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap downloadSimpleBitmap(String urlname, int reqWidth, int reqHeight) {
        try {
            try {
                URL url = new URL(urlname);
                android.util.Log.d(TAG, "url : " + url);
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(2000);
                connection.setReadTimeout(3000);
                InputStream inputStream = connection.getInputStream();
                try {
                    BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
                    bitmapOption.inJustDecodeBounds = true;
                    bitmapOption.inPurgeable = true;
                    Bitmap result = inputStream != null ? BitmapFactory.decodeStream(inputStream, null, bitmapOption) : null;
                    if (bitmapOption.outWidth > -1 && bitmapOption.outHeight > -1) {
                        bitmapOption.inSampleSize = calculateInSampleSize(bitmapOption, reqWidth, reqHeight);
                        bitmapOption.inJustDecodeBounds = false;
                        InputStream inputStream2 = url.openStream();
                        if (inputStream2 != null) {
                            try {
                                result = BitmapFactory.decodeStream(inputStream2, null, bitmapOption);
                            } finally {
                            }
                        }
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return result;
                    }
                    android.util.Log.d(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + bitmapOption.outWidth + ", bitmapOption.outHeight :" + bitmapOption.outHeight);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return result;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (OutOfMemoryError | MalformedURLException e) {
                android.util.Log.e(TAG, e.getMessage());
                return null;
            }
        } catch (IOException e2) {
            android.util.Log.e(TAG, e2.getMessage());
            return null;
        }
    }

    public static Bitmap getFilePathBitmap(String fileName, int reqWidth, int reqHeight) {
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inJustDecodeBounds = true;
        bitmapOption.inPurgeable = true;
        try {
            Bitmap result = BitmapFactory.decodeFile(fileName, bitmapOption);
            if (bitmapOption.outWidth > -1 && bitmapOption.outHeight > -1) {
                bitmapOption.inSampleSize = calculateInSampleSize(bitmapOption, reqWidth, reqHeight);
                bitmapOption.inJustDecodeBounds = false;
                Bitmap result2 = BitmapFactory.decodeFile(fileName, bitmapOption);
                int degree = getExifOrientation(fileName);
                if (degree != 0) {
                    return rotateBitmap(result2, degree);
                }
                return result2;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + bitmapOption.outWidth + ", bitmapOption.outHeight :" + bitmapOption.outHeight);
            return result;
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
        String uriString = uri.toString();
        if (uriString.length() <= LENGTH_CONTENT_URI || uriString.substring(0, LENGTH_CONTENT_URI).compareTo("content://") != 0) {
            return null;
        }
        try {
            InputStream is = contentResolver.openInputStream(uri);
            try {
                Bitmap result = BitmapFactory.decodeStream(is);
                if (is == null) {
                    return result;
                }
                is.close();
                return result;
            } finally {
            }
        } catch (Exception e) {
            android.util.Log.e(TAG, "getUriPathBitmap error :" + e.getMessage());
            return null;
        }
    }

    public static Bitmap getUriPathBitmap(Context context, Uri uri, int reqWidth, int reqHeight) {
        ContentResolver contentResolver;
        Bitmap result = null;
        if (context == null || (contentResolver = context.getContentResolver()) == null) {
            return null;
        }
        try {
            InputStream is = contentResolver.openInputStream(uri);
            BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
            bitmapOption.inJustDecodeBounds = true;
            bitmapOption.inPurgeable = true;
            if (is != null) {
                BitmapFactory.decodeStream(is, null, bitmapOption);
                is.close();
            }
            if (bitmapOption.outWidth > -1 && bitmapOption.outHeight > -1) {
                bitmapOption.inSampleSize = calculateInSampleSize(bitmapOption, reqWidth, reqHeight);
                bitmapOption.inJustDecodeBounds = false;
                InputStream is2 = contentResolver.openInputStream(uri);
                if (is2 != null) {
                    result = BitmapFactory.decodeStream(is2, null, bitmapOption);
                    is2.close();
                }
                return result;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + bitmapOption.outWidth + ", bitmapOption.outHeight :" + bitmapOption.outHeight);
            return null;
        } catch (Exception e) {
            android.util.Log.e(TAG, "getUriPathBitmap error :" + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0048, code lost:
    
        if (r2 == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004a, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0054, code lost:
    
        if (r2 == null) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int findImageDegree(android.content.ContentResolver r10, android.net.Uri r11) {
        /*
            r0 = -1
            r1 = 0
            java.lang.String r2 = "content"
            java.lang.String r3 = r11.getScheme()
            boolean r2 = r2.equals(r3)
            r3 = -1
            if (r2 == 0) goto L5d
            r2 = 0
            r8 = 0
            r9 = 0
            r6 = 0
            r7 = 0
            r4 = r10
            r5 = r11
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50
            r2 = r4
            if (r2 == 0) goto L48
            boolean r4 = r2.moveToNext()     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50
            if (r4 == 0) goto L48
            java.lang.String r4 = "_data"
            int r4 = r2.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50
            if (r4 == r3) goto L30
            java.lang.String r5 = r2.getString(r4)     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50
            r1 = r5
        L30:
            java.lang.String r5 = "orientation"
            int r5 = r2.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50
            r4 = r5
            if (r4 == r3) goto L48
            java.lang.String r5 = r2.getString(r4)     // Catch: java.lang.NumberFormatException -> L44 java.lang.Throwable -> L4e java.lang.Throwable -> L50 java.lang.Throwable -> L50
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> L44 java.lang.Throwable -> L4e java.lang.Throwable -> L50 java.lang.Throwable -> L50
            r0 = r5
            goto L48
        L44:
            r5 = move-exception
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L4e java.lang.Throwable -> L50 java.lang.Throwable -> L50
        L48:
            if (r2 == 0) goto L6e
        L4a:
            r2.close()
            goto L6e
        L4e:
            r3 = move-exception
            goto L57
        L50:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L4e
            if (r2 == 0) goto L6e
            goto L4a
        L57:
            if (r2 == 0) goto L5c
            r2.close()
        L5c:
            throw r3
        L5d:
            java.lang.String r2 = "file"
            java.lang.String r4 = r11.getScheme()
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L6e
            java.lang.String r1 = r11.getPath()
            goto L6f
        L6e:
        L6f:
            if (r0 != r3) goto L80
            if (r1 != 0) goto L75
            r0 = 0
            goto L80
        L75:
            int r2 = getExifOrientation(r1)     // Catch: java.lang.Exception -> L7b
            r0 = r2
            goto L80
        L7b:
            r2 = move-exception
            r2.printStackTrace()
            r0 = 0
        L80:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.ClipboardDataBitmapUtil.findImageDegree(android.content.ContentResolver, android.net.Uri):int");
    }

    private static int getExifOrientation(String filepath) {
        int orientation;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif == null || (orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) == -1) {
            return 0;
        }
        switch (orientation) {
            case 3:
                break;
            case 6:
                break;
            case 8:
                break;
        }
        return 0;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (!bitmap.sameAs(converted)) {
                    bitmap.recycle();
                    return converted;
                }
                return bitmap;
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                return bitmap;
            }
        }
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(height / reqHeight);
        int widthRatio = Math.round(width / reqWidth);
        int inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        return inSampleSize;
    }

    public static int getThumbReqWidth(Context context) {
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int displayHeigth = context.getResources().getDisplayMetrics().heightPixels;
        if (displayWidth < displayHeigth) {
            int reqWidth = Math.round(displayWidth / 3.0f);
            return reqWidth;
        }
        int reqWidth2 = Math.round(displayWidth / 5.0f);
        return reqWidth2;
    }

    public static int getThumbReqHeigth(Context context) {
        return Math.round(convertDpToPixel(context, 110.0f));
    }

    public static float convertDpToPixel(Context context, float dp) {
        return (context.getResources().getDisplayMetrics().densityDpi / 160.0f) * dp;
    }
}
