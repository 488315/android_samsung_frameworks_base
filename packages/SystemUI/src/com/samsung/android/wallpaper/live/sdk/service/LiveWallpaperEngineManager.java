package com.samsung.android.wallpaper.live.sdk.service;

import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class LiveWallpaperEngineManager {
    public static LiveWallpaperEngineManager sInstance;
    public final ArrayList mEngineList = new ArrayList();

    private LiveWallpaperEngineManager(Context context) {
        context.getApplicationContext();
    }

    public static synchronized LiveWallpaperEngineManager getInstance(Context context) {
        try {
            if (sInstance == null) {
                sInstance = new LiveWallpaperEngineManager(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public final synchronized LiveWallpaperService.BaseEngine getEngine(int i) {
        int size = this.mEngineList.size();
        for (int i2 = size - 1; i2 >= 0; i2--) {
            LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) ((WeakReference) this.mEngineList.get(i2)).get();
            if (baseEngine != null) {
                int sourceWhich = baseEngine.getSourceWhich();
                if (sourceWhich == i) {
                    return baseEngine;
                }
                if (i == 0 && (sourceWhich & 3) == 0) {
                    return baseEngine;
                }
            }
        }
        SdkLog.i("LiveWallpaperEngineManager", "getEngine : cannot find the engine. which=" + i + ", engineCnt=" + size);
        return null;
    }

    public final synchronized ArrayList getEngines() {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (int size = this.mEngineList.size() - 1; size >= 0; size--) {
                LiveWallpaperService.BaseEngine baseEngine = (LiveWallpaperService.BaseEngine) ((WeakReference) this.mEngineList.get(size)).get();
                if (baseEngine != null) {
                    arrayList.add(baseEngine);
                }
            }
            SdkLog.i("LiveWallpaperEngineManager", "getEngines : returnCnt=" + arrayList.size());
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }
}
