package android.hardware.input;

import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.CombinedVibration;
import android.os.NullVibrator;
import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.SparseArray;
import android.view.InputDevice;
import com.samsung.android.vibrator.VibrationDebugInfo;

/* loaded from: classes2.dex */
public class InputDeviceVibratorManager extends VibratorManager implements InputManager.InputDeviceListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "InputDeviceVibratorManager";
    private final int mDeviceId;
    private final SparseArray<Vibrator> mVibrators = new SparseArray<>();
    private final InputManagerGlobal mGlobal = InputManagerGlobal.getInstance();
    private final Binder mToken = new Binder();

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
    }

    @Override // android.os.VibratorManager
    public int semGetNumberOfSupportedPatterns() {
        return 0;
    }

    @Override // android.os.VibratorManager
    public int semGetSupportedVibrationType() {
        return 0;
    }

    public InputDeviceVibratorManager(int i) {
        this.mDeviceId = i;
        initializeVibrators();
    }

    private void initializeVibrators() {
        synchronized (this.mVibrators) {
            this.mVibrators.clear();
            InputDevice.getDevice(this.mDeviceId);
            int[] vibratorIds = this.mGlobal.getVibratorIds(this.mDeviceId);
            for (int i = 0; i < vibratorIds.length; i++) {
                this.mVibrators.put(vibratorIds[i], new InputDeviceVibrator(this.mDeviceId, vibratorIds[i]));
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        synchronized (this.mVibrators) {
            if (i == this.mDeviceId) {
                this.mVibrators.clear();
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        if (i == this.mDeviceId) {
            initializeVibrators();
        }
    }

    @Override // android.os.VibratorManager
    public int[] getVibratorIds() {
        int[] iArr;
        synchronized (this.mVibrators) {
            iArr = new int[this.mVibrators.size()];
            for (int i = 0; i < this.mVibrators.size(); i++) {
                iArr[i] = this.mVibrators.keyAt(i);
            }
        }
        return iArr;
    }

    @Override // android.os.VibratorManager
    public Vibrator getVibrator(int i) {
        synchronized (this.mVibrators) {
            if (this.mVibrators.contains(i)) {
                return this.mVibrators.get(i);
            }
            return NullVibrator.getInstance();
        }
    }

    @Override // android.os.VibratorManager
    public Vibrator getDefaultVibrator() {
        synchronized (this.mVibrators) {
            if (this.mVibrators.size() > 0) {
                return this.mVibrators.valueAt(0);
            }
            return NullVibrator.getInstance();
        }
    }

    @Override // android.os.VibratorManager
    public void vibrate(int i, String str, CombinedVibration combinedVibration, String str2, VibrationAttributes vibrationAttributes) {
        this.mGlobal.vibrate(this.mDeviceId, combinedVibration, this.mToken);
    }

    @Override // android.os.VibratorManager
    public void cancel() {
        this.mGlobal.cancelVibrate(this.mDeviceId, this.mToken);
    }

    @Override // android.os.VibratorManager
    public void cancel(int i) {
        cancel();
    }

    @Override // android.os.VibratorManager
    public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) {
        return "";
    }
}
