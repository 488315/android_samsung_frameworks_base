package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.util.Slog;

import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.locales.LocaleManagerService;

public final class AppSpecificLocalesBackupHelper extends BlobBackupHelper {
    public final LocaleManagerService.LocaleManagerInternalImpl mLocaleManagerInternal;
    public final int mUserId;

    public AppSpecificLocalesBackupHelper(int i) {
        super(1, new String[] {"app_locales"});
        this.mUserId = i;
        this.mLocaleManagerInternal =
                (LocaleManagerService.LocaleManagerInternalImpl)
                        LocalServices.getService(
                                LocaleManagerService.LocaleManagerInternalImpl.class);
    }

    public final void applyRestoredPayload(String str, byte[] bArr) {
        if (!"app_locales".equals(str)) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Unexpected restore key ", str, "AppLocalesBackupHelper");
            return;
        }
        try {
            this.mLocaleManagerInternal.stageAndApplyRestoredPayload(this.mUserId, bArr);
        } catch (Exception e) {
            Slog.e("AppLocalesBackupHelper", "Couldn't communicate with locale manager", e);
        }
    }

    public final byte[] getBackupPayload(String str) {
        if (!"app_locales".equals(str)) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Unexpected backup key ", str, "AppLocalesBackupHelper");
            return null;
        }
        try {
            return this.mLocaleManagerInternal.getBackupPayload(this.mUserId);
        } catch (Exception e) {
            Slog.e("AppLocalesBackupHelper", "Couldn't communicate with locale manager", e);
            return null;
        }
    }
}
