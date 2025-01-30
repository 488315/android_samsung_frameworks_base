package com.google.android.material.chip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.text.TextUtilsCompat;
import com.android.systemui.R;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SeslPeoplePicker extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SeslChipGroup mChipGroup;
    public final SeslExpandableContainer mContainer;
    public boolean mInitialized;
    public final boolean mIsRtl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.chip.SeslPeoplePicker$4 */
    public final class C42574 extends AnimatorListenerAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;

        public C42574() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            ViewGroup.LayoutParams layoutParams = SeslPeoplePicker.this.mContainer.getLayoutParams();
            layoutParams.height = -2;
            SeslPeoplePicker.this.mContainer.setLayoutParams(layoutParams);
            SeslExpansionButton seslExpansionButton = SeslPeoplePicker.this.mContainer.mExpansionButton;
            seslExpansionButton.mTimer.cancel();
            seslExpansionButton.mTimer.start();
            SeslPeoplePicker seslPeoplePicker = SeslPeoplePicker.this;
            seslPeoplePicker.mContainer.post(new SeslPeoplePicker$$ExternalSyntheticLambda2(seslPeoplePicker, 3));
            SeslChipGroup seslChipGroup = SeslPeoplePicker.this.mChipGroup;
            seslChipGroup.setLayoutTransition(seslChipGroup.mLayoutTransition);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            SeslPeoplePicker.this.mChipGroup.setAlpha(0.0f);
            SeslChipGroup seslChipGroup = SeslPeoplePicker.this.mChipGroup;
            seslChipGroup.singleLine = !r1.mContainer.mExpanded;
            seslChipGroup.setLayoutTransition(null);
        }
    }

    public SeslPeoplePicker(Context context) {
        this(context, null);
    }

    public final void updateFloatWhenExpanded() {
        if (!this.mContainer.mExpanded || this.mChipGroup.getChildCount() <= 0) {
            return;
        }
        SeslExpansionButton seslExpansionButton = this.mContainer.mExpansionButton;
        if (seslExpansionButton.getVisibility() != 0) {
            seslExpansionButton.setVisibility(0);
        }
        int paddingStart = this.mChipGroup.getPaddingStart();
        int paddingEnd = this.mChipGroup.getPaddingEnd();
        SeslChipGroup seslChipGroup = this.mChipGroup;
        int i = seslChipGroup.chipSpacingHorizontal;
        int width = seslChipGroup.getChildAt(0).getWidth() + paddingStart + paddingEnd + i;
        int width2 = getWidth();
        for (int i2 = 1; i2 < this.mChipGroup.getChildCount(); i2++) {
            int width3 = ((Chip) this.mChipGroup.getChildAt(i2)).getWidth();
            if (width + width3 >= width2) {
                break;
            }
            width += width3 + i;
        }
        if ((width - i) - paddingEnd >= getWidth() - this.mContainer.mExpansionButton.getWidth()) {
            seslExpansionButton.setFloated(true);
        } else {
            seslExpansionButton.setFloated(false);
        }
    }

    public SeslPeoplePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public SeslPeoplePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public SeslPeoplePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Locale locale = Locale.getDefault();
        int i3 = TextUtilsCompat.$r8$clinit;
        this.mIsRtl = TextUtils.getLayoutDirectionFromLocale(locale) == 1;
        View inflate = LayoutInflater.from(context).inflate(R.layout.sesl_people_picker_layout, (ViewGroup) this, true);
        SeslChipGroup seslChipGroup = (SeslChipGroup) inflate.findViewById(R.id.chip);
        this.mChipGroup = seslChipGroup;
        seslChipGroup.singleLine = true;
        SeslExpandableContainer seslExpandableContainer = (SeslExpandableContainer) inflate.findViewById(R.id.container);
        this.mContainer = seslExpandableContainer;
        seslExpandableContainer.mFadeAnimation = true;
        seslExpandableContainer.mOnExpansionButtonClickedListener = new SeslPeoplePicker$$ExternalSyntheticLambda0(this);
        seslChipGroup.mChipAddListener = new SeslPeoplePicker$$ExternalSyntheticLambda1(this, context);
        seslChipGroup.mChipRemoveListener = new SeslPeoplePicker$$ExternalSyntheticLambda1(this, context);
    }
}
