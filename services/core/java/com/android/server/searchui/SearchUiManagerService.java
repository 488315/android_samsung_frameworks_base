package com.android.server.searchui;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.search.ISearchCallback;
import android.app.search.ISearchUiManager;
import android.app.search.Query;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.app.search.SearchTargetEvent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.io.FileDescriptor;
import java.util.function.Consumer;

public final class SearchUiManagerService extends AbstractMasterSystemService {
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;

    public final class SearchUiManagerStub extends ISearchUiManager.Stub {
        public SearchUiManagerStub() {}

        public final void createSearchSession(
                SearchContext searchContext, SearchSessionId searchSessionId, IBinder iBinder) {
            runForUserLocked(
                    "createSearchSession",
                    searchSessionId,
                    new SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0(
                            searchContext, searchSessionId, iBinder));
        }

        public final void destroySearchSession(final SearchSessionId searchSessionId) {
            runForUserLocked(
                    "destroySearchSession",
                    searchSessionId,
                    new Consumer() { // from class:
                                     // com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SearchUiPerUserService) obj).onDestroyLocked(searchSessionId);
                        }
                    });
        }

        public final void notifyEvent(
                SearchSessionId searchSessionId, Query query, SearchTargetEvent searchTargetEvent) {
            runForUserLocked(
                    "notifyEvent",
                    searchSessionId,
                    new SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0(
                            searchSessionId, query, searchTargetEvent, 0));
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new SearchUiManagerServiceShellCommand(SearchUiManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void query(
                SearchSessionId searchSessionId, Query query, ISearchCallback iSearchCallback) {
            runForUserLocked(
                    "query",
                    searchSessionId,
                    new SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0(
                            searchSessionId, query, iSearchCallback, 1));
        }

        public final void registerEmptyQueryResultUpdateCallback(
                SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
            runForUserLocked(
                    "registerEmptyQueryResultUpdateCallback",
                    searchSessionId,
                    new SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda3(
                            searchSessionId, iSearchCallback, 0));
        }

        public final void runForUserLocked(
                String str, SearchSessionId searchSessionId, Consumer consumer) {
            int handleIncomingUser =
                    ((ActivityManagerInternal)
                                    LocalServices.getService(ActivityManagerInternal.class))
                            .handleIncomingUser(
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid(),
                                    searchSessionId.getUserId(),
                                    false,
                                    0,
                                    (String) null,
                                    (String) null);
            if (SearchUiManagerService.this.mServiceNameResolver.isTemporary(handleIncomingUser)
                    || SearchUiManagerService.this.mActivityTaskManagerInternal.isCallerRecents(
                            Binder.getCallingUid())) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (SearchUiManagerService.this.mLock) {
                        consumer.accept(
                                (SearchUiPerUserService)
                                        SearchUiManagerService.this.getServiceForUserLocked(
                                                handleIncomingUser));
                    }
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Permission Denial: ", str, " from pid=");
            m.append(Binder.getCallingPid());
            m.append(", uid=");
            m.append(Binder.getCallingUid());
            String sb = m.toString();
            Slog.w("SearchUiManagerService", sb);
            throw new SecurityException(sb);
        }

        public final void unregisterEmptyQueryResultUpdateCallback(
                SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
            runForUserLocked(
                    "unregisterEmptyQueryResultUpdateCallback",
                    searchSessionId,
                    new SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda3(
                            searchSessionId, iSearchCallback, 1));
        }
    }

    public SearchUiManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(context, R.string.demo_starting_message),
                null,
                17);
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_SEARCH_UI", "SearchUiManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new SearchUiPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        SearchUiPerUserService searchUiPerUserService =
                (SearchUiPerUserService) peekServiceForUserLocked(i);
        if (searchUiPerUserService != null) {
            if (searchUiPerUserService.mMaster.debug) {
                Slog.v("SearchUiPerUserService", "onPackageRestartedLocked()");
            }
            searchUiPerUserService.destroyAndRebindRemoteService$2();
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        SearchUiPerUserService searchUiPerUserService =
                (SearchUiPerUserService) peekServiceForUserLocked(i);
        if (searchUiPerUserService != null) {
            if (searchUiPerUserService.mMaster.debug) {
                Slog.v("SearchUiPerUserService", "onPackageUpdatedLocked()");
            }
            searchUiPerUserService.destroyAndRebindRemoteService$2();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("search_ui", new SearchUiManagerStub());
    }
}
