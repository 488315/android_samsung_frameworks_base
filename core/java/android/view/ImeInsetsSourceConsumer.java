package android.view;

import android.os.IBinder;
import android.os.Trace;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.ImeTracing;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public final class ImeInsetsSourceConsumer extends InsetsSourceConsumer {
    private boolean mHasPendingRequest;
    private boolean mIsRequestedVisibleAwaitingLeash;

    public ImeInsetsSourceConsumer(int id, InsetsState state, Supplier<SurfaceControl.Transaction> transactionSupplier, InsetsController controller) {
        super(id, WindowInsets.Type.ime(), state, transactionSupplier, controller);
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean onAnimationStateChanged(boolean running) {
        if (Flags.refactorInsetsController()) {
            return super.onAnimationStateChanged(running);
        }
        if (!running) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#onAnimationFinished", this.mController.getHost().getInputMethodManager(), null);
        }
        boolean insetsChanged = super.onAnimationStateChanged(running);
        if (running && !isShowRequested() && this.mController.isPredictiveBackImeHideAnimInProgress()) {
            insetsChanged |= applyLocalVisibilityOverride();
        }
        if (!isShowRequested()) {
            this.mIsRequestedVisibleAwaitingLeash = false;
            if (!running && !this.mHasPendingRequest) {
                ImeTracker.Token statsToken = ImeTracker.forLogging().onStart(2, 5, 51, this.mController.getHost().isHandlingPointerEvent());
                notifyHidden(statsToken);
                removeSurface();
            }
        }
        this.mHasPendingRequest = false;
        return insetsChanged;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusGained(boolean hasViewFocus) {
        super.onWindowFocusGained(hasViewFocus);
        if (!Flags.refactorInsetsController()) {
            getImm().registerImeConsumer(this);
            if ((this.mController.getRequestedVisibleTypes() & getType()) != 0 && !hasLeash()) {
                this.mIsRequestedVisibleAwaitingLeash = true;
            }
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusLost() {
        super.onWindowFocusLost();
        if (!Flags.refactorInsetsController()) {
            getImm().unregisterImeConsumer(this);
            this.mIsRequestedVisibleAwaitingLeash = false;
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean applyLocalVisibilityOverride() {
        ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#applyLocalVisibilityOverride", this.mController.getHost().getInputMethodManager(), null);
        return super.applyLocalVisibilityOverride();
    }

    @Override // android.view.InsetsSourceConsumer
    public int requestShow(boolean fromIme, ImeTracker.Token statsToken) {
        if (Flags.refactorInsetsController()) {
            return 2;
        }
        if (fromIme) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#requestShow", this.mController.getHost().getInputMethodManager(), null);
        }
        onShowRequested();
        ImeTracker.forLogging().onProgress(statsToken, 36);
        if (!hasLeash()) {
            this.mIsRequestedVisibleAwaitingLeash = true;
        }
        if (!fromIme) {
            if (this.mState.isSourceOrDefaultVisible(getId(), getType()) && hasLeash()) {
                return 0;
            }
            return getImm().requestImeShow(this.mController.getHost().getWindowToken(), statsToken) ? 1 : 2;
        }
        return 0;
    }

    @Override // android.view.InsetsSourceConsumer
    void requestHide(boolean fromIme, ImeTracker.Token statsToken) {
        ImeTracker.Token notifyStatsToken;
        if (!Flags.refactorInsetsController()) {
            if (!fromIme) {
                if (hasLeash()) {
                    notifyStatsToken = ImeTracker.forLogging().onStart(2, 5, 52, this.mController.getHost().isHandlingPointerEvent());
                } else {
                    notifyStatsToken = statsToken;
                }
                notifyHidden(notifyStatsToken);
            }
            if (this.mAnimationState == 1) {
                this.mHasPendingRequest = true;
            }
        }
    }

    private void notifyHidden(ImeTracker.Token statsToken) {
        if (!Flags.refactorInsetsController()) {
            ImeTracker.forLogging().onProgress(statsToken, 38);
            getImm().notifyImeHidden(this.mController.getHost().getWindowToken(), statsToken);
            this.mIsRequestedVisibleAwaitingLeash = false;
            Trace.asyncTraceEnd(8L, "IC.hideRequestFromApi", 0);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public void removeSurface() {
        IBinder window = this.mController.getHost().getWindowToken();
        if (window != null) {
            getImm().removeImeSurface(window);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean setControl(InsetsSourceControl control, int[] showTypes, int[] hideTypes) {
        if (Flags.refactorInsetsController()) {
            return super.setControl(control, showTypes, hideTypes);
        }
        ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#setControl", this.mController.getHost().getInputMethodManager(), null);
        if (!super.setControl(control, showTypes, hideTypes)) {
            return false;
        }
        if (control == null && !this.mIsRequestedVisibleAwaitingLeash) {
            this.mController.setRequestedVisibleTypes(0, getType());
            removeSurface();
        }
        boolean hasLeash = (control == null || control.getLeash() == null) ? false : true;
        if (hasLeash) {
            this.mIsRequestedVisibleAwaitingLeash = false;
        }
        return true;
    }

    @Override // android.view.InsetsSourceConsumer
    protected boolean isRequestedVisibleAwaitingControl() {
        return super.isRequestedVisibleAwaitingControl() || this.mIsRequestedVisibleAwaitingLeash;
    }

    private boolean hasLeash() {
        InsetsSourceControl control = getControl();
        return (control == null || control.getLeash() == null) ? false : true;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onPerceptible(boolean perceptible) {
        IBinder window;
        super.onPerceptible(perceptible);
        if (!Flags.refactorInsetsController() && (window = this.mController.getHost().getWindowToken()) != null) {
            getImm().reportPerceptible(window, perceptible);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        super.dumpDebug(proto, 1146756268033L);
        proto.write(1133871366147L, this.mIsRequestedVisibleAwaitingLeash);
        proto.write(1133871366150L, this.mHasPendingRequest);
        proto.end(token);
    }

    public void onShowRequested() {
        if (this.mAnimationState == 2 || this.mController.isPredictiveBackImeHideAnimInProgress()) {
            this.mHasPendingRequest = true;
        }
    }

    private InputMethodManager getImm() {
        return this.mController.getHost().getInputMethodManager();
    }
}
