package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class StackScrollerDecorView extends ExpandableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mContent;
    public boolean mContentAnimating;
    public final StackScrollerDecorView$$ExternalSyntheticLambda0 mContentVisibilityEndRunnable;
    public boolean mContentVisible;
    public final int mDuration;
    public float mEndAlpha;
    public boolean mIsSecondaryVisible;
    public boolean mIsVisible;
    public boolean mSecondaryAnimating;
    public View mSecondaryView;
    public final StackScrollerDecorView$$ExternalSyntheticLambda1 mSecondaryVisibilityEndRunnable;

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsVisible = true;
        this.mContentVisible = true;
        this.mIsSecondaryVisible = true;
        this.mDuration = 260;
        this.mContentVisibilityEndRunnable = new StackScrollerDecorView$$ExternalSyntheticLambda0(this);
        this.mSecondaryAnimating = false;
        this.mSecondaryVisibilityEndRunnable = new StackScrollerDecorView$$ExternalSyntheticLambda1(this, 0);
        this.mEndAlpha = 1.0f;
        setClipChildren(false);
    }

    public abstract View findContentView();

    public abstract View findSecondaryView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isSecondaryVisible() {
        return this.mIsSecondaryVisible;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isTransparent() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean needsClippingToShelf() {
        return this instanceof SectionHeaderView;
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
        setContentVisible(null, true, true);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        setContentVisible(new StackScrollerDecorView$$ExternalSyntheticLambda1(runnable, 1), false, true);
        return 0L;
    }

    public final void setContentVisible(final Consumer consumer, boolean z, boolean z2) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StackScrollerDecorView stackScrollerDecorView = StackScrollerDecorView.this;
                    Consumer consumer2 = consumer;
                    Boolean bool = (Boolean) obj;
                    stackScrollerDecorView.mContentVisibilityEndRunnable.run();
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
        this.mContentVisibilityEndRunnable.run();
    }

    public final void setSecondaryVisible(boolean z) {
        if (this.mIsSecondaryVisible != z) {
            this.mSecondaryAnimating = false;
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, false, this.mSecondaryVisibilityEndRunnable);
        }
        if (this.mSecondaryAnimating) {
            return;
        }
        this.mSecondaryVisibilityEndRunnable.accept(Boolean.TRUE);
    }

    public final void setViewVisible(View view, boolean z, boolean z2, final Consumer consumer) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        view.animate().cancel();
        float f = z ? NotiRune.NOTI_STYLE_EMPTY_SHADE ? this.mEndAlpha : 1.0f : 0.0f;
        if (z2) {
            view.animate().alpha(f).setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT).setDuration(z ? 1000L : this.mDuration).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.row.StackScrollerDecorView.1
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
            return;
        }
        view.setAlpha(f);
        if (consumer != null) {
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
    public final void performAddAnimation(long j, long j2, boolean z) {
        setContentVisible(null, true, true);
    }
}
