package android.content.pm;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class FallbackCategoryProvider {
    private static final String TAG = "FallbackCategoryProvider";
    private static final ArrayMap<String, Integer> sFallbacks = new ArrayMap<>();

    public static void loadFallbacks() throws IOException {
        sFallbacks.clear();
        if (SystemProperties.getBoolean("fw.ignore_fb_categories", false)) {
            Log.d(TAG, "Ignoring fallback categories");
            return;
        }
        AssetManager assetManager = new AssetManager();
        assetManager.addAssetPath("/system/framework/framework-res.apk");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new Resources(assetManager, null, null).openRawResource(R.raw.fallback_categories)));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        if (line.charAt(0) != '#') {
                            String[] strArrSplit = line.split(",");
                            if (strArrSplit.length == 2) {
                                sFallbacks.put(strArrSplit[0], Integer.valueOf(Integer.parseInt(strArrSplit[1])));
                            }
                        }
                    } else {
                        Log.d(TAG, "Found " + sFallbacks.size() + " fallback categories");
                        bufferedReader.close();
                        return;
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (IOException | NumberFormatException e) {
            Log.w(TAG, "Failed to read fallback categories", e);
        }
    }

    public static int getFallbackCategory(String str) {
        return sFallbacks.getOrDefault(str, -1).intValue();
    }
}
