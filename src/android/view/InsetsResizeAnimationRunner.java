package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsAnimationControlRunner;
import android.view.InsetsState;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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

    @Override // android.view.WindowInsetsAnimationController
    public void finish(boolean z) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return 3;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentAlpha() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentFraction() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public long getDurationMs() {
        return 0L;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Interpolator getInsetsInterpolator() {
        return null;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public ImeTracker.Token getStatsToken() {
        return null;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return false;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isFinished() {
        return false;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int i) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(Insets insets, float f, float f2) {
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean z) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateLayoutInsetsDuringAnimation(int i) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> sparseArray) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public boolean willUpdateSurface() {
        return false;
    }

    public InsetsResizeAnimationRunner(Rect rect, InsetsState insetsState, InsetsState insetsState2, Interpolator interpolator, long j, int i, InsetsAnimationControlCallbacks insetsAnimationControlCallbacks, InsetsController insetsController) {
        InsetsSourceControl control;
        this.mFromState = insetsState;
        this.mToState = insetsState2;
        this.mTypes = i;
        this.mController = insetsAnimationControlCallbacks;
        WindowInsetsAnimation windowInsetsAnimation = new WindowInsetsAnimation(i, interpolator, j);
        this.mAnimation = windowInsetsAnimation;
        windowInsetsAnimation.setAlpha(1.0f);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && (WindowInsets.Type.ime() & i) != 0 && (control = insetsController.getImeSourceConsumer().getControl()) != null) {
            this.mImeSourceControl = new InsetsSourceControl(control);
        }
        Insets insetsCalculateInsets = insetsState.calculateInsets(rect, i, false);
        Insets insetsCalculateInsets2 = insetsState2.calculateInsets(rect, i, false);
        insetsAnimationControlCallbacks.startAnimation(this, this, i, windowInsetsAnimation, new WindowInsetsAnimation.Bounds(Insets.min(insetsCalculateInsets, insetsCalculateInsets2), Insets.max(insetsCalculateInsets, insetsCalculateInsets2)));
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
    public InsetsAnimationControlRunner.SurfaceParamsApplier getSurfaceParamsApplier() {
        return InsetsAnimationControlRunner.SurfaceParamsApplier.DEFAULT;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (this.mCancelled || this.mFinished) {
            return;
        }
        this.mCancelled = true;
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isCancelled() {
        return this.mCancelled;
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onReady(WindowInsetsAnimationController windowInsetsAnimationController, int i) {
        if (this.mCancelled) {
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(this.mAnimation.getDurationMillis());
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsResizeAnimationRunner$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$onReady$0(valueAnimator);
            }
        });
        this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.view.InsetsResizeAnimationRunner.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                InsetsResizeAnimationRunner.this.mFinished = true;
                InsetsResizeAnimationRunner.this.mController.scheduleApplyChangeInsets(InsetsResizeAnimationRunner.this);
            }
        });
        this.mAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReady$0(ValueAnimator valueAnimator) {
        this.mAnimation.setFraction(valueAnimator.getAnimatedFraction());
        this.mController.scheduleApplyChangeInsets(this);
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(final InsetsState insetsState) {
        if (this.mCancelled) {
            return false;
        }
        final float interpolatedFraction = this.mAnimation.getInterpolatedFraction();
        InsetsState.traverse(this.mFromState, this.mToState, new InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsResizeAnimationRunner.2
            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(InsetsSource insetsSource, InsetsSource insetsSource2) {
                Rect frame = insetsSource.getFrame();
                Rect frame2 = insetsSource2.getFrame();
                Rect rect = new Rect((int) (frame.left + (interpolatedFraction * (frame2.left - frame.left))), (int) (frame.top + (interpolatedFraction * (frame2.top - frame.top))), (int) (frame.right + (interpolatedFraction * (frame2.right - frame.right))), (int) (frame.bottom + (interpolatedFraction * (frame2.bottom - frame.bottom))));
                InsetsSource insetsSource3 = new InsetsSource(insetsSource.getId(), insetsSource.getType());
                insetsSource3.setFrame(rect);
                insetsSource3.setVisible(insetsSource2.isVisible());
                insetsState.addSource(insetsSource3);
                if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && insetsSource2.getType() == WindowInsets.Type.ime() && insetsSource.getType() == insetsSource2.getType() && InsetsResizeAnimationRunner.this.mImeSourceControl != null) {
                    SyncRtSurfaceTransactionApplier.SurfaceParams imeLeashSurfaceParam = InsetsResizeAnimationRunner.this.getImeLeashSurfaceParam(insetsSource, rect.top - frame.top);
                    if (imeLeashSurfaceParam != null && (InsetsResizeAnimationRunner.this.mController instanceof InsetsAnimationControlRunner.SurfaceParamsApplier)) {
                        ((InsetsAnimationControlRunner.SurfaceParamsApplier) InsetsResizeAnimationRunner.this.mController).applySurfaceParams(imeLeashSurfaceParam);
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
    public SyncRtSurfaceTransactionApplier.SurfaceParams getImeLeashSurfaceParam(InsetsSource insetsSource, int i) {
        SurfaceControl leash = this.mImeSourceControl.getLeash();
        this.mTmpMatrix.setTranslate(this.mImeSourceControl.getSurfacePosition().x, this.mImeSourceControl.getSurfacePosition().y + insetsSource.getMinimizedInsetHint().top);
        this.mTmpMatrix.postTranslate(0.0f, i);
        this.mTmpMatrix.getValues(this.mTmpFloat9);
        if (leash != null) {
            return new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash).withMatrix(this.mTmpMatrix).build();
        }
        return null;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mCancelled);
        protoOutputStream.write(1133871366146L, this.mFinished);
        protoOutputStream.write(1138166333443L, PerfettoProtoLogImpl.NULL_STRING);
        protoOutputStream.write(1138166333444L, PerfettoProtoLogImpl.NULL_STRING);
        protoOutputStream.write(1108101562373L, this.mAnimation.getInterpolatedFraction());
        protoOutputStream.write(1133871366150L, true);
        protoOutputStream.write(1108101562375L, 1.0f);
        protoOutputStream.write(1108101562376L, 1.0f);
        protoOutputStream.end(jStart);
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

    @Override // android.view.InsetsAnimationControlRunner
    public boolean isCancelRequested() {
        return this.mCancelled;
    }
}
