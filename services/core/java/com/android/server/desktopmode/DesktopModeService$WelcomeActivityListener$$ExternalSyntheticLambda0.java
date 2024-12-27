package com.android.server.desktopmode;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;

public final /* synthetic */
class DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeService.WelcomeActivityListener f$0;

    public /* synthetic */ DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda0(
            DesktopModeService.WelcomeActivityListener welcomeActivityListener, int i) {
        this.$r8$classId = i;
        this.f$0 = welcomeActivityListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DesktopModeService.WelcomeActivityListener welcomeActivityListener = this.f$0;
        switch (i) {
            case 0:
                welcomeActivityListener.mShown = true;
                DesktopModeService desktopModeService = welcomeActivityListener.this$0;
                StateManager.InternalState state =
                        ((StateManager) desktopModeService.mStateManager).getState();
                welcomeActivityListener.showOrDismissOverlay(
                        state.mIsExternalDisplayConnected, state.mCoverSupportState == 2);
                SemDesktopModeState semDesktopModeState = state.mDesktopModeState;
                if (welcomeActivityListener.mShown && semDesktopModeState.compareTo(2, 0)) {
                    welcomeActivityListener.setDesktopModeState(
                            10, state.mIsExternalDisplayConnected);
                }
                ((StateManager) desktopModeService.mStateManager)
                        .registerListener(welcomeActivityListener.mStateListener);
                break;
            default:
                welcomeActivityListener.mShown = false;
                DesktopModeService desktopModeService2 = welcomeActivityListener.this$0;
                ((StateManager) desktopModeService2.mStateManager)
                        .unregisterListener(welcomeActivityListener.mStateListener);
                welcomeActivityListener.showOrDismissOverlay(false, false);
                StateManager.InternalState state2 =
                        ((StateManager) desktopModeService2.mStateManager).getState();
                if (!welcomeActivityListener.mShown && state2.mDesktopModeState.compareTo(2, 10)) {
                    welcomeActivityListener.setDesktopModeState(
                            0, state2.mIsExternalDisplayConnected);
                }
                if (DesktopModeFeature.SUPPORT_WIRELESS_DEX
                        && state2.isWirelessDexConnected()
                        && !welcomeActivityListener.mStartPressed) {
                    desktopModeService2.mWirelessDexManager.disconnect();
                }
                welcomeActivityListener.mStartPressed = false;
                break;
        }
    }
}
