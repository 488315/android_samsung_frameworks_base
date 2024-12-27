package com.android.systemui.statusbar.phone;

import android.text.TextPaint;
import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.policy.QSClockIndicatorView;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class PhoneStatusBarClockManager implements SlimIndicatorViewSubscriber {
    public static final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final QSClockIndicatorView mClockView;
    public final ViewGroup mGrandParentView;
    public IndicatorGarden mIndicatorGarden;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public ViewGroup mLeftContainer;
    public ViewGroup mMiddleContainer;
    public ViewGroup mRightContainer;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
    public POSITION mClockPosition = POSITION.NONE;
    public boolean mClockBlocked = false;
    public boolean mIsChangedClockPosition = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum POSITION {
        NONE,
        LEFT,
        MIDDLE,
        RIGHT
    }

    public PhoneStatusBarClockManager(ViewGroup viewGroup, SlimIndicatorViewMediator slimIndicatorViewMediator, IndicatorGardenPresenter indicatorGardenPresenter, QSClockIndicatorView qSClockIndicatorView) {
        this.mGrandParentView = viewGroup;
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.mClockView = qSClockIndicatorView;
    }

    public final void addClockView(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        if (viewGroup.getVisibility() != 0) {
            viewGroup.setVisibility(0);
        }
        viewGroup.addView(this.mClockView);
    }

    public final int getClockWidth() {
        QSClockIndicatorView qSClockIndicatorView = this.mClockView;
        int i = 0;
        if (qSClockIndicatorView == null) {
            return 0;
        }
        TextPaint paint = qSClockIndicatorView.getPaint();
        if (paint != null && qSClockIndicatorView.getText() != null) {
            i = (int) paint.measureText(qSClockIndicatorView.getText().toString());
        }
        return Math.max(qSClockIndicatorView.getMeasuredWidth(), qSClockIndicatorView.getPaddingEnd() + qSClockIndicatorView.getPaddingStart() + i);
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
    public final void updateQuickStarStyle() {
        SlimIndicatorViewMediator slimIndicatorViewMediator = this.mSlimIndicatorViewMediator;
        boolean isLeftClockPosition = ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).isLeftClockPosition();
        boolean isMiddleClockPosition = ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).isMiddleClockPosition();
        boolean isRightClockPosition = ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).isRightClockPosition();
        this.mClockBlocked = ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).isBlocked(SubRoom.EXTRA_VALUE_CLOCK);
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateQuickStarStyle() left:", ", middle:", ", right:", isLeftClockPosition, isMiddleClockPosition);
        m.append(isRightClockPosition);
        m.append(", mClockBlocked:");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, this.mClockBlocked, "[QuickStar]PhoneStatusBarClockManager");
        POSITION position = this.mClockPosition;
        if (isLeftClockPosition) {
            this.mClockPosition = POSITION.LEFT;
        } else if (isRightClockPosition) {
            this.mClockPosition = POSITION.RIGHT;
        } else if (isMiddleClockPosition) {
            this.mClockPosition = POSITION.MIDDLE;
        } else {
            this.mClockPosition = POSITION.NONE;
        }
        if (position != this.mClockPosition) {
            this.mIsChangedClockPosition = true;
            updateResources();
        }
    }

    public final void updateResources() {
        QSClockIndicatorView qSClockIndicatorView;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("updateResources() mGrandParentView is null ? ");
            sb.append(this.mGrandParentView == null);
            sb.append(", mIsChangedClockPosition:");
            sb.append(this.mIsChangedClockPosition);
            sb.append(", mClockPosition:");
            sb.append(this.mClockPosition);
            Log.d("[QuickStar]PhoneStatusBarClockManager", sb.toString());
        }
        if (this.mGrandParentView == null || (qSClockIndicatorView = this.mClockView) == null || !this.mIsChangedClockPosition) {
            return;
        }
        ViewGroup viewGroup = this.mLeftContainer;
        if (viewGroup != null) {
            viewGroup.removeView(qSClockIndicatorView);
            viewGroup.setVisibility(8);
        }
        ViewGroup viewGroup2 = this.mMiddleContainer;
        if (viewGroup2 != null) {
            viewGroup2.removeView(this.mClockView);
            viewGroup2.setVisibility(8);
        }
        ViewGroup viewGroup3 = this.mRightContainer;
        if (viewGroup3 != null) {
            viewGroup3.removeView(this.mClockView);
            viewGroup3.setVisibility(8);
        }
        int ordinal = this.mClockPosition.ordinal();
        if (ordinal == 2) {
            addClockView(this.mMiddleContainer);
        } else if (ordinal != 3) {
            addClockView(this.mLeftContainer);
        } else {
            addClockView(this.mRightContainer);
        }
        IndicatorGardenPresenter indicatorGardenPresenter = this.mIndicatorGardenPresenter;
        IndicatorGarden indicatorGarden = this.mIndicatorGarden;
        indicatorGardenPresenter.getClass();
        indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, indicatorGarden));
        this.mIsChangedClockPosition = false;
    }
}
