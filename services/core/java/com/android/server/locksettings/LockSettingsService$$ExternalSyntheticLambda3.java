package com.android.server.locksettings;

import android.app.admin.PasswordMetrics;

import com.android.internal.widget.LockscreenCredential;
import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LockSettingsService$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ LockSettingsService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LockSettingsService$$ExternalSyntheticLambda3(
            LockSettingsService lockSettingsService, PasswordMetrics passwordMetrics, int i) {
        this.f$0 = lockSettingsService;
        this.f$1 = passwordMetrics;
        this.f$2 = i;
    }

    public /* synthetic */ LockSettingsService$$ExternalSyntheticLambda3(
            LockSettingsService lockSettingsService,
            LockscreenCredential lockscreenCredential,
            int i) {
        this.f$0 = lockSettingsService;
        this.f$1 = lockscreenCredential;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LockSettingsService lockSettingsService = this.f$0;
                PasswordMetrics passwordMetrics = (PasswordMetrics) this.f$1;
                int i = this.f$2;
                lockSettingsService
                        .mInjector
                        .getDevicePolicyManager()
                        .reportPasswordChanged(passwordMetrics, i);
                ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class))
                        .reportPasswordChanged(i);
                break;
            default:
                LockSettingsService lockSettingsService2 = this.f$0;
                LockscreenCredential lockscreenCredential = (LockscreenCredential) this.f$1;
                int i2 = this.f$2;
                lockSettingsService2
                        .mInjector
                        .getDevicePolicyManager()
                        .reportPasswordChanged(
                                PasswordMetrics.computeForCredential(lockscreenCredential), i2);
                ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class))
                        .reportPasswordChanged(i2);
                break;
        }
    }
}
