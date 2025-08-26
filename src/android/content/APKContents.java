package android.content;

import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.view.DisplayAdjustments;
import java.io.IOException;

/* loaded from: classes.dex */
public class APKContents {
    public static final String MAIN_PACKAGE_DIR = "/data/overlays/main_packages/";
    private AssetManager mAssetManager;
    private Resources mResources;

    public APKContents(String str) {
        try {
            Trace.traceBegin(8192L, "APKContents#Constructor for " + str);
            ApkAssets apkAssetsLoadFromPath = ApkAssets.loadFromPath(str);
            AssetManager.Builder builder = new AssetManager.Builder();
            builder.addApkAssets(apkAssetsLoadFromPath);
            this.mAssetManager = builder.build();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            ResourcesImpl resourcesImpl = new ResourcesImpl(this.mAssetManager, displayMetrics, configuration, new DisplayAdjustments());
            Resources resources = new Resources(ClassLoader.getSystemClassLoader());
            this.mResources = resources;
            resources.setImpl(resourcesImpl);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public AssetManager getAssets() {
        return this.mAssetManager;
    }

    public Resources getResources() {
        return this.mResources;
    }

    public static String getMainThemePackagePath(String str) {
        return MAIN_PACKAGE_DIR + str + ".apk";
    }

    public static String getCurrentThemePackagePath(String str) {
        return "/data/overlays/currentstyle/" + str + ".apk";
    }
}
