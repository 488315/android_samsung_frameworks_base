package com.android.systemui.util.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LoopedAnimatable2DrawableWrapper extends DrawableWrapperCompat implements Animatable2 {
    private final Animatable2 animatable2;
    private boolean isLoopedCallbackRegistered;
    private final LoopedCallback loopedCallback;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final LoopedAnimatable2DrawableWrapper fromDrawable(Drawable drawable) {
            if (drawable instanceof Animatable2) {
                return new LoopedAnimatable2DrawableWrapper((Animatable2) drawable, null);
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class LoopedCallback extends Animatable2.AnimationCallback {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Animatable2.AnimationCallback
        public void onAnimationEnd(Drawable drawable) {
            Animatable2 animatable2 = drawable instanceof Animatable2 ? (Animatable2) drawable : null;
            if (animatable2 != null) {
                animatable2.start();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class LoopedDrawableState extends Drawable.ConstantState {
        private final Drawable.ConstantState nestedState;

        public LoopedDrawableState(Drawable.ConstantState constantState) {
            this.nestedState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.nestedState.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.nestedState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return LoopedAnimatable2DrawableWrapper.Companion.fromDrawable(this.nestedState.newDrawable());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return LoopedAnimatable2DrawableWrapper.Companion.fromDrawable(this.nestedState.newDrawable(resources));
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return LoopedAnimatable2DrawableWrapper.Companion.fromDrawable(this.nestedState.newDrawable(resources, theme));
        }
    }

    public /* synthetic */ LoopedAnimatable2DrawableWrapper(Animatable2 animatable2, DefaultConstructorMarker defaultConstructorMarker) {
        this(animatable2);
    }

    private final void setLoopingRegistered(boolean z) {
        if (z == this.isLoopedCallbackRegistered) {
            return;
        }
        this.isLoopedCallbackRegistered = z;
        if (z) {
            this.animatable2.registerAnimationCallback(this.loopedCallback);
        } else {
            this.animatable2.unregisterAnimationCallback(this.loopedCallback);
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public void clearAnimationCallbacks() {
        this.animatable2.clearAnimationCallbacks();
        this.isLoopedCallbackRegistered = false;
        setLoopingRegistered(true);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        Drawable drawable = getDrawable();
        Intrinsics.checkNotNull(drawable);
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            return new LoopedDrawableState(constantState);
        }
        return null;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.animatable2.isRunning();
    }

    @Override // android.graphics.drawable.Animatable2
    public void registerAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        this.animatable2.registerAnimationCallback(animationCallback);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.animatable2.start();
        setLoopingRegistered(true);
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        setLoopingRegistered(false);
        this.animatable2.stop();
    }

    @Override // android.graphics.drawable.Animatable2
    public boolean unregisterAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        return this.animatable2.unregisterAnimationCallback(animationCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private LoopedAnimatable2DrawableWrapper(Animatable2 animatable2) {
        super((Drawable) animatable2);
        this.animatable2 = animatable2;
        this.loopedCallback = new LoopedCallback();
    }
}
