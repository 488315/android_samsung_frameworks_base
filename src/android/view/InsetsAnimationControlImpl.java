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
import android.view.InsetsAnimationControlRunner;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class InsetsAnimationControlImpl implements InternalInsetsAnimationController, InsetsAnimationControlRunner {
    private static final String TAG = "InsetsAnimationCtrlImpl";
    private final WindowInsetsAnimation mAnimation;
    private final int mAnimationType;
    private boolean mCancelled;
    private boolean mCancelling;
    private final InsetsAnimationControlCallbacks mController;
    private int mControllingTypes;
    private final SparseArray<InsetsSourceControl> mControls;
    private float mCurrentAlpha;
    private Insets mCurrentInsets;
    private final long mDurationMs;
    private boolean mFinished;
    private final boolean mHasZeroInsetsIme;
    private final Insets mHiddenInsets;
    private final InsetsState mInitialInsetsState;
    private final Interpolator mInterpolator;
    private int mLayoutInsetsDuringAnimation;
    private final WindowInsetsAnimationControlListener mListener;
    private float mPendingAlpha;
    private float mPendingFraction;
    private Insets mPendingInsets;
    private Boolean mPerceptible;
    private boolean mReadyDispatched;
    private final Insets mShownInsets;
    private boolean mShownOnFinish;
    private final SparseSetArray<InsetsSourceControl> mSideControlsMap;
    private final ImeTracker.Token mStatsToken;
    private final InsetsAnimationControlRunner.SurfaceParamsApplier mSurfaceParamsApplier;
    private final Rect mTmpFrame = new Rect();
    private final Matrix mTmpMatrix;
    private final CompatibilityInfo.Translator mTranslator;
    private final int mTypes;

    private static float sanitize(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        return f;
    }

    public InsetsAnimationControlImpl(SparseArray<InsetsSourceControl> sparseArray, Rect rect, InsetsState insetsState, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int i, InsetsAnimationControlCallbacks insetsAnimationControlCallbacks, InsetsAnimationControlRunner.SurfaceParamsApplier surfaceParamsApplier, InsetsAnimationSpec insetsAnimationSpec, int i2, int i3, CompatibilityInfo.Translator translator, ImeTracker.Token token) {
        SparseSetArray<InsetsSourceControl> sparseSetArray = new SparseSetArray<>();
        this.mSideControlsMap = sparseSetArray;
        this.mTmpMatrix = new Matrix();
        this.mCurrentAlpha = 1.0f;
        this.mPendingAlpha = 1.0f;
        this.mControls = sparseArray;
        this.mListener = windowInsetsAnimationControlListener;
        this.mTypes = i;
        this.mControllingTypes = i;
        this.mController = insetsAnimationControlCallbacks;
        this.mSurfaceParamsApplier = surfaceParamsApplier;
        InsetsState insetsState2 = new InsetsState(insetsState, true);
        this.mInitialInsetsState = insetsState2;
        if (rect != null) {
            SparseIntArray sparseIntArray = new SparseIntArray();
            this.mCurrentInsets = getInsetsFromState(insetsState2, rect, null);
            this.mHiddenInsets = calculateInsets(insetsState2, rect, sparseArray, false, null);
            Insets calculateInsets = calculateInsets(insetsState2, rect, sparseArray, true, sparseIntArray);
            this.mShownInsets = calculateInsets;
            boolean z = calculateInsets.bottom == 0 && controlsType(WindowInsets.Type.ime());
            this.mHasZeroInsetsIme = z;
            if (z) {
                sparseIntArray.put(InsetsSource.ID_IME, 4);
            }
            buildSideControlsMap(sparseIntArray, sparseSetArray, sparseArray);
        } else {
            this.mCurrentInsets = calculateInsets(insetsState2, sparseArray, true);
            this.mHiddenInsets = calculateInsets(null, sparseArray, false);
            Insets calculateInsets2 = calculateInsets(null, sparseArray, true);
            this.mShownInsets = calculateInsets2;
            this.mHasZeroInsetsIme = calculateInsets2.bottom == 0 && controlsType(WindowInsets.Type.ime());
            buildSideControlsMap(sparseSetArray, sparseArray);
        }
        this.mPendingInsets = this.mCurrentInsets;
        long durationMs = insetsAnimationSpec.getDurationMs(this.mHasZeroInsetsIme);
        this.mDurationMs = durationMs;
        Interpolator insetsInterpolator = insetsAnimationSpec.getInsetsInterpolator(this.mHasZeroInsetsIme);
        this.mInterpolator = insetsInterpolator;
        WindowInsetsAnimation windowInsetsAnimation = new WindowInsetsAnimation(i, insetsInterpolator, durationMs);
        this.mAnimation = windowInsetsAnimation;
        windowInsetsAnimation.setAlpha(getCurrentAlpha());
        this.mAnimationType = i2;
        this.mLayoutInsetsDuringAnimation = i3;
        this.mTranslator = translator;
        this.mStatsToken = token;
        if (ImeTracker.DEBUG_IME_VISIBILITY && (WindowInsets.Type.ime() & i) != 0) {
            String tag = token != null ? token.getTag() : ImeTracker.TOKEN_NONE;
            EventLog.writeEvent(EventLogTags.IMF_IME_ANIM_START, tag, Integer.valueOf(i2), Float.valueOf(this.mCurrentAlpha), "Current:" + this.mCurrentInsets, "Shown:" + this.mShownInsets, "Hidden:" + this.mHiddenInsets);
        }
        insetsAnimationControlCallbacks.startAnimation(this, windowInsetsAnimationControlListener, i, windowInsetsAnimation, new WindowInsetsAnimation.Bounds(this.mHiddenInsets, this.mShownInsets));
    }

    private boolean calculatePerceptible(Insets insets, float f) {
        return insets.left * 100 >= (this.mShownInsets.left - this.mHiddenInsets.left) * 5 && insets.top * 100 >= (this.mShownInsets.top - this.mHiddenInsets.top) * 5 && insets.right * 100 >= (this.mShownInsets.right - this.mHiddenInsets.right) * 5 && insets.bottom * 100 >= (this.mShownInsets.bottom - this.mHiddenInsets.bottom) * 5 && f >= 0.5f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return this.mHasZeroInsetsIme;
    }

    @Override // android.view.WindowInsetsAnimationController
    public long getDurationMs() {
        return this.mDurationMs;
    }

    @Override // android.view.WindowInsetsAnimationController
    public Interpolator getInsetsInterpolator() {
        return this.mInterpolator;
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean z) {
        this.mReadyDispatched = z;
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

    @Override // android.view.WindowInsetsAnimationController, android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mControllingTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int i) {
        this.mControllingTypes = (~i) & this.mControllingTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = sparseArray.valueAt(size);
            InsetsSourceControl insetsSourceControl = this.mControls.get(valueAt.getId());
            if (insetsSourceControl != null) {
                Point surfacePosition = valueAt.getSurfacePosition();
                insetsSourceControl.setSurfacePosition(surfacePosition.x, surfacePosition.y);
            }
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public boolean willUpdateSurface() {
        return (this.mFinished || this.mCancelled) ? false : true;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return this.mAnimationType;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public InsetsAnimationControlRunner.SurfaceParamsApplier getSurfaceParamsApplier() {
        return this.mSurfaceParamsApplier;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public ImeTracker.Token getStatsToken() {
        return this.mStatsToken;
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(Insets insets, float f, float f2) {
        setInsetsAndAlpha(insets, f, f2, false);
    }

    private void setInsetsAndAlpha(Insets insets, float f, float f2, boolean z) {
        if (!z && this.mFinished) {
            throw new IllegalStateException("Can't change insets on an animation that is finished.");
        }
        if (this.mCancelled) {
            throw new IllegalStateException("Can't change insets on an animation that is cancelled.");
        }
        this.mPendingFraction = sanitize(f2);
        this.mPendingInsets = sanitize(insets);
        this.mPendingAlpha = sanitize(f);
        this.mController.scheduleApplyChangeInsets(this);
        boolean calculatePerceptible = calculatePerceptible(this.mPendingInsets, this.mPendingAlpha);
        Boolean bool = this.mPerceptible;
        if (bool == null || calculatePerceptible != bool.booleanValue()) {
            this.mController.reportPerceptible(this.mTypes, calculatePerceptible);
            this.mPerceptible = Boolean.valueOf(calculatePerceptible);
        }
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(InsetsState insetsState) {
        if (this.mCancelled) {
            if (!InsetsController.DEBUG) {
                return false;
            }
            Log.d(TAG, "applyChangeInsets canceled");
            return false;
        }
        Insets subtract = Insets.subtract(this.mShownInsets, this.mPendingInsets);
        ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList = new ArrayList<>();
        updateLeashesForSide(1, subtract.left, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(2, subtract.top, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(3, subtract.right, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(4, subtract.bottom, arrayList, insetsState, this.mPendingAlpha);
        this.mSurfaceParamsApplier.applySurfaceParams((SyncRtSurfaceTransactionApplier.SurfaceParams[]) arrayList.toArray(new SyncRtSurfaceTransactionApplier.SurfaceParams[arrayList.size()]));
        this.mCurrentInsets = this.mPendingInsets;
        this.mAnimation.setFraction(this.mPendingFraction);
        float f = this.mPendingAlpha;
        this.mCurrentAlpha = f;
        this.mAnimation.setAlpha(f);
        if (this.mFinished) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, String.format("notifyFinished shown: %s, currentAlpha: %f, currentInsets: %s", Boolean.valueOf(this.mShownOnFinish), Float.valueOf(this.mCurrentAlpha), this.mCurrentInsets));
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
        for (int size = this.mControls.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = this.mControls.valueAt(size);
            if (valueAt != null) {
                final InsetsAnimationControlCallbacks insetsAnimationControlCallbacks = this.mController;
                Objects.requireNonNull(insetsAnimationControlCallbacks);
                valueAt.release(new Consumer() { // from class: android.view.InsetsAnimationControlImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        InsetsAnimationControlCallbacks.this.releaseSurfaceControlFromRt((SurfaceControl) obj);
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
        if (!ImeTracker.DEBUG_IME_VISIBILITY || (this.mTypes & WindowInsets.Type.ime()) == 0) {
            return;
        }
        ImeTracker.Token token = this.mStatsToken;
        EventLog.writeEvent(EventLogTags.IMF_IME_ANIM_FINISH, token != null ? token.getTag() : ImeTracker.TOKEN_NONE, Integer.valueOf(this.mAnimationType), Float.valueOf(this.mCurrentAlpha), Integer.valueOf(z ? 1 : 0), Objects.toString(insets));
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
        this.mPendingInsets = this.mLayoutInsetsDuringAnimation == 0 ? this.mShownInsets : this.mHiddenInsets;
        this.mPendingAlpha = 1.0f;
        this.mCancelling = true;
        applyChangeInsets(null);
        this.mCancelled = true;
        this.mListener.onCancelled(this.mReadyDispatched ? this : null);
        if (InsetsController.DEBUG) {
            Log.d(TAG, "notify Control request cancelled for types: " + this.mTypes);
        }
        if (ImeTracker.DEBUG_IME_VISIBILITY && (this.mTypes & WindowInsets.Type.ime()) != 0) {
            ImeTracker.Token token = this.mStatsToken;
            EventLog.writeEvent(EventLogTags.IMF_IME_ANIM_CANCEL, token != null ? token.getTag() : ImeTracker.TOKEN_NONE, Integer.valueOf(this.mAnimationType), Objects.toString(this.mPendingInsets));
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
    public void updateLayoutInsetsDuringAnimation(int i) {
        this.mLayoutInsetsDuringAnimation = i;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mCancelled);
        protoOutputStream.write(1133871366146L, this.mFinished);
        protoOutputStream.write(1138166333443L, Objects.toString(this.mTmpMatrix));
        protoOutputStream.write(1138166333444L, Objects.toString(this.mPendingInsets));
        protoOutputStream.write(1108101562373L, this.mPendingFraction);
        protoOutputStream.write(1133871366150L, this.mShownOnFinish);
        protoOutputStream.write(1108101562375L, this.mCurrentAlpha);
        protoOutputStream.write(1108101562376L, this.mPendingAlpha);
        protoOutputStream.end(start);
    }

    SparseArray<InsetsSourceControl> getControls() {
        return this.mControls;
    }

    private Insets getInsetsFromState(InsetsState insetsState, Rect rect, SparseIntArray sparseIntArray) {
        return insetsState.calculateInsets(rect, null, false, 16, 0, 0, 2, 0, sparseIntArray).getInsets(this.mTypes);
    }

    private Insets calculateInsets(InsetsState insetsState, Rect rect, SparseArray<InsetsSourceControl> sparseArray, boolean z, SparseIntArray sparseIntArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null) {
                insetsState.setSourceVisible(valueAt.getId(), z);
            }
        }
        return getInsetsFromState(insetsState, rect, sparseIntArray);
    }

    private Insets calculateInsets(InsetsState insetsState, SparseArray<InsetsSourceControl> sparseArray, boolean z) {
        Insets insets = Insets.NONE;
        if (!z) {
            return insets;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null && (insetsState == null || insetsState.isSourceOrDefaultVisible(valueAt.getId(), valueAt.getType()))) {
                insets = Insets.max(insets, valueAt.getInsetsHint());
            }
        }
        return insets;
    }

    private Insets sanitize(Insets insets) {
        if (insets == null) {
            insets = getCurrentInsets();
        }
        return hasZeroInsetsIme() ? insets : Insets.max(Insets.min(insets, this.mShownInsets), this.mHiddenInsets);
    }

    private void updateLeashesForSide(int i, int i2, ArrayList<SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList, InsetsState insetsState, float f) {
        boolean z;
        ArraySet<InsetsSourceControl> arraySet = this.mSideControlsMap.get(i);
        if (arraySet == null) {
            return;
        }
        if (this.mFinished) {
            z = this.mShownOnFinish;
        } else {
            z = !this.mCancelling ? this.mAnimationType == 0 && this.mPendingFraction == 0.0f : this.mLayoutInsetsDuringAnimation != 0;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = arraySet.valueAt(size);
            InsetsSource peekSource = this.mInitialInsetsState.peekSource(valueAt.getId());
            SurfaceControl leash = valueAt.getLeash();
            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && peekSource != null && valueAt.getType() == WindowInsets.Type.ime()) {
                this.mTmpMatrix.setTranslate(valueAt.getSurfacePosition().x, valueAt.getSurfacePosition().y + peekSource.getMinimizedInsetHint().top);
            } else {
                this.mTmpMatrix.setTranslate(valueAt.getSurfacePosition().x, valueAt.getSurfacePosition().y);
            }
            if (peekSource != null) {
                this.mTmpFrame.set(peekSource.getFrame());
            }
            addTranslationToMatrix(i, i2, this.mTmpMatrix, this.mTmpFrame);
            if (insetsState != null && peekSource != null) {
                insetsState.addSource(new InsetsSource(peekSource).setVisible(z).setFrame(this.mTmpFrame));
            }
            if (leash != null) {
                arrayList.add(new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash).withAlpha(f).withMatrix(this.mTmpMatrix).withVisibility(z).build());
            }
        }
    }

    private void addTranslationToMatrix(int i, int i2, Matrix matrix, Rect rect) {
        CompatibilityInfo.Translator translator = this.mTranslator;
        float translateLengthInAppWindowToScreen = translator != null ? translator.translateLengthInAppWindowToScreen(i2) : i2;
        if (i == 1) {
            matrix.postTranslate(-translateLengthInAppWindowToScreen, 0.0f);
            rect.offset(-i2, 0);
            return;
        }
        if (i == 2) {
            matrix.postTranslate(0.0f, -translateLengthInAppWindowToScreen);
            rect.offset(0, -i2);
        } else if (i == 3) {
            matrix.postTranslate(translateLengthInAppWindowToScreen, 0.0f);
            rect.offset(i2, 0);
        } else {
            if (i != 4) {
                return;
            }
            matrix.postTranslate(0.0f, translateLengthInAppWindowToScreen);
            rect.offset(0, i2);
        }
    }

    private static void buildSideControlsMap(SparseIntArray sparseIntArray, SparseSetArray<InsetsSourceControl> sparseSetArray, SparseArray<InsetsSourceControl> sparseArray) {
        for (int size = sparseIntArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseIntArray.keyAt(size);
            int valueAt = sparseIntArray.valueAt(size);
            InsetsSourceControl insetsSourceControl = sparseArray.get(keyAt);
            if (insetsSourceControl != null) {
                sparseSetArray.add(valueAt, insetsSourceControl);
            }
        }
    }

    private static void buildSideControlsMap(SparseSetArray<InsetsSourceControl> sparseSetArray, SparseArray<InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null) {
                int insetSide = InsetsSource.getInsetSide(valueAt.getInsetsHint());
                if (insetSide == 0 && valueAt.getType() == WindowInsets.Type.ime()) {
                    insetSide = 4;
                }
                sparseSetArray.add(insetSide, valueAt);
            }
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public boolean isCancelRequested() {
        return this.mCancelled;
    }
}
