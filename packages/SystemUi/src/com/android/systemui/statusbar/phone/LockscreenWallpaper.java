package com.android.systemui.statusbar.phone;

import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.util.IndentingPrintWriter;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import java.io.PrintWriter;
import libcore.io.IoUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LockscreenWallpaper extends IWallpaperManagerCallback.Stub implements Runnable, Dumpable {
    public Bitmap mCache;
    public boolean mCached;
    public int mCurrentUserId;

    /* renamed from: mH */
    public final Handler f355mH;
    public AsyncTask mLoader;
    public final NotificationMediaManager mMediaManager;
    public final WallpaperManager mWallpaperManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoaderResult {
        public final Bitmap bitmap;
        public final boolean success;

        public LoaderResult(boolean z, Bitmap bitmap) {
            this.success = z;
            this.bitmap = bitmap;
        }
    }

    public LockscreenWallpaper(WallpaperManager wallpaperManager, IWallpaperManager iWallpaperManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, NotificationMediaManager notificationMediaManager, Handler handler, UserTracker userTracker) {
        dumpManager.registerDumpable("LockscreenWallpaper", this);
        this.mWallpaperManager = wallpaperManager;
        this.mCurrentUserId = ((UserTrackerImpl) userTracker).getUserId();
        this.mMediaManager = notificationMediaManager;
        this.f355mH = handler;
        if (iWallpaperManager == null || wallpaperManager.isLockscreenLiveWallpaperEnabled()) {
            return;
        }
        try {
            iWallpaperManager.setLockWallpaperCallback(this);
        } catch (RemoteException e) {
            Log.e("LockscreenWallpaper", "System dead?" + e);
        }
    }

    public final void assertLockscreenLiveWallpaperNotEnabled() {
        if (this.mWallpaperManager.isLockscreenLiveWallpaperEnabled()) {
            throw new IllegalStateException("Methods from LockscreenWallpaper.java should not be called in this version. The lock screen wallpaper should be managed by the WallpaperManagerService and not by this class.");
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("LockscreenWallpaper:");
        IndentingPrintWriter increaseIndent = new IndentingPrintWriter(printWriter, "  ").increaseIndent();
        increaseIndent.println("mCached=" + this.mCached);
        increaseIndent.println("mCache=" + this.mCache);
        increaseIndent.println("mCurrentUserId=" + this.mCurrentUserId);
        increaseIndent.println("mSelectedUser=null");
    }

    public final void onWallpaperChanged() {
        assertLockscreenLiveWallpaperNotEnabled();
        assertLockscreenLiveWallpaperNotEnabled();
        Handler handler = this.f355mH;
        if (handler == null) {
            Log.wtfStack("LockscreenWallpaper", "Trying to use LockscreenWallpaper before initialization.");
        } else {
            handler.removeCallbacks(this);
            this.f355mH.post(this);
        }
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        assertLockscreenLiveWallpaperNotEnabled();
    }

    @Override // java.lang.Runnable
    public final void run() {
        assertLockscreenLiveWallpaperNotEnabled();
        AsyncTask asyncTask = this.mLoader;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        final int i = this.mCurrentUserId;
        final UserHandle userHandle = null;
        this.mLoader = new AsyncTask() { // from class: com.android.systemui.statusbar.phone.LockscreenWallpaper.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                LoaderResult loaderResult;
                LockscreenWallpaper lockscreenWallpaper = LockscreenWallpaper.this;
                int i2 = i;
                UserHandle userHandle2 = userHandle;
                lockscreenWallpaper.assertLockscreenLiveWallpaperNotEnabled();
                if (!lockscreenWallpaper.mWallpaperManager.isWallpaperSupported()) {
                    return new LoaderResult(true, null);
                }
                if (userHandle2 != null) {
                    i2 = userHandle2.getIdentifier();
                }
                ParcelFileDescriptor wallpaperFile = lockscreenWallpaper.mWallpaperManager.getWallpaperFile(2, i2);
                if (wallpaperFile == null) {
                    return userHandle2 != null ? new LoaderResult(true, lockscreenWallpaper.mWallpaperManager.getBitmapAsUser(userHandle2.getIdentifier(), true)) : new LoaderResult(true, null);
                }
                try {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.HARDWARE;
                        loaderResult = new LoaderResult(true, BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor(), null, options));
                    } catch (OutOfMemoryError e) {
                        Log.w("LockscreenWallpaper", "Can't decode file", e);
                        loaderResult = new LoaderResult(false, null);
                    }
                    return loaderResult;
                } finally {
                    IoUtils.closeQuietly(wallpaperFile);
                }
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                LoaderResult loaderResult = (LoaderResult) obj;
                super.onPostExecute(loaderResult);
                if (isCancelled()) {
                    return;
                }
                if (loaderResult.success) {
                    LockscreenWallpaper lockscreenWallpaper = LockscreenWallpaper.this;
                    lockscreenWallpaper.mCached = true;
                    lockscreenWallpaper.mCache = loaderResult.bitmap;
                    lockscreenWallpaper.mMediaManager.updateMediaMetaData(true, true);
                }
                LockscreenWallpaper.this.mLoader = null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final void onSemMultipackApplied(int i) {
    }

    public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
    }

    public final void onSemBackupStatusChanged(int i, int i2, int i3) {
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
    }

    public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
    }
}
