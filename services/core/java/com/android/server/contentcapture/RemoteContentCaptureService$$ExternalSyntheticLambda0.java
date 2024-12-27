package com.android.server.contentcapture;

import android.os.IInterface;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.IContentCaptureService;
import android.view.contentcapture.DataRemovalRequest;

import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
