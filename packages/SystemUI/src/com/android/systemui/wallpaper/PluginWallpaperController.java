package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.sec.ims.presence.ServiceTuple;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public final class PluginWallpaperController implements PluginWallpaper, PluginWallpaperCallback {
    public final Context mContext;
    public MultiPackDispatcher mMultiPackDispatcher;
    public final PluginLockUtils mPluginLockUtils;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SparseArray mWallpaperConsumers = new SparseArray();
    public final int[] mWallpaperId;
    public final WallpaperLogger mWallpaperLogger;
    public final WallpaperManager mWallpaperManager;

    /* renamed from: com.android.systemui.wallpaper.PluginWallpaperController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1(PluginWallpaperController pluginWallpaperController) {
        }

        public static void onMultipackApplied(int i) {
            Log.i("PluginWallpaperController", "onMultipackApplied: reason = ".concat(i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "APPLY_MULTIPACK_RESULT_FAIL_DLS_INTERNAL_ERROR" : "APPLY_MULTIPACK_RESULT_FAIL_LIVE_WALLPAPER" : "APPLY_MULTIPACK_RESULT_FAIL_RETRY_COUNT_OVER" : "APPLY_MULTIPACK_RESULT_SUCCESS"));
        }
    }

    public PluginWallpaperController(Context context, WallpaperManager wallpaperManager, WallpaperLogger wallpaperLogger, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SelectedUserInteractor selectedUserInteractor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        int i = -1;
        int[] iArr = {-1, -1};
        this.mWallpaperId = iArr;
        this.mContext = context;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        pluginWallpaperManager.setLockWallpaperCallback(this);
        this.mPluginLockUtils = pluginLockUtils;
        this.mWallpaperManager = wallpaperManager;
        this.mWallpaperLogger = wallpaperLogger;
        this.mSelectedUserInteractor = selectedUserInteractor;
        iArr[0] = wallpaperManager.getWallpaperId(6);
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            iArr[1] = wallpaperManager.getWallpaperId(18);
        }
        int semGetWallpaperType = wallpaperManager.semGetWallpaperType(6);
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            i = wallpaperManager.semGetWallpaperType(6);
        }
        if ((semGetWallpaperType == 3 || semGetWallpaperType == 1000 || i == 3 || i == 1000) && !MultiPackDispatcher.enableDlsIfDisabled(context)) {
            Log.e("PluginWallpaperController", "Failed to enable DLS.");
        }
    }

    public static int getScreen(int i) {
        return (!WhichChecker.isFlagEnabled(i, 16) || LsRune.WALLPAPER_SUB_WATCHFACE) ? 0 : 1;
    }

    public final boolean containsVideo(int i) {
        String str;
        if (this.mWallpaperManager.semGetWallpaperType(i) != 3) {
            return false;
        }
        Uri semGetUri = this.mWallpaperManager.semGetUri(i);
        File[] listFiles = new File("/data/overlays/homewallpaper/" + semGetUri.getHost() + semGetUri.getPath()).listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                String path = file.getPath();
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                try {
                    str = URLConnection.guessContentTypeFromName(path);
                } catch (Exception unused) {
                    try {
                        str = Files.probeContentType(Paths.get(path, new String[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                        str = null;
                    }
                }
                if (str != null && str.startsWith(ServiceTuple.MEDIA_CAP_VIDEO)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final Bitmap getFbeWallpaper(int i, boolean z, boolean z2) {
        Bitmap fbeWallpaper;
        int screen = getScreen(i);
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        if (pluginWallpaperManager.isFbeAvailable(screen) || z) {
            try {
                this.mWallpaperManager.semSetDLSWallpaperColors(pluginWallpaperManager.getFbeSemWallpaperColors(screen), i);
            } catch (IllegalArgumentException e) {
                Log.e("PluginWallpaperController", "getFbeWallpaper: " + e.getMessage());
            }
            fbeWallpaper = pluginWallpaperManager.getFbeWallpaper(screen, z2);
        } else {
            fbeWallpaper = null;
        }
        Log.d("PluginWallpaperController", "getFbeWallpaper: bitmap = " + fbeWallpaper);
        return fbeWallpaper;
    }

    public final Bitmap getWallpaperBitmap(int i) {
        int semGetWallpaperType;
        int screen = getScreen(i);
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        if (pluginWallpaperManager.isCloneDisplayRequired()) {
            screen = 0;
        }
        boolean hasIntelligentCrops = hasIntelligentCrops(screen, false);
        boolean hasIntelligentCrops2 = hasIntelligentCrops(screen, true);
        Bitmap fbeWallpaper = getFbeWallpaper(i, false, hasIntelligentCrops2);
        boolean isValidBitmap = WallpaperUtils.isValidBitmap(fbeWallpaper);
        boolean isWallpaperSrcBitmap = pluginWallpaperManager.isWallpaperSrcBitmap(screen);
        if (!isValidBitmap && pluginWallpaperManager.isDynamicWallpaperEnabled(screen)) {
            if (isWallpaperSrcBitmap) {
                fbeWallpaper = pluginWallpaperManager.getWallpaperBitmap(screen);
                if (WallpaperUtils.isValidBitmap(fbeWallpaper)) {
                    Bitmap copy = fbeWallpaper.copy(fbeWallpaper.getConfig(), false);
                    Log.d("PluginWallpaperController", "getWallpaperBitmap: copiedBitmap = " + copy + ", hasICrops = " + hasIntelligentCrops);
                    return copy;
                }
            } else if (pluginWallpaperManager.isWallpaperSrcPath(screen)) {
                String wallpaperPath = pluginWallpaperManager.getWallpaperPath(screen);
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getWallpaperBitmap: path = ", wallpaperPath, "PluginWallpaperController");
                fbeWallpaper = pluginWallpaperManager.getBitmapFromPath(wallpaperPath, hasIntelligentCrops);
            } else if (pluginWallpaperManager.isWallpaperSrcUri(screen)) {
                Uri wallpaperUri = pluginWallpaperManager.getWallpaperUri(screen);
                Log.d("PluginWallpaperController", "getWallpaperBitmap: uri = " + wallpaperUri);
                fbeWallpaper = pluginWallpaperManager.getBitmapFromUri(wallpaperUri, hasIntelligentCrops);
            } else {
                Log.e("PluginWallpaperController", "getWallpaperBitmap: source is not identified.");
            }
        }
        if (!WallpaperUtils.isValidBitmap(fbeWallpaper) && ((semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i)) == 3 || semGetWallpaperType == 1000)) {
            Log.d("PluginWallpaperController", "getWallpaperBitmap: bitmap is null. Trying to get fbe wallpaper.");
            fbeWallpaper = getFbeWallpaper(i, true, hasIntelligentCrops2);
        }
        Log.d("PluginWallpaperController", "getWallpaperBitmap: bitmap = " + fbeWallpaper + ", hasICrops = " + hasIntelligentCrops);
        return fbeWallpaper;
    }

    public final int getWallpaperType(int i) {
        int screen = getScreen(i);
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        return pluginWallpaperManager.isFbeAvailable(screen) ? pluginWallpaperManager.getFbeWallpaperType(screen) : pluginWallpaperManager.getWallpaperType(screen);
    }

    public final boolean hasIntelligentCrops(int i, boolean z) {
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        String fbeWallpaperIntelligentCrop = z ? pluginWallpaperManager.getFbeWallpaperIntelligentCrop(i) : pluginWallpaperManager.getWallpaperIntelligentCrop(i);
        ExifInterface$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("hasIntelligentCrops: screen = ", i, ", isFbe = ", z, ", iCrops = "), fbeWallpaperIntelligentCrop, "PluginWallpaperController");
        Size logicalDisplaySize = WallpaperUtils.getLogicalDisplaySize(this.mContext);
        Rect nearestCropHint = !TextUtils.isEmpty(fbeWallpaperIntelligentCrop) ? IntelligentCropHelper.getNearestCropHint(new Point(logicalDisplaySize.getWidth(), logicalDisplaySize.getHeight()), IntelligentCropHelper.parseCropHints(fbeWallpaperIntelligentCrop)) : null;
        return (nearestCropHint == null || nearestCropHint.isEmpty()) ? false : true;
    }

    public final boolean isPluginWallpaperRequired(int i) {
        int semGetWallpaperType;
        int screen = getScreen(i);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        if (selectedUserId > 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "isPluginWallpaperRequired: currentUser = ", "PluginWallpaperController");
            return false;
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        boolean isDynamicWallpaperEnabled = pluginWallpaperManager.isDynamicWallpaperEnabled(screen);
        boolean isFbeAvailable = pluginWallpaperManager.isFbeAvailable(screen);
        boolean z = isDynamicWallpaperEnabled || isFbeAvailable;
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("isPluginWallpaperRequired: which = ", i, ", isRequired = ", z, ", isPluginWallpaper = "), isDynamicWallpaperEnabled, ", isFbeCondition = ", isFbeAvailable, "PluginWallpaperController");
        if (z || !((semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i)) == 3 || semGetWallpaperType == 1000)) {
            return z;
        }
        boolean isFbeWallpaperAvailable = pluginWallpaperManager.isFbeWallpaperAvailable(screen);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isPluginWallpaperRequired: PluginWallpaper is not ready yet. isFbeAvailable = ", "PluginWallpaperController", isFbeWallpaperAvailable);
        return isFbeWallpaperAvailable;
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onDataCleared() {
        int semGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(6);
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            this.mWallpaperManager.semGetWallpaperType(18);
        }
        if (semGetWallpaperType == 3) {
            startMultiPack(6);
        }
        if (semGetWallpaperType == 3) {
            startMultiPack(18);
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onReady() {
        Log.d("PluginWallpaperController", "onReady");
        sendUpdate(false);
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors != null && WhichChecker.isFlagEnabled(semWallpaperColors.getWhich(), 1)) {
            Log.d("PluginWallpaperController", "onWallpaperHintUpdate: invalid which. which = " + semWallpaperColors.getWhich());
        } else {
            int which = semWallpaperColors != null ? semWallpaperColors.getWhich() : WallpaperUtils.sCurrentWhich;
            if ((which & 60) == 0) {
                which |= 4;
            }
            this.mWallpaperManager.semSetDLSWallpaperColors(semWallpaperColors, which);
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperUpdate(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onWallpaperUpdate: isFirst = ", "PluginWallpaperController", z);
        if (z) {
            int screen = getScreen(WallpaperUtils.sCurrentWhich);
            int wallpaperId = this.mWallpaperManager.getWallpaperId(WallpaperUtils.sCurrentWhich);
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(screen, "onWallpaperUpdate: mWallpaperId[", "] = ");
            int[] iArr = this.mWallpaperId;
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, iArr[screen], ", wallpaperId = ", wallpaperId, "PluginWallpaperController");
            iArr[screen] = wallpaperId;
        }
        sendUpdate(z);
    }

    public final void sendUpdate(boolean z) {
        Consumer consumer = (Consumer) this.mWallpaperConsumers.get(getScreen(WallpaperUtils.sCurrentWhich));
        RecyclerView$$ExternalSyntheticOutline0.m(WallpaperUtils.sCurrentWhich, "PluginWallpaperController", new StringBuilder("sendUpdate: which = "));
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
    }

    public final void setWallpaperUpdateConsumer(int i, Consumer consumer) {
        Log.d("PluginWallpaperController", "setWallpaperUpdateConsumer: which = " + i + ", consumer = " + consumer);
        this.mWallpaperConsumers.put(getScreen(i), consumer);
    }

    public final void startMultiPack(int i) {
        if (this.mMultiPackDispatcher == null) {
            MultiPackDispatcher multiPackDispatcher = new MultiPackDispatcher(this.mContext, this.mWallpaperLogger, this.mPluginLockUtils, this.mSelectedUserInteractor.getSelectedUserId(false));
            this.mMultiPackDispatcher = multiPackDispatcher;
            multiPackDispatcher.mOnApplyMultipackListener = new AnonymousClass1(this);
        }
        MultiPackDispatcher multiPackDispatcher2 = this.mMultiPackDispatcher;
        if (multiPackDispatcher2 != null) {
            multiPackDispatcher2.startMultipack(i);
        }
    }
}
