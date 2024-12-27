package android.view;

import android.os.CancellationSignal;
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
    private ArrayList<WindowInsetsController.OnControllableInsetsChangedListener>
            mControllableInsetsChangedListeners = new ArrayList<>();
    private int mImeCaptionBarInsetsHeight = 0;
    private int mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();

    private interface PendingRequest {
        void replay(InsetsController insetsController);
    }

    @Override // android.view.WindowInsetsController
    public void show(int types) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.show(types);
        } else {
            this.mRequests.add(new ShowRequest(types));
            this.mRequestedVisibleTypes |= types;
        }
    }

    @Override // android.view.WindowInsetsController
    public void hide(int types) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.hide(types);
        } else {
            this.mRequests.add(new HideRequest(types));
            this.mRequestedVisibleTypes &= ~types;
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int appearance, int mask) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemBarsAppearance(appearance, mask);
        } else {
            this.mAppearance = (this.mAppearance & (~mask)) | (appearance & mask);
            this.mAppearanceMask |= mask;
        }
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearanceFromResource(int appearance, int mask) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemBarsAppearanceFromResource(appearance, mask);
        } else {
            this.mAppearanceFromResource =
                    (this.mAppearanceFromResource & (~mask)) | (appearance & mask);
            this.mAppearanceFromResourceMask |= mask;
        }
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getSystemBarsAppearance();
        }
        return this.mAppearance | (this.mAppearanceFromResource & (~this.mAppearanceMask));
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int height) {
        this.mImeCaptionBarInsetsHeight = height;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int behavior) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemBarsBehavior(behavior);
        } else {
            this.mBehavior = behavior;
        }
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getSystemBarsBehavior();
        }
        if (this.mBehavior == -1) {
            return 1;
        }
        return this.mBehavior;
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean disable) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setAnimationsDisabled(disable);
        } else {
            this.mAnimationsDisabled = disable;
        }
    }

    @Override // android.view.WindowInsetsController
    public InsetsState getState() {
        return this.mDummyState;
    }

    @Override // android.view.WindowInsetsController
    public int getRequestedVisibleTypes() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getRequestedVisibleTypes();
        }
        return this.mRequestedVisibleTypes;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(
            WindowInsetsController.OnControllableInsetsChangedListener listener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.addOnControllableInsetsChangedListener(listener);
        } else {
            this.mControllableInsetsChangedListeners.add(listener);
            listener.onControllableInsetsChanged(this, 0);
        }
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(
            WindowInsetsController.OnControllableInsetsChangedListener listener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.removeOnControllableInsetsChangedListener(listener);
        } else {
            this.mControllableInsetsChangedListeners.remove(listener);
        }
    }

    public void replayAndAttach(InsetsController controller) {
        if (this.mBehavior != -1) {
            controller.setSystemBarsBehavior(this.mBehavior);
        }
        if (this.mAppearanceMask != 0) {
            controller.setSystemBarsAppearance(this.mAppearance, this.mAppearanceMask);
        }
        if (this.mAppearanceFromResourceMask != 0) {
            controller.setSystemBarsAppearanceFromResource(
                    this.mAppearanceFromResource, this.mAppearanceFromResourceMask);
        }
        if (this.mImeCaptionBarInsetsHeight != 0) {
            controller.setImeCaptionBarInsetsHeight(this.mImeCaptionBarInsetsHeight);
        }
        if (this.mAnimationsDisabled) {
            controller.setAnimationsDisabled(true);
        }
        int size = this.mRequests.size();
        for (int i = 0; i < size; i++) {
            this.mRequests.get(i).replay(controller);
        }
        int size2 = this.mControllableInsetsChangedListeners.size();
        for (int i2 = 0; i2 < size2; i2++) {
            controller.addOnControllableInsetsChangedListener(
                    this.mControllableInsetsChangedListeners.get(i2));
        }
        if (this.mLoggingListener != null) {
            controller.setSystemDrivenInsetsAnimationLoggingListener(this.mLoggingListener);
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
        this.mReplayedInsetsController = controller;
    }

    public void detach() {
        this.mReplayedInsetsController = null;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(
            WindowInsetsAnimationControlListener listener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemDrivenInsetsAnimationLoggingListener(listener);
        } else {
            this.mLoggingListener = listener;
        }
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(
            int types,
            long durationMillis,
            Interpolator interpolator,
            CancellationSignal cancellationSignal,
            WindowInsetsAnimationControlListener listener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.controlWindowInsetsAnimation(
                    types, durationMillis, interpolator, cancellationSignal, listener);
        } else {
            listener.onCancelled(null);
        }
    }

    private static class ShowRequest implements PendingRequest {
        private final int mTypes;

        public ShowRequest(int types) {
            this.mTypes = types;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(InsetsController controller) {
            controller.show(this.mTypes);
        }
    }

    private static class HideRequest implements PendingRequest {
        private final int mTypes;

        public HideRequest(int types) {
            this.mTypes = types;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(InsetsController controller) {
            controller.hide(this.mTypes);
        }
    }
}
