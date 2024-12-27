package com.android.systemui.qs.tileimpl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class QSIconViewImpl extends QSIconView {
    public final boolean isNoBgLargeTile;
    public boolean mAnimationEnabled;
    public final ValueAnimator mColorAnimator;
    public boolean mDisabledByPolicy;
    public long mHighestScheduledIconChangeTransactionId;
    public final View mIcon;
    public int mIconSizePx;
    QSTile.Icon mLastIcon;
    public long mScheduledIconChangeTransactionId;
    public final SecQSIconViewImpl mSecQSIconViewImpl;
    public int mState;
    public int mTint;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class EndRunnableAnimatorListener extends AnimatorListenerAdapter {
        public final Runnable mRunnable;

        public EndRunnableAnimatorListener(Runnable runnable) {
            this.mRunnable = runnable;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            super.onAnimationCancel(animator);
            this.mRunnable.run();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            this.mRunnable.run();
        }
    }

    public QSIconViewImpl(Context context) {
        this(context, false);
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public final void disableAnimation() {
        this.mAnimationEnabled = false;
    }

    public final int getColor(QSTile.State state) {
        Context context = getContext();
        boolean z = this.isNoBgLargeTile;
        SecQSIconViewImpl.Companion.getClass();
        if (z) {
            Log.d("SecQSIconViewImpl", "getIconColorForState: state = " + state);
            return context.getColor(R.color.qs_tile_no_round_icon_color);
        }
        int i = state.state;
        if (i == 0) {
            return ((((int) ((context.getColor(R.color.qs_tile_icon_on_dim_tint_color) >>> 24) * 0.5f)) * 16777216) + 16777215) & context.getColor(R.color.qs_tile_icon_on_dim_tint_color);
        }
        if (i == 1) {
            return context.getColor(R.color.qs_tile_icon_off_tint_color);
        }
        if (i == 2) {
            return context.getColor(R.color.qs_tile_icon_on_dim_tint_color);
        }
        Log.e("SecQSIconViewImpl", "getIconCOlorForState: Invalid state " + state);
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public final View getIconView() {
        return this.mIcon;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SecQSIconViewImpl secQSIconViewImpl = this.mSecQSIconViewImpl;
        this.mIconSizePx = secQSIconViewImpl != null ? secQSIconViewImpl.getIconSize() : getContext().getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2;
        View view = this.mIcon;
        view.layout(measuredWidth, 0, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight());
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        SecQSIconViewImpl secQSIconViewImpl = this.mSecQSIconViewImpl;
        if (secQSIconViewImpl != null) {
            this.mIconSizePx = secQSIconViewImpl.getIconSize();
        }
        int size = View.MeasureSpec.getSize(i);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mIconSizePx, 1073741824));
        setMeasuredDimension(size, this.mIcon.getMeasuredHeight());
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public final void setIcon(QSTile.State state, boolean z) {
        setIcon((ImageView) this.mIcon, state, z);
    }

    public final void setTint(ImageView imageView, int i) {
        imageView.setImageTintList(ColorStateList.valueOf(i));
        this.mTint = i;
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("state=" + this.mState);
        sb.append(", tint=" + this.mTint);
        if (this.mLastIcon != null) {
            sb.append(", lastIcon=" + this.mLastIcon.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
    
        if (r12 != false) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x008e  */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v5, types: [android.graphics.drawable.Drawable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIcon(android.widget.ImageView r10, com.android.systemui.plugins.qs.QSTile.State r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.QSIconViewImpl.updateIcon(android.widget.ImageView, com.android.systemui.plugins.qs.QSTile$State, boolean):void");
    }

    public QSIconViewImpl(Context context, boolean z) {
        super(context);
        this.mAnimationEnabled = true;
        this.mState = -1;
        this.mDisabledByPolicy = false;
        this.mScheduledIconChangeTransactionId = -1L;
        this.mHighestScheduledIconChangeTransactionId = -1L;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.mColorAnimator = valueAnimator;
        context.getResources();
        SecQSIconViewImpl secQSIconViewImpl = new SecQSIconViewImpl(((ViewGroup) this).mContext, z);
        this.mSecQSIconViewImpl = secQSIconViewImpl;
        this.isNoBgLargeTile = z;
        this.mIconSizePx = secQSIconViewImpl.getIconSize();
        ImageView imageView = new ImageView(((ViewGroup) this).mContext);
        imageView.setId(android.R.id.icon);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mIcon = imageView;
        addView(imageView);
        valueAnimator.setDuration(350L);
    }

    public void setIcon(final ImageView imageView, final QSTile.State state, final boolean z) {
        if (this.mSecQSIconViewImpl != null) {
            QSTile.State copy = state.copy();
            boolean z2 = copy.disabledByPolicy;
            if (z2) {
                imageView.setColorFilter(getColor(copy), PorterDuff.Mode.SRC_IN);
                if (copy.state != this.mState) {
                    setTint(imageView, getColor(copy));
                }
            } else if (this.mState != copy.state || this.mDisabledByPolicy != z2) {
                imageView.setColorFilter(getColor(copy), PorterDuff.Mode.SRC_IN);
            }
            this.mState = copy.state;
            this.mDisabledByPolicy = copy.disabledByPolicy;
            updateIcon(imageView, copy, z);
            return;
        }
        if (state.state == this.mState && state.disabledByPolicy == this.mDisabledByPolicy) {
            updateIcon(imageView, state, z);
            return;
        }
        int color = getColor(state);
        this.mState = state.state;
        this.mDisabledByPolicy = state.disabledByPolicy;
        if (this.mTint == 0 || !z || !this.mAnimationEnabled || !imageView.isShown() || imageView.getDrawable() == null) {
            setTint(imageView, color);
            updateIcon(imageView, state, z);
            return;
        }
        final long j = this.mHighestScheduledIconChangeTransactionId + 1;
        this.mHighestScheduledIconChangeTransactionId = j;
        this.mScheduledIconChangeTransactionId = j;
        int i = this.mTint;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QSIconViewImpl qSIconViewImpl = QSIconViewImpl.this;
                long j2 = j;
                ImageView imageView2 = imageView;
                QSTile.State state2 = state;
                boolean z3 = z;
                if (qSIconViewImpl.mScheduledIconChangeTransactionId == j2) {
                    qSIconViewImpl.updateIcon(imageView2, state2, z3);
                }
            }
        };
        this.mColorAnimator.cancel();
        if (!this.mAnimationEnabled || !ValueAnimator.areAnimatorsEnabled()) {
            setTint(imageView, color);
            runnable.run();
            return;
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("color", i, color);
        ofInt.setEvaluator(ArgbEvaluator.getInstance());
        this.mColorAnimator.setValues(ofInt);
        this.mColorAnimator.removeAllListeners();
        this.mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                QSIconViewImpl qSIconViewImpl = QSIconViewImpl.this;
                ImageView imageView2 = imageView;
                qSIconViewImpl.getClass();
                qSIconViewImpl.setTint(imageView2, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.mColorAnimator.addListener(new EndRunnableAnimatorListener(runnable));
        this.mColorAnimator.start();
    }
}
