package com.android.server.accessibility;

import android.os.Binder;
import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ AccessibilityManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda6(int i, AccessibilityManagerService accessibilityManagerService) {
        this.f$0 = accessibilityManagerService;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AccessibilityManagerService accessibilityManagerService = this.f$0;
        int i = this.f$1;
        accessibilityManagerService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_magnification_mode", i, accessibilityManagerService.mCurrentUserId);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
