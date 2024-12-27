package com.android.server.ondeviceintelligence;

import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.IResponseCallback;
import android.app.ondeviceintelligence.IStreamingResponseCallback;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.provider.Settings;
import android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final /* synthetic */ class OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda0
        implements ServiceConnector.Job {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ OnDeviceIntelligenceManagerService.AnonymousClass1 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Feature f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ AndroidFuture f$5;
    public final /* synthetic */ AndroidFuture f$6;
    public final /* synthetic */ Object f$7;

    public /* synthetic */ OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda0(
            OnDeviceIntelligenceManagerService.AnonymousClass1 anonymousClass1,
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IResponseCallback iResponseCallback) {
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = feature;
        this.f$3 = bundle;
        this.f$4 = i2;
        this.f$5 = androidFuture;
        this.f$6 = androidFuture2;
        this.f$7 = iResponseCallback;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda0(
            OnDeviceIntelligenceManagerService.AnonymousClass1 anonymousClass1,
            int i,
            Feature feature,
            Bundle bundle,
            int i2,
            AndroidFuture androidFuture,
            AndroidFuture androidFuture2,
            IStreamingResponseCallback iStreamingResponseCallback) {
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = feature;
        this.f$3 = bundle;
        this.f$4 = i2;
        this.f$5 = androidFuture;
        this.f$6 = androidFuture2;
        this.f$7 = iStreamingResponseCallback;
    }

    public final Object run(Object obj) {
        AndroidFuture androidFuture;
        long longForUser;
        AndroidFuture androidFuture2;
        long longForUser2;
        switch (this.$r8$classId) {
            case 0:
                OnDeviceIntelligenceManagerService.AnonymousClass1 anonymousClass1 = this.f$0;
                int i = this.f$1;
                Feature feature = this.f$2;
                Bundle bundle = this.f$3;
                int i2 = this.f$4;
                AndroidFuture androidFuture3 = this.f$5;
                AndroidFuture androidFuture4 = this.f$6;
                IStreamingResponseCallback iStreamingResponseCallback =
                        (IStreamingResponseCallback) this.f$7;
                IOnDeviceSandboxedInferenceService iOnDeviceSandboxedInferenceService =
                        (IOnDeviceSandboxedInferenceService) obj;
                anonymousClass1.getClass();
                AndroidFuture androidFuture5 = new AndroidFuture();
                AndroidFuture m741$$Nest$mwrapCancellationFuture =
                        OnDeviceIntelligenceManagerService.m741$$Nest$mwrapCancellationFuture(
                                OnDeviceIntelligenceManagerService.this, androidFuture3);
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                        OnDeviceIntelligenceManagerService.this;
                onDeviceIntelligenceManagerService.getClass();
                if (androidFuture4 == null) {
                    androidFuture = null;
                } else {
                    androidFuture = new AndroidFuture();
                    androidFuture.whenCompleteAsync(
                            new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6(
                                    onDeviceIntelligenceManagerService, androidFuture4, 0));
                }
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService2 =
                        OnDeviceIntelligenceManagerService.this;
                iOnDeviceSandboxedInferenceService.processRequestStreaming(
                        i,
                        feature,
                        bundle,
                        i2,
                        m741$$Nest$mwrapCancellationFuture,
                        androidFuture,
                        new IStreamingResponseCallback
                                .Stub() { // from class:
                                          // com.android.server.ondeviceintelligence.BundleUtil.1
                            public final /* synthetic */ AndroidFuture val$future;
                            public final /* synthetic */ InferenceInfoStore val$inferenceInfoStore;
                            public final /* synthetic */ Executor val$resourceClosingExecutor;
                            public final /* synthetic */ IStreamingResponseCallback
                                    val$streamingResponseCallback;

                            public AnonymousClass1(
                                    IStreamingResponseCallback iStreamingResponseCallback2,
                                    Executor executor,
                                    InferenceInfoStore inferenceInfoStore,
                                    AndroidFuture androidFuture52) {
                                r1 = iStreamingResponseCallback2;
                                r2 = executor;
                                r3 = inferenceInfoStore;
                                r4 = androidFuture52;
                            }

                            public final void onDataAugmentRequest(
                                    Bundle bundle2, RemoteCallback remoteCallback) {
                                try {
                                    BundleUtil.sanitizeResponseParams(bundle2);
                                    r1.onDataAugmentRequest(
                                            bundle2,
                                            new RemoteCallback(
                                                    new BundleUtil$1$$ExternalSyntheticLambda2(
                                                            remoteCallback, r2, 0)));
                                } finally {
                                    r2.execute(
                                            new BundleUtil$1$$ExternalSyntheticLambda0(2, bundle2));
                                }
                            }

                            public final void onFailure(
                                    int i3, String str, PersistableBundle persistableBundle) {
                                r1.onFailure(i3, str, persistableBundle);
                                r3.addInferenceInfoFromBundle(persistableBundle);
                                r4.completeExceptionally(new TimeoutException());
                            }

                            public final void onNewContent(Bundle bundle2) {
                                try {
                                    BundleUtil.sanitizeResponseParams(bundle2);
                                    r1.onNewContent(bundle2);
                                } finally {
                                    r2.execute(
                                            new BundleUtil$1$$ExternalSyntheticLambda0(0, bundle2));
                                }
                            }

                            public final void onSuccess(Bundle bundle2) {
                                try {
                                    BundleUtil.sanitizeResponseParams(bundle2);
                                    r1.onSuccess(bundle2);
                                } finally {
                                    r3.addInferenceInfoFromBundle(bundle2);
                                    r2.execute(
                                            new BundleUtil$1$$ExternalSyntheticLambda0(1, bundle2));
                                    r4.complete((Object) null);
                                }
                            }
                        });
                longForUser =
                        Settings.Secure.getLongForUser(
                                r12.mContext.getContentResolver(),
                                "on_device_intelligence_idle_timeout_ms",
                                TimeUnit.HOURS.toMillis(1L),
                                OnDeviceIntelligenceManagerService.this.mContext.getUserId());
                return androidFuture52.orTimeout(longForUser, TimeUnit.MILLISECONDS);
            default:
                OnDeviceIntelligenceManagerService.AnonymousClass1 anonymousClass12 = this.f$0;
                int i3 = this.f$1;
                Feature feature2 = this.f$2;
                Bundle bundle2 = this.f$3;
                int i4 = this.f$4;
                AndroidFuture androidFuture6 = this.f$5;
                AndroidFuture androidFuture7 = this.f$6;
                IResponseCallback iResponseCallback = (IResponseCallback) this.f$7;
                IOnDeviceSandboxedInferenceService iOnDeviceSandboxedInferenceService2 =
                        (IOnDeviceSandboxedInferenceService) obj;
                anonymousClass12.getClass();
                AndroidFuture androidFuture8 = new AndroidFuture();
                AndroidFuture m741$$Nest$mwrapCancellationFuture2 =
                        OnDeviceIntelligenceManagerService.m741$$Nest$mwrapCancellationFuture(
                                OnDeviceIntelligenceManagerService.this, androidFuture6);
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService3 =
                        OnDeviceIntelligenceManagerService.this;
                onDeviceIntelligenceManagerService3.getClass();
                if (androidFuture7 == null) {
                    androidFuture2 = null;
                } else {
                    androidFuture2 = new AndroidFuture();
                    androidFuture2.whenCompleteAsync(
                            new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6(
                                    onDeviceIntelligenceManagerService3, androidFuture7, 0));
                }
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService4 =
                        OnDeviceIntelligenceManagerService.this;
                iOnDeviceSandboxedInferenceService2.processRequest(
                        i3,
                        feature2,
                        bundle2,
                        i4,
                        m741$$Nest$mwrapCancellationFuture2,
                        androidFuture2,
                        new IResponseCallback
                                .Stub() { // from class:
                                          // com.android.server.ondeviceintelligence.BundleUtil.2
                            public final /* synthetic */ AndroidFuture val$future;
                            public final /* synthetic */ InferenceInfoStore val$inferenceInfoStore;
                            public final /* synthetic */ Executor val$resourceClosingExecutor;
                            public final /* synthetic */ IResponseCallback val$responseCallback;

                            public AnonymousClass2(
                                    IResponseCallback iResponseCallback2,
                                    InferenceInfoStore inferenceInfoStore,
                                    Executor executor,
                                    AndroidFuture androidFuture82) {
                                r1 = iResponseCallback2;
                                r2 = inferenceInfoStore;
                                r3 = executor;
                                r4 = androidFuture82;
                            }

                            public final void onDataAugmentRequest(
                                    Bundle bundle3, RemoteCallback remoteCallback) {
                                try {
                                    BundleUtil.sanitizeResponseParams(bundle3);
                                    r1.onDataAugmentRequest(
                                            bundle3,
                                            new RemoteCallback(
                                                    new BundleUtil$1$$ExternalSyntheticLambda2(
                                                            remoteCallback, r3, 1)));
                                } finally {
                                    r3.execute(
                                            new BundleUtil$1$$ExternalSyntheticLambda0(4, bundle3));
                                }
                            }

                            public final void onFailure(
                                    int i5, String str, PersistableBundle persistableBundle) {
                                r1.onFailure(i5, str, persistableBundle);
                                r2.addInferenceInfoFromBundle(persistableBundle);
                                r4.completeExceptionally(new TimeoutException());
                            }

                            public final void onSuccess(Bundle bundle3) {
                                try {
                                    BundleUtil.sanitizeResponseParams(bundle3);
                                    r1.onSuccess(bundle3);
                                } finally {
                                    r2.addInferenceInfoFromBundle(bundle3);
                                    r3.execute(
                                            new BundleUtil$1$$ExternalSyntheticLambda0(5, bundle3));
                                    r4.complete((Object) null);
                                }
                            }
                        });
                longForUser2 =
                        Settings.Secure.getLongForUser(
                                r12.mContext.getContentResolver(),
                                "on_device_intelligence_idle_timeout_ms",
                                TimeUnit.HOURS.toMillis(1L),
                                OnDeviceIntelligenceManagerService.this.mContext.getUserId());
                return androidFuture82.orTimeout(longForUser2, TimeUnit.MILLISECONDS);
        }
    }
}
