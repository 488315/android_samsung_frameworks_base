package com.android.server.wm;

import android.R;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.CustomLetterboxEnhancedController;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class CustomLetterboxConfiguration {
    public static final int DEFAULT_ENHANCED_MODE;
    public static final int DEFAULT_MODE;
    public boolean mAllowFreezeWallpaper;
    public SparseArray mEnhancedControllers;
    public boolean mHasLiveWallpaper;
    public int mLetterboxActivityCornersRadius;
    public final Color mLetterboxBackgroundColor;
    public int mLetterboxBackgroundWallpaperBlurRadius;
    public final float mLetterboxBackgroundWallpaperDarkScrimAlpha;
    public int mMode;
    public int mNightMode;
    public WindowManagerService mWmService;

    public abstract class LazyHolder {
        public static final CustomLetterboxConfiguration sInstance = new CustomLetterboxConfiguration();
    }

    /* renamed from: -$$Nest$mupdateWallpaperType, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m13131$$Nest$mupdateWallpaperType(CustomLetterboxConfiguration customLetterboxConfiguration) {
        customLetterboxConfiguration.updateWallpaperType();
    }

    public static boolean isEnabledEnhanced(int i) {
        return i == 2 || i == 6 || i == 7;
    }

    public static CustomLetterboxConfiguration getInstance() {
        return LazyHolder.sInstance;
    }

    static {
        int i;
        if (CoreRune.ONE_UI_6_1_1) {
            i = 9;
        } else {
            i = CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_HIDING_WALLPAPER ? 7 : 2;
        }
        DEFAULT_ENHANCED_MODE = i;
        if (!CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED) {
            i = 1;
        }
        DEFAULT_MODE = i;
    }

    public CustomLetterboxConfiguration() {
        this.mMode = DEFAULT_MODE;
        this.mLetterboxBackgroundColor = Color.valueOf(-16777216);
        this.mLetterboxBackgroundWallpaperDarkScrimAlpha = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mEnhancedControllers = CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED ? new SparseArray() : null;
    }

    public static void setWindowManager(WindowManagerService windowManagerService) {
        getInstance().mWmService = windowManagerService;
    }

    public static void onConfigurationChanged(int i) {
        if ((671094272 & i) == 0) {
            return;
        }
        final CustomLetterboxConfiguration customLetterboxConfiguration = getInstance();
        customLetterboxConfiguration.reloadResources();
        if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR) {
            Slog.d("CustomLetterbox", "onConfigurationChanged: changes=" + i);
            final boolean z = i != 0;
            customLetterboxConfiguration.mWmService.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.CustomLetterboxConfiguration$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CustomLetterboxConfiguration.this.applyLetterboxEnhancedIfNeeded((DisplayContent) obj, true, z);
                }
            });
        }
    }

    public final void reloadResources() {
        Resources resources = this.mWmService.mLetterboxConfiguration.mContext.getResources();
        this.mLetterboxActivityCornersRadius = resources.getDimensionPixelSize(R.dimen.config_progressBarCornerRadius);
        this.mLetterboxBackgroundWallpaperBlurRadius = resources.getDimensionPixelSize(R.dimen.config_qsTileStrokeWidthActive);
        this.mNightMode = resources.getConfiguration().uiMode & 48;
    }

    public static boolean hasWallpaperBackgroundForLetterbox(ActivityRecord activityRecord) {
        return isCustomLetterboxEnabled(activityRecord) && getCustomLetterboxBackgroundType(activityRecord) == 3;
    }

    public static void setUseLetterbox(boolean z) {
        setMode(z ? 8 : DEFAULT_MODE);
    }

    public static void setMode(int i) {
        if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED || !isEnabledEnhanced(i)) {
            if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_HIDING_WALLPAPER || i != 7) {
                CustomLetterboxConfiguration customLetterboxConfiguration = getInstance();
                WindowManagerGlobalLock windowManagerGlobalLock = customLetterboxConfiguration.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (i == customLetterboxConfiguration.mMode) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        customLetterboxConfiguration.mMode = i;
                        customLetterboxConfiguration.mWmService.requestTraversal();
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        }
    }

    public static boolean isCustomLetterboxEnabled(ActivityRecord activityRecord) {
        return (getInstance().mMode == 0 || activityRecord == null || !activityRecord.mCompatRecord.isCompatModeEnabled() || !activityRecord.mCompatRecord.getController().supportsCustomLetterbox() || activityRecord.mCompatRecord.isAboveEmbeddedTaskFragment()) ? false : true;
    }

    public static boolean shouldUseCapturedBlurWallpaper() {
        return getInstance().mMode == 9;
    }

    public static boolean isAllowFreezeWallpaper(WindowState windowState) {
        return getInstance().mAllowFreezeWallpaper || windowState == null || !isCustomLetterboxEnabled(windowState.mActivityRecord);
    }

    public static int getCustomLetterboxBackgroundType(ActivityRecord activityRecord) {
        if (activityRecord.mAtmService.mMultiTaskingController.shouldNotSupportWallpaper()) {
            return 0;
        }
        if (isCustomLetterboxEnabled(activityRecord)) {
            return getInstance().mMode == 8 ? 0 : 3;
        }
        return -1;
    }

    public static int getCustomLetterboxActivityCornersRadius(ActivityRecord activityRecord) {
        if (!isCustomLetterboxEnabled(activityRecord)) {
            return -1;
        }
        int i = getInstance().mMode;
        if (i == 4 || i == 5 || i == 6) {
            return 0;
        }
        return getInstance().mLetterboxActivityCornersRadius;
    }

    public static Color getCustomLetterboxBackgroundColor(ActivityRecord activityRecord) {
        if (isCustomLetterboxEnabled(activityRecord)) {
            return getInstance().mLetterboxBackgroundColor;
        }
        return null;
    }

    public static float getCustomLetterboxBackgroundWallpaperDarkScrimAlpha(ActivityRecord activityRecord) {
        if (!isCustomLetterboxEnabled(activityRecord)) {
            return -1.0f;
        }
        Objects.requireNonNull(getInstance());
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public static int getCustomLetterboxBackgroundWallpaperBlurRadius(ActivityRecord activityRecord) {
        if (!isCustomLetterboxEnabled(activityRecord)) {
            return -1;
        }
        int i = getInstance().mMode;
        if (i == 3 || i == 5 || i == 8) {
            return 0;
        }
        if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX && shouldUseCapturedBlurWallpaper()) {
            DisplayContent displayContent = activityRecord.mDisplayContent;
            if (displayContent != null) {
                return BlurWallpaperLetterbox.getLetterboxWallpaperBlurRadius(displayContent);
            }
            return -1;
        }
        return getLetterboxBackgroundWallpaperBlurRadius();
    }

    public static int getLetterboxBackgroundWallpaperBlurRadius() {
        return getInstance().mLetterboxBackgroundWallpaperBlurRadius;
    }

    public static void adjustLetterboxes(DisplayContent displayContent) {
        if (displayContent.isDefaultDisplay && CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED) {
            getInstance().lambda$updateWallpaperType$2(displayContent);
        }
    }

    public static boolean hasLetterboxSurface(DisplayContent displayContent) {
        CustomLetterboxConfiguration customLetterboxConfiguration = getInstance();
        int i = customLetterboxConfiguration.mMode;
        if (i == 3 || i == 5) {
            return false;
        }
        if (!CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED || !isEnabledEnhanced(i) || displayContent == null) {
            return true;
        }
        CustomLetterboxEnhancedController customLetterboxEnhancedController = (CustomLetterboxEnhancedController) customLetterboxConfiguration.mEnhancedControllers.get(displayContent.getDisplayId());
        return customLetterboxEnhancedController == null || customLetterboxEnhancedController.shouldShowLetterboxLocked();
    }

    /* renamed from: com.android.server.wm.CustomLetterboxConfiguration$1 */
    public class C28881 extends BroadcastReceiver {
        public final /* synthetic */ CustomLetterboxConfiguration val$instance;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (CoreRune.SAFE_DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("onReceive: action=");
                sb.append(intent != null ? intent.getAction() : "null");
                Slog.d("CustomLetterbox", sb.toString());
            }
            WindowManagerService.HandlerC2998H handlerC2998H = this.val$instance.mWmService.f1749mH;
            final CustomLetterboxConfiguration customLetterboxConfiguration = this.val$instance;
            handlerC2998H.post(new Runnable() { // from class: com.android.server.wm.CustomLetterboxConfiguration$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CustomLetterboxConfiguration.m13131$$Nest$mupdateWallpaperType(CustomLetterboxConfiguration.this);
                }
            });
        }
    }

    public final void updateWallpaperType() {
        try {
            WallpaperManager wallpaperManager = (WallpaperManager) SafetySystemService.getSystemService(WallpaperManager.class);
            if (wallpaperManager == null) {
                return;
            }
            boolean z = wallpaperManager.getWallpaperInfo() != null;
            WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (z == this.mHasLiveWallpaper) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    this.mHasLiveWallpaper = z;
                    Slog.d("CustomLetterbox", "updateWallpaperType: hasLiveWallpaper=" + z);
                    this.mWmService.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.CustomLetterboxConfiguration$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            CustomLetterboxConfiguration.this.lambda$updateWallpaperType$2((DisplayContent) obj);
                        }
                    });
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: applyLetterboxEnhancedIfNeeded, reason: merged with bridge method [inline-methods] */
    public final void lambda$updateWallpaperType$2(DisplayContent displayContent) {
        applyLetterboxEnhancedIfNeeded(displayContent, false, false);
    }

    public final void applyLetterboxEnhancedIfNeeded(DisplayContent displayContent, boolean z, boolean z2) {
        boolean isEnabledEnhanced = isEnabledEnhanced(this.mMode);
        int displayId = displayContent.getDisplayId();
        CustomLetterboxEnhancedController customLetterboxEnhancedController = (CustomLetterboxEnhancedController) this.mEnhancedControllers.get(displayId);
        if (isEnabledEnhanced) {
            if (customLetterboxEnhancedController == null) {
                if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR) {
                    customLetterboxEnhancedController = new CustomLetterboxEnhancedController.CapturedBlurWallpaper(displayContent);
                } else {
                    customLetterboxEnhancedController = new CustomLetterboxEnhancedController(displayContent);
                }
                this.mEnhancedControllers.put(displayId, customLetterboxEnhancedController);
            }
        } else if (customLetterboxEnhancedController == null) {
            return;
        } else {
            this.mEnhancedControllers.remove(displayId);
        }
        customLetterboxEnhancedController.applyLetterboxEnhancedIfNeededLocked(isEnabledEnhanced, this.mHasLiveWallpaper, z, z2);
    }

    public static void performEnhancedControllerIfNonNull(DisplayContent displayContent, Consumer consumer) {
        CustomLetterboxEnhancedController customLetterboxEnhancedController;
        if (displayContent == null || (customLetterboxEnhancedController = (CustomLetterboxEnhancedController) getInstance().mEnhancedControllers.get(displayContent.mDisplayId)) == null || !customLetterboxEnhancedController.isAvailable()) {
            return;
        }
        consumer.accept(customLetterboxEnhancedController);
    }

    public static boolean isWaitingForEnhancedEnabled(DisplayContent displayContent) {
        CustomLetterboxEnhancedController customLetterboxEnhancedController = (CustomLetterboxEnhancedController) getInstance().mEnhancedControllers.get(displayContent.mDisplayId);
        return customLetterboxEnhancedController != null && customLetterboxEnhancedController.isAvailable() && customLetterboxEnhancedController.isWaitingForEnhancedEnabledLocked();
    }

    public static boolean isEnhancedControllerToken(IBinder iBinder) {
        return iBinder instanceof CustomLetterboxEnhancedController.EnhancedControllerToken;
    }

    public static boolean shouldHideWallpaper(DisplayContent displayContent) {
        CustomLetterboxEnhancedController customLetterboxEnhancedController = (CustomLetterboxEnhancedController) getInstance().mEnhancedControllers.get(displayContent.mDisplayId);
        return customLetterboxEnhancedController != null && customLetterboxEnhancedController.isAvailable() && customLetterboxEnhancedController.shouldHideWallpaper();
    }

    public static boolean isAllowWallpaperBelowLetterbox() {
        return getInstance().mMode != 7;
    }

    public static void dump(PrintWriter printWriter, String str) {
        getInstance().dump(printWriter, str, str + "  ");
    }

    public final void dump(PrintWriter printWriter, String str, String str2) {
        printWriter.print(str);
        printWriter.println("CustomLetterboxConfiguration");
        printWriter.print(str2);
        printWriter.print("Mode=");
        printWriter.print(modeToString(this.mMode));
        printWriter.print(", CornersRadius=");
        printWriter.print(this.mLetterboxActivityCornersRadius);
        printWriter.print(", BlurRadius=");
        printWriter.print(this.mLetterboxBackgroundWallpaperBlurRadius);
        printWriter.println();
        if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED) {
            printWriter.print(str2);
            printWriter.print("HasLiveWallpaper=");
            printWriter.println(this.mHasLiveWallpaper);
            for (int i = 0; i < this.mEnhancedControllers.size(); i++) {
                ((CustomLetterboxEnhancedController) this.mEnhancedControllers.valueAt(i)).dumpLocked(printWriter, str, str2);
            }
        }
    }

    public static boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
        boolean z = false;
        if ("-setAllowFreezeWallpaper".equals(str)) {
            CustomLetterboxConfiguration customLetterboxConfiguration = getInstance();
            try {
                z = Boolean.parseBoolean(strArr[0]);
            } catch (Exception unused) {
            }
            WindowManagerGlobalLock windowManagerGlobalLock = customLetterboxConfiguration.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    customLetterboxConfiguration.mAllowFreezeWallpaper = z;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            printWriter.println(str + ", AllowFreezeWallpaper=" + z);
            return true;
        }
        if (!"-setCustomLetterboxMode".equals(str)) {
            return false;
        }
        if (strArr.length > 1) {
            printWriter.println("Too many arguments.");
            return true;
        }
        int i = DEFAULT_MODE;
        try {
            i = Integer.parseInt(strArr[0]);
        } catch (Exception unused2) {
        }
        printWriter.println(str + ", Mode=" + modeToString(i));
        setMode(i);
        return true;
    }

    public static String modeToString(int i) {
        switch (i) {
            case 0:
                return "DISABLED";
            case 1:
                return "ENABLED";
            case 2:
                return "ENABLED_ENHANCED";
            case 3:
                return "ENABLED_WITHOUT_BLUR";
            case 4:
                return "ENABLED_WITHOUT_ROUNDED_CORNER";
            case 5:
                return "ENABLED_WITHOUT_BLUR_AND_ROUNDED_CORNER";
            case 6:
                return "ENABLED_ENHANCED_WITHOUT_ROUNDED_CORNER";
            case 7:
                return "ENABLED_ENHANCED_HIDING_WALLPAPER";
            case 8:
                return "ENABLED_BLACK_LETTERBOX";
            case 9:
                return "ENABLED_CAPTURED_BLUR_WALLPAPER";
            default:
                return "Unknown(" + i + ")";
        }
    }
}
