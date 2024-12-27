package android.view;

import android.content.res.CompatibilityInfo;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoOutputStream;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class InsetsAnimationControlImpl
        implements InternalInsetsAnimationController, InsetsAnimationControlRunner {
    private static final String TAG = "InsetsAnimationCtrlImpl";
    private final WindowInsetsAnimation mAnimation;
    private final int mAnimationType;
    private boolean mCancelled;
    private final InsetsAnimationControlCallbacks mController;
    private int mControllingTypes;
    private final SparseArray<InsetsSourceControl> mControls;
    private Insets mCurrentInsets;
    private boolean mFinished;
    private final boolean mHasZeroInsetsIme;
    private final Insets mHiddenInsets;
    private final InsetsState mInitialInsetsState;
    private int mLayoutInsetsDuringAnimation;
    private final WindowInsetsAnimationControlListener mListener;
    private float mPendingFraction;
    private Insets mPendingInsets;
    private Boolean mPerceptible;
    private boolean mReadyDispatched;
    private final Insets mShownInsets;
    private boolean mShownOnFinish;
    private final ImeTracker.Token mStatsToken;
    private final CompatibilityInfo.Translator mTranslator;
    private final int mTypes;
    private final Rect mTmpFrame = new Rect();
    private final SparseSetArray<InsetsSourceControl> mSideControlsMap = new SparseSetArray<>();
    private final Matrix mTmpMatrix = new Matrix();
    private float mCurrentAlpha = 1.0f;
    private float mPendingAlpha = 1.0f;

    public InsetsAnimationControlImpl(
            SparseArray<InsetsSourceControl> controls,
            Rect frame,
            InsetsState state,
            WindowInsetsAnimationControlListener listener,
            int types,
            InsetsAnimationControlCallbacks controller,
            long durationMs,
            Interpolator interpolator,
            int animationType,
            int layoutInsetsDuringAnimation,
            CompatibilityInfo.Translator translator,
            ImeTracker.Token statsToken) {
        this.mControls = controls;
        this.mListener = listener;
        this.mTypes = types;
        this.mControllingTypes = types;
        this.mController = controller;
        this.mInitialInsetsState = new InsetsState(state, true);
        if (frame == null) {
            this.mCurrentInsets = calculateInsets(this.mInitialInsetsState, controls, true);
            this.mHiddenInsets = calculateInsets(null, controls, false);
            this.mShownInsets = calculateInsets(null, controls, true);
            this.mHasZeroInsetsIme =
                    this.mShownInsets.bottom == 0 && controlsType(WindowInsets.Type.ime());
            buildSideControlsMap(this.mSideControlsMap, controls);
        } else {
            SparseIntArray idSideMap = new SparseIntArray();
            this.mCurrentInsets = getInsetsFromState(this.mInitialInsetsState, frame, null);
            this.mHiddenInsets =
                    calculateInsets(this.mInitialInsetsState, frame, controls, false, null);
            this.mShownInsets =
                    calculateInsets(this.mInitialInsetsState, frame, controls, true, idSideMap);
            this.mHasZeroInsetsIme =
                    this.mShownInsets.bottom == 0 && controlsType(WindowInsets.Type.ime());
            if (this.mHasZeroInsetsIme) {
                idSideMap.put(InsetsSource.ID_IME, 4);
            }
            buildSideControlsMap(idSideMap, this.mSideControlsMap, controls);
        }
        this.mPendingInsets = this.mCurrentInsets;
        this.mAnimation = new WindowInsetsAnimation(this.mTypes, interpolator, durationMs);
        this.mAnimation.setAlpha(getCurrentAlpha());
        this.mAnimationType = animationType;
        this.mLayoutInsetsDuringAnimation = layoutInsetsDuringAnimation;
        this.mTranslator = translator;
        this.mStatsToken = statsToken;
        if (ImeTracker.DEBUG_IME_VISIBILITY && (WindowInsets.Type.ime() & types) != 0) {
            EventLog.writeEvent(
                    EventLogTags.IMF_IME_ANIM_START,
                    this.mStatsToken != null ? this.mStatsToken.getTag() : ImeTracker.TOKEN_NONE,
                    Integer.valueOf(this.mAnimationType),
                    Float.valueOf(this.mCurrentAlpha),
                    "Current:" + this.mCurrentInsets,
                    "Shown:" + this.mShownInsets,
                    "Hidden:" + this.mHiddenInsets);
        }
        this.mController.startAnimation(
                this,
                listener,
                types,
                this.mAnimation,
                new WindowInsetsAnimation.Bounds(this.mHiddenInsets, this.mShownInsets));
    }

    private boolean calculatePerceptible(Insets currentInsets, float currentAlpha) {
        return currentInsets.left * 100 >= (this.mShownInsets.left - this.mHiddenInsets.left) * 5
                && currentInsets.top * 100 >= (this.mShownInsets.top - this.mHiddenInsets.top) * 5
                && currentInsets.right * 100
                        >= (this.mShownInsets.right - this.mHiddenInsets.right) * 5
                && currentInsets.bottom * 100
                        >= (this.mShownInsets.bottom - this.mHiddenInsets.bottom) * 5
                && currentAlpha >= 0.5f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return this.mHasZeroInsetsIme;
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean dispatched) {
        this.mReadyDispatched = dispatched;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getHiddenStateInsets() {
        return this.mHiddenInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getShownStateInsets() {
        return this.mShownInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Insets getCurrentInsets() {
        return this.mCurrentInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentAlpha() {
        return this.mCurrentAlpha;
    }

    @Override // android.view.WindowInsetsAnimationController,
              // android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mControllingTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int types) {
        this.mControllingTypes &= ~types;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> controls) {
        for (int i = controls.size() - 1; i >= 0; i--) {
            InsetsSourceControl control = controls.valueAt(i);
            InsetsSourceControl c = this.mControls.get(control.getId());
            if (c != null) {
                Point position = control.getSurfacePosition();
                c.setSurfacePosition(position.x, position.y);
            }
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return this.mAnimationType;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public ImeTracker.Token getStatsToken() {
        return this.mStatsToken;
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(Insets insets, float alpha, float fraction) {
        setInsetsAndAlpha(insets, alpha, fraction, false);
    }

    private void setInsetsAndAlpha(
            Insets insets, float alpha, float fraction, boolean allowWhenFinished) {
        if (!allowWhenFinished && this.mFinished) {
            throw new IllegalStateException(
                    "Can't change insets on an animation that is finished.");
        }
        if (this.mCancelled) {
            throw new IllegalStateException(
                    "Can't change insets on an animation that is cancelled.");
        }
        this.mPendingFraction = sanitize(fraction);
        this.mPendingInsets = sanitize(insets);
        this.mPendingAlpha = sanitize(alpha);
        this.mController.scheduleApplyChangeInsets(this);
        boolean perceptible = calculatePerceptible(this.mPendingInsets, this.mPendingAlpha);
        if (this.mPerceptible == null || perceptible != this.mPerceptible.booleanValue()) {
            this.mController.reportPerceptible(this.mTypes, perceptible);
            this.mPerceptible = Boolean.valueOf(perceptible);
        }
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(InsetsState outState) {
        if (this.mCancelled) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, "applyChangeInsets canceled");
                return false;
            }
            return false;
        }
        Insets offset = Insets.subtract(this.mShownInsets, this.mPendingInsets);
        ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> params = new ArrayList<>();
        updateLeashesForSide(1, offset.left, params, outState, this.mPendingAlpha);
        updateLeashesForSide(2, offset.top, params, outState, this.mPendingAlpha);
        updateLeashesForSide(3, offset.right, params, outState, this.mPendingAlpha);
        updateLeashesForSide(4, offset.bottom, params, outState, this.mPendingAlpha);
        this.mController.applySurfaceParams(
                (SyncRtSurfaceTransactionApplier.SurfaceParams[])
                        params.toArray(
                                new SyncRtSurfaceTransactionApplier.SurfaceParams[params.size()]));
        this.mCurrentInsets = this.mPendingInsets;
        this.mAnimation.setFraction(this.mPendingFraction);
        this.mCurrentAlpha = this.mPendingAlpha;
        this.mAnimation.setAlpha(this.mPendingAlpha);
        if (this.mFinished) {
            if (InsetsController.DEBUG) {
                Log.d(
                        TAG,
                        String.format(
                                "notifyFinished shown: %s, currentAlpha: %f, currentInsets: %s",
                                Boolean.valueOf(this.mShownOnFinish),
                                Float.valueOf(this.mCurrentAlpha),
                                this.mCurrentInsets));
            }
            this.mController.notifyFinished(this, this.mShownOnFinish);
            releaseLeashes();
            if (InsetsController.DEBUG) {
                Log.d(TAG, "Animation finished abruptly.");
            }
        }
        return this.mFinished;
    }

    private void releaseLeashes() {
        for (int i = this.mControls.size() - 1; i >= 0; i--) {
            InsetsSourceControl c = this.mControls.valueAt(i);
            if (c != null) {
                final InsetsAnimationControlCallbacks insetsAnimationControlCallbacks =
                        this.mController;
                Objects.requireNonNull(insetsAnimationControlCallbacks);
                c.release(
                        new Consumer() { // from class:
                                         // android.view.InsetsAnimationControlImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                InsetsAnimationControlCallbacks.this.releaseSurfaceControlFromRt(
                                        (SurfaceControl) obj);
                            }
                        });
            }
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public void finish(boolean z) {
        if (this.mCancelled || this.mFinished) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, "Animation already canceled or finished, not notifying.");
                return;
            }
            return;
        }
        this.mShownOnFinish = z;
        this.mFinished = true;
        Insets insets = z ? this.mShownInsets : this.mHiddenInsets;
        setInsetsAndAlpha(insets, this.mPendingAlpha, 1.0f, true);
        if (InsetsController.DEBUG) {
            Log.d(TAG, "notify control request finished for types: " + this.mTypes);
        }
        this.mListener.onFinished(this);
        if (ImeTracker.DEBUG_IME_VISIBILITY && (this.mTypes & WindowInsets.Type.ime()) != 0) {
            EventLog.writeEvent(
                    EventLogTags.IMF_IME_ANIM_FINISH,
                    this.mStatsToken != null ? this.mStatsToken.getTag() : ImeTracker.TOKEN_NONE,
                    Integer.valueOf(this.mAnimationType),
                    Float.valueOf(this.mCurrentAlpha),
                    Integer.valueOf(z ? 1 : 0),
                    Objects.toString(insets));
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentFraction() {
        return this.mAnimation.getFraction();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (this.mFinished) {
            return;
        }
        this.mPendingInsets =
                this.mLayoutInsetsDuringAnimation == 0 ? this.mShownInsets : this.mHiddenInsets;
        this.mPendingAlpha = 1.0f;
        this.mPendingFraction = 1.0f;
        applyChangeInsets(null);
        this.mCancelled = true;
        this.mListener.onCancelled(this.mReadyDispatched ? this : null);
        if (InsetsController.DEBUG) {
            Log.d(TAG, "notify Control request cancelled for types: " + this.mTypes);
        }
        if (ImeTracker.DEBUG_IME_VISIBILITY && (this.mTypes & WindowInsets.Type.ime()) != 0) {
            EventLog.writeEvent(
                    EventLogTags.IMF_IME_ANIM_CANCEL,
                    this.mStatsToken != null ? this.mStatsToken.getTag() : ImeTracker.TOKEN_NONE,
                    Integer.valueOf(this.mAnimationType),
                    Objects.toString(this.mPendingInsets));
        }
        releaseLeashes();
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isFinished() {
        return this.mFinished;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isCancelled() {
        return this.mCancelled;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public WindowInsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateLayoutInsetsDuringAnimation(int layoutInsetsDuringAnimation) {
        this.mLayoutInsetsDuringAnimation = layoutInsetsDuringAnimation;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1133871366145L, this.mCancelled);
        proto.write(1133871366146L, this.mFinished);
        proto.write(1138166333443L, Objects.toString(this.mTmpMatrix));
        proto.write(1138166333444L, Objects.toString(this.mPendingInsets));
        proto.write(1108101562373L, this.mPendingFraction);
        proto.write(1133871366150L, this.mShownOnFinish);
        proto.write(1108101562375L, this.mCurrentAlpha);
        proto.write(1108101562376L, this.mPendingAlpha);
        proto.end(token);
    }

    SparseArray<InsetsSourceControl> getControls() {
        return this.mControls;
    }

    private Insets getInsetsFromState(InsetsState state, Rect frame, SparseIntArray idSideMap) {
        return state.calculateInsets(frame, null, false, 16, 0, 0, 2, 0, idSideMap)
                .getInsets(this.mTypes);
    }

    private Insets calculateInsets(
            InsetsState state,
            Rect frame,
            SparseArray<InsetsSourceControl> controls,
            boolean shown,
            SparseIntArray idSideMap) {
        for (int i = controls.size() - 1; i >= 0; i--) {
            InsetsSourceControl control = controls.valueAt(i);
            if (control != null) {
                state.setSourceVisible(control.getId(), shown);
            }
        }
        return getInsetsFromState(state, frame, idSideMap);
    }

    private Insets calculateInsets(
            InsetsState state, SparseArray<InsetsSourceControl> controls, boolean shownOrCurrent) {
        Insets insets = Insets.NONE;
        if (!shownOrCurrent) {
            return insets;
        }
        for (int i = controls.size() - 1; i >= 0; i--) {
            InsetsSourceControl control = controls.valueAt(i);
            if (control != null
                    && (state == null
                            || state.isSourceOrDefaultVisible(
                                    control.getId(), control.getType()))) {
                insets = Insets.max(insets, control.getInsetsHint());
            }
        }
        return insets;
    }

    private Insets sanitize(Insets insets) {
        if (insets == null) {
            insets = getCurrentInsets();
        }
        if (hasZeroInsetsIme()) {
            return insets;
        }
        return Insets.max(Insets.min(insets, this.mShownInsets), this.mHiddenInsets);
    }

    private static float sanitize(float alpha) {
        if (alpha >= 1.0f) {
            return 1.0f;
        }
        if (alpha <= 0.0f) {
            return 0.0f;
        }
        return alpha;
    }

    private void updateLeashesForSide(
            int side,
            int offset,
            ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> surfaceParams,
            InsetsState outState,
            float alpha) {
        ArraySet<InsetsSourceControl> controls = this.mSideControlsMap.get(side);
        if (controls == null) {
            return;
        }
        for (int i = controls.size() - 1; i >= 0; i--) {
            InsetsSourceControl control = controls.valueAt(i);
            InsetsSource source = this.mInitialInsetsState.peekSource(control.getId());
            SurfaceControl leash = control.getLeash();
            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM
                    && source != null
                    && control.getType() == WindowInsets.Type.ime()) {
                this.mTmpMatrix.setTranslate(
                        control.getSurfacePosition().x,
                        control.getSurfacePosition().y + source.getMinimizedInsetHint().top);
            } else {
                this.mTmpMatrix.setTranslate(
                        control.getSurfacePosition().x, control.getSurfacePosition().y);
            }
            if (source != null) {
                this.mTmpFrame.set(source.getFrame());
            }
            addTranslationToMatrix(side, offset, this.mTmpMatrix, this.mTmpFrame);
            boolean z = false;
            if (this.mPendingFraction == 0.0f) {
                if (this.mAnimationType != 0) {
                    z = true;
                }
            } else if (!this.mFinished || this.mShownOnFinish) {
                z = true;
            }
            boolean visible = z;
            if (outState != null && source != null) {
                outState.addSource(
                        new InsetsSource(source).setVisible(visible).setFrame(this.mTmpFrame));
            }
            if (leash != null) {
                SyncRtSurfaceTransactionApplier.SurfaceParams params =
                        new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash)
                                .withAlpha(alpha)
                                .withMatrix(this.mTmpMatrix)
                                .withVisibility(visible)
                                .build();
                surfaceParams.add(params);
            }
        }
    }

    private void addTranslationToMatrix(int side, int offset, Matrix m, Rect frame) {
        float surfaceOffset =
                this.mTranslator != null
                        ? this.mTranslator.translateLengthInAppWindowToScreen(offset)
                        : offset;
        switch (side) {
            case 1:
                m.postTranslate(-surfaceOffset, 0.0f);
                frame.offset(-offset, 0);
                break;
            case 2:
                m.postTranslate(0.0f, -surfaceOffset);
                frame.offset(0, -offset);
                break;
            case 3:
                m.postTranslate(surfaceOffset, 0.0f);
                frame.offset(offset, 0);
                break;
            case 4:
                m.postTranslate(0.0f, surfaceOffset);
                frame.offset(0, offset);
                break;
        }
    }

    private static void buildSideControlsMap(
            SparseIntArray idSideMap,
            SparseSetArray<InsetsSourceControl> sideControlsMap,
            SparseArray<InsetsSourceControl> controls) {
        for (int i = idSideMap.size() - 1; i >= 0; i--) {
            int type = idSideMap.keyAt(i);
            int side = idSideMap.valueAt(i);
            InsetsSourceControl control = controls.get(type);
            if (control != null) {
                sideControlsMap.add(side, control);
            }
        }
    }

    private static void buildSideControlsMap(
            SparseSetArray<InsetsSourceControl> sideControlsMap,
            SparseArray<InsetsSourceControl> controls) {
        for (int i = controls.size() - 1; i >= 0; i--) {
            InsetsSourceControl control = controls.valueAt(i);
            if (control != null) {
                int side = InsetsSource.getInsetSide(control.getInsetsHint());
                if (side == 0 && control.getType() == WindowInsets.Type.ime()) {
                    side = 4;
                }
                sideControlsMap.add(side, control);
            }
        }
    }
}
