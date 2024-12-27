package com.android.server.policy;

import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.wm.WmCoverState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface WindowManagerPolicy extends WindowManagerPolicyConstants {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnKeyguardExitResult {
        void onKeyguardExitResult(boolean z);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ScreenOffListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface WindowManagerFuncs {
        void enableScreenIfNeeded();

        int getCameraLensCoverState();

        int getLidState();

        boolean isAppTransitionStateIdle();

        void lockDeviceNow();

        void moveDisplayToTopIfAllowed(int i);

        void notifyKeyguardTrustedChanged();

        void onKeyguardShowingAndNotOccludedChanged();

        void onPowerKeyDown(boolean z);

        void onUserSwitched();

        void reboot(boolean z);

        void rebootSafeMode(boolean z);

        void screenTurningOff(int i, ScreenOffListener screenOffListener);

        void shutdown(boolean z);

        void triggerAnimationFailsafe();

        void updateRotation(boolean z, boolean z2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface WindowState {
    }

    static int getWindowLayerFromTypeLw(int i) {
        if (WindowManager.LayoutParams.isSystemAlertWindowType(i)) {
            throw new IllegalArgumentException("Use getWindowLayerFromTypeLw() or getWindowLayerLw() for alert window types");
        }
        return getWindowLayerFromTypeLw(i, false, false);
    }

    static int getWindowLayerFromTypeLw(int i, boolean z, boolean z2) {
        int windowLayerFromTypeLw;
        if (z2 && z) {
            return 36;
        }
        int i2 = 1;
        if (i >= 1 && i <= 99) {
            return 2;
        }
        switch (i) {
            case 2000:
                return 15;
            case 2001:
                return 4;
            case 2002:
            case 2030:
            case 2034:
            case 2035:
            case 2037:
                return 3;
            case 2003:
                return z ? 12 : 9;
            case 2004:
            case 2014:
            case 2023:
            case 2025:
            case 2028:
            case 2029:
            default:
                switch (i) {
                    case FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080P_FHD /* 2095 */:
                        i2 = 6;
                        break;
                    case 2099:
                    case 2411:
                        if (!WmCoverState.sIsEnabled || (i2 = WmCoverState.getInstance().getWindowLayerFromTypeLw(i)) == -1) {
                            i2 = 21;
                            break;
                        }
                        break;
                    case 2225:
                        i2 = 10;
                        break;
                    case 2226:
                    case 2415:
                        i2 = 18;
                        break;
                    case 2227:
                    case 2600:
                        i2 = 31;
                        break;
                    case 2228:
                    case 2601:
                    case 2605:
                        i2 = 26;
                        break;
                    case 2270:
                    case 2271:
                    case 2621:
                        i2 = 15;
                        break;
                    case 2274:
                    case 2281:
                    case 2440:
                    case 2441:
                        i2 = 23;
                        break;
                    case 2280:
                    case 2401:
                    case 2405:
                        i2 = 25;
                        break;
                    case 2402:
                    case 2412:
                        i2 = 7;
                        break;
                    case 2403:
                        i2 = 5;
                        break;
                    case 2406:
                    case 2430:
                    case 2632:
                        i2 = 3;
                        break;
                    case 2407:
                    case 2619:
                        i2 = 34;
                        break;
                    case 2408:
                        i2 = 30;
                        break;
                    case 2414:
                        i2 = 22;
                        break;
                    case 2431:
                    case FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080X2400 /* 2618 */:
                    case 2624:
                        i2 = 12;
                        break;
                    case 2442:
                    case 2606:
                    case 2623:
                        i2 = 24;
                        break;
                    case 2620:
                        break;
                    case 2622:
                        i2 = 17;
                        break;
                    case 2630:
                        i2 = 28;
                        break;
                    case 2631:
                        i2 = 27;
                        break;
                    default:
                        i2 = (i == 2603 || i == 2604) ? 3 : i != 2606 ? -1 : 4;
                        if (i2 <= 0) {
                            i2 = -1;
                            break;
                        }
                        break;
                }
                if (i2 > 0) {
                    return i2;
                }
                NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown window type: ", "WindowManager");
                return 3;
            case 2005:
                if (!WmCoverState.sIsEnabled || (windowLayerFromTypeLw = WmCoverState.getInstance().getWindowLayerFromTypeLw(i)) == -1) {
                    return 21;
                }
                return windowLayerFromTypeLw;
            case 2006:
                return z ? 23 : 10;
            case 2007:
                return 8;
            case 2008:
                return 6;
            case 2009:
                return 19;
            case 2010:
                return z ? 27 : 9;
            case 2011:
                return 13;
            case 2012:
                return 14;
            case 2013:
                return 1;
            case 2015:
                return 33;
            case 2016:
                return 30;
            case 2017:
                return 18;
            case 2018:
                return 35;
            case 2019:
                return 24;
            case 2020:
                return 22;
            case 2021:
                return 34;
            case 2022:
                return 5;
            case 2024:
                return 25;
            case 2026:
                return 29;
            case 2027:
                return 28;
            case 2031:
                return 21;
            case 2032:
                return 31;
            case 2033:
                return 20;
            case 2036:
                return 26;
            case 2038:
                return 11;
            case 2039:
                return 32;
            case 2040:
                return 17;
            case 2041:
                return 16;
        }
    }

    static int getWindowLayerLw(WindowState windowState) {
        com.android.server.wm.WindowState windowState2;
        com.android.server.wm.WindowState windowState3 = (com.android.server.wm.WindowState) windowState;
        windowState3.getClass();
        com.android.server.wm.WindowState windowState4 = windowState3;
        loop0: while (true) {
            windowState2 = windowState4;
            while (windowState4 != null && windowState4.mIsChildWindow) {
                windowState4 = windowState4.getParentWindow();
                if (windowState4 != null) {
                    break;
                }
            }
        }
        return getWindowLayerFromTypeLw(windowState2.mAttrs.type, windowState3.mOwnerCanAddInternalSystemWindow, false);
    }
}
