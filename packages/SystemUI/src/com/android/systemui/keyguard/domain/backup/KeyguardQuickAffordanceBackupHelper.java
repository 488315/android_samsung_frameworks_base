package com.android.systemui.keyguard.domain.backup;

import android.app.backup.BackupDataInputStream;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.os.Trace;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserFileManagerImpl;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class KeyguardQuickAffordanceBackupHelper extends SharedPreferencesBackupHelper {
    public final Context context;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceBackupHelper(Context context, int i) {
        super(context, UserFileManagerImpl.Companion.createFile(i, "quick_affordance_selections").getPath());
        UserFileManagerImpl.Companion.getClass();
        this.context = context;
    }

    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    public final void restoreEntity(BackupDataInputStream backupDataInputStream) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(this.context.getUserId(), "Starting restore for ", backupDataInputStream != null ? backupDataInputStream.getKey() : null, " for user ", "KeyguardQuickAffordanceBackupHelper");
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("KeyguardQuickAffordanceBackupHelper File restore: ", backupDataInputStream != null ? backupDataInputStream.getKey() : null);
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice(m);
        }
        try {
            super.restoreEntity(backupDataInputStream);
            Unit unit = Unit.INSTANCE;
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(this.context.getUserId(), "Finished restore for ", backupDataInputStream != null ? backupDataInputStream.getKey() : null, " for user ", "KeyguardQuickAffordanceBackupHelper");
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
