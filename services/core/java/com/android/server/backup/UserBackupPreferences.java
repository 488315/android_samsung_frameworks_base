package com.android.server.backup;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public final class UserBackupPreferences {
    public final SharedPreferences.Editor mEditor;
    public final SharedPreferences mPreferences;

    public UserBackupPreferences(Context context, File file) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(new File(file, "backup_preferences"), 0);
        this.mPreferences = sharedPreferences;
        this.mEditor = sharedPreferences.edit();
    }
}
