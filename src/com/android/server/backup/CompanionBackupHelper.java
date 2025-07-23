package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.companion.ICompanionDeviceManager;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Slog;

/* loaded from: classes6.dex */
public class CompanionBackupHelper extends BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final String KEY_COMPANION = "companion";
    private static final String TAG = "CompanionBackupHelper";
    private final int mUserId;

    public CompanionBackupHelper(int i) {
        super(1, KEY_COMPANION);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String str) {
        if (!KEY_COMPANION.equals(str)) {
            return null;
        }
        try {
            return ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService(Context.COMPANION_DEVICE_SERVICE)).getBackupPayload(this.mUserId);
        } catch (Exception e) {
            Slog.e(TAG, "Error getting backup from CompanionDeviceManager.", e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String str, byte[] bArr) {
        Slog.i(TAG, "Got companion backup data.");
        if (KEY_COMPANION.equals(str)) {
            try {
                ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService(Context.COMPANION_DEVICE_SERVICE)).applyRestoredPayload(bArr, this.mUserId);
            } catch (Exception e) {
                Slog.e(TAG, "Error applying restored payload to CompanionDeviceManager.", e);
            }
        }
    }
}
