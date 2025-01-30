package com.android.server.wallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.os.FileUtils;
import android.os.SELinux;
import android.text.TextUtils;
import android.util.Slog;
import android.view.DisplayInfo;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wallpaper.WallpaperDisplayHelper;
import com.samsung.android.app.SemWallpaperUtils;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class WallpaperCropper {
    public static final String TAG = "WallpaperCropper";
    public SemWallpaperManagerService mSemService;
    public final WallpaperDisplayHelper mWallpaperDisplayHelper;

    public WallpaperCropper(WallpaperDisplayHelper wallpaperDisplayHelper, SemWallpaperManagerService semWallpaperManagerService) {
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
        this.mSemService = semWallpaperManagerService;
    }

    public void generateCrop(WallpaperData wallpaperData) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("WPMS.generateCrop");
        generateCropInternal(wallpaperData);
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0466  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x052e  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0353 A[LOOP:0: B:67:0x034f->B:69:0x0353, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0355 A[EDGE_INSN: B:70:0x0355->B:71:0x0355 BREAK  A[LOOP:0: B:67:0x034f->B:69:0x0353], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0380 A[Catch: all -> 0x0506, Exception -> 0x050f, TryCatch #5 {Exception -> 0x050f, all -> 0x0506, blocks: (B:66:0x0347, B:67:0x034f, B:71:0x0355, B:73:0x0380, B:74:0x03b9, B:77:0x0468, B:79:0x04af, B:81:0x04b7), top: B:65:0x0347 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x04af A[Catch: all -> 0x0506, Exception -> 0x050f, TryCatch #5 {Exception -> 0x050f, all -> 0x0506, blocks: (B:66:0x0347, B:67:0x034f, B:71:0x0355, B:73:0x0380, B:74:0x03b9, B:77:0x0468, B:79:0x04af, B:81:0x04b7), top: B:65:0x0347 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x04b7 A[Catch: all -> 0x0506, Exception -> 0x050f, TRY_LEAVE, TryCatch #5 {Exception -> 0x050f, all -> 0x0506, blocks: (B:66:0x0347, B:67:0x034f, B:71:0x0355, B:73:0x0380, B:74:0x03b9, B:77:0x0468, B:79:0x04af, B:81:0x04b7), top: B:65:0x0347 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void generateCropInternal(WallpaperData wallpaperData) {
        boolean z;
        boolean z2;
        FileOutputStream fileOutputStream;
        final int i;
        int i2;
        int width;
        Bitmap decodeBitmap;
        BufferedOutputStream bufferedOutputStream;
        boolean z3;
        int min;
        int max;
        int displayId = this.mSemService.getDisplayId(wallpaperData.mSemWallpaperData);
        WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(displayId);
        DisplayInfo displayInfo = this.mWallpaperDisplayHelper.getDisplayInfo(displayId);
        Rect rect = new Rect(wallpaperData.cropHint);
        if ((Rune.isTablet() || Rune.isFolder()) && wallpaperData.getOrientation() != 0 && this.mSemService.getOrientation() != 0 && wallpaperData.getOrientation() != this.mSemService.getOrientation()) {
            int i3 = displayInfo.logicalWidth;
            displayInfo.logicalWidth = displayInfo.logicalHeight;
            displayInfo.logicalHeight = i3;
        }
        if (displayInfo.logicalWidth == 0 || displayInfo.logicalHeight == 0) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String absolutePath = wallpaperData.wallpaperFile.getAbsolutePath();
        SemWallpaperUtils.decodeFileConsiderQMG(absolutePath, options);
        boolean isQmgImage = SemWallpaperUtils.isQmgImage(absolutePath);
        String str = TAG;
        Log.m129d(str, "generateCrop  info=(" + displayInfo.logicalWidth + 'x' + displayInfo.logicalHeight + ") options=(" + options.outWidth + 'x' + options.outHeight + ") preload=" + wallpaperData.mSemWallpaperData.getIsPreloaded() + " isQmg=" + isQmgImage);
        Log.addLogString(str, "generateCrop  info=(" + displayInfo.logicalWidth + 'x' + displayInfo.logicalHeight + ") options=(" + options.outWidth + 'x' + options.outHeight + ") crop=(" + rect.width() + 'x' + rect.height() + ") dim=(" + displayDataOrCreate.mWidth + 'x' + displayDataOrCreate.mHeight + ") preload=" + wallpaperData.mSemWallpaperData.getIsPreloaded() + " isQmg=" + isQmgImage);
        boolean z4 = false;
        if (options.outWidth <= 0 || options.outHeight <= 0) {
            Slog.w(str, "Invalid wallpaper data");
        } else {
            try {
                if (rect.isEmpty()) {
                    rect.top = 0;
                    rect.left = 0;
                    rect.right = options.outWidth;
                    rect.bottom = options.outHeight;
                } else {
                    int i4 = rect.right;
                    int i5 = options.outWidth;
                    int i6 = i4 > i5 ? i5 - i4 : 0;
                    int i7 = rect.bottom;
                    int i8 = options.outHeight;
                    rect.offset(i6, i7 > i8 ? i8 - i7 : 0);
                    if (rect.left < 0) {
                        rect.left = 0;
                    }
                    if (rect.top < 0) {
                        rect.top = 0;
                    }
                    if (options.outHeight > rect.height() || options.outWidth > rect.width()) {
                        z = true;
                        z2 = rect.height() <= displayDataOrCreate.mHeight || rect.height() > GLHelper.getMaxTextureSize() || rect.width() > GLHelper.getMaxTextureSize();
                        if (z2) {
                            if (wallpaperData.mSemWallpaperData.getOrientation() == 2) {
                                if (Rune.isTablet() || (Rune.SUPPORT_SUB_DISPLAY_MODE && !WhichChecker.isSubDisplay(wallpaperData.mSemWallpaperData.getWhich()))) {
                                    min = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                    max = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                } else {
                                    min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                    max = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                }
                            } else {
                                min = Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
                                max = Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
                            }
                            if (((int) (rect.width() * (max / rect.height()))) < min) {
                                Log.m129d(str, "before center crop " + rect);
                                int abs = Math.abs(rect.height() - ((int) (((float) rect.width()) * (displayInfo.logicalHeight / displayInfo.logicalWidth)))) / 2;
                                rect.top = rect.top + abs;
                                rect.bottom = rect.bottom - abs;
                                Log.m129d(str, "after center crop" + rect);
                                if (Rune.SUPPORT_SUB_DISPLAY_MODE && wallpaperData.mSemWallpaperData.isThemeContents() && rect.height() < options.outHeight) {
                                    Log.m129d(str, "generateCrop: Center crop.");
                                    int height = (options.outHeight - rect.height()) / 2;
                                    Log.m129d(str, "generateCrop: [before] cropHint.top = " + rect.top + ", cropHint.bottom = " + rect.bottom);
                                    rect.top = height;
                                    rect.bottom = rect.bottom + height;
                                    Log.m129d(str, "generateCrop: [after] cropHint.top = " + rect.top + ", cropHint.bottom = " + rect.bottom);
                                }
                                z = true;
                            }
                        }
                        Slog.v(str, "crop: w=" + rect.width() + " h=" + rect.height());
                        Slog.v(str, "dims: w=" + displayDataOrCreate.mWidth + " h=" + displayDataOrCreate.mHeight);
                        Slog.v(str, "meas: w=" + options.outWidth + " h=" + options.outHeight);
                        Slog.v(str, "crop?=" + z + " scale?=" + z2);
                        if ((z && !z2) || wallpaperData.mSemWallpaperData.getIsPreloaded() || isQmgImage) {
                            z4 = FileUtils.copyFile(wallpaperData.wallpaperFile, wallpaperData.cropFile);
                            if (!z4) {
                                wallpaperData.cropFile.delete();
                            }
                        } else {
                            BufferedOutputStream bufferedOutputStream2 = null;
                            i = 1;
                            while (true) {
                                i2 = i * 2;
                                if (i2 <= rect.height() / displayDataOrCreate.mHeight) {
                                    break;
                                } else {
                                    i = i2;
                                }
                            }
                            options.inSampleSize = i;
                            options.inJustDecodeBounds = false;
                            final Rect rect2 = new Rect(rect);
                            scaleEstimateCrop(rect2, options.inSampleSize);
                            float height2 = displayDataOrCreate.mHeight / rect2.height();
                            int height3 = (int) (rect2.height() * height2);
                            width = (int) (rect2.width() * height2);
                            if (width > GLHelper.getMaxTextureSize()) {
                                int i9 = (int) (displayDataOrCreate.mHeight / height2);
                                int i10 = (int) (displayDataOrCreate.mWidth / height2);
                                rect2.set(rect);
                                rect2.left += (rect.width() - i10) / 2;
                                int height4 = rect2.top + ((rect.height() - i9) / 2);
                                rect2.top = height4;
                                rect2.right = rect2.left + i10;
                                rect2.bottom = height4 + i9;
                                rect.set(rect2);
                                scaleEstimateCrop(rect2, options.inSampleSize);
                            }
                            int height5 = (int) (rect2.height() * height2);
                            int width2 = (int) (rect2.width() * height2);
                            String str2 = TAG;
                            Slog.v(str2, "Decode parameters:");
                            Slog.v(str2, "  cropHint=" + rect + ", estimateCrop=" + rect2);
                            Slog.v(str2, "  down sampling=" + options.inSampleSize + ", hRatio=" + height2);
                            Slog.v(str2, "  dest=" + width + "x" + height3);
                            Slog.v(str2, "  safe=" + width2 + "x" + height5);
                            StringBuilder sb = new StringBuilder();
                            sb.append("  maxTextureSize=");
                            sb.append(GLHelper.getMaxTextureSize());
                            Slog.v(str2, sb.toString());
                            File file = new File(WallpaperUtils.getWallpaperDir(wallpaperData.userId), !wallpaperData.wallpaperFile.getName().equals("wallpaper_orig") ? "decode_record" : "decode_lock_record");
                            file.createNewFile();
                            Slog.v(str2, "record path =" + file.getPath() + ", record name =" + file.getName());
                            decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(wallpaperData.wallpaperFile), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.server.wallpaper.WallpaperCropper$$ExternalSyntheticLambda0
                                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                    WallpaperCropper.lambda$generateCropInternal$0(i, rect2, imageDecoder, imageInfo, source);
                                }
                            });
                            file.delete();
                            if (decodeBitmap != null) {
                                Slog.e(str2, "Could not decode new wallpaper");
                                fileOutputStream = null;
                                z3 = false;
                            } else {
                                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeBitmap, width2, height5, true);
                                fileOutputStream = new FileOutputStream(wallpaperData.cropFile);
                                try {
                                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 32768);
                                } catch (Exception unused) {
                                } catch (Throwable th) {
                                    th = th;
                                }
                                try {
                                    String semGetLastCallingPackage = wallpaperData.semGetLastCallingPackage(false);
                                    if (!TextUtils.isEmpty(semGetLastCallingPackage) && ("android.app.cts.wallpapers".equals(semGetLastCallingPackage) || "com.android.wallpaperbackup".equals(semGetLastCallingPackage))) {
                                        createScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
                                    } else {
                                        createScaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
                                    }
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream2 = bufferedOutputStream;
                                    z3 = true;
                                } catch (Exception unused2) {
                                    bufferedOutputStream2 = bufferedOutputStream;
                                    IoUtils.closeQuietly(bufferedOutputStream2);
                                    IoUtils.closeQuietly(fileOutputStream);
                                    if (!z4) {
                                    }
                                    if (wallpaperData.cropFile.exists()) {
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    bufferedOutputStream2 = bufferedOutputStream;
                                    IoUtils.closeQuietly(bufferedOutputStream2);
                                    IoUtils.closeQuietly(fileOutputStream);
                                    throw th;
                                }
                            }
                            IoUtils.closeQuietly(bufferedOutputStream2);
                            IoUtils.closeQuietly(fileOutputStream);
                            z4 = z3;
                        }
                    }
                }
                i = 1;
                while (true) {
                    i2 = i * 2;
                    if (i2 <= rect.height() / displayDataOrCreate.mHeight) {
                    }
                    i = i2;
                }
                options.inSampleSize = i;
                options.inJustDecodeBounds = false;
                final Rect rect22 = new Rect(rect);
                scaleEstimateCrop(rect22, options.inSampleSize);
                float height22 = displayDataOrCreate.mHeight / rect22.height();
                int height32 = (int) (rect22.height() * height22);
                width = (int) (rect22.width() * height22);
                if (width > GLHelper.getMaxTextureSize()) {
                }
                int height52 = (int) (rect22.height() * height22);
                int width22 = (int) (rect22.width() * height22);
                String str22 = TAG;
                Slog.v(str22, "Decode parameters:");
                Slog.v(str22, "  cropHint=" + rect + ", estimateCrop=" + rect22);
                Slog.v(str22, "  down sampling=" + options.inSampleSize + ", hRatio=" + height22);
                Slog.v(str22, "  dest=" + width + "x" + height32);
                Slog.v(str22, "  safe=" + width22 + "x" + height52);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("  maxTextureSize=");
                sb2.append(GLHelper.getMaxTextureSize());
                Slog.v(str22, sb2.toString());
                File file2 = new File(WallpaperUtils.getWallpaperDir(wallpaperData.userId), !wallpaperData.wallpaperFile.getName().equals("wallpaper_orig") ? "decode_record" : "decode_lock_record");
                file2.createNewFile();
                Slog.v(str22, "record path =" + file2.getPath() + ", record name =" + file2.getName());
                decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(wallpaperData.wallpaperFile), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.server.wallpaper.WallpaperCropper$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        WallpaperCropper.lambda$generateCropInternal$0(i, rect22, imageDecoder, imageInfo, source);
                    }
                });
                file2.delete();
                if (decodeBitmap != null) {
                }
                IoUtils.closeQuietly(bufferedOutputStream2);
                IoUtils.closeQuietly(fileOutputStream);
                z4 = z3;
            } catch (Exception unused3) {
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
            z = false;
            if (rect.height() <= displayDataOrCreate.mHeight) {
            }
            if (z2) {
            }
            Slog.v(str, "crop: w=" + rect.width() + " h=" + rect.height());
            Slog.v(str, "dims: w=" + displayDataOrCreate.mWidth + " h=" + displayDataOrCreate.mHeight);
            Slog.v(str, "meas: w=" + options.outWidth + " h=" + options.outHeight);
            Slog.v(str, "crop?=" + z + " scale?=" + z2);
            if (z) {
            }
            BufferedOutputStream bufferedOutputStream22 = null;
        }
        if (!z4) {
            Slog.e(TAG, "Unable to apply new wallpaper");
            wallpaperData.cropFile.delete();
        }
        if (wallpaperData.cropFile.exists()) {
            return;
        }
        SELinux.restorecon(wallpaperData.cropFile.getAbsoluteFile());
    }

    public static /* synthetic */ void lambda$generateCropInternal$0(int i, Rect rect, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setTargetSampleSize(i);
        imageDecoder.setCrop(rect);
    }

    public final void scaleEstimateCrop(Rect rect, int i) {
        float f = 1.0f / i;
        if (f != 1.0f) {
            rect.left = (int) ((rect.left * f) + 0.5f);
            rect.top = (int) ((rect.top * f) + 0.5f);
            rect.right = (int) (rect.right * f);
            rect.bottom = (int) (rect.bottom * f);
        }
    }
}
