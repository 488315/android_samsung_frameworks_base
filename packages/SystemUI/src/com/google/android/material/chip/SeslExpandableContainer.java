package com.google.android.material.chip;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SeslExpandableContainer extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mChipGroupInitialized;
    public boolean mExpanded;
    public final SeslExpansionButton mExpansionButton;
    public final int mExpansionButtonContainerId;
    public final boolean mFloatChangeAllowed;
    public final boolean mIsRtl;
    public final boolean mPaddingAllowed;
    public final View mPaddingView;
    public final HorizontalScrollView mScrollView;
    public int mScrollViewPos;
    public final LinearLayout mScrollingChipsContainer;

    public SeslExpandableContainer(Context context) {
        this(context, null);
    }

    public final int getScrollContentsWidth() {
        if (this.mExpanded) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mScrollingChipsContainer.getChildCount(); i2++) {
            View childAt = this.mScrollingChipsContainer.getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                if (childAt instanceof SeslChipGroup) {
                    SeslChipGroup seslChipGroup = (SeslChipGroup) childAt;
                    int paddingEnd = seslChipGroup.getPaddingEnd() + seslChipGroup.getPaddingStart();
                    int childCount = seslChipGroup.getChildCount();
                    if (childCount > 0) {
                        for (int i3 = 0; i3 < childCount; i3++) {
                            View childAt2 = seslChipGroup.getChildAt(i3);
                            paddingEnd = (childAt2 instanceof SeslChip ? ((SeslChip) childAt2).chipDrawable.getIntrinsicWidth() : childAt2.getWidth()) + paddingEnd;
                        }
                        if (childCount > 1) {
                            paddingEnd += (childCount - 2) * seslChipGroup.chipSpacingHorizontal;
                        }
                    }
                    i += paddingEnd;
                } else {
                    i += childAt.getWidth();
                }
            }
        }
        return i;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        refreshLayout();
    }

    public final void refreshLayout() {
        setLayoutTransition(null);
        int i = 1;
        if (this.mExpanded) {
            if (this.mScrollingChipsContainer.getChildCount() > 0) {
                SeslExpansionButton seslExpansionButton = this.mExpansionButton;
                seslExpansionButton.mAutoDisappear = false;
                seslExpansionButton.mTimer.cancel();
                this.mScrollViewPos = this.mScrollView.getScrollX();
                int childCount = this.mScrollingChipsContainer.getChildCount();
                View[] viewArr = new View[childCount];
                LinearLayout linearLayout = this.mScrollingChipsContainer;
                boolean z = this.mIsRtl;
                for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
                    viewArr[i2] = linearLayout.getChildAt(i2);
                }
                if (z) {
                    Collections.reverse(Arrays.asList(viewArr));
                }
                int i3 = 0;
                for (int i4 = 0; i4 < childCount; i4++) {
                    View view = viewArr[i4];
                    if (!this.mPaddingAllowed || view.getId() != this.mPaddingView.getId()) {
                        this.mScrollingChipsContainer.removeView(view);
                        addView(view, i);
                        i3 += view.getHeight();
                        i++;
                    }
                }
                this.mScrollView.setVisibility(8);
                if (this.mExpansionButton.getVisibility() == 0 || i3 <= 0) {
                    return;
                }
                this.mExpansionButton.setVisibility(0);
                return;
            }
            return;
        }
        if (getChildCount() > 2) {
            this.mExpansionButton.mAutoDisappear = true;
            this.mScrollView.setVisibility(0);
            int childCount2 = getChildCount();
            View[] viewArr2 = new View[childCount2];
            boolean z2 = this.mIsRtl;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                viewArr2[i5] = getChildAt(i5);
            }
            if (z2) {
                Collections.reverse(Arrays.asList(viewArr2));
            }
            int i6 = 0;
            for (int i7 = 0; i7 < childCount2; i7++) {
                View view2 = viewArr2[i7];
                if (!this.mChipGroupInitialized && (view2 instanceof SeslChipGroup)) {
                    SeslChipGroup seslChipGroup = (SeslChipGroup) view2;
                    seslChipGroup.mChipMaxWidth = getWidth() - ((seslChipGroup.getPaddingEnd() + seslChipGroup.getPaddingStart()) + seslChipGroup.getResources().getDimensionPixelSize(R.dimen.expansion_button_size));
                    this.mChipGroupInitialized = true;
                }
                int id = view2.getId();
                if (id != this.mScrollView.getId() && id != this.mExpansionButtonContainerId && id != this.mPaddingView.getId()) {
                    removeView(view2);
                    this.mScrollingChipsContainer.addView(view2, i6);
                    i6++;
                }
            }
            this.mScrollView.scrollTo(this.mScrollViewPos, 0);
            updateScrollExpansionButton();
        }
    }

    public final void updateScrollExpansionButton() {
        int scrollContentsWidth = getScrollContentsWidth();
        int width = getWidth() + 10;
        int width2 = this.mPaddingView.getWidth();
        if (this.mPaddingAllowed) {
            if ((this.mPaddingView.getVisibility() == 0 && scrollContentsWidth - width2 > width) || (this.mPaddingView.getVisibility() == 8 && scrollContentsWidth > width)) {
                if (this.mExpansionButton.getVisibility() != 0) {
                    this.mExpansionButton.setVisibility(0);
                }
                this.mExpansionButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final SeslExpandableContainer seslExpandableContainer = SeslExpandableContainer.this;
                        seslExpandableContainer.mExpanded = !seslExpandableContainer.mExpanded;
                        seslExpandableContainer.refreshLayout();
                        seslExpandableContainer.post(new Runnable() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SeslExpandableContainer seslExpandableContainer2 = SeslExpandableContainer.this;
                                SeslExpansionButton seslExpansionButton = seslExpandableContainer2.mExpansionButton;
                                seslExpansionButton.mExpanded = seslExpandableContainer2.mExpanded;
                                seslExpansionButton.refreshDrawableState();
                            }
                        });
                    }
                });
            } else if (this.mExpansionButton.getVisibility() == 0) {
                this.mExpansionButton.setVisibility(4);
            }
        } else if (scrollContentsWidth > width) {
            if (this.mExpansionButton.getVisibility() != 0) {
                this.mExpansionButton.setVisibility(0);
            }
            this.mExpansionButton.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    final SeslExpandableContainer seslExpandableContainer = SeslExpandableContainer.this;
                    seslExpandableContainer.mExpanded = !seslExpandableContainer.mExpanded;
                    seslExpandableContainer.refreshLayout();
                    seslExpandableContainer.post(new Runnable() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SeslExpandableContainer seslExpandableContainer2 = SeslExpandableContainer.this;
                            SeslExpansionButton seslExpansionButton = seslExpandableContainer2.mExpansionButton;
                            seslExpansionButton.mExpanded = seslExpandableContainer2.mExpanded;
                            seslExpansionButton.refreshDrawableState();
                        }
                    });
                }
            });
        } else if (this.mExpansionButton.getVisibility() == 0) {
            this.mExpansionButton.setVisibility(4);
        }
        if (this.mFloatChangeAllowed && this.mScrollView.getVisibility() == 0) {
            if (!this.mPaddingAllowed || ((this.mIsRtl && this.mScrollView.getScrollX() > this.mPaddingView.getWidth() / 2) || (!this.mIsRtl && this.mScrollView.getScrollX() < getScrollContentsWidth() - getWidth()))) {
                SeslExpansionButton seslExpansionButton = this.mExpansionButton;
                seslExpansionButton.mFloated = true;
                seslExpansionButton.refreshDrawableState();
            } else {
                SeslExpansionButton seslExpansionButton2 = this.mExpansionButton;
                seslExpansionButton2.mFloated = false;
                seslExpansionButton2.refreshDrawableState();
            }
        }
    }

    public SeslExpandableContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public SeslExpandableContainer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public SeslExpandableContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mExpanded = false;
        this.mPaddingAllowed = true;
        this.mScrollViewPos = 0;
        this.mFloatChangeAllowed = true;
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        this.mIsRtl = z;
        View inflate = LayoutInflater.from(context).inflate(R.layout.sesl_expandable_container, (ViewGroup) null);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) inflate.findViewById(R.id.sesl_scroll_view);
        this.mScrollView = horizontalScrollView;
        horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.google.android.material.chip.SeslExpandableContainer$$ExternalSyntheticLambda0
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i3, int i4, int i5, int i6) {
                SeslExpandableContainer seslExpandableContainer = SeslExpandableContainer.this;
                int i7 = SeslExpandableContainer.$r8$clinit;
                seslExpandableContainer.updateScrollExpansionButton();
            }
        });
        this.mScrollingChipsContainer = (LinearLayout) inflate.findViewById(R.id.sesl_scrolling_chips_container);
        this.mPaddingView = inflate.findViewById(R.id.sesl_padding_view);
        addView(inflate);
        int generateViewId = View.generateViewId();
        this.mExpansionButtonContainerId = generateViewId;
        SeslExpansionButton seslExpansionButton = new SeslExpansionButton(context);
        this.mExpansionButton = seslExpansionButton;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.expansion_button_margin_top), 0, 0);
        seslExpansionButton.setLayoutParams(layoutParams);
        seslExpansionButton.setBackground(context.getDrawable(R.drawable.sesl_expansion_button_background));
        seslExpansionButton.setImageDrawable(context.getDrawable(R.drawable.sesl_expansion_button_foreground));
        seslExpansionButton.mAutoDisappear = true;
        seslExpansionButton.mExpanded = false;
        seslExpansionButton.refreshDrawableState();
        seslExpansionButton.mFloated = true;
        seslExpansionButton.refreshDrawableState();
        seslExpansionButton.setVisibility(8);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        relativeLayout.setId(generateViewId);
        if (z) {
            relativeLayout.setGravity(3);
        } else {
            relativeLayout.setGravity(5);
        }
        addView(relativeLayout);
        relativeLayout.addView(seslExpansionButton);
    }
}
