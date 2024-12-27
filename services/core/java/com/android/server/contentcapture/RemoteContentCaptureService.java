package com.android.server.contentcapture;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.service.contentcapture.ActivityEvent;
import android.service.contentcapture.IContentCaptureService;
import android.service.contentcapture.IContentCaptureServiceCallback;
import android.service.contentcapture.SnapshotData;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Slog;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.DataShareRequest;

import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.CollectionUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteContentCaptureService
        extends AbstractMultiplePendingRequestsRemoteService {
    public final int mIdleUnbindTimeoutMs;
    public final ContentCapturePerUserService mPerUserService;
    public final IBinder mServerCallback;

    public RemoteContentCaptureService(
            Context context,
            ComponentName componentName,
            IContentCaptureServiceCallback iContentCaptureServiceCallback,
            int i,
            ContentCapturePerUserService contentCapturePerUserService,
            boolean z,
            boolean z2,
            int i2) {
        super(
                context,
                "android.service.contentcapture.ContentCaptureService",
                componentName,
                i,
                contentCapturePerUserService,
                context.getMainThreadHandler(),
                (z ? 4194304 : 0) | 4096,
                z2,
                2);
        this.mPerUserService = contentCapturePerUserService;
        this.mServerCallback = iContentCaptureServiceCallback.asBinder();
        this.mIdleUnbindTimeoutMs = i2;
        scheduleBind();
    }

    public final void ensureBoundLocked() {
        scheduleBind();
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IContentCaptureService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return this.mIdleUnbindTimeoutMs;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        ArraySet whitelistedPackages;
        if (z && this.mIdleUnbindTimeoutMs != 0) {
            scheduleUnbind();
        }
        try {
            if (!z) {
                ((AbstractMultiplePendingRequestsRemoteService) this).mService.onDisconnected();
                ContentCaptureMetricsLogger.writeServiceEvent(
                        2, ((AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
                EventLog.writeEvent(53200, Integer.valueOf(this.mPerUserService.mUserId), 2, 0);
                return;
            }
            try {
                ((AbstractMultiplePendingRequestsRemoteService) this)
                        .mService.onConnected(
                                this.mServerCallback,
                                ContentCaptureHelper.sVerbose,
                                ContentCaptureHelper.sDebug);
                ContentCaptureMetricsLogger.writeServiceEvent(
                        1, ((AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
                Integer valueOf = Integer.valueOf(this.mPerUserService.mUserId);
                ContentCapturePerUserService contentCapturePerUserService = this.mPerUserService;
                synchronized (contentCapturePerUserService.mLock) {
                    whitelistedPackages =
                            ((ContentCaptureManagerService) contentCapturePerUserService.mMaster)
                                    .mGlobalContentCaptureOptions.getWhitelistedPackages(
                                            contentCapturePerUserService.mUserId);
                }
                EventLog.writeEvent(
                        53200,
                        valueOf,
                        1,
                        Integer.valueOf(CollectionUtils.size(whitelistedPackages)));
            } finally {
                this.mPerUserService.onConnected();
            }
        } catch (Exception e) {
            Slog.w(
                    ((AbstractMultiplePendingRequestsRemoteService) this).mTag,
                    "Exception calling onConnectedStateChanged(" + z + "): " + e);
        }
    }

    public final void onActivityLifecycleEvent(ActivityEvent activityEvent) {
        scheduleAsyncRequest(
                new RemoteContentCaptureService$$ExternalSyntheticLambda0(activityEvent));
    }

    public final void onActivitySnapshotRequest(final int i, final SnapshotData snapshotData) {
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda5
                    public final void run(IInterface iInterface) {
                        ((IContentCaptureService) iInterface).onActivitySnapshot(i, snapshotData);
                    }
                });
    }

    public final void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
        scheduleAsyncRequest(
                new RemoteContentCaptureService$$ExternalSyntheticLambda0(dataRemovalRequest));
        ContentCaptureMetricsLogger.writeServiceEvent(
                5, ((AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
    }

    public final void onDataShareRequest(
            final DataShareRequest dataShareRequest,
            final ContentCaptureManagerService.DataShareCallbackDelegate
                    dataShareCallbackDelegate) {
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda4
                    public final void run(IInterface iInterface) {
                        ((IContentCaptureService) iInterface)
                                .onDataShared(dataShareRequest, dataShareCallbackDelegate);
                    }
                });
        ContentCaptureMetricsLogger.writeServiceEvent(
                6, ((AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
    }

    public final void onSessionFinished(final int i) {
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda1
                    public final void run(IInterface iInterface) {
                        ((IContentCaptureService) iInterface).onSessionFinished(i);
                    }
                });
        ContentCaptureMetricsLogger.writeSessionEvent(i, 2, 0, getComponentName());
    }

    public final void onSessionStarted(
            final ContentCaptureContext contentCaptureContext,
            final int i,
            final int i2,
            final IResultReceiver iResultReceiver,
            final int i3) {
        scheduleAsyncRequest(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda2
                    public final void run(IInterface iInterface) {
                        ((IContentCaptureService) iInterface)
                                .onSessionStarted(
                                        contentCaptureContext, i, i2, iResultReceiver, i3);
                    }
                });
        ContentCaptureMetricsLogger.writeSessionEvent(i, 1, i3, getComponentName());
    }
}
