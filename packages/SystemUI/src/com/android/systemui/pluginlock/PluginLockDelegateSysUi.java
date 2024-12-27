package com.android.systemui.pluginlock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.util.LogUtil;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import java.util.Scanner;

public class PluginLockDelegateSysUi implements PluginLockBasicManager.Callback {
    public static final String TAG = "PluginLockDelegateSysUi";
    private final PluginLockMediator mMediator;
    private PluginLockInstanceState mPluginLockInstanceState;

    public PluginLockDelegateSysUi(PluginLockMediator pluginLockMediator) {
        this.mMediator = pluginLockMediator;
    }

    private String getPrintableHints(String str) {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(str);
        while (scanner.hasNextLine()) {
            String trim = scanner.nextLine().trim();
            if (trim.contains("</Version>") || trim.contains("</Which>") || trim.contains("</FontColor>")) {
                sb.append(trim.replace("</Version>", "").replace("</Which>", "").replace("</FontColor>", ""));
                sb.append(",");
            }
        }
        scanner.close();
        return sb.toString();
    }

    public /* synthetic */ void lambda$updateDynamicLockData$1(String str) {
        this.mMediator.updateDynamicLockData(str);
    }

    /* renamed from: setDynamicLockDataInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$setDynamicLockData$0(String str) {
        this.mMediator.setDynamicLockData(str);
        this.mMediator.setPluginLockItem(this.mPluginLockInstanceState);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void dispatchEvent(Bundle bundle) {
        PluginLockMediator pluginLockMediator;
        String string = bundle.getString("action");
        Log.d(TAG, "dispatchEvent() event: " + string);
        string.getClass();
        if (string.equals(PluginLock.ACTION_DATA_CLEAR) && (pluginLockMediator = this.mMediator) != null) {
            pluginLockMediator.onDataCleared();
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public String getDynamicLockData() {
        return null;
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void goToLockedShade() {
        Log.d(TAG, "goToLockedShade");
        this.mMediator.goToLockedShade();
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public boolean isSecure() {
        return this.mMediator.isSecure();
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void makeExpandedInvisible() {
        this.mMediator.makeExpandedInvisible();
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void requestDismissKeyguard(Intent intent) {
        Log.d(TAG, "requestDismissKeyguard() " + intent);
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.requestDismissKeyguard(intent);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setBiometricRecognition(boolean z) {
        Log.d(TAG, "setBiometricRecognition");
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.updateBiometricRecognition(z);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setDynamicLockData(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setDynamicLockData : ", str, TAG);
        if (str == null) {
            Log.w(TAG, "wrong request");
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            lambda$setDynamicLockData$0(str);
        } else {
            new Handler(Looper.getMainLooper()).post(new PluginLockDelegateSysUi$$ExternalSyntheticLambda0(this, str, 0));
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setLockscreenTimer(long j) {
        Log.d(TAG, "setLockscreenTimer() sec: " + j);
        this.mMediator.setLockscreenTimer(j);
    }

    public void setPluginLockInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        this.mPluginLockInstanceState = pluginLockInstanceState;
        if (pluginLockInstanceState != null || this.mMediator == null) {
            return;
        }
        setViewMode(0);
        this.mMediator.setInstanceState(i, null);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setPluginLockWallpaper(int i, int i2, String str) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setPluginLockWallpaper() wallpaperType:", TAG);
        this.mMediator.setPluginWallpaper(i, i2, str);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setPluginWallpaper(int i, int i2, int i3, String str) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "setPluginWallpaper() screen: ", ", wallpaperType:", TAG);
        this.mMediator.setPluginWallpaper(i, i2, i3, str);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setPluginWallpaperHints(int i, String str) {
        ExifInterface$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setPluginWallpaperHints() screen: ", ", hints: "), getPrintableHints(str), TAG);
        this.mMediator.setPluginWallpaperHint(i, str);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setScreenOrientation(boolean z, boolean z2) {
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.setScreenOrientation(z, z2);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setTimeOut(boolean z) {
        this.mMediator.updateOverlayUserTimeout(z);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setViewMode(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setViewMode : ", TAG);
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.onViewModeChanged(i);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setWallpaperHints(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setWallpaperHints() : ", str, TAG);
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.setPluginWallpaperHint(str);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void updateDynamicLockData(String str) {
        int currentScreenType = this.mMediator.getCurrentScreenType();
        Log.d(TAG, "updateDynamicLockData() screenType: " + currentScreenType + ", dynamicLockData: " + str);
        if (str == null) {
            Log.w(TAG, "updateDynamicLockData() wrong request");
            return;
        }
        if ((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && currentScreenType == 1) {
            LogUtil.w(TAG, "updateDynamicLockData() skip", new Object[0]);
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            this.mMediator.updateDynamicLockData(str);
        } else {
            new Handler(Looper.getMainLooper()).post(new PluginLockDelegateSysUi$$ExternalSyntheticLambda0(this, str, 1));
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void updateWindowSecureState(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateWindowSecureState : ", TAG, z);
        PluginLockMediator pluginLockMediator = this.mMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.updateWindowSecureState(z);
        }
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void userActivity() {
        this.mMediator.onUserActivity();
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setPluginWallpaper(int i, int i2, int i3, String str, String str2) {
        ExifInterface$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "setPluginWallpaper() screen: ", ", wallpaperType:", ", iCrops = "), str2, TAG);
        this.mMediator.setPluginWallpaper(i, i2, i3, str, str2);
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void onLaunchTransitionFadingEnded() {
    }

    @Override // com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager.Callback
    public void setRotationAllowed(boolean z) {
    }
}
