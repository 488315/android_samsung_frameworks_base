package com.android.server.smartspace;

import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceSessionId;
import android.os.IInterface;
import android.service.smartspace.ISmartspaceService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SmartspacePerUserService$$ExternalSyntheticLambda4 implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SmartspaceSessionId f$0;
    public final /* synthetic */ ISmartspaceCallback f$1;

    public /* synthetic */ SmartspacePerUserService$$ExternalSyntheticLambda4(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = smartspaceSessionId;
        this.f$1 = iSmartspaceCallback;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((ISmartspaceService) iInterface).unregisterSmartspaceUpdates(this.f$0, this.f$1);
                break;
            default:
                ((ISmartspaceService) iInterface).registerSmartspaceUpdates(this.f$0, this.f$1);
                break;
        }
    }
}
