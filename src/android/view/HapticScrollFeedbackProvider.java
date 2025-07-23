package android.view;

import android.view.flags.Flags;

/* loaded from: classes4.dex */
public class HapticScrollFeedbackProvider implements ScrollFeedbackProvider {
    private static final boolean INITIAL_END_OF_LIST_HAPTICS_ENABLED = false;
    private static final String TAG = "HapticScrollFeedbackProvider";
    private static final int TICK_INTERVAL_NO_TICK = 0;
    private int mAxis;
    private boolean mCanPlayLimitFeedback;
    private int mDeviceId;
    private boolean mHapticScrollFeedbackEnabled;
    private final boolean mIsFromView;
    private int mSource;
    private int mTickIntervalPixels;
    private int mTotalScrollPixels;
    private final View mView;
    private final ViewConfiguration mViewConfig;

    public HapticScrollFeedbackProvider(View view) {
        this(view, ViewConfiguration.get(view.getContext()), false);
    }

    public HapticScrollFeedbackProvider(View view, ViewConfiguration viewConfiguration, boolean z) {
        this.mDeviceId = -1;
        this.mAxis = -1;
        this.mSource = -1;
        this.mTickIntervalPixels = 0;
        this.mTotalScrollPixels = 0;
        this.mCanPlayLimitFeedback = false;
        this.mHapticScrollFeedbackEnabled = false;
        this.mView = view;
        this.mViewConfig = viewConfiguration;
        this.mIsFromView = z;
        if (!Flags.dynamicViewRotaryHapticsConfiguration() || z) {
            return;
        }
        view.disableRotaryScrollFeedback();
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollProgress(int i, int i2, int i3, int i4) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (this.mHapticScrollFeedbackEnabled) {
            if (i4 != 0) {
                this.mCanPlayLimitFeedback = true;
            }
            if (this.mTickIntervalPixels == 0) {
                return;
            }
            int i5 = this.mTotalScrollPixels + i4;
            this.mTotalScrollPixels = i5;
            int abs = Math.abs(i5);
            int i6 = this.mTickIntervalPixels;
            if (abs >= i6) {
                this.mTotalScrollPixels %= i6;
                if (android.os.vibrator.Flags.hapticFeedbackInputSourceCustomizationEnabled()) {
                    this.mView.performHapticFeedbackForInputDevice(18, i, i2, 0);
                } else {
                    this.mView.performHapticFeedback(18);
                }
            }
        }
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollLimit(int i, int i2, int i3, boolean z) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (this.mHapticScrollFeedbackEnabled && this.mCanPlayLimitFeedback) {
            if (android.os.vibrator.Flags.hapticFeedbackInputSourceCustomizationEnabled()) {
                this.mView.performHapticFeedbackForInputDevice(20, i, i2, 0);
            } else {
                this.mView.performHapticFeedback(20);
            }
            this.mCanPlayLimitFeedback = false;
        }
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onSnapToItem(int i, int i2, int i3) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (this.mHapticScrollFeedbackEnabled) {
            if (android.os.vibrator.Flags.hapticFeedbackInputSourceCustomizationEnabled()) {
                this.mView.performHapticFeedbackForInputDevice(19, i, i2, 0);
            } else {
                this.mView.performHapticFeedback(19);
            }
            this.mCanPlayLimitFeedback = true;
        }
    }

    private void maybeUpdateCurrentConfig(int i, int i2, int i3) {
        if (this.mAxis == i3 && this.mSource == i2 && this.mDeviceId == i) {
            return;
        }
        this.mSource = i2;
        this.mAxis = i3;
        this.mDeviceId = i;
        if (!Flags.dynamicViewRotaryHapticsConfiguration() && !this.mIsFromView && i2 == 4194304 && this.mViewConfig.isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
            this.mHapticScrollFeedbackEnabled = false;
            return;
        }
        this.mHapticScrollFeedbackEnabled = this.mViewConfig.isHapticScrollFeedbackEnabled(i, i3, i2);
        this.mCanPlayLimitFeedback = false;
        this.mTotalScrollPixels = 0;
        updateTickIntervals(i, i2, i3);
    }

    private void updateTickIntervals(int i, int i2, int i3) {
        this.mTickIntervalPixels = this.mHapticScrollFeedbackEnabled ? this.mViewConfig.getHapticScrollFeedbackTickInterval(i, i3, i2) : 0;
    }
}
