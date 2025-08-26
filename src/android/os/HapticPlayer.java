package android.os;

import android.app.ActivityThread;
import android.content.Context;
import android.os.IVibratorManagerService;
import android.os.VibrationAttributes;
import android.util.Log;
import com.samsung.android.vibrator.VibrationTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class HapticPlayer implements AutoCloseable {
    private static final int DEFAULT_PARAM = -1;
    private static final int DYNAMIC_DEFAULT_INTERVAL = 50;
    private static final int DYNAMIC_MAX_AMPLITUDE = 100;
    private static final int DYNAMIC_MAX_DURATION = 5000;
    private static final int DYNAMIC_MAX_FREQUENCY = 25;
    private static final int DYNAMIC_MIN_DURATION = 5;
    private static final int DYNAMIC_STEP_COUNT_TYPE_B = 4;
    private static final int DYNAMIC_STEP_COUNT_TYPE_C = 4;
    private static final int DYNAMIC_STEP_COUNT_TYPE_D = 1;
    private static final int DYNAMIC_TRANSIENT_DURATION = 20;
    private static final String TAG = "HapticPlayer";
    private static final boolean mAvailable;
    private static final IVibratorManagerService mService = IVibratorManagerService.Stub.asInterface(ServiceManager.getService(Context.VIBRATOR_MANAGER_SERVICE));
    private static final int mVibratorGroup;
    private DynamicEffect mEffect;
    private int mLoop;
    private final int mStepCount;
    private List<StepParameter> mStepParameters;
    private final Binder mToken;

    private boolean checkParameters(int i, int i2, int i3) {
        if ((i < 0 && i != -1) || i > 1000) {
            return false;
        }
        if ((i2 >= 1 || i2 == -1) && i2 <= 255) {
            return i3 >= 0 || i3 == -1;
        }
        return false;
    }

    private float interpolate(float f, float f2, float f3) {
        return f + (f3 * (f2 - f));
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    static {
        int vibratorGroup = getVibratorGroup();
        mVibratorGroup = vibratorGroup;
        mAvailable = vibratorGroup > 1;
    }

    private HapticPlayer() {
        this.mLoop = 1;
        this.mToken = new Binder();
        this.mStepCount = getStepCount();
    }

    public HapticPlayer(DynamicEffect dynamicEffect) {
        this();
        this.mEffect = dynamicEffect;
    }

    public static boolean isAvailable() {
        return mAvailable;
    }

    private int getStepCount() {
        int i = mVibratorGroup;
        return (i == 2 || i == 3) ? 4 : 1;
    }

    private static int getVibratorGroup() {
        IVibratorManagerService iVibratorManagerService = mService;
        if (iVibratorManagerService == null) {
            Log.w(TAG, "Failed to getVibratorGroup; no service.");
            return 0;
        }
        try {
            return iVibratorManagerService.getSupportedVibratorGroup();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to getVibratorGroup.", e);
            return 0;
        }
    }

    public void start(int i) {
        start(i, -1, -1, -1, true);
    }

    public void start(int i, int i2, int i3) {
        start(i, i2, i3, -1, true);
    }

    public void start(int i, int i2, int i3, int i4) {
        start(i, i2, i3, -1, true);
    }

    private void start(int i, final int i2, final int i3, final int i4, final boolean z) {
        if (!mAvailable || mService == null || this.mEffect == null) {
            Log.w(TAG, "Failed to start vibrate; no support, no service or no effect info.");
            return;
        }
        if (!checkParameters(i2, i3, i4)) {
            Log.w(TAG, "Failed to start vibrate; invalid interval, amplitude or frequency.");
            return;
        }
        if (i == -1 || i >= 128) {
            this.mLoop = 128;
        } else {
            this.mLoop = Math.max(i, 1);
        }
        new Thread(new Runnable() { // from class: android.os.HapticPlayer.1
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                HapticPlayer.this.stop();
                if (HapticPlayer.this.mStepParameters == null) {
                    HapticPlayer.this.mStepParameters = new ArrayList();
                }
                if (z) {
                    HapticPlayer.this.mStepParameters.clear();
                    HapticPlayer hapticPlayer = HapticPlayer.this;
                    List ramp = hapticPlayer.parseRamp(hapticPlayer.mEffect.getEffectInfo());
                    if (ramp != null) {
                        Iterator it = ramp.iterator();
                        while (it.hasNext()) {
                            HapticPlayer.this.mStepParameters.addAll(HapticPlayer.this.rampToStepParameter((RampParameter) it.next()));
                        }
                    } else {
                        Log.w(HapticPlayer.TAG, "Failed to parse effect.");
                        return;
                    }
                }
                VibrationAttributes.Builder usage = new VibrationAttributes.Builder().setUsage(18);
                if ("com.samsung.android.game.gametools".equals(ActivityThread.currentPackageName())) {
                    usage.semAddTag(VibrationTag.ALLOWED_IN_BACKGROUND_PROCESS);
                }
                HapticPlayer hapticPlayer2 = HapticPlayer.this;
                VibrationEffect vibrationEffectCreateStepEffect = hapticPlayer2.createStepEffect(hapticPlayer2.mStepParameters, i2, i3, i4);
                if (vibrationEffectCreateStepEffect == null) {
                    return;
                }
                try {
                    HapticPlayer.mService.vibrate(Process.myUid(), 0, ActivityThread.currentPackageName(), CombinedVibration.createParallel(vibrationEffectCreateStepEffect), usage.build(), "DynamicEffect_" + HapticPlayer.this.mLoop, HapticPlayer.this.mToken);
                } catch (RemoteException e) {
                    Log.w(HapticPlayer.TAG, "Failed to start vibrate.", e);
                }
            }
        }, "DynamicEffectThread").start();
    }

    public void updateInterval(int i) {
        update(i, -1, -1, false);
    }

    public void updateAmplitude(int i) {
        update(-1, i, -1, false);
    }

    public void updateFrequency(int i) {
        update(-1, -1, i, false);
    }

    public void updateParameter(int i, int i2, int i3) {
        update(i, i2, i3, true);
    }

    private void update(int i, int i2, int i3, boolean z) {
        if (z && (i == -1 || i2 == -1)) {
            Log.w(TAG, "Fail to update.");
        } else {
            start(this.mLoop, i, i2, i3, false);
        }
    }

    public void stop() {
        IVibratorManagerService iVibratorManagerService;
        if (!mAvailable || (iVibratorManagerService = mService) == null) {
            Log.w(TAG, "Failed to stop vibrate; no support or no service.");
            return;
        }
        try {
            iVibratorManagerService.cancelVibrate(-1, this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to stop vibrate.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VibrationEffect createStepEffect(List<StepParameter> list, int i, int i2, int i3) {
        if (list == null || list.isEmpty()) {
            Log.w(TAG, "createStepEffect: parameters is null or empty.");
            return null;
        }
        int size = list.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        int[] iArr = new int[size];
        float f = (i2 < 1 || i2 > 255) ? 1.0f : i2 / 255.0f;
        for (int i4 = 0; i4 < size; i4++) {
            StepParameter stepParameter = list.get(i4);
            iArr[i4] = Math.max(Math.min(stepParameter.getDuration(), 5000), 0);
            fArr[i4] = Math.max(Math.min(stepParameter.getAmplitude() * f, 1.0f), 0.0f);
            fArr2[i4] = Math.max(Math.min(stepParameter.getFrequency() * 1.0f, 4.0f), 0.0f);
        }
        if (i >= 0 && i <= 1000) {
            iArr[size - 1] = i;
        }
        return VibrationEffect.createWaveform(iArr, fArr, fArr2, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<RampParameter> parseRamp(String str) throws JSONException {
        List<RampParameter> list;
        int i;
        if (str == null || str.isEmpty()) {
            Log.w(TAG, "parseRamp: invalid JsonString.");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("Pattern");
            int i2 = 0;
            int i3 = 0;
            while (i2 < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2).getJSONObject("Event");
                String string = jSONObject.getString("Type");
                int i4 = jSONObject.getInt("RelativeTime");
                JSONObject jSONObject2 = jSONObject.getJSONObject("Parameters");
                int i5 = jSONObject2.getInt("Intensity");
                int i6 = jSONObject2.getInt("Frequency");
                if (i4 != i3) {
                    arrayList.add(new RampParameter(0.0f, 0.0f, 0.0f, 0.0f, Math.abs(i4 - i3)));
                }
                if ("continuous".equals(string)) {
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("Curve");
                    int length = jSONArray2.length();
                    float f = 0.0f;
                    int i7 = 0;
                    int i8 = 0;
                    int i9 = 0;
                    int i10 = 0;
                    while (i7 < length) {
                        list = null;
                        try {
                            JSONObject jSONObject3 = (JSONObject) jSONArray2.get(i7);
                            i8 = jSONObject3.getInt("Time");
                            int i11 = i2;
                            float f2 = (float) jSONObject3.getDouble("Intensity");
                            int i12 = jSONObject3.getInt("Frequency");
                            if (i7 > 0) {
                                float f3 = i5;
                                arrayList.add(new RampParameter((f * f3) / 100.0f, (f3 * f2) / 100.0f, (i9 + i6) / 25.0f, (i12 + i6) / 25.0f, i8 - i10));
                            }
                            i7++;
                            i9 = i12;
                            f = f2;
                            i10 = i8;
                            i2 = i11;
                        } catch (JSONException e) {
                            e = e;
                            Log.w(TAG, "parseRamp: Failed to parse json string.", e);
                            return list;
                        }
                    }
                    i = i2;
                    i3 = i4 + i8;
                } else {
                    i = i2;
                    if ("transient".equals(string)) {
                        float f4 = i5 / 100.0f;
                        float f5 = i6 / 25.0f;
                        arrayList.add(new RampParameter(f4, f4, f5, f5, 20));
                        i3 = i4 + 20;
                    }
                }
                i2 = i + 1;
            }
            list = null;
            arrayList.add(new RampParameter(0.0f, 0.0f, 0.0f, 0.0f, 50));
            return arrayList;
        } catch (JSONException e2) {
            e = e2;
            list = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<StepParameter> rampToStepParameter(RampParameter rampParameter) {
        float startAmplitude = rampParameter.getStartAmplitude();
        float endAmplitude = rampParameter.getEndAmplitude();
        float startFrequency = rampParameter.getStartFrequency();
        float endFrequency = rampParameter.getEndFrequency();
        int duration = rampParameter.getDuration();
        if (Float.compare(startAmplitude, endAmplitude) == 0) {
            return Collections.singletonList(new StepParameter(endAmplitude, endFrequency, duration));
        }
        int iMax = Math.max(Math.min(this.mStepCount, duration / 5), 1);
        int i = duration / iMax;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 < iMax; i2++) {
            float f = i2 / iMax;
            arrayList.add(new StepParameter(interpolate(startAmplitude, endAmplitude, f), interpolate(startFrequency, endFrequency, f), i));
        }
        arrayList.add(new StepParameter(endAmplitude, endFrequency, duration - (i * (iMax - 1))));
        return arrayList;
    }

    private static class StepParameter {
        private final float amplitude;
        private final int duration;
        private final float frequency;

        StepParameter(float f, float f2, int i) {
            this.amplitude = f;
            this.frequency = f2;
            this.duration = i;
        }

        public int getDuration() {
            return this.duration;
        }

        public float getAmplitude() {
            return this.amplitude;
        }

        public float getFrequency() {
            return this.frequency;
        }
    }

    private static class RampParameter {
        private final int duration;
        private final float endAmplitude;
        private final float endFrequency;
        private final float startAmplitude;
        private final float startFrequency;

        RampParameter(float f, float f2, float f3, float f4, int i) {
            this.startAmplitude = f;
            this.endAmplitude = f2;
            this.startFrequency = f3;
            this.endFrequency = f4;
            this.duration = i;
        }

        public int getDuration() {
            return this.duration;
        }

        public float getStartAmplitude() {
            return this.startAmplitude;
        }

        public float getEndAmplitude() {
            return this.endAmplitude;
        }

        public float getStartFrequency() {
            return this.startFrequency;
        }

        public float getEndFrequency() {
            return this.endFrequency;
        }
    }
}
