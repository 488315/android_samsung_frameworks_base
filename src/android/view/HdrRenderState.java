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

    HdrRenderState(ViewRootImpl viewRootImpl) {
        this.mViewRoot = viewRootImpl;
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
        if (!isHdrEnabled() || this.mIsListenerRegistered || this.mViewRoot.mDisplay == null || !this.mViewRoot.mDisplay.isHdrSdrRatioAvailable()) {
            return;
        }
        this.mViewRoot.mDisplay.registerHdrSdrRatioChangedListener(this.mViewRoot.mExecutor, this);
        this.mIsListenerRegistered = true;
    }

    boolean updateForFrame(long j) {
        boolean z = this.mUpdateHdrSdrRatioInfo;
        this.mUpdateHdrSdrRatioInfo = false;
        this.mRenderHdrSdrRatio = this.mTargetHdrSdrRatio;
        float fMax = Math.max(Math.min(32L, j - this.mLastUpdateMillis), 8L) * 0.01f;
        this.mLastUpdateMillis = j;
        if (z && FLAG_ANIMATE_ENABLED) {
            if (isHdrEnabled()) {
                float f = this.mTargetHdrSdrRatio;
                float f2 = this.mPreviousRenderRatio;
                if (f - f2 > fMax) {
                    this.mRenderHdrSdrRatio = f2 + fMax;
                    this.mUpdateHdrSdrRatioInfo = true;
                    this.mViewRoot.invalidate();
                }
                this.mPreviousRenderRatio = this.mRenderHdrSdrRatio;
                float f3 = this.mTargetDesiredHdrSdrRatio;
                float f4 = this.mDesiredHdrSdrRatio;
                if (f3 < f4) {
                    float fMax2 = Math.max(f3, f4 - fMax);
                    this.mDesiredHdrSdrRatio = fMax2;
                    if (fMax2 != this.mTargetDesiredHdrSdrRatio) {
                        this.mUpdateHdrSdrRatioInfo = true;
                        this.mViewRoot.invalidate();
                        return z;
                    }
                }
            } else {
                this.mPreviousRenderRatio = this.mTargetHdrSdrRatio;
                this.mDesiredHdrSdrRatio = this.mTargetDesiredHdrSdrRatio;
            }
        }
        return z;
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

    void setDesiredHdrSdrRatio(boolean z, float f) {
        this.mIsHdrEnabled = z;
        this.mLastUpdateMillis = SystemClock.uptimeMillis();
        if (f != this.mTargetDesiredHdrSdrRatio) {
            this.mTargetDesiredHdrSdrRatio = f;
            if (f > this.mDesiredHdrSdrRatio || !FLAG_ANIMATE_ENABLED) {
                this.mDesiredHdrSdrRatio = f;
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
