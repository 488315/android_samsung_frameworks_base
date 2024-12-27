package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

public class GlobalActionsColumnLayout extends GlobalActionsLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mLastSnap;

    public GlobalActionsColumnLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void centerAlongEdge() {
        int currentRotation = getCurrentRotation();
        if (currentRotation == 1) {
            setPadding(0, 0, 0, 0);
            setGravity(49);
        } else if (currentRotation != 3) {
            setPadding(0, 0, 0, 0);
            setGravity(21);
        } else {
            setPadding(0, 0, 0, 0);
            setGravity(81);
        }
    }

    public float getAnimationDistance() {
        return getGridItemSize() / 2.0f;
    }

    public float getGridItemSize() {
        return getContext().getResources().getDimension(R.dimen.global_actions_grid_item_height);
    }

    public int getPowerButtonOffsetDistance() {
        return Math.round(getContext().getResources().getDimension(R.dimen.global_actions_top_padding));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        post(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsColumnLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GlobalActionsColumnLayout globalActionsColumnLayout = GlobalActionsColumnLayout.this;
                int i5 = GlobalActionsColumnLayout.$r8$clinit;
                globalActionsColumnLayout.updateSnap();
            }
        });
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public final void onUpdateList() {
        super.onUpdateList();
        if (shouldReverseListItems()) {
            getListView().bringToFront();
        } else {
            getSeparatedView().bringToFront();
        }
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        if (currentRotation == 0) {
            return false;
        }
        return getCurrentLayoutDirection() == 1 ? currentRotation == 1 : currentRotation == 3;
    }

    public boolean shouldSnapToPowerButton() {
        int measuredWidth;
        int measuredWidth2;
        int powerButtonOffsetDistance = getPowerButtonOffsetDistance();
        View childAt = getChildAt(0);
        if (getCurrentRotation() == 0) {
            measuredWidth = childAt.getMeasuredHeight();
            measuredWidth2 = getMeasuredHeight();
        } else {
            measuredWidth = childAt.getMeasuredWidth();
            measuredWidth2 = getMeasuredWidth();
        }
        return measuredWidth + powerButtonOffsetDistance < measuredWidth2;
    }

    public void snapToPowerButton() {
        int powerButtonOffsetDistance = getPowerButtonOffsetDistance();
        int currentRotation = getCurrentRotation();
        if (currentRotation == 1) {
            setPadding(powerButtonOffsetDistance, 0, 0, 0);
            setGravity(51);
        } else if (currentRotation != 3) {
            setPadding(0, powerButtonOffsetDistance, 0, 0);
            setGravity(53);
        } else {
            setPadding(0, 0, powerButtonOffsetDistance, 0);
            setGravity(85);
        }
    }

    public void updateSnap() {
        boolean shouldSnapToPowerButton = shouldSnapToPowerButton();
        if (shouldSnapToPowerButton != this.mLastSnap) {
            if (shouldSnapToPowerButton) {
                snapToPowerButton();
            } else {
                centerAlongEdge();
            }
        }
        this.mLastSnap = shouldSnapToPowerButton;
    }
}
