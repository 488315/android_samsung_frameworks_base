package android.view;

import android.os.SystemClock;
import com.android.graphics.hwui.flags.Flags;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
class HdrRenderState implements Consumer<Display> {
    private static final boolean FLAG_ANIMATE_ENABLED = Flags.animateHdrTransitions();
    private static final float TRANSITION_PER_MS = 0.01f;
    private final ViewRootImpl mViewRoot;
    private boolean mIsHdrEnabled = false;
    private boolean mIsListenerRegistered = false;
    private boolean mUpdateHdrSdrRatioInfo = false;
    private float mDesiredHdrSdrRatio = 1.0f;
    private float mTargetDesiredHdrSdrRatio = 1.0f;
    private float mTargetHdrSdrRatio = 1.0f;
    private float mRenderHdrSdrRatio = 1.0f;
    private float mPreviousRenderRatio = 1.0f;
    private long mLastUpdateMillis = -1;

    HdrRenderState(ViewRootImpl viewRoot) {
        this.mViewRoot = viewRoot;
    }

    @Override // java.util.function.Consumer
    public void accept(Display display) {
        forceUpdateHdrSdrRatio();
        this.mViewRoot.invalidate();
    }

    boolean isHdrEnabled() {
        return this.mIsHdrEnabled;
    }

    void stopListening() {
        if (this.mIsListenerRegistered) {
            this.mViewRoot.mDisplay.unregisterHdrSdrRatioChangedListener(this);
            this.mIsListenerRegistered = false;
        }
    }

    void startListening() {
        if (isHdrEnabled() && !this.mIsListenerRegistered && this.mViewRoot.mDisplay != null && this.mViewRoot.mDisplay.isHdrSdrRatioAvailable()) {
            this.mViewRoot.mDisplay.registerHdrSdrRatioChangedListener(this.mViewRoot.mExecutor, this);
            this.mIsListenerRegistered = true;
        }
    }

    boolean updateForFrame(long frameTimeMillis) {
        boolean hasUpdate = this.mUpdateHdrSdrRatioInfo;
        this.mUpdateHdrSdrRatioInfo = false;
        this.mRenderHdrSdrRatio = this.mTargetHdrSdrRatio;
        long timeDelta = Math.max(Math.min(32L, frameTimeMillis - this.mLastUpdateMillis), 8L);
        float maxStep = timeDelta * 0.01f;
        this.mLastUpdateMillis = frameTimeMillis;
        if (hasUpdate && FLAG_ANIMATE_ENABLED) {
            if (isHdrEnabled()) {
                float delta = this.mTargetHdrSdrRatio - this.mPreviousRenderRatio;
                if (delta > maxStep) {
                    this.mRenderHdrSdrRatio = this.mPreviousRenderRatio + maxStep;
                    this.mUpdateHdrSdrRatioInfo = true;
                    this.mViewRoot.invalidate();
                }
                this.mPreviousRenderRatio = this.mRenderHdrSdrRatio;
                if (this.mTargetDesiredHdrSdrRatio < this.mDesiredHdrSdrRatio) {
                    this.mDesiredHdrSdrRatio = Math.max(this.mTargetDesiredHdrSdrRatio, this.mDesiredHdrSdrRatio - maxStep);
                    if (this.mDesiredHdrSdrRatio != this.mTargetDesiredHdrSdrRatio) {
                        this.mUpdateHdrSdrRatioInfo = true;
                        this.mViewRoot.invalidate();
                    }
                }
            } else {
                this.mPreviousRenderRatio = this.mTargetHdrSdrRatio;
                this.mDesiredHdrSdrRatio = this.mTargetDesiredHdrSdrRatio;
            }
        }
        return hasUpdate;
    }

    float getDesiredHdrSdrRatio() {
        return this.mDesiredHdrSdrRatio;
    }

    float getRenderHdrSdrRatio() {
        return this.mRenderHdrSdrRatio;
    }

    void forceUpdateHdrSdrRatio() {
        if (isHdrEnabled()) {
            this.mTargetHdrSdrRatio = Math.min(this.mDesiredHdrSdrRatio, this.mViewRoot.mDisplay.getHdrSdrRatio());
        } else {
            this.mTargetHdrSdrRatio = 1.0f;
        }
        this.mUpdateHdrSdrRatioInfo = true;
    }

    void setDesiredHdrSdrRatio(boolean isHdrEnabled, float desiredRatio) {
        this.mIsHdrEnabled = isHdrEnabled;
        this.mLastUpdateMillis = SystemClock.uptimeMillis();
        if (desiredRatio != this.mTargetDesiredHdrSdrRatio) {
            this.mTargetDesiredHdrSdrRatio = desiredRatio;
            if (this.mTargetDesiredHdrSdrRatio > this.mDesiredHdrSdrRatio || !FLAG_ANIMATE_ENABLED) {
                this.mDesiredHdrSdrRatio = this.mTargetDesiredHdrSdrRatio;
            }
            forceUpdateHdrSdrRatio();
            this.mViewRoot.invalidate();
            if (isHdrEnabled()) {
                startListening();
            } else {
                stopListening();
            }
        }
    }
}
