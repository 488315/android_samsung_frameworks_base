package com.android.systemui.communal.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;

public final class CommunalPrefsBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "communal_hub_prefs").getPath());
        UserFileManagerImpl.Companion.getClass();
    }
}
