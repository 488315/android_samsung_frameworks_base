package com.android.server.backup;

import android.accounts.AccountManagerInternal;
import android.app.backup.BlobBackupHelper;
import android.util.Slog;
import com.android.server.LocalServices;

/* loaded from: classes6.dex */
public class AccountManagerBackupHelper extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_ACCOUNT_ACCESS_GRANTS = "account_access_grants";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "AccountsBackup";
    private final int mUserId;

    public AccountManagerBackupHelper(int i) {
        super(1, KEY_ACCOUNT_ACCESS_GRANTS);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String str) {
        AccountManagerInternal accountManagerInternal = (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
        try {
            if (str.hashCode() == 1544100736 && str.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                return accountManagerInternal.backupAccountAccessPermissions(this.mUserId);
            }
            Slog.w(TAG, "Unexpected backup key " + str);
        } catch (Exception e) {
            Slog.e(TAG, "Unable to store payload " + str, e);
        }
        return new byte[0];
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String str, byte[] bArr) {
        AccountManagerInternal accountManagerInternal = (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
        try {
            if (str.hashCode() == 1544100736 && str.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                accountManagerInternal.restoreAccountAccessPermissions(bArr, this.mUserId);
                return;
            }
            Slog.w(TAG, "Unexpected restore key " + str);
        } catch (Exception e) {
            Slog.e(TAG, "Unable to restore key " + str, e);
        }
    }
}
