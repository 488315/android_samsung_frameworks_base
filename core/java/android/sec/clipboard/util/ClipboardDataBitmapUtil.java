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
      while (bitmapOption.outWidth / sampleSize >= reqWidth
          && bitmapOption.outHeight / sampleSize >= reqHeight) {
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
    Bitmap result = null;
    try {
      URL url = new URL(urlname);
      android.util.Log.m94d(TAG, "url : " + url);
      URLConnection connection = url.openConnection();
      connection.setConnectTimeout(2000);
      connection.setReadTimeout(3000);
      InputStream is = connection.getInputStream();
      BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
      bitmapOption.inJustDecodeBounds = true;
      bitmapOption.inPurgeable = true;
      if (is != null) {
        result = BitmapFactory.decodeStream(is, null, bitmapOption);
        is.close();
      }
      if (bitmapOption.outWidth > -1 && bitmapOption.outHeight > -1) {
        bitmapOption.inSampleSize = calculateInSampleSize(bitmapOption, reqWidth, reqHeight);
        bitmapOption.inJustDecodeBounds = false;
        InputStream is2 = url.openStream();
        if (is2 != null) {
          Bitmap result2 = BitmapFactory.decodeStream(is2, null, bitmapOption);
          is2.close();
          return result2;
        }
        return result;
      }
      android.util.Log.m94d(
          TAG,
          "Return null because received bitmap size is invalid. bitmapOption.outWidth :"
              + bitmapOption.outWidth
              + ", bitmapOption.outHeight :"
              + bitmapOption.outHeight);
      return result;
    } catch (IOException e) {
      android.util.Log.m96e(TAG, e.getMessage());
      return null;
    } catch (OutOfMemoryError | MalformedURLException e2) {
      android.util.Log.m96e(TAG, e2.getMessage());
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
      android.util.Log.m98i(
          TAG,
          "Return null because received bitmap size is invalid. bitmapOption.outWidth :"
              + bitmapOption.outWidth
              + ", bitmapOption.outHeight :"
              + bitmapOption.outHeight);
      return result;
    } catch (Exception e) {
      android.util.Log.m96e(TAG, "getFilePathBitmap error :" + e.getMessage());
      return null;
    }
  }

  public static Bitmap getBitmapFromContentUri(Context context, Uri uri) {
    ContentResolver contentResolver;
    if (context == null || (contentResolver = context.getContentResolver()) == null) {
      return null;
    }
    String uriString = uri.toString();
    int length = uriString.length();
    int i = LENGTH_CONTENT_URI;
    if (length <= i || uriString.substring(0, i).compareTo("content://") != 0) {
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
      android.util.Log.m96e(TAG, "getUriPathBitmap error :" + e.getMessage());
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
      android.util.Log.m98i(
          TAG,
          "Return null because received bitmap size is invalid. bitmapOption.outWidth :"
              + bitmapOption.outWidth
              + ", bitmapOption.outHeight :"
              + bitmapOption.outHeight);
      return null;
    } catch (Exception e) {
      android.util.Log.m96e(TAG, "getUriPathBitmap error :" + e.getMessage());
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
  */
  private static int findImageDegree(ContentResolver contentResolver, Uri uri) {
    int orientation = -1;
    if ("content".equals(uri.getScheme())) {
      Cursor c = null;
      try {
        try {
          c = contentResolver.query(uri, null, null, null, null);
          if (c != null && c.moveToNext()) {
            int columnIdx = c.getColumnIndex("_data");
            _data = columnIdx != -1 ? c.getString(columnIdx) : null;
            int columnIdx2 = c.getColumnIndex("orientation");
            if (columnIdx2 != -1) {
              try {
                orientation = Integer.parseInt(c.getString(columnIdx2));
              } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
              }
            }
          }
        } catch (SQLException | UnsupportedOperationException sqle) {
          sqle.printStackTrace();
        }
      } catch (Throwable th) {
        if (c != null) {
          c.close();
        }
        throw th;
      }
    } else if ("file".equals(uri.getScheme())) {
      _data = uri.getPath();
    }
    if (orientation != -1) {
      return orientation;
    }
    if (_data == null) {
      return 0;
    }
    try {
      return getExifOrientation(_data);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  private static int getExifOrientation(String filepath) {
    int orientation;
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(filepath);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    if (exif == null
        || (orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) == -1) {
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
        Bitmap converted =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
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

  public static int calculateInSampleSize(
      BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
