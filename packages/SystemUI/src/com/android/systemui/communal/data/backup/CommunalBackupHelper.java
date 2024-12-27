package com.android.systemui.communal.data.backup;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Flags;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalBackupHelper implements BackupHelper {
    public final CommunalBackupUtils communalBackupUtils;
    public final UserHandle userHandle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public CommunalBackupHelper(UserHandle userHandle, CommunalBackupUtils communalBackupUtils) {
        this.userHandle = userHandle;
        this.communalBackupUtils = communalBackupUtils;
    }

    @Override // android.app.backup.BackupHelper
    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        Flags.communalHub();
        Log.d("CommunalBackupHelper", "Skipping backup. Communal not enabled");
    }

    @Override // android.app.backup.BackupHelper
    public final void restoreEntity(BackupDataInputStream backupDataInputStream) {
        if (backupDataInputStream == null) {
            Log.e("CommunalBackupHelper", "Restore failed. Data is null");
            return;
        }
        if (!this.userHandle.isSystem()) {
            Log.d("CommunalBackupHelper", "Restore skipped for non-system user");
            return;
        }
        if (!Intrinsics.areEqual(backupDataInputStream.getKey(), "communal_hub_state")) {
            Log.d("CommunalBackupHelper", "Restore skipped due to mismatching entity key");
            return;
        }
        int size = backupDataInputStream.size();
        byte[] bArr = new byte[size];
        try {
            backupDataInputStream.read(bArr, 0, size);
            try {
                CommunalBackupUtils communalBackupUtils = this.communalBackupUtils;
                communalBackupUtils.getClass();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(communalBackupUtils.context.getFilesDir(), "communal_restore"));
                fileOutputStream.write(bArr);
                fileOutputStream.close();
                Log.i("CommunalBackupHelper", "Restore complete");
            } catch (Exception e) {
                Log.e("CommunalBackupHelper", "Restore failed while writing to disk: " + e.getLocalizedMessage());
            }
        } catch (IOException e2) {
            Log.e("CommunalBackupHelper", "Restore failed while reading data: " + e2.getLocalizedMessage());
        }
    }

    @Override // android.app.backup.BackupHelper
    public final void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
    }
}
