package com.android.systemui.keyguard.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "quick_affordance_selections").getPath());
        UserFileManagerImpl.Companion.getClass();
    }
}
