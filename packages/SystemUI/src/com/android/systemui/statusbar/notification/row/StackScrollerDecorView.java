package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class StackScrollerDecorView extends ExpandableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAnimationDuration;
    public View mContent;
    public boolean mContentAnimating;
    public boolean mContentVisible;
    public boolean mIsSecondaryVisible;
    public boolean mIsVisible;
    public View mSecondaryView;

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsVisible = true;
        this.mContentVisible = true;
        this.mIsSecondaryVisible = true;
        this.mAnimationDuration = 260;
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
        setSecondaryVisible(false);
        setOutlineProvider(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2) {
        setContentVisibleAnimated(true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(long j, long j2, float f, boolean z, boolean z2, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide) {
        runnable.run();
        setContentVisible(false, true, new StackScrollerDecorView$$ExternalSyntheticLambda1(runnable2, 1));
        return 0L;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public final void setContentVisible(boolean z, boolean z2, final Consumer consumer) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StackScrollerDecorView stackScrollerDecorView = StackScrollerDecorView.this;
                    Consumer consumer2 = consumer;
                    Boolean bool = (Boolean) obj;
                    int i = StackScrollerDecorView.$r8$clinit;
                    stackScrollerDecorView.onContentVisibilityAnimationEnd();
                    if (consumer2 != null) {
                        consumer2.accept(bool);
                    }
                }
            });
        } else if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
        if (this.mContentAnimating) {
            return;
        }
        onContentVisibilityAnimationEnd();
    }

    public final void setContentVisibleAnimated(boolean z) {
        setContentVisible(z, true, null);
    }

    public final void setSecondaryVisible(boolean z) {
        if (this.mIsSecondaryVisible != z) {
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, false, new StackScrollerDecorView$$ExternalSyntheticLambda1(this, 0));
        }
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
            view.animate().alpha(f).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).setDuration(this.mAnimationDuration).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView.1
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
                setContentVisible(z, false, null);
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
            setContentVisible(z, true, null);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2, boolean z, boolean z2, Runnable runnable) {
        setContentVisibleAnimated(true);
    }
}
