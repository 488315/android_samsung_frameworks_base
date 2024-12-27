package com.android.server;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.ISpqrService;
import android.os.ServiceManager;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SpqrService extends ProfileService {
    public volatile ISpqrService mService;

    public SpqrService(Context context) {
        super(context, "SpqrService", "sqpr_service");
        this.packageBlockList = initPackageBlockList("/system/etc/spqr-package-blocklist.conf");
    }

    @Override // com.android.server.ProfileService
    public final boolean checkAppId(int i) {
        if (i >= 0) {
            return true;
        }
        Slog.w(this.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid app id: "));
        return false;
    }

    @Override // com.android.server.ProfileService
    public final IBinder getBinderOfService() {
        return ServiceManager.getService("spqr_service");
    }

    @Override // com.android.server.ProfileService
    public final void initializeInterfaceOfService() {
        this.mService = null;
    }

    @Override // com.android.server.ProfileService
    public final boolean isServiceRunning() {
        if (this.mService != null) {
            return true;
        }
        Slog.w(this.TAG, "spqr_service is not running");
        return false;
    }

    @Override // com.android.server.ProfileService
    public final void setInterfaceOfService(IBinder iBinder) {
        this.mService = ISpqrService.Stub.asInterface(iBinder);
    }
}
