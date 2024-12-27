package com.samsung.android.server.pm;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PmSharedPreferences {
    public static void putLong(Context context, long j) {
        synchronized (PmSharedPreferences.class) {
            context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).edit().putLong("attempt_count", j).apply();
        }
    }
}
