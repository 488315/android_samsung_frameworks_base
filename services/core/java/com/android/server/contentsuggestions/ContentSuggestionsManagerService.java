package com.android.server.contentsuggestions;

import android.R;
import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.IContentSuggestionsManager;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.Slog;
import android.window.TaskSnapshot;

import com.android.internal.os.IResultReceiver;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;

import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContentSuggestionsManagerService extends AbstractMasterSystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ContentSuggestionsManagerStub extends IContentSuggestionsManager.Stub {
        public ContentSuggestionsManagerStub() {}

        public final void classifyContentSelections(
                int i,
                ClassificationsRequest classificationsRequest,
                IClassificationsCallback iClassificationsCallback) {
            RemoteContentSuggestionsService ensureRemoteServiceLocked;
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "classifyContentSelections");
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                        (ContentSuggestionsPerUserService)
                                ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                if (contentSuggestionsPerUserService != null
                        && (ensureRemoteServiceLocked =
                                        contentSuggestionsPerUserService
                                                .ensureRemoteServiceLocked())
                                != null) {
                    ensureRemoteServiceLocked.classifyContentSelections(
                            classificationsRequest, iClassificationsCallback);
                }
            }
        }

        public final void isEnabled(int i, IResultReceiver iResultReceiver) {
            boolean isDisabledLocked;
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "isEnabled");
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                isDisabledLocked = ContentSuggestionsManagerService.this.isDisabledLocked(i);
            }
            iResultReceiver.send(!isDisabledLocked ? 1 : 0, (Bundle) null);
        }

        public final void notifyInteraction(int i, String str, Bundle bundle) {
            RemoteContentSuggestionsService ensureRemoteServiceLocked;
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "notifyInteraction");
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                        (ContentSuggestionsPerUserService)
                                ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                if (contentSuggestionsPerUserService != null
                        && (ensureRemoteServiceLocked =
                                        contentSuggestionsPerUserService
                                                .ensureRemoteServiceLocked())
                                != null) {
                    ensureRemoteServiceLocked.notifyInteraction(bundle, str);
                }
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
            int callingUid = Binder.getCallingUid();
            if (callingUid == 2000 || callingUid == 0) {
                new ContentSuggestionsManagerServiceShellCommand(
                                ContentSuggestionsManagerService.this)
                        .exec(
                                this,
                                fileDescriptor,
                                fileDescriptor2,
                                fileDescriptor3,
                                strArr,
                                shellCallback,
                                resultReceiver);
            } else {
                Slog.e("ContentSuggestionsManagerService", "Expected shell caller");
            }
        }

        public final void provideContextBitmap(int i, Bitmap bitmap, Bundle bundle) {
            if (bitmap == null) {
                throw new IllegalArgumentException("Expected non-null bitmap");
            }
            if (bundle == null) {
                throw new IllegalArgumentException("Expected non-null imageContextRequestExtras");
            }
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "provideContextBitmap");
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                try {
                    ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                            (ContentSuggestionsPerUserService)
                                    ContentSuggestionsManagerService.this.getServiceForUserLocked(
                                            i);
                    if (contentSuggestionsPerUserService != null) {
                        bundle.putParcelable("android.contentsuggestions.extra.BITMAP", bitmap);
                        RemoteContentSuggestionsService ensureRemoteServiceLocked =
                                contentSuggestionsPerUserService.ensureRemoteServiceLocked();
                        if (ensureRemoteServiceLocked != null) {
                            ensureRemoteServiceLocked.provideContextImage(-1, null, 0, bundle);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void provideContextImage(int i, int i2, Bundle bundle) {
            HardwareBuffer hardwareBuffer;
            RemoteContentSuggestionsService ensureRemoteServiceLocked;
            TaskSnapshot taskSnapshot;
            if (bundle == null) {
                throw new IllegalArgumentException("Expected non-null imageContextRequestExtras");
            }
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "provideContextImage");
            int i3 = 0;
            if (bundle.containsKey("android.contentsuggestions.extra.BITMAP")
                    || (taskSnapshot =
                                    ActivityTaskManagerService.this.getTaskSnapshot(
                                            i2, false, false))
                            == null) {
                hardwareBuffer = null;
            } else {
                hardwareBuffer = taskSnapshot.getHardwareBuffer();
                ColorSpace colorSpace = taskSnapshot.getColorSpace();
                if (colorSpace != null) {
                    i3 = colorSpace.getId();
                }
            }
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                        (ContentSuggestionsPerUserService)
                                ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                if (contentSuggestionsPerUserService != null
                        && (ensureRemoteServiceLocked =
                                        contentSuggestionsPerUserService
                                                .ensureRemoteServiceLocked())
                                != null) {
                    ensureRemoteServiceLocked.provideContextImage(i2, hardwareBuffer, i3, bundle);
                }
            }
        }

        public final void resetTemporaryService(int i) {
            ContentSuggestionsManagerService.this.resetTemporaryService(i);
        }

        public final void setDefaultServiceEnabled(int i, boolean z) {
            ContentSuggestionsManagerService.this.setDefaultServiceEnabled(i, z);
        }

        public final void setTemporaryService(int i, String str, int i2) {
            ContentSuggestionsManagerService.this.setTemporaryService(i, str, i2);
        }

        public final void suggestContentSelections(
                int i,
                SelectionsRequest selectionsRequest,
                ISelectionsCallback iSelectionsCallback) {
            RemoteContentSuggestionsService ensureRemoteServiceLocked;
            ContentSuggestionsManagerService.m385$$Nest$menforceCaller(
                    ContentSuggestionsManagerService.this,
                    UserHandle.getCallingUserId(),
                    "suggestContentSelections");
            synchronized (ContentSuggestionsManagerService.this.mLock) {
                ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                        (ContentSuggestionsPerUserService)
                                ContentSuggestionsManagerService.this.getServiceForUserLocked(i);
                if (contentSuggestionsPerUserService != null
                        && (ensureRemoteServiceLocked =
                                        contentSuggestionsPerUserService
                                                .ensureRemoteServiceLocked())
                                != null) {
                    ensureRemoteServiceLocked.suggestContentSelections(
                            selectionsRequest, iSelectionsCallback);
                }
            }
        }
    }

    /* renamed from: -$$Nest$menforceCaller, reason: not valid java name */
    public static void m385$$Nest$menforceCaller(
            ContentSuggestionsManagerService contentSuggestionsManagerService, int i, String str) {
        if (contentSuggestionsManagerService
                                .getContext()
                                .checkCallingPermission(
                                        "android.permission.MANAGE_CONTENT_SUGGESTIONS")
                        != 0
                && !contentSuggestionsManagerService.mServiceNameResolver.isTemporary(i)
                && !contentSuggestionsManagerService.mActivityTaskManagerInternal.isCallerRecents(
                        Binder.getCallingUid())) {
            throw new SecurityException(
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Permission Denial: ", str, " from pid="),
                            ", uid=",
                            " expected caller is recents",
                            "ContentSuggestionsManagerService"));
        }
    }

    public ContentSuggestionsManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(
                        context, R.string.date_picker_increment_year_button),
                "no_content_suggestions");
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_CONTENT_SUGGESTIONS",
                        "ContentSuggestionsManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                new ContentSuggestionsPerUserService(this, this.mLock, i);
        return contentSuggestionsPerUserService;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("content_suggestions", new ContentSuggestionsManagerStub());
    }
}
