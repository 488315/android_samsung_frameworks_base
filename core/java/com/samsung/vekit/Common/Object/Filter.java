package com.samsung.vekit.Common.Object;

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
  private static final String GRAIN_TEMPLATE_IMAGE =
      "/system/cameradata/myfilter/GrainTemplateImage";
  private static final int NOISE_TYPE_GRAY = 1;
  private static final int NOISE_TYPE_NONE = 0;
  private static final int NOISE_TYPE_RGB = 3;
  private String auxPath;
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
    this.f3097id = id;
    this.name = name;
    setPath(filterPath);
    this.TAG = getClass().getSimpleName();
  }

  private void setPath(String filterPath) {
    this.path = filterPath;
    if (filterPath.isEmpty()) {
      Log.m96e(this.TAG, "filterPath is Empty.");
      return;
    }
    File filterFile = new File(filterPath);
    if (!filterFile.exists()) {
      Log.m96e(this.TAG, "filterPath doesn't exist.");
      return;
    }
    String str = filterPath.substring(0, filterPath.lastIndexOf(46)) + ".json";
    this.jsonPath = str;
    if (!parseJson(str)) {
      Log.m96e(this.TAG, "Parse failed");
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
        if (line != null) {
          json.append(line);
        } else {
          try {
            break;
          } catch (JSONException e) {
            Log.m97e(this.TAG, "parseJson: ", e);
            return false;
          }
        }
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
      } else if ("myfilter_effect".equals(filterData)) {
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
      }
      return true;
    } catch (IOException e2) {
      Log.m97e(this.TAG, e2.getMessage(), e2);
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
}
