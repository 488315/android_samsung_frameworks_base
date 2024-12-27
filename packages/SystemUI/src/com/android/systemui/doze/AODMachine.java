package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Bundle;
import com.android.systemui.LsRune;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.util.wakelock.WakeLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AODMachine extends DozeMachine implements PluginAOD.Callback {
    public AODScreenBrightness mAODScreenBrightness;
    public AODUi mAODUi;

    static {
        boolean z = DozeService.DEBUG;
    }

    public AODMachine(DozeMachine.Service service, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, WakefulnessLifecycle wakefulnessLifecycle, DozeLog dozeLog, DockManager dockManager, DozeHost dozeHost, DozeMachine.Part[] partArr, UserTracker userTracker) {
        super(service, ambientDisplayConfiguration, wakeLock, wakefulnessLifecycle, dozeLog, dockManager, dozeHost, partArr, userTracker);
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void dozeTimeTick() {
        AODUi aODUi = getAODUi();
        if (aODUi != null) {
            ((DozeServiceHost) aODUi.mHost).dozeTimeTick();
            aODUi.mHandler.post(aODUi.mWakeLock.wrap(new AODUi$$ExternalSyntheticLambda0()));
        }
    }

    public final AODScreenBrightness getAODDozeBrightness() {
        if (this.mAODScreenBrightness == null) {
            DozeMachine.Part[] partArr = this.mParts;
            int length = partArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                DozeMachine.Part part = partArr[i];
                if (part instanceof AODScreenBrightness) {
                    this.mAODScreenBrightness = (AODScreenBrightness) part;
                    break;
                }
                i++;
            }
        }
        return this.mAODScreenBrightness;
    }

    public final AODUi getAODUi() {
        if (this.mAODUi == null) {
            DozeMachine.Part[] partArr = this.mParts;
            int length = partArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                DozeMachine.Part part = partArr[i];
                if (part instanceof AODUi) {
                    this.mAODUi = (AODUi) part;
                    break;
                }
                i++;
            }
        }
        return this.mAODUi;
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onFinishMOD(int i) {
        DozeMachine.State state;
        int i2 = (~i) & this.mMODReason;
        this.mMODReason = i2;
        if (i2 == 0 && this.mState == DozeMachine.State.DOZE_MOD && (state = this.mStateBeforeMOD) != DozeMachine.State.UNINITIALIZED) {
            requestState(state);
        }
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onRequestMOD(int i) {
        requestState(DozeMachine.State.DOZE_MOD);
        this.mMODReason = i | this.mMODReason;
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onRequestState(int i) {
        DozeMachine.State state;
        switch (i) {
            case 2:
                state = DozeMachine.State.DOZE;
                break;
            case 3:
            default:
                state = DozeMachine.State.UNINITIALIZED;
                break;
            case 4:
                state = DozeMachine.State.DOZE_AOD;
                break;
            case 5:
                state = DozeMachine.State.DOZE_AOD_PAUSED;
                break;
            case 6:
                state = DozeMachine.State.DOZE_MOD;
                break;
            case 7:
                state = DozeMachine.State.FINISH;
                break;
            case 8:
                state = DozeMachine.State.DOZE_DISPLAY_STATE_ON;
                break;
        }
        requestState(state);
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final Bundle onSendExtraData(Bundle bundle) {
        return null;
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onUpdateDozeBrightness(int i, int i2) {
        AODScreenBrightness aODDozeBrightness = getAODDozeBrightness();
        if (aODDozeBrightness != null) {
            aODDozeBrightness.updateDozeBrightness(i, i2, -1);
        }
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onUpdateWindowLayoutParams() {
        AODUi aODUi = getAODUi();
        if (aODUi != null) {
            DozeServiceHost dozeServiceHost = (DozeServiceHost) aODUi.mHost;
            boolean z = ((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) dozeServiceHost.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.dozing = z;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
    }

    @Override // com.android.systemui.plugins.aod.PluginAOD.Callback
    public final void onUpdateDozeBrightness(int i, int i2, int i3) {
        AODScreenBrightness aODDozeBrightness;
        if (!LsRune.AOD_HYSTERESIS_BRIGHTNESS || (aODDozeBrightness = getAODDozeBrightness()) == null) {
            return;
        }
        aODDozeBrightness.updateDozeBrightness(i, i2, i3);
    }
}
