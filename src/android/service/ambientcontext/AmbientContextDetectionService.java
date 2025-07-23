package android.service.ambientcontext;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.ambientcontext.AmbientContextEventRequest;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.service.ambientcontext.AmbientContextDetectionService;
import android.service.ambientcontext.IAmbientContextDetectionService;
import android.util.Slog;
import java.util.Objects;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class AmbientContextDetectionService extends Service {
    public static final String SERVICE_INTERFACE = "android.service.ambientcontext.AmbientContextDetectionService";
    private static final String TAG = "AmbientContextDetectionService";

    public abstract void onQueryServiceStatus(int[] iArr, String str, Consumer<AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(AmbientContextEventRequest ambientContextEventRequest, String str, Consumer<AmbientContextDetectionResult> consumer, Consumer<AmbientContextDetectionServiceStatus> consumer2);

    public abstract void onStopDetection(String str);

    /* renamed from: android.service.ambientcontext.AmbientContextDetectionService$1, reason: invalid class name */
    class AnonymousClass1 extends IAmbientContextDetectionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, final RemoteCallback remoteCallback, final RemoteCallback remoteCallback2) {
            Objects.requireNonNull(ambientContextEventRequest);
            Objects.requireNonNull(str);
            Objects.requireNonNull(remoteCallback);
            Objects.requireNonNull(remoteCallback2);
            AmbientContextDetectionService.this.onStartDetection(ambientContextEventRequest, str, new Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AmbientContextDetectionService.AnonymousClass1.lambda$startDetection$0(RemoteCallback.this, (AmbientContextDetectionResult) obj);
                }
            }, new Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AmbientContextDetectionService.AnonymousClass1.lambda$startDetection$1(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            });
            Slog.d(AmbientContextDetectionService.TAG, "startDetection " + ambientContextEventRequest);
        }

        static /* synthetic */ void lambda$startDetection$0(RemoteCallback remoteCallback, AmbientContextDetectionResult ambientContextDetectionResult) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, ambientContextDetectionResult);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startDetection$1(RemoteCallback remoteCallback, AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void stopDetection(String str) {
            Objects.requireNonNull(str);
            AmbientContextDetectionService.this.onStopDetection(str);
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void queryServiceStatus(int[] iArr, String str, final RemoteCallback remoteCallback) {
            Objects.requireNonNull(iArr);
            Objects.requireNonNull(str);
            Objects.requireNonNull(remoteCallback);
            AmbientContextDetectionService.this.onQueryServiceStatus(iArr, str, new Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AmbientContextDetectionService.AnonymousClass1.lambda$queryServiceStatus$2(RemoteCallback.this, (AmbientContextDetectionServiceStatus) obj);
                }
            });
        }

        static /* synthetic */ void lambda$queryServiceStatus$2(RemoteCallback remoteCallback, AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new AnonymousClass1();
        }
        return null;
    }
}
