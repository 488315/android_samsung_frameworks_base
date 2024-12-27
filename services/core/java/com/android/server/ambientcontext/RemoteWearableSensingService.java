package com.android.server.ambientcontext;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.os.RemoteCallback;
import android.service.wearable.IWearableSensingService;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
final class RemoteWearableSensingService extends ServiceConnector.Impl implements RemoteAmbientDetectionService {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public final void queryServiceStatus(final int[] iArr, final String str, final RemoteCallback remoteCallback) {
        Slog.i("RemoteWearableSensingService", "Query status for " + str);
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda3
            public final void runNoResult(Object obj) {
                int[] iArr2 = iArr;
                String str2 = str;
                RemoteCallback remoteCallback2 = remoteCallback;
                int i = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj).queryServiceStatus(iArr2, str2, remoteCallback2);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public final void startDetection(final AmbientContextEventRequest ambientContextEventRequest, final String str, final RemoteCallback remoteCallback, final RemoteCallback remoteCallback2) {
        Slog.i("RemoteWearableSensingService", "Start detection for " + ambientContextEventRequest.getEventTypes());
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda2
            public final void runNoResult(Object obj) {
                AmbientContextEventRequest ambientContextEventRequest2 = ambientContextEventRequest;
                String str2 = str;
                RemoteCallback remoteCallback3 = remoteCallback;
                RemoteCallback remoteCallback4 = remoteCallback2;
                int i = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj).startDetection(ambientContextEventRequest2, str2, remoteCallback3, remoteCallback4);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public final void stopDetection(final String str) {
        Slog.i("RemoteWearableSensingService", "Stop detection for " + str);
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda1
            public final void runNoResult(Object obj) {
                String str2 = str;
                int i = RemoteWearableSensingService.$r8$clinit;
                ((IWearableSensingService) obj).stopDetection(str2);
            }
        });
    }
}
