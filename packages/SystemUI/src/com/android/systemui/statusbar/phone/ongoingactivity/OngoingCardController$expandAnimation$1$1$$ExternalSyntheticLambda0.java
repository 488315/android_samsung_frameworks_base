package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OngoingCardController f$0;

    public /* synthetic */ OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0(OngoingCardController ongoingCardController, int i) {
        this.$r8$classId = i;
        this.f$0 = ongoingCardController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                OngoingCardController ongoingCardController = this.f$0;
                OngoingCardController.OaCardState oaCardState = ongoingCardController.oaCardState;
                if (oaCardState == OngoingCardController.OaCardState.EXPAND) {
                    Log.d("{OngoingExpandedPipController}", "expandAnimation startExpandAnimation endListener run");
                    ongoingCardController.onAllowStateChanged(false);
                    Log.d("{OngoingExpandedPipController}", "watchSelfValidation stop");
                    ongoingCardController.isWatchSelfValidationProc = false;
                    ongoingCardController.selfValidationHandler.removeCallbacks(ongoingCardController.selfDestroyRunnable);
                    ongoingCardController.setCardState(OngoingCardController.OaCardState.DISPLAY);
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    OngoingActivityDataHelper.updateMediaProgressAndMarqueeStateIfNeeded(null, null);
                } else {
                    Log.e("{OngoingExpandedPipController}", "expandAnimation startExpandAnimation endListener run. But oaCardState(" + oaCardState + ") is changed");
                }
                break;
            default:
                Log.d("{OngoingExpandedPipController}", "startCollapseAnimation. endListener");
                final OngoingCardController ongoingCardController2 = this.f$0;
                ongoingCardController2.mainUIHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$postCollapseAnimation$1$1$2$1
                    @Override // java.lang.Runnable
                    public final void run() throws Exception {
                        ongoingCardController2.onDestroy(true);
                    }
                });
                break;
        }
        return Unit.INSTANCE;
    }
}
