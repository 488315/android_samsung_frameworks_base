package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BaseProgressIndicator extends ProgressBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnimatorDurationScaleProvider animatorDurationScaleProvider;
    public final RunnableC43282 delayedHide;
    public final RunnableC43271 delayedShow;
    public final C43304 hideAnimationCallback;
    public boolean isIndeterminateModeChangeRequested;
    public final boolean isParentDoneInitializing;
    public final int minHideDelay;
    public final BaseProgressIndicatorSpec spec;
    public int storedProgress;
    public boolean storedProgressAnimated;
    public final C43293 switchIndeterminateModeCallback;
    public final int visibilityAfterHide;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.progressindicator.BaseProgressIndicator$3 */
    public final class C43293 extends Animatable2Compat.AnimationCallback {
        public C43293() {
        }

        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
            baseProgressIndicator.setIndeterminate(false);
            baseProgressIndicator.setProgressCompat(baseProgressIndicator.storedProgress, baseProgressIndicator.storedProgressAnimated);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.progressindicator.BaseProgressIndicator$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.material.progressindicator.BaseProgressIndicator$2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.google.android.material.progressindicator.BaseProgressIndicator$4] */
    public BaseProgressIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019151), attributeSet, i);
        this.isIndeterminateModeChangeRequested = false;
        this.visibilityAfterHide = 4;
        this.delayedShow = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.1
            @Override // java.lang.Runnable
            public final void run() {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                int i3 = BaseProgressIndicator.$r8$clinit;
                if (baseProgressIndicator.minHideDelay > 0) {
                    SystemClock.uptimeMillis();
                }
                baseProgressIndicator.setVisibility(0);
            }
        };
        this.delayedHide = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.2
            @Override // java.lang.Runnable
            public final void run() {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                int i3 = BaseProgressIndicator.$r8$clinit;
                boolean z = false;
                ((DrawableWithAnimatedVisibilityChange) baseProgressIndicator.getCurrentDrawable()).setVisible(false, false, true);
                if ((baseProgressIndicator.getProgressDrawable() == null || !baseProgressIndicator.getProgressDrawable().isVisible()) && (baseProgressIndicator.getIndeterminateDrawable() == null || !baseProgressIndicator.getIndeterminateDrawable().isVisible())) {
                    z = true;
                }
                if (z) {
                    baseProgressIndicator.setVisibility(4);
                }
                BaseProgressIndicator.this.getClass();
            }
        };
        this.switchIndeterminateModeCallback = new C43293();
        this.hideAnimationCallback = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.4
            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                if (baseProgressIndicator.isIndeterminateModeChangeRequested) {
                    return;
                }
                baseProgressIndicator.setVisibility(baseProgressIndicator.visibilityAfterHide);
            }
        };
        Context context2 = getContext();
        this.spec = createSpec(context2, attributeSet);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.BaseProgressIndicator, i, i2, new int[0]);
        obtainStyledAttributes.getInt(5, -1);
        this.minHideDelay = Math.min(obtainStyledAttributes.getInt(3, -1), 1000);
        obtainStyledAttributes.recycle();
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
    }

    public abstract BaseProgressIndicatorSpec createSpec(Context context, AttributeSet attributeSet);

    @Override // android.widget.ProgressBar
    public final Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    @Override // android.view.View
    public final void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getProgressDrawable() != null && getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDelegate.registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (visibleToUser()) {
            if (this.minHideDelay > 0) {
                SystemClock.uptimeMillis();
            }
            setVisibility(0);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.delayedHide);
        removeCallbacks(this.delayedShow);
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(false, false, false);
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
            getIndeterminateDrawable().animatorDelegate.unregisterAnimatorsCompleteCallback();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        int save = canvas.save();
        if (getPaddingLeft() != 0 || getPaddingTop() != 0) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
        }
        if (getPaddingRight() != 0 || getPaddingBottom() != 0) {
            canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        DrawingDelegate drawingDelegate = null;
        if (isIndeterminate()) {
            if (getIndeterminateDrawable() != null) {
                drawingDelegate = getIndeterminateDrawable().drawingDelegate;
            }
        } else if (getProgressDrawable() != null) {
            drawingDelegate = getProgressDrawable().drawingDelegate;
        }
        if (drawingDelegate == null) {
            return;
        }
        setMeasuredDimension(drawingDelegate.getPreferredWidth() < 0 ? ProgressBar.getDefaultSize(getSuggestedMinimumWidth(), i) : drawingDelegate.getPreferredWidth() + getPaddingLeft() + getPaddingRight(), drawingDelegate.getPreferredHeight() < 0 ? ProgressBar.getDefaultSize(getSuggestedMinimumHeight(), i2) : drawingDelegate.getPreferredHeight() + getPaddingTop() + getPaddingBottom());
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        boolean z = i == 0;
        if (this.isParentDoneInitializing) {
            ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(visibleToUser(), false, z);
        }
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (this.isParentDoneInitializing) {
            ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(visibleToUser(), false, false);
        }
    }

    public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider animatorDurationScaleProvider) {
        this.animatorDurationScaleProvider = animatorDurationScaleProvider;
        if (getProgressDrawable() != null) {
            getProgressDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
    }

    @Override // android.widget.ProgressBar
    public final synchronized void setIndeterminate(boolean z) {
        if (z == isIndeterminate()) {
            return;
        }
        DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
        if (drawableWithAnimatedVisibilityChange != null) {
            drawableWithAnimatedVisibilityChange.setVisible(false, false, false);
        }
        super.setIndeterminate(z);
        DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange2 = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
        if (drawableWithAnimatedVisibilityChange2 != null) {
            drawableWithAnimatedVisibilityChange2.setVisible(visibleToUser(), false, false);
        }
        if ((drawableWithAnimatedVisibilityChange2 instanceof IndeterminateDrawable) && visibleToUser()) {
            ((IndeterminateDrawable) drawableWithAnimatedVisibilityChange2).animatorDelegate.startAnimator();
        }
        this.isIndeterminateModeChangeRequested = false;
    }

    @Override // android.widget.ProgressBar
    public final void setIndeterminateDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable(null);
        } else {
            if (!(drawable instanceof IndeterminateDrawable)) {
                throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
            }
            ((DrawableWithAnimatedVisibilityChange) drawable).setVisible(false, false, false);
            super.setIndeterminateDrawable(drawable);
        }
    }

    @Override // android.widget.ProgressBar
    public final synchronized void setProgress(int i) {
        if (isIndeterminate()) {
            return;
        }
        setProgressCompat(i, false);
    }

    public void setProgressCompat(int i, boolean z) {
        if (!isIndeterminate()) {
            super.setProgress(i);
            if (getProgressDrawable() == null || z) {
                return;
            }
            getProgressDrawable().jumpToCurrentState();
            return;
        }
        if (getProgressDrawable() != null) {
            this.storedProgress = i;
            this.storedProgressAnimated = z;
            this.isIndeterminateModeChangeRequested = true;
            if (getIndeterminateDrawable().isVisible()) {
                AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
                ContentResolver contentResolver = getContext().getContentResolver();
                animatorDurationScaleProvider.getClass();
                if (Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f) != 0.0f) {
                    getIndeterminateDrawable().animatorDelegate.requestCancelAnimatorAfterCurrentCycle();
                    return;
                }
            }
            this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
        }
    }

    @Override // android.widget.ProgressBar
    public final void setProgressDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable(null);
        } else {
            if (!(drawable instanceof DeterminateDrawable)) {
                throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
            }
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) drawable;
            determinateDrawable.setVisible(false, false, false);
            super.setProgressDrawable(determinateDrawable);
            determinateDrawable.setLevel((int) ((getProgress() / getMax()) * 10000.0f));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0022, code lost:
    
        if (getWindowVisibility() == 0) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean visibleToUser() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!ViewCompat.Api19Impl.isAttachedToWindow(this) || getWindowVisibility() != 0) {
            return false;
        }
        View view = this;
        while (true) {
            if (view.getVisibility() != 0) {
                break;
            }
            Object parent = view.getParent();
            if (parent != null) {
                if (!(parent instanceof View)) {
                    break;
                }
                view = (View) parent;
            }
        }
        boolean z = false;
        return z;
    }

    @Override // android.widget.ProgressBar
    public final IndeterminateDrawable getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    @Override // android.widget.ProgressBar
    public final DeterminateDrawable getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }
}
