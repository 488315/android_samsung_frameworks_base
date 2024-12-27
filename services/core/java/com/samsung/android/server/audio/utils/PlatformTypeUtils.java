package com.samsung.android.server.audio.utils;

import android.content.Context;
import android.media.AudioSystem;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PlatformTypeUtils {
    public static int getPlatformType(Context context) {
        try {
            String[] split = String.valueOf(context.getPackageManager().getPackageInfo("com.samsung.android.mdecservice", 0).versionName).split("\\.");
            if (split.length == 4) {
                if (Integer.parseInt(split[0]) >= 2) {
                    return 1;
                }
            }
        } catch (Exception e) {
            Log.e("AS.PlatformTypeUtils", "hasCmcFeature error", e);
        }
        return AudioSystem.getPlatformType(context);
    }
}
