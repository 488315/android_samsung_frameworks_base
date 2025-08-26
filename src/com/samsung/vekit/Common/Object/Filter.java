package com.samsung.vekit.Common.Object;

import android.media.quality.ParameterCapability;
import android.util.Log;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FilterType;
import com.samsung.vekit.Common.VEContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Filter extends Element {
    private static final String AI_GRAIN_TEMPLATE_IMAGE = "/system/cameradata/preloadfilters/grain_patch_strong.png";
    private static final String GRAIN_TEMPLATE_IMAGE = "/system/cameradata/myfilter/GrainTemplateImage";
    private static final int NOISE_TYPE_GRAY = 1;
    private static final int NOISE_TYPE_NONE = 0;
    private static final int NOISE_TYPE_RGB = 3;
    private int aiGrainInitialPower;
    private float aiGrainIntensity;
    private float aiGrainRadius;
    private int aiGrainStyle;
    private IntensityInfo aiIntensityInfo;
    private String auxPath;
    private boolean enableAiGrain;
    private boolean enableGrain;
    private boolean enableVignette;
    private FilterType filterType;
    private String grainPath;
    private float grainPower;
    private float grainRadius;
    private String jsonPath;
    private float noiseIntensity;
    private int noiseType;
    private String path;

    public Filter(VEContext vEContext, int i, String str, String str2) {
        super(vEContext, ElementType.FILTER, i, str);
        this.filterType = FilterType.LUT;
        this.grainPower = 0.0f;
        this.grainRadius = 0.0f;
        this.noiseType = 0;
        this.noiseIntensity = 0.0f;
        this.enableVignette = false;
        this.enableGrain = false;
        this.enableAiGrain = false;
        this.aiIntensityInfo = new IntensityInfo();
        this.aiGrainRadius = 0.95f;
        this.aiGrainStyle = 1;
        this.aiGrainInitialPower = 36;
        this.aiGrainIntensity = 4.0f;
        this.TAG = getClass().getSimpleName();
        this.id = i;
        this.name = str;
        setPath(str2);
    }

    private void setPath(String str) {
        this.path = str;
        if (str.isEmpty()) {
            Log.e(this.TAG, "filterPath is Empty.");
            return;
        }
        if (!new File(str).exists()) {
            Log.e(this.TAG, "filterPath doesn't exist.");
            return;
        }
        this.enableAiGrain = new File(AI_GRAIN_TEMPLATE_IMAGE).exists();
        String str2 = str.substring(0, str.lastIndexOf(46)) + ".json";
        this.jsonPath = str2;
        if (parseJson(str2)) {
            return;
        }
        Log.e(this.TAG, "Parse failed");
    }

    private boolean parseJson(String str) throws JSONException, IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), StandardCharsets.UTF_8));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    try {
                        break;
                    } catch (JSONException e) {
                        Log.e(this.TAG, "parseJson: ", e);
                        return false;
                    }
                }
                sb.append(line);
            }
            JSONObject jSONObject = new JSONObject(sb.toString());
            String string = jSONObject.getString("filter_type");
            if ("basic".equals(string)) {
                this.filterType = FilterType.NOISE;
                this.noiseIntensity = (float) jSONObject.getDouble("noise_intensity");
                String string2 = jSONObject.getString("noise_color");
                if ("gray".equals(string2)) {
                    this.noiseType = 1;
                } else if ("rgb".equals(string2)) {
                    this.noiseType = 3;
                } else {
                    this.noiseType = 0;
                }
            } else if ("myfilter_effect".equals(string)) {
                this.filterType = FilterType.MY_FILTER;
                this.auxPath = str.substring(0, str.lastIndexOf(46)) + ".aux";
                this.enableVignette = new File(this.auxPath).exists();
                this.grainPath = GRAIN_TEMPLATE_IMAGE;
                this.enableGrain = new File(this.grainPath).exists();
                this.grainPower = (float) jSONObject.getDouble("grain_power");
                this.grainRadius = (float) jSONObject.getDouble("grain_radius");
            } else if ("ai_myfilter_effect".equals(string)) {
                this.filterType = FilterType.AI_MY_FILTER;
                this.auxPath = str.substring(0, str.lastIndexOf(46)) + ".aux";
                this.enableVignette = new File(this.auxPath).exists();
                JSONObject jSONObject2 = jSONObject.getJSONObject("ai_grain_power");
                this.aiIntensityInfo.setMin(jSONObject2.getInt(ParameterCapability.CAPABILITY_MIN));
                this.aiIntensityInfo.setMax(jSONObject2.getInt("max"));
                this.aiIntensityInfo.setBase(jSONObject2.getInt("default"));
                this.aiIntensityInfo.setStep(jSONObject2.getInt("step"));
                this.aiGrainRadius = (float) jSONObject.getDouble("ai_grain_radius");
                this.aiGrainStyle = jSONObject.getInt("ai_grain_style");
                this.aiGrainInitialPower = jSONObject.getInt("ai_grain_initial_power");
                this.aiGrainIntensity = jSONObject.getInt("ai_grain_intensity");
            } else {
                this.filterType = FilterType.LUT;
                JSONObject jSONObject3 = jSONObject.getJSONObject("ai_grain_power");
                this.aiIntensityInfo.setMin(jSONObject3.getInt(ParameterCapability.CAPABILITY_MIN));
                this.aiIntensityInfo.setMax(jSONObject3.getInt("max"));
                this.aiIntensityInfo.setBase(jSONObject3.getInt("default"));
                this.aiIntensityInfo.setStep(jSONObject3.getInt("step"));
                this.aiGrainRadius = (float) jSONObject.getDouble("ai_grain_radius");
                this.aiGrainStyle = jSONObject.getInt("ai_grain_style");
                this.aiGrainInitialPower = jSONObject.getInt("ai_grain_initial_power");
            }
            return true;
        } catch (IOException e2) {
            Log.e(this.TAG, e2.getMessage(), e2);
            return false;
        }
    }

    public String getPath() {
        return this.path;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }

    public String getJsonPath() {
        return this.jsonPath;
    }

    public String getGrainPath() {
        return this.grainPath;
    }

    public String getAuxPath() {
        return this.auxPath;
    }

    public float getGrainPower() {
        return this.grainPower;
    }

    public float getGrainRadius() {
        return this.grainRadius;
    }

    public int getNoiseType() {
        return this.noiseType;
    }

    public float getNoiseIntensity() {
        return this.noiseIntensity;
    }

    public boolean isEnableVignette() {
        return this.enableVignette;
    }

    public boolean isEnableGrain() {
        return this.enableGrain;
    }

    public boolean isEnableAiGrain() {
        return this.enableAiGrain;
    }

    public void setEnableAiGrain(boolean z) {
        this.enableAiGrain = z;
    }

    public IntensityInfo getAiGrainPower() {
        return this.aiIntensityInfo;
    }

    public void setAiGrainPower(IntensityInfo intensityInfo) {
        this.aiIntensityInfo = intensityInfo;
    }

    public float getAiGrainRadius() {
        return this.aiGrainRadius;
    }

    public void setAiGrainRadius(float f) {
        this.aiGrainRadius = f;
    }

    public int getAiGrainStyle() {
        return this.aiGrainStyle;
    }

    public void setAiGrainStyle(int i) {
        this.aiGrainStyle = i;
    }

    public int getAiGrainInitialPower() {
        return this.aiGrainInitialPower;
    }

    public void setAiGrainInitialPower(int i) {
        this.aiGrainInitialPower = i;
    }

    public float getAiGrainIntensity() {
        return this.aiGrainIntensity;
    }

    public void setAiGrainIntensity(float f) {
        this.aiGrainIntensity = f;
    }
}
