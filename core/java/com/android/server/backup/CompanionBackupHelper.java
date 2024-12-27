package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.companion.ICompanionDeviceManager;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Slog;

/* loaded from: classes5.dex */
public class CompanionBackupHelper extends BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final String KEY_COMPANION = "companion";
    private static final String TAG = "CompanionBackupHelper";
    private final int mUserId;

    public CompanionBackupHelper(int userId) {
        super(1, KEY_COMPANION);
        this.mUserId = userId;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String key) {
        if (!KEY_COMPANION.equals(key)) {
            return null;
        }
        try {
            ICompanionDeviceManager cdm = ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService(Context.COMPANION_DEVICE_SERVICE));
            byte[] payload = cdm.getBackupPayload(this.mUserId);
            return payload;
        } catch (Exception e) {
            Slog.e(TAG, "Error getting backup from CompanionDeviceManager.", e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String key, byte[] payload) {
        Slog.i(TAG, "Got companion backup data.");
        if (KEY_COMPANION.equals(key)) {
            try {
                ICompanionDeviceManager cdm = ICompanionDeviceManager.Stub.asInterface(ServiceManager.getService(Context.COMPANION_DEVICE_SERVICE));
                cdm.applyRestoredPayload(payload, this.mUserId);
            } catch (Exception e) {
                Slog.e(TAG, "Error applying restored payload to CompanionDeviceManager.", e);
            }
        }
    }
}
