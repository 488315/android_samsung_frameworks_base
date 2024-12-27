package com.android.server.smartspace;

import android.app.smartspace.SmartspaceSessionId;
import android.os.IInterface;
import android.service.smartspace.ISmartspaceService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SmartspacePerUserService$$ExternalSyntheticLambda0 implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SmartspaceSessionId f$0;

    public /* synthetic */ SmartspacePerUserService$$ExternalSyntheticLambda0(SmartspaceSessionId smartspaceSessionId, int i) {
        this.$r8$classId = i;
        this.f$0 = smartspaceSessionId;
    }

    public final void run(IInterface iInterface) {
        int i = this.$r8$classId;
        SmartspaceSessionId smartspaceSessionId = this.f$0;
        ISmartspaceService iSmartspaceService = (ISmartspaceService) iInterface;
        switch (i) {
            case 0:
                iSmartspaceService.onDestroySmartspaceSession(smartspaceSessionId);
                break;
            default:
                iSmartspaceService.requestSmartspaceUpdate(smartspaceSessionId);
                break;
        }
    }
}
