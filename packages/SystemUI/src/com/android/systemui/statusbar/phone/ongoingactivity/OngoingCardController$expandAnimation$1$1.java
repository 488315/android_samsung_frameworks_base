package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.res.Resources;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackView;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class OngoingCardController$expandAnimation$1$1 implements Runnable {
    public final /* synthetic */ OngoingCardController this$0;

    public OngoingCardController$expandAnimation$1$1(OngoingCardController ongoingCardController) {
        this.this$0 = ongoingCardController;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        Boolean bool = Boolean.FALSE;
        ongoingActivityDataHelper.getClass();
        OngoingActivityDataHelper.updateMediaProgressAndMarqueeStateIfNeeded(null, bool);
        this.this$0.onChangeCapsuleVisibility(8, null);
        int i = 0;
        this.this$0.mCardStackView.setVisibility(0);
        OngoingCardController ongoingCardController = this.this$0;
        View viewFindViewById = ongoingCardController.mExpandedView.findViewById(R.id.ongoing_card_background);
        viewFindViewById.getClass();
        OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0 = new OngoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0(this.this$0, 0);
        Log.d("{OngoingExpandedPipController}", "startExpandAnimation");
        ongoingCardController.mCapsule.getLocationOnScreen(new int[2]);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ongoingCardController.mCardStackView.getLayoutParams();
        PointF pointF = new PointF(r12[0] - marginLayoutParams.getMarginStart(), r12[1] - marginLayoutParams.topMargin);
        PointF cardStackLocationOnScreen = ongoingCardController.getCardStackLocationOnScreen();
        if (ongoingCardController.mCardStackView.getLayoutDirection() == 1) {
            pointF.x = (pointF.x - ongoingCardController.mCapsule.getResources().getDisplayMetrics().widthPixels) + ongoingCardController.mCapsule.getWidth();
            cardStackLocationOnScreen.x = (ongoingCardController.mCapsule.getResources().getDisplayMetrics().widthPixels - ongoingCardController.mCardStackView.getWidth()) - cardStackLocationOnScreen.x;
        }
        float f = pointF.x;
        float f2 = pointF.y;
        float f3 = cardStackLocationOnScreen.x;
        float f4 = cardStackLocationOnScreen.y + ongoingCardController.indicatorGardenPresenter.cachedGardenModel.totalHeight;
        CardStackView.Companion.getClass();
        ongoingCardController.startAnimation(viewFindViewById, f, f2, f3, f4, CardStackView.expandRootInterpolator, 500L, ongoingCardController$expandAnimation$1$1$$ExternalSyntheticLambda0);
        ArrayList arrayList = ongoingCardController.onStateEventListeners;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CollapsedStatusBarFragment.OngoingActivityListenerImpl ongoingActivityListenerImpl = ((OngoingActivityController$createCardController$2) obj).this$0.ongoingActivityListener;
            if (ongoingActivityListenerImpl != null) {
                ongoingActivityListenerImpl.onNudgeClockRequired();
            }
        }
    }
}
