package com.samsung.android.server.pm;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public abstract class PmSharedPreferences {
    public static void putLong(Context context, long j) {
        synchronized (PmSharedPreferences.class) {
            context.createDeviceProtectedStorageContext()
                    .getSharedPreferences(
                            new File(
                                    Environment.getDataSystemDirectory(),
                                    "samsung_pm_settings.xml"),
                            0)
                    .edit()
                    .putLong("attempt_count", j)
                    .apply();
        }
    }
}
