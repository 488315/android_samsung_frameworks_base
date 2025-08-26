package com.android.systemui.qs.panels.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;

/* loaded from: classes2.dex */
public final class QSPreferencesBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSPreferencesBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "quick_settings_prefs").getPath());
        UserFileManagerImpl.Companion.getClass();
    }
}
