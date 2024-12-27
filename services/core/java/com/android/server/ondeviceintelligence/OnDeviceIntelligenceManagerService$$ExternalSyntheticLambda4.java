package com.android.server.ondeviceintelligence;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda4
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OnDeviceIntelligenceManagerService f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda4(
            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService,
            String str,
            int i) {
        this.$r8$classId = i;
        this.f$0 = onDeviceIntelligenceManagerService;
        this.f$1 = str;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService = this.f$0;
                String str = this.f$1;
                onDeviceIntelligenceManagerService.getClass();
                OnDeviceIntelligenceManagerService.validateServiceElevated(str, true);
                break;
            default:
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService2 = this.f$0;
                String str2 = this.f$1;
                onDeviceIntelligenceManagerService2.getClass();
                OnDeviceIntelligenceManagerService.validateServiceElevated(str2, false);
                break;
        }
    }
}
