package android.view;

import android.graphics.Rect;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class InsetsSourceConsumer {
    protected static final int ANIMATION_STATE_HIDE = 2;
    protected static final int ANIMATION_STATE_NONE = 0;
    protected static final int ANIMATION_STATE_SHOW = 1;
    private static final String TAG = "InsetsSourceConsumer";
    protected int mAnimationState = 0;
    protected final InsetsController mController;
    private boolean mHasViewFocusWhenWindowFocusGain;
    private boolean mHasWindowFocus;
    private int mId;
    private Rect mPendingFrame;
    private Rect mPendingVisibleFrame;
    private InsetsSourceControl mSourceControl;
    protected final InsetsState mState;
    private final Supplier<SurfaceControl.Transaction> mTransactionSupplier;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    @interface ShowResult {
        public static final int IME_SHOW_DELAYED = 1;
        public static final int IME_SHOW_FAILED = 2;
        public static final int SHOW_IMMEDIATELY = 0;
    }

    public InsetsSourceConsumer(int id, int type, InsetsState state, Supplier<SurfaceControl.Transaction> transactionSupplier, InsetsController controller) {
        this.mId = id;
        this.mType = type;
        this.mState = state;
        this.mTransactionSupplier = transactionSupplier;
        this.mController = controller;
    }

    public boolean setControl(InsetsSourceControl control, int[] showTypes, int[] hideTypes) {
        boolean serverVisible = false;
        if (Objects.equals(this.mSourceControl, control)) {
            if (this.mSourceControl != null && this.mSourceControl != control) {
                this.mSourceControl.release(new InsetsController$$ExternalSyntheticLambda8());
                this.mSourceControl = control;
            }
            return false;
        }
        InsetsSourceControl lastControl = this.mSourceControl;
        this.mSourceControl = control;
        if (control != null && InsetsController.DEBUG) {
            Log.d(TAG, String.format("setControl -> %s on %s", WindowInsets.Type.toString(control.getType()), this.mController.getHost().getRootViewTitle()));
        }
        if (this.mSourceControl == null) {
            this.mController.notifyControlRevoked(this);
            InsetsSource localSource = this.mState.peekSource(this.mId);
            InsetsSource serverSource = this.mController.getLastDispatchedState().peekSource(this.mId);
            boolean localVisible = localSource != null && localSource.isVisible();
            if (serverSource != null && serverSource.isVisible()) {
                serverVisible = true;
            }
            if (localSource != null) {
                localSource.setVisible(serverVisible);
            }
            if (localVisible != serverVisible) {
                this.mController.notifyVisibilityChanged();
            }
        } else {
            boolean requestedVisible = isRequestedVisibleAwaitingControl();
            SurfaceControl oldLeash = lastControl != null ? lastControl.getLeash() : null;
            SurfaceControl newLeash = control.getLeash();
            if (newLeash != null && ((oldLeash == null || !newLeash.isSameSurface(oldLeash)) && requestedVisible != control.isInitiallyVisible())) {
                if (InsetsController.DEBUG) {
                    Log.d(TAG, String.format("Gaining leash in %s, requestedVisible: %b", this.mController.getHost().getRootViewTitle(), Boolean.valueOf(requestedVisible)));
                }
                if (requestedVisible) {
                    showTypes[0] = showTypes[0] | this.mType;
                } else {
                    hideTypes[0] = hideTypes[0] | this.mType;
                }
            } else {
                if (applyLocalVisibilityOverride()) {
                    this.mController.notifyVisibilityChanged();
                }
                if (this.mController.getAnimationType(this.mType) == -1) {
                    applyRequestedVisibilityToControl();
                }
                if (!requestedVisible && lastControl == null) {
                    removeSurface();
                }
            }
        }
        if (lastControl != null) {
            lastControl.release(new InsetsController$$ExternalSyntheticLambda8());
        }
        return true;
    }

    public InsetsSourceControl getControl() {
        return this.mSourceControl;
    }

    protected boolean isRequestedVisibleAwaitingControl() {
        return (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
    }

    int getId() {
        return this.mId;
    }

    void setId(int id) {
        this.mId = id;
    }

    int getType() {
        return this.mType;
    }

    public boolean onAnimationStateChanged(boolean running) {
        boolean cancelledForNewAnimation;
        boolean insetsChanged = false;
        if (!running && this.mPendingFrame != null) {
            InsetsSource source = this.mState.peekSource(this.mId);
            if (source != null) {
                source.setFrame(this.mPendingFrame);
                source.setVisibleFrame(this.mPendingVisibleFrame);
                insetsChanged = true;
            }
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
        }
        boolean showRequested = isShowRequested();
        int i = 2;
        if (Flags.refactorInsetsController()) {
            cancelledForNewAnimation = (this.mController.getCancelledForNewAnimationTypes() & this.mType) != 0;
        } else if (!running && showRequested) {
            cancelledForNewAnimation = this.mAnimationState == 2;
        } else {
            cancelledForNewAnimation = this.mAnimationState == 1;
        }
        if (running) {
            if (showRequested) {
                i = 1;
            }
        } else {
            i = 0;
        }
        this.mAnimationState = i;
        if (!cancelledForNewAnimation) {
            return insetsChanged | applyLocalVisibilityOverride();
        }
        return insetsChanged;
    }

    protected boolean isShowRequested() {
        return (this.mController.getRequestedVisibleTypes() & getType()) != 0;
    }

    public void onWindowFocusGained(boolean hasViewFocus) {
        this.mHasWindowFocus = true;
        this.mHasViewFocusWhenWindowFocusGain = hasViewFocus;
    }

    public void onWindowFocusLost() {
        this.mHasWindowFocus = false;
    }

    boolean hasViewFocusWhenWindowFocusGain() {
        return this.mHasViewFocusWhenWindowFocusGain;
    }

    public boolean applyLocalVisibilityOverride() {
        InsetsSource source = this.mState.peekSource(this.mId);
        if (source == null) {
            return false;
        }
        boolean requestedVisible = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        if (Flags.refactorInsetsController()) {
            if (this.mSourceControl == null) {
                if (InsetsController.DEBUG) {
                    Log.d(TAG, TextUtils.formatSimple("applyLocalVisibilityOverride: No control in %s for type %s, requestedVisible=%s", this.mController.getHost().getRootViewTitle(), WindowInsets.Type.toString(this.mType), Boolean.valueOf(requestedVisible)));
                }
                return false;
            }
            if (this.mId != InsetsSource.ID_IME_CAPTION_BAR && this.mSourceControl.getLeash() == null) {
                if (InsetsController.DEBUG) {
                    Log.d(TAG, TextUtils.formatSimple("applyLocalVisibilityOverride: Set the source visibility to false, as there is no leash yet for type %s in %s", WindowInsets.Type.toString(this.mType), this.mController.getHost().getRootViewTitle()));
                }
                boolean wasVisible = source.isVisible();
                source.setVisible(false);
                return wasVisible;
            }
        } else if (this.mSourceControl == null) {
            if (InsetsController.DEBUG) {
                Log.d(TAG, "applyLocalVisibilityOverride: No control in " + this.mController.getHost().getRootViewTitle() + " requestedVisible=" + requestedVisible);
            }
            return false;
        }
        if (source.isVisible() == requestedVisible) {
            return false;
        }
        if (InsetsController.DEBUG) {
            Log.d(TAG, String.format("applyLocalVisibilityOverride: %s requestedVisible: %b", this.mController.getHost().getRootViewTitle(), Boolean.valueOf(requestedVisible)));
        }
        source.setVisible(requestedVisible);
        return true;
    }

    public int requestShow(boolean fromController, ImeTracker.Token statsToken) {
        return 0;
    }

    void requestHide(boolean fromController, ImeTracker.Token statsToken) {
    }

    public void onPerceptible(boolean perceptible) {
        IBinder window;
        if (Flags.refactorInsetsController() && this.mType == WindowInsets.Type.ime() && (window = this.mController.getHost().getWindowToken()) != null) {
            this.mController.getHost().getInputMethodManager().reportPerceptible(window, perceptible);
        }
    }

    public void removeSurface() {
    }

    public void updateSource(InsetsSource newSource, int animationType) {
        InsetsSource source = this.mState.peekSource(this.mId);
        Rect rect = null;
        if (source == null || animationType == -1 || source.getFrame().equals(newSource.getFrame())) {
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
            this.mState.addSource(newSource);
            return;
        }
        InsetsSource newSource2 = new InsetsSource(newSource);
        this.mPendingFrame = new Rect(newSource2.getFrame());
        if (newSource2.getVisibleFrame() != null) {
            rect = new Rect(newSource2.getVisibleFrame());
        }
        this.mPendingVisibleFrame = rect;
        newSource2.setFrame(source.getFrame());
        newSource2.setVisibleFrame(source.getVisibleFrame());
        this.mState.addSource(newSource2);
        if (InsetsController.DEBUG) {
            Log.d(TAG, "updateSource: " + newSource2);
        }
    }

    private void applyRequestedVisibilityToControl() {
        if (this.mSourceControl == null || this.mSourceControl.getLeash() == null) {
            return;
        }
        boolean requestedVisible = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        SurfaceControl.Transaction t = this.mTransactionSupplier.get();
        try {
            Log.i(TAG, "applyRequestedVisibilityToControl: visible=" + requestedVisible + ", type=" + WindowInsets.Type.toString(this.mType) + ", host=" + this.mController.getHost().getRootViewTitle());
            if (requestedVisible) {
                t.show(this.mSourceControl.getLeash());
            } else {
                t.hide(this.mSourceControl.getLeash());
            }
            t.setAlpha(this.mSourceControl.getLeash(), requestedVisible ? 1.0f : 0.0f);
            t.apply();
            if (t != null) {
                t.close();
            }
            onPerceptible(requestedVisible);
        } catch (Throwable th) {
            if (t != null) {
                try {
                    t.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1133871366146L, this.mHasWindowFocus);
        proto.write(1133871366147L, isShowRequested());
        if (this.mSourceControl != null) {
            this.mSourceControl.dumpDebug(proto, 1146756268036L);
        }
        if (this.mPendingFrame != null) {
            this.mPendingFrame.dumpDebug(proto, 1146756268037L);
        }
        if (this.mPendingVisibleFrame != null) {
            this.mPendingVisibleFrame.dumpDebug(proto, 1146756268038L);
        }
        proto.write(1120986464263L, this.mAnimationState);
        proto.write(1120986464264L, this.mType);
        proto.end(token);
    }
}
