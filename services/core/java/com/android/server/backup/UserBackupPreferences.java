package com.android.server.backup;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UserBackupPreferences {
    public final SharedPreferences.Editor mEditor;
    public final SharedPreferences mPreferences;

    public UserBackupPreferences(Context context, File file) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(new File(file, "backup_preferences"), 0);
        this.mPreferences = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
    }
}
