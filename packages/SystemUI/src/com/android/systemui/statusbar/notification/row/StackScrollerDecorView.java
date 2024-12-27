package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class StackScrollerDecorView extends ExpandableView {
    public int mAnimationDuration;
    public View mContent;
    public boolean mContentAnimating;
    public boolean mContentVisible;
    public boolean mIsSecondaryVisible;
    public boolean mIsVisible;
    public boolean mSecondaryAnimating;
    public View mSecondaryView;

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsVisible = true;
        this.mContentVisible = true;
        this.mIsSecondaryVisible = true;
        this.mAnimationDuration = 260;
        this.mSecondaryAnimating = false;
        setClipChildren(false);
    }

    public abstract View findContentView();

    public abstract View findSecondaryView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isTransparent() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean needsClippingToShelf() {
        return this instanceof SectionHeaderView;
    }

    public final void onContentVisibilityAnimationEnd() {
        this.mContentAnimating = false;
        if (getVisibility() == 8 || this.mIsVisible) {
            return;
        }
        setVisibility(8);
        this.mWillBeGone = false;
        notifyHeightChanged(false);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContent = findContentView();
        this.mSecondaryView = findSecondaryView();
        setVisible(false, false);
        setSecondaryVisible(false, false, null);
        setOutlineProvider(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2) {
        setContentVisibleAnimated(true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(float f, long j, long j2, AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide, Runnable runnable, final Runnable runnable2, boolean z) {
        runnable.run();
        setContentVisible(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable2.run();
            }
        }, false, true);
        return 0L;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public final void setContentVisible(Consumer consumer, boolean z, boolean z2) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, new StackScrollerDecorView$$ExternalSyntheticLambda0(this, consumer));
        } else if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
        if (this.mContentAnimating) {
            return;
        }
        onContentVisibilityAnimationEnd();
    }

    public final void setContentVisibleAnimated(boolean z) {
        setContentVisible(null, z, true);
    }

    public final void setSecondaryVisible(boolean z, boolean z2, FooterViewBinder$bindClearAllButton$2.AnonymousClass3.AnonymousClass1.C01911 c01911) {
        if (this.mIsSecondaryVisible != z) {
            this.mSecondaryAnimating = z2;
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, z2, new StackScrollerDecorView$$ExternalSyntheticLambda0(this, c01911));
        }
        if (this.mSecondaryAnimating) {
            return;
        }
        this.mSecondaryAnimating = false;
        if (this.mSecondaryView == null || getVisibility() == 8 || this.mSecondaryView.getVisibility() == 8 || this.mIsSecondaryVisible) {
            return;
        }
        this.mSecondaryView.setVisibility(8);
    }

    public final void setViewVisible(View view, boolean z, boolean z2, final Consumer consumer) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        view.animate().cancel();
        float f = z ? 1.0f : 0.0f;
        if (z2) {
            view.animate().alpha(f).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).setDuration(z ? 1000L : this.mAnimationDuration).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView.1
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    consumer.accept(Boolean.valueOf(this.mCancelled));
                }
            });
        } else {
            view.setAlpha(f);
            consumer.accept(Boolean.TRUE);
        }
    }

    public final void setVisible(boolean z, boolean z2) {
        if (this.mIsVisible != z) {
            this.mIsVisible = z;
            if (!z2) {
                setVisibility(z ? 0 : 8);
                setContentVisible(null, z, false);
                this.mWillBeGone = false;
                notifyHeightChanged(false);
                return;
            }
            if (z) {
                setVisibility(0);
                this.mWillBeGone = false;
                notifyHeightChanged(false);
            } else {
                this.mWillBeGone = true;
            }
            setContentVisible(null, z, true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, boolean z, long j2) {
        setContentVisibleAnimated(true);
    }
}
