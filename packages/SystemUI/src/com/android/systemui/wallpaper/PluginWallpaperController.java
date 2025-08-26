package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
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

/* loaded from: classes3.dex */
public class PluginWallpaperController implements PluginWallpaper, PluginWallpaperCallback {
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
    public class AnonymousClass1 {
        public AnonymousClass1(PluginWallpaperController pluginWallpaperController) {
        }

        public static void onMultipackApplied(int i) {
            Log.i("PluginWallpaperController", "onMultipackApplied: reason = ".concat(i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "APPLY_MULTIPACK_RESULT_FAIL_DLS_INTERNAL_ERROR" : "APPLY_MULTIPACK_RESULT_FAIL_LIVE_WALLPAPER" : "APPLY_MULTIPACK_RESULT_FAIL_RETRY_COUNT_OVER" : "APPLY_MULTIPACK_RESULT_SUCCESS"));
        }
    }

    public PluginWallpaperController(Context context, WallpaperManager wallpaperManager, WallpaperLogger wallpaperLogger, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SelectedUserInteractor selectedUserInteractor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        int iSemGetWallpaperType = -1;
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
        int iSemGetWallpaperType2 = wallpaperManager.semGetWallpaperType(6);
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            iSemGetWallpaperType = wallpaperManager.semGetWallpaperType(6);
        }
        if ((iSemGetWallpaperType2 == 3 || iSemGetWallpaperType2 == 1000 || iSemGetWallpaperType == 3 || iSemGetWallpaperType == 1000) && !MultiPackDispatcher.enableDlsIfDisabled(context)) {
            Log.e("PluginWallpaperController", "Failed to enable DLS.");
        }
    }

    public static int getScreen(int i) {
        return (!WhichChecker.isFlagEnabled(i, 16) || LsRune.WALLPAPER_SUB_WATCHFACE) ? 0 : 1;
    }

    public final boolean containsVideo(int i) throws IOException {
        String strProbeContentType;
        if (this.mWallpaperManager.semGetWallpaperType(i) != 3) {
            return false;
        }
        Uri uriSemGetUri = this.mWallpaperManager.semGetUri(i);
        File[] fileArrListFiles = new File("/data/overlays/homewallpaper/" + uriSemGetUri.getHost() + uriSemGetUri.getPath()).listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            for (File file : fileArrListFiles) {
                String path = file.getPath();
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                try {
                    strProbeContentType = URLConnection.guessContentTypeFromName(path);
                } catch (Exception unused) {
                    try {
                        strProbeContentType = Files.probeContentType(Paths.get(path, new String[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                        strProbeContentType = null;
                    }
                }
                if (strProbeContentType != null && strProbeContentType.startsWith(ServiceTuple.MEDIA_CAP_VIDEO)) {
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
        int iSemGetWallpaperType;
        int screen = getScreen(i);
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE;
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        if (z2 && WhichChecker.isFlagEnabled(i, 16) && this.mWallpaperManager.semGetWallpaperType(i) != 3 && pluginWallpaperManager.isCloneDisplayRequired()) {
            screen = 0;
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, screen, "getWallpaperBitmap: which = ", ", screen = ", "PluginWallpaperController");
        boolean zHasIntelligentCrops = hasIntelligentCrops(screen, false);
        boolean zHasIntelligentCrops2 = hasIntelligentCrops(screen, true);
        Bitmap fbeWallpaper = getFbeWallpaper(i, false, zHasIntelligentCrops2);
        boolean zIsValidBitmap = WallpaperUtils.isValidBitmap(fbeWallpaper);
        boolean zIsWallpaperSrcBitmap = pluginWallpaperManager.isWallpaperSrcBitmap(screen);
        if (!zIsValidBitmap && pluginWallpaperManager.isDynamicWallpaperEnabled(screen)) {
            if (zIsWallpaperSrcBitmap) {
                fbeWallpaper = pluginWallpaperManager.getWallpaperBitmap(screen);
                if (WallpaperUtils.isValidBitmap(fbeWallpaper)) {
                    Bitmap bitmapCopy = fbeWallpaper.copy(fbeWallpaper.getConfig(), false);
                    Log.d("PluginWallpaperController", "getWallpaperBitmap: copiedBitmap = " + bitmapCopy + ", hasICrops = " + zHasIntelligentCrops);
                    return bitmapCopy;
                }
            } else if (pluginWallpaperManager.isWallpaperSrcPath(screen)) {
                String wallpaperPath = pluginWallpaperManager.getWallpaperPath(screen);
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getWallpaperBitmap: path = ", wallpaperPath, "PluginWallpaperController");
                fbeWallpaper = pluginWallpaperManager.getBitmapFromPath(wallpaperPath, zHasIntelligentCrops);
            } else if (pluginWallpaperManager.isWallpaperSrcUri(screen)) {
                Uri wallpaperUri = pluginWallpaperManager.getWallpaperUri(screen);
                Log.d("PluginWallpaperController", "getWallpaperBitmap: uri = " + wallpaperUri);
                fbeWallpaper = pluginWallpaperManager.getBitmapFromUri(wallpaperUri, zHasIntelligentCrops);
            } else {
                Log.e("PluginWallpaperController", "getWallpaperBitmap: source is not identified.");
            }
        }
        if (!WallpaperUtils.isValidBitmap(fbeWallpaper) && ((iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i)) == 3 || iSemGetWallpaperType == 1000)) {
            Log.d("PluginWallpaperController", "getWallpaperBitmap: bitmap is null. Trying to get fbe wallpaper.");
            fbeWallpaper = getFbeWallpaper(i, true, zHasIntelligentCrops2);
        }
        StringBuilder sb = new StringBuilder("getWallpaperBitmap: bitmap = ");
        sb.append(fbeWallpaper);
        sb.append(", hasICrops = ");
        sb.append(zHasIntelligentCrops);
        sb.append(", hasICropsForFbe = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, zHasIntelligentCrops2, "PluginWallpaperController");
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
        Context context = this.mContext;
        boolean z2 = WallpaperUtils.mIsExternalLiveWallpaper;
        Configuration configuration = context.getResources().getConfiguration();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i2 = configuration.orientation;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int i3 = point.x;
        int i4 = point.y;
        boolean z3 = configuration.semMobileKeyboardCovered == 1;
        int i5 = z3 ? displayMetrics.widthPixels : i2 == 1 ? i3 : i4;
        if (z3) {
            i3 = displayMetrics.heightPixels;
        } else if (i2 == 1) {
            i3 = i4;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet") && i5 < i3 && i2 == 2) {
            Log.d("WallpaperUtils", "getLogicalDisplaySize: Adjust width and height for landscape tablet.");
            int i6 = i5;
            i5 = i3;
            i3 = i6;
        }
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i3, "getLogicalDisplaySize: ", " x ", " dm ");
        sbM.append(displayMetrics.widthPixels);
        sbM.append(" x ");
        sbM.append(displayMetrics.heightPixels);
        sbM.append(" orientation:");
        sbM.append(i2);
        Log.d("WallpaperUtils", sbM.toString());
        Size size = new Size(i5, i3);
        Rect nearestCropHint = !TextUtils.isEmpty(fbeWallpaperIntelligentCrop) ? IntelligentCropHelper.getNearestCropHint(new Point(size.getWidth(), size.getHeight()), IntelligentCropHelper.parseCropHints(fbeWallpaperIntelligentCrop)) : null;
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("hasIntelligentCrops: screen = ", i, ", isFbe = ", z, ", iCrops = ");
        sbM2.append(fbeWallpaperIntelligentCrop);
        sbM2.append(", displaySize = ");
        sbM2.append(size);
        sbM2.append(", src = ");
        sbM2.append(nearestCropHint);
        Log.d("PluginWallpaperController", sbM2.toString());
        return (nearestCropHint == null || nearestCropHint.isEmpty()) ? false : true;
    }

    public final boolean isPluginWallpaperRequired(int i) {
        int iSemGetWallpaperType;
        int screen = getScreen(i);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        if (selectedUserId > 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "isPluginWallpaperRequired: currentUser = ", "PluginWallpaperController");
            return false;
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        boolean zIsDynamicWallpaperEnabled = pluginWallpaperManager.isDynamicWallpaperEnabled(screen);
        boolean zIsFbeAvailable = pluginWallpaperManager.isFbeAvailable(screen);
        boolean z = zIsDynamicWallpaperEnabled || zIsFbeAvailable;
        CarrierTextManager$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("isPluginWallpaperRequired: which = ", i, ", isRequired = ", z, ", isPluginWallpaper = "), zIsDynamicWallpaperEnabled, ", isFbeCondition = ", zIsFbeAvailable, "PluginWallpaperController");
        if (z || !((iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(i)) == 3 || iSemGetWallpaperType == 1000)) {
            return z;
        }
        boolean zIsFbeWallpaperAvailable = pluginWallpaperManager.isFbeWallpaperAvailable(screen);
        EmergencyButtonController$$ExternalSyntheticOutline0.m("isPluginWallpaperRequired: PluginWallpaper is not ready yet. isFbeAvailable = ", "PluginWallpaperController", zIsFbeWallpaperAvailable);
        return zIsFbeWallpaperAvailable;
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onDataCleared() {
        int iSemGetWallpaperType = this.mWallpaperManager.semGetWallpaperType(6);
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            this.mWallpaperManager.semGetWallpaperType(18);
        }
        if (iSemGetWallpaperType == 3) {
            startMultiPack(6);
        }
        if (iSemGetWallpaperType == 3) {
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onWallpaperUpdate: isFirst = ", "PluginWallpaperController", z);
        if (z) {
            int screen = getScreen(WallpaperUtils.sCurrentWhich);
            int wallpaperId = this.mWallpaperManager.getWallpaperId(WallpaperUtils.sCurrentWhich);
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(screen, "onWallpaperUpdate: mWallpaperId[", "] = ");
            int[] iArr = this.mWallpaperId;
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sbM, iArr[screen], ", wallpaperId = ", wallpaperId, "PluginWallpaperController");
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
            MultiPackDispatcher multiPackDispatcher = new MultiPackDispatcher(this.mContext, this.mWallpaperLogger, this.mPluginLockUtils, this.mSelectedUserInteractor.getSelectedUserId());
            this.mMultiPackDispatcher = multiPackDispatcher;
            multiPackDispatcher.mOnApplyMultipackListener = new AnonymousClass1(this);
        }
        MultiPackDispatcher multiPackDispatcher2 = this.mMultiPackDispatcher;
        if (multiPackDispatcher2 != null) {
            multiPackDispatcher2.startMultipack(i);
        }
    }
}
