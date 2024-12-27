package android.view;

public class HapticScrollFeedbackProvider implements ScrollFeedbackProvider {
    private static final boolean INITIAL_END_OF_LIST_HAPTICS_ENABLED = false;
    private static final String TAG = "HapticScrollFeedbackProvider";
    private static final int TICK_INTERVAL_NO_TICK = 0;
    private int mAxis;
    private boolean mCanPlayLimitFeedback;
    private int mDeviceId;
    private final boolean mDisabledIfViewPlaysScrollHaptics;
    private boolean mHapticScrollFeedbackEnabled;
    private int mSource;
    private int mTickIntervalPixels;
    private int mTotalScrollPixels;
    private final View mView;
    private final ViewConfiguration mViewConfig;

    public HapticScrollFeedbackProvider(View view) {
        this(view, ViewConfiguration.get(view.getContext()), true);
    }

    public HapticScrollFeedbackProvider(
            View view, ViewConfiguration viewConfig, boolean disabledIfViewPlaysScrollHaptics) {
        this.mDeviceId = -1;
        this.mAxis = -1;
        this.mSource = -1;
        this.mTickIntervalPixels = 0;
        this.mTotalScrollPixels = 0;
        this.mCanPlayLimitFeedback = false;
        this.mHapticScrollFeedbackEnabled = false;
        this.mView = view;
        this.mViewConfig = viewConfig;
        this.mDisabledIfViewPlaysScrollHaptics = disabledIfViewPlaysScrollHaptics;
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollProgress(int inputDeviceId, int source, int axis, int deltaInPixels) {
        maybeUpdateCurrentConfig(inputDeviceId, source, axis);
        if (!this.mHapticScrollFeedbackEnabled) {
            return;
        }
        if (deltaInPixels != 0) {
            this.mCanPlayLimitFeedback = true;
        }
        if (this.mTickIntervalPixels == 0) {
            return;
        }
        this.mTotalScrollPixels += deltaInPixels;
        if (Math.abs(this.mTotalScrollPixels) >= this.mTickIntervalPixels) {
            this.mTotalScrollPixels %= this.mTickIntervalPixels;
            this.mView.performHapticFeedback(18);
        }
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollLimit(int inputDeviceId, int source, int axis, boolean isStart) {
        maybeUpdateCurrentConfig(inputDeviceId, source, axis);
        if (!this.mHapticScrollFeedbackEnabled || !this.mCanPlayLimitFeedback) {
            return;
        }
        this.mView.performHapticFeedback(20);
        this.mCanPlayLimitFeedback = false;
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onSnapToItem(int inputDeviceId, int source, int axis) {
        maybeUpdateCurrentConfig(inputDeviceId, source, axis);
        if (!this.mHapticScrollFeedbackEnabled) {
            return;
        }
        this.mView.performHapticFeedback(19);
        this.mCanPlayLimitFeedback = true;
    }

    private void maybeUpdateCurrentConfig(int deviceId, int source, int axis) {
        if (this.mAxis != axis || this.mSource != source || this.mDeviceId != deviceId) {
            if (this.mDisabledIfViewPlaysScrollHaptics
                    && source == 4194304
                    && this.mViewConfig.isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
                this.mHapticScrollFeedbackEnabled = false;
                return;
            }
            this.mSource = source;
            this.mAxis = axis;
            this.mDeviceId = deviceId;
            this.mHapticScrollFeedbackEnabled =
                    this.mViewConfig.isHapticScrollFeedbackEnabled(deviceId, axis, source);
            this.mCanPlayLimitFeedback = false;
            this.mTotalScrollPixels = 0;
            updateTickIntervals(deviceId, source, axis);
        }
    }

    private void updateTickIntervals(int deviceId, int source, int axis) {
        int i;
        if (this.mHapticScrollFeedbackEnabled) {
            i = this.mViewConfig.getHapticScrollFeedbackTickInterval(deviceId, axis, source);
        } else {
            i = 0;
        }
        this.mTickIntervalPixels = i;
    }
}
