package com.android.server.ondeviceintelligence;

import android.R;
import android.app.AppGlobals;
import android.app.ondeviceintelligence.Feature;
import android.app.ondeviceintelligence.FeatureDetails;
import android.app.ondeviceintelligence.IDownloadCallback;
import android.app.ondeviceintelligence.IFeatureCallback;
import android.app.ondeviceintelligence.IFeatureDetailsCallback;
import android.app.ondeviceintelligence.IListFeaturesCallback;
import android.app.ondeviceintelligence.IOnDeviceIntelligenceManager;
import android.app.ondeviceintelligence.IResponseCallback;
import android.app.ondeviceintelligence.IStreamingResponseCallback;
import android.app.ondeviceintelligence.ITokenInfoCallback;
import android.app.ondeviceintelligence.InferenceInfo;
import android.app.ondeviceintelligence.OnDeviceIntelligenceException;
import android.app.ondeviceintelligence.TokenInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;
import android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService;
import android.service.ondeviceintelligence.IProcessingUpdateStatusCallback;
import android.service.ondeviceintelligence.IRemoteProcessingService;
import android.service.ondeviceintelligence.IRemoteStorageService;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.BackgroundThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.ondeviceintelligence.callbacks.ListenableDownloadCallback;

import java.io.FileDescriptor;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OnDeviceIntelligenceManagerService extends SystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long MAX_AGE_MS = TimeUnit.HOURS.toMillis(3);
    public final Executor broadcastExecutor;
    public final Executor callbackExecutor;
    public String mBroadcastPackageName;
    public final Executor mConfigExecutor;
    public final Context mContext;
    public final InferenceInfoStore mInferenceInfoStore;
    public volatile boolean mIsServiceEnabled;
    public final Object mLock;
    public final Handler mMainHandler;
    public final OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0
            mOnPropertiesChangedListener;
    public RemoteOnDeviceSandboxedInferenceService mRemoteInferenceService;
    public RemoteOnDeviceIntelligenceService mRemoteOnDeviceIntelligenceService;
    public String[] mTemporaryBroadcastKeys;
    public String mTemporaryConfigNamespace;
    public AnonymousClass10 mTemporaryHandler;
    public String[] mTemporaryServiceNames;
    public int remoteInferenceServiceUid;
    public final Executor resourceClosingExecutor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IOnDeviceIntelligenceManager.Stub {
        public AnonymousClass1() {}

        public final void getFeature(final int i, final IFeatureCallback iFeatureCallback) {
            int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal getFeatures");
            Objects.requireNonNull(iFeatureCallback);
            OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                    "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                    "OnDeviceIntelligenceManagerService");
            if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                iFeatureCallback.onFailure(
                        100,
                        "OnDeviceIntelligenceManagerService is unavailable",
                        PersistableBundle.EMPTY);
            } else {
                OnDeviceIntelligenceManagerService.this
                        .ensureRemoteIntelligenceServiceInitialized();
                final int callingUid = Binder.getCallingUid();
                OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService
                        .postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda4
                                    public final Object run(Object obj) {
                                        long longForUser;
                                        OnDeviceIntelligenceManagerService.AnonymousClass1
                                                anonymousClass1 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass1.this;
                                        int i3 = callingUid;
                                        int i4 = i;
                                        final IFeatureCallback iFeatureCallback2 = iFeatureCallback;
                                        anonymousClass1.getClass();
                                        final AndroidFuture androidFuture = new AndroidFuture();
                                        ((IOnDeviceIntelligenceService) obj)
                                                .getFeature(
                                                        i3,
                                                        i4,
                                                        new IFeatureCallback
                                                                .Stub() { // from class:
                                                                          // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.1.1
                                                            public final void onFailure(
                                                                    int i5,
                                                                    String str,
                                                                    PersistableBundle
                                                                            persistableBundle) {
                                                                iFeatureCallback2.onFailure(
                                                                        i5, str, persistableBundle);
                                                                androidFuture.completeExceptionally(
                                                                        new TimeoutException());
                                                            }

                                                            public final void onSuccess(
                                                                    Feature feature) {
                                                                iFeatureCallback2.onSuccess(
                                                                        feature);
                                                                androidFuture.complete(
                                                                        (Object) null);
                                                            }
                                                        });
                                        longForUser =
                                                Settings.Secure.getLongForUser(
                                                        r5.mContext.getContentResolver(),
                                                        "on_device_intelligence_idle_timeout_ms",
                                                        TimeUnit.HOURS.toMillis(1L),
                                                        OnDeviceIntelligenceManagerService.this
                                                                .mContext.getUserId());
                                        return androidFuture.orTimeout(
                                                longForUser, TimeUnit.MILLISECONDS);
                                    }
                                });
            }
        }

        public final void getFeatureDetails(
                final Feature feature, final IFeatureDetailsCallback iFeatureDetailsCallback) {
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal getFeatureStatus");
            Objects.requireNonNull(feature);
            Objects.requireNonNull(iFeatureDetailsCallback);
            OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                    "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                    "OnDeviceIntelligenceManagerService");
            if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                iFeatureDetailsCallback.onFailure(
                        100,
                        "OnDeviceIntelligenceManagerService is unavailable",
                        PersistableBundle.EMPTY);
            } else {
                OnDeviceIntelligenceManagerService.this
                        .ensureRemoteIntelligenceServiceInitialized();
                final int callingUid = Binder.getCallingUid();
                OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService
                        .postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda9
                                    public final Object run(Object obj) {
                                        long longForUser;
                                        OnDeviceIntelligenceManagerService.AnonymousClass1
                                                anonymousClass1 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass1.this;
                                        int i2 = callingUid;
                                        Feature feature2 = feature;
                                        final IFeatureDetailsCallback iFeatureDetailsCallback2 =
                                                iFeatureDetailsCallback;
                                        anonymousClass1.getClass();
                                        final AndroidFuture androidFuture = new AndroidFuture();
                                        ((IOnDeviceIntelligenceService) obj)
                                                .getFeatureDetails(
                                                        i2,
                                                        feature2,
                                                        new IFeatureDetailsCallback
                                                                .Stub() { // from class:
                                                                          // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.1.3
                                                            public final void onFailure(
                                                                    int i3,
                                                                    String str,
                                                                    PersistableBundle
                                                                            persistableBundle) {
                                                                androidFuture.completeExceptionally(
                                                                        (Throwable) null);
                                                                iFeatureDetailsCallback2.onFailure(
                                                                        i3, str, persistableBundle);
                                                            }

                                                            public final void onSuccess(
                                                                    FeatureDetails featureDetails) {
                                                                androidFuture.complete(
                                                                        (Object) null);
                                                                iFeatureDetailsCallback2.onSuccess(
                                                                        featureDetails);
                                                            }
                                                        });
                                        longForUser =
                                                Settings.Secure.getLongForUser(
                                                        r5.mContext.getContentResolver(),
                                                        "on_device_intelligence_idle_timeout_ms",
                                                        TimeUnit.HOURS.toMillis(1L),
                                                        OnDeviceIntelligenceManagerService.this
                                                                .mContext.getUserId());
                                        return androidFuture.orTimeout(
                                                longForUser, TimeUnit.MILLISECONDS);
                                    }
                                });
            }
        }

        public final List getLatestInferenceInfo(final long j) {
            Context context = OnDeviceIntelligenceManagerService.this.mContext;
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            context.enforceCallingPermission(
                    "android.permission.DUMP", "OnDeviceIntelligenceManagerService");
            return OnDeviceIntelligenceManagerService.this
                    .mInferenceInfoStore
                    .inferenceInfos
                    .stream()
                    .filter(
                            new Predicate() { // from class:
                                              // com.android.server.ondeviceintelligence.InferenceInfoStore$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return ((InferenceInfo) obj).getStartTimeMs() > j;
                                }
                            })
                    .toList();
        }

        public final String getRemoteServicePackageName() {
            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                    OnDeviceIntelligenceManagerService.this;
            onDeviceIntelligenceManagerService.getClass();
            try {
                ComponentName unflattenFromString =
                        ComponentName.unflattenFromString(
                                onDeviceIntelligenceManagerService.getServiceNames()[1]);
                if (unflattenFromString != null) {
                    return unflattenFromString.getPackageName();
                }
            } catch (Resources.NotFoundException e) {
                Slog.e("OnDeviceIntelligenceManagerService", "Could not find resource", e);
            }
            return null;
        }

        public final void getVersion(final RemoteCallback remoteCallback) {
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal getVersion");
            Objects.requireNonNull(remoteCallback);
            OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                    "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                    "OnDeviceIntelligenceManagerService");
            if (OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                OnDeviceIntelligenceManagerService.this
                        .ensureRemoteIntelligenceServiceInitialized();
                OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService
                        .postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda13
                                    public final Object run(Object obj) {
                                        long longForUser;
                                        OnDeviceIntelligenceManagerService.AnonymousClass1
                                                anonymousClass1 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass1.this;
                                        RemoteCallback remoteCallback2 = remoteCallback;
                                        anonymousClass1.getClass();
                                        AndroidFuture androidFuture = new AndroidFuture();
                                        ((IOnDeviceIntelligenceService) obj)
                                                .getVersion(
                                                        new RemoteCallback(
                                                                new OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda3(
                                                                        remoteCallback2,
                                                                        androidFuture)));
                                        longForUser =
                                                Settings.Secure.getLongForUser(
                                                        r4.mContext.getContentResolver(),
                                                        "on_device_intelligence_idle_timeout_ms",
                                                        TimeUnit.HOURS.toMillis(1L),
                                                        OnDeviceIntelligenceManagerService.this
                                                                .mContext.getUserId());
                                        return androidFuture.orTimeout(
                                                longForUser, TimeUnit.MILLISECONDS);
                                    }
                                });
            } else {
                Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                remoteCallback.sendResult((Bundle) null);
            }
        }

        public final void listFeatures(final IListFeaturesCallback iListFeaturesCallback) {
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal getFeatures");
            Objects.requireNonNull(iListFeaturesCallback);
            OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                    "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                    "OnDeviceIntelligenceManagerService");
            if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                iListFeaturesCallback.onFailure(
                        100,
                        "OnDeviceIntelligenceManagerService is unavailable",
                        PersistableBundle.EMPTY);
            } else {
                OnDeviceIntelligenceManagerService.this
                        .ensureRemoteIntelligenceServiceInitialized();
                final int callingUid = Binder.getCallingUid();
                OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService
                        .postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda3
                                    public final Object run(Object obj) {
                                        long longForUser;
                                        OnDeviceIntelligenceManagerService.AnonymousClass1
                                                anonymousClass1 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass1.this;
                                        int i2 = callingUid;
                                        final IListFeaturesCallback iListFeaturesCallback2 =
                                                iListFeaturesCallback;
                                        anonymousClass1.getClass();
                                        final AndroidFuture androidFuture = new AndroidFuture();
                                        ((IOnDeviceIntelligenceService) obj)
                                                .listFeatures(
                                                        i2,
                                                        new IListFeaturesCallback
                                                                .Stub() { // from class:
                                                                          // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.1.2
                                                            public final void onFailure(
                                                                    int i3,
                                                                    String str,
                                                                    PersistableBundle
                                                                            persistableBundle) {
                                                                iListFeaturesCallback2.onFailure(
                                                                        i3, str, persistableBundle);
                                                                androidFuture.completeExceptionally(
                                                                        new TimeoutException());
                                                            }

                                                            public final void onSuccess(List list) {
                                                                iListFeaturesCallback2.onSuccess(
                                                                        list);
                                                                androidFuture.complete(
                                                                        (Object) null);
                                                            }
                                                        });
                                        longForUser =
                                                Settings.Secure.getLongForUser(
                                                        r4.mContext.getContentResolver(),
                                                        "on_device_intelligence_idle_timeout_ms",
                                                        TimeUnit.HOURS.toMillis(1L),
                                                        OnDeviceIntelligenceManagerService.this
                                                                .mContext.getUserId());
                                        return androidFuture.orTimeout(
                                                longForUser, TimeUnit.MILLISECONDS);
                                    }
                                });
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new OnDeviceIntelligenceShellCommand(OnDeviceIntelligenceManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void processRequest(
                Feature feature,
                Bundle bundle,
                int i,
                AndroidFuture androidFuture,
                AndroidFuture androidFuture2,
                IResponseCallback iResponseCallback) {
            int i2 = 2;
            AndroidFuture androidFuture3 = null;
            try {
                int i3 = OnDeviceIntelligenceManagerService.$r8$clinit;
                Slog.i(
                        "OnDeviceIntelligenceManagerService",
                        "OnDeviceIntelligenceManagerInternal processRequest");
                Objects.requireNonNull(feature);
                BundleUtil.sanitizeInferenceParams(bundle);
                Objects.requireNonNull(iResponseCallback);
                OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                        "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                        "OnDeviceIntelligenceManagerService");
                if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                    Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                    iResponseCallback.onFailure(
                            15,
                            "OnDeviceIntelligenceManagerService is unavailable",
                            PersistableBundle.EMPTY);
                }
                OnDeviceIntelligenceManagerService.this.ensureRemoteInferenceServiceInitialized();
                androidFuture3 =
                        OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.postAsync(
                                new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda0(
                                        this,
                                        Binder.getCallingUid(),
                                        feature,
                                        bundle,
                                        i,
                                        androidFuture,
                                        androidFuture2,
                                        iResponseCallback));
                androidFuture3.whenCompleteAsync(
                        new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
                                i2, bundle),
                        OnDeviceIntelligenceManagerService.this.resourceClosingExecutor);
            } catch (Throwable th) {
                if (androidFuture3 == null) {
                    OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                            new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                    i2, bundle));
                }
                throw th;
            }
        }

        public final void processRequestStreaming(
                Feature feature,
                Bundle bundle,
                int i,
                AndroidFuture androidFuture,
                AndroidFuture androidFuture2,
                IStreamingResponseCallback iStreamingResponseCallback) {
            int i2 = 0;
            AndroidFuture androidFuture3 = null;
            try {
                int i3 = OnDeviceIntelligenceManagerService.$r8$clinit;
                Slog.i(
                        "OnDeviceIntelligenceManagerService",
                        "OnDeviceIntelligenceManagerInternal processRequestStreaming");
                Objects.requireNonNull(feature);
                BundleUtil.sanitizeInferenceParams(bundle);
                Objects.requireNonNull(iStreamingResponseCallback);
                OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                        "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                        "OnDeviceIntelligenceManagerService");
                if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                    Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                    iStreamingResponseCallback.onFailure(
                            15,
                            "OnDeviceIntelligenceManagerService is unavailable",
                            PersistableBundle.EMPTY);
                }
                OnDeviceIntelligenceManagerService.this.ensureRemoteInferenceServiceInitialized();
                androidFuture3 =
                        OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.postAsync(
                                new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda0(
                                        this,
                                        Binder.getCallingUid(),
                                        feature,
                                        bundle,
                                        i,
                                        androidFuture,
                                        androidFuture2,
                                        iStreamingResponseCallback));
                androidFuture3.whenCompleteAsync(
                        new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
                                i2, bundle),
                        OnDeviceIntelligenceManagerService.this.resourceClosingExecutor);
            } catch (Throwable th) {
                if (androidFuture3 == null) {
                    OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                            new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                    i2, bundle));
                }
                throw th;
            }
        }

        public final void requestFeatureDownload(
                final Feature feature,
                final AndroidFuture androidFuture,
                final IDownloadCallback iDownloadCallback) {
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal requestFeatureDownload");
            Objects.requireNonNull(feature);
            Objects.requireNonNull(iDownloadCallback);
            OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                    "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                    "OnDeviceIntelligenceManagerService");
            if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                iDownloadCallback.onDownloadFailed(
                        4,
                        "OnDeviceIntelligenceManagerService is unavailable",
                        PersistableBundle.EMPTY);
            }
            OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
            final int callingUid = Binder.getCallingUid();
            OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.postAsync(
                    new ServiceConnector
                            .Job() { // from class:
                                     // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda5
                        public final Object run(Object obj) {
                            long longForUser;
                            OnDeviceIntelligenceManagerService.AnonymousClass1 anonymousClass1 =
                                    OnDeviceIntelligenceManagerService.AnonymousClass1.this;
                            IDownloadCallback iDownloadCallback2 = iDownloadCallback;
                            int i2 = callingUid;
                            Feature feature2 = feature;
                            AndroidFuture androidFuture2 = androidFuture;
                            anonymousClass1.getClass();
                            AndroidFuture androidFuture3 = new AndroidFuture();
                            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                                    OnDeviceIntelligenceManagerService.this;
                            Handler handler = onDeviceIntelligenceManagerService.mMainHandler;
                            longForUser =
                                    Settings.Secure.getLongForUser(
                                            onDeviceIntelligenceManagerService.mContext
                                                    .getContentResolver(),
                                            "on_device_intelligence_idle_timeout_ms",
                                            TimeUnit.HOURS.toMillis(1L),
                                            onDeviceIntelligenceManagerService.mContext
                                                    .getUserId());
                            ((IOnDeviceIntelligenceService) obj)
                                    .requestFeatureDownload(
                                            i2,
                                            feature2,
                                            OnDeviceIntelligenceManagerService
                                                    .m741$$Nest$mwrapCancellationFuture(
                                                            OnDeviceIntelligenceManagerService.this,
                                                            androidFuture2),
                                            new ListenableDownloadCallback(
                                                    iDownloadCallback2,
                                                    handler,
                                                    androidFuture3,
                                                    longForUser));
                            return androidFuture3;
                        }
                    });
        }

        public final void requestTokenInfo(
                final Feature feature,
                final Bundle bundle,
                final AndroidFuture androidFuture,
                final ITokenInfoCallback iTokenInfoCallback) {
            int i = 1;
            int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.i(
                    "OnDeviceIntelligenceManagerService",
                    "OnDeviceIntelligenceManagerInternal requestTokenInfo");
            AndroidFuture androidFuture2 = null;
            try {
                Objects.requireNonNull(feature);
                BundleUtil.sanitizeInferenceParams(bundle);
                Objects.requireNonNull(iTokenInfoCallback);
                OnDeviceIntelligenceManagerService.this.mContext.enforceCallingPermission(
                        "android.permission.USE_ON_DEVICE_INTELLIGENCE",
                        "OnDeviceIntelligenceManagerService");
                if (!OnDeviceIntelligenceManagerService.this.mIsServiceEnabled) {
                    Slog.w("OnDeviceIntelligenceManagerService", "Service not available");
                    iTokenInfoCallback.onFailure(
                            100,
                            "OnDeviceIntelligenceManagerService is unavailable",
                            PersistableBundle.EMPTY);
                }
                OnDeviceIntelligenceManagerService.this.ensureRemoteInferenceServiceInitialized();
                final int callingUid = Binder.getCallingUid();
                androidFuture2 =
                        OnDeviceIntelligenceManagerService.this.mRemoteInferenceService.postAsync(
                                new ServiceConnector
                                        .Job() { // from class:
                                                 // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda10
                                    public final Object run(Object obj) {
                                        long longForUser;
                                        OnDeviceIntelligenceManagerService.AnonymousClass1
                                                anonymousClass1 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass1.this;
                                        int i3 = callingUid;
                                        Feature feature2 = feature;
                                        Bundle bundle2 = bundle;
                                        AndroidFuture androidFuture3 = androidFuture;
                                        ITokenInfoCallback iTokenInfoCallback2 = iTokenInfoCallback;
                                        anonymousClass1.getClass();
                                        AndroidFuture androidFuture4 = new AndroidFuture();
                                        ((IOnDeviceSandboxedInferenceService) obj)
                                                .requestTokenInfo(
                                                        i3,
                                                        feature2,
                                                        bundle2,
                                                        OnDeviceIntelligenceManagerService
                                                                .m741$$Nest$mwrapCancellationFuture(
                                                                        OnDeviceIntelligenceManagerService
                                                                                .this,
                                                                        androidFuture3),
                                                        new ITokenInfoCallback
                                                                .Stub() { // from class:
                                                                          // com.android.server.ondeviceintelligence.BundleUtil.3
                                                            public final /* synthetic */
                                                            AndroidFuture val$future;
                                                            public final /* synthetic */
                                                            InferenceInfoStore
                                                                    val$inferenceInfoStore;
                                                            public final /* synthetic */
                                                            ITokenInfoCallback val$responseCallback;

                                                            public AnonymousClass3(
                                                                    ITokenInfoCallback
                                                                            iTokenInfoCallback22,
                                                                    InferenceInfoStore
                                                                            inferenceInfoStore,
                                                                    AndroidFuture androidFuture42) {
                                                                r1 = iTokenInfoCallback22;
                                                                r2 = inferenceInfoStore;
                                                                r3 = androidFuture42;
                                                            }

                                                            public final void onFailure(
                                                                    int i4,
                                                                    String str,
                                                                    PersistableBundle
                                                                            persistableBundle) {
                                                                r1.onFailure(
                                                                        i4, str, persistableBundle);
                                                                r2.addInferenceInfoFromBundle(
                                                                        persistableBundle);
                                                                r3.completeExceptionally(
                                                                        new TimeoutException());
                                                            }

                                                            public final void onSuccess(
                                                                    TokenInfo tokenInfo) {
                                                                r1.onSuccess(tokenInfo);
                                                                r2.addInferenceInfoFromBundle(
                                                                        tokenInfo.getInfoParams());
                                                                r3.complete((Object) null);
                                                            }
                                                        });
                                        longForUser =
                                                Settings.Secure.getLongForUser(
                                                        r8.mContext.getContentResolver(),
                                                        "on_device_intelligence_idle_timeout_ms",
                                                        TimeUnit.HOURS.toMillis(1L),
                                                        OnDeviceIntelligenceManagerService.this
                                                                .mContext.getUserId());
                                        return androidFuture42.orTimeout(
                                                longForUser, TimeUnit.MILLISECONDS);
                                    }
                                });
                androidFuture2.whenCompleteAsync(
                        new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
                                i, bundle),
                        OnDeviceIntelligenceManagerService.this.resourceClosingExecutor);
            } catch (Throwable th) {
                if (androidFuture2 == null) {
                    OnDeviceIntelligenceManagerService.this.resourceClosingExecutor.execute(
                            new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                    i, bundle));
                }
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnector.ServiceLifecycleCallbacks {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ OnDeviceIntelligenceManagerService this$0;

        public /* synthetic */ AnonymousClass2(
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = onDeviceIntelligenceManagerService;
        }

        public final void onConnected(IInterface iInterface) {
            switch (this.$r8$classId) {
                case 0:
                    IOnDeviceIntelligenceService iOnDeviceIntelligenceService =
                            (IOnDeviceIntelligenceService) iInterface;
                    try {
                        OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService =
                                this.this$0;
                        onDeviceIntelligenceManagerService.getClass();
                        iOnDeviceIntelligenceService.registerRemoteServices(
                                onDeviceIntelligenceManagerService.new AnonymousClass3());
                        iOnDeviceIntelligenceService.ready();
                        break;
                    } catch (RemoteException e) {
                        int i = OnDeviceIntelligenceManagerService.$r8$clinit;
                        Slog.w(
                                "OnDeviceIntelligenceManagerService",
                                "Failed to send connected event",
                                e);
                        return;
                    }
                default:
                    final IOnDeviceSandboxedInferenceService iOnDeviceSandboxedInferenceService =
                            (IOnDeviceSandboxedInferenceService) iInterface;
                    try {
                        this.this$0.ensureRemoteIntelligenceServiceInitialized();
                        OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService2 =
                                this.this$0;
                        onDeviceIntelligenceManagerService2.getClass();
                        iOnDeviceSandboxedInferenceService.registerRemoteStorageService(
                                onDeviceIntelligenceManagerService2.new AnonymousClass7(),
                                new IRemoteCallback
                                        .Stub() { // from class:
                                                  // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$4$1
                                    public final void sendResult(Bundle bundle) {
                                        int callingUid = Binder.getCallingUid();
                                        OnDeviceIntelligenceManagerService
                                                onDeviceIntelligenceManagerService3 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass2.this
                                                                .this$0;
                                        synchronized (onDeviceIntelligenceManagerService3.mLock) {
                                            onDeviceIntelligenceManagerService3
                                                            .remoteInferenceServiceUid =
                                                    callingUid;
                                        }
                                    }
                                });
                        this.this$0.mRemoteOnDeviceIntelligenceService.run(
                                new OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda0());
                        this.this$0.broadcastExecutor.execute(
                                new Runnable() { // from class:
                                    // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        OnDeviceIntelligenceManagerService.AnonymousClass2
                                                anonymousClass2 =
                                                        OnDeviceIntelligenceManagerService
                                                                .AnonymousClass2.this;
                                        IOnDeviceSandboxedInferenceService
                                                iOnDeviceSandboxedInferenceService2 =
                                                        iOnDeviceSandboxedInferenceService;
                                        final OnDeviceIntelligenceManagerService
                                                onDeviceIntelligenceManagerService3 =
                                                        anonymousClass2.this$0;
                                        onDeviceIntelligenceManagerService3.getClass();
                                        try {
                                            final String[] broadcastKeys =
                                                    onDeviceIntelligenceManagerService3
                                                            .getBroadcastKeys();
                                            Bundle bundle = new Bundle();
                                            bundle.putBoolean(
                                                    "register_model_update_callback", true);
                                            try {
                                                iOnDeviceSandboxedInferenceService2
                                                        .updateProcessingState(
                                                                bundle,
                                                                new IProcessingUpdateStatusCallback
                                                                        .Stub() { // from class:
                                                                    // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.5
                                                                    public final void onFailure(
                                                                            int i2, String str) {
                                                                        int i3 =
                                                                                OnDeviceIntelligenceManagerService
                                                                                        .$r8$clinit;
                                                                        Slog.e(
                                                                                "OnDeviceIntelligenceManagerService",
                                                                                "Failed to register"
                                                                                    + " model"
                                                                                    + " loading"
                                                                                    + " callback"
                                                                                    + " with status"
                                                                                    + " code",
                                                                                new OnDeviceIntelligenceException(
                                                                                        i2, str));
                                                                    }

                                                                    public final void onSuccess(
                                                                            PersistableBundle
                                                                                    persistableBundle) {
                                                                        String str;
                                                                        Binder
                                                                                .clearCallingIdentity();
                                                                        synchronized (
                                                                                OnDeviceIntelligenceManagerService
                                                                                        .this
                                                                                        .mLock) {
                                                                            try {
                                                                                if (persistableBundle
                                                                                        .containsKey(
                                                                                                "model_loaded")) {
                                                                                    String str2 =
                                                                                            broadcastKeys[
                                                                                                    0];
                                                                                    if (str2 != null
                                                                                            && !str2
                                                                                                    .isEmpty()) {
                                                                                        Intent
                                                                                                intent =
                                                                                                        new Intent(
                                                                                                                str2);
                                                                                        intent
                                                                                                .setPackage(
                                                                                                        OnDeviceIntelligenceManagerService
                                                                                                                .this
                                                                                                                .mBroadcastPackageName);
                                                                                        OnDeviceIntelligenceManagerService
                                                                                                .this
                                                                                                .mContext
                                                                                                .sendBroadcast(
                                                                                                        intent,
                                                                                                        "android.permission.USE_ON_DEVICE_INTELLIGENCE");
                                                                                    }
                                                                                } else if (persistableBundle
                                                                                                .containsKey(
                                                                                                        "model_unloaded")
                                                                                        && (str =
                                                                                                        broadcastKeys[
                                                                                                                1])
                                                                                                != null
                                                                                        && !str
                                                                                                .isEmpty()) {
                                                                                    Intent intent2 =
                                                                                            new Intent(
                                                                                                    str);
                                                                                    intent2
                                                                                            .setPackage(
                                                                                                    OnDeviceIntelligenceManagerService
                                                                                                            .this
                                                                                                            .mBroadcastPackageName);
                                                                                    OnDeviceIntelligenceManagerService
                                                                                            .this
                                                                                            .mContext
                                                                                            .sendBroadcast(
                                                                                                    intent2,
                                                                                                    "android.permission.USE_ON_DEVICE_INTELLIGENCE");
                                                                                }
                                                                            } catch (Throwable th) {
                                                                                throw th;
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                            } catch (RemoteException e2) {
                                                Slog.e(
                                                        "OnDeviceIntelligenceManagerService",
                                                        "Failed to register model loading callback"
                                                            + " with status code",
                                                        e2);
                                            }
                                        } catch (Resources.NotFoundException unused) {
                                            Slog.d(
                                                    "OnDeviceIntelligenceManagerService",
                                                    "Skipping model broadcasts as broadcast intents"
                                                        + " configured.");
                                        }
                                    }
                                });
                        this.this$0.mConfigExecutor.execute(
                                new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda2(
                                        5, this));
                        break;
                    } catch (RemoteException e2) {
                        int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
                        Slog.w(
                                "OnDeviceIntelligenceManagerService",
                                "Failed to send connected event",
                                e2);
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends IRemoteProcessingService.Stub {
        public AnonymousClass3() {}

        public final void updateProcessingState(
                Bundle bundle, IProcessingUpdateStatusCallback iProcessingUpdateStatusCallback) {
            OnDeviceIntelligenceManagerService.this.callbackExecutor.execute(
                    new OnDeviceIntelligenceManagerService$3$$ExternalSyntheticLambda0(
                            this, bundle, iProcessingUpdateStatusCallback));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$6, reason: invalid class name */
    public final class AnonymousClass6 extends IProcessingUpdateStatusCallback.Stub {
        public final void onFailure(int i, String str) {
            int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.e(
                    "OnDeviceIntelligenceManagerService",
                    "Config update failed with code ["
                            + String.valueOf(i)
                            + "] and message = "
                            + str);
        }

        public final void onSuccess(PersistableBundle persistableBundle) {
            int i = OnDeviceIntelligenceManagerService.$r8$clinit;
            Slog.d(
                    "OnDeviceIntelligenceManagerService",
                    "Config update successful." + persistableBundle);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$7, reason: invalid class name */
    public final class AnonymousClass7 extends IRemoteStorageService.Stub {
        public AnonymousClass7() {}

        public final void getReadOnlyFeatureFileDescriptorMap(
                final Feature feature, final RemoteCallback remoteCallback) {
            OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
            OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.run(
                    new ServiceConnector
                            .VoidJob() { // from class:
                                         // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda2
                        public final void runNoResult(Object obj) {
                            OnDeviceIntelligenceManagerService.AnonymousClass7 anonymousClass7 =
                                    OnDeviceIntelligenceManagerService.AnonymousClass7.this;
                            Feature feature2 = feature;
                            RemoteCallback remoteCallback2 = remoteCallback;
                            anonymousClass7.getClass();
                            ((IOnDeviceIntelligenceService) obj)
                                    .getReadOnlyFeatureFileDescriptorMap(
                                            feature2,
                                            new RemoteCallback(
                                                    new OnDeviceIntelligenceManagerService$7$$ExternalSyntheticLambda3(
                                                            anonymousClass7, remoteCallback2)));
                        }
                    });
        }

        public final void getReadOnlyFileDescriptor(String str, AndroidFuture androidFuture) {
            OnDeviceIntelligenceManagerService.this.ensureRemoteIntelligenceServiceInitialized();
            AndroidFuture androidFuture2 = new AndroidFuture();
            OnDeviceIntelligenceManagerService.this.mRemoteOnDeviceIntelligenceService.run(
                    new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda3(
                            str, androidFuture2));
            androidFuture2.whenCompleteAsync(
                    new OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
                            androidFuture),
                    OnDeviceIntelligenceManagerService.this.callbackExecutor);
        }
    }

    /* renamed from: -$$Nest$mwrapCancellationFuture, reason: not valid java name */
    public static AndroidFuture m741$$Nest$mwrapCancellationFuture(
            OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService,
            AndroidFuture androidFuture) {
        onDeviceIntelligenceManagerService.getClass();
        if (androidFuture == null) {
            return null;
        }
        AndroidFuture androidFuture2 = new AndroidFuture();
        androidFuture2.whenCompleteAsync(
                new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda6(
                        onDeviceIntelligenceManagerService, androidFuture, 1));
        return androidFuture2;
    }

    public OnDeviceIntelligenceManagerService(Context context) {
        super(context);
        this.resourceClosingExecutor = Executors.newCachedThreadPool();
        this.callbackExecutor = Executors.newCachedThreadPool();
        this.broadcastExecutor = Executors.newCachedThreadPool();
        this.mConfigExecutor = Executors.newCachedThreadPool();
        this.mLock = new Object();
        this.remoteInferenceServiceUid = -1;
        this.mOnPropertiesChangedListener =
                new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0(this, 0);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mContext = context;
        this.mTemporaryServiceNames = new String[0];
        this.mInferenceInfoStore = new InferenceInfoStore(MAX_AGE_MS);
    }

    public static void checkServiceRequiresPermission(ServiceInfo serviceInfo, String str) {
        if (str.equals(serviceInfo.permission)) {
            return;
        }
        ComponentName componentName = serviceInfo.getComponentName();
        String str2 = serviceInfo.permission;
        StringBuilder sb = new StringBuilder("Service ");
        sb.append(componentName);
        sb.append(" requires ");
        sb.append(str);
        sb.append(" permission. Found ");
        throw new SecurityException(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str2, " permission"));
    }

    public static void enforceShellOnly(int i, String str) {
        if (i != 2000 && i != 0) {
            throw new SecurityException(str.concat(": Only shell user can call it"));
        }
    }

    public static void validateServiceElevated(String str, boolean z) {
        try {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalStateException(
                        "Remote service is not configured to complete the request");
            }
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager()
                            .getServiceInfo(
                                    ComponentName.unflattenFromString(str),
                                    786432L,
                                    UserHandle.SYSTEM.getIdentifier());
            if (serviceInfo == null) {
                throw new IllegalStateException(
                        "Remote service is not configured to complete the request.");
            }
            if (!z) {
                checkServiceRequiresPermission(
                        serviceInfo, "android.permission.BIND_ON_DEVICE_INTELLIGENCE_SERVICE");
                return;
            }
            checkServiceRequiresPermission(
                    serviceInfo, "android.permission.BIND_ON_DEVICE_SANDBOXED_INFERENCE_SERVICE");
            int i = serviceInfo.flags;
            if ((i & 2) == 0 || (i & 4) != 0) {
                throw new SecurityException(
                        "Call required an isolated service, but the configured service: "
                                + str
                                + ", is not isolated");
            }
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not fetch service info for remote services", e);
        }
    }

    public final void ensureRemoteInferenceServiceInitialized() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteInferenceService == null) {
                    String str = getServiceNames()[1];
                    Binder.withCleanCallingIdentity(
                            new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda4(
                                    this, str, 0));
                    Context context = this.mContext;
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                    RemoteOnDeviceSandboxedInferenceService
                            remoteOnDeviceSandboxedInferenceService =
                                    new RemoteOnDeviceSandboxedInferenceService(
                                            context,
                                            new Intent(
                                                            "android.service.ondeviceintelligence.OnDeviceSandboxedInferenceService")
                                                    .setComponent(unflattenFromString),
                                            67112960,
                                            UserHandle.SYSTEM.getIdentifier(),
                                            new RemoteOnDeviceSandboxedInferenceService$$ExternalSyntheticLambda0());
                    remoteOnDeviceSandboxedInferenceService.connect();
                    this.mRemoteInferenceService = remoteOnDeviceSandboxedInferenceService;
                    remoteOnDeviceSandboxedInferenceService.setServiceLifecycleCallbacks(
                            new AnonymousClass2(this, 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void ensureRemoteIntelligenceServiceInitialized() {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteOnDeviceIntelligenceService == null) {
                    String str = getServiceNames()[0];
                    Binder.withCleanCallingIdentity(
                            new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda4(
                                    this, str, 1));
                    Context context = this.mContext;
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                    RemoteOnDeviceIntelligenceService remoteOnDeviceIntelligenceService =
                            new RemoteOnDeviceIntelligenceService(
                                    context,
                                    new Intent(
                                                    "android.service.ondeviceintelligence.OnDeviceIntelligenceService")
                                            .setComponent(unflattenFromString),
                                    67112960,
                                    UserHandle.SYSTEM.getIdentifier(),
                                    new RemoteOnDeviceIntelligenceService$$ExternalSyntheticLambda0());
                    remoteOnDeviceIntelligenceService.connect();
                    this.mRemoteOnDeviceIntelligenceService = remoteOnDeviceIntelligenceService;
                    remoteOnDeviceIntelligenceService.setServiceLifecycleCallbacks(
                            new AnonymousClass2(this, 0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String[] getBroadcastKeys() {
        synchronized (this.mLock) {
            try {
                String[] strArr = this.mTemporaryBroadcastKeys;
                return (strArr == null || strArr.length != 2)
                        ? new String[] {
                            this.mContext
                                    .getResources()
                                    .getString(R.string.ext_media_badremoval_notification_title),
                            this.mContext.getResources().getString(R.string.ext_media_browse_action)
                        }
                        : strArr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String[] getServiceNames() {
        synchronized (this.mLock) {
            try {
                String[] strArr = this.mTemporaryServiceNames;
                return (strArr == null || strArr.length != 2)
                        ? new String[] {
                            this.mContext.getResources().getString(R.string.default_card_name),
                            this.mContext
                                    .getResources()
                                    .getString(R.string.default_notification_channel_label)
                        }
                        : strArr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService$10] */
    public final synchronized Handler getTemporaryHandler() {
        try {
            if (this.mTemporaryHandler == null) {
                this.mTemporaryHandler =
                        new Handler(
                                Looper
                                        .getMainLooper()) { // from class:
                                                            // com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService.10
                            @Override // android.os.Handler
                            public final void handleMessage(Message message) {
                                synchronized (OnDeviceIntelligenceManagerService.this.mLock) {
                                    try {
                                        int i = message.what;
                                        if (i == 0) {
                                            OnDeviceIntelligenceManagerService.this
                                                    .resetTemporaryServices();
                                        } else if (i == 1) {
                                            OnDeviceIntelligenceManagerService
                                                    onDeviceIntelligenceManagerService =
                                                            OnDeviceIntelligenceManagerService.this;
                                            onDeviceIntelligenceManagerService
                                                            .mTemporaryBroadcastKeys =
                                                    null;
                                            onDeviceIntelligenceManagerService
                                                            .mBroadcastPackageName =
                                                    "android";
                                        } else if (i == 2) {
                                            OnDeviceIntelligenceManagerService.this
                                                            .mTemporaryConfigNamespace =
                                                    null;
                                        } else {
                                            int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
                                            Slog.wtf(
                                                    "OnDeviceIntelligenceManagerService",
                                                    "invalid handler msg: " + message);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        };
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mTemporaryHandler;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            DeviceConfig.addOnPropertiesChangedListener(
                    "ondeviceintelligence",
                    BackgroundThread.getExecutor(),
                    new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda0(this, 1));
            this.mIsServiceEnabled =
                    DeviceConfig.getBoolean("ondeviceintelligence", "service_enabled", true);
        }
        if (i == 600) {
            try {
                ensureRemoteInferenceServiceInitialized();
                ensureRemoteIntelligenceServiceInitialized();
            } catch (Exception e) {
                Slog.w(
                        "OnDeviceIntelligenceManagerService",
                        "Couldn't pre-start remote ondeviceintelligence services.",
                        e);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("on_device_intelligence", new AnonymousClass1(), true);
        LocalServices.addService(
                OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2.class,
                new OnDeviceIntelligenceManagerService$$ExternalSyntheticLambda2(this));
    }

    public final void resetTemporaryServices() {
        synchronized (this.mLock) {
            try {
                AnonymousClass10 anonymousClass10 = this.mTemporaryHandler;
                if (anonymousClass10 != null) {
                    anonymousClass10.removeMessages(0);
                    this.mTemporaryHandler = null;
                }
                this.mRemoteInferenceService = null;
                this.mRemoteOnDeviceIntelligenceService = null;
                this.mTemporaryServiceNames = new String[0];
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
