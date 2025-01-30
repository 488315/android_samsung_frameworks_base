package com.samsung.server.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SELinux;
import android.os.SystemClock;
import com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: PreloadedLiveWallpaperHelper.java */
/* loaded from: classes2.dex */
public class ThumbnailFetcher {
    public static final String TAG = "ThumbnailFetcher";
    public Context mContext;
    public ProviderRequester mProviderRequester;
    public SemWallpaperManagerService mSemService;

    public ThumbnailFetcher(Context context, SemWallpaperManagerService semWallpaperManagerService, ProviderRequester providerRequester, PreloadedLiveWallpaperHelper.Callback callback) {
        this.mContext = context.getApplicationContext();
        this.mSemService = semWallpaperManagerService;
        this.mProviderRequester = providerRequester;
    }

    public synchronized File getThumbnailFile(int i, int i2, ComponentName componentName, int i3, Bundle bundle) {
        File thumbnailFile = this.mSemService.getThumbnailFile(i, i2, i3);
        if (thumbnailFile.exists()) {
            return thumbnailFile;
        }
        if (fetchThumbnailAndWriteToFile(componentName, i, i2, i3, bundle, thumbnailFile)) {
            return thumbnailFile;
        }
        return null;
    }

    public final synchronized boolean fetchThumbnailAndWriteToFile(ComponentName componentName, int i, int i2, int i3, Bundle bundle, File file) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ParcelFileDescriptor requestThumbnail = this.mProviderRequester.requestThumbnail(componentName, i, i2, i3, bundle);
        if (requestThumbnail == null) {
            Log.m130e(TAG, "fetchThumbnailAndWriteToFile : failed to get remote FD");
            return false;
        }
        try {
            File file2 = new File(file.getAbsolutePath() + ".tmp");
            if (!copyFile(requestThumbnail, file2)) {
                Log.m130e(TAG, "fetchThumbnailAndWriteToFile : failed to copy remote thumbnail file");
                file2.delete();
                return false;
            }
            if (!file2.renameTo(file)) {
                Log.m134w(TAG, "fetchThumbnailAndWriteToFile : failed to move from temp file to thumbnail file");
                file2.delete();
                try {
                    requestThumbnail.close();
                } catch (IOException unused) {
                }
                return false;
            }
            if (!SELinux.restorecon(file)) {
                Log.m134w(TAG, "fetchThumbnailAndWriteToFile : restorecon failed");
            }
            long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
            Log.m132i(TAG, "fetchThumbnailAndWriteToFile : finished. " + file.getAbsolutePath() + ", " + file.length() + " bytes, " + elapsedRealtime2 + "ms");
            try {
                requestThumbnail.close();
            } catch (IOException unused2) {
            }
            return true;
        } finally {
            try {
                requestThumbnail.close();
            } catch (IOException unused3) {
            }
        }
    }

    public final synchronized boolean copyFile(ParcelFileDescriptor parcelFileDescriptor, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    FileUtils.copy(fileInputStream, fileOutputStream);
                    fileOutputStream.close();
                    fileInputStream.close();
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Log.m131e(TAG, "copyFile : e=" + e, e);
            return false;
        }
        return true;
    }
}
