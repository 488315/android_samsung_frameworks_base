package com.samsung.vekit.Common.Object;

import android.util.Log;

import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FilterType;
import com.samsung.vekit.Common.VEContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Filter extends Element {
    private static final String GRAIN_TEMPLATE_IMAGE =
            "/system/cameradata/myfilter/GrainTemplateImage";
    private static final int NOISE_TYPE_GRAY = 1;
    private static final int NOISE_TYPE_NONE = 0;
    private static final int NOISE_TYPE_RGB = 3;
    private int aiGrainInitialPower;
    private float aiGrainIntensity;
    private float aiGrainRadius;
    private int aiGrainStyle;
    private ArrayList<String> aiGrainTextureList;
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

    public Filter(VEContext context, int id, String name, String filterPath) {
        super(context, ElementType.FILTER, id, name);
        this.filterType = FilterType.LUT;
        this.grainPower = 0.0f;
        this.grainRadius = 0.0f;
        this.noiseType = 0;
        this.noiseIntensity = 0.0f;
        this.enableVignette = false;
        this.enableGrain = false;
        this.enableAiGrain = false;
        this.aiGrainTextureList = new ArrayList<>();
        this.aiIntensityInfo = new IntensityInfo();
        this.aiGrainRadius = 0.7f;
        this.aiGrainStyle = 1;
        this.aiGrainInitialPower = 52;
        this.aiGrainIntensity = 4.0f;
        this.TAG = getClass().getSimpleName();
        this.id = id;
        this.name = name;
        this.aiGrainTextureList.add("/system/cameradata/preloadfilters/grain_patch_weak_1.png");
        this.aiGrainTextureList.add("/system/cameradata/preloadfilters/grain_patch_strong_4.png");
        this.aiGrainTextureList.add("/system/cameradata/preloadfilters/grain_patch_strong_6.png");
        setPath(filterPath);
    }

    private void setPath(String filterPath) {
        this.path = filterPath;
        if (filterPath.isEmpty()) {
            Log.e(this.TAG, "filterPath is Empty.");
            return;
        }
        File filterFile = new File(filterPath);
        if (!filterFile.exists()) {
            Log.e(this.TAG, "filterPath doesn't exist.");
            return;
        }
        if (!this.aiGrainTextureList.isEmpty()) {
            this.grainPath = this.aiGrainTextureList.get(0);
            File grainFile = new File(this.grainPath);
            this.enableAiGrain = grainFile.exists();
        }
        this.jsonPath = filterPath.substring(0, filterPath.lastIndexOf(46)) + ".json";
        if (!parseJson(this.jsonPath)) {
            Log.e(this.TAG, "Parse failed");
        }
    }

    private boolean parseJson(String jsonPath) {
        StringBuilder json = new StringBuilder();
        try {
            FileInputStream stream = new FileInputStream(jsonPath);
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(reader);
            while (true) {
                String line = buffer.readLine();
                if (line == null) {
                    try {
                        break;
                    } catch (JSONException e) {
                        Log.e(this.TAG, "parseJson: ", e);
                        return false;
                    }
                }
                json.append(line);
            }
            JSONObject obj = new JSONObject(json.toString());
            String filterData = obj.getString("filter_type");
            if ("basic".equals(filterData)) {
                this.filterType = FilterType.NOISE;
                this.noiseIntensity = (float) obj.getDouble("noise_intensity");
                String noiseType = obj.getString("noise_color");
                if ("gray".equals(noiseType)) {
                    this.noiseType = 1;
                } else if ("rgb".equals(noiseType)) {
                    this.noiseType = 3;
                } else {
                    this.noiseType = 0;
                }
                return true;
            }
            if ("myfilter_effect".equals(filterData)) {
                this.filterType = FilterType.MY_FILTER;
                String filePath = jsonPath.substring(0, jsonPath.lastIndexOf(46));
                this.auxPath = filePath + ".aux";
                File auxFile = new File(this.auxPath);
                this.enableVignette = auxFile.exists();
                this.grainPath = GRAIN_TEMPLATE_IMAGE;
                File grainFile = new File(this.grainPath);
                this.enableGrain = grainFile.exists();
                this.grainPower = (float) obj.getDouble("grain_power");
                this.grainRadius = (float) obj.getDouble("grain_radius");
                return true;
            }
            if ("ai_myfilter_effect".equals(filterData)) {
                this.filterType = FilterType.AI_MY_FILTER;
                String filePath2 = jsonPath.substring(0, jsonPath.lastIndexOf(46));
                this.auxPath = filePath2 + ".aux";
                File auxFile2 = new File(this.auxPath);
                this.enableVignette = auxFile2.exists();
                JSONObject grainPowerObject = obj.getJSONObject("ai_grain_power");
                this.aiIntensityInfo.setMin(grainPowerObject.getInt("min"));
                this.aiIntensityInfo.setMax(grainPowerObject.getInt("max"));
                this.aiIntensityInfo.setBase(grainPowerObject.getInt("default"));
                this.aiIntensityInfo.setStep(grainPowerObject.getInt("step"));
                this.aiGrainRadius = (float) obj.getDouble("ai_grain_radius");
                this.aiGrainStyle = obj.getInt("ai_grain_style");
                this.aiGrainInitialPower = obj.getInt("ai_grain_initial_power");
                this.aiGrainIntensity = obj.getInt("ai_grain_intensity");
                return true;
            }
            this.filterType = FilterType.LUT;
            JSONObject grainPowerObject2 = obj.getJSONObject("ai_grain_power");
            this.aiIntensityInfo.setMin(grainPowerObject2.getInt("min"));
            this.aiIntensityInfo.setMax(grainPowerObject2.getInt("max"));
            this.aiIntensityInfo.setBase(grainPowerObject2.getInt("default"));
            this.aiIntensityInfo.setStep(grainPowerObject2.getInt("step"));
            this.aiGrainRadius = (float) obj.getDouble("ai_grain_radius");
            this.aiGrainStyle = obj.getInt("ai_grain_style");
            this.aiGrainInitialPower = obj.getInt("ai_grain_initial_power");
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

    public void setEnableAiGrain(boolean enableAiGrain) {
        this.enableAiGrain = enableAiGrain;
    }

    public IntensityInfo getAiGrainPower() {
        return this.aiIntensityInfo;
    }

    public void setAiGrainPower(IntensityInfo aiIntensityInfo) {
        this.aiIntensityInfo = aiIntensityInfo;
    }

    public float getAiGrainRadius() {
        return this.aiGrainRadius;
    }

    public void setAiGrainRadius(float aiGrainRadius) {
        this.aiGrainRadius = aiGrainRadius;
    }

    public int getAiGrainStyle() {
        return this.aiGrainStyle;
    }

    public void setAiGrainStyle(int aiGrainStyle) {
        this.aiGrainStyle = aiGrainStyle;
    }

    public int getAiGrainInitialPower() {
        return this.aiGrainInitialPower;
    }

    public void setAiGrainInitialPower(int aiGrainInitialPower) {
        this.aiGrainInitialPower = aiGrainInitialPower;
    }

    public float getAiGrainIntensity() {
        return this.aiGrainIntensity;
    }

    public void setAiGrainIntensity(float aiGrainIntensity) {
        this.aiGrainIntensity = aiGrainIntensity;
    }
}
