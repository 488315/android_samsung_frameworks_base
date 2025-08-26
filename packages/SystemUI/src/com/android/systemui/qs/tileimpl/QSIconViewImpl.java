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
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import java.util.Objects;
import java.util.function.Supplier;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public class QSIconViewImpl extends QSIconView {
    public static final /* synthetic */ int $r8$clinit = 0;
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

    public class EndRunnableAnimatorListener extends AnimatorListenerAdapter {
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
        this.mIconSizePx = secQSIconViewImpl != null ? ((SecQSPanelResourcePicker) secQSIconViewImpl.resourcePicker$delegate.getValue()).getTileImageSize(secQSIconViewImpl.context) : getContext().getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
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
        this.mIconSizePx = secQSIconViewImpl != null ? ((SecQSPanelResourcePicker) secQSIconViewImpl.resourcePicker$delegate.getValue()).getTileImageSize(secQSIconViewImpl.context) : getContext().getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c6  */
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
    */
    public final void updateIcon(ImageView imageView, QSTile.State state, boolean z) {
        boolean z2;
        String string;
        boolean zContains;
        this.mScheduledIconChangeTransactionId = -1L;
        Supplier<QSTile.Icon> supplier = state.iconSupplier;
        QSTile.Icon icon = supplier != null ? supplier.get() : state.icon;
        if (Objects.equals(icon, imageView.getTag(R.id.qs_icon_tag))) {
            return;
        }
        if (z && this.mAnimationEnabled && imageView.isShown() && imageView.getDrawable() != null) {
            SecQSIconViewImpl secQSIconViewImpl = this.mSecQSIconViewImpl;
            z2 = true;
            if (secQSIconViewImpl != null) {
                QuickCustomTileIconResize quickCustomTileIconResize = secQSIconViewImpl.quickCustomTileIconResize;
                if (quickCustomTileIconResize != null) {
                    Supplier<QSTile.Icon> supplier2 = state.iconSupplier;
                    if (supplier2 == null || (string = supplier2.toString()) == null) {
                        string = "";
                    }
                    if (StringsKt__StringsKt.contains(string, "CustomTile", false)) {
                        String str = state.tileClassName;
                        if (Intrinsics.areEqual(str, "NearbyShare")) {
                            zContains = false;
                        } else {
                            zContains = StringsKt__StringsKt.contains(quickCustomTileIconResize.context.getResources().getString(R.string.quick_settings_custom_tile_component_names), str != null ? str : "", false);
                        }
                        boolean z3 = !zContains;
                        if (z3) {
                        }
                    }
                }
            }
        } else {
            z2 = false;
        }
        this.mLastIcon = icon;
        ?? drawable = icon != null ? z2 ? icon.getDrawable(((ViewGroup) this).mContext) : icon.getInvisibleDrawable(((ViewGroup) this).mContext) : 0;
        int padding = icon != null ? icon.getPadding() : 0;
        if (drawable != 0) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            drawable = drawable;
            if (constantState != null) {
                drawable = drawable.getConstantState().newDrawable();
            }
            drawable.setAutoMirrored(false);
            drawable.setLayoutDirection(getLayoutDirection());
        }
        SecQSIconViewImpl secQSIconViewImpl2 = this.mSecQSIconViewImpl;
        if (secQSIconViewImpl2 != null) {
            QuickCustomTileIconResize quickCustomTileIconResize2 = secQSIconViewImpl2.quickCustomTileIconResize;
            if (quickCustomTileIconResize2 != null) {
                if ((drawable instanceof ScalingDrawableWrapper ? quickCustomTileIconResize2 : null) != null) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                }
            } else if (secQSIconViewImpl2.isNoBgLargeTile) {
                setFocusable(false);
            }
        }
        Object drawable2 = imageView.getDrawable();
        if (drawable2 instanceof Animatable2) {
            ((Animatable2) drawable2).clearAnimationCallbacks();
        }
        imageView.setImageDrawable(drawable);
        imageView.setTag(R.id.qs_icon_tag, icon);
        imageView.setPadding(0, padding, 0, padding);
        if (drawable instanceof Animatable2) {
            final Animatable2 animatable2 = (Animatable2) drawable;
            animatable2.start();
            if (!z2) {
                animatable2.stop();
                return;
            } else {
                if (state.isTransient) {
                    animatable2.registerAnimationCallback(new Animatable2.AnimationCallback(this) { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.1
                        @Override // android.graphics.drawable.Animatable2.AnimationCallback
                        public final void onAnimationEnd(Drawable drawable3) {
                            animatable2.start();
                        }
                    });
                    return;
                }
                return;
            }
        }
        if (this.mSecQSIconViewImpl == null || drawable == 0 || !(drawable instanceof AnimationDrawable) || !imageView.isShown()) {
            return;
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        animationDrawable.start();
        if (imageView.isShown()) {
            return;
        }
        animationDrawable.stop();
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
        SecQSIconViewImpl secQSIconViewImpl = new SecQSIconViewImpl(((ViewGroup) this).mContext, z);
        this.mSecQSIconViewImpl = secQSIconViewImpl;
        this.isNoBgLargeTile = z;
        context.getResources();
        this.mIconSizePx = ((SecQSPanelResourcePicker) secQSIconViewImpl.resourcePicker$delegate.getValue()).getTileImageSize(secQSIconViewImpl.context);
        ImageView imageView = new ImageView(((ViewGroup) this).mContext);
        imageView.setId(android.R.id.icon);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mIcon = imageView;
        addView(imageView);
        valueAnimator.setDuration(350L);
    }

    public void setIcon(final ImageView imageView, final QSTile.State state, final boolean z) {
        if (this.mSecQSIconViewImpl != null) {
            QSTile.State stateCopy = state.copy();
            boolean z2 = stateCopy.disabledByPolicy;
            if (z2) {
                imageView.setColorFilter(getColor(stateCopy), PorterDuff.Mode.SRC_IN);
                if (stateCopy.state != this.mState) {
                    setTint(imageView, getColor(stateCopy));
                }
            } else if (this.mState != stateCopy.state || this.mDisabledByPolicy != z2) {
                imageView.setColorFilter(getColor(stateCopy), PorterDuff.Mode.SRC_IN);
            }
            this.mState = stateCopy.state;
            this.mDisabledByPolicy = stateCopy.disabledByPolicy;
            updateIcon(imageView, stateCopy, z);
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
                QSIconViewImpl qSIconViewImpl = this.f$0;
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
        PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt("color", i, color);
        propertyValuesHolderOfInt.setEvaluator(ArgbEvaluator.getInstance());
        this.mColorAnimator.setValues(propertyValuesHolderOfInt);
        this.mColorAnimator.removeAllListeners();
        this.mColorAnimator.removeAllUpdateListeners();
        this.mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                QSIconViewImpl qSIconViewImpl = this.f$0;
                ImageView imageView2 = imageView;
                int i2 = QSIconViewImpl.$r8$clinit;
                qSIconViewImpl.getClass();
                qSIconViewImpl.setTint(imageView2, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.mColorAnimator.addListener(new EndRunnableAnimatorListener(runnable));
        this.mColorAnimator.start();
    }
}
