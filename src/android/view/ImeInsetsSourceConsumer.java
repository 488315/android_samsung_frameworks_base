package android.view;

import android.os.IBinder;
import android.os.Trace;
import android.util.proto.ProtoOutputStream;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.inputmethod.ImeTracing;

/* loaded from: classes4.dex */
public final class ImeInsetsSourceConsumer extends InsetsSourceConsumer {
    private boolean mHasPendingRequest;
    private boolean mIsRequestedVisibleAwaitingLeash;

    public ImeInsetsSourceConsumer(int i, InsetsState insetsState, InsetsController insetsController) {
        super(i, WindowInsets.Type.ime(), insetsState, insetsController);
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean onAnimationStateChanged(boolean z) {
        if (Flags.refactorInsetsController()) {
            return super.onAnimationStateChanged(z);
        }
        if (!z) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#onAnimationFinished", this.mController.getHost().getInputMethodManager(), null);
        }
        boolean zApplyLocalVisibilityOverride = ((Flags.predictiveBackIme() && !z && isShowRequested() && this.mAnimationState == 2) ? applyLocalVisibilityOverride() : false) | super.onAnimationStateChanged(z);
        if (z && !isShowRequested() && this.mController.isPredictiveBackImeHideAnimInProgress()) {
            zApplyLocalVisibilityOverride |= applyLocalVisibilityOverride();
        }
        if (!isShowRequested()) {
            this.mIsRequestedVisibleAwaitingLeash = false;
            if (!z && !this.mHasPendingRequest) {
                notifyHidden(ImeTracker.forLogging().onStart(2, 5, 51, this.mController.getHost().isHandlingPointerEvent()));
                removeSurface();
            }
        }
        this.mHasPendingRequest = false;
        return zApplyLocalVisibilityOverride;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusGained(boolean z) {
        super.onWindowFocusGained(z);
        if (Flags.refactorInsetsController()) {
            return;
        }
        getImm().registerImeConsumer(this);
        if ((this.mController.getRequestedVisibleTypes() & getType()) == 0 || hasLeash()) {
            return;
        }
        this.mIsRequestedVisibleAwaitingLeash = true;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusLost() {
        super.onWindowFocusLost();
        if (Flags.refactorInsetsController()) {
            return;
        }
        getImm().unregisterImeConsumer(this);
        this.mIsRequestedVisibleAwaitingLeash = false;
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean applyLocalVisibilityOverride() {
        if (!Flags.refactorInsetsController()) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#applyLocalVisibilityOverride", this.mController.getHost().getInputMethodManager(), null);
        }
        return super.applyLocalVisibilityOverride();
    }

    @Override // android.view.InsetsSourceConsumer
    public int requestShow(boolean z, ImeTracker.Token token) {
        if (Flags.refactorInsetsController()) {
            return 2;
        }
        if (z) {
            ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#requestShow", this.mController.getHost().getInputMethodManager(), null);
        }
        onShowRequested();
        ImeTracker.forLogging().onProgress(token, 36);
        if (!hasLeash()) {
            this.mIsRequestedVisibleAwaitingLeash = true;
        }
        if (z) {
            return 0;
        }
        if (this.mState.isSourceOrDefaultVisible(getId(), getType()) && hasLeash()) {
            return 0;
        }
        return getImm().requestImeShow(this.mController.getHost().getWindowToken(), token) ? 1 : 2;
    }

    @Override // android.view.InsetsSourceConsumer
    void requestHide(boolean z, ImeTracker.Token token) {
        if (Flags.refactorInsetsController()) {
            return;
        }
        if (!z) {
            if (hasLeash()) {
                token = ImeTracker.forLogging().onStart(2, 5, 52, this.mController.getHost().isHandlingPointerEvent());
            }
            notifyHidden(token);
        }
        if (this.mAnimationState == 1) {
            this.mHasPendingRequest = true;
        }
    }

    private void notifyHidden(ImeTracker.Token token) {
        if (Flags.refactorInsetsController()) {
            return;
        }
        ImeTracker.forLogging().onProgress(token, 38);
        getImm().notifyImeHidden(this.mController.getHost().getWindowToken(), token);
        this.mIsRequestedVisibleAwaitingLeash = false;
        Trace.asyncTraceEnd(8L, "IC.hideRequestFromApi", 0);
    }

    @Override // android.view.InsetsSourceConsumer
    public void removeSurface() {
        if (Flags.refactorInsetsController()) {
            super.removeSurface();
            return;
        }
        IBinder windowToken = this.mController.getHost().getWindowToken();
        if (windowToken != null) {
            getImm().removeImeSurface(windowToken);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean setControl(InsetsSourceControl insetsSourceControl, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        if (Flags.refactorInsetsController()) {
            return super.setControl(insetsSourceControl, iArr, iArr2, iArr3, iArr4);
        }
        ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#setControl", this.mController.getHost().getInputMethodManager(), null);
        if (!super.setControl(insetsSourceControl, iArr, iArr2, iArr3, iArr4)) {
            return false;
        }
        if (insetsSourceControl == null && !this.mIsRequestedVisibleAwaitingLeash) {
            this.mController.setRequestedVisibleTypes(0, getType());
            removeSurface();
        }
        if (insetsSourceControl == null || insetsSourceControl.getLeash() == null) {
            return true;
        }
        this.mIsRequestedVisibleAwaitingLeash = false;
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
    public void onPerceptible(boolean z) {
        IBinder windowToken;
        super.onPerceptible(z);
        if (Flags.refactorInsetsController() || (windowToken = this.mController.getHost().getWindowToken()) == null) {
            return;
        }
        getImm().reportPerceptible(windowToken, z);
    }

    @Override // android.view.InsetsSourceConsumer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1133871366147L, this.mIsRequestedVisibleAwaitingLeash);
        protoOutputStream.write(1133871366150L, this.mHasPendingRequest);
        protoOutputStream.end(jStart);
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
