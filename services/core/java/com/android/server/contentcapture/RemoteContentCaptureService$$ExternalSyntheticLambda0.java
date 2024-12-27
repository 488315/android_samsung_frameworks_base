package com.android.server.contentcapture;

import android.os.IInterface;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.IContentCaptureService;
import android.view.contentcapture.DataRemovalRequest;

import com.android.internal.infra.AbstractRemoteService;

public final /* synthetic */ class RemoteContentCaptureService$$ExternalSyntheticLambda0
        implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    public final void run(IInterface iInterface) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((IContentCaptureService) iInterface)
                        .onDataRemovalRequest((DataRemovalRequest) obj);
                break;
            default:
                ((IContentCaptureService) iInterface).onActivityEvent((ActivityEvent) obj);
                break;
        }
    }
}
