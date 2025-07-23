package com.android.wm.shell.shared.bubbles;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.IntProperty;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DismissView extends FrameLayout {
    public static final String TAG;
    public final long DISMISS_SCRIM_FADE_MS;
    public final DismissView$GRADIENT_ALPHA$1 GRADIENT_ALPHA;
    public final PhysicsAnimator animator;
    public final DismissCircleView circle;
    public Config config;
    public final Rect dismissArea;
    public GradientDrawable gradientDrawable;
    public boolean isBeingEntered;
    public boolean isShowing;
    public final PhysicsAnimator.SpringConfig spring;
    public final WindowManager wm;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Config {
        public final int backgroundResId;
        public final int bottomMarginResId;
        public final int dismissViewResId;
        public final int floatingGradientColorResId;
        public final int floatingGradientHeightResId;
        public final int iconResId;
        public final int iconSizeResId;
        public final int targetSizeResId;

        public Config(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.dismissViewResId = i;
            this.targetSizeResId = i2;
            this.iconSizeResId = i3;
            this.bottomMarginResId = i4;
            this.floatingGradientHeightResId = i5;
            this.floatingGradientColorResId = i6;
            this.backgroundResId = i7;
            this.iconResId = i8;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Config)) {
                return false;
            }
            Config config = (Config) obj;
            return this.dismissViewResId == config.dismissViewResId && this.targetSizeResId == config.targetSizeResId && this.iconSizeResId == config.iconSizeResId && this.bottomMarginResId == config.bottomMarginResId && this.floatingGradientHeightResId == config.floatingGradientHeightResId && this.floatingGradientColorResId == config.floatingGradientColorResId && this.backgroundResId == config.backgroundResId && this.iconResId == config.iconResId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.iconResId) + ReorderTile$$ExternalSyntheticOutline0.m(this.backgroundResId, ReorderTile$$ExternalSyntheticOutline0.m(this.floatingGradientColorResId, ReorderTile$$ExternalSyntheticOutline0.m(this.floatingGradientHeightResId, ReorderTile$$ExternalSyntheticOutline0.m(this.bottomMarginResId, ReorderTile$$ExternalSyntheticOutline0.m(this.iconSizeResId, ReorderTile$$ExternalSyntheticOutline0.m(this.targetSizeResId, Integer.hashCode(this.dismissViewResId) * 31, 31), 31), 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Config(dismissViewResId=");
            sb.append(this.dismissViewResId);
            sb.append(", targetSizeResId=");
            sb.append(this.targetSizeResId);
            sb.append(", iconSizeResId=");
            sb.append(this.iconSizeResId);
            sb.append(", bottomMarginResId=");
            sb.append(this.bottomMarginResId);
            sb.append(", floatingGradientHeightResId=");
            sb.append(this.floatingGradientHeightResId);
            sb.append(", floatingGradientColorResId=");
            sb.append(this.floatingGradientColorResId);
            sb.append(", backgroundResId=");
            sb.append(this.backgroundResId);
            sb.append(", iconResId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.iconResId, ")", sb);
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(DismissView.class).getSimpleName();
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.wm.shell.shared.bubbles.DismissView$GRADIENT_ALPHA$1] */
    public DismissView(Context context) {
        super(context);
        DismissCircleView dismissCircleView = new DismissCircleView(context);
        this.circle = dismissCircleView;
        PhysicsAnimator.Companion.getClass();
        this.animator = PhysicsAnimator.Companion.getInstance(dismissCircleView);
        this.spring = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
        this.DISMISS_SCRIM_FADE_MS = 200L;
        this.wm = (WindowManager) context.getSystemService("window");
        this.GRADIENT_ALPHA = new IntProperty() { // from class: com.android.wm.shell.shared.bubbles.DismissView$GRADIENT_ALPHA$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                return Integer.valueOf(((GradientDrawable) obj).getAlpha());
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ((GradientDrawable) obj).setAlpha(i);
            }
        };
        setClipToPadding(false);
        setClipChildren(false);
        setVisibility(4);
        addView(dismissCircleView);
        this.dismissArea = new Rect();
    }

    public final void hide() {
        if (this.isShowing) {
            GradientDrawable gradientDrawable = this.gradientDrawable;
            if (gradientDrawable == null) {
                Log.e(TAG, "The view isn't ready. Should be called after `setup`");
            }
            if (gradientDrawable == null) {
                return;
            }
            this.isShowing = false;
            ObjectAnimator ofInt = ObjectAnimator.ofInt(gradientDrawable, this.GRADIENT_ALPHA, gradientDrawable.getAlpha(), 0);
            ofInt.setDuration(this.DISMISS_SCRIM_FADE_MS);
            ofInt.start();
            this.isBeingEntered = false;
            PhysicsAnimator physicsAnimator = this.animator;
            physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, getHeight(), 0.0f, this.spring);
            physicsAnimator.withEndActions(new DismissView$$ExternalSyntheticLambda0(this, 0));
            physicsAnimator.start();
        }
    }

    public final void setup(Config config) {
        this.config = config;
        setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(config.floatingGradientHeightResId), 80));
        Config config2 = this.config;
        if (config2 == null) {
            Log.e(TAG, "The view isn't ready. Should be called after `setup`");
        }
        if (config2 != null) {
            setPadding(0, 0, 0, getResources().getDimensionPixelSize(config2.bottomMarginResId) + this.wm.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars()).bottom);
        }
        int color = getContext().getColor(config.floatingGradientColorResId);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.argb((int) 178.5f, Color.red(color), Color.green(color), Color.blue(color)), 0});
        gradientDrawable.setDither(true);
        gradientDrawable.setAlpha(0);
        this.gradientDrawable = gradientDrawable;
        setBackgroundDrawable(gradientDrawable);
        this.circle.setId(config.dismissViewResId);
        this.circle.setup(config.backgroundResId, config.iconResId, config.iconSizeResId);
        int dimensionPixelSize = getResources().getDimensionPixelSize(config.targetSizeResId);
        this.circle.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 81));
        this.circle.setTranslationY(getResources().getDimensionPixelSize(r2));
    }

    public final void show() {
        if (this.isShowing) {
            return;
        }
        GradientDrawable gradientDrawable = this.gradientDrawable;
        if (gradientDrawable == null) {
            Log.e(TAG, "The view isn't ready. Should be called after `setup`");
        }
        if (gradientDrawable == null) {
            return;
        }
        this.isShowing = true;
        setVisibility(0);
        ObjectAnimator ofInt = ObjectAnimator.ofInt(gradientDrawable, this.GRADIENT_ALPHA, gradientDrawable.getAlpha(), 255);
        ofInt.setDuration(this.DISMISS_SCRIM_FADE_MS);
        ofInt.start();
        this.animator.cancel();
        PhysicsAnimator physicsAnimator = this.animator;
        physicsAnimator.withEndActions(new DismissView$$ExternalSyntheticLambda0(this, 1));
        physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, 0.0f, 0.0f, this.spring);
        physicsAnimator.start();
    }
}
