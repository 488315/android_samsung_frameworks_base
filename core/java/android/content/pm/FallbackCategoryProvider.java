package android.content.pm;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.C4337R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class FallbackCategoryProvider {
  private static final String TAG = "FallbackCategoryProvider";
  private static final ArrayMap<String, Integer> sFallbacks = new ArrayMap<>();

  public static void loadFallbacks() {
    sFallbacks.clear();
    if (SystemProperties.getBoolean("fw.ignore_fb_categories", false)) {
      Log.m94d(TAG, "Ignoring fallback categories");
      return;
    }
    AssetManager assets = new AssetManager();
    assets.addAssetPath("/system/framework/framework-res.apk");
    Resources res = new Resources(assets, null, null);
    try {
      BufferedReader reader =
          new BufferedReader(
              new InputStreamReader(res.openRawResource(C4337R.raw.fallback_categories)));
      while (true) {
        try {
          String line = reader.readLine();
          if (line != null) {
            if (line.charAt(0) != '#') {
              String[] split = line.split(",");
              if (split.length == 2) {
                sFallbacks.put(split[0], Integer.valueOf(Integer.parseInt(split[1])));
              }
            }
          } else {
            Log.m94d(TAG, "Found " + sFallbacks.size() + " fallback categories");
            reader.close();
            return;
          }
        } catch (Throwable th) {
          try {
            reader.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
          throw th;
        }
      }
    } catch (IOException | NumberFormatException e) {
      Log.m103w(TAG, "Failed to read fallback categories", e);
    }
  }

  public static int getFallbackCategory(String packageName) {
    return sFallbacks.getOrDefault(packageName, -1).intValue();
  }
}
