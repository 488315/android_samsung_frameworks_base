package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.permission.PermissionManagerInternal;
import android.util.Slog;
import com.android.server.LocalServices;

/* loaded from: classes6.dex */
public class PermissionBackupHelper extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_PERMISSIONS = "permissions";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "PermissionBackup";
    private final PermissionManagerInternal mPermissionManager;
    private final int mUserId;

    public PermissionBackupHelper(int i) {
        super(1, KEY_PERMISSIONS);
        this.mUserId = i;
        this.mPermissionManager = (PermissionManagerInternal) LocalServices.getService(PermissionManagerInternal.class);
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String str) {
        try {
            if (str.hashCode() == 1133704324 && str.equals(KEY_PERMISSIONS)) {
                return this.mPermissionManager.backupRuntimePermissions(this.mUserId);
            }
            Slog.w(TAG, "Unexpected backup key " + str);
            return null;
        } catch (Exception e) {
            Slog.e(TAG, "Unable to store payload " + str, e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String str, byte[] bArr) {
        try {
            if (str.hashCode() == 1133704324 && str.equals(KEY_PERMISSIONS)) {
                this.mPermissionManager.restoreRuntimePermissions(bArr, this.mUserId);
                return;
            }
            Slog.w(TAG, "Unexpected restore key " + str);
        } catch (Exception e) {
            Slog.e(TAG, "Unable to restore key " + str, e);
        }
    }
}
