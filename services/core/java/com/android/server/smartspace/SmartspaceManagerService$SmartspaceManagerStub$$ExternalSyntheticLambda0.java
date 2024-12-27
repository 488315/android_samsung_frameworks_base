package com.android.server.smartspace;

import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTargetEvent;

import java.util.function.Consumer;

public final /* synthetic */
class SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SmartspaceSessionId f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SmartspaceManagerService$SmartspaceManagerStub$$ExternalSyntheticLambda0(
            SmartspaceSessionId smartspaceSessionId, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = smartspaceSessionId;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SmartspaceSessionId smartspaceSessionId = this.f$0;
                ISmartspaceCallback iSmartspaceCallback = (ISmartspaceCallback) this.f$1;
                SmartspacePerUserService smartspacePerUserService = (SmartspacePerUserService) obj;
                SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo =
                        (SmartspacePerUserService.SmartspaceSessionInfo)
                                smartspacePerUserService.mSessionInfos.get(smartspaceSessionId);
                if (smartspaceSessionInfo != null
                        && smartspacePerUserService.resolveService$1(
                                new SmartspacePerUserService$$ExternalSyntheticLambda4(
                                        smartspaceSessionId, iSmartspaceCallback, 0))) {
                    smartspaceSessionInfo.mCallbacks.unregister(iSmartspaceCallback);
                    break;
                }
                break;
            case 1:
                SmartspaceSessionId smartspaceSessionId2 = this.f$0;
                ISmartspaceCallback iSmartspaceCallback2 = (ISmartspaceCallback) this.f$1;
                SmartspacePerUserService smartspacePerUserService2 = (SmartspacePerUserService) obj;
                SmartspacePerUserService.SmartspaceSessionInfo smartspaceSessionInfo2 =
                        (SmartspacePerUserService.SmartspaceSessionInfo)
                                smartspacePerUserService2.mSessionInfos.get(smartspaceSessionId2);
                if (smartspaceSessionInfo2 != null
                        && smartspacePerUserService2.resolveService$1(
                                new SmartspacePerUserService$$ExternalSyntheticLambda4(
                                        smartspaceSessionId2, iSmartspaceCallback2, 1))) {
                    smartspaceSessionInfo2.mCallbacks.register(iSmartspaceCallback2);
                    break;
                }
                break;
            default:
                SmartspaceSessionId smartspaceSessionId3 = this.f$0;
                SmartspaceTargetEvent smartspaceTargetEvent = (SmartspaceTargetEvent) this.f$1;
                SmartspacePerUserService smartspacePerUserService3 = (SmartspacePerUserService) obj;
                if (((SmartspacePerUserService.SmartspaceSessionInfo)
                                smartspacePerUserService3.mSessionInfos.get(smartspaceSessionId3))
                        != null) {
                    smartspacePerUserService3.resolveService$1(
                            new SmartspacePerUserService$$ExternalSyntheticLambda1(
                                    smartspaceSessionId3, smartspaceTargetEvent));
                    break;
                }
                break;
        }
    }
}
