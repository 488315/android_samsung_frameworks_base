package com.android.server.contentcapture;

import com.android.server.contentcapture.ContentCaptureManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContentCaptureManagerService.ContentCaptureManagerServiceStub f$0;

    public /* synthetic */ ContentCaptureManagerService$ContentCaptureManagerServiceStub$$ExternalSyntheticLambda1(ContentCaptureManagerService.ContentCaptureManagerServiceStub contentCaptureManagerServiceStub, int i) {
        this.$r8$classId = i;
        this.f$0 = contentCaptureManagerServiceStub;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ContentCaptureManagerService.ContentCaptureManagerServiceStub contentCaptureManagerServiceStub = this.f$0;
        switch (i) {
            case 0:
                contentCaptureManagerServiceStub.this$0.enforceCallingPermissionForManagement();
                return;
            default:
                if (!contentCaptureManagerServiceStub.this$0.isCalledByServiceLocked("isContentCaptureFeatureEnabled()")) {
                    throw new SecurityException("caller is not user's ContentCapture service");
                }
                return;
        }
    }
}
