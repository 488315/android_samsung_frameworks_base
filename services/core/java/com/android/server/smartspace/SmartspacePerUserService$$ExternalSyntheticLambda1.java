package com.android.server.smartspace;

import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTargetEvent;
import android.os.IInterface;
import android.service.smartspace.ISmartspaceService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SmartspacePerUserService$$ExternalSyntheticLambda1 implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SmartspaceSessionId f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SmartspacePerUserService$$ExternalSyntheticLambda1(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) {
        this.f$1 = smartspaceConfig;
        this.f$0 = smartspaceSessionId;
    }

    public /* synthetic */ SmartspacePerUserService$$ExternalSyntheticLambda1(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) {
        this.f$0 = smartspaceSessionId;
        this.f$1 = smartspaceTargetEvent;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((ISmartspaceService) iInterface).notifySmartspaceEvent(this.f$0, (SmartspaceTargetEvent) this.f$1);
                break;
            default:
                ((ISmartspaceService) iInterface).onCreateSmartspaceSession((SmartspaceConfig) this.f$1, this.f$0);
                break;
        }
    }
}
