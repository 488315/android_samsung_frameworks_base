package com.android.keyguard;

import android.R;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.util.concurrency.ExecutionImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardClockSwitchController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardClockSwitchController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean hasCustomWeatherDataDisplay;
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardClockSwitchController) this.f$0).displayClock(1, true);
                break;
            case 1:
                KeyguardClockSwitchController keyguardClockSwitchController = (KeyguardClockSwitchController) this.f$0;
                View view = keyguardClockSwitchController.mWeatherView;
                LockscreenSmartspaceController lockscreenSmartspaceController = keyguardClockSwitchController.mSmartspaceController;
                ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
                view.setVisibility(lockscreenSmartspaceController.secureSettings.getIntForUser(lockscreenSmartspaceController.context.getResources().getBoolean(R.bool.config_isDesktopModeSupported) ? 1 : 0, ((UserTrackerImpl) lockscreenSmartspaceController.userTracker).getUserId(), "lockscreen_weather_enabled") == 1 ? 0 : 8);
                break;
            case 2:
                KeyguardClockSwitchController keyguardClockSwitchController2 = (KeyguardClockSwitchController) this.f$0;
                ViewGroup viewGroup = keyguardClockSwitchController2.mDateWeatherView;
                ClockController clockController = keyguardClockSwitchController2.mClockEventController.clock;
                if (clockController == null) {
                    hasCustomWeatherDataDisplay = false;
                } else {
                    hasCustomWeatherDataDisplay = (keyguardClockSwitchController2.mCurrentClockSize == 0 ? clockController.getLargeClock() : clockController.getSmallClock()).getConfig().getHasCustomWeatherDataDisplay();
                }
                viewGroup.setVisibility(hasCustomWeatherDataDisplay ? keyguardClockSwitchController2.mKeyguardDateWeatherViewInvisibility : 0);
                break;
            default:
                ((ClockController) this.f$0).getLargeClock().getAnimations().enter();
                break;
        }
    }
}
