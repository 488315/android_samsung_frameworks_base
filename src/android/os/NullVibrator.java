package android.os;

/* loaded from: classes3.dex */
public class NullVibrator extends Vibrator {
    private static final NullVibrator sInstance = new NullVibrator();

    @Override // android.os.Vibrator
    public void cancel() {
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return false;
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        return false;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        return false;
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, String str, VibrationEffect vibrationEffect, String str2, VibrationAttributes vibrationAttributes) {
    }

    private NullVibrator() {
    }

    public static NullVibrator getInstance() {
        return sInstance;
    }
}
