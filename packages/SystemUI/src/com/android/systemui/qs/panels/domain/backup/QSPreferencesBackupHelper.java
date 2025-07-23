package com.android.systemui.qs.panels.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPreferencesBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSPreferencesBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "quick_settings_prefs").getPath());
        UserFileManagerImpl.Companion.getClass();
    }
}
