package com.samsung.server.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.server.wallpaper.WallpaperData;
import com.samsung.android.wallpaper.utils.WhichChecker;
import java.io.File;

/* loaded from: classes2.dex */
public class PreloadedLiveWallpaperHelper {
    public Callback mCallback;
    public ProviderRequester mProviderRequester;
    public ThumbnailFetcher mThumbnailFetcher;

    public interface Callback {
        WallpaperData getWallpaperData(int i, int i2);
    }

    public PreloadedLiveWallpaperHelper(Context context, SemWallpaperManagerService semWallpaperManagerService, Callback callback) {
        this.mProviderRequester = new ProviderRequester(context);
        this.mThumbnailFetcher = new ThumbnailFetcher(context, semWallpaperManagerService, this.mProviderRequester, callback);
        this.mCallback = callback;
    }

    public File getThumbnailFile(int i, int i2, int i3) {
        if (WhichChecker.getMode(i) == 0) {
            Log.m134w("PreloadedLiveWallpaperHelper", "getThumbnailFile : mode is missing. which = " + i);
        }
        WallpaperData wallpaperData = getWallpaperData(i, i2);
        if (!isPreloadedLiveWallpaper(wallpaperData)) {
            Log.m129d("PreloadedLiveWallpaperHelper", "getThumbnailFile : not preloaded live wallpaper. which = " + i);
            return null;
        }
        int wallpaperId = wallpaperData.getWallpaperId();
        File thumbnailFile = this.mThumbnailFetcher.getThumbnailFile(i, i2, wallpaperData.getWallpaperComponent(), i3, getServiceSettings(wallpaperData));
        WallpaperData wallpaperData2 = getWallpaperData(i, i2);
        if (wallpaperId != wallpaperData2.getWallpaperId()) {
            Log.m134w("PreloadedLiveWallpaperHelper", "getThumbnailFile : wallpaper changed during fetching the thumbnail file.which=" + i + ", idBeforeFetch=" + wallpaperId + ", idAfterFetch=" + wallpaperData2.getWallpaperId());
            if (thumbnailFile != null && thumbnailFile.exists()) {
                Log.m132i("PreloadedLiveWallpaperHelper", "getThumbnailFile : deleting old thumbnail file. " + thumbnailFile.getAbsolutePath());
                thumbnailFile.delete();
            }
            return null;
        }
        if (thumbnailFile == null || thumbnailFile.exists()) {
            return thumbnailFile;
        }
        Log.m132i("PreloadedLiveWallpaperHelper", "getThumbnailFile : thumbnail file not exist. which=" + i + ", " + thumbnailFile.getAbsolutePath());
        return null;
    }

    public final Bundle getServiceSettings(WallpaperData wallpaperData) {
        return getServiceSettings(wallpaperData.getSemWallpaperData().getExternalParams());
    }

    public Bundle getServiceSettings(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getBundle("serviceSettings");
    }

    public Bundle requestWallpaperPrepare(ComponentName componentName, int i, int i2, Bundle bundle) {
        return convertSnakeCaseBundleToCamelCase(this.mProviderRequester.requestWallpaperPrepare(componentName, i, i2, bundle));
    }

    public boolean isPreloadedLiveWallpaper(WallpaperData wallpaperData) {
        ComponentName wallpaperComponent;
        if (wallpaperData.getSemWallpaperData().getWpType() == 7 && (wallpaperComponent = wallpaperData.getWallpaperComponent()) != null) {
            return isPreloadedLiveWallpaperComponent(wallpaperComponent);
        }
        return false;
    }

    public boolean isPreloadedLiveWallpaperComponent(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return "com.samsung.android.wallpaper.live".equals(componentName.getPackageName());
    }

    public boolean isSupportSystemAndLockPairOnly(ComponentName componentName) {
        if (componentName != null && isPreloadedLiveWallpaperComponent(componentName)) {
            return "com.samsung.android.wallpaper.live.fold.FoldInteractive".equals(componentName.getClassName());
        }
        return false;
    }

    public static void recoverComponentNameIfMissed(WallpaperData wallpaperData) {
        Bundle externalParams;
        String str;
        if (wallpaperData != null && wallpaperData.getWallpaperComponent() == null) {
            Log.m129d("PreloadedLiveWallpaperHelper", "recoverComponentNameIfMissed: ComponentName is null. Recovering...");
            SemWallpaperData semWallpaperData = wallpaperData.getSemWallpaperData();
            if (semWallpaperData == null || (externalParams = semWallpaperData.getExternalParams()) == null) {
                return;
            }
            String string = externalParams.getString("contentType");
            if (TextUtils.isEmpty(string)) {
                return;
            }
            string.hashCode();
            switch (string) {
                case "layered":
                    str = "com.samsung.android.wallpaper.live.layered.LayeredWallpaperService";
                    break;
                case "infinity":
                    str = "com.samsung.android.wallpaper.live.infinity.InfinityWallpaper";
                    break;
                case "weather":
                    str = "com.samsung.android.wallpaper.live.weather.effects.WeatherWallpaperService";
                    break;
                default:
                    str = null;
                    break;
            }
            if (str != null) {
                wallpaperData.setWallpaperComponent(new ComponentName("com.samsung.android.wallpaper.live", str));
                return;
            }
            Log.m129d("PreloadedLiveWallpaperHelper", "recoverComponentNameIfMissed: Failed to recover ComponentName. contentType = " + string);
        }
    }

    public final WallpaperData getWallpaperData(int i, int i2) {
        return this.mCallback.getWallpaperData(i, i2);
    }

    public final String snakeCaseToCamelCase(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split("[\\W_]+");
        if (split.length == 1) {
            return split[0];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String str2 = split[i];
            if (!str2.isEmpty()) {
                if (i == 0) {
                    sb.append(str2.toLowerCase());
                } else {
                    sb.append(Character.toUpperCase(str2.charAt(0)));
                    sb.append(str2.substring(1).toLowerCase());
                }
            }
        }
        return sb.toString();
    }

    public final Bundle convertSnakeCaseBundleToCamelCase(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        for (String str : bundle.keySet()) {
            bundle2.putObject(snakeCaseToCamelCase(str), bundle.get(str));
        }
        return bundle2;
    }
}
