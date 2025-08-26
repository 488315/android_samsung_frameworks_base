package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.back.domain.interactor.BackActionInteractor;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.media.controls.util.MediaSessionLegacyHelperWrapper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* loaded from: classes2.dex */
public final class KeyguardKeyEventInteractor {
    public final Integer[] IGNORED_EXT_KEYCODE = {26, 27, 126, 85, 85, 91, 79, 86, 87, 88, 89, 130, 90, 222, 24, 25, 164};
    public final BackActionInteractor backActionInteractor;
    public final Context context;
    public final DisplayLifecycle displayLifecycle;
    public Boolean mIsKeyDownInDozing;
    public int mKeyUpCountInDozing;
    public final MediaSessionLegacyHelperWrapper mediaSessionLegacyHelperWrapper;
    public final PowerInteractor powerInteractor;
    public final ShadeController shadeController;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final StatusBarStateController statusBarStateController;
    public final KeyguardSysDumpTrigger sysDumpTrigger;

    public KeyguardKeyEventInteractor(KeyguardSysDumpTrigger keyguardSysDumpTrigger, DisplayLifecycle displayLifecycle, Context context, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ShadeController shadeController, MediaSessionLegacyHelperWrapper mediaSessionLegacyHelperWrapper, BackActionInteractor backActionInteractor, PowerInteractor powerInteractor, KeyguardMediaKeyInteractor keyguardMediaKeyInteractor) {
        this.sysDumpTrigger = keyguardSysDumpTrigger;
        this.displayLifecycle = displayLifecycle;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.shadeController = shadeController;
        this.mediaSessionLegacyHelperWrapper = mediaSessionLegacyHelperWrapper;
        this.backActionInteractor = backActionInteractor;
        this.powerInteractor = powerInteractor;
    }

    public final boolean dispatchMenuKeyEvent() {
        if (!((WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).isAwake() || this.statusBarStateController.getState() == 0) {
            return false;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.statusBarKeyguardViewManager;
        if (!statusBarKeyguardViewManager.shouldDismissOnMenuPressed()) {
            return false;
        }
        statusBarKeyguardViewManager.dismissWithAction(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor.dispatchMenuKeyEvent.1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                return false;
            }
        }, null);
        return true;
    }
}
