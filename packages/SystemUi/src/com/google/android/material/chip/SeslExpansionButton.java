package com.google.android.material.chip;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SeslExpansionButton extends ImageView {
    public boolean mAutoDisappear;
    public boolean mExpanded;
    public boolean mFloated;
    public final CountDownTimerC42531 mTimer;

    public SeslExpansionButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (this.mExpanded) {
            ImageView.mergeDrawableStates(onCreateDrawableState, new int[]{R.attr.state_expansion_button_expanded});
        }
        if (this.mFloated) {
            ImageView.mergeDrawableStates(onCreateDrawableState, new int[]{R.attr.state_expansion_button_floated});
        }
        return onCreateDrawableState;
    }

    public final void setFloated(boolean z) {
        this.mFloated = z;
        refreshDrawableState();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            cancel();
            start();
        }
    }

    public SeslExpansionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    /* JADX WARN: Type inference failed for: r8v5, types: [com.google.android.material.chip.SeslExpansionButton$1] */
    public SeslExpansionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAutoDisappear = false;
        setElevation(getResources().getDimension(R.dimen.expansion_button_elevation));
        long integer = context.getResources().getInteger(R.integer.expansion_button_duration);
        this.mTimer = new CountDownTimer(integer, integer) { // from class: com.google.android.material.chip.SeslExpansionButton.1
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                SeslExpansionButton seslExpansionButton = SeslExpansionButton.this;
                if (seslExpansionButton.mAutoDisappear && seslExpansionButton.getVisibility() == 0) {
                    SeslExpansionButton.this.setVisibility(4);
                }
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j) {
            }
        };
    }
}
