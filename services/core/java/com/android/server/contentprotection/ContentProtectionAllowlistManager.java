package com.android.server.contentprotection;

import android.os.Handler;
import android.service.contentcapture.IContentProtectionAllowlistCallback;
import android.util.Slog;

import com.android.internal.content.PackageMonitor;
import com.android.server.contentcapture.ContentCaptureManagerService;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ContentProtectionAllowlistManager {
    public final ContentCaptureManagerService mContentCaptureManagerService;
    public final Handler mHandler;
    public boolean mStarted;
    public final long mTimeoutMs;
    public Instant mUpdatePendingUntil;
    public final Object mHandlerToken = new Object();
    public final Object mLock = new Object();
    public Set mAllowedPackages = Set.of();
    final PackageMonitor mPackageMonitor = createPackageMonitor();
    final IContentProtectionAllowlistCallback mAllowlistCallback = createAllowlistCallback();

    public final class ContentProtectionAllowlistCallback
            extends IContentProtectionAllowlistCallback.Stub {
        public ContentProtectionAllowlistCallback() {}

        public final void setAllowlist(final List list) {
            ContentProtectionAllowlistManager.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.contentprotection.ContentProtectionAllowlistManager$ContentProtectionAllowlistCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContentProtectionAllowlistManager.ContentProtectionAllowlistCallback
                                    contentProtectionAllowlistCallback =
                                            ContentProtectionAllowlistManager
                                                    .ContentProtectionAllowlistCallback.this;
                            List list2 = list;
                            ContentProtectionAllowlistManager contentProtectionAllowlistManager =
                                    ContentProtectionAllowlistManager.this;
                            synchronized (contentProtectionAllowlistManager.mLock) {
                                contentProtectionAllowlistManager.mAllowedPackages =
                                        (Set)
                                                list2.stream()
                                                        .collect(Collectors.toUnmodifiableSet());
                            }
                            contentProtectionAllowlistManager.mUpdatePendingUntil = null;
                        }
                    });
        }
    }

    public final class ContentProtectionPackageMonitor extends PackageMonitor {
        public ContentProtectionPackageMonitor() {}

        public final void onSomePackagesChanged() {
            ContentProtectionAllowlistManager.this.handlePackagesChanged();
        }
    }

    public ContentProtectionAllowlistManager(
            ContentCaptureManagerService contentCaptureManagerService, Handler handler, long j) {
        this.mContentCaptureManagerService = contentCaptureManagerService;
        this.mHandler = handler;
        this.mTimeoutMs = j;
    }

    public IContentProtectionAllowlistCallback createAllowlistCallback() {
        return new ContentProtectionAllowlistCallback();
    }

    public PackageMonitor createPackageMonitor() {
        return new ContentProtectionPackageMonitor();
    }

    public final void handlePackagesChanged() {
        RemoteContentProtectionService createRemoteContentProtectionService;
        if ((this.mUpdatePendingUntil == null || !Instant.now().isBefore(this.mUpdatePendingUntil))
                && (createRemoteContentProtectionService =
                                this.mContentCaptureManagerService
                                        .createRemoteContentProtectionService())
                        != null) {
            this.mHandler.removeCallbacksAndMessages(this.mHandlerToken);
            this.mUpdatePendingUntil = Instant.now().plusMillis(this.mTimeoutMs);
            try {
                createRemoteContentProtectionService.run(
                        new RemoteContentProtectionService$$ExternalSyntheticLambda1(
                                0, this.mAllowlistCallback));
            } catch (Exception e) {
                Slog.e("ContentProtectionAllowlistManager", "Failed to call remote service", e);
            }
        }
    }

    public final void stop() {
        try {
            this.mPackageMonitor.unregister();
        } catch (IllegalStateException unused) {
        }
        this.mHandler.removeCallbacksAndMessages(this.mHandlerToken);
        this.mUpdatePendingUntil = null;
        this.mStarted = false;
    }
}
