package android.content;

import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.p009os.Trace;
import android.util.DisplayMetrics;
import android.view.DisplayAdjustments;
import java.io.IOException;

/* loaded from: classes.dex */
public class APKContents {
  public static final String MAIN_PACKAGE_DIR = "/data/overlays/main_packages/";
  private AssetManager mAssetManager;
  private Resources mResources;

  public APKContents(String path) {
    try {
      try {
        Trace.traceBegin(8192L, "APKContents#Constructor for " + path);
        ApkAssets apkAssets = ApkAssets.loadFromPath(path);
        AssetManager.Builder builder = new AssetManager.Builder();
        builder.addApkAssets(apkAssets);
        this.mAssetManager = builder.build();
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.setToDefaults();
        Configuration config = new Configuration();
        config.setToDefaults();
        ResourcesImpl mResourcesImpl =
            new ResourcesImpl(this.mAssetManager, metrics, config, new DisplayAdjustments());
        Resources resources = new Resources(ClassLoader.getSystemClassLoader());
        this.mResources = resources;
        resources.setImpl(mResourcesImpl);
      } catch (IOException e) {
        e.printStackTrace();
      }
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

  public static String getMainThemePackagePath(String pkgName) {
    return MAIN_PACKAGE_DIR + pkgName + ".apk";
  }
}
