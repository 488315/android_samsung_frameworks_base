package android.sec.clipboard.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
    private static final int LENGTH_CONTENT_URI = 10;
    private static final String PREFIX_CONTENT_URI = "content://";
    private static final String TAG = "ClipboardDataBitmapUtil";

    public static Bitmap getResizeBitmap(byte[] bArr, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        try {
            Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
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
                return bitmapDecodeByteArray;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap downloadSimpleBitmap(String str, int i, int i2) {
        try {
            try {
                URL url = new URL(str);
                android.util.Log.d(TAG, "url : " + url);
                URLConnection uRLConnectionOpenConnection = url.openConnection();
                uRLConnectionOpenConnection.setConnectTimeout(2000);
                uRLConnectionOpenConnection.setReadTimeout(3000);
                InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inPurgeable = true;
                    Bitmap bitmapDecodeStream = inputStream != null ? BitmapFactory.decodeStream(inputStream, null, options) : null;
                    if (options.outWidth > -1 && options.outHeight > -1) {
                        options.inSampleSize = calculateInSampleSize(options, i, i2);
                        options.inJustDecodeBounds = false;
                        InputStream inputStreamOpenStream = url.openStream();
                        if (inputStreamOpenStream != null) {
                            try {
                                bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenStream, null, options);
                            } finally {
                            }
                        }
                        if (inputStreamOpenStream != null) {
                            inputStreamOpenStream.close();
                        }
                        if (inputStream != null) {
                        }
                        return bitmapDecodeStream;
                    }
                    android.util.Log.d(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + options.outWidth + ", bitmapOption.outHeight :" + options.outHeight);
                    if (inputStream == null) {
                        return bitmapDecodeStream;
                    }
                    inputStream.close();
                    return bitmapDecodeStream;
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
            } catch (IOException e) {
                android.util.Log.e(TAG, e.getMessage());
                return null;
            }
        } catch (OutOfMemoryError | MalformedURLException e2) {
            android.util.Log.e(TAG, e2.getMessage());
            return null;
        }
    }

    public static Bitmap getFilePathBitmap(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        try {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
            if (options.outWidth > -1 && options.outHeight > -1) {
                options.inSampleSize = calculateInSampleSize(options, i, i2);
                options.inJustDecodeBounds = false;
                Bitmap bitmapDecodeFile2 = BitmapFactory.decodeFile(str, options);
                int exifOrientation = getExifOrientation(str);
                return exifOrientation != 0 ? rotateBitmap(bitmapDecodeFile2, exifOrientation) : bitmapDecodeFile2;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + options.outWidth + ", bitmapOption.outHeight :" + options.outHeight);
            return bitmapDecodeFile;
        } catch (Exception e) {
            android.util.Log.e(TAG, "getFilePathBitmap error :" + e.getMessage());
            return null;
        }
    }

    public static Bitmap getBitmapFromContentUri(Context context, Uri uri) throws IOException {
        ContentResolver contentResolver;
        if (context == null || (contentResolver = context.getContentResolver()) == null) {
            return null;
        }
        String string = uri.toString();
        int length = string.length();
        int i = LENGTH_CONTENT_URI;
        if (length > i && string.substring(0, i).compareTo("content://") == 0) {
            try {
                InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                    return bitmapDecodeStream;
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
            InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            if (inputStreamOpenInputStream != null) {
                BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                inputStreamOpenInputStream.close();
            }
            if (options.outWidth > -1 && options.outHeight > -1) {
                options.inSampleSize = calculateInSampleSize(options, i, i2);
                options.inJustDecodeBounds = false;
                InputStream inputStreamOpenInputStream2 = contentResolver.openInputStream(uri);
                if (inputStreamOpenInputStream2 == null) {
                    return null;
                }
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
                inputStreamOpenInputStream2.close();
                return bitmapDecodeStream;
            }
            android.util.Log.i(TAG, "Return null because received bitmap size is invalid. bitmapOption.outWidth :" + options.outWidth + ", bitmapOption.outHeight :" + options.outHeight);
            return null;
        } catch (Exception e) {
            android.util.Log.e(TAG, "getUriPathBitmap error :" + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e A[PHI: r2
      0x004e: PHI (r2v13 java.lang.String) = (r2v16 java.lang.String), (r2v17 java.lang.String), (r2v10 java.lang.String), (r2v10 java.lang.String) binds: [B:6:0x0018, B:8:0x001e, B:13:0x0033, B:18:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int findImageDegree(ContentResolver contentResolver, Uri uri) throws Throwable {
        int i;
        RuntimeException runtimeException;
        String str;
        Throwable th;
        String path = null;
        path = null;
        path = null;
        cursor = null;
        Cursor cursor = null;
        if ("content".equals(uri.getScheme())) {
            try {
                try {
                    Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
                    if (cursorQuery != null) {
                        try {
                            try {
                                if (cursorQuery.moveToNext()) {
                                    int columnIndex = cursorQuery.getColumnIndex("_data");
                                    path = columnIndex != -1 ? cursorQuery.getString(columnIndex) : null;
                                    int columnIndex2 = cursorQuery.getColumnIndex("orientation");
                                    if (columnIndex2 != -1) {
                                        try {
                                            i = Integer.parseInt(cursorQuery.getString(columnIndex2));
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                        if (cursorQuery != null) {
                                        }
                                    }
                                } else {
                                    i = -1;
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    cursor.close();
                                    throw th;
                                }
                                throw th;
                            }
                        } catch (SQLException | UnsupportedOperationException e2) {
                            runtimeException = e2;
                            cursor = cursorQuery;
                            str = null;
                            runtimeException.printStackTrace();
                            if (cursor != null) {
                                cursor.close();
                            }
                            path = str;
                            i = -1;
                            if (i != -1) {
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (SQLException | UnsupportedOperationException e3) {
                runtimeException = e3;
                str = null;
            }
            if (i != -1) {
                return i;
            }
            if (path == null) {
                return 0;
            }
            try {
                return getExifOrientation(path);
            } catch (Exception e4) {
                e4.printStackTrace();
                return 0;
            }
        }
        if ("file".equals(uri.getScheme())) {
            path = uri.getPath();
        }
        i = -1;
        if (i != -1) {
        }
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
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (bitmap2.sameAs(bitmapCreateBitmap)) {
                    return bitmap2;
                }
                bitmap2.recycle();
                return bitmapCreateBitmap;
            } catch (OutOfMemoryError e) {
                e = e;
                e.printStackTrace();
                return bitmap2;
            }
        } catch (OutOfMemoryError e2) {
            e = e2;
            bitmap2 = bitmap;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int iRound = Math.round(i3 / i2);
        int iRound2 = Math.round(i4 / i);
        return iRound < iRound2 ? iRound : iRound2;
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
