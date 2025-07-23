package com.google.android.material.chip;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SeslExpansionButton extends ImageView {
    public boolean mAutoDisappear;
    public boolean mExpanded;
    public boolean mFloated;
    public final AnonymousClass1 mTimer;

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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.chip.SeslExpansionButton$1] */
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
