package com.samsung.android.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.util.Log;

import com.samsung.android.wallpaperbackup.WallpaperBackupRestoreManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SemWallpaperUtils {
    private static final String TAG = "SemWallpaperUtils";

    private SemWallpaperUtils() {}

    @Deprecated
    public static void startBackup(Context context, String pathValue, String source) {
        WallpaperBackupRestoreManager wallpaperBackupRestoreManager =
                new WallpaperBackupRestoreManager();
        wallpaperBackupRestoreManager.startBackupWallpaper(context, pathValue, source);
    }

    @Deprecated
    public static void startRestore(Context context, String pathValue, String source) {
        WallpaperBackupRestoreManager wallpaperBackupRestoreManager =
                new WallpaperBackupRestoreManager();
        wallpaperBackupRestoreManager.startRestoreWallpaper(context, pathValue, source);
    }

    public static Bitmap decodeFileConsiderQMG(String path, BitmapFactory.Options options) {
        InputStream fis = null;
        try {
            try {
                fis = new FileInputStream(path);
                Bitmap decodeStreamConsiderQMG = decodeStreamConsiderQMG(fis, null, options);
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return decodeStreamConsiderQMG;
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static Bitmap decodeStreamConsiderQMG(
            InputStream is, Rect rect, BitmapFactory.Options options) {
        BufferedInputStream bis = new BufferedInputStream(is);
        if (isQmgImage(bis)) {
            try {
                Class<?> c = Class.forName("android.graphics.BitmapFactory");
                Method method =
                        c.getMethod(
                                "decodeStreamQMG",
                                InputStream.class,
                                Rect.class,
                                BitmapFactory.Options.class);
                method.setAccessible(true);
                Bitmap result = (Bitmap) method.invoke(null, bis, rect, options);
                return result;
            } catch (NoSuchMethodException e) {
                Bitmap result2 = BitmapFactory.decodeStream(bis, rect, options);
                return result2;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        Bitmap result3 = BitmapFactory.decodeStream(bis, rect, options);
        return result3;
    }

    public static BitmapRegionDecoder newRegionDecoder(String path, boolean isShareable) {
        BufferedInputStream bis = null;
        try {
            try {
                BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(path));
                BitmapRegionDecoder decoder = null;
                if (isQmgImage(bis2)) {
                    try {
                        Class<?> c = Class.forName("android.graphics.BitmapRegionDecoder");
                        Method method =
                                c.getMethod("newInstanceQMG", InputStream.class, Boolean.TYPE);
                        method.setAccessible(true);
                        decoder =
                                (BitmapRegionDecoder)
                                        method.invoke(null, bis2, Boolean.valueOf(isShareable));
                    } catch (NoSuchMethodException e) {
                        decoder = BitmapRegionDecoder.newInstance(bis2, isShareable);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    decoder = BitmapRegionDecoder.newInstance(bis2, isShareable);
                }
                try {
                    bis2.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                return decoder;
            } catch (Exception e4) {
                e4.printStackTrace();
                if (0 != 0) {
                    try {
                        bis.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    bis.close();
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isQmgImage(String path) {
        try {
            InputStream fis = new FileInputStream(path);
            try {
                BufferedInputStream bis = new BufferedInputStream(fis);
                try {
                    boolean isQmgImage = isQmgImage(bis);
                    bis.close();
                    fis.close();
                    return isQmgImage;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "isQmgImage : e=" + e, e);
            return false;
        }
    }

    public static boolean isQmgImage(BufferedInputStream bis) {
        if (bis != null) {
            bis.mark(2);
            try {
                int byte1 = bis.read();
                int byte2 = bis.read();
                bis.reset();
                if (byte1 == 81 && byte2 == 71) {
                    return true;
                }
                return false;
            } catch (IOException e) {
                Log.e(TAG, "isQmgImage : e=" + e, e);
                return false;
            }
        }
        return false;
    }
}
