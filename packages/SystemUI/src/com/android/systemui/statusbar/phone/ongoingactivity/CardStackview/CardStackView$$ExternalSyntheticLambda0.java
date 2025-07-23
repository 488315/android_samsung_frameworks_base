package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.util.Log;
import android.view.View;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CardStackView$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CardStackView f$0;

    public /* synthetic */ CardStackView$$ExternalSyntheticLambda0(CardStackView cardStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = cardStackView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CardStackView cardStackView = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                CardStackView.Companion companion = CardStackView.Companion;
                Log.i("{OngoingActivityCardStackView}", "swipeAnimationSet end()");
                cardStackView.isRunningCardFlipAnimation = false;
                cardStackView.isAnimating = false;
                cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                if (cardStackView.getChildCount() >= 2) {
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(cardStackView.getTopViewIndex(), "swapTopItemToBottom() topViewIndex = ", "{OngoingActivityCardStackView}");
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
                    if (copyOnWriteArrayList.size() > 1) {
                        Collections.rotate(copyOnWriteArrayList, -1);
                    }
                    View childAt = cardStackView.getChildAt(cardStackView.getTopViewIndex());
                    cardStackView.removeView(childAt);
                    cardStackView.addView(childAt, 0);
                    cardStackView.updateItem$1();
                }
                cardStackView.requestLayout();
                OngoingCardController.AnonymousClass6 anonymousClass6 = cardStackView.onChangeListener;
                if (anonymousClass6 != null) {
                    OngoingCardController.this.onAllowStateChanged(false);
                }
                break;
            case 1:
                CardStackView.Companion companion2 = CardStackView.Companion;
                Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet end()");
                if (cardStackView.getTopViewIndex() > 0) {
                    Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet ani done. send dismiss");
                    cardStackView.sendDismiss();
                } else {
                    Log.i("{OngoingActivityCardStackView}", "swipeHorizontalAnimationSet ani done");
                }
                cardStackView.isAnimating = false;
                cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                OngoingCardController.AnonymousClass6 anonymousClass62 = cardStackView.onChangeListener;
                if (anonymousClass62 != null) {
                    OngoingCardController.this.onAllowStateChanged(false);
                }
                break;
            case 2:
                CardStackView.Companion companion3 = CardStackView.Companion;
                Log.i("{OngoingActivityCardStackView}", "resetAnimationSet end()");
                cardStackView.isAnimating = false;
                cardStackView.resetAllParentsClipConfig$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                cardStackView.requestLayout();
                OngoingCardController.AnonymousClass6 anonymousClass63 = cardStackView.onChangeListener;
                if (anonymousClass63 != null) {
                    OngoingCardController.this.onAllowStateChanged(false);
                }
                break;
            default:
                cardStackView.isAnimating = false;
                break;
        }
        return Unit.INSTANCE;
    }
}
