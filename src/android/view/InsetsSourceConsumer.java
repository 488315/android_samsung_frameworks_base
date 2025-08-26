package android.view;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsAnimationControlRunner;
import android.view.InsetsController;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.ImeTracing;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public class InsetsSourceConsumer {
    protected static final int ANIMATION_STATE_HIDE = 2;
    protected static final int ANIMATION_STATE_NONE = 0;
    protected static final int ANIMATION_STATE_SHOW = 1;
    private static final String TAG = "InsetsSourceConsumer";
    protected final InsetsController mController;
    private boolean mHasViewFocusWhenWindowFocusGain;
    private boolean mHasWindowFocus;
    private int mId;
    private Rect mPendingFrame;
    private Rect mPendingVisibleFrame;
    private InsetsSourceControl mSourceControl;
    protected final InsetsState mState;
    private final int mType;
    protected int mAnimationState = 0;
    private InsetsAnimationControlRunner.SurfaceParamsApplier mSurfaceParamsApplier = InsetsAnimationControlRunner.SurfaceParamsApplier.DEFAULT;
    private final Matrix mTmpMatrix = new Matrix();

    @Retention(RetentionPolicy.SOURCE)
    @interface ShowResult {
        public static final int IME_SHOW_DELAYED = 1;
        public static final int IME_SHOW_FAILED = 2;
        public static final int SHOW_IMMEDIATELY = 0;
    }

    void requestHide(boolean z, ImeTracker.Token token) {
    }

    public int requestShow(boolean z, ImeTracker.Token token) {
        return 0;
    }

    public InsetsSourceConsumer(int i, int i2, InsetsState insetsState, InsetsController insetsController) {
        this.mId = i;
        this.mType = i2;
        this.mState = insetsState;
        this.mController = insetsController;
    }

    public boolean setControl(InsetsSourceControl insetsSourceControl, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        boolean z = false;
        if (Objects.equals(this.mSourceControl, insetsSourceControl)) {
            InsetsSourceControl insetsSourceControl2 = this.mSourceControl;
            if (insetsSourceControl2 != null && insetsSourceControl2 != insetsSourceControl) {
                insetsSourceControl2.release(new InsetsController$$ExternalSyntheticLambda8());
                this.mSourceControl = insetsSourceControl;
            }
            return false;
        }
        InsetsSourceControl insetsSourceControl3 = this.mSourceControl;
        this.mSourceControl = insetsSourceControl;
        if (insetsSourceControl != null && InsetsController.DEBUG) {
            Log.d(TAG, String.format("setControl -> %s on %s", WindowInsets.Type.toString(insetsSourceControl.getType()), this.mController.getHost().getRootViewTitle()));
        }
        if (this.mSourceControl == null) {
            this.mController.notifyControlRevoked(this);
            InsetsSource insetsSourcePeekSource = this.mState.peekSource(this.mId);
            InsetsSource insetsSourcePeekSource2 = this.mController.getLastDispatchedState().peekSource(this.mId);
            boolean z2 = insetsSourcePeekSource != null && insetsSourcePeekSource.isVisible();
            if (insetsSourcePeekSource2 != null && insetsSourcePeekSource2.isVisible()) {
                z = true;
            }
            if (insetsSourcePeekSource != null) {
                insetsSourcePeekSource.setVisible(z);
            }
            if (z2 != z) {
                this.mController.notifyVisibilityChanged();
            }
            setSurfaceParamsApplier(InsetsAnimationControlRunner.SurfaceParamsApplier.DEFAULT);
        } else {
            if (insetsSourceControl3 != null && !Insets.NONE.equals(insetsSourceControl3.getInsetsHint()) && InsetsSource.getInsetSide(insetsSourceControl3.getInsetsHint()) != InsetsSource.getInsetSide(insetsSourceControl.getInsetsHint())) {
                iArr3[0] = iArr3[0] | this.mType;
            }
            boolean zIsRequestedVisibleAwaitingControl = isRequestedVisibleAwaitingControl();
            SurfaceControl leash = insetsSourceControl3 != null ? insetsSourceControl3.getLeash() : null;
            SurfaceControl leash2 = insetsSourceControl.getLeash();
            if (leash2 != null && ((leash == null || !leash2.isSameSurface(leash)) && zIsRequestedVisibleAwaitingControl != insetsSourceControl.isInitiallyVisible() && (insetsSourceControl.getType() == WindowInsets.Type.ime() || InsetsSource.getInsetSide(insetsSourceControl.getInsetsHint()) != 0))) {
                if (InsetsController.DEBUG) {
                    Log.d(TAG, String.format("Gaining leash in %s, requestedVisible: %b", this.mController.getHost().getRootViewTitle(), Boolean.valueOf(zIsRequestedVisibleAwaitingControl)));
                }
                if (zIsRequestedVisibleAwaitingControl) {
                    iArr[0] = iArr[0] | this.mType;
                } else {
                    iArr2[0] = iArr2[0] | this.mType;
                }
                if (insetsSourceControl3 != null && insetsSourceControl3.isFake()) {
                    iArr4[0] = this.mType | iArr4[0];
                }
            } else {
                if (applyLocalVisibilityOverride()) {
                    this.mController.notifyVisibilityChanged();
                }
                if (!this.mController.hasSurfaceAnimation(this.mType)) {
                    applyRequestedVisibilityAndPositionToControl();
                }
                if (!zIsRequestedVisibleAwaitingControl && insetsSourceControl3 == null) {
                    removeSurface();
                }
            }
        }
        if (insetsSourceControl3 != null) {
            insetsSourceControl3.release(new InsetsController$$ExternalSyntheticLambda8());
        }
        return true;
    }

    public InsetsSourceControl getControl() {
        return this.mSourceControl;
    }

    protected boolean isRequestedVisibleAwaitingControl() {
        return (this.mType & this.mController.getRequestedVisibleTypes()) != 0;
    }

    int getId() {
        return this.mId;
    }

    void setId(int i) {
        this.mId = i;
    }

    int getType() {
        return this.mType;
    }

    void setSurfaceParamsApplier(InsetsAnimationControlRunner.SurfaceParamsApplier surfaceParamsApplier) {
        this.mSurfaceParamsApplier = surfaceParamsApplier;
    }

    public boolean onAnimationStateChanged(boolean z) {
        boolean zApplyLocalVisibilityOverride;
        ViewRootImpl viewRoot;
        int i = 1;
        if (z || this.mPendingFrame == null) {
            zApplyLocalVisibilityOverride = false;
        } else {
            InsetsSource insetsSourcePeekSource = this.mState.peekSource(this.mId);
            if (insetsSourcePeekSource != null) {
                insetsSourcePeekSource.setFrame(this.mPendingFrame);
                insetsSourcePeekSource.setVisibleFrame(this.mPendingVisibleFrame);
                zApplyLocalVisibilityOverride = true;
            } else {
                zApplyLocalVisibilityOverride = false;
            }
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
        }
        boolean zIsShowRequested = isShowRequested();
        boolean z2 = !Flags.refactorInsetsController() ? z || !zIsShowRequested ? this.mAnimationState != 1 : this.mAnimationState != 2 : (this.mController.getCancelledForNewAnimationTypes() & this.mType) == 0;
        if (!z) {
            i = 0;
        } else if (!zIsShowRequested) {
            i = 2;
        }
        this.mAnimationState = i;
        if (!z2) {
            zApplyLocalVisibilityOverride |= applyLocalVisibilityOverride();
        }
        if (Flags.refactorInsetsController()) {
            InsetsController.Host host = this.mController.getHost();
            if (zApplyLocalVisibilityOverride && this.mType == WindowInsets.Type.ime() && (host instanceof ViewRootInsetsControllerHost) && (viewRoot = ((ViewRootInsetsControllerHost) host).getViewRoot()) != null) {
                viewRoot.forceWmRelayout();
            }
        }
        return zApplyLocalVisibilityOverride;
    }

    protected boolean isShowRequested() {
        return (getType() & this.mController.getRequestedVisibleTypes()) != 0;
    }

    public void onWindowFocusGained(boolean z) {
        this.mHasWindowFocus = true;
        this.mHasViewFocusWhenWindowFocusGain = z;
    }

    public void onWindowFocusLost() {
        this.mHasWindowFocus = false;
    }

    boolean hasViewFocusWhenWindowFocusGain() {
        return this.mHasViewFocusWhenWindowFocusGain;
    }

    public boolean applyLocalVisibilityOverride() {
        if (Flags.refactorInsetsController() && this.mType == WindowInsets.Type.ime()) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#applyLocalVisibilityOverride", this.mController.getHost().getInputMethodManager(), null);
        }
        InsetsSource insetsSourcePeekSource = this.mState.peekSource(this.mId);
        if (insetsSourcePeekSource == null) {
            return false;
        }
        boolean z = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        if (this.mSourceControl == null) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, TextUtils.formatSimple("applyLocalVisibilityOverride: No control in %s for type %s, requestedVisible=%s", this.mController.getHost().getRootViewTitle(), WindowInsets.Type.toString(this.mType), Boolean.valueOf(z)));
            }
            return false;
        }
        if (Flags.refactorInsetsController() && this.mId == InsetsSource.ID_IME && this.mSourceControl.getLeash() == null) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, TextUtils.formatSimple("applyLocalVisibilityOverride: Set the source visibility to false, as there is no leash yet for type %s in %s", WindowInsets.Type.toString(this.mType), this.mController.getHost().getRootViewTitle()));
            }
            boolean zIsVisible = insetsSourcePeekSource.isVisible();
            insetsSourcePeekSource.setVisible(false);
            return zIsVisible;
        }
        if (insetsSourcePeekSource.isVisible() == z) {
            return false;
        }
        if (InsetsController.DEBUG) {
            Log.d(TAG, String.format("applyLocalVisibilityOverride: %s requestedVisible: %b", this.mController.getHost().getRootViewTitle(), Boolean.valueOf(z)));
        }
        insetsSourcePeekSource.setVisible(z);
        return true;
    }

    public void onPerceptible(boolean z) {
        IBinder windowToken;
        if (Flags.refactorInsetsController() && this.mType == WindowInsets.Type.ime() && (windowToken = this.mController.getHost().getWindowToken()) != null) {
            this.mController.getHost().getInputMethodManager().reportPerceptible(windowToken, z);
        }
    }

    public void removeSurface() {
        IBinder windowToken;
        if (Flags.refactorInsetsController() && this.mType == WindowInsets.Type.ime() && (windowToken = this.mController.getHost().getWindowToken()) != null) {
            this.mController.getHost().getInputMethodManager().removeImeSurface(windowToken);
        }
    }

    public void updateSource(InsetsSource insetsSource, int i) {
        InsetsSource insetsSourcePeekSource = this.mState.peekSource(this.mId);
        if (insetsSourcePeekSource == null || i == -1 || insetsSourcePeekSource.getFrame().equals(insetsSource.getFrame())) {
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
            this.mState.addSource(insetsSource);
            return;
        }
        this.mPendingFrame = new Rect(insetsSource.getFrame());
        this.mPendingVisibleFrame = insetsSource.getVisibleFrame() != null ? new Rect(insetsSource.getVisibleFrame()) : null;
        insetsSource.setFrame(insetsSourcePeekSource.getFrame());
        insetsSource.setVisibleFrame(insetsSourcePeekSource.getVisibleFrame());
        this.mState.addSource(insetsSource);
        if (InsetsController.DEBUG) {
            Log.d(TAG, "updateSource: " + insetsSource);
        }
    }

    private void applyRequestedVisibilityAndPositionToControl() {
        SurfaceControl leash;
        InsetsSourceControl insetsSourceControl = this.mSourceControl;
        if (insetsSourceControl == null || (leash = insetsSourceControl.getLeash()) == null || !leash.isValid()) {
            return;
        }
        boolean z = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        Point surfacePosition = this.mSourceControl.getSurfacePosition();
        Log.i(TAG, "applyRequestedVisibilityToControl: visible=" + z + ", type=" + WindowInsets.Type.toString(this.mType) + ", host=" + this.mController.getHost().getRootViewTitle());
        this.mTmpMatrix.setTranslate((float) surfacePosition.x, (float) surfacePosition.y);
        InsetsAnimationControlRunner.SurfaceParamsApplier surfaceParamsApplier = this.mSurfaceParamsApplier;
        SyncRtSurfaceTransactionApplier.SurfaceParams[] surfaceParamsArr = new SyncRtSurfaceTransactionApplier.SurfaceParams[1];
        surfaceParamsArr[0] = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash).withVisibility(z).withAlpha(z ? 1.0f : 0.0f).withMatrix(this.mTmpMatrix).build();
        surfaceParamsApplier.applySurfaceParams(surfaceParamsArr);
        onPerceptible(z);
    }

    void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1133871366146L, this.mHasWindowFocus);
        protoOutputStream.write(1133871366147L, isShowRequested());
        InsetsSourceControl insetsSourceControl = this.mSourceControl;
        if (insetsSourceControl != null) {
            insetsSourceControl.dumpDebug(protoOutputStream, 1146756268036L);
        }
        Rect rect = this.mPendingFrame;
        if (rect != null) {
            rect.dumpDebug(protoOutputStream, 1146756268037L);
        }
        Rect rect2 = this.mPendingVisibleFrame;
        if (rect2 != null) {
            rect2.dumpDebug(protoOutputStream, 1146756268038L);
        }
        protoOutputStream.write(1120986464263L, this.mAnimationState);
        protoOutputStream.write(1120986464264L, this.mType);
        protoOutputStream.end(jStart);
    }

    boolean hasPendingFrame() {
        return this.mPendingFrame != null;
    }
}
