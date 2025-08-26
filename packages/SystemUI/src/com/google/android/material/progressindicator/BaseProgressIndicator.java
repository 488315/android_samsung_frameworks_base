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
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public abstract class BaseProgressIndicator extends ProgressBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnimatorDurationScaleProvider animatorDurationScaleProvider;
    public final AnonymousClass2 delayedHide;
    public final AnonymousClass1 delayedShow;
    public final AnonymousClass4 hideAnimationCallback;
    public boolean isIndeterminateModeChangeRequested;
    public final boolean isParentDoneInitializing;
    public final int minHideDelay;
    public final BaseProgressIndicatorSpec spec;
    public int storedProgress;
    public final AnonymousClass3 switchIndeterminateModeCallback;
    public final int visibilityAfterHide;

    /* renamed from: com.google.android.material.progressindicator.BaseProgressIndicator$3, reason: invalid class name */
    public class AnonymousClass3 extends Animatable2Compat.AnimationCallback {
        public AnonymousClass3() {
        }

        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
            baseProgressIndicator.setIndeterminate(false);
            baseProgressIndicator.setProgressCompat(baseProgressIndicator.storedProgress);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.material.progressindicator.BaseProgressIndicator$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.progressindicator.BaseProgressIndicator$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.material.progressindicator.BaseProgressIndicator$4] */
    public BaseProgressIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_MaterialComponents_ProgressIndicator), attributeSet, i);
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
                BaseProgressIndicator.access$100(BaseProgressIndicator.this);
                BaseProgressIndicator.this.getClass();
            }
        };
        this.switchIndeterminateModeCallback = new AnonymousClass3();
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
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.BaseProgressIndicator, i, i2, new int[0]);
        typedArrayObtainStyledAttributes.getInt(6, -1);
        this.minHideDelay = Math.min(typedArrayObtainStyledAttributes.getInt(4, -1), 1000);
        typedArrayObtainStyledAttributes.recycle();
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
    }

    public static void access$100(BaseProgressIndicator baseProgressIndicator) {
        ((DrawableWithAnimatedVisibilityChange) baseProgressIndicator.getCurrentDrawable()).setVisible(false, false, true);
        if (((DeterminateDrawable) super.getProgressDrawable()) == null || !((DeterminateDrawable) super.getProgressDrawable()).isVisible()) {
            if (((IndeterminateDrawable) super.getIndeterminateDrawable()) == null || !((IndeterminateDrawable) super.getIndeterminateDrawable()).isVisible()) {
                baseProgressIndicator.setVisibility(4);
            }
        }
    }

    public abstract BaseProgressIndicatorSpec createSpec(Context context, AttributeSet attributeSet);

    @Override // android.widget.ProgressBar
    public final Drawable getCurrentDrawable() {
        return isIndeterminate() ? (IndeterminateDrawable) super.getIndeterminateDrawable() : (DeterminateDrawable) super.getProgressDrawable();
    }

    @Override // android.widget.ProgressBar
    public final Drawable getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    @Override // android.widget.ProgressBar
    public final Drawable getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
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
        if (((DeterminateDrawable) super.getProgressDrawable()) != null && ((IndeterminateDrawable) super.getIndeterminateDrawable()) != null) {
            ((IndeterminateDrawable) super.getIndeterminateDrawable()).animatorDelegate.registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
            ((DeterminateDrawable) super.getProgressDrawable()).registerAnimationCallback(this.hideAnimationCallback);
        }
        if (((IndeterminateDrawable) super.getIndeterminateDrawable()) != null) {
            ((IndeterminateDrawable) super.getIndeterminateDrawable()).registerAnimationCallback(this.hideAnimationCallback);
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
        if (((IndeterminateDrawable) super.getIndeterminateDrawable()) != null) {
            ((IndeterminateDrawable) super.getIndeterminateDrawable()).unregisterAnimationCallback(this.hideAnimationCallback);
            ((IndeterminateDrawable) super.getIndeterminateDrawable()).animatorDelegate.unregisterAnimatorsCompleteCallback();
        }
        if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
            ((DeterminateDrawable) super.getProgressDrawable()).unregisterAnimationCallback(this.hideAnimationCallback);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        try {
            int iSave = canvas.save();
            if (getPaddingLeft() != 0 || getPaddingTop() != 0) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            if (getPaddingRight() != 0 || getPaddingBottom() != 0) {
                canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
            }
            getCurrentDrawable().draw(canvas);
            canvas.restoreToCount(iSave);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        try {
            DrawingDelegate drawingDelegate = null;
            if (isIndeterminate()) {
                if (((IndeterminateDrawable) super.getIndeterminateDrawable()) != null) {
                    drawingDelegate = ((IndeterminateDrawable) super.getIndeterminateDrawable()).drawingDelegate;
                }
            } else if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
                drawingDelegate = ((DeterminateDrawable) super.getProgressDrawable()).drawingDelegate;
            }
            if (drawingDelegate == null) {
                return;
            }
            setMeasuredDimension(drawingDelegate.getPreferredWidth() < 0 ? ProgressBar.getDefaultSize(getSuggestedMinimumWidth(), i) : drawingDelegate.getPreferredWidth() + getPaddingLeft() + getPaddingRight(), drawingDelegate.getPreferredHeight() < 0 ? ProgressBar.getDefaultSize(getSuggestedMinimumHeight(), i2) : drawingDelegate.getPreferredHeight() + getPaddingTop() + getPaddingBottom());
        } finally {
        }
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
        if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
            ((DeterminateDrawable) super.getProgressDrawable()).animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
        if (((IndeterminateDrawable) super.getIndeterminateDrawable()) != null) {
            ((IndeterminateDrawable) super.getIndeterminateDrawable()).animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
    }

    @Override // android.widget.ProgressBar
    public final synchronized void setIndeterminate(boolean z) {
        try {
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
        } catch (Throwable th) {
            throw th;
        }
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
        setProgressCompat(i);
    }

    public void setProgressCompat(int i) {
        if (!isIndeterminate()) {
            super.setProgress(i);
            if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
                ((DeterminateDrawable) super.getProgressDrawable()).jumpToCurrentState();
                return;
            }
            return;
        }
        if (((DeterminateDrawable) super.getProgressDrawable()) != null) {
            this.storedProgress = i;
            this.isIndeterminateModeChangeRequested = true;
            if (((IndeterminateDrawable) super.getIndeterminateDrawable()).isVisible()) {
                AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
                ContentResolver contentResolver = getContext().getContentResolver();
                animatorDurationScaleProvider.getClass();
                if (Settings.Global.getFloat(contentResolver, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) != 0.0f) {
                    ((IndeterminateDrawable) super.getIndeterminateDrawable()).animatorDelegate.requestCancelAnimatorAfterCurrentCycle();
                    return;
                }
            }
            this.switchIndeterminateModeCallback.onAnimationEnd((IndeterminateDrawable) super.getIndeterminateDrawable());
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

    public final boolean visibleToUser() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!isAttachedToWindow() || getWindowVisibility() != 0) {
            return false;
        }
        View view = this;
        while (view.getVisibility() == 0) {
            Object parent = view.getParent();
            if (parent == null) {
                return getWindowVisibility() == 0;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            view = (View) parent;
        }
        return false;
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
