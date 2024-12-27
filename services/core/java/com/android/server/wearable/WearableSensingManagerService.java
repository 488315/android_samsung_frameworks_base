package com.android.server.wearable;

import android.R;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.wearable.IWearableSensingCallback;
import android.app.wearable.IWearableSensingManager;
import android.app.wearable.WearableSensingDataRequest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.ResultReceiver;
import android.os.SharedMemory;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.service.voice.HotwordAudioStream;
import android.service.voice.VoiceInteractionManagerInternal;
import android.service.wearable.IWearableSensingService;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.infra.ServiceConnector;
import com.android.server.LocalServices;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.utils.quota.MultiRateLimiter;

import java.io.FileDescriptor;
import java.time.Duration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WearableSensingManagerService extends AbstractMasterSystemService {
    public final Context mContext;
    public final Set mDataRequestObserverContexts;
    public volatile MultiRateLimiter mDataRequestRateLimiter;
    public volatile boolean mIsServiceEnabled;
    public final AtomicInteger mNextDataRequestObserverId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DataRequestObserverContext {
        public final int mDataRequestObserverId;
        public final PendingIntent mDataRequestPendingIntent;
        public final RemoteCallback mDataRequestRemoteCallback;
        public final int mDataType;
        public final int mUserId;

        public DataRequestObserverContext(
                int i, int i2, int i3, PendingIntent pendingIntent, RemoteCallback remoteCallback) {
            this.mDataType = i;
            this.mUserId = i2;
            this.mDataRequestObserverId = i3;
            this.mDataRequestPendingIntent = pendingIntent;
            this.mDataRequestRemoteCallback = remoteCallback;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WearableSensingManagerInternal extends IWearableSensingManager.Stub {
        public WearableSensingManagerInternal() {}

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new WearableSensingShellCommand(WearableSensingManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void provideConnection(
                ParcelFileDescriptor parcelFileDescriptor,
                IWearableSensingCallback iWearableSensingCallback,
                RemoteCallback remoteCallback) {
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal provideConnection.");
            Objects.requireNonNull(parcelFileDescriptor);
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (WearableSensingManagerService.this.mIsServiceEnabled) {
                WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                        WearableSensingManagerService.this,
                        new WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1(
                                parcelFileDescriptor, iWearableSensingCallback, remoteCallback, 0),
                        remoteCallback);
            } else {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            }
        }

        public final void provideData(
                PersistableBundle persistableBundle,
                SharedMemory sharedMemory,
                RemoteCallback remoteCallback) {
            Slog.d("WearableSensingManagerService", "WearableSensingManagerInternal provideData.");
            Objects.requireNonNull(persistableBundle);
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (WearableSensingManagerService.this.mIsServiceEnabled) {
                WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                        WearableSensingManagerService.this,
                        new WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1(
                                persistableBundle, sharedMemory, remoteCallback),
                        remoteCallback);
            } else {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            }
        }

        public final void provideDataStream(
                ParcelFileDescriptor parcelFileDescriptor,
                IWearableSensingCallback iWearableSensingCallback,
                RemoteCallback remoteCallback) {
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal provideDataStream.");
            Objects.requireNonNull(parcelFileDescriptor);
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (WearableSensingManagerService.this.mIsServiceEnabled) {
                WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                        WearableSensingManagerService.this,
                        new WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda1(
                                parcelFileDescriptor, iWearableSensingCallback, remoteCallback, 1),
                        remoteCallback);
            } else {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            }
        }

        public final void registerDataRequestObserver(
                final int i,
                final PendingIntent pendingIntent,
                final RemoteCallback remoteCallback) {
            final RemoteCallback remoteCallback2;
            final int i2;
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal registerDataRequestObserver.");
            Objects.requireNonNull(pendingIntent);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (!WearableSensingManagerService.this.mIsServiceEnabled) {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
                return;
            }
            final int callingUserId = UserHandle.getCallingUserId();
            synchronized (WearableSensingManagerService.this.mDataRequestObserverContexts) {
                try {
                    DataRequestObserverContext m1045$$Nest$mgetDataRequestObserverContext =
                            WearableSensingManagerService
                                    .m1045$$Nest$mgetDataRequestObserverContext(
                                            WearableSensingManagerService.this,
                                            i,
                                            callingUserId,
                                            pendingIntent);
                    if (m1045$$Nest$mgetDataRequestObserverContext != null) {
                        Slog.i(
                                "WearableSensingManagerService",
                                "Received duplicate data request observer.");
                        RemoteCallback remoteCallback3 =
                                m1045$$Nest$mgetDataRequestObserverContext
                                        .mDataRequestRemoteCallback;
                        i2 = m1045$$Nest$mgetDataRequestObserverContext.mDataRequestObserverId;
                        remoteCallback2 = remoteCallback3;
                    } else {
                        final WearableSensingManagerService wearableSensingManagerService =
                                WearableSensingManagerService.this;
                        wearableSensingManagerService.getClass();
                        RemoteCallback remoteCallback4 =
                                new RemoteCallback(
                                        new RemoteCallback.OnResultListener() { // from class:
                                            // com.android.server.wearable.WearableSensingManagerService$$ExternalSyntheticLambda1
                                            public final void onResult(Bundle bundle) {
                                                WearableSensingManagerService
                                                        wearableSensingManagerService2 =
                                                                WearableSensingManagerService.this;
                                                int i3 = callingUserId;
                                                PendingIntent pendingIntent2 = pendingIntent;
                                                wearableSensingManagerService2.getClass();
                                                WearableSensingDataRequest
                                                        wearableSensingDataRequest =
                                                                (WearableSensingDataRequest)
                                                                        bundle.getParcelable(
                                                                                "android.app.wearable.WearableSensingDataRequestBundleKey",
                                                                                WearableSensingDataRequest
                                                                                        .class);
                                                if (wearableSensingDataRequest == null) {
                                                    Slog.e(
                                                            "WearableSensingManagerService",
                                                            "Received data request callback without"
                                                                + " a request.");
                                                    return;
                                                }
                                                RemoteCallback remoteCallback5 =
                                                        (RemoteCallback)
                                                                bundle.getParcelable(
                                                                        "android.app.wearable.WearableSensingDataRequestStatusCallbackBundleKey",
                                                                        RemoteCallback.class);
                                                if (remoteCallback5 == null) {
                                                    Slog.e(
                                                            "WearableSensingManagerService",
                                                            "Received data request callback without"
                                                                + " a status callback.");
                                                    return;
                                                }
                                                if (wearableSensingDataRequest.getDataSize()
                                                        > WearableSensingDataRequest
                                                                .getMaxRequestSize()) {
                                                    Slog.w(
                                                            "WearableSensingManagerService",
                                                            TextUtils.formatSimple(
                                                                    "WearableSensingDataRequest"
                                                                        + " size exceeds the"
                                                                        + " maximum allowed size of"
                                                                        + " %s bytes. Dropping the"
                                                                        + " request.",
                                                                    new Object[] {
                                                                        Integer.valueOf(
                                                                                WearableSensingDataRequest
                                                                                        .getMaxRequestSize())
                                                                    }));
                                                    WearableSensingManagerPerUserService
                                                            .notifyStatusCallback(
                                                                    3, remoteCallback5);
                                                    return;
                                                }
                                                if (!wearableSensingManagerService2
                                                        .mDataRequestRateLimiter.isWithinQuota(
                                                        i3,
                                                        "android",
                                                        "WearableSensingManagerService")) {
                                                    Slog.w(
                                                            "WearableSensingManagerService",
                                                            "Data request exceeded rate limit."
                                                                + " Dropping the request.");
                                                    WearableSensingManagerPerUserService
                                                            .notifyStatusCallback(
                                                                    4, remoteCallback5);
                                                    return;
                                                }
                                                Intent intent = new Intent();
                                                intent.putExtra(
                                                        "android.app.wearable.extra.WEARABLE_SENSING_DATA_REQUEST",
                                                        (Parcelable) wearableSensingDataRequest);
                                                BroadcastOptions makeBasic =
                                                        BroadcastOptions.makeBasic();
                                                makeBasic
                                                        .setPendingIntentBackgroundActivityStartMode(
                                                                2);
                                                wearableSensingManagerService2
                                                        .mDataRequestRateLimiter.noteEvent(
                                                        i3,
                                                        "android",
                                                        "WearableSensingManagerService");
                                                long clearCallingIdentity =
                                                        Binder.clearCallingIdentity();
                                                try {
                                                    try {
                                                        pendingIntent2.send(
                                                                wearableSensingManagerService2
                                                                        .getContext(),
                                                                0,
                                                                intent,
                                                                null,
                                                                null,
                                                                null,
                                                                makeBasic.toBundle());
                                                        WearableSensingManagerPerUserService
                                                                .notifyStatusCallback(
                                                                        1, remoteCallback5);
                                                        Slog.i(
                                                                "WearableSensingManagerService",
                                                                TextUtils.formatSimple(
                                                                        "Sending data request to"
                                                                            + " %s: %s",
                                                                        new Object[] {
                                                                            pendingIntent2
                                                                                    .getCreatorPackage(),
                                                                            wearableSensingDataRequest
                                                                                    .toExpandedString()
                                                                        }));
                                                    } catch (
                                                            PendingIntent.CanceledException
                                                                    unused) {
                                                        Slog.w(
                                                                "WearableSensingManagerService",
                                                                "Could not deliver pendingIntent: "
                                                                        + pendingIntent2);
                                                        WearableSensingManagerPerUserService
                                                                .notifyStatusCallback(
                                                                        2, remoteCallback5);
                                                    }
                                                } finally {
                                                    Binder.restoreCallingIdentity(
                                                            clearCallingIdentity);
                                                }
                                            }
                                        });
                        int andIncrement =
                                WearableSensingManagerService.this.mNextDataRequestObserverId
                                        .getAndIncrement();
                        ((HashSet) WearableSensingManagerService.this.mDataRequestObserverContexts)
                                .add(
                                        new DataRequestObserverContext(
                                                i,
                                                callingUserId,
                                                andIncrement,
                                                pendingIntent,
                                                remoteCallback4));
                        remoteCallback2 = remoteCallback4;
                        i2 = andIncrement;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                    WearableSensingManagerService.this,
                    new Consumer() { // from class:
                        // com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda6
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            final int i3 = i;
                            final RemoteCallback remoteCallback5 = remoteCallback2;
                            final int i4 = i2;
                            PendingIntent pendingIntent2 = pendingIntent;
                            final RemoteCallback remoteCallback6 = remoteCallback;
                            WearableSensingManagerPerUserService
                                    wearableSensingManagerPerUserService =
                                            (WearableSensingManagerPerUserService) obj;
                            final String creatorPackage = pendingIntent2.getCreatorPackage();
                            synchronized (wearableSensingManagerPerUserService.mLock) {
                                try {
                                    if (!wearableSensingManagerPerUserService
                                            .setUpServiceIfNeeded()) {
                                        Slog.w(
                                                "WearableSensingManagerPerUserService",
                                                "Detection service is not available at this"
                                                    + " moment.");
                                        WearableSensingManagerPerUserService.notifyStatusCallback(
                                                3, remoteCallback6);
                                    } else {
                                        wearableSensingManagerPerUserService
                                                .ensureRemoteServiceInitiated$2();
                                        RemoteWearableSensingService remoteWearableSensingService =
                                                wearableSensingManagerPerUserService.mRemoteService;
                                        remoteWearableSensingService.getClass();
                                        remoteWearableSensingService.post(
                                                new ServiceConnector.VoidJob() { // from class:
                                                    // com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda4
                                                    public final void runNoResult(Object obj2) {
                                                        int i5 = i3;
                                                        RemoteCallback remoteCallback7 =
                                                                remoteCallback5;
                                                        int i6 = i4;
                                                        String str = creatorPackage;
                                                        RemoteCallback remoteCallback8 =
                                                                remoteCallback6;
                                                        int i7 =
                                                                RemoteWearableSensingService
                                                                        .$r8$clinit;
                                                        ((IWearableSensingService) obj2)
                                                                .registerDataRequestObserver(
                                                                        i5,
                                                                        remoteCallback7,
                                                                        i6,
                                                                        str,
                                                                        remoteCallback8);
                                                    }
                                                });
                                    }
                                } finally {
                                }
                            }
                        }
                    },
                    remoteCallback);
        }

        public final void startHotwordRecognition(
                final ComponentName componentName, final RemoteCallback remoteCallback) {
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal startHotwordRecognition.");
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (WearableSensingManagerService.this.mIsServiceEnabled) {
                WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                        WearableSensingManagerService.this,
                        new Consumer() { // from class:
                            // com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                final ComponentName componentName2 = componentName;
                                final RemoteCallback remoteCallback2 = remoteCallback;
                                final WearableSensingManagerPerUserService
                                        wearableSensingManagerPerUserService =
                                                (WearableSensingManagerPerUserService) obj;
                                synchronized (wearableSensingManagerPerUserService.mLock) {
                                    try {
                                        if (!wearableSensingManagerPerUserService
                                                .setUpServiceIfNeeded()) {
                                            Slog.w(
                                                    "WearableSensingManagerPerUserService",
                                                    "Detection service is not available at this"
                                                        + " moment.");
                                            WearableSensingManagerPerUserService
                                                    .notifyStatusCallback(3, remoteCallback2);
                                            return;
                                        }
                                        if (wearableSensingManagerPerUserService
                                                        .mVoiceInteractionManagerInternal
                                                == null) {
                                            wearableSensingManagerPerUserService
                                                            .mVoiceInteractionManagerInternal =
                                                    (VoiceInteractionManagerInternal)
                                                            LocalServices.getService(
                                                                    VoiceInteractionManagerInternal
                                                                            .class);
                                        }
                                        if (wearableSensingManagerPerUserService
                                                        .mVoiceInteractionManagerInternal
                                                == null) {
                                            Slog.w(
                                                    "WearableSensingManagerPerUserService",
                                                    "Voice interaction manager is not available at"
                                                        + " this moment.");
                                            WearableSensingManagerPerUserService
                                                    .notifyStatusCallback(3, remoteCallback2);
                                            return;
                                        }
                                        wearableSensingManagerPerUserService
                                                .ensureRemoteServiceInitiated$2();
                                        RemoteWearableSensingService remoteWearableSensingService =
                                                wearableSensingManagerPerUserService.mRemoteService;
                                        final RemoteCallback remoteCallback3 =
                                                new RemoteCallback(
                                                        new RemoteCallback
                                                                .OnResultListener() { // from class:
                                                            // com.android.server.wearable.WearableSensingManagerPerUserService$$ExternalSyntheticLambda0
                                                            public final void onResult(
                                                                    Bundle bundle) {
                                                                WearableSensingManagerPerUserService
                                                                        wearableSensingManagerPerUserService2 =
                                                                                WearableSensingManagerPerUserService
                                                                                        .this;
                                                                ComponentName componentName3 =
                                                                        componentName2;
                                                                wearableSensingManagerPerUserService2
                                                                        .getClass();
                                                                HotwordAudioStream
                                                                        hotwordAudioStream =
                                                                                (HotwordAudioStream)
                                                                                        bundle
                                                                                                .getParcelable(
                                                                                                        "android.app.wearable.HotwordAudioStreamBundleKey",
                                                                                                        HotwordAudioStream
                                                                                                                .class);
                                                                if (hotwordAudioStream == null) {
                                                                    Slog.w(
                                                                            "WearableSensingManagerPerUserService",
                                                                            "No hotword audio"
                                                                                + " stream"
                                                                                + " received,"
                                                                                + " unable to"
                                                                                + " process"
                                                                                + " hotword.");
                                                                    return;
                                                                }
                                                                long clearCallingIdentity =
                                                                        Binder
                                                                                .clearCallingIdentity();
                                                                try {
                                                                    wearableSensingManagerPerUserService2
                                                                            .mVoiceInteractionManagerInternal
                                                                            .startListeningFromWearable(
                                                                                    hotwordAudioStream
                                                                                            .getAudioStreamParcelFileDescriptor(),
                                                                                    hotwordAudioStream
                                                                                            .getAudioFormat(),
                                                                                    hotwordAudioStream
                                                                                            .getMetadata(),
                                                                                    componentName3,
                                                                                    wearableSensingManagerPerUserService2
                                                                                            .mUserId,
                                                                                    new VoiceInteractionManagerInternal
                                                                                            .WearableHotwordDetectionCallback() { // from class: com.android.server.wearable.WearableSensingManagerPerUserService.2
                                                                                        public
                                                                                        AnonymousClass2() {}

                                                                                        public final
                                                                                        void
                                                                                                onDetected() {
                                                                                            Slog.i(
                                                                                                    "WearableSensingManagerPerUserService",
                                                                                                    "hotwordDetectionCallback"
                                                                                                        + " onDetected.");
                                                                                            WearableSensingManagerPerUserService
                                                                                                    wearableSensingManagerPerUserService3 =
                                                                                                            WearableSensingManagerPerUserService
                                                                                                                    .this;
                                                                                            synchronized (
                                                                                                    wearableSensingManagerPerUserService3
                                                                                                            .mLock) {
                                                                                                try {
                                                                                                    if (!wearableSensingManagerPerUserService3
                                                                                                            .setUpServiceIfNeeded()) {
                                                                                                        Slog
                                                                                                                .w(
                                                                                                                        "WearableSensingManagerPerUserService",
                                                                                                                        "Wearable"
                                                                                                                            + " sensing"
                                                                                                                            + " service"
                                                                                                                            + " is not"
                                                                                                                            + " available"
                                                                                                                            + " at this"
                                                                                                                            + " moment.");
                                                                                                        return;
                                                                                                    }
                                                                                                    wearableSensingManagerPerUserService3
                                                                                                            .ensureRemoteServiceInitiated$2();
                                                                                                    RemoteWearableSensingService
                                                                                                            remoteWearableSensingService2 =
                                                                                                                    wearableSensingManagerPerUserService3
                                                                                                                            .mRemoteService;
                                                                                                    remoteWearableSensingService2
                                                                                                            .getClass();
                                                                                                    remoteWearableSensingService2
                                                                                                            .post(
                                                                                                                    new RemoteWearableSensingService$$ExternalSyntheticLambda7(
                                                                                                                            1));
                                                                                                } finally {
                                                                                                }
                                                                                            }
                                                                                        }

                                                                                        public final
                                                                                        void
                                                                                                onError(
                                                                                                        String
                                                                                                                str) {
                                                                                            Slog.i(
                                                                                                    "WearableSensingManagerPerUserService",
                                                                                                    "hotwordDetectionCallback"
                                                                                                        + " onError."
                                                                                                        + " ErrorMessage:"
                                                                                                        + " "
                                                                                                            + str);
                                                                                            WearableSensingManagerPerUserService
                                                                                                    .m1043$$Nest$mstopActiveHotwordAudio(
                                                                                                            WearableSensingManagerPerUserService
                                                                                                                    .this);
                                                                                        }

                                                                                        public final
                                                                                        void
                                                                                                onRejected() {
                                                                                            Slog.i(
                                                                                                    "WearableSensingManagerPerUserService",
                                                                                                    "hotwordDetectionCallback"
                                                                                                        + " onRejected.");
                                                                                            WearableSensingManagerPerUserService
                                                                                                    .m1043$$Nest$mstopActiveHotwordAudio(
                                                                                                            WearableSensingManagerPerUserService
                                                                                                                    .this);
                                                                                        }
                                                                                    });
                                                                } finally {
                                                                    Binder.restoreCallingIdentity(
                                                                            clearCallingIdentity);
                                                                }
                                                            }
                                                        });
                                        remoteWearableSensingService.getClass();
                                        remoteWearableSensingService.post(
                                                new ServiceConnector.VoidJob() { // from class:
                                                    // com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda3
                                                    public final void runNoResult(Object obj2) {
                                                        RemoteCallback remoteCallback4 =
                                                                remoteCallback3;
                                                        RemoteCallback remoteCallback5 =
                                                                remoteCallback2;
                                                        int i =
                                                                RemoteWearableSensingService
                                                                        .$r8$clinit;
                                                        ((IWearableSensingService) obj2)
                                                                .startHotwordRecognition(
                                                                        remoteCallback4,
                                                                        remoteCallback5);
                                                    }
                                                });
                                    } finally {
                                    }
                                }
                            }
                        },
                        remoteCallback);
            } else {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            }
        }

        public final void stopHotwordRecognition(final RemoteCallback remoteCallback) {
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal stopHotwordRecognition.");
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (WearableSensingManagerService.this.mIsServiceEnabled) {
                WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                        WearableSensingManagerService.this,
                        new Consumer() { // from class:
                            // com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                final RemoteCallback remoteCallback2 = remoteCallback;
                                WearableSensingManagerPerUserService
                                        wearableSensingManagerPerUserService =
                                                (WearableSensingManagerPerUserService) obj;
                                synchronized (wearableSensingManagerPerUserService.mLock) {
                                    try {
                                        if (!wearableSensingManagerPerUserService
                                                .setUpServiceIfNeeded()) {
                                            Slog.w(
                                                    "WearableSensingManagerPerUserService",
                                                    "Detection service is not available at this"
                                                        + " moment.");
                                            WearableSensingManagerPerUserService
                                                    .notifyStatusCallback(3, remoteCallback2);
                                        } else {
                                            wearableSensingManagerPerUserService
                                                    .ensureRemoteServiceInitiated$2();
                                            RemoteWearableSensingService
                                                    remoteWearableSensingService =
                                                            wearableSensingManagerPerUserService
                                                                    .mRemoteService;
                                            remoteWearableSensingService.getClass();
                                            remoteWearableSensingService.post(
                                                    new ServiceConnector.VoidJob() { // from class:
                                                        // com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda5
                                                        public final void runNoResult(Object obj2) {
                                                            RemoteCallback remoteCallback3 =
                                                                    remoteCallback2;
                                                            int i =
                                                                    RemoteWearableSensingService
                                                                            .$r8$clinit;
                                                            ((IWearableSensingService) obj2)
                                                                    .stopHotwordRecognition(
                                                                            remoteCallback3);
                                                        }
                                                    });
                                        }
                                    } finally {
                                    }
                                }
                            }
                        },
                        remoteCallback);
            } else {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            }
        }

        public final void unregisterDataRequestObserver(
                final int i, PendingIntent pendingIntent, final RemoteCallback remoteCallback) {
            Slog.i(
                    "WearableSensingManagerService",
                    "WearableSensingManagerInternal unregisterDataRequestObserver.");
            Objects.requireNonNull(pendingIntent);
            Objects.requireNonNull(remoteCallback);
            WearableSensingManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.MANAGE_WEARABLE_SENSING_SERVICE",
                    "WearableSensingManagerService");
            if (!WearableSensingManagerService.this.mIsServiceEnabled) {
                Slog.w("WearableSensingManagerService", "Service not available.");
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            synchronized (WearableSensingManagerService.this.mDataRequestObserverContexts) {
                try {
                    DataRequestObserverContext m1045$$Nest$mgetDataRequestObserverContext =
                            WearableSensingManagerService
                                    .m1045$$Nest$mgetDataRequestObserverContext(
                                            WearableSensingManagerService.this,
                                            i,
                                            callingUserId,
                                            pendingIntent);
                    if (m1045$$Nest$mgetDataRequestObserverContext == null) {
                        Slog.w(
                                "WearableSensingManagerService",
                                "Previous observer not found, cannot unregister.");
                        return;
                    }
                    ((HashSet) WearableSensingManagerService.this.mDataRequestObserverContexts)
                            .remove(m1045$$Nest$mgetDataRequestObserverContext);
                    final int i2 =
                            m1045$$Nest$mgetDataRequestObserverContext.mDataRequestObserverId;
                    final String creatorPackage =
                            m1045$$Nest$mgetDataRequestObserverContext.mDataRequestPendingIntent
                                    .getCreatorPackage();
                    WearableSensingManagerService.m1044$$Nest$mcallPerUserServiceIfExist(
                            WearableSensingManagerService.this,
                            new Consumer() { // from class:
                                // com.android.server.wearable.WearableSensingManagerService$WearableSensingManagerInternal$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    final int i3 = i;
                                    final int i4 = i2;
                                    final String str = creatorPackage;
                                    final RemoteCallback remoteCallback2 = remoteCallback;
                                    WearableSensingManagerPerUserService
                                            wearableSensingManagerPerUserService =
                                                    (WearableSensingManagerPerUserService) obj;
                                    synchronized (wearableSensingManagerPerUserService.mLock) {
                                        try {
                                            if (!wearableSensingManagerPerUserService
                                                    .setUpServiceIfNeeded()) {
                                                Slog.w(
                                                        "WearableSensingManagerPerUserService",
                                                        "Detection service is not available at this"
                                                            + " moment.");
                                                WearableSensingManagerPerUserService
                                                        .notifyStatusCallback(3, remoteCallback2);
                                            } else {
                                                wearableSensingManagerPerUserService
                                                        .ensureRemoteServiceInitiated$2();
                                                RemoteWearableSensingService
                                                        remoteWearableSensingService =
                                                                wearableSensingManagerPerUserService
                                                                        .mRemoteService;
                                                remoteWearableSensingService.getClass();
                                                remoteWearableSensingService.post(
                                                        new ServiceConnector
                                                                .VoidJob() { // from class:
                                                            // com.android.server.wearable.RemoteWearableSensingService$$ExternalSyntheticLambda6
                                                            public final void runNoResult(
                                                                    Object obj2) {
                                                                int i5 = i3;
                                                                int i6 = i4;
                                                                String str2 = str;
                                                                RemoteCallback remoteCallback3 =
                                                                        remoteCallback2;
                                                                int i7 =
                                                                        RemoteWearableSensingService
                                                                                .$r8$clinit;
                                                                ((IWearableSensingService) obj2)
                                                                        .unregisterDataRequestObserver(
                                                                                i5,
                                                                                i6,
                                                                                str2,
                                                                                remoteCallback3);
                                                            }
                                                        });
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            },
                            remoteCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcallPerUserServiceIfExist, reason: not valid java name */
    public static void m1044$$Nest$mcallPerUserServiceIfExist(
            WearableSensingManagerService wearableSensingManagerService,
            Consumer consumer,
            RemoteCallback remoteCallback) {
        wearableSensingManagerService.getClass();
        int callingUserId = UserHandle.getCallingUserId();
        synchronized (wearableSensingManagerService.mLock) {
            try {
                WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                        (WearableSensingManagerPerUserService)
                                wearableSensingManagerService.getServiceForUserLocked(
                                        callingUserId);
                if (wearableSensingManagerPerUserService != null) {
                    consumer.accept(wearableSensingManagerPerUserService);
                    return;
                }
                Slog.w(
                        "WearableSensingManagerService",
                        "Service not available for userId " + callingUserId);
                WearableSensingManagerPerUserService.notifyStatusCallback(3, remoteCallback);
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mgetDataRequestObserverContext, reason: not valid java name */
    public static DataRequestObserverContext m1045$$Nest$mgetDataRequestObserverContext(
            WearableSensingManagerService wearableSensingManagerService,
            int i,
            int i2,
            PendingIntent pendingIntent) {
        synchronized (wearableSensingManagerService.mDataRequestObserverContexts) {
            try {
                Iterator it =
                        ((HashSet) wearableSensingManagerService.mDataRequestObserverContexts)
                                .iterator();
                while (it.hasNext()) {
                    DataRequestObserverContext dataRequestObserverContext =
                            (DataRequestObserverContext) it.next();
                    if (dataRequestObserverContext.mDataType == i
                            && dataRequestObserverContext.mUserId == i2
                            && dataRequestObserverContext.mDataRequestPendingIntent.equals(
                                    pendingIntent)) {
                        return dataRequestObserverContext;
                    }
                }
                return null;
            } finally {
            }
        }
    }

    public WearableSensingManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(
                        context, R.string.device_storage_monitor_notification_channel),
                null,
                68);
        this.mNextDataRequestObserverId = new AtomicInteger(1);
        this.mDataRequestObserverContexts = new HashSet();
        this.mContext = context;
        MultiRateLimiter.Builder builder = new MultiRateLimiter.Builder(context, null);
        builder.addRateLimit(
                WearableSensingDataRequest.getRateLimit(),
                WearableSensingDataRequest.getRateLimitWindowSize());
        this.mDataRequestRateLimiter = new MultiRateLimiter(builder.mQuotaTrackers);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT",
                        "WearableSensingManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return Build.isDebuggable() ? Integer.MAX_VALUE : 30000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new WearableSensingManagerPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService,
              // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            DeviceConfig.addOnPropertiesChangedListener(
                    "wearable_sensing",
                    getContext().getMainExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.wearable.WearableSensingManagerService$$ExternalSyntheticLambda0
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            WearableSensingManagerService wearableSensingManagerService =
                                    WearableSensingManagerService.this;
                            wearableSensingManagerService.getClass();
                            if (properties.getKeyset().contains("service_enabled")) {
                                wearableSensingManagerService.mIsServiceEnabled =
                                        DeviceConfig.getBoolean(
                                                "wearable_sensing", "service_enabled", true);
                            }
                        }
                    });
            this.mIsServiceEnabled =
                    DeviceConfig.getBoolean("wearable_sensing", "service_enabled", true);
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        Slog.d("WearableSensingManagerService", "onServicePackageRestartedLocked.");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        Slog.d("WearableSensingManagerService", "onServicePackageUpdatedLocked.");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                (WearableSensingManagerPerUserService) abstractPerUserSystemService;
        Slog.d("WearableSensingManagerService", "onServiceRemoved");
        wearableSensingManagerPerUserService.getClass();
        Slog.d(
                "WearableSensingManagerPerUserService",
                "Trying to cancel the remote request. Reason: Service destroyed.");
        if (wearableSensingManagerPerUserService.mRemoteService != null) {
            synchronized (wearableSensingManagerPerUserService.mLock) {
                wearableSensingManagerPerUserService.mRemoteService.unbind();
                wearableSensingManagerPerUserService.mRemoteService = null;
            }
        }
        synchronized (wearableSensingManagerPerUserService.mSecureChannelLock) {
            try {
                WearableSensingSecureChannel wearableSensingSecureChannel =
                        wearableSensingManagerPerUserService.mSecureChannel;
                if (wearableSensingSecureChannel != null) {
                    wearableSensingSecureChannel.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("wearable_sensing", new WearableSensingManagerInternal());
    }

    public void provideData(
            int i,
            PersistableBundle persistableBundle,
            SharedMemory sharedMemory,
            RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                        (WearableSensingManagerPerUserService) getServiceForUserLocked(i);
                if (wearableSensingManagerPerUserService != null) {
                    wearableSensingManagerPerUserService.onProvidedData(
                            persistableBundle, sharedMemory, remoteCallback);
                } else {
                    Slog.w("WearableSensingManagerService", "Service not available.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void provideDataStream(
            int i, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) {
        synchronized (this.mLock) {
            try {
                WearableSensingManagerPerUserService wearableSensingManagerPerUserService =
                        (WearableSensingManagerPerUserService) getServiceForUserLocked(i);
                if (wearableSensingManagerPerUserService != null) {
                    wearableSensingManagerPerUserService.onProvideDataStream(
                            parcelFileDescriptor, null, remoteCallback);
                } else {
                    Slog.w("WearableSensingManagerService", "Service not available.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void resetDataRequestRateLimitWindowSize() {
        Slog.w(
                "WearableSensingManagerService",
                "Resetting the data request rate limit window size back to the default value. This"
                    + " also resets the current limit and should only be callable from a test.");
        MultiRateLimiter.Builder builder = new MultiRateLimiter.Builder(this.mContext, null);
        builder.addRateLimit(
                WearableSensingDataRequest.getRateLimit(),
                WearableSensingDataRequest.getRateLimitWindowSize());
        this.mDataRequestRateLimiter = new MultiRateLimiter(builder.mQuotaTrackers);
    }

    public void setDataRequestRateLimitWindowSize(Duration duration) {
        Slog.w(
                "WearableSensingManagerService",
                TextUtils.formatSimple(
                        "Setting the data request rate limit window size to %s. This also resets"
                            + " the current limit and should only be callable from a test.",
                        new Object[] {duration}));
        MultiRateLimiter.Builder builder = new MultiRateLimiter.Builder(this.mContext, null);
        builder.addRateLimit(WearableSensingDataRequest.getRateLimit(), duration);
        this.mDataRequestRateLimiter = new MultiRateLimiter(builder.mQuotaTrackers);
    }
}
