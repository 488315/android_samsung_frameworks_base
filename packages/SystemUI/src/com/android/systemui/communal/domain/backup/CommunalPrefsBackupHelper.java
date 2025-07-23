package com.android.systemui.communal.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalPrefsBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "communal_hub_prefs").getPath());
        UserFileManagerImpl.Companion.getClass();
    }
}
