package com.android.systemui.keyguard.ui.binder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.util.Log;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.repository.KeyguardSurfaceBehindRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardSurfaceBehindModel;
import com.android.wm.shell.animation.Interpolators;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindParamsApplier {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ValueAnimator alphaAnimator;
    public float animatedAlpha;
    public final FloatValueHolder animatedTranslationY;
    public final Executor executor;
    public final KeyguardSurfaceBehindInteractor interactor;
    public final KeyguardViewController keyguardViewController;
    public final RemoteAnimationTarget surfaceBehind;
    public final SpringAnimation translateYSpring;
    public final KeyguardSurfaceBehindModel viewParams;
    public final Matrix matrix = new Matrix();
    public final float[] tmpFloat = new float[9];

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardSurfaceBehindParamsApplier(Executor executor, KeyguardViewController keyguardViewController, KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        this.executor = executor;
        this.keyguardViewController = keyguardViewController;
        this.interactor = keyguardSurfaceBehindInteractor;
        FloatValueHolder floatValueHolder = new FloatValueHolder();
        this.animatedTranslationY = floatValueHolder;
        SpringAnimation springAnimation = new SpringAnimation(floatValueHolder);
        springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m(275.0f, 0.98f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier$translateYSpring$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                int i = KeyguardSurfaceBehindParamsApplier.$r8$clinit;
                KeyguardSurfaceBehindParamsApplier.this.applyToSurfaceBehind();
            }
        });
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier$translateYSpring$1$3
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                try {
                    KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier = KeyguardSurfaceBehindParamsApplier.this;
                    int i = KeyguardSurfaceBehindParamsApplier.$r8$clinit;
                    keyguardSurfaceBehindParamsApplier.updateIsAnimatingSurface();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        this.translateYSpring = springAnimation;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(Interpolators.ALPHA_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier$alphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSurfaceBehindParamsApplier.this.animatedAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardSurfaceBehindParamsApplier.this.applyToSurfaceBehind();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier$alphaAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier = KeyguardSurfaceBehindParamsApplier.this;
                int i = KeyguardSurfaceBehindParamsApplier.$r8$clinit;
                keyguardSurfaceBehindParamsApplier.updateIsAnimatingSurface();
            }
        });
        this.alphaAnimator = ofFloat;
        this.viewParams = new KeyguardSurfaceBehindModel(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 31, null);
    }

    public final void applyToSurfaceBehind() {
        final SurfaceControl surfaceControl;
        RemoteAnimationTarget remoteAnimationTarget = this.surfaceBehind;
        if (remoteAnimationTarget == null || (surfaceControl = remoteAnimationTarget.leash) == null) {
            return;
        }
        this.executor.execute(new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier$applyToSurfaceBehind$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier = KeyguardSurfaceBehindParamsApplier.this;
                if (keyguardSurfaceBehindParamsApplier.surfaceBehind == null) {
                    Log.d("KeyguardUnlock", "Attempting to modify params of surface that isn't animating. Ignoring.");
                    KeyguardSurfaceBehindParamsApplier.this.matrix.set(Matrix.IDENTITY_MATRIX);
                    return;
                }
                float f = keyguardSurfaceBehindParamsApplier.translateYSpring.mRunning ? keyguardSurfaceBehindParamsApplier.animatedTranslationY.mValue : keyguardSurfaceBehindParamsApplier.viewParams.translationY;
                float f2 = keyguardSurfaceBehindParamsApplier.alphaAnimator.isRunning() ? KeyguardSurfaceBehindParamsApplier.this.animatedAlpha : KeyguardSurfaceBehindParamsApplier.this.viewParams.alpha;
                View view = KeyguardSurfaceBehindParamsApplier.this.keyguardViewController.getViewRootImpl().getView();
                if ((view != null && view.getVisibility() == 0) || !surfaceControl.isValid()) {
                    KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier2 = KeyguardSurfaceBehindParamsApplier.this;
                    keyguardSurfaceBehindParamsApplier2.getClass();
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(keyguardSurfaceBehindParamsApplier2.keyguardViewController.getViewRootImpl().getView());
                    SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl);
                    Matrix matrix = KeyguardSurfaceBehindParamsApplier.this.matrix;
                    matrix.setTranslate(0.0f, f);
                    Unit unit = Unit.INSTANCE;
                    syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.withMatrix(matrix).withAlpha(f2).build()});
                    return;
                }
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                SurfaceControl surfaceControl2 = surfaceControl;
                KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier3 = KeyguardSurfaceBehindParamsApplier.this;
                Matrix matrix2 = keyguardSurfaceBehindParamsApplier3.matrix;
                matrix2.setTranslate(0.0f, f);
                Unit unit2 = Unit.INSTANCE;
                transaction.setMatrix(surfaceControl2, matrix2, keyguardSurfaceBehindParamsApplier3.tmpFloat);
                transaction.setAlpha(surfaceControl2, f2);
                transaction.apply();
            }
        });
    }

    public final void updateIsAnimatingSurface() {
        ((KeyguardSurfaceBehindRepositoryImpl) this.interactor.repository)._isAnimatingSurface.updateState(null, Boolean.valueOf(this.translateYSpring.mRunning || this.alphaAnimator.isRunning()));
    }
}
