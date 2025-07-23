package android.view;

import android.os.CancellationSignal;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PendingInsetsController implements WindowInsetsController {
    private static final int KEEP_BEHAVIOR = -1;
    private boolean mAnimationsDisabled;
    private int mAppearance;
    private int mAppearanceFromResource;
    private int mAppearanceFromResourceMask;
    private int mAppearanceMask;
    private WindowInsetsAnimationControlListener mLoggingListener;
    private InsetsController mReplayedInsetsController;
    private final ArrayList<PendingRequest> mRequests = new ArrayList<>();
    private int mBehavior = -1;
    private final InsetsState mDummyState = new InsetsState();
    private ArrayList<WindowInsetsController.OnControllableInsetsChangedListener> mControllableInsetsChangedListeners = new ArrayList<>();
    private int mImeCaptionBarInsetsHeight = 0;
    private int mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();

    private interface PendingRequest {
        void replay(InsetsController insetsController);
    }

    @Override // android.view.WindowInsetsController
    public void show(int i) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.show(i);
        } else {
            this.mRequests.add(new ShowRequest(i));
            this.mRequestedVisibleTypes = i | this.mRequestedVisibleTypes;
        }
    }

    @Override // android.view.WindowInsetsController
    public void hide(int i) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.hide(i);
            return;
        }
        this.mRequests.add(new HideRequest(i));
        this.mRequestedVisibleTypes = (~i) & this.mRequestedVisibleTypes;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int i, int i2) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.setSystemBarsAppearance(i, i2);
            return;
        }
        this.mAppearance = (i & i2) | (this.mAppearance & (~i2));
        this.mAppearanceMask |= i2;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearanceFromResource(int i, int i2) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.setSystemBarsAppearanceFromResource(i, i2);
            return;
        }
        this.mAppearanceFromResource = (i & i2) | (this.mAppearanceFromResource & (~i2));
        this.mAppearanceFromResourceMask |= i2;
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            return insetsController.getSystemBarsAppearance();
        }
        return ((~this.mAppearanceMask) & this.mAppearanceFromResource) | this.mAppearance;
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int i) {
        this.mImeCaptionBarInsetsHeight = i;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int i) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.setSystemBarsBehavior(i);
        } else {
            this.mBehavior = i;
        }
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            return insetsController.getSystemBarsBehavior();
        }
        int i = this.mBehavior;
        if (i == -1) {
            return 1;
        }
        return i;
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean z) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.setAnimationsDisabled(z);
        } else {
            this.mAnimationsDisabled = z;
        }
    }

    @Override // android.view.WindowInsetsController
    public InsetsState getState() {
        return this.mDummyState;
    }

    @Override // android.view.WindowInsetsController
    public int getRequestedVisibleTypes() {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            return insetsController.getRequestedVisibleTypes();
        }
        return this.mRequestedVisibleTypes;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
        } else {
            this.mControllableInsetsChangedListeners.add(onControllableInsetsChangedListener);
            onControllableInsetsChangedListener.onControllableInsetsChanged(this, 0);
        }
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
        } else {
            this.mControllableInsetsChangedListeners.remove(onControllableInsetsChangedListener);
        }
    }

    public void replayAndAttach(InsetsController insetsController) {
        int i = this.mBehavior;
        if (i != -1) {
            insetsController.setSystemBarsBehavior(i);
        }
        int i2 = this.mAppearanceMask;
        if (i2 != 0) {
            insetsController.setSystemBarsAppearance(this.mAppearance, i2);
        }
        int i3 = this.mAppearanceFromResourceMask;
        if (i3 != 0) {
            insetsController.setSystemBarsAppearanceFromResource(this.mAppearanceFromResource, i3);
        }
        int i4 = this.mImeCaptionBarInsetsHeight;
        if (i4 != 0) {
            insetsController.setImeCaptionBarInsetsHeight(i4);
        }
        if (this.mAnimationsDisabled) {
            insetsController.setAnimationsDisabled(true);
        }
        int size = this.mRequests.size();
        for (int i5 = 0; i5 < size; i5++) {
            this.mRequests.get(i5).replay(insetsController);
        }
        int size2 = this.mControllableInsetsChangedListeners.size();
        for (int i6 = 0; i6 < size2; i6++) {
            insetsController.addOnControllableInsetsChangedListener(this.mControllableInsetsChangedListeners.get(i6));
        }
        WindowInsetsAnimationControlListener windowInsetsAnimationControlListener = this.mLoggingListener;
        if (windowInsetsAnimationControlListener != null) {
            insetsController.setSystemDrivenInsetsAnimationLoggingListener(windowInsetsAnimationControlListener);
        }
        this.mRequests.clear();
        this.mControllableInsetsChangedListeners.clear();
        this.mBehavior = -1;
        this.mAppearance = 0;
        this.mAppearanceMask = 0;
        this.mAppearanceFromResource = 0;
        this.mAppearanceFromResourceMask = 0;
        this.mAnimationsDisabled = false;
        this.mLoggingListener = null;
        this.mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        this.mReplayedInsetsController = insetsController;
    }

    public void detach() {
        this.mReplayedInsetsController = null;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.setSystemDrivenInsetsAnimationLoggingListener(windowInsetsAnimationControlListener);
        } else {
            this.mLoggingListener = windowInsetsAnimationControlListener;
        }
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        InsetsController insetsController = this.mReplayedInsetsController;
        if (insetsController != null) {
            insetsController.controlWindowInsetsAnimation(i, j, interpolator, cancellationSignal, windowInsetsAnimationControlListener);
        } else {
            windowInsetsAnimationControlListener.onCancelled(null);
        }
    }

    private static class ShowRequest implements PendingRequest {
        private final int mTypes;

        public ShowRequest(int i) {
            this.mTypes = i;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(InsetsController insetsController) {
            insetsController.show(this.mTypes);
        }
    }

    private static class HideRequest implements PendingRequest {
        private final int mTypes;

        public HideRequest(int i) {
            this.mTypes = i;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(InsetsController insetsController) {
            insetsController.hide(this.mTypes);
        }
    }
}
