package com.google.android.material.appbar.model.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SeslIndicator;
import androidx.core.view.ViewGroupKt;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.R;
import com.google.android.material.appbar.model.view.ViewPagerAppBarView$inflate$1$1;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public abstract class BasicViewPagerAppBarView extends ViewPagerAppBarView {
    private final ValueAnimator deleteAlphaAnimator;
    private final long deleteAlphaDuration;
    private AnimatorSet deleteAnimator;
    private ValueAnimator deleteScaleAnimator;
    private final long deleteScaleDuration;
    private final PropertyValuesHolder deleteScaleX;
    private final PropertyValuesHolder deleteScaleY;
    private boolean isDeleteAnimatorRunning;
    private final BasicViewPagerAppBarView$pageChangeCallback$1 pageChangeCallback;

    /* JADX WARN: Multi-variable type inference failed */
    public BasicViewPagerAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final void internalRemoveItem(int i) {
        removeItem(i);
        removeIndicator(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void moveNextAndRemove(ViewPager2 viewPager2, final int i) {
        RecyclerView.Adapter adapter = viewPager2.mRecyclerView.mAdapter;
        if (adapter == null || i < 0 || i >= adapter.getItemCount()) {
            return;
        }
        if (i != viewPager2.mCurrentItem) {
            removeItem(i);
            return;
        }
        int itemCount = adapter.getItemCount();
        int i2 = i == itemCount + (-1) ? i - 1 : i < itemCount ? i + 1 : i;
        this.isDeleteAnimatorRunning = true;
        viewPager2.mFakeDragger.mScrollEventAdapter.getClass();
        viewPager2.setCurrentItemInternal(i2);
        viewPager2.postDelayed(new Runnable() { // from class: com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BasicViewPagerAppBarView.moveNextAndRemove$lambda$11$lambda$10(this.f$0, i);
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void moveNextAndRemove$lambda$11$lambda$10(BasicViewPagerAppBarView basicViewPagerAppBarView, int i) {
        basicViewPagerAppBarView.isDeleteAnimatorRunning = false;
        basicViewPagerAppBarView.removeItem(i);
    }

    public final void addIndicator() throws Resources.NotFoundException {
        final SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            final SeslIndicator.PageIndicatorMarker pageIndicatorMarker = new SeslIndicator.PageIndicatorMarker(indicator.getContext(), null, 2, null);
            pageIndicatorMarker.defaultCircle = indicator.defaultCircle;
            pageIndicatorMarker.setActive(pageIndicatorMarker.isActive);
            pageIndicatorMarker.selectCircle = indicator.selectCircle;
            pageIndicatorMarker.setActive(pageIndicatorMarker.isActive);
            pageIndicatorMarker.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.SeslIndicator$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SeslIndicator seslIndicator = indicator;
                    ViewPagerAppBarView$inflate$1$1 viewPagerAppBarView$inflate$1$1 = seslIndicator.itemClickListener;
                    if (viewPagerAppBarView$inflate$1$1 != null) {
                        int iIndexOf = ((ArrayList) seslIndicator.indicator).indexOf(view);
                        ViewPager2 viewpager = viewPagerAppBarView$inflate$1$1.this$0.getViewpager();
                        if (viewpager != null) {
                            viewpager.mFakeDragger.mScrollEventAdapter.getClass();
                            viewpager.setCurrentItemInternal(iIndexOf);
                        }
                    }
                }
            });
            ((ArrayList) indicator.indicator).add(pageIndicatorMarker);
            pageIndicatorMarker.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: androidx.appcompat.widget.SeslIndicator$addIndicator$1
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setContentDescription(indicator.getResources().getString(R.string.sesl_appbar_suggest_pagination, Integer.valueOf(((ArrayList) indicator.indicator).indexOf(pageIndicatorMarker) + 1), Integer.valueOf(((ArrayList) indicator.indicator).size())));
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            int dimensionPixelSize = indicator.getContext().getResources().getDimensionPixelSize(R.dimen.sesl_viewpager_indicator_horizontal_padding);
            layoutParams.setMargins(dimensionPixelSize, 0, dimensionPixelSize, 0);
            indicator.addView(pageIndicatorMarker, layoutParams);
            if (indicator.selectedPosition == -1) {
                indicator.setSelectedPosition(0);
            }
        }
    }

    public final void initIndicator(int i) throws Resources.NotFoundException {
        if (i > 1) {
            for (int i2 = 0; i2 < i; i2++) {
                addIndicator();
            }
        }
        ViewPager2 viewpager = getViewpager();
        if (viewpager != null) {
            ((ArrayList) viewpager.mExternalPageChangeCallbacks.mCallbacks).add(this.pageChangeCallback);
        }
    }

    public final void removeIndicator(int i) {
        RecyclerView.Adapter adapter;
        SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            indicator.removeIndicator(i);
            ViewPager2 viewpager = getViewpager();
            if (viewpager == null || (adapter = viewpager.mRecyclerView.mAdapter) == null || adapter.getItemCount() != 1) {
                return;
            }
            indicator.removeIndicator(i);
        }
    }

    public abstract void removeItem(int i);

    public final void removeItem(final int i, boolean z) {
        RecyclerView.Adapter adapter;
        RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition;
        if (!z) {
            internalRemoveItem(i);
            return;
        }
        final ViewPager2 viewpager = getViewpager();
        if (viewpager == null || (adapter = viewpager.mRecyclerView.mAdapter) == null || i < 0 || i >= adapter.getItemCount() || viewpager.getChildCount() < 0) {
            return;
        }
        View view = ViewGroupKt.get(viewpager, 0);
        RecyclerView recyclerView = view instanceof RecyclerView ? (RecyclerView) view : null;
        if (recyclerView == null || (viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(i)) == null) {
            internalRemoveItem(i);
            return;
        }
        View view2 = viewHolderFindViewHolderForAdapterPosition.itemView;
        if (this.deleteAnimator == null) {
            if (this.deleteScaleAnimator == null) {
                ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view2, this.deleteScaleX, this.deleteScaleY);
                objectAnimatorOfPropertyValuesHolder.setDuration(this.deleteScaleDuration);
                objectAnimatorOfPropertyValuesHolder.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.sesl_interpolator_22_25_0_1));
                this.deleteScaleAnimator = objectAnimatorOfPropertyValuesHolder;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator[] valueAnimatorArr = new ValueAnimator[2];
            ValueAnimator valueAnimator = this.deleteScaleAnimator;
            if (valueAnimator == null) {
                valueAnimator = null;
            }
            valueAnimatorArr[0] = valueAnimator;
            valueAnimatorArr[1] = this.deleteAlphaAnimator;
            animatorSet.playTogether(Arrays.asList(valueAnimatorArr));
            this.deleteAnimator = animatorSet;
        }
        ValueAnimator valueAnimator2 = this.deleteAlphaAnimator;
        valueAnimator2.removeAllListeners();
        valueAnimator2.addListener(new Animator.AnimatorListener() { // from class: com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$removeItem$1$1$3$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                this.this$0.moveNextAndRemove(viewpager, i);
                this.this$0.removeIndicator(i);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        AnimatorSet animatorSet2 = this.deleteAnimator;
        AnimatorSet animatorSet3 = animatorSet2 != null ? animatorSet2 : null;
        animatorSet3.setTarget(view2);
        animatorSet3.start();
    }

    public /* synthetic */ BasicViewPagerAppBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$pageChangeCallback$1] */
    public BasicViewPagerAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.deleteScaleDuration = 350L;
        this.deleteAlphaDuration = 150L;
        this.deleteScaleX = PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f, 0.9f);
        this.deleteScaleY = PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f, 0.9f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) View.ALPHA, 0.0f);
        objectAnimatorOfFloat.setDuration(150L);
        objectAnimatorOfFloat.setInterpolator(AnimationUtils.loadInterpolator(context, R.interpolator.sesl_interpolator_0_0_1_1));
        this.deleteAlphaAnimator = objectAnimatorOfFloat;
        this.pageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$pageChangeCallback$1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageSelected(int i) {
                SeslIndicator indicator;
                BasicViewPagerAppBarView basicViewPagerAppBarView = this.this$0;
                if (basicViewPagerAppBarView.isDeleteAnimatorRunning || (indicator = basicViewPagerAppBarView.getIndicator()) == null) {
                    return;
                }
                indicator.setSelectedPosition(i);
            }
        };
    }
}
