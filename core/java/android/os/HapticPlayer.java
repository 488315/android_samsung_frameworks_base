package android.os;

import android.app.ActivityThread;
import android.content.Context;
import android.os.IVibratorManagerService;
import android.os.VibrationAttributes;
import android.util.Log;
import com.samsung.android.vibrator.VibrationTag;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final int mVibratorGroup = getVibratorGroup();
    private DynamicEffect mEffect;
    private int mLoop;
    private final int mStepCount;
    private List<StepParameter> mStepParameters;
    private final Binder mToken;

    static {
        mAvailable = mVibratorGroup > 1;
    }

    private HapticPlayer() {
        this.mLoop = 1;
        this.mToken = new Binder();
        this.mStepCount = getStepCount();
    }

    public HapticPlayer(DynamicEffect effect) {
        this();
        this.mEffect = effect;
    }

    public static boolean isAvailable() {
        return mAvailable;
    }

    private int getStepCount() {
        if (mVibratorGroup == 2 || mVibratorGroup == 3) {
            return 4;
        }
        return 1;
    }

    private static int getVibratorGroup() {
        if (mService == null) {
            Log.w(TAG, "Failed to getVibratorGroup; no service.");
            return 0;
        }
        try {
            int vibratorGroup = mService.getSupportedVibratorGroup();
            return vibratorGroup;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to getVibratorGroup.", e);
            return 0;
        }
    }

    public void start(int loop) {
        start(loop, -1, -1, -1, true);
    }

    public void start(int loop, int interval, int amplitude) {
        start(loop, interval, amplitude, -1, true);
    }

    public void start(int loop, int interval, int amplitude, int freq) {
        start(loop, interval, amplitude, -1, true);
    }

    private void start(int loop, final int interval, final int amplitude, final int freq, final boolean needParseEffect) {
        if (!mAvailable || mService == null || this.mEffect == null) {
            Log.w(TAG, "Failed to start vibrate; no support, no service or no effect info.");
            return;
        }
        if (!checkParameters(interval, amplitude, freq)) {
            Log.w(TAG, "Failed to start vibrate; invalid interval, amplitude or frequency.");
            return;
        }
        if (loop == -1 || loop >= 128) {
            this.mLoop = 128;
        } else {
            this.mLoop = Math.max(loop, 1);
        }
        new Thread(new Runnable() { // from class: android.os.HapticPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                HapticPlayer.this.stop();
                if (HapticPlayer.this.mStepParameters == null) {
                    HapticPlayer.this.mStepParameters = new ArrayList();
                }
                if (needParseEffect) {
                    HapticPlayer.this.mStepParameters.clear();
                    List<RampParameter> rampParameters = HapticPlayer.this.parseRamp(HapticPlayer.this.mEffect.getEffectInfo());
                    if (rampParameters != null) {
                        for (RampParameter parameter : rampParameters) {
                            HapticPlayer.this.mStepParameters.addAll(HapticPlayer.this.rampToStepParameter(parameter));
                        }
                    }
                }
                VibrationAttributes.Builder builder = new VibrationAttributes.Builder().setUsage(18);
                if ("com.samsung.android.game.gametools".equals(ActivityThread.currentPackageName())) {
                    builder.semAddTag(VibrationTag.ALLOWED_IN_BACKGROUND_PROCESS);
                }
                VibrationEffect effect = HapticPlayer.this.createStepEffect(HapticPlayer.this.mStepParameters, interval, amplitude, freq);
                String reason = "DynamicEffect_" + HapticPlayer.this.mLoop;
                try {
                    HapticPlayer.mService.vibrate(Process.myUid(), 0, ActivityThread.currentPackageName(), CombinedVibration.createParallel(effect), builder.build(), reason, HapticPlayer.this.mToken);
                } catch (RemoteException e) {
                    Log.w(HapticPlayer.TAG, "Failed to start vibrate.", e);
                }
            }
        }, "DynamicEffectThread").start();
    }

    public void updateInterval(int interval) {
        update(interval, -1, -1, false);
    }

    public void updateAmplitude(int amplitude) {
        update(-1, amplitude, -1, false);
    }

    public void updateFrequency(int freq) {
        update(-1, -1, freq, false);
    }

    public void updateParameter(int interval, int amplitude, int freq) {
        update(interval, amplitude, freq, true);
    }

    private void update(int interval, int amplitude, int freq, boolean needCheck) {
        if (needCheck && (interval == -1 || amplitude == -1)) {
            Log.w(TAG, "Fail to update.");
        } else {
            start(this.mLoop, interval, amplitude, freq, false);
        }
    }

    public void stop() {
        if (!mAvailable || mService == null) {
            Log.w(TAG, "Failed to stop vibrate; no support or no service.");
            return;
        }
        try {
            mService.cancelVibrate(-1, this.mToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to stop vibrate.", e);
        }
    }

    private boolean checkParameters(int interval, int amplitude, int freq) {
        if ((interval < 0 && interval != -1) || interval > 1000) {
            return false;
        }
        if ((amplitude < 1 && amplitude != -1) || amplitude > 255) {
            return false;
        }
        if (freq < 0 && freq != -1) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VibrationEffect createStepEffect(List<StepParameter> parameters, int interval, int amplitude, int freq) {
        if (parameters == null) {
            return null;
        }
        int size = parameters.size();
        float[] amplitudes = new float[size];
        float[] frequencies = new float[size];
        int[] durations = new int[size];
        float amplitudeScale = 1.0f;
        if (amplitude >= 1 && amplitude <= 255) {
            amplitudeScale = amplitude / 255.0f;
        }
        for (int i = 0; i < size; i++) {
            StepParameter parameter = parameters.get(i);
            durations[i] = Math.max(Math.min(parameter.getDuration(), 5000), 0);
            amplitudes[i] = Math.max(Math.min(parameter.getAmplitude() * amplitudeScale, 1.0f), 0.0f);
            frequencies[i] = Math.max(Math.min(parameter.getFrequency() * 1.0f, 4.0f), 0.0f);
        }
        if (interval >= 0 && interval <= 1000) {
            durations[size - 1] = interval;
        }
        return VibrationEffect.createWaveform(durations, amplitudes, frequencies, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<RampParameter> parseRamp(String json) {
        String str;
        JSONArray patternArray;
        int i;
        String str2;
        String str3 = "Frequency";
        if (json == null || json.isEmpty()) {
            Log.w(TAG, "parseRamp: invalid JsonString.");
            return null;
        }
        List<RampParameter> parameters = new ArrayList<>();
        try {
            JSONArray patternArray2 = new JSONObject(json).getJSONArray("Pattern");
            int curRelativeTime = 0;
            int i2 = 0;
            while (i2 < patternArray2.length()) {
                JSONObject eventJson = patternArray2.getJSONObject(i2).getJSONObject("Event");
                String type = eventJson.getString("Type");
                int relativeTime = eventJson.getInt("RelativeTime");
                JSONObject paramJson = eventJson.getJSONObject("Parameters");
                int intensity = paramJson.getInt("Intensity");
                int frequency = paramJson.getInt(str3);
                if (relativeTime != curRelativeTime) {
                    parameters.add(new RampParameter(0.0f, 0.0f, 0.0f, 0.0f, Math.abs(relativeTime - curRelativeTime)));
                }
                if ("continuous".equals(type)) {
                    JSONArray jsonArray = paramJson.getJSONArray("Curve");
                    int length = jsonArray.length();
                    int startTime = 0;
                    int endTime = 0;
                    int startFrequency = 0;
                    float startIntensity = 0.0f;
                    int ii = 0;
                    while (true) {
                        int length2 = length;
                        if (ii >= length2) {
                            break;
                        }
                        JSONObject jsonObject = (JSONObject) jsonArray.get(ii);
                        JSONArray jsonArray2 = jsonArray;
                        JSONArray patternArray3 = patternArray2;
                        endTime = jsonObject.getInt("Time");
                        int curRelativeTime2 = curRelativeTime;
                        int i3 = i2;
                        float endIntensity = (float) jsonObject.getDouble("Intensity");
                        int endFrequency = jsonObject.getInt(str3);
                        if (ii <= 0) {
                            str2 = str3;
                        } else {
                            str2 = str3;
                            parameters.add(new RampParameter((intensity * startIntensity) / 100.0f, (intensity * endIntensity) / 100.0f, (startFrequency + frequency) / 25.0f, (endFrequency + frequency) / 25.0f, endTime - startTime));
                        }
                        startTime = endTime;
                        startIntensity = endIntensity;
                        startFrequency = endFrequency;
                        ii++;
                        i2 = i3;
                        patternArray2 = patternArray3;
                        str3 = str2;
                        curRelativeTime = curRelativeTime2;
                        jsonArray = jsonArray2;
                        length = length2;
                    }
                    str = str3;
                    patternArray = patternArray2;
                    i = i2;
                    int curRelativeTime3 = relativeTime + endTime;
                    curRelativeTime = curRelativeTime3;
                } else {
                    str = str3;
                    patternArray = patternArray2;
                    int curRelativeTime4 = curRelativeTime;
                    i = i2;
                    if (!"transient".equals(type)) {
                        curRelativeTime = curRelativeTime4;
                    } else {
                        parameters.add(new RampParameter(intensity / 100.0f, intensity / 100.0f, frequency / 25.0f, frequency / 25.0f, 20));
                        curRelativeTime = relativeTime + 20;
                    }
                }
                i2 = i + 1;
                patternArray2 = patternArray;
                str3 = str;
            }
            parameters.add(new RampParameter(0.0f, 0.0f, 0.0f, 0.0f, 50));
        } catch (JSONException e) {
            Log.w(TAG, "parseRamp: Failed to parse json string.", e);
        }
        return parameters;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<StepParameter> rampToStepParameter(RampParameter parameter) {
        float startAmplitude = parameter.getStartAmplitude();
        float endAmplitude = parameter.getEndAmplitude();
        float startFrequency = parameter.getStartFrequency();
        float endFrequency = parameter.getEndFrequency();
        int rampDuration = parameter.getDuration();
        if (Float.compare(startAmplitude, endAmplitude) == 0) {
            return Collections.singletonList(new StepParameter(endAmplitude, endFrequency, rampDuration));
        }
        int stepCount = Math.max(Math.min(this.mStepCount, rampDuration / 5), 1);
        int stepDuration = rampDuration / stepCount;
        List<StepParameter> steps = new ArrayList<>();
        for (int i = 1; i < stepCount; i++) {
            float pos = i / stepCount;
            steps.add(new StepParameter(interpolate(startAmplitude, endAmplitude, pos), interpolate(startFrequency, endFrequency, pos), stepDuration));
        }
        steps.add(new StepParameter(endAmplitude, endFrequency, rampDuration - ((stepCount - 1) * stepDuration)));
        return steps;
    }

    private float interpolate(float start, float end, float position) {
        return ((end - start) * position) + start;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    private static class StepParameter {
        private final float amplitude;
        private final int duration;
        private final float frequency;

        StepParameter(float amplitude, float frequency, int duration) {
            this.amplitude = amplitude;
            this.frequency = frequency;
            this.duration = duration;
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

        RampParameter(float startAmplitude, float endAmplitude, float startFrequency, float endFrequency, int duration) {
            this.startAmplitude = startAmplitude;
            this.endAmplitude = endAmplitude;
            this.startFrequency = startFrequency;
            this.endFrequency = endFrequency;
            this.duration = duration;
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
