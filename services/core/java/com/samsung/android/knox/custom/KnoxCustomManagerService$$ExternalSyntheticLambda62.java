package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda62
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda62(
            KnoxCustomManagerService knoxCustomManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        KnoxCustomManagerService knoxCustomManagerService = this.f$0;
        switch (i) {
            case 0:
                String str = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.lambda$startProKioskModeInternal$143();
                break;
            case 1:
                String str2 = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.lambda$closeLauncherApp$140();
                break;
            case 2:
                String str3 = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.lambda$stopProKioskModeInternal$142();
                break;
            case 3:
                String str4 = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.lambda$closeSettingsApp$144();
                break;
            default:
                String str5 = KnoxCustomManagerService.TAG;
                knoxCustomManagerService.lambda$initialiseSystemUi$154();
                break;
        }
    }
}
