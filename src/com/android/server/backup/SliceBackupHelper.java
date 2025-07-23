package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.app.slice.ISliceManager;
import android.content.Context;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

/* loaded from: classes6.dex */
public class SliceBackupHelper extends BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final String KEY_SLICES = "slices";
    static final String TAG = "SliceBackupHelper";
    static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public SliceBackupHelper(Context context) {
        super(1, KEY_SLICES);
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(String str) {
        if (KEY_SLICES.equals(str)) {
            try {
                return ISliceManager.Stub.asInterface(ServiceManager.getService("slice")).getBackupPayload(0);
            } catch (Exception e) {
                Slog.e(TAG, "Couldn't communicate with slice manager", e);
            }
        }
        return null;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(String str, byte[] bArr) {
        if (DEBUG) {
            Slog.v(TAG, "Got restore of " + str);
        }
        if (KEY_SLICES.equals(str)) {
            try {
                ISliceManager.Stub.asInterface(ServiceManager.getService("slice")).applyRestore(bArr, 0);
            } catch (Exception e) {
                Slog.e(TAG, "Couldn't communicate with slice manager", e);
            }
        }
    }
}
