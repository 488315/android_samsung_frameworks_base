package com.android.systemui.wallpaper.video;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class VideoFileSaveService extends Service {
    public String mVideoFileExt = "mp4";
    public int mUserId = 0;
    public int mCurentWhich = 2;
    public final AnonymousClass1 mVideoWallpaperFileFilter = new FilenameFilter(this) { // from class: com.android.systemui.wallpaper.video.VideoFileSaveService.1
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return str.contains("video_wallpaper");
        }
    };
    public final AnonymousClass2 mBinder = new AnonymousClass2();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wallpaper.video.VideoFileSaveService$2, reason: invalid class name */
    public final class AnonymousClass2 extends IVideoFileSaveService$Stub {
        public AnonymousClass2() {
        }

        public final boolean deleteVideoFileWithFilename(String str, boolean z) {
            if (UserHandle.semGetMyUserId() == 0) {
                File file = new File(getVideoFilePath(str, z));
                return file.exists() && file.delete();
            }
            throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
        }

        public final ParcelFileDescriptor getVideoFileDescriptorAsUserWithFilename(String str, boolean z) {
            if (UserHandle.semGetMyUserId() == 0) {
                try {
                    return ParcelFileDescriptor.open(new File(getVideoFilePath(str, z)), z ? 1006632960 : 939524096);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
        }

        public final String getVideoFilePath(String str, boolean z) {
            VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
            return z ? VideoFileSaveService.m2374$$Nest$mgetTempFilePath(videoFileSaveService, str, videoFileSaveService.mVideoFileExt, videoFileSaveService.mUserId, videoFileSaveService.mCurentWhich) : VideoFileSaveService.m2373$$Nest$mgetSavedFilePath(videoFileSaveService, str, videoFileSaveService.mVideoFileExt, videoFileSaveService.mUserId, videoFileSaveService.mCurentWhich);
        }

        public final boolean isVideoFileExistsWithFilename(String str, boolean z) {
            if (UserHandle.semGetMyUserId() != 0) {
                throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
            }
            String videoFilePath = getVideoFilePath(str, z);
            boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
            if (TextUtils.isEmpty(videoFilePath)) {
                return false;
            }
            return new File(videoFilePath).exists();
        }

        public final boolean renameVideoFileWithFilename(String str) {
            if (UserHandle.semGetMyUserId() != 0) {
                throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
            }
            VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
            String m2374$$Nest$mgetTempFilePath = VideoFileSaveService.m2374$$Nest$mgetTempFilePath(videoFileSaveService, str, videoFileSaveService.mVideoFileExt, videoFileSaveService.mUserId, videoFileSaveService.mCurentWhich);
            VideoFileSaveService videoFileSaveService2 = VideoFileSaveService.this;
            String m2373$$Nest$mgetSavedFilePath = VideoFileSaveService.m2373$$Nest$mgetSavedFilePath(videoFileSaveService2, str, videoFileSaveService2.mVideoFileExt, videoFileSaveService2.mUserId, videoFileSaveService2.mCurentWhich);
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
            File file = new File(m2374$$Nest$mgetTempFilePath);
            File file2 = new File(m2373$$Nest$mgetSavedFilePath);
            return file.exists() && !file2.exists() && file.renameTo(file2);
        }

        public final void setVideoLockscreenWallpaperAsOwnerWithFilename(String str) {
            if (UserHandle.semGetMyUserId() != 0) {
                throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
            }
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(VideoFileSaveService.this.getApplicationContext());
            String videoFilePath = getVideoFilePath(str, false);
            VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
            wallpaperManager.setVideoLockscreenWallpaper(videoFilePath, null, null, videoFileSaveService.mUserId, videoFileSaveService.mCurentWhich);
        }

        public final void setVideoWallpaperAsOwnerWithFilename(Bundle bundle, String str) {
            if (UserHandle.semGetMyUserId() != 0) {
                throw new IllegalStateException("This service must be run from the owner(" + UserHandle.semGetMyUserId() + ")");
            }
            boolean z = bundle != null ? bundle.getBoolean("allowBackup", true) : true;
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(VideoFileSaveService.this.getApplicationContext());
            String videoFilePath = getVideoFilePath(str, false);
            VideoFileSaveService videoFileSaveService = VideoFileSaveService.this;
            wallpaperManager.setVideoWallpaper(videoFilePath, null, null, videoFileSaveService.mUserId, videoFileSaveService.mCurentWhich, false, z, bundle);
        }
    }

    /* renamed from: -$$Nest$mgetSavedFilePath, reason: not valid java name */
    public static String m2373$$Nest$mgetSavedFilePath(VideoFileSaveService videoFileSaveService, String str, String str2, int i, int i2) {
        String str3;
        File filesDir = videoFileSaveService.getFilesDir();
        if (filesDir != null) {
            str3 = filesDir.getAbsolutePath();
        } else {
            Log.w("VideoFileCopyService", "getSavedFilePath() file is null");
            str3 = "/data/user_de/0/com.android.systemui/files";
        }
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "video_wallpaper_", "_", ".");
        m.append(str2);
        String sb = m.toString();
        if (TextUtils.isEmpty(str)) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, "/", sb);
        }
        return str3 + "/" + str + sb;
    }

    /* renamed from: -$$Nest$mgetTempFilePath, reason: not valid java name */
    public static String m2374$$Nest$mgetTempFilePath(VideoFileSaveService videoFileSaveService, String str, String str2, int i, int i2) {
        String str3;
        File filesDir = videoFileSaveService.getFilesDir();
        if (filesDir != null) {
            str3 = filesDir.getAbsolutePath();
        } else {
            Log.w("VideoFileCopyService", "getTempFilePath() file is null");
            str3 = "/data/user_de/0/com.android.systemui/files";
        }
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "video_wallpaper_", "_", "_temp.");
        m.append(str2);
        String sb = m.toString();
        if (TextUtils.isEmpty(str)) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, "/", sb);
        }
        return str3 + "/" + str + sb;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
