package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.content.pm.IShortcutService;
import android.os.ServiceManager;
import android.util.Slog;

/* loaded from: classes6.dex */
public class ShortcutBackupHelper extends BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final String KEY_USER_FILE = "shortcutuser.xml";
    private static final String TAG = "ShortcutBackupAgent";
    private final int mUserId;

    public ShortcutBackupHelper(int i) {
        super(1, KEY_USER_FILE);
        this.mUserId = i;
    }

    private IShortcutService getShortcutService() {
        return IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut"));
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String str) {
        str.hashCode();
        if (str.equals(KEY_USER_FILE)) {
            try {
                return getShortcutService().getBackupPayload(this.mUserId);
            } catch (Exception e) {
                Slog.wtf(TAG, "Backup failed", e);
                return null;
            }
        }
        Slog.w(TAG, "Unknown key: " + str);
        return null;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String str, byte[] bArr) {
        str.hashCode();
        if (str.equals(KEY_USER_FILE)) {
            try {
                getShortcutService().applyRestore(bArr, this.mUserId);
                return;
            } catch (Exception e) {
                Slog.wtf(TAG, "Restore failed", e);
                return;
            }
        }
        Slog.w(TAG, "Unknown key: " + str);
    }
}
