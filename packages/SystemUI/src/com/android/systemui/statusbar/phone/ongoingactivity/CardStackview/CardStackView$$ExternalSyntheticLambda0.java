package com.android.systemui.statusbar.phone.ongoingactivity.CardStackview;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController$$ExternalSyntheticLambda0;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final /* synthetic */ class CardStackView$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CardStackView f$0;

    public /* synthetic */ CardStackView$$ExternalSyntheticLambda0(CardStackView cardStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = cardStackView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() throws Exception {
        final CardStackView cardStackView = this.f$0;
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
                View childAt2 = cardStackView.getChildAt(cardStackView.getTopViewIndex());
                childAt2.setX(0.0f);
                childAt2.setY(0.0f);
                OngoingActivityDataHelper.INSTANCE.getClass();
                if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
                    Log.i("{OngoingActivityCardStackView}", "OngoingActivityDataHelper.getDataSize() == 0");
                } else {
                    final String str = OngoingActivityDataHelper.getDataByIndex(0).mNotiID;
                    cardStackView.removingSbnId = str;
                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Card swipe dismiss. removingSbnId:", str, "{OngoingActivityCardStackView}");
                    OngoingCardController$$ExternalSyntheticLambda0 ongoingCardController$$ExternalSyntheticLambda0 = cardStackView.dismiss;
                    if (ongoingCardController$$ExternalSyntheticLambda0 != null) {
                        ongoingCardController$$ExternalSyntheticLambda0.mo781invoke(str);
                    }
                    Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView$sendDismiss$runnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (Intrinsics.areEqual(cardStackView.removingSbnId, "")) {
                                return;
                            }
                            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Can't receive entryRemoved yet. force clear for sbnId:", str, "{OngoingActivityCardStackView}");
                            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                            String str2 = cardStackView.removingSbnId;
                            ongoingActivityDataHelper.getClass();
                            OngoingActivityDataHelper.removeOngoingActivityByKey(str2);
                            CardStackView cardStackView2 = cardStackView;
                            cardStackView2.removingSbnId = "";
                            cardStackView2.isRunningSwipeDismissTopCardMove = false;
                            cardStackView2.isAnimating = false;
                        }
                    };
                    Handler handler = cardStackView.getHandler();
                    if (handler != null) {
                        handler.postDelayed(runnable, 500L);
                    }
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
                cardStackView.isRunningRemoveTopCardAnimation = false;
                cardStackView.isAnimating = false;
                break;
        }
        return Unit.INSTANCE;
    }
}
