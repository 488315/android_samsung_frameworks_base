package com.android.server.ambientcontext;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.service.ambientcontext.IAmbientContextDetectionService;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import java.io.PrintWriter;
import java.util.function.Function;

/* loaded from: classes.dex */
final class DefaultRemoteAmbientContextDetectionService extends ServiceConnector.Impl implements RemoteAmbientDetectionService {
    public static final String TAG = DefaultRemoteAmbientContextDetectionService.class.getSimpleName();

    public long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public DefaultRemoteAmbientContextDetectionService(Context context, ComponentName componentName, int i) {
        super(context, new Intent("android.service.ambientcontext.AmbientContextDetectionService").setComponent(componentName), 67112960, i, new Function() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IAmbientContextDetectionService.Stub.asInterface((IBinder) obj);
            }
        });
        connect();
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void startDetection(final AmbientContextEventRequest ambientContextEventRequest, final String str, final RemoteCallback remoteCallback, final RemoteCallback remoteCallback2) {
        Slog.i(TAG, "Start detection for " + ambientContextEventRequest.getEventTypes());
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda2
            public final void runNoResult(Object obj) {
                ((IAmbientContextDetectionService) obj).startDetection(ambientContextEventRequest, str, remoteCallback, remoteCallback2);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void stopDetection(final String str) {
        Slog.i(TAG, "Stop detection for " + str);
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda0
            public final void runNoResult(Object obj) {
                ((IAmbientContextDetectionService) obj).stopDetection(str);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void queryServiceStatus(final int[] iArr, final String str, final RemoteCallback remoteCallback) {
        Slog.i(TAG, "Query status for " + str);
        post(new ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda3
            public final void runNoResult(Object obj) {
                ((IAmbientContextDetectionService) obj).queryServiceStatus(iArr, str, remoteCallback);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void dump(String str, PrintWriter printWriter) {
        super.dump(str, printWriter);
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void unbind() {
        super.unbind();
    }
}
