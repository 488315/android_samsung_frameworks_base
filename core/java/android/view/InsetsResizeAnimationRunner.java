package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsState;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes4.dex */
public class InsetsResizeAnimationRunner implements InsetsAnimationControlRunner, InternalInsetsAnimationController, WindowInsetsAnimationControlListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "InsetsResizeAnimRunner";
    private final WindowInsetsAnimation mAnimation;
    private ValueAnimator mAnimator;
    private boolean mCancelled;
    private final InsetsAnimationControlCallbacks mController;
    private boolean mFinished;
    private final InsetsState mFromState;
    private InsetsSourceControl mImeSourceControl;
    private final InsetsState mToState;
    private final int mTypes;
    private final Matrix mTmpMatrix = new Matrix();
    private final float[] mTmpFloat9 = new float[9];

    public InsetsResizeAnimationRunner(Rect frame, InsetsState fromState, InsetsState toState, Interpolator interpolator, long duration, int types, InsetsAnimationControlCallbacks controller, InsetsController insetsController) {
        InsetsSourceControl control;
        this.mFromState = fromState;
        this.mToState = toState;
        this.mTypes = types;
        this.mController = controller;
        this.mAnimation = new WindowInsetsAnimation(types, interpolator, duration);
        this.mAnimation.setAlpha(1.0f);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && (this.mTypes & WindowInsets.Type.ime()) != 0 && (control = insetsController.getImeSourceConsumer().getControl()) != null) {
            this.mImeSourceControl = new InsetsSourceControl(control);
        }
        Insets fromInsets = fromState.calculateInsets(frame, types, false);
        Insets toInsets = toState.calculateInsets(frame, types, false);
        controller.startAnimation(this, this, types, this.mAnimation, new WindowInsetsAnimation.Bounds(Insets.min(fromInsets, toInsets), Insets.max(fromInsets, toInsets)));
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public WindowInsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return 3;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public ImeTracker.Token getStatsToken() {
        return null;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (this.mCancelled || this.mFinished) {
            return;
        }
        this.mCancelled = true;
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isCancelled() {
        return this.mCancelled;
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onReady(WindowInsetsAnimationController controller, int types) {
        if (this.mCancelled) {
            return;
        }
        this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator.setDuration(this.mAnimation.getDurationMillis());
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsResizeAnimationRunner$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                InsetsResizeAnimationRunner.this.lambda$onReady$0(valueAnimator);
            }
        });
        this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.view.InsetsResizeAnimationRunner.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                InsetsResizeAnimationRunner.this.mFinished = true;
                InsetsResizeAnimationRunner.this.mController.scheduleApplyChangeInsets(InsetsResizeAnimationRunner.this);
            }
        });
        this.mAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReady$0(ValueAnimator animation) {
        this.mAnimation.setFraction(animation.getAnimatedFraction());
        this.mController.scheduleApplyChangeInsets(this);
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(final InsetsState outState) {
        if (this.mCancelled) {
            return false;
        }
        final float fraction = this.mAnimation.getInterpolatedFraction();
        InsetsState.traverse(this.mFromState, this.mToState, new InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsResizeAnimationRunner.2
            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(InsetsSource fromSource, InsetsSource toSource) {
                Rect fromFrame = fromSource.getFrame();
                Rect toFrame = toSource.getFrame();
                Rect frame = new Rect((int) (fromFrame.left + (fraction * (toFrame.left - fromFrame.left))), (int) (fromFrame.top + (fraction * (toFrame.top - fromFrame.top))), (int) (fromFrame.right + (fraction * (toFrame.right - fromFrame.right))), (int) (fromFrame.bottom + (fraction * (toFrame.bottom - fromFrame.bottom))));
                InsetsSource source = new InsetsSource(fromSource.getId(), fromSource.getType());
                source.setFrame(frame);
                source.setVisible(toSource.isVisible());
                outState.addSource(source);
                if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && toSource.getType() == WindowInsets.Type.ime() && fromSource.getType() == toSource.getType() && InsetsResizeAnimationRunner.this.mImeSourceControl != null) {
                    SyncRtSurfaceTransactionApplier.SurfaceParams param = InsetsResizeAnimationRunner.this.getImeLeashSurfaceParam(fromSource, frame.top - fromFrame.top);
                    if (param != null) {
                        InsetsResizeAnimationRunner.this.mController.applySurfaceParams(param);
                    }
                    InsetsResizeAnimationRunner.this.mTmpMatrix.reset();
                }
            }
        });
        if (this.mFinished) {
            this.mController.notifyFinished(this, true);
        }
        return this.mFinished;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SyncRtSurfaceTransactionApplier.SurfaceParams getImeLeashSurfaceParam(InsetsSource fromImeSource, int offset) {
        SurfaceControl leash = this.mImeSourceControl.getLeash();
        this.mTmpMatrix.setTranslate(this.mImeSourceControl.getSurfacePosition().x, this.mImeSourceControl.getSurfacePosition().y + fromImeSource.getMinimizedInsetHint().top);
        this.mTmpMatrix.postTranslate(0.0f, offset);
        this.mTmpMatrix.getValues(this.mTmpFloat9);
        if (leash != null) {
            return new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash).withMatrix(this.mTmpMatrix).build();
        }
        return null;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1133871366145L, this.mCancelled);
        proto.write(1133871366146L, this.mFinished);
        proto.write(1138166333443L, "null");
        proto.write(1138166333444L, "null");
        proto.write(1108101562373L, this.mAnimation.getInterpolatedFraction());
        proto.write(1133871366150L, true);
        proto.write(1108101562375L, 1.0f);
        proto.write(1108101562376L, 1.0f);
        proto.end(token);
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getHiddenStateInsets() {
        return Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getShownStateInsets() {
        return Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getCurrentInsets() {
        return Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentFraction() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentAlpha() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(Insets insets, float alpha, float fraction) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public void finish(boolean shown) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isFinished() {
        return false;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int types) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> controls) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return false;
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean dispatched) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onFinished(WindowInsetsAnimationController controller) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onCancelled(WindowInsetsAnimationController controller) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateLayoutInsetsDuringAnimation(int layoutInsetsDuringAnimation) {
    }
}
