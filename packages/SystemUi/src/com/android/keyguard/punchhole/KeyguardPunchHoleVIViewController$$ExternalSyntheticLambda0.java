package com.android.keyguard.punchhole;

import android.provider.Settings;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetUtil;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPunchHoleVIViewController f$0;

    public /* synthetic */ KeyguardPunchHoleVIViewController$$ExternalSyntheticLambda0(KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPunchHoleVIViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController = this.f$0;
                ((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).mPunchHoleCallback = keyguardPunchHoleVIViewController.mPunchHoleCallback;
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = keyguardPunchHoleVIViewController.mKeyguardUpdateMonitorCallback;
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardPunchHoleVIViewController.mKeyguardUpdateMonitor;
                keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
                keyguardPunchHoleVIViewController.mWakefulnessLifecycle.addObserver(keyguardPunchHoleVIViewController.mWakefulnessObserver);
                keyguardPunchHoleVIViewController.mSettingsHelper.registerCallback(keyguardPunchHoleVIViewController.mSettingsListener, Settings.System.getUriFor("any_screen_running"));
                if (DeviceState.shouldEnableKeyguardScreenRotation(keyguardPunchHoleVIViewController.getContext())) {
                    if (keyguardUpdateMonitor.isFaceOptionEnabled()) {
                        keyguardPunchHoleVIViewController.mRotationWatcher.addCallback(keyguardPunchHoleVIViewController.mRotationConsumer);
                    }
                    keyguardPunchHoleVIViewController.mDisplayLifecycle.addObserver(keyguardPunchHoleVIViewController.mDisplayLifeCycleObserver);
                }
                ((ConfigurationControllerImpl) keyguardPunchHoleVIViewController.mConfigurationController).addCallback(keyguardPunchHoleVIViewController.mConfigurationListener);
                WallpaperUtils.registerSystemUIWidgetCallback(keyguardPunchHoleVIViewController, SystemUIWidgetUtil.convertFlag("background") | SystemUIWidgetUtil.convertFlag("statusbar"));
                ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardPunchHoleVIViewController.mKeyguardEditModeController).listeners).add(keyguardPunchHoleVIViewController.mEditModeListener);
                keyguardPunchHoleVIViewController.mPluginLockStarManager.registerCallback(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController.mView).TAG, keyguardPunchHoleVIViewController.mLockStarCallback);
                break;
            default:
                KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController2 = this.f$0;
                keyguardPunchHoleVIViewController2.mKeyguardUpdateMonitor.removeCallback(keyguardPunchHoleVIViewController2.mKeyguardUpdateMonitorCallback);
                keyguardPunchHoleVIViewController2.mWakefulnessLifecycle.removeObserver(keyguardPunchHoleVIViewController2.mWakefulnessObserver);
                keyguardPunchHoleVIViewController2.mSettingsHelper.unregisterCallback(keyguardPunchHoleVIViewController2.mSettingsListener);
                if (DeviceState.shouldEnableKeyguardScreenRotation(keyguardPunchHoleVIViewController2.getContext())) {
                    keyguardPunchHoleVIViewController2.mRotationWatcher.removeCallback(keyguardPunchHoleVIViewController2.mRotationConsumer);
                    keyguardPunchHoleVIViewController2.mDisplayLifecycle.removeObserver(keyguardPunchHoleVIViewController2.mDisplayLifeCycleObserver);
                }
                ((ConfigurationControllerImpl) keyguardPunchHoleVIViewController2.mConfigurationController).removeCallback(keyguardPunchHoleVIViewController2.mConfigurationListener);
                WallpaperUtils.removeSystemUIWidgetCallback(keyguardPunchHoleVIViewController2);
                ((ArrayList) ((KeyguardEditModeControllerImpl) keyguardPunchHoleVIViewController2.mKeyguardEditModeController).listeners).remove(keyguardPunchHoleVIViewController2.mEditModeListener);
                keyguardPunchHoleVIViewController2.mPluginLockStarManager.unregisterCallback(((KeyguardPunchHoleVIView) keyguardPunchHoleVIViewController2.mView).TAG);
                break;
        }
    }
}
