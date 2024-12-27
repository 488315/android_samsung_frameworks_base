package com.android.keyguard;

import android.database.ContentObserver;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardClockSwitchController;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardClockSwitchController keyguardClockSwitchController = (KeyguardClockSwitchController) obj;
                KeyguardClockSwitchController.AnonymousClass1 anonymousClass1 = keyguardClockSwitchController.mDoubleLineClockObserver;
                SecureSettings secureSettings = keyguardClockSwitchController.mSecureSettings;
                secureSettings.registerContentObserverForUserSync("lockscreen_use_double_line_clock", false, (ContentObserver) anonymousClass1, -1);
                secureSettings.registerContentObserverForUserSync("lockscreen_weather_enabled", false, (ContentObserver) keyguardClockSwitchController.mShowWeatherObserver, -1);
                break;
            case 1:
                KeyguardClockSwitchController keyguardClockSwitchController2 = (KeyguardClockSwitchController) obj;
                ViewGroup viewGroup = keyguardClockSwitchController2.mDateWeatherView;
                ClockController clock = keyguardClockSwitchController2.getClock();
                if (clock == null) {
                    hasCustomWeatherDataDisplay = false;
                } else {
                    hasCustomWeatherDataDisplay = (keyguardClockSwitchController2.mCurrentClockSize == 0 ? clock.getLargeClock() : clock.getSmallClock()).getConfig().getHasCustomWeatherDataDisplay();
                }
                viewGroup.setVisibility(hasCustomWeatherDataDisplay ? keyguardClockSwitchController2.mKeyguardDateWeatherViewInvisibility : 0);
                break;
            case 2:
                KeyguardClockSwitchController keyguardClockSwitchController3 = (KeyguardClockSwitchController) obj;
                KeyguardClockSwitchController.AnonymousClass1 anonymousClass12 = keyguardClockSwitchController3.mDoubleLineClockObserver;
                SecureSettings secureSettings2 = keyguardClockSwitchController3.mSecureSettings;
                secureSettings2.unregisterContentObserverSync(anonymousClass12);
                secureSettings2.unregisterContentObserverSync(keyguardClockSwitchController3.mShowWeatherObserver);
                break;
            case 3:
                ((KeyguardClockSwitch) ((KeyguardClockSwitchController) obj).mView).updateClockTargetRegions();
                break;
            case 4:
                KeyguardClockSwitchController keyguardClockSwitchController4 = (KeyguardClockSwitchController) obj;
                View view = keyguardClockSwitchController4.mWeatherView;
                LockscreenSmartspaceController lockscreenSmartspaceController = keyguardClockSwitchController4.mSmartspaceController;
                lockscreenSmartspaceController.execution.assertIsMainThread();
                view.setVisibility(lockscreenSmartspaceController.secureSettings.getIntForUser("lockscreen_weather_enabled", 1, ((UserTrackerImpl) lockscreenSmartspaceController.userTracker).getUserId()) == 1 ? 0 : 8);
                break;
            case 5:
                ((KeyguardClockSwitchController) obj).displayClock(1, true);
                break;
            case 6:
                KeyguardClockSwitchController keyguardClockSwitchController5 = (KeyguardClockSwitchController) obj;
                keyguardClockSwitchController5.mStatusArea.setVisibility(keyguardClockSwitchController5.mIsActiveDreamLockscreenHosted ? 4 : 0);
                break;
            default:
                ((ClockController) obj).getLargeClock().getAnimations().enter();
                break;
        }
    }
}
