package com.android.server.searchui;

import android.app.AppGlobals;
import android.app.search.ISearchCallback;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.service.search.ISearchUiService;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.infra.AbstractRemoteService;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SearchUiPerUserService extends AbstractPerUserSystemService
        implements RemoteSearchUiService.RemoteSearchUiServiceCallbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
    public RemoteSearchUiService mRemoteService;
    public final ArrayMap mSessionInfos;
    public boolean mZombie;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SearchSessionInfo {
        public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
        public final IBinder.DeathRecipient mDeathRecipient;
        public final SearchContext mSearchContext;
        public final SearchSessionId mSessionId;
        public final IBinder mToken;

        public SearchSessionInfo(
                SearchSessionId searchSessionId,
                SearchContext searchContext,
                IBinder iBinder,
                SearchUiPerUserService$$ExternalSyntheticLambda2
                        searchUiPerUserService$$ExternalSyntheticLambda2) {
            int i = SearchUiPerUserService.$r8$clinit;
            Slog.d(
                    "SearchUiPerUserService",
                    "Creating SearchSessionInfo for session Id=" + searchSessionId);
            this.mSessionId = searchSessionId;
            this.mSearchContext = searchContext;
            this.mToken = iBinder;
            this.mDeathRecipient = searchUiPerUserService$$ExternalSyntheticLambda2;
        }
    }

    public SearchUiPerUserService(
            SearchUiManagerService searchUiManagerService, Object obj, int i) {
        super(searchUiManagerService, obj, i);
        this.mSessionInfos = new ArrayMap();
    }

    public final void destroyAndRebindRemoteService$2() {
        if (this.mRemoteService == null) {
            return;
        }
        if (this.mMaster.debug) {
            Slog.d("SearchUiPerUserService", "Destroying the old remote service.");
        }
        this.mRemoteService.destroy();
        this.mRemoteService = null;
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        RemoteSearchUiService remoteServiceLocked = getRemoteServiceLocked();
        this.mRemoteService = remoteServiceLocked;
        if (remoteServiceLocked != null) {
            if (this.mMaster.debug) {
                Slog.d("SearchUiPerUserService", "Rebinding to the new remote service.");
            }
            this.mRemoteService.reconnect();
        }
    }

    public final RemoteSearchUiService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            String componentNameLocked = getComponentNameLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (componentNameLocked == null) {
                if (!((SearchUiManagerService) abstractMasterSystemService).verbose) {
                    return null;
                }
                Slog.v("SearchUiPerUserService", "getRemoteServiceLocked(): not set");
                return null;
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(componentNameLocked);
            Context context = abstractMasterSystemService.getContext();
            SearchUiManagerService searchUiManagerService =
                    (SearchUiManagerService) abstractMasterSystemService;
            this.mRemoteService =
                    new RemoteSearchUiService(
                            context,
                            unflattenFromString,
                            this.mUserId,
                            this,
                            searchUiManagerService.isBindInstantServiceAllowed(),
                            searchUiManagerService.verbose);
        }
        return this.mRemoteService;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda2] */
    public final void onCreateSearchSessionLocked(
            SearchContext searchContext, final SearchSessionId searchSessionId, IBinder iBinder) {
        if (!resolveService(
                        new SearchUiPerUserService$$ExternalSyntheticLambda3(
                                searchContext, searchSessionId))
                || this.mSessionInfos.containsKey(searchSessionId)) {
            return;
        }
        SearchSessionInfo searchSessionInfo =
                new SearchSessionInfo(
                        searchSessionId,
                        searchContext,
                        iBinder,
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda2
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                SearchUiPerUserService searchUiPerUserService =
                                        SearchUiPerUserService.this;
                                SearchSessionId searchSessionId2 = searchSessionId;
                                synchronized (searchUiPerUserService.mLock) {
                                    searchUiPerUserService.onDestroyLocked(searchSessionId2);
                                }
                            }
                        });
        try {
            searchSessionInfo.mToken.linkToDeath(searchSessionInfo.mDeathRecipient, 0);
            this.mSessionInfos.put(searchSessionId, searchSessionInfo);
        } catch (RemoteException unused) {
            Slog.w(
                    "SearchUiPerUserService",
                    "Caller is dead before session can be started, sessionId: "
                            + searchSessionInfo.mSessionId);
            onDestroyLocked(searchSessionId);
        }
    }

    public final void onDestroyLocked(final SearchSessionId searchSessionId) {
        if (this.mMaster.debug) {
            Slog.d("SearchUiPerUserService", "onDestroyLocked(): sessionId=" + searchSessionId);
        }
        SearchSessionInfo searchSessionInfo =
                (SearchSessionInfo) this.mSessionInfos.remove(searchSessionId);
        if (searchSessionInfo == null) {
            return;
        }
        resolveService(
                new AbstractRemoteService
                        .AsyncRequest() { // from class:
                                          // com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda5
                    public final void run(IInterface iInterface) {
                        ((ISearchUiService) iInterface).onDestroy(searchSessionId);
                    }
                });
        Slog.d(
                "SearchUiPerUserService",
                "Removing all callbacks for session Id="
                        + searchSessionInfo.mSessionId
                        + " and "
                        + searchSessionInfo.mCallbacks.getRegisteredCallbackCount()
                        + " callbacks.");
        IBinder iBinder = searchSessionInfo.mToken;
        if (iBinder != null) {
            iBinder.unlinkToDeath(searchSessionInfo.mDeathRecipient, 0);
        }
        searchSessionInfo.mCallbacks.kill();
    }

    public final void onServiceDied(Object obj) {
        RemoteSearchUiService remoteSearchUiService = (RemoteSearchUiService) obj;
        if (this.mMaster.debug) {
            Slog.w("SearchUiPerUserService", "onServiceDied(): service=" + remoteSearchUiService);
        }
        synchronized (this.mLock) {
            this.mZombie = true;
        }
        RemoteSearchUiService remoteSearchUiService2 = this.mRemoteService;
        if (remoteSearchUiService2 != null) {
            remoteSearchUiService2.destroy();
            this.mRemoteService = null;
        }
    }

    public final void registerEmptyQueryResultUpdateCallbackLocked(
            SearchSessionId searchSessionId, ISearchCallback iSearchCallback) {
        SearchSessionInfo searchSessionInfo =
                (SearchSessionInfo) this.mSessionInfos.get(searchSessionId);
        if (searchSessionInfo != null
                && resolveService(
                        new SearchUiPerUserService$$ExternalSyntheticLambda3(
                                searchSessionId, iSearchCallback, 0))) {
            Slog.d(
                    "SearchUiPerUserService",
                    "Storing callback for session Id="
                            + searchSessionInfo.mSessionId
                            + " and callback="
                            + iSearchCallback.asBinder());
            searchSessionInfo.mCallbacks.register(iSearchCallback);
        }
    }

    public final boolean resolveService(AbstractRemoteService.AsyncRequest asyncRequest) {
        RemoteSearchUiService remoteServiceLocked = getRemoteServiceLocked();
        if (remoteServiceLocked != null) {
            remoteServiceLocked.executeOnResolvedService(asyncRequest);
        }
        return remoteServiceLocked != null;
    }

    public final void resurrectSessionsLocked$2() {
        int size = this.mSessionInfos.size();
        if (this.mMaster.debug) {
            Slog.d(
                    "SearchUiPerUserService",
                    "Resurrecting remote service ("
                            + this.mRemoteService
                            + ") on "
                            + size
                            + " sessions.");
        }
        for (final SearchSessionInfo searchSessionInfo : this.mSessionInfos.values()) {
            IBinder iBinder = searchSessionInfo.mToken;
            Slog.d(
                    "SearchUiPerUserService",
                    "Resurrecting remote service ("
                            + getRemoteServiceLocked()
                            + ") for session Id="
                            + searchSessionInfo.mSessionId
                            + " and "
                            + searchSessionInfo.mCallbacks.getRegisteredCallbackCount()
                            + " callbacks.");
            onCreateSearchSessionLocked(
                    searchSessionInfo.mSearchContext, searchSessionInfo.mSessionId, iBinder);
            searchSessionInfo.mCallbacks.broadcast(
                    new Consumer() { // from class:
                                     // com.android.server.searchui.SearchUiPerUserService$SearchSessionInfo$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.registerEmptyQueryResultUpdateCallbackLocked(
                                    SearchUiPerUserService.SearchSessionInfo.this.mSessionId,
                                    (ISearchCallback) obj);
                        }
                    });
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        RemoteSearchUiService remoteSearchUiService;
        boolean updateLocked = super.updateLocked(z);
        if (updateLocked
                && !isEnabledLocked()
                && (remoteSearchUiService = this.mRemoteService) != null) {
            remoteSearchUiService.destroy();
            this.mRemoteService = null;
        }
        return updateLocked;
    }
}
