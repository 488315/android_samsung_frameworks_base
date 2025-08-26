package com.android.server.backup;

import android.app.AppGlobals;
import android.app.backup.BlobBackupHelper;
import android.content.pm.IPackageManager;
import android.util.Slog;

/* loaded from: classes6.dex */
public class PreferredActivityBackupHelper extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final int STATE_VERSION = 4;
    private static final String TAG = "PreferredBackup";
    private final int mUserId;
    private static final String KEY_PREFERRED = "preferred-activity";
    private static final String KEY_DEFAULT_APPS = "default-apps";

    @Deprecated
    private static final String KEY_INTENT_VERIFICATION = "intent-verification";
    private static final String KEY_DOMAIN_VERIFICATION = "domain-verification";
    private static final String[] KEYS = {KEY_PREFERRED, KEY_DEFAULT_APPS, KEY_INTENT_VERIFICATION, KEY_DOMAIN_VERIFICATION};

    private @interface Key {
    }

    public PreferredActivityBackupHelper(int i) {
        super(4, KEYS);
        this.mUserId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected byte[] getBackupPayload(String str) {
        char c;
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            switch (str.hashCode()) {
                case -696985986:
                    if (!str.equals(KEY_DEFAULT_APPS)) {
                        c = 65535;
                        break;
                    } else {
                        c = 1;
                        break;
                    }
                case -549387132:
                    if (str.equals(KEY_DOMAIN_VERIFICATION)) {
                        c = 3;
                        break;
                    }
                    break;
                case -429170260:
                    if (str.equals(KEY_INTENT_VERIFICATION)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1336142555:
                    if (str.equals(KEY_PREFERRED)) {
                        c = 0;
                        break;
                    }
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            Slog.e(TAG, "Unable to store payload " + str, e);
        }
        if (c == 0) {
            return packageManager.getPreferredActivityBackup(this.mUserId);
        }
        if (c == 1) {
            return packageManager.getDefaultAppsBackup(this.mUserId);
        }
        if (c == 2) {
            return null;
        }
        if (c == 3) {
            return packageManager.getDomainVerificationBackup(this.mUserId);
        }
        Slog.w(TAG, "Unexpected backup key " + str);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void applyRestoredPayload(String str, byte[] bArr) {
        char c;
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            switch (str.hashCode()) {
                case -696985986:
                    if (!str.equals(KEY_DEFAULT_APPS)) {
                        c = 65535;
                        break;
                    } else {
                        c = 1;
                        break;
                    }
                case -549387132:
                    if (str.equals(KEY_DOMAIN_VERIFICATION)) {
                        c = 3;
                        break;
                    }
                    break;
                case -429170260:
                    if (str.equals(KEY_INTENT_VERIFICATION)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1336142555:
                    if (str.equals(KEY_PREFERRED)) {
                        c = 0;
                        break;
                    }
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                packageManager.restorePreferredActivities(bArr, this.mUserId);
                return;
            }
            if (c == 1) {
                packageManager.restoreDefaultApps(bArr, this.mUserId);
                return;
            }
            if (c != 2) {
                if (c == 3) {
                    packageManager.restoreDomainVerification(bArr, this.mUserId);
                    return;
                }
                Slog.w(TAG, "Unexpected restore key " + str);
            }
        } catch (Exception e) {
            Slog.e(TAG, "Unable to restore key " + str, e);
        }
    }
}
