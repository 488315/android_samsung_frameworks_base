package com.android.server.smartspace;

import android.app.smartspace.SmartspaceSessionId;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SmartspaceSessionId f$0;

    public /* synthetic */ SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda2(
            SmartspaceSessionId smartspaceSessionId, int i) {
        this.$r8$classId = i;
        this.f$0 = smartspaceSessionId;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SmartspaceSessionId smartspaceSessionId = this.f$0;
        SmartspacePerUserService smartspacePerUserService = (SmartspacePerUserService) obj;
        switch (i) {
            case 0:
                smartspacePerUserService.onDestroyLocked(smartspaceSessionId);
                break;
            default:
                if (((SmartspacePerUserService.SmartspaceSessionInfo)
                                smartspacePerUserService.mSessionInfos.get(smartspaceSessionId))
                        != null) {
                    smartspacePerUserService.resolveService$1(
                            new SmartspacePerUserService$$ExternalSyntheticLambda0(
                                    smartspaceSessionId, 1));
                    break;
                }
                break;
        }
    }
}
