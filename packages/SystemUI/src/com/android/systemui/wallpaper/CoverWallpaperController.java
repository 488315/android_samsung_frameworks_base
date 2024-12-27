package com.android.systemui.wallpaper;

import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.accessory.SmartCardController;
import com.android.systemui.wallpaper.accessory.SmartCardController$getMainHandler$1;
import com.android.systemui.wallpaper.accessory.SmartCardController$settingObserver$1;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.WhichChecker;
import java.io.File;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CoverWallpaperController extends IWallpaperManagerCallback.Stub implements CoverWallpaper, PluginWallpaperCallback {
    public static CoverWallpaperController sInstance;
    public final Context mContext;
    public boolean mIsFbeColorSet;
    public final KeyguardUpdateMonitorCallback mMonitorCallback;
    public MultiPackDispatcher mMultiPackDispatcher;
    public final PluginLockUtils mPluginLockUtils;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SmartCardController mSmartCardController;
    public final SubScreenManager mSubScreenManager;
    public Consumer mWallpaperConsumer;
    public int mWallpaperId;
    public final WallpaperLogger mWallpaperLogger;
    public final WallpaperManager mWallpaperManager;

    public CoverWallpaperController(Context context, WallpaperManager wallpaperManager, WallpaperLogger wallpaperLogger, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SubScreenManager subScreenManager, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wallpaper.CoverWallpaperController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitchComplete, userId: ", "CoverWallpaperController");
                try {
                    SmartCardController smartCardController = CoverWallpaperController.this.mSmartCardController;
                    smartCardController.getClass();
                    Log.d("SmartCardController", "onUserSwitchCompleted, " + i);
                    smartCardController.sendUpdateState(0L, false);
                } catch (Exception e) {
                    AbsAdapter$1$$ExternalSyntheticOutline0.m("onUserSwitchComplete, e: ", e, "CoverWallpaperController");
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserSwitching, userId: ", "CoverWallpaperController");
                try {
                    CoverWallpaperController.this.mSmartCardController.getClass();
                    Log.d("SmartCardController", "onUserSwitching, " + i);
                } catch (Exception e) {
                    AbsAdapter$1$$ExternalSyntheticOutline0.m("onUserSwitching, e: ", e, "CoverWallpaperController");
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserUnlocked() {
                Log.d("CoverWallpaperController", "onUserUnlocked");
                SmartCardController smartCardController = CoverWallpaperController.this.mSmartCardController;
                smartCardController.getClass();
                Log.d("SmartCardController", "onUserUnlocked");
                if (smartCardController.mainHandler == null) {
                    smartCardController.mainHandler = new SmartCardController$getMainHandler$1(smartCardController, Looper.getMainLooper());
                }
                SmartCardController$getMainHandler$1 smartCardController$getMainHandler$1 = smartCardController.mainHandler;
                smartCardController$getMainHandler$1.sendMessageDelayed(smartCardController$getMainHandler$1.obtainMessage(20230526, Boolean.FALSE), 3000L);
            }
        };
        this.mMonitorCallback = keyguardUpdateMonitorCallback;
        sInstance = this;
        this.mIsFbeColorSet = false;
        this.mContext = context;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        pluginWallpaperManager.setHomeWallpaperCallback(this);
        this.mPluginLockUtils = pluginLockUtils;
        this.mWallpaperManager = wallpaperManager;
        this.mWallpaperLogger = wallpaperLogger;
        this.mSubScreenManager = subScreenManager;
        this.mSelectedUserInteractor = selectedUserInteractor;
        SmartCardController smartCardController = new SmartCardController(context, settingsHelper, keyguardUpdateMonitor, pluginWallpaperManager);
        this.mSmartCardController = smartCardController;
        this.mWallpaperId = wallpaperManager.getWallpaperId(getCoverWhich());
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            Log.d("CoverWallpaperController", "setCoverWallpaperCallback()");
            ((WallpaperLoggerImpl) wallpaperLogger).log("CoverWallpaperController", "setCoverWallpaperCallback");
            wallpaperManager.setCoverWallpaperCallback(this);
        }
        if (LsRune.WALLPAPER_SUPPORT_SUIT_CASE) {
            ContentResolver contentResolver = smartCardController.context.getContentResolver();
            Uri uriFor = Settings.System.getUriFor(SettingsHelper.SMART_COVER_ACCESSORY_COVER_URI);
            SmartCardController$settingObserver$1 smartCardController$settingObserver$1 = smartCardController.settingObserver;
            contentResolver.registerContentObserver(uriFor, false, smartCardController$settingObserver$1);
            smartCardController.context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, smartCardController$settingObserver$1);
            smartCardController.context.getContentResolver().registerContentObserver(Settings.System.getUriFor("dls_state"), false, smartCardController$settingObserver$1);
            keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        }
        if (wallpaperManager.semGetWallpaperType(getCoverWhich()) != 3 || MultiPackDispatcher.enableDlsIfDisabled(context)) {
            return;
        }
        Log.e("CoverWallpaperController", "Failed to enable DLS.");
    }

    public static int getCoverMode() {
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            return 16;
        }
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            return 32;
        }
        Log.w("CoverWallpaperController", "getCoverMode, abnormal state ");
        return 16;
    }

    public static int getCoverWhich() {
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            return 17;
        }
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            return 33;
        }
        Log.w("CoverWallpaperController", "getCoverWhich, abnormal state ");
        return 1;
    }

    public final Bitmap getWallpaperBitmap() {
        if (!isFbeAvailable()) {
            return this.mPluginWallpaperManager.getHomeWallpaperBitmap(getCoverMode());
        }
        if (this.mIsFbeColorSet) {
            Log.d("CoverWallpaperController", "getWallpaperBitmap: SemWallpaperColors for FBE was already set.");
        } else {
            try {
                this.mIsFbeColorSet = true;
                this.mWallpaperManager.semSetDLSWallpaperColors(this.mPluginWallpaperManager.getFbeSemWallpaperColors(1), getCoverWhich());
            } catch (IllegalArgumentException e) {
                Log.e("CoverWallpaperController", "getWallpaperBitmap: " + e.getMessage());
            }
        }
        boolean z = this.mPluginWallpaperManager.getFbeWallpaperIntelligentCrop(1) != null;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("getWallpaperBitmap: hasICrops = ", "CoverWallpaperController", z);
        return this.mPluginWallpaperManager.getFbeWallpaper(1, z);
    }

    public final String getWallpaperIntelligentCrop() {
        return isFbeAvailable() ? this.mPluginWallpaperManager.getFbeWallpaperIntelligentCrop(1) : this.mPluginWallpaperManager.getHomeWallpaperIntelligentCrop(getCoverMode());
    }

    public final String getWallpaperPath() {
        return isFbeAvailable() ? this.mPluginWallpaperManager.getFbeWallpaperPath(1) : this.mPluginWallpaperManager.getHomeWallpaperPath(getCoverMode());
    }

    public final int getWallpaperType() {
        return isFbeAvailable() ? this.mPluginWallpaperManager.getSubFbeWallpaperType() : this.mPluginWallpaperManager.getHomeWallpaperType(getCoverMode());
    }

    public final boolean isCoverWallpaperRequired() {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        if (selectedUserId > 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "isCoverWallpaperRequired: currentUser = ", "CoverWallpaperController");
            return false;
        }
        boolean z = this.mPluginWallpaperManager.getHomeCurrentScreen() == 1;
        boolean isHomeWallpaperRequired = this.mPluginWallpaperManager.isHomeWallpaperRequired(1);
        boolean isFbeAvailable = isFbeAvailable();
        boolean z2 = isHomeWallpaperRequired || isFbeAvailable;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isCoverWallpaperRequired: ", ", [homeWallpaperReq:", ", isFbeAvailable:", z2, isHomeWallpaperRequired);
        m.append(isFbeAvailable);
        m.append(", isSubScreen:");
        m.append(z);
        m.append("]");
        Log.d("CoverWallpaperController", m.toString());
        return z2;
    }

    public final boolean isFbeAvailable() {
        return this.mPluginWallpaperManager.isFbeAvailable() && this.mPluginWallpaperManager.isFbeRequired(1) && this.mPluginWallpaperManager.isFbeWallpaperAvailable(1);
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onDataCleared() {
        String group;
        int coverWhich = getCoverWhich();
        if ((coverWhich & 60) != 0 && this.mWallpaperManager.semGetWallpaperType(coverWhich) == 3) {
            if (this.mMultiPackDispatcher == null) {
                this.mMultiPackDispatcher = new MultiPackDispatcher(this.mContext, this.mWallpaperLogger, this.mPluginLockUtils, this.mSelectedUserInteractor.getSelectedUserId(false));
            }
            if (this.mMultiPackDispatcher.startMultipack(coverWhich)) {
                MultiPackDispatcher multiPackDispatcher = this.mMultiPackDispatcher;
                multiPackDispatcher.getClass();
                SparseIntArray sparseIntArray = new SparseIntArray();
                try {
                    Uri semGetUri = WallpaperManager.getInstance(multiPackDispatcher.mContext).semGetUri(coverWhich);
                    File[] listFiles = new File("/data/overlays/homewallpaper/" + (semGetUri.getHost() + semGetUri.getPath())).listFiles();
                    if (listFiles != null && listFiles.length > 0) {
                        for (File file : listFiles) {
                            String name = file.getName();
                            if (Pattern.matches("wallpaper_[\\d][.][\\w]+", name)) {
                                Matcher matcher = Pattern.compile("wallpaper_[\\d][.]").matcher(name);
                                if (matcher.find() && (group = matcher.group(0)) != null) {
                                    int parseInt = Integer.parseInt(group.replaceAll("[^0-9]", ""));
                                    boolean z = (coverWhich & 48) != 0;
                                    sparseIntArray.append(parseInt, Pattern.matches("^\\S+.(?i)(gif)$", name) ? z ? 22 : 12 : Pattern.matches("^\\S+.(?i)(jpg|jpeg|png)$", name) ? z ? 21 : 11 : z ? 23 : 13);
                                }
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                    e.printStackTrace();
                }
                sparseIntArray.get(0);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onReady() {
        Log.d("CoverWallpaperController", "onReady");
        Consumer consumer = this.mWallpaperConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onSemWallpaperChanged: type = ", ", which = ", "CoverWallpaperController");
        if (WhichChecker.isWatchFace(i2)) {
            PluginSubScreen pluginSubScreen = this.mSubScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onSemWallpaperChanged() no plugin");
            } else {
                pluginSubScreen.onSemWallpaperChanged(null);
            }
        }
        if ((WhichChecker.isWatchFace(i2) || WhichChecker.isVirtualDisplay(i2)) && i != 3) {
            this.mWallpaperConsumer = null;
            this.mPluginWallpaperManager.onHomeWallpaperChanged(getCoverMode());
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors == null || (semWallpaperColors.getWhich() & 2) == 0) {
            this.mWallpaperManager.semSetDLSWallpaperColors(semWallpaperColors, getCoverWhich());
            return;
        }
        Log.d("CoverWallpaperController", "onWallpaperHintUpdate: invalid which. which = " + semWallpaperColors.getWhich());
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperUpdate(boolean z) {
        Log.d("CoverWallpaperController", "onWallpaperUpdate:" + this.mWallpaperConsumer + ", isFirst:" + z);
        if (z) {
            this.mWallpaperId = this.mWallpaperManager.getWallpaperId(getCoverWhich());
        }
        Consumer consumer = this.mWallpaperConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        if (z && WhichChecker.isWatchFace(getCoverWhich())) {
            PluginSubScreen pluginSubScreen = this.mSubScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onSemWallpaperChanged() no plugin");
            } else {
                pluginSubScreen.onSemWallpaperChanged(null);
            }
        }
    }

    public final void onWallpaperChanged() {
    }

    public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
    }

    public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }
}
